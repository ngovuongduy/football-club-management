package gui;

import until.DatabaseConnection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class LichSuKhachHangDialog extends JDialog {
    private int maKH;
    private JTable table;
    private DefaultTableModel model;
    private JLabel lblTongChi;

    public LichSuKhachHangDialog(JFrame parent, int maKH, String tenKH) {
        super(parent, "Lịch sử mua hàng - " + tenKH, true);
        this.maKH = maKH;
        setSize(700, 500);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        // Bảng
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Mã HD", "Ngày mua", "Sản phẩm/Vé", "SL", "Đơn giá", "Thành tiền"});
        table = new JTable(model);
        table.setRowHeight(25);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Tổng chi
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        lblTongChi = new JLabel("Tổng chi tiêu: 0 VNĐ");
        lblTongChi.setFont(new Font("Arial", Font.BOLD, 14));
        lblTongChi.setForeground(Color.BLUE);
        bottomPanel.add(lblTongChi);
        
        JButton btnDong = new JButton("Đóng");
        btnDong.addActionListener(e -> dispose());
        bottomPanel.add(btnDong);
        add(bottomPanel, BorderLayout.SOUTH);

        loadData();
    }

    private void loadData() {
        model.setRowCount(0);
        double tongChi = 0;

        String sql = "SELECT hd.MaHD, hd.NgayHD, sp.TenSP AS Ten, ct.SoLuong AS SL, ct.DonGia AS Gia, " +
                     "(ct.SoLuong * ct.DonGia) AS ThanhTien " +
                     "FROM HOADON hd " +
                     "JOIN CTHD_SANPHAM ct ON hd.MaHD = ct.MaHD " +
                     "JOIN SANPHAM sp ON ct.MaSP = sp.MaSP " +
                     "WHERE hd.MaKH = ? " +
                     "UNION ALL " +
                     "SELECT hd.MaHD, hd.NgayHD, v.LoaiVe + ' - ' + t.DoiThu AS Ten, cv.SL, cv.Gia, " +
                     "(cv.SL * cv.Gia) AS ThanhTien " +
                     "FROM HOADON hd " +
                     "JOIN CTHD_VE cv ON hd.MaHD = cv.MaHD " +
                     "JOIN VETRANDAU v ON cv.MaVe = v.MaVe " +
                     "JOIN TRANDAU t ON v.MaTD = t.MaTD " +
                     "WHERE hd.MaKH = ? " +
                     "ORDER BY NgayHD DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maKH);
            ps.setInt(2, maKH);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    model.addRow(new Object[]{
                        rs.getInt("MaHD"),
                        rs.getDate("NgayHD"),
                        rs.getString("Ten"),
                        rs.getInt("SL"),
                        String.format("%,.0f", rs.getDouble("Gia")),
                        String.format("%,.0f", rs.getDouble("ThanhTien"))
                    });
                    tongChi += rs.getDouble("ThanhTien");
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
        }

        lblTongChi.setText("Tổng chi tiêu: " + String.format("%,.0f", tongChi) + " VNĐ");
    }
}
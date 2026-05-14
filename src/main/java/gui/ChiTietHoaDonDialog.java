package gui;

import bus.BanHangBUS;
import bus.BanVeBUS;
import dto.CthdSanPham;
import dto.CthdVe;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ChiTietHoaDonDialog extends JDialog {
    private int maHD;
    private JTable tableSP, tableVe;
    private DefaultTableModel modelSP, modelVe;
    private JLabel lblTongSP, lblTongVe, lblTongTien;
    private JButton btnThemSP, btnXoaSP, btnThemVe, btnXoaVe, btnDong;
    private BanHangBUS busSP = new BanHangBUS();
    private BanVeBUS busVe = new BanVeBUS();
    private Runnable onClose; 

    public ChiTietHoaDonDialog(JFrame parent, int maHD, Runnable onClose) {
    super(parent, "Chi tiết hóa đơn #" + maHD, true);
    this.maHD = maHD;
    this.onClose = onClose;
    // ... phần còn lại
        setSize(800, 600);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        // === TAB SẢN PHẨM ===
        JPanel panelSP = new JPanel(new BorderLayout());
        modelSP = new DefaultTableModel();
        modelSP.setColumnIdentifiers(new String[]{"Mã SP", "Tên SP", "SL", "Đơn giá", "Thành tiền"});
        tableSP = new JTable(modelSP);
        tableSP.setRowHeight(25);
        panelSP.add(new JScrollPane(tableSP), BorderLayout.CENTER);

        JPanel btnSP = new JPanel();
        btnThemSP = new JButton("Thêm SP");
        btnXoaSP = new JButton("Xóa SP");
        btnSP.add(btnThemSP);
        btnSP.add(btnXoaSP);
        panelSP.add(btnSP, BorderLayout.SOUTH);
        tabbedPane.addTab("Sản phẩm", panelSP);

        // === TAB VÉ ===
        JPanel panelVe = new JPanel(new BorderLayout());
        modelVe = new DefaultTableModel();
        modelVe.setColumnIdentifiers(new String[]{"Mã vé", "Loại vé", "Trận đấu", "Đối thủ", "SL", "Giá", "Thành tiền"});
        tableVe = new JTable(modelVe);
        tableVe.setRowHeight(25);
        panelVe.add(new JScrollPane(tableVe), BorderLayout.CENTER);

        JPanel btnVe = new JPanel();
        btnThemVe = new JButton("Thêm vé");
        btnXoaVe = new JButton("Xóa vé");
        btnVe.add(btnThemVe);
        btnVe.add(btnXoaVe);
        panelVe.add(btnVe, BorderLayout.SOUTH);
        tabbedPane.addTab("Vé", panelVe);

        add(tabbedPane, BorderLayout.CENTER);

        // === TỔNG TIỀN ===
        JPanel bottomPanel = new JPanel(new GridLayout(4, 1));

        // Dòng tổng SP
        JPanel panelTongSP = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        lblTongSP = new JLabel("Tổng tiền Sản phẩm: 0 VNĐ");
        lblTongSP.setFont(new Font("Arial", Font.PLAIN, 14));
        panelTongSP.add(lblTongSP);

        // Dòng tổng Vé
        JPanel panelTongVe = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        lblTongVe = new JLabel("Tổng tiền Vé: 0 VNĐ");
        lblTongVe.setFont(new Font("Arial", Font.PLAIN, 14));
        panelTongVe.add(lblTongVe);

        // Dòng tổng cộng
        JPanel panelTongCong = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        lblTongTien = new JLabel("TỔNG CỘNG: 0 VNĐ");
        lblTongTien.setFont(new Font("Arial", Font.BOLD, 18));
        lblTongTien.setForeground(Color.RED);
        panelTongCong.add(lblTongTien);

        // Nút Đóng
       JPanel panelDong = new JPanel(new FlowLayout(FlowLayout.CENTER));
btnDong = new JButton("Đóng");
btnDong.addActionListener(e -> {
    if (onClose != null) onClose.run();
    dispose();
});
panelDong.add(btnDong);

        bottomPanel.add(panelTongSP);
        bottomPanel.add(panelTongVe);
        bottomPanel.add(panelTongCong);
        bottomPanel.add(panelDong);
        add(bottomPanel, BorderLayout.SOUTH);

        // Sự kiện
        btnThemSP.addActionListener(e -> themSanPham());
        btnXoaSP.addActionListener(e -> xoaSanPham());
        btnThemVe.addActionListener(e -> themVe());
        btnXoaVe.addActionListener(e -> xoaVe());
       btnDong.addActionListener(e -> {
    if (onClose != null) onClose.run();  // 👈 THÊM DÒNG NÀY
    dispose();
});

        loadData();
    }

    private void loadData() {
        // Load SP
        modelSP.setRowCount(0);
        double tongSP = 0;
        try {
            List<CthdSanPham> listSP = busSP.getChiTietHD(maHD);
            for (CthdSanPham ct : listSP) {
                modelSP.addRow(new Object[]{
                    ct.getMaSP(), ct.getTenSP(), ct.getSoLuong(),
                    String.format("%,.0f", ct.getDonGia()),
                    String.format("%,.0f", ct.getThanhTien())
                });
                tongSP += ct.getThanhTien();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi SP: " + ex.getMessage());
        }

        // Load Vé
        modelVe.setRowCount(0);
        double tongVe = 0;
        try {
            List<CthdVe> listVe = busVe.getChiTietVe(maHD);
            for (CthdVe cv : listVe) {
                modelVe.addRow(new Object[]{
                    cv.getMaVe(), cv.getLoaiVe(), cv.getTenVongDau(),
                    cv.getDoiThu(), cv.getSl(),
                    String.format("%,.0f", cv.getGia()),
                    String.format("%,.0f", cv.getThanhTien())
                });
                tongVe += cv.getThanhTien();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi Vé: " + ex.getMessage());
        }

        // Cập nhật label
        lblTongSP.setText("Tổng tiền Sản phẩm: " + String.format("%,.0f", tongSP) + " VNĐ");
        lblTongVe.setText("Tổng tiền Vé: " + String.format("%,.0f", tongVe) + " VNĐ");
        lblTongTien.setText("TỔNG CỘNG: " + String.format("%,.0f", tongSP + tongVe) + " VNĐ");
    }

    private void themSanPham() {
        JTextField txtMaSP = new JTextField(10);
        JTextField txtSL = new JTextField(10);

        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.add(new JLabel("Mã SP:"));
        panel.add(txtMaSP);
        panel.add(new JLabel("Số lượng:"));
        panel.add(txtSL);

        int result = JOptionPane.showConfirmDialog(this, panel, "Thêm sản phẩm", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int maSP = Integer.parseInt(txtMaSP.getText().trim());
                int sl = Integer.parseInt(txtSL.getText().trim());
                busSP.themSanPhamVaoHD(maHD, maSP, sl);
                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
            }
        }
    }

    private void xoaSanPham() {
        int row = tableSP.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Chọn SP cần xóa!"); return; }
        int maSP = (int) modelSP.getValueAt(row, 0);
        int sl = (int) modelSP.getValueAt(row, 2);
        int confirm = JOptionPane.showConfirmDialog(this, "Xóa sản phẩm này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                busSP.xoaSanPhamKhoiHD(maHD, maSP, sl);
                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
            }
        }
    }

    private void themVe() {
        JTextField txtMaVe = new JTextField(10);
        JTextField txtSL = new JTextField(10);

        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.add(new JLabel("Mã vé:"));
        panel.add(txtMaVe);
        panel.add(new JLabel("Số lượng:"));
        panel.add(txtSL);

        int result = JOptionPane.showConfirmDialog(this, panel, "Thêm vé", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int maVe = Integer.parseInt(txtMaVe.getText().trim());
                int sl = Integer.parseInt(txtSL.getText().trim());
                busVe.themVeVaoHD(maHD, maVe, sl);
                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
            }
        }
    }

    private void xoaVe() {
        int row = tableVe.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Chọn vé cần xóa!"); return; }
        int maVe = (int) modelVe.getValueAt(row, 0);
        int sl = (int) modelVe.getValueAt(row, 4);
        int confirm = JOptionPane.showConfirmDialog(this, "Xóa vé này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                busVe.xoaVeKhoiHD(maHD, maVe, sl);
                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
            }
        }
    }
}
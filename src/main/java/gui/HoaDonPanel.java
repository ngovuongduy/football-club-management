package gui;

import bus.HoaDonBUS;
import dto.HoaDon;
import until.Session;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class HoaDonPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JButton btnTaoHD, btnChiTiet, btnLamMoi, btnTim;
    private JTextField txtTimKiem;
    private JComboBox<String> cboLoc;
    private HoaDonBUS bus = new HoaDonBUS();
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public HoaDonPanel() {
        setLayout(new BorderLayout());

        // Panel tìm kiếm + lọc
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        topPanel.add(new JLabel("Tìm kiếm:"));
        txtTimKiem = new JTextField(15);
        topPanel.add(txtTimKiem);
        
        topPanel.add(new JLabel("Lọc:"));
        cboLoc = new JComboBox<>(new String[]{"Tất cả", "Hôm nay", "7 ngày qua", "30 ngày qua"});
        topPanel.add(cboLoc);
        
        btnTim = new JButton("Tìm/Lọc");
        topPanel.add(btnTim);
        add(topPanel, BorderLayout.NORTH);

        // Bảng
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Mã HD", "Ngày", "Nhân viên", "Khách hàng", "Tổng tiền"});
        table = new JTable(model);
        table.setRowHeight(25);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Nút
        JPanel btnPanel = new JPanel();
        btnTaoHD = new JButton("Tạo hóa đơn");
        btnChiTiet = new JButton("Chi tiết");
        btnLamMoi = new JButton("Làm mới");
        btnPanel.add(btnTaoHD);
        btnPanel.add(btnChiTiet);
        btnPanel.add(btnLamMoi);
        add(btnPanel, BorderLayout.SOUTH);

        // Sự kiện
        btnLamMoi.addActionListener(e -> { txtTimKiem.setText(""); cboLoc.setSelectedIndex(0); loadData(); });
        btnTaoHD.addActionListener(e -> taoHoaDon());
        btnChiTiet.addActionListener(e -> xemChiTiet());
        btnTim.addActionListener(e -> timKiemVaLoc());
        txtTimKiem.addActionListener(e -> timKiemVaLoc());
        cboLoc.addActionListener(e -> timKiemVaLoc());

        loadData();
    }

    public void loadData() {
        model.setRowCount(0);
        try {
            List<HoaDon> list = bus.getAll();
            for (HoaDon hd : list) {
                model.addRow(new Object[]{
                    hd.getMaHD(), hd.getNgayHD(), hd.getTenNV(),
                    hd.getTenKH(), String.format("%,.0f", hd.getThanhTien())
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
        }
    }

    private void timKiemVaLoc() {
        String keyword = txtTimKiem.getText().trim().toLowerCase();
        int locIndex = cboLoc.getSelectedIndex();
        
        model.setRowCount(0);
        try {
            List<HoaDon> list = bus.getAll();
            
            // Lọc theo thời gian
            if (locIndex == 1) { // Hôm nay
                String today = sdf.format(new Date());
                list = list.stream().filter(hd -> sdf.format(hd.getNgayHD()).equals(today)).collect(Collectors.toList());
            }
            
            // Tìm kiếm + hiển thị
            for (HoaDon hd : list) {
                if (keyword.isEmpty() ||
                    String.valueOf(hd.getMaHD()).contains(keyword) ||
                    hd.getTenKH().toLowerCase().contains(keyword) ||
                    hd.getTenNV().toLowerCase().contains(keyword)) {
                    model.addRow(new Object[]{
                        hd.getMaHD(), hd.getNgayHD(), hd.getTenNV(),
                        hd.getTenKH(), String.format("%,.0f", hd.getThanhTien())
                    });
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
        }
    }

    private void taoHoaDon() {
        JTextField txtMaKH = new JTextField();
        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.add(new JLabel("Mã KH (bỏ trống nếu khách lẻ):"));
        panel.add(txtMaKH);

        int result = JOptionPane.showConfirmDialog(this, panel, "Tạo hóa đơn mới", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                Integer maKH = null;
                String maKHStr = txtMaKH.getText().trim();
                if (!maKHStr.isEmpty()) maKH = Integer.parseInt(maKHStr);
                int maHD = bus.taoHoaDon(Session.maNV, maKH);
                JOptionPane.showMessageDialog(this, "Tạo hóa đơn thành công! Mã HD: " + maHD);
                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
            }
        }
    }

    private void xemChiTiet() {
        int row = table.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Chọn hóa đơn cần xem!"); return; }
        int maHD = (int) model.getValueAt(row, 0);
        new ChiTietHoaDonDialog((JFrame) SwingUtilities.getWindowAncestor(this), maHD, this::loadData).setVisible(true);
    }
}
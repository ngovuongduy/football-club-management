package gui;

import bus.KhachHangBUS;
import dto.KhachHang;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class KhachHangPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JButton btnThem, btnSua, btnXoa, btnLichSu, btnLamMoi, btnTim;
    private JTextField txtTimKiem;
    private JComboBox<String> cboLoaiKH;
    private KhachHangBUS bus = new KhachHangBUS();
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public KhachHangPanel() {
        setLayout(new BorderLayout());

        // Panel tìm kiếm + lọc
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        topPanel.add(new JLabel("Tìm kiếm (Tên/SĐT):"));
        txtTimKiem = new JTextField(15);
        topPanel.add(txtTimKiem);
        
        topPanel.add(new JLabel("Loại KH:"));
        cboLoaiKH = new JComboBox<>(new String[]{"Tất cả", "VIP", "Thân thiết", "Thường"});
        topPanel.add(cboLoaiKH);
        
        btnTim = new JButton("Tìm/Lọc");
        topPanel.add(btnTim);
        add(topPanel, BorderLayout.NORTH);

        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Mã KH", "Họ tên", "Giới tính", "Ngày sinh", "SĐT", "Loại KH", "Điểm tích lũy"});
        table = new JTable(model);
        table.setRowHeight(25);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnLichSu = new JButton("Lịch sử");
        btnLamMoi = new JButton("Làm mới");
        btnPanel.add(btnThem);
        btnPanel.add(btnSua);
        btnPanel.add(btnXoa);
        btnPanel.add(btnLichSu);
        btnPanel.add(btnLamMoi);
        add(btnPanel, BorderLayout.SOUTH);

        // Sự kiện
        btnLamMoi.addActionListener(e -> { txtTimKiem.setText(""); cboLoaiKH.setSelectedIndex(0); loadData(); });
        btnThem.addActionListener(e -> themKH());
        btnSua.addActionListener(e -> suaKH());
        btnXoa.addActionListener(e -> xoaKH());
        btnLichSu.addActionListener(e -> xemLichSu());
        btnTim.addActionListener(e -> timKiemVaLoc());
        txtTimKiem.addActionListener(e -> timKiemVaLoc());
        cboLoaiKH.addActionListener(e -> timKiemVaLoc());

        loadData();
    }

    private void loadData() {
        model.setRowCount(0);
        try {
            List<KhachHang> list = bus.getAll();
            for (KhachHang kh : list) {
                model.addRow(new Object[]{
                    kh.getMaKH(), kh.getHoTen(), kh.getGioiTinh(),
                    kh.getNgaySinh() != null ? sdf.format(kh.getNgaySinh()) : "",
                    kh.getSoDT(), kh.getLoaiKH(), kh.getDiemTichLuy()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
        }
    }

    private void timKiemVaLoc() {
        String keyword = txtTimKiem.getText().trim().toLowerCase();
        String loaiKH = (String) cboLoaiKH.getSelectedItem();
        
        model.setRowCount(0);
        try {
            List<KhachHang> list = bus.getAll();
            for (KhachHang kh : list) {
                // Lọc loại KH
                if (!loaiKH.equals("Tất cả") && !kh.getLoaiKH().equals(loaiKH)) continue;
                
                // Tìm kiếm
                if (keyword.isEmpty() ||
                    kh.getHoTen().toLowerCase().contains(keyword) ||
                    kh.getSoDT().contains(keyword)) {
                    model.addRow(new Object[]{
                        kh.getMaKH(), kh.getHoTen(), kh.getGioiTinh(),
                        kh.getNgaySinh() != null ? sdf.format(kh.getNgaySinh()) : "",
                        kh.getSoDT(), kh.getLoaiKH(), kh.getDiemTichLuy()
                    });
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
        }
    }

    private void themKH() {
        JTextField txtMa = new JTextField(10);
        JTextField txtHo = new JTextField(10);
        JTextField txtTen = new JTextField(10);
        JComboBox<String> cboGT = new JComboBox<>(new String[]{"Nam", "Nữ"});
        JTextField txtNgaySinh = new JTextField("1995-01-01", 10);
        JTextField txtSDT = new JTextField(10);

        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.add(new JLabel("Mã KH:"));
        panel.add(txtMa);
        panel.add(new JLabel("Họ:"));
        panel.add(txtHo);
        panel.add(new JLabel("Tên:"));
        panel.add(txtTen);
        panel.add(new JLabel("Giới tính:"));
        panel.add(cboGT);
        panel.add(new JLabel("Ngày sinh (yyyy-MM-dd):"));
        panel.add(txtNgaySinh);
        panel.add(new JLabel("SĐT:"));
        panel.add(txtSDT);

        int result = JOptionPane.showConfirmDialog(this, panel, "Thêm khách hàng", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                KhachHang kh = new KhachHang();
                kh.setMaKH(Integer.parseInt(txtMa.getText()));
                kh.setHo(txtHo.getText());
                kh.setTen(txtTen.getText());
                kh.setGioiTinh((String) cboGT.getSelectedItem());
                kh.setNgaySinh(new SimpleDateFormat("yyyy-MM-dd").parse(txtNgaySinh.getText()));
                kh.setSoDT(txtSDT.getText());
                kh.setLoaiKH("Thường");
                kh.setDiemTichLuy(0);
                bus.them(kh);
                loadData();
                JOptionPane.showMessageDialog(this, "Thêm thành công!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
            }
        }
    }

    private void suaKH() {
        int row = table.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Chọn khách hàng!"); return; }
        int maKH = (int) model.getValueAt(row, 0);
        String hoTen = (String) model.getValueAt(row, 1);
        String[] parts = hoTen.split(" ", 2);
        JTextField txtHo = new JTextField(parts[0], 10);
        JTextField txtTen = new JTextField(parts.length > 1 ? parts[1] : "", 10);
        JTextField txtSDT = new JTextField((String) model.getValueAt(row, 4), 10);

        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.add(new JLabel("Họ:"));
        panel.add(txtHo);
        panel.add(new JLabel("Tên:"));
        panel.add(txtTen);
        panel.add(new JLabel("SĐT:"));
        panel.add(txtSDT);

        int result = JOptionPane.showConfirmDialog(this, panel, "Sửa khách hàng", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                KhachHang kh = new KhachHang();
                kh.setMaKH(maKH);
                kh.setHo(txtHo.getText());
                kh.setTen(txtTen.getText());
                kh.setGioiTinh((String) model.getValueAt(row, 2));
                try { kh.setNgaySinh(sdf.parse((String) model.getValueAt(row, 3))); } catch (Exception e) {}
                kh.setSoDT(txtSDT.getText());
                kh.setLoaiKH((String) model.getValueAt(row, 5));
                kh.setDiemTichLuy((double) model.getValueAt(row, 6));
                bus.sua(kh);
                loadData();
                JOptionPane.showMessageDialog(this, "Sửa thành công!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
            }
        }
    }

    private void xoaKH() {
        int row = table.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Chọn khách hàng!"); return; }
        int maKH = (int) model.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Xóa khách hàng này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                bus.xoa(maKH);
                loadData();
                JOptionPane.showMessageDialog(this, "Xóa thành công!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
            }
        }
    }

    private void xemLichSu() {
        int row = table.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Chọn khách hàng!"); return; }
        int maKH = (int) model.getValueAt(row, 0);
        String tenKH = (String) model.getValueAt(row, 1);
        new LichSuKhachHangDialog((JFrame) SwingUtilities.getWindowAncestor(this), maKH, tenKH).setVisible(true);
    }
}
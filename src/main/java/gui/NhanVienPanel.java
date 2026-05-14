package gui;

import bus.NhanVienBUS;
import dto.NhanVien;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class NhanVienPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JButton btnThem, btnSua, btnXoa, btnTaiKhoan, btnLamMoi, btnTim;
    private JTextField txtTimKiem;
    private NhanVienBUS bus = new NhanVienBUS();
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public NhanVienPanel() {
        setLayout(new BorderLayout());

        // Panel tìm kiếm
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        topPanel.add(new JLabel("Tìm kiếm (Tên/SĐT/TK):"));
        txtTimKiem = new JTextField(15);
        topPanel.add(txtTimKiem);
        btnTim = new JButton("Tìm");
        topPanel.add(btnTim);
        add(topPanel, BorderLayout.NORTH);

        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Mã NV", "Họ tên", "Giới tính", "SĐT", "CMND", "Ngày sinh", "Ngày vào làm", "Lương", "Tài khoản", "Quyền"});
        table = new JTable(model);
        table.setRowHeight(25);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnTaiKhoan = new JButton("Tài khoản");
        btnLamMoi = new JButton("Làm mới");
        btnPanel.add(btnThem);
        btnPanel.add(btnSua);
        btnPanel.add(btnXoa);
        btnPanel.add(btnTaiKhoan);
        btnPanel.add(btnLamMoi);
        add(btnPanel, BorderLayout.SOUTH);

        // Sự kiện
        btnLamMoi.addActionListener(e -> { txtTimKiem.setText(""); loadData(); });
        btnThem.addActionListener(e -> themNhanVien());
        btnSua.addActionListener(e -> suaNhanVien());
        btnXoa.addActionListener(e -> xoaNhanVien());
        btnTaiKhoan.addActionListener(e -> quanLyTaiKhoan());
        btnTim.addActionListener(e -> timKiem());
        txtTimKiem.addActionListener(e -> timKiem());

        loadData();
    }

    private void loadData() {
        model.setRowCount(0);
        try {
            List<NhanVien> list = bus.getAll();
            for (NhanVien nv : list) {
                model.addRow(new Object[]{
                    nv.getMaNV(), nv.getHoTen(), nv.getGioiTinh(), nv.getSoDT(),
                    nv.getCmnd(), nv.getNgaySinh() != null ? sdf.format(nv.getNgaySinh()) : "",
                    nv.getNgayBD() != null ? sdf.format(nv.getNgayBD()) : "",
                    nv.getLuong(),
                    nv.getTenTK() != null ? nv.getTenTK() : "Chưa có",
                    nv.getLoaiTK() != null ? nv.getLoaiTK() : ""
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
        }
    }

    private void timKiem() {
        String keyword = txtTimKiem.getText().trim().toLowerCase();
        model.setRowCount(0);
        try {
            List<NhanVien> list = bus.getAll();
            for (NhanVien nv : list) {
                if (keyword.isEmpty() ||
                    nv.getHoTen().toLowerCase().contains(keyword) ||
                    nv.getSoDT().contains(keyword) ||
                    (nv.getTenTK() != null && nv.getTenTK().toLowerCase().contains(keyword))) {
                    model.addRow(new Object[]{
                        nv.getMaNV(), nv.getHoTen(), nv.getGioiTinh(), nv.getSoDT(),
                        nv.getCmnd(), nv.getNgaySinh() != null ? sdf.format(nv.getNgaySinh()) : "",
                        nv.getNgayBD() != null ? sdf.format(nv.getNgayBD()) : "",
                        nv.getLuong(),
                        nv.getTenTK() != null ? nv.getTenTK() : "Chưa có",
                        nv.getLoaiTK() != null ? nv.getLoaiTK() : ""
                    });
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
        }
    }

    private void themNhanVien() {
        JTextField txtMa = new JTextField(10);
        JTextField txtHo = new JTextField(10);
        JTextField txtTen = new JTextField(10);
        JComboBox<String> cboGT = new JComboBox<>(new String[]{"Nam", "Nữ"});
        JTextField txtDiaChi = new JTextField(15);
        JTextField txtCMND = new JTextField(10);
        JTextField txtNgaySinh = new JTextField("1990-01-01", 10);
        JTextField txtNgayBD = new JTextField("2020-01-01", 10);
        JTextField txtSDT = new JTextField(10);
        JTextField txtLuong = new JTextField(10);

        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.add(new JLabel("Mã NV:"));
        panel.add(txtMa);
        panel.add(new JLabel("Họ:"));
        panel.add(txtHo);
        panel.add(new JLabel("Tên:"));
        panel.add(txtTen);
        panel.add(new JLabel("Giới tính:"));
        panel.add(cboGT);
        panel.add(new JLabel("Địa chỉ:"));
        panel.add(txtDiaChi);
        panel.add(new JLabel("CMND:"));
        panel.add(txtCMND);
        panel.add(new JLabel("Ngày sinh (yyyy-MM-dd):"));
        panel.add(txtNgaySinh);
        panel.add(new JLabel("Ngày vào làm:"));
        panel.add(txtNgayBD);
        panel.add(new JLabel("SĐT:"));
        panel.add(txtSDT);
        panel.add(new JLabel("Lương:"));
        panel.add(txtLuong);

        int result = JOptionPane.showConfirmDialog(this, panel, "Thêm nhân viên", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                NhanVien nv = new NhanVien();
                nv.setMaNV(Integer.parseInt(txtMa.getText()));
                nv.setHo(txtHo.getText());
                nv.setTen(txtTen.getText());
                nv.setGioiTinh((String) cboGT.getSelectedItem());
                nv.setDiaChi(txtDiaChi.getText());
                nv.setCmnd(txtCMND.getText());
                nv.setNgaySinh(new SimpleDateFormat("yyyy-MM-dd").parse(txtNgaySinh.getText()));
                nv.setNgayBD(new SimpleDateFormat("yyyy-MM-dd").parse(txtNgayBD.getText()));
                nv.setSoDT(txtSDT.getText());
                nv.setLuong(Double.parseDouble(txtLuong.getText()));
                bus.them(nv);
                loadData();
                JOptionPane.showMessageDialog(this, "Thêm thành công!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
            }
        }
    }

    private void suaNhanVien() {
        int row = table.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Chọn nhân viên!"); return; }
        String luongStr = JOptionPane.showInputDialog(this, "Nhập lương mới:", model.getValueAt(row, 7));
        if (luongStr != null) {
            try {
                NhanVien nv = new NhanVien();
                nv.setMaNV((int) model.getValueAt(row, 0));
                String hoTen = (String) model.getValueAt(row, 1);
                String[] parts = hoTen.split(" ", 2);
                nv.setHo(parts[0]);
                nv.setTen(parts.length > 1 ? parts[1] : "");
                nv.setGioiTinh((String) model.getValueAt(row, 2));
                nv.setDiaChi("");
                nv.setCmnd((String) model.getValueAt(row, 4));
                try { nv.setNgaySinh(sdf.parse((String) model.getValueAt(row, 5))); } catch (Exception e) {}
                try { nv.setNgayBD(sdf.parse((String) model.getValueAt(row, 6))); } catch (Exception e) {}
                nv.setSoDT((String) model.getValueAt(row, 3));
                nv.setLuong(Double.parseDouble(luongStr));
                bus.sua(nv);
                loadData();
                JOptionPane.showMessageDialog(this, "Sửa thành công!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
            }
        }
    }

    private void xoaNhanVien() {
        int row = table.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Chọn nhân viên!"); return; }
        int maNV = (int) model.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Xóa nhân viên này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                bus.xoa(maNV);
                loadData();
                JOptionPane.showMessageDialog(this, "Xóa thành công!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
            }
        }
    }

    private void quanLyTaiKhoan() {
        int row = table.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Chọn nhân viên!"); return; }
        int maNV = (int) model.getValueAt(row, 0);
        String tenTKCu = (String) model.getValueAt(row, 8);
        if ("Chưa có".equals(tenTKCu)) tenTKCu = "";

        JTextField txtTenTK = new JTextField(tenTKCu, 15);
        JTextField txtMK = new JTextField("123456", 15);
        JComboBox<String> cboQuyen = new JComboBox<>(new String[]{"Admin", "User"});

        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.add(new JLabel("Tên tài khoản:"));
        panel.add(txtTenTK);
        panel.add(new JLabel("Mật khẩu:"));
        panel.add(txtMK);
        panel.add(new JLabel("Quyền:"));
        panel.add(cboQuyen);

        int result = JOptionPane.showConfirmDialog(this, panel, "Quản lý tài khoản", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                bus.saveTaiKhoan(maNV, txtTenTK.getText(), txtMK.getText(), (String) cboQuyen.getSelectedItem());
                loadData();
                JOptionPane.showMessageDialog(this, "Lưu tài khoản thành công!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
            }
        }
    }
}
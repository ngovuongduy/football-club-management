package gui;

import bus.NhaCungCapBUS;
import dto.NhaCungCap;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class NhaCungCapPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JButton btnThem, btnSua, btnXoa, btnLamMoi;
    private NhaCungCapBUS bus = new NhaCungCapBUS();

    public NhaCungCapPanel() {
        setLayout(new BorderLayout());

        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Mã NCC", "Tên NCC", "Đia chỉ", "SĐT"});
        table = new JTable(model);
        table.setRowHeight(25);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnLamMoi = new JButton("Làm mới");
        btnPanel.add(btnThem);
        btnPanel.add(btnSua);
        btnPanel.add(btnXoa);
        btnPanel.add(btnLamMoi);
        add(btnPanel, BorderLayout.SOUTH);

        btnLamMoi.addActionListener(e -> loadData());
        btnThem.addActionListener(e -> themNCC());
        btnSua.addActionListener(e -> suaNCC());
        btnXoa.addActionListener(e -> xoaNCC());

        loadData();
    }

    private void loadData() {
        model.setRowCount(0);
        try {
            List<NhaCungCap> list = bus.getAll();
            for (NhaCungCap ncc : list) {
                model.addRow(new Object[]{
                    ncc.getMaNCC(), ncc.getTenNCC(), ncc.getDiaChi(), ncc.getSoDT()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
        }
    }

    private void themNCC() {
        JTextField txtMa = new JTextField(10);
        JTextField txtTen = new JTextField(15);
        JTextField txtDiaChi = new JTextField(15);
        JTextField txtSDT = new JTextField(10);

        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.add(new JLabel("Mã NCC:"));
        panel.add(txtMa);
        panel.add(new JLabel("Tên NCC:"));
        panel.add(txtTen);
        panel.add(new JLabel("Địa chỉ:"));
        panel.add(txtDiaChi);
        panel.add(new JLabel("SĐT:"));
        panel.add(txtSDT);

        int result = JOptionPane.showConfirmDialog(this, panel, "Thêm nhà cung cấp", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                NhaCungCap ncc = new NhaCungCap();
                ncc.setMaNCC(Integer.parseInt(txtMa.getText()));
                ncc.setTenNCC(txtTen.getText());
                ncc.setDiaChi(txtDiaChi.getText());
                ncc.setSoDT(txtSDT.getText());
                bus.them(ncc);
                loadData();
                JOptionPane.showMessageDialog(this, "Thêm thành công!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
            }
        }
    }

    private void suaNCC() {
        int row = table.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Chọn NCC!"); return; }

        int maNCC = (int) model.getValueAt(row, 0);
        JTextField txtTen = new JTextField((String) model.getValueAt(row, 1), 15);
        JTextField txtDiaChi = new JTextField((String) model.getValueAt(row, 2), 15);
        JTextField txtSDT = new JTextField((String) model.getValueAt(row, 3), 10);

        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.add(new JLabel("Tên NCC:"));
        panel.add(txtTen);
        panel.add(new JLabel("Địa chỉ:"));
        panel.add(txtDiaChi);
        panel.add(new JLabel("SĐT:"));
        panel.add(txtSDT);

        int result = JOptionPane.showConfirmDialog(this, panel, "Sửa nhà cung cấp", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                NhaCungCap ncc = new NhaCungCap();
                ncc.setMaNCC(maNCC);
                ncc.setTenNCC(txtTen.getText());
                ncc.setDiaChi(txtDiaChi.getText());
                ncc.setSoDT(txtSDT.getText());
                bus.sua(ncc);
                loadData();
                JOptionPane.showMessageDialog(this, "Sửa thành công!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
            }
        }
    }

    private void xoaNCC() {
        int row = table.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Chọn NCC!"); return; }
        int maNCC = (int) model.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Xóa NCC này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                bus.xoa(maNCC);
                loadData();
                JOptionPane.showMessageDialog(this, "Xóa thành công!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
            }
        }
    }
}
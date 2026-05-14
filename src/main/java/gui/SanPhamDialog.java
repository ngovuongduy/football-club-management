package gui;

import dto.SanPham;
import javax.swing.*;
import java.awt.*;

public class SanPhamDialog extends JDialog {
    private JTextField txtMaSP, txtTenSP, txtGia, txtSL, txtMauSac, txtKichCo, txtMaNCC;
    private JButton btnLuu, btnHuy;
    private boolean confirmed = false;
    private SanPham sanPham;

    public SanPhamDialog(JFrame parent, String title, SanPham oldSp) {
        super(parent, title, true);
        setSize(420, 380);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Mã sản phẩm
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Mã SP:"), gbc);
        txtMaSP = new JTextField(15);
        gbc.gridx = 1;
        add(txtMaSP, gbc);

        // Tên sản phẩm
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Tên SP:"), gbc);
        txtTenSP = new JTextField(15);
        gbc.gridx = 1;
        add(txtTenSP, gbc);

        // Giá
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Giá:"), gbc);
        txtGia = new JTextField(15);
        gbc.gridx = 1;
        add(txtGia, gbc);

        // Số lượng
        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Số lượng:"), gbc);
        txtSL = new JTextField(15);
        gbc.gridx = 1;
        add(txtSL, gbc);

        // Màu sắc
        gbc.gridx = 0; gbc.gridy = 4;
        add(new JLabel("Màu sắc:"), gbc);
        txtMauSac = new JTextField(15);
        gbc.gridx = 1;
        add(txtMauSac, gbc);

        // Kích cỡ
        gbc.gridx = 0; gbc.gridy = 5;
        add(new JLabel("Kích cỡ:"), gbc);
        txtKichCo = new JTextField(15);
        gbc.gridx = 1;
        add(txtKichCo, gbc);

        // Mã nhà cung cấp
        gbc.gridx = 0; gbc.gridy = 6;
        add(new JLabel("Mã NCC:"), gbc);
        txtMaNCC = new JTextField(15);
        gbc.gridx = 1;
        add(txtMaNCC, gbc);

        // Panel nút
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        btnLuu = new JButton("Lưu");
        btnHuy = new JButton("Hủy");
        btnPanel.add(btnLuu);
        btnPanel.add(btnHuy);

        gbc.gridx = 0; gbc.gridy = 7;
        gbc.gridwidth = 2;
        add(btnPanel, gbc);

        // Điền dữ liệu cũ nếu có (chế độ sửa)
        if (oldSp != null) {
            txtMaSP.setText(String.valueOf(oldSp.getMaSP()));
            txtMaSP.setEditable(false);   // không cho sửa mã
            txtTenSP.setText(oldSp.getTenSP());
            txtGia.setText(String.valueOf(oldSp.getGia()));
            txtSL.setText(String.valueOf(oldSp.getSl()));
            txtMauSac.setText(oldSp.getMauSac() != null ? oldSp.getMauSac() : "");
            txtKichCo.setText(oldSp.getKichCo() != null ? oldSp.getKichCo() : "");
            txtMaNCC.setText(String.valueOf(oldSp.getMaNCC()));
        }

        // Sự kiện nút Lưu
        btnLuu.addActionListener(e -> {
            try {
                // Validate dữ liệu cơ bản
                String maSPStr = txtMaSP.getText().trim();
                String tenSP = txtTenSP.getText().trim();
                String giaStr = txtGia.getText().trim();
                String slStr = txtSL.getText().trim();
                String mauSac = txtMauSac.getText().trim();
                String kichCo = txtKichCo.getText().trim();
                String maNCCStr = txtMaNCC.getText().trim();

                if (maSPStr.isEmpty() || tenSP.isEmpty() || giaStr.isEmpty() || slStr.isEmpty() || maNCCStr.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin bắt buộc!");
                    return;
                }

                int maSP = Integer.parseInt(maSPStr);
                double gia = Double.parseDouble(giaStr);
                int sl = Integer.parseInt(slStr);
                int maNCC = Integer.parseInt(maNCCStr);

                if (gia < 0 || sl < 0) {
                    JOptionPane.showMessageDialog(this, "Giá và số lượng không được âm!");
                    return;
                }

                // Tạo đối tượng sản phẩm
                sanPham = new SanPham();
                sanPham.setMaSP(maSP);
                sanPham.setTenSP(tenSP);
                sanPham.setGia(gia);
                sanPham.setSl(sl);
                sanPham.setMauSac(mauSac);
                sanPham.setKichCo(kichCo);
                sanPham.setMaNCC(maNCC);
                sanPham.setLoaiSP(1);   // mặc định loại 1, bạn có thể thêm field nếu cần

                confirmed = true;
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số hợp lệ cho Mã SP, Giá, Số lượng, Mã NCC!");
            }
        });

        btnHuy.addActionListener(e -> dispose());
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public SanPham getSanPham() {
        return sanPham;
    }
}
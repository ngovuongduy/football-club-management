package gui;

import bus.TaiKhoanBUS;
import dto.TaiKhoan;
import until.ArsenalTheme;
import until.Session;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JTextField txtTenTK;
    private JPasswordField txtMatKhau;
    private JButton btnDangNhap;

    public LoginFrame() {
        setTitle("ARSENAL FC - Đăng nhập");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 380);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(ArsenalTheme.WHITE);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 25, 5, 25);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ===== TIÊU ĐỀ =====
        JLabel lblTitle = new JLabel("ARSENAL FC", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 30));
        lblTitle.setForeground(ArsenalTheme.RED);
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(30, 25, 5, 25);
        mainPanel.add(lblTitle, gbc);

        JLabel lblSubtitle = new JLabel("HỆ THỐNG QUẢN LÝ CÂU LẠC BỘ", JLabel.CENTER);
        lblSubtitle.setFont(new Font("Arial", Font.PLAIN, 11));
        lblSubtitle.setForeground(ArsenalTheme.GRAY);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 25, 20, 25);
        mainPanel.add(lblSubtitle, gbc);

        // ===== ĐƯỜNG KẺ =====
        JPanel line = new JPanel();
        line.setBackground(ArsenalTheme.GOLD);
        line.setPreferredSize(new Dimension(300, 3));
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 25, 25, 25);
        mainPanel.add(line, gbc);

        // ===== FORM =====
        gbc.gridwidth = 1;

        JLabel lblTenTK = new JLabel("Tài khoản");
        lblTenTK.setFont(new Font("Arial", Font.BOLD, 12));
        lblTenTK.setForeground(ArsenalTheme.DARK_RED);
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.insets = new Insets(5, 25, 5, 5);
        mainPanel.add(lblTenTK, gbc);
        
        txtTenTK = new JTextField(15);
        txtTenTK.setFont(new Font("Arial", Font.PLAIN, 14));
        txtTenTK.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ArsenalTheme.RED, 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        gbc.gridx = 1;
        gbc.insets = new Insets(5, 5, 5, 25);
        mainPanel.add(txtTenTK, gbc);

        JLabel lblMK = new JLabel("Mật khẩu");
        lblMK.setFont(new Font("Arial", Font.BOLD, 12));
        lblMK.setForeground(ArsenalTheme.DARK_RED);
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.insets = new Insets(5, 25, 5, 5);
        mainPanel.add(lblMK, gbc);
        
        txtMatKhau = new JPasswordField(15);
        txtMatKhau.setFont(new Font("Arial", Font.PLAIN, 14));
        txtMatKhau.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ArsenalTheme.RED, 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        gbc.gridx = 1;
        gbc.insets = new Insets(5, 5, 5, 25);
        mainPanel.add(txtMatKhau, gbc);

        // ===== NÚT =====
        btnDangNhap = new JButton("ĐĂNG NHẬP");
        btnDangNhap.setFont(new Font("Arial", Font.BOLD, 15));
        btnDangNhap.setBackground(ArsenalTheme.RED);
        btnDangNhap.setForeground(ArsenalTheme.WHITE);
        btnDangNhap.setFocusPainted(false);
        btnDangNhap.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDangNhap.setBorder(BorderFactory.createEmptyBorder(12, 40, 12, 40));
        
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(25, 25, 20, 25);
        mainPanel.add(btnDangNhap, gbc);

        // ===== FOOTER =====
        JLabel lblFooter = new JLabel("#COYG © 2026", JLabel.CENTER);
        lblFooter.setFont(new Font("Arial", Font.PLAIN, 9));
        lblFooter.setForeground(ArsenalTheme.GRAY);
        gbc.gridy = 6;
        gbc.insets = new Insets(0, 25, 15, 25);
        mainPanel.add(lblFooter, gbc);

        btnDangNhap.addActionListener(e -> xuLyDangNhap());
        txtMatKhau.addActionListener(e -> xuLyDangNhap());
        getRootPane().setDefaultButton(btnDangNhap);

        add(mainPanel);
    }

    private void xuLyDangNhap() {
        String tenTK = txtTenTK.getText().trim();
        String matKhau = new String(txtMatKhau.getPassword());

        if (tenTK.isEmpty() || matKhau.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Arsenal FC", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            TaiKhoanBUS bus = new TaiKhoanBUS();
            TaiKhoan tk = bus.dangNhap(tenTK, matKhau);
            if (tk != null) {
                Session.tenTK = tk.getTenTK();
                Session.maNV = tk.getMaNV();
                Session.loaiTK = tk.getLoaiTK();
                new MainFrame().setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Sai tài khoản hoặc mật khẩu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {}
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
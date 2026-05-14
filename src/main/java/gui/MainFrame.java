package gui;

import until.ArsenalTheme;
import until.Session;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private JPanel contentPanel;
    private JLabel lblContentTitle;

    public MainFrame() {
        setTitle("ARSENAL FC - Trang chủ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        // ===== HEADER =====
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(ArsenalTheme.RED);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));

        JLabel lblHeader = new JLabel("ARSENAL FC - TRANG CHỦ");
        lblHeader.setFont(new Font("Arial", Font.BOLD, 18));
        lblHeader.setForeground(ArsenalTheme.WHITE);
        headerPanel.add(lblHeader, BorderLayout.WEST);

        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        userPanel.setOpaque(false);

        JLabel lblUser = new JLabel("Admin: " + Session.tenTK);
        lblUser.setFont(new Font("Arial", Font.BOLD, 13));
        lblUser.setForeground(ArsenalTheme.GOLD);
        userPanel.add(lblUser);

        JButton btnLogout = new JButton("Đăng xuất");
        styleHeaderBtn(btnLogout);
        btnLogout.addActionListener(e -> {
            Session.clear();
            new LoginFrame().setVisible(true);
            dispose();
        });
        userPanel.add(btnLogout);

        JButton btnExit = new JButton("Thoát");
        styleHeaderBtn(btnExit);
        btnExit.addActionListener(e -> System.exit(0));
        userPanel.add(btnExit);

        headerPanel.add(userPanel, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);

        // ===== MENU TRÁI =====
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(ArsenalTheme.WHITE);
        menuPanel.setPreferredSize(new Dimension(230, 0));

        menuPanel.add(createMenuTitle("QUẢN LÝ"));
        menuPanel.add(createMenuItem("Quản lý hoá đơn", () -> showPanel(new HoaDonPanel(), "Quản lý Hoá đơn")));
        menuPanel.add(createMenuItem("Quản lý sản phẩm", () -> showPanel(new SanPhamPanel(), "Quản lý Sản phẩm")));
        menuPanel.add(createMenuItem("Quản lý nhân viên", () -> showPanel(new NhanVienPanel(), "Quản lý Nhân viên")));
        menuPanel.add(createMenuItem("Quản lý khách hàng", () -> showPanel(new KhachHangPanel(), "Quản lý Khách hàng")));
        menuPanel.add(createMenuItem("Trận đấu & Vé", () -> showPanel(new TranDauPanel(), "Trận đấu & Vé")));
        menuPanel.add(createMenuItem("Nhà cung cấp", () -> showPanel(new NhaCungCapPanel(), "Nhà cung cấp")));
        menuPanel.add(createMenuItem("Phiếu nhập hàng", () -> showPanel(new PhieuNhapPanel(), "Phiếu nhập hàng")));
        menuPanel.add(Box.createVerticalStrut(10));

        menuPanel.add(createMenuTitle("BÁO CÁO"));
        menuPanel.add(createMenuItem("Thống kê báo cáo", () -> showPanel(new ThongKePanel(), "Thống kê báo cáo")));
        menuPanel.add(Box.createVerticalGlue());

        add(menuPanel, BorderLayout.WEST);

        // ===== CONTENT =====
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(ArsenalTheme.WHITE);

        lblContentTitle = new JLabel("Trang chủ");
        lblContentTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblContentTitle.setForeground(ArsenalTheme.RED);
        lblContentTitle.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel lblDefault = new JLabel("Chọn chức năng bên trái để bắt đầu", JLabel.CENTER);
        lblDefault.setFont(new Font("Arial", Font.PLAIN, 18));
        lblDefault.setForeground(ArsenalTheme.GRAY);

        contentPanel.add(lblContentTitle, BorderLayout.NORTH);
        contentPanel.add(lblDefault, BorderLayout.CENTER);
        add(contentPanel, BorderLayout.CENTER);

        // ===== FOOTER =====
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBackground(ArsenalTheme.LIGHT_GRAY);
        JLabel lblFooter = new JLabel("Emirates Stadium | Arsenal FC © 2026 | #COYG");
        lblFooter.setFont(new Font("Arial", Font.PLAIN, 10));
        lblFooter.setForeground(ArsenalTheme.GRAY);
        footerPanel.add(lblFooter);
        add(footerPanel, BorderLayout.SOUTH);
    }

    private void styleHeaderBtn(JButton btn) {
        btn.setFont(new Font("Arial", Font.BOLD, 11));
        btn.setBackground(ArsenalTheme.WHITE);
        btn.setForeground(ArsenalTheme.RED);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
    }

    private JLabel createMenuTitle(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Arial", Font.BOLD, 13));
        lbl.setForeground(ArsenalTheme.RED);
        lbl.setBorder(BorderFactory.createEmptyBorder(15, 20, 8, 10));
        return lbl;
    }

    private JButton createMenuItem(String text, Runnable action) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.PLAIN, 14));
        btn.setBackground(ArsenalTheme.WHITE);
        btn.setForeground(Color.DARK_GRAY);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 10));
        btn.setMaximumSize(new Dimension(230, 40));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { btn.setBackground(ArsenalTheme.LIGHT_RED); }
            public void mouseExited(java.awt.event.MouseEvent e) { btn.setBackground(ArsenalTheme.WHITE); }
        });
        btn.addActionListener(e -> action.run());
        return btn;
    }

    private void showPanel(JPanel panel, String title) {
        contentPanel.removeAll();
        lblContentTitle.setText(title);
        contentPanel.add(lblContentTitle, BorderLayout.NORTH);
        contentPanel.add(panel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}
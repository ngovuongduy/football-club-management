package gui;

import bus.SanPhamBUS;
import dto.SanPham;
import until.ArsenalTheme;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class SanPhamPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JButton btnThem, btnSua, btnXoa, btnLamMoi, btnTim;
    private JTextField txtTimKiem;
    private SanPhamBUS bus = new SanPhamBUS();

    public SanPhamPanel() {
        setLayout(new BorderLayout());

        // Tìm kiếm
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        topPanel.add(new JLabel("Tìm kiếm:"));
        txtTimKiem = new JTextField(15);
        topPanel.add(txtTimKiem);
        btnTim = new JButton("Tìm");
        ArsenalTheme.styleSecondaryButton(btnTim);
        topPanel.add(btnTim);
        add(topPanel, BorderLayout.NORTH);

        // Bảng
        model = new DefaultTableModel() {
            public Class<?> getColumnClass(int c) { return c == 0 ? ImageIcon.class : Object.class; }
        };
        model.setColumnIdentifiers(new String[]{"", "Mã SP", "Tên SP", "Giá", "SL", "Màu sắc", "Kích cỡ", "Nhà CC"});
        table = new JTable(model);
        table.setRowHeight(55);
        table.getColumnModel().getColumn(0).setPreferredWidth(55);
        ArsenalTheme.styleTable(table);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Nút
        JPanel btnPanel = new JPanel();
        btnThem = new JButton("Thêm"); btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa"); btnLamMoi = new JButton("Làm mới");
        ArsenalTheme.styleSecondaryButton(btnThem); ArsenalTheme.styleSecondaryButton(btnSua);
        ArsenalTheme.styleSecondaryButton(btnXoa); ArsenalTheme.styleSecondaryButton(btnLamMoi);
        btnPanel.add(btnThem); btnPanel.add(btnSua); btnPanel.add(btnXoa); btnPanel.add(btnLamMoi);
        add(btnPanel, BorderLayout.SOUTH);

        // Sự kiện
        btnLamMoi.addActionListener(e -> { txtTimKiem.setText(""); loadData(); });
        btnThem.addActionListener(e -> themSanPham());
        btnSua.addActionListener(e -> suaSanPham());
        btnXoa.addActionListener(e -> xoaSanPham());
        btnTim.addActionListener(e -> timKiem());
        txtTimKiem.addActionListener(e -> timKiem());

        loadData();
    }

    // Icon Arsenal tròn đỏ chữ A
    private ImageIcon getIcon() {
        int s = 45;
        BufferedImage img = new BufferedImage(s, s, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(ArsenalTheme.RED); g.fillOval(0, 0, s, s);
        g.setColor(ArsenalTheme.GOLD); g.setStroke(new BasicStroke(2)); g.drawOval(1, 1, s-2, s-2);
        g.setColor(ArsenalTheme.WHITE); g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("A", 13, 32);
        g.dispose();
        return new ImageIcon(img);
    }

    public void loadData() {
        model.setRowCount(0);
        try {
            for (SanPham sp : bus.getAll()) {
                model.addRow(new Object[]{getIcon(), sp.getMaSP(), sp.getTenSP(),
                    String.format("%,.0f", sp.getGia()), sp.getSl(), sp.getMauSac(), sp.getKichCo(), sp.getTenNCC()});
            }
        } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage()); }
    }

    private void timKiem() {
        String kw = txtTimKiem.getText().trim().toLowerCase();
        model.setRowCount(0);
        try {
            for (SanPham sp : bus.getAll()) {
                if (kw.isEmpty() || sp.getTenSP().toLowerCase().contains(kw) || String.valueOf(sp.getMaSP()).contains(kw)) {
                    model.addRow(new Object[]{getIcon(), sp.getMaSP(), sp.getTenSP(),
                        String.format("%,.0f", sp.getGia()), sp.getSl(), sp.getMauSac(), sp.getKichCo(), sp.getTenNCC()});
                }
            }
        } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage()); }
    }

    private void themSanPham() {
        JTextField m = new JTextField(5), t = new JTextField(10), g = new JTextField(7), sl = new JTextField(3);
        JTextField ms = new JTextField(7), kc = new JTextField(5), ncc = new JTextField(3);
        JPanel p = new JPanel(new GridLayout(0, 2, 5, 5));
        p.add(new JLabel("Mã SP:")); p.add(m); p.add(new JLabel("Tên:")); p.add(t);
        p.add(new JLabel("Giá:")); p.add(g); p.add(new JLabel("SL:")); p.add(sl);
        p.add(new JLabel("Màu:")); p.add(ms); p.add(new JLabel("Cỡ:")); p.add(kc);
        p.add(new JLabel("Mã NCC:")); p.add(ncc);
        if (JOptionPane.showConfirmDialog(this, p, "Thêm", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                SanPham sp = new SanPham();
                sp.setMaSP(Integer.parseInt(m.getText())); sp.setTenSP(t.getText());
                sp.setGia(Double.parseDouble(g.getText())); sp.setSl(Integer.parseInt(sl.getText()));
                sp.setMauSac(ms.getText()); sp.setKichCo(kc.getText()); sp.setMaNCC(Integer.parseInt(ncc.getText()));
                sp.setLoaiSP(1);
                bus.themSanPham(sp); loadData();
                JOptionPane.showMessageDialog(this, "Thêm thành công!");
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage()); }
        }
    }

    private void suaSanPham() {
        int r = table.getSelectedRow();
        if (r == -1) { JOptionPane.showMessageDialog(this, "Chọn sản phẩm!"); return; }
        String gm = JOptionPane.showInputDialog(this, "Nhập giá mới:", model.getValueAt(r, 3));
        if (gm != null) {
            try {
                SanPham sp = new SanPham();
                sp.setMaSP((int) model.getValueAt(r, 1)); sp.setTenSP((String) model.getValueAt(r, 2));
                sp.setGia(Double.parseDouble(gm.replaceAll("[^0-9]", "")));
                sp.setSl((int) model.getValueAt(r, 4));
                sp.setMauSac((String) model.getValueAt(r, 5)); sp.setKichCo((String) model.getValueAt(r, 6));
                sp.setMaNCC(1);
                bus.suaSanPham(sp); loadData();
                JOptionPane.showMessageDialog(this, "Sửa thành công!");
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage()); }
        }
    }

    private void xoaSanPham() {
        int r = table.getSelectedRow();
        if (r == -1) { JOptionPane.showMessageDialog(this, "Chọn sản phẩm!"); return; }
        if (JOptionPane.showConfirmDialog(this, "Xóa?", "OK", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            try { bus.xoaSanPham((int) model.getValueAt(r, 1)); loadData(); }
            catch (Exception ex) { JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage()); }
        }
    }
}
package gui;

import bus.PhieuNhapBUS;
import dao.CtpnDAO;
import dto.PhieuNhap;
import dto.Ctpn;
import until.Session;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class PhieuNhapPanel extends JPanel {
    private JTable tablePN, tableCT;
    private DefaultTableModel modelPN, modelCT;
    private JButton btnTaoPN, btnThemSP, btnLamMoi;
    private PhieuNhapBUS bus = new PhieuNhapBUS();
    private CtpnDAO ctpnDAO = new CtpnDAO();
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private JLabel lblTongTien;

    public PhieuNhapPanel() {
        setLayout(new BorderLayout());

        // Panel trên: Danh sách phiếu nhập
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createTitledBorder("Danh sách phiếu nhập"));
        
        modelPN = new DefaultTableModel();
        modelPN.setColumnIdentifiers(new String[]{"Mã PN", "Ngày nhập", "Nhà CC", "Nhân viên", "Tổng tiền"});
        tablePN = new JTable(modelPN);
        tablePN.setRowHeight(25);
        topPanel.add(new JScrollPane(tablePN), BorderLayout.CENTER);

        JPanel btnPN = new JPanel();
        btnTaoPN = new JButton("Tạo phiếu nhập");
        btnLamMoi = new JButton("Làm mới");
        btnPN.add(btnTaoPN);
        btnPN.add(btnLamMoi);
        topPanel.add(btnPN, BorderLayout.SOUTH);

        // Panel dưới: Chi tiết phiếu nhập
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createTitledBorder("Chi tiết phiếu nhập"));

        modelCT = new DefaultTableModel();
        modelCT.setColumnIdentifiers(new String[]{"Mã SP", "Tên SP", "SL", "Giá nhập", "Thành tiền"});
        tableCT = new JTable(modelCT);
        tableCT.setRowHeight(25);
        bottomPanel.add(new JScrollPane(tableCT), BorderLayout.CENTER);

        JPanel btnCT = new JPanel(new BorderLayout());
        btnThemSP = new JButton("Thêm sản phẩm");
        lblTongTien = new JLabel("Tổng tiền: 0 VNĐ");
        lblTongTien.setFont(new Font("Arial", Font.BOLD, 14));
        lblTongTien.setForeground(Color.BLUE);
        btnCT.add(btnThemSP, BorderLayout.WEST);
        btnCT.add(lblTongTien, BorderLayout.EAST);
        bottomPanel.add(btnCT, BorderLayout.SOUTH);

        // Chia đôi màn hình
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topPanel, bottomPanel);
        splitPane.setDividerLocation(250);
        add(splitPane, BorderLayout.CENTER);

        // Sự kiện
        btnLamMoi.addActionListener(e -> loadData());
        btnTaoPN.addActionListener(e -> taoPhieuNhap());
        btnThemSP.addActionListener(e -> themSP());
        tablePN.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) loadChiTiet();
        });

        loadData();
    }

    private void loadData() {
        modelPN.setRowCount(0);
        try {
            List<PhieuNhap> list = bus.getAll();
            for (PhieuNhap pn : list) {
                modelPN.addRow(new Object[]{
                    pn.getMaPN(), pn.getNgayNhap() != null ? sdf.format(pn.getNgayNhap()) : "",
                    pn.getTenNCC(), pn.getTenNV(), String.format("%,.0f", pn.getTongTien())
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
        }
    }

    private void loadChiTiet() {
        modelCT.setRowCount(0);
        int row = tablePN.getSelectedRow();
        if (row == -1) return;

        int maPN = (int) modelPN.getValueAt(row, 0);
        double tong = 0;
        try {
            List<Ctpn> list = ctpnDAO.getByMaPN(maPN);
            for (Ctpn ct : list) {
                modelCT.addRow(new Object[]{
                    ct.getMaSP(), ct.getTenSP(), ct.getSl(),
                    String.format("%,.0f", ct.getGia()),
                    String.format("%,.0f", ct.getThanhTien())
                });
                tong += ct.getThanhTien();
            }
            lblTongTien.setText("Tổng tiền: " + String.format("%,.0f", tong) + " VNĐ");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
        }
    }

    private void taoPhieuNhap() {
        JTextField txtMaNCC = new JTextField(10);
        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.add(new JLabel("Mã NCC:"));
        panel.add(txtMaNCC);

        int result = JOptionPane.showConfirmDialog(this, panel, "Tạo phiếu nhập", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int maNCC = Integer.parseInt(txtMaNCC.getText());
                int maPN = bus.taoPhieuNhap(Session.maNV, maNCC);
                JOptionPane.showMessageDialog(this, "Tạo phiếu nhập thành công! Mã PN: " + maPN);
                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
            }
        }
    }

    private void themSP() {
        int row = tablePN.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Chọn phiếu nhập!"); return; }
        int maPN = (int) modelPN.getValueAt(row, 0);

        JTextField txtMaSP = new JTextField(10);
        JTextField txtSL = new JTextField(10);
        JTextField txtGia = new JTextField(10);

        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.add(new JLabel("Mã SP:"));
        panel.add(txtMaSP);
        panel.add(new JLabel("Số lượng:"));
        panel.add(txtSL);
        panel.add(new JLabel("Giá nhập:"));
        panel.add(txtGia);

        int result = JOptionPane.showConfirmDialog(this, panel, "Thêm sản phẩm", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int maSP = Integer.parseInt(txtMaSP.getText());
                int sl = Integer.parseInt(txtSL.getText());
                double gia = Double.parseDouble(txtGia.getText());
                bus.themSPVaoPhieu(maPN, maSP, sl, gia);
                loadChiTiet();
                loadData();
                JOptionPane.showMessageDialog(this, "Thêm thành công! Tồn kho đã tăng!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
            }
        }
    }
}
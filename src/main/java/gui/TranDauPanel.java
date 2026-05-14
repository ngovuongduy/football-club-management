package gui;

import bus.TranDauBUS;
import bus.VeTranDauBUS;
import dto.TranDau;
import dto.VeTranDau;
import until.ArsenalTheme;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class TranDauPanel extends JPanel {
    private JTable tableTD, tableVe;
    private DefaultTableModel modelTD, modelVe;
    private JButton btnThemTD, btnSuaTD, btnXoaTD, btnThemVe, btnXoaVe, btnLamMoi;
    private TranDauBUS busTD = new TranDauBUS();
    private VeTranDauBUS busVe = new VeTranDauBUS();

    public TranDauPanel() {
        setLayout(new BorderLayout());
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setDividerLocation(280);

        // Bảng trận đấu
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createTitledBorder("Danh sách trận đấu"));
        modelTD = new DefaultTableModel();
        modelTD.setColumnIdentifiers(new String[]{"Mã TD", "Vòng đấu", "Đối thủ", "Ngày giờ", "Địa điểm", "Trạng thái"});
        tableTD = new JTable(modelTD);
        tableTD.setRowHeight(25);
        ArsenalTheme.styleTable(tableTD);
        topPanel.add(new JScrollPane(tableTD), BorderLayout.CENTER);
        JPanel btnPanelTD = new JPanel();
        btnThemTD = new JButton("Thêm trận"); btnSuaTD = new JButton("Sửa trận");
        btnXoaTD = new JButton("Xóa trận"); btnLamMoi = new JButton("Làm mới");
        ArsenalTheme.styleSecondaryButton(btnThemTD); ArsenalTheme.styleSecondaryButton(btnSuaTD);
        ArsenalTheme.styleSecondaryButton(btnXoaTD); ArsenalTheme.styleSecondaryButton(btnLamMoi);
        btnPanelTD.add(btnThemTD); btnPanelTD.add(btnSuaTD); btnPanelTD.add(btnXoaTD); btnPanelTD.add(btnLamMoi);
        topPanel.add(btnPanelTD, BorderLayout.SOUTH);
        splitPane.setTopComponent(topPanel);

        // Bảng vé
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createTitledBorder("Vé của trận đấu đã chọn"));
        modelVe = new DefaultTableModel();
        modelVe.setColumnIdentifiers(new String[]{"Mã vé", "Loại vé", "Giá", "SL Tổng", "SL Còn lại"});
        tableVe = new JTable(modelVe);
        tableVe.setRowHeight(25);
        ArsenalTheme.styleTable(tableVe);
        bottomPanel.add(new JScrollPane(tableVe), BorderLayout.CENTER);
        JPanel btnPanelVe = new JPanel();
        btnThemVe = new JButton("Thêm vé"); btnXoaVe = new JButton("Xóa vé");
        ArsenalTheme.styleSecondaryButton(btnThemVe); ArsenalTheme.styleSecondaryButton(btnXoaVe);
        btnPanelVe.add(btnThemVe); btnPanelVe.add(btnXoaVe);
        bottomPanel.add(btnPanelVe, BorderLayout.SOUTH);
        splitPane.setBottomComponent(bottomPanel);

        add(splitPane, BorderLayout.CENTER);

        // Sự kiện
        tableTD.getSelectionModel().addListSelectionListener(e -> { if (!e.getValueIsAdjusting()) loadVeByTranDau(); });
        btnLamMoi.addActionListener(e -> loadAll());
        btnThemTD.addActionListener(e -> themTranDau());
        btnSuaTD.addActionListener(e -> suaTranDau());
        btnXoaTD.addActionListener(e -> xoaTranDau());
        btnThemVe.addActionListener(e -> themVe());
        btnXoaVe.addActionListener(e -> xoaVe());

        loadAll();
    }

    private void loadAll() { loadTranDau(); loadVeByTranDau(); }

    private void loadTranDau() {
        modelTD.setRowCount(0);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            for (TranDau td : busTD.getAll()) {
                modelTD.addRow(new Object[]{td.getMaTD(), td.getTenVongDau(), td.getDoiThu(),
                        td.getNgayGio() != null ? sdf.format(td.getNgayGio()) : "", td.getDiaDiem(), td.getTrangThaiText()});
            }
        } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Lỗi tải trận đấu: " + ex.getMessage()); }
    }

    private void loadVeByTranDau() {
        modelVe.setRowCount(0);
        int row = tableTD.getSelectedRow();
        if (row == -1) return;
        int maTD = (int) modelTD.getValueAt(row, 0);
        try {
            for (VeTranDau ve : busVe.getByMaTD(maTD)) {
                modelVe.addRow(new Object[]{ve.getMaVe(), ve.getLoaiVe(), ve.getGia(), ve.getSlTong(), ve.getSlConLai()});
            }
        } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Lỗi tải vé: " + ex.getMessage()); }
    }

    private void themTranDau() {
        JTextField m = new JTextField(5), v = new JTextField(10), d = new JTextField(10);
        JTextField n = new JTextField("2026-06-01 19:00", 10), dd = new JTextField(10);
        JPanel p = new JPanel(new GridLayout(0,2,5,5));
        p.add(new JLabel("Mã TD:")); p.add(m); p.add(new JLabel("Vòng đấu:")); p.add(v);
        p.add(new JLabel("Đối thủ:")); p.add(d); p.add(new JLabel("Ngày giờ:")); p.add(n);
        p.add(new JLabel("Địa điểm:")); p.add(dd);
        if(JOptionPane.showConfirmDialog(this,p,"Thêm trận",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION){
            try {
                TranDau td = new TranDau();
                td.setMaTD(Integer.parseInt(m.getText().trim())); td.setTenVongDau(v.getText().trim());
                td.setDoiThu(d.getText().trim()); td.setNgayGio(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(n.getText().trim()));
                td.setDiaDiem(dd.getText().trim()); td.setTrangThai(1);
                busTD.themTranDau(td); loadTranDau();
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage()); }
        }
    }

    private void suaTranDau() {
        int row = tableTD.getSelectedRow();
        if(row==-1){JOptionPane.showMessageDialog(this,"Chọn trận!");return;}
        JTextField v = new JTextField((String)modelTD.getValueAt(row,1),10);
        JTextField d = new JTextField((String)modelTD.getValueAt(row,2),10);
        JTextField n = new JTextField((String)modelTD.getValueAt(row,3),10);
        JTextField dd = new JTextField((String)modelTD.getValueAt(row,4),10);
        JPanel p = new JPanel(new GridLayout(0,2,5,5));
        p.add(new JLabel("Vòng:")); p.add(v); p.add(new JLabel("Đối thủ:")); p.add(d);
        p.add(new JLabel("Ngày:")); p.add(n); p.add(new JLabel("Địa điểm:")); p.add(dd);
        if(JOptionPane.showConfirmDialog(this,p,"Sửa",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION){
            try {
                TranDau td = new TranDau(); td.setMaTD((int)modelTD.getValueAt(row,0));
                td.setTenVongDau(v.getText().trim()); td.setDoiThu(d.getText().trim());
                td.setNgayGio(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(n.getText().trim()));
                td.setDiaDiem(dd.getText().trim()); td.setTrangThai(1);
                busTD.suaTranDau(td); loadTranDau();
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage()); }
        }
    }

    private void xoaTranDau() {
        int row = tableTD.getSelectedRow();
        if(row==-1){JOptionPane.showMessageDialog(this,"Chọn trận!");return;}
        if(JOptionPane.showConfirmDialog(this,"Xóa?","OK",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
            try { busTD.xoaTranDau((int)modelTD.getValueAt(row,0)); loadTranDau(); modelVe.setRowCount(0); }
            catch (Exception ex) { JOptionPane.showMessageDialog(this,"Lỗi: "+ex.getMessage()); }
        }
    }

    private void themVe() {
        int row = tableTD.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Bạn phải chọn một trận đấu trước!");
            return;
        }
        int maTD = (int) modelTD.getValueAt(row, 0);

        JTextField txtMaVe = new JTextField(5);
        JComboBox<String> cboLoai = new JComboBox<>(new String[]{"VIP", "Thường"});
        JTextField txtGia = new JTextField(7);
        JTextField txtSL = new JTextField(5);

        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.add(new JLabel("Mã vé:"));
        panel.add(txtMaVe);
        panel.add(new JLabel("Loại vé:"));
        panel.add(cboLoai);
        panel.add(new JLabel("Giá:"));
        panel.add(txtGia);
        panel.add(new JLabel("Số lượng:"));
        panel.add(txtSL);

        int result = JOptionPane.showConfirmDialog(this, panel, "Thêm vé mới", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int maVe = Integer.parseInt(txtMaVe.getText().trim());
                double gia = Double.parseDouble(txtGia.getText().trim());
                int sl = Integer.parseInt(txtSL.getText().trim());
                if (sl <= 0) { JOptionPane.showMessageDialog(this, "Số lượng phải > 0"); return; }

                VeTranDau ve = new VeTranDau();
                ve.setMaVe(maVe);
                ve.setMaTD(maTD);
                ve.setLoaiVe((String) cboLoai.getSelectedItem());
                ve.setGia(gia);
                ve.setSlTong(sl);
                ve.setSlConLai(sl);

                busVe.themVe(ve);      // Gọi BUS thêm vé
                loadVeByTranDau();     // Cập nhật bảng vé
                JOptionPane.showMessageDialog(this, "Thêm vé thành công!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số hợp lệ!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi thêm vé: " + ex.getMessage());
            }
        }
    }

    private void xoaVe() {
        int row = tableVe.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Chọn vé cần xóa!"); return; }
        int maVe = (int) modelVe.getValueAt(row, 0);
        if (JOptionPane.showConfirmDialog(this, "Xóa vé này?", "Xác nhận", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            try {
                busVe.xoaVe(maVe);
                loadVeByTranDau();
                JOptionPane.showMessageDialog(this, "Xóa vé thành công!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi xóa vé: " + ex.getMessage());
            }
        }
    }
}
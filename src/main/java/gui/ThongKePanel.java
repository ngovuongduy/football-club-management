package gui;

import until.DatabaseConnection;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ThongKePanel extends JPanel {

    public ThongKePanel() {
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        // Tab 1: Doanh thu theo ngày (có biểu đồ cột)
        tabbedPane.addTab("Doanh thu", createDoanhThuPanel());

        // Tab 2: Top sản phẩm (có biểu đồ tròn)
        tabbedPane.addTab("Top sản phẩm", createTopSanPhamPanel());

        // Tab 3: Top trận đấu (có biểu đồ ngang)
        tabbedPane.addTab("Top trận đấu", createTopTranDauPanel());

        add(tabbedPane, BorderLayout.CENTER);
    }

    // ==================== DOANH THU THEO NGÀY + BIỂU ĐỒ CỘT ====================
    private JPanel createDoanhThuPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Ngày", "Số HĐ", "Doanh thu"});
        JTable table = new JTable(model);

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        String sql = "SELECT TOP 7 CAST(NgayHD AS DATE) AS Ngay, COUNT(DISTINCT hd.MaHD) AS SoHD, " +
                     "ISNULL(SUM(ctsp.SoLuong * ctsp.DonGia), 0) + ISNULL(SUM(ctv.SL * ctv.Gia), 0) AS Tong " +
                     "FROM HOADON hd " +
                     "LEFT JOIN CTHD_SANPHAM ctsp ON hd.MaHD = ctsp.MaHD " +
                     "LEFT JOIN CTHD_VE ctv ON hd.MaHD = ctv.MaHD " +
                     "GROUP BY CAST(NgayHD AS DATE) " +
                     "ORDER BY Ngay DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String ngay = rs.getDate("Ngay").toString();
                double tong = rs.getDouble("Tong");
                model.addRow(new Object[]{ngay, rs.getInt("SoHD"), String.format("%,.0f", tong)});
                dataset.addValue(tong, "Doanh thu", ngay);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
        }

        JFreeChart chart = ChartFactory.createBarChart(
            "Doanh thu 7 ngày gần nhất", "Ngày", "VNĐ",
            dataset, PlotOrientation.VERTICAL, true, true, false
        );
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 300));

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
            new JScrollPane(table), chartPanel);
        splitPane.setDividerLocation(200);
        panel.add(splitPane, BorderLayout.CENTER);

        return panel;
    }

    // ==================== TOP SẢN PHẨM + BIỂU ĐỒ TRÒN ====================
    private JPanel createTopSanPhamPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Mã SP", "Tên SP", "SL bán", "Doanh thu"});
        JTable table = new JTable(model);

        DefaultPieDataset dataset = new DefaultPieDataset();

        String sql = "SELECT TOP 5 sp.MaSP, sp.TenSP, SUM(ct.SoLuong) AS TongSL, " +
                     "SUM(ct.SoLuong * ct.DonGia) AS TongTien " +
                     "FROM CTHD_SANPHAM ct JOIN SANPHAM sp ON ct.MaSP = sp.MaSP " +
                     "GROUP BY sp.MaSP, sp.TenSP " +
                     "ORDER BY TongSL DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String tenSP = rs.getString("TenSP");
                int sl = rs.getInt("TongSL");
                double tien = rs.getDouble("TongTien");
                model.addRow(new Object[]{rs.getInt("MaSP"), tenSP, sl, String.format("%,.0f", tien)});
                dataset.setValue(tenSP, sl);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
        }

        JFreeChart chart = ChartFactory.createPieChart(
            "Top 5 sản phẩm bán chạy", dataset, true, true, false
        );
        ChartPanel chartPanel = new ChartPanel(chart);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
            new JScrollPane(table), chartPanel);
        splitPane.setDividerLocation(350);
        panel.add(splitPane, BorderLayout.CENTER);

        return panel;
    }

    // ==================== TOP TRẬN ĐẤU + BIỂU ĐỒ NGANG ====================
    private JPanel createTopTranDauPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Mã TD", "Vòng đấu", "Đối thủ", "Số vé bán", "Doanh thu"});
        JTable table = new JTable(model);

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        String sql = "SELECT TOP 5 t.MaTD, t.TenVongDau, t.DoiThu, " +
                     "SUM(ct.SL) AS TongVe, SUM(ct.SL * ct.Gia) AS TongTien " +
                     "FROM TRANDAU t " +
                     "JOIN VETRANDAU v ON t.MaTD = v.MaTD " +
                     "JOIN CTHD_VE ct ON v.MaVe = ct.MaVe " +
                     "GROUP BY t.MaTD, t.TenVongDau, t.DoiThu " +
                     "ORDER BY TongVe DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String ten = rs.getString("DoiThu");
                int ve = rs.getInt("TongVe");
                double tien = rs.getDouble("TongTien");
                model.addRow(new Object[]{
                    rs.getInt("MaTD"), rs.getString("TenVongDau"),
                    ten, ve, String.format("%,.0f", tien)
                });
                dataset.addValue(ve, "Số vé", ten);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
        }

        // Biểu đồ ngang
        JFreeChart chart = ChartFactory.createBarChart(
            "Top 5 trận đấu bán nhiều vé nhất", "Đối thủ", "Số vé",
            dataset, PlotOrientation.HORIZONTAL, true, true, false
        );
        ChartPanel chartPanel = new ChartPanel(chart);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
            new JScrollPane(table), chartPanel);
        splitPane.setDividerLocation(200);
        panel.add(splitPane, BorderLayout.CENTER);

        return panel;
    }
}
package dao;

import dto.CthdVe;
import until.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CthdVeDAO {

    // Lấy chi tiết vé của hóa đơn
    public List<CthdVe> getByMaHD(int maHD) throws SQLException {
        List<CthdVe> list = new ArrayList<>();
        String sql = "SELECT cv.MaHD, cv.MaVe, cv.SL, cv.Gia, v.LoaiVe, t.TenVongDau, t.DoiThu " +
                     "FROM CTHD_VE cv " +
                     "JOIN VETRANDAU v ON cv.MaVe = v.MaVe " +
                     "JOIN TRANDAU t ON v.MaTD = t.MaTD " +
                     "WHERE cv.MaHD = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maHD);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CthdVe cv = new CthdVe();
                    cv.setMaHD(rs.getInt("MaHD"));
                    cv.setMaVe(rs.getInt("MaVe"));
                    cv.setSl(rs.getInt("SL"));
                    cv.setGia(rs.getDouble("Gia"));
                    cv.setLoaiVe(rs.getString("LoaiVe"));
                    cv.setTenVongDau(rs.getString("TenVongDau"));
                    cv.setDoiThu(rs.getString("DoiThu"));
                    list.add(cv);
                }
            }
        }
        return list;
    }

    // Thêm vé vào hóa đơn
    public void insert(CthdVe cv) throws SQLException {
        String sql = "INSERT INTO CTHD_VE (MaHD, MaVe, SL, Gia) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cv.getMaHD());
            ps.setInt(2, cv.getMaVe());
            ps.setInt(3, cv.getSl());
            ps.setDouble(4, cv.getGia());
            ps.executeUpdate();
        }
    }

    // Xóa vé khỏi hóa đơn
    public void delete(int maHD, int maVe) throws SQLException {
        String sql = "DELETE FROM CTHD_VE WHERE MaHD = ? AND MaVe = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maHD);
            ps.setInt(2, maVe);
            ps.executeUpdate();
        }
    }

    // Trừ số lượng vé còn lại
    public void giamSLConLai(int maVe, int sl) throws SQLException {
        String sql = "UPDATE VETRANDAU SET SLConLai = SLConLai - ? WHERE MaVe = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, sl);
            ps.setInt(2, maVe);
            ps.executeUpdate();
        }
    }

    // Lấy SL còn lại của vé
    public int getSLConLai(int maVe) throws SQLException {
        String sql = "SELECT SLConLai FROM VETRANDAU WHERE MaVe = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maVe);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("SLConLai");
            }
        }
        return 0;
    }

    // Lấy giá vé
    public double getGiaVe(int maVe) throws SQLException {
        String sql = "SELECT Gia FROM VETRANDAU WHERE MaVe = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maVe);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getDouble("Gia");
            }
        }
        throw new SQLException("Không tìm thấy vé!");
    }
public void updateThanhTienHD(int maHD) throws SQLException {
    String sql = "UPDATE HOADON SET ThanhTien = " +
                 "(SELECT ISNULL(SUM(SoLuong * DonGia), 0) FROM CTHD_SANPHAM WHERE MaHD = ?) + " +
                 "(SELECT ISNULL(SUM(SL * Gia), 0) FROM CTHD_VE WHERE MaHD = ?) " +
                 "WHERE MaHD = ?";
    
    System.out.println("SQL: " + sql + " | MaHD=" + maHD);
    
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, maHD);
        ps.setInt(2, maHD);
        ps.setInt(3, maHD);
        int rows = ps.executeUpdate();
        System.out.println("Đã cập nhật " + rows + " dòng cho HD: " + maHD);
    }
}
    // Cập nhật tổng tiền hóa đơn (gộp cả SP và Vé)
  
}
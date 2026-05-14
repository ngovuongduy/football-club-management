package dao;

import dto.KhachHang;
import until.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KhachHangDAO {

    public List<KhachHang> getAll() throws SQLException {
        List<KhachHang> list = new ArrayList<>();
        String sql = "SELECT MaKH, Ho, Ten, NgaySinh, GioiTinh, SoDT, LoaiKH, DiemTichLuy FROM KHACHHANG ORDER BY MaKH";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setMaKH(rs.getInt("MaKH"));
                kh.setHo(rs.getString("Ho"));
                kh.setTen(rs.getString("Ten"));
                kh.setNgaySinh(rs.getDate("NgaySinh"));
                kh.setGioiTinh(rs.getString("GioiTinh"));
                kh.setSoDT(rs.getString("SoDT"));
                kh.setLoaiKH(rs.getString("LoaiKH"));
                kh.setDiemTichLuy(rs.getDouble("DiemTichLuy"));
                list.add(kh);
            }
        }
        return list;
    }

    public void insert(KhachHang kh) throws SQLException {
        String sql = "INSERT INTO KHACHHANG (MaKH, Ho, Ten, NgaySinh, GioiTinh, SoDT, LoaiKH, DiemTichLuy) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, kh.getMaKH());
            ps.setString(2, kh.getHo());
            ps.setString(3, kh.getTen());
            ps.setDate(4, kh.getNgaySinh() != null ? new java.sql.Date(kh.getNgaySinh().getTime()) : null);
            ps.setString(5, kh.getGioiTinh());
            ps.setString(6, kh.getSoDT());
            ps.setString(7, kh.getLoaiKH());
            ps.setDouble(8, kh.getDiemTichLuy());
            ps.executeUpdate();
        }
    }

    public void update(KhachHang kh) throws SQLException {
        String sql = "UPDATE KHACHHANG SET Ho=?, Ten=?, NgaySinh=?, GioiTinh=?, SoDT=?, LoaiKH=?, DiemTichLuy=? " +
                     "WHERE MaKH=?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, kh.getHo());
            ps.setString(2, kh.getTen());
            ps.setDate(3, kh.getNgaySinh() != null ? new java.sql.Date(kh.getNgaySinh().getTime()) : null);
            ps.setString(4, kh.getGioiTinh());
            ps.setString(5, kh.getSoDT());
            ps.setString(6, kh.getLoaiKH());
            ps.setDouble(7, kh.getDiemTichLuy());
            ps.setInt(8, kh.getMaKH());
            ps.executeUpdate();
        }
    }

    public void delete(int maKH) throws SQLException {
        String sql = "DELETE FROM KHACHHANG WHERE MaKH = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maKH);
            ps.executeUpdate();
        }
    }
}
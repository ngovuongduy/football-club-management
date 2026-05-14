package dao;

import dto.VeTranDau;
import until.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VeTranDauDAO {

    // Lấy vé theo mã trận đấu
    public List<VeTranDau> getByMaTD(int maTD) throws SQLException {
        List<VeTranDau> list = new ArrayList<>();
        String sql = "SELECT v.MaVe, v.MaTD, v.LoaiVe, v.Gia, v.SLTong, v.SLConLai, " +
                     "t.TenVongDau, t.DoiThu " +
                     "FROM VETRANDAU v JOIN TRANDAU t ON v.MaTD = t.MaTD " +
                     "WHERE v.MaTD = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maTD);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    VeTranDau ve = new VeTranDau();
                    ve.setMaVe(rs.getInt("MaVe"));
                    ve.setMaTD(rs.getInt("MaTD"));
                    ve.setLoaiVe(rs.getString("LoaiVe"));
                    ve.setGia(rs.getDouble("Gia"));
                    ve.setSlTong(rs.getInt("SLTong"));
                    ve.setSlConLai(rs.getInt("SLConLai"));
                    ve.setTenVongDau(rs.getString("TenVongDau"));
                    ve.setDoiThu(rs.getString("DoiThu"));
                    list.add(ve);
                }
            }
        }
        return list;
    }

    // Lấy tất cả vé
    public List<VeTranDau> getAll() throws SQLException {
        List<VeTranDau> list = new ArrayList<>();
        String sql = "SELECT v.MaVe, v.MaTD, v.LoaiVe, v.Gia, v.SLTong, v.SLConLai, " +
                     "t.TenVongDau, t.DoiThu " +
                     "FROM VETRANDAU v JOIN TRANDAU t ON v.MaTD = t.MaTD " +
                     "ORDER BY t.NgayGio DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                VeTranDau ve = new VeTranDau();
                ve.setMaVe(rs.getInt("MaVe"));
                ve.setMaTD(rs.getInt("MaTD"));
                ve.setLoaiVe(rs.getString("LoaiVe"));
                ve.setGia(rs.getDouble("Gia"));
                ve.setSlTong(rs.getInt("SLTong"));
                ve.setSlConLai(rs.getInt("SLConLai"));
                ve.setTenVongDau(rs.getString("TenVongDau"));
                ve.setDoiThu(rs.getString("DoiThu"));
                list.add(ve);
            }
        }
        return list;
    }

    public void insert(VeTranDau ve) throws SQLException {
        String sql = "INSERT INTO VETRANDAU (MaVe, MaTD, LoaiVe, Gia, SLTong, SLConLai) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ve.getMaVe());
            ps.setInt(2, ve.getMaTD());
            ps.setString(3, ve.getLoaiVe());
            ps.setDouble(4, ve.getGia());
            ps.setInt(5, ve.getSlTong());
            ps.setInt(6, ve.getSlConLai());
            ps.executeUpdate();
        }
    }

    public void update(VeTranDau ve) throws SQLException {
        String sql = "UPDATE VETRANDAU SET LoaiVe=?, Gia=?, SLTong=?, SLConLai=? WHERE MaVe=?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ve.getLoaiVe());
            ps.setDouble(2, ve.getGia());
            ps.setInt(3, ve.getSlTong());
            ps.setInt(4, ve.getSlConLai());
            ps.setInt(5, ve.getMaVe());
            ps.executeUpdate();
        }
    }

    public void delete(int maVe) throws SQLException {
        String sql = "DELETE FROM VETRANDAU WHERE MaVe = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maVe);
            ps.executeUpdate();
        }
    }
}
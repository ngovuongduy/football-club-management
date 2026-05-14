package dao;

import dto.TranDau;
import until.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TranDauDAO {

    public List<TranDau> getAll() throws SQLException {
        List<TranDau> list = new ArrayList<>();
        String sql = "SELECT MaTD, TenVongDau, DoiThu, NgayGio, DiaDiem, TrangThai FROM TRANDAU ORDER BY NgayGio DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                TranDau td = new TranDau();
                td.setMaTD(rs.getInt("MaTD"));
                td.setTenVongDau(rs.getString("TenVongDau"));
                td.setDoiThu(rs.getString("DoiThu"));
                td.setNgayGio(rs.getTimestamp("NgayGio"));
                td.setDiaDiem(rs.getString("DiaDiem"));
                td.setTrangThai(rs.getInt("TrangThai"));
                list.add(td);
            }
        }
        return list;
    }

    public void insert(TranDau td) throws SQLException {
        String sql = "INSERT INTO TRANDAU (MaTD, TenVongDau, DoiThu, NgayGio, DiaDiem, TrangThai) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, td.getMaTD());
            ps.setString(2, td.getTenVongDau());
            ps.setString(3, td.getDoiThu());
            ps.setTimestamp(4, new Timestamp(td.getNgayGio().getTime()));
            ps.setString(5, td.getDiaDiem());
            ps.setInt(6, td.getTrangThai());
            ps.executeUpdate();
        }
    }

    public void update(TranDau td) throws SQLException {
        String sql = "UPDATE TRANDAU SET TenVongDau=?, DoiThu=?, NgayGio=?, DiaDiem=?, TrangThai=? WHERE MaTD=?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, td.getTenVongDau());
            ps.setString(2, td.getDoiThu());
            ps.setTimestamp(3, new Timestamp(td.getNgayGio().getTime()));
            ps.setString(4, td.getDiaDiem());
            ps.setInt(5, td.getTrangThai());
            ps.setInt(6, td.getMaTD());
            ps.executeUpdate();
        }
    }

    public void delete(int maTD) throws SQLException {
        String sql = "DELETE FROM TRANDAU WHERE MaTD = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maTD);
            ps.executeUpdate();
        }
    }
}

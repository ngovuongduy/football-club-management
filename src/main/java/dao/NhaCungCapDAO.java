package dao;

import dto.NhaCungCap;
import until.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NhaCungCapDAO {

    public List<NhaCungCap> getAll() throws SQLException {
        List<NhaCungCap> list = new ArrayList<>();
        String sql = "SELECT MaNCC, TenNCC, DiaChi, SoDT FROM NHACUNGCAP ORDER BY MaNCC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                NhaCungCap ncc = new NhaCungCap();
                ncc.setMaNCC(rs.getInt("MaNCC"));
                ncc.setTenNCC(rs.getString("TenNCC"));
                ncc.setDiaChi(rs.getString("DiaChi"));
                ncc.setSoDT(rs.getString("SoDT"));
                list.add(ncc);
            }
        }
        return list;
    }

    public void insert(NhaCungCap ncc) throws SQLException {
        String sql = "INSERT INTO NHACUNGCAP (MaNCC, TenNCC, DiaChi, SoDT) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ncc.getMaNCC());
            ps.setString(2, ncc.getTenNCC());
            ps.setString(3, ncc.getDiaChi());
            ps.setString(4, ncc.getSoDT());
            ps.executeUpdate();
        }
    }

    public void update(NhaCungCap ncc) throws SQLException {
        String sql = "UPDATE NHACUNGCAP SET TenNCC=?, DiaChi=?, SoDT=? WHERE MaNCC=?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ncc.getTenNCC());
            ps.setString(2, ncc.getDiaChi());
            ps.setString(3, ncc.getSoDT());
            ps.setInt(4, ncc.getMaNCC());
            ps.executeUpdate();
        }
    }

    public void delete(int maNCC) throws SQLException {
        String sql = "DELETE FROM NHACUNGCAP WHERE MaNCC = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maNCC);
            ps.executeUpdate();
        }
    }
}
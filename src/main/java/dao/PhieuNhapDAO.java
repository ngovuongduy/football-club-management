package dao;

import dto.PhieuNhap;
import until.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhieuNhapDAO {

    public List<PhieuNhap> getAll() throws SQLException {
        List<PhieuNhap> list = new ArrayList<>();
        String sql = "SELECT pn.*, nv.Ho + ' ' + nv.Ten AS TenNV, ncc.TenNCC " +
                     "FROM PHIEUNHAP pn " +
                     "JOIN NHANVIEN nv ON pn.MaNV = nv.MaNV " +
                     "JOIN NHACUNGCAP ncc ON pn.MaNCC = ncc.MaNCC " +
                     "ORDER BY pn.NgayNhap DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                PhieuNhap pn = new PhieuNhap();
                pn.setMaPN(rs.getInt("MaPN"));
                pn.setNgayNhap(rs.getDate("NgayNhap"));
                pn.setTongTien(rs.getDouble("TongTien"));
                pn.setMaNV(rs.getInt("MaNV"));
                pn.setMaNCC(rs.getInt("MaNCC"));
                pn.setTenNV(rs.getString("TenNV"));
                pn.setTenNCC(rs.getString("TenNCC"));
                list.add(pn);
            }
        }
        return list;
    }

    public int getNextMaPN() throws SQLException {
        String sql = "SELECT ISNULL(MAX(MaPN), 0) + 1 FROM PHIEUNHAP";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1);
        }
        return 1;
    }

    public void insert(PhieuNhap pn) throws SQLException {
        String sql = "INSERT INTO PHIEUNHAP (MaPN, NgayNhap, MaNV, MaNCC) VALUES (?, GETDATE(), ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, pn.getMaPN());
            ps.setInt(2, pn.getMaNV());
            ps.setInt(3, pn.getMaNCC());
            ps.executeUpdate();
        }
    }
}
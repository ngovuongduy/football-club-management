package dao;

import dto.SanPham;
import until.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SanPhamDAO {

    public List<SanPham> getAll() throws SQLException {
        List<SanPham> list = new ArrayList<>();
        String sql = "SELECT sp.MaSP, sp.TenSP, sp.LoaiSP, sp.Gia, sp.SL, sp.MaNCC, sp.KichCo, sp.MauSac, sp.HinhAnh, ncc.TenNCC " +
                     "FROM SANPHAM sp JOIN NHACUNGCAP ncc ON sp.MaNCC = ncc.MaNCC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setMaSP(rs.getInt("MaSP"));
                sp.setTenSP(rs.getString("TenSP"));
                sp.setLoaiSP(rs.getInt("LoaiSP"));
                sp.setGia(rs.getDouble("Gia"));
                sp.setSl(rs.getInt("SL"));
                sp.setMaNCC(rs.getInt("MaNCC"));
                sp.setKichCo(rs.getString("KichCo"));
                sp.setMauSac(rs.getString("MauSac"));
                sp.setHinhAnh(rs.getString("HinhAnh"));
                sp.setTenNCC(rs.getString("TenNCC"));
                list.add(sp);
            }
        }
        return list;
    }

    public void insert(SanPham sp) throws SQLException {
        String sql = "INSERT INTO SANPHAM (MaSP, TenSP, LoaiSP, Gia, SL, MaNCC, KichCo, MauSac, HinhAnh) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, sp.getMaSP());
            ps.setString(2, sp.getTenSP());
            ps.setInt(3, sp.getLoaiSP());
            ps.setDouble(4, sp.getGia());
            ps.setInt(5, sp.getSl());
            ps.setInt(6, sp.getMaNCC());
            ps.setString(7, sp.getKichCo());
            ps.setString(8, sp.getMauSac());
            ps.setString(9, sp.getHinhAnh());
            ps.executeUpdate();
        }
    }

    public void update(SanPham sp) throws SQLException {
        String sql = "UPDATE SANPHAM SET TenSP=?, LoaiSP=?, Gia=?, SL=?, MaNCC=?, KichCo=?, MauSac=?, HinhAnh=? " +
                     "WHERE MaSP=?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sp.getTenSP());
            ps.setInt(2, sp.getLoaiSP());
            ps.setDouble(3, sp.getGia());
            ps.setInt(4, sp.getSl());
            ps.setInt(5, sp.getMaNCC());
            ps.setString(6, sp.getKichCo());
            ps.setString(7, sp.getMauSac());
            ps.setString(8, sp.getHinhAnh());
            ps.setInt(9, sp.getMaSP());
            ps.executeUpdate();
        }
    }

    public void delete(int maSP) throws SQLException {
        String sql = "DELETE FROM SANPHAM WHERE MaSP = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maSP);
            ps.executeUpdate();
        }
    }

    public double getGiaByMaSP(int maSP) throws SQLException {
        String sql = "SELECT Gia FROM SANPHAM WHERE MaSP = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maSP);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getDouble("Gia");
            }
        }
        throw new SQLException("Không tìm thấy sản phẩm!");
    }
}
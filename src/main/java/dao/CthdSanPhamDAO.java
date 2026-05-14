package dao;

import dto.CthdSanPham;
import until.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CthdSanPhamDAO {

    // Lấy chi tiết hóa đơn
    public List<CthdSanPham> getByMaHD(int maHD) throws SQLException {
        List<CthdSanPham> list = new ArrayList<>();
        String sql = "SELECT ct.MaHD, ct.MaSP, ct.SoLuong, ct.DonGia, sp.TenSP " +
                     "FROM CTHD_SANPHAM ct JOIN SANPHAM sp ON ct.MaSP = sp.MaSP " +
                     "WHERE ct.MaHD = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maHD);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CthdSanPham ct = new CthdSanPham();
                    ct.setMaHD(rs.getInt("MaHD"));
                    ct.setMaSP(rs.getInt("MaSP"));
                    ct.setSoLuong(rs.getInt("SoLuong"));
                    ct.setDonGia(rs.getDouble("DonGia"));
                    ct.setTenSP(rs.getString("TenSP"));
                    list.add(ct);
                }
            }
        }
        return list;
    }

    // Thêm sản phẩm vào hóa đơn
    public void insert(CthdSanPham ct) throws SQLException {
        String sql = "INSERT INTO CTHD_SANPHAM (MaHD, MaSP, SoLuong, DonGia) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ct.getMaHD());
            ps.setInt(2, ct.getMaSP());
            ps.setInt(3, ct.getSoLuong());
            ps.setDouble(4, ct.getDonGia());
            ps.executeUpdate();
        }
    }

    // Xóa sản phẩm khỏi hóa đơn
    public void delete(int maHD, int maSP) throws SQLException {
        String sql = "DELETE FROM CTHD_SANPHAM WHERE MaHD = ? AND MaSP = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maHD);
            ps.setInt(2, maSP);
            ps.executeUpdate();
        }
    }

    // Cập nhật tổng tiền hóa đơn
   public void updateThanhTien(int maHD) throws SQLException {
    String sql = "UPDATE HOADON SET ThanhTien = " +
                 "(SELECT ISNULL(SUM(SoLuong * DonGia), 0) FROM CTHD_SANPHAM WHERE MaHD = ?) + " +
                 "(SELECT ISNULL(SUM(SL * Gia), 0) FROM CTHD_VE WHERE MaHD = ?) " +
                 "WHERE MaHD = ?";
    
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, maHD);
        ps.setInt(2, maHD);
        ps.setInt(3, maHD);
        ps.executeUpdate();
    }
}

    // Kiểm tra tồn kho
    public int getTonKho(int maSP) throws SQLException {
        String sql = "SELECT SL FROM SANPHAM WHERE MaSP = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maSP);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("SL");
            }
        }
        return 0;
    }

    // Cập nhật tồn kho (giảm)
    public void giamTonKho(int maSP, int soLuong) throws SQLException {
        String sql = "UPDATE SANPHAM SET SL = SL - ? WHERE MaSP = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, soLuong);
            ps.setInt(2, maSP);
            ps.executeUpdate();
        }
    }
}
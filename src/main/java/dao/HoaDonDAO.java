package dao;

import dto.HoaDon;
import until.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HoaDonDAO {

    // Lấy tất cả hóa đơn
    public List<HoaDon> getAll() throws SQLException {
        List<HoaDon> list = new ArrayList<>();
        String sql = "SELECT hd.MaHD, hd.NgayHD, hd.MaNV, hd.MaKH, hd.ThanhTien, " +
                     "nv.Ho + ' ' + nv.Ten AS TenNV, " +
                     "ISNULL(kh.Ho + ' ' + kh.Ten, N'Khách lẻ') AS TenKH " +
                     "FROM HOADON hd " +
                     "JOIN NHANVIEN nv ON hd.MaNV = nv.MaNV " +
                     "LEFT JOIN KHACHHANG kh ON hd.MaKH = kh.MaKH " +
                     "ORDER BY hd.NgayHD DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setMaHD(rs.getInt("MaHD"));
                hd.setNgayHD(rs.getDate("NgayHD"));
                hd.setMaNV(rs.getInt("MaNV"));
                hd.setMaKH((Integer) rs.getObject("MaKH"));
                hd.setThanhTien(rs.getDouble("ThanhTien"));
                hd.setTenNV(rs.getString("TenNV"));
                hd.setTenKH(rs.getString("TenKH"));
                list.add(hd);
            }
        }
        return list;
    }

    // Tạo hóa đơn mới
    public void insert(HoaDon hd) throws SQLException {
        String sql = "INSERT INTO HOADON (MaHD, NgayHD, MaNV, MaKH, ThanhTien) VALUES (?, GETDATE(), ?, ?, 0)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, hd.getMaHD());
            ps.setInt(2, hd.getMaNV());
            if (hd.getMaKH() != null) {
                ps.setInt(3, hd.getMaKH());
            } else {
                ps.setNull(3, Types.INTEGER);
            }
            ps.executeUpdate();
        }
    }

    // Lấy mã hóa đơn tiếp theo
    public int getNextMaHD() throws SQLException {
        String sql = "SELECT ISNULL(MAX(MaHD), 0) + 1 FROM HOADON";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1);
        }
        return 1;
    }
}
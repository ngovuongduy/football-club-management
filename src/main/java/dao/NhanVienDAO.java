package dao;

import dto.NhanVien;
import until.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NhanVienDAO {

    public List<NhanVien> getAll() throws SQLException {
        List<NhanVien> list = new ArrayList<>();
        String sql = "SELECT nv.*, tk.TenTK, tk.LoaiTK " +
                     "FROM NHANVIEN nv LEFT JOIN TAIKHOAN tk ON nv.MaNV = tk.MaNV " +
                     "ORDER BY nv.MaNV";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setMaNV(rs.getInt("MaNV"));
                nv.setHo(rs.getString("Ho"));
                nv.setTen(rs.getString("Ten"));
                nv.setGioiTinh(rs.getString("GioiTinh"));
                nv.setDiaChi(rs.getString("DiaChi"));
                nv.setCmnd(rs.getString("CMND"));
                nv.setNgaySinh(rs.getDate("NgaySinh"));
                nv.setNgayBD(rs.getDate("NgayBD"));
                nv.setSoDT(rs.getString("SoDT"));
                nv.setLuong(rs.getDouble("Luong"));
                nv.setHinhAnh(rs.getString("HinhAnh"));
                nv.setTenTK(rs.getString("TenTK"));
                nv.setLoaiTK(rs.getString("LoaiTK"));
                list.add(nv);
            }
        }
        return list;
    }

    public void insert(NhanVien nv) throws SQLException {
        String sql = "INSERT INTO NHANVIEN (MaNV, Ho, Ten, GioiTinh, DiaChi, CMND, NgaySinh, NgayBD, SoDT, Luong) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, nv.getMaNV());
            ps.setString(2, nv.getHo());
            ps.setString(3, nv.getTen());
            ps.setString(4, nv.getGioiTinh());
            ps.setString(5, nv.getDiaChi());
            ps.setString(6, nv.getCmnd());
            ps.setDate(7, new java.sql.Date(nv.getNgaySinh().getTime()));
            ps.setDate(8, new java.sql.Date(nv.getNgayBD().getTime()));
            ps.setString(9, nv.getSoDT());
            ps.setDouble(10, nv.getLuong());
            ps.executeUpdate();
        }
    }

    public void update(NhanVien nv) throws SQLException {
        String sql = "UPDATE NHANVIEN SET Ho=?, Ten=?, GioiTinh=?, DiaChi=?, CMND=?, " +
                     "NgaySinh=?, NgayBD=?, SoDT=?, Luong=? WHERE MaNV=?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nv.getHo());
            ps.setString(2, nv.getTen());
            ps.setString(3, nv.getGioiTinh());
            ps.setString(4, nv.getDiaChi());
            ps.setString(5, nv.getCmnd());
            ps.setDate(6, new java.sql.Date(nv.getNgaySinh().getTime()));
            ps.setDate(7, new java.sql.Date(nv.getNgayBD().getTime()));
            ps.setString(8, nv.getSoDT());
            ps.setDouble(9, nv.getLuong());
            ps.setInt(10, nv.getMaNV());
            ps.executeUpdate();
        }
    }

    public void delete(int maNV) throws SQLException {
        // Xóa tài khoản trước
        String sqlTK = "DELETE FROM TAIKHOAN WHERE MaNV = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlTK)) {
            ps.setInt(1, maNV);
            ps.executeUpdate();
        }
        
        // Xóa nhân viên
        String sql = "DELETE FROM NHANVIEN WHERE MaNV = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maNV);
            ps.executeUpdate();
        }
    }

    // Tạo/cập nhật tài khoản
    public void saveTaiKhoan(int maNV, String tenTK, String matKhau, String loaiTK) throws SQLException {
        String sql = "IF EXISTS (SELECT 1 FROM TAIKHOAN WHERE MaNV = ?) " +
                     "UPDATE TAIKHOAN SET TenTK=?, MatKhau=?, LoaiTK=? WHERE MaNV=? " +
                     "ELSE " +
                     "INSERT INTO TAIKHOAN (MaTK, TenTK, MatKhau, LoaiTK, MaNV) " +
                     "VALUES ((SELECT ISNULL(MAX(MaTK),0)+1 FROM TAIKHOAN), ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maNV);
            ps.setString(2, tenTK);
            ps.setString(3, matKhau);
            ps.setString(4, loaiTK);
            ps.setInt(5, maNV);
            ps.setString(6, tenTK);
            ps.setString(7, matKhau);
            ps.setString(8, loaiTK);
            ps.setInt(9, maNV);
            ps.executeUpdate();
        }
    }
}
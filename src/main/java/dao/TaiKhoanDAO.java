/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;


import dto.TaiKhoan;
import until.DatabaseConnection;
import java.sql.*;

public class TaiKhoanDAO {
    public TaiKhoan findByUsernamePassword(String tenTK, String matKhau) throws SQLException {
        String sql = "SELECT MaTK, TenTK, LoaiTK, MaNV FROM TAIKHOAN WHERE TenTK = ? AND MatKhau = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tenTK);
            ps.setString(2, matKhau);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    TaiKhoan tk = new TaiKhoan();
                    tk.setMaTK(rs.getInt("MaTK"));
                    tk.setTenTK(rs.getString("TenTK"));
                    tk.setLoaiTK(rs.getString("LoaiTK"));
                    tk.setMaNV(rs.getInt("MaNV"));
                    return tk;
                }
            }
        }
        return null;
    }
}
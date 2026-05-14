package dao;

import dto.Ctpn;
import until.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CtpnDAO {

    public List<Ctpn> getByMaPN(int maPN) throws SQLException {
        List<Ctpn> list = new ArrayList<>();
        String sql = "SELECT ct.*, sp.TenSP FROM CTPN ct JOIN SANPHAM sp ON ct.MaSP = sp.MaSP WHERE ct.MaPN = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maPN);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Ctpn ct = new Ctpn();
                    ct.setMaPN(rs.getInt("MaPN"));
                    ct.setMaSP(rs.getInt("MaSP"));
                    ct.setSl(rs.getInt("SL"));
                    ct.setGia(rs.getDouble("Gia"));
                    ct.setTenSP(rs.getString("TenSP"));
                    list.add(ct);
                }
            }
        }
        return list;
    }

    public void insert(Ctpn ct) throws SQLException {
        String sql = "INSERT INTO CTPN (MaPN, MaSP, SL, Gia) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ct.getMaPN());
            ps.setInt(2, ct.getMaSP());
            ps.setInt(3, ct.getSl());
            ps.setDouble(4, ct.getGia());
            ps.executeUpdate();
        }
    }

    // Tăng tồn kho
    public void tangTonKho(int maSP, int sl) throws SQLException {
        String sql = "UPDATE SANPHAM SET SL = SL + ? WHERE MaSP = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, sl);
            ps.setInt(2, maSP);
            ps.executeUpdate();
        }
    }

    // Cập nhật tổng tiền phiếu nhập
    public void updateTongTien(int maPN) throws SQLException {
        String sql = "UPDATE PHIEUNHAP SET TongTien = " +
                     "(SELECT ISNULL(SUM(SL * Gia), 0) FROM CTPN WHERE MaPN = ?) WHERE MaPN = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maPN);
            ps.setInt(2, maPN);
            ps.executeUpdate();
        }
    }
}
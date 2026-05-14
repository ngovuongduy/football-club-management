package bus;

import dao.CthdSanPhamDAO;
import dao.SanPhamDAO;
import dto.CthdSanPham;
import java.sql.SQLException;
import java.util.List;

public class BanHangBUS {
    private CthdSanPhamDAO dao = new CthdSanPhamDAO();
    private SanPhamDAO sanPhamDAO = new SanPhamDAO();

    public List<CthdSanPham> getChiTietHD(int maHD) throws SQLException {
        return dao.getByMaHD(maHD);
    }

    public void themSanPhamVaoHD(int maHD, int maSP, int soLuong) throws Exception {
        // Kiểm tra tồn kho
        int tonKho = dao.getTonKho(maSP);
        if (soLuong > tonKho) {
            throw new Exception("Không đủ tồn kho! Hiện có: " + tonKho);
        }

        // Lấy giá từ DB
        double donGia = sanPhamDAO.getGiaByMaSP(maSP);

        CthdSanPham ct = new CthdSanPham();
        ct.setMaHD(maHD);
        ct.setMaSP(maSP);
        ct.setSoLuong(soLuong);
        ct.setDonGia(donGia);

        dao.insert(ct);
        dao.giamTonKho(maSP, soLuong);
        dao.updateThanhTien(maHD);
    }

    public void xoaSanPhamKhoiHD(int maHD, int maSP, int soLuong) throws Exception {
        dao.delete(maHD, maSP);
        // Hoàn lại tồn kho
        dao.giamTonKho(maSP, -soLuong);
        dao.updateThanhTien(maHD);
    }

    public double getTongTien(int maHD) throws SQLException {
        List<CthdSanPham> list = dao.getByMaHD(maHD);
        double tong = 0;
        for (CthdSanPham ct : list) {
            tong += ct.getThanhTien();
        }
        return tong;
    }
}
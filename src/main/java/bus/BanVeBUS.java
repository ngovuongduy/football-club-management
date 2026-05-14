package bus;

import dao.CthdVeDAO;
import dto.CthdVe;
import java.sql.SQLException;
import java.util.List;

public class BanVeBUS {
    private CthdVeDAO dao = new CthdVeDAO();

    public List<CthdVe> getChiTietVe(int maHD) throws SQLException {
        return dao.getByMaHD(maHD);
    }

    public void themVeVaoHD(int maHD, int maVe, int sl) throws Exception {
        int conLai = dao.getSLConLai(maVe);
        if (sl > conLai) {
            throw new Exception("Không đủ vé! Còn lại: " + conLai);
        }
        if (sl <= 0) {
            throw new Exception("Số lượng phải lớn hơn 0!");
        }

        double gia = dao.getGiaVe(maVe);

        CthdVe cv = new CthdVe();
        cv.setMaHD(maHD);
        cv.setMaVe(maVe);
        cv.setSl(sl);
        cv.setGia(gia);

        dao.insert(cv);
        dao.giamSLConLai(maVe, sl);
        dao.updateThanhTienHD(maHD);
        
        System.out.println("Đã thêm vé và gọi updateThanhTienHD cho HD: " + maHD);
    }

    public void xoaVeKhoiHD(int maHD, int maVe, int sl) throws Exception {
        dao.delete(maHD, maVe);
        dao.giamSLConLai(maVe, -sl);
        dao.updateThanhTienHD(maHD);
        
        System.out.println("Đã xóa vé và gọi updateThanhTienHD cho HD: " + maHD);
    }
}
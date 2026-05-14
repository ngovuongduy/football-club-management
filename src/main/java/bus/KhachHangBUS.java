package bus;

import dao.KhachHangDAO;
import dto.KhachHang;
import java.sql.SQLException;
import java.util.List;

public class KhachHangBUS {
    private KhachHangDAO dao = new KhachHangDAO();

    public List<KhachHang> getAll() throws SQLException {
        return dao.getAll();
    }

    public void them(KhachHang kh) throws Exception {
        if (kh.getHo().trim().isEmpty() || kh.getTen().trim().isEmpty()) {
            throw new Exception("Họ tên không được trống!");
        }
        if (kh.getSoDT().trim().isEmpty()) {
            throw new Exception("Số điện thoại không được trống!");
        }
        dao.insert(kh);
    }

    public void sua(KhachHang kh) throws Exception {
        dao.update(kh);
    }

    public void xoa(int maKH) throws Exception {
        try {
            dao.delete(maKH);
        } catch (SQLException e) {
            throw new Exception("Không thể xóa khách hàng đã có hóa đơn!");
        }
    }
}
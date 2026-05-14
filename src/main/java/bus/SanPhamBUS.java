package bus;

import dao.SanPhamDAO;
import dto.SanPham;
import java.sql.SQLException;
import java.util.List;

public class SanPhamBUS {
    private SanPhamDAO dao = new SanPhamDAO();

    public List<SanPham> getAll() throws SQLException {
        return dao.getAll();
    }

    public void themSanPham(SanPham sp) throws Exception {
        // Kiểm tra rỗng
        if (sp.getTenSP().trim().isEmpty()) {
            throw new Exception("Tên sản phẩm không được để trống");
        }
        if (sp.getGia() < 0) {
            throw new Exception("Giá sản phẩm không được âm");
        }
        if (sp.getSl() < 0) {
            throw new Exception("Số lượng không được âm");
        }
        
        try {
            dao.insert(sp);
        } catch (SQLException e) {
            if (e.getMessage().contains("PRIMARY KEY")) {
                throw new Exception("Mã sản phẩm đã tồn tại!");
            }
            throw e;
        }
    }

    public void suaSanPham(SanPham sp) throws Exception {
        if (sp.getTenSP().trim().isEmpty()) {
            throw new Exception("Tên sản phẩm không được để trống");
        }
        dao.update(sp);
    }

    public void xoaSanPham(int maSP) throws Exception {
        try {
            dao.delete(maSP);
        } catch (SQLException e) {
            if (e.getMessage().contains("FK_") || e.getMessage().contains("foreign key")) {
                throw new Exception("Không thể xóa sản phẩm đã có trong hóa đơn!");
            }
            throw e;
        }
    }
}
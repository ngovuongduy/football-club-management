package bus;

import dao.NhaCungCapDAO;
import dto.NhaCungCap;
import java.sql.SQLException;
import java.util.List;

public class NhaCungCapBUS {
    private NhaCungCapDAO dao = new NhaCungCapDAO();

    public List<NhaCungCap> getAll() throws SQLException {
        return dao.getAll();
    }

    public void them(NhaCungCap ncc) throws Exception {
        if (ncc.getTenNCC().trim().isEmpty()) {
            throw new Exception("Tên nhà cung cấp không được trống!");
        }
        dao.insert(ncc);
    }

    public void sua(NhaCungCap ncc) throws Exception {
        dao.update(ncc);
    }

    public void xoa(int maNCC) throws Exception {
        try {
            dao.delete(maNCC);
        } catch (SQLException e) {
            throw new Exception("Không thể xóa NCC đã có sản phẩm!");
        }
    }
}
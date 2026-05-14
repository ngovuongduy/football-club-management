package bus;

import dao.NhanVienDAO;
import dto.NhanVien;
import java.sql.SQLException;
import java.util.List;

public class NhanVienBUS {
    private NhanVienDAO dao = new NhanVienDAO();

    public List<NhanVien> getAll() throws SQLException {
        return dao.getAll();
    }

    public void them(NhanVien nv) throws Exception {
        if (nv.getHo().trim().isEmpty() || nv.getTen().trim().isEmpty()) {
            throw new Exception("Họ tên không được trống!");
        }
        if (nv.getNgayBD().before(nv.getNgaySinh())) {
            throw new Exception("Ngày vào làm phải sau ngày sinh!");
        }
        dao.insert(nv);
    }

    public void sua(NhanVien nv) throws Exception {
        dao.update(nv);
    }

    public void xoa(int maNV) throws Exception {
        dao.delete(maNV);
    }

    public void saveTaiKhoan(int maNV, String tenTK, String matKhau, String loaiTK) throws Exception {
        if (tenTK.trim().isEmpty() || matKhau.trim().isEmpty()) {
            throw new Exception("Tên TK và mật khẩu không được trống!");
        }
        dao.saveTaiKhoan(maNV, tenTK, matKhau, loaiTK);
    }
}
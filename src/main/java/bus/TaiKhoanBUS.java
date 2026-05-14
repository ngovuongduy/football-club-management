package bus;

import dao.TaiKhoanDAO;
import dto.TaiKhoan;

public class TaiKhoanBUS {
    private TaiKhoanDAO dao = new TaiKhoanDAO();

    public TaiKhoan dangNhap(String tenTK, String matKhau) throws Exception {
        if (tenTK.trim().isEmpty() || matKhau.trim().isEmpty()) {
            throw new Exception("Tên đăng nhập và mật khẩu không được để trống");
        }
        return dao.findByUsernamePassword(tenTK, matKhau);
    }
}
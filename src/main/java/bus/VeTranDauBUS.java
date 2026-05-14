package bus;

import dao.VeTranDauDAO;
import dto.VeTranDau;
import java.sql.SQLException;
import java.util.List;

public class VeTranDauBUS {
    private VeTranDauDAO dao = new VeTranDauDAO();

    public List<VeTranDau> getAll() throws SQLException {
        return dao.getAll();
    }

    public List<VeTranDau> getByMaTD(int maTD) throws SQLException {
        return dao.getByMaTD(maTD);
    }

    public void themVe(VeTranDau ve) throws Exception {
        if (ve.getGia() < 0) throw new Exception("Giá vé không được âm");
        if (ve.getSlTong() < 0) throw new Exception("Số lượng vé không được âm");
        dao.insert(ve);
    }

    public void suaVe(VeTranDau ve) throws Exception {
        dao.update(ve);
    }

    public void xoaVe(int maVe) throws Exception {
        dao.delete(maVe);
    }
}

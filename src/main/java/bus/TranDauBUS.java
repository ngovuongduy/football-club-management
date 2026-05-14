package bus;

import dao.TranDauDAO;
import dto.TranDau;
import java.sql.SQLException;
import java.util.List;

public class TranDauBUS {
    private TranDauDAO dao = new TranDauDAO();

    public List<TranDau> getAll() throws SQLException {
        return dao.getAll();
    }

    public void themTranDau(TranDau td) throws Exception {
        if (td.getTenVongDau().trim().isEmpty()) throw new Exception("Tên vòng đấu không được trống");
        if (td.getDoiThu().trim().isEmpty()) throw new Exception("Đối thủ không được trống");
        dao.insert(td);
    }

    public void suaTranDau(TranDau td) throws Exception {
        if (td.getTenVongDau().trim().isEmpty()) throw new Exception("Tên vòng đấu không được trống");
        dao.update(td);
    }

    public void xoaTranDau(int maTD) throws Exception {
        try {
            dao.delete(maTD);
        } catch (SQLException e) {
            throw new Exception("Không thể xóa trận đấu đã có vé!");
        }
    }
}
package bus;

import dao.HoaDonDAO;
import dto.HoaDon;
import java.sql.SQLException;
import java.util.List;

public class HoaDonBUS {
    private HoaDonDAO dao = new HoaDonDAO();

    public List<HoaDon> getAll() throws SQLException {
        return dao.getAll();
    }

    public int taoHoaDon(int maNV, Integer maKH) throws Exception {
        HoaDon hd = new HoaDon();
        hd.setMaHD(dao.getNextMaHD());
        hd.setMaNV(maNV);
        hd.setMaKH(maKH);
        dao.insert(hd);
        return hd.getMaHD();
    }
}

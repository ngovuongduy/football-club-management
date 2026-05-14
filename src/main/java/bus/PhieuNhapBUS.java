package bus;

import dao.PhieuNhapDAO;
import dao.CtpnDAO;
import dto.PhieuNhap;
import dto.Ctpn;
import java.sql.SQLException;
import java.util.List;

public class PhieuNhapBUS {
    private PhieuNhapDAO dao = new PhieuNhapDAO();
    private CtpnDAO ctpnDAO = new CtpnDAO();

    public List<PhieuNhap> getAll() throws SQLException {
        return dao.getAll();
    }

    public int taoPhieuNhap(int maNV, int maNCC) throws Exception {
        PhieuNhap pn = new PhieuNhap();
        pn.setMaPN(dao.getNextMaPN());
        pn.setMaNV(maNV);
        pn.setMaNCC(maNCC);
        dao.insert(pn);
        return pn.getMaPN();
    }

    public void themSPVaoPhieu(int maPN, int maSP, int sl, double gia) throws Exception {
        Ctpn ct = new Ctpn();
        ct.setMaPN(maPN);
        ct.setMaSP(maSP);
        ct.setSl(sl);
        ct.setGia(gia);
        ctpnDAO.insert(ct);
        ctpnDAO.tangTonKho(maSP, sl);
        ctpnDAO.updateTongTien(maPN);
    }
}
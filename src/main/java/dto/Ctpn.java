package dto;

public class Ctpn {
    private int maPN;
    private int maSP;
    private int sl;
    private double gia;
    private String tenSP;

    public Ctpn() {}

    public int getMaPN() { return maPN; }
    public void setMaPN(int maPN) { this.maPN = maPN; }
    public int getMaSP() { return maSP; }
    public void setMaSP(int maSP) { this.maSP = maSP; }
    public int getSl() { return sl; }
    public void setSl(int sl) { this.sl = sl; }
    public double getGia() { return gia; }
    public void setGia(double gia) { this.gia = gia; }
    public String getTenSP() { return tenSP; }
    public void setTenSP(String tenSP) { this.tenSP = tenSP; }
    public double getThanhTien() { return sl * gia; }
}

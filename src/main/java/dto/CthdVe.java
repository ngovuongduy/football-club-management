package dto;

public class CthdVe {
    private int maHD;
    private int maVe;
    private int sl;
    private double gia;
    // Hiển thị thêm
    private String loaiVe;
    private String tenVongDau;
    private String doiThu;

    public CthdVe() {}

    public int getMaHD() { return maHD; }
    public void setMaHD(int maHD) { this.maHD = maHD; }
    public int getMaVe() { return maVe; }
    public void setMaVe(int maVe) { this.maVe = maVe; }
    public int getSl() { return sl; }
    public void setSl(int sl) { this.sl = sl; }
    public double getGia() { return gia; }
    public void setGia(double gia) { this.gia = gia; }
    public String getLoaiVe() { return loaiVe; }
    public void setLoaiVe(String loaiVe) { this.loaiVe = loaiVe; }
    public String getTenVongDau() { return tenVongDau; }
    public void setTenVongDau(String tenVongDau) { this.tenVongDau = tenVongDau; }
    public String getDoiThu() { return doiThu; }
    public void setDoiThu(String doiThu) { this.doiThu = doiThu; }
    public double getThanhTien() { return sl * gia; }
}
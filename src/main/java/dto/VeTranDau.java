package dto;

public class VeTranDau {
    private int maVe;
    private int maTD;
    private String loaiVe;
    private double gia;
    private int slTong;
    private int slConLai;
    // Hiển thị thêm
    private String tenVongDau;
    private String doiThu;

    public VeTranDau() {}

    public int getMaVe() { return maVe; }
    public void setMaVe(int maVe) { this.maVe = maVe; }
    public int getMaTD() { return maTD; }
    public void setMaTD(int maTD) { this.maTD = maTD; }
    public String getLoaiVe() { return loaiVe; }
    public void setLoaiVe(String loaiVe) { this.loaiVe = loaiVe; }
    public double getGia() { return gia; }
    public void setGia(double gia) { this.gia = gia; }
    public int getSlTong() { return slTong; }
    public void setSlTong(int slTong) { this.slTong = slTong; }
    public int getSlConLai() { return slConLai; }
    public void setSlConLai(int slConLai) { this.slConLai = slConLai; }
    public String getTenVongDau() { return tenVongDau; }
    public void setTenVongDau(String tenVongDau) { this.tenVongDau = tenVongDau; }
    public String getDoiThu() { return doiThu; }
    public void setDoiThu(String doiThu) { this.doiThu = doiThu; }
}

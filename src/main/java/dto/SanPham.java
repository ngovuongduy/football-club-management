package dto;

public class SanPham {
    private int maSP;
    private String tenSP;
    private int loaiSP;
    private double gia;
    private int sl;
    private int maNCC;
    private String kichCo;
    private String mauSac;
    // Thêm tên NCC để hiển thị
    private String tenNCC;
private String hinhAnh;
    public SanPham() {}

    public SanPham(int maSP, String tenSP, int loaiSP, double gia, int sl, int maNCC, String kichCo, String mauSac) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.loaiSP = loaiSP;
        this.gia = gia;
        this.sl = sl;
        this.maNCC = maNCC;
        this.kichCo = kichCo;
        this.mauSac = mauSac;
    }

    // Getters và Setters
    public int getMaSP() { return maSP; }
    public String getHinhAnh() { 
    return hinhAnh; 
}

public void setHinhAnh(String hinhAnh) { 
    this.hinhAnh = hinhAnh; 
}

    public void setMaSP(int maSP) { this.maSP = maSP; }
    public String getTenSP() { return tenSP; }
    public void setTenSP(String tenSP) { this.tenSP = tenSP; }
    public int getLoaiSP() { return loaiSP; }
    public void setLoaiSP(int loaiSP) { this.loaiSP = loaiSP; }
    public double getGia() { return gia; }
    public void setGia(double gia) { this.gia = gia; }
    public int getSl() { return sl; }
    public void setSl(int sl) { this.sl = sl; }
    public int getMaNCC() { return maNCC; }
    public void setMaNCC(int maNCC) { this.maNCC = maNCC; }
    public String getKichCo() { return kichCo; }
    public void setKichCo(String kichCo) { this.kichCo = kichCo; }
    public String getMauSac() { return mauSac; }
    public void setMauSac(String mauSac) { this.mauSac = mauSac; }
    public String getTenNCC() { return tenNCC; }
    public void setTenNCC(String tenNCC) { this.tenNCC = tenNCC; }
}
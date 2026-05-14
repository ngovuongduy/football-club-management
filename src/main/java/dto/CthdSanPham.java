package dto;

public class CthdSanPham {
    private int maHD;
    private int maSP;
    private int soLuong;
    private double donGia;
    // Hiển thị thêm
    private String tenSP;
private String hinhAnh;
public String getHinhAnh() { return hinhAnh; }
public void setHinhAnh(String hinhAnh) { this.hinhAnh = hinhAnh; }
    public CthdSanPham() {}

    public int getMaHD() { return maHD; }
    public void setMaHD(int maHD) { this.maHD = maHD; }
    public int getMaSP() { return maSP; }
    public void setMaSP(int maSP) { this.maSP = maSP; }
    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }
    public double getDonGia() { return donGia; }
    public void setDonGia(double donGia) { this.donGia = donGia; }
    public String getTenSP() { return tenSP; }
    public void setTenSP(String tenSP) { this.tenSP = tenSP; }
    public double getThanhTien() { return soLuong * donGia; }
}
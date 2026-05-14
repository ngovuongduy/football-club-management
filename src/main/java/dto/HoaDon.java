package dto;

import java.util.Date;

public class HoaDon {
    private int maHD;
    private Date ngayHD;
    private int maNV;
    private Integer maKH;  // có thể null
    private double thanhTien;
    // Thông tin thêm để hiển thị
    private String tenKH;
    private String tenNV;

    public HoaDon() {}

    // Getters và Setters
    public int getMaHD() { return maHD; }
    public void setMaHD(int maHD) { this.maHD = maHD; }
    public Date getNgayHD() { return ngayHD; }
    public void setNgayHD(Date ngayHD) { this.ngayHD = ngayHD; }
    public int getMaNV() { return maNV; }
    public void setMaNV(int maNV) { this.maNV = maNV; }
    public Integer getMaKH() { return maKH; }
    public void setMaKH(Integer maKH) { this.maKH = maKH; }
    public double getThanhTien() { return thanhTien; }
    public void setThanhTien(double thanhTien) { this.thanhTien = thanhTien; }
    public String getTenKH() { return tenKH; }
    public void setTenKH(String tenKH) { this.tenKH = tenKH; }
    public String getTenNV() { return tenNV; }
    public void setTenNV(String tenNV) { this.tenNV = tenNV; }
}
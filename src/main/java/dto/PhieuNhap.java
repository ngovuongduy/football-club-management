package dto;

import java.util.Date;

public class PhieuNhap {
    private int maPN;
    private Date ngayNhap;
    private double tongTien;
    private int maNV;
    private int maNCC;
    private String tenNV;
    private String tenNCC;

    public PhieuNhap() {}

    public int getMaPN() { return maPN; }
    public void setMaPN(int maPN) { this.maPN = maPN; }
    public Date getNgayNhap() { return ngayNhap; }
    public void setNgayNhap(Date ngayNhap) { this.ngayNhap = ngayNhap; }
    public double getTongTien() { return tongTien; }
    public void setTongTien(double tongTien) { this.tongTien = tongTien; }
    public int getMaNV() { return maNV; }
    public void setMaNV(int maNV) { this.maNV = maNV; }
    public int getMaNCC() { return maNCC; }
    public void setMaNCC(int maNCC) { this.maNCC = maNCC; }
    public String getTenNV() { return tenNV; }
    public void setTenNV(String tenNV) { this.tenNV = tenNV; }
    public String getTenNCC() { return tenNCC; }
    public void setTenNCC(String tenNCC) { this.tenNCC = tenNCC; }
}

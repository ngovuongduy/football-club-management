package dto;

import java.util.Date;

public class KhachHang {
    private int maKH;
    private String ho;
    private String ten;
    private Date ngaySinh;
    private String gioiTinh;
    private String soDT;
    private String loaiKH;
    private double diemTichLuy;

    public KhachHang() {}

    public int getMaKH() { return maKH; }
    public void setMaKH(int maKH) { this.maKH = maKH; }
    public String getHo() { return ho; }
    public void setHo(String ho) { this.ho = ho; }
    public String getTen() { return ten; }
    public void setTen(String ten) { this.ten = ten; }
    public String getHoTen() { return ho + " " + ten; }
    public Date getNgaySinh() { return ngaySinh; }
    public void setNgaySinh(Date ngaySinh) { this.ngaySinh = ngaySinh; }
    public String getGioiTinh() { return gioiTinh; }
    public void setGioiTinh(String gioiTinh) { this.gioiTinh = gioiTinh; }
    public String getSoDT() { return soDT; }
    public void setSoDT(String soDT) { this.soDT = soDT; }
    public String getLoaiKH() { return loaiKH; }
    public void setLoaiKH(String loaiKH) { this.loaiKH = loaiKH; }
    public double getDiemTichLuy() { return diemTichLuy; }
    public void setDiemTichLuy(double diemTichLuy) { this.diemTichLuy = diemTichLuy; }
}
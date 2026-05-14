package dto;

import java.util.Date;

public class NhanVien {
    private int maNV;
    private String ho;
    private String ten;
    private String gioiTinh;
    private String diaChi;
    private String cmnd;
    private Date ngaySinh;
    private Date ngayBD;
    private String soDT;
    private double luong;
    private String hinhAnh;
    // Tài khoản
    private String tenTK;
    private String loaiTK;

    public NhanVien() {}

    public int getMaNV() { return maNV; }
    public void setMaNV(int maNV) { this.maNV = maNV; }
    public String getHo() { return ho; }
    public void setHo(String ho) { this.ho = ho; }
    public String getTen() { return ten; }
    public void setTen(String ten) { this.ten = ten; }
    public String getHoTen() { return ho + " " + ten; }
    public String getGioiTinh() { return gioiTinh; }
    public void setGioiTinh(String gioiTinh) { this.gioiTinh = gioiTinh; }
    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }
    public String getCmnd() { return cmnd; }
    public void setCmnd(String cmnd) { this.cmnd = cmnd; }
    public Date getNgaySinh() { return ngaySinh; }
    public void setNgaySinh(Date ngaySinh) { this.ngaySinh = ngaySinh; }
    public Date getNgayBD() { return ngayBD; }
    public void setNgayBD(Date ngayBD) { this.ngayBD = ngayBD; }
    public String getSoDT() { return soDT; }
    public void setSoDT(String soDT) { this.soDT = soDT; }
    public double getLuong() { return luong; }
    public void setLuong(double luong) { this.luong = luong; }
    public String getHinhAnh() { return hinhAnh; }
    public void setHinhAnh(String hinhAnh) { this.hinhAnh = hinhAnh; }
    public String getTenTK() { return tenTK; }
    public void setTenTK(String tenTK) { this.tenTK = tenTK; }
    public String getLoaiTK() { return loaiTK; }
    public void setLoaiTK(String loaiTK) { this.loaiTK = loaiTK; }
}

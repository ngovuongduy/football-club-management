package dto;

import java.util.Date;

public class TranDau {
    private int maTD;
    private String tenVongDau;
    private String doiThu;
    private Date ngayGio;
    private String diaDiem;
    private int trangThai;

    public TranDau() {}

    public int getMaTD() { return maTD; }
    public void setMaTD(int maTD) { this.maTD = maTD; }
    public String getTenVongDau() { return tenVongDau; }
    public void setTenVongDau(String tenVongDau) { this.tenVongDau = tenVongDau; }
    public String getDoiThu() { return doiThu; }
    public void setDoiThu(String doiThu) { this.doiThu = doiThu; }
    public Date getNgayGio() { return ngayGio; }
    public void setNgayGio(Date ngayGio) { this.ngayGio = ngayGio; }
    public String getDiaDiem() { return diaDiem; }
    public void setDiaDiem(String diaDiem) { this.diaDiem = diaDiem; }
    public int getTrangThai() { return trangThai; }
    public void setTrangThai(int trangThai) { this.trangThai = trangThai; }
    
    public String getTrangThaiText() {
        switch (trangThai) {
            case 1: return "Sắp diễn ra";
            case 2: return "Đang diễn ra";
            case 3: return "Kết thúc";
            default: return "Không xác định";
        }
    }
}
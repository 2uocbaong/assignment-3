/**
 * Lớp đại diện cho một cuốn sách trong thư viện.
 */
public class Book {
    private String maSach;
    private String tenSach;
    private String tacGia;
    private int namXuatBan;
    private int soLuongKho;
    private int tongLuotMuon; // dùng cho thống kê

    public Book(String maSach, String tenSach, String tacGia, int namXuatBan, int soLuongKho) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.tacGia = tacGia;
        this.namXuatBan = namXuatBan;
        this.soLuongKho = soLuongKho;
        this.tongLuotMuon = 0;
    }

    // ── Getters ──────────────────────────────────────────────
    public String getMaSach()      { return maSach; }
    public String getTenSach()     { return tenSach; }
    public String getTacGia()      { return tacGia; }
    public int    getNamXuatBan()  { return namXuatBan; }
    public int    getSoLuongKho()  { return soLuongKho; }
    public int    getTongLuotMuon(){ return tongLuotMuon; }

    // ── Setters ──────────────────────────────────────────────
    public void setTenSach(String tenSach)         { this.tenSach = tenSach; }
    public void setTacGia(String tacGia)           { this.tacGia = tacGia; }
    public void setNamXuatBan(int namXuatBan)      { this.namXuatBan = namXuatBan; }
    public void setSoLuongKho(int soLuongKho)      { this.soLuongKho = soLuongKho; }

    // ── Nghiệp vụ ────────────────────────────────────────────

    /** Kiểm tra sách còn trong kho không */
    public boolean conHang() {
        return soLuongKho > 0;
    }

    /** Giảm kho 1 khi cho mượn */
    public boolean giamKho() {
        if (!conHang()) return false;
        soLuongKho--;
        tongLuotMuon++;
        return true;
    }

    /** Tăng kho 1 khi nhận trả */
    public void tangKho() {
        soLuongKho++;
    }

    @Override
    public String toString() {
        return String.format("[%s] \"%s\" - %s (%d) | Kho: %d",
                maSach, tenSach, tacGia, namXuatBan, soLuongKho);
    }
}

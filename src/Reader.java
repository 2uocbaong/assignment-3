/**
 * Lớp đại diện cho một độc giả (sinh viên hoặc giảng viên).
 */
public class Reader {
    private String maDocGia;
    private String hoTen;
    private String email;
    private ReaderType loaiThe;

    public Reader(String maDocGia, String hoTen, String email, ReaderType loaiThe) {
        this.maDocGia = maDocGia;
        this.hoTen    = hoTen;
        this.email    = email;
        this.loaiThe  = loaiThe;
    }

    // ── Getters ──────────────────────────────────────────────
    public String     getMaDocGia() { return maDocGia; }
    public String     getHoTen()    { return hoTen; }
    public String     getEmail()    { return email; }
    public ReaderType getLoaiThe()  { return loaiThe; }

    // ── Setters ──────────────────────────────────────────────
    public void setHoTen(String hoTen)       { this.hoTen = hoTen; }
    public void setEmail(String email)       { this.email = email; }
    public void setLoaiThe(ReaderType type)  { this.loaiThe = type; }

    // ── Nghiệp vụ ────────────────────────────────────────────

    /** Số sách tối đa độc giả được mượn cùng lúc */
    public int getGioiHanMuon() {
        return loaiThe.getGioiHanMuon();
    }

    @Override
    public String toString() {
        return String.format("[%s] %s <%s> - %s",
                maDocGia, hoTen, email, loaiThe.getTenLoai());
    }
}

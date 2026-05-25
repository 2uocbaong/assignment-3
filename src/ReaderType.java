/**
 * Enum định nghĩa loại thẻ độc giả và giới hạn mượn sách.
 */
public enum ReaderType {
    SINH_VIEN("Sinh viên", 3),
    GIANG_VIEN("Giảng viên", 5);

    private final String tenLoai;
    private final int gioiHanMuon;

    ReaderType(String tenLoai, int gioiHanMuon) {
        this.tenLoai = tenLoai;
        this.gioiHanMuon = gioiHanMuon;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public int getGioiHanMuon() {
        return gioiHanMuon;
    }

    @Override
    public String toString() {
        return tenLoai + " (tối đa " + gioiHanMuon + " cuốn)";
    }
}

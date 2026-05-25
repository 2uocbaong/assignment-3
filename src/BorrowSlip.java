/**
 * Phiếu mượn sách: liên kết một Reader với một Book, ghi ngày mượn và ngày hẹn trả.
 */
public class BorrowSlip {
    private String maPhieu;
    private Reader docGia;
    private Book   sach;
    private int    ngayMuon;   // số ngày (ví dụ: ngày thứ 1, 2, 3…)
    private int    ngayHenTra;
    private int    ngayTraThucTe; // -1 nếu chưa trả
    private Fine   fine;          // null nếu chưa trả

    public BorrowSlip(String maPhieu, Reader docGia, Book sach, int ngayMuon, int ngayHenTra) {
        this.maPhieu       = maPhieu;
        this.docGia        = docGia;
        this.sach          = sach;
        this.ngayMuon      = ngayMuon;
        this.ngayHenTra    = ngayHenTra;
        this.ngayTraThucTe = -1;
        this.fine          = null;
    }

    // ── Getters ──────────────────────────────────────────────
    public String getMaPhieu()        { return maPhieu; }
    public Reader getDocGia()         { return docGia; }
    public Book   getSach()           { return sach; }
    public int    getNgayMuon()       { return ngayMuon; }
    public int    getNgayHenTra()     { return ngayHenTra; }
    public int    getNgayTraThucTe()  { return ngayTraThucTe; }
    public Fine   getFine()           { return fine; }

    /** Kiểm tra đã trả chưa */
    public boolean daTra() {
        return ngayTraThucTe != -1;
    }

    /** Kiểm tra quá hạn theo ngày hiện tại giả định */
    public boolean quaHan(int ngayHienTai) {
        return !daTra() && ngayHienTai > ngayHenTra;
    }

    /** Ghi nhận trả sách và tính phạt */
    public Fine thucHienTra(int ngayTraThucTe) {
        this.ngayTraThucTe = ngayTraThucTe;
        this.fine = Fine.tinh(ngayHenTra, ngayTraThucTe);
        sach.tangKho();
        return fine;
    }

    @Override
    public String toString() {
        String trangThai = daTra()
                ? "Đã trả (ngày " + ngayTraThucTe + ")"
                : "Đang mượn";
        return String.format("[%s] %s mượn \"%s\" | Ngày %d → Hạn %d | %s",
                maPhieu,
                docGia.getHoTen(),
                sach.getTenSach(),
                ngayMuon,
                ngayHenTra,
                trangThai);
    }
}

import java.util.ArrayList;
import java.util.List;

/**
 * Lớp trung tâm quản lý danh sách sách, độc giả và phiếu mượn.
 */
public class Library {
    private List<Book>       danhSachSach      = new ArrayList<>();
    private List<Reader>     danhSachDocGia    = new ArrayList<>();
    private List<BorrowSlip> danhSachPhieu     = new ArrayList<>();
    private int              boiSoPhieu        = 1; // bộ đếm tự tăng để sinh mã phiếu

    // ── Quản lý sách ────────────────────────────────────────

    public void themSach(Book sach) {
        danhSachSach.add(sach);
    }

    public Book timSachTheoMa(String maSach) {
        for (Book b : danhSachSach) {
            if (b.getMaSach().equalsIgnoreCase(maSach)) return b;
        }
        return null;
    }

    /**
     * Tìm kiếm sách theo tên HOẶC tác giả (không phân biệt hoa thường, khớp gần đúng).
     */
    public List<Book> timKiemSach(String tuKhoa) {
        List<Book> ketQua = new ArrayList<>();
        String kw = tuKhoa.toLowerCase();
        for (Book b : danhSachSach) {
            if (b.getTenSach().toLowerCase().contains(kw)
                    || b.getTacGia().toLowerCase().contains(kw)) {
                ketQua.add(b);
            }
        }
        return ketQua;
    }

    public List<Book> getDanhSachSach() { return danhSachSach; }

    // ── Quản lý độc giả ─────────────────────────────────────

    public void themDocGia(Reader docGia) {
        danhSachDocGia.add(docGia);
    }

    public Reader timDocGiaTheoMa(String maDocGia) {
        for (Reader r : danhSachDocGia) {
            if (r.getMaDocGia().equalsIgnoreCase(maDocGia)) return r;
        }
        return null;
    }

    public List<Reader> getDanhSachDocGia() { return danhSachDocGia; }

    // ── Quản lý phiếu mượn ──────────────────────────────────

    public void themPhieuMuon(BorrowSlip phieu) {
        danhSachPhieu.add(phieu);
    }

    public BorrowSlip timPhieuTheoMa(String maPhieu) {
        for (BorrowSlip p : danhSachPhieu) {
            if (p.getMaPhieu().equalsIgnoreCase(maPhieu)) return p;
        }
        return null;
    }

    /** Đếm số sách một độc giả đang mượn (chưa trả). */
    public int demSachDangMuon(Reader docGia) {
        int dem = 0;
        for (BorrowSlip p : danhSachPhieu) {
            if (!p.daTra() && p.getDocGia().getMaDocGia().equals(docGia.getMaDocGia())) {
                dem++;
            }
        }
        return dem;
    }

    /** Liệt kê phiếu mượn đang quá hạn tính đến ngày hiện tại giả định. */
    public List<BorrowSlip> layPhieuQuaHan(int ngayHienTai) {
        List<BorrowSlip> ketQua = new ArrayList<>();
        for (BorrowSlip p : danhSachPhieu) {
            if (p.quaHan(ngayHienTai)) ketQua.add(p);
        }
        return ketQua;
    }

    /** Tạo mã phiếu mượn tự tăng. */
    public String sinhMaPhieu() {
        return String.format("PM%03d", boiSoPhieu++);
    }

    public List<BorrowSlip> getDanhSachPhieu() { return danhSachPhieu; }

    // ── Thống kê (thử thách) ─────────────────────────────────

    /** Sách được mượn nhiều nhất. */
    public Book sachDuocMuonNhieuNhat() {
        Book best = null;
        for (Book b : danhSachSach) {
            if (best == null || b.getTongLuotMuon() > best.getTongLuotMuon()) {
                best = b;
            }
        }
        return best;
    }

    /** Độc giả mượn nhiều sách nhất (tổng phiếu – kể cả đã trả). */
    public Reader docGiaMuonNhieuNhat() {
        Reader best = null;
        int maxSo = 0;
        for (Reader r : danhSachDocGia) {
            int dem = 0;
            for (BorrowSlip p : danhSachPhieu) {
                if (p.getDocGia().getMaDocGia().equals(r.getMaDocGia())) dem++;
            }
            if (dem > maxSo) {
                maxSo = dem;
                best  = r;
            }
        }
        return best;
    }
}

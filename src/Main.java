import java.util.List;
import java.util.Scanner;

/**
 * Lớp Main: menu dòng lệnh điều khiển toàn bộ hệ thống thư viện.
 */
public class Main {

    private static final Library   library   = new Library();
    private static final Librarian librarian = new Librarian("TT001", "Nguyễn Thị Lan");
    private static final Scanner   sc        = new Scanner(System.in);

    public static void main(String[] args) {
        khoiTaoDuLieuMau();
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║   HỆ THỐNG QUẢN LÝ THƯ VIỆN TRƯỜNG  ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.println(librarian + " đang phục vụ.\n");

        boolean chay = true;
        while (chay) {
            inMenu();
            int lua = docSoNguyen("Chọn: ");
            System.out.println();
            switch (lua) {
                case 1  -> xemDanhSachSach();
                case 2  -> xemDanhSachDocGia();
                case 3  -> choMuonSach();
                case 4  -> traSach();
                case 5  -> timKiemSach();
                case 6  -> xemPhieuQuaHan();
                case 7  -> xemTatCaPhieu();
                case 8  -> thongKe();
                case 0  -> { System.out.println("Tạm biệt!"); chay = false; }
                default -> System.out.println("Lựa chọn không hợp lệ.\n");
            }
        }
    }

    // ── Menu ─────────────────────────────────────────────────

    private static void inMenu() {
        System.out.println("─────────────────────────────────────");
        System.out.println(" 1. Xem danh sách sách");
        System.out.println(" 2. Xem danh sách độc giả");
        System.out.println(" 3. Cho mượn sách");
        System.out.println(" 4. Trả sách");
        System.out.println(" 5. Tìm kiếm sách (tên / tác giả)");
        System.out.println(" 6. Xem phiếu quá hạn");
        System.out.println(" 7. Xem tất cả phiếu mượn");
        System.out.println(" 8. Thống kê");
        System.out.println(" 0. Thoát");
        System.out.println("─────────────────────────────────────");
    }

    // ── Chức năng 1: Xem danh sách sách ─────────────────────

    private static void xemDanhSachSach() {
        System.out.println("=== DANH SÁCH SÁCH ===");
        List<Book> ds = library.getDanhSachSach();
        if (ds.isEmpty()) { System.out.println("Chưa có sách nào.\n"); return; }
        for (Book b : ds) System.out.println("  " + b);
        System.out.println();
    }

    // ── Chức năng 2: Xem danh sách độc giả ─────────────────

    private static void xemDanhSachDocGia() {
        System.out.println("=== DANH SÁCH ĐỘC GIẢ ===");
        List<Reader> ds = library.getDanhSachDocGia();
        if (ds.isEmpty()) { System.out.println("Chưa có độc giả nào.\n"); return; }
        for (Reader r : ds) {
            int dangMuon = library.demSachDangMuon(r);
            System.out.printf("  %s | Đang mượn: %d/%d%n",
                    r, dangMuon, r.getGioiHanMuon());
        }
        System.out.println();
    }

    // ── Chức năng 3: Cho mượn sách ───────────────────────────

    private static void choMuonSach() {
        System.out.println("=== CHO MƯỢN SÁCH ===");
        String maDocGia = docChuoi("Mã độc giả: ");
        Reader docGia   = library.timDocGiaTheoMa(maDocGia);
        if (docGia == null) { System.out.println("✗ Không tìm thấy độc giả.\n"); return; }

        String maSach = docChuoi("Mã sách: ");
        Book   sach   = library.timSachTheoMa(maSach);
        if (sach == null) { System.out.println("✗ Không tìm thấy sách.\n"); return; }

        int ngayMuon   = docSoNguyen("Ngày mượn (số nguyên): ");
        int ngayHenTra = docSoNguyen("Ngày hẹn trả (số nguyên): ");

        librarian.choMuon(library, docGia, sach, ngayMuon, ngayHenTra);
        System.out.println();
    }

    // ── Chức năng 4: Trả sách ────────────────────────────────

    private static void traSach() {
        System.out.println("=== TRẢ SÁCH ===");
        String maPhieu = docChuoi("Mã phiếu mượn: ");
        BorrowSlip phieu = library.timPhieuTheoMa(maPhieu);
        if (phieu == null) { System.out.println("✗ Không tìm thấy phiếu mượn.\n"); return; }

        int ngayTraThucTe = docSoNguyen("Ngày trả thực tế (số nguyên): ");
        librarian.nhanTra(phieu, ngayTraThucTe);
        System.out.println();
    }

    // ── Chức năng 5: Tìm kiếm sách ───────────────────────────

    private static void timKiemSach() {
        System.out.println("=== TÌM KIẾM SÁCH ===");
        String tuKhoa = docChuoi("Nhập tên sách hoặc tác giả: ");
        List<Book> ketQua = library.timKiemSach(tuKhoa);
        if (ketQua.isEmpty()) {
            System.out.println("Không tìm thấy sách phù hợp.");
        } else {
            System.out.println("Kết quả (" + ketQua.size() + " sách):");
            for (Book b : ketQua) System.out.println("  " + b);
        }
        System.out.println();
    }

    // ── Chức năng 6: Xem phiếu quá hạn ──────────────────────

    private static void xemPhieuQuaHan() {
        System.out.println("=== PHIẾU MƯỢN QUÁ HẠN ===");
        int ngayHienTai = docSoNguyen("Ngày hiện tại giả định: ");
        List<BorrowSlip> ds = library.layPhieuQuaHan(ngayHienTai);
        if (ds.isEmpty()) {
            System.out.println("Không có phiếu nào quá hạn.");
        } else {
            System.out.println("Danh sách quá hạn (" + ds.size() + " phiếu):");
            for (BorrowSlip p : ds) {
                System.out.printf("  %s | Quá hạn %d ngày%n",
                        p, ngayHienTai - p.getNgayHenTra());
            }
        }
        System.out.println();
    }

    // ── Chức năng 7: Xem tất cả phiếu ───────────────────────

    private static void xemTatCaPhieu() {
        System.out.println("=== TẤT CẢ PHIẾU MƯỢN ===");
        List<BorrowSlip> ds = library.getDanhSachPhieu();
        if (ds.isEmpty()) { System.out.println("Chưa có phiếu nào.\n"); return; }
        for (BorrowSlip p : ds) System.out.println("  " + p);
        System.out.println();
    }

    // ── Chức năng 8: Thống kê ────────────────────────────────

    private static void thongKe() {
        System.out.println("=== THỐNG KÊ ===");
        Book   sachHot  = library.sachDuocMuonNhieuNhat();
        Reader docGiaHot = library.docGiaMuonNhieuNhat();

        if (sachHot != null) {
            System.out.printf("  Sách mượn nhiều nhất : \"%s\" (%d lượt)%n",
                    sachHot.getTenSach(), sachHot.getTongLuotMuon());
        } else {
            System.out.println("  Chưa có lượt mượn nào.");
        }

        if (docGiaHot != null) {
            int tong = 0;
            for (BorrowSlip p : library.getDanhSachPhieu()) {
                if (p.getDocGia().getMaDocGia().equals(docGiaHot.getMaDocGia())) tong++;
            }
            System.out.printf("  Độc giả tích cực nhất: %s (%d phiếu)%n",
                    docGiaHot.getHoTen(), tong);
        } else {
            System.out.println("  Chưa có độc giả nào mượn.");
        }
        System.out.println();
    }

    // ── Dữ liệu mẫu ──────────────────────────────────────────

    private static void khoiTaoDuLieuMau() {
        // Sách
        library.themSach(new Book("S001", "Lập trình Java cơ bản",  "Nguyễn Văn A", 2020, 3));
        library.themSach(new Book("S002", "Cấu trúc dữ liệu & Giải thuật", "Trần Thị B", 2019, 2));
        library.themSach(new Book("S003", "Hệ điều hành",            "Lê Văn C",    2021, 1));
        library.themSach(new Book("S004", "Mạng máy tính",           "Phạm Thị D",  2018, 4));
        library.themSach(new Book("S005", "Cơ sở dữ liệu",          "Nguyễn Văn A", 2022, 0)); // hết kho

        // Độc giả
        library.themDocGia(new Reader("DG001", "Trần Minh Khoa",  "khoa@sv.edu.vn",    ReaderType.SINH_VIEN));
        library.themDocGia(new Reader("DG002", "Lê Thị Hoa",      "hoa@sv.edu.vn",     ReaderType.SINH_VIEN));
        library.themDocGia(new Reader("DG003", "PGS.TS Nguyễn Nam", "nam@gv.edu.vn",   ReaderType.GIANG_VIEN));
        library.themDocGia(new Reader("DG004", "ThS. Phạm Thu",    "thu@gv.edu.vn",    ReaderType.GIANG_VIEN));
    }

    // ── Tiện ích nhập liệu ───────────────────────────────────

    private static String docChuoi(String nhanHien) {
        System.out.print(nhanHien);
        return sc.nextLine().trim();
    }

    private static int docSoNguyen(String nhanHien) {
        while (true) {
            System.out.print(nhanHien);
            String line = sc.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("  Vui lòng nhập số nguyên.");
            }
        }
    }
}

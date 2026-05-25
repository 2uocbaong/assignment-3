/**
 * Thủ thư: thực hiện nghiệp vụ cho mượn, nhận trả, tính phạt.
 */
public class Librarian {
    private String maThuThu;
    private String hoTen;

    public Librarian(String maThuThu, String hoTen) {
        this.maThuThu = maThuThu;
        this.hoTen    = hoTen;
    }

    public String getMaThuThu() { return maThuThu; }
    public String getHoTen()    { return hoTen; }
    public void   setHoTen(String hoTen) { this.hoTen = hoTen; }

    /**
     * Cho mượn sách: kiểm tra kho, kiểm tra giới hạn loại thẻ, tạo phiếu mượn.
     *
     * @param library     kho dữ liệu thư viện
     * @param docGia      độc giả muốn mượn
     * @param sach        sách muốn mượn
     * @param ngayMuon    ngày mượn (số nguyên giả định)
     * @param ngayHenTra  ngày hẹn trả
     * @return BorrowSlip nếu thành công, null nếu thất bại
     */
    public BorrowSlip choMuon(Library library, Reader docGia, Book sach,
                              int ngayMuon, int ngayHenTra) {
        // 1. Kiểm tra sách còn trong kho
        if (!sach.conHang()) {
            System.out.println("✗ Sách \"" + sach.getTenSach() + "\" đã hết trong kho.");
            return null;
        }

        // 2. Kiểm tra giới hạn mượn theo loại thẻ
        int dangMuon = library.demSachDangMuon(docGia);
        if (dangMuon >= docGia.getGioiHanMuon()) {
            System.out.printf("✗ %s đang mượn %d/%d cuốn – đã đạt giới hạn (%s).%n",
                    docGia.getHoTen(), dangMuon, docGia.getGioiHanMuon(),
                    docGia.getLoaiThe().getTenLoai());
            return null;
        }

        // 3. Tạo phiếu mượn
        String maPhieu = library.sinhMaPhieu();
        BorrowSlip phieu = new BorrowSlip(maPhieu, docGia, sach, ngayMuon, ngayHenTra);
        sach.giamKho();
        library.themPhieuMuon(phieu);

        System.out.printf("✓ Tạo phiếu %s: %s mượn \"%s\" (hạn ngày %d).%n",
                maPhieu, docGia.getHoTen(), sach.getTenSach(), ngayHenTra);
        return phieu;
    }

    /**
     * Nhận trả sách, tính phạt nếu trễ.
     *
     * @param phieu          phiếu mượn cần trả
     * @param ngayTraThucTe  ngày trả thực tế
     */
    public void nhanTra(BorrowSlip phieu, int ngayTraThucTe) {
        if (phieu.daTra()) {
            System.out.println("✗ Phiếu " + phieu.getMaPhieu() + " đã được trả trước đó.");
            return;
        }

        Fine fine = phieu.thucHienTra(ngayTraThucTe);
        System.out.printf("✓ Nhận trả sách \"%s\" từ %s (ngày %d).%n",
                phieu.getSach().getTenSach(),
                phieu.getDocGia().getHoTen(),
                ngayTraThucTe);

        if (fine.coTienPhat()) {
            System.out.println("  → " + fine);
        } else {
            System.out.println("  → Trả đúng hạn. Không có tiền phạt.");
        }
    }

    @Override
    public String toString() {
        return String.format("Thủ thư [%s] %s", maThuThu, hoTen);
    }
}

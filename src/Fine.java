/**
 * Lớp tách riêng logic tính tiền phạt trả sách trễ.
 */
public class Fine {
    private static final long PHI_PHAT_MOI_NGAY = 5_000; // VND

    private int soNgayTre;
    private long soTienPhat;

    public Fine(int soNgayTre) {
        this.soNgayTre  = soNgayTre;
        this.soTienPhat = soNgayTre > 0 ? soNgayTre * PHI_PHAT_MOI_NGAY : 0;
    }

    public int  getSoNgayTre()   { return soNgayTre; }
    public long getSoTienPhat()  { return soTienPhat; }
    public boolean coTienPhat()  { return soTienPhat > 0; }

    /** Tính tiền phạt dựa trên ngày hẹn trả và ngày trả thực tế */
    public static Fine tinh(int ngayHenTra, int ngayTraThucTe) {
        int soNgayTre = ngayTraThucTe - ngayHenTra;
        return new Fine(Math.max(0, soNgayTre));
    }

    @Override
    public String toString() {
        if (!coTienPhat()) return "Không có tiền phạt.";
        return String.format("Trễ %d ngày → Tiền phạt: %,d VND", soNgayTre, soTienPhat);
    }
}

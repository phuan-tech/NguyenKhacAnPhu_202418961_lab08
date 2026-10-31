// Nguyến Khắc An Phú 202418961

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InvalidDeviceReadingException {
        System.out.println("======================================================================");
        System.out.println("    CHƯƠNG TRÌNH KIỂM THỬ HỆ THỐNG QUẢN LÝ THIẾT BỊ PHÒNG HỌC (LAB 08)");
        System.out.println("======================================================================\n");

        // Khởi tạo các dịch vụ lắng nghe (Subscribers)
        ConsoleNotificationService consoleNotifier = new ConsoleNotificationService();
        DeviceAuditLogger auditLogger = new DeviceAuditLogger();
        MaintenanceService maintenanceService = new MaintenanceService();

        // Khởi tạo các thiết bị (Publishers)
        ProjectorRemote projector = new ProjectorRemote("PRJ-01", "Remote Máy Chiếu P101", 85);
        AirConditionerRemote acRemote = new AirConditionerRemote("AC-01", "Remote Điều Hòa P101", "P101", true);
        WirelessMicrophone mic = new WirelessMicrophone("MIC-01", "Micro Không Dây P101", 70, 90);

        List<TeachingDevice> devices = new ArrayList<>();
        devices.add(projector);
        devices.add(acRemote);
        devices.add(mic);

        // ====================================================================
        // TEST 1: KIỂM THỬ ĐIỀU PHỐI ĐĂNG KÝ (DeviceMonitoringCoordinator)
        // Đăng ký các dịch vụ lắng nghe vào thiết bị một cách tập trung.
        // ====================================================================
        System.out.println("--- [TEST 1] ĐIỀU PHỐI ĐĂNG KÝ SỰ KIỆN (DeviceMonitoringCoordinator) ---");
        DeviceMonitoringCoordinator coordinator = new DeviceMonitoringCoordinator(
                devices, consoleNotifier, auditLogger, maintenanceService
        );
        coordinator.subscribe();
        System.out.println("-> Đã điều phối đăng ký tất cả thiết bị với các Subscriber thành công.\n");

        // ====================================================================
        // TEST 2: THAY ĐỔI TRẠNG THÁI THIẾT BỊ (StatusChanged Event & AuditLogger)
        // Quy tắc:
        // - Trạng thái mới khác trạng thái cũ: phát sinh sự kiện và ghi log.
        // - Trạng thái mới trùng trạng thái cũ: không phát sinh sự kiện.
        // ====================================================================
        System.out.println("--- [TEST 2] KIỂM THỬ THAY ĐỔI TRẠNG THÁI THIẾT BỊ ---");
        System.out.println("1. Chuyển trạng thái máy chiếu: AVAILABLE -> BORROWED");
        projector.changeStatus(DeviceStatus.BORROWED);

        System.out.println("2. Chuyển tiếp trạng thái máy chiếu: BORROWED -> UNDER_MAINTENANCE");
        projector.changeStatus(DeviceStatus.UNDER_MAINTENANCE);

        System.out.println("3. Thử đặt lại trạng thái UNDER_MAINTENANCE (trùng trạng thái hiện tại):");
        int countBefore = auditLogger.getEntries().size();
        projector.changeStatus(DeviceStatus.UNDER_MAINTENANCE);
        int countAfter = auditLogger.getEntries().size();
        System.out.println("-> Số lượng log trước: " + countBefore + ", sau: " + countAfter 
                + (countBefore == countAfter ? " (HỢP LỆ: Không phát sinh event trùng!)" : " (LỖI: Bị ghi trùng!)"));

        System.out.println("\nNhật ký Audit Logger ghi nhận được:");
        for (String entry : auditLogger.getEntries()) {
            System.out.println("   [LOG] " + entry);
        }
        System.out.println();

        // ====================================================================
        // TEST 3: CẢNH BÁO MÁY CHIẾU (ProjectorRemote)
        // Quy tắc:
        // - Pin < 20% và > 0%: phát sinh cảnh báo Warning.
        // - Cập nhật tiếp trong vùng pin thấp (<20%): không phát sinh lặp lại.
        // - Pin = 0%: phát sinh cảnh báo Critical.
        // ====================================================================
        System.out.println("--- [TEST 3] KIỂM THỬ CẢNH BÁO PIN MÁY CHIẾU (ProjectorRemote) ---");
        try {
            System.out.println("1. Cập nhật pin xuống 15% (dưới 20% -> kỳ vọng Warning):");
            projector.updateBatteryLevel(15);

            System.out.println("2. Cập nhật pin tiếp xuống 10% (cùng vùng < 20% -> kỳ vọng KHÔNG cảnh báo lặp):");
            projector.updateBatteryLevel(10);

            System.out.println("3. Cập nhật pin xuống 0% (kỳ vọng Critical):");
            projector.updateBatteryLevel(0);
        } catch (InvalidDeviceReadingException e) {
            System.err.println("Lỗi đọc pin: " + e.getMessage());
        }
        System.out.println();

        // ====================================================================
        // TEST 4: CẢNH BÁO ĐIỀU HÒA (AirConditionerRemote)
        // Quy tắc:
        // - Chuyển từ phản hồi (true) sang không phản hồi (false): phát sinh Critical.
        // - Tiếp tục nhận trạng thái không phản hồi (false): không phát sinh lặp lại.
        // ====================================================================
        System.out.println("--- [TEST 4] KIỂM THỬ ĐIỀU HÒA MẤT PHẢN HỒI (AirConditionerRemote) ---");
        System.out.println("1. Cập nhật isResponsive = false (kỳ vọng Critical):");
        acRemote.updateResponseState(false);

        System.out.println("2. Tiếp tục cập nhật isResponsive = false (kỳ vọng KHÔNG cảnh báo lặp):");
        acRemote.updateResponseState(false);

        System.out.println("3. Thiết bị phản hồi trở lại (isResponsive = true):");
        acRemote.updateResponseState(true);
        System.out.println();

        // ====================================================================
        // TEST 5: CẢNH BÁO MICRO KHÔNG DÂY (WirelessMicrophone)
        // Quy tắc:
        // - Pin < 30%: Warning; Pin = 0%: Critical.
        // - Tín hiệu < 40%: Warning; Tín hiệu = 0%: Critical.
        // ====================================================================
        System.out.println("--- [TEST 5] KIỂM THỬ MICRO KHÔNG DÂY (WirelessMicrophone) ---");
        try {
            System.out.println("1. Giảm pin xuống 25% (< 30% -> kỳ vọng Warning):");
            mic.updateBatteryLevel(25);

            System.out.println("2. Giảm tín hiệu xuống 35% (< 40% -> kỳ vọng Warning):");
            mic.updateSignalQuality(35);

            System.out.println("3. Tín hiệu về 0% (kỳ vọng Critical):");
            mic.updateSignalQuality(0);
        } catch (InvalidDeviceReadingException e) {
            System.err.println("Lỗi đọc giá trị micro: " + e.getMessage());
        }
        System.out.println();

        // ====================================================================
        // TEST 6: DỊCH VỤ BẢO TRÌ (MaintenanceService)
        // Quy tắc:
        // - Chỉ xử lý và lưu yêu cầu bảo trì đối với sự kiện CRITICAL.
        // - Bỏ qua hoàn toàn các sự kiện WARNING / INFORMATION.
        // ====================================================================
        System.out.println("--- [TEST 6] KIỂM TRA DỊCH VỤ BẢO TRÌ (MaintenanceService) ---");
        System.out.println("Danh sách yêu cầu bảo trì đã tự động tiếp nhận (chỉ chứa Critical):");
        for (String req : maintenanceService.getRequests()) {
            System.out.println("   [MAINTENANCE] " + req);
        }
        System.out.println();

        // ====================================================================
        // TEST 7: DELEGATE/CALLBACK LỌC THÔNG BÁO (FilteredAlertSubscriber)
        // Quy tắc:
        // - Sử dụng cơ chế lọc dựa trên Predicate (Filter) và Consumer (Action)
        //   để minh họa callback có điều kiện tùy chỉnh.
        // ====================================================================
        System.out.println("--- [TEST 7] BỘ LỌC THÔNG BÁO TÙY BIẾN (FilteredAlertSubscriber) ---");
        // Ví dụ 1: Bộ lọc chỉ quan tâm cảnh báo CRITICAL
        FilteredAlertSubscriber criticalSubscriber = new FilteredAlertSubscriber(
                alert -> alert.getSeverity() == AlertSeverity.CRITICAL,
                alert -> System.out.println("   [FILTER-CRITICAL] Phát hiện lỗi nghiêm trọng từ thiết bị: " 
                        + alert.getDeviceId() + " -> " + alert.getMessage())
        );

        // Đăng ký trực tiếp bộ lọc này vào Micro
        mic.addAlertListener(criticalSubscriber);

        System.out.println("Kích hoạt cảnh báo pin = 0% trên Micro để kiểm tra bộ lọc:");
        try {
            mic.updateBatteryLevel(0);
        } catch (InvalidDeviceReadingException e) {
            System.err.println(e.getMessage());
        }
        System.out.println();

        // ====================================================================
        // TEST 8: HỦY ĐĂNG KÝ SỰ KIỆN (Unsubscribe & Coordinator)
        // Quy tắc:
        // - Khi hủy đăng ký, thiết bị phát cảnh báo nhưng các dịch vụ không còn nhận.
        // ====================================================================
        System.out.println("--- [TEST 8] HỦY ĐĂNG KÝ SỰ KIỆN (Unsubscribe) ---");
        coordinator.unsubscribe();
        System.out.println("-> Đã gọi coordinator.unsubscribe().");
        System.out.println("Thử chuyển trạng thái máy chiếu sang AVAILABLE (kỳ vọng không in cảnh báo, không ghi log mới):");
        int logSizeBefore = auditLogger.getEntries().size();
        projector.changeStatus(DeviceStatus.AVAILABLE);
        int logSizeAfter = auditLogger.getEntries().size();
        System.out.println("-> Số log ghi nhận: " + logSizeBefore + " -> " + logSizeAfter 
                + (logSizeBefore == logSizeAfter ? " (HỢP LỆ: Đã hủy đăng ký thành công!)" : " (LỖI: Vẫn bị ghi log!)"));
        System.out.println();

        // ====================================================================
        // TEST 9: KIỂM THỬ XỬ LÝ NGOẠI LỆ (InvalidDeviceReadingException)
        // Quy tắc:
        // - Khi mức pin hoặc tín hiệu < 0 hoặc > 100, phải ném ngoại lệ InvalidDeviceReadingException.
        // ====================================================================
        System.out.println("--- [TEST 9] KIỂM THỬ NGOẠI LỆ ĐỌC DỮ LIỆU BẤT THƯỜNG ---");
        try {
            System.out.println("1. Thử cập nhật pin = 150 (vượt quá 100):");
            projector.updateBatteryLevel(150);
            System.out.println("-> LỖI: Không phát hiện giá trị bất thường!");
        } catch (InvalidDeviceReadingException e) {
            System.out.println("-> Bắt ngoại lệ thành công: " + e.getMessage() 
                    + " (Mã: " + e.getDeviceId() + ", Giá trị sai: " + e.getInvalidValue() + ")");
        }

        try {
            System.out.println("2. Thử cập nhật tín hiệu Micro = -10 (nhỏ hơn 0):");
            mic.updateSignalQuality(-10);
            System.out.println("-> LỖI: Không phát hiện giá trị bất thường!");
        } catch (InvalidDeviceReadingException e) {
            System.out.println("-> Bắt ngoại lệ thành công: " + e.getMessage() 
                    + " (Mã: " + e.getDeviceId() + ", Giá trị sai: " + e.getInvalidValue() + ")");
        }
    }
}

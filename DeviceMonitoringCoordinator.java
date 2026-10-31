// Nguyến Khắc An Phú 202418961

import java.util.ArrayList;
import java.util.List;

public class DeviceMonitoringCoordinator implements AutoCloseable {
    private final List<TeachingDevice> devices;
    private final ConsoleNotificationService notificationService;
    private final DeviceAuditLogger auditLogger;
    private final MaintenanceService maintenanceService;

    private boolean subscribed;

    public DeviceMonitoringCoordinator(List<TeachingDevice> devices, ConsoleNotificationService notificationService,
                                    DeviceAuditLogger auditLogger, MaintenanceService maintenanceService) {
        this.devices = new ArrayList<>(devices);
        this.notificationService = notificationService;
        this.auditLogger = auditLogger;
        this.maintenanceService = maintenanceService;
    }

    // Đăng kí
    public void subscribe() {
        if (subscribed) {
            return;
        }

        for (TeachingDevice device : devices) {
            device.addAlertListener(notificationService);
            device.addStatusChangedListener(auditLogger);
            device.addAlertListener(maintenanceService);
        }

        subscribed = true;
    }

    // Hủy đăng kí
    public void unsubscribe() {
        if (!subscribed) {
            return;
        }

        for (TeachingDevice device : devices) {
            device.removeAlertListener(notificationService);
            device.removeStatusChangedListener(auditLogger);
            device.removeAlertListener(maintenanceService);
        }

        subscribed = false;
    }

    @Override
    public void close() {
        unsubscribe();
    }
}
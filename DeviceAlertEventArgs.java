// Nguyến Khắc An Phú 202418961

import java.time.LocalDateTime;

public class DeviceAlertEventArgs {
    private final String deviceId;
    private final String message;
    private final AlertSeverity severity;
    private final LocalDateTime occurredAt;

    public DeviceAlertEventArgs(String deviceId, String message, AlertSeverity severity, LocalDateTime occurredAt) {
        this.deviceId = deviceId;
        this.message = message;
        this.severity = severity;
        this.occurredAt = occurredAt;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getMessage() {
        return message;
    }

    public AlertSeverity getSeverity() {
        return severity;
    }

    public LocalDateTime getOccurredAt() {
        return occurredAt;
    }
}

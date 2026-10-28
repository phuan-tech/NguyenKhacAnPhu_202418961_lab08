import java.time.LocalDateTime;

public class DeviceAlertEventArgs {
    private String deviceId;
    private String message;
    private AlertSeverity severity;
    private LocalDateTime occurredAt;

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

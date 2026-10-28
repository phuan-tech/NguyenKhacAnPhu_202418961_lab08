import java.time.LocalDateTime;

public class DeviceStatusChangedEventArgs {
    private String deviceId;
    private DeviceStatus oldStatus;
    private DeviceStatus newStatus;
    private LocalDateTime changedAt;

    public DeviceStatusChangedEventArgs(String deviceId, DeviceStatus oldStatus, DeviceStatus newStatus, LocalDateTime changedAt) {
        this.deviceId = deviceId;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
        this.changedAt = changedAt;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public DeviceStatus getOldStatus() {
        return oldStatus;
    }

    public DeviceStatus getNewStatus() {
        return newStatus;
    }

    public LocalDateTime getChangedAt() {
        return changedAt;
    }
}

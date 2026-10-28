import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;

public abstract class TeachingDevice {
    private String id;
    private String name;
    private DeviceStatus status;
    private final List<DeviceStatusChangedListener> statusListeners = new ArrayList<>();
    private final List<DeviceAlertListener> alertListeners = new ArrayList<>();

    protected TeachingDevice(String id, String name) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Mã thiết bị không được rỗng");
        }

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Tên thiết bị không được rỗng");
        }

        this.id = id;
        this.name = name;
        this.status = DeviceStatus.AVAILABLE;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public DeviceStatus getDeviceStatus() {
        return this.status;
    }

    public void addStatusChangedListener(DeviceStatusChangedListener listener) {
        if (listener != null || !statusListeners.contains(listener)) {
            statusListeners.add(listener);
        }
    }

    public void removeStatusChangedListener(DeviceStatusChangedListener listener) {
        statusListeners.remove(listener);
    }

    public void addAlertListener(DeviceAlertListener listener) {
        if (listener != null || !alertListeners.contains(listener)) {
            alertListeners.add(listener);
        }
    }

    public void changeStatus(DeviceStatus newStatus) {
        if (newStatus == this.status) {
            return;
        }

        DeviceStatus oldStatus = this.status;
        this.status = newStatus;
    }
}

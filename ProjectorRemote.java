// Nguyễn Khắc An Phú 202418961

public class ProjectorRemote extends TeachingDevice {
    private int batteryLevel;

    public ProjectorRemote(String id, String name, int batteryLevel) throws InvalidDeviceReadingException {
        super(id, name);
        if (batteryLevel < 0 || batteryLevel > 100) {
            throw new InvalidDeviceReadingException(this.getId(), batteryLevel);
        }

        this.batteryLevel = batteryLevel;
    }
    
    // Cập nhật mức pin
    public void updateBatteryLevel(int newLevel) throws InvalidDeviceReadingException{
        if (newLevel < 0 || newLevel > 100) {
            throw new InvalidDeviceReadingException(this.getId(), newLevel);
        }

        int oldLevel = this.batteryLevel;
        this.batteryLevel = newLevel;

        if (oldLevel >= 20) {
            if (batteryLevel < 20 && batteryLevel > 0) {
                raiseAlert("Projector remote "+ this.getId() + " battery is low: " + batteryLevel + "%", AlertSeverity.WARNING);
            }
        }

        if (oldLevel > 0) {
            if (batteryLevel == 0) {
                raiseAlert("Projector remote " + this.getId() + " battery is depleted: 0%", AlertSeverity.CRITICAL);
            }
        }
    }

    public int getBatteryLevel() {
        return this.batteryLevel;
    }
}

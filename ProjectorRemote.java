public class ProjectorRemote extends TeachingDevice {
    private int batteryLevel;

    public ProjectorRemote(String id, String name, int batteryLevel) {
        super(id, name);
        if (batteryLevel < 0 || batteryLevel > 100) {
            throw new IllegalArgumentException("Mức pin phải từ 0 đến 100");
        }

        this.batteryLevel = batteryLevel;
    }
    
    public void updateBatteryLevel(int newLevel) {
        if (newLevel < 0 || newLevel > 100) {
            throw new IllegalArgumentException("Mức pin phải từ 0 đến 100");
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

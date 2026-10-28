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
        this.batteryLevel = newLevel;
    }

    public int getBatteryLevel() {
        return this.batteryLevel;
    }
}

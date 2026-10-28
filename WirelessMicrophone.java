public class WirelessMicrophone extends TeachingDevice {
    private int batteryLevel;
    private int signalQuality;

    public WirelessMicrophone(String id, String name, int batteryLevel, int signalQuality) {
        super(id, name);
        if (batteryLevel < 0 || batteryLevel > 100) {
            throw new IllegalArgumentException("Mức pin phải từ 0 đến 100");
        }

        if (signalQuality < 0 || signalQuality > 100) {
            throw new IllegalArgumentException("Chất lượng tín hiệu phải từ 0 đến 100");
        }

        this.batteryLevel = batteryLevel;
        this.signalQuality = signalQuality;
    }

    public int getBatteryLevel() {
        return this.batteryLevel;
    }

    public int getSignalQuality() {
        return this.signalQuality;
    }
}

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

    public void updateBatteryLevel(int newLevel) {
        if (batteryLevel < 0 || batteryLevel > 100) {
            throw new IllegalArgumentException("Mức pin phải từ 0 đến 100");
        }

        int oldLevel = this.batteryLevel;
        this.batteryLevel = newLevel;

        if (oldLevel >= 30) {
            if (batteryLevel < 30 && batteryLevel > 0) {
                raiseAlert("Wireless microphone " + this.getId() + " battery is low: " + batteryLevel + "%", AlertSeverity.WARNING);
            }
        }

        if (oldLevel > 0) {
            if (batteryLevel == 0) {
                raiseAlert("Wireless microphone battery is depleted: 0%", AlertSeverity.CRITICAL);
            }
        }
    }

    public void updateSignalQuality(int newQuality) {
        if (signalQuality < 0 || signalQuality > 100) {
            throw new IllegalArgumentException("Chất lượng tín hiệu phải từ 0 đến 100");
        }

        int oldQuality = this.signalQuality;
        this.signalQuality = newQuality;

        if (oldQuality >= 40) {
            if (signalQuality < 40 && signalQuality > 0) {
                raiseAlert("Wireless microphone " + this.getId() + " signal quality is weak: " + signalQuality + "%", AlertSeverity.WARNING);
            }
        }

        if (oldQuality > 0) {
            if (signalQuality == 0) {
                raiseAlert("Wireless microphone signal is lost: 0%", AlertSeverity.CRITICAL);
            }
        }
    }
}

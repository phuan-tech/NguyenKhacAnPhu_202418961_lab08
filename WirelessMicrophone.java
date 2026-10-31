// Nguyễn Khắc An Phú 202418961

public class WirelessMicrophone extends TeachingDevice {
    private int batteryLevel;
    private int signalQuality;

    public WirelessMicrophone(String id, String name, int batteryLevel, int signalQuality) throws InvalidDeviceReadingException {
        super(id, name);
        if (batteryLevel < 0 || batteryLevel > 100) {
            throw new InvalidDeviceReadingException(this.getId(), batteryLevel);
        }

        if (signalQuality < 0 || signalQuality > 100) {
            throw new InvalidDeviceReadingException(this.getId(), signalQuality);
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

    // Cập nhật mức pin
    public void updateBatteryLevel(int newLevel) throws InvalidDeviceReadingException {
        if (newLevel < 0 || newLevel > 100) {
            throw new InvalidDeviceReadingException(this.getId(), newLevel);
        }

        int oldLevel = this.batteryLevel;
        this.batteryLevel = newLevel;

        if (oldLevel >= 30) {
            if (batteryLevel < 30 && batteryLevel > 0) {
                raiseAlert("Wireless microphone " + this.getId() + " battery is low: " + batteryLevel + "%",
                        AlertSeverity.WARNING);
            }
        }

        if (oldLevel > 0) {
            if (batteryLevel == 0) {
                raiseAlert("Wireless microphone " + this.getId() + " battery is depleted: 0%", AlertSeverity.CRITICAL);
            }
        }
    }

    // Cập nhật chất lượng tín hiệu
    public void updateSignalQuality(int newQuality) throws InvalidDeviceReadingException {
        if (newQuality < 0 || newQuality > 100) {
            throw new InvalidDeviceReadingException(this.getId(), newQuality);
        }

        int oldQuality = this.signalQuality;
        this.signalQuality = newQuality;

        if (oldQuality >= 40) {
            if (signalQuality < 40 && signalQuality > 0) {
                raiseAlert("Wireless microphone " + this.getId() + " signal quality is weak: " + signalQuality + "%",
                        AlertSeverity.WARNING);
            }
        }

        if (oldQuality > 0) {
            if (signalQuality == 0) {
                raiseAlert("Wireless microphone " + this.getId() + " signal is lost: 0%", AlertSeverity.CRITICAL);
            }
        }
    }
}

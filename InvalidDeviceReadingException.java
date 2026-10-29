public class InvalidDeviceReadingException extends Exception {
    private String deviceId;
    private int invalidValue;

    public InvalidDeviceReadingException(String deviceId, int invalidValue) {
        super("Invalid reading for device " + deviceId + ": " + invalidValue);
        this.deviceId = deviceId;
        this.invalidValue = invalidValue;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public int getInvalidValue() {
        return invalidValue;
    }
}

public class InvalidDeviceReadingException extends Exception {
    private String deviceId;
    private int invalidValue;

    public InvalidDeviceReadingException(String deviceId, int invalidValue) {
        super("Thiết bị " + deviceId + " không hợp lệ");
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

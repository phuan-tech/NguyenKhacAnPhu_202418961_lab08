// Nguyến Khắc An Phú 202418961

public class ConsoleNotificationService implements DeviceAlertListener {

    @Override
    public void onAlertRaised(Object sender, DeviceAlertEventArgs event) {
        System.out.println("[" + event.getSeverity() + "] " + event.getDeviceId() + ": " + event.getMessage());
    }
}
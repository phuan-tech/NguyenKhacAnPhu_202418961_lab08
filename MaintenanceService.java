import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MaintenanceService implements DeviceAlertListener {

    private final List<String> requests = new ArrayList<>();

    public List<String> getRequests() {
        return Collections.unmodifiableList(requests);
    }

    @Override
    public void onAlertRaised(Object sender, DeviceAlertEventArgs event) {

        if (event.getSeverity() != AlertSeverity.CRITICAL) {
            return;
        }

        requests.add(
                "Maintenance request for "
                + event.getDeviceId() + ": "
                + event.getMessage());
    }
}
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DeviceAuditLogger implements DeviceStatusChangedListener {
    private final List<String> entries = new ArrayList<>();

    public List<String> getEntries() {
        return Collections.unmodifiableList(entries);
    }

    @Override
    public void onStatusChanged(Object sender, DeviceStatusChangedEventArgs event) {

        entries.add(event.getChangedAt() + ": "
                + event.getDeviceId() + ", "
                + event.getOldStatus() + " -> "
                + event.getNewStatus());
    }
}
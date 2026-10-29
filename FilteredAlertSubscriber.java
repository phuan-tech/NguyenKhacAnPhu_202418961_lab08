import java.util.function.Consumer;
import java.util.function.Predicate;

public class FilteredAlertSubscriber implements DeviceAlertListener {

    private final Predicate<DeviceAlertEventArgs> filter;
    private final Consumer<DeviceAlertEventArgs> action;

    public FilteredAlertSubscriber(Predicate<DeviceAlertEventArgs> filter, Consumer<DeviceAlertEventArgs> action) {
        this.filter = filter;
        this.action = action;
    }

    @Override
    public void onAlertRaised(Object sender, DeviceAlertEventArgs event) {
        if (filter.test(event)) {
            action.accept(event);
        }
    }
}

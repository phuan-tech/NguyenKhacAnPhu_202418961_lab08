public class AirConditionerRemote extends TeachingDevice {
    private String assignedRoom;
    private boolean isResponsive;

    public AirConditionerRemote(String id, String name, String assignedRoom, boolean isResponsive) {
        super(id, name);
        if (assignedRoom == null || assignedRoom.isBlank()) {
            throw new IllegalArgumentException();
        }

        this.assignedRoom = assignedRoom;
        this.isResponsive = isResponsive;
    }

    public String getAssignedRoom() {
        return this.assignedRoom;
    }

    public boolean getIsResponsive() {
        return this.isResponsive;
    }
}

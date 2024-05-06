
public class Room {
    private String id;
    private GuestGroup residents = null;
    private Maid maid = null;

    public Room(int id) {
        this.id = Integer.toString(id);
    }

    public String id() { return id; }

    public boolean isRentable() { return residents == null; }
    
    public boolean hasMaid() { return maid != null; }

    public void stopCleaning() { maid = null; }

    public void startCleaning(Maid maid) { this.maid = maid; }

    public void setResidents(GuestGroup val) { residents = val; }

    public void freeRoom() { residents = null; }
}

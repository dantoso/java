
public class Maid extends Thread {
    private int id;
    private Room cleandedRoom = null;

    public Maid(int id) {
        this.id = id;
    }

    public int id() { return id; }

    public void clean(Room room) {
        cleandedRoom = room;
        start();
        room.startCleaning(this);
    }

    public void run() {
        System.out.println("Maid " + id + " has started cleaning Room " + cleandedRoom.id());
        waitMs(1000);
        cleandedRoom.stopCleaning();
        System.out.println("Maid " + id + " finished cleaning Room " + cleandedRoom.id());
        Hotel.singleton.maidIsDone(id);
    }

    private void waitMs(long ms) {
        try {
            sleep(ms);
        } catch (InterruptedException e) {
            System.out.println("maid was interrupted while cleaning");
        }
    }
}

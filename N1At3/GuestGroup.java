import java.util.Random;
import java.util.LinkedList;
import java.util.Queue;

public class GuestGroup extends Thread {
    private String id;
    private Queue<Room> usedRooms = new LinkedList<Room>();
    private int size;
    private boolean isSecondTry = false;
    private boolean isSatisfied = false;
    private boolean hasLeftComplaint = false;
    private PatienceThread patience = new PatienceThread(10000);

    public GuestGroup(int id) {
        Random gen = new Random();
        size = gen.nextInt(1, 7);
        this.id = Integer.toString(id);
    }

    public int size() { return size; }

    public void startOver() {
        isSecondTry = true;
        System.out.println("Guest group " + id + " has restarted");
        Room[] rooms = lookForRoom();

        if(rooms == null) {
            complain();
        } else {
            System.out.println("Guest group " + id + " got rooms!");
            waitMs(5000);

            Hotel.singleton.roomLock.lock();
            try {
                for(Room room: usedRooms) {
                    usedRooms = null;
                    room.freeRoom();
                    Hotel.singleton.rentDidEnd(room);
                }
            } finally {
                Hotel.singleton.roomLock.unlock();
            }
        }
    }

    public void run() {
        System.out.println("Guest group " + id + " has started");
        Room[] rooms = lookForRoom();

        if(rooms == null) {
            waitInQueue();
        } else {
            System.out.println("Guest group " + id + " got rooms!");
            waitMs(5000);
            for(Room room: usedRooms) {
                usedRooms = null;
                room.freeRoom();
                Hotel.singleton.rentDidEnd(room);
            }
        }
    }

    public void receiveRoom(Room room) {
        usedRooms.add(room);
    }

    private Room[] lookForRoom() {
       Receptionist receptionist = Hotel.singleton.lookForReceptionist(this);

        try {
            receptionist.join();
        } catch(InterruptedException exception) {
            System.out.println("A receptionist got interrupted on execution");
        }

        return receptionist.result;
    }

    private void waitInQueue() {
        Hotel.singleton.addGroupToQueue(this);
        System.out.println("Guest group " + id + " is waiting in Queue");
        patience.countDown(this);
    }
    
    private void waitMs(long ms) {
        try {
            sleep(ms);
        } catch (InterruptedException e) {
            System.out.println("guest was interrupted while waiting");
        }
    }


    public synchronized void patienceDidRunOut() {
        if(!isSatisfied && !isSecondTry) {
            complain();
        }
    }

    private synchronized void complain() {
        if(!hasLeftComplaint) {
            Hotel.singleton.groupGiveUpQueue(this);
            Hotel.singleton.receiveComplaint();
            System.out.println("Guest group " + id + " has left complaint!");
            hasLeftComplaint = true;
        }
    }

    public String id() { return id; }
}

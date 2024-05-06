import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Hotel {
    
    public static Hotel singleton = new Hotel();

    public Room[] rooms = new Room[10];
    public Lock roomLock = new ReentrantLock();

    public Receptionist[] receptionists = new Receptionist[5];
    public Maid[] maids = new Maid[10];
    private int receptionistCount = 0;
    private int maidCount = 0;

    private Queue<GuestGroup> queue = new LinkedList<GuestGroup>();
    private int complaintCounter = 0;
    
    public Hotel() {
        for(int i = 0; i < 10; i++) {
            rooms[i] = new Room(i);
        }
        for(int i = 0; i < 5; i++) {
            receptionists[i] = null;
        }
        for(int i = 0; i < 10; i++) {
            maids[i] = null;
        }
    }

    public synchronized Receptionist lookForReceptionist(GuestGroup group) {
        while(true) {
            for(int i = 0; i < 5; i++) {
                if(receptionists[i] != null) {
                    continue;
                }
                receptionistCount++;
                receptionists[i] = new Receptionist(receptionistCount);
                receptionists[i].findRoomsFor(group);
                return receptionists[i];
            }
        }        
    }

    public void rent(Room room, GuestGroup group) {
        room.setResidents(group);
        group.receiveRoom(room);
        System.out.println("Guest group " + group.id() + " got Room " + room.id());
    }

    public synchronized void addGroupToQueue(GuestGroup group) {
        queue.add(group);
    }

    public synchronized void groupGiveUpQueue(GuestGroup group) {
        queue.remove(group);
    }

    public void rentDidEnd(Room room) {
        Maid maid = findMaidToCleanRoom(room);
        
        try {
            maid.join();
        } catch(InterruptedException err) {
            System.out.println("maid was interrupted while cleaning room");
        }
        if(!queue.isEmpty()) {
            GuestGroup group = queue.remove();
            group.startOver();
        }
    }

    public synchronized void receiveComplaint() {
        complaintCounter++;
    }

    private synchronized Maid findMaidToCleanRoom(Room room) {
        for(int i = 0; i < 10; i++) {
            if(maids[i] != null) {
                continue;
            }
            maidCount++;
            maids[i] = new Maid(maidCount);
            maids[i].clean(room);
            return maids[i];
        }

        // nao precisa lidar com null pq NumMaids = NumRooms
        return null;
    }

    public void receptionistIsDone(int id) {
        for(int i = 0; i<5; i++) {
            if(receptionists[i] == null) {
                continue;
            }
            if(receptionists[i].id() == id) {
                receptionists[i] = null;
                break;
            }
        }
    }

    public void maidIsDone(int id) {
        for(int i = 0; i<10; i++) {
            if(maids[i] == null) {
                continue;
            }
            if(maids[i].id() == id) {
                maids[i] = null;
                break;
            }
        }
    }

    public void printComplaints() {
        System.out.println("Number of complaints: " + complaintCounter);
    }
}


public class Receptionist extends Thread {
    private int id;
    public Room[] result = null;
    private GuestGroup assistedGroup = null;

    public Receptionist(int id) {
        this.id = id;
    }

    public int id() { return id; }

    public synchronized void findRoomsFor(GuestGroup group) {
        assistedGroup = group;
        start();
    }

    public void run() {
        System.out.println("Receptionist " + id + " has started helping Guest " + assistedGroup.id());

        int quantity = roomQuantityForGroup(assistedGroup.size());
        System.out.println("Receptionist " + id + " is waiting lock...");
        Hotel.singleton.roomLock.lock();
        try {
            System.out.println("Receptionist " + id + " needs " + quantity + " rooms");
            result = searchRentableRooms(quantity);
            if(result != null) {
                for(Room room: result) {
                    Hotel.singleton.rent(room, assistedGroup);
                }
            }
        } finally {
            System.out.println("Receptionist " + id + " unlocked!");
            Hotel.singleton.roomLock.unlock();
            Hotel.singleton.receptionistIsDone(id);
        }
    }

    private Room[] searchRentableRooms(int quantity) {
        Room[] rooms = Hotel.singleton.rooms;
        Room[] foundRooms = new Room[quantity];

        int idx = 0;

        for(Room room: rooms) {
            if(room.isRentable()) {
                foundRooms[idx] = room;
                idx++;
                if(idx == quantity) {
                    System.out.println("Receptionist " + id + " got all rooms");
                    return foundRooms;
                }
            }
        }
        System.out.println("Receptionist " + id + " failed!!");
        return null;
    }

    private int roomQuantityForGroup(int size) {
        float num = (float) size/4;
        int integer = (int) num;
        if(num - integer > 0) {
            integer++;
        }
        // System.out.println(integer);
        return integer;
    }
}

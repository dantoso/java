
public class Main {
    public static void main(String args[]) {
        GuestGroup[] guests = new GuestGroup[50];

        for(int i = 0; i < 50; i++) {
            guests[i] = new GuestGroup(i);
        }

        for(int i = 0; i < 50; i++) {
            guests[i].start();
        }
    }
}

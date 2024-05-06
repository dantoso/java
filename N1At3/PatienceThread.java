public class PatienceThread extends Thread {
    private GuestGroup group;
    private long time;

    public PatienceThread(long time) {
        this.time = time;
    }

    public void countDown(GuestGroup group) {
        this.group = group;
        start();
    }

    public void run() {
        waitMs(time);
        group.patienceDidRunOut();
    }

    private void waitMs(long ms) {
        try {
            sleep(ms);
        } catch (InterruptedException e) {
            System.out.println("patience thread was interrupted while sleeping");
        }
    }
}

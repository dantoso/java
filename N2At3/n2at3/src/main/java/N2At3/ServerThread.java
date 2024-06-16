package N2At3;

public class ServerThread extends Thread {
    public Server server;
    public int port;
    public void run() {
        server.start(port);
    }
}

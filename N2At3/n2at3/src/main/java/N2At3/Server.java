package N2At3;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    private String filename = "livros.json";
    private ServerSocket server;
    private Socket socket;

    private InputStream input;
    private OutputStream out;

    private File database = new File(filename);

    public void start(int port) {
        try {
            server = new ServerSocket(port);
            System.out.println("Server started");
            System.out.println("Waiting for a client ...");

            socket = server.accept();
            input = socket.getInputStream();
            out = socket.getOutputStream();

            System.out.println("Connection established");

            while(true) {
                sendData();
                waitForResponse();   
            }

        } catch(Exception e) {
            System.out.println("Server failed to start");
        }
    }

    private void waitForResponse() {
        try {
            byte[] buffer = new byte[1024];
            int responseLen = input.read(buffer);
            String response = new String(buffer, 0, responseLen);

            Character command = response.charAt(0);
            String data = response.substring(1);

            switch(command) {
                case 'N':
                    registerBook(data);
                    break;
                case 'A':
                    rentBook(data);
                    break;
                case 'D':
                    returnBook(data);
                    break;
                default:
                    break;
            }

        } catch(Exception e) {
            System.out.println("Server failed to read input");
            e.printStackTrace();
        }
    }

    private void registerBook(String data) {
        Book newBook = BookParser.toBook(data);

        String databaseString = scanFile();
        Book[] list = BookParser.toBookList(databaseString);

        Book[] newList = new Book[list.length + 1];
        for(int i = 0; i<list.length; i++) {
            newList[i] = list[i];
        }
        newList[list.length] = newBook;

        saveFile(newList);
    }

    private void rentBook(String data) {
        int index = Integer.parseInt(data);
        String databaseString = scanFile();

        Book[] list = BookParser.toBookList(databaseString);
        list[index].takeCopies(1);

        saveFile(list);
    }

    private void returnBook(String data) {
        int index = Integer.parseInt(data);
        String databaseString = scanFile();

        Book[] list = BookParser.toBookList(databaseString);
        list[index].returnCopies(1);

        saveFile(list);
    }

    private void sendData() {
        String data = scanFile();
        byte[] bytes = data.getBytes();
        try {
            out.write(bytes);
            out.flush();
        } catch (Exception e) {
            System.out.println("Server failed to send data");
        }
    }

    private String scanFile() {
        String databaseString = null;

        try {
            Scanner scanner = new Scanner(database);

            databaseString = "";
            while(scanner.hasNextLine()) {
                databaseString += scanner.nextLine();
            }
            
            scanner.close();
        } catch(Exception e) {
            System.out.println("Failed to read livros.json file");
            e.printStackTrace();
        }

        return databaseString;
    }

    private void saveFile(Book[] list) {
        String newData = BookParser.toString(list);
        database.delete();
        database = new File(filename);
        try {
            database.createNewFile();
            FileWriter writer = new FileWriter(filename);
            writer.write(newData);
            writer.close();
        } catch (Exception e) {
            System.out.println("failed to save data");
        }
    }

    public void saveFromString(String data) {
        try {
            database.createNewFile();
            FileWriter writer = new FileWriter(filename);
            writer.write(data);
            writer.close();
        } catch (Exception e) {
            System.out.println("failed to save data");
        }
    }
}

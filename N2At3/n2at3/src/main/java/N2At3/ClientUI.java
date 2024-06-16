package N2At3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class ClientUI {
    private ClientService service;
    private Book[] booksCache;

    public ClientUI(ClientService service) {
        this.service = service;
    }

    public void start() {
        service.start();
        Book[] books = service.getAllBooks();
        booksCache = books;
        showBookList(books);
    }

    private void showBookList(Book[] books) {
        printSeparator();
        System.out.println("Digite o ID de um livro para alugar ou devolver");
        System.out.println("Digite N para registrar um novo livro");

        for(int i = 0; i < books.length; i++) {
            System.out.print("ID: ");
            System.out.print(i);
            System.out.print(" - ");
            print(books[i]);
        }

        String input = readString();

        switch (input) {
            case "N", "n":
                registerNewBook();
                break;
        
            default:
                int index = Integer.parseInt(input);
                showBookData(books[index], index);
                break;
        }
    }

    private void showBookData(Book book, int index) {
        printSeparator();
        print("O que quer fazer?");
        if(book.getNumCopies() > 0) {
            print("Alugar um exemplar (Digite A)");
        }
        print("Devolver um exemplar (Digite D)");
        print("Digite qualquer outra coisa para voltar ao menu");

        String input = readString();

        switch(input) {
            case "A", "a":
                rentBook(index);
                break;
            case "D", "d":
                returnBook(index);
                break;
            default:
                showBookList(booksCache);
                break;
        }
    }

    private void registerNewBook() {
        print("Digite o autor: ");
        String author = readString();
        print("Digite o titulo da obra: ");
        String title = readString();
        print("Digite o genero: ");
        String theme = readString();
        print("Digite o numero de exemplares disponiveis: ");
        int numCopies = readInt();

        Book newBook = new Book(author, title, theme, numCopies);

        service.registerNewBook(newBook);
    }

    private void rentBook(int index) {
        service.rent(index);
    }

    private void returnBook(int index) {
        service.returnBook(index);
    }

    private int readInt() {
        Scanner input = new Scanner(System.in);
        int num = input.nextInt();
        input.close();

        return num;
    }

    private String readString() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            String line = reader.readLine();
            return line;
        } catch(Exception e) {
            System.out.println("Client failed to read line");
            e.printStackTrace();
        }
        return null;
    }

    public void print(Book book) {
        System.out.print(book.getAuthor() + ", " + book.getTitle() + " || " + book.getTheme());

        if(book.getNumCopies() <= 0) {
            System.out.print(" -- INSIPONIVEL --");
        }

        System.out.println();
    }

    private void print(String string) {
        System.out.println(string);
    }

    private void printSeparator() {
        System.out.println("==================================================");
    }
}

package LibraryManagement;

import Models.Book;
import PatronModels.Patron;
import Builders.BookBuilder;
import Builders.PatronBuilder;
import java.util.Date;
import java.util.Scanner;

public class LibraryMenuHandeling {
    private final Library library;
    private final Scanner sc = new Scanner(System.in);
    private PatronBuilder patronBuilder = new PatronBuilder();
    private BookBuilder bookBuilder = new BookBuilder();

    public LibraryMenuHandeling(Library library) {
        this.library = library;
    }

    //Starter Code which we will trigger from Main
    public void start() {
        boolean running = true;
        while (running) {
            printMenu();
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1": handleAddBook(); break;
                case "2": handleRemoveBook(); break;
                case "3": handleUpdateBook(); break;
                case "4": handleRegisterPatron(); break;
                case "5": handleRemovePatron(); break;
                case "6": handleUpdatePatron(); break;
                case "7": handleSearchBook(); break;
                case "8": handleCheckout(); break;
                case "9": handleReturn(); break;
                case "10": library.displayPatronsDetails(); break;
                case "11": library.displayBooksDetails(); break;
                case "0": running = confirmExit(); break;
                default: System.out.println("Invalid option. Try again.");
            }
            System.out.println();
        }
        sc.close();
    }

    // Displaying the menu options
    private void printMenu() {
        System.out.println("Library Management System by Rakesh Vinay");
        System.out.println("<-------------------------->");
        System.out.println("1. Add Book");
        System.out.println("2. Remove Book");
        System.out.println("3. Update Book");
        System.out.println("4. Register Patron");
        System.out.println("5. Remove Patron");
        System.out.println("6. Update Patron");
        System.out.println("7. Search Book");
        System.out.println("8. Checkout Book");
        System.out.println("9. Return Book");
        System.out.println("10. Display Patron Details");
        System.out.println("11. Display All Books");
        System.out.println("0. Exit");
        System.out.print("Select an option: ");
    }

    //Adding a new book
    private void handleAddBook() {
        try {
            System.out.print("Title: ");
            String title = sc.nextLine().trim();
            System.out.print("Author: ");
            String author = sc.nextLine().trim();
            System.out.print("ISBN: ");
            String isbn = sc.nextLine().trim();
            System.out.print("Publication: ");
            String pub = sc.nextLine().trim();
            System.out.print("Pages: ");
            int pages = Integer.parseInt(sc.nextLine().trim());

            //using a Book Builder to simplify things and avoid verbose terms
            bookBuilder.title(title).author(author).isbn(isbn).publication(pub).pages(pages);
            Book builtBook = bookBuilder.build();

            //Adding to books inventory
            boolean ok = library.addBook(builtBook);

            System.out.println(ok ? "Book added." : "Failed to add book (maybe ISBN exists).");
        }
        catch (Exception e) {
            System.out.println("Error adding book: All fields are required and pages must be > 0." + e.getMessage());
        }
    }

    // Removing a book by ISBN
    private void handleRemoveBook() {
        System.out.print("ISBN to remove: ");
        String isbn = sc.nextLine().trim();
        if (isbn.isEmpty()) {
            System.out.println("ISBN required to remove.");
            return;
        }
        System.out.print("Confirm remove book with ISBN " + isbn + " (y/N): ");
        String confirm = sc.nextLine().trim().toLowerCase();
        if (!confirm.equals("y") && !confirm.equals("yes")) {
            System.out.println("Cancelled.");
            return;
        }
        boolean ok = library.removeBook(isbn);
        System.out.println(ok ? "Book removed." : "Book not found / remove failed.");
    }

    private void handleUpdateBook() {
        System.out.print("Enter the Books ISBN you want to update: ");
        String isbn = sc.nextLine().trim();
        System.out.print("Title: ");
        String title = sc.nextLine().trim();
        System.out.print("Author: ");
        String author = sc.nextLine().trim();
        System.out.print("Publication: ");
        String pub = sc.nextLine().trim();
        System.out.print("Pages: ");
        int pages = Integer.parseInt(sc.nextLine().trim());

        bookBuilder.title(title).author(author).isbn(isbn).publication(pub).pages(pages);
        Book builtBook = bookBuilder.build();

        boolean ok = library.updateBook(builtBook);
        System.out.println(ok ? "Book updated successfully" : "Failed to update book (maybe ISBN not found).");
    }

    // Adding a new patron
    private void handleRegisterPatron() {
        try {
            System.out.print("Patron ID (numeric): ");
            long id = Long.parseLong(sc.nextLine().trim());
            System.out.print("Name: ");
            String name = sc.nextLine().trim();

            patronBuilder.id(id).name(name).memberSince(new Date());
            Patron patron = patronBuilder.build();
            library.registerPatron(patron);
            System.out.println("Patron registered successfully: " + id);
        }
        catch (Exception e) {
            System.out.println("Invalid input. Patron not registered." + e.getMessage());
        }
    }

    // Removing a patron by ID
    private void handleRemovePatron() {
        try {
            System.out.print("Enter the Patron ID you want to remove(numeric): ");
            long id = Long.parseLong(sc.nextLine().trim());
            library.removePatron(id);
        }
        catch (Exception e) {
            System.out.println("Invalid input. Patron not registered." + e.getMessage());
        }
    }

    //Updating a patron's name by ID
    private void handleUpdatePatron() {
        System.out.print("Enter the Patron ID you want to Update: ");
        long id = Long.parseLong(sc.nextLine().trim());
        Patron p = library.findPatron(id);
        if (p == null) {
            System.out.println("Sorry, Patron not found to update: " + id);
            return;
        }
        try {
            System.out.print("<-------- Enter the new details to update (Only Name is Updatable): -------->\n");
            System.out.print("Name: ");
            String name = sc.nextLine().trim();
            patronBuilder.id(id).name(name).memberSince(new Date());
            Patron patron = patronBuilder.build();
            library.updatePatron(patron);
        }
        catch (Exception e) {
            System.out.println("Invalid input. Patron not updated." + e.getMessage());
        }
    }

    // Searching for a book by ISBN, title, or author depending on user choice
    private void handleSearchBook() {
        System.out.println("Search by: 1) ISBN  2) Title  3) Author");
        String opt = sc.nextLine().trim();
        if ("1".equals(opt)) {
            System.out.print("ISBN: ");
            String isbn = sc.nextLine().trim();
            Book book = library.getBookByIsbn(isbn); // returns Book or null
            if (book != null) {
                System.out.println("Found: " + book.getTitle() + " | Author: " + book.getAuthor() + " | copies=" + book.getTotalCopies());
            } else {
                System.out.println("Book not found.");
            }
        } else if ("2".equals(opt)) {
            System.out.print("Title keyword: ");
            String title = sc.nextLine().trim();
            if(title.isEmpty()){
                System.out.println("Title cannot be empty.");
                return;
            }
            Book book = library.getBookByTitle(title); // your getBookByTitle may return first match
            if (book != null) {
                System.out.println("Found: " + book.getTitle() + " | Author: " + book.getAuthor() + " | copies=" + book.getTotalCopies());
            } else {
                System.out.println("No matches found.");
            }
        } else if ("3".equals(opt)) {
            System.out.print("Author keyword: ");
            String author = sc.nextLine().trim();
            Book book = library.getBookByAuthor(author);
            if (book != null) {
                System.out.println("Found: " + book.getTitle() + " | Author: " + book.getAuthor() + " | copies=" + book.getTotalCopies());
            } else {
                System.out.println("No matches found.");
            }
        } else {
            System.out.println("Invalid option.");
        }
    }

    //We will handle the checkout process here
    private void handleCheckout() {
        try {
            System.out.print("ISBN to checkout: ");
            String isbn = sc.nextLine().trim();
            System.out.print("Patron ID: ");
            Long id = Long.parseLong(sc.nextLine().trim());
            library.borrowBook(isbn, id); // Library prints outcome
        } catch (NumberFormatException nfe) {
            System.out.println("Invalid patron id.");
        } catch (Exception e) {
            System.out.println("Error during checkout: " + e.getMessage());
        }
    }

    //We will handle the return process here
    private void handleReturn() {
        try {
            System.out.print("ISBN to return: ");
            String isbn = sc.nextLine().trim();
            System.out.print("Patron ID: ");
            Long id = Long.parseLong(sc.nextLine().trim());
            library.returnBook(isbn, id);
        } catch (NumberFormatException nfe) {
            System.out.println("Invalid patron id.");
        } catch (Exception e) {
            System.out.println("Error during return: " + e.getMessage());
        }
    }

    // Confirm before exiting the application
    private boolean confirmExit() {
        System.out.print("Are you sure you want to exit? (y/N): ");
        String ans = sc.nextLine().trim().toLowerCase();
        return !ans.equals("y") && !ans.equals("yes"); // stop running
    }
}

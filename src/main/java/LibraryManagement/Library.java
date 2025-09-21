package LibraryManagement;

import Models.Book;
import PatronModels.Patron;
import Services.ICheckoutService;
import Services.IInventoryService;
import Services.IMemberService;

/**
 * Responsibilities:
 *  - expose high-level operations: add book, register patron, borrow, return
 *  - delegate orchestration to ICheckoutService
 *  - handle user-facing messages (printing) or convert results to UI-friendly responses
 *  - SOLID principles have been thoroughly followed
 *  - depends only on interfaces (DIP)
 *  - Follow Law of Demeter / principle of Least knowledge
 *  - Does NOT manage state directly (no collections of books/patrons)
 **/

public class Library {

    private final IInventoryService inventoryService;
    private final IMemberService memberService;
    private final ICheckoutService checkoutService;

    public Library(IInventoryService inventoryService, IMemberService memberService, ICheckoutService checkoutService) {
        this.inventoryService = inventoryService;
        this.memberService = memberService;
        this.checkoutService = checkoutService;
    }

    // --- Book management (delegates to inventory) ---
    public boolean addBook(Book book) {
        return inventoryService.addItem(book);
    }

    public boolean removeBook(String isbn) {
        return inventoryService.removeItemByIsbn(isbn);
    }

    public boolean updateBook(Book book) {
        return inventoryService.updateItem(book);
    }

    public Book getBookByIsbn(String isbn) {
        return inventoryService.getItemByIsbn(isbn);
    }

    public Book getBookByTitle(String title) {
        return inventoryService.getItemByTitle(title);
    }

    public Book getBookByAuthor(String author) {
        return inventoryService.getItemByAuthor(author);
    }

    // --- Patron management (delegates to member service) ---
    public void registerPatron(Patron patron) {
        memberService.addMember(patron);
    }

    public void removePatron(Long patronId) {
        memberService.removeMember(patronId);
    }

    public void updatePatron(Patron patron) {
        memberService.updateMember(patron);
    }

    public Patron findPatron(Long patronId) {
        return memberService.getById(patronId);
    }

    // --- Borrow flow: delegate to checkoutService and print outcome ---
    public void borrowBook(String isbn, Long patronId) {
        boolean ok = checkoutService.canBorrowBook(isbn, patronId);
        if (ok) {
            if(inventoryService.isItemAvailableByIsbn(isbn)){
                System.out.println("Book borrowed successfully for patron " + patronId + " (ISBN: " + isbn + ").");
                var member = memberService.getById(patronId);
                member.borrowBook(isbn);
            }
            else {
                System.out.println("Borrow failed: book not available in inventory.");
            }

        } else {
            // Give message - Borrow failed
            System.out.println("Borrow failed: patron not eligible (limit/fines/membership).");
        }
    }

    // --- Return flow: delegate to checkoutService and print outcome ---
    public void returnBook(String isbn, Long patronId) {
        boolean ok = checkoutService.returnBook(isbn, patronId);
        if (ok) {
            System.out.println("Book returned successfully for patron " + patronId + " (ISBN: " + isbn + ").");
        } else {
            System.out.println("Return failed: patron did not have this book or inventory update failed.");
        }
    }

    //print patron status (borrowed books) (If required we can use this to print individual patron details in future)
    public void printPatronStatus(Long patronId) {
        var patron = memberService.getById(patronId);
        if (patron == null) {
            System.out.println("Patron not found: " + patronId);
            return;
        }
        var p  = memberService.getById(patronId);
        System.out.println("Patron " + p.getName() + " (id=" + p.getPatronId() + ") borrowed: " + p.getBorrowedBooks());
    }

    //Display all patrons details
    public void displayPatronsDetails() {
        System.out.println("<---------- Patrons Details: ----------->");

        if(memberService.listAllPatrons().isEmpty()){
            System.out.println("No Patrons are registered yet.");
            return;
        }
        int i = 0;
        for (Patron p : memberService.listAllPatrons()) {
            StringBuilder borrowedTitles = new StringBuilder();

            for (String isbn : p.getBorrowedBooks()) {
                Book book = inventoryService.getItemByIsbn(isbn);
                if (book != null) {
                    borrowedTitles.append(book.getTitle()).append(", ");
                }
            }

            String booksList = borrowedTitles.length() > 0
                    ? borrowedTitles.substring(0, borrowedTitles.length() - 2)
                    : "No books";

            System.out.println(i+1 + ". " + "Patron Id: " + p.getPatronId() + " |<------->| Patron Name: " + p.getName() + " |<------->| Books Borrowed: " + booksList);
            i++;
        }
    }

    //Display all books details
    public void displayBooksDetails() {
        System.out.println("<---------- Books Details: ----------->");
        inventoryService.displayAllItems();
    }
}

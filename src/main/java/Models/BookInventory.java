package Models;

import Services.IInventoryService;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BookInventory implements IInventoryService, Serializable {

    // Use ConcurrentHashMap for safety; HashMap would be okay for single-threaded
    private final Map<String, Book> books = new ConcurrentHashMap<>();

    @Override
    public boolean addItem(Book book) {
        if (book == null) return false;
        String isbn = book.getIsbn();
        // If book exists -> increment existing's count
        Book existing = books.get(isbn);
        if (existing != null) {
            existing.incrementCopies(1);
            return true;
        } else {
            // ensure at least 1 copy if caller passed 0
            if (book.getTotalCopies() <= 0) book.setCopies(1);
            books.put(isbn, book);
            return true;
        }
    }

    @Override
    public boolean removeAllItems(Book book) {
        if (book == null) return false;
        return books.remove(book.getIsbn()) != null;
    }

    @Override
    public void displayAllItems() {
        int i =0;
        if (books.isEmpty()) {
            System.out.println("No books in inventory.");
            return;
        }
        for (Book book : books.values()) {
            System.out.println(i+1+"." + " Book: "+ book.getTitle() + "<--------->" + "," + "Author: " + book.getAuthor() + "," + "<--------->" + "ISBN: " + book.getIsbn() + "<--------->" + " Publication: " + book.getPublication() + ", Pages: " + book.getTotalNoOfPages() + ", Copies: " + book.getTotalCopies());
            i++;
        }
    }

    @Override
    public boolean removeItem(Book book) {
        if (book == null) return false;
        Book existing = books.get(book.getIsbn());
        if (existing == null) return false;
        if (existing.getTotalCopies() > 1) {
            existing.decrementCopies(1);
            return true;
        } else {
            // last copy -> remove the book entirely
            books.remove(book.getIsbn());
            return true;
        }
    }

    @Override
    public boolean removeItemByIsbn(String isbn) {
        if (isbn == null) return false;
        Book existing = books.get(isbn);
        if (existing == null) return false;
        if (existing.getTotalCopies() > 1) {
            existing.decrementCopies(1);
            return true;
        } else {
            // last copy -> remove the book entirely
            books.remove(isbn);
            return true;
        }
    }

    @Override
    public Book getItemByIsbn(String isbn) {
        if( isbn == null) return null;
        String q = isbn.trim().toLowerCase().replaceAll("\\s+", "");
        for (Book book : books.values()) {
            if (book.getIsbn() != null) {
                String isbnFiltered = book.getIsbn().toLowerCase().replaceAll("\\s+", "");
                if (isbnFiltered.contains(q)) {
                    return book;
                }
            }
        }
        return null;
    }

    @Override
    public Book getItemByTitle(String title) {
        if (title == null) return null;
        String q = title.trim().toLowerCase().replaceAll("\\s+", ""); // Remove spaces to predict the book
        for (Book book : books.values()) {
            if (book.getTitle() != null) {
                String titleWithoutSpaces = book.getTitle().toLowerCase().replaceAll("\\s+", "");
                if (titleWithoutSpaces.contains(q)) {
                    return book;
                }
            }
        }
        return null;
    }

    @Override
    public Book getItemByAuthor(String author) {
        if (author == null) return null;
        String q = author.trim().toLowerCase().replaceAll("\\s+", ""); // Remove spaces to predict the book
        for (Book book : books.values()) {
            if(book.getAuthor() != null) {
                String authorWithoutSpaces = book.getAuthor().toLowerCase().replaceAll("\\s+", "");
                if (authorWithoutSpaces.contains(q)) {
                    return book;
                }
            }
        }
        return null;
    }

    @Override
    public boolean updateItem(Book book) {
        if (book == null) return false;
        // replace metadata but preserve copies count if desired:
        Book existing = books.get(book.getIsbn());
        if (existing != null) {
            // update fields (preserve copies)
            existing.setTitle(book.getTitle());
            existing.setAuthor(book.getAuthor());
            existing.setPublication(book.getPublication());
            return true;
        } else {
            // if not exists, add it
            books.put(book.getIsbn(), book);
            return false;
        }
    }

    @Override
    public boolean isItemAvailableByIsbn(String isbn) {
        Book b = books.get(isbn);
        return b != null && b.getTotalCopies() > 0;
    }

    @Override
    public boolean isItemAvailableByTitle(String title) {
        if (title == null) return false;
        String q = title.trim().toLowerCase();
        for (Book book : books.values()) {
            if (book.getTitle() != null && book.getTitle().toLowerCase().contains(q)) {
                return book.getTotalCopies() > 0;
            }
        }
        return false;
    }

    @Override
    public boolean isItemAvailableByAuthor(String author) {
        if (author == null) return false;
        String q = author.trim().toLowerCase();
        for (Book book : books.values()) {
            if (book.getAuthor() != null && book.getAuthor().toLowerCase().contains(q)) {
                return book.getTotalCopies() > 0;
            }
        }
        return false;
    }

    // Helpful extras you can add:
    public Book getBookByIsbn(String isbn) { return books.get(isbn); }
    public Map<String, Book> getAllBooks() { return Map.copyOf(books); } // read-only view
}

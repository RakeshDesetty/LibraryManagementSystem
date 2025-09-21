package Services;

import Models.Book;
import java.util.List;
import java.util.Optional;

public interface IInventoryService {
    boolean addItem(Book book);
    boolean removeAllItems(Book book); // removes all copies of the book (we can implement in future)
    boolean removeItem(Book book);
    boolean updateItem(Book book);
    void displayAllItems();
    Book getItemByIsbn(String isbn);
    Book getItemByTitle(String title);
    Book getItemByAuthor(String author);
    boolean removeItemByIsbn(String isbn);


    boolean isItemAvailableByIsbn(String isbn);
    boolean isItemAvailableByTitle(String title);
    boolean isItemAvailableByAuthor(String author);
}

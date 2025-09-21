package Builders;
import Models.Book;
import java.util.Objects;

public class BookBuilder {
    //Book Builder to make creation of books clutter free

    private String title;
    private String author;
    private String isbn;
    private String publication;
    private int pages = 0;
    private int copies = 250; // default


    public BookBuilder title(String title) { this.title = title; return this; }
    public BookBuilder author(String author) { this.author = author; return this; }
    public BookBuilder isbn(String isbn) { this.isbn = isbn; return this; }
    public BookBuilder publication(String publication) { this.publication = publication; return this; }
    public BookBuilder pages(int pages) { this.pages = pages; return this; }
    public BookBuilder copies(int copies) { this.copies = copies; return this; }

    // Validate and build a Book instance.
    public Book build() {
        // Basic validation
        Objects.requireNonNull(title, "title is required");
        Objects.requireNonNull(author, "author is required");
        Objects.requireNonNull(isbn, "isbn is required");
        if (pages <= 0) throw new IllegalStateException("pages must be > 0");
        if (copies <= 0) copies = 1;

        // Create book using your constructor
        Book b = new Book(title, author, isbn, publication, pages);

        return b;
    }

    @Override
    public String toString() {
        return "BookBuilder[" + title + ", " + author + ", " + isbn + ", pages=" + pages + ", copies=" + copies + "]";
    }
}

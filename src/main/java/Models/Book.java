package Models;

import java.util.Objects;

public class Book {
    // immutable fields of Book
    private final String isbn;
    private final int totalNoOfPages;
    private String title;
    private String author;
    private String publication;
    // totalCopies will be updated by Inventory; keep setter package-private or remove public setter
    private int totalCopies = 250;

    //Constructor of the Book
    public Book(String title, String author, String isbn, String publication, int totalNoOfPages) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publication = publication;
        this.totalNoOfPages = totalNoOfPages;
    }

    // getters
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getIsbn() { return isbn; }
    public String getPublication() { return publication; }
    public int getTotalNoOfPages() { return totalNoOfPages; }
    public int getTotalCopies() { return totalCopies; }

    // setters for mutable metadata (not for isbn)
    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setPublication(String publication) { this.publication = publication; }

    // package-private methods for Inventory to change copies; avoids public mutation
    void incrementCopies(int n) { this.totalCopies += n; }
    void decrementCopies(int n) { this.totalCopies = Math.max(0, this.totalCopies - n); }
    void setCopies(int n) { this.totalCopies = Math.max(0, n); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book b = (Book) o;
        return Objects.equals(isbn, b.isbn);
    }

    //Additional Helper methods we can make use if we want
    @Override
    public int hashCode() { return Objects.hash(isbn); }

    @Override
    public String toString() {
        return String.format("Book[%s - %s by %s, copies=%d]", isbn, title, author, totalCopies);
    }
}

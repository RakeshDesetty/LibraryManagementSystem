package Builders;
import PatronModels.Patron;
import java.util.*;

public class PatronBuilder {
    //Patron Builder to make creation of patrons clutter free

    private Long id;
    private String name;
    private Date memberSince;
    private final Set<String> borrowedBooks = new HashSet<>();

    public PatronBuilder id(Long id) { this.id = id; return this; }
    public PatronBuilder name(String name) { this.name = name; return this; }
    public PatronBuilder memberSince(Date date) { this.memberSince = date; return this; }
    public PatronBuilder addBorrowedIsbn(String isbn) {
        if (isbn != null) borrowedBooks.add(isbn);
        return this;
    }
    public PatronBuilder borrowedIsbns(Set<String> isbns) {
        if (isbns != null) this.borrowedBooks.addAll(isbns);
        return this;
    }

    public Patron build() {
        Objects.requireNonNull(id, "patron id is required");
        Objects.requireNonNull(name, "patron name is required");
        Patron p = new Patron(id, name, memberSince);
        return p;
    }

    @Override
    public String toString() {
        return "PatronBuilder[id=" + id + ", name=" + name + ", memberSince=" + memberSince + ", borrowed=" + borrowedBooks + "]";
    }

}

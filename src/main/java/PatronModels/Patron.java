package PatronModels;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.Date;

public class Patron {

    // Class Fields
    private final Long patronId;
    private final int BorrowLimit = 15;
    private String name;
    private Date dateOfMembership;
    // initialize to new HashSet avoid NPEs
    private final Set<String> borrowedBooks = new HashSet<>();

    //Constructor of the Patron
    public Patron(Long patronId, String name, Date dateOfMembership) {
        this.patronId = Objects.requireNonNull(patronId, "patronId");
        this.name = name;
        this.dateOfMembership = dateOfMembership;
    }

    //Getters and Setters
    public Long getPatronId() { return patronId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Date getDateOfMembership() { return dateOfMembership; }
    public void setDateOfMembership(Date dateOfMembership) { this.dateOfMembership = dateOfMembership; }

    // Return an unmodifiable view so callers can't mutate internal set
    public Set<String> getBorrowedBooks() {
        return Collections.unmodifiableSet(borrowedBooks);
    }

    // Add single borrowed ISBN/copyId
    public boolean borrowBook(String isbnOrCopyId) {
        if (isbnOrCopyId == null) return false;
        return this.borrowedBooks.add(isbnOrCopyId);
    }

    // Remove when returned
    public boolean returnBook(String isbnOrCopyId) {
        if (isbnOrCopyId == null) return false;
        return this.borrowedBooks.remove(isbnOrCopyId);
    }

    // Utility: how many currently borrowed
    public int getBorrowedCount() { return borrowedBooks.size(); }

    // equality based on patronId (useful for collections)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Patron)) return false;
        Patron p = (Patron) o;
        return Objects.equals(patronId, p.patronId);
    }

    // Additional Helper methods to print Patron and get Hashcode for faster lookup
    @Override
    public int hashCode() {
        return Objects.hash(patronId);
    }

    @Override
    public String toString() {
        return "Patron{" + "id=" + patronId + ", name='" + name + '\'' + ", borrowed=" + borrowedBooks.size() + '}';
    }
}

package PatronModels;

import java.util.*;
import Services.IMemberService;

public class PatronManagement implements IMemberService {

    // Use a Map for fast lookup by patronId
    private final Map<Long, Patron> patronMap = new HashMap<>();

    //Add new Patron
    @Override
    public void addMember(Patron p) {
        if (p == null) return;
        if (patronMap.containsKey(p.getPatronId())) {
            System.out.println("Patron already exists: " + p.getPatronId());
            return;
        }
        patronMap.put(p.getPatronId(), p);
    }

    //Remove Patron on basis of ID
    @Override
    public void removeMember(Long patronId) {
        if (patronId == null) return;
        if (patronMap.get(patronId) == null) {
            System.out.println("Patron does not exist: " + patronId);
            return;
        }
        patronMap.remove(patronId);
        System.out.println("Patron removed successfully: " + patronId);
    }

    //Update Patron details
    @Override
    public void updateMember(Patron p) {
        if (p == null) return;
        if (!patronMap.containsKey(p.getPatronId())) {
            System.out.println("Patron does not exist: " + p.getPatronId());
            return;
        }
        // replace stored patron metadata (we keep same id)
        patronMap.put(p.getPatronId(), p);
    }

    //Check whether patron is eligible to borrow a book
    @Override
    public boolean isEligibleForBookCheckout(String Isbn, Long Id) {
        if (Isbn == null && Id == null) return false;
        // NOTE: This checks whether ANY patron has the ISBN borrowed.
        // It's better to check eligibility per-patron (see suggested API below).
        for (Patron p : patronMap.values()) {
            if (Objects.equals(p.getPatronId(), Id) && p.getBorrowedBooks().contains(Isbn)) {
                return false; // someone already has it (business rule you had)
            }
        }
         return true;
    }

    @Override
    public Patron getById(Long id) {
        return patronMap.get(id);
    }

    public Collection<Patron> listAllPatrons() {
        return Collections.unmodifiableCollection(patronMap.values());
    }

    // Additional helper methods to manage Borrowed books if we want
    public boolean addBorrowedToPatron(Long patronId, String isbnOrCopyId) {
        Patron p = patronMap.get(patronId);
        if (p == null) return false;
        return p.borrowBook(isbnOrCopyId);
    }

    public boolean removeBorrowedFromPatron(Long patronId, String isbnOrCopyId) {
        Patron p = patronMap.get(patronId);
        if (p == null) return false;
        return p.returnBook(isbnOrCopyId);
    }
}

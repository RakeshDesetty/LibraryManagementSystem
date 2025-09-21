package CheckoutModels;

import Services.ICheckoutService;
import Services.IInventoryService;
import Services.IMemberService;

import java.util.Optional;

/**
 * Checkout (Loan) service — orchestrates borrow/return operations.
 * - Depends only on interfaces (DIP).
 * - Performs atomic-ish reservation (decrement then member update).
 * - Rolls back inventory change if member update fails.
 */
public class Checkout implements ICheckoutService {

    private final IInventoryService inventoryService;
    private final IMemberService memberService;

    public Checkout(IInventoryService inventoryService, IMemberService memberService) {
        this.inventoryService = inventoryService;
        this.memberService = memberService;
    }

    /**
     * Attempt to borrow a copy identified by ISBN for patronId.
     * Returns true on success; false on any failure.
     */
    // LAW OF DEMETER
    @Override
    public boolean canBorrowBook(String isbn, Long patronId) {
        // This decides whether to give the book and delegates to Library to take action
        if (isbn == null || isbn.isBlank() || patronId == null) return false;
        return memberService.isEligibleForBookCheckout(isbn, patronId);
    }

    /**
     * Return a borrowed copy (by ISBN) for the given patron.
     * Returns true on success.
     */
    @Override
    public boolean returnBook(String isbn, Long patronId) {
        if (isbn == null || isbn.isBlank() || patronId == null) return false;

        // 1) Remove borrowed entry from patron
        boolean removed = memberService.getById(patronId).returnBook(isbn);
        if (!removed) return false; // patron didn't have this book

        // 2) Increment inventory copies
        var bookToAdd = inventoryService.getItemByIsbn(isbn);
        boolean incremented = inventoryService.addItem(bookToAdd);
        if (!incremented) {
            // best-effort rollback: re-add to patron to restore consistency
            memberService.getById(patronId).borrowBook(isbn);
            return false;
        }
        return true;
    }

    @Override
    public boolean isPatronEligible(String isbn, Long patronId) {
        if (isbn == null || isbn.isBlank() || patronId == null) return false;
        if (!memberService.isEligibleForBookCheckout(isbn, patronId)) return false;
        return inventoryService.isItemAvailableByIsbn(isbn);
    }
}

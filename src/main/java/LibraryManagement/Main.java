package LibraryManagement;

import CheckoutModels.Checkout;
import Models.BookInventory;
import PatronModels.PatronManagement;
import Services.ICheckoutService;
import Services.IInventoryService;
import Services.IMemberService;

public class Main {

    public static void main(String[] args) {
        // composition / wiring of things together and creating an Library instance
        IInventoryService bookService = new BookInventory();
        IMemberService memberService = new PatronManagement();
        ICheckoutService checkoutService = new Checkout(bookService, memberService);
        Library myLib = new Library(bookService, memberService, checkoutService);

        // seed test data simply where we are inserting 20 books and 20 patrons
        AddTestData.seedBooks(myLib);
        AddTestData.seedPatrons(myLib);

        //Abstracted library menu handeler to simplify main and reduce clutter
        LibraryMenuHandeling Handler = new LibraryMenuHandeling(myLib);
        Handler.start();
    }
}

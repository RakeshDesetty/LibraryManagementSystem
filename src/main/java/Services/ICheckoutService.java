package Services;

public interface ICheckoutService {
    public boolean canBorrowBook(String isbn, Long id);

    public boolean returnBook(String isbn, Long id);

    public boolean isPatronEligible(String isbn, Long id);
}

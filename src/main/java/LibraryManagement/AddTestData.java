package LibraryManagement;

import Models.Book;
import PatronModels.Patron;
import java.util.Date;

public class AddTestData {
    // This is just a Helper class to add some test data
    // Comment this class in Main to add manual test data

    protected static void seedBooks(Library myLib) {
        Book[] books = {
                new Book("Monk Who Sold His Ferrari", "Robin Sharma", "1234567890", "Indian Publication", 300),
                new Book("Wings of Fire", "A.P.J Abdul Kalam", "0987654321", "Indian Publication", 250),
                new Book("Atomic Habits", "James Clear", "1122334455", "American Publication", 320),
                new Book("The Alchemist", "Paulo Coelho", "2233445566", "HarperCollins", 210),
                new Book("Ikigai", "Héctor García", "3344556677", "Penguin Random House", 180),
                new Book("The Power of Now", "Eckhart Tolle", "4455667788", "New World Library", 240),
                new Book("Deep Work", "Cal Newport", "5566778899", "Grand Central", 304),
                new Book("Clean Code", "Robert C. Martin", "6677889900", "Prentice Hall", 464),
                new Book("Design Patterns", "Erich Gamma", "7788990011", "Addison-Wesley", 395),
                new Book("Introduction to Algorithms", "Thomas H. Cormen", "8899001122", "MIT Press", 1312),
                new Book("Java: The Complete Reference", "Herbert Schildt", "9900112233", "McGraw Hill", 1248),
                new Book("Effective Java", "Joshua Bloch", "1011121314", "Addison-Wesley", 416),
                new Book("C++ Primer", "Stanley Lippman", "1213141516", "Pearson", 976),
                new Book("You Can Win", "Shiv Khera", "1314151617", "Macmillan", 320),
                new Book("Think and Grow Rich", "Napoleon Hill", "1415161718", "The Ralston Society", 238),
                new Book("Rich Dad Poor Dad", "Robert T. Kiyosaki", "1516171819", "Plata Publishing", 336),
                new Book("The Psychology of Money", "Morgan Housel", "1617181920", "Harriman House", 252),
                new Book("Sapiens: A Brief History of Humankind", "Yuval Noah Harari", "1718192021", "Harvill Secker", 498),
                new Book("Educated", "Tara Westover", "1819202122", "Random House", 334),
                new Book("Becoming", "Michelle Obama", "1920212223", "Crown Publishing", 448)
        };

        for (Book b : books) myLib.addBook(b);
    }

    protected static void seedPatrons(Library myLib) {
        Patron[] patrons = {
                new Patron(1L,  "John Doe",        new Date(120, 1, 15)),  // Feb 15, 2020
                new Patron(2L,  "Alice Smith",     new Date(121, 5, 10)),  // Jun 10, 2021
                new Patron(3L,  "Robert Brown",    new Date(119, 3, 5)),   // Apr 5, 2019
                new Patron(4L,  "Emily Davis",     new Date(118, 7, 25)),  // Aug 25, 2018
                new Patron(5L,  "Michael Wilson",  new Date(122, 2, 12)),  // Mar 12, 2022
                new Patron(6L,  "Sophia Taylor",   new Date(121, 9, 30)),  // Oct 30, 2021
                new Patron(7L,  "David Johnson",   new Date(120, 11, 8)),  // Dec 8, 2020
                new Patron(8L,  "Olivia Martinez", new Date(119, 0, 18)),  // Jan 18, 2019
                new Patron(9L,  "Daniel Garcia",   new Date(118, 4, 20)),  // May 20, 2018
                new Patron(10L, "Emma Anderson",   new Date(122, 6, 14)),  // Jul 14, 2022
                new Patron(11L, "James Thomas",    new Date(121, 8, 1)),   // Sep 1, 2021
                new Patron(12L, "Isabella Lee",    new Date(120, 10, 27)), // Nov 27, 2020
                new Patron(13L, "William Harris",  new Date(119, 2, 9)),   // Mar 9, 2019
                new Patron(14L, "Mia Clark",       new Date(118, 6, 3)),   // Jul 3, 2018
                new Patron(15L, "Ethan Lewis",     new Date(122, 0, 16)),  // Jan 16, 2022
                new Patron(16L, "Charlotte Young", new Date(121, 4, 22)),  // May 22, 2021
                new Patron(17L, "Benjamin Hall",   new Date(120, 7, 19)),  // Aug 19, 2020
                new Patron(18L, "Amelia Allen",    new Date(119, 9, 11)),  // Oct 11, 2019
                new Patron(19L, "Lucas Scott",     new Date(118, 11, 28)), // Dec 28, 2018
                new Patron(20L, "Harper King",     new Date(122, 3, 6))    // Apr 6, 2022
        };

        for (Patron p : patrons) myLib.registerPatron(p);
    }

}

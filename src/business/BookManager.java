package business;

import dao.BookDao;
import entitiy.Book;

public class BookManager {
    private final BookDao bookDao;

    public BookManager() {
        this.bookDao = new BookDao();
    }

    public boolean save(Book book) {
        return this.bookDao.save(book);
    }

}

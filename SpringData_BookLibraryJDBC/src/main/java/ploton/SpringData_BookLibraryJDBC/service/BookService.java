package ploton.SpringData_BookLibraryJDBC.service;

import org.springframework.stereotype.Service;
import ploton.SpringData_BookLibraryJDBC.entity.Book;
import ploton.SpringData_BookLibraryJDBC.repository.BookCrudRepository;

import java.util.List;
import java.util.Map;

@Service
public class BookService implements BookServiceable {
    private final BookCrudRepository bookRepository;

    public BookService(BookCrudRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Integer save(Book entity) {
        return bookRepository.save(entity);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book findById(Integer id) {
        Book book = bookRepository.findById(id);
        if (book == null) {
            try {
                throw new NoSuchFieldException("Book ID - " + id + " Not Found");
            } catch (NoSuchFieldException e) {
                return null;
            }
        }
        return book;
    }

    @Override
    public Integer updateById(Integer id, Map<String, Object> updates) {
        Book book = bookRepository.findById(id);
        return bookRepository.updateById(book, updates);
    }

    @Override
    public Integer deleteById(Integer id) {
        bookRepository.findById(id);
        return bookRepository.deleteById(id);
    }
}

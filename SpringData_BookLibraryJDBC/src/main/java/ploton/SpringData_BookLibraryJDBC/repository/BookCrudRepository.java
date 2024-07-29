package ploton.SpringData_BookLibraryJDBC.repository;

import ploton.SpringData_BookLibraryJDBC.entity.Book;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BookCrudRepository {
    Integer save(Book entity);

    List<Book> findAll();

    Book findById(Integer id);

    Integer updateById(Book entity, Map<String, Object> updates);

    Integer deleteById(Integer id);
}

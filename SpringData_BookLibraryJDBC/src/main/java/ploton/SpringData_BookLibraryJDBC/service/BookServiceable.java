package ploton.SpringData_BookLibraryJDBC.service;

import ploton.SpringData_BookLibraryJDBC.entity.Book;

import java.util.List;
import java.util.Map;

public interface BookServiceable {
    Integer save(Book entity);

    List<Book> findAll();

    Book findById(Integer id);

    Integer updateById(Integer id, Map<String, Object> updates);

    Integer deleteById(Integer id);
}

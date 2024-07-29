package ploton.SpringData_BookLibraryJDBC.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ploton.SpringData_BookLibraryJDBC.entity.Book;
import ploton.SpringData_BookLibraryJDBC.service.BookServiceable;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {
    private final BookServiceable bookService;

    public BookController(BookServiceable bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }

    @PostMapping("/")
    public ResponseEntity<Integer> save(@RequestBody Book entity) {
        return new ResponseEntity<>(bookService.save(entity), HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> findById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(bookService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<Book>> findAll() {
        return new ResponseEntity<>(bookService.findAll(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(bookService.deleteById(id), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Integer> updateById(@PathVariable("id") Integer id,
                                              @RequestBody Map<String, Object> updates) {
        return new ResponseEntity<>(bookService.updateById(id, updates), HttpStatus.OK);
    }

}

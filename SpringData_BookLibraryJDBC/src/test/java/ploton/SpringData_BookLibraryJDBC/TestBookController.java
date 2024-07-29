package ploton.SpringData_BookLibraryJDBC;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ploton.SpringData_BookLibraryJDBC.controller.BookController;
import ploton.SpringData_BookLibraryJDBC.entity.Book;
import ploton.SpringData_BookLibraryJDBC.service.BookServiceable;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
public class TestBookController {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    BookServiceable bookService;
    Book book;
    Map<String, Object> updates;
    ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();

        book = new Book();
        book.setId(1);
        book.setTitle("Book Part I");
        book.setAuthor("Ivanov Ivan");
        book.setPublicationYear(1937);
        updates = Map.of(
                "publicationYear", 999,
                "author", "Updated Author");
    }

    @Test
    void save_CorrectInput_Integer() throws Exception {
        when(bookService.save(any(Book.class))).thenReturn(book.getId());

        mockMvc.perform(post("/api/v1/books/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(book)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$").isNumber())
                .andExpect(jsonPath("$").value(book.getId()));
    }

    @Test
    void save_IncorrectInput_IllegalArgumentException() throws Exception {
        when(bookService.save(any(Book.class))).thenThrow(new IllegalArgumentException(
                "ValidateEntityError. JsonParsing Error"
        ));

        mockMvc.perform(post("/api/v1/books/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"i\":1,\n" +
                                "    \"t\":\"Book Part I\",\n" +
                                "    \"a\": \"Ivanov Ivan\",\n" +
                                "    \"p\": 1982\n" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findAll_Correct_ListOfBooks() throws Exception {
        when(bookService.findAll()).thenReturn(List.of(book));

        mockMvc.perform(get("/api/v1/books/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(book.getId()))
                .andExpect(jsonPath("$.[0].title").value(book.getTitle()))
                .andExpect(jsonPath("$.[0].author").value(book.getAuthor()))
                .andExpect(jsonPath("$.[0].publicationYear").value(book.getPublicationYear()));
    }

    @Test
    void findById_CorrectInput_Integer() throws Exception {
        when(bookService.findById(anyInt())).thenReturn(book);

        mockMvc.perform(get("/api/v1/books/{1}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(book.getId()))
                .andExpect(jsonPath("$.title").value(book.getTitle()))
                .andExpect(jsonPath("$.author").value(book.getAuthor()))
                .andExpect(jsonPath("$.publicationYear").value(book.getPublicationYear()));
    }

    @Test
    void findById_IncorrectInput_NoSuchElementException() throws Exception {
        when(bookService.findById(anyInt())).thenThrow(new NoSuchElementException("Book ID - " + 1));

        mockMvc.perform(get("/api/v1/books/{1}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteById_CorrectInput_Integer() throws Exception {
        when(bookService.deleteById(anyInt())).thenReturn(1);

        mockMvc.perform(delete("/api/v1/books/{1}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(1));
    }

    @Test
    void deleteById_IncorrectInput_NoSuchElementException() throws Exception {
        when(bookService.deleteById(anyInt())).thenThrow(new NoSuchElementException("Book ID - " + 1));

        mockMvc.perform(delete("/api/v1/books/{1}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateById_CorrectInput_Integer() throws Exception {
        Book updatedBook = book;
        updatedBook.setId(99);
        updatedBook.setAuthor((String) updates.get("author"));
        updatedBook.setPublicationYear((Integer) updates.get("publicationYear"));

        when(bookService.updateById(anyInt(), anyMap())).thenReturn(updatedBook.getId());

        mockMvc.perform(patch("/api/v1/books/{1}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 99, \"author\": \"Updated Author\", \"publicationYear\": 999}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(updatedBook.getId()));
    }

    @Test
    void updateById_IncorrectInput_IllegalArgumentException() throws Exception {
        when(bookService.updateById(anyInt(), anyMap()))
                .thenThrow(new IllegalArgumentException("Updates Validation Error"));

        mockMvc.perform(patch("/api/v1/books/{1}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"i\": 1, \"a\": \"Updated Author\", \"p\": 999}"))
                .andExpect(status().isBadRequest());
    }
}

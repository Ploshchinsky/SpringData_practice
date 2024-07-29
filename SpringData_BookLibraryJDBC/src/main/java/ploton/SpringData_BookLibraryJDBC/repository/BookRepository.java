package ploton.SpringData_BookLibraryJDBC.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ploton.SpringData_BookLibraryJDBC.entity.Book;

import java.util.*;

@Repository
public class BookRepository implements BookCrudRepository {
    private final NamedParameterJdbcTemplate jdbc;

    public BookRepository(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Integer save(Book entity) {
        validateEntity(entity);
        SqlParameterSource params = new BeanPropertySqlParameterSource(entity);
        String sqlSaveEntity =
                "insert into books (id, title, author, publication_year) " +
                        "values (:id, :title, :author, :publicationYear)";
        return jdbc.update(sqlSaveEntity, params);
    }

    @Override
    public List<Book> findAll() {
        String sqlFindAll = "select * from books";
        return jdbc.query(sqlFindAll, new BookMapper());
    }

    @Override
    public Book findById(Integer id) {
        String sqlFindById = "select * from books where id = :id";
        Map<String, Object> params = Collections.singletonMap("id", id);
        try {
            return jdbc.queryForObject(sqlFindById, params, new BookMapper());
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public Integer updateById(Book entity, Map<String, Object> updates) {
        StringBuilder sqlUpdateById = getSqlString(entity, updates);
        return jdbc.update(sqlUpdateById.toString(), updates);
    }

    public static StringBuilder getSqlString(Book entity, Map<String, Object> updates) {
        StringBuilder sqlUpdateById = new StringBuilder("update books set ");
        Iterator<Map.Entry<String, Object>> iterator = updates.entrySet().iterator();
        fieldsValidate(entity, updates);
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            if (!entry.getKey().equals("id")) {
                sqlUpdateById
                        .append(entry.getKey().replace("publicationYear", "publication_year"))
                        .append(" = :").append(entry.getKey());
            }
            if (iterator.hasNext()) {
                sqlUpdateById.append(" ,");
            } else {
                sqlUpdateById.append(" where id = :id");
                updates.put("id", entity.getId());
            }
        }
        return sqlUpdateById;
    }

    public static void validateEntity(Book entity) {
        try {
            new ObjectMapper().writeValueAsString(entity);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("ValidateEntityError. JsonParsing Error - " + e.toString());
        }
    }

    public static void fieldsValidate(Book entity, Map<String, Object> updates) {
        updates.forEach((key, value) -> {
            switch (key) {
                case "id":
                    if (value instanceof Integer)
                        entity.setId((Integer) value);
                    break;
                case "title":
                    if (value instanceof String)
                        entity.setTitle((String) value);
                    break;
                case "author":
                    if (value instanceof String)
                        entity.setAuthor((String) value);
                    break;
                case "publicationYear":
                    if (value instanceof Integer)
                        entity.setPublicationYear((Integer) value);
                    break;
                default:
                    throw new IllegalArgumentException("Update: Wrong Book Field [" + key + " - " + value + "]");
            }
        });
    }

    @Override
    public Integer deleteById(Integer id) {
        String sqlDeleteById = "delete from books where id=:id";
        Map<String, Object> params = Collections.singletonMap("id", id);
        return jdbc.update(sqlDeleteById, params);
    }
}

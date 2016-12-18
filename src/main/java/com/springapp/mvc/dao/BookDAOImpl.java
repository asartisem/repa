package com.springapp.mvc.dao;

import com.springapp.mvc.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BookDAOImpl implements BookDAO{
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addBook(Book book) {
        String sql = "INSERT INTO books(isn ,name, author) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, book.getIsn(), book.getBookName(), book.getBookAuthor());
    }

    public void updateBook(Book book) {
        String sql = "UPDATE books SET name = ?, author = ? WHERE isn = ?";
        jdbcTemplate.update(sql, new Object[] {book.getBookName(), book.getBookAuthor(), book.getIsn()});
    }

    public void takeBook(String userName, int isn) {
        String sql = "INSERT INTO book_to_user (username, book_isn) VALUES (?, ?)";
        jdbcTemplate.update(sql, userName, isn);
    }

    public void deleteBook(int isn) {
        String sql = "DELETE FROM books WHERE isn = ?";
        jdbcTemplate.update(sql, isn);
    }

    public void returnBook(int isn) {
        String sql = "DELETE FROM book_to_user WHERE book_isn = ?";
        jdbcTemplate.update(sql, isn);
    }

    public List<Book> getListBooks(int limit, int offset) {
        String sql = "SELECT * FROM books " +
                "LEFT JOIN book_to_user " +
                "ON books.isn = book_to_user.book_isn " +
                " LIMIT " + limit + " OFFSET " + offset;
//                + limit + " offset " + offset;
        return jdbcTemplate.query(sql, new BookRowMapper());
    }


    private final static class BookRowMapper implements RowMapper<Book> {

        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            Book book = new Book();
            book.setIsn(resultSet.getInt("isn"));
            book.setBookName(resultSet.getString("name"));
            book.setBookAuthor(resultSet.getString("author"));
            book.setUserName(resultSet.getString("username"));
            return book;
        }
    }
}

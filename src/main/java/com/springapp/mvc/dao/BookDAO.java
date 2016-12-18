package com.springapp.mvc.dao;

import com.springapp.mvc.model.Book;
import org.springframework.context.annotation.Bean;

import java.util.List;

public interface BookDAO {
    void addBook (Book book);

    void updateBook (Book book);

    void takeBook (String userName, int isn);

    void returnBook (int isn);

    void deleteBook(int isn);

    List<Book> getListBooks(int limit, int offset);
}

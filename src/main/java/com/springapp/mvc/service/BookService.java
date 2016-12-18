package com.springapp.mvc.service;

import com.springapp.mvc.model.Book;

import java.util.List;

public interface BookService {
    void addBook (Book boook);

    void updateBook(Book book);

    void takeBook(String userName, int isn);

    void returnBook (int isn);

    void deleteBook(int isn);

    List<Book> listBooks(int limit, int offset);
}

package com.springapp.mvc.service;

import com.springapp.mvc.dao.BookDAO;
import com.springapp.mvc.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDAO bookDAO;

    public void setBookDAO(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Override
    @Transactional
    public void addBook(Book boook) {
        this.bookDAO.addBook(boook);
    }

    @Override
    public Book getBookByIsn(int isn) {
        return bookDAO.getBookByIsn(isn);
    }

    @Override
    @Transactional
    public String getHandlerByIsn(int isn) {
        return this.bookDAO.getHandlerByIsn(isn);
    }

    @Override
    @Transactional
    public void updateBook(Book book) {
        this.bookDAO.updateBook(book);
    }

    @Override
    @Transactional
    public void takeBook(String userName, int isn) {
        this.bookDAO.takeBook(userName, isn);
    }

    @Override
    @Transactional
    public void returnBook(int isn) {
        this.bookDAO.returnBook(isn);
    }

    @Override
    public void deleteBook(int isn) {
        this.bookDAO.deleteBook(isn);
    }

    @Override
    @Transactional
    public List<Book> listBooks(int limit, int offset) {
        return this.bookDAO.getListBooks(limit, offset);
    }
}

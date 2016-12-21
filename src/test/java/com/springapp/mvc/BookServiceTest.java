package com.springapp.mvc;

import com.springapp.mvc.model.Book;
import com.springapp.mvc.service.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration({"classpath:/services-test-configuration.xml"})
public class BookServiceTest {
    @Autowired
    private BookService bookService;

    @Test
    public void addBookTest(){
        Book book = new Book();
        book.setIsn(123);
        book.setBookName("Книга");
        book.setBookAuthor("Автор");

        bookService.addBook(book);

        Book testBook = bookService.getBookByIsn(123);

        assertEquals(book.getBookName(), testBook.getBookName());
    }

    @Test
    public void updateBookTest(){
        Book book = new Book();
        book.setIsn(2);
        book.setBookName("Книга тест");
        book.setBookAuthor("Тест");

        bookService.updateBook(book);

        Book testBook = bookService.getBookByIsn(2);

        assertEquals(book.getBookName(), testBook.getBookName());
    }

    @Test
    public void takeBookTest(){
        String username = "tisem";
        int book_isn = 9;
        bookService.takeBook(username, book_isn);

        String testUserName = bookService.getHandlerByIsn(book_isn);

        assertEquals(username, testUserName);
    }
}

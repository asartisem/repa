package com.springapp.mvc.controller;

import com.springapp.mvc.model.Book;
import com.springapp.mvc.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BookController {
    private BookService bookService;

    @Autowired()
    @Qualifier("bookServiceImpl")
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping(value = {"/", "books"}, method = RequestMethod.GET)
    public String listBooks(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("listBooks", bookService.listBooks(5, 0));
        return "books";
    }

    @RequestMapping(value = "/moreBooks", method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
    @ResponseBody
    public List<Book> getMoreBooks(@RequestParam int offset) {
        List<Book> list = bookService.listBooks(5, offset);
        return list;
    }

    @RequestMapping("/remove-book/{isn}")
    public String removeBook(@PathVariable("isn") int isn) {
        this.bookService.deleteBook(isn);

        return "redirect:/books";
    }

    @RequestMapping(value = "/add-book", method = RequestMethod.POST, produces = {"text/html; charset=utf-8"})
    public String addBook(@ModelAttribute("book") Book book) {

        this.bookService.addBook(book);

        return "redirect:/books";
    }

    @RequestMapping(value = "/update-book", method = RequestMethod.POST, produces = {"text/html; charset=utf-8"})
    public String updateBook(@ModelAttribute("book") Book book) {

        this.bookService.updateBook(book);

        return "redirect:/books";
    }


    @RequestMapping(value = "/take-book/{userName}/{isn}")
    public String takeBook(@PathVariable("userName") String userName, @PathVariable("isn") int isn) {

        this.bookService.takeBook(userName, isn);

        return "redirect:/books";
    }

    @RequestMapping(value = "/return-book/{isn}")
    public String takeBook(@PathVariable("isn") int isn) {
        this.bookService.returnBook(isn);

        return "redirect:/books";
    }
}
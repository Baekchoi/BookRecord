package com.example.bookrecord2.controller;

import com.example.bookrecord2.dto.BookSummary;
import com.example.bookrecord2.entity.Book;
import com.example.bookrecord2.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @GetMapping("/search")
    public List<BookSummary> searchBooks(@RequestParam String title) {
        return bookService.searchBooksByTitle(title);
    }

    @GetMapping("/{id}")
    public Book getBookDetails(@PathVariable Long id) {
        return bookService.getBookById(id);
    }
}

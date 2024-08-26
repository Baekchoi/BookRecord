package com.example.bookrecord2.service;

import com.example.bookrecord2.dto.BookSummary;
import com.example.bookrecord2.entity.Book;
import com.example.bookrecord2.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public List<BookSummary> searchBooksByTitle(String title) {
        List<Book> books = bookRepository.findByTitleContaining(title);
        return books.stream()
                .map(book -> new BookSummary(book.getTitle(), book.getAuthor(), book.getPublisher()))
                .collect(Collectors.toList());
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("책을 찾을 수 없습니다."));
    }

}

package com.example.bookrecord2.service;

import com.example.bookrecord2.dto.BookSummary;
import com.example.bookrecord2.entity.Book;
import com.example.bookrecord2.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
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

    public Book searchBooksByBarcode(String barcode) {
        return bookRepository.findByBarcode(barcode)
                .orElseThrow(() -> new RuntimeException("바코드에 해당하는 책을 찾을 수 없습니다."));
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("책을 찾을 수 없습니다."));
    }
}

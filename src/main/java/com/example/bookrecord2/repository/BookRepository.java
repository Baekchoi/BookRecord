package com.example.bookrecord2.repository;

import com.example.bookrecord2.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByTitleContaining(String title);
    Optional<Book> findByBarcode(String barcode);
}

package com.example.bookrecord2.repository;

import com.example.bookrecord2.entity.UserLibrary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLibraryRepository extends JpaRepository<UserLibrary, Long> {

}

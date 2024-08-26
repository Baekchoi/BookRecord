package com.example.bookrecord2.repository;

import com.example.bookrecord2.dto.constant.ReadingStatus;
import com.example.bookrecord2.entity.UserLibrary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserLibraryRepository extends JpaRepository<UserLibrary, Long> {

    List<UserLibrary> findByUserIdAndStatus(Long userId, ReadingStatus status);
}

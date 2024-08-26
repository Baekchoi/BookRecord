package com.example.bookrecord2.controller;

import com.example.bookrecord2.dto.UserLibraryDto;
import com.example.bookrecord2.service.UserLibraryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/library")
public class UserLibraryController {

    private final UserLibraryService userLibraryService;

    // 읽고 싶은 책 등록
    @PostMapping("/want-to-read")
    public ResponseEntity<Void> addWantToRead(@RequestBody UserLibraryDto dto) {
        userLibraryService.addWantToRead(dto);
        return ResponseEntity.ok().build();
    }

    // 읽고 있는 책 등록
    @PostMapping("/reading")
    public ResponseEntity<Void> addReading(@RequestBody UserLibraryDto dto) {
        userLibraryService.addReading(dto);
        return ResponseEntity.ok().build();
    }

    // 읽은 책 등록
    @PostMapping("/completed")
    public ResponseEntity<Void> addCompleted(@RequestBody UserLibraryDto dto) {
        userLibraryService.addCompleted(dto);
        return ResponseEntity.ok().build();
    }
}

package com.example.bookrecord2.controller;

import com.example.bookrecord2.dto.UserLibraryDto;
import com.example.bookrecord2.dto.constant.ReadingStatus;
import com.example.bookrecord2.service.UserLibraryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // 책 상태 수정
    @PutMapping("/{entryId}")
    public ResponseEntity<Void> updateEntry(@PathVariable Long entryId, @RequestBody UserLibraryDto dto) {
        userLibraryService.updateEntry(entryId, dto);
        return ResponseEntity.ok().build();
    }

    // 상태별 목록 조회
    @GetMapping("/status/{status}")
    public ResponseEntity<List<UserLibraryDto>> getEntriesByStatus(@PathVariable ReadingStatus status, @RequestParam Long userId) {
        List<UserLibraryDto> entries = userLibraryService.getEntriesByStatus(status, userId);
        return ResponseEntity.ok(entries);
    }

    // 특정 책 상세 조회
    @GetMapping("/entry/{id}")
    public ResponseEntity<UserLibraryDto> getEntryDetail(@PathVariable Long id) {
        UserLibraryDto dto = userLibraryService.getEntryDetailById(id);
        return ResponseEntity.ok(dto);
    }

    // 책 삭제
    @DeleteMapping("/{entryId}")
    public ResponseEntity<Void> deleteEntry(@PathVariable Long entryId) {
        userLibraryService.deleteEntry(entryId);
        return ResponseEntity.ok().build();
    }

}

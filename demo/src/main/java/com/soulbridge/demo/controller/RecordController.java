package com.soulbridge.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class RecordController {

    private static final String UPLOAD_DIRECTORY = "uploads/";

    @PostMapping("/upload")
    public ResponseEntity<String> uploadAudioFile(@RequestParam("audio") MultipartFile audioFile) {
        if (audioFile.isEmpty()) {
            return new ResponseEntity<>("음원 파일을 선택해주세요.", HttpStatus.BAD_REQUEST);
        }
        try {
            byte[] bytes = audioFile.getBytes();
            Path path = Paths.get(UPLOAD_DIRECTORY + audioFile.getOriginalFilename());
            Files.createDirectories(path.getParent());
            Files.write(path, bytes);

            return new ResponseEntity<>("음원 파일이 업로드되었습니다: " + audioFile.getOriginalFilename(), HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("음원 파일 업로드 중 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

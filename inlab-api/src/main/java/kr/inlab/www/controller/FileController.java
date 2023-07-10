package kr.inlab.www.controller;

import kr.inlab.www.dto.response.ResponseAudioFileUrlDto;
import kr.inlab.www.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/file-upload")
public class FileController {

    private final FileService fileService;

    @PostMapping
    public ResponseEntity<?> fileUpload(@RequestBody MultipartFile blob) {
        File tempBlob = fileService.saveBlobFile(blob);
        File mp3 = fileService.convertBlobToMp3(tempBlob);
        String audioFileUrl = fileService.transferToFileServer(mp3);
        ResponseAudioFileUrlDto responseDto = ResponseAudioFileUrlDto.builder()
                .audioFileUrl(audioFileUrl)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
}

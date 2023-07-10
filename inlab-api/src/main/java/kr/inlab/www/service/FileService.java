package kr.inlab.www.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface FileService {

    File saveBlobFile(MultipartFile blob);

    File convertBlobToMp3(File blob);

    String transferToFileServer(File mp3);
}

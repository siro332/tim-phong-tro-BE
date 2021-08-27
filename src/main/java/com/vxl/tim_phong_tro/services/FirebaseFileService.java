package com.vxl.tim_phong_tro.services;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface FirebaseFileService {
    String saveFile(MultipartFile file) throws IOException;
    String getImageUrl(String name);
    String generateFileName(String originalFileName);
    String getExtension(String fileName);
    void delete(String name) throws IOException;
}

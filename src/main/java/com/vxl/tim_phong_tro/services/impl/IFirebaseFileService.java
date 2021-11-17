package com.vxl.tim_phong_tro.services.impl;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.cloud.StorageClient;
import com.vxl.tim_phong_tro.services.FirebaseFileService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.UUID;

@Service
@Transactional
public class IFirebaseFileService implements FirebaseFileService {

    private final Storage storage = StorageOptions.getDefaultInstance().getService();

    @Override
    public String saveFile(MultipartFile multipartFile) throws IOException {
        String imageName = generateFileName(multipartFile.getOriginalFilename());
        Bucket bucket = StorageClient.getInstance().bucket();

        bucket.create(imageName, multipartFile.getBytes(), multipartFile.getContentType());

        return imageName;
    }

    @Override
    public String getImageUrl(String name) {
        return String.format("https://storage.googleapis.com/"+StorageClient.getInstance().bucket().getName()+"/%s", name);
    }
    @Override
    public String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }
    @Override
    public String generateFileName(String originalFileName) {
        return UUID.randomUUID() + getExtension(originalFileName);
    }
    @Override
    public void delete(String name) throws IOException {

        Bucket bucket = StorageClient.getInstance().bucket();

        if (StringUtils.isEmpty(name)) {
            throw new IOException("invalid file name");
        }

        Blob blob = bucket.get(name);

        if (blob == null) {
            throw new IOException("file not found");
        }

        blob.delete();
    }
}

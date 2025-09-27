package com.dropbox.backend.service;

import com.dropbox.backend.config.FileConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;


@Service("localStorageService")
public class LocalStorageService implements StorageService {

    private final FileConfig config;
    private static final Logger logger = LoggerFactory.getLogger(LocalStorageService.class);

    @Autowired
    LocalStorageService(FileConfig config){
        this.config = config;
    }

    @Override
    public String store(MultipartFile file, String subFolder) throws IOException {
        String baseStoragePath = config.getPath();
        Path dir = (subFolder == null || subFolder.isEmpty()) ? Paths.get(baseStoragePath) : Paths.get(baseStoragePath, subFolder);

        if (!Files.exists(dir)) {
            Files.createDirectories(dir);
            logger.info("Created storage directory at: {}", dir.toAbsolutePath());
        } else {
            logger.info("Using existing storage directory at: {}", dir.toAbsolutePath());
        }


        String filename = System.currentTimeMillis() + "_" + UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path destination = dir.resolve(filename).normalize();

        try {
            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException("Failed to store file " + filename, e);
        }

        return destination.toAbsolutePath().toString();
    }

    @Override
    public Resource loadAsResource(String storageLocation) throws IOException {
        Path path = Paths.get(storageLocation);
        if (!Files.exists(path)) throw new NoSuchFileException(storageLocation);
        return new UrlResource(path.toUri());
    }

    @Override
    public boolean delete(String storageLocation) throws IOException {
        return Files.deleteIfExists(Paths.get(storageLocation));
    }
}


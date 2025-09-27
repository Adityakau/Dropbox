package com.dropbox.backend.service;

import com.dropbox.backend.contracts.FileMetaDataDTO;
import com.dropbox.backend.model.FileEntity;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.dropbox.backend.repository.FileRepository;


import java.io.IOException;
import java.security.MessageDigest;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FileServiceImpl implements FileService {


    private final FileRepository fileRepository;
    private final StorageService storageService;


    // allowed content-types can be externalized to properties later
    private static final String ALLOWED_TYPES = "text/plain,image/jpeg,image/png,application/json,application/pdf";


    public FileServiceImpl(FileRepository fileRepository, StorageService storageService) {
        this.fileRepository = fileRepository;
        this.storageService = storageService;
    }


    @Override
    public FileEntity saveFile(MultipartFile file) {
        try {
            if (file == null || file.isEmpty()) throw new IOException("Empty file");
            String contentType = file.getContentType();
            if (contentType == null || !ALLOWED_TYPES.contains(contentType)) {
                throw new IOException("Unsupported file type: " + contentType);
            }

            String storedPath = storageService.store(file, null);
            String checksum = computeSHA256(file.getBytes());


            FileEntity entity = FileEntity.builder()
                    .originalFilename(file.getOriginalFilename())
                    .storagePath(storedPath)
                    .mimeType(contentType)
                    .fileSize(file.getSize())
                    .checksum(checksum)
                    .version(1)
                    .uploadTime(ZonedDateTime.now())
                    .build();


            return fileRepository.save(entity);
        } catch (IOException e) {
            throw new RuntimeException("Exception occurred while saving a file : "+ e.getMessage(),e);
        }
    }

    @Override
    public Resource downloadFile(Long id) {
        try{
            Optional<FileEntity> maybe = fileRepository.findById(id);
            if (maybe.isEmpty()) throw new IOException("File not found: " + id);
            FileEntity entity = maybe.get();
            return storageService.loadAsResource(entity.getStoragePath());
        } catch (IOException e) {
            throw new RuntimeException("Exception occurred while downloading a file : "+ e.getMessage(),e);
        }

    }


    @Override
    public List<FileMetaDataDTO> listFiles(int page, int size, String search) {
        List<FileEntity> all = fileRepository.findAll();

        return all.stream()
                .filter(f -> search == null || f.getOriginalFilename().toLowerCase().contains(search.toLowerCase()))
                .map(f -> new FileMetaDataDTO(f.getId(), f.getOriginalFilename(), f.getMimeType(), f.getFileSize(), f.getUploadTime(), f.getVersion()))
                .collect(Collectors.toList());
    }


    private String computeSHA256(byte[] bytes) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(bytes);
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }
}
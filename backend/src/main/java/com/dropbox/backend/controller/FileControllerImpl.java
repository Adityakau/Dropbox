package com.dropbox.backend.controller;

import com.dropbox.backend.contracts.FileMetaDataDTO;
import com.dropbox.backend.model.FileEntity;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import com.dropbox.backend.service.FileService;

import java.io.IOException;
import java.util.List;

@Component
public class FileControllerImpl implements FileController {

    private final FileService fileService;

    public FileControllerImpl(FileService fileService) {
        this.fileService = fileService;
    }

    @Override
    public FileMetaDataDTO uploadFile(MultipartFile file) throws IOException {
        FileEntity saved = fileService.saveFile(file);
        return new FileMetaDataDTO(
                saved.getId(),
                saved.getOriginalFilename(),
                saved.getMimeType(),
                saved.getFileSize(),
                saved.getUploadTime(),
                saved.getVersion()
        );
    }

    @Override
    public List<FileMetaDataDTO> listFiles(int page, int size, String search) {
        return fileService.listFiles(page, size, search);
    }

    @Override
    public Resource downloadFile(Long id) throws IOException {
        return fileService.downloadFile(id);
    }

    @Override
    public Resource previewFile(Long id) throws IOException {
        return fileService.downloadFile(id);
    }
}

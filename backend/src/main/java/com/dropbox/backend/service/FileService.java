package com.dropbox.backend.service;

import com.dropbox.backend.contracts.FileMetaDataDTO;
import com.dropbox.backend.model.FileEntity;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    FileEntity saveFile(MultipartFile file);
    Resource downloadFile(Long id);
    List<FileMetaDataDTO> listFiles(int page, int size, String search);
}

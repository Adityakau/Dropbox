package com.dropbox.backend.controller;


import com.dropbox.backend.contracts.FileMetaDataDTO;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileController {
    FileMetaDataDTO uploadFile(MultipartFile file) throws IOException;
    List<FileMetaDataDTO> listFiles(int page, int size, String search);
    Resource downloadFile(Long id) throws IOException;
    Resource previewFile(Long id) throws IOException;
}


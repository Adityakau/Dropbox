package com.dropbox.backend.api;

import com.dropbox.backend.contracts.FileMetaDataDTO;
import com.dropbox.backend.controller.FileController;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.http.MediaTypeFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/files")
@CrossOrigin(origins = "http://localhost:3000")
public class FileAPIHandler {

    private final FileController fileController;

    public FileAPIHandler(FileController fileController) {
        this.fileController = fileController;
    }

    @PostMapping("/upload")
    public ResponseEntity<FileMetaDataDTO> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        FileMetaDataDTO dto = fileController.uploadFile(file);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping
    public ResponseEntity<List<FileMetaDataDTO>> listFiles(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "25") int size,
                                                           @RequestParam(required = false) String search) {
        List<FileMetaDataDTO> list = fileController.listFiles(page, size, search);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) throws IOException {
        Resource resource = fileController.downloadFile(id);

        String filename = resource.getFilename();
        if (filename == null || filename.isEmpty()) {
            filename = "file_" + id;
        }

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .contentLength(resource.contentLength())
                .body(resource);
    }

    @GetMapping("/preview/{id}")
    public ResponseEntity<Resource> previewFile(@PathVariable Long id) throws IOException {
        Resource resource = fileController.previewFile(id);
        String filename = resource.getFilename();
        MediaType mediaType = MediaTypeFactory.getMediaType(filename).orElse(MediaType.APPLICATION_OCTET_STREAM);
        return ResponseEntity.ok()
                .contentType(mediaType)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                .body(resource);
    }
}

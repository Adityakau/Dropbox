package com.dropbox.backend.contracts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.ZonedDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileMetaDataDTO {
    private Long id;
    private String originalFilename;
    private String mimeType;
    private Long fileSize;
    private ZonedDateTime uploadTime;
    private Integer version;
}
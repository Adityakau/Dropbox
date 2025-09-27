package com.dropbox.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long ownerId;

    private String originalFilename;

    @Column(length = 1000)
    private String storagePath;

    private String mimeType;

    private Long fileSize;

    private String checksum;

    private Integer version;

    private ZonedDateTime uploadTime;
}

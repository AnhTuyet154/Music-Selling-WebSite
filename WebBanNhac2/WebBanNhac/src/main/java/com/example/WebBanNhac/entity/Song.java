package com.example.WebBanNhac.entity;

import com.example.WebBanNhac.validator.annotation.ValidGenreId;
import com.example.WebBanNhac.validator.annotation.ValidUserId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Entity
@Table(name = "song")
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    @NotEmpty(message = "Missing title")
    @Size(max = 50, min = 1, message = "50 characters at least")
    private String title;

    @Column(name = "artist")
    private String artist;

    @Column(name = "price")
    @NotNull(message = "Missing Price")
    private Double price;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    @ValidGenreId
    private Genre genre;

    @ManyToOne
    @JoinColumn(name= "user_id", referencedColumnName = "id")
    @ValidUserId
    private User user;

    @Transient
    private MultipartFile mp3File;

    @Lob
    @Column(name = "file_content", length = 1000000) // Specify the desired length
    private byte[] fileContent;

    @Transient
    private MultipartFile picture; // New field for picture upload

    @Lob
    @Column(name = "picture_content", length = 1000000) // Specify the desired length
    private byte[] pictureContent;
}

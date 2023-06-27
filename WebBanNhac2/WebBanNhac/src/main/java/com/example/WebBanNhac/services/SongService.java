package com.example.WebBanNhac.services;

import com.example.WebBanNhac.entity.Song;
import com.example.WebBanNhac.repository.ISongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service

public class SongService {

    @Autowired

    private ISongRepository songRepository;

    public List<Song> getAllSongs() {
        return songRepository.findAll();

    }

    public Song getSongById(Long id) {

        Optional<Song> optional = songRepository.findById(id);
        return optional.orElse( null);
    }
    public void addSong(Song song, MultipartFile mp3File, MultipartFile picture) {
        try {
            if (!mp3File.isEmpty()) {
                song.setFileContent(mp3File.getBytes());
            }
            if (!picture.isEmpty()) {
                song.setPictureContent(picture.getBytes());
            }
            songRepository.save(song);
        } catch (IOException e) {
            // Handle the exception accordingly
        }
    }





    public void updateSong(Song song) {
        songRepository.save(song);

    }

    public void deleteSong(Long id) {
        songRepository.deleteById(id);
    }
}
package com.example.WebBanNhac.controller;

import com.example.WebBanNhac.daos.Item;
import com.example.WebBanNhac.entity.Order;
import com.example.WebBanNhac.entity.Song;
import com.example.WebBanNhac.services.GenreService;
import com.example.WebBanNhac.services.SongService;
import com.example.WebBanNhac.services.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/songs")
public class SongController {

    @Autowired
    private SongService songService;

    @Autowired
    private GenreService genreService;
    @GetMapping
    public String showAllSongs(Model model) {
        List<Song> songs = songService.getAllSongs();
        model.addAttribute("songs", songs);
        return "song/list";
    }
    @GetMapping("/add")
    public String addBookForm(Model model) {
        model.addAttribute("song", new Song());
        model.addAttribute("genres", genreService.getAllGenres());
        return "song/add";
    }

    @PostMapping("/add")
    public String addSong(
            @ModelAttribute("song") @Validated Song song,
            BindingResult result,
            @RequestParam("file") MultipartFile mp3File,
            @RequestParam("picture") MultipartFile picture
    ) throws IOException {
        if (result.hasErrors()) {
            return "song/add";
        } else {
            if (mp3File.getContentType().equals("audio/mpeg")) {
                // Set the uploaded MP3 file in the song object
                song.setMp3File(mp3File);

                // Set the uploaded picture in the song object
                song.setPicture(picture);

                songService.addSong(song, mp3File, picture);
                return "redirect:/songs";
            } else {
                // Handle the case when the uploaded file is not an MP3 file
                // You can add an error message to the binding result or handle it in another way
                result.rejectValue("mp3File", "Invalid file", "Please upload an MP3 file");
                return "song/add";
            }
        }
    }





    @GetMapping("/edit/{id}")
    public String showEditSongForm(@PathVariable("id") Long id, Model model) {
        Song song = songService.getSongById(id);
        model.addAttribute("song", song);
        model.addAttribute("genres", genreService.getAllGenres());
        return "song/edit";
    }

    @PostMapping("/edit")
    public String editSong(@ModelAttribute("song") @Validated Song song, BindingResult result) {
        if (result.hasErrors()) {
            return "song/edit";
        } else {
            songService.updateSong(song);
            return "redirect:/songs";
        }
    }
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") Long id) {
        songService.deleteSong(id);
        return "redirect:/songs";
    }
    @PostMapping("/add-to-cart")
    public String addToCart(HttpSession session,
                            @RequestParam long id,
                            @RequestParam String name,
                            @RequestParam double price,
                            @RequestParam(defaultValue = "1") int quantity)
    {
        var cart = CartService.getCart(session);
        cart.addItems(new Item(id, name, price, quantity));
        CartService.updateCart(session, cart);
        return "redirect:/songs";
    }
    @GetMapping("/play/{id}")
    public ResponseEntity<byte[]> playSong(@PathVariable("id") Long id) {
        Song song = songService.getSongById(id);

        if (song != null && song.getFileContent() != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("audio/mpeg")); // Set the appropriate audio MIME type

            return new ResponseEntity<>(song.getFileContent(), headers, HttpStatus.OK);
        }

        return ResponseEntity.notFound().build();
    }
    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> showImage(@PathVariable("id") Long id) {
        Song song = songService.getSongById(id);

        if (song != null && song.getPictureContent() != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // Set the appropriate image MIME type

            return new ResponseEntity<>(song.getPictureContent(), headers, HttpStatus.OK);
        }

        return ResponseEntity.notFound().build();
    }
}
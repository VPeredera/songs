package com.peredera.songs.web;

import com.peredera.songs.domain.Song;
import com.peredera.songs.service.SongServiceBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/songs", produces = MediaType.APPLICATION_JSON_VALUE)
public class SongController {

    private final SongServiceBean songServiceBean;

    public SongController(SongServiceBean songServiceBean) {
        this.songServiceBean = songServiceBean;
    }

    @PostMapping(value = "")
    @ResponseStatus(HttpStatus.CREATED)
    public Optional<Song> createSong(@RequestBody Song song) {
        return Optional.ofNullable(songServiceBean.createSong(song));
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Song> findSongById(@PathVariable("id") Long id) {
        return Optional.ofNullable(songServiceBean.findSongById(id));
    }

    @GetMapping(value = "", params = {"name"})
    @ResponseStatus(HttpStatus.OK)
    public Collection<Song> findSongByName(@RequestParam String name) {
        return songServiceBean.findSongByName(name);
    }

    @GetMapping(value = "", params = {"isDeleted"})
    @ResponseStatus(HttpStatus.OK)
    public Collection<Song> findSongByDeletedIs(@RequestParam Boolean isDeleted) {
        return songServiceBean.findSongByIsDeleted(isDeleted);
    }

    @GetMapping(value = "")
    @ResponseStatus(HttpStatus.OK)
    public Collection<Song> findAllSongs() {
        return songServiceBean.findAllSongs();
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Song> updateSong(@PathVariable Long id, @RequestBody Song song) {
        return Optional.ofNullable(songServiceBean.updateSong(id, song));
    }

    @PatchMapping(value = "/{id}", params = {"releaseDate"})
    @ResponseStatus(HttpStatus.OK)
    public Optional<Song> updateReleaseDate(@PathVariable("id") Long id, @RequestParam("releaseDate") LocalDate releaseDate) {
        return Optional.ofNullable(songServiceBean.updateReleaseDate(id, releaseDate));
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSong(@PathVariable Long id) {
        songServiceBean.deleteSong(id);
    }
}
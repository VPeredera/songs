package com.peredera.songs.web;

import com.peredera.songs.domain.Song;
import com.peredera.songs.dto.SongCreateDto;
import com.peredera.songs.dto.SongDto;
import com.peredera.songs.dto.SongInfoDto;
import com.peredera.songs.dto.SongUpdateDto;
import com.peredera.songs.service.SongServiceBean;
import com.peredera.songs.util.config.SongMapperImpl;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value = "/api/songs", produces = MediaType.APPLICATION_JSON_VALUE)
public class SongController {

    private final SongServiceBean songServiceBean;
    private final SongMapperImpl songMapper;

    public SongController(SongServiceBean songServiceBean, SongMapperImpl songMapper) {
        this.songServiceBean = songServiceBean;
        this.songMapper = songMapper;
    }
    @PostMapping(value = "")
    @ResponseStatus(HttpStatus.CREATED)
    public SongCreateDto createSong(@Valid @RequestBody SongCreateDto request) {
        log.debug("createSong(): song = {}", request);
        Song entity = songMapper.fromCreateDto(request);
        return songMapper.toCreateDto(songServiceBean.createSong(entity));
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SongDto findSongById(@PathVariable("id") Long id) {
        log.debug("findSongById(): id = {}", id);
        return songMapper.toSongDto(songServiceBean.findSongById(id));
    }

    @GetMapping(value = "", params = {"name"})
    @ResponseStatus(HttpStatus.OK)
    public Collection<SongInfoDto> findSongByName(@RequestParam String name) {
        log.debug("findSongByName(): name = {}", name);
        return songServiceBean.findSongByName(name).stream().map(songMapper::toInfoDto).toList();
    }

    @GetMapping(value = "", params = {"isDeleted"})
    @ResponseStatus(HttpStatus.OK)
    public Collection<SongDto> findSongByDeletedIs(@RequestParam Boolean isDeleted) {
        log.debug("findSongByDeletedIs(): isDeleted = {}", isDeleted);
        return songServiceBean.findSongByIsDeleted(isDeleted).stream().map(songMapper::toSongDto).toList();
    }

    @GetMapping(value = "")
    @ResponseStatus(HttpStatus.OK)
    public Collection<SongInfoDto> findAllSongs() {
        log.debug("findAllSongs()");
        return songServiceBean.findAllSongs().stream().map(songMapper::toInfoDto).toList();
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SongUpdateDto updateSong(@PathVariable Long id, @Valid @RequestBody SongUpdateDto request) {
        log.debug("updateSong(): id = {}, song = {}", id, request);
        Song entity = songMapper.fromUpdateDto(request);
        return songMapper.toUpdateDto(songServiceBean.updateSong(id, entity));
    }

    @PatchMapping(value = "/{id}", params = {"releaseDate"})
    @ResponseStatus(HttpStatus.OK)
    public Optional<Song> updateReleaseDate(@PathVariable("id") Long id, @RequestParam("releaseDate") LocalDate releaseDate) {
        log.debug("updateReleaseDate(): id = {}, releaseDate = {}", id, releaseDate);
        return Optional.ofNullable(songServiceBean.updateReleaseDate(id, releaseDate));
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSong(@PathVariable Long id) {
        log.debug("deleteSong(): id = {}", id);
        songServiceBean.deleteSong(id);
    }
}
package com.peredera.songs.song.web;

import com.peredera.songs.song.domain.Song;
import com.peredera.songs.song.dto.SongCreateDto;
import com.peredera.songs.song.dto.SongDto;
import com.peredera.songs.song.dto.SongInfoDto;
import com.peredera.songs.song.dto.SongUpdateDto;
import com.peredera.songs.song.service.SongServiceBean;
import com.peredera.songs.song.dto.mapper.SongMapperImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value = "/api/songs", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Song", description = "Song API")
public class SongController implements SongControllerSwagger{

    private final SongServiceBean songServiceBean;
    private final SongMapperImpl songMapper;

    public SongController(SongServiceBean songServiceBean, SongMapperImpl songMapper) {
        this.songServiceBean = songServiceBean;
        this.songMapper = songMapper;
    }

    @Override
    @PostMapping(value = "")
    @ResponseStatus(HttpStatus.CREATED)
    public SongCreateDto createSong(@Valid @RequestBody SongCreateDto request) {
        log.debug("createSong(): song = {}", request);
        var entity = songMapper.fromCreateDto(request);
        return songMapper.toCreateDto(songServiceBean.createSong(entity));
    }

    @Override
    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SongDto findSongById(@PathVariable("id") Long id) {
        log.debug("findSongById(): id = {}", id);
        return songMapper.toSongDto(songServiceBean.findSongById(id));
    }

    @Override
    @GetMapping(value = "", params = {"name"})
    @ResponseStatus(HttpStatus.OK)
    public Collection<SongInfoDto> findSongByName(@RequestParam String name) {
        log.debug("findSongByName(): name = {}", name);
        return songServiceBean.findSongByName(name).stream().map(songMapper::toInfoDto).toList();
    }

    @Override
    @GetMapping(value = "", params = {"isDeleted"})
    @ResponseStatus(HttpStatus.OK)
    public Collection<SongDto> findSongByDeletedIs(@RequestParam Boolean isDeleted) {
        log.debug("findSongByDeletedIs(): isDeleted = {}", isDeleted);
        return songServiceBean.findSongByIsDeleted(isDeleted).stream().map(songMapper::toSongDto).toList();
    }

    @Override
    @GetMapping(value = "")
    @ResponseStatus(HttpStatus.OK)
    public Collection<SongInfoDto> findAllSongs() {
        log.debug("findAllSongs()");
        return songServiceBean.findAllSongs().stream().map(songMapper::toInfoDto).toList();
    }

    @Override
    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SongUpdateDto updateSong(@PathVariable Long id, @Valid @RequestBody SongUpdateDto request) {
        log.debug("updateSong(): id = {}, song = {}", id, request);
        var entity = songMapper.fromUpdateDto(request);
        return songMapper.toUpdateDto(songServiceBean.updateSong(id, entity));
    }

    @Override
    @PatchMapping(value = "/{id}", params = {"releaseDate"})
    @ResponseStatus(HttpStatus.OK)
    public Optional<Song> updateReleaseDate(@PathVariable("id") Long id, @RequestParam("releaseDate") LocalDate releaseDate) {
        log.debug("updateReleaseDate(): id = {}, releaseDate = {}", id, releaseDate);
        return Optional.ofNullable(songServiceBean.updateReleaseDate(id, releaseDate));
    }

    @Override
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSong(@PathVariable Long id) {
        log.debug("deleteSong(): id = {}", id);
        songServiceBean.deleteSong(id);
    }
}
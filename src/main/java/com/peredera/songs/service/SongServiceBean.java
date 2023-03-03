package com.peredera.songs.service;

import com.peredera.songs.domain.Song;
import com.peredera.songs.repository.SongRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class SongServiceBean implements SongService {

    private final SongRepository songRepository;

    public SongServiceBean(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @Override
    public Song createSong(Song song) {
        log.info("createSong() - start: name = {}", song.getName());
        check80sSong(song);
        song.setDeleted(Boolean.FALSE);
        log.info("createSong() - finish: name = {}", song.getName());
        return songRepository.save(song);
    }

    @Override
    public Song findSongById(Long id) {
        log.info("findSongById(): id = {}", id);
        return checkSong(id);
    }

    @Override
    public List<Song> findSongByName(String name) {
        log.info("findSongByName(): name = {}", name);
        return songRepository.findByName(name);
    }

    @Override
    public List<Song> findSongByIsDeleted(Boolean isDeleted) {
        log.info("findByIsDeleted(): isDeleted = {}", isDeleted);
        return songRepository.findByIsDeleted(isDeleted);
    }

    @Override
    public List<Song> findAllSongs() {
        log.info("findAllSongs()");
        return songRepository.findAll();
    }

    @Override
    public Song updateSong(Long id, Song song) {
        log.info("updateSong() - start: id = {}, songName = {}", id, song.getName());
        check80sSong(song);
        log.info("updateSong() - finish: id = {}, songName = {}", id, song.getName());
        return songRepository.findById(id)
                .map(entity -> {
                    entity.setName(song.getName());
                    entity.setSinger(song.getSinger());
                    entity.setGroupName(song.getGroupName());
                    entity.setReleaseDate(song.getReleaseDate());
                    entity.setSongLength(song.getSongLength());
                    entity.setDeleted(song.getDeleted());
                    return songRepository.save(entity);
                })
                .orElseThrow(() -> new EntityNotFoundException("Song not found with id " + id));
    }

    @Override
    public Song updateReleaseDate(Long id, LocalDate releaseDate) {
        log.info("findSongByName() - start: id = {}, releaseDate={}", id, releaseDate);
        checkSong(id);
        songRepository.updateReleaseDate(id, releaseDate);
        log.info("findSongByName() - finish: id = {}, releaseDate={}", id, findSongById(id).getReleaseDate());
        return checkSong(id);
    }

    @Override
    public void deleteSong(Long id) {
        log.info("deleteSong(): id = {}", id);
        Song song = checkSong(id);
        songRepository.deleteSong(song.getId());
    }

    private void check80sSong(Song song) {
        if(song.getReleaseDate().isAfter(LocalDate.of(1980, Month.JANUARY, 1)) &&
                song.getReleaseDate().isBefore(LocalDate.of(1990, Month.JANUARY, 1))) {
            song.setName(song.getName() + " |80's");
        }
    }

    private Song checkSong(Long id) {
        return songRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Song not found with id " + id));
    }
}

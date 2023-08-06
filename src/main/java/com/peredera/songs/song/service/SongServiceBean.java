package com.peredera.songs.song.service;

import com.peredera.songs.song.domain.Song;
import com.peredera.songs.song.repository.SongRepository;
import com.peredera.songs.util.exception.ResourceNotFoundException;
import com.peredera.songs.util.exception.ResourceWasDeletedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import static java.util.function.Predicate.not;

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
        log.info("createSong() - finish: name = {}", song.getName());
        return songRepository.save(song);
    }

    @Override
    public Song findSongById(Long id) {
        log.info("findSongById(): id = {}", id);
        return checkIfSongIsDeleted(getSongById(id));
    }

    @Override
    public List<Song> findSongByName(String name) {
        log.info("findSongByName(): name = {}", name);
        return songRepository.findByName(name).stream().filter(not(Song::getIsDeleted)).toList();
    }

    @Override
    public List<Song> findSongByIsDeleted(Boolean isDeleted) {
        log.info("findByIsDeleted(): isDeleted = {}", isDeleted);
        return songRepository.findByIsDeleted(isDeleted);
    }

    @Override
    public List<Song> findAllSongs() {
        log.info("findAllSongs()");
        return songRepository.findAll().stream().filter(not(Song::getIsDeleted)).toList();
    }

    @Override
    public Song updateSong(Long id, Song song) {
        log.info("updateSong() - start: id = {}, songName = {}", id, song.getName());
        log.debug("updateSong() -> check80sSong(): name = {}", song.getName());
        check80sSong(song);
        log.info("updateSong() - finish: id = {}, songName = {}", id, song.getName());
        return songRepository.save(updateSong(getSongById(id), song));
    }

    @Override
    public Song updateReleaseDate(Long id, LocalDate releaseDate) {
        log.info("findSongByName() - start: id = {}, releaseDate={}", id, releaseDate);
        log.debug("updateReleaseDate() -> checkSong(): id = {}", id);
        getSongById(id);
        songRepository.updateReleaseDate(id, releaseDate);
        log.info("findSongByName() - finish: id = {}, releaseDate={}", id, findSongById(id).getReleaseDate());
        return getSongById(id);
    }

    @Override
    public void deleteSong(Long id) {
        log.info("deleteSong(): id = {}", id);
        log.debug("deleteSong() -> checkSong(): id = {}", id);
        Song song = checkIfSongIsDeleted(getSongById(id));
        songRepository.deleteSong(song.getId());
    }

    private void check80sSong(Song song) {
        if(song.getReleaseDate().isAfter(LocalDate.of(1980, Month.JANUARY, 1)) &&
                song.getReleaseDate().isBefore(LocalDate.of(1990, Month.JANUARY, 1))) {
            song.setName(song.getName() + " |80's");
        }
    }

    private Song getSongById(Long id) {
        return songRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
    }

    private Song checkIfSongIsDeleted(Song song) {
        if(song.getIsDeleted()) throw new ResourceWasDeletedException();
        return song;
    }

    private Song updateSong(Song initial, Song updated) {
        initial.setName(updated.getName());
        initial.setSinger(updated.getSinger());
        initial.setGroupName(updated.getGroupName());
        initial.setReleaseDate(updated.getReleaseDate());
        initial.setSongLength(updated.getSongLength());
        initial.setIsDeleted(updated.getIsDeleted());
        return initial;
    }
}

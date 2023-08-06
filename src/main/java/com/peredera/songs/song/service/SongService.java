package com.peredera.songs.song.service;

import com.peredera.songs.song.domain.Song;

import java.time.LocalDate;
import java.util.List;

public interface SongService {
    Song createSong(Song song);

    Song findSongById(Long id);

    List<Song> findSongByName(String name);

    List<Song> findSongByIsDeleted(Boolean isDeleted);

    List<Song> findAllSongs();

    Song updateSong(Long id, Song song);

    Song updateReleaseDate(Long id, LocalDate releaseDate);

    void deleteSong(Long id);
}

package com.peredera.songs;

import com.peredera.songs.domain.Song;
import com.peredera.songs.repository.SongRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.util.List;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SongRepositoryTests {

    @Autowired
    private SongRepository repository;

    @Test
    @Order(1)
    @Rollback(value = false)
    public void saveSongTest() {
        Song song = Song.builder().name("Song").build();

        repository.save(song);

        Assertions.assertThat(song.getId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    @DisplayName("findByIdTest()")
    public void getSongTest() {
        Song song = repository.findById(1L).orElseThrow();

        Assertions.assertThat(song.getId()).isEqualTo(1);
    }

    @Test
    @Order(3)
    public void getListOfSongTest() {
        List<Song> songs = repository.findAll();

        Assertions.assertThat(songs.size()).isGreaterThan(0);
    }

    @Test
    @Order(4)
    @Rollback(value = false)
    public void updateSongTest() {
        Song song = repository.findById(1L).orElseThrow();

        song.setName("NewName");
        Song songUpdated = repository.save(song);

        Assertions.assertThat(songUpdated.getName()).isEqualTo("NewName");
    }

    @Test
    @Order(5)
    @Rollback(value = false)
    public void deleteSongTest() {
        Song song = repository.findById(1L).orElseThrow();

        repository.deleteSong(song.getId());

        Song songDeleted = repository.findById(song.getId()).orElseThrow();
        Assertions.assertThat(songDeleted.getDeleted()).isTrue();
    }

    @Test
    @Order(6)
    public void findByIsDeletedTest() {
        List<Song> songs = repository.findByIsDeleted(Boolean.TRUE);

        Assertions.assertThat(songs).allMatch(s -> s.getDeleted().equals(Boolean.TRUE));
    }

    @Test
    @Order(7)
    public void findByNameTest() {
        List<Song> songs = repository.findByName("NewName");

        Assertions.assertThat(songs).allMatch(s -> s.getName().contains("NewName"));
    }

    @Test
    @Order(8)
    public void updatedReleaseDate() {
        Song song = repository.findById(1L).orElseThrow();

        repository.updateReleaseDate(song.getId(), LocalDate.parse("2023-03-11"));

        Song updatedSong = repository.findById(song.getId()).orElseThrow();
        Assertions.assertThat(updatedSong.getReleaseDate()).isEqualTo("2023-03-11");
    }
}

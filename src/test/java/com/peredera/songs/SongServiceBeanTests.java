package com.peredera.songs;

import com.peredera.songs.song.domain.Song;
import com.peredera.songs.song.repository.SongRepository;
import com.peredera.songs.song.service.SongServiceBean;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SongServiceBeanTests {

    @InjectMocks
    private SongServiceBean service;

    @Mock
    private SongRepository repository;

    @Test
    @DisplayName("createSong")
    void whenSaveSong_ShouldReturnSong() {
        Song song = Song.builder()
                .name("Bad Man (Kordhell Remix)")
                .releaseDate(LocalDate.parse("2022-11-18"))
                .build();

        when(repository.save(any(Song.class))).thenReturn(song);

        Song expected = service.createSong(song);

        assertThat(expected)
                .satisfies(exp -> {
                    assertThat(exp.getName()).isEqualTo(song.getName());
                    assertThat(exp.getReleaseDate()).isEqualTo(song.getReleaseDate());
                });
        verify(repository).save(song);
    }

    @Test
    @DisplayName("findSongById")
    void whenGivenId_shouldReturnSong_ifFound() {
        Song song = Song.builder().id(1L).isDeleted(false).build();

        when(repository.findById(any(Long.class))).thenReturn(Optional.of(song));

        Song expected = service.findSongById(song.getId());

        assertThat(expected).isSameAs(song);
        verify(repository).findById(song.getId());
    }

    @Test
    @DisplayName("checkSong")
    void shouldThrowException_whenSongDoesntExist() {
        assertThatThrownBy(() -> service.findSongById(99L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Song not found with id");
    }

    @Test
    @DisplayName("findSongByName")
    void whenGivenName_shouldReturnListOfSongs() {
        Song song1 = Song.builder().name("New Decadence").build();
        Song song2 = Song.builder().name("Another Decadence").build();
        String searchName = "decadence";
        List<Song> songs = new ArrayList<>(Arrays.asList(song1, song2));

        when(repository.findByName(any(String.class))).thenReturn(songs);

        List<Song> expected = service.findSongByName(searchName);

        assertThat(expected).isEqualTo(songs);
        assertThat(expected).allMatch(s -> s.getName().toLowerCase().contains(searchName));
        verify(repository).findByName(searchName);
    }

    @Test
    @DisplayName("findByIsDeleted")
    void whenGivenIsDeleted_shouldReturnListOfSongs() {
        Song song1 = Song.builder().isDeleted(Boolean.TRUE).build();
        Song song2 = Song.builder().isDeleted(Boolean.TRUE).build();
        List<Song> songs = new ArrayList<>(Arrays.asList(song1, song2));

        when(repository.findByIsDeleted(any(Boolean.class))).thenReturn(songs);

        List<Song> expected = service.findSongByIsDeleted(Boolean.TRUE);

        assertThat(expected).isEqualTo(songs);
        assertThat(expected).allMatch(s -> s.getIsDeleted().equals(Boolean.TRUE));
        verify(repository).findByIsDeleted(Boolean.TRUE);
    }

    @Test
    @DisplayName("findAllSongs")
    void shouldReturnAllSongs() {
        Song song1 = Song.builder().build();
        Song song2 = Song.builder().build();
        List<Song> songs = new ArrayList<>(Arrays.asList(song1, song2));

        when(repository.findAll()).thenReturn(songs);

        List<Song> expected = service.findAllSongs();

        assertThat(expected)
                .satisfies(exp -> {
                    assertThat(exp).isEqualTo(songs);
                    assertThat(exp).hasSize(2);
                });
        verify(repository).findAll();
    }

    @Test
    @DisplayName("updateSong")
    void shouldSetNewParametersToSong_thenReturnUpdatedSong() {
        Song song = Song.builder()
                .id(1L)
                .name("Song")
                .singer("Singer")
                .groupName("Group")
                .releaseDate(LocalDate.parse("1999-01-01"))
                .songLength(300)
                .isDeleted(Boolean.FALSE)
                .build();
        Song updatedSong = Song.builder()
                .id(1L)
                .name("NewSong")
                .singer("NewSinger")
                .groupName("NewGroup")
                .releaseDate(LocalDate.parse("2000-01-01"))
                .songLength(310)
                .isDeleted(Boolean.TRUE)
                .build();

        when(repository.findById(any(Long.class))).thenReturn(Optional.of(song));
        when(repository.save(any(Song.class))).thenReturn(updatedSong);

        Song expected = service.updateSong(1L, updatedSong);

        assertThat(expected).isEqualTo(updatedSong);
        verify(repository).findById(1L);
        verify(repository).save(updatedSong);
    }

    @Test
    @DisplayName("updateReleaseDate")
    void whenGivenNewIdAndReleaseDate_shouldReturnUpdatedSong() {
        Song song = Song.builder().id(1L).releaseDate(LocalDate.parse("2020-01-01")).isDeleted(Boolean.FALSE).build();

        when(repository.findById(any(Long.class))).thenReturn(Optional.of(song));
        doAnswer( arg -> {
            song.setReleaseDate(arg.getArgument(1));
            return null;
        }).when(repository).updateReleaseDate(any(Long.class), any(LocalDate.class));

        Song expected = service.updateReleaseDate(1L, LocalDate.parse("2023-01-01"));

        assertThat(expected.getReleaseDate()).isEqualTo(song.getReleaseDate());
        verify(repository).updateReleaseDate(1L, LocalDate.parse("2023-01-01"));
    }

    @Test
    @DisplayName("deleteSong")
    void shouldDeleteSong_withoutReturnValue() {
        Song song = Song.builder().id(1L).build();

        when(repository.findById(any(Long.class))).thenReturn(Optional.of(song));

        service.deleteSong(song.getId());

        verify(repository).deleteSong(1L);
    }
}
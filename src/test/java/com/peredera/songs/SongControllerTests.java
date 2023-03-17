package com.peredera.songs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.peredera.songs.domain.Song;
import com.peredera.songs.service.SongServiceBean;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SongControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private SongServiceBean service;

    @Test
    @DisplayName("POST /songs")
    void createSong() throws Exception {
        Song songToPost = Song.builder()
                .name("Inner Fire")
                .releaseDate(LocalDate.parse("2000-01-01"))
                .build();
        Song songToReturn = Song.builder()
                .id(1L)
                .name("Inner Fire")
                .releaseDate(LocalDate.parse("2000-01-01"))
                .build();

        when(service.createSong(any(Song.class))).thenReturn(songToReturn);

        MockHttpServletRequestBuilder mockRequest = post("/api/songs")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(songToPost));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Inner Fire")))
                .andExpect(jsonPath("$.releaseDate", is("2000-01-01")));
    }

    @Test
    @DisplayName("GET /songs/{id}")
    void findSongById_success() throws Exception {
        Song song = Song.builder().name("The Hills").build();

        when(service.findSongById(anyLong())).thenReturn(song);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/songs/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("The Hills")));
    }

    @Test
    @DisplayName("GET /songs param = {name}")
    void findSongByName_success() throws Exception {
        Song song1 = Song.builder().name("The Hills").build();
        Song song2 = Song.builder().name("Another The Hills").build();

        when(service.findSongByName(anyString())).thenReturn(Arrays.asList(song1, song2));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/songs")
                        .param("name", "The Hills")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$[0].name", containsString("The Hills")))
                .andExpect(jsonPath("$[1].name", containsString("The Hills")));
    }

    @Test
    @DisplayName("GET /songs param = {isDeleted}")
    void findSongByDeletedIs_success() throws Exception {
        Song song1 = Song.builder().id(1L).isDeleted(Boolean.TRUE).build();
        Song song2 = Song.builder().id(2L).isDeleted(Boolean.TRUE).build();

        when(service.findSongByIsDeleted(anyBoolean())).thenReturn(Arrays.asList(song1, song2));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/songs")
                        .param("isDeleted", "true")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)));
    }

    @Test
    @DisplayName("GET /songs")
    void findAllSongs() throws Exception {
        Song song1 = Song.builder().id(1L).build();
        Song song2 = Song.builder().id(2L).build();

        when(service.findAllSongs()).thenReturn(Arrays.asList(song1, song2));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/songs")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)));
    }

    @Test
    @DisplayName("PUT /songs/{id}")
    void updateSong() throws Exception {
        Song songToPut = Song.builder().name("New Name").build();
        Song songToReturn = Song.builder().id(1L).name("New Name").build();

        when(service.updateSong(anyLong(), any(Song.class))).thenReturn(songToReturn);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/songs/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(songToPut)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("New Name")));
    }

    @Test
    @DisplayName("PUT /songs/{id} param = {releaseDate}")
    void updateReleaseDate() throws Exception {
        Song songToReturn = Song.builder().id(1L).releaseDate(LocalDate.parse("2023-03-03")).build();

        when(service.updateReleaseDate(anyLong(), any(LocalDate.class))).thenReturn(songToReturn);

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/songs/1")
                        .param("releaseDate", "2023-03-03")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.releaseDate", is("2023-03-03")));
    }

    @Test
    @DisplayName("DELETE /songs/{id}")
    void deleteSong() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/songs/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
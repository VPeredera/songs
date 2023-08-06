package com.peredera.songs.song.web;

import com.peredera.songs.song.domain.Song;
import com.peredera.songs.song.dto.SongCreateDto;
import com.peredera.songs.song.dto.SongDto;
import com.peredera.songs.song.dto.SongInfoDto;
import com.peredera.songs.song.dto.SongUpdateDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

public interface SongControllerSwagger {

    @Operation(summary = "Endpoint to add a new song", description = "Create request to add a new song", tags = {"Song"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED. The new song is successfully created and added to database"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST. Invalid input.", content = @Content)})
    SongCreateDto createSong(SongCreateDto request);

    @Operation(summary = "Endpoint to find song by id", description = "Create request to find song by id", tags = {"Song"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. The song is successfully found by id"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified song request not found", content = @Content)})
    SongDto findSongById(@Parameter(description = "ID of song to be searched") Long id);

    @Operation(summary = "Endpoint to find song by title", description = "Create request to find song by title", tags = {"Song"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. The song is successfully found by title")})
    Collection<SongInfoDto> findSongByName(@Parameter(description = "Title of song to be searched") String name);

    @Operation(summary = "Endpoint to find song by deleting", description = "Create request to find song by deleting", tags = {"Song"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. The song is successfully found by isDeleted field")})
    Collection<SongDto> findSongByDeletedIs(Boolean isDeleted);

    @Operation(summary = "Endpoint to find all songs", description = "Create request to find all songs", tags = {"Song"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. Songs is successfully found.")})
    Collection<SongInfoDto> findAllSongs();

    @Operation(summary = "Endpoint to update song", description = "Create request to update song by id", tags = {"Song"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. The song is successfully updated."),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST. Invalid input.", content = @Content),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified song request not found", content = @Content)})
    SongUpdateDto updateSong(@Parameter(description = "ID of song to be updated") Long id, SongUpdateDto request);

    @Operation(summary = "Endpoint to update song release date", description = "Create request to update song release date", tags = {"Song"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. The song is successfully updated."),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified song request not found", content = @Content)})
    Optional<Song> updateReleaseDate(@Parameter(description = "ID of song to be updated") Long id, LocalDate releaseDate);

    @Operation(summary = "Endpoint to delete song", description = "Create request to delete song", tags = {"Song"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "NO CONTENT. The song is successfully deleted."),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified song request not found", content = @Content)})
    void deleteSong(@Parameter(description = "ID of song to be deleted") Long id);
}

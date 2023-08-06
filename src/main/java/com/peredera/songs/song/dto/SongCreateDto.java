package com.peredera.songs.song.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class SongCreateDto {
    @NotNull(message = "Song should have a title")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters long")
    @Schema(description = "Song title", example = "Temple of Odin")
    public String title;
    @NotBlank(message = "Singer is mandatory")
    @Size(max = 50)
    @Schema(description = "Singer of a song", example = "Reidar Olsen")
    public String singer;
    @Size(max = 100)
    @Schema(description = "Name of a group", example = "Danheim")
    public String group;
    @PastOrPresent(message = "Song should be released")
    @Schema(description = "Release date of a song", example = "2017-03-17")
    public LocalDate releaseDate;
    @Min(value = 1, message = "Minimal length of song is 1")
    @Schema(description = "Song duration (seconds)", example = "203")
    public Integer length;
}

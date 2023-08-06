package com.peredera.songs.song.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class SongUpdateDto {

    @NotNull(message = "Song should have a title")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters long")
    @Schema(description = "Song title", example = "Forever Young")
    public String title;
    @NotBlank(message = "Singer is mandatory")
    @Size(max = 50)
    @Schema(description = "Singer of a song", example = "Marian Gold")
    public String singer;
    @Size(max = 100)
    @Schema(description = "Name of a group", example = "Alphaville")
    public String group;
    @PastOrPresent(message = "Song should be released")
    @Schema(description = "Release date of a song", example = "1984-09-27")
    public LocalDate releaseDate;
    @Min(value = 1)
    @Schema(description = "Song duration (seconds)", example = "226")
    public Integer length;
    @Schema(description = "Song is deleted or not (true-false)", example = "false")
    public Boolean deleted;
}

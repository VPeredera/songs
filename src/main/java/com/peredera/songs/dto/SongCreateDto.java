package com.peredera.songs.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class SongCreateDto {
    @NotNull(message = "Song should have a title")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters long")
    public String title;
    @NotBlank(message = "Singer is mandatory")
    @Size(max = 50)
    public String singer;
    @Size(max = 100)
    public String group;
    public LocalDate releaseDate;
    @Min(value = 1, message = "Minimal length of song is 1")
    public Integer length;
}

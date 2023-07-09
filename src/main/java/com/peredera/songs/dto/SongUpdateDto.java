package com.peredera.songs.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class SongUpdateDto {

    @NotNull(message = "Song should have a title")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters long")
    public String title;
    @NotBlank(message = "Singer is mandatory")
    @Size(max = 50)
    public String singer;
    @Size(max = 100)
    public String group;
    @PastOrPresent(message = "Song should be released")
    public LocalDate releaseDate;
    @Min(value = 1)
    public Integer length;
    public Boolean deleted;
}

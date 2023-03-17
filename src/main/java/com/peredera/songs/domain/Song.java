package com.peredera.songs.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "songs")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String singer;
    private String groupName;
    private LocalDate releaseDate;
    private Integer songLength;

    @JsonIgnore
    private Boolean isDeleted = Boolean.FALSE;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getSongLength() {
        return songLength;
    }

    public void setSongLength(Integer songLength) {
        this.songLength = songLength;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return id.equals(song.id) && name.equals(song.name) && singer.equals(song.singer) &&
                groupName.equals(song.groupName) && releaseDate.equals(song.releaseDate) &&
                songLength.equals(song.songLength) && isDeleted.equals(song.isDeleted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, singer, groupName, releaseDate, songLength, isDeleted);
    }
}

package com.peredera.songs.song.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.peredera.songs.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "songs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "singer", nullable = false, length = 50)
    private String singer;

    @Column(name = "group_name", length = 100)
    private String groupName;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "song_length")
    private Integer songLength;

    @Column(name = "is_deleted")
    private Boolean isDeleted = Boolean.FALSE;

    @ManyToMany(mappedBy = "songs", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<User> users;
}

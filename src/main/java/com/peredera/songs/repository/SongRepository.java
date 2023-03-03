package com.peredera.songs.repository;

import com.peredera.songs.domain.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
    @Query(value = "SELECT s FROM Song s WHERE s.name LIKE CONCAT('%', :name, '%')")
    List<Song> findByName(@Param("name") String name);

    @Query(value = "SELECT s FROM Song s WHERE s.isDeleted = :deleted")
    List<Song> findByIsDeleted(@Param("deleted") Boolean isDeleted);

    @Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "UPDATE Song s SET s.isDeleted = true WHERE s.id = :id")
    void deleteSong(@Param("id") Long id);

    @Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "UPDATE Song s SET s.releaseDate = :date WHERE s.id = :id")
    void updateReleaseDate(@Param("id") Long id, @Param("date") LocalDate releaseDate);
}
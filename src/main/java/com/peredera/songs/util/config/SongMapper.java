package com.peredera.songs.util.config;

import com.peredera.songs.domain.Song;
import com.peredera.songs.dto.SongCreateDto;
import com.peredera.songs.dto.SongDto;
import com.peredera.songs.dto.SongInfoDto;
import com.peredera.songs.dto.SongUpdateDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface SongMapper {

    @Mapping(target = "name", source = "title")
    @Mapping(target = "groupName", source = "group")
    @Mapping(target = "songLength", source = "length")
    @Mapping(target = "isDeleted", constant = "false")
    Song fromCreateDto(SongCreateDto dto);

    @Mapping(target = "title", source = "name")
    @Mapping(target = "group", source = "groupName")
    @Mapping(target = "length", source = "songLength")
    SongCreateDto toCreateDto(Song entity);

    @Mapping(target = "title", source = "name")
    @Mapping(target = "group", source = "groupName")
    @Mapping(target = "length", source = "songLength")
    SongDto toSongDto(Song entity);

    @Mapping(target = "title", source = "name")
    @Mapping(target = "group", source = "groupName")
    @Mapping(target = "length", source = "songLength")
    SongInfoDto toInfoDto(Song entity);

    @Mapping(target = "name", source = "title")
    @Mapping(target = "groupName", source = "group")
    @Mapping(target = "songLength", source = "length")
    @Mapping(target = "isDeleted", source = "deleted")
    Song fromUpdateDto(SongUpdateDto dto);

    @Mapping(target = "title", source = "name")
    @Mapping(target = "group", source = "groupName")
    @Mapping(target = "length", source = "songLength")
    @Mapping(target = "deleted", source = "isDeleted")
    SongUpdateDto toUpdateDto(Song entity);
}

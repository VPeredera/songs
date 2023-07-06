package com.peredera.songs.util.config.mappers;

import com.peredera.songs.domain.Song;
import com.peredera.songs.dto.SongCreateDto;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;

public class SongCreateMapper extends CustomMapper<Song, SongCreateDto> {

    public void mapAtoB(Song entity, SongCreateDto dto, MappingContext mappingContext) {
        dto.title = entity.getName();
        dto.group = entity.getGroupName();
        dto.length = entity.getSongLength();
    }

    public void mapBtoA(SongCreateDto dto, Song entity, MappingContext mappingContext) {
        entity.setName(dto.title);
        entity.setGroupName(dto.group);
        entity.setSongLength(dto.length);
    }
}

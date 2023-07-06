package com.peredera.songs.util.config.mappers;

import com.peredera.songs.domain.Song;
import com.peredera.songs.dto.SongUpdateDto;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;

public class SongUpdateMapper extends CustomMapper<Song, SongUpdateDto> {

    @Override
    public void mapAtoB(Song entity, SongUpdateDto dto, MappingContext mappingContext) {
        dto.title = entity.getName();
        dto.group = entity.getGroupName();
        dto.length = entity.getSongLength();
        dto.deleted = entity.getIsDeleted();
    }

    @Override
    public void mapBtoA(SongUpdateDto dto, Song entity, MappingContext context) {
        entity.setName(dto.title);
        entity.setGroupName(dto.group);
        entity.setSongLength(dto.length);
        entity.setIsDeleted(dto.deleted);
    }
}

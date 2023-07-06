package com.peredera.songs.util.config.mappers;

import com.peredera.songs.domain.Song;
import com.peredera.songs.dto.SongDto;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;

public class SongMapper extends CustomMapper<Song, SongDto> {

    @Override
    public void mapAtoB(Song entity, SongDto dto, MappingContext context) {
        dto.title = entity.getName();
        dto.group = entity.getGroupName();
        dto.length = entity.getSongLength();
    }
}

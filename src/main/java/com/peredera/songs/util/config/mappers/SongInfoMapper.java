package com.peredera.songs.util.config.mappers;

import com.peredera.songs.domain.Song;
import com.peredera.songs.dto.SongInfoDto;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;

public class SongInfoMapper extends CustomMapper<Song, SongInfoDto> {

    @Override
    public void mapAtoB(Song entity, SongInfoDto dto, MappingContext context) {
        dto.title = entity.getName();
        dto.group = entity. getGroupName();
        dto.length = entity.getSongLength();
    }
}

package com.peredera.songs.util.config;

import com.peredera.songs.domain.Song;
import com.peredera.songs.dto.SongCreateDto;
import com.peredera.songs.dto.SongDto;
import com.peredera.songs.dto.SongInfoDto;
import com.peredera.songs.dto.SongUpdateDto;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Component;

@Component
public class SongConverter {

    private final MapperFacade mapperFacade;

    public SongConverter(MapperFacade mapperFacade) {
        this.mapperFacade = mapperFacade;
    }

    public SongDto toDto(Song entity) {
        return mapperFacade.map(entity, SongDto.class);
    }

    public SongCreateDto toCreateDto(Song entity) {
        return mapperFacade.map(entity, SongCreateDto.class);
    }

    public Song fromCreateDto(SongCreateDto dto) {
        return mapperFacade.map(dto, Song.class);
    }

    public SongInfoDto toInfoDto(Song entity) {
        return mapperFacade.map(entity, SongInfoDto.class);
    }

    public SongUpdateDto toUpdateDto(Song entity) {
        return mapperFacade.map(entity, SongUpdateDto.class);
    }

    public Song fromUpdateDto(SongUpdateDto dto) {
        return mapperFacade.map(dto, Song.class);
    }
}

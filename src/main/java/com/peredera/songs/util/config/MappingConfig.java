package com.peredera.songs.util.config;

import com.peredera.songs.domain.Song;
import com.peredera.songs.dto.SongCreateDto;
import com.peredera.songs.dto.SongDto;
import com.peredera.songs.dto.SongInfoDto;
import com.peredera.songs.dto.SongUpdateDto;
import com.peredera.songs.util.config.mappers.SongCreateMapper;
import com.peredera.songs.util.config.mappers.SongInfoMapper;
import com.peredera.songs.util.config.mappers.SongMapper;
import com.peredera.songs.util.config.mappers.SongUpdateMapper;
import dev.akkinoc.spring.boot.orika.OrikaMapperFactoryConfigurer;
import ma.glasnost.orika.MapperFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class MappingConfig implements OrikaMapperFactoryConfigurer {

    @Override
    public void configure(@NotNull MapperFactory orikaMapperFactory) {

        orikaMapperFactory.classMap(Song.class, SongUpdateDto.class)
                .customize(new SongUpdateMapper())
                .byDefault()
                .register();

        orikaMapperFactory.classMap(Song.class, SongInfoDto.class)
                .customize(new SongInfoMapper())
                .byDefault()
                .register();

        orikaMapperFactory.classMap(Song.class, SongCreateDto.class)
                .customize(new SongCreateMapper())
                .byDefault()
                .register();

        orikaMapperFactory.classMap(Song.class, SongDto.class)
                .customize(new SongMapper())
                .byDefault()
                .register();
    }
}

package ru.cft.yellowrubberduck.repository;

import ru.cft.yellowrubberduck.repository.model.SampleEntity;

import java.util.List;

public interface BaseRepository {

    public List<SampleEntity> selectAll();

}

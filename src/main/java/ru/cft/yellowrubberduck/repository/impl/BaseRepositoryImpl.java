package ru.cft.yellowrubberduck.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.cft.yellowrubberduck.repository.BaseRepository;
import ru.cft.yellowrubberduck.repository.model.SampleEntity;

import java.util.List;

@Repository
public class BaseRepositoryImpl implements BaseRepository {

    private JdbcTemplate jdbcTemplate;
    private RowMapper<SampleEntity> rowMapper;

    @Autowired
    public BaseRepositoryImpl(JdbcTemplate jdbcTemplate, RowMapper<SampleEntity> rowMapper){
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
    }

    @Override
    public List<SampleEntity> selectAll() {
        return jdbcTemplate.query("Select * from sample;", rowMapper);
    }
}

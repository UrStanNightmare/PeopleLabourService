package ru.cft.yellowrubberduck.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.cft.yellowrubberduck.repository.BaseRepository;
import ru.cft.yellowrubberduck.repository.model.SampleEntity;
import ru.cft.yellowrubberduck.service.SampleService;

import java.util.List;

@Service
public class SampleServiceImpl {

    private final BaseRepository baseRepository;

    @Autowired
    public SampleServiceImpl(BaseRepository baseRepository) {
        this.baseRepository = baseRepository;
    }
    
    public List<SampleEntity> getAllSample() {
        return baseRepository.selectAll();
    }
}

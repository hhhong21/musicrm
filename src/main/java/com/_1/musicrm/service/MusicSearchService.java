package com._1.musicrm.service;

import com._1.musicrm.model.MusicFile;
import com._1.musicrm.repository.MusicSearchRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MusicSearchService {

    private final MusicSearchRepository repository;

    public MusicSearchService(MusicSearchRepository repository) {
        this.repository = repository;
    }

    public List<MusicFile> searchMusic(String keyword) {
        return repository.search(keyword);
    }
}
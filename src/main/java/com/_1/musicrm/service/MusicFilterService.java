package com._1.musicrm.service;

import com._1.musicrm.Repository.MusicFilterRepository;
import com._1.musicrm.model.MusicFile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MusicFilterService {

    private final MusicFilterRepository musicFilterRepository;

    public MusicFilterService(MusicFilterRepository musicFilterRepository) {
        this.musicFilterRepository = musicFilterRepository;
    }

    public List<MusicFile> filterMusic(Long categoryId, List<Long> tagIds) {
        return musicFilterRepository.filterBy(categoryId, tagIds);
    }
}

package com._1.musicrm.Repository;

import com._1.musicrm.model.MusicFile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class MusicFilterRepository {
    private final List<MusicFile> musicList = new ArrayList<>();

    public MusicFilterRepository() {
        musicList.add(new MusicFile(
            1L,
            "Love Story",
            "Taylor Swift",
            "Fearless",
            List.of(1L),             // categoryIds
            List.of(100L, 101L),     // tagIds
            "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3" //  filePath
        ));

        musicList.add(new MusicFile(
            2L,
            "Imagine",
            "John Lennon",
            "Imagine",
            List.of(2L),
            List.of(102L),
            "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3" //模拟数据
        ));
    }

    public List<MusicFile> filterBy(Long categoryId, List<Long> tagIds) {
        return musicList.stream()
            .filter(m -> categoryId == null || m.getCategoryIds().contains(categoryId))
            .filter(m -> tagIds == null || tagIds.isEmpty() ||
                m.getTagIds().stream().anyMatch(tagIds::contains))
            .collect(Collectors.toList());
    }
}

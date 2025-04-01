package com._1.musicrm.repository;

import com._1.musicrm.model.MusicFile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class MusicSearchRepository {

    private final List<MusicFile> musicList = new ArrayList<>();

    public MusicSearchRepository() {
        // 模拟数据
        musicList.add(new MusicFile(1L, "Love Story", "Taylor Swift", "Fearless"));
        musicList.add(new MusicFile(2L, "Imagine", "John Lennon", "Imagine"));
        musicList.add(new MusicFile(3L, "Bad Guy", "Billie Eilish", "When We All Fall Asleep"));
        musicList.add(new MusicFile(4L, "Smooth Criminal", "Michael Jackson", "Bad"));
    }

    public List<MusicFile> search(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return musicList;
        }
        String lowerKeyword = keyword.toLowerCase();

        return musicList.stream()
                .filter(m -> m.getTitle().toLowerCase().contains(lowerKeyword)
                        || m.getArtist().toLowerCase().contains(lowerKeyword)
                        || m.getAlbum().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());
    }
}
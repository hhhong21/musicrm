package com._1.musicrm.model;

import java.util.List;

public class MusicFile {
    private Long id;
    private String title;
    private String artist;
    private String album;
    private List<Long> categoryIds;
    private List<Long> tagIds;

    // 无参构造函数
    public MusicFile() {}

    // 全参数构造函数
    public MusicFile(Long id, String title, String artist, String album, List<Long> categoryIds, List<Long> tagIds) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.categoryIds = categoryIds;
        this.tagIds = tagIds;
    }

    // Getter
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public List<Long> getCategoryIds() {
        return categoryIds;
    }

    public List<Long> getTagIds() {
        return tagIds;
    }

    // Setter
    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setCategoryIds(List<Long> categoryIds) {
        this.categoryIds = categoryIds;
    }

    public void setTagIds(List<Long> tagIds) {
        this.tagIds = tagIds;
    }
}
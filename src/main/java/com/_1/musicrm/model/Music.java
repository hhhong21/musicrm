package com._1.musicrm.model;

import java.util.List;

public class Music {
    private Long id;
    private String title;
    private String artist;
    private String category;
    private List<String> tags;

    // 全参数构造函数
    public Music(Long id, String title, String artist, String category, List<String> tags) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.category = category;
        this.tags = tags;
    }

    // Getter 方法
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getArtist() { return artist; }
    public String getCategory() { return category; }
    public List<String> getTags() { return tags; }

    // Setter 方法（根据需求可选）
    public void setId(Long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setArtist(String artist) { this.artist = artist; }
    public void setCategory(String category) { this.category = category; }
    public void setTags(List<String> tags) { this.tags = tags; }
}
package com._1.musicrm.model;

public class Tag {
    private Long id;
    private String name;

    // 无参构造函数
    public Tag() {}

    // 全参数构造函数
    public Tag(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getter
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // Setter
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
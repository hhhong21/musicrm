package com._1.musicrm.controller;

import com._1.musicrm.model.MusicFile;
import com._1.musicrm.service.MusicFilterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/music")
public class MusicFilterController {

    private final MusicFilterService musicFilterService;

    public MusicFilterController(MusicFilterService musicFilterService) {
        this.musicFilterService = musicFilterService;
    }

    @GetMapping("/filter")
    public ResponseEntity<List<MusicFile>> filterMusic(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) List<Long> tagIds) {

        List<MusicFile> result = musicFilterService.filterMusic(categoryId, tagIds);
        return ResponseEntity.ok(result);
    }
}

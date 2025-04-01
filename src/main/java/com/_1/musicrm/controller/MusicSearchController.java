package com._1.musicrm.controller;

import com._1.musicrm.model.MusicFile;
import com._1.musicrm.service.MusicSearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/music")
@CrossOrigin(origins = "*")
public class MusicSearchController {

    private final MusicSearchService musicSearchService;

    public MusicSearchController(MusicSearchService musicSearchService) {
        this.musicSearchService = musicSearchService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<MusicFile>> searchMusic(@RequestParam(required = false) String keyword) {
        List<MusicFile> result = musicSearchService.searchMusic(keyword);
        return ResponseEntity.ok(result);
    }
}
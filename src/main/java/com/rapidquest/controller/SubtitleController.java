package com.rapidquest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rapidquest.entity.Subtitle;
import com.rapidquest.services.SubtitleService;

@RestController
@RequestMapping("/subtitles")
@CrossOrigin
public class SubtitleController {
	
	@Autowired
    SubtitleService subtitleService;

    @PostMapping
    public Subtitle createSubtitle(@RequestBody Subtitle subtitle) {
        return subtitleService.saveSubtitle(subtitle);
    }

    @GetMapping("/{id}")
    public Optional<Subtitle> getSubtitleById(@PathVariable int id) {
        return subtitleService.getSubtitleById(id);
    }

    @GetMapping
    public List<Subtitle> getAllSubtitles() {
        return subtitleService.getAllSubtitles();
    }

    @DeleteMapping("/{id}")
    public void deleteSubtitle(@PathVariable int id) {
        subtitleService.deleteSubtitle(id);
    }
    
    @GetMapping("/video/{videoId}")
    public List<Subtitle> getSubtitlesByVideoId(@PathVariable int videoId) {
        return subtitleService.getSubtitlesByVideoId(videoId);
    }


}

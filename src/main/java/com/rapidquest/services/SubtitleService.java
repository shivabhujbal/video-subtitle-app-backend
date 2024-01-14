package com.rapidquest.services;

import java.util.List;
import java.util.Optional;


import com.rapidquest.entity.Subtitle;

public interface SubtitleService {
	 	Subtitle saveSubtitle(Subtitle subtitle);
	    Optional<Subtitle> getSubtitleById(int id);
	    List<Subtitle> getAllSubtitles();
	    void deleteSubtitle(int id);
	    public List<Subtitle> getSubtitlesByVideoId(int videoId);
}

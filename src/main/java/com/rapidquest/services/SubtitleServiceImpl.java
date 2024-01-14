package com.rapidquest.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.rapidquest.entity.Subtitle;
import com.rapidquest.repository.SubtitleRepository;

@Service
public class SubtitleServiceImpl implements SubtitleService {
	
	@Autowired
	SubtitleRepository subtitleRepository;
	
	@Override
	public Subtitle saveSubtitle(Subtitle subtitle) {
		// TODO Auto-generated method stub
		return subtitleRepository.save(subtitle);
	}

	@Override
	public Optional<Subtitle> getSubtitleById(int id) {
		// TODO Auto-generated method stub
		return subtitleRepository.findById(id);
	}

	@Override
	public List<Subtitle> getAllSubtitles() {
		// TODO Auto-generated method stub
		return subtitleRepository.findAll();
	}

	@Override
	public void deleteSubtitle(int id) {
		Optional<Subtitle> optSub = subtitleRepository.findById(id);
		if(optSub.isPresent()) {
			
			subtitleRepository.deleteById(id);
			
		}
		else {
			throw new NotFoundException("No id for Delete");
		}
		
	}
	
	@Override
	public List<Subtitle> getSubtitlesByVideoId(int videoId) {
        // Assuming you have a repository method to fetch subtitles by video ID
        return subtitleRepository.findByVideoId(videoId);
    }
	
}

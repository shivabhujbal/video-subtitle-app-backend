package com.rapidquest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rapidquest.entity.Subtitle;

@Repository
public interface SubtitleRepository extends JpaRepository<Subtitle, Integer> {
	List<Subtitle> findByVideoId(int videoId);
}

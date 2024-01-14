package com.rapidquest.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name = "videos")
public class Video {
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int id;

	    @Column(name = "video_name")
	    private String videoName;
	    
	    @Column(name = "video_path")
	    private String videoPath;
	    
	    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	    @JsonManagedReference
	    private List<Subtitle> subtitles;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getVideoName() {
			return videoName;
		}

		public void setVideoName(String videoName) {
			this.videoName = videoName;
		}

		public String getVideoPath() {
			return videoPath;
		}

		public void setVideoPath(String videoPath) {
			this.videoPath = videoPath;
		}

		public List<Subtitle> getSubtitles() {
			return subtitles;
		}

		public void setSubtitles(List<Subtitle> subtitles) {
			this.subtitles = subtitles;
		}

		public Video(int id, String videoName, String videoPath, List<Subtitle> subtitles) {
			super();
			this.id = id;
			this.videoName = videoName;
			this.videoPath = videoPath;
			this.subtitles = subtitles;
		}

		public Video() {
			super();
			// TODO Auto-generated constructor stub
		}
	    
	    
	    
	    
	    
	    

}

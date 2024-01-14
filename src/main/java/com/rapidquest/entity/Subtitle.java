package com.rapidquest.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "subtitles")
public class Subtitle {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "subtitle_text")
	private String subtitleText;

	@Column(name = "start_time")
	private String startTime;

	@Column(name = "end_time")
	private String endTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "video_id")
	@JsonBackReference
	private Video video;

	public Subtitle(int id, String subtitleText, String startTime, String endTime, Video video) {
		super();
		this.id = id;
		this.subtitleText = subtitleText;
		this.startTime = startTime;
		this.endTime = endTime;
		this.video = video;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSubtitleText() {
		return subtitleText;
	}

	public void setSubtitleText(String subtitleText) {
		this.subtitleText = subtitleText;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	public Subtitle() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Subtitle [id=" + id + ", subtitleText=" + subtitleText + ", startTime=" + startTime + ", endTime="
				+ endTime + ", video=" + video + "]";
	}
	
	
	
	
	

}

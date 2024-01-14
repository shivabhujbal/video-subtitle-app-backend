package com.rapidquest.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;

import com.rapidquest.entity.Video;

public interface VideoService {
	Video saveVideo(Video video);
    Optional<Video> getVideoById(int id);
    List<Video> getAllVideos();
    void deleteVideo(int id);
    String uploadVideo(MultipartFile file) throws IOException;
    public void deleteVideoFromS3(String fileName);
    public List<String> getAllFilesFromS3();
	byte[] getVideoBytesFromS3(String fileName);
}

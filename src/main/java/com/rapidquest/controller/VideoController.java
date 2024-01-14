package com.rapidquest.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.kms.model.NotFoundException;
import com.rapidquest.entity.Video;
import com.rapidquest.services.VideoService;

@RestController
@RequestMapping("/videos")
@CrossOrigin
public class VideoController {
	
	@Autowired
    private VideoService videoService;

    @PostMapping
    public Video createVideo(@RequestBody Video video) {
        return videoService.saveVideo(video);
    }

    @GetMapping("/{id}")
    public Optional<Video> getVideoById(@PathVariable int id) {
        return videoService.getVideoById(id);
    }

    @GetMapping("getAll")
    public List<Video> getAllVideos() {
        return videoService.getAllVideos();
    }

   
    
    @PostMapping("/upload")
    public ResponseEntity<String> uploadVideo(@RequestParam("file") MultipartFile file){
    	try {
			String filUrl = videoService.uploadVideo(file);
			return ResponseEntity.ok().body("Video uploaded!!. URL: "+ filUrl);
			
		} catch (IOException e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload video");
		}
    }
    
    
    
    @GetMapping("/{videoId}/stream")
	public ResponseEntity<ByteArrayResource> streamVideo(@PathVariable Integer videoId) {
        Optional<Video> optionalVideo = videoService.getVideoById(videoId);
        
        
        if (optionalVideo.isPresent()) {
            Video video = optionalVideo.get();
            String fileName = video.getVideoName();
            
            byte[] videoBytes = videoService.getVideoBytesFromS3(fileName);
			if (videoBytes != null) {
			    HttpHeaders headers = new HttpHeaders();
			    headers.add(HttpHeaders.CONTENT_TYPE, "video/mp4");

			    int contentLength = videoBytes.length;

			    return ResponseEntity.ok()
			            .headers(headers)
			            .contentLength(contentLength)
			            .body(new ByteArrayResource(videoBytes));
			} else {
			    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}     
            
        }    
           
		return ResponseEntity.notFound().build();
	}
    
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVideo(@PathVariable int id) {
        try {
            // Check if video exists
            videoService.getVideoById(id).orElseThrow(() -> new NotFoundException("Video not found with id: " + id));

            // Delete video from both S3 and the database
            videoService.deleteVideo(id);

            return ResponseEntity.ok().body("Video deleted successfully");
        } catch (Exception e) {
            // Handle exceptions and return an appropriate response
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error deleting video");
        }
    }
    
    
    @GetMapping("/all-files-from-s3")
    public ResponseEntity<List<String>> getAllFilesFromS3() {
        List<String> fileNames = videoService.getAllFilesFromS3();
        return ResponseEntity.ok(fileNames);
    }

}













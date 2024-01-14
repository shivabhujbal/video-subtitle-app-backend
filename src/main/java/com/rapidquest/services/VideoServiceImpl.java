package com.rapidquest.services;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.rapidquest.entity.Video;
import com.rapidquest.repository.VideoRepository;

@Service
public class VideoServiceImpl implements VideoService {

	@Autowired
	VideoRepository videoRepository;
	
	@Override
	public Video saveVideo(Video video) {
		// TODO Auto-generated method stub
		return videoRepository.save(video);
	}

	@Override
	public Optional<Video> getVideoById(int id) {
		// TODO Auto-generated method stub
		return videoRepository.findById(id);
	}

	@Override
	public List<Video> getAllVideos() {
		// TODO Auto-generated method stub
		return videoRepository.findAll();
	}

	@Override
	public void deleteVideo(int id) {
		Optional<Video> optionalVideo = videoRepository.findById(id);

	    if (optionalVideo.isPresent()) {
	        Video video = optionalVideo.get();

	        // Delete video from S3
	        String fileName = video.getVideoName();
	        deleteVideoFromS3(fileName);

	        // Delete video from the database
	        videoRepository.deleteById(id);
	    }	}
	
	@Autowired
	private AmazonS3 s3Client;
	@Value("${aws.bucketName}")
	private String bucketName;

	@Override
	public String uploadVideo(MultipartFile file) throws IOException {
		String fileName = generateFileName(file.getOriginalFilename());
		File convertedFile = convertMultiPartToFile(file);
		uploadFileToS3Bucket(fileName,convertedFile);
		convertedFile.delete();
		
		String fileUrl = getFileUrl(fileName);
		
		Video video = new Video();
		video.setVideoName(fileName);
		video.setVideoPath(fileUrl);
		videoRepository.save(video);
		
		return fileUrl;
	}

	private String getFileUrl(String fileName) {
		// TODO Auto-generated method stub
		return s3Client.getUrl(bucketName, fileName).toString();
	}

	private void uploadFileToS3Bucket(String fileName, File file) {

		s3Client.putObject(new PutObjectRequest(bucketName, fileName, file));
	}

	private File convertMultiPartToFile(MultipartFile file) throws IOException {
		
		File convertedFile = new File(file.getOriginalFilename());
		try(FileOutputStream fos = new FileOutputStream(convertedFile)) {
			fos.write(file.getBytes());
			
		}
		return convertedFile;
	}

	private String generateFileName(String originalFilename) {
		// TODO Auto-generated method stub
		return System.currentTimeMillis()+"_" + originalFilename.replace(" ", "_");
	}

	
	
	
	@Override
	public byte[] getVideoBytesFromS3(String fileName) {
	    if (s3Client == null || bucketName == null) {
	        throw new IllegalStateException("S3 client or bucket name not initialized");
	    }

	    S3Object s3Object = s3Client.getObject(bucketName, fileName);
	    try (S3ObjectInputStream inputStream = s3Object.getObjectContent()) {
	        // Read the content into a byte array
	        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
	        int nRead;
	        byte[] data = new byte[16384]; // You can adjust the buffer size as needed

	        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
	            buffer.write(data, 0, nRead);
	        }

	        buffer.flush();
	        return buffer.toByteArray();
	    } catch (IOException e) {
	        // Handle exception
	        e.printStackTrace();
	        return null; // or throw a custom exception
	    }
	}


	@Override
	public void deleteVideoFromS3(String fileName) {
		s3Client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
	}

	@Override
	public List<String> getAllFilesFromS3() {
		List<String> fileNames = new ArrayList<String>();
		
		ObjectListing objectListing = s3Client.listObjects(bucketName);
		
		for(S3ObjectSummary s3ObjectSummary : objectListing.getObjectSummaries()) {
			
			fileNames.add(s3ObjectSummary.getKey());
			
		}
		return fileNames;
	}
	
	
	
	
	
	


}

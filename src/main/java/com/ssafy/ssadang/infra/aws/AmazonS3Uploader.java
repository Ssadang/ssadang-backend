package com.ssafy.ssadang.infra.aws;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.ssafy.ssadang.global.error.exception.AmazonS3IOException;
import com.ssafy.ssadang.global.error.exception.RequestImageInvalidException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class AmazonS3Uploader {
	
	private List<String> supportedExtensions = List.of("bmp", "gif", "jpeg", "jpg", "png", "webp");
	
	private final AmazonS3 amazonS3;
	
	@Value("${cloud.aws.s3.bucket-name}")
	private String bucketName;
	
	public String uploadImage(MultipartFile image) {
		validateImage(image);
		String originalFilename = image.getOriginalFilename();
		String fileExtension = extractFileExtension(originalFilename);
		String uploadedFilename = System.currentTimeMillis() + "_" + originalFilename;
		
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType("image/" + fileExtension);
		try (InputStream is = image.getInputStream()) {
			byte[] bytes = IOUtils.toByteArray(is);
			objectMetadata.setContentLength(bytes.length);
			
			try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes)) {
				PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, uploadedFilename, bais, objectMetadata)
						.withCannedAcl(CannedAccessControlList.PublicRead);
				amazonS3.putObject(putObjectRequest);
			}
		} catch (IOException e) {
			throw new AmazonS3IOException();
		}
		return amazonS3.getUrl(bucketName, uploadedFilename).toString();
	}
	
	private String extractFileExtension(String originalFilename) {
		int lastDotIdx = originalFilename.lastIndexOf('.');
		if (lastDotIdx == -1) {
			throw new RequestImageInvalidException();
		}
		String lowercaseExtension = originalFilename.substring(lastDotIdx + 1).toLowerCase();
		if (!supportedExtensions.contains(lowercaseExtension)) {
			throw new RequestImageInvalidException();
		}
		return lowercaseExtension;
	}
	
	private void validateImage(MultipartFile image) {
		String originalFilename = image.getOriginalFilename();
		if (image.isEmpty() || originalFilename == null) {
			throw new RequestImageInvalidException();
		}
		validateImageFile(image);
	}
	
	private void validateImageFile(MultipartFile image) {
		try (InputStream is = image.getInputStream()) {
			BufferedImage bufferedImage = ImageIO.read(is);
			if (bufferedImage == null) {
				throw new RequestImageInvalidException();
			}
		} catch (IOException e) {
			throw new AmazonS3IOException();
		}
	}

}

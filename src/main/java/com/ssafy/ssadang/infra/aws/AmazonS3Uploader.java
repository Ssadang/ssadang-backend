package com.ssafy.ssadang.infra.aws;

import java.awt.image.BufferedImage;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.ssafy.ssadang.global.error.exception.RequestImageInvalidException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class AmazonS3Uploader {
	
	private final List<String> supportedExtensions = List.of("bmp", "gif", "jpeg", "jpg", "png", "webp");
	
	private final AmazonS3 amazonS3;
	
	public String uploadImage(MultipartFile image) {
		validateImage(image);
		return null;
	}
	
	private void validateImage(MultipartFile image) {
		String originalFilename = image.getOriginalFilename();
		if (image.isEmpty() || originalFilename == null) {
			throw new RequestImageInvalidException();
		}
		validateFileExtension(originalFilename);
		validateImageFile(image);
	}
	
	private void validateFileExtension(String originalFilename) {
		int lastDotIdx = originalFilename.lastIndexOf('.');
		if (lastDotIdx == -1) {
			throw new RequestImageInvalidException();
		}
		String lowercaseExtension = originalFilename.substring(lastDotIdx + 1).toLowerCase();
		if (!supportedExtensions.contains(lowercaseExtension)) {
			throw new RequestImageInvalidException();
		}
	}
	
	private void validateImageFile(MultipartFile image) {
		try {
			BufferedImage bufferedImage = ImageIO.read(image.getInputStream());
			if (bufferedImage == null) {
				throw new IllegalArgumentException();
			}
		} catch (Exception e) {
			throw new RequestImageInvalidException();
		}
	}

}

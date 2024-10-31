package com.services;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class cloudinaryService {

	private final Cloudinary cloudinary;

	
	public cloudinaryService(@Value("${cloudinary.cloud_name}") String cloudName,
            				 @Value("${cloudinary.api_key}") String apiKey,
            				 @Value("${cloudinary.api_secret}") String apiSecret) {
					this.cloudinary = new Cloudinary(ObjectUtils.asMap(
							"cloud_name", cloudName,
							"api_key", apiKey,
							"api_secret", apiSecret));
		}		

	  public String uploadFileToFolder(MultipartFile file, String folder) throws IOException {
	        Map uploadParams = ObjectUtils.asMap(
	                "folder", folder
	        );
	        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), uploadParams);
	        return uploadResult.get("url").toString(); // Get the file URL after upload
	    }
}

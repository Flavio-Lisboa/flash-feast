package com.flavio.flashfeast.domain.utils;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Component
public class CloudinaryUtil {
    private final Cloudinary cloudinary;

    public CloudinaryUtil(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }
    
    public String uploadFile(MultipartFile logo, String folder) {
        try {
            File uploadedLogo = convertMultipartToFile(logo);
            Map<?, ?> uploadResult = cloudinary.uploader().upload(uploadedLogo, ObjectUtils.asMap("folder", folder));
            boolean isDeleted = uploadedLogo.delete();

            if (isDeleted)System.out.println("File successfully deleted");
            else System.out.println("File doesn't exist");

            return uploadResult.get("secure_url").toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private File convertMultipartToFile(MultipartFile logo) throws IOException {
        File convertedLogo = new File(Objects.requireNonNull(logo.getOriginalFilename()));
        FileOutputStream fileOutputStream = new FileOutputStream(convertedLogo);
        fileOutputStream.write(logo.getBytes());
        fileOutputStream.close();
        return convertedLogo;
    }
}

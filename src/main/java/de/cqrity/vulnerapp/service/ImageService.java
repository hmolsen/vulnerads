package de.cqrity.vulnerapp.service;

import com.google.common.io.ByteStreams;
import com.google.common.io.Files;
import de.cqrity.vulnerapp.domain.Image;
import de.cqrity.vulnerapp.repository.ImageRepository;
import de.cqrity.vulnerapp.util.VirusScanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class ImageService {

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    ServletContext context;

    @Autowired
    VirusScanner virusScanner;

    public void updateDefaultPhoto(byte[] image) {
        Image defaultImage = new Image();
        defaultImage.setImage(image);
        defaultImage.setName("default");
        imageRepository.save(defaultImage);
    }

    public String storeImage(MultipartFile adphoto, long adId) {
        String filename = adphoto.getOriginalFilename();
        File imageFolder = getImageFolder();
        File subfolder = new File(imageFolder, String.valueOf(adId));
        if (!subfolder.exists()) subfolder.mkdirs();
        File photoFile = new File(subfolder, filename);
        try {
            Files.write(adphoto.getBytes(), photoFile);
            if (!virusScanner.forFile(photoFile).scan().isVirusFree()) {
                photoFile.delete();
            }
            return filename;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] retrieveImageOrDefault(String fn) {
        byte[] image;

        image = retrieveImageFromFilesystem(fn);
        if (image != null) {
            return image;
        }

        image = retrieveDefaultImageFromDatabase();
        if (image != null) {
            return image;
        }

        image = retrieveDefaultImageFromResource();
        if (image != null) {
            return image;
        }

        return new byte[0];
    }

    private File getImageFolder() {
        File imageFolder = new File(System.getProperty("user.home"), "vulnerapp_photos");
        if (!imageFolder.exists()) {
            imageFolder.mkdirs();
        }
        return imageFolder;
    }

    private byte[] retrieveImageFromFilesystem(String fn) {
        String path = System.getProperty("user.home") + "/vulnerapp_photos/" + fn;
        try {
            InputStream photoStream = new FileInputStream(path);
            return ByteStreams.toByteArray(photoStream);
        } catch (IOException e) {
            return null;
        }
    }

    private byte[] retrieveDefaultImageFromDatabase() {
        Image imageFromDb = imageRepository.findByName("default");
        if (imageFromDb == null) {
            return null;
        }
        return imageFromDb.getImage();
    }

    private byte[] retrieveDefaultImageFromResource() {
        Resource imageFromResource = new ServletContextResource(context, "/resources/img/default.jpg");
        try {
            InputStream photoStream = imageFromResource.getInputStream();
            return ByteStreams.toByteArray(photoStream);
        } catch (IOException e) {
            return null;
        }
    }
}

package de.cqrity.vulnerapp.service;

import de.cqrity.vulnerapp.domain.Image;
import de.cqrity.vulnerapp.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    @Autowired
    ImageRepository imageRepository;

    public void updateDefaultPhoto(byte[] image) {
        Image defaultImage = new Image();
        defaultImage.setImage(image);
        defaultImage.setName("default");
        imageRepository.save(defaultImage);
    }
}

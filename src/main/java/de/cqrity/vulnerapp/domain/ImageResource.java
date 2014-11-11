package de.cqrity.vulnerapp.domain;

import org.springframework.web.multipart.MultipartFile;

public class ImageResource {

    private MultipartFile image;

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}

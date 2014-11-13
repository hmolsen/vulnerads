package de.cqrity.vulnerapp.controller;

import de.cqrity.vulnerapp.repository.ImageRepository;
import de.cqrity.vulnerapp.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ImageController {

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    ImageService imageService;

    @RequestMapping(value = "/photo", method = RequestMethod.GET)
    public HttpEntity<byte[]> getPhoto(@RequestParam(value = "fn") String fn) {
        return httpEntityOf(imageService.retrieveImageOrDefault(fn));
    }

    private HttpEntity<byte[]> httpEntityOf(byte[] image) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(image.length);
        return new HttpEntity<>(image, headers);
    }
}

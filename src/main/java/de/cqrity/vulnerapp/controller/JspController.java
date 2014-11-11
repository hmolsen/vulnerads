package de.cqrity.vulnerapp.controller;


import com.google.common.io.ByteStreams;
import de.cqrity.vulnerapp.domain.Image;
import de.cqrity.vulnerapp.repository.ClassifiedAdRepository;
import de.cqrity.vulnerapp.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class JspController {

    @Autowired
    ClassifiedAdRepository classifiedAdRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    ServletContext context;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView showIndex(@RequestParam(value = "deleted", required = false, defaultValue = "false") boolean deleted) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index");
        mav.addObject("deleted", deleted);
        mav.addObject("latestAds", classifiedAdRepository.findAllByOrderByCreatedtimestampDesc());

        return mav;
    }

    @RequestMapping(value = "/photo", method = RequestMethod.GET)
    public HttpEntity<byte[]> getPhoto(@RequestParam(value = "fn") String fn) {
        byte[] image;

        image = retrieveImageFromFilesystem(fn);
        if (image != null) {
            return httpEntityOf(image);
        }

        image = retrieveDefaultImageFromDatabase();
        if (image != null) {
            return httpEntityOf(image);
        }

        image = retrieveDefaultImageFromResource();
        if (image != null) {
            return httpEntityOf(image);
        }

        return httpEntityOf(new byte[0]);

    }

    private HttpEntity<byte[]> httpEntityOf(byte[] image) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(image.length);
        return new HttpEntity<>(image, headers);
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

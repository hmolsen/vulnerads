package de.cqrity.vulnerapp.controller;


import com.google.common.io.ByteStreams;
import de.cqrity.vulnerapp.exception.NotFound;
import de.cqrity.vulnerapp.repository.ClassifiedAdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class JspController {

    @Autowired
    ClassifiedAdRepository classifiedAdRepository;

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
        String path = System.getProperty("user.home") + "/vulnerapp_photos/" + fn;
        InputStream photoStream = null;
        try {
            photoStream = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            throw new NotFound("Photo nicht gefunden: " + path);
        }
        byte[] photo = new byte[0];
        try {
            photo = ByteStreams.toByteArray(photoStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(photo.length);
        return new HttpEntity<>(photo, headers);
    }

}

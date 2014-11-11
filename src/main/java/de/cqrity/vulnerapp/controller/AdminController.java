package de.cqrity.vulnerapp.controller;

import de.cqrity.vulnerapp.domain.ImageResource;
import de.cqrity.vulnerapp.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    ImageService imageService;

    @RequestMapping(value = "/defaultphoto", method = RequestMethod.GET)
    public ModelAndView changeDefaultPhoto() {
        ModelAndView mav = new ModelAndView("/admin/change_default_photo", "command", new ImageResource());

        return mav;
    }

    @RequestMapping(value = "/defaultphoto", method = RequestMethod.POST)
    public ModelAndView changeDefaultPhoto(@ModelAttribute("command")ImageResource request, BindingResult result) {
        ModelAndView mav = new ModelAndView("/admin/change_default_photo");

        try {
            imageService.updateDefaultPhoto(request.getImage().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mav;
    }
}

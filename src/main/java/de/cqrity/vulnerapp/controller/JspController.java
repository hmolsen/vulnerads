package de.cqrity.vulnerapp.controller;


import de.cqrity.vulnerapp.repository.ClassifiedAdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class JspController {

    @Autowired
    ClassifiedAdRepository classifiedAdRepository;

    @GetMapping("/.well-known/appspecific/com.chrome.devtools.json")
    public ResponseEntity<Void> chromeDevTools() {
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView showIndex(@RequestParam(value = "deleted", required = false, defaultValue = "false") boolean deleted) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index");
        mav.addObject("deleted", deleted);
        mav.addObject("latestAds", classifiedAdRepository.findAllByOrderByCreatedtimestampDesc());

        return mav;
    }

}

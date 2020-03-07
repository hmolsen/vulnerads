package de.cqrity.vulnerapp.controller;

import de.cqrity.vulnerapp.domain.ClassifiedAd;
import de.cqrity.vulnerapp.domain.ClassifiedAdResource;
import de.cqrity.vulnerapp.domain.User;
import de.cqrity.vulnerapp.exception.NotFound;
import de.cqrity.vulnerapp.repository.ClassifiedAdRepository;
import de.cqrity.vulnerapp.repository.UserRepository;
import de.cqrity.vulnerapp.service.ClassifiedAdService;
import de.cqrity.vulnerapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Controller
public class ClassifiedAdController {
    public static final String USER_FILTER = "user:";

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ClassifiedAdRepository classifiedAdRepository;

    @Autowired
    ClassifiedAdService classifiedAdService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/ads", method = RequestMethod.GET)
    public ModelAndView showFilteredAds(@RequestParam(value = "s", required = false, defaultValue = "") String s) {
        if (s.startsWith(USER_FILTER) && s.length() > USER_FILTER.length()) {
            String username = s.split(USER_FILTER, 2)[1];
            return showAdsByUser(username);
        }

        ModelAndView mav = new ModelAndView();
        mav.setViewName("index");

        List<ClassifiedAd> ads = classifiedAdService.fetchLatestAds(s);

        mav.addObject("latestAds", ads);
        mav.addObject("s", s);

        return mav;
    }

    @RequestMapping(value = "/ads/{username}", method = RequestMethod.GET)
    public ModelAndView showAdsByUser(@PathVariable String username) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index");

        List<ClassifiedAd> ads = classifiedAdRepository.findAllByUsername(username);

        mav.addObject("latestAds", ads);
        mav.addObject("s", USER_FILTER + username);

        return mav;
    }

    @RequestMapping(value = "/ad/{id}", method = RequestMethod.GET)
    public ModelAndView showFilteredAds(@PathVariable long id) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("ad_detail");

        ClassifiedAd ad = classifiedAdRepository.findById(id);
        if (ad != null) {
            mav.setViewName("ad_detail");
            mav.addObject("ad", ad);
        } else {
            mav.setViewName("index");
            mav.addObject("error", messageSource.getMessage("ad.controller.error.notexist=", null, LocaleContextHolder.getLocale()));
            mav.addObject("latestAds", classifiedAdService.fetchLatestAds(""));
        }
        return mav;
    }

    @RequestMapping(value = "/ad/{id}", method = RequestMethod.POST)
    public ModelAndView updateAd(@PathVariable Long id,
                                 @ModelAttribute("command") @Valid ClassifiedAdResource request,
                                 BindingResult result) {

        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView("redirect:/ad/{id}/edit");
            mav.addObject("error", "War nix. Mekrste selbst...");
            return mav;
        }

        ModelAndView mav = new ModelAndView("ad_detail");
        ClassifiedAd ad = classifiedAdService.update(request);
        mav.addObject("ad", ad);

        return mav;
    }

    @RequestMapping(value = "/ad/{id}/delete", method = RequestMethod.GET)
    public ModelAndView deleteAd(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/");

        classifiedAdRepository.deleteById(id);
        mav.addObject("deleted", true);

        return mav;
    }


    @RequestMapping(value = "/ad/{id}/edit", method = RequestMethod.GET)
    public ModelAndView editAd(@PathVariable long id) {
        ClassifiedAd ad = classifiedAdRepository.findById(id);
        if (ad == null) {
            throw new NotFound(messageSource.getMessage("ad.controller.error.notexist=", null, LocaleContextHolder.getLocale()));
        }

        ModelAndView mav = new ModelAndView("ad_edit", "command", new ClassifiedAdResource(ad));
        mav.addObject("ad", ad);
        return mav;
    }


    @RequestMapping(value = "/ad/create", method = RequestMethod.GET)
    public ModelAndView createAd() {
        User principal = userService.getPrincipal();
        ClassifiedAd ad = new ClassifiedAd(principal, null, null, 0, new Date());

        ModelAndView mav = new ModelAndView("ad_create", "command", new ClassifiedAdResource(ad));
        mav.addObject("ad", ad);
        return mav;
    }

    @RequestMapping(value = "/ad/create", method = RequestMethod.POST)
    public ModelAndView createAd(@ModelAttribute("command") @Valid ClassifiedAdResource request,
                                 BindingResult result) {
        ModelAndView mav = new ModelAndView();
        if (result.hasErrors()) {
            mav.setViewName("ad_create");
            return mav;
        }

        ClassifiedAd ad = classifiedAdService.create(request);
        mav.setViewName("redirect:/ad/" + ad.getId());
        mav.addObject("ad", ad);

        return mav;
    }
    
    @RequestMapping(value = "/ad/import", method = RequestMethod.GET)
    public ModelAndView createImport() {
        ClassifiedAdFileResource adfile = new ClassifiedAdFileResource(null);
        return new ModelAndView("ad_import", "command", adfile);
    }

    @RequestMapping(value = "/ad/import", method = RequestMethod.POST)
    public ModelAndView importAd(@ModelAttribute("command") @Valid ClassifiedAdFileResource request,
            BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("ad_import");
            return mav;
        }

        ClassifiedAd ad = classifiedAdService.importAd(request);
        if (ad == null) {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("ad_import");
            mav.addObject("importError", messageSource.getMessage("ad.controller.error.noimport=", null, LocaleContextHolder.getLocale()));
            return mav;
        }

        User principal = userService.getPrincipal();
        ad.setOwner(principal);
        
        ModelAndView mav = new ModelAndView("ad_create", "command", new ClassifiedAdResource(ad));
        mav.addObject("ad", ad);
        return mav;
    }

}

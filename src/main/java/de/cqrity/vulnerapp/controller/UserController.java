package de.cqrity.vulnerapp.controller;

import de.cqrity.vulnerapp.domain.CreateUserResource;
import de.cqrity.vulnerapp.domain.User;
import de.cqrity.vulnerapp.domain.UserResource;
import de.cqrity.vulnerapp.repository.UserRepository;
import de.cqrity.vulnerapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView showRegistrationPage() {
        return new ModelAndView("register", "command", new CreateUserResource());
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView registerNewUser(@ModelAttribute("command") @Valid CreateUserResource request, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView("register");
        modelAndView.addObject("username", request.getUsername());
        if (result.hasErrors()) {
            return modelAndView;
        }
        if (!request.getPassword().equals(request.getPassword2())) {
            modelAndView.addObject("error", "Passwörter stimmen nicht überein");
            result.addError(new FieldError("password", "password2", "Passwörter stimmen nicht überein"));
            return modelAndView;
        }
        try {
            userService.save(new User(request.getUsername(), request.getPassword(), userService.findAuthority("USER")));
        } catch (UnsupportedOperationException e) {
            modelAndView.addObject("error", "Benutzer existiert bereits");
            result.addError(new FieldError("username", "username", "Benutzer existiert bereits"));
            return modelAndView;
        }
        modelAndView.addObject("success", true);
        return modelAndView;
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView showEditProfileView() {
        String username = userService.getPrincipal().getUsername();
        User user = userRepository.findByUsername(username);
        return new ModelAndView("profile", "command", new UserResource(user));
    }

    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public ModelAndView editUserProfile(@ModelAttribute("command") @Valid UserResource request, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView("profile");
        if (result.hasErrors()) {
            return modelAndView;
        }
        if (!request.getPassword().equals(request.getPassword2())) {
            modelAndView.addObject("error", "Passwörter stimmen nicht überein");
            result.addError(new FieldError("password", "password2", "Passwörter stimmen nicht überein"));
            return modelAndView;
        }
        try {
            userService.update(request);
        } catch (UnsupportedOperationException e) {
            modelAndView.addObject("error", e.getMessage());
            result.addError(new FieldError("username", "username", e.getMessage()));
            return modelAndView;
        }
        modelAndView.addObject("success", true);
        return modelAndView;
    }

    @RequestMapping("/admin/users/list")
    public ModelAndView getUserList() {
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("users", userService.getUsers());
        return new ModelAndView("/admin/users/list", modelMap);
    }
}

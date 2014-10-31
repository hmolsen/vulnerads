package de.cqrity.vulnerapp.controller.login;

import de.cqrity.vulnerapp.domain.User;
import de.cqrity.vulnerapp.domain.UserCreationRequest;
import de.cqrity.vulnerapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView showRegistrationPage() {
        return new ModelAndView("register", "command", new UserCreationRequest());
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ModelAndView registerNewUser(@ModelAttribute("command") @Valid UserCreationRequest request, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView("register");
        modelAndView.addObject("username", request.getUsername());
        if (result.hasErrors()) {
            return modelAndView;
        }
        if (!request.getPassword().equals(request.getPassword2())) {
            modelAndView.addObject("error", "Passwords do not match");
            return modelAndView;
        }
        try {
            userService.save(new User(request.getUsername(), request.getPassword(), userService.findAuthority("USER")));
        } catch (UnsupportedOperationException e) {
            modelAndView.addObject("error", "User already exists");
            return modelAndView;
        }
        modelAndView.addObject("success", true);
        return modelAndView;
    }
}

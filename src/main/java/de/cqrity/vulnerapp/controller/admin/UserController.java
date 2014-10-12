package de.cqrity.vulnerapp.controller.admin;

import de.cqrity.vulnerapp.domain.Authority;
import de.cqrity.vulnerapp.domain.User;
import de.cqrity.vulnerapp.domain.UserCreationRequest;
import de.cqrity.vulnerapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/users")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/list")
    public ModelAndView getUserList() {
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("users", userService.getUsers());
        return new ModelAndView("/admin/users/list", modelMap);
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView getCreateUserView() {
        return new ModelAndView("/admin/users/create", "form", new UserCreationRequest());
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createUser(@ModelAttribute("form") UserCreationRequest form, BindingResult result) {
        if (result.hasErrors()) {
            return "create";
        }
        try {
            userService.save(new User(form.getUsername(), form.getPassword(), new Authority("USER")));
        } catch (UnsupportedOperationException e) {
            result.reject("user.error.exists");
            return "create";
        }
        return "redirect:list";
    }
}

package de.cqrity.vulnerapp.controller.admin;

import de.cqrity.vulnerapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
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
}

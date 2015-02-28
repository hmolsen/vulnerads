package de.cqrity.vulnerapp.controller;

import de.cqrity.vulnerapp.domain.CreateUserResource;
import de.cqrity.vulnerapp.domain.User;
import de.cqrity.vulnerapp.domain.UserResource;
import de.cqrity.vulnerapp.repository.UserRepository;
import de.cqrity.vulnerapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.sql.ResultSet;
import java.sql.SQLException;

@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    private UserService userService;

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

    @RequestMapping(value = "/userdetail", method = RequestMethod.GET)
    public ModelAndView showEditProfileView(@RequestParam("id") String id) {
        String findById = "SELECT firstname, lastname, phonenumber, town, zip FROM usr WHERE id =" + id;
        User user = jdbcTemplate.queryForObject(findById, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User(null, null, null);
                user.setFirstname(rs.getString("firstname"));
                user.setLastname(rs.getString("lastname"));
                user.setZip(rs.getString("zip"));
                user.setTown(rs.getString("town"));
                user.setPhonenumber(rs.getString("phonenumber"));
                return user;
            }
        });
        return new ModelAndView("userdetail", "command", new UserResource(user));
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

    @RequestMapping(value = "/user/{id}/delete")
    public String deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return "redirect:/admin/users/list";
    }

    @RequestMapping("/admin/users/list")
    public ModelAndView getUserList() {
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("users", userService.getUsers());
        return new ModelAndView("/admin/users/list", modelMap);
    }
}

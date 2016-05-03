package de.cqrity.vulnerapp.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import de.cqrity.vulnerapp.domain.CreateUserResource;
import de.cqrity.vulnerapp.domain.User;
import de.cqrity.vulnerapp.domain.UserResource;
import de.cqrity.vulnerapp.repository.UserRepository;
import de.cqrity.vulnerapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
            userService.save(new User(request.getUsername(),
                    request.getPassword(),
                    userService.findAuthority("USER")));
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
        ModelAndView modelAndView = new ModelAndView("profile", "command", new UserResource(user));
        addAuthorityString(username, modelAndView);
        return modelAndView;
    }

    @RequestMapping(value = "/userdetail", method = RequestMethod.GET)
    public ModelAndView showEditProfileView(@RequestParam("id") String id) {
        String findById = "SELECT username,firstname, lastname, phonenumber, town, zip FROM usr WHERE id =" + id;
        User user = jdbcTemplate.queryForObject(findById, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User(rs.getString("username"), null, null);
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
        } catch (DataIntegrityViolationException e) {
            modelAndView.addObject("error", "Benutzer existiert bereits");
            result.addError(new FieldError("username", "username", "Benutzer existiert bereits"));
            return modelAndView;
        }
        modelAndView.addObject("success", true);
        addAuthorityString(request.getUsername(), modelAndView);
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

    @RequestMapping(value = "/profile/tfasecret.png", method = RequestMethod.GET)
    public void generateQRCode(HttpServletResponse response, Authentication auth) throws WriterException, IOException {
        String otpProtocol = userService.generateOTPProtocol(auth.getName());
        response.setContentType("image/png");
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix matrix = writer.encode(otpProtocol, BarcodeFormat.QR_CODE, 250, 250);
        MatrixToImageWriter.writeToStream(matrix, "PNG", response.getOutputStream());
        response.getOutputStream().flush();
    }

    private void addAuthorityString(String username, ModelAndView modelAndView) {
        try {
            String sql = "SELECT authority.authority " +
                    "FROM authority " +
                    "INNER JOIN usr on usr.authority_id=authority.id " +
                    "WHERE usr.username='" + username + "'";
            List<String> authority = jdbcTemplate.queryForList(sql, String.class);
            if (!authority.isEmpty()) {
                if (authority.contains("USER")) {
                    modelAndView.addObject("authority", "Standard-Benutzer");
                } else {
                    modelAndView.addObject("authority", "Administrator");
                }
            }
        } catch (Exception e) {
            modelAndView.addObject("authority", "Standard-Benutzer");
            modelAndView.addObject("error", "Unvorhergesehener Ausnahmefehler an der Adresse 0x00000000");
        }
    }
}

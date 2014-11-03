package de.cqrity.vulnerapp.controller;


import de.cqrity.vulnerapp.domain.Category;
import de.cqrity.vulnerapp.domain.ClassifiedAd;
import de.cqrity.vulnerapp.domain.User;
import de.cqrity.vulnerapp.repository.ClassifiedAdRepository;
import de.cqrity.vulnerapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Controller
public class JspController {

    @Autowired
    ClassifiedAdRepository classifiedAdRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView showIndex() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index");
        mav.addObject("latestAds", classifiedAdRepository.findAll());

        return mav;
    }

    @RequestMapping(value = "/ads", method = RequestMethod.GET)
    public ModelAndView showFilteredAds(@RequestParam(value = "s", required = false, defaultValue = "") String s) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index");

        String sql = "SELECT * FROM classified_ad WHERE UCASE(title) LIKE UCASE('%" + s + "%')";
        List<ClassifiedAd> ads = jdbcTemplate.query(sql, new RowMapper<ClassifiedAd>() {
            @Override
            public ClassifiedAd mapRow(ResultSet rs, int rowNum) throws SQLException {
                User owner = userRepository.findOne(rs.getLong("OWNER_ID"));
                return new ClassifiedAd(
                        owner,
                        Category.valueOf(rs.getString("CATEGORY")),
                        rs.getString("TITLE"),
                        rs.getString("DESCRIPTION"),
                        rs.getInt("PRICE"));
            }
        });

        mav.addObject("latestAds", ads);
        mav.addObject("s", s);

        return mav;
    }

}

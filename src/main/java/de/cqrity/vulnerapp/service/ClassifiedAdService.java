package de.cqrity.vulnerapp.service;

import de.cqrity.vulnerapp.domain.ClassifiedAd;
import de.cqrity.vulnerapp.domain.ClassifiedAdResource;
import de.cqrity.vulnerapp.domain.User;
import de.cqrity.vulnerapp.repository.ClassifiedAdRepository;
import de.cqrity.vulnerapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Service
public class ClassifiedAdService {

    private static final String UPPER_FN = "UPPER";

    @Autowired
    ClassifiedAdRepository classifiedAdRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ImageService imageService;

    @Autowired
    JdbcTemplate jdbcTemplate;

    public ClassifiedAd update(ClassifiedAdResource request) {
        ClassifiedAd ad = classifiedAdRepository.findOne(request.getId());

        ad.setTitle(request.getTitle());
        ad.setDescription(request.getDescription());
        ad.setPrice(request.getPrice());
        String photofilename = imageService.storeImage(request.getAdphoto(), request.getId());
        ad.setPhotofilename(photofilename);

        return classifiedAdRepository.save(ad);
    }

    public ClassifiedAd create(ClassifiedAdResource request) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ClassifiedAd ad = new ClassifiedAd(principal,
                                           request.getTitle(),
                                           request.getDescription(),
                                           request.getPrice(),
                                           new Date());

        ad = classifiedAdRepository.save(ad); // to get the id

        ad.setPhotofilename(imageService.storeImage(request.getAdphoto(), ad.getId()));

        return classifiedAdRepository.save(ad);
    }

    public List<ClassifiedAd> fetchLatestAds(String searchString) {
        String sql = "SELECT * FROM classified_ad WHERE " + UPPER_FN + "(title) LIKE " + UPPER_FN + "('%" + searchString + "%') " +
                "ORDER BY createdtimestamp DESC";
        return jdbcTemplate.query(sql, new RowMapper<ClassifiedAd>() {
            @Override
            public ClassifiedAd mapRow(ResultSet rs, int rowNum) throws SQLException {
                User owner = userRepository.findOne(rs.getLong("OWNER_ID"));
                ClassifiedAd ad = new ClassifiedAd(
                        owner,
                        rs.getString("TITLE"),
                        rs.getString("DESCRIPTION"),
                        rs.getInt("PRICE"),
                        rs.getTimestamp("CREATEDTIMESTAMP"));
                ad.setId(rs.getLong("ID"));
                ad.setPhotofilename(rs.getString("PHOTOFILENAME"));
                return ad;
            }
        });
    }

}

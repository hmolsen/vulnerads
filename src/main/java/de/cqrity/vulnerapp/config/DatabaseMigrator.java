package de.cqrity.vulnerapp.config;

import de.cqrity.vulnerapp.domain.User;
import de.cqrity.vulnerapp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class DatabaseMigrator {

    private static final Logger log = LoggerFactory.getLogger(DatabaseMigrator.class);

    @Autowired
    UserRepository userRepository;

    private static final boolean active = false;

    @PostConstruct
    private void init() {
        if (!active) return;

        List<User> users = userRepository.findAll();
        for (User user : users) {
            String md5Password = user.getPassword();
            String bcryptPassword = new BCryptPasswordEncoder().encode(md5Password);
            user.setPassword(bcryptPassword);
            userRepository.save(user);
            log.info("Migrating user password " + md5Password + " to " + bcryptPassword);
        }

    }
}

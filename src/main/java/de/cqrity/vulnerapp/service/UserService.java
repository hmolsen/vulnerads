package de.cqrity.vulnerapp.service;

import de.cqrity.vulnerapp.domain.Authority;
import de.cqrity.vulnerapp.domain.User;
import de.cqrity.vulnerapp.repository.AuthorityRepository;
import de.cqrity.vulnerapp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    public List<User> getUsers() {
        LOG.debug("get list of users");
        return userRepository.findAll();
    }

    public Authority findAuthority(String name) {
        return authorityRepository.findByName(name);
    }

    public User save(final User user) {
        if (userRepository.exists(user.getId())) {
            throw new UnsupportedOperationException("User already exists");
        }
        return userRepository.save(user);
    }

}

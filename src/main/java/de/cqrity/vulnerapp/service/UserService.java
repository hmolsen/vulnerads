package de.cqrity.vulnerapp.service;

import de.cqrity.vulnerapp.domain.Authority;
import de.cqrity.vulnerapp.domain.User;
import de.cqrity.vulnerapp.domain.UserResource;
import de.cqrity.vulnerapp.repository.AuthorityRepository;
import de.cqrity.vulnerapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public Authority findAuthority(String name) {
        return authorityRepository.findByName(name);
    }

    public User save(final User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new UnsupportedOperationException("Benutzer existiert bereits");
        }
        return userRepository.save(user);
    }

    public User update(UserResource request) {
        User user = userRepository.findOne(request.getUserid());

        if (usernameShallBeChanged(user.getUsername(), request.getUsername())
                && usernameAlreadyExists(request.getUsername())) {
            throw new UnsupportedOperationException("Benutzername wird bereits verwendet");
        }
        user.setUsername(request.getUsername());
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setCreditcardnumber(request.getCreditcardnumber());
        if (!request.getPassword().isEmpty()) {
            user.setPassword(request.getPassword());
        }

        return userRepository.save(user);
    }

    private boolean usernameShallBeChanged(String oldUsername, String newUsername) {
        return !oldUsername.equals(newUsername);
    }

    private boolean usernameAlreadyExists(String username) {
        return userRepository.findByUsername(username) != null;
    }
}

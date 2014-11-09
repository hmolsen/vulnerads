package de.cqrity.vulnerapp.service;

import de.cqrity.vulnerapp.domain.Authority;
import de.cqrity.vulnerapp.domain.User;
import de.cqrity.vulnerapp.domain.UserResource;
import de.cqrity.vulnerapp.exception.NotFound;
import de.cqrity.vulnerapp.repository.AuthorityRepository;
import de.cqrity.vulnerapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public Authority findAuthority(String authority) {
        return authorityRepository.findByAuthority(authority);
    }

    public User save(final User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new UnsupportedOperationException("Benutzer existiert bereits");
        }
        return userRepository.save(user);
    }

    public User update(UserResource request) {
        User user = userRepository.findOne(request.getUserid());

        if (user == null) {
            throw new NotFound("Benutzer mit ID " + request.getUserid() + " existiert nicht!");
        }

        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setCreditcardnumber(request.getCreditcardnumber());
        user.setPhonenumber(request.getPhonenumber());
        user.setZip(request.getZip());
        user.setTown(request.getTown());
        if (!request.getPassword().isEmpty()) {
            user.setPassword(request.getPassword());
        }

        User updatedUser = userRepository.save(user);
        updatePrincipal();
        return updatedUser;
    }

    public User getPrincipal() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private void updatePrincipal() {
        String username = getPrincipal().getUsername();
        User user = userRepository.findByUsername(username);
        Authentication auth = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

}

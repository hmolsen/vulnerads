package de.cqrity.vulnerapp.service;

import de.cqrity.vulnerapp.domain.User;
import de.cqrity.vulnerapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class VulnerappUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private MessageSource messageSource;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(messageSource.getMessage("error.usrnotfound.one", null, LocaleContextHolder.getLocale()) + username + messageSource.getMessage("error.usrnotfound.two", null, LocaleContextHolder.getLocale()));
        }
        return user;
    }
}

package de.cqrity.vulnerapp.service;

import de.cqrity.vulnerapp.config.DatabaseEncryptor;
import de.cqrity.vulnerapp.domain.Authority;
import de.cqrity.vulnerapp.domain.ClassifiedAd;
import de.cqrity.vulnerapp.domain.User;
import de.cqrity.vulnerapp.domain.UserResource;
import de.cqrity.vulnerapp.exception.NotFound;
import de.cqrity.vulnerapp.repository.AuthorityRepository;
import de.cqrity.vulnerapp.repository.ClassifiedAdRepository;
import de.cqrity.vulnerapp.repository.UserRepository;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private ClassifiedAdRepository classifiedAdRepository;

    @Autowired
    private MessageSource messageSource;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public Authority findAuthority(String authority) {
        return authorityRepository.findByAuthority(authority);
    }

    public User save(final User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new UnsupportedOperationException(messageSource.getMessage("error.existinguser", null, LocaleContextHolder.getLocale()));
        }
        return userRepository.save(user);
    }

    public User update(UserResource request) {
        User user = userRepository.findById(request.getUserid());

        if (user == null) {
            throw new NotFound(messageSource.getMessage("error.usrnotfound.one", null, LocaleContextHolder.getLocale()) + request.getUserid() + messageSource.getMessage("error.usrnotfound.two", null, LocaleContextHolder.getLocale()));
        }

        user.setUsername(request.getUsername());
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setCreditcardnumber(request.getCreditcardnumber());
        user.setPhonenumber(request.getPhonenumber());
        user.setZip(request.getZip());
        user.setTown(request.getTown());
        if (!request.getPassword().isEmpty()) {
            user.setPassword(DigestUtils.md5Hex(request.getPassword()));
        }
        user.setTfaEnabled(request.isTfaEnabled());

        User updatedUser = userRepository.save(user);
        updatePrincipal();
        return updatedUser;
    }

    public User getPrincipal() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private void updatePrincipal() {
        long userId = getPrincipal().getId();
        User user = userRepository.findById(userId);
        Authentication auth = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    public void delete(long id) {
        User userToDelete = userRepository.findById(id);
        List<ClassifiedAd> allByUsername = classifiedAdRepository.findAllByUsername(userToDelete.getUsername());
        classifiedAdRepository.deleteAll(allByUsername);
        userRepository.delete(userToDelete);
    }

    public String generateOTPProtocol(String userName) {
        User user = userRepository.findByUsername(userName);
        String unencryptedTfaSecret = generateNewTfaSecret();
        user.setEncryptedTfaSecret(DatabaseEncryptor.getInstance().encrypt(unencryptedTfaSecret));
        return String.format("otpauth://totp/Vulnerads%%20Kleinanzeigen:%s?secret=%s&issuer=Vulnerads%%20Kleinanzeigen", userName, unencryptedTfaSecret);
    }

    public String generateNewTfaSecret() {
        byte[] buffer = new byte[10];
        new SecureRandom().nextBytes(buffer);
        return new String(new Base32().encode(buffer));
    }
}

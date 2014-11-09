package de.cqrity.vulnerapp.service;

import de.cqrity.vulnerapp.domain.ClassifiedAd;
import de.cqrity.vulnerapp.domain.ClassifiedAdResource;
import de.cqrity.vulnerapp.domain.User;
import de.cqrity.vulnerapp.repository.ClassifiedAdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ClassifiedAdService {

    @Autowired
    ClassifiedAdRepository classifiedAdRepository;

    public ClassifiedAd update(ClassifiedAdResource request) {
        ClassifiedAd ad = classifiedAdRepository.findOne(request.getId());

        ad.setTitle(request.getTitle());
        ad.setDescription(request.getDescription());
        ad.setPrice(request.getPrice());

        return classifiedAdRepository.save(ad);
    }

    public ClassifiedAd create(ClassifiedAdResource request) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ClassifiedAd ad = new ClassifiedAd(principal,
                                           request.getTitle(),
                                           request.getDescription(),
                                           request.getPrice(),
                                           new Date());

        return classifiedAdRepository.save(ad);
    }
}

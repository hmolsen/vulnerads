package de.cqrity.vulnerapp.service;

import de.cqrity.vulnerapp.domain.ClassifiedAd;
import de.cqrity.vulnerapp.domain.ClassifiedAdResource;
import de.cqrity.vulnerapp.repository.ClassifiedAdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}

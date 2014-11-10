package de.cqrity.vulnerapp.service;

import com.google.common.io.Files;
import de.cqrity.vulnerapp.domain.ClassifiedAd;
import de.cqrity.vulnerapp.domain.ClassifiedAdResource;
import de.cqrity.vulnerapp.domain.User;
import de.cqrity.vulnerapp.repository.ClassifiedAdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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

        MultipartFile adphoto = request.getAdphoto();
        String filename = adphoto.getOriginalFilename();
        File photoFolder = new File(System.getProperty("user.home"), "vulnerapp_photos");
        if (!photoFolder.exists()) {
            photoFolder.mkdirs();
        }
        File photoFile = new File(photoFolder, filename);
        try {
            Files.write(adphoto.getBytes(), photoFile);
            ad.setPhotofilename(filename);
        } catch (IOException e) {
            ad.setPhotofilename(null);
            e.printStackTrace();
        }


        return classifiedAdRepository.save(ad);
    }
}

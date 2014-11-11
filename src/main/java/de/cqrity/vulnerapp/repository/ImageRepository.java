package de.cqrity.vulnerapp.repository;

import de.cqrity.vulnerapp.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
    public Image findByName(String name);
}

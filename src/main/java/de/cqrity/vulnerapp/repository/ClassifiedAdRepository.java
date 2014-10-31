package de.cqrity.vulnerapp.repository;

import de.cqrity.vulnerapp.domain.ClassifiedAd;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassifiedAdRepository extends JpaRepository<ClassifiedAd, Long> {
}

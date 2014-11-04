package de.cqrity.vulnerapp.repository;

import de.cqrity.vulnerapp.domain.ClassifiedAd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClassifiedAdRepository extends JpaRepository<ClassifiedAd, Long> {

    @Query("SELECT ad FROM ClassifiedAd ad WHERE LOWER(ad.owner.username) = LOWER(:username)")
    public List<ClassifiedAd> findAllByUsername(@Param("username") String username);
}

package de.cqrity.vulnerapp.repository;

import de.cqrity.vulnerapp.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Authority findByAuthority(String authority);
}

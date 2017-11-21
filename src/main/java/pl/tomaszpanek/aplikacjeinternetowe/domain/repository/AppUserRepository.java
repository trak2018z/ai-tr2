package pl.tomaszpanek.aplikacjeinternetowe.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.tomaszpanek.aplikacjeinternetowe.domain.model.AppUser;

import java.util.UUID;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, UUID> {

}

package pl.tomaszpanek.aplikacjeinternetowe.domain.repository.forum;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.tomaszpanek.aplikacjeinternetowe.domain.model.forum.SubCategory;

import java.util.UUID;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, UUID> {

}

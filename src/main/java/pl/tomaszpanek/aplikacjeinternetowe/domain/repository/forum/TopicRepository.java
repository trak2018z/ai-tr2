package pl.tomaszpanek.aplikacjeinternetowe.domain.repository.forum;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.tomaszpanek.aplikacjeinternetowe.domain.model.forum.Topic;

import java.util.UUID;

@Repository
public interface TopicRepository extends JpaRepository<Topic, UUID> {

}

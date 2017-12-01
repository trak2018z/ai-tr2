package pl.tomaszpanek.aplikacjeinternetowe.domain.model.forum;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.tomaszpanek.aplikacjeinternetowe.domain.model.BaseEntity;
import pl.tomaszpanek.aplikacjeinternetowe.domain.model.user.AppUser;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "COMMENT")
public class Comment extends BaseEntity {

    @Column(name = "content", columnDefinition = "text", nullable = false, length = 2048)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id", nullable = false)
    private AppUser owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;
}

package pl.tomaszpanek.aplikacjeinternetowe.domain.model.forum;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.tomaszpanek.aplikacjeinternetowe.domain.model.BaseEntity;
import pl.tomaszpanek.aplikacjeinternetowe.domain.model.user.AppUser;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "TOPIC")
public class Topic extends BaseEntity {

    @Column(name = "name", length = 64, nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id", nullable = false)
    private AppUser owner;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subcategory_id", nullable = false)
    private SubCategory subCategory;
}

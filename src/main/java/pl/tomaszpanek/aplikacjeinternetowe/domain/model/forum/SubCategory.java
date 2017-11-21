package pl.tomaszpanek.aplikacjeinternetowe.domain.model.forum;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.tomaszpanek.aplikacjeinternetowe.domain.model.AppUser;
import pl.tomaszpanek.aplikacjeinternetowe.domain.model.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "SUB_CATEGORY")
public class SubCategory extends BaseEntity {

    @Column(name = "name", length = 64, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id", nullable = false)
    private AppUser owner;

    @OneToMany(mappedBy = "subCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Topic> topics = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
}

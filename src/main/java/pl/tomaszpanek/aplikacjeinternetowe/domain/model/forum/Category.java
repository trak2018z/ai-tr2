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
@Table(name = "CATEGORY")
public class Category extends BaseEntity {

    @Column(name = "name", length = 64, unique = true, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "app_user_id", nullable = false)
    private AppUser owner;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<SubCategory> subCategories = new ArrayList<>();
}

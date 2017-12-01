package pl.tomaszpanek.aplikacjeinternetowe.domain.model.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.tomaszpanek.aplikacjeinternetowe.domain.model.BaseEntity;
import pl.tomaszpanek.aplikacjeinternetowe.domain.model.forum.Category;
import pl.tomaszpanek.aplikacjeinternetowe.domain.model.forum.Comment;
import pl.tomaszpanek.aplikacjeinternetowe.domain.model.forum.SubCategory;
import pl.tomaszpanek.aplikacjeinternetowe.domain.model.forum.Topic;
import pl.tomaszpanek.aplikacjeinternetowe.domain.security.PasswordFactory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "APP_USER")
public class AppUser extends BaseEntity {

    @Column(name = "username", length = 64, nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", length = 64, nullable = false, unique = true)
    private String email;

    @Column(name = "first_name", length = 64)
    private String firstName;

    @Column(name = "last_name", length = 64)
    private String lastName;

    @Column(name = "activated", nullable = false)
    private boolean activated = false;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Category> categories = new ArrayList<>();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubCategory> subCategories = new ArrayList<>();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Topic> topics = new ArrayList<>();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comment = new ArrayList<>();

    public void setUsername(String username) {
        if (username != null) {
            this.username = username.toLowerCase();
        } else {
            this.username = null;
        }
    }

    public void setPassword(String password) {
        /* JWT must have default setter for password */
        this.password = password;
    }

    public void setEncodedPassword(String password) {
        this.password = PasswordFactory.bCryptEncode(password);
    }

    public void setEmail(String email) {
        if (email != null) {
            this.email = email.toLowerCase();
        } else {
            this.email = null;
        }
    }
}

package pl.tomaszpanek.aplikacjeinternetowe.domain.model;

import org.hibernate.Hibernate;
import pl.tomaszpanek.aplikacjeinternetowe.domain.model.forum.Category;
import pl.tomaszpanek.aplikacjeinternetowe.domain.model.forum.Comment;
import pl.tomaszpanek.aplikacjeinternetowe.domain.model.forum.SubCategory;
import pl.tomaszpanek.aplikacjeinternetowe.domain.model.forum.Topic;
import pl.tomaszpanek.aplikacjeinternetowe.domain.model.user.AppUser;
import pl.tomaszpanek.aplikacjeinternetowe.webui.dto.forum.CategoryDto;
import pl.tomaszpanek.aplikacjeinternetowe.webui.dto.forum.CommentDto;
import pl.tomaszpanek.aplikacjeinternetowe.webui.dto.forum.SubCategoryDto;
import pl.tomaszpanek.aplikacjeinternetowe.webui.dto.forum.TopicDto;
import pl.tomaszpanek.aplikacjeinternetowe.webui.dto.user.AppUserDto;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

public class Mapper {

    private Mapper() {

    }

    public static AppUserDto map(AppUser appUser) {
        return Optional.ofNullable(appUser)
                .map(u -> AppUserDto
                        .builder()
                        .username(u.getUsername())
                        .email(u.getEmail())
                        .firstName(u.getFirstName())
                        .lastName(u.getLastName())
                        .activated(u.isActivated())
                        .build())
                .orElse(new AppUserDto());
    }

    public static CategoryDto map(Category category) {
        return Optional.ofNullable(category)
                .map(c -> CategoryDto
                        .builder()
                        .id(c.getId())
                        .name(c.getName())
                        .owner(c.getOwner().getUsername())
                        .subCategories(Hibernate.isInitialized(c.getSubCategories()) ? c
                                .getSubCategories()
                                .stream()
                                .map(Mapper::map)
                                .collect(Collectors.toList()) : new ArrayList<>())
                        .build())
                .orElse(new CategoryDto());
    }

    public static SubCategoryDto map(SubCategory subCategory) {
        return Optional.ofNullable(subCategory)
                .map(sb ->
                        SubCategoryDto
                                .builder()
                                .id(sb.getId())
                                .name(sb.getName())
                                .owner(sb.getOwner().getUsername())
                                .topics(Hibernate.isInitialized(sb.getTopics()) ? sb
                                        .getTopics()
                                        .stream()
                                        .map(Mapper::map)
                                        .collect(Collectors.toList()) : new ArrayList<>())
                                .build())
                .orElse(new SubCategoryDto());
    }

    public static TopicDto map(Topic topic) {
        return Optional.ofNullable(topic)
                .map(t -> TopicDto
                        .builder()
                        .id(t.getId())
                        .name(t.getName())
                        .owner(t.getOwner().getUsername())
                        .commentsDtos(Hibernate.isInitialized(t.getComments()) ? t
                                .getComments()
                                .stream()
                                .map(Mapper::map)
                                .collect(Collectors.toList()) : new ArrayList<>())
                        .build())
                .orElse(new TopicDto());
    }

    public static CommentDto map(Comment comment) {
        return Optional.ofNullable(comment)
                .map(c -> CommentDto
                        .builder()
                        .id(c.getId())
                        .content(c.getContent())
                        .owner(c.getOwner().getUsername())
                        .build())
                .orElse(new CommentDto());
    }
}

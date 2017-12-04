package pl.tomaszpanek.aplikacjeinternetowe.domain.service;

import org.springframework.stereotype.Service;
import pl.tomaszpanek.aplikacjeinternetowe.domain.model.forum.SubCategory;
import pl.tomaszpanek.aplikacjeinternetowe.domain.model.forum.Topic;
import pl.tomaszpanek.aplikacjeinternetowe.domain.model.user.AppUser;
import pl.tomaszpanek.aplikacjeinternetowe.domain.repository.forum.SubCategoryRepository;
import pl.tomaszpanek.aplikacjeinternetowe.domain.repository.forum.TopicRepository;
import pl.tomaszpanek.aplikacjeinternetowe.domain.repository.user.AppUserRepository;
import pl.tomaszpanek.aplikacjeinternetowe.webui.dto.forum.TopicDto;

import java.util.Optional;
import java.util.UUID;

@Service
public class TopicService {

    private final TopicRepository topicRepository;
    private final AppUserRepository appUserRepository;
    private final SubCategoryRepository subCategoryRepository;

    public TopicService(TopicRepository topicRepository, AppUserRepository appUserRepository,
                        SubCategoryRepository subCategoryRepository) {
        this.topicRepository = topicRepository;
        this.appUserRepository = appUserRepository;
        this.subCategoryRepository = subCategoryRepository;
    }

    public Topic save(TopicDto topicDto) {
        AppUser appUser = appUserRepository.findByUsername(topicDto.getOwner());
        SubCategory subCategory = subCategoryRepository.findOne(topicDto.getSubCategoryId());
        Topic topic = new Topic();
        topic.setName(topicDto.getName());
        topic.setOwner(appUser);
        topic.setSubCategory(subCategory);
        return topicRepository.save(topic);
    }

    public Topic update(TopicDto topicDto) {
        Topic topic = topicRepository.findOne(topicDto.getId());
        setProperties(topicDto, topic);
        return topicRepository.save(topic);
    }

    private void setProperties(TopicDto topicDto, Topic topic) {
        Optional<String> name = Optional.ofNullable(topicDto.getName());
        Optional<String> owner = Optional.ofNullable(topicDto.getOwner());
        Optional<UUID> subCategoryId = Optional.ofNullable(topicDto.getSubCategoryId());

        name.ifPresent(topic::setName);
        if (owner.isPresent()) {
            Optional<AppUser> appUser = Optional.ofNullable(appUserRepository.findByUsername(owner.get()));
            appUser.ifPresent(topic::setOwner);
        }
        if (subCategoryId.isPresent()) {
            Optional<SubCategory> subCategory = Optional.ofNullable(subCategoryRepository.findOne(subCategoryId.get()));
            subCategory.ifPresent(topic::setSubCategory);
        }
    }
}

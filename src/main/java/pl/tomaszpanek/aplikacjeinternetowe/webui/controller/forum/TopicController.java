package pl.tomaszpanek.aplikacjeinternetowe.webui.controller.forum;

import org.hibernate.Hibernate;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.tomaszpanek.aplikacjeinternetowe.domain.model.Mapper;
import pl.tomaszpanek.aplikacjeinternetowe.domain.model.forum.Topic;
import pl.tomaszpanek.aplikacjeinternetowe.domain.service.TopicService;
import pl.tomaszpanek.aplikacjeinternetowe.webui.controller.Mappings;
import pl.tomaszpanek.aplikacjeinternetowe.webui.dto.forum.TopicDto;

@RestController
@RequestMapping(Mappings.TOPIC)
public class TopicController {

    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addTopic(@RequestBody @Validated TopicDto topicDto) {
        topicService.save(topicDto);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTopic(@RequestBody @Validated TopicDto topicDto) {
        topicService.update(topicDto);
    }

    @GetMapping("/{id}")
    public TopicDto getSubCategoryById(@PathVariable("id") Topic topic) {
        Hibernate.initialize(topic.getComments());
        return Mapper.map(topic);
    }
}

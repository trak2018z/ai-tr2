package pl.tomaszpanek.aplikacjeinternetowe.domain.service;

import org.springframework.stereotype.Service;
import pl.tomaszpanek.aplikacjeinternetowe.domain.model.forum.Comment;
import pl.tomaszpanek.aplikacjeinternetowe.domain.model.forum.Topic;
import pl.tomaszpanek.aplikacjeinternetowe.domain.model.user.AppUser;
import pl.tomaszpanek.aplikacjeinternetowe.domain.repository.forum.CommentRepository;
import pl.tomaszpanek.aplikacjeinternetowe.domain.repository.forum.TopicRepository;
import pl.tomaszpanek.aplikacjeinternetowe.domain.repository.user.AppUserRepository;
import pl.tomaszpanek.aplikacjeinternetowe.webui.dto.forum.CommentDto;

import java.util.Optional;

@Service
public class CommentService {

    private CommentRepository commentRepository;
    private TopicRepository topicRepository;
    private AppUserRepository appUserRepository;

    public CommentService(CommentRepository commentRepository, TopicRepository topicRepository, AppUserRepository appUserRepository) {
        this.commentRepository = commentRepository;
        this.topicRepository = topicRepository;
        this.appUserRepository = appUserRepository;
    }

    public Comment save(CommentDto commentDto) {
        Topic topic = topicRepository.findOne(commentDto.getTopicId());
        AppUser appUser = appUserRepository.findByUsername(commentDto.getOwner());
        Comment comment = new Comment();
        comment.setContent(commentDto.getContent());
        comment.setTopic(topic);
        comment.setOwner(appUser);
        return commentRepository.save(comment);
    }

    public Comment update(CommentDto commentDto) {
        Comment comment = commentRepository.findOne(commentDto.getId());
        Optional<String> text = Optional.ofNullable(commentDto.getContent());
        text.ifPresent(comment::setContent);
        return commentRepository.save(comment);
    }
}

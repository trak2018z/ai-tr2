package pl.tomaszpanek.aplikacjeinternetowe.webui.controller.forum;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.tomaszpanek.aplikacjeinternetowe.domain.service.CommentService;
import pl.tomaszpanek.aplikacjeinternetowe.webui.controller.Mappings;
import pl.tomaszpanek.aplikacjeinternetowe.webui.dto.forum.CommentDto;

@RestController
@RequestMapping(Mappings.COMMENT)
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addComment(@RequestBody @Validated CommentDto commentDto) {
        commentService.save(commentDto);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateComment(@RequestBody @Validated CommentDto commentDto) {
        commentService.update(commentDto);
    }
}

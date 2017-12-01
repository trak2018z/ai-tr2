package pl.tomaszpanek.aplikacjeinternetowe.webui.dto.forum;

import lombok.*;

import java.util.UUID;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private UUID id;
    private String content;
    private String owner;
    private UUID topicId;
}

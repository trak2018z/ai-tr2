package pl.tomaszpanek.aplikacjeinternetowe.webui.dto.forum;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopicDto {

    private UUID id;
    private String name;
    private String owner;
    private List<CommentDto> commentsDtos;
    private UUID subCategoryId;
}

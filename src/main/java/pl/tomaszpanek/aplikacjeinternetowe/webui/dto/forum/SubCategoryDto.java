package pl.tomaszpanek.aplikacjeinternetowe.webui.dto.forum;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubCategoryDto {

    private UUID id;
    private String name;
    private String description;
    private String owner;
    private List<TopicDto> topics;
    private UUID categoryId;
}

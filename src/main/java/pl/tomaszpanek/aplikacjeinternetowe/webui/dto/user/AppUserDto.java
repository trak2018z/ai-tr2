package pl.tomaszpanek.aplikacjeinternetowe.webui.dto.user;

import lombok.*;

import java.util.UUID;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppUserDto {

    private UUID id;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private Boolean activated;
}

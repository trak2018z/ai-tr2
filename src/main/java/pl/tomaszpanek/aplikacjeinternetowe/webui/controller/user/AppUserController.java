package pl.tomaszpanek.aplikacjeinternetowe.webui.controller.user;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.tomaszpanek.aplikacjeinternetowe.domain.model.Mapper;
import pl.tomaszpanek.aplikacjeinternetowe.domain.repository.user.AppUserRepository;
import pl.tomaszpanek.aplikacjeinternetowe.domain.service.user.AppUserService;
import pl.tomaszpanek.aplikacjeinternetowe.webui.controller.Mappings;
import pl.tomaszpanek.aplikacjeinternetowe.webui.dto.user.AppUserDto;

@RestController
@RequestMapping(Mappings.APP_USER)
public class AppUserController {

    private final AppUserService appUserService;
    private final AppUserRepository appUserRepository;

    public AppUserController(AppUserService appUserService, AppUserRepository appUserRepository) {
        this.appUserService = appUserService;
        this.appUserRepository = appUserRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addAppUser(@RequestBody @Validated AppUserDto appUserDto) {
        appUserService.save(appUserDto);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(@RequestBody @Validated AppUserDto appUserDto) {
        appUserService.update(appUserDto);
    }

    @GetMapping("/{username}")
    public AppUserDto getUserById(@PathVariable String username) {
        return Mapper.map(appUserRepository.findByUsername(username));
    }
}

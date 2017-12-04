package pl.tomaszpanek.aplikacjeinternetowe.domain.service.user;

import org.springframework.stereotype.Service;
import pl.tomaszpanek.aplikacjeinternetowe.domain.model.user.AppUser;
import pl.tomaszpanek.aplikacjeinternetowe.domain.repository.user.AppUserRepository;
import pl.tomaszpanek.aplikacjeinternetowe.webui.dto.user.AppUserDto;

import java.util.Optional;

@Service
public class AppUserService {

    private final AppUserRepository appUserRepository;

    public AppUserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public AppUser save(AppUserDto appUserDto) {
        AppUser appUser = new AppUser();
        appUser.setUsername(appUserDto.getUsername());
        appUser.setEncodedPassword(appUserDto.getPassword());
        appUser.setEmail(appUserDto.getEmail());
        appUser.setFirstName(appUserDto.getFirstName());
        appUser.setLastName(appUserDto.getLastName());
        appUser.setActivated(true);
        return appUserRepository.save(appUser);
    }

    public AppUser update(AppUserDto appUserDto) {
        AppUser appUser = appUserRepository.findByUsername(appUserDto.getUsername());
        Optional<String> password = Optional.ofNullable(appUserDto.getPassword());
        Optional<String> firstName = Optional.ofNullable(appUserDto.getFirstName());
        Optional<String> lastName = Optional.ofNullable(appUserDto.getLastName());
        password.ifPresent(appUser::setEncodedPassword);
        firstName.ifPresent(appUser::setFirstName);
        lastName.ifPresent(appUser::setLastName);
        return appUserRepository.save(appUser);
    }
}

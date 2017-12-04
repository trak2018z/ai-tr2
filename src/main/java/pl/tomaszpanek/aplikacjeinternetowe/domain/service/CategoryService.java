package pl.tomaszpanek.aplikacjeinternetowe.domain.service;

import org.springframework.stereotype.Service;
import pl.tomaszpanek.aplikacjeinternetowe.domain.model.Mapper;
import pl.tomaszpanek.aplikacjeinternetowe.domain.model.forum.Category;
import pl.tomaszpanek.aplikacjeinternetowe.domain.model.user.AppUser;
import pl.tomaszpanek.aplikacjeinternetowe.domain.repository.forum.CategoryRepository;
import pl.tomaszpanek.aplikacjeinternetowe.domain.repository.user.AppUserRepository;
import pl.tomaszpanek.aplikacjeinternetowe.webui.dto.forum.CategoryDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;
    private AppUserRepository appUserRepository;

    public CategoryService(CategoryRepository categoryRepository, AppUserRepository appUserRepository) {
        this.categoryRepository = categoryRepository;
        this.appUserRepository = appUserRepository;
    }

    public Category save(CategoryDto categoryDto) {
        AppUser ownerEntity = appUserRepository.findByUsername(categoryDto.getOwner());
        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setOwner(ownerEntity);
        category.setDescription(category.getDescription());
        return categoryRepository.save(category);
    }

    public Category update(CategoryDto categoryDto) {
        Category category = categoryRepository.findOne(categoryDto.getId());
        Optional<String> owner = Optional.ofNullable(categoryDto.getOwner());
        Optional<String> name = Optional.ofNullable(categoryDto.getName());
        Optional<String> description = Optional.ofNullable(categoryDto.getDescription());
        if (owner.isPresent()) {
            Optional<AppUser> ownerEntity = Optional.ofNullable(appUserRepository.findByUsername(owner.get()));
            ownerEntity.ifPresent(category::setOwner);
        }
        name.ifPresent(category::setName);
        description.ifPresent(category::setDescription);
        return categoryRepository.save(category);
    }

    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream().map(Mapper::map).collect(Collectors.toList());
    }
}
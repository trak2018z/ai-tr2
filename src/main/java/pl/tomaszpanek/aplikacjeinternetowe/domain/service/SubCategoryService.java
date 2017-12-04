package pl.tomaszpanek.aplikacjeinternetowe.domain.service;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import pl.tomaszpanek.aplikacjeinternetowe.domain.model.Mapper;
import pl.tomaszpanek.aplikacjeinternetowe.domain.model.forum.Category;
import pl.tomaszpanek.aplikacjeinternetowe.domain.model.forum.SubCategory;
import pl.tomaszpanek.aplikacjeinternetowe.domain.model.user.AppUser;
import pl.tomaszpanek.aplikacjeinternetowe.domain.repository.forum.CategoryRepository;
import pl.tomaszpanek.aplikacjeinternetowe.domain.repository.forum.SubCategoryRepository;
import pl.tomaszpanek.aplikacjeinternetowe.domain.repository.user.AppUserRepository;
import pl.tomaszpanek.aplikacjeinternetowe.webui.dto.forum.SubCategoryDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SubCategoryService {

    private final SubCategoryRepository subCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final AppUserRepository appUserRepository;

    public SubCategoryService(SubCategoryRepository subCategoryRepository, CategoryRepository categoryRepository,
                              AppUserRepository appUserRepository) {
        this.subCategoryRepository = subCategoryRepository;
        this.categoryRepository = categoryRepository;
        this.appUserRepository = appUserRepository;
    }

    public SubCategory save(SubCategoryDto subCategoryDto) {
        SubCategory subCategory = new SubCategory();
        Category category = categoryRepository.findOne(subCategoryDto.getCategoryId());
        AppUser owner = appUserRepository.findByUsername(subCategoryDto.getOwner());
        subCategory.setCategory(category);
        subCategory.setOwner(owner);
        subCategory.setName(subCategoryDto.getName());
        subCategory.setDescription(subCategoryDto.getDescription());
        return subCategoryRepository.save(subCategory);
    }

    public SubCategory update(SubCategoryDto subCategoryDto) {
        SubCategory subCategory = subCategoryRepository.findOne(subCategoryDto.getId());
        setProperties(subCategoryDto, subCategory);
        return subCategoryRepository.save(subCategory);
    }

    private void setProperties(SubCategoryDto subCategoryDto, SubCategory subCategory) {
        Optional<UUID> categoryId = Optional.ofNullable(subCategoryDto.getCategoryId());
        Optional<String> owner = Optional.ofNullable(subCategoryDto.getOwner());
        Optional<String> name = Optional.ofNullable(subCategoryDto.getName());
        Optional<String> description = Optional.ofNullable(subCategoryDto.getDescription());
        if (owner.isPresent()) {
            Optional<AppUser> subCategoryOwner = Optional.ofNullable(appUserRepository.findByUsername(owner.get()));
            subCategoryOwner.ifPresent(subCategory::setOwner);
        }
        if (categoryId.isPresent()) {
            Optional<Category> category = Optional.ofNullable(categoryRepository.findOne(categoryId.get()));
            category.ifPresent(subCategory::setCategory);
        }
        description.ifPresent(subCategory::setDescription);
        name.ifPresent(subCategory::setName);
    }

    public List<SubCategoryDto> findAll() {
        return subCategoryRepository.findAll().stream().peek(subCategory -> Hibernate.initialize(subCategory.getTopics())).map(Mapper::map).collect(Collectors.toList());
    }
}

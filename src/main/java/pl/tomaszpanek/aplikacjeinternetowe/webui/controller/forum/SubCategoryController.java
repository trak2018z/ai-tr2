package pl.tomaszpanek.aplikacjeinternetowe.webui.controller.forum;

import org.hibernate.Hibernate;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.tomaszpanek.aplikacjeinternetowe.domain.model.Mapper;
import pl.tomaszpanek.aplikacjeinternetowe.domain.model.forum.SubCategory;
import pl.tomaszpanek.aplikacjeinternetowe.domain.service.SubCategoryService;
import pl.tomaszpanek.aplikacjeinternetowe.webui.controller.Mappings;
import pl.tomaszpanek.aplikacjeinternetowe.webui.dto.forum.SubCategoryDto;

import java.util.List;

@RestController
@RequestMapping(Mappings.SUB_CATEGORY)
public class SubCategoryController {

    private final SubCategoryService subCategoryService;

    public SubCategoryController(SubCategoryService subCategoryService) {
        this.subCategoryService = subCategoryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addSubCategory(@RequestBody @Validated SubCategoryDto subCategoryDto) {
        subCategoryService.save(subCategoryDto);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateSubCategory(@RequestBody @Validated SubCategoryDto subCategoryDto) {
        subCategoryService.update(subCategoryDto);
    }

    @GetMapping
    public List<SubCategoryDto> getSubcategories() {
        return subCategoryService.findAll();
    }

    @GetMapping("/{id}")
    public SubCategoryDto getSubCategoryById(@PathVariable("id") SubCategory subCategory) {
        Hibernate.initialize(subCategory.getTopics());
        return Mapper.map(subCategory);
    }
}

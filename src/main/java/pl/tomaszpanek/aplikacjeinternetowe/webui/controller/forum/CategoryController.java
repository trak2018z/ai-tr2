package pl.tomaszpanek.aplikacjeinternetowe.webui.controller.forum;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.tomaszpanek.aplikacjeinternetowe.domain.model.Mapper;
import pl.tomaszpanek.aplikacjeinternetowe.domain.model.forum.Category;
import pl.tomaszpanek.aplikacjeinternetowe.domain.service.CategoryService;
import pl.tomaszpanek.aplikacjeinternetowe.webui.controller.Mappings;
import pl.tomaszpanek.aplikacjeinternetowe.webui.dto.forum.CategoryDto;

import java.util.List;

@RestController
@RequestMapping(Mappings.CATEGORY)
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addCategory(@RequestBody @Validated CategoryDto categoryDto) {
        categoryService.save(categoryDto);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCategory(@RequestBody @Validated CategoryDto categoryDto) {
        categoryService.update(categoryDto);
    }

    @GetMapping
    public List<CategoryDto> getCategories() {
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public CategoryDto getCategoryById(@PathVariable("id") Category category) {
        return Mapper.map(category);
    }
}

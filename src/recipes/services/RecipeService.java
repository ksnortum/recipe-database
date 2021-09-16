package recipes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recipes.businesslayer.Recipe;
import recipes.persistance.RecipeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    @Autowired
    RecipeRepository repository;

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    public Recipe save(Recipe recipe) {
        return repository.save(recipe);
    }

    public Optional<Recipe> findById(Long id) {
        return repository.findById(id);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public List<Recipe> findAllRecipesByCategory(String category) {
        return repository.findAllByCategoryIgnoreCaseOrderByDateDesc(category);
    }

    public List<Recipe> findAllRecipesByName(String name) {
        return repository.findAllByNameContainingIgnoreCaseOrderByDateDesc(name);
    }
}

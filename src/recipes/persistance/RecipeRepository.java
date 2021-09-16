package recipes.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import recipes.businesslayer.Recipe;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findAllByCategoryIgnoreCaseOrderByDateDesc(String category);
    List<Recipe> findAllByNameContainingIgnoreCaseOrderByDateDesc(String name);
}

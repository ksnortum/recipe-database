package recipes.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recipes.businesslayer.Account;
import recipes.businesslayer.Recipe;
import recipes.services.AccountService;
import recipes.services.RecipeService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private AccountService accountService;

    @PostMapping("register")
    public ResponseEntity<Account> registerAccount(@Valid @RequestBody Account account) {
        if (accountService.existsByEmail(account.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        accountService.save(account);
        return ResponseEntity.ok().build() ;
    }

    @PostMapping("recipe/new")
    public ResponseEntity<Map<String, Long>> saveRecipe(@Valid @RequestBody Recipe recipe) {
        Recipe returnRecipe = recipeService.save(recipe);
        return ResponseEntity.ok(Map.of("id", returnRecipe.getId()));
    }

    @GetMapping("recipe/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable long id) {
        Optional<Recipe> recipe = recipeService.findById(id);
        return recipe.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("recipe/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<Recipe> deleteRecipe(@PathVariable long id) {
        authenticateName(id);
        recipeService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("recipe/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateRecipe(@Valid @RequestBody Recipe recipe, @PathVariable long id) {
        String authorizedName = authenticateName(id);
        recipe.setId(id);
        recipe.setCreator(authorizedName);
        recipeService.save(recipe);
    }

    @GetMapping("recipe/search")
    public List<Recipe> searchRecipes(@RequestParam Map<String, String> queryParameters) {
        if (queryParameters.size() != 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (queryParameters.containsKey("name")) {
            String param = queryParameters.get("name");

            return recipeService.findAllRecipesByName(param);

        } else if (queryParameters.containsKey("category")) {
            String param = queryParameters.get("category");

            return recipeService.findAllRecipesByCategory(param);
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    private String authenticateName(long id) throws ResponseStatusException{
        Optional<Recipe> recipeOptional = recipeService.findById(id);

        if (recipeOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        String authorizedName = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!authorizedName.equals(recipeOptional.get().getCreator())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return authorizedName;
    }
}

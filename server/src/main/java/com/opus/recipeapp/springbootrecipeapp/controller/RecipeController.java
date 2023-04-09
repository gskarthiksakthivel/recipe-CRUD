package com.opus.recipeapp.springbootrecipeapp.controller;

import com.opus.recipeapp.springbootrecipeapp.model.Recipe;
import com.opus.recipeapp.springbootrecipeapp.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RecipeController {

    @Autowired
    RecipeRepository recipeRepository;


    /**
     * Get All Recipes
     * @return
     */
    @GetMapping("/allrecipes")
    public List<Recipe> getAllRecipes() {
        return (List<Recipe>) recipeRepository.findAll();
    }

    /**
     * Get  Recipes By Id
     *
     * @param id
     * @return
     */
    @GetMapping("/recipes/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable(value = "id") long id) {
        Recipe user = recipeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recipes not found on :: "+ id));
        return ResponseEntity.ok().body(user);

    }


    /**
     * Search  Recipes Based on Input
     * @param keyword
     * @return
     */
    @GetMapping("/search")
    public ResponseEntity<List<Recipe>> searchDishes(@RequestParam String keyword) {
        return ResponseEntity.ok(recipeRepository.findByKeyword(keyword));
    }

    /**
     * Search  Recipes Based on Input
     * @param dish
     * @param ingredients
     * @param servings
     * @return
     */
    @GetMapping("/searchRecipes")
    public ResponseEntity<List<Recipe>> searchRecipes(@RequestParam String dish,
                                                      @RequestParam String ingredients,
                                                      @RequestParam int servings) {
        return ResponseEntity.ok(recipeRepository.searchByCriteria(dish, ingredients, servings));
    }

    /**
     * @param recipe
     * @return
     */
    @PostMapping(path = "/recipe/create", consumes = "application/json", produces = "application/json")
    public Recipe postRecipe(@RequestBody Recipe recipe) {
        return recipeRepository.save(recipe);
    }


    /**
     * Delete  Recipes
     * @param id
     * @return
     */
    @DeleteMapping("/recipes/delete/{id}")
    public ResponseEntity<String> deleteRecipe(@PathVariable("id") long id) {
        recipeRepository.deleteById(id);
        return new ResponseEntity<>("Recipe has been deleted!", HttpStatus.OK);
    }


    /**
     * Delete All Recipes
     * @return
     */
    @DeleteMapping("/recipes/delete")
    public ResponseEntity<String> deleteAllRecipes() {
        recipeRepository.deleteAll();
        return new ResponseEntity<>("All recipes have been deleted!", HttpStatus.OK);
    }


    /**
     * Update  Recipes
     * @param id
     * @param recipe
     * @return
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable("id") long id, @RequestBody Recipe recipe) {
        Optional<Recipe> recipeData = recipeRepository.findById(id);

        if (recipeData.isPresent()) {
            Recipe _recipe = recipeData.get();
            _recipe.setName(recipe.getName());
            _recipe.setServings(recipe.getServings());
            _recipe.setInstructions(recipe.getInstructions());
            _recipe.setIngredients(recipe.getIngredients());
            _recipe.setDish(recipe.getDish());
            return new ResponseEntity<>(recipeRepository.save(_recipe), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

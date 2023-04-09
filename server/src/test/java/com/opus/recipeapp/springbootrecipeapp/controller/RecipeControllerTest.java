package com.opus.recipeapp.springbootrecipeapp.controller;

import com.opus.recipeapp.springbootrecipeapp.model.Recipe;
import com.opus.recipeapp.springbootrecipeapp.repository.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RecipeController.class)
class RecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecipeRepository mockRecipeRepository;

    @Test
    void testGetAllRecipes() throws Exception {
        // Setup
        // Configure RecipeRepository.findAll(...).
        final List<Recipe> recipes = List.of(
                new Recipe(0L, "name", 0, new String[]{"ingredients"}, new String[]{"instructions"}, "dish"));
        when(mockRecipeRepository.findAll()).thenReturn(recipes);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/api/allrecipes")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetAllRecipes_RecipeRepositoryReturnsNoItems() throws Exception {
        // Setup
        when(mockRecipeRepository.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/api/allrecipes")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[]");
    }

    @Test
    void testGetRecipeById() throws Exception {
        // Setup
        // Configure RecipeRepository.findById(...).
        final Optional<Recipe> recipe = Optional.of(
                new Recipe(0L, "name", 0, new String[]{"ingredients"}, new String[]{"instructions"}, "Veg"));
        when(mockRecipeRepository.findById(0L)).thenReturn(recipe);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/api/recipes/{id}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetRecipeById_RecipeRepositoryReturnsAbsent() throws Exception {
        // Setup
        when(mockRecipeRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/api/recipes/{id}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("");
    }

    @Test
    void testSearchDishes() throws Exception {
        // Setup
        // Configure RecipeRepository.findByKeyword(...).
        final List<Recipe> recipes = List.of(
                new Recipe(0L, "name", 0, new String[]{"ingredients"}, new String[]{"instructions"}, "dish"));
        when(mockRecipeRepository.findByKeyword("keyword")).thenReturn(recipes);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/api/search")
                        .param("keyword", "keyword")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testSearchDishes_RecipeRepositoryReturnsNoItems() throws Exception {
        // Setup
        when(mockRecipeRepository.findByKeyword("keyword")).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/api/search")
                        .param("keyword", "keyword")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[]");
    }

    @Test
    void testSearchRecipes() throws Exception {
        // Setup
        // Configure RecipeRepository.searchByCriteria(...).
        final List<Recipe> recipes = List.of(
                new Recipe(0L, "name", 0, new String[]{"ingredients"}, new String[]{"instructions"}, "dish"));
        when(mockRecipeRepository.searchByCriteria("dish", "ingredients", 0)).thenReturn(recipes);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/api/searchRecipes")
                        .param("dish", "dish")
                        .param("ingredients", "ingredients")
                        .param("servings", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testSearchRecipes_RecipeRepositoryReturnsNoItems() throws Exception {
        // Setup
        when(mockRecipeRepository.searchByCriteria("dish", "ingredients", 0)).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/api/searchRecipes")
                        .param("dish", "dish")
                        .param("ingredients", "ingredients")
                        .param("servings", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[]");
    }

    @Test
    void testPostRecipe() throws Exception {
        // Setup
        // Configure RecipeRepository.save(...).
        final Recipe recipe = new Recipe(0L, "name", 0, new String[]{"ingredients"}, new String[]{"instructions"},
                "dish");
        when(mockRecipeRepository.save(
                new Recipe(0L, "name", 0, new String[]{"ingredients"}, new String[]{"instructions"},
                        "dish"))).thenReturn(recipe);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/api/recipe/create")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testDeleteRecipe() throws Exception {
        // Setup
        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(delete("/api/recipes/delete/{id}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
        verify(mockRecipeRepository).deleteById(0L);
    }

    @Test
    void testDeleteAllRecipes() throws Exception {
        // Setup
        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(delete("/api/recipes/delete")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
        verify(mockRecipeRepository).deleteAll();
    }

    @Test
    void testUpdateRecipe() throws Exception {
        // Setup
        // Configure RecipeRepository.findById(...).
        final Optional<Recipe> recipe = Optional.of(
                new Recipe(0L, "name", 0, new String[]{"ingredients"}, new String[]{"instructions"}, "dish"));
        when(mockRecipeRepository.findById(0L)).thenReturn(recipe);

        // Configure RecipeRepository.save(...).
        final Recipe recipe1 = new Recipe(0L, "name", 0, new String[]{"ingredients"}, new String[]{"instructions"},
                "dish");
        when(mockRecipeRepository.save(
                new Recipe(0L, "name", 0, new String[]{"ingredients"}, new String[]{"instructions"},
                        "dish"))).thenReturn(recipe1);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/api/update/{id}", 0)
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testUpdateRecipe_RecipeRepositoryFindByIdReturnsAbsent() throws Exception {
        // Setup
        when(mockRecipeRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/api/update/{id}", 0)
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }
}

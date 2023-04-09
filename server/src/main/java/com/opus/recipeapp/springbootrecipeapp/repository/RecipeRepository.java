package com.opus.recipeapp.springbootrecipeapp.repository;

import com.opus.recipeapp.springbootrecipeapp.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findByName(String name);

    @Query(value = "select * from RECIPE r where r.DISH like %:keyword% or r.INGREDIENTS  like %:keyword%", nativeQuery = true)
    List<Recipe> findByKeyword(@Param("keyword") String keyword);

    @Query(value = "select * from RECIPE r where r.DISH like %:dish% or r.INGREDIENTS  like %:ingredients% or r.Servings  like %:servings%", nativeQuery = true)
    List<Recipe> searchByCriteria(@Param("dish") String dish,
                                  @Param("ingredients") String ingredients,
                                  @Param("servings") int servings);

}
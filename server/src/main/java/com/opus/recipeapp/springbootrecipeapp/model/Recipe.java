package com.opus.recipeapp.springbootrecipeapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@ToString
@Table(name = "recipe")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;
    @Column(name = "servings")
    private int servings;

    @Column(name = "ingredients")
    private String[] ingredients;

    @Column(name = "instructions")
    private String[] instructions;
    @Column(name = "dish")
    private String dish;

    public Recipe(String name, int servings, String[] ingredients, String[] instructions, String dish) {
        this.name = name;
        this.servings = servings;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.dish = dish;
    }

}
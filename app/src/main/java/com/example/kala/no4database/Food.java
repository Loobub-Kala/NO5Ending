package com.example.kala.no4database;

public class Food {
    private int id;
    private String name;
    private String ingredients;

    public Food() {
        id = 0;
        name = null;
        ingredients = null;
    }

    public Food(int id, String name, String ingredients) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }
}

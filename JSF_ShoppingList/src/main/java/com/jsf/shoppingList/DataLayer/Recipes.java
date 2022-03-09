package com.jsf.shoppingList.DataLayer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Recipes {
    private int RecipesID;
    private String RecipeNameCZ;
    private String RecipeNameEN;
    private int Portion;
    private int PortionDefault;
    private String PathDirectionsCZ;
    private String PathDirectionsEN;
    private String PathIMG;
    private String NutritionValues;
    private List<Ingredients> ingredients;

    public Recipes(){

    }

    public void setIngredientsAmoutPerPortion(){
        for (Ingredients i: this.ingredients) {
            i.setAmountPerPortion_calc(i.getAmount() / PortionDefault);
            i.setAlterAmountPerPortion_calc(i.getAlterAmount() / PortionDefault);
        }
    }

    public int getPortionDefault() {
        return PortionDefault;
    }

    public void setPortionDefault(int portionDefault) {
        PortionDefault = portionDefault;
    }

    public int getRecipesID() {
        return RecipesID;
    }

    public void setRecipesID(int recipesID) {
        RecipesID = recipesID;
    }

    public String getRecipeNameCZ() {
        return RecipeNameCZ;
    }

    public void setRecipeNameCZ(String recipeNameCZ) {
        RecipeNameCZ = recipeNameCZ;
    }

    public String getRecipeNameEN() {
        return RecipeNameEN;
    }

    public void setRecipeNameEN(String recipeNameEN) {
        RecipeNameEN = recipeNameEN;
    }

    public int getPortion() {
        return Portion;
    }

    public void setPortion(int portion) {
        Portion = portion;
    }

    public String getPathDirectionsCZ() {
        return PathDirectionsCZ;
    }

    public void setPathDirectionsCZ(String pathDirectionsCZ) {
        PathDirectionsCZ = pathDirectionsCZ;
    }

    public String getPathDirectionsEN() {
        return PathDirectionsEN;
    }

    public void setPathDirectionsEN(String pathDirectionsEN) {
        PathDirectionsEN = pathDirectionsEN;
    }

    public String getPathIMG() {
        return PathIMG;
    }

    public void setPathIMG(String pathIMG) {
        PathIMG = pathIMG;
    }

    public List<List<String>> getNutritionValues() {
        List<String> rows = Arrays.stream(NutritionValues.split(", ")).toList();
        List<List<String>> result = new ArrayList<>();
        for (String r : rows) {
            result.add((Arrays.stream(r.split("; ")).toList()));
        }
        return result;
    }

    public void setNutritionValues(String nutritionValues) {
        NutritionValues = nutritionValues;
    }

    public List<Ingredients> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "Recipes{" +
                "RecipesID=" + RecipesID +
                ", RecipeNameCZ='" + RecipeNameCZ + '\'' +
                ", RecipeNameEN='" + RecipeNameEN + '\'' +
                ", Portion=" + Portion +
                ", PathDirectionsCZ='" + PathDirectionsCZ + '\'' +
                ", PathDirectionsEN='" + PathDirectionsEN + '\'' +
                ", PathIMG='" + PathIMG + '\'' +
                ", NutritionValues='" + NutritionValues + '\'' +
                ", ingredients=" + ingredients +
                '}';
    }
}

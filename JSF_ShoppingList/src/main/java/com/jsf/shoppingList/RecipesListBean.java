package com.jsf.shoppingList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import java.io.Serializable;
import java.sql.Types;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.jsf.shoppingList.DataLayer.*;
import com.microsoft.sqlserver.jdbc.SQLServerDataTable;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import org.primefaces.PrimeFaces;

import static com.jsf.shoppingList.DataLayer.DbConnect.*;

@ManagedBean(name = "recipesList")
@SessionScoped
public class RecipesListBean implements Serializable {

    private List<Recipes> RecipeList;
    private List<String> selectedIngredient = new ArrayList<>();
    private List<Ingredients> IngredientFilterList;
    private List<Recipes> RecipesNamesList;
    private String selectedRecipes = "";

    public void getListFromDB (String recipeName, SQLServerDataTable ingredientsID) {
        boolean isConnected = false;
        if (Connect("Recipes")){
            RecipeList = getRecipesFromDB(recipeName, ingredientsID);
            isConnected = true;
        }
    }

    public void getFilterListFromDB(){
        if(Connect("Recipes")){
            IngredientFilterList = getFilterIngFromDB();
        }
    }
    public String getRecipeUrl(int recipeId){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("recipeId",recipeId);
        return "recipe.xhtml?faces-redirect=true&recipeID="+recipeId;
    }

    public List<Ingredients> getIngredientFilterList() {
        if(IngredientFilterList == null) {
            getFilterListFromDB();
        }
        return IngredientFilterList;
    }

    public List<Recipes> getRecipesList() {
        if(RecipeList == null) {
            try {
                SQLServerDataTable ingredients = new SQLServerDataTable();
                ingredients.addColumnMetadata("ID", Types.INTEGER);
                for (String i : selectedIngredient) {
                    ingredients.addRow(Integer.parseInt(i));
                }
                getListFromDB(selectedRecipes, ingredients);
            } catch (SQLServerException e) {
                e.printStackTrace();
            }
        }
        return RecipeList;
    }

    public void setRecipeList(List<Recipes> recipeList) {
        RecipeList = recipeList;
    }

    public void setNullRecipeList(){
        setRecipeList(null);
        PrimeFaces.current().ajax().update("@([id$='recipe-grid-view'])");
    }
    public void setNullFilters(){
        setSelectedRecipes("");
        setSelectedIngredient(new ArrayList<>());
        setRecipeList(null);
    }

    public List<String> getSelectedIngredient() {
        return selectedIngredient;
    }

    public void setSelectedIngredient(List<String> selectedIngredient) {
        this.selectedIngredient = selectedIngredient;
    }

    public void getAllRecipesNames(){
        if(Connect("Recipes")){
            RecipesNamesList = DbConnect.getAllRecipesNames();
        }
    }

    public List<Recipes> getRecipesNamesList() {
        if(RecipesNamesList == null) {
            getAllRecipesNames();
        }
        return RecipesNamesList;
    }
    public List<String> completeRecipe (String query) {
        String queryLowerCase = query.toLowerCase();
        List<String> recipesList = new ArrayList<>();
        for (Recipes recipes : getRecipesNamesList()) {
            recipesList.add(recipes.getRecipeNameEN());
        }
        return recipesList.stream().filter(r -> r.toLowerCase().contains(queryLowerCase)).collect(Collectors.toList());
    }


    public String getSelectedRecipes() {
        return selectedRecipes;
    }

    public void setSelectedRecipes(String selectedRecipes) {
        this.selectedRecipes = selectedRecipes;
    }

}

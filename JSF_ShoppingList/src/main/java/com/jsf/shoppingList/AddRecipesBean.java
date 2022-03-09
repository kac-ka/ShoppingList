package com.jsf.shoppingList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.jsf.shoppingList.DataLayer.*;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

@SessionScoped
@ManagedBean(eager = true)
public class AddRecipesBean implements Serializable {

    private Recipes recipes;
    private Integer calories;
    private Integer protein;
    private Integer totalCarbs;
    private Integer sugars;
    private Integer fiber;
    private Integer fat;
    private Integer saturatedFat;
    private Integer transFat;
    private Integer mufa;
    private Integer pufa;
    private List<Ingredients> ingredientsList;
    private List<Ingredients> recipeIngredientsList;
    private UploadedFile originalImageFile;
    private List<Unit> unitList;



    public Recipes getRecipes() {
        if(recipes == null){
            recipes = new Recipes();
        }
        return recipes;
    }

    public List<Ingredients> getIngredientsList() {
        if(ingredientsList == null) {
            ingredientsList = DbConnect.getIngredientFromDB();
        }
        return ingredientsList;
    }

    public void handleFileUpload(FileUploadEvent event) {
        this.originalImageFile = null;
        UploadedFile file = event.getFile();
        if (file != null && file.getContent() != null && file.getContent().length > 0 && file.getFileName() != null) {
            this.originalImageFile = file;
            FacesMessage msg = new FacesMessage("Successful", this.originalImageFile.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public List<Unit> getAllUnit() {
        if(unitList == null) {
            unitList = DbConnect.getAllUnitFromDB();
        }
        return unitList;
    }


    public void setIngredientsList(List<Ingredients> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    public List<Ingredients> getRecipeIngredientsList() {
        if(recipeIngredientsList == null){
            recipeIngredientsList = new ArrayList<>();
            recipeIngredientsList.add(new Ingredients());
        }
        return recipeIngredientsList;
    }

    public void setRecipeIngredientsList(List<Ingredients> recipeIngredientsList) {
        this.recipeIngredientsList = recipeIngredientsList;
    }

    public void addRecipeIngredient() {
        recipeIngredientsList.add(new Ingredients());
    }

    public void setRecipes(Recipes recipes) {
        this.recipes = recipes;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public Integer getProtein() {
        return protein;
    }

    public void setProtein(Integer protein) {
        this.protein = protein;
    }

    public Integer getTotalCarbs() {
        return totalCarbs;
    }

    public void setTotalCarbs(Integer totalCarbs) {
        this.totalCarbs = totalCarbs;
    }

    public Integer getSugars() {
        return sugars;
    }

    public void setSugars(Integer sugars) {
        this.sugars = sugars;
    }

    public Integer getFiber() {
        return fiber;
    }

    public void setFiber(Integer fiber) {
        this.fiber = fiber;
    }

    public Integer getFat() {
        return fat;
    }

    public void setFat(Integer fat) {
        this.fat = fat;
    }

    public Integer getSaturatedFat() {
        return saturatedFat;
    }

    public void setSaturatedFat(Integer saturatedFat) {
        this.saturatedFat = saturatedFat;
    }

    public Integer getTransFat() {
        return transFat;
    }

    public void setTransFat(Integer transFat) {
        this.transFat = transFat;
    }

    public Integer getMufa() {
        return mufa;
    }

    public void setMufa(Integer mufa) {
        this.mufa = mufa;
    }

    public Integer getPufa() {
        return pufa;
    }

    public void setPufa(Integer pufa) {
        this.pufa = pufa;
    }
}

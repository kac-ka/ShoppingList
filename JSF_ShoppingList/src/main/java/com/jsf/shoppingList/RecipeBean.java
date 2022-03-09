package com.jsf.shoppingList;

import com.jsf.shoppingList.DataLayer.*;


import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.*;

import org.primefaces.PrimeFaces;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.jsf.shoppingList.DataLayer.DbConnect.*;
//import static com.jsf.shoppingList.DataLayer.DbConnect.getRecipesFromDB;

@RequestScoped
@ManagedBean
public class RecipeBean implements Serializable {

    private final static Logger _logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private static Recipes recipe;

   // @ManagedProperty(value="#{param.id}")
    private int RecipeID;

    private static String DescriptionTitle;

    //private static boolean isConnected = Connect("Recipes");

    public RecipeBean(){
    }

    @PostConstruct
    public void setRecipeFromDB(){

        boolean isConnected = false;
        if (Connect("Recipes")){
            recipe = getRecipesFromDB(getRecipeID());
            isConnected = true;
        }
    }

    public int getRecipeID() {
        if (RecipeID == 0) {
            RecipeID = (int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("recipeId");
        }
        return RecipeID;
    }

    public void setRecipeID(int RecipeID) {
        this.RecipeID = RecipeID;
    }

    public String getDescriptionPath(){
       return String.format("C:\\Programování\\JSF_ShoppingList\\Recipes\\%s",getRecipe().getPathDirectionsEN());
    }

    public String getIMGPath(){
        return String.format("/images/%s", getRecipe().getPathIMG());
    }

    public static ArrayList<String> getDescriptionFromFile(String filePath) {
       try (BufferedReader br = new BufferedReader(new FileReader(filePath, StandardCharsets.UTF_8))) {
           StringBuilder sb = new StringBuilder();


           ArrayList<String> lines = new ArrayList<>();
           String line = br.readLine();
           while((line != null)) {
               lines.add(line);
               line = br.readLine();
           }
           return lines;
       } catch (java.io.IOException e){
            e.printStackTrace();
            _logger.log(Level.SEVERE, "Nastala chyba.", e);
       }
       return null;
    }

    public String getDescriptionTitle(){
        ArrayList<String> lines = new ArrayList<>();
        lines = getDescriptionFromFile(getDescriptionPath());
        return lines.get(0);
    }

    public ArrayList<String> getDescriptionToString(){
        ArrayList<String> lines = new ArrayList<>();
        lines = getDescriptionFromFile(getDescriptionPath());
        lines.remove(0);
        return lines;
    }

    public  Recipes getRecipe() {
        if (recipe == null){
            setRecipeFromDB();
        }
        return recipe;
    }

    public  int getPortion(){
        int result = getRecipe().getPortion();
        System.out.println(String.format("getPortion =", result));;
        return result;
    }

   public void setAllChecked(boolean checked) {
       for (Ingredients i : getRecipe().getIngredients()) {
           i.setChecked(checked);
       }
       System.out.println("toggleAll");
   }
   public boolean isAllChecked() {
        boolean result = !getRecipe().getIngredients().isEmpty();
        if(result)
       for (Ingredients i : getRecipe().getIngredients()) {
            result = i.isChecked();
            if(!result) {
                break;
            }
       }
       return result;
   }
   public void toggleAllChecked() {
        setAllChecked(isAllChecked());
       System.out.println("toggleAll");
        PrimeFaces.current().ajax().update("@([id$='ingredientsList']) @([id$='rowCheckbox'])");
    }

    public void calculatedAmount() {
        System.out.println("aMOUNT " + getRecipe().getPortion());
        PrimeFaces.current().ajax().update("@([id$='ingredientsList']) @([id$='wholeAmount']) @([id$='fractionAmount']) @([id$='wholeAlterAmount']) @([id$='fractionAlterAmount'])");
    }

//    public void setIngredientListToSession (){
//        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("shoppingList",);
//    }
}


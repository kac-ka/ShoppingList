package com.jsf.shoppingList;

import com.jsf.shoppingList.DataLayer.Ingredients;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@SessionScoped
@ManagedBean
public class ShoppingListBean implements Serializable {
    private static List<Ingredients> IngredientsList = new ArrayList<>();

    public static void addIngredientToList(List<Ingredients> ingredients) {
        if(!IngredientsList.isEmpty()){
            for (Ingredients i: ingredients) {
                if(IngredientsList.stream().filter(x -> x.getIngredientID() == i.getIngredientID()).findFirst().isEmpty()){

                }
            }
        }
    }

}

package com.jsf.shoppingList;


import com.jsf.shoppingList.DataLayer.AltUnitValue;
import com.jsf.shoppingList.DataLayer.Ingredients;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.criteria.CriteriaBuilder;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.jsf.shoppingList.DataLayer.*;

@SessionScoped
@ManagedBean
public class AddIngredientBean implements Serializable {

    private List<Ingredients> IngredientsList;
    private List<AltUnitValue> AltUnitValueList;
    private int counter = 1;
    private Map<Integer, List<String>> CategoryMap;

    public List<Ingredients> getIngredientsList() {
        if(IngredientsList == null){
            IngredientsList = new ArrayList<>();
            IngredientsList.add(new Ingredients(counter));
        }
        return IngredientsList;
    }

    public void setIngredientsList(List<Ingredients> ingredientsList) {
        IngredientsList = ingredientsList;
    }

    public void InsertIngredients() {

        for (Ingredients i: IngredientsList) {
            i.setAltUnitValueList(AltUnitValueList.stream().filter(x->x.getIngredientRowId() == i.getRowID() && x.getValue() != null).collect(Collectors.toList()));
        }

        DbConnect.insertIngredients(getIngredientsList());
        setIngredientsList(null);
    }

    public List<AltUnitValue> getAltUnitValueList() {
        if(AltUnitValueList == null) {
            AltUnitValueList = DbConnect.getAltUnitValue(1);
        }
        return AltUnitValueList;
    }
    public List<AltUnitValue> getAltUnitValueListPerRow(int rowId, int rowDefaultUnitId){
        if (AltUnitValueList == null || AltUnitValueList.isEmpty()){
            AltUnitValueList = DbConnect.getAltUnitValue(1);
        }
        switch (rowDefaultUnitId) {
            case 5:
                return AltUnitValueList.stream().filter(x -> x.getIngredientRowId() == rowId && x.isShownG()).collect(Collectors.toList());
            case 7:
                return AltUnitValueList.stream().filter(x -> x.getIngredientRowId() == rowId && x.isShownMl()).collect(Collectors.toList());
            default:
                return AltUnitValueList.stream().filter(x -> x.getIngredientRowId() == rowId).collect(Collectors.toList());
        }
    }

    public void addNewLine(int rowId) {
        AltUnitValueList.addAll(DbConnect.getAltUnitValue(rowId + 1));
        IngredientsList.add(new Ingredients(rowId + 1));
    }

    public Map<Integer, List<String>> getCategoryMap() {
        if(CategoryMap == null) {
           CategoryMap = DbConnect.getCategory();
        }
        return CategoryMap;
    }

}

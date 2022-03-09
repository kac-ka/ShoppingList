package com.jsf.shoppingList.DataLayer;

import com.jsf.shoppingList.Utils.ConvertToFraction;

import java.util.List;

public class Ingredients {
    public int RecipesID;
    public int IngredientInRecipeID;
    private int IngredientID;
    private int RowID;
    public String IngredientNameCZ;
    public String IngredientNameEN;
    public double Amount;
    public double AmountPerPortion_calc;
    public String UnitNameCZ;
    public String UnitNameEN;
    public double AlterAmount;
    public double AlterAmountPerPortion_calc;
    public String AlterUnitNameCZ;
    public String AlterUnitNameEN;
    public List<AltIngredients> AltIngredientsList;
    private boolean Checked;
    private int DefaultUnitID;
    private boolean IsFiltered;
    private int CategoryID;
    private List<AltUnitValue> AltUnitValueList;
    private int UnitID;

    public Ingredients(){

    }

    public Ingredients(int rowID){
        this.RowID = rowID;
    }

    public boolean isChecked() {
        return Checked;
    }

    public void setChecked(boolean checked) {
        this.Checked = checked;
    }
    public int getIngredientID() {
        return IngredientID;
    }

    public void setIngredientID(int ingredientID) {
        IngredientID = ingredientID;
    }

    public void setIngredientNameCZ(String ingredientNameCZ) {
        IngredientNameCZ = ingredientNameCZ;
    }

    public String getIngredientNameEN() {
        return IngredientNameEN;
    }

    public void setIngredientNameEN(String ingredientNameEN) {
        IngredientNameEN = ingredientNameEN;
    }

    public double getAmount() {
        return Amount;
    }

    public ConvertToFraction getCalculatedAmountFraction(int portions){
        ConvertToFraction ctf = new ConvertToFraction(AmountPerPortion_calc * portions);
        System.out.printf("porce = %s, vypočítaná hodnota = %s%n",portions,ctf);
        return ctf;
    }

    public int getRowID() {
        return RowID;
    }

    public void setRowID(int rowID) {
        RowID = rowID;
    }

    public String getUnitNameEN() {
        return UnitNameEN;
    }

    public void setUnitNameEN(String unitNameEN) {
        UnitNameEN = unitNameEN;
    }

    public double getAlterAmount() {
        return AlterAmount;
    }


    public void setAmountPerPortion_calc(double amountPerPortion_calc) {
        AmountPerPortion_calc = amountPerPortion_calc;
    }

    public void setAlterAmountPerPortion_calc(double AlterAmoutPerPortion_calc) {
        this.AlterAmountPerPortion_calc = AlterAmoutPerPortion_calc;
    }

    public ConvertToFraction getCalculatedAlterAmoutFraction(int portions){
        return new ConvertToFraction(AlterAmountPerPortion_calc * portions);
    }

    public boolean hasAlterAmount(){
        if(AlterAmount == 0){
            return false;
        }
        return true;
    }
    public String getAlterUnitNameEN() {
        return AlterUnitNameEN;
    }

    public void setAlterUnitNameEN(String alterUnitNameEN) {
        AlterUnitNameEN = alterUnitNameEN;
    }

    public int getDefaultUnitID() {
        return DefaultUnitID;
    }

    public void setDefaultUnitID(int defaultUnitID) {
        DefaultUnitID = defaultUnitID;
    }

    public int getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(int categoryID) {
        CategoryID = categoryID;
    }

    public List<AltUnitValue> getAltUnitValueList() {
        return AltUnitValueList;
    }

    public void setAltUnitValueList(List<AltUnitValue> altUnitValueList) {
        AltUnitValueList = altUnitValueList;
    }

    public boolean isFiltered() {
        return IsFiltered;
    }

    public void setFiltered(boolean filtered) {
        IsFiltered = filtered;
    }

    public int getUnitID() {
        return UnitID;
    }

    public void setUnitID(int unitID) {
        UnitID = unitID;
    }

    @Override
    public String toString() {
        return "Ingredients{" +
                "RecipesID=" + RecipesID +
                ", IngredientInRecipeID=" + IngredientInRecipeID +
                ", IngredientNameCZ='" + IngredientNameCZ + '\'' +
                ", IngredientNameEN='" + IngredientNameEN + '\'' +
                ", Amount=" + Amount +
                ", UnitNameCZ='" + UnitNameCZ + '\'' +
                ", UnitNameEN='" + UnitNameEN + '\'' +
                ", AlterAmount=" + AlterAmount +
                ", AlterUnitNameCZ='" + AlterUnitNameCZ + '\'' +
                ", AlterUnitNameEN='" + AlterUnitNameEN + '\'' +
                ", altIngredientsList=" + AltIngredientsList +
                '}';
    }
}

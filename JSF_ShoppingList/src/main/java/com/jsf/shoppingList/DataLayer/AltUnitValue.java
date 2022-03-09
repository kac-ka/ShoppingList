package com.jsf.shoppingList.DataLayer;

public class AltUnitValue {
    private Double Value;
    private int UnitID;
    private int IngredientRowId;
    private String UnitNameEN;
    private String UnitNameCZ;
    private boolean IsShownG;
    private boolean IsShownMl;

    public Double getValue() {
        return Value;
    }

    public void setValue(Double value) {
        Value = value;
    }

    public int getUnitID() {
        return UnitID;
    }

    public void setUnitID(int unitID) {
        UnitID = unitID;
    }

    public String getUnitNameEN() {
        return UnitNameEN;
    }

    public void setUnitNameEN(String unitNameEN) {
        UnitNameEN = unitNameEN;
    }

    public String getUnitNameCZ() {
        return UnitNameCZ;
    }

    public void setUnitNameCZ(String unitNameCZ) {
        UnitNameCZ = unitNameCZ;
    }

    public boolean isShownG() {
        return IsShownG;
    }

    public void setShownG(boolean shownG) {
        IsShownG = shownG;
    }

    public boolean isShownMl() {
        return IsShownMl;
    }

    public void setShownMl(boolean shownMl) {
        IsShownMl = shownMl;
    }

    public int getIngredientRowId() {
        return IngredientRowId;
    }

    public void setIngredientRowId(int ingredientRowId) {
        IngredientRowId = ingredientRowId;
    }
}

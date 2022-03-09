package com.jsf.shoppingList.DataLayer;

public class Unit {
    private Double Amount;
    private int UnitID;
    private String UnitNameEN;
    private String UnitNameCZ;
    private boolean IsShownG;
    private boolean IsShownMl;

    public Double getAmount() {
        return Amount;
    }

    public void setAmount(Double amount) {
        Amount = amount;
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
}

package com.jsf.shoppingList.Utils;

public class ConvertToFraction {

    // denom = jmenovatel, num = čitatel
    private int denom;
    private int num;
    private int wholeNumb;
    private double originalNumb;

    public ConvertToFraction(double amount) {
//        if (amount < 0){
//            return "-" + convertDecimalToFraction(-amount);
//        }

        originalNumb = amount;
        wholeNumb = (int) amount;
        amount -= wholeNumb;

        if (amount != 0) {
            double tolerance = 1.0E-3;
            double h1 = 1;
            double h2 = 0;
            double k1 = 0;
            double k2 = 1;
            double b = amount;
            do {
                double a = Math.floor(b);
                double aux = h1;
                h1 = a * h1 + h2;
                h2 = aux;
                aux = k1;
                k1 = a * k1 + k2;
                k2 = aux;
                b = 1 / (b - a);
            } while (Math.abs(amount - h1 / k1) > amount * tolerance);

            num = (int) h1;
            denom = (int) k1;
        }
    }

    public int getDenom() {
        return denom;
    }

    public int getNum() {
        return num;
    }

    public int getWholeNumb() {
        return wholeNumb;
    }

    public double getOriginalNumb() {
        return originalNumb;
    }

    @Override
    public String toString() {
        return "ConvertToFraction{" +
                "denom=" + denom +
                ", num=" + num +
                ", wholeNumb=" + wholeNumb +
                ", originalNumb=" + originalNumb +
                '}';
    }
}

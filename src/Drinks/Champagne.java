/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Drinks;

/**
 *
 * @author dalib
 */
public class Champagne extends CarbonatedBeverage implements AlcoholicDrink{

    public Champagne(String name, double volume, double price, int quantity) {
        super(name, volume, price, quantity, "champagne");
    }

    @Override
    public double percentOfAlc() {
        return 11.6;
    }
    
}

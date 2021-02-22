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
public class NonCarbonatedBeverage extends Beverage{

    public NonCarbonatedBeverage(String name, double volume, double price, int quantity, String category) {
        super(name, volume, price, quantity, category);
    }
    
    @Override
    public boolean isCarbonated() {
        return false;
    }
    
}

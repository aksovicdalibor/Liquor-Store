/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Drinks;


public class Beverage extends ABeverage {

    public Beverage() {
    }

    public Beverage(String name, double volume, double price, int quantity, String category) {
        super(name, volume, price, quantity, category);
    }

    @Override
    public boolean isCarbonated() {
        return true;
    }

    @Override
    public int compareTo(ABeverage o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

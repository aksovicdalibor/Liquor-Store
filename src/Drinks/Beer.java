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
public class Beer extends CarbonatedBeverage implements AlcoholicDrink{

    public Beer(String name, double volume, double price, int quantity) {
        super(name, volume, price, quantity, "beer");
    }
    
    @Override
    public double percentOfAlc() {
        return 4.5;
    }
    
}

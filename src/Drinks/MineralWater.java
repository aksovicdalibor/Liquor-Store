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
public class MineralWater extends CarbonatedBeverage{
    
    public MineralWater(String name, double volume, double price, int quantity) {
        super(name, volume, price, quantity, "mineral water");
    }
    
}

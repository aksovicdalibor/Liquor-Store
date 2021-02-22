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
public class Juice extends NonCarbonatedBeverage{
    
    public Juice(String name, double volume, double price, int quantity) {
        super(name, volume, price, quantity, "juice");
    }
    
}

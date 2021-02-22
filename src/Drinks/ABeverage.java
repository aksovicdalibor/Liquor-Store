/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Drinks;

import Exceptions.InvalidBevCategoryException;
import Users.AbstractUser;
import Users.Admin;
import Users.User;
import static Users.User.users;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import projekatoopdaliboraksovicrt3017.ProjekatOOPDaliborAksovicRT3017;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dalib
 */
 public abstract class ABeverage  implements Comparable<ABeverage>{
    static ArrayList<ABeverage> drinks = new ArrayList<ABeverage>();
    String name;
    double volume;
    double price;
    int quantity;
    String category;
    // <editor-fold defaultstate="collapsed" desc="beverageCategories = .. ">
    public static String beverageCategories = "1. Beer\n"
                                            + "2. Wine\n"
                                            + "3. Champagne\n"
                                            + "4. Spirit drink\n"
                                            + "5. Juice\n"
                                            + "6. Soda\n"
                                            + "7. Water\n"
                                            + "8. Mineral water\n"
                                            + "\n"
                                            + "0. Cancel\n\n";

// </editor-fold>
    
    public ABeverage(String name, double volume, double price, int quantity, String category) {
        this.name = name;
        this.volume = volume;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }

    public ABeverage() {
        this.name = "";
        this.volume = 0;
        this.price = 0;
        this.quantity = 0;
        this.category = "";
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public double getVolume() {
        return volume;
    }
    
    public void setVolume(double volume) {
        this.volume = volume;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public static ArrayList<ABeverage> getDrinks() {
        if(drinks.isEmpty())
            importBevs();
        return drinks;
    }
    
    public static void importBevs(){
        drinks.clear();
        
        FileReader fr;
        try{
            fr = new FileReader("beverages.txt");
            BufferedReader br = new BufferedReader(fr);

            String temp;
            String[] partsOfTemp;
            while((temp=br.readLine())!=null){
                partsOfTemp = temp.split("\\,");
                switch (partsOfTemp[4].trim()){                    
                    // <editor-fold defaultstate="collapsed" desc=" Categories ">
                    case "beer": {

                        drinks.add(new Beer(partsOfTemp[0].trim(),
                                Double.parseDouble(partsOfTemp[1].trim()),
                                Double.parseDouble(partsOfTemp[2].trim()),
                                Integer.parseInt((partsOfTemp[3].trim())))
                        );
                        break;
                    }
                    case "wine": {
                        drinks.add(new Wine(partsOfTemp[0].trim(),
                                Double.parseDouble(partsOfTemp[1].trim()),
                                Double.parseDouble(partsOfTemp[2].trim()),
                                Integer.parseInt((partsOfTemp[3].trim())))
                        );
                        break;
                    }
                    case "champagne": {
                        drinks.add(new Champagne(partsOfTemp[0].trim(),
                                Double.parseDouble(partsOfTemp[1].trim()),
                                Double.parseDouble(partsOfTemp[2].trim()),
                                Integer.parseInt((partsOfTemp[3].trim()))
                        )
                        );
                        break;
                    }
                    case "spirit drink": {
                        drinks.add(new SpiritDrink(partsOfTemp[0].trim(),
                                Double.parseDouble(partsOfTemp[1].trim()),
                                Double.parseDouble(partsOfTemp[2].trim()),
                                Integer.parseInt((partsOfTemp[3].trim()))
                        )
                        );
                        break;
                    }
                    case "juice": {
                        drinks.add(new Juice(partsOfTemp[0].trim(),
                                Double.parseDouble(partsOfTemp[1].trim()),
                                Double.parseDouble(partsOfTemp[2].trim()),
                                Integer.parseInt((partsOfTemp[3].trim()))
                        )
                        );
                        break;
                    }
                    case "soda": {
                        drinks.add(new Soda(partsOfTemp[0].trim(),
                                Double.parseDouble(partsOfTemp[1].trim()),
                                Double.parseDouble(partsOfTemp[2].trim()),
                                Integer.parseInt((partsOfTemp[3].trim()))
                        )
                        );
                        break;
                    }
                    case "water": {
                        drinks.add(new Water(partsOfTemp[0].trim(),
                                Double.parseDouble(partsOfTemp[1].trim()),
                                Double.parseDouble(partsOfTemp[2].trim()),
                                Integer.parseInt((partsOfTemp[3].trim()))
                        )
                        );
                        break;
                    }
                    case "mineral water": {
                        drinks.add(new MineralWater(partsOfTemp[0].trim(),
                                Double.parseDouble(partsOfTemp[1].trim()),
                                Double.parseDouble(partsOfTemp[2].trim()),
                                Integer.parseInt((partsOfTemp[3].trim()))
                        )
                        );
                        break;
                    }
                    default:
                        throw new InvalidBevCategoryException();

// </editor-fold>
                }  
            }   
            
            br.close();
            fr.close();
        } catch (FileNotFoundException ex) {
            System.out.println("File not found.");
        } catch (IOException ex) {
            System.out.println("An error occurred while accessing the users.txt file.");
        } catch (InvalidBevCategoryException ex) {
            System.out.println("Record with non-valid category found.");
        }
    }   
        
    public static boolean exportBevs(){
        
        FileWriter fw;
        try{
            fw = new FileWriter("beverages.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            for(ABeverage b:drinks){
                bw.write(b.name+","+b.volume+","+b.price+","+b.quantity+","+b.category);
                bw.newLine();
            } 
            bw.close();
            fw.close();
            return true;
        } catch (FileNotFoundException ex) {
            System.out.println("File not found.");
            return false;
        } catch (IOException ex) {
            System.out.println("An error occurred while accessing the users.txt file.");                
            return false;
        }            
        
    }
    
    public static ABeverage checkBevName(String name){
        importBevs();
        for(ABeverage b:drinks){
            if(b.name.equalsIgnoreCase(name))
                return b;
        }
        return null; 
    }
    
    abstract public boolean isCarbonated();

    @Override
    public String toString() {
        return "Beverage{" + "name=" + name + ", volume=" + volume + ", price=" + price + ", quantity=" + quantity + ", category=" + category + '}';
    }
    
    public static String listToString(){
        String value="drinks:";
        for (ABeverage b:drinks){
            value+=b+"\n";
        }
        return value;
    }
    
    public static void bevOverview(AbstractUser au){
        importBevs();
               
        if(au instanceof Admin){
            System.out.println("All beverages: ");
        }
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------");
        if(au instanceof Admin)
            System.out.print("ID: |  Name:                           "+"| Volume(l): "+"|  Price(€):  "+"|    Category:    "+"| Bottles available:  \n");
        else 
            System.out.print("ID: |  Name:                           "+"| Volume(l): "+"|  Price(€):  "+"|    Category:    :  \n");            
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------");
        
        int id=0;
        for(ABeverage b:drinks){
            id++;
            String brN="",brP="",brQ="", brID="", brC1="",brC2="";
            for(int i = b.name.length(); i<32; i++){
                brN+=" ";
            }
            for(int i = 5-(""+b.price).length(); i>0; i--){
                brP+=" ";
            }
            for(int i = 5-(""+b.quantity).length(); i>0; i--){
                brQ+=" ";
            }
            for(int i = 2-(""+id).length(); i>0; i--){
                brID+=" ";
            }
            for(int i = (17-b.getCategory().length())/2;i>0;i--){
                brC1+=" "; brC2+=" ";
            }
            if((b.getCategory().length())%2==0)
                brC2+=" ";
            
            if(au instanceof Admin){
                if(b.quantity==0)
                    System.out.println(ProjekatOOPDaliborAksovicRT3017.BLACK_BOLD+" "+id+brID+" |  "+b.name+brN+"|    "+b.volume+"     |     "+b.price+brP+"   |"+brC1+b.category+brC2+"|  "+b.quantity+ProjekatOOPDaliborAksovicRT3017.RESET);
                else 
                    System.out.println(" "+id+brID+" |  "+b.name+brN+"|    "+b.volume+"     |     "+b.price+brP+"   |"+brC1+b.category+brC2+"|  "+b.quantity);
            }else{
                if(b.quantity==0)
                    System.out.println(ProjekatOOPDaliborAksovicRT3017.BLACK_BOLD+" "+id+brID+" |  "+b.name+brN+"|    "+b.volume+"     |     "+b.price+brP+"   |"+brC1+b.category+brC2+ProjekatOOPDaliborAksovicRT3017.RESET);
                else 
                    System.out.println(" "+id+brID+" |  "+b.name+brN+"|    "+b.volume+"     |     "+b.price+brP+"   |"+brC1+b.category+brC2);
            }
        }
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------");
        
    };

    @Override
    public int compareTo(ABeverage b) {
        int compare = this.category.compareTo(b.getCategory());
        if(compare == 0){
            compare = this.getName().compareTo(b.getName());
        }
        return compare;
    }
    
    public static int indexOfStringWise(ABeverage o, ArrayList<ABeverage> array) {
        for (int i = 0; i < array.size(); i++)
            if (o.toString().equals(array.get(i).toString()))
                return i;
        
        return -1;
    }
}

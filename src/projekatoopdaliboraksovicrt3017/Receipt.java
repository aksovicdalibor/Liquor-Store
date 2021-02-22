/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekatoopdaliboraksovicrt3017;

import Drinks.ABeverage;
import Drinks.Beer;
import Drinks.Beverage;
import Drinks.Champagne;
import Drinks.Juice;
import Drinks.MineralWater;
import Drinks.Soda;
import Drinks.SpiritDrink;
import Drinks.Water;
import Drinks.Wine;
import Users.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author dalib
 */
public class Receipt {
    static ArrayList<Receipt> receipts = new ArrayList<>();
    Date date; 
    ArrayList<Beverage> selectedBeverages;
    ArrayList<Integer> quantities;
    String customersUsername;
    
    public Receipt(ArrayList<ABeverage> selectedBeverages, ArrayList<Integer> quantities, String username) {
        this.selectedBeverages = new ArrayList<>();
        this.quantities = quantities;
        this.customersUsername = username;
        this.date = new Date();
        
        for(int i = 0; i<selectedBeverages.size(); i++){
            ABeverage temp=selectedBeverages.get(i);
            switch(temp.getCategory()){
            case "beer": { this.selectedBeverages.add(new Beer(temp.getName(), temp.getVolume(), temp.getPrice(), temp.getQuantity())); break; }
            case "wine": { this.selectedBeverages.add(new Wine(temp.getName(), temp.getVolume(), temp.getPrice(), temp.getQuantity())); break; }
            case "champagne": { this.selectedBeverages.add(new Champagne(temp.getName(), temp.getVolume(), temp.getPrice(), temp.getQuantity())); break; }
            case "spirit drink": { this.selectedBeverages.add(new SpiritDrink(temp.getName(), temp.getVolume(), temp.getPrice(), temp.getQuantity())); break; }
            case "juice": { this.selectedBeverages.add(new Juice(temp.getName(), temp.getVolume(), temp.getPrice(), temp.getQuantity())); break; }
            case "soda": { this.selectedBeverages.add(new Soda(temp.getName(), temp.getVolume(), temp.getPrice(), temp.getQuantity())); break; }
            case "water": { this.selectedBeverages.add(new Water(temp.getName(), temp.getVolume(), temp.getPrice(), temp.getQuantity())); break; }
            case "mineral water": { this.selectedBeverages.add(new MineralWater(temp.getName(), temp.getVolume(), temp.getPrice(), temp.getQuantity())); break; }
            }
        }
    }
    
    public static ArrayList<Receipt> getReceipts() {
        importReceipts();
        return receipts;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ArrayList<Beverage> getSelectedBeverages() {
        return selectedBeverages;
    }

    public void setSelectedBeverages(ArrayList<Beverage> selectedBeverages) {
        this.selectedBeverages = selectedBeverages;
    }

    public String getCustomersUsername() {
        return customersUsername;
    }

    public void setCustomersUsername(String customersUsername) {
        this.customersUsername = customersUsername;
    }

   

    public ArrayList<Integer> getQuantities() {
        return quantities;
    }

    public void setQuantities(ArrayList<Integer> quantities) {
        this.quantities = quantities;
    }

    
    
    @Override
    public String toString() {
        return "Receipt{" + "date=" + date + ", selectedBeverages=" + selectedBeverages + ", quantities=" + quantities + ", customer=" + customersUsername + '}';
    }
    
    public void printReceipt(){
        
        System.out.println("-------------------------------------------------------------------------------------------");
        System.out.print(" Item:                         "+"|  Price(€) per unit:  "+"|  # of units:  |  Price for item:  \n");     
        System.out.println("-------------------------------------------------------------------------------------------");
        
        for(int i=0; i<selectedBeverages.size(); i++){
            try{
                String brN="",brP1="",brP2="", brQ1="", brQ2="",brTP1="",brTP2="";

                double pricePerBottle = selectedBeverages.get(i).getPrice();
                int qty = quantities.get(i);

                // <editor-fold defaultstate="collapsed" desc=" Spacer for name ">
                for (int j = selectedBeverages.get(i).getName().length(); j < 30; j++) {
                    brN += " ";
                }

    // </editor-fold>

                // <editor-fold defaultstate="collapsed" desc=" Spacesrs for price per bottle ">
                for (int j = (22 - ("" + selectedBeverages.get(i).getPrice()).length()) / 2; j > 0; j--) {
                    brP1 += " ";
                    brP2 += " ";
                }
                if ((("" + selectedBeverages.get(i).getPrice()).length()) % 2 == 1) {
                    brP2 += " ";
                }

    // </editor-fold>

                // <editor-fold defaultstate="collapsed" desc=" Spacers for quantity ">
                for (int j = (15 - ("" + quantities.get(i)).length()) / 2; j > 0; j--) {
                    brQ1 += " ";
                    brQ2 += " ";
                }
                if ((("" + quantities.get(i)).length()) % 2 == 0) {
                    brQ2 += " ";
                }

    // </editor-fold>

                // <editor-fold defaultstate="collapsed" desc=" Spacers for price*qty ">
                for (int j = (19 - ("" + pricePerBottle * qty).length()) / 2; j > 0; j--) {
                    brTP1 += " ";
                    brTP2 += " ";
                }
                if ((("" + quantities.get(i)).length()) % 2 == 0) {
                    brTP2 += " ";
                }

    // </editor-fold>

                System.out.println(" "+
                        selectedBeverages.get(i).getName()+brN+"|"
                        +brP1+pricePerBottle+brP2+"|"
                        +brQ1+qty+brQ2+"|"
                        +brTP1+pricePerBottle*qty);

            
            }catch(Exception ex){
                System.out.println(ex.toString());
            }
            
        System.out.println("-------------------------------------------------------------------------------------------");
        }
        double total = total(this);
        
        System.out.println("\n      *Total price is: "+total+"€\n");
    }
    
    public void printReceipt(String tab){
    
        for(int i=0; i<selectedBeverages.size(); i++){
            try{
                String brN="",brP1="",brP2="", brQ1="", brQ2="",brTP1="",brTP2="";

                double pricePerBottle = selectedBeverages.get(i).getPrice();
                int qty = quantities.get(i);

                // <editor-fold defaultstate="collapsed" desc=" Spacer for name ">
                for (int j = selectedBeverages.get(i).getName().length(); j < 30; j++) {
                    brN += " ";
                }

    // </editor-fold>

                // <editor-fold defaultstate="collapsed" desc=" Spacesrs for price per bottle ">
                for (int j = (22 - ("" + selectedBeverages.get(i).getPrice()).length()) / 2; j > 0; j--) {
                    brP1 += " ";
                    brP2 += " ";
                }
                if ((("" + selectedBeverages.get(i).getPrice()).length()) % 2 == 1) {
                    brP2 += " ";
                }

    // </editor-fold>

                // <editor-fold defaultstate="collapsed" desc=" Spacers for quantity ">
                for (int j = (15 - ("" + quantities.get(i)).length()) / 2; j > 0; j--) {
                    brQ1 += " ";
                    brQ2 += " ";
                }
                if ((("" + quantities.get(i)).length()) % 2 == 0) {
                    brQ2 += " ";
                }

    // </editor-fold>

                // <editor-fold defaultstate="collapsed" desc=" Spacers for price*qty ">
                for (int j = (19 - ("" + pricePerBottle * qty).length()) / 2; j > 0; j--) {
                    brTP1 += " ";
                    brTP2 += " ";
                }
                if ((("" + quantities.get(i)).length()) % 2 == 0) {
                    brTP2 += " ";
                }

    // </editor-fold>

                System.out.println(tab+" "+
                        selectedBeverages.get(i).getName()+brN+"|"
                        +brP1+pricePerBottle+brP2+"|"
                        +brQ1+qty+brQ2+"|"
                        +brTP1+pricePerBottle*qty);

            
            }catch(Exception ex){
                System.out.println(ex.toString());
            }
        }
        double total = total(this);
        
        System.out.println("\n      *Total price is: "+total+"€\n");
    }
    
    static public void exportReceipt(Receipt r){
        
        Gson gson = new Gson();
        
        importReceipts();
//        JsonReader reader = null;
//        ArrayList<Receipt> history = new ArrayList<>();
        try{
            receipts.add(r);
            
            FileWriter fw = new FileWriter("receipts.json");
            gson.toJson(receipts, fw);
            fw.close();

        }catch (FileNotFoundException ex) {
            System.out.println("File not found.");
            
        }catch (IOException ex) {
            System.out.println("An error occurred while accessing the receipts file.");           
        }
    }

    static private void importReceipts(){
        
        Gson gson = new Gson();
        try{
            FileReader fr = new FileReader("receipts.json");
            JsonReader reader = new JsonReader(fr);
            receipts = gson.fromJson(reader,new TypeToken<List<Receipt>>(){}.getType());
            reader.close();
            fr.close();
        }catch (FileNotFoundException ex) {
            System.out.println("File not found.");
        }catch (IOException ex) {
            System.out.println("An error occurred while accessing the receipts file.");           
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
    }
    
    public static double total(Receipt r){
        double value=0; 
        for(int i=0; i<r.selectedBeverages.size(); i++){
            value += r.selectedBeverages.get(i).getPrice()*r.quantities.get(i);
        }
        return value;
    }

    
    
    
    
}

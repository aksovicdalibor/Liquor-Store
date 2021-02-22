/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Users;


import Exceptions.IDoutOfRangeException;
import Exceptions.QuantityOutOfRangeException;
import Exceptions.PriceOutOfRangeException;
import Exceptions.*;
import Drinks.*;
import static Drinks.Beverage.importBevs;
import static Users.User.exportUsers;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.jfr.events.FileWriteEvent;
import projekatoopdaliboraksovicrt3017.Meni;
import projekatoopdaliboraksovicrt3017.ProjekatOOPDaliborAksovicRT3017;
import static projekatoopdaliboraksovicrt3017.ProjekatOOPDaliborAksovicRT3017.mainMenu;
import projekatoopdaliboraksovicrt3017.Receipt;

/**
 *
 * @author dalib
 */
public class Admin extends AbstractUser{

    
    public Admin() {
        super("admin", "admin");
    }
    
    @Override
    public boolean changePassword() {
        Scanner input = new Scanner(System.in);
        boolean exceptionFound; 
        String tempPassword;
        FileWriter fw;
        
        System.out.println("Please, enter new password: ");
        
        do {
            try {
                exceptionFound = false;
                
                tempPassword = input.next();
                if (tempPassword.equalsIgnoreCase("cancel")) {
                    System.out.println("Password remains unchanged.");
                    return false;
                }
                if(tempPassword.length()<4){
                    throw new PasswordTooShortException();
                }
                
                fw = new FileWriter("admin.txt");
                BufferedWriter bw = new BufferedWriter(fw);

                bw.write(tempPassword);
                bw.close();
                fw.close();
                
                System.out.println("Password succesfully changed!");
            }catch (PasswordTooShortException ex) {
                exceptionFound = true;
                System.out.println("Password must have at least 4 characters.\n"
                        + "Please enter new password or type in 'cancel' for exit: ");
            }catch (Exception ex){
                System.out.println("Unexpected error occured, password remains unchanged, taking you back to menu..");
                return false;
            }
        } while (exceptionFound == true);
        return true;
    }
    
    public static void addBeverage(){
        // <editor-fold defaultstate="collapsed" desc=" Variables ">
        Scanner input = new Scanner(System.in);
        boolean flag = true;
        boolean invalidOption;
        boolean alreadyListed;

// </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc=" Category selection ">
        String optionS = "";
        String category = "";
        System.out.println("Let's add a new beverage!\n\nFirst, select a category:\n ");
        do {
            try {
                System.out.println(ABeverage.beverageCategories);
                optionS = input.nextLine();
                if (optionS.length() == 1) {
                    invalidOption = false;
                    switch (optionS) {
                        case "1": {
                            category = "beer";
                            break;
                        }
                        case "2": {
                            category = "wine";
                            break;
                        }
                        case "3": {
                            category = "champagne";
                            break;
                        }
                        case "4": {
                            category = "spirit drink";
                            break;
                        }
                        case "5": {
                            category = "juice";
                            break;
                        }
                        case "6": {
                            category = "soda";
                            break;
                        }
                        case "7": {
                            category = "water";
                            break;
                        }
                        case "8": {
                            category = "mineral water";
                            break;
                        }
                        case "0": {
                            System.out.println("No beverage added.");
                            return;
                        }
                        default:
                            throw new InvalidOptionException();
                    }
                } else {
                    throw new Exception();
                }
            } catch (InvalidOptionException ex) {
                invalidOption = true;
                System.out.println("***Invalid input!***\n\n" + " Try again: \n\n" + ABeverage.beverageCategories);
            } catch (Exception ex) {
                invalidOption = true;
                System.out.println("***Category must cotain only 1 character!***\n\n" + " Try again: \n\n");
            }
        } while (invalidOption == true);
        

// </editor-fold>
        
        String name = "";
        double volume = 0;
        do {
            alreadyListed = false;

            // <editor-fold defaultstate="collapsed" desc=" BeverageName input ">
            
            flag = true;
            System.out.println("Please enter name of the beverage(format: Brand-Name) or \"cancel\" to exit: ");
            do {
                try {
                    name = input.nextLine();
                    if (name.equalsIgnoreCase("cancel")) {
                        return;
                    }
                    if (name.length() < 2) {
                        throw new NameTooShortException();
                    }
                    
                    flag = false;                    
                } catch (NameTooShortException ex) {
                    flag = true;
                    System.out.println("Name of the beverage must consist of at least two letters. \n"
                            + "Please enter again "
                            + "or type in 'cancel' to exit: ");
                }                
            } while (flag == true);

            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc=" Volume input ">
            
            String volumeS = "";
            flag = true;
            System.out.println("Please enter volume of the beverage (in litres) or \"cancel\" to exit: ");
            do {
                try {
                    volumeS = input.nextLine();
                    if (volumeS.equalsIgnoreCase("cancel")) {
                        return;
                    }
                    if((Double.parseDouble(volumeS)>=10)||(Double.parseDouble(volumeS)<=0)){
                        throw new VolumeOutOfRangeException();
                    }
                        
                    volume = Double.parseDouble(volumeS);
                    flag = false;            
                    
                    
                } catch (NumberFormatException ex) {
                    flag = true;
                    System.out.println("Invalid format. \n"
                            + "Please enter again "
                            + "or type in \"cancel\" for exit: ");
                } catch (VolumeOutOfRangeException ex) {
                    flag = true;
                    System.out.println("Volume is out of range(0<volume<10). \n"
                            + "Please enter again "
                            + "or type in \"cancel\" for exit: ");
                }                
            } while (flag == true);

            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc=" Check if beverage is already listed ">
            ABeverage tempBev;
            if ((tempBev = ABeverage.checkBevName(name)) != null) {
                try {
                    if (tempBev.getVolume() == volume && tempBev.getCategory().equals(category)) {
                        throw new AlreadyListedException();
                    }
                } catch (AlreadyListedException ex) {
                    System.out.println("Sorry, but beverage you tried to add already exsists.\n"
                            + "Please, repeat inputs.\n");
                    alreadyListed = true;
                }
            }

            // </editor-fold>

        } while (alreadyListed == true);

        // <editor-fold defaultstate="collapsed" desc=" Price input ">
        
        String priceS = "";
        double price = 0;
        System.out.println("Enter price in EUR(per bottle) or \"cancel\" to exit: ");
        flag = true;
        do {
            try {
                priceS = input.nextLine();
                if (priceS.equalsIgnoreCase("cancel")) {
                    return;
                }
                if((Double.parseDouble(priceS)>=1000)||(Double.parseDouble(priceS)<=0)){
                        throw new PriceOutOfRangeException();
                    }
                
                price = Double.parseDouble(priceS);
                flag = false;                
            } catch (NumberFormatException ex) {
                flag = true;
                System.out.println("Invalid format. \n"
                        + "Please enter again "
                        + "or type in \"cancel\" for exit: ");
            } catch (PriceOutOfRangeException ex) {
                flag = true;
                System.out.println("Price is out of range(0<price<1000). \n"
                        + "Please enter again "
                        + "or type in \"cancel\" for exit: ");
            }            
        } while (flag == true);

// </editor-fold>

        // <editor-fold defaultstate="collapsed" desc=" Quantity input ">
        
        String quantityS = "";
        int quantity = 0;
        System.out.println("Enter number of bottles available or \"cancel\" to exit: ");
        flag = true;
        do {
            try {
                quantityS = input.nextLine();
                if (quantityS.equalsIgnoreCase("cancel")) {
                    return;
                }
                if((Double.parseDouble(quantityS)>=1000)||(Double.parseDouble(quantityS)<=0)){
                        throw new QuantityOutOfRangeException();
                }
                
                quantity = Integer.parseInt(quantityS);
                flag = false;                
            } catch (NumberFormatException ex) {
                flag = true;
                System.out.println("Invalid format. \n"
                        + "Please enter again "
                        + "or type in \"cancel\" for exit: ");
            } catch (QuantityOutOfRangeException ex) {
                flag = true;
                System.out.println("Number of bottles available is out of range(0<quantity<1000). \n"
                        + "Please enter again "
                        + "or type in \"cancel\" for exit: ");
            } catch (Exception ex){
                System.out.println(ex.toString());
            }
        } while (flag == true);

        // </editor-fold>

        ArrayList<ABeverage> bevs = Beverage.getDrinks();

        
        switch(category){
            case "beer": { bevs.add(new Beer(name, volume, price, quantity)); break; }
            case "wine": { bevs.add(new Wine(name, volume, price, quantity)); break; }
            case "champagne": { bevs.add(new Champagne(name, volume, price, quantity)); break; }
            case "spirit drink": { bevs.add(new SpiritDrink(name, volume, price, quantity)); break; }
            case "juice": { bevs.add(new Juice(name, volume, price, quantity)); break; }
            case "soda": { bevs.add(new Soda(name, volume, price, quantity)); break; }
            case "water": { bevs.add(new Water(name, volume, price, quantity)); break; }
            case "mineral water": { bevs.add(new MineralWater(name, volume, price, quantity)); break; }
        }
        
        Collections.sort(bevs);
        
        if(ABeverage.exportBevs())
            System.out.println("Beverage succesfully added!");
        else 
            System.out.println("Error occured. Beverage entered has not been added!.");
        
        
    }
    
    public static void removeBeverage() {
        Scanner input = new Scanner(System.in);
        String id="";
        boolean invalidID;
        
        
        ArrayList<ABeverage> bevs = ABeverage.getDrinks();
        System.out.println("Please, enter ID of the drink that you want to remove or \"cancel\" to exit: ");
        do {
            ABeverage.bevOverview(new Admin());
            invalidID = false;
            try {
                id = input.nextLine();
                if(id.trim().equalsIgnoreCase("cancel"))
                    return;
                if (id.length() <= (""+bevs.size()).length()) {
                    if(Integer.parseInt(id)>ABeverage.getDrinks().size())
                        throw new IDoutOfRangeException();
                    else{
                        ABeverage.getDrinks().remove(Integer.parseInt(id)-1);
                        System.out.println("Beverage deleted!");
                        ABeverage.exportBevs();
                    }
                } else {
                    throw new Exception();
                }
                
            } catch (IDoutOfRangeException ex) {
                invalidID = true;
                System.out.println("***ID is not found!***\n\n" + " Try again or type in \"cancel\" to exit: \n\n");
            } catch (Exception ex) {
                invalidID = true;
                System.out.println("***Invalid input!***\n\n" + " Try again or type in \"cancel\" to exit: \n\n");
            }
        } while (invalidID == true);
        
    }

    public static void alterBeverage() {
        Scanner input = new Scanner(System.in);
        String id="";
        boolean invalidID;
        
        
        ArrayList<ABeverage> bevs = ABeverage.getDrinks();
        System.out.println("Please, enter ID of the drink that you want to change or \"cancel\" to exit: ");
        do {
            ABeverage.bevOverview(new Admin());
            invalidID = false;
            try {
                id = input.nextLine();
                if(id.trim().equalsIgnoreCase("cancel"))
                    return;
                if (id.length() <= (""+bevs.size()).length()) {
                    if(Integer.parseInt(id)>ABeverage.getDrinks().size())
                        throw new IDoutOfRangeException();
                    else{
                        //essentially empty block
                        System.out.println("");
                    }
                } else {
                    throw new Exception();
                }
                
            } catch (IDoutOfRangeException ex) {
                invalidID = true;
                System.out.println("***ID is not found!***\n\n" + " Try again or type in \"cancel\" to exit: \n\n");
            } catch (Exception ex) {
                invalidID = true;
                System.out.println("***Invalid input!***\n\n" + " Try again or type in \"cancel\" to exit: \n\n");
            }
        } while (invalidID == true);
        
        String option = "";
        boolean invalidOption;
        do{
            invalidOption = false;
            System.out.println("Choose an attribute to change:\n");
            try{
                System.out.println("1. Price\n2. Quantity\n");
                option = input.nextLine();
                if(option.length()==1)
                    switch(option){
                        case "1": { 
                            // <editor-fold defaultstate="collapsed" desc=" New price input ">
        
                            String priceS = "";
                            double price = 0;
                            System.out.println("Enter new price in EUR(per bottle) or \"cancel\" to exit: ");
                            boolean flag = true;
                            do {
                                try {
                                    priceS = input.nextLine();
                                    if (priceS.equalsIgnoreCase("cancel")) {
                                        return;
                                    }
                                    if((Double.parseDouble(priceS)>=1000)&&(Double.parseDouble(priceS)<=0)){
                                            throw new PriceOutOfRangeException();
                                        }

                                    price = Double.parseDouble(priceS);
                                    flag = false;                
                                } catch (NumberFormatException ex) {
                                    flag = true;
                                    System.out.println("Invalid format. \n"
                                            + "Please enter again "
                                            + "or type in \"cancel\" for exit: ");
                                } catch (PriceOutOfRangeException ex) {
                                    flag = true;
                                    System.out.println("Price is out of range(0<price<1000). \n"
                                            + "Please enter again "
                                            + "or type in \"cancel\" for exit: ");
                                }            
                            } while (flag == true);
                            
                            ABeverage.getDrinks().get(Integer.parseInt(id)-1).setPrice(price);

                    // </editor-fold>
                            break; 
                        }
                        case "2": { 
                            // <editor-fold defaultstate="collapsed" desc=" New quantity input ">
        
                            String quantityS = "";
                            int quantity = 0;
                            System.out.println("Enter new number of bottles available or \"cancel\" to exit: ");
                            boolean flag = true;
                            do {
                                try {
                                    quantityS = input.nextLine();
                                    if (quantityS.equalsIgnoreCase("cancel")) {
                                        return;
                                    }
                                    if((Double.parseDouble(quantityS)>=1000)&&(Double.parseDouble(quantityS)<=0)){
                                            throw new QuantityOutOfRangeException();
                                    }

                                    quantity = Integer.parseInt(quantityS);
                                    flag = false;                
                                } catch (NumberFormatException ex) {
                                    flag = true;
                                    System.out.println("Invalid format. \n"
                                            + "Please enter again "
                                            + "or type in \"cancel\" for exit: ");
                                } catch (QuantityOutOfRangeException ex) {
                                    flag = true;
                                    System.out.println("Number of bottles available is out of range(0<quantity<1000). \n"
                                            + "Please enter again "
                                            + "or type in \"cancel\" for exit: ");
                                }            
                            } while (flag == true);

                             ABeverage.getDrinks().get(Integer.parseInt(id)-1).setQuantity(quantity);
                            // </editor-fold>
                            break; 
                        }
                        default: throw new Exception();
                    }
                else
                    throw new Exception();
            }catch(Exception ex){
                System.out.println("***Invalid input!***\n\n"+" Try again: \n\n");
                invalidOption = true;
            }
        }while(invalidOption);
        
        ABeverage.exportBevs();
    }

    public static void usersOverview() {
        User.importUsers();
               
        
        System.out.println("All users: ");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------");
        System.out.print(" Name:                           "+"|   Usermame:   "+"|         Email:         "+"|  Credit: \n");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------");
        for(User u:User.users){
            String spacer="";
            for(int i = 33-u.getFirstName().length()-u.getLastName().length()-2; i>0; i--){
                spacer+=" ";
            }
            System.out.print(" "+u.getFirstName().trim()+" "+u.getLastName().trim()+spacer+"|");
            
            
            String spacerF="",spacerB="";
            for(int i = (15-u.getUserName().length())/2; i>0; i--){
                spacerF+=" "; spacerB+=" ";
            }
            if((u.getUserName().length())%2==0)
                spacerF+=" ";
            System.out.print(spacerB+u.getUserName()+spacerF+"|");
            
            
            spacerF=""; spacerB="";
            for(int i = (24-u.getEmail().length())/2; i>0; i--){
                spacerF+=" "; spacerB+=" ";
            }
            if((u.getEmail().length())%2==1)
                spacerF+=" ";
            System.out.print(spacerB+u.getEmail()+spacerF+"|");
            
            
            spacer="";
            for(int i = 8-(""+u.getCredit()).length(); i>0; i--){
                spacer+=" ";
            }
            System.out.println("  "+u.getCredit()+spacer);
        }
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------");
        System.out.print("Enter anything to continue to menu: ");
                                            
    }

    public static void putCredit() {
        Scanner input = new Scanner(System.in);
        String userName="";
        boolean flag=true;
        User u;
        String amount="";
        
        System.out.println("Enter username or type \"cancel\" for exit: ");
        do {
            flag = false;
            try {
                userName = input.nextLine();
                if(userName.equalsIgnoreCase("admin")){
                    throw new AdminRegForbiddenException();
                }
                if(userName.equalsIgnoreCase("cancel")){
                    return;
                }
                if((u=User.checkUserName(userName))!=null){
                    System.out.println("Enter the amount to add(put \"-\" for subtraction): ");
                    amount = input.nextLine();
                    if(amount.equalsIgnoreCase("cancel")){
                        return;
                    }
                    u.setCredit(u.getCredit()+Double.parseDouble(amount)); 
                    User.exportUsers();
                    System.out.println("Credits of User: "+u.getFirstName()+" "+u.getLastName()+" succesfully changed!");
                }else 
                    throw new UsernameNotFoundException();
            } catch (AdminRegForbiddenException ex) {
                flag = true;
                System.out.println("Username cannot be admin.\n"
                        + "Please enter new one or type in 'cancel' for exit: ");
            } catch (NumberFormatException ex) {
                flag = true;
                System.out.println("Invalid amount entered.\n"
                        + "Please, try again or type in 'cancel' for exit: ");
            } catch (UsernameNotFoundException ex) {
                flag = true;
                System.out.println("Username not found.\n"
                        + "Please, try again or type in 'cancel' for exit: ");
            }
        
        }while (flag == true);

    }
    
    public static void historyOfPurchases(){
        ArrayList<Receipt> receipts = Receipt.getReceipts();
        
        if(receipts != null){
            System.out.println("All purchases: ");
            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------");
            System.out.print(" Date:                       \t"+"Customer:      "+"             Item:                "+"       Price:       "+"         QTY:       Price per item: \n");
            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------");

            for(Receipt r:receipts){
                String tab = "                                                 ";

                System.out.print(r.getDate()+"\t");
                System.out.print(r.getCustomersUsername()+":\n");
                r.printReceipt(tab);
                System.out.println("---------------------------------------------------------------------------------------------------------------------------------------");
            }
            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------");
        }else{
            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("There are no purchases to show at the moment.");
            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------");
        }
        System.out.print("Enter anything to continue to menu: ");
    }
}

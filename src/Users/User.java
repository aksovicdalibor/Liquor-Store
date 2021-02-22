/*
 * 
 */
package Users;

import Drinks.ABeverage;
import Exceptions.OutOfStockException;
import Drinks.AlcoholicDrink;
import Drinks.Beer;
import Exceptions.MoreThanAvailableException;
import Drinks.Beverage;
import Drinks.Champagne;
import Drinks.Juice;
import Drinks.MineralWater;
import Drinks.Soda;
import Drinks.SpiritDrink;
import Drinks.Water;
import Drinks.Wine;
import Exceptions.IDoutOfRangeException;
import Exceptions.PasswordTooShortException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Year;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.border.BevelBorder;
import jdk.jfr.events.FileWriteEvent;
import projekatoopdaliboraksovicrt3017.Receipt;

/**
 *
 * @author dalib
 */
public class User extends AbstractUser{
    public static List<User> users = new ArrayList<User>();
    private String firstName;
    private String lastName;
    private String email;
    private int yearOfBirth;
    private double credit;
    
    

    public User(String firstName, String lastName, String email, int yearOfBirth, String userName, String password) {
        //Called on registration
        super(userName, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.yearOfBirth = yearOfBirth;
        this.credit = 0;
    }
    
    public User(String firstName, String lastName, String email, int yearOfBirth, String userName, String password, double credit) {
        //Called when reading users from a file
        super(userName, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.yearOfBirth = yearOfBirth;
        this.credit = credit;
    }

    public static ArrayList<User> getUsers() {
        return new ArrayList<User>(users);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean changePassword() {
        Scanner input = new Scanner(System.in);
        boolean exceptionFound;
        String tempPassword;
        
        System.out.println("Enter new password: ");
        do {
            exceptionFound = false;
            try {
                tempPassword = input.next();
                if (tempPassword.equalsIgnoreCase("cancel")) {
                    return false;
                }
                if(tempPassword.length()<8){
                    throw new PasswordTooShortException();
                }
                password = tempPassword;
                exportUsers();
                System.out.println("Password succesfully changed!");
            }catch (PasswordTooShortException ex) {
                exceptionFound = true;
                System.out.println("Password must have at least 8 characters.\n"
                        + "Please enter new password or type in 'cancel' for exit: ");
            }catch (Exception ex){
                System.out.println("Unexpected error occured, password remains unchanged, taking you back to menu.");
                return false;
            }
        } while (exceptionFound == true);
        return true;
    }
    
    static private void clearUsers(){
        users.clear();
    }
    
    static public void importUsers(){ // i.e. Reading users from a file into Static Member users
        if(!getUsers().isEmpty()){
            clearUsers();
        }
        
        List<User> tempUsers = new ArrayList<User>();
        
        FileReader fr;
        try{
            fr = new FileReader("users.txt");
            BufferedReader br = new BufferedReader(fr);

            String temp;
            String[] partsOfTemp;

            while((temp=br.readLine())!=null){
                partsOfTemp = temp.split("\\,");
                tempUsers.add(new User( partsOfTemp[0].trim(), 
                                    partsOfTemp[1].trim(), 
                                    partsOfTemp[2].trim(), 
                                    Integer.parseInt(partsOfTemp[3].trim()),
                                    partsOfTemp[4].trim(), 
                                    partsOfTemp[5].trim(), 
                                    Double.parseDouble(partsOfTemp[6].trim())));
            }  
            
            
            br.close();
            fr.close();
        } catch (FileNotFoundException ex) {
            System.out.println("File not found.");
        } catch (IOException ex) {
            System.out.println("An error occurred while accessing the users.txt file.");
        }
        users = tempUsers;
    }
    
    static public boolean exportUsers(){ //i.e. Writing records from Static Member users to file
        FileWriter fw;
        try{
            fw = new FileWriter("users.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            for(User u:users){
                bw.write(u.firstName+","+u.lastName+","+u.email+","+u.yearOfBirth+","+u.userName+","+u.password+","+u.credit);
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
    
    static public User checkUserName(String username){
        importUsers();
        for(User u:users){
            if(username.trim().equalsIgnoreCase(u.userName.trim()))
                return u;
        }
        return null;
    }
    
    static public boolean checkEmail(String email){
        importUsers();
        for(User u:users){
            if(email.trim().equalsIgnoreCase(u.email))
                return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "User{"+ "username="+ userName + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", yearOfBirth=" + yearOfBirth + ", credit=" + credit + '}';
    }
    
    public static String listToString(){
        String value="";
        for (User u:users){
            value+=u+"\n";
        }
        return value;
    }
    
    public void purchase(){       
        Scanner input = new Scanner(System.in);
        String id = "";
        String status = "";
        boolean invalidID;
        boolean continueShopping = true;
        ArrayList<ABeverage> bevs = Beverage.getDrinks();
        ArrayList<ABeverage> bevsInCart = new ArrayList<>();
        ArrayList<Integer> quantitiesInCart = new ArrayList<>();
        
        
        System.out.println("Please, enter ID of the drink that you want to purchase \n"
                         + "(*greyed out ones are out of stock) or \"cancel\" to exit: ");
        do{
            // <editor-fold defaultstate="collapsed" desc="  ">
            do {
                Beverage.bevOverview(this);
                // <editor-fold defaultstate="collapsed" desc=" Input for beverage ID ">
                invalidID = false;
                try {
                    id = input.nextLine();
                    if (id.trim().equalsIgnoreCase("cancel")) {
                        System.out.println("The purchase was cancelled.");
                    //resetting quantites in case of cancelling of the purchase
                        for(int i = 0; i<bevsInCart.size();i++){
                            bevs.get(Beverage.indexOfStringWise(bevsInCart.get(i), bevs)).setQuantity(bevs.get(Beverage.indexOfStringWise(bevsInCart.get(i), bevs)).getQuantity()+quantitiesInCart.get(i));
                        }
                        ABeverage.exportBevs();
                        return;
                    }
                    
                    if (id.length() <= ("" + bevs.size()).length()) {
                        if (bevs.get(Integer.parseInt(id)-1).getQuantity()==0) {
                            throw new OutOfStockException();
                        }
                        if (Integer.parseInt(id) > Beverage.getDrinks().size()) {
                            throw new IDoutOfRangeException();
                        }
                    } else {
                        throw new Exception();
                    }
                    
                } catch (IDoutOfRangeException ex) {
                    invalidID = true;
                    System.out.println("***ID is not found!***\n\n" + " Try again or type in \"cancel\" to exit: \n\n");
                }catch (OutOfStockException ex){
                    invalidID = true;
                    System.out.println("Selected beverage is not available at the moment.\n"
                                        + "Please, try again or type in \"cancel\" to exit: ");
                } catch (Exception ex) {
                    invalidID = true;
                    System.out.println("***Invalid input!***\n\n" + " Try again or type in \"cancel\" to exit: \n\n");
                }
// </editor-fold> 
            } while (invalidID == true);
            
            
            String amount = "";
            boolean invalidAmount;
            do {                                    
                // <editor-fold defaultstate="collapsed" desc=" Input for amount and adding to temp lists if amount is valid">
                invalidAmount = false;
                System.out.println("Please enter the amount that you would like to buy or \"cancel\" to dismiss this drink from order: ");
                try {
                    amount = input.nextLine();

                     if (amount.trim().equalsIgnoreCase("cancel")) {
                        System.out.println("Last beverage was not added to receipt.");
                        break;
                    }
                    if (Integer.parseInt(amount) > bevs.get(Integer.parseInt(id) - 1).getQuantity()) {
                        throw new MoreThanAvailableException();
                    }
                    if(Integer.parseInt(amount)==0){
                        throw new NumberFormatException();
                    }
                    int alreadyInCart = ABeverage.indexOfStringWise(bevs.get(Integer.parseInt(id) - 1), bevsInCart); 
                    if(alreadyInCart==-1){      // if it IS NOT already in cart
                        bevsInCart.add(bevs.get(Integer.parseInt(id) - 1));
                        quantitiesInCart.add(Integer.parseInt(amount));
                        bevs.get(Integer.parseInt(id) - 1).setQuantity(bevs.get(Integer.parseInt(id) - 1).getQuantity()-Integer.parseInt(amount));
                        ABeverage.exportBevs();
                    }else{                      // if it IS already in cart -> just update quantities.
                        int startQuantity = quantitiesInCart.get(alreadyInCart);
                        quantitiesInCart.set(alreadyInCart, startQuantity+Integer.parseInt(amount));
                    }  
                } catch (NumberFormatException ex) {
                    invalidAmount = true;
                    System.out.println("**Invalid quantity entered, please enter again(must be natural number(integer greater than 0)or \"cancel\" to exit: ");
                } catch (MoreThanAvailableException ex) {
                    invalidAmount = true;
                    System.out.println("Sorry, but amount you have entered is not available at the moment.\n"
                            + "Right now, we can send you maximum " + bevs.get(Integer.parseInt(id) - 1).getQuantity() + " bottles."
                            + "Please enter amount again or \"cancel\" to exit: ");
                } catch (Exception ex) {
                    invalidAmount = true;
                    System.out.println("*Invalid input. Please, try again or type in \"cancel\" to exit: ");
                }
            // </editor-fold>
            } while (invalidAmount == true);

            System.out.println("Type in \"c\" to go to checkout or anything else to contionue shopping: ");
            if(input.nextLine().equalsIgnoreCase("c"))
                continueShopping = false;           //Could also be just a break in endless while loop.
        }while(continueShopping == true);   
        
        
        if(this.notOfLegalAge()){
            // <editor-fold defaultstate="collapsed" desc=" Checking if underage user selected alcoholic beverage ">
            boolean illegalPurchase = false;
            for (ABeverage b : bevsInCart) {
                if (b instanceof AlcoholicDrink) {
                    illegalPurchase = true;
                    break;                    
                }
            }
            if (illegalPurchase == true) {
                System.out.println("There is an alcoholic drink added to your cart and yet, \n"
                        + "you are not of legal age to buy it. You can remove all of the \n"
                        + "alcoholic beverages by typing in \"r\" or dismiss the whole cart by\n"
                        + "entering anything: ");
                if (input.nextLine().equals("r")) {
                    ArrayList<ABeverage> bevsInCartCopy = new ArrayList<>();
                    ArrayList<Integer> quantitiesInCartCopy = new ArrayList<>();
                    for (ABeverage temp:bevsInCart) {
                        if (!(temp instanceof AlcoholicDrink)) {
                            bevsInCartCopy.add(temp);
                            quantitiesInCartCopy.add(quantitiesInCart.get(bevsInCart.indexOf(temp)));
                        }else{
                            bevs.get(Beverage.indexOfStringWise(temp, bevs)).setQuantity(bevs.get(Beverage.indexOfStringWise(temp, bevs)).getQuantity()+quantitiesInCart.get(bevsInCart.indexOf(temp)));
                        }
                    }
                    ABeverage.exportBevs();
                    
                    bevsInCart=bevsInCartCopy;
                    quantitiesInCart=quantitiesInCartCopy;
                } else {
                    System.out.println("Sorry, but we can't sell you alcoholic drink. \n"
                            + "The whole order has been dismissed.\n");
                //resetting quantites in case of cancelling of the purchase
                    for(int i = 0; i<bevsInCart.size();i++){
                        bevs.get(Beverage.indexOfStringWise(bevsInCart.get(i), bevs)).setQuantity(bevs.get(Beverage.indexOfStringWise(bevsInCart.get(i), bevs)).getQuantity()+quantitiesInCart.get(i));
                    }
                    ABeverage.exportBevs();
                    return;
                }                
            }

// </editor-fold>
        }
        
        if(bevsInCart.isEmpty()){
            System.out.println("You haven't ordered any non alcoholic beverages, so after removing the alcoholic ones,\n"
                + "your cart is left empty. Returning to menu.");
        }else {
            Receipt r = new Receipt( bevsInCart, quantitiesInCart, this.getUserName());
        
            if(Receipt.total(r)>this.getCredit()){
                System.out.println("Unfortunately, there is not enough credit on your account. The order has been dismissed.");
            //resetting quantites in case of cancelling of the purchase
                for(int i = 0; i<bevsInCart.size();i++){
                    bevs.get(Beverage.indexOfStringWise(bevsInCart.get(i), bevs)).setQuantity(bevs.get(Beverage.indexOfStringWise(bevsInCart.get(i), bevs)).getQuantity()+quantitiesInCart.get(i));
                }
                ABeverage.exportBevs();
                return;
            }
            System.out.println("Your receipt is:");
            r.printReceipt();
            System.out.println("We just need you to confirm purchase by typing in \"confirm\".\n");
            if(input.nextLine().trim().equalsIgnoreCase("confirm")){
                // <editor-fold defaultstate="collapsed" desc=" Confirmation of the purchase ">
                
                System.out.println("Purchase complete!");
                //Loop for setting the quantities of beverages according to number of beverages bought.
                for (ABeverage temp : bevsInCart) {
                    int sold = quantitiesInCart.get(bevsInCart.indexOf(temp));
                    int totalLeft = bevs.get(Beverage.indexOfStringWise(temp, bevs)).getQuantity();
                    bevs.get(Beverage.indexOfStringWise(temp, bevs)).setQuantity(totalLeft - sold);
                }
                Receipt.exportReceipt(r);
                Beverage.exportBevs();
                this.credit -= Receipt.total(r);
                exportUsers();
                
                // </editor-fold>
                
            }else{
                System.out.println("The purchase has been dismissed.");
            //resetting quantites in case of cancelling of the purchase
                for(int i = 0; i<bevsInCart.size();i++){
                    bevs.get(Beverage.indexOfStringWise(bevsInCart.get(i), bevs)).setQuantity(bevs.get(Beverage.indexOfStringWise(bevsInCart.get(i), bevs)).getQuantity()+quantitiesInCart.get(i));
                }
                ABeverage.exportBevs();
            }
        }
    } 

    private boolean notOfLegalAge() {
        if(Integer.parseInt(Year.now().toString())-yearOfBirth<18)
            return true;
        else 
            return false;
    }
}
























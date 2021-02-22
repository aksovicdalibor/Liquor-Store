
package projekatoopdaliboraksovicrt3017;


import Drinks.Beverage;
import Exceptions.InvalidAmountException;
import Exceptions.InvalidOptionException;
import Users.Admin;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ProjekatOOPDaliborAksovicRT3017 {
    
    // <editor-fold defaultstate="collapsed" desc="String mainMenu=..">

    public static String mainMenu =         "1. Login\n"
                                        +   "2. Registration\n"
                                        +   "\n"
                                        +   "0. Exit\n";
    
            // </editor-fold>

    public static final String RESET = "\033[0m";
    public static final String BLACK_BOLD = "\033[1;30m";
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String option = "";
        
        System.out.println("Welcome to our liquor store application! " + "\n\nPlease, choose an option: \n"+mainMenu);
        do{
            try{
                option = input.nextLine();
                if(option.length()==1)
                    switch(option){
                        case "1": { Meni.login(); break; }
                        case "2": { Meni.registration() ;break; }
                        case "0": { break; }
                        default: throw new InvalidOptionException();
                    }
                else
                    throw new Exception();
            }catch(InvalidOptionException ex){
                System.out.println("***Invalid option!***");
            } catch (Exception ex) {
                System.out.println("***Option must contain only 1 character!***");
            }
            if(!option.equals("0"))
            System.out.println("_____________________________________________________\n\n"
                                    +"Please, choose an option: \n\n"+mainMenu);
        }while(!option.equals("0"));
        
        System.out.println("Thanks for using our app! Have a good day!");
    }
    
}

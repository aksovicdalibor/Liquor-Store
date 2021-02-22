/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekatoopdaliboraksovicrt3017;

import Drinks.Beverage;
import Exceptions.NotADotException;
import Exceptions.*;
import Users.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Year;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import static projekatoopdaliboraksovicrt3017.ProjekatOOPDaliborAksovicRT3017.mainMenu;

/**
 *
 * @author dalib
 */
public class Meni {
    
    static public void login(){
        Scanner input = new Scanner(System.in);
        String loginUserName;
        String loginPassword;
        User tempUser; 
        
        System.out.println("Enter username: ");
        do{
            loginUserName=input.nextLine();
            if(loginUserName.equalsIgnoreCase("cancel")){
                System.out.println("\nReturning to main menu..");
                return;
            }
            if(loginUserName.trim().equalsIgnoreCase("admin")){
                // <editor-fold defaultstate="collapsed" desc=" Admins Logon UNFINISHED"> 

                // <editor-fold defaultstate="collapsed" desc=" Reading admins password from admin.txt ">

                FileReader fr;
                String adminsPassword = "";
                try {
                    fr = new FileReader("admin.txt");
                    BufferedReader br = new BufferedReader(fr);
                    adminsPassword = br.readLine();
                    br.close();
                    fr.close();
                } catch (FileNotFoundException ex) {
                    System.out.println("File admin.txt cannot be found.");
                } catch (IOException ex) {
                    System.out.println("An error occurred while accessing the admin.txt file.");
                }

    // </editor-fold>   

                System.out.println("Enter password: ");            
                do{
                    loginPassword = input.nextLine();
                    if(loginPassword.equalsIgnoreCase("cancel")){
                        System.out.println("\nReturning to main menu..");
                        return;
                    }
                    if (loginPassword.trim().equals(adminsPassword)) {
                        // <editor-fold defaultstate="collapsed" desc=" Admins options UNFINISHED">

                        // <editor-fold defaultstate="collapsed" desc=" String adminsMenu=.. ">

                        String adminsMenu =     "1. Beverage overview\n"
                                               +"2. Add beverage\n"
                                               +"3. Remove beverage\n"
                                               +"4. Alter beverage(change price or quantity)\n"
                                               +"5. Users overview\n"
                                               +"6. Add credits to specific user\n"
                                               +"7. Change password\n"
                                               +"8. History of purchases\n"
                                + "\n"
                                               +"0. Log out\n\n";

                        // </editor-fold>
                        String option = "";
                        System.out.println("\n\nWelcome, admin!\nPlease, choose an action: \n"); 
                        do{
                            try{
                                System.out.println(adminsMenu);
                                option = input.nextLine();
                                if(option.length()==1)
                                    switch(Integer.parseInt(option)){
                                        case 1: { 
                                            Beverage.bevOverview(new Admin());
                                            System.out.print("Enter anything to continue to menu: ");
                                            input.nextLine();
                                            break; 
                                        }
                                        case 2: { Admin.addBeverage(); break; }
                                        case 3: { Admin.removeBeverage(); break; }
                                        case 4: { Admin.alterBeverage(); break; }
                                        case 5: { 
                                            Admin.usersOverview(); 
                                            input.nextLine();
                                            break;
                                        }
                                        case 6: { Admin.putCredit(); break; }
                                        case 7: { new Admin().changePassword(); break; }
                                        case 8: {
                                            Admin.historyOfPurchases(); 
                                            input.nextLine();
                                            break; 
                                        }

                                        case 0: { break; }
                                        default: throw new Exception();
                                    }
                                else
                                    throw new Exception();
                            }catch(Exception ex){
                                System.out.println(ex.toString());
                                System.out.println("***Invalid input!***\n\n"+" Try again: \n\n");
                            }
                            if(!option.equals("0")){
                                System.out.println("==================================================\n\n"
                                    +"Please, choose an option:\n");
                            }
                        }while(!option.equals("0"));
                        return;



    // </editor-fold>
                    } else {
                        System.out.println("Wrong password! Try again or type cancel for exit:");
                    }
                }while(!loginPassword.trim().equals(adminsPassword.trim()));



    // </editor-fold>
            }
            else 
                if((tempUser=User.checkUserName(loginUserName))!=null){
                    // <editor-fold defaultstate="collapsed" desc=" User's Logon ">
                    System.out.println("Enter password: ");
                    do {
                        loginPassword = input.nextLine();
                         if(loginPassword.equalsIgnoreCase("cancel")){
                            return;
                        }
                        if (loginPassword.trim().equals(tempUser.getPassword().trim())) {
                            // <editor-fold defaultstate="collapsed" desc=" Users Options ">

                            // <editor-fold defaultstate="collapsed" desc=" String usersMenu=..  ">

                                String usersMenu =    "1. New purchase\n"
                                                    + "2. Change password\n"
                                                    + "\n"
                                                    + "0. Log out\n\n";

                            // </editor-fold>

                            String option = "";
                            System.out.println("\n********************************************");
                            System.out.println("Welcome, " + tempUser.getFirstName() + " " + tempUser.getLastName() + "!"
                                                + "\nCurrently, you have "+tempUser.getCredit()+"€ available.");
                            do{
                                System.out.println("\nPlease, choose an action: \n");
                                try{
                                    System.out.println(usersMenu);
                                    option = input.nextLine();
                                    if(option.length()==1)
                                        switch(Integer.parseInt(option)){
                                            case 1: { tempUser.purchase(); break; }
                                            case 2: { tempUser.changePassword(); break; }
                                            
                                            case 0: { break; }
                                            default: throw new Exception();
                                        }
                                    
                                    else
                                        throw new Exception();
                                }catch(Exception ex){
                                    System.out.println(ex.toString());
                                    System.out.println("***Invalid input!***\n\n"+" Try again: \n\n");
                                }
                                if(!option.equals("0"))
                                    System.out.println("_____________________________________________________\n\n");
                            }while(!option.equals("0"));
                            
                            return;
    // </editor-fold>
                        } else {
                            System.out.println("Wrong password! Try again or type cancel for exit:");
                        }
                    } while (!loginPassword.trim().equals(tempUser.getPassword().trim()));

    // </editor-fold>
                }   
                else{
                    System.out.println("Username not found!"+ "\nEnter another one or type in \"cancel\" for exit: \n");
            }
        }while(!loginUserName.equalsIgnoreCase("cancel"));
    }

    static public void registration(){

        Scanner input = new Scanner(System.in);
        boolean flag;
        
        // <editor-fold defaultstate="collapsed" desc=" FirstName input ">
        String firstName="";
        System.out.println("Please enter your first name: ");
        flag = false;
        do {
            try {
                firstName = input.nextLine();
                if(firstName.contains(" ")){
                    System.out.println("You have entered more than one word, so only the first one will be used.");
                    firstName = firstName.split(" ")[0];
                }
                if(firstName.equalsIgnoreCase("cancel")){
                    return;
                }
                if(firstName.length()<2){
                    throw new NameTooShortException();
                }
                for (int i = 0; i < firstName.length(); i++) {
                    if (!Character.isLetter(firstName.charAt(i))) {
                        throw new InvalidNameException();
                    }
                    flag=false;
                }
            } catch (InvalidNameException e) {
                flag = true;
                System.out.println("Name cannot contain any character that is not a letter. \n"
                                    + "Please enter your first name again using only letters(A-Z and a-z) "
                                    + "or type cancel for exit: ");
                
            } catch (NameTooShortException ex) {
                flag = true;
                System.out.println("Name must consist of at least two letters. \n"
                                            + "Please enter your first name again "
                                            + "or type in 'cancel' for exit: ");
            }
        
        }while (flag == true);
        
        firstName = firstName.toUpperCase().charAt(0)+firstName.substring(1);

// </editor-fold>    

        // <editor-fold defaultstate="collapsed" desc=" LastName input ">
        String lastName = "";
        System.out.println("Please enter your last name: ");
        do {
            flag = false;
            try {
                lastName = input.nextLine();
                if(lastName.contains(" ")){
                    System.out.println("You have entered more than one word, so only the first one will be used.");
                    lastName = lastName.split(" ")[0];
                }
                if(lastName.equalsIgnoreCase("cancel")){
                    return;
                }
                if(lastName.length()<2){
                    throw new NameTooShortException();
                }
                for (int i = 0; i < lastName.length(); i++) {
                    if (!Character.isLetter(lastName.charAt(i))) {
                        throw new InvalidNameException();
                    }
                }
            } catch (InvalidNameException e) {
                flag = true;
                System.out.println("Name cannot contain any character that is not a letter. \n"
                                            + "Please enter your last name again using only letters(A-Z and a-z) "
                                            + "or type in 'cancel' for exit: ");
            } catch (NameTooShortException ex) {
                flag = true;
                System.out.println("Name must consist of at least two letters. \n"
                                            + "Please enter your last name again "
                                            + "or type in 'cancel' for exit: ");
            }
        } while (flag == true);
        
        lastName = lastName.toUpperCase().charAt(0)+lastName.substring(1);

// </editor-fold>

        // <editor-fold defaultstate="collapsed" desc=" Email input ">
        String email="";
        System.out.println("Please enter your email: ");
        do {
            flag = false;
            try {
                email = input.nextLine();
                if(email.contains(" ")){
                    System.out.println("You have entered more than one word, so only the first one will be used.");
                    email = email.split(" ")[0];
                }
                if (email.equalsIgnoreCase("cancel")) {
                    return;
                }
                if (email.split("@").length == 2) {
                    if (!email.split("@")[1].contains(".")) {
                        throw new NotADotException();
                    }
                } else {
                    throw new NotOneAtSymbolException();
                }
                if(User.checkEmail(email)){
                    throw new EmailAlreadyInUseException();
                }
            } catch (NotOneAtSymbolException ex) {
                flag = true;
                System.out.println("Email must consist of exactly one @ character.\n"
                        + "Please enter email again or type in 'cancel' for exit: ");
            } catch (NotADotException ex) {
                flag = true;
                System.out.println("Email must contain at least one dot(.) atfer @ character\n"
                        + "Please enter email again or type in 'cancel' for exit: ");
            } catch (EmailAlreadyInUseException ex) {
                flag = true;
                System.out.println("Email you have entered is alreday in use.\n"
                        + "Please enter new email address or type in 'cancel' for exit: ");
            }
        } while (flag == true);

// </editor-fold>

        // <editor-fold defaultstate="collapsed" desc=" YearOfBrth input ">
        String yearOfBirth;
        System.out.println("Please enter a year you were born in: ");
        
        do {
            flag = false;
            try {
                yearOfBirth = input.nextLine();
                if(yearOfBirth.contains(" ")){
                    System.out.println("You have entered more than one word, so only the first one will be used as year.");
                    yearOfBirth = yearOfBirth.split(" ")[0];
                }
                if (yearOfBirth.equalsIgnoreCase("cancel")) {
                    return;
                }
                int currentYear = Integer.parseInt(Year.now().toString());
                if (currentYear - Integer.parseInt(yearOfBirth) < 10 || currentYear - Integer.parseInt(yearOfBirth) > 120) {
                    throw new YearsOutOfBoundsException();
                }
            } catch (YearsOutOfBoundsException e) {
                System.out.println("Sorry, but you must be over 10yrs in order to use our application... \n\n"
                        + "or younger than 120.. Going back to Main menu.");
                return;
            }
        } while (flag == true);

// </editor-fold>

        // <editor-fold defaultstate="collapsed" desc=" Username input ">
        
        
        String userName="";
        System.out.println("Almost done, enter your desired username: ");
        flag = false;
        do {
            try {
                userName = input.nextLine();
                if(userName.contains(" ")){
                    System.out.println("You have entered more than one word, so only the first one will be used.");
                    userName = userName.split(" ")[0];
                }
                if(userName.equalsIgnoreCase("admin")){
                    throw new AdminRegForbiddenException();
                }
                if(userName.equalsIgnoreCase("cancel")){
                    return;
                }
                if(userName.length()<2){
                    throw new NameTooShortException();
                }
                if(User.checkUserName(userName)!=null){
                    throw new UsernameAlreadyInUseException();
                }
            } catch (NameTooShortException ex) {
                flag = true;
                System.out.println("Username must consist of at least two letters. \n"
                                            + "Please enter your first name again "
                                            + "or type in 'cancel' for exit: ");
            } catch (UsernameAlreadyInUseException ex) {
                flag = true;
                System.out.println("Username you have entered is already in use.\n"
                        + "Please enter new one or type in 'cancel' for exit: ");
            } catch (AdminRegForbiddenException ex) {
                flag = true;
                System.out.println("Username cannot be admin.\n"
                        + "Please enter new one or type in 'cancel' for exit: ");
            }
        
        }while (flag == true);
        
        
        // </editor-fold> 
        
        // <editor-fold defaultstate="collapsed" desc=" Password input ">
        
        String password="";
        System.out.println("And only thing left is to enter your password(more than 8 characters): ");
        do {
            flag = false;
            try {
                password = input.next();
                if (password.equalsIgnoreCase("cancel")) {
                    return;
                }
                if(password.length()<8){
                    throw new PasswordTooShortException();
                }
            }catch (PasswordTooShortException ex) {
                flag = true;
                System.out.println("Password must have at least 8 characters.\n"
                        + "Please enter new password or type in 'cancel' for exit: ");
            }
            
        } while (flag == true);
        
// </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc=" Appending new user to a file ">
        User.users.add(new User(firstName, lastName, email, Integer.parseInt(yearOfBirth), userName, password));
        User.exportUsers();
        User.importUsers();
        
        System.out.println("Registration complete!");
        System.out.println("\nPlease, choose an action: ");
        // </editor-fold>
    }       
    
}
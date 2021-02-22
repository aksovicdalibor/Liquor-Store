/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Users;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dalib
 */
abstract public class AbstractUser {
    protected String userName;
    protected String password;

    public AbstractUser(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    abstract public boolean changePassword();
    
    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }
    
}

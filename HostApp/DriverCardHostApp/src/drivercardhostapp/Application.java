/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drivercardhostapp;

import drivercardhostapp.commons.utils.ConvertData;
import form.EnterPinGUI;
import java.math.BigInteger;
import java.util.Arrays;

/**
 *
 * @author Admin
 */
public class Application {
     private static EnterPinGUI enterPINGUI;
     private static DriverCardHostApp host;
    public static void main(String[] args) {
         /* Create and display the form */
            host = new DriverCardHostApp();  
            enterPINGUI = new EnterPinGUI(host);
            
    
            if(host.establishConnectionToSimulator()){
                enterPINGUI.setLocationRelativeTo(null);
                enterPINGUI.setVisible(true);
            }else{
                System.out.println("Không thể kết nối");
            }
      
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drivercardhostapp;

import drivercardhostapp.form.EnterPinGUI;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.smartcardio.CardException;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class Main {

    public static void main(String[] args) {
        try {

            if (HostAppHelper.getInstance().selectAuthenApplet()) {
                System.out.println();
                System.out.println("||========================||");
                System.out.println("||   KẾT NỐI THÀNH CÔNG   ||");
                System.out.println("||========================||");
                
                EnterPinGUI enterPINGUI = new EnterPinGUI();
                enterPINGUI.setLocationRelativeTo(null);
                enterPINGUI.setVisible(true);
            } else {
                System.out.println("Không thể kết nối !");
            }
        } catch (CardException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Lỗi kết nối");
        }
    }

}

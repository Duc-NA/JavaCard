/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drivercardhostapp;

import drivercardhostapp.commons.constants.APPLET_CONSTANTS;
import drivercardhostapp.commons.constants.ISO7816;
import java.util.List;
import javax.smartcardio.ATR;
import javax.smartcardio.Card;
import javax.smartcardio.CardChannel;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;
import javax.smartcardio.TerminalFactory;
import javax.swing.JOptionPane;
import javax.smartcardio.CardException;

/**
 *
 * @author Admin
 */
public class DriverCardHostApp {
    private Card card;
    private TerminalFactory factory;
    private CardChannel channel;
    private CardTerminal terminal;
    private List<CardTerminal> terminals;
    private ResponseAPDU response;

    public boolean establishConnectionToSimulator() {
        try{
            factory = TerminalFactory.getDefault();
            terminals = factory.terminals().list();
            terminal = terminals.get(0);
            card = terminal.connect("T=0");
            
            System.out.println("Card: " + card);
            // get the ATR
            ATR atr = card.getATR();
            byte[] baAtr = atr.getBytes();

            System.out.print("ATR = ");
            for(int i = 0; i < baAtr.length; i++ ){
                System.out.printf("%02X ",baAtr[i]);
            }

       
            channel = card.getBasicChannel();
            
            if(channel == null){
                return false;
            }
         
            response = channel.transmit(new CommandAPDU(
                    0x00, (byte)0xA4, 
                    0x04, 0x00, 
                    APPLET_CONSTANTS.AID_AUTH_APPLET)
            );
          
            String check = Integer.toHexString(response.getSW());
            
            if(check.equals(ISO7816.SW_NO_ERROR)){
                byte[] baCardUid = response.getData();

                System.out.print("Card UID = 0x");
                for(int i = 0; i < baCardUid.length; i++ ){
                    System.out.printf("%02X ", baCardUid [i]);
                }
                return true;
            }else if(check.equals(ISO7816.SW_DISABLED)){
                JOptionPane.showMessageDialog(null, "Thẻ bị vô hiệu hóa");
                return true;
            }
            return false;
        }catch(Exception e){
            System.out.println(e.toString());
        }
        return false;

    }


    public boolean closeConnection() {
        try {
            card.disconnect(false);
            return true;
        } catch (Exception e) {
            System.out.println("Lỗi: "+e.toString());
        }
        return false;
    }
    
    public ResponseAPDU sendAPDU(
            int cla, int ins, int p1, int p2, byte[] data 
    ) throws CardException{
        if(channel== null){
            throw new CardException(ISO7816.SW_UNKNOWN);
        }
        return channel.transmit(new CommandAPDU(
        cla, ins, p1, p2, data));
    }
}

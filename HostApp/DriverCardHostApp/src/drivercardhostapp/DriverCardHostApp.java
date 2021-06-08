/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drivercardhostapp;

import drivercardhostapp.commons.ISO7816;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
import javax.smartcardio.ATR;
import javax.smartcardio.Card;
import javax.smartcardio.CardChannel;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;
import javax.smartcardio.TerminalFactory;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class DriverCardHostApp {

    private static final byte[] AID_APPLET = {
        (byte)0x61, (byte)0x75, (byte)0x74, (byte)0x68, (byte)0x65, 
        (byte)0x6e, (byte)0x2f, (byte)0x70, (byte)0x69, (byte)0x6e
    };
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

            System.out.println("ATR = 0x");
            for(int i = 0; i < baAtr.length; i++ ){
                System.out.printf("%02X ",baAtr[i]);
            }

       
            channel = card.getBasicChannel();
            if(channel == null){
                return false;
            }
         
            response = channel.transmit(new CommandAPDU(0x00, (byte)0xA4, 0x04, 0x00, AID_APPLET));
            String check = Integer.toHexString(response.getSW());
            if(check.equals(ISO7816.SW_NO_ERROR)){
                byte[] baCardUid = response.getData();

                System.out.print("Card UID = 0x");
                for(int i = 0; i < baCardUid.length; i++ ){
                    System.out.printf("%02X ", baCardUid [i]);
                }
                return true;
            }
            else if(check.equals(ISO7816.SW_DISABLED)){
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
}

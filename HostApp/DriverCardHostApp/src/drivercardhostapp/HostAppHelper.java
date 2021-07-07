/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drivercardhostapp;

import drivercardhostapp.commons.constants.APPLET_CONSTANTS;
import drivercardhostapp.commons.constants.AUTH_CONSTANTS;
import drivercardhostapp.commons.constants.INFO_CONSTANTS;
import drivercardhostapp.commons.constants.ISO7816;
import drivercardhostapp.commons.utils.ConvertData;
import drivercardhostapp.model.Person;
import java.awt.HeadlessException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.smartcardio.ATR;
import javax.smartcardio.Card;
import javax.smartcardio.CardChannel;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;
import javax.smartcardio.TerminalFactory;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class HostAppHelper {

    private Card card;
    private TerminalFactory factory;
    public CardChannel channel;
    private CardTerminal terminal;
    private List<CardTerminal> terminals;

    private static HostAppHelper instance;

    private HostAppHelper() {
    }

    public static HostAppHelper getInstance() {
        if (instance == null) {
            instance = new HostAppHelper();
        }
        return instance;
    }

    public boolean connectReader() throws CardException {
        System.out.println("||===================||");
        System.out.println("||   CONNECTING...   ||");
        System.out.println("||===================||");
        if (channel == null) {

            try {
                factory = TerminalFactory.getDefault();
                terminals = factory.terminals().list();
                terminal = terminals.get(0);
                card = terminal.connect("T=0");

                System.out.println("Card: " + card);
                // get the ATR
                ATR atr = card.getATR();
                byte[] baAtr = atr.getBytes();

                System.out.print("ATR = ");
                for (int i = 0; i < baAtr.length; i++) {
                    System.out.printf("%02X ", baAtr[i]);
                }
                channel = card.getBasicChannel();

                return channel != null;
            } catch (CardException | HeadlessException e) {
                System.out.println("Lỗi kết nối reader");
                throw e;
            }
        }
        return false;
    }

    public int getTotalFailure() throws CardException {
        try {
            if (selectInfoApplet()) {
                ResponseAPDU response = sendAPDU(
                        APPLET_CONSTANTS.CLA, INFO_CONSTANTS.INS_CHECKERROR, 1,
                        2, null);
                String check = Integer.toHexString(response.getSW());
                if (check.equals(ISO7816.SW_NO_ERROR)) {
                    byte[] data = response.getData();
                    return data[0];
                }
            }
        } catch (CardException ex) {
            Logger.getLogger(HostAppHelper.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("getTotalFailure: " + ex);
            throw ex;
        }

        return -1;
    }

    public boolean incrementFailure() throws CardException {
        try {
            if (selectInfoApplet()) {
                ResponseAPDU response = sendAPDU(
                        APPLET_CONSTANTS.CLA, INFO_CONSTANTS.INS_ERROR, 1,
                        2, null);
                String check = Integer.toHexString(response.getSW());
                return check.equals(ISO7816.SW_NO_ERROR);
            }
        } catch (CardException ex) {
            Logger.getLogger(HostAppHelper.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("incrementFailure: " + ex);
            throw ex;
        }
        return false;
    }

    public boolean unblockInfo() throws CardException {
        try {
            if (selectInfoApplet()) {
                ResponseAPDU response = sendAPDU(
                        APPLET_CONSTANTS.CLA, INFO_CONSTANTS.INS_UNBLOCK, 1,
                        2, null);
                String check = Integer.toHexString(response.getSW());
                return check.equals(ISO7816.SW_NO_ERROR);
            }
        } catch (CardException ex) {
            Logger.getLogger(HostAppHelper.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("unblockInfo: " + ex);
            throw ex;
        }

        return false;
    }

    public boolean blockInfo() throws CardException {
        try {
            if (selectInfoApplet()) {
                ResponseAPDU response = sendAPDU(
                        APPLET_CONSTANTS.CLA, INFO_CONSTANTS.INS_BLOCK, 1,
                        2, null);
                String check = Integer.toHexString(response.getSW());
                return check.equals(ISO7816.SW_NO_ERROR);
            }
        } catch (CardException ex) {
            Logger.getLogger(HostAppHelper.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("blockInfo: " + ex);
            throw ex;
        }

        return false;
    }

    public int isBlockInfo() throws CardException {
        try {
            if (selectInfoApplet()) {
                ResponseAPDU response = sendAPDU(
                        APPLET_CONSTANTS.CLA, INFO_CONSTANTS.INS_CHECKBLOCKE, 1,
                        2, null);
                String check = Integer.toHexString(response.getSW());
                if (check.equals(ISO7816.SW_NO_ERROR)) {
                    byte[] data = response.getData();
                    return data[0];
                }
            }
        } catch (CardException ex) {
            Logger.getLogger(HostAppHelper.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("isBlockInfo: " + ex);
            throw ex;
        }

        return -1;
    }

    public Person getInfomation() throws CardException {
        try {
            if (selectInfoApplet()) {
                ResponseAPDU response = sendAPDU(
                        APPLET_CONSTANTS.CLA, INFO_CONSTANTS.INS_THONGTIN, 1,
                        2, null);
                String check = Integer.toHexString(response.getSW());
                if (check.equals(ISO7816.SW_NO_ERROR)) {
                    byte[] data = response.getData();

                    Person person = new Person(data);
                    person.setTotalFailure(getTotalFailure());
                    person.setIsBlocked(isBlockInfo());
                    return person;
                }
            }
        } catch (CardException ex) {
            Logger.getLogger(HostAppHelper.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("LỖI GET INFO: " + ex);
            throw ex;
        }

        return null;
    }

    // type = jpg
    public boolean uploadImage(File file, String type) {
        try {
            System.out.println();
            System.out.println("||===================||");
            System.out.println("||   UPLOAD IMAGE... ||");
            System.out.println("||===================||");

            if (selectInfoApplet()) {

                try {
                    BufferedImage bImage = ImageIO.read(file);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    ImageIO.write(bImage, type, bos);

                    byte[] napanh = bos.toByteArray();
                    System.out.println(napanh.length);

                    int soLan = napanh.length / 249;
                    String strsend = soLan + "S" + napanh.length % 249;
                    System.out.println(strsend);

                    byte[] send = strsend.getBytes();
                    ResponseAPDU response = channel.transmit(
                            new CommandAPDU(APPLET_CONSTANTS.CLA, INFO_CONSTANTS.INS_SETCOUNT, 0x00, 0x01, send));
                    String check = Integer.toHexString(response.getSW());
                    System.out.println("check: " + check);

                    if (check.equals(ISO7816.SW_NO_ERROR)) {
                        for (int i = 0; i <= soLan; i++) {
                            byte p1 = (byte) i;
                            int start = 0, end = 0;
                            start = i * 249;
                            if (i != soLan) {
                                end = (i + 1) * 249;
                            } else {
                                end = napanh.length;
                            }
                            byte[] slice = Arrays.copyOfRange(napanh, start, end);
                            System.out.println(slice.length);
                            response = channel.transmit(
                                    new CommandAPDU(APPLET_CONSTANTS.CLA, INFO_CONSTANTS.INS_NAPANH, p1, 0x01, slice));
                            String check1 = Integer.toHexString(response.getSW());
                            if (!check1.equals(ISO7816.SW_NO_ERROR)) {
                                return false;
                            }
                        }
                        return true;
                    }

                } catch (IOException | CardException e) {
                    System.out.println("Ouch: " + e.toString());
                }
            }
        } catch (CardException ex) {
            Logger.getLogger(HostAppHelper.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public BufferedImage downloadImage() {
        System.out.println();
        System.out.println("||===================||");
        System.out.println("|| DOWNLOAD IMAGE... ||");
        System.out.println("||===================||");
        try {
            if (selectInfoApplet()) {
                int size = 0;
                try {
                    ResponseAPDU answer = channel.transmit(
                            new CommandAPDU(APPLET_CONSTANTS.CLA, INFO_CONSTANTS.INS_COUNTANH, 0x01, 0x01));
                    String check = Integer.toHexString(answer.getSW());
                    if (check.equals(ISO7816.SW_NO_ERROR)) {
                        byte[] sizeanh = answer.getData();

                        if (ConvertData.isByteArrayAllZero(sizeanh)) {
                            return null;
                        }

                        byte[] arranh = new byte[10000];
                        String strsizeanh = new String(sizeanh);
                        String[] output1 = strsizeanh.split("S");

                        int lan = Integer.parseInt(output1[0].replaceAll("\\D", ""));
                        int du = Integer.parseInt(output1[1].replaceAll("\\D", ""));
                        size = lan * 249 + du;

                        int count = size / 249;

                        for (int j = 0; j <= count; j++) {
                            answer = channel.transmit(new CommandAPDU(0xB0, 0x02, (byte) j, 0x01));
                            String check1 = Integer.toHexString(answer.getSW());
                            if (check1.equals(ISO7816.SW_NO_ERROR)) {
                                byte[] result = answer.getData();
                                int leng = 249;
                                if (j == count) {
                                    leng = size % 249;
                                }
                                System.arraycopy(result, 0, arranh, j * 249, leng);
                            }
                        }

                        ByteArrayInputStream bais = new ByteArrayInputStream(arranh);
                        try {
                            BufferedImage image = ImageIO.read(bais);
                            return image;
                        } catch (IOException ex) {
                            Logger.getLogger(HostAppHelper.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }

                } catch (CardException | NumberFormatException e) {
                    System.out.println("Ouch: " + e.toString());
                }
            }
        } catch (CardException ex) {
            Logger.getLogger(HostAppHelper.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public boolean sendInfomation(Person person) throws CardException {
        if (selectInfoApplet()) {
            if (sendInfoFieldLen(person)) {
                ResponseAPDU response = sendAPDU(
                        APPLET_CONSTANTS.CLA, INFO_CONSTANTS.INS_INSERT, 1,
                        2, person.toBytes());
                String check = Integer.toHexString(response.getSW());
                return check.equals(ISO7816.SW_NO_ERROR);
            }
            System.out.println("Lỗi khởi tạo thông tin");
        }
        return false;
    }

    private boolean sendInfoFieldLen(Person person) throws CardException {
        ResponseAPDU response = sendAPDU(
                APPLET_CONSTANTS.CLA, INFO_CONSTANTS.INS_COUNTINSERT, 1,
                2, person.toBytes());
        String check = Integer.toHexString(response.getSW());
        return check.equals(ISO7816.SW_NO_ERROR);
    }

    public boolean selectInfoApplet() throws CardException {
        try {
            System.out.println();
            System.out.println("||===================||");
            System.out.println("||   SELECT INFO...  ||");
            System.out.println("||===================||");
            ResponseAPDU response = selectApplet(APPLET_CONSTANTS.AID_INFO_APPLET);
            String check = Integer.toHexString(response.getSW());

            if (check.equals(ISO7816.SW_NO_ERROR)) {
                byte[] baCardUid = response.getData();

                System.out.print("Card UID = 0x");
                for (int i = 0; i < baCardUid.length; i++) {
                    System.out.printf("%02X ", baCardUid[i]);
                }
                System.out.println("");
                return true;

            }
        } catch (CardException ex) {
            Logger.getLogger(HostAppHelper.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Lỗi select info applet");
            throw ex;
        }
        System.out.println("Lỗi khởi tạo length");
        return false;
    }

    public boolean selectAuthenApplet() throws CardException {
        if (channel == null) {
            connectReader();
        }

        try {
            System.out.println();
            System.out.println("||===================||");
            System.out.println("||   SELECT AUTH...  ||");
            System.out.println("||===================||");
            ResponseAPDU response = selectApplet(APPLET_CONSTANTS.AID_AUTH_APPLET);
            String check = Integer.toHexString(response.getSW());

            switch (check) {
                case ISO7816.SW_NO_ERROR:
                    byte[] baCardUid = response.getData();

                    System.out.print("Card UID = 0x");
                    for (int i = 0; i < baCardUid.length; i++) {
                        System.out.printf("%02X ", baCardUid[i]);
                    }
                    return true;
                case ISO7816.SW_DISABLED:
                    JOptionPane.showMessageDialog(null, "Thẻ đã bị vô hiệu hóa");
                    return true;
            }
        } catch (CardException ex) {
            Logger.getLogger(HostAppHelper.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Lỗi select auth applet");
            throw ex;
        }

        return false;
    }

    // select
    private ResponseAPDU selectApplet(byte[] aid) throws CardException {
        ResponseAPDU response = channel.transmit(new CommandAPDU(
                0x00, (byte) 0xA4,
                0x04, 0x00,
                aid)
        );
        return response;
    }

    public String enterPin(char[] pinText) throws CardException {
        if (selectAuthenApplet()) {
            ResponseAPDU response = sendAPDU(
                    APPLET_CONSTANTS.CLA, APPLET_CONSTANTS.VERIFY, AUTH_CONSTANTS.AUTH,
                    (byte) 2, ConvertData.charArrayToByteArray(pinText));
            return Integer.toHexString(response.getSW());
        }
        return null;

    }

    public String updatePin(char[] oldPin, char[] newPin) throws CardException {
        if (selectAuthenApplet()) {
            char[] data = new char[2 * oldPin.length];
            System.arraycopy(oldPin, 0, data, 0, oldPin.length);
            System.arraycopy(newPin, 0, data, oldPin.length, newPin.length);
            ResponseAPDU response = sendAPDU(
                    APPLET_CONSTANTS.CLA, APPLET_CONSTANTS.UPDATE, AUTH_CONSTANTS.AUTH,
                    (byte) 2, ConvertData.charArrayToByteArray(data));
            return Integer.toHexString(response.getSW());
        }
        return null;
    }

    public boolean unlockCard() throws CardException {
        if (selectAuthenApplet()) {
            ResponseAPDU response = sendAPDU(
                    APPLET_CONSTANTS.CLA, APPLET_CONSTANTS.UNBLOCK, AUTH_CONSTANTS.AUTH,
                    (byte) 2, null);
            String check = Integer.toHexString(response.getSW());
            return check.equals(ISO7816.SW_NO_ERROR);
        }
        return false;
    }

    public boolean closeConnection() {
        try {
            card.disconnect(false);
            channel = null;
            return true;
        } catch (Exception e) {
            System.out.println("Lỗi: " + e.toString());
        }
        return false;
    }

    public ResponseAPDU sendAPDU(
            int cla, int ins, int p1, int p2, byte[] data
    ) throws CardException {
        if (channel == null) {
            throw new CardException(ISO7816.SW_UNKNOWN);
        }
        return channel.transmit(new CommandAPDU(
                cla, ins, p1, p2, data));
    }
}

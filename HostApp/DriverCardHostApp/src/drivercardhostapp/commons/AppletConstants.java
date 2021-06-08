/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drivercardhostapp.commons;

/**
 *
 * @author Admin
 */
public class AppletConstants {
    public final static byte Wallet_CLA =(byte)0xB0;
    public final static byte VERIFY = (byte) 0x20;
    public final static byte CREDIT = (byte) 0x30;
    public final static byte DEBIT = (byte) 0x40;
    public final static byte GET_BALANCE = (byte) 0x50;
    public final static byte UNBLOCK = (byte) 0x60;
}

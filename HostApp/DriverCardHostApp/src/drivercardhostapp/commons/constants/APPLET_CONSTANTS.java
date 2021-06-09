/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drivercardhostapp.commons.constants;

/**
 *
 * @author Admin
 */
public interface  APPLET_CONSTANTS {
    public static final byte[] AID_AUTH_APPLET = {
        (byte)0x61, (byte)0x75, (byte)0x74, (byte)0x68, (byte)0x65, 
        (byte)0x6E, (byte)0x2F, (byte)0x70, (byte)0x69, (byte)0x6E
    };
    
    public final static byte CLA =(byte)0xB0;

    public final static byte CREATE = (byte) 0x10;
    public final static byte UPDATE = (byte) 0x11;
    public final static byte READ = (byte) 0x13;
    public final static byte DELETE = (byte) 0x14;
    public final static byte UNBLOCK = (byte) 0x15;
    public final static byte VERIFY = (byte) 0x16;
    public final static byte CLEAR = (byte) 0x17;
}

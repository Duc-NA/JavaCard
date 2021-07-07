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
public interface ISO7816 {
    public final static String SW_NO_ERROR = "9000";
    public final static String SW_DISABLED = "6400";
    public final static String SW_WRONG_P1P2 = "0x6B00";
    public final static String SW_CLA_NOT_SUPPORTED = "0x6E00";
    public final static String SW_INS_NOT_SUPPORTED = "0x6D00";
    public final static String SW_UNKNOWN = "0x6F00";
}
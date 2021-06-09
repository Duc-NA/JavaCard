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
public interface AUTH_CONSTANTS {
    final static byte AUTH =(byte)0x63;

    // maximum number of incorrect tries before the
    // PIN is blocked
    final static int PIN_TRY_LIMIT = 5;

    // maximum size PIN
    final static int MAX_PIN_SIZE = 6;

    final static String  SW_VERIFICATION_FAILED = "6312";
    final static String SW_PIN_VERIFICATION_REQUIRED = "6311";
    final static String SW_WRONG_PIN_LEN = "6313";
    final static String SW_OVER_TRY_TIMES = "6314";
}

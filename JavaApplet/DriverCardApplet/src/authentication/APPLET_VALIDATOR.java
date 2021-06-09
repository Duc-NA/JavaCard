package authentication;

import javacard.framework.*;

public abstract class APPLET_VALIDATOR {
	public static boolean isValidPINLenByAPDU(APDU apdu){
		// retrieve the PIN data for validation.
		byte byteRead = (byte)(apdu.setIncomingAndReceive());
		
		if(byteRead != AUTH_CONSTANTS.MAX_PIN_SIZE){
			return false;
		}
		
		return true;
	}
}

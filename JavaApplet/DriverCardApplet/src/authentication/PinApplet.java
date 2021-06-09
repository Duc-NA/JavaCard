package authentication;

import javacard.framework.*;

public class PinApplet extends Applet
{
	private static OwnerPIN pin;
	
	public static void install(byte[] bArray, short bOffset, byte bLength) 
	{
		new PinApplet(bArray, bOffset, bLength);
	}
	
	private PinApplet(byte[] bArray, short bOffset, byte bLength) {
		byte aIDLen = bArray[bOffset];
		
		if(aIDLen == 0){
			register();
		}else{
			register(bArray, (short) (bOffset + 1), aIDLen);
		}
		
		initPIN(AUTH_CONSTANTS.DEAULT_PIN_CODE);
    }
    
	
	public void process(APDU apdu)
	{
		if (selectingApplet()){return;}
		
		byte[] buffer = apdu.getBuffer();
		
		 // check SELECT APDU command
		if((buffer[ISO7816.OFFSET_CLA] == 0x00
			 && buffer[ISO7816.OFFSET_INS] == (byte)(0xA4)))
			return;
		
		if (buffer[ISO7816.OFFSET_CLA] != APPLET_CONSTANTS.CLA)
			ISOException.throwIt(ISO7816.SW_CLA_NOT_SUPPORTED);
		
		if(buffer[ISO7816.OFFSET_P1] != AUTH_CONSTANTS.AUTH)
			ISOException.throwIt(ISO7816.SW_WRONG_P1P2);
			
		switch (buffer[ISO7816.OFFSET_INS])
		{
		case APPLET_CONSTANTS.UPDATE:
			updatePIN(apdu);
			return;
		case APPLET_CONSTANTS.VERIFY:
			verify(apdu);
			return;
		case APPLET_CONSTANTS.UNBLOCK: 
			resetAndUnblock();
			return;
		default:
			ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
		}
	}
	
	private static void initPIN(byte[]pinArr){
		if((byte)pinArr.length != AUTH_CONSTANTS.MAX_PIN_SIZE){
			ISOException.throwIt(AUTH_CONSTANTS.SW_WRONG_PIN_LEN);
		}
		pin = new OwnerPIN(AUTH_CONSTANTS.PIN_TRY_LIMIT, AUTH_CONSTANTS.MAX_PIN_SIZE);
		pin.update(pinArr, (short) 0, (byte)pinArr.length);
	}
	
	private void updatePIN(APDU apdu){	
		checkValidatePIN();
		byte byteRead = (byte)(apdu.setIncomingAndReceive());
		if(byteRead != AUTH_CONSTANTS.MAX_PIN_SIZE)
			ISOException.throwIt(AUTH_CONSTANTS.SW_WRONG_PIN_LEN);
		
		byte[] buffer = apdu.getBuffer();
			
		byte[] tempPIN = new byte[byteRead];
		Util.arrayCopy(buffer, (short)ISO7816.OFFSET_CDATA, tempPIN, (short)0, (short)byteRead);
		pin.update(tempPIN, (short) 0, byteRead);
	}
	
	private void verify(APDU apdu) {
		byte[] buffer = apdu.getBuffer();
		
		byte byteRead = (byte)(apdu.setIncomingAndReceive());
		
		if(byteRead != AUTH_CONSTANTS.MAX_PIN_SIZE){
			ISOException.throwIt(AUTH_CONSTANTS.SW_WRONG_PIN_LEN);
		}
	 
		if ( pin.check(buffer, ISO7816.OFFSET_CDATA,byteRead) == false ){
			if(pin.getTriesRemaining() == 0){
				ISOException.throwIt(AUTH_CONSTANTS.SW_OVER_TRY_TIMES);
			}
			ISOException.throwIt(AUTH_CONSTANTS.SW_VERIFICATION_FAILED);
		}
	} 
	
	private void checkValidatePIN(){
		if (!pin.isValidated()){
			if(pin.getTriesRemaining() == 0){
				ISOException.throwIt(AUTH_CONSTANTS.SW_OVER_TRY_TIMES);
			}
			ISOException.throwIt(AUTH_CONSTANTS.SW_PIN_VERIFICATION_REQUIRED);
		}
	}
	
	private void resetAndUnblock(){
		pin.resetAndUnblock();
	}
	
	private void sendLongData(APDU apdu, byte[] data){
		// gui du lieu
		if(data == null || data.length == (short)0){
			ISOException.throwIt(ISO7816.SW_CORRECT_LENGTH_00);
		}
		
		short dataLen = (short)data.length;
		
		short le = apdu.setOutgoing();
		apdu.setOutgoingLength(dataLen);
		apdu.sendBytesLong(data, (short)0, dataLen);
	}
}

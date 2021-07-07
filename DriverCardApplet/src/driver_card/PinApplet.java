package driver_card;

import javacard.security.*;
import javacardx.crypto.Cipher;
import javacard.framework.*;

public class PinApplet extends Applet implements INFO_CONSTANTS, PinInterface
{
    private byte[] arrayKeyAES;
    private MessageDigest mDig = MessageDigest.getInstance(MessageDigest.ALG_MD5, true);
	
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
		
		// hash pin by md5
		arrayKeyAES = JCSystem.makeTransientByteArray(APPLET_CONSTANTS.KEY_SIZE, JCSystem.CLEAR_ON_RESET);
		arrayKeyAES = hashMD5Message(AUTH_CONSTANTS.DEAULT_PIN_CODE);
    }
    
    public boolean select() {
		return true;
	}
   
	public void deselect() {
		pin.reset();
	}

	public Shareable getShareableInterfaceObject(AID clientAID, byte parameter) 
	{
		return this;
	}
    
    public byte[] getHashPin(){
		return arrayKeyAES;
	}
	
	public void process(APDU apdu)
	{
		if (selectingApplet()){return;}
		
		byte[] buf = apdu.getBuffer();
		
		 // check SELECT APDU command
		if((buf[ISO7816.OFFSET_CLA] == 0x00
			 && buf[ISO7816.OFFSET_INS] == (byte)(0xA4)))
			return;
		
		if (buf[ISO7816.OFFSET_CLA] != APPLET_CONSTANTS.CLA)
			ISOException.throwIt(ISO7816.SW_CLA_NOT_SUPPORTED);
		
		if(buf[ISO7816.OFFSET_P1] != AUTH_CONSTANTS.AUTH)
			ISOException.throwIt(ISO7816.SW_WRONG_P1P2);
			
		switch (buf[ISO7816.OFFSET_INS])
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
		byte byteRead = (byte)(apdu.setIncomingAndReceive());
		if(byteRead != (byte)(2*AUTH_CONSTANTS.MAX_PIN_SIZE))
			ISOException.throwIt(AUTH_CONSTANTS.SW_WRONG_PIN_LEN);
		
		
		byte[] buffer = apdu.getBuffer();
		// check code pin old 	
		if (!pin.check(buffer, ISO7816.OFFSET_CDATA, AUTH_CONSTANTS.MAX_PIN_SIZE)) {
			ISOException.throwIt(AUTH_CONSTANTS.SW_VERIFICATION_FAILED);
		}
			
		byte[] tempPIN = new byte[AUTH_CONSTANTS.MAX_PIN_SIZE];
		Util.arrayCopy(
					buffer, 
					(short) (ISO7816.OFFSET_CDATA + AUTH_CONSTANTS.MAX_PIN_SIZE),
					tempPIN, 
					(short)0, (short)AUTH_CONSTANTS.MAX_PIN_SIZE
		);
		pin.update(tempPIN, (short) 0, (byte)tempPIN.length);
		JCSystem.beginTransaction();
		arrayKeyAES = hashMD5Message(tempPIN);
		JCSystem.commitTransaction();
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
	
	private byte[] hashMD5Message(byte[] input) {
		byte[] output = new byte[APPLET_CONSTANTS.KEY_SIZE];
		
		mDig.reset();
		mDig.doFinal(input, (short)0, (short)input.length, output, (short) 0);
		
		return output;
	}
	
}

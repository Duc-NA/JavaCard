package AuthenticationPin;

import javacard.framework.*;

public class AuthenticationPin extends Applet
{
	final static byte Pin_CLA =(byte)0xB0;
    // check Pin code 
	private final static byte[] test = new byte[8];
	private final static byte[] TEST_CHECK = new byte[]{(byte)0x01,(byte)0x01,(byte)0x01,(byte)0x01};
    //size code pin
	private final static byte PIN_MIN_SIZE = (byte) 4;
	private final static byte PIN_MAX_SIZE = (byte) 16;
	// set pin default 	
	private final static byte MAX_NUM_PINS = (byte) 8;	
	private final static byte[] PIN_INIT_VALUE={(byte)'a',(byte)'n',(byte)'h',(byte)'d',(byte)'u',(byte)'c'};
	
	//INS-PIN
	private final static byte INS_CREATE_PIN = (byte) 0x40;
	private final static byte INS_VERIFY_PIN = (byte) 0x41;
	private final static byte INS_CHANGE_PIN = (byte) 0x42;
	private final static byte INS_UNBLOCK_PIN = (byte) 0x43;
	
	private final static byte INS_CREATE_INFORMATION = (byte)0x50;
	private final static byte INS_LOGOUT_ALL = (byte) 0x60;
	/* check status  setup */
	private boolean setupDone = false;
	// INS - status setup
	private final static byte INS_SETUP = (byte) 0x2B;
	private OwnerPIN[] pins, ublk_pins;
	// check status login
	private short logged_ids;
	
	
	/** error tham so truyen vao */
	private final static short SW_INVALID_PARAMETER = (short) 0x9C0F;
	/** return card block */
	private final static short SW_IDENTITY_BLOCKED = (short) 0x9C0C;
	/** check code pin error */
	private final static short SW_AUTH_FAILED = (short) 0x9C02;
	/** return code pin not unlock but using void unlock  */
	private final static short SW_OPERATION_NOT_ALLOWED = (short) 0x9C03;
	/** check error */
	private final static short SW_INTERNAL_ERROR = (short) 0x9CFF;
	/** return using card not setup */
	private final static short SW_SETUP_NOT_DONE = (short) 0x9C04;
	/** error P1*/
	private final static short SW_INCORRECT_P1 = (short) 0x9C10;
	/** error P2*/
	private final static short SW_INCORRECT_P2 = (short) 0x9C11;
	
	// void log out 
	public boolean select() {
		LogOutAll();
		return true;
	}
	public void deselect() {
		LogOutAll();
	}
	
	// register applet pin and unblock pin
	private AuthenticationPin(byte[] bArray, short bOffset, byte bLength){
		if (!CheckPINPolicy(PIN_INIT_VALUE, (short) 0, (byte) PIN_INIT_VALUE.length))
		    ISOException.throwIt(SW_INTERNAL_ERROR);

	    ublk_pins = new OwnerPIN[MAX_NUM_PINS];
		pins = new OwnerPIN[MAX_NUM_PINS];

		/* set default pin check max 3 again*/
		pins[0] = new OwnerPIN((byte) 3, (byte) PIN_INIT_VALUE.length);
		pins[0].update(PIN_INIT_VALUE, (short) 0, (byte) PIN_INIT_VALUE.length);
				
		register();
	}
	
	public static void install(byte[] bArray, short bOffset, byte bLength) 
	{
		AuthenticationPin wal = new AuthenticationPin(bArray, bOffset, bLength);
	}
	
	public void process(APDU apdu)
	{
		if (selectingApplet())
		{
			return;
		}

		byte[] buffer = apdu.getBuffer();
		if ((buffer[ISO7816.OFFSET_CLA] == 0) && (buffer[ISO7816.OFFSET_INS] == (byte) 0xA4))
			return;
		if (buffer[ISO7816.OFFSET_CLA] != Pin_CLA)
			ISOException.throwIt(ISO7816.SW_CLA_NOT_SUPPORTED);
		byte ins = buffer[ISO7816.OFFSET_INS];
		if (!setupDone && (ins != (byte) INS_SETUP))
			ISOException.throwIt(SW_SETUP_NOT_DONE);

		if (setupDone && (ins == (byte) INS_SETUP))
			ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
		
		switch (ins)
		{
		case INS_SETUP:
			setup(apdu, buffer);
			break;
		case INS_CREATE_PIN:
			CreatePIN(apdu, buffer);
			break;
		case INS_VERIFY_PIN:
			VerifyPIN(apdu, buffer);
			break;
		case INS_CHANGE_PIN:
			ChangePIN(apdu, buffer);
			break;
		case INS_UNBLOCK_PIN:
			UnblockPIN(apdu, buffer);
			break;
		case INS_LOGOUT_ALL:
			LogOutAll();
			break;
		case INS_CREATE_INFORMATION:
			SetupInformation(apdu,buffer);
			break;
		default:
			ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
		}
	}
	// set up first using card 
	private void setup(APDU apdu, byte[] buffer) {
		setupDone = true;
	}

	private void CreatePIN(APDU apdu, byte[] buffer) {			
		byte num_tries = buffer[ISO7816.OFFSET_P2];
		short avail = Util.makeShort((byte) 0x00, buffer[ISO7816.OFFSET_LC]); // 05
		if (apdu.setIncomingAndReceive() != avail)
			ISOException.throwIt(ISO7816.SW_WRONG_LENGTH);		
		if (avail < (short)5)
			ISOException.throwIt(SW_INVALID_PARAMETER);
		byte pin_size = buffer[ISO7816.OFFSET_CDATA]; // 04
		if (avail < (short) (1 + pin_size))
			ISOException.throwIt(SW_INVALID_PARAMETER);
		if (!CheckPINPolicy(buffer, (short) (ISO7816.OFFSET_CDATA + 1), pin_size))
			ISOException.throwIt(SW_INVALID_PARAMETER);
			
		pins[0] = new OwnerPIN(num_tries, PIN_MAX_SIZE);
		pins[0].update(buffer, (short) (ISO7816.OFFSET_CDATA + 1), pin_size);
		
		/*===CHECK PIN===*/
		if(pins[0].check(TEST_CHECK,(short)0,(byte)TEST_CHECK.length)){
			test[1] = (byte)0x02;
		}
		else{
			test[1] = (byte)0x03;
		}
		apdu.setOutgoing();
		apdu.setOutgoingLength((short)test.length);
		Util.arrayCopy(test,(short)0,buffer,(short)0,(short)test.length);
		apdu.sendBytes((short)0,(short)test.length);
	}
	
	private void VerifyPIN(APDU apdu, byte[] buffer) {
		byte pin_nb = (byte)0;
		if ((pin_nb < 0) || (pin_nb >= MAX_NUM_PINS))
			ISOException.throwIt(SW_INCORRECT_P1);
		OwnerPIN pin = pins[pin_nb];
		if (pin == null)
			ISOException.throwIt(SW_INCORRECT_P1);
		if (buffer[ISO7816.OFFSET_P2] != 0x00)
			ISOException.throwIt(SW_INCORRECT_P2);		
		short numBytes = Util.makeShort((byte) 0x00, buffer[ISO7816.OFFSET_LC]);
		
		if (numBytes != apdu.setIncomingAndReceive())
			ISOException.throwIt(ISO7816.SW_WRONG_LENGTH);
		if (!CheckPINPolicy(buffer, ISO7816.OFFSET_CDATA, (byte) numBytes))
			ISOException.throwIt(SW_INVALID_PARAMETER);
		// check so lan nhap sai
		if (pin.getTriesRemaining() == (byte) 0x00)
			ISOException.throwIt(SW_IDENTITY_BLOCKED);
		if (!pin.check(buffer, (short) ISO7816.OFFSET_CDATA, (byte) numBytes)) {
			LogoutIdentity(pin_nb);
			ISOException.throwIt(SW_AUTH_FAILED);
		}
		logged_ids |= (short) (0x0001 << pin_nb);
	}
	
	private void ChangePIN(APDU apdu, byte[] buffer) {		
		byte pin_nb = (byte)0;
		// check number pin
		if ((pin_nb < 0) || (pin_nb >= MAX_NUM_PINS))
			ISOException.throwIt(SW_INCORRECT_P1);
		OwnerPIN pin = pins[pin_nb];
		// check code pin null
		if (pin == null)
			ISOException.throwIt(SW_INCORRECT_P1);
		if (buffer[ISO7816.OFFSET_P2] != (byte) 0x00)
			ISOException.throwIt(SW_INCORRECT_P2);
		short avail = Util.makeShort((byte) 0x00, buffer[ISO7816.OFFSET_LC]);
		// check status in coming
		if (apdu.setIncomingAndReceive() != avail)
			ISOException.throwIt(ISO7816.SW_WRONG_LENGTH);		
		// check size data min
		if (avail < (short)4)
			ISOException.throwIt(SW_INVALID_PARAMETER);
		byte pin_size = buffer[ISO7816.OFFSET_CDATA];
		// check data total  < size data pin 
		if (avail < (short) (1 + pin_size))
			ISOException.throwIt(SW_INVALID_PARAMETER);
		// check pin size 4 < pin size < 16 
		if (!CheckPINPolicy(buffer, (short) (ISO7816.OFFSET_CDATA + 1), pin_size))
			ISOException.throwIt(SW_INVALID_PARAMETER);
		byte new_pin_size = buffer[(short) (ISO7816.OFFSET_CDATA + 1 + pin_size)];
		// check data total < data pin size + data new pin size
		if (avail < (short) (1 + pin_size + new_pin_size))
			ISOException.throwIt(SW_INVALID_PARAMETER);
		// check pin size 4 < new pin size < 16 	
		if (!CheckPINPolicy(buffer, (short) (ISO7816.OFFSET_CDATA + 1 + pin_size + 1), new_pin_size))
			ISOException.throwIt(SW_INVALID_PARAMETER);
		// check so lan thu. Default = 3	
		if (pin.getTriesRemaining() == (byte) 0x00)
			ISOException.throwIt(SW_IDENTITY_BLOCKED);
		// check code pin old 	
		if (!pin.check(buffer, (short) (ISO7816.OFFSET_CDATA + 1), pin_size)) {
			LogoutIdentity(pin_nb);
			ISOException.throwIt(SW_AUTH_FAILED);
		}
		// update pin
		pin.update(buffer, (short) (ISO7816.OFFSET_CDATA + 1 + pin_size + 1), new_pin_size);
		
		/*===CHECK Test===*/
		if(pins[0].check(TEST_CHECK,(short)0,(byte)TEST_CHECK.length)){
			test[1] = (byte)0x02;
		}
		else{
			test[1] = (byte)0x03;
		}
		apdu.setOutgoing();
		apdu.setOutgoingLength((short)test.length);
		Util.arrayCopy(test,(short)0,buffer,(short)0,(short)test.length);
		apdu.sendBytes((short)0,(short)test.length);
		/*===END CHECK Test PIN===*/
		logged_ids &= (short) ((short) 0xFFFF ^ (0x01 << pin_nb));
	}
	private void UnblockPIN(APDU apdu, byte[] buffer) {
		//byte pin_nb = buffer[ISO7816.OFFSET_P1];
		byte pin_nb = (byte)0;
		if ((pin_nb < 0) || (pin_nb >= MAX_NUM_PINS))
			ISOException.throwIt(SW_INCORRECT_P1);
		
		OwnerPIN pin = pins[pin_nb];
		
		if (pin == null)
			ISOException.throwIt(SW_INCORRECT_P1);		
		if (pin.getTriesRemaining() != 0)
			ISOException.throwIt(SW_OPERATION_NOT_ALLOWED);
		if (buffer[ISO7816.OFFSET_P2] != 0x00)
			ISOException.throwIt(SW_INCORRECT_P2);
		short numBytes = Util.makeShort((byte) 0x00, buffer[ISO7816.OFFSET_LC]);		
		if (numBytes != apdu.setIncomingAndReceive())
			ISOException.throwIt(ISO7816.SW_WRONG_LENGTH);
		if (!CheckPINPolicy(buffer, ISO7816.OFFSET_CDATA, (byte) numBytes))
			ISOException.throwIt(SW_INVALID_PARAMETER);		
		pin.resetAndUnblock();
	}
	private void SetupInformation(APDU apdu, byte[] buffer){
		
	}
	/*CHECK PIN POLICY*/
	private boolean CheckPINPolicy(byte[] pin_buffer, short pin_offset, byte pin_size) {
		if ((pin_size < PIN_MIN_SIZE) || (pin_size > PIN_MAX_SIZE))
			return false;
		return true;
	}
	private void LogOutAll() {
		logged_ids = (short) 0x0000; 
		byte i;
		for (i = (byte) 0; i < MAX_NUM_PINS; i++)
			if (pins[i] != null)
				pins[i].reset();
	}	
	private void LogoutIdentity(byte id_nb) {
		logged_ids &= (short) ~(0x0001 << id_nb);
	}
}

package authentication;

import javacard.framework.*;

public class PinApplet extends Applet implements INFO_CONSTANTS
{
	private static OwnerPIN pin;
	
	// variable
	private static byte[] arrayhoten, arrayngaysinh, arrayCMND,arrayGPLX, 
							arrayvehicle, image, size;
	final static byte phi = (byte) 0x03;
	byte balance = (byte) 0x0A;
	short countht, countns, countcmnd, countgplx, countvehicle;
	
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
		
		image = new byte[10000];
		size = new byte[7];
		
		initPIN(AUTH_CONSTANTS.DEAULT_PIN_CODE);
    }
    
    public boolean select() {
		return true;
	}
   
	public void deselect() {
		pin.reset();
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
		
		// if(buffer[ISO7816.OFFSET_P1] != AUTH_CONSTANTS.AUTH)
			// ISOException.throwIt(ISO7816.SW_WRONG_P1P2);
			
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
		/// ==================
		/// 	INFOMATION
		/// ==================
		case INS_COUNTINSERT:
			// Khoi tao do dai cac truong
			// vidu: [lenHoTen][lenNS][lenCMND][lenGPLX][lenVehicle]
			// B0 
			// 012101210121012101
			apdu.setIncomingAndReceive();
			short dataLen1 = (short)(buf[ISO7816.OFFSET_LC]&0xff);
			short flag1 = (short)1;
			JCSystem.beginTransaction();
			countht = 0;
			countns = 0;
			countcmnd = 0;
			countgplx = 0;
			countvehicle = 0;
			for(short i = (short)(ISO7816.OFFSET_CDATA)
				;i<(short)(ISO7816.OFFSET_CDATA +dataLen1)
				;i++ ){
				
				// phan tach giua cac truong
				if(buf[i]==(byte)0x21){
					flag1+=(short)1;
					continue;
				}
				
				// Thu tu nhap nao thi cong them 1 tuong ung voi bien day
				if(flag1 ==(short)1){
					countht++;
				}
				else if(flag1 ==(short)2){
						countns++; 
				}
				else if(flag1 ==(short)3){
					countcmnd++;
				}
				else if(flag1 ==(short)4){
					countgplx++;
				}else if(flag1 ==(short)5){
					countvehicle++;
				}
			}
			// countht++;
			// countns++;
			// countcmnd++;
			// countgplx++;
			JCSystem.commitTransaction();
			break;
		case INS_INSERT:
			apdu.setIncomingAndReceive();
			short dataLen = (short)(buf[ISO7816.OFFSET_LC]&0xff);
			short flag = (short)1;
			JCSystem.beginTransaction();
			arrayhoten = new byte[countht];
			arrayngaysinh = new byte[countns];
			arrayCMND = new byte[countcmnd];
			arrayGPLX = new byte[countgplx];
			arrayvehicle = new byte[countvehicle];
			
			//  copy length array 
			// apdu data: [HoTen][NS][CMND][GPLX][Vehicle]
			// B0 03 11 22 
			// 01 21 02 21 03 21 04 21 05
			
			short tempIndex = (short)0;
			for(short i = (short)(ISO7816.OFFSET_CDATA)
				;i<(short)(ISO7816.OFFSET_CDATA +dataLen)
				;i++ ){
				// ky tu phan cach
				if(buf[i]==(byte)0x21){
					flag+=(short)1;
					tempIndex = (short)0;
					continue;
				}
				if(flag ==(short)1){
					arrayhoten[tempIndex++]=buf[i];
				}
				else if(flag ==(short)2){
					arrayngaysinh[tempIndex++]=buf[i];
				}
				else if(flag ==(short)3){
					arrayCMND[tempIndex++]=buf[i];
				}
				else if(flag ==(short)4){
					arrayGPLX[tempIndex++]=buf[i];						
				}else if(flag ==(short)5){
					arrayvehicle[tempIndex++]=buf[i];
				}
			}
			JCSystem.commitTransaction();
			break;	
			
		case INS_THONGTIN:	
			apdu.setIncomingAndReceive();
			
			// [len] + [data]
			short lenhoten = (short) arrayhoten.length;
			short lenngaysinh = (short) arrayngaysinh.length;
			short lencmnd = (short) arrayCMND.length;
			short lengplx = (short) arrayGPLX.length;
			short lenvehicle = (short) arrayvehicle.length;
			short len = (short) 
				(lenhoten + lenngaysinh + lencmnd + lengplx + lenvehicle +(short)5);
			
			apdu.setOutgoing();
			apdu.setOutgoingLength(len);
			
			Util.setShort(buf, (short)0, lenhoten);
			apdu.sendBytes((short)1, (short)1);
			Util.arrayCopy(arrayhoten,(short)0,buf,(short)0,lenhoten);
			apdu.sendBytes((short)0, lenhoten);
		
			Util.setShort(buf, (short)0, lenngaysinh);
			apdu.sendBytes((short)1, (short)1);
			Util.arrayCopy(arrayngaysinh,(short)0,buf,(short)0,lenngaysinh);
			apdu.sendBytes((short)0, lenngaysinh);
			
			Util.setShort(buf, (short)0, lencmnd);
			apdu.sendBytes((short)1, (short)1);
			Util.arrayCopy(arrayCMND,(short)0,buf,(short)0,lencmnd);
			apdu.sendBytes((short)0, lencmnd);
			
			Util.setShort(buf, (short)0, lengplx);
			apdu.sendBytes((short)1, (short)1);
			Util.arrayCopy(arrayGPLX,(short)0,buf,(short)0,lengplx);
			apdu.sendBytes((short)0, lengplx);
			
			Util.setShort(buf, (short)0, lenvehicle);
			apdu.sendBytes((short)1, (short)1);
			Util.arrayCopy(arrayvehicle,(short)0,buf,(short)0,lenvehicle);
			apdu.sendBytes((short)0, lenvehicle);
			break;		
		
		// XU LY ANH
		case INS_NAPANH:
			apdu.setIncomingAndReceive();
			short p1 = (short)(buf[ISO7816.OFFSET_P1]&0xff);
			short count1 = (short)(249 * p1);
			Util.arrayCopy(buf, ISO7816.OFFSET_CDATA, image, count1, (short)249);
			break;
		case INS_SETCOUNT:
			Util.arrayCopy(buf, ISO7816.OFFSET_CDATA, size, (short)0, (short)7);
			break;
		case INS_COUNTANH:
			Util.arrayCopy(size, (short)0, buf, (short)0, (short)(size.length));
			apdu.setOutgoingAndSend((short)0,(short)7);
			break;
		case INS_ANH:
			apdu.setIncomingAndReceive();
			apdu.setOutgoing();
			short p = (short)(buf[ISO7816.OFFSET_P1]&0xff);
			short count = (short)(249 * p);
			apdu.setOutgoingLength((short)249);
			apdu.sendBytesLong(image, count, (short)249);
			break;
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

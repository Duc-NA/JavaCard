package driver_card;

import javacard.framework.*;
import javacardx.crypto.Cipher;
import javacard.security.*;
import driver_card.PinInterface;

public class InfoApplet extends Applet implements INFO_CONSTANTS
{
	// variable
	private static byte[] arrayhoten, arrayngaysinh, arrayCMND,arrayGPLX, arrayvehicle, image, size;
	final static byte phi = (byte) 0x03;
	byte balance = (byte) 0x0A;
	short countht, countns, countcmnd, countgplx, countvehicle;
	
	// bien dem so lan vi pham giao thong
	private short error = 0;
	// neu nhu the block thi bien block = 1; Neu the khong bi block thi block = 0
	private short block = 0;
	
	// khai bao bien
	private Cipher aesCipher;
    private AESKey aesKey;
    
    // variable	 encrypt
	public static byte[] arrayhotenencrypt = new byte[256];
	public static byte[] arrayngaysinhencrypt = new byte[256];
	public static byte[] arrayCMNDencrypt = new byte[256];
	public static byte[] arrayGPLXencrypt = new byte[256];
	public static byte[] arrayvehicleencrypt = new byte[256];
	
	
	private final byte[] pinAID 
		= new byte[]{(byte)0x61, (byte)0x75, (byte)0x74, 
		(byte)0x68, (byte)0x65, (byte)0x6E, (byte)0x2F, (byte)0x70, (byte)0x69, (byte)0x6E};

	public static void install(byte[] bArray, short bOffset, byte bLength) 
	{
		new InfoApplet(bArray,bOffset,bLength);
	}
	
	private InfoApplet(byte[] bArray, short bOffset, byte bLength){
		image = new byte[10000];
		size = new byte[7];
		
		// Khoi tao AES
		aesCipher = Cipher.getInstance(Cipher.ALG_AES_BLOCK_128_CBC_NOPAD, false);
        aesKey = (AESKey) KeyBuilder.buildKey(KeyBuilder.TYPE_AES, KeyBuilder.LENGTH_AES_128, false);
        
        register();
	}
	
	private void setAESKey(){
		// get AES Key from PIN applet
		AID masterAID = JCSystem.lookupAID(
						pinAID, 
						(short)0, 
						(byte)pinAID.length);
		PinInterface sio = (PinInterface)
			(JCSystem.getAppletShareableInterfaceObject(masterAID, (byte)0x00));
		byte[] hasPin= sio.getHashPin();
	
        try {
            aesKey.setKey(hasPin, (short) 0);
        } catch(Exception e) {
            Util.arrayFillNonAtomic(new byte[16], (short) 0, APPLET_CONSTANTS.KEY_SIZE, (byte) 0);
        }
    
	}

	public void process(APDU apdu)
	{
		if (selectingApplet())
		{
			return;
		}

		byte[] buf = apdu.getBuffer();
		apdu.setIncomingAndReceive();
		
		
		switch (buf[ISO7816.OFFSET_INS])
		{

	case INS_COUNTINSERT:
			short dataLen1 = (short)(buf[ISO7816.OFFSET_LC]&0xff);
			short flag1 = (short)1;
			countht = 0;
			countns = 0;
			countcmnd = 0;
			countgplx = 0;
			countvehicle = 0;
			for(short i = (short)(ISO7816.OFFSET_CDATA)
				;i<(short)(ISO7816.OFFSET_CDATA +dataLen1)
				;i++ ){
				if(buf[i]==(byte)0x21){
					flag1+=(short)1;
					continue;
				}
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
				
			break;
		case INS_INSERT:
			short dataLen = (short)(buf[ISO7816.OFFSET_LC]&0xff);
			short flag = (short)1;
			arrayhoten = new byte[countht];
			arrayngaysinh = new byte[countns];
			arrayCMND = new byte[countcmnd];
			arrayGPLX = new byte[countgplx];
			arrayvehicle = new byte[countvehicle];
			//  copy length array 
			short tempIndex = (short)0;
				for(short i = (short)(ISO7816.OFFSET_CDATA);i<(short)(ISO7816.OFFSET_CDATA +dataLen);i++ ){
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
			// encrypt
			setAESKey();
			byte[] ht = encrypt(arrayhoten);
			byte[] ns = encrypt(arrayngaysinh);
			byte[] cmnd = encrypt(arrayCMND);
			byte[] gplx = encrypt(arrayGPLX);
			byte[] vc = encrypt(arrayvehicle);

			Util.arrayCopy(ht,(short)0,arrayhotenencrypt,(short)0, (short)ht.length);
			Util.arrayCopy(ns,(short)0,arrayngaysinhencrypt,(short)0, (short)ns.length);
			Util.arrayCopy(cmnd,(short)0,arrayCMNDencrypt,(short)0, (short)cmnd.length);
			Util.arrayCopy(gplx,(short)0,arrayGPLXencrypt,(short)0, (short)gplx.length);
			Util.arrayCopy(vc,(short)0,arrayvehicleencrypt,(short)0, (short)vc.length);
			break;	
			
		case INS_THONGTIN:
			// decrypt
			byte[] htin = decrypt(arrayhotenencrypt);
			byte[] nsin = decrypt(arrayngaysinhencrypt);
			byte[] cmndin = decrypt(arrayCMNDencrypt);
			byte[] gplxin = decrypt(arrayGPLXencrypt);
			byte[] vcin = decrypt(arrayvehicleencrypt);
			
			short lenhoten = (short) arrayhoten.length;
			short lenngaysinh = (short) arrayngaysinh.length;
			short lencmnd = (short) arrayCMND.length;
			short lengplx = (short) arrayGPLX.length;
			short lenvehicle = (short) arrayvehicle.length;
			short len = (short) (lenhoten + lenngaysinh + lencmnd + lengplx + lenvehicle + 5);
			apdu.setOutgoing();
			apdu.setOutgoingLength(len);
			
			buf[0] = (byte) lenhoten;
			apdu.sendBytes((short)0, (byte)1);
			Util.arrayCopy(htin,(short)0,buf,(short)0,lenhoten);
			apdu.sendBytes((short)0, lenhoten);
			
			buf[0] = (byte) lenngaysinh;
			apdu.sendBytes((short)0, (byte)1);
			Util.arrayCopy(nsin,(short)0,buf,(short)0,lenngaysinh);
			apdu.sendBytes((short)0, lenngaysinh);
			
			buf[0] = (byte) lencmnd;
			apdu.sendBytes((short)0, (byte)1);
			Util.arrayCopy(cmndin,(short)0,buf,(short)0,lencmnd);
			apdu.sendBytes((short)0, lencmnd);
			
			buf[0] = (byte) lengplx;
			apdu.sendBytes((short)0, (byte)1);
			Util.arrayCopy(gplxin,(short)0,buf,(short)0,lengplx);
			apdu.sendBytes((short)0, lengplx);
			
			buf[0] = (byte) lenvehicle;
			apdu.sendBytes((short)0, (byte)1);
			Util.arrayCopy(vcin,(short)0,buf,(short)0,lenvehicle);
			apdu.sendBytes((short)0, lenvehicle);
			break;		
			
		case INS_NAPANH:
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
			apdu.setOutgoing();
			short p = (short)(buf[ISO7816.OFFSET_P1]&0xff);
			short count = (short)(249 * p);
			apdu.setOutgoingLength((short)249);
			apdu.sendBytesLong(image, count, (short)249);
			break;
		case INS_ERROR:
			error++;
			break;	
			// check xem da vi pham bao nhieu loi
		case INS_CHECKERROR:
			byte[] checkerror = new byte[1];
			checkerror[0] = (byte)error;
			apdu.setOutgoing();
			apdu.setOutgoingLength((short)1);
			Util.arrayCopy(checkerror,(short)0,buf,(short)0,(short)1);
			apdu.sendBytes((short)0, (short)1);
			break;
			// check xem co block hay khong
		case INS_CHECKBLOCKE:
			byte[] checkblock = new byte[1];
			checkblock[0] = (byte)block;
			apdu.setOutgoing();			
			apdu.setOutgoingLength((short)1);
			Util.arrayCopy(checkblock,(short)0,buf,(short)0,(short)1);
			apdu.sendBytes((short)0, (short)1);
			break;
			
			// chuc nang block the
		case INS_BLOCK:			
			block = (short)1;
			break;
			// chuc nang unblock the
		case INS_UNBLOCK:
			block = (short)0;
			error = (short)0;
			break;
		default:
			ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
		}
	}
	
	/*AES*/
	private byte[] encrypt(byte[] encryptData) {
        aesCipher.init(aesKey, Cipher.MODE_ENCRYPT);
        short flag = (short) 1;
	    byte[] temp = new byte[256];
    	while(flag == (short)1){
    		for(short i=0;i<=(short) encryptData.length;i++){
    			if(i!=(short) encryptData.length){
					temp[i] = encryptData[i];
    			}
    			else{
	    			flag = (short) 0;
    			}
    		}
    	}
             
        byte[] dataEncrypted = new byte[256];
        aesCipher.doFinal(temp, (short) 0 , (short)256, dataEncrypted, (short) 0x00);
        return dataEncrypted;
    }

    
    private byte[] decrypt(byte[] decryptData) {
        aesCipher.init(aesKey, Cipher.MODE_DECRYPT);
        byte[] dataDecrypted = new byte[256];
        aesCipher.doFinal(decryptData, (short) 0, (short) 256, dataDecrypted, (short) 0x00);
        return dataDecrypted;
    }
   
}

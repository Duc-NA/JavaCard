package AuthenticationPin;

import javacard.framework.*;

public class Infomation extends Applet
{
	//CLA
	final static byte Bot_CLA = (byte) 0xB0;
	
	//INS
	final static byte INS_THONGTIN = (byte)0x00;
	final static byte INS_NAPANH = (byte)0x01;
	final static byte INS_ANH = (byte)0x02;
	final static byte INS_INSERT = (byte)0x03;
	final static byte INS_COUNTINSERT = (byte)0x04;
	final static byte INS_SETCOUNT = (byte)0x05;
	final static byte INS_COUNTANH = (byte)0x06;
	
	// variable
	private static byte[] arrayhoten, arrayngaysinh, arrayCMND,arrayGPLX, arrayvehicle, image, size;
	final static byte phi = (byte) 0x03;
	byte balance = (byte) 0x0A;
	short countht, countns, countcmnd, countgplx, countvehicle;

	public static void install(byte[] bArray, short bOffset, byte bLength) 
	{
		new Infomation().register(bArray, (short) (bOffset + 1), bArray[bOffset]);
		image = new byte[10000];
		size = new byte[7];
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
				for(short i = (short)(ISO7816.OFFSET_CDATA);i<(short)(ISO7816.OFFSET_CDATA +1+dataLen1);i++ ){
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
			countht++;
			countns++;
			countcmnd++;
			countgplx++;
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
				for(short i = (short)(ISO7816.OFFSET_CDATA);i<(short)(ISO7816.OFFSET_CDATA +1+dataLen);i++ ){
					if(buf[i]==(byte)0x21){
						// if(flag ==(short)1){
							// arrayhoten[tempIndex++]=buf[i];
						// }
						// else if(flag ==(short)2){
							// arrayngaysinh[tempIndex++]=buf[i];
						// }
						// else if(flag ==(short)3){
							// arrayCMND[tempIndex++]=buf[i];
						// }else if(flag ==(short)4){
							// arrayGPLX[tempIndex++]=buf[i];
						// }else if(flag ==(short)5){
							// arrayvehicle[tempIndex++]=buf[i];
						// }
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
			break;	
			
		case INS_THONGTIN:			
			short lenhoten = (short) arrayhoten.length;
			short lenngaysinh = (short) arrayngaysinh.length;
			short lencmnd = (short) arrayCMND.length;
			short lengplx = (short) arrayGPLX.length;
			short lenvehicle = (short) arrayvehicle.length;
			short len = (short) (lenhoten + lenngaysinh + lencmnd + lengplx + lenvehicle);
			apdu.setOutgoing();
			apdu.setOutgoingLength(len);
			Util.arrayCopy(arrayhoten,(short)0,buf,(short)0,lenhoten);
			apdu.sendBytes((short)0, lenhoten);
			Util.arrayCopy(arrayngaysinh,(short)0,buf,(short)0,lenngaysinh);
			apdu.sendBytes((short)0, lenngaysinh);
			Util.arrayCopy(arrayCMND,(short)0,buf,(short)0,lencmnd);
			apdu.sendBytes((short)0, lencmnd);
			Util.arrayCopy(arrayGPLX,(short)0,buf,(short)0,lengplx);
			apdu.sendBytes((short)0, lengplx);
			Util.arrayCopy(arrayvehicle,(short)0,buf,(short)0,lenvehicle);
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
		default:
			ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
		}
	}

}

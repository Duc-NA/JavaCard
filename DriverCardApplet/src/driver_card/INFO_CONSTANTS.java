package driver_card;

public interface INFO_CONSTANTS {
	final static byte Bot_CLA = (byte) 0xB0;
	
	final static byte INS_THONGTIN = (byte)0x00;
	final static byte INS_NAPANH = (byte)0x01;
	final static byte INS_ANH = (byte)0x02;
	final static byte INS_INSERT = (byte)0x03;
	final static byte INS_COUNTINSERT = (byte)0x04;
	final static byte INS_SETCOUNT = (byte)0x05;
	final static byte INS_COUNTANH = (byte)0x06;
	
	final static byte INS_BLOCK = (byte)0x07;
	final static byte INS_UNBLOCK = (byte)0x08;
	final static byte INS_ERROR = (byte)0x09;
	final static byte INS_CHECKBLOCKE = (byte)0xA0;
	final static byte INS_CHECKERROR = (byte)0x0A1;
}

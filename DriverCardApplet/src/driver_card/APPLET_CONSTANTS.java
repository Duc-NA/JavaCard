package driver_card;

public class APPLET_CONSTANTS {
	public final static byte CLA =(byte)0xB0;
	public final static byte CLA_SELECT = 0x00;
	public final static byte SELECT =(byte)0x04;
	
	public final static byte CREATE = (byte) 0x10;
	public final static byte UPDATE = (byte) 0x11;
	public final static byte READ = (byte) 0x13;
	public final static byte DELETE = (byte) 0x14;
	public final static byte UNBLOCK = (byte) 0x15;
	public final static byte VERIFY = (byte) 0x16;
	public final static byte CLEAR = (byte) 0x17;
	public final static byte INIT_CARD = (byte) 0x99;
	
	public final static short KEY_SIZE= 16;
}

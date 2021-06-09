package authentication;
public class AUTH_CONSTANTS {
	final static byte AUTH =(byte)0x63;
	
	final static byte[] DEAULT_PIN_CODE = new byte[]{1,2,3,4,5,6};
	
	// maximum number of incorrect tries before the
	// PIN is blocked
	final static byte PIN_TRY_LIMIT =(byte)0x05;

	// maximum size PIN
	final static byte MAX_PIN_SIZE =(byte)0x06;

	// signal that the PIN verification failed
	final static short SW_VERIFICATION_FAILED = 0x6312;

	// signal the PIN validation is required
	// for a credit or a debit transaction
	final static short SW_PIN_VERIFICATION_REQUIRED = 0x6311;
	final static short SW_WRONG_PIN_LEN = 0x6313;
	final static short SW_OVER_TRY_TIMES = 0x6314;
}

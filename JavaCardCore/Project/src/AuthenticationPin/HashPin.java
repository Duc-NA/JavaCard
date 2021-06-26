package AuthenticationPin;

import javacard.framework.*;
import javacard.security.KeyBuilder;
import javacard.security.KeyPair;
import javacard.security.RSAPrivateCrtKey;
import javacard.security.RSAPublicKey;
import javacardx.crypto.Cipher;

public class HashPin extends Applet
{
	final static byte Bot_CLA = (byte) 0xB0;

	private final static byte RSA_ENCODE = (byte) 0xD0;

	private final static byte RSA_DECODE = (byte) 0xD2;

	byte[] tmp;

	private KeyPair keyPair;

	private RSAPrivateCrtKey rsa_privateKey;

	private RSAPublicKey rsa_publicKey;

	private Cipher rsaCipher = null;
	
	private static byte[] arraytest;
	
	public HashPin() {
		tmp = JCSystem.makeTransientByteArray((short) 256, JCSystem.CLEAR_ON_RESET);
		rsaCipher = Cipher.getInstance(Cipher.ALG_RSA_PKCS1, false);
		keyPair = new KeyPair(KeyPair.ALG_RSA_CRT, KeyBuilder.LENGTH_RSA_512);
		rsa_privateKey = (RSAPrivateCrtKey) keyPair.getPrivate();
		rsa_publicKey = (RSAPublicKey) keyPair.getPublic();
		keyPair.genKeyPair();

	}
	
	public static void install(byte[] bArray, short bOffset, byte bLength) 
	{
		new HashPin().register(bArray, (short) (bOffset + 1), bArray[bOffset]);
	}

	public void process(APDU apdu)
	{
		if (selectingApplet())
		{
			return;
		}
		
		short outLength;
		byte[] buf = apdu.getBuffer();
		switch (buf[ISO7816.OFFSET_INS])
		{
		case RSA_ENCODE:
			// rsa_encode(apdu);
			readBuffer(apdu, tmp, (short) 0, (buf[ISO7816.OFFSET_LC]));
			apdu.setOutgoing();
			rsaCipher.init(rsa_publicKey, Cipher.MODE_ENCRYPT);
			outLength = rsaCipher.doFinal(tmp, (short) 0, (buf[ISO7816.OFFSET_LC]), buf, (short) 0);
			apdu.setOutgoingLength(outLength);
			apdu.sendBytes((short) 0, outLength);
			break;
		// decode cipher text from input
		case RSA_DECODE:
			readBuffer(apdu, tmp, (short) 0, (buf[ISO7816.OFFSET_LC]));
			apdu.setOutgoing();
			rsaCipher.init(rsa_privateKey, Cipher.MODE_DECRYPT);
			outLength = rsaCipher.doFinal(tmp, (short) 0, (buf[ISO7816.OFFSET_LC]), buf, (short) 0);
			apdu.setOutgoingLength(outLength);
			apdu.sendBytes((short) 0, outLength);
			break;
		default:
			ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
		}
	}
	private void readBuffer(APDU apdu, byte[] dest, short offset, short length) {
		byte[] buf = apdu.getBuffer();
		short readCount = apdu.setIncomingAndReceive();
		short i = 0;
		Util.arrayCopy(buf, ISO7816.OFFSET_CDATA, dest, offset, readCount);
		while ((short) (i + readCount) < length) {
			i += readCount;
			offset += readCount;
			readCount = (short) apdu.receiveBytes(ISO7816.OFFSET_CDATA);
			Util.arrayCopy(buf, ISO7816.OFFSET_CDATA, dest, offset, readCount);
		}
	}

}

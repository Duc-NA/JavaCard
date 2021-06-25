package AuthenticationPin;

import javacard.framework.*;
import javacard.security.Key;
import javacard.security.KeyAgreement;
import javacard.security.KeyBuilder;
javacard.security.Signature
import javacard.security.KeyPair;
import javacard.security.RSAPrivateCrtKey;
import javacard.security.RSAPrivateKey;
import javacard.security.RSAPublicKey;

public class HashPin extends Applet
{

	final static byte Bot_CLA = (byte) 0xB0;
	final static byte INS_RSA = (byte)0x01;	
	final static byte[] m_privateKey;
	final static byte[] m_publicKey;
	final static byte[] m_keyPair;
	
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

		byte[] buf = apdu.getBuffer();
		switch (buf[ISO7816.OFFSET_INS])
		{
		case INS_RSA:
			m_privateKey = KeyBuilder.buildKey(KeyBuilder.TYPE_RSA_PRIVATE,KeyBuilder.LENGTH_RSA_1024,false);
			m_publicKey = KeyBuilder.buildKey(KeyBuilder.TYPE_RSA_PUBLIC,KeyBuilder.LENGTH_RSA_1024,true);
			m_keyPair = new KeyPair(KeyPair.ALG_RSA, (short) m_publicKey.getSize());

			m_keyPair.genKeyPair();

			m_publicKey = m_keyPair.getPublic();
			m_privateKey = m_keyPair.getPrivate();
			Signature m_sign = Signature.getInstance(Signature.ALG_RSA_SHA_PKCS1, false);
			m_sign.init(m_privateKey, Signature.MODE_SIGN);
			signLen = m_sign.sign(buf, ISO7816.OFFSET_CDATA, (byte) 0, m_ramArray, (byte) 0);
		    short publicKey = (short) m_publicKey.length;
		    short privateKey = (short) m_privateKey.length;
		    short lent = publicKey + privateKey;
		    
			apdu.setOutgoing();
			apdu.setOutgoingLength(lent);
			Util.arrayCopy(m_publicKey,(short)0,buf,(short)0,publicKey);
			apdu.sendBytes((short)0, publicKey);
			Util.arrayCopy(m_privateKey,(short)0,buf,(short)0,privateKey);
			apdu.sendBytes((short)0, privateKey);
			break;
		default:
			ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
		}
	}

}

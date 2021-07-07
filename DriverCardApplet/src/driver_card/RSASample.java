package driver_card;

/*
 * @file  RSASample.java
 * @version v1.0
 * Package AID: 4A617661436172644F53 
 * Applet AID:  4A617661436172644F5303
 * @brief The ALgorithm of RSA Sample Code in JavaCard API Specification
 * @comment The purpose of this example is only used to show the usage of API functions and there is no practical significance.
 * @copyright Copyright(C) 2016 JavaCardOS Technologies Co., Ltd. All rights reserved.
 */

import javacard.framework.*;
import javacard.security.KeyBuilder;
import javacard.security.*;
import javacardx.crypto.*;

public class RSASample extends Applet
{
    private static final byte INS_GET_RSA_PUBKEY           = (byte)0x31;
    private static final byte INS_RSA_SIGN                 = (byte)0x35;

	private byte[] tempBuffer;

    private byte[] flags;
    private static final short OFF_INS    = (short)0;
    private static final short OFF_P1     = (short)1;
    private static final short OFF_P2     = (short)2;
    private static final short OFF_LEN    = (short)3;
    private static final short FLAGS_SIZE = (short)5;

	private static final byte ID_N   = 0;
    private static final byte ID_D   = 1;
    private static final byte ID_P   = 2;
    private static final byte ID_Q   = 3;
    private static final byte ID_PQ  = 4;
    private static final byte ID_DP1 = 5;
    private static final byte ID_DQ1 = 6;
    
	private byte[] rsaPubKey;
    private short rsaPubKeyLen;
    private byte[] rsaPriKey;
    private short rsaPriKeyLen;
    private Cipher rsaCipher;    
    private Signature rsaSignature;
    
   private static final short SW_REFERENCE_DATA_NOT_FOUND = (short)0x6A88;
   
   public RSASample()
    {
       //Create a transient byte array to store the temporary data
        tempBuffer = JCSystem.makeTransientByteArray((short)256, JCSystem.CLEAR_ON_DESELECT);
        flags = JCSystem.makeTransientByteArray(FLAGS_SIZE, JCSystem.CLEAR_ON_DESELECT);

        rsaPubKey = new byte[(short)(256 + 32)];
        rsaPriKey = new byte[(short)(128 * 5)];
        rsaPubKeyLen = 0;
        rsaPriKeyLen = 0;
        rsaSignature = null;
        //Create a RSA(not pad) object instance
        rsaCipher = Cipher.getInstance(Cipher.ALG_RSA_NOPAD, false);

        JCSystem.requestObjectDeletion();
        
        genRsaKeyPair();
    }
    
   public static void install(byte[] bArray, short bOffset, byte bLength) 
   {
      new RSASample().register(bArray, (short) (bOffset + 1), bArray[bOffset]);
   }

   public void process(APDU apdu)
   {
      if (selectingApplet())
      {
         return;
      }

      byte[] buf = apdu.getBuffer();
      short len = apdu.setIncomingAndReceive();
      switch (buf[ISO7816.OFFSET_INS])
      {
        case INS_GET_RSA_PUBKEY:
           //GET_RSA_PUBKEY
            getRsaPubKey(apdu);
            break;
        case INS_RSA_SIGN:
           //RSA_SIGN
            rsaSign(apdu, len);
            break;
      default:
         ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
      }
   }
   
    //Get the value of RSA Public Key from the global variable 'rsaPubKey' 
    private void getRsaPubKey(APDU apdu)
    {
        byte[] buffer = apdu.getBuffer();
        if (rsaPubKeyLen == 0)
        {
            ISOException.throwIt(SW_REFERENCE_DATA_NOT_FOUND);
        }

        short modLen = rsaPubKeyLen <= (128 + 32) ? (short)128 : (short)256;
        switch (buffer[ISO7816.OFFSET_P1])
        {
        case 0:
            Util.arrayCopyNonAtomic(rsaPubKey,(short)0,buffer,(short)0,modLen);
            apdu.setOutgoingAndSend((short)0,modLen);
            break;
        case 1:
            //get public key E
            short eLen = (short)(rsaPubKeyLen - modLen);
            Util.arrayCopyNonAtomic(rsaPubKey,modLen,buffer,(short)0,eLen);
            apdu.setOutgoingAndSend((short)0,eLen);
            break;
        default:
            ISOException.throwIt(ISO7816.SW_INCORRECT_P1P2);
            break;
        }

    }
   //According to the different ID, returns the value/length of RSA Private component
    private short getRsaPriKeyComponent(byte id, byte[] outBuff, short outOff)
    {
        if (rsaPriKeyLen == 0)
        {
            return (short)0;
        }
        short modLen;
        
        
        if (rsaPriKeyLen == 128 * 2)
		{
			modLen = (short)128;
		}
		else
		{
			modLen = (short)256;
        }
        
        short readOff;
        short readLen;

        switch (id)
        {
        case ID_N:
            readOff = (short)0;
            readLen = modLen;
            break;
        case ID_D:
            //RSA private key D
            readOff = modLen;
            readLen = modLen;
            break;
        case ID_P:
            readOff = (short)0;
            readLen = (short)(modLen / 2);
            break;
        case ID_Q:
            readOff = (short)(modLen / 2);
            readLen = (short)(modLen / 2);
            break;
        case ID_PQ:
            readOff = (short)(modLen);
            readLen = (short)(modLen / 2);
            break;
        case ID_DP1:
            readOff = (short)(modLen / 2 * 3);
            readLen = (short)(modLen / 2);
            break;
        case ID_DQ1:
            readOff = (short)(modLen * 2);
            readLen = (short)(modLen / 2);
            break;
        default:
            return 0;
        }
        Util.arrayCopyNonAtomic(rsaPriKey, readOff, outBuff, outOff, readLen);
        return readLen;
    }

    //
   private void genRsaKeyPair()
    {
        short keyLen =  (short)1024;
        byte alg = KeyPair.ALG_RSA;
        KeyPair keyPair = new KeyPair(alg, keyLen);
        
        //(Re)Initializes the key objects encapsulated in this KeyPair instance with new key values.
        keyPair.genKeyPair();
        JCSystem.beginTransaction();
        rsaPubKeyLen = 0;
        rsaPriKeyLen = 0;
        JCSystem.commitTransaction();
        //Get a reference to the public key component of this 'keyPair' object.
        RSAPublicKey pubKey = (RSAPublicKey)keyPair.getPublic();
        short pubKeyLen = 0;
        //Store the RSA public key value in the global variable 'rsaPubKey', the public key contains modulo N and Exponent E
        pubKeyLen += pubKey.getModulus(rsaPubKey, pubKeyLen);
        pubKeyLen += pubKey.getExponent(rsaPubKey, pubKeyLen);

        short priKeyLen = 0;
   
    
		//Returns a reference to the private key component of this KeyPair object.
		RSAPrivateKey priKey = (RSAPrivateKey)keyPair.getPrivate();
		//RSA Algorithm,  the Private Key contains N and D, and store these parameters value in global variable 'rsaPriKey'.
		priKeyLen += priKey.getModulus(rsaPriKey, priKeyLen);
		priKeyLen += priKey.getExponent(rsaPriKey, priKeyLen);
  
        JCSystem.beginTransaction();
        rsaPubKeyLen = pubKeyLen;
        rsaPriKeyLen = priKeyLen;
        JCSystem.commitTransaction();

        JCSystem.requestObjectDeletion();
    }
    
   //RSA Signature
    private void rsaSign(APDU apdu, short len)
    {
        byte[] buffer = apdu.getBuffer();
        if (rsaPriKeyLen == 0)
        {
            ISOException.throwIt(ISO7816.SW_CONDITIONS_NOT_SATISFIED);
        }
        boolean hasMoreCmd = (buffer[ISO7816.OFFSET_P1] & 0x80) != 0;
        short resultLen = 0;
        if (buffer[ISO7816.OFFSET_P2] == 0) //first block
        {
            Key key;
            
			short ret;
			//Creates uninitialized private keys for signature algorithms.
			key = KeyBuilder.buildKey(KeyBuilder.TYPE_RSA_PRIVATE, (short)(rsaPriKeyLen / 2 * 8), false);
			ret = getRsaPriKeyComponent(ID_N, tempBuffer, (short)0);
			((RSAPrivateKey)key).setModulus(tempBuffer, (short)0, ret);
			ret = getRsaPriKeyComponent(ID_D, tempBuffer, (short)0);
			((RSAPrivateKey)key).setExponent(tempBuffer, (short)0, ret);
            
         // Creates a Signature object instance of the ALG_RSA_SHA_PKCS1 algorithm.
            rsaSignature = Signature.getInstance(Signature.ALG_RSA_SHA_PKCS1, false);
            JCSystem.requestObjectDeletion();
         //Initializ the Signature object.
            rsaSignature.init(key, Signature.MODE_SIGN);

            Util.arrayCopyNonAtomic(buffer, ISO7816.OFFSET_INS, flags, OFF_INS, (short)3);
            JCSystem.requestObjectDeletion();
        }
        else
        {
            if (flags[OFF_INS] != buffer[ISO7816.OFFSET_INS]
                    || (flags[OFF_P1] & 0x7f) != (buffer[ISO7816.OFFSET_P1] & 0x7f)
                    || (short)(flags[OFF_P2] & 0xff) != (short)((buffer[ISO7816.OFFSET_P2] & 0xff) - 1))
            {
                Util.arrayFillNonAtomic(flags, (short)0, (short)flags.length, (byte)0);
                ISOException.throwIt(ISO7816.SW_INCORRECT_P1P2);
            }

            flags[OFF_P2] ++;
        }

        if (hasMoreCmd)
        {
           // Accumulates a signature of the input data. 
            rsaSignature.update(buffer, ISO7816.OFFSET_CDATA, len);
        }
        else
        {
           //Generates the signature of all input data.
            short ret = rsaSignature.sign(buffer, ISO7816.OFFSET_CDATA, len, buffer, (short)0);
            Util.arrayFillNonAtomic(flags, (short)0, (short)flags.length, (byte)0);
            apdu.setOutgoingAndSend((short)0, ret);
        }
    }
    
}
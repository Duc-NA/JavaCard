package driver_card;

import javacard.security.*;
import javacardx.crypto.Cipher;
import javacard.framework.Shareable;

public interface PinInterface extends Shareable{
	 public byte[] getHashPin();
}

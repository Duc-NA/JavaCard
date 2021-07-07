/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drivercardhostapp.commons.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64.Encoder;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Admin
 */
public class RSAData {

    public static void savePublicKey(PublicKey publicKey) {
        FileOutputStream fos = null;
        try {
            File publicKeyFile = createKeyFile(new File("publicKey.rsa"));
            // Lưu Public Key
            fos = new FileOutputStream(publicKeyFile);
            fos.write(publicKey.getEncoded());
            fos.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RSAData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RSAData.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fos.close();
            } catch (IOException ex) {
                Logger.getLogger(RSAData.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static PublicKey getPublicKey() {
        FileInputStream fis = null;
        try {
            // Đọc file chứa private key
            fis = new FileInputStream("publicKey.rsa");
            byte[] b = new byte[fis.available()];
            fis.read(b);
            fis.close();
            // Tạo public key
            X509EncodedKeySpec spec = new X509EncodedKeySpec(b);
            KeyFactory factory = KeyFactory.getInstance("RSA");
            PublicKey pubKey = factory.generatePublic(spec);

            return pubKey;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RSAData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RSAData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(RSAData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(RSAData.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(RSAData.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return null;
    }

    private static File createKeyFile(File file) throws IOException {
        if (!file.exists()) {
            file.createNewFile();
        } else {
            file.delete();
            file.createNewFile();
        }
        return file;
    }

    public static PublicKey initPublicKey(byte[] modulusBytes, byte[] exponentBytes) {
        try {
            BigInteger modulus = new BigInteger(
                    1,
                    modulusBytes
            );
            BigInteger exponent = new BigInteger(
                    1,
                    exponentBytes
            );

            RSAPublicKeySpec spec = new RSAPublicKeySpec(modulus, exponent);
            KeyFactory factory = KeyFactory.getInstance("RSA");
            PublicKey pub = factory.generatePublic(spec);

            return pub;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(RSAData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static boolean verify(PublicKey publicKey, byte[] sigBytes, byte[] data) {
        try {
            Signature signature1 = Signature.getInstance("SHA1withRSA");
            signature1.initVerify(publicKey);
            signature1.update(data);
            boolean result = signature1.verify(sigBytes);
            return result;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(RSAData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(RSAData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SignatureException ex) {
            Logger.getLogger(RSAData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}

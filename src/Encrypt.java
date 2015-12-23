import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.AlgorithmParameters;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.*;

import org.apache.commons.codec.binary.Base64;

import com.ibm.icu.impl.ICUService.Factory;
import java.util.*;

public class Encrypt {
	

	private static String ekey="0123456789abcdef";
		
	 public static String encrypt(String plainText) throws Exception {
		    Cipher cipher = Cipher.getInstance("AES");
		    byte[] keybytes = ekey.getBytes();
		    SecretKeySpec sKey= new SecretKeySpec(keybytes,"AES");
		    cipher.init(Cipher.ENCRYPT_MODE, sKey);
		    return Base64.encodeBase64String(cipher.doFinal(plainText.getBytes("UTF-8")));
		  }
	
	public static String decrypt(String encryptedText)throws Exception {
		Cipher cipher = Cipher.getInstance("AES");
		byte[] keybytes =ekey.getBytes();
		SecretKeySpec sKey = new SecretKeySpec(keybytes,"AES");
		cipher.init(Cipher.DECRYPT_MODE, sKey);
		return new String(cipher.doFinal(Base64.decodeBase64(encryptedText)));
	}
	public static void main(String args[]){
		try{
		String test = encrypt("ASDFGHJ");
		System.out.println(decrypt(test));
		}catch(Exception ex){
			System.out.println(ex);
		}
	}
	
}

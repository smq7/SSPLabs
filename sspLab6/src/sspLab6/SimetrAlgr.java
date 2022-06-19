package sspLab6;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.util.Arrays;
import java.util.Base64;

public class SimetrAlgr {
	public static void main(String[] args) throws Exception {
		SimetrAlgr cryptoEx = new SimetrAlgr();

        SecretKeySpec key = cryptoEx.createSecretKey();
        String text = "decrypt!decrypt!decrypt!decrypt!";

//        System.out.println(textHash(text));
        
        byte[] enc = cryptoEx.encrypt(key, text.getBytes());
        String plainBefore = new String(enc);
        System.out.println("Original text: '" + text + "'");
        System.out.println("digital signature fo original text :\n" + plainBefore);

        byte[] bytes = cryptoEx.decrypt(key, enc);
        if (bytes != null) {
            String plainAfter = new String(bytes);
            System.out.println("signature after decryption: '" +
                                        plainAfter + "'");
//            System.out.println(textHash(plainAfter));
        }
		 
	  }
	
	Cipher cipher;
	
//    String transformation = "AES/ECB/PKCS5Padding";
    String transformation = "AES/CBC/PKCS5Padding";
    
    byte[] sec_bytes = new byte[16];
    public SimetrAlgr() {
        SecureRandom random = new SecureRandom();
        random.nextBytes(sec_bytes);
    }
    
    
    public byte[] encrypt(SecretKeySpec secretKey, 
                           byte[] plainText) {
        try {
            cipher = Cipher.getInstance(transformation);
            if(transformation.contains("ECB")) {
            	cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            }
            else {
            	IvParameterSpec ivSpec;
                ivSpec = new IvParameterSpec(sec_bytes);
                cipher.init(Cipher.ENCRYPT_MODE, secretKey, 
                                                 ivSpec);
            }
          return Base64.getEncoder()
                       .encode(cipher.doFinal(plainText));
        } catch (Exception e) { 
        	System.out.println(e.getMessage());
        }
        return null;
    }    
    
    public byte[] decrypt(SecretKeySpec secretKey, 
                          byte[] encryptedText) {
        try {
            cipher = Cipher.getInstance(transformation);
            if(transformation.contains("ECB")) {
            	cipher.init(Cipher.DECRYPT_MODE, secretKey);
            }else {
            	IvParameterSpec ivSpec;
                ivSpec = new IvParameterSpec(sec_bytes);
                cipher.init(Cipher.DECRYPT_MODE, secretKey, 
                                                 ivSpec);
            }
            return cipher.doFinal(Base64.getDecoder()
                                  .decode(encryptedText));
        } catch (Exception e) {
        	System.out.println(e.getMessage());
        }
        return null;
    }
	
	public static SecretKeySpec createSecretKey()
	{
	    SecretKeySpec sks    = null;        
	    byte[]        bytes  = new byte[16];
	    SecureRandom  random = new SecureRandom();
	    random.nextBytes(bytes);
	    try {
	        MessageDigest md;
	        byte[]        key;
	        md = MessageDigest.getInstance("SHA-256");

	        key = md.digest(bytes);
	        key = Arrays.copyOf(bytes, 16);
	        writeKeyInfile(key);
	        sks = new SecretKeySpec(key, "AES");
	    } catch (NoSuchAlgorithmException e) { 
	    	System.out.println(e.getMessage());
	    }    	
	    return sks;
	}
	
	public static String textHash(String text) throws NoSuchAlgorithmException {
		
		MessageDigest hashFuncFortext = MessageDigest.getInstance("SHA-256");
        hashFuncFortext.update(text.getBytes());
        byte[] hashForStartText = hashFuncFortext.digest();
        StringBuffer sb = new StringBuffer();
        for (int j = 0; j < hashForStartText.length; j++) {
            String s = Integer.toHexString(0xff & hashForStartText[j]);
            s = (s.length() == 1) ? "0" + s : s;
            sb.append(s);
        }
        String str= sb.toString();
		return str;
		
	}
	
	public static void writeKeyInfile(byte[] key) {
		try(FileWriter writer = new FileWriter("E:\\Study2.0\\SSP\\keys.txt", true))
        {
			if (key != null) { 
				byte[] textb =Base64.getEncoder().encode(key);
			    String text = new String(textb);
	            writer.write(text);
	            writer.append('\n');
	            writer.flush();
			}
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        } 
	}
}

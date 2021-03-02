package data_acess_object_dao;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//Class used to produced encrypted passwords using the SHA512 algorithm
public class PasswordEncryption {
	
	/*
	 * Method that receives a string as argument and encrypts it using the SHA512 algorithm.
	 * Returns a 128 character string resulting from encrypting the passed string.
	 */
	public static String get_SHA_512_SecurePassword(String passwordToHash){
	   
		String passwordGerada = null;
		
		//algorithm requires a salt
		String salt = "Grupo2";
		
	    try {
	        MessageDigest md = MessageDigest.getInstance("SHA-512");
	        md.update(salt.getBytes(StandardCharsets.UTF_8));
	        byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
	        StringBuilder sb = new StringBuilder();
	        
	        for(int i=0; i< bytes.length ;i++){
	            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	        }
	        
	        passwordGerada = sb.toString();
	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    }
	    return passwordGerada;
	}
}

package com.capgemini.go.utility;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.capgemini.go.exception.AuthenticationException;

public class Authentication {

	/*******************************************************************************************************
	 * - Function Name : encrypt - Input Parameters : password,key Return Type
	 * :String Throws : AuthenticationException- Author : Agnibha Creation Date :
	 * 21/9/2019 - Description : to encrypt the password
	 ********************************************************************************************************/

	public static String encrypt(String strClearText, String strKey) throws AuthenticationException {
		String strData = "";

		try {

			SecretKeySpec skeyspec = new SecretKeySpec(strKey.getBytes(), "Blowfish");
			Cipher cipher = Cipher.getInstance("Blowfish");
			cipher.init(Cipher.ENCRYPT_MODE, skeyspec);
			byte[] encrypted = cipher.doFinal(strClearText.getBytes());
			strData = new String(encrypted);

		} catch (Exception e) {

			throw new AuthenticationException(e.getMessage());
		}
		return strData;
	}

	/*******************************************************************************************************
	 * - Function Name : decrypt - Input Parameters : password,key Return Type
	 * :String Throws : AuthenticationException- Author : Agnibha Creation Date :
	 * 21/9/2019 - Description : to Decrypt the password
	 ********************************************************************************************************/
	public static String decrypt(String strEncrypted, String strKey) throws AuthenticationException {
		String strData = "";

		try {

			SecretKeySpec skeyspec = new SecretKeySpec(strKey.getBytes(), "Blowfish");
			Cipher cipher = Cipher.getInstance("Blowfish");
			cipher.init(Cipher.DECRYPT_MODE, skeyspec);
			byte[] decrypted = cipher.doFinal(strEncrypted.getBytes());
			strData = new String(decrypted);

		} catch (Exception e) {

			throw new AuthenticationException(e.getMessage());
		}
		return strData;
	}

	
}

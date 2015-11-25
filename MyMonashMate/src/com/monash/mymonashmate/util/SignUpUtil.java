package com.monash.mymonashmate.util;

import java.util.regex.Pattern;

import android.text.Editable;

public class SignUpUtil {
	
	private static final String USERNAME_PATTERN = "^[a-z0-9_-]{4,15}$";
	private static final String PASSWORD_PATTERN = "^[a-z0-9_-]{4,15}$";
	
	public static boolean usernameIsValid(String username) {
		Pattern pattern = Pattern.compile(USERNAME_PATTERN);
		return pattern.matcher(username).matches();
	}
	
	public static boolean passwordIsValid(String password) {
		Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
		return pattern.matcher(password).matches();
	}
	
    public static boolean missionFields(Editable usernameText, Editable passwordText, Editable passwordConfirmText) {
    	if (usernameText.length() == 0 || passwordText.length() == 0 || passwordConfirmText.length() == 0 ) {
    		return true;
    	}
    	return false; 
    }
    
    public static boolean passwordNotMatch(String passwordString, String passwordConfirmString) {
    	if (!passwordString.equals(passwordConfirmString)) {
    		return true;
    	}
    	return false;
    }

}

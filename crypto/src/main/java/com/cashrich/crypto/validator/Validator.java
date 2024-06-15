package com.cashrich.crypto.validator;

public class Validator {

    public static final String EMAIL_PATTERN = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
    public static final String MOBILE_PATTERN = "^[6-9]{1}[0-9]{9}$";
    public static final String USERNAME = "^[a-zA-Z0-9]{4,15}$";
    public static final String PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$";

    public static Boolean isMobileValid(String mobile) {
        return mobile.matches(MOBILE_PATTERN);
    }
    public static Boolean isEmailValid(String email) {
        return email.matches(EMAIL_PATTERN);
    }
    public static Boolean isUsernameValid(String username) { return username.matches(USERNAME);}
    public static Boolean isPasswordValid(String password) { return password.matches(PASSWORD);}

}

package com.cristhian.gametobeat.util;

import android.text.TextUtils;
import android.util.Patterns;

public class LoginUtil {

    private static final int MIN_CHARACTERS_PASSWORD = 6;

    private LoginUtil() {}

    public static boolean isEmailValid(String email) {
        if (email != null &&
                Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return true;
        }
        return false;
    }

    public static boolean isPasswordValid(String password) {
        // Check if password is valid and length is bigger than 3 characters
        return password != null
                && !TextUtils.isEmpty(password)
                && password.length() >= MIN_CHARACTERS_PASSWORD;
    }

    public static boolean isPasswordMatch(String password, String passwordRepeat) {
        return password.equals(passwordRepeat);
    }
}

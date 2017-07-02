package com.whisper.whispme.helpers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by NightmareTK on 01/07/2017.
 */

public class InputValidator {

    InputValidator() {
    }


    public static boolean isBlank(String field) {
        return field.isEmpty();
    }


    public static boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidPassword(String password) {
        return password.length() >= 6;
    }

    public static boolean isValidUsername(String username) {
        // ken97, k97en or kenkina97k for example
        String USERNAME_PATTERN = "^[a-z][a-z0-9_-]{2,9}$";

        Pattern pattern = Pattern.compile(USERNAME_PATTERN);
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }
}

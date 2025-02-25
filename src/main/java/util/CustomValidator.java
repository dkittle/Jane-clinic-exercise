package util;

import java.util.regex.Pattern;

public class CustomValidator {

    public static boolean isValidPhoneNumber(String phoneNumber) {
        String pattern = "^\\d{3}-\\d{3}-\\d{4}$";
        return Pattern.compile(pattern).matcher(phoneNumber).matches();
    }

    public static boolean isValidEmail(String email) {
        String pattern = "^[^@]+@[^@]+\\.[^@]+$";
        return Pattern.compile(pattern).matcher(email).matches();
    }

}

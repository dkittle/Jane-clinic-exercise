package util;

import java.util.regex.Pattern;

public class CustomValidator {

    public static boolean isValidPhoneNumber(String phoneNumber) {
        String pattern = "^\\d{3}-\\d{3}-\\d{4}$";
        return Pattern.compile(pattern).matcher(phoneNumber).matches();
    }
}

package com.techisgood.carrentals.user;


import java.util.regex.Pattern;

public class UserNameValidator {
    // Email regex pattern
    public static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    // Phone number regex pattern
    public static final Pattern PHONE_PATTERN = Pattern.compile("^\\+\\d{1,15}$");

    /**
     * Checks if the given string is an email.
     *
     * @param input String to check
     * @return true if the string is an email, false otherwise
     */
    public static boolean isEmail(String input) {
        return EMAIL_PATTERN.matcher(input).matches();
    }

    /**
     * Checks if the given string is a phone number.
     *
     * @param input String to check
     * @return true if the string is a phone number, false otherwise
     */
    public static boolean isPhoneNumber(String input) {
        return PHONE_PATTERN.matcher(input).matches();
    }

    /**
     * Checks if the given string is either an email or a phone number.
     *
     * @param input String to check
     * @return true if the string is either an email or a phone number, false otherwise
     */
    public static boolean isEmailOrPhoneNumber(String input) {
        return isEmail(input) || isPhoneNumber(input);
    }
}

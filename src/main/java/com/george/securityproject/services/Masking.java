package com.george.securityproject.services;

import org.springframework.stereotype.Component;

@Component
public class Masking {

    /**
     * Masks the email address by replacing characters in the local part with '*' characters,
     * keeping the first and last character intact, and leaving the domain part unchanged.
     *
     * @param email The email address to mask
     * @return The masked email address, or null if email is null
     */
    public static String maskEmail(String email) {
        if (email == null) {
            return null;
        }

        int atIndex = email.indexOf('@');
        String localPart = email.substring(0, atIndex);
        String domainPart = email.substring(atIndex);

        // Create the masked email by keeping the first and last characters of the local part,
        // and replacing the rest with '*'.
        StringBuilder maskedLocalPart = new StringBuilder();
        maskedLocalPart.append(localPart.charAt(0)); // keep the first character
        for (int i = 1; i < localPart.length() - 1; i++) {
            maskedLocalPart.append('*'); // mask characters between first and last
        }
        maskedLocalPart.append(localPart.charAt(localPart.length() - 1)); // keep the last character

        return maskedLocalPart.toString() + domainPart;
    }
}

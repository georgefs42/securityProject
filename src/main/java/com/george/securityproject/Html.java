package com.george.securityproject;

import org.springframework.stereotype.Component;

@Component
public class Html {

    // Masks the email address for privacy
    public String maskEmail(String email) {
        if (email == null) {
            return null; // Return null if the email is null
        }
        int atIndex = email.indexOf('@'); // Find the index of '@' in the email
        if (atIndex > 0) {
            // Mask the part of the email before '@'
            String maskedPart = repeatMasking(atIndex);
            String domainPart = email.substring(atIndex); // Get the domain part of the email
            return maskedPart + domainPart; // Return the masked email
        } else {
            return email; // Return the email as is if '@' is not found
        }
    }

    // Helper method to create a string of asterisks (*) of given length
    private String repeatMasking(int count) {
        return new String(new char[count]).replace("\0", "*"); // Create a string of asterisks
    }
}

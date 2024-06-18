package com.george.securityproject.services;

import org.springframework.stereotype.Component;
import org.springframework.web.util.HtmlUtils;

@Component
public class Html {

    /**
     * Escapes HTML special characters in the input string.
     *
     * @param input The input string to escape
     * @return The escaped HTML-safe string, or null if input is null
     */
    public String escapeHtml(String input){
        if (input == null) {
            return null;
        }
        return HtmlUtils.htmlEscape(input);
    }

    /**
     * Placeholder method for email masking (currently returns the original email).
     * Implement email masking logic here if needed.
     *
     * @param email The email to mask
     * @return The masked email (currently returns the original email)
     */
    public String maskEmail(String email) {
        return email;
    }
}

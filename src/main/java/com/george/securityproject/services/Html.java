package com.george.securityproject.services;

import org.springframework.stereotype.Component;
import org.springframework.web.util.HtmlUtils;

@Component
public class Html {

    public String escapeHtml(String input){
        if (input == null) {
            return null;
        }
        return HtmlUtils.htmlEscape(input);
    }

    public String maskEmail(String email) {
        return email;
    }
}
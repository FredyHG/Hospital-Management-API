package dev.dracarys.com.hospitalquerysystem.util;

import org.springframework.stereotype.Component;

@Component
public class IsNumberCheck {
    public static boolean isNumber(String s) {
        if (s == null) {
            return false;
        }
        try {
            Long.parseLong(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}

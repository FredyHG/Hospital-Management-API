package dev.dracarys.com.hospitalquerysystem.util;

import org.springframework.stereotype.Component;

@Component
public class TitleCase {

    public static String convertToTitleCaseIteratingChars(String text) {
    if (text == null || text.isEmpty()) {
        return text;
    }

    StringBuilder converted = new StringBuilder();

    boolean convertNext = true;
    for (char ch : text.toCharArray()) {
        if (Character.isSpaceChar(ch)) {
            convertNext = true;
        } else if (convertNext) {
            ch = Character.toTitleCase(ch);
            convertNext = false;
        } else {
            ch = Character.toLowerCase(ch);
        }
        converted.append(ch);
    }

    return converted.toString();
}
}

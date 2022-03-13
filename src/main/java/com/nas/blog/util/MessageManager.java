package com.nas.blog.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public final class MessageManager {

    private static MessageSource messageSource;

    @Autowired
    public MessageManager(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public static String getMessage(String code, String... params) {
        return messageSource.getMessage(code, getMessages(params), getLocale());
    }

    public static String[] getMessages(String... codes) {

        if (codes == null) {
            return null;
        }

        String[] messages = new String[codes.length];
        for (int i = 0; i < codes.length; i++) {
            messages[i] = getMessage(codes[i]);
        }
        return messages;
    }

    public static String getMessage(String code) {
        return messageSource.getMessage(code, null, getLocale());
    }

    private static Locale getLocale() {
        return LocaleContextHolder.getLocale();
    }
}

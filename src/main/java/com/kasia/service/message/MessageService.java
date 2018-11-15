package com.kasia.service.message;

import com.kasia.service.Service;
import com.kasia.service.message.link.MessageLink;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageService implements Service {

    public String getMessage(MessageLink link) {
        return getMessage(Locale.getDefault(), link);
    }

    public String getMessage(Locale locale, MessageLink link) {
        Locale.setDefault(locale);
        ResourceBundle bundle = ResourceBundle.getBundle(link.getBundle().getName());
        return bundle.getString(link.getLink());
    }
}

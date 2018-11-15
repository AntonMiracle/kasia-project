package com.kasia.service.message.link;

import org.junit.Test;

import java.util.Locale;
import java.util.ResourceBundle;

public class RegistrationLinkTest {

    @Test
    public void allLinksValid() {
        for (RegistrationLink l : RegistrationLink.values()) {

            Locale.setDefault(Locale.CHINA);
            ResourceBundle.getBundle(l.getBundle().getName()).getString(l.getLink());

            Locale.setDefault(new Locale.Builder().setLanguage("pl").setRegion("PL").build());
            ResourceBundle.getBundle(l.getBundle().getName()).getString(l.getLink());

            Locale.setDefault(Locale.forLanguageTag("ru-RU"));
            ResourceBundle.getBundle(l.getBundle().getName()).getString(l.getLink());
        }
    }

}
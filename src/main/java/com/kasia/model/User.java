package com.kasia.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;

public class User implements Model {
    private String email;
    private String name;
    private String password;
    private ZoneId zoneId;
    private Locale locale;
    private LocalDateTime createOn;
}

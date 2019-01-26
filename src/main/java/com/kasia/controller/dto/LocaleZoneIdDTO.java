package com.kasia.controller.dto;

public class LocaleZoneIdDTO {
    private boolean updateZone;
    private boolean updateLocale;
    private String lang;
    private String country;
    private String zoneId;

    @Override
    public String toString() {
        return "LocaleZoneIdDTO{" +
                "updateZone=" + updateZone +
                ", updateLocale=" + updateLocale +
                ", lang='" + lang + '\'' +
                ", country='" + country + '\'' +
                ", zoneId='" + zoneId + '\'' +
                '}';
    }

    public boolean isUpdateZone() {
        return updateZone;
    }

    public void setUpdateZone(boolean updateZone) {
        this.updateZone = updateZone;
    }

    public boolean isUpdateLocale() {
        return updateLocale;
    }

    public void setUpdateLocale(boolean updateLocale) {
        this.updateLocale = updateLocale;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }
}

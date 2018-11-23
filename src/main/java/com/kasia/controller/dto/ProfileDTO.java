package com.kasia.controller.dto;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class ProfileDTO {
    private String password;
    private String confirm;
    private boolean isUpdateZoneId;
    private String zoneId;
    private int defaultBudgetId;

    public int getDefaultBudgetId() {
        return defaultBudgetId;
    }

    public void setDefaultBudgetId(int defaultBudgetId) {
        this.defaultBudgetId = defaultBudgetId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public boolean isUpdateZoneId() {
        return isUpdateZoneId;
    }

    public void setUpdateZoneId(boolean updateZoneId) {
        isUpdateZoneId = updateZoneId;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }
}

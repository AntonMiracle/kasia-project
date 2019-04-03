package com.kasia.controller.dto;

public class Settings {
    private long budgetIdForDelete;
    private String confirmPassword = "";
    private long ownerUserId;
    private String emailToConnect = "";
    private boolean hasOwnBudget;

    public boolean isHasOwnBudget() {
        return hasOwnBudget;
    }

    public void setHasOwnBudget(boolean hasOwnBudget) {
        this.hasOwnBudget = hasOwnBudget;
    }

    public long getBudgetIdForDelete() {
        return budgetIdForDelete;
    }

    public void setBudgetIdForDelete(long budgetIdForDelete) {
        this.budgetIdForDelete = budgetIdForDelete;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public long getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(long ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    public String getEmailToConnect() {
        return emailToConnect;
    }

    public void setEmailToConnect(String emailToConnect) {
        this.emailToConnect = emailToConnect;
    }

    @Override
    public String toString() {
        return "Settings{" +
                "budgetIdForDelete=" + budgetIdForDelete +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", ownerUserId=" + ownerUserId +
                ", emailToConnect='" + emailToConnect + '\'' +
                ", hasOwnBudget=" + hasOwnBudget +
                '}';
    }
}

package com.kasia.controller.dto;

public class Settings {
    private long budgetIdForDelete;
    private long budgetIdForConnect;
    private String confirmDelete = "";
    private String confirmConnect = "";
    private long ownerUserId;
    private String emailToConnect = "";
    private boolean hasOwnBudget;
    private boolean hasConnectBudget;
    private boolean hasConnectedUser;

    public boolean isHasConnectedUser() {
        return hasConnectedUser;
    }

    public void setHasConnectedUser(boolean hasConnectedUser) {
        this.hasConnectedUser = hasConnectedUser;
    }

    public boolean isHasConnectBudget() {
        return hasConnectBudget;
    }

    public void setHasConnectBudget(boolean hasConnectBudget) {
        this.hasConnectBudget = hasConnectBudget;
    }

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

    public String getConfirmDelete() {
        return confirmDelete;
    }

    public void setConfirmDelete(String confirmDelete) {
        this.confirmDelete = confirmDelete;
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

    public String getConfirmConnect() {
        return confirmConnect;
    }

    public void setConfirmConnect(String confirmConnect) {
        this.confirmConnect = confirmConnect;
    }

    public long getBudgetIdForConnect() {
        return budgetIdForConnect;
    }

    public void setBudgetIdForConnect(long budgetIdForConnect) {
        this.budgetIdForConnect = budgetIdForConnect;
    }

    @Override
    public String toString() {
        return "Settings{" +
                "budgetIdForDelete=" + budgetIdForDelete +
                ", budgetIdForConnect=" + budgetIdForConnect +
                ", confirmDelete='" + confirmDelete + '\'' +
                ", confirmConnect='" + confirmConnect + '\'' +
                ", ownerUserId=" + ownerUserId +
                ", emailToConnect='" + emailToConnect + '\'' +
                ", hasOwnBudget=" + hasOwnBudget +
                '}';
    }
}

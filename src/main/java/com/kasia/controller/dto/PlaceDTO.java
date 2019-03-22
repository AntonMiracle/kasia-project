package com.kasia.controller.dto;

import com.kasia.controller.dto.validator.constraint.DescriptionValid;
import com.kasia.controller.dto.validator.constraint.PlaceNameValid;

@PlaceNameValid(nameFN = "name", min = 4, max = 64, regex = "^\\S+[[ ]?\\S+]*$", message = "{validation.name.error}")
@DescriptionValid(stringFN = "description", max = 64, regex = "^\\S+[[ ]?\\S+]*$", message = "{validation.description.error}")
public class PlaceDTO {
    private String name = "";
    private String description = "";
    private long budgetId;
    private boolean canBeDeleted;
    private long userId;
    private String oldName = "";
    private String oldDescription = "";
    private long placeId;

    @Override
    public String toString() {
        return "PlaceDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", budgetId=" + budgetId +
                ", canBeDeleted=" + canBeDeleted +
                ", userId=" + userId +
                ", oldName='" + oldName + '\'' +
                ", oldDescription='" + oldDescription + '\'' +
                ", placeId=" + placeId +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(long budgetId) {
        this.budgetId = budgetId;
    }

    public boolean isCanBeDeleted() {
        return canBeDeleted;
    }

    public void setCanBeDeleted(boolean canBeDeleted) {
        this.canBeDeleted = canBeDeleted;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    public String getOldDescription() {
        return oldDescription;
    }

    public void setOldDescription(String oldDescription) {
        this.oldDescription = oldDescription;
    }

    public long getPlaceId() {
        return placeId;
    }

    public void setPlaceId(long placeId) {
        this.placeId = placeId;
    }
}

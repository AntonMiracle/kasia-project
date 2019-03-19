package com.kasia.controller.dto;

import com.kasia.controller.dto.validator.constraint.DescriptionValid;
import com.kasia.controller.dto.validator.constraint.ProviderNameValid;

@ProviderNameValid(nameFN = "name", min = 1, max = 64, regex = "^\\S+[[ ]?\\S+]*$", message = "{validation.name.error}")
@DescriptionValid(stringFN = "description", max = 64, regex = "^\\S+[[ ]?\\S+]*$", message = "{validation.description.error}")
public class ProviderDTO {
    private String name;
    private String description;
    private long budgetId;
    private String oldName;
    private boolean canBeDeleted;

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    @Override
    public String toString() {
        return "ProviderDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", budgetId=" + budgetId +
                ", oldName='" + oldName + '\'' +
                ", canBeDeleted=" + canBeDeleted +
                ", delete=" + delete +
                '}';
    }

    private boolean delete;

    public boolean isCanBeDeleted() {
        return canBeDeleted;
    }

    public void setCanBeDeleted(boolean canBeDeleted) {
        this.canBeDeleted = canBeDeleted;
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
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
}

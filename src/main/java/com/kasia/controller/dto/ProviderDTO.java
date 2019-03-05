package com.kasia.controller.dto;

import com.kasia.controller.dto.validator.constraint.ProviderNameValid;
import com.kasia.controller.dto.validator.constraint.DescriptionValid;

@ProviderNameValid(nameFN = "name", min = 1, max = 64, regex = "^\\S+[[ ]?\\S+]*$", message = "{validation.name.error}")
@DescriptionValid(stringFN = "description", regex = "^\\S+[[ ]?\\S+]*$", message = "{validation.description.error}")
public class ProviderDTO {
    private String name;
    private String description;
    private long budgetId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProviderDTO that = (ProviderDTO) o;

        if (budgetId != that.budgetId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return description != null ? description.equals(that.description) : that.description == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (int) (budgetId ^ (budgetId >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "ProviderDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", budgetId=" + budgetId +
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
}

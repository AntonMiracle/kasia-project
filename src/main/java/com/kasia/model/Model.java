package com.kasia.model;

import javax.validation.constraints.Min;
import java.io.Serializable;

public interface Model extends Serializable {
    long getId();


    void setId(@Min(0) long id);
}

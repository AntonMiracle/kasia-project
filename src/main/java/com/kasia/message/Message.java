package com.kasia.message;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.Locale;

public interface Message {

    String get();

    String get(Locale locale);

    default FacesMessage get(FacesContext facesContext) {
        return new FacesMessage(get(facesContext.getViewRoot().getLocale()));
    }

}

package com.kasia.controller;

import com.kasia.page.Attribute;
import com.kasia.message.Message;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

public interface Controller extends Serializable {

    default void addAttribute(Attribute param, Message message) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        request.setAttribute(param.getName(), message.get(context).getDetail());
    }
}

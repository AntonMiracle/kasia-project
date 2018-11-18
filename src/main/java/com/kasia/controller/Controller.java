package com.kasia.controller;

import com.kasia.controller.page.parameter.Parameter;
import com.kasia.message.Message;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

public interface Controller extends Serializable {

    default void addParam(Parameter param, Message message) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        request.setAttribute(param.get(), message.get(context).getDetail());
    }
}

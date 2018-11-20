package com.kasia.session;

import com.kasia.page.Attribute;
import com.kasia.page.Page;
import com.kasia.exception.NoSessionRuntimeException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SessionService {

    public HttpSession get(HttpServletRequest request) throws NoSessionRuntimeException {
        HttpSession session = request.getSession(false);
        if (session == null) throw new NoSessionRuntimeException();
        return session;
    }

    public HttpSession get(ServletRequest request) throws NoSessionRuntimeException {
        return get((HttpServletRequest) request);
    }

    public HttpSession get(FacesContext context) throws NoSessionRuntimeException {
        ExternalContext externalContext = context.getExternalContext();
        HttpSession session = ((HttpServletRequest) externalContext.getRequest()).getSession(false);
        if (session == null) throw new NoSessionRuntimeException();
        return session;
    }

    public HttpSession getNew(HttpServletRequest request) {
        return request.getSession(true);
    }

    public HttpSession getNew(ServletRequest request) {
        return getNew((HttpServletRequest) request);
    }

    public HttpSession getNew(FacesContext context) {
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        return session;
    }

    public void set(Attribute attribute, Object object, HttpSession session) throws NoSessionRuntimeException {
        if (session == null) throw new NoSessionRuntimeException();
        session.setAttribute(attribute.getName(), object);
    }

    public void set(Attribute attribute, Object object, HttpServletRequest request) throws NoSessionRuntimeException {
        get(request).setAttribute(attribute.getName(), object);
    }

    public void set(Attribute attribute, Object object, ServletRequest request) throws NoSessionRuntimeException {
        get(request).setAttribute(attribute.getName(), object);
    }

    public void set(Attribute attribute, Object obj, FacesContext context) throws NoSessionRuntimeException {
        HttpSession session = get(context);
        set(attribute, obj, session);
    }

    public Object get(Attribute attribute, HttpSession session) throws NoSessionRuntimeException {
        if (session == null) throw new NoSessionRuntimeException();
        return session.getAttribute(attribute.getName());
    }

    public Object get(Attribute attribute, HttpServletRequest request) throws NoSessionRuntimeException {
        return get(request).getAttribute(attribute.getName());
    }

    public Object get(Attribute attribute, ServletRequest request) throws NoSessionRuntimeException {
        return get(request).getAttribute(attribute.getName());
    }

    public Object get(Attribute attribute, FacesContext context) throws NoSessionRuntimeException {
        HttpSession session = get(context);
        return get(attribute, session);
    }

    public void redirectTo(Page page, HttpServletResponse response) throws IOException {
        response.sendRedirect(page.relativePath());
    }

    public void redirectTo(Page page, ServletResponse response) throws IOException {
        redirectTo(page, (HttpServletResponse) response);
    }

    public void redirectTo(String name, HttpServletResponse response) throws IOException {
        response.sendRedirect(name);
    }

    public void redirectTo(String name, ServletResponse response) throws IOException {
        redirectTo(name, (HttpServletResponse) response);
    }

    public void redirectTo(Page home, FacesContext facesContext) {
    }

    public void deleteSession(HttpSession session) {
        session.invalidate();
    }

    public void deleteSession(HttpServletRequest request) {
        get(request).invalidate();
    }

    public void deleteSession(ServletRequest request) {
        get(request).invalidate();
    }

    public void deleteSession(FacesContext context) {
        get(context).invalidate();
    }


}

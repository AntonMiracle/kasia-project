package com.kasia.security;

import com.kasia.exception.NoActiveUserRuntimeException;
import com.kasia.model.User;
import com.kasia.session.SessionService;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import static com.kasia.page.Attribute.SESSION_USER_ATTRIBUTE_NAME;

public class SecurityService {
    @Inject
    private SessionService sessionService;

    public User getActiveUser() throws NoActiveUserRuntimeException {
        Object obj = sessionService.get(SESSION_USER_ATTRIBUTE_NAME, getFacesContext());
        if (obj == null)
            throw new NoActiveUserRuntimeException();

        return (User) obj;
    }

    public void setActiveUser(User user) {
        HttpSession session = sessionService.get(getFacesContext());
        sessionService.set(SESSION_USER_ATTRIBUTE_NAME, user, session);
    }

    public boolean isActiveUserExist(HttpSession session) {
        if (session == null) return false;
        Object obj = sessionService.get(SESSION_USER_ATTRIBUTE_NAME, session);
        return obj != null;
    }

    public boolean isActiveUserHasRole(User.Role role) {
        return getActiveUser().getRoles().contains(role);
    }

    public void removeActiveUser() {
        sessionService.deleteSession(getFacesContext());
    }

    private FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }
}

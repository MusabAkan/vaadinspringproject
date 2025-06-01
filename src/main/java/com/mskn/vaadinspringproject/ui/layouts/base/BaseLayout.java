package com.mskn.vaadinspringproject.ui.layouts.base;

import com.vaadin.flow.component.dependency.CssImport;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.server.VaadinServletRequest;
import com.vaadin.flow.component.UI;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

@CssImport("./styles/shared-styles.css")
public abstract class BaseLayout extends AppLayout {

    public BaseLayout() {
        animation();
    }


    protected void animation() {
        addClassName("fade-in");
    }

    protected boolean isUserAuthenticated() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.isAuthenticated()
                && !(auth instanceof AnonymousAuthenticationToken)
                && !"anonymousUser".equals(auth.getPrincipal());
    }


    protected String getAuthenticatedUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (auth != null) ? auth.getName() : "";
    }


    protected void logoutAndNavigateToLogin() {
        var request = VaadinServletRequest.getCurrent().getHttpServletRequest();
        new SecurityContextLogoutHandler().logout(request, null, null);
        UI.getCurrent().navigate("login");
    }
}

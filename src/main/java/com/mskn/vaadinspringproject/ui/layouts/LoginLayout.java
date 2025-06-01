package com.mskn.vaadinspringproject.ui.layouts;

import com.mskn.vaadinspringproject.ui.layouts.base.BaseLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.RouterLayout;

public class LoginLayout extends BaseLayout implements RouterLayout, BeforeEnterObserver {

    public LoginLayout() {
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (isUserAuthenticated()) {
            event.forwardTo("dashboard");
        }
    }
}

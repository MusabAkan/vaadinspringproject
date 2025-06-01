package com.mskn.vaadinspringproject.ui.view.login;

import com.mskn.vaadinspringproject.ui.layouts.MainLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Route(value = "dashboard", layout = MainLayout.class)
@PageTitle("Ana Sayfa")
@RolesAllowed({"USER", "ADMIN"})
public class DashboardView extends VerticalLayout implements BeforeEnterObserver {

    public DashboardView() {
        setSizeFull();
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        Span welcome = new Span("🎉 Hoş Geldiniz!");
        welcome.getStyle().set("font-size", "30px").set("font-weight", "bold");

        Span info = new Span("📌 Soldaki menüden seçim yapabilirsiniz.");
        info.getStyle().set("color", "#888").set("font-size", "16px");

        add(welcome, info);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
            event.forwardTo("login");
        }
    }
}


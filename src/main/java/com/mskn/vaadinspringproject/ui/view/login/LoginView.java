package com.mskn.vaadinspringproject.ui.view.login;

import com.mskn.vaadinspringproject.ui.layouts.LoginLayout;
import com.mskn.vaadinspringproject.ui.utilities.helpers.NotificationHelper;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.util.Collections;

//todo:Dil seçeneği daha sonrasında eklenecek

@Route(value = "login", layout = LoginLayout.class)
@PageTitle("Giriş Yap | CRM")
@AnonymousAllowed
public class LoginView extends VerticalLayout implements AfterNavigationObserver {

    private final LoginForm loginForm = new LoginForm();

    public LoginView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        loginForm.setAction("login"); // Spring Security login URL
        loginForm.setForgotPasswordButtonVisible(true);
        loginForm.setI18n(createLanguageLoginI18n());

        loginForm.addForgotPasswordListener(e -> UI.getCurrent().navigate(ForgotPasswordView.class));

        Button registerButton = new Button("Hesabınız yok mu? Kayıt olun");
        registerButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        registerButton.addClickListener(e -> UI.getCurrent().navigate(RegisterView.class));

        add(loginForm, registerButton);
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        String error = event.getLocation().getQueryParameters()
                .getParameters()
                .getOrDefault("error", Collections.emptyList())
                .stream().findFirst().orElse(null);

        if (error != null) {
            loginForm.setError(true);
        }

        String expired = event.getLocation().getQueryParameters()
                .getParameters()
                .getOrDefault("expired", Collections.emptyList())
                .stream().findFirst().orElse(null);

        if (expired != null) {
            NotificationHelper.showWarning("Oturum süreniz doldu. Lütfen tekrar giriş yapın.");
        }
    }


    private LoginI18n createLanguageLoginI18n() {
        LoginI18n i18n = LoginI18n.createDefault();

        i18n.setHeader(new LoginI18n.Header());
        i18n.getHeader().setTitle("CRM Giriş");
        i18n.getHeader().setDescription("Lütfen giriş bilgilerinizi girin");

        i18n.getForm().setTitle("Üye Girişi");
        i18n.getForm().setUsername("Kullanıcı Adı");
        i18n.getForm().setPassword("Şifre");
        i18n.getForm().setSubmit("Giriş Yap");
        i18n.getForm().setForgotPassword("Şifremi unuttum");

        i18n.getErrorMessage().setTitle("Hatalı giriş");
        i18n.getErrorMessage().setMessage("Kullanıcı adı veya şifre hatalı. Lütfen tekrar deneyin.");
        i18n.getErrorMessage().setUsername("Kullanıcı Zorunlu");
        i18n.getErrorMessage().setPassword("Şifre Zorunlu");

        return i18n;
    }
}

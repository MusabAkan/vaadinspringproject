package com.mskn.vaadinspringproject.ui.view.login;

import com.mskn.vaadinspringproject.backend.entities.AppUser;
import com.mskn.vaadinspringproject.backend.services.IAppUserService;
import com.mskn.vaadinspringproject.backend.services.IPasswordResetService;
import com.mskn.vaadinspringproject.ui.layouts.LoginLayout;
import com.mskn.vaadinspringproject.ui.utilities.helpers.NotificationHelper;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "forgot-password", layout = LoginLayout.class)
@PageTitle("Şifremi Unuttum | CRM")
@AnonymousAllowed
public class ForgotPasswordView extends VerticalLayout {

    @Autowired
    private final IAppUserService userService;

    @Autowired
    private final IPasswordResetService passwordResetService;

    public ForgotPasswordView(IAppUserService userService, IPasswordResetService resetService) {
        this.userService = userService;
        this.passwordResetService = resetService;

        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        H1 title = new H1("Şifremi Unuttum");

        EmailField emailField = new EmailField("E-posta");
        emailField.setClearButtonVisible(true);
        emailField.setErrorMessage("Geçerli bir e-posta giriniz");

        Button sendButton = new Button("Sıfırlama Bağlantısı Gönder");
        Button backButton = new Button("Giriş Sayfasına Dön", e -> UI.getCurrent().navigate("login"));

        sendButton.addClickListener(e -> {
            String email = emailField.getValue();
            if (email == null || email.isEmpty()) {
                NotificationHelper.showWarning("Lütfen e-posta adresinizi girin.");
                return;
            }

            AppUser user = userService.findAll().stream()
                    .filter(u -> email.equalsIgnoreCase(u.getEmail()))
                    .findFirst()
                    .orElse(null);

            if (user != null) {
                passwordResetService.createResetToken(user);
            }

            NotificationHelper.showInfo("Eğer e-posta sistemde kayıtlıysa, sıfırlama bağlantısı gönderildi.");
        });

        VerticalLayout form = new VerticalLayout(title, emailField, sendButton, backButton);
        form.setWidth("400px");
        form.setAlignItems(Alignment.STRETCH);
        form.getStyle()
                .set("background-color", "#f5f5f5")
                .set("padding", "30px")
                .set("border-radius", "10px")
                .set("box-shadow", "0 4px 12px rgba(0,0,0,0.1)");

        add(form);
    }
}
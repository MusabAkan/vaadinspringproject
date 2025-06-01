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
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@Route(value = "reset-password", layout = LoginLayout.class)
@PageTitle("Şifre Sıfırlama | CRM")
@AnonymousAllowed
public class ResetPasswordView extends VerticalLayout implements BeforeEnterObserver {

    private final IPasswordResetService resetService;
    private final IAppUserService userService;
    private final PasswordEncoder passwordEncoder;

    private final PasswordField newPassword = new PasswordField("Yeni Şifre");
    private final PasswordField confirmPassword = new PasswordField("Şifreyi Onayla");
    private final Button resetButton = new Button("Şifreyi Güncelle");

    public ResetPasswordView(IPasswordResetService resetService,
                             IAppUserService userService,
                             PasswordEncoder passwordEncoder) {
        this.resetService = resetService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;

        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        H1 title = new H1("Yeni Şifre Belirle");

        VerticalLayout form = new VerticalLayout(title, newPassword, confirmPassword, resetButton);
        form.setWidth("400px");
        form.setAlignItems(Alignment.STRETCH);
        form.getStyle()
                .set("background-color", "#f5f5f5")
                .set("padding", "30px")
                .set("border-radius", "10px")
                .set("box-shadow", "0 4px 12px rgba(0,0,0,0.1)");

        add(form);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        String token = event.getLocation().getQueryParameters().getParameters().getOrDefault("token", List.of("")).get(0);

        if (token == null || token.isEmpty()) {
            NotificationHelper.showError("Geçersiz bağlantı.");
            resetButton.setEnabled(false);
            return;
        }

        Optional<AppUser> userOpt = resetService.validateToken(token);
        if (userOpt.isEmpty()) {
            NotificationHelper.showError("Token geçersiz veya süresi dolmuş.");
            resetButton.setEnabled(false);
            return;
        }

        AppUser user = userOpt.get();

        resetButton.addClickListener(e -> {
            if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
                NotificationHelper.showWarning("Lütfen tüm alanları doldurun.");
                return;
            }

            if (!newPassword.getValue().equals(confirmPassword.getValue())) {
                NotificationHelper.showError("Şifreler uyuşmuyor.");
                return;
            }

            user.setPassword(passwordEncoder.encode(newPassword.getValue()));
            userService.save(user);
            resetService.invalidateToken(token);

            NotificationHelper.showSuccess("Şifreniz başarıyla güncellendi.");
            UI.getCurrent().navigate("login");
        });
    }
}

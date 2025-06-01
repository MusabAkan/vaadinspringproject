package com.mskn.vaadinspringproject.ui.view.login;

import com.mskn.vaadinspringproject.backend.entities.AppUser;
import com.mskn.vaadinspringproject.backend.enums.Role;
import com.mskn.vaadinspringproject.backend.services.IAppUserService;
import com.mskn.vaadinspringproject.ui.layouts.LoginLayout;
import com.mskn.vaadinspringproject.ui.utilities.helpers.NotificationHelper;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.security.crypto.password.PasswordEncoder;

@Route(value = "register", layout = LoginLayout.class)
@PageTitle("Kayıt Ol | CRM")
@AnonymousAllowed
public class RegisterView extends VerticalLayout {

    public RegisterView(IAppUserService userService, PasswordEncoder passwordEncoder) {
        AppUser user = new AppUser();
        Binder<AppUser> binder = new Binder<>(AppUser.class);

        TextField username = new TextField("Kullanıcı Adı");
        EmailField email = new EmailField("E-posta");
        PasswordField password = new PasswordField("Şifre");

        Button registerButton = new Button("Kayıt Ol");
        Button backButton = new Button("Geri Dön", e -> UI.getCurrent().navigate("login"));
        Label message = new Label();

        FormLayout formLayout = new FormLayout();
        formLayout.add(username, email);
        formLayout.setColspan(password, 2);
        formLayout.add(password);
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 2));
        formLayout.setWidthFull();


        HorizontalLayout buttons = new HorizontalLayout(registerButton, backButton);
        buttons.setJustifyContentMode(JustifyContentMode.CENTER);


        binder.forField(username)
                .asRequired("Kullanıcı adı zorunludur")
                .bind(AppUser::getUsername, AppUser::setUsername);

        binder.forField(email)
                .withValidator(new EmailValidator("Geçerli bir e-posta giriniz"))
                .bind(AppUser::getEmail, AppUser::setEmail);

        binder.forField(password)
                .asRequired("Şifre zorunludur")
                .bind(AppUser::getPassword, (u, pwd) -> u.setPassword(passwordEncoder.encode(pwd)));

        registerButton.addClickListener(e -> {
            try {
                binder.writeBean(user);

                if (userService.findByUsername(user.getUsername()) != null) {
                    NotificationHelper.showError("Bu kullanıcı adı zaten alınmış.");
                    return;
                }

                user.setRole(Role.USER);
                user.setActive(true);
                userService.save(user);

                NotificationHelper.showSuccess("Kayıt başarılı! Giriş sayfasına yönlendiriliyorsunuz...");
                UI.getCurrent().navigate("login");

            } catch (ValidationException ex) {
                message.setText("Lütfen tüm alanları doğru doldurun.");
            }
        });

        // İçerik container (gri kutu)
        VerticalLayout formContainer = new VerticalLayout(
                new H1("Kayıt Ol"),
                formLayout,
                buttons,
                message
        );
        formContainer.setAlignItems(Alignment.CENTER);
        formContainer.setWidth("100%");
        formContainer.setMaxWidth("500px");
        formContainer.getStyle()
                .set("background-color", "#f5f5f5")
                .set("padding", "30px")
                .set("border-radius", "10px")
                .set("box-shadow", "0 4px 12px rgba(0,0,0,0.1)");

        // Dış container (tam ortalama için)
        Div outer = new Div(formContainer);
        outer.getStyle()
                .set("display", "flex")
                .set("justify-content", "center")
                .set("align-items", "center")
                .set("height", "100vh")
                .set("width", "100vw");

        // Sayfa ayarları
        setSizeFull();
        setPadding(false);
        setMargin(false);
        add(outer);
    }
}




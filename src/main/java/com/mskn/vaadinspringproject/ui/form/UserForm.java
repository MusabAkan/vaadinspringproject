package com.mskn.vaadinspringproject.ui.form;

import com.mskn.vaadinspringproject.backend.entities.AppUser;
import com.mskn.vaadinspringproject.backend.enums.Role;
import com.mskn.vaadinspringproject.backend.services.IAppUserService;
import com.mskn.vaadinspringproject.ui.form.base.BaseForm;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.data.validator.StringLengthValidator;
import org.springframework.beans.factory.annotation.Autowired;

public class UserForm extends BaseForm<AppUser> {
    private final IAppUserService userService;

    private TextField username;
    private TextField email;
    private ComboBox<Role> role;
    private Checkbox active;

    @Autowired
    public UserForm(IAppUserService userService) {
        super(AppUser.class);
        this.userService = userService;
        fillFields();
    }

    @Override
    protected void buildFormLayout() {
        username = new TextField("Kullanıcı Adı");
        email = new TextField("E-posta");
        role = new ComboBox<>("Rol");
        role.setItems(Role.values());
        active = new Checkbox("Aktif");

        add(username, email, role, active);
    }

    @Override
    protected void validate() {
        binder.forField(username)
                .withValidator(new StringLengthValidator("En az 3 karakter olmalı", 3, null))
                .bind(AppUser::getUsername, AppUser::setUsername);

        binder.forField(email)
                .withValidator(new EmailValidator("Geçerli e-posta giriniz."))
                .bind(AppUser::getEmail, AppUser::setEmail);

        binder.forField(role)
                .bind(AppUser::getRole, AppUser::setRole);

        binder.forField(active)
                .bind(AppUser::getActive, AppUser::setActive);
    }

    @Override
    protected void saveEntity(AppUser user) {
        userService.save(user);
    }

    @Override
    protected void deleteEntity(AppUser user) {
        userService.delete(user);
    }

    @Override
    protected void fillFields() {
        role.setItems(Role.values());
    }

    public String getUsernameFilter() {
        return username.getValue();
    }

    public String getEmailFilter() {
        return email.getValue();
    }

    public Role getRoleFilter() {
        return role.getValue();
    }

    public Boolean getActiveFilter() {
        return active.getValue();
    }
}

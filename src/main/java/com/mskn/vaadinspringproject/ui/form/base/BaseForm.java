package com.mskn.vaadinspringproject.ui.form.base;

import com.mskn.vaadinspringproject.ui.utilities.helpers.NotificationHelper;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;

public abstract class BaseForm<T> extends FormLayout {
    protected Binder<T> binder;
    protected T entity;

    protected Button save;
    protected Button delete;
    protected Button cancel;

    public BaseForm(Class<T> clazz) {
        this.binder = new Binder<>(clazz);
        buildFormLayout();
        validate();
        buildButtonLayout();
    }

    protected abstract void buildFormLayout();

    protected abstract void validate();

    protected abstract void saveEntity(T entity);

    protected abstract void deleteEntity(T entity);

    protected abstract void fillFields();

    public void setEntity(T entity) {
        this.entity = entity;
        if (entity != null) {
            binder.readBean(entity);
            setVisible(true);
        } else {
            setVisible(false);
        }
    }

    private void buildButtonLayout() {
        save = new Button("Kaydet", new Icon(VaadinIcon.CHECK));
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        save.addClickListener(e -> save());

        delete = new Button("Sil", new Icon(VaadinIcon.TRASH));
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        delete.addClickListener(e -> delete());

        cancel = new Button("İptal", new Icon(VaadinIcon.CLOSE));
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        cancel.addClickListener(e -> setEntity(null));

        HorizontalLayout buttons = new HorizontalLayout(save, delete, cancel);
        buttons.setSpacing(true);
        add(buttons);
    }

    private void save() {
        try {
            binder.writeBean(entity);
            saveEntity(entity);
            NotificationHelper.showSuccess("Kaydedildi.");
            setEntity(null);
        } catch (ValidationException e) {
            NotificationHelper.showError("Hata: Kaydedilemedi.");
        }
    }

    private void delete() {
        if (entity != null) {
            deleteEntity(entity);
            NotificationHelper.showSuccess("Silindi.");
            setEntity(null);
        }
    }
}
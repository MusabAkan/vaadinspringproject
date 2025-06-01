package com.mskn.vaadinspringproject.ui.field;

import com.mskn.vaadinspringproject.backend.entities.base.BaseEntity;
import com.mskn.vaadinspringproject.backend.services.base.IBaseService;
import com.vaadin.flow.component.combobox.ComboBox;
import org.springframework.beans.factory.annotation.Autowired;

public class EntityComboField<T extends BaseEntity> extends ComboBox<T> {

    private final IBaseService<T> service;

    @Autowired
    public EntityComboField(IBaseService<T> service) {
        super();
        this.service = service;
        setItems(service.findAll());
        setItemLabelGenerator(Object::toString);
    }
}
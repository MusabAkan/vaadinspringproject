package com.mskn.vaadinspringproject.ui.field;

import com.vaadin.flow.component.combobox.ComboBox;

public class  EnumComboField<E extends Enum<E>> extends ComboBox<E> {

    public EnumComboField(Class<E> enumType) {
        super();
        setItems(enumType.getEnumConstants());

        setItemLabelGenerator(Enum::toString);
    }
}
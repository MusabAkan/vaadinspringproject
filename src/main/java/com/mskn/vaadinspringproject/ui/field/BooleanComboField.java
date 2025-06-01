package com.mskn.vaadinspringproject.ui.field;

import com.vaadin.flow.component.combobox.ComboBox;

public class BooleanComboField extends ComboBox<Boolean> {

    private String isTrue = "Evet";
    private String isFalse = "Hayır";

    public BooleanComboField() {
        super();
        setItems(true, false);
        setItemLabelGenerator(value -> {
            if (value == null) return "";
            return value ? isTrue : isFalse;
        });
        setClearButtonVisible(true);
    }

    public BooleanComboField(String isTrue, String isFalse) {
        this();
        this.isTrue = isTrue;
        this.isFalse = isFalse;
        setItemLabelGenerator(value -> {
            if (value == null) return "";
            return value ? this.isTrue : this.isFalse;
        });
    }
}

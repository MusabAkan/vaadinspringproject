package com.mskn.vaadinspringproject.ui.utilities.helpers;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class PaginationHelper extends HorizontalLayout {

    private final Button prevButton = new Button("◀");
    private final Button nextButton = new Button("▶");
    private final NumberField pageNumberField = new NumberField();
    private final Span totalPagesLabel = new Span();
    private final ComboBox<Integer> pageSizeComboBox = new ComboBox<>();
    private final Span infoLabel = new Span();

    private int currentPage = 0;
    private int totalPages = 1;
    private int pageSize = 10;
    private final Consumer<Integer> onPageChange;

    public PaginationHelper(Consumer<Integer> onPageChange) {
        this.onPageChange = onPageChange;

        setWidthFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        setSpacing(true);

        /**
         * Sayfa numarası alanı
         */
        pageNumberField.setMin(1);
        pageNumberField.setWidth("60px");
        pageNumberField.setValue((double) (currentPage + 1));
        pageNumberField.addKeyDownListener(Key.ENTER, event -> {
            Double val = pageNumberField.getValue();
            if (val != null) {
                int page = val.intValue() - 1;
                if (page >= 0 && page < totalPages && page != currentPage) {
                    currentPage = page;
                    update();
                    onPageChange.accept(currentPage);
                } else {
                    pageNumberField.setValue((double) (currentPage + 1));
                }
            }
        });

        /**
         * Sayfa boyutu seçimi
         */
        List<Integer> pageSizes = Arrays.asList(10, 20, 50, 100, 1000);
        pageSizeComboBox.setItems(pageSizes);
        pageSizeComboBox.setValue(pageSize);
        pageSizeComboBox.setWidth("80px");
        pageSizeComboBox.addValueChangeListener(event -> {
            Integer selectedSize = event.getValue();
            if (selectedSize != null) {
                pageSize = selectedSize;
                currentPage = 0;
                update();
                onPageChange.accept(currentPage);
            }
        });

        // Önceki / Sonraki butonları
        prevButton.addClickListener(e -> {
            if (currentPage > 0) {
                currentPage--;
                update();
                onPageChange.accept(currentPage);
            }
        });

        nextButton.addClickListener(e -> {
            if (currentPage < totalPages - 1) {
                currentPage++;
                update();
                onPageChange.accept(currentPage);
            }
        });

        // Ortalanmış içerik
        HorizontalLayout centerLayout = new HorizontalLayout(
                infoLabel,
                prevButton,
                pageNumberField,
                totalPagesLabel,
                nextButton,
                new Span(" Sayfa Boyutu:"),
                pageSizeComboBox
        );
        centerLayout.setAlignItems(Alignment.CENTER);
        centerLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        centerLayout.setSpacing(true);

        add(centerLayout);
        update();
    }

    private void update() {
        pageNumberField.setValue((double) (currentPage + 1));
        totalPagesLabel.setText("/ " + totalPages);
        prevButton.setVisible(currentPage > 0);
        nextButton.setVisible(currentPage < totalPages - 1);
    }

    public void setTotalItems(int totalItems) {
        this.totalPages = Math.max(1, (int) Math.ceil((double) totalItems / pageSize));
        if (currentPage >= totalPages) currentPage = totalPages - 1;
        infoLabel.setText(totalItems + " Kayıt Listelendi");
        update();
    }

    public int getOffset() {
        return currentPage * pageSize;
    }

    public int getLimit() {
        return pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }
}

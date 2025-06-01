package com.mskn.vaadinspringproject.ui.viewList.base;

import com.mskn.vaadinspringproject.ui.utilities.helpers.PaginationHelper;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import java.lang.reflect.InvocationTargetException;

public abstract class BaseListView<T> extends VerticalLayout {
    protected final Grid<T> grid;

    protected final Button searchButton;
    protected final Button addButton;
    protected final Button clearButton;

    protected final PaginationHelper paginationHelper;
    protected final HorizontalLayout filterBar = new HorizontalLayout();

    protected Example<T> exampleFilter;
    protected Binder<T> filterBinder;

    /**
     * Model sınıfından bir boş örnek oluşturup, Spring Data Example filtrelemek için hazırlar; Grid ve form bağlayıcıyı (Binder) model tipine göre başlatır ve arayüz elemanlarını (filtre bar, grid) yapılandırır.
     * @param clazz
     */
    public BaseListView(Class<T> clazz) {
        this.grid = new Grid<>(clazz);
        this.paginationHelper = new PaginationHelper(this::onPageChange);

        filterBinder = new Binder<>(clazz);

        searchButton = new Button("Ara");
        searchButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        searchButton.setIcon(new Icon(VaadinIcon.SEARCH));

        addButton = new Button("Ekle");
        addButton.setIcon(new Icon(VaadinIcon.PLUS));
        addButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

        clearButton = new Button("Temizle");
        clearButton.setIcon(new Icon(VaadinIcon.CLOSE));
        clearButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

        try {
            T probe = clazz.getDeclaredConstructor().newInstance();
            exampleFilter = Example.of(probe);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            e.printStackTrace();
            exampleFilter = null;
        }

        configureFilterBar();
        configureGrid();
    }

    public void init() {
        configureLayout();
        fillList();
    }

    private void configureFilterBar() {
        filterBar.setSpacing(true);
        filterBar.add(searchButton, addButton, clearButton);

        searchButton.addClickListener(e -> fillList());
        addButton.addClickListener(e -> addItem());
        clearButton.addClickListener(e -> clearFilters());
    }

    private void configureGrid() {
        grid.setSizeFull();
        configureGridColumns(grid);
        grid.asSingleSelect().addValueChangeListener(event -> onSelect(event.getValue()));
    }

    private void configureLayout() {
        setSizeFull();
        setSpacing(true);
        setPadding(true);

        Component filterContent = getFilterContent();
        Component content = getContent();

        HorizontalLayout paginationWrapper = new HorizontalLayout(paginationHelper);
        paginationWrapper.setWidthFull();
        paginationWrapper.setJustifyContentMode(JustifyContentMode.CENTER);

        add(filterContent, filterBar, content, paginationWrapper);
        expand(content);
    }

    /**
     * Filtre verilerini binder'dan alır ve exampleFilter'ı günceller.
     */
    public void bindFilter() {
        T probe = filterBinder.getBean();
        if (probe != null) {
            ExampleMatcher matcher = ExampleMatcher.matching()
                    .withIgnoreNullValues()
                    .withIgnoreCase()
                    .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

            this.exampleFilter = Example.of(probe, matcher);
        } else {
            this.exampleFilter = null;
        }
    }


    /**
     * Listeyi doldurur. Filtreleme işlemi yapılacaksa bindFilter çağrılır.
     */
    protected void fillList() {
        bindFilter();
        fillData();
    }

    /**
     * fillList içinde gerçek liste doldurma işlemi bu metotta yapılır.
     * Subclass'lar override eder.
     */
    protected abstract void fillData();

    protected abstract void configureGridColumns(Grid<T> grid);

    protected abstract void addItem();

    protected abstract void clearFilters();

    protected abstract void onSelect(T item);

    protected abstract Component getContent();

    protected void onPageChange(int page) {
        fillList();
    }

    protected Component getFilterContent() {
        return new VerticalLayout();
    }

    protected int getOffset() {
        return paginationHelper.getOffset();
    }

    protected int getLimit() {
        return paginationHelper.getLimit();
    }

    protected int getCurrentPage() {
        return paginationHelper.getCurrentPage();
    }

    protected int getPageSize() {
        return paginationHelper.getPageSize();
    }

    public void setTotalItems(int totalItems) {
        paginationHelper.setTotalItems(totalItems);
    }

    public Example<T> getExampleFilter() {
        return exampleFilter;
    }

    public void setExampleFilter(Example<T> exampleFilter) {
        this.exampleFilter = exampleFilter;
    }
}


package com.mskn.vaadinspringproject.ui.viewList;

import com.mskn.vaadinspringproject.backend.entities.AppUser;
import com.mskn.vaadinspringproject.backend.entities.Customer;
import com.mskn.vaadinspringproject.backend.enums.Role;
import com.mskn.vaadinspringproject.backend.services.IAppUserService;
import com.mskn.vaadinspringproject.backend.services.ICustomerService;
import com.mskn.vaadinspringproject.ui.field.BooleanComboField;
import com.mskn.vaadinspringproject.ui.form.CustomerForm;
import com.mskn.vaadinspringproject.ui.layouts.MainLayout;
import com.mskn.vaadinspringproject.ui.viewList.base.BaseListView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@Route(value = "customers", layout = MainLayout.class)
@PageTitle("Müşteriler | CRM")
@RolesAllowed({"ADMIN", "USER"})
public class CustomerListView extends BaseListView<Customer> {

    private final ICustomerService customerService;
    private final IAppUserService userService;

    private final CustomerForm form;
    private TextField nameFilter;
    private TextField emailFilter;
    private BooleanComboField activeFilter;

    private AppUser currentUser;

    @Autowired
    public CustomerListView(ICustomerService customerService, IAppUserService userService) {
        super(Customer.class);
        this.customerService = customerService;
        this.userService = userService;

        this.form = new CustomerForm(customerService, userService);

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        this.currentUser = userService.findByUsername(username);

        closeEditor();
        init();
    }

    @Override
    protected void configureGridColumns(Grid<Customer> grid) {
        grid.removeAllColumns();
        grid.addColumn(Customer::getName).setHeader("Ad Soyad").setAutoWidth(true);
        grid.addColumn(Customer::getEmail).setHeader("E-posta").setAutoWidth(true);
        grid.addColumn(Customer::getPhone).setHeader("Telefon").setAutoWidth(true);
        grid.addColumn(Customer::getActive).setHeader("Aktif").setAutoWidth(true);
    }

    @Override
    protected void fillData() {
        Pageable pageable = PageRequest.of(getCurrentPage(), getPageSize());


        if (!Role.ADMIN.equals(currentUser.getRole())) {
            //todo rol yetkisine göre düzenlersin
        }

        List<Customer> customers = customerService.findAll(getExampleFilter(), pageable);
        long totalCount = customerService.countAll(getExampleFilter());

        grid.setItems(customers);
        setTotalItems((int) totalCount);
    }

    @Override
    protected Component getFilterContent() {
        nameFilter = new TextField("Ad Soyad");
        nameFilter.setPlaceholder("İsme göre ara");
        filterBinder.forField(nameFilter).bind(Customer::getName, Customer::setName);

        emailFilter = new TextField("E-posta");
        emailFilter.setPlaceholder("E-posta'ya göre ara");
        filterBinder.forField(emailFilter).bind(Customer::getEmail, Customer::setEmail);

        activeFilter = new BooleanComboField();
        activeFilter.setLabel("Durum");
        activeFilter.setPlaceholder("Durum göre ara");
        filterBinder.forField(activeFilter).bind(Customer::getActive, Customer::setActive);

        filterBinder.setBean(new Customer());

        HorizontalLayout filters = new HorizontalLayout(nameFilter, emailFilter, activeFilter);
        filters.setSpacing(true);

        return filters;
    }

    @Override
    protected void addItem() {
        grid.asSingleSelect().clear();
        form.setCustomer(new Customer());
        form.setVisible(true);
    }

    @Override
    protected void clearFilters() {
        nameFilter.clear();
        emailFilter.clear();
        activeFilter.clear();
    }

    @Override
    protected void onSelect(Customer customer) {
        if (customer == null) {
            closeEditor();
        } else {
            form.setCustomer(customer);
            form.setVisible(true);
        }
    }

    @Override
    protected HorizontalLayout getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.setSizeFull();
        return content;
    }

    private void closeEditor() {
        form.setCustomer(null);
        form.setVisible(false);
    }
}
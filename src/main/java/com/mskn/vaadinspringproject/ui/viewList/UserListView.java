package com.mskn.vaadinspringproject.ui.viewList;

import com.mskn.vaadinspringproject.backend.entities.AppUser;
import com.mskn.vaadinspringproject.backend.enums.Role;
import com.mskn.vaadinspringproject.backend.services.IAppUserService;
import com.mskn.vaadinspringproject.ui.field.BooleanComboField;
import com.mskn.vaadinspringproject.ui.field.EnumComboField;
import com.mskn.vaadinspringproject.ui.form.UserForm;
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

import java.util.List;


@Route(value = "users", layout = MainLayout.class)
@PageTitle("Kullanıcılar | CRM")
@RolesAllowed({"ADMIN"})
public class UserListView extends BaseListView<AppUser> {

    private final IAppUserService userService;
    private final UserForm form;

    private TextField usernameFilter;
    private TextField emailFilter;
    private EnumComboField<Role> roleFilter;
    private BooleanComboField activeFilter;

    @Autowired
    public UserListView(IAppUserService userService) {
        super(AppUser.class);
        this.userService = userService;
        this.form = new UserForm(userService);

        closeEditor();
        init();
    }

    @Override
    protected void configureGridColumns(Grid<AppUser> grid) {
        grid.removeAllColumns();
        grid.addColumn(AppUser::getUsername).setHeader("Kullanıcı Adı").setSortable(true);
        grid.addColumn(AppUser::getEmail).setHeader("E-posta").setSortable(true);
        grid.addColumn(AppUser::getRole).setHeader("Rol").setSortable(true);
        grid.addColumn(AppUser::getActive).setHeader("Aktif").setSortable(true);
    }

    @Override
    protected void fillData() {
        Pageable pageable = PageRequest.of(getCurrentPage(), getPageSize());

        List<AppUser> users = userService.findAll(getExampleFilter(), pageable);
        long totalCount = userService.countAll(getExampleFilter());

        grid.setItems(users);
        setTotalItems((int) totalCount);
    }

    @Override
    protected Component getFilterContent() {
        usernameFilter = new TextField();
        usernameFilter.setLabel("Kullanıcı Adı");
        usernameFilter.setPlaceholder("Kullanı adına göre ara");
        filterBinder.forField(usernameFilter)
                .bind(AppUser::getUsername, AppUser::setUsername);

        emailFilter = new TextField();
        emailFilter.setLabel("E-posta");
        emailFilter.setPlaceholder("E-posta göre ara");
        filterBinder.forField(emailFilter)
                .bind(AppUser::getEmail, AppUser::setEmail);

        roleFilter = new EnumComboField<>(Role.class);
        roleFilter.setLabel("Rol");
        roleFilter.setPlaceholder("Role göre ara");
        roleFilter.setItems(Role.values());
        filterBinder.forField(roleFilter)
                .bind(AppUser::getRole, AppUser::setRole);

        activeFilter = new BooleanComboField("Aktif", "Pasif");
        activeFilter.setLabel("Durum");
        activeFilter.setPlaceholder("Duruma göre ara");
        filterBinder.forField(activeFilter)
                .bind(AppUser::getActive, AppUser::setActive);

        filterBinder.setBean(new AppUser());

        HorizontalLayout filters = new HorizontalLayout(usernameFilter, emailFilter, roleFilter, activeFilter);
        filters.setSpacing(true);

        return filters;
    }

    @Override
    protected void clearFilters() {
        usernameFilter.clear();
        emailFilter.clear();
        roleFilter.clear();
        activeFilter.clear();
    }

    @Override
    protected void addItem() {
        grid.asSingleSelect().clear();
        form.setEntity(new AppUser());
        form.setVisible(true);
    }

    @Override
    protected void onSelect(AppUser user) {
        if (user == null) {
            closeEditor();
        } else {
            form.setEntity(user);
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
        form.setEntity(null);
        form.setVisible(false);
    }
}

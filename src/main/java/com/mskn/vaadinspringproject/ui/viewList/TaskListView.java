package com.mskn.vaadinspringproject.ui.viewList;

import com.mskn.vaadinspringproject.backend.entities.AppUser;
import com.mskn.vaadinspringproject.backend.entities.Customer;
import com.mskn.vaadinspringproject.backend.entities.Task;
import com.mskn.vaadinspringproject.backend.enums.TaskStatus;
import com.mskn.vaadinspringproject.backend.services.IAppUserService;
import com.mskn.vaadinspringproject.backend.services.ICustomerService;
import com.mskn.vaadinspringproject.backend.services.ITaskService;
import com.mskn.vaadinspringproject.ui.field.EntityComboField;
import com.mskn.vaadinspringproject.ui.field.EnumComboField;
import com.mskn.vaadinspringproject.ui.form.TaskForm;
import com.mskn.vaadinspringproject.ui.layouts.MainLayout;
import com.mskn.vaadinspringproject.ui.viewList.base.BaseListView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Route(value = "tasks", layout = MainLayout.class)
@PageTitle("Görevler | CRM")
@RolesAllowed({"ADMIN", "USER"})
public class TaskListView extends BaseListView<Task> {

    private final ITaskService taskService;
    private final IAppUserService userService;
    private final ICustomerService customerService;

    private final TaskForm form;

    private EnumComboField<TaskStatus> statusFilter;
    private EntityComboField<Customer> customerFilter;
    private EntityComboField<AppUser> assignedUserFilter;

    @Autowired
    public TaskListView(ITaskService taskService, IAppUserService userService, ICustomerService customerService) {
        super(Task.class);
        this.taskService = taskService;
        this.userService = userService;
        this.customerService = customerService;

        this.form = new TaskForm(taskService, customerService, userService);

        closeEditor();
        init();
    }


    @Override
    protected void configureGridColumns(Grid<Task> grid) {
        grid.removeAllColumns();
        grid.addColumn(Task::getTitle).setHeader("Başlık");
        grid.addColumn(Task::getStatus).setHeader("Durum");
        grid.addColumn(task -> task.getCustomer().getName()).setHeader("Müşteri");
        grid.addColumn(task -> task.getAssignedUser().getUsername()).setHeader("Atanan");
        grid.addColumn(Task::getDueDate).setHeader("Son Tarih");
    }

    @Override
    protected void fillData() {
        Pageable pageable = PageRequest.of(getCurrentPage(), getPageSize());

        List<Task> taskList = taskService.findAll(getExampleFilter(), pageable);
        long totalCount = taskService.countAll(getExampleFilter());

        grid.setItems(taskList);
        setTotalItems((int) totalCount);
    }

    @Override
    protected Component getFilterContent() {

        statusFilter = new EnumComboField<>(TaskStatus.class);
        statusFilter.setLabel("Durum");
        statusFilter.setPlaceholder("Durum göre ara");
        filterBinder.forField(statusFilter).bind(Task::getStatus, Task::setStatus);

        customerFilter = new EntityComboField<>(customerService);
        customerFilter.setLabel("Müşteri");
        customerFilter.setPlaceholder("Müşteriye göre ara");
        filterBinder.forField(customerFilter).bind(Task::getCustomer, Task::setCustomer);

        assignedUserFilter = new EntityComboField<>(userService);
        assignedUserFilter.setLabel("Atanan");
        assignedUserFilter.setPlaceholder("Atanan göre ara");
        filterBinder.forField(assignedUserFilter).bind(Task::getAssignedUser, Task::setAssignedUser);

        filterBinder.setBean(new Task());

        HorizontalLayout filters = new HorizontalLayout(customerFilter, assignedUserFilter, statusFilter);
        filters.setSpacing(true);

        return filters;
    }


    @Override
    protected void addItem() {
        grid.asSingleSelect().clear();
        form.setTask(new Task());
        form.setVisible(true);
    }


    @Override
    protected void clearFilters() {
        statusFilter.clear();
        customerFilter.clear();
        assignedUserFilter.clear();
    }

    @Override
    protected void onSelect(Task task) {
        if (task == null) {
            closeEditor();
        } else {
            form.setTask(task);
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
        form.setTask(null);
        form.setVisible(false);
    }
}

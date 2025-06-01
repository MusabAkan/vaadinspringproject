package com.mskn.vaadinspringproject.ui.form;

import com.mskn.vaadinspringproject.backend.entities.AppUser;
import com.mskn.vaadinspringproject.backend.entities.Customer;
import com.mskn.vaadinspringproject.backend.entities.Task;
import com.mskn.vaadinspringproject.backend.enums.TaskStatus;
import com.mskn.vaadinspringproject.backend.services.IAppUserService;
import com.mskn.vaadinspringproject.backend.services.ICustomerService;
import com.mskn.vaadinspringproject.backend.services.ITaskService;
import com.mskn.vaadinspringproject.ui.form.base.BaseForm;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

public class TaskForm extends BaseForm<Task> {

    private final ITaskService taskService;

    private TextField title;
    private TextArea description;
    private DatePicker dueDate;
    private ComboBox<TaskStatus> status;
    private ComboBox<Customer> customer;
    private ComboBox<AppUser> assignedUser;

    public TaskForm(ITaskService taskService, ICustomerService customerService, IAppUserService userService) {
        super(Task.class);
        this.taskService = taskService;

        customer.setItems(customerService.findAll());
        customer.setItemLabelGenerator(Customer::getName);

        assignedUser.setItems(userService.findAll());
        assignedUser.setItemLabelGenerator(AppUser::getUsername);
    }

    @Override
    protected void buildFormLayout() {
        title = new TextField("Başlık");
        description = new TextArea("Açıklama");
        dueDate = new DatePicker("Son Tarih");

        status = new ComboBox<>("Durum");
        status.setItems(TaskStatus.values());

        customer = new ComboBox<>("Müşteri");
        assignedUser = new ComboBox<>("Atanan Kullanıcı");

        add(title, description, dueDate, status, customer, assignedUser);
    }

    @Override
    protected void validate() {
        binder.forField(title)
                .asRequired("Başlık boş olamaz.")
                .bind(Task::getTitle, Task::setTitle);

        // Diğer alanlar otomatik bağlanır
        binder.bindInstanceFields(this);
    }

    @Override
    protected void saveEntity(Task task) {
        taskService.save(task);
    }

    @Override
    protected void deleteEntity(Task task) {
        taskService.delete(task);
    }

    @Override
    protected void fillFields() {

    }

    public void setTask(Task task) {
        setEntity(task);
    }
}

package com.mskn.vaadinspringproject.ui.form;

import com.mskn.vaadinspringproject.backend.entities.AppUser;
import com.mskn.vaadinspringproject.backend.entities.Customer;
import com.mskn.vaadinspringproject.backend.services.IAppUserService;
import com.mskn.vaadinspringproject.backend.services.ICustomerService;
import com.mskn.vaadinspringproject.ui.form.base.BaseForm;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.data.validator.StringLengthValidator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CustomerForm extends BaseForm<Customer> {
    private final ICustomerService customerService;
    private final IAppUserService userService;

    private TextField name;
    private EmailField email;
    private TextField phone;
    private TextField address;
    private TextArea notes;
    private Checkbox active;
    private MultiSelectComboBox<AppUser> userSelector;

    @Autowired
    public CustomerForm(ICustomerService customerService, IAppUserService userService) {
        super(Customer.class);

        this.customerService = customerService;
        this.userService = userService;

        fillFields();
    }

    @Override
    protected void buildFormLayout() {
        name = new TextField("Ad Soyad");
        email = new EmailField("E-posta");
        phone = new TextField("Telefon");
        address = new TextField("Adres");
        notes = new TextArea("Notlar");
        active = new Checkbox("Aktif");
        userSelector = new MultiSelectComboBox<>("Kullanıcılar");

        add(name, email, phone, address, notes, active, userSelector);
    }

    @Override
    protected void validate() {
        binder.forField(name)
                .asRequired("Ad Soyad alanı zorunludur.")
                .withValidator(new StringLengthValidator("Ad Soyad en az 2 karakter olmalıdır.", 2, null))
                .bind(Customer::getName, Customer::setName);

        binder.forField(email)
                .asRequired("E-posta alanı zorunludur.")
                .withValidator(new EmailValidator("Geçerli bir e-posta adresi giriniz."))
                .bind(Customer::getEmail, Customer::setEmail);

        binder.forField(phone)
                .asRequired("Telefon alanı zorunludur.")
                .bind(Customer::getPhone, Customer::setPhone);

        binder.forField(address)
                .bind(Customer::getAddress, Customer::setAddress);

        binder.forField(notes)
                .bind(Customer::getNotes, Customer::setNotes);

        binder.forField(active)
                .bind(Customer::getActive, Customer::setActive);

        binder.forField(userSelector)
                .asRequired("En az bir kullanıcı seçilmelidir.")
                .withValidator(users -> users != null && !users.isEmpty(), "Kullanıcı seçimi zorunludur.")
                .bind(Customer::getOwners, Customer::setOwners);
    }

    @Override
    protected void saveEntity(Customer customer) {
        customerService.save(customer);
    }

    @Override
    protected void deleteEntity(Customer customer) {
        customerService.delete(customer);
    }

    public void setCustomer(Customer customer) {
        setEntity(customer);
        if (customer != null) {
            userSelector.setValue(customer.getOwners());
        }
    }

    @Override
    public void fillFields() {
        List<AppUser> appUserList = userService.findAll();
        userSelector.setItems(appUserList);
        userSelector.setItemLabelGenerator(AppUser::getUsername);
    }
}

package com.mskn.vaadinspringproject.ui.view.login;

import com.mskn.vaadinspringproject.backend.entities.AppUser;
import com.mskn.vaadinspringproject.backend.entities.Customer;
import com.mskn.vaadinspringproject.backend.entities.Task;
import com.mskn.vaadinspringproject.backend.services.IAppUserService;
import com.mskn.vaadinspringproject.backend.services.ICustomerService;
import com.mskn.vaadinspringproject.backend.services.ITaskService;
import com.mskn.vaadinspringproject.ui.chart.ChartJsPieChart;
import com.mskn.vaadinspringproject.ui.layouts.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Route(value = "dashboard", layout = MainLayout.class)
@PageTitle("Ana Sayfa")
@RolesAllowed({"USER", "ADMIN"})
public class DashboardView extends VerticalLayout implements BeforeEnterObserver {
    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
            event.forwardTo("login");
        }
    }

    private final ICustomerService customerService;
    private final ITaskService taskService;

    private final IAppUserService appUserService;

    @Autowired
    public DashboardView(ICustomerService customerService,
                         ITaskService taskService,
                         IAppUserService appUserService) {
        this.customerService = customerService;
        this.taskService = taskService;
        this.appUserService = appUserService;

        setSizeFull();
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setSpacing(true);
        setPadding(true);

        HorizontalLayout topRow = new HorizontalLayout(createCustomerCountBox(), createWeeklyTaskListBox());
        HorizontalLayout bottomRow = new HorizontalLayout( createUpcomingTasksBox());
        HorizontalLayout thirdRow = new HorizontalLayout(createRecentCustomersBox());
        HorizontalLayout chartRow = new HorizontalLayout(createTaskStatusPieChartBox());

        bottomRow.setSpacing(true);
        topRow.setSpacing(true);
        thirdRow.setSpacing(true);
        chartRow.setSpacing(true);

        add(chartRow,topRow, bottomRow, thirdRow);
    }

    private Component createCustomerCountBox() {
        long totalCustomers = customerService.countAll();

        Icon icon = VaadinIcon.USER.create();
        icon.setColor("teal");
        icon.setSize("40px");

        H2 count = new H2(String.valueOf(totalCustomers));
        Span label = new Span("Toplam Müşteri");

        VerticalLayout layout = new VerticalLayout(icon, count, label);
        layout.setAlignItems(Alignment.CENTER);
        layout.setPadding(true);
        layout.getStyle()
                .set("border", "1px solid #e0e0e0")
                .set("border-radius", "10px")
                .set("box-shadow", "0 2px 5px rgba(0,0,0,0.1)")
                .set("width", "200px")
                .set("background-color", "#f9f9f9");

        return layout;
    }

    private Component createWeeklyTaskListBox() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser currentUser = appUserService.findByUsername(username);

        LocalDate today = LocalDate.now();
        LocalDate endOfWeek = today.plusDays(6);

        List<Task> tasks = taskService.findByAssignedUserAndDueDateBetween(currentUser, today, endOfWeek);

        VerticalLayout taskLayout = new VerticalLayout();
        taskLayout.setPadding(false);
        taskLayout.setSpacing(false);

        if (tasks.isEmpty()) {
            taskLayout.add(new Span("Bu hafta atanmış görev yok."));
        } else {
            for (Task task : tasks) {
                Span title = new Span("📌 " + task.getTitle() + " (" + task.getDueDate() + ")");
                title.getStyle().set("font-weight", "500");

                Span status = new Span("Durum: " + task.getStatus());
                status.getStyle().set("color", "#888");

                Div taskCard = new Div(title, status);
                taskCard.getStyle()
                        .set("padding", "10px")
                        .set("margin-bottom", "8px")
                        .set("border", "1px solid #ccc")
                        .set("border-radius", "6px")
                        .set("background-color", "#fefefe");

                taskLayout.add(taskCard);
            }
        }

        VerticalLayout wrapper = new VerticalLayout(new H3("🗓️ Bu Haftanın Görevleri"), taskLayout);
        wrapper.getStyle()
                .set("border", "1px solid #e0e0e0")
                .set("border-radius", "10px")
                .set("box-shadow", "0 2px 5px rgba(0,0,0,0.1)")
                .set("padding", "15px")
                .set("background-color", "#f9f9f9")
                .set("width", "400px");

        return wrapper;
    }

    private Component createUpcomingTasksBox() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser currentUser = appUserService.findByUsername(username);

        LocalDate today = LocalDate.now();
        LocalDate in3Days = today.plusDays(3);

        List<Task> tasks = taskService.findByAssignedUserAndDueDateBetween(currentUser, today, in3Days);

        VerticalLayout layout = new VerticalLayout();
        layout.setPadding(false);
        layout.setSpacing(false);

        if (tasks.isEmpty()) {
            layout.add(new Span("Önümüzdeki 3 gün içinde göreviniz yok."));
        } else {
            for (Task task : tasks) {
                String title = task.getTitle();
                String status = task.getStatus().toString();
                LocalDate due = task.getDueDate();

                Span taskTitle = new Span("📌 " + title + " → " + due);
                taskTitle.getStyle().set("font-weight", "bold");

                Span statusSpan = new Span("Durum: " + status);
                statusSpan.getStyle().set("color", "#888");

                Div box = new Div(taskTitle, statusSpan);
                box.getStyle()
                        .set("padding", "10px")
                        .set("margin-bottom", "8px")
                        .set("border", "1px solid #ccc")
                        .set("border-radius", "6px")
                        .set("background-color", "#fffbe6"); // sarımsı uyarı tonu

                layout.add(box);
            }
        }

        VerticalLayout wrapper = new VerticalLayout(new H3("⏰ Yaklaşan Görevler"), layout);
        wrapper.getStyle()
                .set("border", "1px solid #e0e0e0")
                .set("border-radius", "10px")
                .set("box-shadow", "0 2px 5px rgba(0,0,0,0.1)")
                .set("padding", "15px")
                .set("background-color", "#f9f9f9")
                .set("width", "400px");

        return wrapper;
    }

    private Component createRecentCustomersBox() {
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        List<Customer> recentCustomers = customerService.findByCreatedAtAfter(sevenDaysAgo);

        VerticalLayout layout = new VerticalLayout();
        layout.setPadding(false);
        layout.setSpacing(false);

        if (recentCustomers.isEmpty()) {
            layout.add(new Span("Son 7 günde müşteri eklenmedi."));
        } else {
            for (Customer customer : recentCustomers) {
                Span name = new Span("👤 " + customer.getName());
                Span email = new Span("📧 " + (customer.getEmail() != null ? customer.getEmail() : "-"));

                Div customerCard = new Div(name, email);
                customerCard.getStyle()
                        .set("padding", "10px")
                        .set("margin-bottom", "8px")
                        .set("border", "1px solid #ccc")
                        .set("border-radius", "6px")
                        .set("background-color", "#f9f9ff");

                layout.add(customerCard);
            }
        }

        VerticalLayout wrapper = new VerticalLayout(new H3("🆕 Son Eklenen Müşteriler"), layout);
        wrapper.getStyle()
                .set("border", "1px solid #e0e0e0")
                .set("border-radius", "10px")
                .set("box-shadow", "0 2px 5px rgba(0,0,0,0.1)")
                .set("padding", "15px")
                .set("background-color", "#f9f9f9")
                .set("width", "400px");

        return wrapper;
    }

    private Component createTaskStatusPieChartBox() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser currentUser = appUserService.findByUsername(username);
        List<Task> tasks = taskService.findByAssignedUser(currentUser);

        Map<String, Long> statusCount = tasks.stream()
                .collect(Collectors.groupingBy(t -> t.getStatus().toString(), Collectors.counting()));

        ChartJsPieChart chart = new ChartJsPieChart();
        chart.setChartData(statusCount);

        VerticalLayout layout = new VerticalLayout(new H3("📈 Görev Durum Grafiği"), chart);
        layout.setWidth("400px");
        layout.getStyle()
                .set("border", "1px solid #e0e0e0")
                .set("border-radius", "10px")
                .set("padding", "15px")
                .set("background-color", "#f9f9f9");

        return layout;
    }

}



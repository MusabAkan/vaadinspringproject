package com.mskn.vaadinspringproject.ui.layouts;

import com.mskn.vaadinspringproject.ui.layouts.base.BaseLayout;
import com.mskn.vaadinspringproject.ui.viewList.CustomerListView;
import com.mskn.vaadinspringproject.ui.view.login.DashboardView;
import com.mskn.vaadinspringproject.ui.viewList.TaskListView;
import com.mskn.vaadinspringproject.ui.viewList.UserListView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;

public class MainLayout extends BaseLayout {

    public MainLayout() {
        if (!isUserAuthenticated()) {
            UI.getCurrent().navigate("login");
            return;
        }

        setPrimarySection(Section.DRAWER);
        setDrawerOpened(true);

        createHeader();
        createDrawer();
    }

    private void createHeader() {
        Icon menuIcon = VaadinIcon.MENU.create();
        menuIcon.setSize("30px");
        menuIcon.getStyle().set("cursor", "pointer");
        menuIcon.addClickListener(e -> setDrawerOpened(!isDrawerOpened()));

        Icon logoIcon = VaadinIcon.USER_HEART.create();
        logoIcon.setColor("red");
        logoIcon.setSize("40px");

        H2 title = new H2("CRM Paneli");
        title.getStyle().set("margin", "0").set("color", "grey");

        HorizontalLayout titleSection = new HorizontalLayout(menuIcon, logoIcon, title);
        titleSection.setAlignItems(FlexComponent.Alignment.CENTER);
        titleSection.setSpacing(true);

        String username = getAuthenticatedUsername(); // BaseLayout fonksiyonu
        Span userNameSpan = new Span(username);

        MenuBar userMenu = new MenuBar();
        MenuItem userItem = userMenu.addItem(new HorizontalLayout(VaadinIcon.USER.create(), userNameSpan));
        SubMenu subMenu = userItem.getSubMenu();
        subMenu.addItem("Ayarlar", e -> UI.getCurrent().navigate("settings"));
        subMenu.addItem("Çıkış Yap", e -> logoutAndNavigateToLogin()); // BaseLayout fonksiyonu

        HorizontalLayout header = new HorizontalLayout(titleSection, userMenu);
        header.setWidthFull();
        header.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.setPadding(true);

        addToNavbar(header);
    }

    private void createDrawer() {
        VerticalLayout menu = new VerticalLayout();
        menu.setPadding(true);
        menu.setSpacing(true);

        menu.add(createNavLink("Ana Sayfa", VaadinIcon.HOME, DashboardView.class));
        menu.add(createNavLink("Kullanıcılar", VaadinIcon.USERS, UserListView.class));
        menu.add(createNavLink("Müşteriler", VaadinIcon.USER_CARD, CustomerListView.class));
        menu.add(createNavLink("Görevler", VaadinIcon.TASKS, TaskListView.class));

        addToDrawer(menu);
    }

    private Component createNavLink(String label, VaadinIcon iconType, Class<?> navigationTarget) {
        Icon icon = iconType.create();
        Span text = new Span(label);

        RouterLink link = new RouterLink((Class<? extends Component>) navigationTarget);
        link.add(text);
        link.setHighlightCondition((l, e) -> false);

        HorizontalLayout layout = new HorizontalLayout(icon, link);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.setSpacing(true);

        return layout;
    }
}



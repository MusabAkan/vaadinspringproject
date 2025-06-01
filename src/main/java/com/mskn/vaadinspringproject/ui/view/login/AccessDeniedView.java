package com.mskn.vaadinspringproject.ui.view.login;

import com.mskn.vaadinspringproject.ui.layouts.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.servlet.http.HttpServletResponse;

@Route(value = "access-denied", layout = MainLayout.class)
public class AccessDeniedView extends VerticalLayout implements HasErrorParameter<AccessDeniedException> {

    public AccessDeniedView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        getStyle().set("background-color", "#f5f5f5");

        // Orta boy görsel
        Image image = new Image("images/accessDenied.png", "Yetkisiz Erişim");
        image.setWidth("300px");
        image.setHeight("auto");
        image.getStyle().set("border-radius", "8px").set("box-shadow", "0 4px 8px rgba(0,0,0,0.1)");

        // Başlık ve açıklama
        H1 title = new H1("Yetkisiz Giriş!");
        title.getStyle().set("color", "#D32F2F");

        Paragraph description = new Paragraph("Bu sayfaya erişim izniniz bulunmamaktadır.");
        description.getStyle().set("font-size", "16px").set("color", "#333");

        Button backButton = new Button("Ana sayfaya dön", e -> getUI().ifPresent(ui -> ui.navigate("dashboard")));
        backButton.getStyle().set("margin-top", "20px");

        add(image, title, description, backButton);
    }

    @Override
    public int setErrorParameter(BeforeEnterEvent event, ErrorParameter<AccessDeniedException> parameter) {
        return HttpServletResponse.SC_FORBIDDEN;
    }
}

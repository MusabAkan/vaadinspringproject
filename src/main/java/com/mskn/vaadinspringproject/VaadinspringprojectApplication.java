package com.mskn.vaadinspringproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.component.page.AppShellConfigurator;

@Theme("my-theme")
@SpringBootApplication(scanBasePackages = "com.mskn")
public class VaadinspringprojectApplication implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(VaadinspringprojectApplication.class, args);
    }
}

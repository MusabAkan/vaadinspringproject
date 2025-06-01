package com.mskn.vaadinspringproject.ui.utilities.helpers;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import static com.vaadin.flow.component.notification.Notification.Position.TOP_STRETCH;

/**
 * NotificationHelper sınıfı, uygulama genelinde bildirimleri yönetmek için kullanılan bir yardımcı sınıftır.
 * Farklı türlerde (hata, başarı, uyarı, bilgi) bildirimler oluşturmak için kullanılabilir.
 */
public class NotificationHelper {
    public static final int DEFAULT_DURATION = 2000;

    public enum Type {
        ERROR(NotificationVariant.LUMO_ERROR),
        SUCCESS(NotificationVariant.LUMO_SUCCESS),
        WARNING(NotificationVariant.LUMO_CONTRAST),
        INFO(NotificationVariant.LUMO_PRIMARY);

        private final NotificationVariant variant;

        Type(NotificationVariant variant) {
            this.variant = variant;
        }

        public NotificationVariant getVariant() {
            return variant;
        }
    }

    public static void show(String message, Type type, int duration, Notification.Position position, Icon icon) {
        Notification notification = new Notification();
        notification.setDuration(duration);
        notification.setPosition(position);
        notification.addThemeVariants(type.getVariant());

        var layout = new HorizontalLayout(icon, new Span(message));
        notification.add(layout);

        notification.open();
    }

    public static void showError(String message) {
        show( message, Type.ERROR, DEFAULT_DURATION, TOP_STRETCH,VaadinIcon.BAN.create());
    }

    public static void showSuccess(String message) {
        show(message, Type.SUCCESS, DEFAULT_DURATION, TOP_STRETCH, VaadinIcon.CHECK_CIRCLE.create());
    }

    public static void showWarning(String message) {
        show(message, Type.WARNING, DEFAULT_DURATION, TOP_STRETCH, VaadinIcon.BELL.create());
    }

    public static void showInfo(String message) {
        show(message, Type.INFO, DEFAULT_DURATION, TOP_STRETCH, VaadinIcon.ELLIPSIS_CIRCLE_O.create());
    }
}
package com.mskn.vaadinspringproject.backend.security;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* com.mskn.vaadinspringproject.backend.services..*(..))")
    public void logBefore(JoinPoint joinPoint) {
        AppLogger.info(getClass(), "Başlıyor: " + joinPoint.getSignature().toShortString());
    }

    @AfterReturning(pointcut = "execution(* com.mskn.vaadinspringproject.backend.services..*(..))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        AppLogger.info(getClass(), "Tamamlandı: " + joinPoint.getSignature().toShortString() +
                " | Sonuç: " + (result != null ? result.toString() : "null"));
    }

    @AfterThrowing(pointcut = "execution(* com.mskn.vaadinspringproject.backend.services..*(..))", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        AppLogger.error(getClass(), "Hata: " + joinPoint.getSignature().toShortString(), ex);
    }
}

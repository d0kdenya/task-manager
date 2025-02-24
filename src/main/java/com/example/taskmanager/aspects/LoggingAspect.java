package com.example.taskmanager.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* com.example.taskmanager.controllers.*.*(..)) || execution(* com.example.taskmanager.services.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("Before: Выполняется метод: " + joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "execution(* com.example.taskmanager.controllers.*.*(..)) || execution(* com.example.taskmanager.services.*.*(..))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        System.out.println("AfterReturning: Метод " + joinPoint.getSignature().getName() + " вернул: " + result);
    }

    @AfterThrowing(pointcut = "execution(* com.example.taskmanager.controllers.*.*(..)) || execution(* com.example.taskmanager.services.*.*(..))", throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        System.out.println("AfterThrowing: Метод " + joinPoint.getSignature().getName() + " выбросил исключение: " + error.getMessage());
    }

    @Around("execution(* com.example.taskmanager.controllers.*.*(..)) || execution(* com.example.taskmanager.services.*.*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result;
        try {
            result = joinPoint.proceed();
        } finally {
            long timeTaken = System.currentTimeMillis() - startTime;
            System.out.println("Around: Метод " + joinPoint.getSignature().getName() + " выполнен за " + timeTaken + " мс");
        }
        return result;
    }
}
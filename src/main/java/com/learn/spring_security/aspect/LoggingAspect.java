package com.learn.spring_security.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);


    @Pointcut("execution(* com.learn.spring_security.app..*(..))")
    public void allMethodsWithinApplication() {}

    @Before("allMethodsWithinApplication()")
    public void logBeforeMethod(JoinPoint joinPoint) {
        logger.info("::: METHOD CALLED:: METHOD {} IS ABOUT TO BE CALLED WITH ARGUMENTS:: {} :::", joinPoint.getSignature(), joinPoint.getArgs());
    }

    @AfterReturning(pointcut = "allMethodsWithinApplication()", returning = "result")
    public void logAfterMethod(JoinPoint joinPoint, Object result) {
        logger.info("::: METHOD RETURNS:: METHOD {} HAS RETURNED WITH VALUE:: {} :::", joinPoint.getSignature(), result);
    }

    @AfterThrowing(pointcut = "allMethodsWithinApplication()", throwing = "ex")
    public void handleException(JoinPoint joinPoint, Exception ex) {
        logger.error("::: EXCEPTION:: METHOD: {} ENCOUNTERED EXCEPTION: {}",joinPoint.getSignature(), ex.getMessage());
    }
}

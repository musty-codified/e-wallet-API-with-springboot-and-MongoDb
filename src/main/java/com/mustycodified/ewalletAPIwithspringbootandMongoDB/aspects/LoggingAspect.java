package com.mustycodified.ewalletAPIwithspringbootandMongoDB.aspects;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution (* com.mustycodified.ewalletAPIwithspringbootandMongoDB.services.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        logger.info(joinPoint.getSignature().getName() + " method started. [" +joinPoint.getSignature().getDeclaringTypeName()+"]");
    }

    @AfterReturning("execution (* com.mustycodified.ewalletAPIwithspringbootandMongoDB.services.*.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        logger.info(joinPoint.getSignature().getName() + " method successfully executed. [" +joinPoint.getSignature().getDeclaringTypeName()+"]");
    }

    @AfterReturning("execution (* com.mustycodified.ewalletAPIwithspringbootandMongoDB.repositories.*.save(..))")
    public void logEntityCreation(JoinPoint joinPoint) {
        logger.info(joinPoint.getSignature().getName() + " method started. [" +joinPoint.getSignature().getDeclaringTypeName()+"]");
    }
}

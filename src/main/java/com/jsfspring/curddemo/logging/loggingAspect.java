/**
 * 
 */
/**
 * @author s727953
 *
 */
package com.jsfspring.curddemo.logging;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class loggingAspect {
	private static final Logger LOGGER = LoggerFactory.getLogger(loggingAspect.class);

	@Around("execution(* com.jsfspring.curddemo.*.* (**))")
	public void logAround(ProceedingJoinPoint joinPoint) throws Throwable {

		LOGGER.info("logAround() is running!");
		LOGGER.info("hijacked method : " + joinPoint.getSignature().getName());
		LOGGER.info("hijacked arguments : " + Arrays.toString(joinPoint.getArgs()));

		LOGGER.info("Around before is running!");
		joinPoint.proceed(); // continue on the intercepted method
		LOGGER.info("Around after is running!");

		LOGGER.info("******");

	}

}
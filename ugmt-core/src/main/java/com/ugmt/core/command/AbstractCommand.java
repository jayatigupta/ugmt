package com.ugmt.core.command;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * @author Puneet
 * 
 */
public abstract class AbstractCommand {

    protected static ApplicationContext appContext = new ClassPathXmlApplicationContext("applicationContext.xml");
}

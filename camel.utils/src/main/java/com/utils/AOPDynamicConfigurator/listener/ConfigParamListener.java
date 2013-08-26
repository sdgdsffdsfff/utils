package com.utils.AOPDynamicConfigurator.listener;

import java.util.ResourceBundle;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.utils.AOPDynamicConfigurator.facade.EnvConfigFacade;
/**
 * 
 * <pre>
 * DESC:监听器，如果需要在web项目中使用自定义环境变量配置，需要在web.xml中配置本监听器
 * @author camel.deng
 * @2013-2-18
 * </pre>
 */
public class ConfigParamListener implements ServletContextListener
{

    public ConfigParamListener()
    {
    }

    public void contextInitialized(ServletContextEvent event)
    {
    	ResourceBundle rb = ResourceBundle.getBundle("envConfigSelector");
    	String envConfigLocation = rb.getString("envConfigLocation");
    	if (envConfigLocation != null && envConfigLocation.startsWith("${")){
    		envConfigLocation = null;
    	}
    	EnvConfigFacade.initiation(envConfigLocation);
    }

    public void contextDestroyed(ServletContextEvent servletcontextevent)
    {
    }
}

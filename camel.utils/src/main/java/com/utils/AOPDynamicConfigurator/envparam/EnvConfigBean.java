package com.utils.AOPDynamicConfigurator.envparam;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * <pre>
 * DESC:环境参数bean，用于对应environmentConfig.xml内容。单例模式，通过getInstance方法获取对象实例
 * @author camel.deng
 * @2013-2-18
 * </pre>
 */
public class EnvConfigBean
{
    private String configType;
    private String aopConfig;
    private Map<String,ConfigDataSourceBean> jdbc = new HashMap<String,ConfigDataSourceBean>();
    private Map<String,ConfigDataSourceBean> jndi = new HashMap<String,ConfigDataSourceBean>();
    private Map<String,ConfigParamBean> configParam = new HashMap<String,ConfigParamBean>();
    
    /**
     * 获取单例
     * @return
     */
    public static EnvConfigBean getInstance()
    {
    	return EnvConfigContainer.instance;
    }
    
    private static class EnvConfigContainer
    {
        private static EnvConfigBean instance = new EnvConfigBean();
    }
    
    /**
     * 单例模式，外部对象不可使用new方式实例此对象
     */
    private EnvConfigBean(){
    	
    }

    
    public String getConfigType()
    {
        return configType;
    }

    public void setConfigType(String configType)
    {
        this.configType = configType;
    }

    public String getAopConfig()
    {
        return aopConfig;
    }

    public void setAopConfig(String aopConfig)
    {
        this.aopConfig = aopConfig;
    }

    public Map<String,ConfigDataSourceBean> getJdbc()
    {
        return jdbc;
    }

    public void setJdbc(Map<String,ConfigDataSourceBean> jdbc)
    {
        this.jdbc = jdbc;
    }

    public Map<String,ConfigDataSourceBean> getJndi()
    {
        return jndi;
    }

    public void setJndi(Map<String,ConfigDataSourceBean> jndi)
    {
        this.jndi = jndi;
    }

    public Map<String,ConfigParamBean> getConfigParam()
    {
        return configParam;
    }

    public void setConfigParam(Map<String,ConfigParamBean> configParam)
    {
        this.configParam = configParam;
    }
}

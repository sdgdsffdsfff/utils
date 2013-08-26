/**
 * Copyright (c) 2012, 4px or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.utils.AOPDynamicConfigurator.facade;

import com.utils.AOPDynamicConfigurator.envparam.ConfigParamDBReader;
import com.utils.AOPDynamicConfigurator.envparam.ConfigParamManager;
import com.utils.AOPDynamicConfigurator.javassistaop.EnvConfigAOPManager;

/**
 * <pre>
 * DESC:进入点，所有使用本工具的，调用此类的initiation方法
 * @author camel.deng
 * @2013-2-18
 * </pre>
 */
public class EnvConfigFacade {
	/**
	 * 处理默认位置的environmentConfig.xml
	 */
	public static void initiation(){
		process();
	}
	/**
	 * 处理指定位置的environmentConfig.xml
	 * @param envConfigLocation 指定environmentConfig.xml的相对路径。相对classes根路径
	 */
	public static void initiation(String envConfigLocation){
		ConfigParamManager.setEnvConfigLocation(envConfigLocation);
		process();
	}
	
	public static void setConfigParamTable(String tabelName){
		ConfigParamDBReader.setConfigParamTabel(tabelName);
	}
	
	private static void process(){
		/*
    	 * 调用configParamManager读取默认环境配置参数
    	 */
        ConfigParamManager cpm = new ConfigParamManager();
        cpm.readConfig();
        /*
         * 初始化拦截注入。采用javassist修改源码。
         */
        EnvConfigAOPManager.initAOP();
	}
}

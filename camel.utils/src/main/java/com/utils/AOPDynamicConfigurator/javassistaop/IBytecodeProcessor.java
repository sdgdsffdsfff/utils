package com.utils.AOPDynamicConfigurator.javassistaop;

/**
 * 
 * <pre>
 * DESC: 源码处理接口
 * @author camel.deng
 * @2013-2-17
 * </pre>
 */
public interface IBytecodeProcessor
{
	/**
	 * 织入代码
	 * @return
	 */
    public abstract Object weaveProcessor();

    public static final String spring3PlaceHolderClass = "org.springframework.beans.factory.config.PropertyPlaceholderConfigurer";
    public static final String log4jPlaceHolderClass = "org.apache.log4j.helpers.OptionConverter";
}

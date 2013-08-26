package com.utils.AOPDynamicConfigurator.javassistaop;

import com.utils.AOPDynamicConfigurator.envparam.EnvConfigBean;

/**
 * <pre>
 * DESC: 读取envConfigBean的AOP配置，如果没有则返回；如果有，则依据用户设置判断插入哪个占位符处理类
 * 	envConfigBean从environmentConfig.xml读取配置文件，所以要先初始化ConfigParamManager 的readConfgi()方法
 * @author camel.deng
 * @2013-2-2
 * </pre>
 */
public class EnvConfigAOPManager
{

    public static void initAOP()
    {
        EnvConfigBean envConfigBean = EnvConfigBean.getInstance();
        /*
         * 获取AopConfig配置
         */
        String aopConfig = envConfigBean.getAopConfig();
        if(null == aopConfig || aopConfig.trim().length() == 0){
            return;
        }
        /*
         * AopConfig设置以,分割需要初始化的占位符处理类
         */
        String supportes[] = aopConfig.split(",");
        for(int i = 0; i < supportes.length; i++)
        {
            String supported = supportes[i];
            IBytecodeProcessor bytecodeProcessor = BytecodeProcessor.getBytecodeProcessor(supported);
            bytecodeProcessor.weaveProcessor();
        }

    }
}

package com.utils.AOPDynamicConfigurator.javassistaop;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

import com.utils.AOPDynamicConfigurator.envparam.ConfigParamMap;
import com.utils.AOPDynamicConfigurator.javassistaop.BytecodeProcessor;

/**
 * 处理mybatis获取数据库配置文件的javassit类
 * 
 * @author yinfc
 * @date 2013年9月22日
 */
public class MybatisPlaceHolderProcessor extends BytecodeProcessor{

	@Override
	public Object weaveProcessor() {
		super.weaveProcessor();
		pool.importPackage("com.utils.AOPDynamicConfigurator.javassistaop.MybatisPlaceHolderProcessor");
		try {
			CtClass ctClass = pool.get("org.apache.ibatis.session.Configuration");
			CtMethod ctMethod = ctClass.getDeclaredMethod("setVariables");
			ctMethod.insertBefore("MybatisPlaceHolderProcessor.processPlaceholder($1);");
			ctClass.toClass();
		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (CannotCompileException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 替换属性文件的值
	 * @param p 属性文件
	 */
	public static void processPlaceholder(Properties p){
	    if (null == p){
	        return;
	    }
		Set<Entry<Object, Object>> set = p.entrySet();
		Iterator<Entry<Object, Object>> it = set.iterator();
		while(it.hasNext()){
			Entry<Object, Object> entry = it.next();
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			if(null == value){
				continue;
			}
			if(value.startsWith("{") && value.endsWith("}")){
				value = ConfigParamMap.getValue(key);
				if(null == value){
					System.out.println((new StringBuilder()).append("ERROR: MybatisPlaceHolderProcessor-- no property's value found in ConfigParamMap for ").append(key).append(", being sure this value exist in tabel t_configparam!").toString());
				}
				else{
					//重新设置属性文件的值
					entry.setValue(value);
				}
			}
		}
	}

}

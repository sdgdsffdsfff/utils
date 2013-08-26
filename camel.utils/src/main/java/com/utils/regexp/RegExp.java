/**
 * Copyright (c) 2012, 4px or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.utils.regexp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <pre>
 * DESC:
 * @author camel.deng
 * @2013-2-26
 * </pre>
 */
public class RegExp {
	
	public static void main(String[] args){
		String str = "$2nd .&wOrld";
		
		Pattern p = Pattern.compile("\\&(\\w+)");
		Matcher m = p.matcher(str);
		String value;
		while(m.find()){
			System.out.println(m.group(1));
			System.out.println(m.group());
			value = m.group(1);
			str = str.replace(value, "CHINA");
		}
		System.out.println(str);
		
	}
}

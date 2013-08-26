package com.utils.AOPTest;

import java.io.PrintStream;

public class JavassitAOPMethod
{

	public JavassitAOPMethod() {
	}

	public static void CallForAOPBefore(String local) {
		System.out
				.println((new StringBuilder())
						.append("insert befor save method by class method. and local = ")
						.append(local).toString());
	}
}

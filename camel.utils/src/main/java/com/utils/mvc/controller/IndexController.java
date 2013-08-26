/**
 * Copyright (c) 2012, 4px or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.utils.mvc.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 * <pre>
 * DESC:
 * @author camel.deng
 * @2013-2-18
 * </pre>
 */

@Controller
public class IndexController extends MultiActionController {
	@Autowired
	private BasicDataSource dataSource;
	private static long count;
	
	@RequestMapping(value="/showJdbc")
	public ModelAndView showJdbcConnection(HttpServletRequest request,Model model){
		JDBCConnection jdbcConnection = new JDBCConnection();
		jdbcConnection.setDriverClassName(dataSource.getDriverClassName());
		jdbcConnection.setUrl(dataSource.getUrl());
		jdbcConnection.setUsername(dataSource.getUsername());
		jdbcConnection.setPassword(dataSource.getPassword());

		model.addAttribute("conn", jdbcConnection);
		model.addAttribute("test", "it fine");
		count = count + 1;
		System.out.println("request count =" + count);
		return new ModelAndView("configParamShow","model", model);
	}
	
	@RequestMapping(value="/showJndi")
	public ModelAndView showJdbcndiConnection(HttpServletRequest request,Model model){
		JDBCConnection jdbcConnection = new JDBCConnection();
		jdbcConnection.setDriverClassName(dataSource.getDriverClassName());
		jdbcConnection.setUrl(dataSource.getUrl());
		jdbcConnection.setUsername(dataSource.getUsername());
		jdbcConnection.setPassword(dataSource.getPassword());

		model.addAttribute("conn", jdbcConnection);
		model.addAttribute("test", "it fine");
		count = count + 1;
		System.out.println("request count =" + count);
		return new ModelAndView("configParamShow","model", model);
	}
}

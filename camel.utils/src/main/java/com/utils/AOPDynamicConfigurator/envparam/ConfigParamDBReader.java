package com.utils.AOPDynamicConfigurator.envparam;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * 从db读取环境参数
 * <pre>
 * DESC:
 * @author camel.deng
 * @2013-2-23
 * </pre>
 */
public class ConfigParamDBReader implements IConfigParamReader {

	private static String CONFIG_PARAM_TABEL = "T_CONFIG_PARAM"; 
	
	public ConfigParamDBReader() {
	}

	public Map<String, ConfigParamBean> getAllConfigParam(EnvConfigBean envConfig) {
        Connection conn = null;
        Statement state = null;
        ResultSet rs = null;
        String system = null;
        if(envConfig.getConfigType().equalsIgnoreCase(ReaderEnum.JDBC.name()))
        {
            conn = getJDBCConnection(envConfig.getJdbc());
            system = ((ConfigDataSourceBean)envConfig.getJdbc().get("system")).getValue();
        } else
        {
            conn = getJNDIConnection(envConfig.getJndi());
            if(conn != null)
                system = ((ConfigDataSourceBean)envConfig.getJndi().get("system")).getValue();
        }
        if(conn != null)
            try
            {
                state = conn.createStatement();
                rs = state.executeQuery((new StringBuilder()).append("SELECT * FROM "+CONFIG_PARAM_TABEL+" WHERE ACTIVE='Y' AND SYSTEM='").append(system).append("'").toString());
                Map<String,ConfigParamBean> configParamMap = new HashMap<String,ConfigParamBean>();
                ConfigParamBean cpb;
                for(; rs.next(); configParamMap.put(cpb.getName(), cpb))
                {
                    cpb = new ConfigParamBean();
                    cpb.setId(Integer.valueOf(rs.getInt("id")));
                    cpb.setName(rs.getString("name"));
                    cpb.setValue(rs.getString("value"));
                    cpb.setRemark(rs.getString("remark"));
                    cpb.setActive(rs.getString("active"));
                    cpb.setSystem(rs.getString("system"));
                    if(ConfigParamManager.isDebugMode()){
                    	System.out.println(cpb.toString());
                    }
                }

                return configParamMap;
            }
            catch(SQLException e)
            {
            	System.out.println((new StringBuilder()).append("SQLException: select statement error! ").append(e).toString());
                e.printStackTrace();
            }
        return null;
    }

    /**
     * 获取jdbc连接
     * @param jdbcProperty
     * @return
     */
    private Connection getJDBCConnection(Map<String,ConfigDataSourceBean> jdbcProperty)
    {
        String url = (jdbcProperty.get("url")).getValue();
        String driverClassName = (jdbcProperty.get("driverClassName")).getValue();
        String username = (jdbcProperty.get("username")).getValue();
        String password = (jdbcProperty.get("password")).getValue();
        try
        {
            Class.forName(driverClassName);
            Connection conn = DriverManager.getConnection(url, username, password);
            if(conn != null)
                return conn;
        }
        catch(ClassNotFoundException e)
        {
        	System.out.println((new StringBuilder()).append("JDBC:").append(driverClassName).append("not found!").append(e).toString());
            e.printStackTrace();
        }
        catch(SQLException e)
        {
        	System.out.println((new StringBuilder()).append("JDBC: get Connection error![url:").append(url).append("]").append(e).toString());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取jndi连接
     * @param jndiProperty
     * @return
     */
    private Connection getJNDIConnection(Map<String,ConfigDataSourceBean> jndiProperty)
    {
    	Context initCtx;
		try {
			initCtx = new InitialContext();
			//Context envCtx = (Context) initCtx.lookup("java:comp/env");
			DataSource ds = (DataSource)initCtx.lookup(jndiProperty.get("resRefName").getValue());
			
			Connection conn = ds.getConnection();
			if (conn != null){
				return conn;
			}else{
				System.err.println("Error: connection from JNDI " +jndiProperty.get("resRefName").getValue() +" is null");
			}
		} catch (NamingException e) {
			System.err.println("Error: connection from JNDI get an error: NamingException " +e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("Error: connection from JNDI get an error: SQLException " +e.getMessage());
			e.printStackTrace();
		}
		return null;
    }

	/**
	 * 自定义设定环境配置表名
	 * @param configParamTabel the configParamTabel to set
	 */
	public static void setConfigParamTabel(String configParamTabel) {
		CONFIG_PARAM_TABEL = configParamTabel;
	}
}

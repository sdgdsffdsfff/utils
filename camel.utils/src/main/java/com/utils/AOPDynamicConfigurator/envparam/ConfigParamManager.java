package com.utils.AOPDynamicConfigurator.envparam;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * 
 * <pre>
 * DESC: 自定义环境配置参数处理类入口
 * @author camel.deng
 * @2013-2-18
 * </pre>
 */
public class ConfigParamManager
{
	/**
	 * 默认自定义环境配置参数位置在classes目录下的environmentConfig.xml
	 */
    private static final String defaultLocation = "environmentConfig.xml";
    /**
     * 可以自定义配置文件路径
     */
    private static String envConfigLocation;
    
    private static boolean debugMode = false;
    /**
     * 默认的配置读取类型
     */
    private String defConfigType = "XML";

    public ConfigParamManager()
    {
    }
    /**
     * 读取配置参数
     * @param configLocation，指定的环境配置文件的相对路径
     * @return
     */
    public EnvConfigBean readConfig(String configLocation)
    {
        if(null != configLocation && configLocation.length() > 0)
            setEnvConfigLocation(configLocation);
        return readConfig();
    }
    
    /**
     * 默认的读取配置参数方法
     * @return
     */
    public EnvConfigBean readConfig()
    {
        EnvConfigBean envConfig = EnvConfigBean.getInstance();
        try
        {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            java.io.InputStream is = ConfigParamManager.class.getClassLoader().getResourceAsStream(getEnvConfigLocation());
            Document document = documentBuilder.parse(is);
            envConfig = bind(envConfig, document);
            getAllConfigParam(envConfig);
        }
        catch(ParserConfigurationException e)
        {
            e.printStackTrace();
        }
        catch(SAXException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return envConfig;
    }

    /**
     * 绑定环境配置文件内容到EnvConfigBean对象
     * @param envConfig EnvConfigBean对象
     * @param document 环境配置文件Document，使用jdk默认自带xml处理器JAXP
     * @return EnvConfigBean对象
     */
    public EnvConfigBean bind(EnvConfigBean envConfig, Document document)
    {
    	setDebugMode(getDebugMode(document));
        String configType = getConfigType(document);
        envConfig.setConfigType(configType);
        String aopConfig = getAOPConfig(document);
        envConfig.setAopConfig(aopConfig);
        if(envConfig.getConfigType().equalsIgnoreCase(ReaderEnum.XML.name())){
            bindConfigParamFromXML(envConfig, document);
        }
        else if(envConfig.getConfigType().equalsIgnoreCase(ReaderEnum.JDBC.name())){
            bindJDBCConfig(envConfig, document);
        }
        else if(envConfig.getConfigType().equalsIgnoreCase(ReaderEnum.JNDI.name())){
            bindJNDIConfig(envConfig, document);
        }
        return envConfig;
    }
    
    /**
     * 获取debugMode，这个元素是必须的。true/false
     * @param document
     * @return true/false
     */
    private boolean getDebugMode(Document document)
    {
        NodeList debugModeNode = document.getElementsByTagName("debugMode");
        if(null != debugModeNode && debugModeNode.getLength() > 0)
        {
            NamedNodeMap attrs = debugModeNode.item(0).getAttributes();
            String val = attrs.getNamedItem("name").getNodeValue().trim();
            if ("true".equalsIgnoreCase(val)){
            	return true;
            }else{
            	return false;
            }
        } else{
        	System.err.println("no debugmode setted,default value is 'false'");
            return false;
        }
    }
    
    /**
     * 获取configType，这个元素是必须的。目前有三种：XML,JDBC,JNDI
     * @param document
     * @return
     */
    private String getConfigType(Document document)
    {
        NodeList configTypeNode = document.getElementsByTagName("configType");
        if(null != configTypeNode && configTypeNode.getLength() > 0)
        {
            NamedNodeMap attrs = configTypeNode.item(0).getAttributes();
            if("configType".equalsIgnoreCase(attrs.getNamedItem("name").getNodeValue())){
            	String configType = attrs.getNamedItem("value").getNodeValue();
//            	if (configType.trim().startsWith("${") && configType.trim().endsWith("}")){
//            		return defConfigType;
//            	}
                return configType;
            }else{
                return null;
            }
        } else
        {
            throw new EnvironmentConfigException("no configType node found");
        }
    }
    /**
     * 获取aop config，aopconfig指定需要拦截处理哪些框架或类的占位符处理
     * @param document
     * @return
     */
    private String getAOPConfig(Document document)
    {
        NodeList configNode = document.getElementsByTagName("aopConfig");
        if(null != configNode && configNode.getLength() > 0)
        {
            NamedNodeMap attrs = configNode.item(0).getAttributes();
            if("aop".equalsIgnoreCase(attrs.getNamedItem("name").getNodeValue()))
                return attrs.getNamedItem("value").getNodeValue();
        } else
        {
            System.out.println("no aop Config node found");
        }
        return null;
    }

    /**
     * 绑定xml环境参数到ConfigParamBean
     * @param envConfig
     * @param document
     */
    private void bindConfigParamFromXML(EnvConfigBean envConfig, Document document)
    {
        if(envConfig.getConfigType().equalsIgnoreCase(ReaderEnum.XML.name()))
        {
            NodeList xmlConfigParamNodes = document.getElementsByTagName("configParams");
            if(null != xmlConfigParamNodes && xmlConfigParamNodes.getLength() > 0)
            {
                Map<String,ConfigParamBean> configParamMap = new HashMap<String,ConfigParamBean>();
                Node node = xmlConfigParamNodes.item(0);
                NodeList xmlConfigParamProps = node.getChildNodes();
                int i = 0;
                do
                {
                    if(i >= xmlConfigParamProps.getLength())
                        break;
                    Node prop = xmlConfigParamProps.item(i);
                    short nodeType = prop.getNodeType();
                    switch(nodeType)
                    {
                    case Node.ELEMENT_NODE: // '\001'
                        NamedNodeMap propAttr = prop.getAttributes();
                        ConfigParamBean cpb = new ConfigParamBean();
                        cpb.setId(Integer.valueOf(null != propAttr.getNamedItem("id") ? propAttr.getNamedItem("id").getNodeValue() : "0"));
                        cpb.setName(null != propAttr.getNamedItem("name") ? propAttr.getNamedItem("name").getNodeValue() : "");
                        cpb.setValue(null != propAttr.getNamedItem("value") ? propAttr.getNamedItem("value").getNodeValue() : "");
                        cpb.setRemark(null != propAttr.getNamedItem("remark") ? propAttr.getNamedItem("remark").getNodeValue() : "");
                        cpb.setActive(null != propAttr.getNamedItem("active") ? propAttr.getNamedItem("active").getNodeValue() : "");
                        configParamMap.put(cpb.getName(), cpb);
                        break;
                    }
                    i++;
                } while(true);
                envConfig.setConfigParam(configParamMap);
            }
        }
    }
    /**
     * 绑定JDBC参数到ConfigDataSourceBean
     * @param envConfig
     * @param document
     */
    private void bindJDBCConfig(EnvConfigBean envConfig, Document document)
    {
        NodeList jdbcNodes = document.getElementsByTagName("jdbc");
        if(null != jdbcNodes && jdbcNodes.getLength() > 0)
        {
            Node node = jdbcNodes.item(0);
            NodeList jdbcProps = node.getChildNodes();
            Map<String,ConfigDataSourceBean> jdbcPropertyMap = new HashMap<String,ConfigDataSourceBean>();
            int i = 0;
            do
            {
                if(i >= jdbcProps.getLength())
                    break;
                Node prop = jdbcProps.item(i);
                short nodeType = prop.getNodeType();
                switch(nodeType)
                {
                case Node.ELEMENT_NODE: // '\001'
                    NamedNodeMap propAttr = prop.getAttributes();
                    ConfigDataSourceBean csb = new ConfigDataSourceBean();
                    csb.setName(null != propAttr.getNamedItem("name") ? propAttr.getNamedItem("name").getNodeValue() : "");
                    csb.setValue(null != propAttr.getNamedItem("value") ? propAttr.getNamedItem("value").getNodeValue() : "");
                    csb.setRemark(null != propAttr.getNamedItem("remark") ? propAttr.getNamedItem("remark").getNodeValue() : "");
                    jdbcPropertyMap.put(csb.getName(), csb);
                    break;
                }
                i++;
            } while(true);
            envConfig.setJdbc(jdbcPropertyMap);
        } else
        {
            throw new EnvironmentConfigException("no jdbc node found");
        }
    }
    /**
     * 绑定JNDI参数到ConfigDataSourceBean
     * @param envConfig
     * @param document
     */
    private void bindJNDIConfig(EnvConfigBean envConfig, Document document)
    {
        NodeList jndiNodes = document.getElementsByTagName("jndi");
        if(null != jndiNodes && jndiNodes.getLength() > 0)
        {
            Node node = jndiNodes.item(0);
            NodeList jndiProps = node.getChildNodes();
            Map<String,ConfigDataSourceBean> jndiPropertyMap = new HashMap<String,ConfigDataSourceBean>();
            int i = 0;
            do
            {
                if(i >= jndiProps.getLength())
                    break;
                Node prop = jndiProps.item(i);
                short nodeType = prop.getNodeType();
                switch(nodeType)
                {
                case Node.ELEMENT_NODE: // '\001'
                    NamedNodeMap propAttr = prop.getAttributes();
                    ConfigDataSourceBean csb = new ConfigDataSourceBean();
                    csb.setName(null != propAttr.getNamedItem("name") ? propAttr.getNamedItem("name").getNodeValue() : "");
                    csb.setValue(null != propAttr.getNamedItem("value") ? propAttr.getNamedItem("value").getNodeValue() : "");
                    csb.setRemark(null != propAttr.getNamedItem("remark") ? propAttr.getNamedItem("remark").getNodeValue() : "");
                    jndiPropertyMap.put(csb.getName(), csb);
                    break;
                }
                i++;
            } while(true);
            envConfig.setJndi(jndiPropertyMap);
        } else
        {
            throw new EnvironmentConfigException("no jndi node found");
        }
    }

    /**
     * 获取所有环境参数
     * @param envConfig
     * @return
     */
    public Map<String,ConfigParamBean> getAllConfigParam(EnvConfigBean envConfig)
    {
        IConfigParamReader configParamReader = ConfigParamReaderFactory.createConfigParamReader(envConfig.getConfigType());
        ConfigParamMap.setConfigParamMap(configParamReader.getAllConfigParam(envConfig));
        return ConfigParamMap.getConfigParamMap();
    }

    public static String getEnvConfigLocation()
    {
        if(null == envConfigLocation)
            return defaultLocation;
        else
            return envConfigLocation;
    }

    public static void setEnvConfigLocation(String configLocation)
    {
        if(null != configLocation)
        {
            URL url = ConfigParamManager.class.getClassLoader().getResource(configLocation);
            if(null != url)
                envConfigLocation = configLocation;
            else
                System.out.println((new StringBuilder()).append("Error: environment config location =").append(configLocation).append(" is not found,use deafault file environmentConfig.xml.").toString());
        }
    }
	/**
	 * @return the debugMode
	 */
	public static boolean isDebugMode() {
		return debugMode;
	}
	/**
	 * @param debugMode the debugMode to set
	 */
	public static void setDebugMode(boolean debugMode) {
		ConfigParamManager.debugMode = debugMode;
	}
}

package com.utils.AOPDynamicConfigurator.envparam;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.easymock.EasyMock;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.testng.log4testng.Logger;
import org.w3c.dom.Document;

import com.utils.AOPDynamicConfigurator.envparam.ConfigParamManager;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ConfigParamManager.class,Document.class})
public class ConfigParamManagerTest {
	private Logger logger = Logger.getLogger(ConfigParamManagerTest.class);
	private static String configLocation = "environmentConfig.xml";
	
	@BeforeClass
	public static void setUp(){
		//PropertyConfigurator.configure(ConfigParamManagerTest.class.getClassLoader().getResource("log4j.properties"));
	}
	
	@Test 
	public void readConfig_A$_XML() throws Exception {
		//建立私有方法的mock对象
		ConfigParamManager mock = PowerMock.createPartialMock(ConfigParamManager.class, "getConfigType");		
		//录制私有方法行为
		PowerMock.expectPrivate(mock, "getConfigType", EasyMock.anyObject(Document.class)).andReturn("XML");
		//回放mock对象行为
		PowerMock.replay(mock);

		EnvConfigBean envConfig = mock.readConfig();
		assertNotNull(envConfig);
		assertEquals("XML",envConfig.getConfigType());
		assertEquals("http://172.16.30.20:8090/xms/", ConfigParamMap.getConfigParamMap().get("xms_url").getValue());
		
		PowerMock.verifyAll();
	}
	
	@Test
	public void testReadConfig_JDBC() throws Exception {
		//建立私有方法的mock对象
		ConfigParamManager mock = PowerMock.createPartialMock(ConfigParamManager.class, "getConfigType");		
		//录制私有方法行为
		PowerMock.expectPrivate(mock, "getConfigType", EasyMock.anyObject(Document.class)).andReturn("JDBC");
		//回放mock对象行为
		PowerMock.replay(mock);
		
		EnvConfigBean envConfig = mock.readConfig(configLocation);
		
		assertNotNull(envConfig);
		assertEquals("JDBC",envConfig.getConfigType());
		assertTrue(envConfig.getJdbc().keySet().size()>0);
		for (ConfigDataSourceBean csb: envConfig.getJdbc().values()){
			System.out.println("jdbc config==>"+csb.getName()+" = "+csb.getValue());
		}
		for (ConfigParamBean cpb:ConfigParamMap.getConfigParamMap().values()){
			System.out.println("jdbc configParam==>"+cpb.getName()+" || "+cpb.getValue()+" || "+cpb.getSystem());
		}
	}
	
	@Test
	public void testReadConfig_JNDI() throws Exception {
		//建立私有方法的mock对象
		ConfigParamManager mock = PowerMock.createPartialMock(ConfigParamManager.class, "getConfigType");		
		//录制私有方法行为
		PowerMock.expectPrivate(mock, "getConfigType", EasyMock.anyObject(Document.class)).andReturn("JNDI");
		//回放mock对象行为
		PowerMock.replay(mock);
		
		EnvConfigBean envConfig = mock.readConfig(configLocation);
		
		assertNotNull(envConfig);
		assertEquals("JNDI",envConfig.getConfigType());
		assertEquals(0, envConfig.getJndi().keySet().size());
		for (ConfigDataSourceBean csb: envConfig.getJndi().values()){
			System.out.println("jndi config==>"+csb.getName()+" = "+csb.getValue());
		}
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	@Test(expected=EnvironmentConfigException.class)
	public void testReadConfig_JNDI_EnvironmentConfigException() throws Exception {
		//建立私有方法的mock对象
		ConfigParamManager mock = PowerMock.createPartialMock(ConfigParamManager.class, "getConfigType");		
		//录制私有方法行为
		PowerMock.expectPrivate(mock, "getConfigType", EasyMock.anyObject(Document.class)).andReturn("JNDI");
		
		//建立mock对象
		Document mockDocument = EasyMock.createMock(Document.class);
		//Document a = PowerMock.createMock(Document.class);
		//录制public方法行为
		EasyMock.expect(mockDocument.getElementsByTagName(EasyMock.anyObject(String.class))).andReturn(null);
		
		
		//回放mock对象行为
		PowerMock.replay(mock);
		//回放mock对象行为
		EasyMock.replay(mockDocument);

		EnvConfigBean envConfig = mock.readConfig(configLocation);
		
		PowerMock.verifyAll();
		EasyMock.verify(mockDocument);
	}
	
	@Test
	public void testSetEnvConfigLocation(){
		ConfigParamManager cpm = new ConfigParamManager();
		cpm.setEnvConfigLocation("abc");
		assertEquals("environmentConfig.xml", cpm.getEnvConfigLocation());
	}
	
	@Test
	public void testCustomerConfigLocation() throws Exception{
		//建立私有方法的mock对象
		ConfigParamManager mock = PowerMock.createPartialMock(ConfigParamManager.class, "getConfigType");		
		//录制私有方法行为
		PowerMock.expectPrivate(mock, "getConfigType", EasyMock.anyObject(Document.class)).andReturn("XML");
		//回放mock对象行为
		PowerMock.replay(mock);
		EnvConfigBean envConfig = mock.readConfig("test/testEnvironmentConfig.xml");
		
		assertEquals(3, envConfig.getConfigParam().entrySet().size());
		PowerMock.verifyAll();		
	}
}

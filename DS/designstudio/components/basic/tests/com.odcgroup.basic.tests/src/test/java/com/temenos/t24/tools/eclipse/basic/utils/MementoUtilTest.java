package com.temenos.t24.tools.eclipse.basic.utils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MementoUtilTest {
    
    private static ApplicationContext springContext;
    
    @BeforeClass
    public static void beforeClass() {
        try{
            String location = "spring/test.protocol.spring.xml";
            springContext = new ClassPathXmlApplicationContext(location);
        } catch(Throwable e){
            e.printStackTrace();
        }
    }

    @Before
    public void setUp() {
        MementoUtilFactory.setSpringContext(springContext);
    }
    
    @After
    public void tearDown() throws Exception{
        MementoUtilFactory.setSpringContext(null);
    }
    
    @Test
	public void testLoadProperties(){
        MementoUtil mu = MementoUtilFactory.getMementoUtil();
        mu.createProperty("t24.local.user", "test.user");
        Assert.assertTrue("test.user".equals(mu.getProperty("t24.local.user")));       
    }
    
}

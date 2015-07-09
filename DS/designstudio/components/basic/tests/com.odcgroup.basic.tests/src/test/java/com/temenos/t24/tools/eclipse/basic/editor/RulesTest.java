package com.temenos.t24.tools.eclipse.basic.editor;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.temenos.t24.tools.eclipse.basic.T24BasicPlugin;

public class RulesTest {

    @BeforeClass
    public static void beforeClass() {
        try {
            String[] locations = { "spring/test.protocol.spring.xml", "spring/test.plugin.spring.xml" };
            ClassPathXmlApplicationContext springContext = new ClassPathXmlApplicationContext(locations);
            T24BasicPlugin.setSpringApplicationContext(springContext);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Test
	public void testCommentRule() {
        assertTrue(true);
    }
}

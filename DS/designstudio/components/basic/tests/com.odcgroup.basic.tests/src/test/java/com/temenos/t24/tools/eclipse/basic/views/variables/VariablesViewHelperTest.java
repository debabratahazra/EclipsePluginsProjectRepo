package com.temenos.t24.tools.eclipse.basic.views.variables;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.temenos.t24.tools.eclipse.basic.T24BasicPlugin;

public class VariablesViewHelperTest {

    private static VariablesViewHelper variablesViewHelper = null;

    @Before
    public void setUp() throws Exception {
        String location = "spring/test.plugin.spring.xml";
        ClassPathXmlApplicationContext springApplicationContext = new ClassPathXmlApplicationContext(location);
        T24BasicPlugin.setSpringApplicationContext(springApplicationContext);
    }

    @Test
	public void testGetInstance() {
        variablesViewHelper = VariablesViewHelper.getInstance();
        assertTrue(variablesViewHelper != null);
    }

    @Test
	public void testIsKeyWord() {
        String word = "ABS";
        assertTrue(variablesViewHelper.isKeyword(word));
        word = " DELETE";
        assertFalse(variablesViewHelper.isKeyword(word));
        word = "DELETE ";
        assertFalse(variablesViewHelper.isKeyword(word));
        word = " DELETE ";
        assertFalse(variablesViewHelper.isKeyword(word));
    }

    @Test
	public void testIsSource() {
        String word = "SUBROUTINE";
        assertTrue(variablesViewHelper.isSource(word));
        word = "TEST";
        assertFalse(variablesViewHelper.isSource(word));
        word = "TEST ";
        assertFalse(variablesViewHelper.isSource(word));
        word = " TEST";
        assertFalse(variablesViewHelper.isSource(word));
    }
}

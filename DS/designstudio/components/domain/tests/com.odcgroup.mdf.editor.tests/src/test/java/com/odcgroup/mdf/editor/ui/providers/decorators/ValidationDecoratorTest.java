package com.odcgroup.mdf.editor.ui.providers.decorators;

import org.junit.Test;

import com.odcgroup.mdf.ecore.MdfFactory;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfPrimitive;


public class ValidationDecoratorTest {
	
	@Test
	public void testDecorationWithoutResource_DS4425() {
		MdfClass clazz = MdfFactory.eINSTANCE.createMdfClass();
		
		ValidationDecorator decorator = new ValidationDecorator();
		
		// this should NOT throw an NPE anymore
		decorator.decorate(clazz, null);
	}
	
	@Test
	public void testDecorationWithoutResource_DS4955() {
		MdfPrimitive clazz = MdfFactory.eINSTANCE.createMdfPrimitive();
		
		ValidationDecorator decorator = new ValidationDecorator();
		
		// this should NOT throw an NPE anymore
		decorator.decorate(clazz, null);
	}

}

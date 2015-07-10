package com.zealcore.se.ui.util;

import static org.junit.Assert.*;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.junit.Test;

public class PropertyNestedDescriptorTest {

    private static final String STRING_B = "stringB";
    private static final String STRING_A = "stringA";

    @Test
    public void testPropertyNestedDescriptor() {
        String a = STRING_A;
        String b = STRING_B;
        PropertyNestedDescriptor pnd = new PropertyNestedDescriptor(a, b);
        assertEquals(a, pnd.getPropertyValue(a));
    }

    @Test
    public void testGetEditableValue() {
        String a = STRING_A;
        String b = STRING_B;
        PropertyNestedDescriptor pnd = new PropertyNestedDescriptor(a, b);
        assertEquals(a, pnd.getPropertyValue(a));
    }

    @Test
    public void testGetPropertyDescriptors() {
        String a = STRING_A;
        String b = STRING_B;
        PropertyNestedDescriptor pnd = new PropertyNestedDescriptor(a, b);

        PropertyDescriptor pd1 = new PropertyDescriptor(a, b);
        PropertyDescriptor pd2 = new PropertyDescriptor(a + a, b + b);
        PropertyDescriptor pd3 = new PropertyDescriptor(a + b, b + a);

        pnd.addPropertyDescriptor(pd1);
        pnd.addPropertyDescriptor(pd2);
        pnd.addPropertyDescriptor(pd3);

        IPropertyDescriptor[] propertyDescriptors = pnd
                .getPropertyDescriptors();

        assertEquals(pd1, propertyDescriptors[0]);
        assertEquals(pd2, propertyDescriptors[1]);
        assertEquals(pd3, propertyDescriptors[2]);
        assertNotSame(pd1, propertyDescriptors[2]);
    }

    @Test
    public void testIsPropertySet() {
        String a = STRING_A;
        String b = STRING_B;
        PropertyNestedDescriptor pnd = new PropertyNestedDescriptor(a, b);
        assertTrue(pnd.isPropertySet(null));
        
        pnd = new PropertyNestedDescriptor(a);
        assertFalse(pnd.isPropertySet(null));
    }
}

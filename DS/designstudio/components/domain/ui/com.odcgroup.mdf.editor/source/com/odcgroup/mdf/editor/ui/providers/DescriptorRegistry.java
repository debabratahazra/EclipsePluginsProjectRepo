package com.odcgroup.mdf.editor.ui.providers;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.ui.views.properties.IPropertyDescriptor;


/**
 * @version 1.0
 * @author <a href="cvedovini@odyssey-group.com">Claude Vedovini</a>
 */
public class DescriptorRegistry {
    private static final Logger LOGGER = Logger.getLogger(DescriptorRegistry.class);
    private Map propertyDesc = new HashMap();

    /**
     * Constructor for DescriptorRegistry
     */
    public DescriptorRegistry() {
        super();
    }

    public void setProperty(Object bean, String pname, Object value) {
        try {
            PropertyDescriptor p = getDescriptor(bean.getClass(), pname);
            Method m = p.getWriteMethod();
            m.invoke(bean, new Object[] { value });
        } catch (Exception e) {
            LOGGER.error("Error occured while setting property " + pname +
                " for bean type " + bean.getClass(), e);
            throw new RuntimeException(e.getMessage());
        }
    }

    public Object getProperty(Object bean, String pname) {
        Object value = null;

        try {
            PropertyDescriptor p = getDescriptor(bean.getClass(), pname);

            if (p != null) {
                Method m = p.getReadMethod();
                value = m.invoke(bean, null);
            }
        } catch (Exception e) {
            LOGGER.error("Error occured while getting property " + pname +
                " for bean type " + bean.getClass(), e);
            throw new RuntimeException(e.getMessage());
        }

        return value;
    }

    public List getPropertyDescriptors(Class clazz) {
        List desc = new ArrayList();

        Iterator it = propertyDesc.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Class intrfce = (Class) entry.getKey();

            if (intrfce.isAssignableFrom(clazz)) {
                desc.addAll((List) entry.getValue());
            }
        }

        return desc;
    }

    public void add(Class clazz, IPropertyDescriptor desc) {
        List pds = (List) propertyDesc.get(clazz);

        if (pds == null) {
            pds = new ArrayList();
            propertyDesc.put(clazz, pds);
        }

        pds.add(desc);
    }

    protected synchronized PropertyDescriptor getDescriptor(Class clazz,
        String pname) throws IntrospectionException {
        pname = Introspector.decapitalize(pname);

        BeanInfo bi = Introspector.getBeanInfo(clazz);
        PropertyDescriptor[] pda = bi.getPropertyDescriptors();

        for (int i = 0; i < pda.length; i++) {
            if (pda[i].getName().equals(pname)) {
                return pda[i];
            }
        }

        return null;
    }
}

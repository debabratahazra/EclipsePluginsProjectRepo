package com.temenos.t24.tools.eclipse.basic.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.temenos.t24.tools.eclipse.basic.T24BasicPlugin;


public class MementoUtilFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(MementoUtilFactory.class);

    private static ApplicationContext springContext = null;

    public static synchronized MementoUtil getMementoUtil() {
        MementoUtil mu = null;
        try {
            if (springContext == null) {
                springContext = T24BasicPlugin.getSpringApplicationContext();
            }
            mu = (MementoUtil) springContext.getBean("mementoUtil");
        } catch (Exception e) {
            LOGGER.error("jBC Plug-In Spring configuration failed", e);
        }
        return mu;
    }
        
    /**
     * This is used by tests, only.
     */
    public static void setSpringContext(ApplicationContext springContext) {
        MementoUtilFactory.springContext = springContext;
    }
}

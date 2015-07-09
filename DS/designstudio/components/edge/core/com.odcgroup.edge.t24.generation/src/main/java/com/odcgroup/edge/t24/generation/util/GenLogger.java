package com.odcgroup.edge.t24.generation.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Proxy class for logging in order to track errors, so they can be actioned later.
 *
 * @author saleem.akbar
 *
 */

// TODO Move to Builder and also GenLoggerCallBack etc
// 
public class GenLogger implements InvocationHandler
{
    private static ICallBack        s_globalCallBack = null; 
    
    private final Object m_realLogger;

    private final Class<?> m_loggerClass;

    private GenLogger(Object p_realLogger, Class<?> p_loggerClass) 
    {
        m_realLogger = p_realLogger;
        m_loggerClass = p_loggerClass;
    }
    
    public static Logger getLogger(Class<?> p_class)
    {
        // Use GenLogger class so that we can simplify logger configuration.
        //
        // NB: The original logger class will be appended via the callback
        //
        Logger realLogger = LoggerFactory.getLogger(GenLogger.class); 
        
        
        Logger proxiedLogger = (Logger) Proxy.newProxyInstance  ( realLogger.getClass().getClassLoader(),
                                                                  new Class[] { Logger.class },
                                                                  new GenLogger( realLogger, p_class ) 
                                                                );
        
        return(proxiedLogger);
    }
    
    public static <CallBackType extends ICallBack> CallBackType setGlobalCallBack(CallBackType p_callBack)
    {
        s_globalCallBack = p_callBack;
        
        return p_callBack;
    }

    @Override
    public Object invoke(Object p_proxy, Method p_method, Object[] p_args) throws Throwable
    {
        if  ( s_globalCallBack != null )
        {
            try
            {
                s_globalCallBack.callBack( p_method.getName(), m_loggerClass, p_args );
            }
            catch (Exception p_ex)
            {
                // Can't do anything .. so just dump and run
                //
                p_ex.printStackTrace();
            }
        }
        
        return p_method.invoke(m_realLogger, p_args);
    }
    
    public interface ICallBack
    {
        public void callBack( String p_methodName, Class<?> p_loggerClass, Object[] p_args);
    }
}

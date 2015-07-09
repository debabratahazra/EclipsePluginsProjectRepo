package com.odcgroup.workbench.memoryanalyzer.agent;

import java.lang.instrument.Instrumentation;
import java.util.Properties;

public class VMAgent {

	
	public static final String KEY = "ds.instrumentation";
    
	public static void premain(String options, Instrumentation inst) {
        Properties props = System.getProperties();
        if(props.get(KEY) == null)
           props.put(KEY, inst);
    }

}

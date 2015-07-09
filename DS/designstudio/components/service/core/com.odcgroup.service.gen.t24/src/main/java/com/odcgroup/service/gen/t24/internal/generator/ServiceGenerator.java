package com.odcgroup.service.gen.t24.internal.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import com.odcgroup.service.gen.t24.internal.data.ServiceDescriptor;
import com.odcgroup.service.gen.t24.internal.exceptions.LoadTemplateException;

public abstract class ServiceGenerator {
	/**
	 * Generate output from a cutdown UML model represented 
	 * @param serviceDescriptor represents the cutdown UML model
	 * @param writer output will be written to this, or if null, file(s) and directories will be created.
	 */
	public abstract void generate(ServiceDescriptor serviceDescriptor, Writer writer, String path) throws LoadTemplateException;
	
  //create writer for the given file in the given directory
	protected Writer createWriter(String dirName, String fileName){
		Writer writer = null;
		try {		  
		  File dir = new File(dirName);
		  if(!dir.exists())
		  	dir.mkdirs();
		  
		  File file = new File(dir, fileName);
		  
			writer = new FileWriter(file);
		}catch(IOException e) {
		  e.printStackTrace();
		}
		
		return writer;
		
	}
}
package com.odcgroup.service.gen.t24.internal.generator;

import java.io.Writer;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

import com.odcgroup.service.gen.t24.internal.data.ServiceDescriptor;
import com.odcgroup.service.gen.t24.internal.exceptions.LoadTemplateException;

/**
 * Generates item(s) required to support the SOA framework 
 * @author dadkinson
 *
 */
public class FrameworkGenerator extends ServiceGenerator{
	public static enum FileType { BASIC, BASIC_INSERT, JAVA};
	public final static EnumMap<FileType,String> FileResource = new EnumMap<FileType,String>(FileType.class);
	private static final List<Resource> resources = new ArrayList<Resource>();
	private VelocityTemplateLoader loader;
	private Template template;
	private String templateName;
	private String fileName;
	private FileType fileType;
	private String fileExtension;

	static {
		FileResource.put(FileType.BASIC, ".b");
		FileResource.put(FileType.BASIC_INSERT, "");
		FileResource.put(FileType.JAVA, ".java");
	}

	private class Resource {
		private String filename;
		private String template;
		private FileType fileType;

		public Resource(String filename, String template, FileType fileType) {		
			this.filename = filename;		
			this.template = template;
			this.fileType = fileType;
		}
	}
	
	public FrameworkGenerator(VelocityTemplateLoader loader) {
			
		this.loader = loader; //store in member variable for (re-)use on a per-resource basis
		
		resources.add( new Resource("I_responseDetails","BasicResponseDetailsInsert.vm",FileType.BASIC_INSERT));
		resources.add( new Resource("I_response","BasicResponseInsert.vm",FileType.BASIC_INSERT));
//		resources.add( new Resource("Response","JavaResponse.vm",FileType.JAVA));
//		resources.add( new Resource("ResponseDetails","JavaResponseDetails.vm",FileType.JAVA));
		//...add any further Framework template/filename pairings to generate here

	}

	public void generate(ServiceDescriptor serviceDescriptor, Writer writer, String path) throws LoadTemplateException {
		for (Resource resource : resources) {
			fileName = resource.filename;
			templateName = resource.template;
			fileType = resource.fileType;
			fileExtension = FileResource.get(fileType);
			template = loader.loadTemplate(templateName);
			generateResource(writer, path);
		}
	}
	
	public void generateResource(Writer writer, String path) {
		VelocityContext ctx = new VelocityContext();
		try {
			if (writer == null) {		
				writer = createWriter(path, (fileName + fileExtension));
			}
			System.out.println("Merging Template - " + fileName + fileExtension + " template: " + templateName);
			template.merge(ctx, writer);
			System.out.println("Done");
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}		
	}
	
}

package com.odcgroup.iris.generator;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.eclipse.core.resources.IFile;

import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

/**
 * TODO: Document me!
 *
 * @author sjunejo
 *
 */
public abstract class AbstractIRISMetadataGenerator extends AbstractDSUnitTest {
	
	public void tearDown() throws Exception {
		deleteModelsProjects();
	}
	
	public IOfsModelResource getVersionResource(
			Collection<IOfsModelResource> resources) {
		for(IOfsModelResource resource : resources){
			if(resource.getName().contains(".version")){
				return resource;
			}
		}
		return null;
	}

	public IOfsModelResource getEnquiryResource(
			Collection<IOfsModelResource> resources) {
		for(IOfsModelResource resource : resources){
			if(resource.getName().contains(".enquiry")){
				return resource;
			}
		}
		return null;
	}

	public IOfsModelResource getDomainResourceByName(Collection<IOfsModelResource> resources, String domainName) {
		for(IOfsModelResource resource : resources){
			if(resource.getName().contains(domainName)){
				return resource;
			}
		}
		return null;
	}
	

	public Collection<IOfsModelResource> getDomainResources(Collection<IOfsModelResource> resources) {
		Collection<IOfsModelResource> orderedResources = new ArrayList<IOfsModelResource>();
		for(IOfsModelResource resource : resources){
			if(resource.getName().contains(".domain")){
				orderedResources.add(resource);
			}
		}
		return orderedResources;
	}

	public Collection<IOfsModelResource> getVersionResources(Collection<IOfsModelResource> resources) {
		Collection<IOfsModelResource> orderedResources = new ArrayList<IOfsModelResource>();
		for(IOfsModelResource resource : resources){
			if(resource.getName().contains(".version")){
				orderedResources.add(resource);
			}
		}
		return orderedResources;
	}

	public String fetchGenDocumentAsString(IFile xmlfile) throws Exception {	    
	    StringWriter writer = new StringWriter();
	    InputStream is = xmlfile.getContents();
		IOUtils.copy(is, writer);
		String expected = writer.toString();
		writer.close();
		is.close();
		return expected;
	}
	
	public Properties readPropertiesFile(IFile propfile) throws Exception{
		Properties prop = new Properties();
		FileInputStream is = new FileInputStream(propfile.getLocation().toOSString());
		prop.load(is);
		is.close();
		return prop;
	}
	
}

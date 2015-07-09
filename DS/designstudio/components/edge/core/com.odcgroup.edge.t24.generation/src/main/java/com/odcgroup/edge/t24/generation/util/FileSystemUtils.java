package com.odcgroup.edge.t24.generation.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.Properties;

import org.eclipse.core.internal.preferences.Base64;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

import com.acquire.util.AssertionUtils;
import com.google.inject.Inject;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.version.versionDSL.Version;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

/**
 * <code>FileSystemUtils</code> provides static file system related utility methods.
 *
 * @author Simon Hayes
 */
public class FileSystemUtils {
	public static final File[] EMPTY_FILE_ARRAY = new File[0];
	
	public static String streamToMD5(InputStream p_in) throws Exception
	{
		MessageDigest md = MessageDigest.getInstance("MD5");
		
		byte[] tmp = new byte[2048];
		
		int i;
		
		while((i = p_in.read(tmp)) == tmp.length)
		{
			md.update(tmp, 0, i);
		}
		
		md.update(tmp, 0, i);
		
		md.digest();
		
		return Base64.encode(md.digest()).toString();
	}
	
	public Resource getResourceFromObject(EObject p_obj)
	{
		return p_obj.eResource();
	}
	
	static long  getTimeStampFromMap(Properties p_lastGenerated, String p_key)
	{
		String value = p_lastGenerated.getProperty(p_key);
		
		if(value == null) return -1;
		
		return Long.parseLong(value);
	}
	
	static public String normaliseName(EObject p_object)
	{
		String key = p_object.eResource().getURI().lastSegment();
		
		return key;
	}
	
	static public String applicationNameFromEnquiryFileName(String p_enqFileName)
	{
		return "";
	}
	
	public static void storeLastGeneratedTimestamp(Properties p_lastGenerated, EObject p_object)
	{
		
		Resource resource = p_object.eResource();
		/*
		 * If the resource is null, this means this is a "in-memory" version. 
		 * Created on the flight based on a domain.
		 */
		if (resource != null){ 
			String key = normaliseName(p_object);
			
			long lastModifiedTimestamp = OfsResourceHelper.getFile(resource).getModificationStamp();
			
			p_lastGenerated.put(key, Long.toString(lastModifiedTimestamp));
		}
	}
	
	@Inject ResourceSet resourceSet;
	public static boolean hasVersionChanged(Properties p_lastGenerated, Version p_version ) throws Exception
	{
		long lastModifiedTimestamp = 0, lastGeneratedTimestamp = 0;
		
		// Deal with the Version ...
		
		Resource resource = ((EObject) p_version).eResource();
	
		if (resource == null) {
			/*
			 * Could well be an in memory generated version (no resource)
			 */
			return true;
		}
		
		lastModifiedTimestamp = OfsResourceHelper.getFile(resource).getModificationStamp();
		
		lastGeneratedTimestamp = getTimeStampFromMap(p_lastGenerated, normaliseName(p_version));
		
		if(lastModifiedTimestamp > lastGeneratedTimestamp) return true;
        
		// check the underlying Domain / Application ...
		
		resource = ((EObject) p_version.getForApplication().getParentDomain()).eResource();
		
		lastModifiedTimestamp = OfsResourceHelper.getFile(resource).getModificationStamp();
		
		lastGeneratedTimestamp = getTimeStampFromMap(p_lastGenerated, normaliseName((EObject)p_version.getForApplication().getParentDomain()));
		
		if(lastModifiedTimestamp > lastGeneratedTimestamp) return true;
		
		return false;
	}

	public static boolean hasEnquiryChanged(Properties p_lastGenerated, Enquiry p_enquiry ) throws Exception
	{
		long lastModifiedTimestamp = 0, lastGeneratedTimestamp = 0;
		
		Resource resource = ((EObject) p_enquiry).eResource();
		
		if (resource == null ) { 
			/*
			 * Could well be an in memory generated version (no resource)
			 */
			return true;
		}
	
		lastModifiedTimestamp = OfsResourceHelper.getFile(resource).getModificationStamp();
		
		lastGeneratedTimestamp = getTimeStampFromMap(p_lastGenerated, normaliseName(p_enquiry));
		
		if(lastModifiedTimestamp > lastGeneratedTimestamp) return true;
        
		// check the underlying Domain / Application ...
		
		String fileName = p_enquiry.getFileName();
		
		
		/*resource = ((EObject) p_version.getForApplication().getParentDomain()).eResource();
		
		lastModifiedTimestamp = OfsResourceHelper.getFile(resource).getModificationStamp();
		
		lastGeneratedTimestamp = getTimeStampFromMap(p_lastGenerated, resource.getURI().toString());
		
		if(lastModifiedTimestamp > lastGeneratedTimestamp) return true;
		*/
		return false;
	}
	
	public static boolean hasDomainChanged(Properties p_lastGenerated, MdfDomain p_domain) throws Exception
	{
		long lastModifiedTimestamp = 0, lastGeneratedTimestamp = 0;
		
		Resource resource = ((EObject) p_domain).eResource();

		if (resource == null) {
			/*
			 * Could well be an in memory generated version (no resource)
			 */
			return true;
		}
		
		lastModifiedTimestamp = OfsResourceHelper.getFile(resource).getModificationStamp();
		
		lastGeneratedTimestamp = getTimeStampFromMap(p_lastGenerated, normaliseName((EObject) p_domain));
		
		if(lastModifiedTimestamp > lastGeneratedTimestamp) return true;
       
		return false;
	}
	
	public static boolean hasResourceChanged(Properties p_lastGenerated, Resource p_resource) throws Exception
	{
		long lastModifiedTimestamp = 0, lastGeneratedTimestamp = 0;
		
		
		
		lastModifiedTimestamp = OfsResourceHelper.getFile(p_resource).getModificationStamp();
		
		lastGeneratedTimestamp = getTimeStampFromMap(p_lastGenerated, p_resource.getURI().toString());
		
		if(lastModifiedTimestamp > lastGeneratedTimestamp) return true;
       
		return false;
	}
	
	
	public static File findOrCreateMandatoryDirectory(File p_dir) throws Exception
	{
		AssertionUtils.requireNonNull(p_dir, "p_dir");
		
		if (p_dir.exists())
		{
			if (! p_dir.isDirectory())
				throw new Exception("Required directory path: " + p_dir.getPath() + " preexists as a file !");
		}
		
		else if (! p_dir.mkdirs())
		{
			throw new Exception("Required directory: " + p_dir.getPath() + " does not exist and could not be created");
		}
		
		return p_dir;
	}
	
	public static void tryClose(InputStream p_inStream)
	{
		if (p_inStream != null)
		{
			try
			{
				p_inStream.close();
			}
			
			catch (IOException ioe)
			{
				// Shhh !
			}
		}
	}
}

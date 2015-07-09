package com.odcgroup.edge.t24.generation.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import com.acquire.util.AssertionUtils;

public abstract class ResourcesUtil {
	public static URL getPlugInResource(String p_resourceName)
	{
		AssertionUtils.requireNonNull(p_resourceName, "p_resourceName");
		return ResourcesUtil.class.getResource(p_resourceName);
	}
	
	public static URL getMandatoryPlugInResource(String p_resourceName)
	{
		AssertionUtils.requireNonNull(p_resourceName, "p_resourceName");
		URL result = getPlugInResource(p_resourceName);
		
		if (result == null)
			throw new RuntimeException("Unable to find resource: \"" + p_resourceName + "\" on classpath from which " + ResourcesUtil.class.getName() + " was loaded.");
		
		return result;
	}

    /**
     * Copy a resource to an external file.
     *
     * @param p_resource the resource
     * @param p_outputFile the output file
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws FileNotFoundException the file not found exception
     */
    public static void copyResourceToFile(String p_resource, String p_outputFile) throws IOException, FileNotFoundException
    {
        InputStream inStream = null;
        OutputStream outStream = null;
        
        try
        {
            URL resourceURL = ResourcesUtil.getMandatoryPlugInResource(p_resource);

            // Make sure the output folders exist
            //
            File dir = new File(p_outputFile).getParentFile();
            
            if  ( ! dir.exists() )
            {
                dir.mkdirs();
            }
            
            inStream = new BufferedInputStream( resourceURL.openStream() );
            outStream = new BufferedOutputStream( new FileOutputStream( p_outputFile ) );
            
            byte[] buffer = new byte[10000];
            int len;
            while ( ( len = inStream.read( buffer ) ) != -1 )
            {
                outStream.write( buffer, 0, len );
            }
        }
        finally
        {
            if  ( outStream != null )
            {
                try
                {
                    outStream.close();
                }
                catch (Exception p_ex)
                {
                    // Ignore
                }
            }
            
            if  ( inStream != null )
            {
                try
                {
                    inStream.close();
                }
                catch (Exception p_ex)
                {
                    // Ignore
                }
            }
        }
    }
}

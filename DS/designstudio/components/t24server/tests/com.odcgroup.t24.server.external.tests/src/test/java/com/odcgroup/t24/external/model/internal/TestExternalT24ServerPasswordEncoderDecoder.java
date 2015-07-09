package com.odcgroup.t24.external.model.internal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import junit.framework.Assert;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.server.tests.AbstractServerTest;
import com.odcgroup.t24.server.external.model.internal.ExternalT24Server;
import com.odcgroup.t24.server.external.model.internal.T24ServerException;
import com.odcgroup.t24.server.external.nature.T24ExternalServerNature;
import com.odcgroup.t24.server.external.util.ServerPropertiesPasswordScrambler;

public class TestExternalT24ServerPasswordEncoderDecoder extends AbstractServerTest {

	private IProject serverProject;
	private static final String PASSWORD_VALUE = "123456";
	private static final String PASSWORD_KEY = "password";
	private ServerPropertiesPasswordScrambler scramble = null;
	private String serverProjectName = "Server";
	private final String createPath = "/config";
	
	@Before
	public void setUp() throws CoreException, IOException {
		serverProject = createJavaProject(serverProjectName, T24ExternalServerNature.NATURE_ID).getProject();

		mkdirs(serverProject.getFolder(new Path(createPath)));
		
		IFile file = serverProject.getFile(ExternalT24Server.SERVER_PROPERTIES_PATH);
		String contents = new String(PASSWORD_KEY);
		InputStream source = new ByteArrayInputStream(contents.getBytes());
		file.create(source, true, null);
		
		//encoding the password
		scramble = new ServerPropertiesPasswordScrambler();
		String encodedPwd = scramble.encode(PASSWORD_VALUE);
		
		IPath location = file.getLocation();
		File tempFile = location.toFile();
		
		if (tempFile.exists()) {
			try {
				FileInputStream fstream = new FileInputStream(
						location.toString());
				DataInputStream in = new DataInputStream(fstream);
				BufferedReader br = new BufferedReader(
						new InputStreamReader(in));
				String strLine = null;
				StringBuilder fileContent = new StringBuilder();
				// write the encoded password to the File Line By Line
				while ((strLine = br.readLine()) != null) {
					if ((strLine.startsWith(PASSWORD_KEY)) && strLine != null) {
						String newLine = PASSWORD_KEY + "=" + encodedPwd.trim();
						fileContent.append(newLine + "\n");
					} else{
						fileContent.append(strLine);
						fileContent.append("\n");
					}
				}
				FileWriter fstreamWrite = new FileWriter(
						location.toString());
				BufferedWriter out = new BufferedWriter(fstreamWrite);
				out.write(fileContent.toString());
				out.close();
				in.close();
			} catch (Exception e) {
				//logger
			}
		}else {
			throw new IOException("server.properties file not found.");
		}
	}
	
	@After
	public void tearDown() throws CoreException {
		if(serverProject != null) {
			serverProject.delete(true, null);
		}
	}
	
	@Test
	public void testServerPasswordDecode() throws T24ServerException, SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, ClassNotFoundException {

		ExternalT24Server server = new ExternalT24Server("ServerId", "ServerName", serverProject);
		String actualPwd = null;
		try {
			Properties file = server.readPropertiesFile();
			actualPwd = file.getProperty(PASSWORD_KEY);
			Assert.assertNotNull("password value is not found in properties file.", actualPwd);
		} catch (IOException e) {
			throw new T24ServerException("Unable to read Server Properties file");
		}
		Assert.assertNotNull("NullPointer of ServerPropertiesPasswordScrambler.class", scramble);
	    Assert.assertEquals("Not matched after decode the password.", PASSWORD_VALUE, scramble.decode(actualPwd));
	}
}

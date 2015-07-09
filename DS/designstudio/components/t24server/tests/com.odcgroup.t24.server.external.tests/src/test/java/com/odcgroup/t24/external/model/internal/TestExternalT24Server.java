package com.odcgroup.t24.external.model.internal;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;

import junit.framework.Assert;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.server.tests.AbstractServerTest;
import com.odcgroup.t24.server.external.model.ApplicationDetail;
import com.odcgroup.t24.server.external.model.IExternalLoader;
import com.odcgroup.t24.server.external.model.internal.ExternalT24Server;
import com.odcgroup.t24.server.external.model.internal.T24ServerException;

public class TestExternalT24Server extends AbstractServerTest {
	
	private IProject serverProject;
	
	@Before
	public void setUp() throws CoreException {
		serverProject = createProject("someServer");

		mkdirs(serverProject.getFolder(new Path("/config")));
		
		IFile file = serverProject.getFile(ExternalT24Server.SERVER_PROPERTIES_PATH);
		String contents = "Whatever";
		InputStream source = new ByteArrayInputStream(contents.getBytes());
		file.create(source, true, null);
	}
	
	@After
	public void tearDown() throws CoreException {
		serverProject.delete(true, null);
	}

	@Test
	public void testDS5748ServerPropertiesChangeRequiresDSRestart() throws T24ServerException, SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, ClassNotFoundException {
		// Given
		ExternalT24Server server = new ExternalT24Server("someServerId", "someServerName", serverProject);
		
		// When
		IExternalLoader loader1 = server.getExternalLoader(ApplicationDetail.class);
		IExternalLoader loader2 = server.getExternalLoader(ApplicationDetail.class);
		
		// Then
		Field propertiesField = getField(loader1.getClass(), "properties");
		Assert.assertNotSame(propertiesField.get(loader1), propertiesField.get(loader2));
	}

	private Field getField(Class<?> class1,
			String fieldName) throws SecurityException, NoSuchFieldException {
		Field field;
		try {
			field = class1.getDeclaredField(fieldName);
		} catch (NoSuchFieldException e) {
			Class<?> superclass = class1.getSuperclass();
			if (superclass != null) {
				field = getField(superclass, fieldName);
			} else {
				throw e;
			}
		}
		field.setAccessible(true);
		return field;
	}

}

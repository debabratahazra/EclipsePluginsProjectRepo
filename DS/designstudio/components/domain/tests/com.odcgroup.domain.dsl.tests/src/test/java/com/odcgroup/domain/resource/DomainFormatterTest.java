package com.odcgroup.domain.resource;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.eclipse.xtext.junit4.util.ParseHelper;
import org.eclipse.xtext.resource.SaveOptions;
import org.eclipse.xtext.resource.XtextResourceFactory;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.odcgroup.domain.DomainUiInjectorProvider;
import com.odcgroup.mdf.ecore.MdfDomainImpl;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfReverseAssociation;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;
import com.odcgroup.workbench.core.tests.util.MultiplatformTestUtil;

@RunWith(XtextRunner.class)
@InjectWith(DomainUiInjectorProvider.class)
public class DomainFormatterTest extends AbstractDSUnitTest {

	private static final String DOMAIN = "domain/ds4959/DS4959.domain";

	@Inject	ParseHelper<MdfDomainImpl> parser;
	@Inject XtextResourceFactory resourceFactory;  
    @Inject ResourceSet resourceSet;

	@After
	public void tearDown() throws Exception {
		deleteModelsProjects();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testDomainFormatter() throws Exception {
		// Given
		IOfsProject project = createModelsProject();
		copyResourceInModelsProject(DOMAIN);
		IFile file = project.getProject().getFile(DOMAIN);
		
		InputStream stream = null;
		String formattedDomain = null;
		try {
			stream = file.getContents();
			StringWriter writer = new StringWriter();
			IOUtils.copy(stream, writer);
			formattedDomain = writer.toString();
		} finally {
			IOUtils.closeQuietly(stream);
		}
		String unformattedDomain = formattedDomain.replace('\n', ' ').replace('\r', ' ').replace('\t', ' ');
		unformattedDomain = StringUtils.removeStart(unformattedDomain, "# UTF-8 ");
		unformattedDomain = "# UTF-8\n\r" + unformattedDomain;
		assertNotSame("If the unformatted domain is the same as the formatted domain, then the test is invalid",
				unformattedDomain, formattedDomain);

		// When
		MdfDomainImpl domain = parser.parse(unformattedDomain);
		assertNotNull(domain);
		Resource resource = resourceSet.createResource(URI.createFileURI("DomainFormatterTest.domain"));
		resource.getContents().add(domain);
		
		// Remove the reverses
		for (MdfClass clazz : (List<MdfClass>)domain.getClasses()) {
			for (Iterator iter=clazz.getProperties().iterator(); iter.hasNext(); ) {
				Object object = iter.next();
				if (object instanceof MdfReverseAssociation) {
					iter.remove();
				}
			}
		}
		
		ByteArrayOutputStream outputStream = null;
		String reformattedDomain;
		try {
			outputStream = new ByteArrayOutputStream();
	        Map<Object, Object> options = SaveOptions.newBuilder().format().noValidation().getOptions().toOptionsMap();
			resource.save(outputStream, options);
			reformattedDomain = outputStream.toString();
		} finally {
			IOUtils.closeQuietly(outputStream);
		}
		
		// Strangely encoding only get \n
		reformattedDomain = reformattedDomain.replace("# UTF-8\n", "# UTF-8\r\n");

		// Then
		MultiplatformTestUtil.assertEqualsIgnoringEOL(
				"The formatter is not able to format the domain the same way the original grammar (carriage return sensitive) was.", 
				formattedDomain, reformattedDomain);
	}

}

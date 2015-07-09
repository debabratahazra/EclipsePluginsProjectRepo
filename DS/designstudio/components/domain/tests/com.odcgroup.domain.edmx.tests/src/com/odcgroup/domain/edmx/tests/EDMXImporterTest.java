package com.odcgroup.domain.edmx.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import javax.inject.Inject;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.odcgroup.domain.DomainInjectorProvider;
import com.odcgroup.domain.edmx.EDMXImporter;
import com.odcgroup.domain.edmx.internal.EDMX2DomainMapper;
import com.odcgroup.mdf.ecore.MdfDomainImpl;
import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfAnnotationProperty;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.validation.validator.MdfModelValidator;

/**
 * Test(s) for the EDMX Import stuff.
 */
@RunWith(XtextRunner.class)
@InjectWith(DomainInjectorProvider.class)
public class EDMXImporterTest {

	@Inject
	ResourceSet resourceSet;

	/**
	 * Test model T24 from a file
	 * 
	 * @throws Exception
	 */
	@Test
	public void testEDMXImportT24FromFile() throws Exception {
		EDMXImporter edmxImporter = new EDMXImporter();

		Collection<MdfDomainImpl> domains = edmxImporter.fromInputStreamImportEDMX(EDMXImporterTest.class.getResource(
				"T24MetaData.xml").openStream());
		
		assertEquals(1,domains.size());
		MdfDomainImpl dom = domains.iterator().next();
		
		assertEquals(94,dom.getEntities().size());
		
		//test in detail the  entity CustomerEdge:
		MdfClass mdfCls = dom.getClass("CustomerEdge");
		assertNotNull(mdfCls);
		
		assertEquals(11,mdfCls.getProperties().size());
		//test PK
		MdfModelElement elmt = (MdfModelElement)mdfCls.getPrimaryKeys().get(0);
		assertEquals("Id", elmt.getName());

		//test association
		elmt = (MdfModelElement)mdfCls.getProperty("CustAcctEdges");
		assertNotNull("CustAcctEdges association", elmt);
		
		MdfAnnotation anno = (MdfAnnotation) elmt.getAnnotation(EDMX2DomainMapper.NAMESPACE_ODATA, EDMX2DomainMapper.ODATA);
		assertNotNull("Odata annotation",anno);
		MdfAnnotationProperty annoP = anno.getProperty("Name");
		assertNotNull("annotation property Name ",annoP);
		
		assertEquals("CustAcctEdges", annoP.getValue());
		
		saveDomains(domains,"./");
		validateDomains(domains);
	}

	private void validateDomains(Collection<MdfDomainImpl> domains) throws Exception {
		MdfModelValidator validator = new MdfModelValidator();
		for (MdfDomainImpl domain : domains) {
			IStatus status = validator.validate(domain);
			if (!status.isOK()) {
				throw new Exception("Validation of " + domain.getName() + " failed: " + status.getMessage(),
						status.getException());
			}
		}
	}

	/**
	 * Test model Triple'A from a file, generate *.domain. the domain have some
	 * cross reference.
	 * 
	 * Test is ignored because we need the support of a composite primary key.
	 * 
	 * @throws Exception
	 */
	@Test
	@Ignore
	public void testEDMXImportTAFromFile() throws Exception {
		EDMXImporter edmxImporter = new EDMXImporter();
		Collection<MdfDomainImpl> domains = edmxImporter.fromInputStreamImportEDMX(EDMXImporterTest.class.getResource(
				"TripleAMetadata.xml").openStream());
		saveDomains(domains,"./");
		validateDomains(domains);
	}

	/**
	 * Helper for saving multiple domain at same time.
	 * 
	 * @param domains
	 * @param folderDestination
	 * @throws IOException
	 */
	private void saveDomains(Collection<MdfDomainImpl> domains, String folderDestination) throws IOException {
		for (MdfDomainImpl domain : domains) {
			URI uri = URI.createURI(folderDestination + domain.getName() + ".domain");
			Resource resource = resourceSet.createResource(uri);
			resource.getContents().add(domain);
			
			resource.save(null);
			File file = new File(resource.getURI().toFileString());
			assertTrue(file.exists());
		}
	}

}

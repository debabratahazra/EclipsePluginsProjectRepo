package com.odcgroup.tap.mdf.generation.tests;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import org.eclipse.emf.common.util.URI;
import org.junit.Assert;
import org.junit.Test;

import com.odcgroup.mdf.OutputStreamFactory;
import com.odcgroup.mdf.build.FileOutputStreamFactory; 
import com.odcgroup.mdf.ecore.util.DomainRepository;
import com.odcgroup.mdf.generation.tests.MdfGenerationTestCase;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.utils.ConsoleMessageHandler;
import com.odcgroup.tap.mdf.generation.xls.TAPExcelWriter;

public class TAPExcelWriterTestCase extends MdfGenerationTestCase {

	/**
	 * Test the generation of xls for the domain
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void testDomainExcelGeneration() throws Exception {

    	copyResourceInModelsProject("domain/mdf-generation-tests/mdfMarketTest.domain", "domain/mdf-generation-tests/fake.domain");

    	getOfsProject().getOfsModelResource(URI.createURI("resource:///mdf-generation-tests/mdfMarketTest.domain")).getEMFModel().get(0);
    	getOfsProject().getOfsModelResource(URI.createURI("resource:///mdf-generation-tests/fake.domain")).getEMFModel().get(0);

    	DomainRepository repository = DomainRepository.getInstance(getOfsProject());
    	
		Collection mdfDomains = repository.getDomains();
		Assert.assertNotNull(mdfDomains);
		Assert.assertEquals(2, mdfDomains.size());

		Iterator it = mdfDomains.iterator();

		String p = getOutputLocation().toString();

		OutputStreamFactory factory = new FileOutputStreamFactory(new File(p));
		TAPExcelWriter writer = new TAPExcelWriter();
		writer.setMessageHandler(new ConsoleMessageHandler());
		while (it.hasNext()) {
			MdfDomain domain = (MdfDomain) it.next();
			writer.write(domain, Collections.<MdfDomain>emptyList(), factory);
		}

		// verify that we have 2 files in there: 
		// mdf-market-test.xls and fake.xls
		File mdfMarketXsl = new File(p + "/doc/mdf-market-test.xls");
		Assert.assertTrue("File do not exist : " + mdfMarketXsl.getAbsolutePath(), mdfMarketXsl.exists());

		File fakeXsl = new File(p + "/doc/fake.xls");
		Assert.assertTrue(fakeXsl.exists());
	}

}

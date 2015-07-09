package com.odcgroup.aaa.connector.mdfmml;

import java.io.IOException;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.junit.After;
import org.junit.Before;

import com.odcgroup.aaa.connector.internal.metadictreader.MetaDictDAO;
import com.odcgroup.aaa.connector.internal.metadictreader.MetaDictRepository;
import com.odcgroup.aaa.connector.tests.JUnit4TstJPA;
import com.odcgroup.aaa.connector.util.BusinessTypesUtil;
import com.odcgroup.aaa.core.AAACore;
import com.odcgroup.otf.jpa.utils.BootstrapJPA;
import com.odcgroup.otf.jpa.utils.CreateDB;

/**
 * @author yan
 */
public abstract class AbstractMetaDictTst extends JUnit4TstJPA {

	protected static MetaDictRepository aaaMetaDict;
	protected static final MetaDictDomains domains = new MetaDictDomains();
	
	private ResourceSet resourceSet;
	
	protected AbstractMetaDictTst() throws IOException {
		super();
        connectionProperties = BootstrapJPA.getPropertiesFromClasspathResource("ta-db.properties");
        CreateDB.addNormalLoggingProperties(connectionProperties);
        persistenceUnitName = "aaa";
	}

	@Before
	public synchronized void setUp() throws Exception {
		super.setUp();
		if (aaaMetaDict == null) {
			MetaDictDAO dao = new MetaDictDAO(em);
			aaaMetaDict = dao.getMetaDict();

			resourceSet = new XtextResourceSet();
			domains.businessTypesDomain = BusinessTypesUtil.getBusinessTypesDomain(resourceSet);

			MetaDict2Mml metaDict2MML = new MetaDict2Mml();
			AAACore.setRootAAAFolder("aaa");
			metaDict2MML.createAAADomainFromMetaDict(aaaMetaDict, domains);
		}
	}

	@After
	public void tearDown() throws Exception {
		if (resourceSet != null)
			if (resourceSet.getResources() != null)
				resourceSet.getResources().clear();
        resourceSet = null;
		super.tearDown();
    }
}

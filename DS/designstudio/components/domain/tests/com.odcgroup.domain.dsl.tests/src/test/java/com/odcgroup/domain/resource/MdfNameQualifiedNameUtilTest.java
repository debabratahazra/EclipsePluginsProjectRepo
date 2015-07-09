package com.odcgroup.domain.resource;

import static org.junit.Assert.assertEquals;

import org.eclipse.xtext.naming.QualifiedName;
import org.junit.Test;

import com.odcgroup.domain.scoping.MdfNameQualifiedNameUtil;
import com.odcgroup.mdf.ecore.MdfAssociationImpl;
import com.odcgroup.mdf.ecore.MdfClassImpl;
import com.odcgroup.mdf.ecore.MdfDomainImpl;
import com.odcgroup.mdf.ecore.MdfFactory;
import com.odcgroup.mdf.ecore.PrimitivesDomain;
import com.odcgroup.mdf.metamodel.MdfName;
import com.odcgroup.mdf.metamodel.MdfNameFactory;

/**
 * Unit Test for MdfNameQualifiedNameUtil. 
 *
 * @author Michael Vorburger
 */
public class MdfNameQualifiedNameUtilTest {

	@Test
	public void testMdfNameQualifiedNameUtil() {
		MdfName mdfName = PrimitivesDomain.DATE_TIME.getQualifiedName();
		
		QualifiedName qName = MdfNameQualifiedNameUtil.getQualifiedName(mdfName);
		assertEquals(2, qName.getSegmentCount());
		assertEquals(mdfName.getDomain(), qName.getFirstSegment());
		assertEquals(mdfName.getLocalName(), qName.getLastSegment());
		MdfName newMdfName = MdfNameQualifiedNameUtil.getMdfName(qName);
		assertEquals(mdfName, newMdfName);
	}

	@Test
	public void testMdfDomainName() {
		MdfName mdfDomainName = MdfNameFactory.createMdfName("MyDomain");
		QualifiedName qName = MdfNameQualifiedNameUtil.getQualifiedName(mdfDomainName);
		assertEquals(mdfDomainName.getLocalName(), qName.getFirstSegment());
		assertEquals(qName.toString(), 1, qName.getSegmentCount());
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void testMdfNameQualifiedNameUtilForAssociation() {
		
		MdfDomainImpl domain = (MdfDomainImpl) MdfFactory.eINSTANCE.createMdfDomain();
		domain.setName("NewDomain");
		// No need - and prevents running test as SE/non-OSGi: domain.setMetamodelVersion(OfsCore.getVersionNumber());
		MdfClassImpl clazz = (MdfClassImpl)MdfFactory.eINSTANCE.createMdfClass();
		clazz.setName("NewClass");
		
		MdfClassImpl clazz2 = (MdfClassImpl)MdfFactory.eINSTANCE.createMdfClass();
		clazz2.setName("NewClass2");

		domain.getClasses().add(clazz);
		domain.getClasses().add(clazz2);

		MdfAssociationImpl assoc1 = (MdfAssociationImpl) MdfFactory.eINSTANCE.createMdfAssociation();
		assoc1.setName("b_association");
		assoc1.setType(clazz2);
		clazz.getProperties().add(assoc1);
		
		MdfName mdfName = assoc1.getQualifiedName();
		QualifiedName qName = MdfNameQualifiedNameUtil.getQualifiedName(mdfName);
		assertEquals(3, qName.getSegmentCount());
		assertEquals(mdfName.getDomain(), qName.getFirstSegment());
		assertEquals(mdfName.getLocalName(), qName.getSegment(1)+"#"+qName.getLastSegment());
		MdfName newMdfName = MdfNameQualifiedNameUtil.getMdfName(qName);
		assertEquals(mdfName, newMdfName);
	}

}

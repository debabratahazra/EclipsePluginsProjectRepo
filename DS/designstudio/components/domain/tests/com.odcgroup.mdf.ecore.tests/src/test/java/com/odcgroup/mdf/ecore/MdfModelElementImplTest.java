package com.odcgroup.mdf.ecore;

import org.junit.Assert;
import org.junit.Test;

import com.odcgroup.mdf.metamodel.MdfName;
import com.odcgroup.mdf.metamodel.MdfNameFactory;

public class MdfModelElementImplTest {

	@Test
	public void testThatEqualsWhenBothSidesHaveSameQualifiedNameReturnsTrue() throws Exception {
		// given 
		MdfModelElementImpl element1 = getModelElement("TestName");
		MdfModelElementImpl element2 = getModelElement("TestName");
		// when
		boolean modelsAreEqual = element1.equals(element2);
		// then
		Assert.assertFalse(modelsAreEqual);
	}
	
	@Test
	public void testThatEqualsWhenBothSidesDifferentQualifiedNamesReturnsFalse() throws Exception {
		// given 
		MdfModelElementImpl element1 = getModelElement("TestName");
		MdfModelElementImpl element2 = getModelElement("DifferentTestName");
		// when
		boolean modelsAreEqual = element1.equals(element2);
		// then
		Assert.assertEquals(false, modelsAreEqual);
	}
	
	@Test
	public void testThatEqualsWhenBothSidesHaveNullQualifiedNamesReturnsFalse() throws Exception {
		// given 
		MdfModelElementImpl element1 = getModelElementWithNullQualifiedName();
		MdfModelElementImpl element2 = getModelElementWithNullQualifiedName();
		// when
		boolean modelsAreEqual = element1.equals(element2);
		// then
		Assert.assertEquals(false, modelsAreEqual);
	}
	
	@Test
	public void testThatEqualsWhenLeftSideHasNullQualifiedNameAndRightSideHasQualifiedNameReturnsFalse() throws Exception {
		// given 
		MdfModelElementImpl element1 = getModelElementWithNullQualifiedName();
		MdfModelElementImpl element2 = getModelElement("TestName");
		// when
		boolean modelsAreEqual = element1.equals(element2);
		// then
		Assert.assertEquals(false, modelsAreEqual);
	}
	
	@Test
	public void testThatEqualsWhenLeftSideHasQualifiedNameAndRightSideHasNullQualifiedNameReturnsFalse() throws Exception {
		// given 
		MdfModelElementImpl element1 = getModelElement("TestName");
		MdfModelElementImpl element2 = getModelElementWithNullQualifiedName();
		// when
		boolean modelsAreEqual = element1.equals(element2);
		// then
		Assert.assertEquals(false, modelsAreEqual);
	}
	
	@Test
	public void testThatEqualsWhenLeftSideHasQualifiedNameAndRightSideIsNullReturnsFalse() throws Exception {
		// given 
		MdfModelElementImpl element1 = getModelElement("TestName");
		MdfModelElementImpl element2 = null;
		// when
		boolean modelsAreEqual = element1.equals(element2);
		// then
		Assert.assertEquals(false, modelsAreEqual);
	}
	
	@Test
	public void testThatEqualsWhenLeftSideHasNullQualifiedNameAndRightSideIsNullModelReturnsFalse() throws Exception {
		// given 
		MdfModelElementImpl element1 = getModelElementWithNullQualifiedName();
		MdfModelElementImpl element2 = null;
		// when
		boolean modelsAreEqual = element1.equals(element2);
		// then
		Assert.assertEquals(false, modelsAreEqual);
	}
	
	private MdfModelElementImpl getModelElement(final String qualifiedName) {
		return new MdfModelElementImpl() {
			private static final long serialVersionUID = 1L;
			@Override
			public MdfName getQualifiedName() {
				return MdfNameFactory.createMdfName(qualifiedName);
			}
		};
	}

	private MdfModelElementImpl getModelElementWithNullQualifiedName() {
		return new MdfModelElementImpl() {
			private static final long serialVersionUID = 1L;
			@Override
			public MdfName getQualifiedName() {
				return null;
			}
		};
	}	
}

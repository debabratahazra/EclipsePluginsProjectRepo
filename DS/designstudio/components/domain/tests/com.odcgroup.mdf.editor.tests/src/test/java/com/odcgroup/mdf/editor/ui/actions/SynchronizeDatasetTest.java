package com.odcgroup.mdf.editor.ui.actions;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.junit.Assert;
import org.junit.Test;

import com.odcgroup.mdf.editor.tests.AbstractDomainEditorTestCase;
import com.odcgroup.mdf.editor.ui.editors.DomainModelEditor;
import com.odcgroup.mdf.ext.aaa.AAAAspect;
import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfAnnotationProperty;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetMappedProperty;
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.model.translation.MdfTranslationHelper;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;

/**
 * @author yan
 */
public class SynchronizeDatasetTest extends AbstractDomainEditorTestCase {
	
	private static final String SYNC_DATASET = "SyncDataset.domain";
	
    @Test
	public void testSynchronizeDataset() throws ModelNotFoundException, CoreException, IOException, InvalidMetamodelVersionException {
    	// 1. Execute the Synchronize Dataset action
    	IOfsModelResource ofsModelResource = getModelResource(getOfsProject(), "/mdf-editor-tests/" + SYNC_DATASET);
    	assertNotNull(ofsModelResource);
    	
    	// Avoid popups for confirmation of the action.
    	SynchronizeDatasetsAction action = new SynchronizeDatasetsAction() {
    		protected boolean confirmCloseEditors(Shell shell) {
    			return true;
    		}
    		protected boolean confirmSynchronization(Shell shell) {
    			return true;
    		}
    	};
    	StructuredSelection selection = new StructuredSelection(ofsModelResource);
    	action.selectionChanged(selection);
    	action.run();
    	
    	getProject().refreshLocal(IResource.DEPTH_INFINITE, null);
    	
    	// 2. validate the number of attribute and associations are correctly copied
    	ofsModelResource = getModelResource(getOfsProject(), "/mdf-editor-tests/" + SYNC_DATASET);
    	assertNotNull(ofsModelResource);
    	MdfDomain domain = (MdfDomain)ofsModelResource.getEMFModel().get(0);
    	MdfDataset dataset = domain.getDataset("SynchronizeDataset");

		Assert.assertNotNull("id property should be copied", dataset.getProperty("id"));
		Assert.assertNotNull("attrib property should be copied", dataset.getProperty("attrib"));
		Assert.assertNotNull("assocRef property should be copied", dataset.getProperty("assocRef"));
		Assert.assertEquals("No other properties should be copied", 3, dataset.getProperties().size());
    }
    
    /**
     * @throws CoreException
     */
    @Test
	public void testCopyBaseClass() throws CoreException {
    	// 1. load a simple model with various attributes and associations
    	DomainModelEditor domainEditor = openDomainModelEditor(SYNC_DATASET);
    	EditingDomain editingDomain = domainEditor.getEditingDomain();
    	MdfDomain domain = getMdfDomain(editingDomain, SYNC_DATASET);
    	MdfDataset dataset = domain.getDataset("CopyBaseClassDataset");
    	StructuredSelection selection = new StructuredSelection(dataset);
    	
    	// 2. Execute the Synchronize Dataset action
		DeriveFromBaseClassAction action = new DeriveFromBaseClassAction(domainEditor, editingDomain, selection);
		action.getCopyBaseClassCommands(editingDomain, dataset).execute();
    	
    	// 3. validate the number of attribute and associations are correctly copied
		Assert.assertNotNull("id property should be copied", dataset.getProperty("id"));
		Assert.assertNotNull("attrib property should be copied", dataset.getProperty("attrib"));
		Assert.assertNotNull("assocRef property should be copied", dataset.getProperty("assocRef"));
		Assert.assertEquals("No other properties should be copied", 3, dataset.getProperties().size());
    }
    
    
    /**
     * test of DS-3053 Synchronize datasets removes custom annotations
     */
	@Test
	public void testDS3053_SynchronizeDatasetsRemovesCustomAnnotations() throws CoreException, ModelNotFoundException, IOException, InvalidMetamodelVersionException {
		// 1. Execute copy base class 
    	IOfsModelResource ofsModelResource = getModelResource(getOfsProject(), "/mdf-editor-tests/DS-3053.domain");
    	assertNotNull(ofsModelResource);
		MdfDomain domain = (MdfDomain)ofsModelResource.getEMFModel().get(0);
		MdfDataset dataset = domain.getDataset("NewDataset");
		List<MdfDatasetMappedProperty> mProperties = DeriveBaseClassUtil.getDatasetMappedProperties(getOfsProject(), dataset);
		DatasetFacility.copyBaseClass(dataset, mProperties);

		// 2. Validation
		// Validate attribute1 has LoadPermittedValues=false
		Assert.assertFalse(AAAAspect.getLoadPermittedValus(dataset.getProperty("attribute1")));
		
		// Validate attribute2 has LoadPermittedValues=false + MMLSpecific=true
		Assert.assertFalse(AAAAspect.getLoadPermittedValus(dataset.getProperty("attribute2")));
		Assert.assertNotNull(AAAAspect.getMMLSpecific(dataset.getProperty("attribute2")));

		// Validate attribute3 has LoadPermittedValues=false
		Assert.assertFalse(AAAAspect.getLoadPermittedValus(dataset.getProperty("attribute3")));
		
		// Validate attribute4 has LoadPermittedValues=true
		Assert.assertTrue(AAAAspect.getLoadPermittedValus(dataset.getProperty("attribute4")));
	}
	
	/**
	 * test DS-3294 retain documentation values defined
	 */
	@Test
	public void testDS3294_RetainDocumentation() throws CoreException, ModelNotFoundException, IOException, InvalidMetamodelVersionException {
    	IOfsModelResource ofsModelResource = getModelResource(getOfsProject(), "/mdf-editor-tests/DS-3294.domain");
    	assertNotNull(ofsModelResource);
		MdfDomain domain = (MdfDomain)ofsModelResource.getEMFModel().get(0);
		MdfDataset dataset = domain.getDataset("DSFlat");
		MdfDatasetProperty property = dataset.getProperty("AmountDecimal");
		String documentation = property.getDocumentation();
		
		// validate the AmountDecimal datasetAttribute has documentation defined
		Assert.assertFalse("Documentation exists for property - AmountDecimal", StringUtils.isEmpty(documentation));		

		List<MdfDatasetMappedProperty> mProperties = DeriveBaseClassUtil.getDatasetMappedProperties(getOfsProject(), dataset);
		DatasetFacility.copyBaseClass(dataset, mProperties);
		property = dataset.getProperty("AmountDecimal");
		
		// Assert.assert the documentation of the given property is retained
		Assert.assertEquals(documentation, property.getDocumentation());
	}
	
	@Test
	public void testDS4573_RetainLocalTranslations() throws CoreException, ModelNotFoundException, IOException, InvalidMetamodelVersionException {
    	IOfsModelResource ofsModelResource = getModelResource(getOfsProject(), "/mdf-editor-tests/DS-4573.domain");
    	assertNotNull(ofsModelResource);
		MdfDomain domain = (MdfDomain)ofsModelResource.getEMFModel().get(0);
		MdfDataset dataset = domain.getDataset("TestDS");
		MdfDatasetProperty property = dataset.getProperty("testAttribute");
		MdfAnnotation oldAnnotation = property.getAnnotation(MdfTranslationHelper.NAMESPACE_URI, MdfTranslationHelper.TRANSLATION_LABEL);		
		Assert.assertFalse("Local translations exists for property - testAttribute", oldAnnotation == null);		
		MdfAnnotationProperty oldAnnProp = oldAnnotation.getProperty("en");

		List<MdfDatasetMappedProperty> mProperties = DeriveBaseClassUtil.getDatasetMappedProperties(getOfsProject(), dataset);
		DatasetFacility.copyBaseClass(dataset, mProperties);
		property = dataset.getProperty("testAttribute");
		MdfAnnotation newAnnotation = property.getAnnotation(MdfTranslationHelper.NAMESPACE_URI, MdfTranslationHelper.TRANSLATION_LABEL);		
		Assert.assertFalse("Local translations retained for property - testAttribute", newAnnotation == null);		
		
		// Assert.assert the documentation of the given property is retained
		MdfAnnotationProperty newAnnProp = newAnnotation.getProperty("en");
		Assert.assertEquals(oldAnnProp.getValue(), newAnnProp.getValue());
		
	}
	
    @Test
	public void testDS7575_SynchronizeDataset() throws ModelNotFoundException, CoreException, IOException, InvalidMetamodelVersionException {
    	// 1. Execute the Synchronize Dataset action
    	IOfsModelResource ofsModelResource = getModelResource(getOfsProject(), "/mdf-editor-tests/" + SYNC_DATASET);
    	assertNotNull(ofsModelResource);
    	
    	// Avoid popups for confirmation of the action.
    	SynchronizeDatasetsAction action = new SynchronizeDatasetsAction() {
    		protected boolean confirmCloseEditors(Shell shell) {
    			return true;
    		}
    		protected boolean confirmSynchronization(Shell shell) {
    			return true;
    		}
    	};
    	StructuredSelection selection = new StructuredSelection(ofsModelResource);
    	action.selectionChanged(selection);
    	action.run();
    	
    	getProject().refreshLocal(IResource.DEPTH_INFINITE, null);
    	
    	// 2. validate the number of attribute and associations are correctly copied
    	ofsModelResource = getModelResource(getOfsProject(), "/mdf-editor-tests/" + SYNC_DATASET);
    	assertNotNull(ofsModelResource);
    	MdfDomain domain = (MdfDomain)ofsModelResource.getEMFModel().get(0);
    	MdfDataset dataset = domain.getDataset("AnotherSynchronizedDataset");

		Assert.assertNotNull("id property should be copied", dataset.getProperty("id"));
		Assert.assertNotNull("otherChildAttribute property should be kept", dataset.getProperty("otherChildAttribute"));
		Assert.assertEquals("No other properties should be copied", 2, dataset.getProperties().size());
		
		MdfDatasetProperty property = dataset.getProperty("otherChildAttribute");

		// check documentation
		Assert.assertEquals("Wrong documentation for property otherChildAttribute", "documentation", property.getDocumentation());		
		
		// check translation
		MdfAnnotation annotation = property.getAnnotation(MdfTranslationHelper.NAMESPACE_URI, MdfTranslationHelper.TRANSLATION_LABEL);		
		Assert.assertFalse("Local translations exists for property - otherChildAttribute", annotation == null);		
		MdfAnnotationProperty annProp = annotation.getProperty("en");
		Assert.assertEquals("Wrong english Label translation for property otherChildAttribute", "label", annProp.getValue());		

		
    }
}

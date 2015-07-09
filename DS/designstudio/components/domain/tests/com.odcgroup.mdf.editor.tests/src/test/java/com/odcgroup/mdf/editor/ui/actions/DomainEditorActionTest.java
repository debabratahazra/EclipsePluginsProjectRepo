package com.odcgroup.mdf.editor.ui.actions;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.junit.Assert;
import org.junit.Test;

import com.odcgroup.mdf.editor.tests.AbstractDomainEditorTestCase;
import com.odcgroup.mdf.editor.ui.editors.DomainModelEditor;
import com.odcgroup.mdf.editor.ui.editors.providers.MdfTreeItemReferenceProvider;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfModelElement;

/**
 * 
 * @author pkk
 * 
 */
public class DomainEditorActionTest extends AbstractDomainEditorTestCase {
	

	/**
	 * @throws CoreException
	 */
	@Test
	public void testDS3232_DoubleClickDatasetAttribute() throws CoreException {
		String testModel = "DS-3232.domain";
		DomainModelEditor domainEditor = openDomainModelEditor(testModel);
		EditingDomain editingDomain = domainEditor.getEditingDomain();

    	MdfDomain domain = getMdfDomain(editingDomain, testModel);
    	MdfDataset dataset = domain.getDataset("DSFlat");
    	MdfDatasetProperty property = dataset.getProperty("AmountDecimal");
    	
    	TreeViewer viewer = (TreeViewer) domainEditor.getViewer();
    	
		ISelection selection = new StructuredSelection(property);
		viewer.setSelection(selection, true);

		MdfModelElement element = MdfTreeItemReferenceProvider.getTreeItemReference(property);		
		Assert.assertTrue(element instanceof MdfAttribute);
		MdfAttribute attribute = (MdfAttribute) element;
		Assert.assertEquals("AmountDecimal", attribute.getName());
		Assert.assertEquals(dataset.getBaseClass(), attribute.getParentClass());
    	
	}

}

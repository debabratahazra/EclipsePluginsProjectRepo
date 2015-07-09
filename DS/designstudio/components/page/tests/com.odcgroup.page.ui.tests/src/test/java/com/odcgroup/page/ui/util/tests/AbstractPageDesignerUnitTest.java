package com.odcgroup.page.ui.util.tests;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Collection;
import java.util.Iterator;

import org.apache.xerces.parsers.DOMParser;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.model.EvaluationMode;
import org.eclipse.emf.validation.service.AbstractTraversalStrategy;
import org.eclipse.emf.validation.service.IBatchValidator;
import org.eclipse.emf.validation.service.ModelValidationService;
import org.eclipse.swt.widgets.Display;
import org.junit.Assert;
import org.junit.Before;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.transformmodel.util.TransformUtils;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.ui.tests.util.AbstractDSUIUnitTest;

/**
 *
 * @author pkk
 *
 */
public class AbstractPageDesignerUnitTest extends AbstractDSUIUnitTest {
	
	private IBatchValidator validator = null;

	@Before
	public void initValidator() {
		validator = (IBatchValidator) ModelValidationService.getInstance().newValidator(
				EvaluationMode.BATCH);
		validator.setIncludeLiveConstraints(true);
		validator.setTraversalStrategy(new AbstractTraversalStrategy() {
			protected Iterator<? extends EObject> createIterator(Collection<? extends EObject> traversalRoots) {
				return traversalRoots.iterator();
			}					
			protected int countElements(Collection<? extends EObject> traversalRoots) {
				return traversalRoots.size();
			}
		});
	}

	/**
	 * @return the validation status
	 */
	protected IStatus validateModel(Object obj) {
		IStatus status = null;
		if (obj instanceof EObject) {
			status = validator.validate((EObject)obj);
		}
		return status;
	}
	
	/** Clears the diaplay's event queue. */
	protected void flushEventQueue() {
		final Display display = Display.getDefault();
		while (display != null && display.readAndDispatch()) {
			
		}
	}
	
	/**
	 * @param xspXml
	 * @return
	 */
	protected Document fetchGenDocument(IOfsProject ofsProject, Widget widget) {
		String xspXml = TransformUtils.transformWidgetToXmlString(ofsProject.getProject(), widget);
		DOMParser parser = new DOMParser();
		Reader reader = new StringReader(xspXml);
		Document doc = null;
		try {
			parser.parse(new InputSource(reader));
			doc = parser.getDocument();
		} catch (SAXException e) {
			Assert.fail("Unable to do transform the given page model");
		} catch (IOException e) {
			Assert.fail("Unable to do transform the given page model");
		}
		return doc;
	}

}

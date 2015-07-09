package com.odcgroup.page.transformmodel.tests.cdm;

import org.eclipse.core.runtime.CoreException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.ModelFactory;
import com.odcgroup.page.model.Translation;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.WidgetFactory;
import com.odcgroup.page.transformmodel.TransformModel;
import com.odcgroup.page.transformmodel.WidgetTransformerContext;
import com.odcgroup.page.transformmodel.cdm.CdmGenericWidgetTransformer;
import com.odcgroup.page.transformmodel.impl.WidgetTransformerContextImpl;
import com.odcgroup.page.transformmodel.util.TransformModelRegistry;
import com.odcgroup.page.util.XmlUtils;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

/**
 * @author atr
 */
public class CdmWidgetTransformerTest extends AbstractDSUnitTest{
	
	private static String DOMAIN_MODEL = "domain/ds3675/DS3675.domain";
	
	@Before
	public void setUp() throws Exception {
		createModelsProject();
		copyResourceInModelsProject(DOMAIN_MODEL);
	}

	@After
	public void tearDown() throws Exception  {
		deleteModelsProjects();
	}	
	
	@Test
	public void testDS3675GenerateNumericalTranslationKey() {
		
		// create an Attribute Widget
		MetaModel metaModel = MetaModelRegistry.getMetaModel();
		WidgetType attributeType = metaModel.findWidgetType("xgui", "Attribute");
		Assert.assertTrue("The WidgetType [Attribute] does not exists in library xgui", attributeType != null);
		Widget attribute = new WidgetFactory().create(attributeType);
		Assert.assertTrue("The Widget [Attribute] cannot be created", attribute != null);
		
		attribute.setPropertyValue(PropertyTypeConstants.SHOW_LABEL, PropertyTypeConstants.SHOW_LABEL_SHOW);

		// Add a local translation using the raw interface
		final long TID = 12345L;
		attribute.setTranslationId(TID);
		Translation translation = ModelFactory.eINSTANCE.createTranslation();
		translation.setLanguage("en");
		translation.setMessage("en-test");
		attribute.getLabels().add(translation);
		
		TransformModel tm = TransformModelRegistry.getTransformModel();
		Document document = XmlUtils.createDocument(false);
		WidgetTransformerContext context = 
			new WidgetTransformerContextImpl(MetaModelRegistry.getMetaModel(), tm, document, getProject());
		
		CdmGenericWidgetTransformer cdmTransformer = new CdmGenericWidgetTransformer(attributeType) {
		    public void transform(WidgetTransformerContext context, Widget attribute) throws CoreException {
		    	Document document = context.getDocument();
				Element dummy = document.createElement("dummy");
				document.appendChild(dummy);
				addTextAttribute(context, dummy, attribute);
		    }
		};
		try {
			cdmTransformer.transform(context, attribute);
		} catch (CoreException ex) {
			ex.printStackTrace();
			Assert.fail("Unable to transform CDM Attribute Widget");
		}
		
		Document doc = context.getDocument();
		Node dummy = doc.getChildNodes().item(0);
		Assert.assertTrue("Missing node", dummy != null);
		Node attrKey = dummy.getAttributes().getNamedItem("translatekey");
		Assert.assertTrue("Cannot find attribtue [translatekey]", attrKey != null);
		String expectedKey = TID+".text";
		Assert.assertTrue("Wrong generated translation key", expectedKey.equals(attrKey.getNodeValue()));
	}

	@Test
	public void testDS3675GenerateDomainTranslationKey() {
		
		// create an Attribute Widget
		MetaModel metaModel = MetaModelRegistry.getMetaModel();
		WidgetType moduleType = metaModel.findWidgetType("xgui", "Module");
		Assert.assertTrue("The WidgetType [Module] does not exists in library xgui", moduleType != null);
		Widget module = new WidgetFactory().create(moduleType);
		Assert.assertTrue("The Widget [Module] cannot be created", module != null);
		module.setPropertyValue(PropertyTypeConstants.DOMAIN_ENTITY, "DS3675:Dataset");

		// create an Attribute Widget
		metaModel = MetaModelRegistry.getMetaModel();
		WidgetType attributeType = metaModel.findWidgetType("xgui", "Attribute");
		Assert.assertTrue("The WidgetType [Attribute] does not exists in library xgui", attributeType != null);
		Widget attribute = new WidgetFactory().create(attributeType);
		Assert.assertTrue("The Widget [Attribute] cannot be created", attribute != null);
		attribute.setPropertyValue(PropertyTypeConstants.DOMAIN_ATTRIBUTE, "property");
		
		module.getContents().add(attribute);
		
//		NamespaceFacility facility = NamespaceFacilityUtils.getNamespaceFacility(getOfsProject());
		TransformModel tm = TransformModelRegistry.getTransformModel();
		Document document = XmlUtils.createDocument(false);
		WidgetTransformerContext context = 
			new WidgetTransformerContextImpl(MetaModelRegistry.getMetaModel(), tm, document, getProject());
		
		CdmGenericWidgetTransformer cdmTransformer = new CdmGenericWidgetTransformer(attributeType) {
		    public void transform(WidgetTransformerContext context, Widget attribute) throws CoreException {
		    	Document document = context.getDocument();
				Element dummy = document.createElement("dummy");
				document.appendChild(dummy);
				addTextAttribute(context, dummy, attribute);
		    }
		};
		try {
			cdmTransformer.transform(context, attribute);
		} catch (CoreException ex) {
			ex.printStackTrace();
			Assert.fail("Unable to transform CDM Attribute Widget");
		}
		
		Document doc = context.getDocument();
		Node dummy = doc.getChildNodes().item(0);
		Assert.assertTrue("Missing node", dummy != null);
		Node attrKey = dummy.getAttributes().getNamedItem("translatekey");
		Assert.assertTrue("Cannot find attribtue [translatekey]", attrKey != null);
		String expectedKey = "ds3675.dataset.property.text";
		Assert.assertTrue("Wrong generated translation key", expectedKey.equals(attrKey.getNodeValue()));

		
	}

}

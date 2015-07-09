package com.odcgroup.domain.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.common.types.JvmTypeReference;
import org.eclipse.xtext.parser.IParseResult;

import com.google.inject.Inject;
import com.odcgroup.domain.repository.DomainRepositoryUtil;
import com.odcgroup.mdf.ecore.MdfClassImpl;
import com.odcgroup.mdf.ecore.MdfDomainImpl;
import com.odcgroup.mdf.ecore.MdfModelElementImpl;
import com.odcgroup.mdf.ecore.lazy.Mdf2EcoreMapperLazy;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfName;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.mdf.metamodel.MdfReverseAssociation;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.dsl.resources.AbstractDSLResource;

/**
 * This is a specific XtextResource implementation for domain models. It strips
 * off unnecessary information after loading the models and it pre-processes the
 * model before serializing it.
 * 
 * @author Kai Kreuzer
 * @author Michael Vorburger (some code from kosyakov) made various adaptions following Xtext 2.6 upgrade 
 */
public class DomainDSLResource extends AbstractDSLResource {
	//private static Logger log = Logger.getLogger(DomainDSLResource.class);
	
	@Inject
	private DomainURIEncoder domainURIEncoder;

	@Inject
	private DomainJvmLinkEncoder domainJvmLinkEncoder;
	
	/**
	 * This method behaves the same way as the implementation of the parent
	 * class. The difference is that all references to the AST are removed at
	 * the end, so that all memory of the AST is released again. This reduces
	 * the overall memory allocation of a model by about 50%. The parse result
	 * is kept in case there were errors during the parsing.
	 * 
	 * Note: Due to DS-4813, the removal is only done for Entities.domain
	 */
	@Override
	protected void updateInternalState(IParseResult parseResult) {
		super.updateInternalState(parseResult);
	}

	// we override this for DS-4813
	@Override
	protected void doLoad(InputStream inputStream, Map<?, ?> options) throws IOException {
		try {
			super.doLoad(inputStream, options);
		} catch(IOException e) {
			if(getURI().lastSegment().equals("Entities.domain") || !e.getMessage().startsWith("Resource has syntactical errors")) {
				throw e;
			}
			// else just ignore it and continue
		}
	}

	@Override
	protected void preProcessModelBeforeSaving(EObject rootObject) {
		if (rootObject instanceof MdfDomainImpl) {
			MdfDomainImpl domain = (MdfDomainImpl) rootObject;
			domain.setMetamodelVersion(OfsCore
					.getCurrentMetaModelVersion("domain"));
			processDocumentations(domain);
			processReverseAssociations(domain);
			processAnnotationOrder(domain);
			//processPropertyOrder(domain);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void processAnnotationOrder(MdfDomainImpl domain) {
		TreeIterator<EObject> contents = domain.eAllContents();
		while (contents.hasNext()) {
			EObject eObj = contents.next();

			// translations should always come first in the list of annotations
			if (eObj instanceof MdfModelElementImpl) {
				MdfModelElementImpl element = (MdfModelElementImpl) eObj;
				List i18nAnnotations = element
						.getAnnotations("http://www.odcgroup.com/mdf/translation");
				element.getAnnotations().removeAll(i18nAnnotations);
				element.getAnnotations().addAll(0, i18nAnnotations);
			}
		}
	}

	/*@SuppressWarnings({ "unchecked", "unused" })
	private void processPropertyOrder(MdfDomainImpl domain) {
		// ordering properties according to spec, see DS-3977
		Iterator<EObject> it = domain.eAllContents();
		while (it.hasNext()) {
			EObject eObj = it.next();
			EList<MdfModelElement> properties = null;
			if (eObj instanceof MdfClass) {
				MdfClass clazz = (MdfClass) eObj;
				properties = (EList<MdfModelElement>) clazz.getProperties();
			}
			if (eObj instanceof MdfDataset) {
				MdfDataset dataset = (MdfDataset) eObj;
				properties = (EList<MdfModelElement>) dataset.getProperties();
			}
			if (properties != null) {
				ECollections.sort(properties,
						new Comparator<MdfModelElement>() {
							public int compare(MdfModelElement e1,
									MdfModelElement e2) {
								return e1.getName().toLowerCase()
										.compareTo(e2.getName().toLowerCase());
							}
						});
			}
		}
	}*/

	private void processDocumentations(MdfDomainImpl domain) {
		cleanDocumentation(domain);
		TreeIterator<EObject> contents = domain.eAllContents();
		while (contents.hasNext()) {
			EObject eObj = contents.next();
			if (eObj instanceof MdfModelElementImpl) {
				MdfModelElementImpl element = (MdfModelElementImpl) eObj;
				if (element.getDocumentation() != null) {
					cleanDocumentation(element);
				}
			}
		}
	}

	private void cleanDocumentation(MdfModelElementImpl element) {
		// if the documentation ends with CR/LF/SPACEs, we remove them as it
		// spoils our DSL layout
		while (element.getDocumentation() != null
				&& (element.getDocumentation().endsWith("\n")
						|| element.getDocumentation().endsWith("\r") || element
						.getDocumentation().endsWith(" "))) {
			String doc = element.getDocumentation();
			element.setDocumentation(doc.substring(0, doc.length() - 1));
		}

		// if there is an empty string, we can actually treat it to be no
		// documentation at all
		if ("".equals(element.getDocumentation())) {
			element.setDocumentation(null);
		}
	}

	private void processReverseAssociations(MdfDomainImpl domain) {
		Map<MdfReverseAssociation, MdfClass> toDelete = new HashMap<MdfReverseAssociation, MdfClass>();
		TreeIterator<EObject> contents = domain.eAllContents();
		while (contents.hasNext()) {
			EObject eObj = contents.next();
			if (eObj instanceof MdfReverseAssociation) {
				// we remove all "reverse" pointers as the serialization will
				// otherwise go in circles
				MdfReverseAssociation revAssoc = (MdfReverseAssociation) eObj;
				EObject container = ((EObject) revAssoc).eContainer();
				if (container instanceof MdfClass) {
					MdfClass parentClass = (MdfClass) container;
					toDelete.put(revAssoc, parentClass);
				}
			}
		}
		for (Entry<MdfReverseAssociation, MdfClass> entry : toDelete.entrySet()) {
			entry.getValue().getProperties().remove(entry.getKey());
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void postProcessModelAfterLoading(EObject rootObject) {
		List<MdfReverseAssociation> reverseAssoc = new ArrayList<MdfReverseAssociation>();
		TreeIterator<EObject> it = rootObject.eAllContents();
		while (it.hasNext()) {
			EObject eObj = it.next();
			if (eObj instanceof MdfReverseAssociation) {
				reverseAssoc.add((MdfReverseAssociation) eObj);
			}
		}
		for (MdfReverseAssociation reverse : reverseAssoc) {
			MdfClass parentClass = (MdfClass) reverse.getParentClass();
			if (parentClass == null)
				continue;
			boolean containsReverse = false;
			// Cannot use the contains method because the container is not
			// always set.
			// We should enforce it in the model... if time do it
			for (Iterator props = parentClass.getProperties().iterator(); props
					.hasNext();) {
				MdfProperty prop = (MdfProperty) props.next();
				if (prop instanceof MdfReverseAssociation
						&& reverse.getName().equals(
								((MdfReverseAssociation) prop).getName())) {
					containsReverse = true;
				}
			}
			if (!containsReverse) {
				parentClass.getProperties().add(
						EcoreUtil.copy((EObject) reverse));
			}
		}
	}
	
	@Override
	public void save(Map<?, ?> options) throws IOException {
//		int retries = 10;
//		while(_getContents().size()==0 && retries-->0) {
//			try {
//				Thread.sleep(100L);
//			} catch (InterruptedException e) {}
//		}
		super.save(options);
	}

	@Override
	protected void postProcessModelAfterSaving(EObject rootObject) {
// TODO Why were we doing this?? This doesn't seem a very good idea nowadays, with extends DerivedStateAwareResource & DomainJvmModelInferrer..
// This would cause the MdfDomain at #0 to be removed and added at the end of the contents list, after JvmGenericType derived state...
// that most likely causes a lot more trouble now than whatever benefit this gave at the time Kai originally did it - let's try without
// @see http://rd.oams.com/browse/DS-7339
//		if(isLoaded()) {
//			_getContents().remove(0);
//			_getContents().add(rootObject);
//		}
	}
	
	@Override
	public EObject getEObject(String uriFragment) {
		if (domainURIEncoder.isBrokenCrossLinkFragment(this, uriFragment)) {
			return null;
		} else if (domainJvmLinkEncoder.isFragment(uriFragment)) {
			String fragment = domainJvmLinkEncoder.decode(uriFragment);
			EObject object = getEObject(fragment);
			if (object == null) {
				return null;
			}
			EObject context = getParseResult().getRootASTElement();
			JvmTypeReference typeReference = domainJvmLinkEncoder.newTypeRef(context, object);
			if (typeReference == null) {
				return null;
			}
			return typeReference.getType();
		} else if (Mdf2EcoreMapperLazy.INSTANCE.isMdfLink(uriFragment)) {
			MdfName mdfName = (MdfName) Mdf2EcoreMapperLazy.INSTANCE.getMdfNameOfMdfLink(uriFragment);
			MdfClassImpl mdfClass = (MdfClassImpl) DomainRepositoryUtil.getMdfEntity(mdfName, this);
			EObject eObject = Mdf2EcoreMapperLazy.INSTANCE.create(mdfClass);
			return eObject;
		} else {
			return super.getEObject(uriFragment);
		}
	}

}

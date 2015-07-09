package com.odcgroup.page.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.parser.IParseResult;

import com.odcgroup.page.metamodel.PropertyCategory;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.Parameter;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Snippet;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.resource.exception.ModelNullException;
import com.odcgroup.page.resource.exception.RootWidgetNullException;
import com.odcgroup.workbench.dsl.resources.AbstractDSLResource;

/**
 * This is a specific XtextResource implementation for page models. It strips off unnecessary information
 * after loading the models and it pre-processes the model before serializing it.
 * 
 * @author Kai Kreuzer
 *
 */
public class PageDSLResource extends AbstractDSLResource {
	
	/**
	 * This method behaves the same way as the implementation of the parent
	 * class. The difference is that all references to the AST are removed at
	 * the end, so that all memory of the AST is released again. This reduces
	 * the overall memory allocation of a model by about 50%. The parse result
	 * is kept in case there were errors during the parsing.
	 */
	@Override
	protected void updateInternalState(IParseResult parseResult) {
		super.updateInternalState(parseResult);

		if (getErrors().isEmpty()) {
			TreeIterator<EObject> allContents = getAllContents();
			while (allContents.hasNext()) {
				EObject eObj = allContents.next();
				Adapter nodeAdapter = null;
				for (Adapter adapter : eObj.eAdapters()) {
					if (adapter instanceof INode) {
						nodeAdapter = adapter;
					}
				}
				if (nodeAdapter != null)
					eObj.eAdapters().remove(nodeAdapter);
			}
			setParseResult(null);
		}
	}

	@Override
	protected void preProcessModelBeforeSaving(EObject rootObject) {
		final EObject clonedRoot = EcoreUtil.copy(rootObject);
		processProperties(clonedRoot);
		processParameters(clonedRoot);
		processDocumentations(clonedRoot);
		
		// for saving, we replace the model with the pre-processed clone
		if(getContents().size()>0) {
			getContents().remove(0);
		}
		getContents().add(clonedRoot);
	}

	/**
	 * When the model is saved, we do some pre-processing of the model to ensure
	 * a nice serialization.
	 */
	@Override
	public void save(final Map<?, ?> options) throws IOException {
		// loop while the model is not loaded (what happens sometimes for whatever reason)
//		int retries = 10;
//		while(getContents().size()==0 && retries-->0) {
//			try {
//				Thread.sleep(100L);
//			} catch (InterruptedException e) {}
//		}
		super.save(options);
	}

	@Override
	public EObject getEObject(String uriFragment) {
		if(uriFragment==null) {
			if(isLoaded && getContents().size()>0) {
				return getContents().get(0);
			} else {
				return null;
			}
		} else {
			return super.getEObject(uriFragment);
		}
	}

	private void processProperties(EObject model) {
		if(model==null) return;
		removePropertiesWithDefaultValue(model);
		orderProperties(model);
	}

	private void orderProperties(EObject model) {
		// ordering properties according to spec, see DS-3918
		Iterator<EObject> it = model.eAllContents();
		while(it.hasNext()) {
			EObject eObj = it.next();
			EList<Property> properties = null;
			if(eObj instanceof Widget) {
				Widget w = (Widget) eObj;
				properties = w.getProperties();
			}
			if(eObj instanceof Event) {
				Event e = (Event) eObj;
				properties = e.getProperties();
			}
			if(eObj instanceof Snippet) {
				Snippet s = (Snippet) eObj;
				properties = s.getProperties();
			}
			if(properties!=null) {
				ECollections.sort(properties, new Comparator<Property>() {
					public int compare(Property p1, Property p2) {
						PropertyCategory cat1 = MetaModelRegistry.getMetaModel().findPropertyType(p1.getLibraryName(), p1.getTypeName()).getCategory();
						PropertyCategory cat2 = MetaModelRegistry.getMetaModel().findPropertyType(p2.getLibraryName(), p2.getTypeName()).getCategory();
						if(cat1==cat2) {
							return p1.getTypeName().compareTo(p2.getTypeName());
						} else {
							return getPriority(cat1).compareTo(getPriority(cat2));
						}
					}

					private Integer getPriority(PropertyCategory cat) {
						switch(cat.getValue()) {
							case PropertyCategory.GENERAL 			: return 1;
							case PropertyCategory.PRESENTATION 		: return 2;
							case PropertyCategory.LIMITATION		: return 3;
							case PropertyCategory.EVENT				: return 4;
							case PropertyCategory.TECHNICAL			: return 5;
							case PropertyCategory.DESCRIPTION		: return 6;
							case PropertyCategory.NONE				: return 7;
						}
						return 10;
					}
				});
			}
		}
	}

	private void removePropertiesWithDefaultValue(EObject model) {
		Iterator<EObject> it = model.eAllContents();
		Set<Property> propertiesToRemove = new HashSet<Property>();
		while(it.hasNext()) {
			EObject eObj = it.next();
			if(eObj instanceof Property) {
				Property property = (Property) eObj;
				String defaultValue = null;
				if(property.getType()!=null) defaultValue = property.getType().getDefaultValue();
				if((property.getValue()==null || property.getValue().equals(defaultValue)) && property.getModel()==null) {
					// we remove all properties, which have the default value
					// one exception are properties, which refer a model (srcIncludes) - they are of course kept
					propertiesToRemove.add(property);
				}
			}
		}
		for(Property p : propertiesToRemove) {
			if(p.eContainer() instanceof Widget) {
				((Widget)p.eContainer()).getProperties().remove(p);
			}
			if(p.eContainer() instanceof Snippet) {
				((Snippet)p.eContainer()).getProperties().remove(p);
			}
			if(p.eContainer() instanceof Event) {
				((Event)p.eContainer()).getProperties().remove(p);
			}
		}
	}

	private void processParameters(EObject model) {
		if(model==null) return;
		Iterator<EObject> it = model.eAllContents();
		Set<Parameter> parametersToRemove = new HashSet<Parameter>();
		while(it.hasNext()) {
			EObject eObj = it.next();
			if(eObj instanceof Parameter) {
				Parameter parameter = (Parameter) eObj;
				if(parameter.getValue()==null || parameter.getValue().trim().isEmpty()) {
					// we remove all empty parameters
					parametersToRemove.add(parameter);
				}
			}
		}
		for(Parameter p : parametersToRemove) {
			if(p.eContainer() instanceof Event) {
				((Event)p.eContainer()).getParameters().remove(p);
			}
		}
	}

	private void processDocumentations(EObject model) {
		TreeIterator<EObject> contents = model.eAllContents();
		while(contents.hasNext()) {
			EObject eObj = contents.next();
			if(eObj instanceof Event) {
				Event event = (Event) eObj;
				if(event.getDescription()!=null) {
					cleanDocumentation(event);
				}
			}
		}
	}

	private void cleanDocumentation(Event event) {
		// if the documentation ends with CR/LF/SPACEs, we remove them as it spoils our DSL layout
		while(event.getDescription()!=null && 
				(event.getDescription().endsWith("\n") || 
				 event.getDescription().endsWith("\r") ||
				 event.getDescription().endsWith(" "))) {
			String doc = event.getDescription();
			event.setDescription(doc.substring(0, doc.length()-1));
		}
		
		// if there is an empty string, we can actually treat it to be no documentation at all
		if("".equals(event.getDescription())) {
			event.setDescription(null);
		}
	}
	
	protected EObject getEObject() {
		return getEObject((String)null);
	}
	
	@Override
	protected void doLoad(InputStream inputStream, Map<?, ?> options) throws IOException {
		super.doLoad(inputStream, options);
		Model model = (Model)getEObject();
		if(model == null) {
			setLoaded(false);
			getContents().clear();
			throw new ModelNullException(uri);
		}
		if(model.getWidget() == null) {
			setLoaded(false);
			getContents().clear();
			throw new RootWidgetNullException(uri);
		}
	}

	@Override
	protected void postProcessModelAfterLoading(EObject rootObject) {
		// STUB: nothing to do
	}
	
	@Override
	protected void postProcessModelAfterSaving(EObject rootObject) {
		// after saving, we put back the original model
		if(isLoaded()) {
			getContents().remove(0);
			getContents().add(rootObject);
		}
	}
}

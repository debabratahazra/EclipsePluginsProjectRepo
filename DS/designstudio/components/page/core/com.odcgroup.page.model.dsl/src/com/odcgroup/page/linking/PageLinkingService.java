package com.odcgroup.page.linking;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.xtext.linking.impl.DefaultLinkingService;
import org.eclipse.xtext.linking.impl.IllegalNodeException;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.impl.LeafNode;
import org.eclipse.xtext.scoping.IScopeProvider;

import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.ModelFactory;
import com.odcgroup.page.model.impl.ModelFactoryImpl;

/**
 * This is a custom linking service implementation for page models.
 * It simply creates a proxy for each target object, so that the
 * resolution happens lazily upon the first access.
 * 
 * @author Kai Kreuzer
 *
 */
public class PageLinkingService extends DefaultLinkingService {

	private ModelFactory modelFactory = new ModelFactoryImpl();

	/**
	 * @return the first element returned from the injected {@link IScopeProvider} which matches the text of the passed
	 *         {@link LeafNode}
	 */
	@Override
	public List<EObject> getLinkedObjects(EObject context, EReference ref, INode node)
			throws IllegalNodeException {
		final EClass requiredType = ref.getEReferenceType();
		if (requiredType == null)
			return Collections.<EObject> emptyList();

		String s = getCrossRefNodeAsString(node);

		if (s != null) {
			EObject result = null;
			result = (EObject) createProxy(ref.getEReferenceType(), s);
			if(result!=null) {
				return Collections.singletonList(result);
			}
		}
		return Collections.emptyList();
	}

	private Model createProxy(EClass klass, String uri) {
		Model model = null;
		InternalEObject eObject = (InternalEObject) modelFactory.create(klass);
		eObject.eSetProxyURI(URI.createURI(uri));
		model = (Model) eObject;
		return model;
	}

}

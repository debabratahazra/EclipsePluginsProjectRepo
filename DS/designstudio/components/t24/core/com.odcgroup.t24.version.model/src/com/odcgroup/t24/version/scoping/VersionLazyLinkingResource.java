package com.odcgroup.t24.version.scoping;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.diagnostics.ExceptionDiagnostic;
import org.eclipse.xtext.linking.impl.IllegalNodeException;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.util.Triple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Sets;
import com.google.inject.Provider;

public class VersionLazyLinkingResource extends LazyLinkingResource {
	
	private Logger log = LoggerFactory.getLogger(VersionLazyLinkingResource.class);
	private LinkedHashSet<Triple<EObject, EReference, INode>> resolving = Sets.newLinkedHashSet();
	
	@Override
	public synchronized EObject getEObject(String uriFragment) {
		try {
			if (getEncoder().isCrossLinkFragment(this, uriFragment)) {
				Triple<EObject, EReference, INode> triple = getEncoder().decode(this, uriFragment);
				try {
					if (!resolving.add(triple))
						return handleCyclicResolution(triple);
					Set<String> unresolveableProxies = getCache().get("UNRESOLVEABLE_PROXIES", this,
							new Provider<Set<String>>() {
								public Set<String> get() {
									return Sets.newHashSet();
								}
							});
					if (unresolveableProxies.contains(uriFragment))
						return null;
					EReference reference = triple.getSecond();
						List<EObject> linkedObjects = getLinkingService().getLinkedObjects(triple.getFirst(), reference,
								triple.getThird());
						
						if (linkedObjects.isEmpty()) {
							if (isUnresolveableProxyCacheable(triple))
								unresolveableProxies.add(uriFragment);
							createAndAddDiagnostic(triple);
							return null;
						}
						if (linkedObjects.size() > 1)
							throw new IllegalStateException("linkingService returned more than one object for fragment "
									+ uriFragment);
						EObject result = linkedObjects.get(0);
						if(result != null) {
							if (!EcoreUtil2.isAssignableFrom(reference.getEReferenceType(), result.eClass())) {
								log.error("An element of type " + result.getClass().getName()
										+ " is not assignable to the reference " + reference.getEContainingClass().getName()
										+ "." + reference.getName());
								if (isUnresolveableProxyCacheable(triple))
									unresolveableProxies.add(uriFragment);
								createAndAddDiagnostic(triple);
								return null;
							
							}
						}
						// remove previously added error markers, since everything should be fine now
						unresolveableProxies.remove(uriFragment);
						removeDiagnostic(triple);
						return result;
				} catch (IllegalNodeException ex){
					createAndAddDiagnostic(triple, ex);
				} finally {
					resolving.remove(triple);
				}
			}
		} catch (RuntimeException e) {
			getErrors().add(new ExceptionDiagnostic(e));
			log.error("resolution of uriFragment '" + uriFragment + "' failed.", e);
			// wrapped because the javaDoc of this method states that WrappedExceptions are thrown
			// logged because EcoreUtil.resolve will ignore any exceptions.
			throw new WrappedException(e);
		}
		return super.getEObject(uriFragment);
	}
}

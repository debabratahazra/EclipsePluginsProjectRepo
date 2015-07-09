package com.odcgroup.workbench.core.xtext;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.OnChangeEvictingCache.CacheAdapter;
import org.eclipse.xtext.util.SimpleAttributeResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Util to remove Xtext Parse Tree Nodes.
 * 
 * Originally thought to be useful to save memory, when node model is not needed
 * (thought to be the case in Generator, but certainly NOT in the UI); *BUT* then
 * realized this *BREAKS* normal fully Xtext-based (as opposed to DS hacked) cross
 * resource reference resolution! :-(
 * 
 * @see http://rd.oams.com/browse/DS-7054
 * @see http://rd.oams.com/browse/DS-6707
 * 
 * @deprecated This idea was a huge mistake & mis-analysis.. do *NOT* use this anymore!
 * (It's currently still used in DS TAP for the Entities.domain.. but should remove that too, some day.)
 * 
 * @see http://blog2.vorburger.ch/2014/01/eclipse-emf-xtext-memory-consumption-in.html
 * 
 * @author Michael Vorburger
 */
@Deprecated
public class NodesZapper {
	private static final Logger logger = LoggerFactory.getLogger(NodesZapper.class);

	public void removeNodeModel(Resource r) {
		if (!(r instanceof XtextResource)) {
			return;
		}
		XtextResource xr = (XtextResource) r;
		// If there are errors or warnings, it's maybe safer not to remove the Node Model
		// as keeping it may make it easier to find the (location of) the source of such errors.
		if (!r.getErrors().isEmpty()) {
			logger.warn("removeNodeModel() will *NOT* remove parse tree nodes, because there are errors on this resource: {}", r.getURI());
			return;
		}
		if (!r.getWarnings().isEmpty()) {
			logger.warn("removeNodeModel() will *NOT* remove parse tree nodes, because there are warnings on this resource: {}", r.getURI());
			return;
		}
		TreeIterator<EObject> allContents = r.getAllContents();
		while (allContents.hasNext()) {
			EObject eObj = allContents.next();
			// We cannot remove during the iteration over the adapters, else
			// we'd get a ConcurrentModificationException, so we do it after:
			List<Adapter> nodeAdaptersToRemove = new ArrayList<Adapter>(1);
			for (Adapter adapter : eObj.eAdapters()) {
				if (adapter instanceof INode) {
					nodeAdaptersToRemove.add(adapter);
					
				} else if (adapter instanceof CacheAdapter) {
					// removing these seems more risk than gain
					// In measure with >10k models, it makes only a tiny difference
					// SO DO NOTHING for these (but don't log below them either)
					
				// cannot do this because DiscardingAdapter is private..
				// } else if (adapter instanceof SimpleAttributeResolver.DiscardingAdapter) {
				} else if (adapter.getClass().getName().contains(SimpleAttributeResolver.class.getName())) {
					// not really sure how much memory this saves (if any), but
					// as this Adapter only reacts to Notification.SET it should
					// be safe to remove it in a read-only Generation scenario
					nodeAdaptersToRemove.add(adapter);
					
				} else {
					logger.info("Found unexpected non-INode/CacheAdapter Adapter, ignoring it (not removing; still consumes memory): {} in {}", adapter.getClass().getName(), r.getURI());
				}
			}
			if (!nodeAdaptersToRemove.isEmpty()) {
				for (Adapter adapter : nodeAdaptersToRemove) {
					eObj.eAdapters().remove(adapter);
				}
				nodeAdaptersToRemove.clear();
			}
		}
		xr.setParseResult(null);
	}
	
}

package com.odcgroup.workbench.ui.xtext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.builder.resourceloader.IResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.workbench.core.helper.Timer;

/**
 * IResourceLoader Sorter which sorts by model types, in a predefined order (by which our models typically interdepend).
 *
 * @author Michael Vorburger
 */
public class ByResourceExtensionLoaderSorter implements IResourceLoader.Sorter {
	private static final Logger logger = LoggerFactory.getLogger(ByResourceExtensionLoaderSorter.class);

	// Could make this configurable, via a Feature Switch (but don't have to)..
	private static final String[] modelExtensionOrder = { "domain", "version", "enquiry", "eson", "menu", "rim" };

	@Override
	public Collection<URI> sort(Collection<URI> uris) {
		Timer timer = new Timer().start();
		Map<String, List<URI>> map = new HashMap<String, List<URI>>();

		for (URI uri : uris) {
			String ext = uri.fileExtension();
			List<URI> list = map.get(ext);
			if (list == null) {
				list = new ArrayList<URI>(uris.size() / modelExtensionOrder.length);
				map.put(ext, list);
			}
			list.add(uri);
		}

		ArrayList<URI> r = new ArrayList(uris.size());
		for (String ext : modelExtensionOrder) {
			add(map, r, ext, true);
		}

		for (String unknownExt : map.keySet()) {
			logger.warn("Unknown model extension, adding these at the end of the otherwise explicitly sorted list: {}", unknownExt);
			add(map, r, unknownExt, false);
		}

		if (r.size() != uris.size())
			throw new IllegalStateException("Bug, we lost some URIs!! :-(");
		logger.info("Sorted {} URIs in {}", uris.size(), timer.stopAndText());
		return r;
	}

	private void add(Map<String, List<URI>> map, ArrayList<URI> r, String ext, boolean remove) {
		List<URI> urisWithThisExt = map.get(ext);
		if (urisWithThisExt != null) {
			if (remove)
				map.remove(ext);
			sortAlphabetically(urisWithThisExt);
			r.addAll(urisWithThisExt);
		}
	}

	private void sortAlphabetically(List<URI> urisWithThisExt) {
		Collections.sort(urisWithThisExt, new Comparator<URI>() {

			@Override
			public int compare(URI o1, URI o2) {
				String s1 = toString(o1);
				String s2 = toString(o2);
				return s1.compareTo(s2);
			}

			private String toString(URI uri) {
				return uri.toString();
			}

		});
	}

}

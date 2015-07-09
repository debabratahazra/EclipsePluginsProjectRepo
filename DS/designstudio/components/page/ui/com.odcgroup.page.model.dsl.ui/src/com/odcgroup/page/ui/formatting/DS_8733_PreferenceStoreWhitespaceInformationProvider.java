package com.odcgroup.page.ui.formatting;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.ui.editor.formatting.PreferenceStoreWhitespaceInformationProvider;
import org.eclipse.xtext.ui.resource.IStorage2UriMapper;
import org.eclipse.xtext.util.Pair;

import com.google.inject.Inject;

/**
 * PreferenceStoreWhitespaceInformationProvider which does not
 * senseLineDelimiter(), because that's causing ResourceException: Resource
 * '...' is not local. at least on Eclipse platform 3.7.2 (perhaps different on
 * Luna; it's possible this can be removed again after the Luna upgrade,
 * do try; TODO DS-8776).
 *
 * @see DS-8733 (http://rd.oams.com/browse/DS-8733)
 * @see DS-8776 (http://rd.oams.com/browse/DS-8776)
 *
 * @author Michael Vorburger
 */
public class DS_8733_PreferenceStoreWhitespaceInformationProvider extends PreferenceStoreWhitespaceInformationProvider {

	// copy/pasted from PreferenceStoreWhitespaceInformationProvider because private there
	@Inject private IStorage2UriMapper storage2UriMapper;
	@Inject private IWorkspace workspace;

	// copy/pasted from PreferenceStoreWhitespaceInformationProvider, just call to senseLineDelimiter() commented out
	protected String getLineSeparatorPreference(URI uri) {
//		if (uri.isPlatformResource()) {
//			IFile file = workspace.getRoot().getFile(new Path(uri.toPlatformString(true)));
//			String delimiter = senseLineDelimiter(file);
//			if (delimiter != null) return delimiter;
//		}
		IProject project = null;
		if (uri.isPlatformResource()) {
			project = workspace.getRoot().getProject(uri.segment(1));
		} else {
			for (Pair<IStorage, IProject> storage : storage2UriMapper.getStorages(uri)) {
				project = storage.getSecond();
				break;
			}
		}
		if (project != null) {
			String result = getLineSeparatorPreference(new ProjectScope(project));
			if (result != null)
				return result;
		}
		@SuppressWarnings("all")
		String result = getLineSeparatorPreference(new InstanceScope());
		if (result != null)
			return result;
		return System.getProperty("line.separator");
	}

}

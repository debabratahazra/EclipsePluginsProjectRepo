/**
 * 
 */
package com.odcgroup.t24.version.editor.ui.providers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.fieldassist.SimpleContentProposalProvider;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.swt.widgets.Text;
import org.eclipse.xtext.naming.QualifiedName;

import com.odcgroup.t24.version.editor.ui.dialogs.ScreenSelectionDialog;
import com.odcgroup.t24.version.naming.VersionNameProvider;
import com.odcgroup.t24.version.versionDSL.Version;
import com.odcgroup.workbench.core.repository.ModelURIConverter;

/**
 * @author arajeshwari
 *
 */
public class AutoCompletionTextProvider {
	
	private Text text;
	private IProject iProject;
	private EditingDomain editingDomain;
	private Version version;
	private String[] defaultProposals;

	public AutoCompletionTextProvider(Text text, IProject iProject, EditingDomain editingDomain) {
		this.text = text;
		this.iProject = iProject;
		this.editingDomain = editingDomain;
	}
	
	/**
	 * @param text
	 * @param value
	 */
	public void setAutoCompletion(Text text, String value) {
		try {
			ContentProposalAdapter adapter = null;
			defaultProposals = getAllProposals(value);
			SimpleContentProposalProvider scp = new SimpleContentProposalProvider(
					defaultProposals);
			scp.setProposals(defaultProposals);
			scp.setFiltering(true);
			String KEY_PRESS = "Ctrl+Space";
			KeyStroke ks = KeyStroke.getInstance(KEY_PRESS);
			adapter = new ContentProposalAdapter(text,	new TextContentAdapter(), scp, ks, null);
			adapter.setPropagateKeys(true);
			adapter.setProposalAcceptanceStyle(ContentProposalAdapter.PROPOSAL_REPLACE);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * @param text
	 * @return
	 */
	private String[] getAllProposals(String text) {
			Object[] screens = null;
			List<String> list = new ArrayList<String>();
			String[] proposals = null;
				ScreenSelectionDialog dialog = new ScreenSelectionDialog(this.text.getShell(), false, iProject);
				try {
					screens = dialog.listFilesAsArray((IResource) iProject, true);
				} catch (CoreException e) {
					// LOGGER
					e.printStackTrace();
				}
				if(screens != null){
					for (int i = 0; i < screens.length; i++) {
						if(screens[i] instanceof IResource) {
							URI resourceURI = ModelURIConverter.createModelURI((IResource) screens[i]);
							Resource resource = editingDomain.getResourceSet().getResource(resourceURI, true);
							if(!resource.getContents().isEmpty())
							version = ((Version) resource.getContents().get(0));
							else{
								version = null;
							}
						}
							VersionNameProvider vnp = new VersionNameProvider();
							QualifiedName fullyQualifiedName = vnp.getFullyQualifiedName(version);
							if(fullyQualifiedName != null){
								String fullName = null;
								List<String> segments = fullyQualifiedName.getSegments();
								if(segments.size() == 3)
									fullName = segments.get(0).concat(":").concat(segments.get(1)).concat(",").concat(segments.get(2));
								else{
									fullName = segments.get(0).concat(":").concat(segments.get(1));
								} 
								list.add(fullName.toString());
							}
						}
					proposals = new String[list.size()];
					for (int i = 0; i < list.size(); i++) {
						if(list.get(i) != null)
						proposals[i] = list.get(i);
					}
					}
				
				return proposals;
	}

	public String[] getDefaultProposals() {
		return defaultProposals;
	}
}

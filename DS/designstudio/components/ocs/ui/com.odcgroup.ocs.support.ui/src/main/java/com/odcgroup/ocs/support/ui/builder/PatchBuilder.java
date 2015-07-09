package com.odcgroup.ocs.support.ui.builder;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.transform.TransformerException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.odcgroup.ocs.support.OCSPluginActivator;
import com.odcgroup.ocs.support.ui.OCSSupportUICore;
import com.odcgroup.ocs.support.xpatch.Patch;
import com.odcgroup.ocs.support.xpatch.Patcher;


public class PatchBuilder extends IncrementalProjectBuilder {

	public static final String BUILDER_ID = "com.odcgroup.eds.tools.xpatchbuilder";

	private static final String MARKER_TYPE = "com.odcgroup.eds.tools.xpatchproblem";

	private static final String CONFIG_ORIG = "install/setup/working/J2EEappli/server/config";

	private static final String CONFIG_PATCH = "install/setup/working/J2EEappli/cfg-patch";

	private static final String CONFIG_REPO = "repository";

	protected void clean(final IProgressMonitor monitor) throws CoreException {
		monitor.beginTask("Cleaning project", IProgressMonitor.UNKNOWN);

		try {
			getProject().accept(new IResourceVisitor() {

				public boolean visit(IResource resource) throws CoreException {
					if (isPatch(resource)) {
						File target = getTargetFile(resource);
						File patched = getPatchedTargetFile(target);

						if (!patched.delete()) {
							String msg = "Could not delete patched file "
									+ patched;
							OCSSupportUICore.getDefault().logWarning(msg, null);
							IMarker marker = resource.createMarker(MARKER_TYPE);
							marker.setAttribute(IMarker.MESSAGE, msg);
							marker.setAttribute(IMarker.SEVERITY,
									IMarker.SEVERITY_WARNING);
							marker.setAttribute(IMarker.TRANSIENT, true);
						}
					}

					monitor.worked(1);
					return !monitor.isCanceled();
				}
			});
		} finally {
			monitor.done();
		}
	}

	/**
	 * @see org.eclipse.core.resources.IncrementalProjectBuilder#build(int,
	 *      java.util.Map, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@SuppressWarnings("rawtypes")
	protected IProject[] build(int kind, Map args, IProgressMonitor monitor)
			throws CoreException {

		if (kind == FULL_BUILD) {
			fullBuild(monitor);
		} else {
			IResourceDelta delta = getDelta(getProject());
			if (delta == null) {
				fullBuild(monitor);
			} else {
				incrementalBuild(delta, monitor);
			}
		}

		return null;
	}

	private void fullBuild(final IProgressMonitor monitor) throws CoreException {
		monitor.beginTask("Building project", IProgressMonitor.UNKNOWN);

		try {
			final Map<File, Patcher> patchers = new HashMap<File, Patcher>();

			getProject().accept(new IResourceVisitor() {

				public boolean visit(IResource resource) throws CoreException {
					if (isPatch(resource)) {
						File target = getTargetFile(resource);
						if (target != null)
							doPatch(patchers, resource, target);
					}

					monitor.worked(1);
					return !monitor.isCanceled();
				}
			});

			save(patchers.values(), monitor);
		} finally {
			monitor.done();
		}
	}

	private void incrementalBuild(IResourceDelta delta,
			final IProgressMonitor monitor) throws CoreException {
		monitor.beginTask("Building delta", delta.getAffectedChildren().length);
		try {
			final List<File> changed = new ArrayList<File>();
			final Map<File, Patcher> patchers = new HashMap<File, Patcher>();

			delta.accept(new IResourceDeltaVisitor() {

				/**
				 * @see org.eclipse.core.resources.IResourceDeltaVisitor#visit(org.eclipse.core.resources.IResourceDelta)
				 */
				public boolean visit(IResourceDelta delta) throws CoreException {
					IResource resource = delta.getResource();

					if (isPatch(resource)) {
						switch (delta.getKind()) {
						case IResourceDelta.ADDED:
						case IResourceDelta.CHANGED:
						case IResourceDelta.REMOVED:
							File target = getTargetFile(resource);
							if (target != null)
								changed.add(target);
							break;
						}
					}
					monitor.worked(1);
					return !monitor.isCanceled();
				}
			});

			getProject().accept(new IResourceVisitor() {

				public boolean visit(IResource resource) throws CoreException {
					if (isPatch(resource)) {
						File target = getTargetFile(resource);
						if (target != null && changed.contains(target)) {
							doPatch(patchers, resource, target);
						}
					}

					monitor.worked(1);
					return !monitor.isCanceled();
				}
			});

			save(patchers.values(), monitor);
		} finally {
			monitor.done();
		}
	}

	private void save(Collection<Patcher> patchers, IProgressMonitor monitor)
			throws CoreException {
		for (Patcher patcher : patchers) {
			try {
				// Apply other patches on the installation
				final String targetName = getBasename(patcher.getFile()
						.getName());

				final Set<String> appliedPatchNames = new HashSet<String>();
				for (Patch p : patcher.getAppliedPatches()) {
					appliedPatchNames.add(p.getPatchfile().getName());
				}

				String instDir = OCSPluginActivator.getDefault().getPreferenceManager()
						.getInstallDirectory();
				File cfgPatch = new File(new File(instDir), CONFIG_PATCH);
				File[] otherPatches = cfgPatch.listFiles(new FileFilter() {

					public boolean accept(File file) {
						String baseName = getBasename(file.getName());
						return baseName.contains(targetName)
								&& !appliedPatchNames.contains(file.getName());
					}
				});

				for (File f : otherPatches) {
					patcher.apply(new Patch(f));
					monitor.worked(1);
				}

				File patchedFile = getPatchedTargetFile(patcher.getFile());
				patchedFile.getParentFile().mkdirs();
				patcher.saveAs(patchedFile);
				monitor.worked(1);
			} catch (IOException e) {
				createMarker(getProject(), e);
			} catch (DOMException e) {
				createMarker(getProject(), e);
			} catch (TransformerException e) {
				createMarker(getProject(), e);
			} catch (SAXException e) {
				createMarker(getProject(), e);
			}
		}
	}

	private File getTargetFile(IResource resource) {
		String baseName = getBasename(resource.getName());
		String instDir = OCSPluginActivator.getDefault().getPreferenceManager()
				.getInstallDirectory();
		
		File configOrig = new File(new File(instDir), CONFIG_ORIG);
		if (configOrig.exists()) {
			File[] configFiles = configOrig.listFiles(new FileFilter() {
				public boolean accept(File file) {
					return file.getName().endsWith(".xml");
				}
			});

			for (File f : configFiles) {
				String fname = getBasename(f.getName());

				if (baseName.contains(fname)) {
					return f;
				}
			}
		}

		return null;
	}

	private File getPatchedTargetFile(File file) {
		String fname = file.getName();

		// Backward compatibility with old naming convention
		if (fname.startsWith("oams")) {
			fname = fname.substring(4);
		}

		String instDir = OCSPluginActivator.getDefault().getPreferenceManager()
				.getInstallDirectory();
		return new File(new File(new File(instDir), CONFIG_REPO), fname);
	}

	private String getBasename(String fname) {
		int pos = fname.lastIndexOf('.');

		if (pos > 0) {
			fname = fname.substring(0, pos);
		}

		// Backward compatibility with old naming convention
		if (fname.startsWith("oams")) {
			fname = fname.substring(4);
		}

		return fname;
	}

	private boolean isPatch(IResource resource) {
		return (resource instanceof IFile && resource.getName().endsWith(
				".patch"));
	}

	private void createMarker(IResource resource, Exception e)
			throws CoreException {
		OCSSupportUICore.getDefault().logError(e.getMessage(), e);
		IMarker marker = resource.createMarker(MARKER_TYPE);
		marker.setAttribute(IMarker.MESSAGE, e.getMessage());
		marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
		marker.setAttribute(IMarker.TRANSIENT, false);
		if (e instanceof SAXParseException) {
			marker.setAttribute(IMarker.LINE_NUMBER, ((SAXParseException) e)
					.getLineNumber());
		}
	}

	private void doPatch(final Map<File, Patcher> patchers, IResource resource,
			File target) throws CoreException {
		resource.deleteMarkers(MARKER_TYPE, false, IResource.DEPTH_ZERO);

		try {
			Patcher patcher = patchers.get(target);

			if (patcher == null) {
				patcher = new Patcher(target);
				patchers.put(target, patcher);
			}

			Patch patch = new Patch(resource.getLocation().toFile());
			patcher.apply(patch);
		} catch (DOMException e) {
			createMarker(resource, e);
		} catch (SAXException e) {
			createMarker(resource, e);
		} catch (IOException e) {
			createMarker(resource, e);
		} catch (TransformerException e) {
			createMarker(resource, e);
		}
	}

}

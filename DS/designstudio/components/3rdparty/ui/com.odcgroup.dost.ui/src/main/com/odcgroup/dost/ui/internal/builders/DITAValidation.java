package com.odcgroup.dost.ui.internal.builders;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.core.runtime.content.IContentTypeManager;
import org.eclipse.wst.common.uriresolver.internal.ExtensibleURIResolver;
import org.eclipse.wst.common.uriresolver.internal.provisional.URIResolver;
import org.xml.sax.Attributes;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import com.odcgroup.dost.ui.internal.Activator;

public class DITAValidation extends IncrementalProjectBuilder {

	public static final String BUILDER_ID = "com.odcgroup.dost.ui.DITAValidation";

	private static final String MARKER_TYPE = "com.odcgroup.dost.ui.DITAProblem";

	private SAXParserFactory parserFactory;
	private final IContentType ditaContentType;

	public DITAValidation() {
		IContentTypeManager ctm = Platform.getContentTypeManager();
		ditaContentType = ctm.getContentType("org.xml.dita.any");
	}

	/**
	 * @see org.eclipse.core.resources.IncrementalProjectBuilder#build(int,
	 *      java.util.Map, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@SuppressWarnings("unchecked")
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

	protected void fullBuild(final IProgressMonitor monitor)
			throws CoreException {
		IProject project = getProject();
		monitor.beginTask("Validating project " + project.getName(),
				IProgressMonitor.UNKNOWN);

		try {
			project.accept(new DITAResourceVisitor(monitor));
		} finally {
			monitor.done();
		}
	}

	protected void incrementalBuild(IResourceDelta delta,
			IProgressMonitor monitor) throws CoreException {
		IProject project = getProject();
		monitor.beginTask("Validating project " + project.getName(),
				IProgressMonitor.UNKNOWN);

		try {
			// the visitor does the work.
			delta.accept(new DITAResourceVisitor(monitor));
		} finally {
			monitor.done();
		}
	}

	protected boolean isDITAFile(IResource resource) {
		IContentTypeManager ctm = Platform.getContentTypeManager();
		IContentType actualContent = ctm.findContentTypeFor(resource.getName());
		return (actualContent != null)
				&& actualContent.isKindOf(ditaContentType);
	}

	protected void checkXML(final IFile file) {
		DITAContentHandler reporter = new DITAContentHandler(file);
		try {
			SAXParser parser = getParser();
			XMLReader reader = parser.getXMLReader();

			reader.setEntityResolver(new EntityResolver() {

				private final URIResolver resolver = new ExtensibleURIResolver();

				public InputSource resolveEntity(String publicId,
						String systemId) throws SAXException, IOException {
					String source = resolver.resolve(file.getLocation()
							.toOSString(), publicId, systemId);

					if (source == null) {
						return null;
					} else {
						InputSource src = new InputSource(source);
						src.setPublicId(publicId);
						return src;
					}
				}
			});

			reader.setContentHandler(reporter);
			reader.setErrorHandler(reporter);

			InputSource source = new InputSource(file.getContents());
			source.setSystemId(file.getProjectRelativePath().toString());
			reader.parse(source);
		} catch (CoreException e) {
			Activator.getDefault().getLog().log(e.getStatus());
		} catch (IOException e) {
			Activator.getDefault().log(IStatus.ERROR, e);
		} catch (SAXException e) {
			Activator.getDefault().log(IStatus.ERROR, e);
		} catch (ParserConfigurationException e) {
			Activator.getDefault().log(IStatus.ERROR, e);
		}
	}

	private void addMarker(IFile file, String message, int lineNumber,
			int severity) {
		try {
			IMarker marker = file.createMarker(MARKER_TYPE);
			marker.setAttribute(IMarker.MESSAGE, message);
			marker.setAttribute(IMarker.SEVERITY, severity);
			if (lineNumber == -1) {
				lineNumber = 1;
			}
			marker.setAttribute(IMarker.LINE_NUMBER, lineNumber);
		} catch (CoreException e) {
			Activator.getDefault().getLog().log(e.getStatus());
		}
	}

	private void deleteMarkers(IResource resource) {
		try {
			resource.deleteMarkers(MARKER_TYPE, false, IResource.DEPTH_ZERO);
		} catch (CoreException e) {
			Activator.getDefault().getLog().log(e.getStatus());
		}
	}

	private SAXParser getParser() throws ParserConfigurationException,
			SAXException {
		if (parserFactory == null) {
			parserFactory = SAXParserFactory.newInstance();
			// The parser will validate the document only if a grammar is
			// specified.
			parserFactory.setFeature(
					"http://apache.org/xml/features/validation/dynamic", true);
			// Perform namespace processing
			parserFactory.setFeature("http://xml.org/sax/features/namespaces",
					true);
			// Turn on XML Schema validation by inserting an XML Schema
			// validator into the pipeline.
			parserFactory.setFeature(
					"http://apache.org/xml/features/validation/schema", true);
			// Enable full schema grammar constraint checking
			parserFactory
					.setFeature(
							"http://apache.org/xml/features/validation/schema-full-checking",
							true);
		}

		return parserFactory.newSAXParser();
	}

	private class DITAResourceVisitor implements IResourceDeltaVisitor,
			IResourceVisitor {
		private final IProgressMonitor monitor;

		public DITAResourceVisitor(IProgressMonitor monitor) {
			this.monitor = monitor;
		}

		/**
		 * @see org.eclipse.core.resources.IResourceDeltaVisitor#visit(org.eclipse.core.resources.IResourceDelta)
		 */
		public boolean visit(IResourceDelta delta) throws CoreException {
			IResource resource = delta.getResource();
			deleteMarkers(resource);

			if (resource.getName().startsWith(".")) {
				return false;
			} else if (isDITAFile(resource)) {
				IFile file = (IFile) resource;
				deleteMarkers(file);

				switch (delta.getKind()) {
				case IResourceDelta.ADDED:
				case IResourceDelta.CHANGED:
					// handle changed resource
					monitor.subTask("Validating " + resource.getName());
					checkXML(file);
					monitor.worked(1);
					break;

				case IResourceDelta.REMOVED:
					// handle removed resource
					break;
				}
			}

			// return true to continue visiting children.
			return true;
		}

		public boolean visit(IResource resource) {
			deleteMarkers(resource);

			if (resource.getName().startsWith(".")) {
				return false;
			} else if (isDITAFile(resource)) {
				monitor.subTask("Validating " + resource.getName());
				IFile file = (IFile) resource;
				deleteMarkers(file);
				checkXML(file);
				monitor.worked(1);
			}

			// return true to continue visiting children.
			return true;
		}

	}

	private class DITAContentHandler extends DefaultHandler {

		private final IFile file;
		private Locator locator;

		public DITAContentHandler(IFile file) {
			this.file = file;
		}

		public void setDocumentLocator(Locator locator) {
			this.locator = locator;
		}

		public void startElement(String uri, String localname, String qName,
				Attributes attributes) throws SAXException {
			super.startElement(uri, localname, qName, attributes);
			String ref = attributes.getValue("href");

			if(ref==null) ref = attributes.getValue("conref");
			
			if (ref != null) {
				String scope = attributes.getValue("scope");

				if (scope == null || scope.equals("local")) {
					int pos = ref.lastIndexOf('#');

					if (pos > -1) {
						ref = ref.substring(0, pos);
					}

					try {
						URL baseLocation = file.getProjectRelativePath()
								.toFile().toURL();
						URL target = new URL(baseLocation, ref);

						if ("file".equals(target.getProtocol())) {
							IPath hrefPath = null;

							if (ref.startsWith("/")) {
								hrefPath = new Path(ref);
							} else {
								hrefPath = file.getParent()
										.getProjectRelativePath().append(ref);
							}

							IFile hrefFile = getProject().getFile(hrefPath);

							if (!hrefFile.exists()) {
								DITAValidation.this.addMarker(file, ref
										+ " could not be found", locator
										.getLineNumber(),
										IMarker.SEVERITY_ERROR);
							}
						}
					} catch (MalformedURLException e) {
						DITAValidation.this
								.addMarker(file, ref + " is an invalid URL",
										locator.getLineNumber(),
										IMarker.SEVERITY_ERROR);
					}
				}
			}
		}

		public void error(SAXParseException exception) throws SAXException {
			addMarker(exception, IMarker.SEVERITY_ERROR);
		}

		public void fatalError(SAXParseException exception) throws SAXException {
			addMarker(exception, IMarker.SEVERITY_ERROR);
		}

		public void warning(SAXParseException exception) throws SAXException {
			addMarker(exception, IMarker.SEVERITY_WARNING);
		}

		private void addMarker(SAXParseException e, int severity) {
			DITAValidation.this.addMarker(file, e.getMessage(), e
					.getLineNumber(), severity);
		}
	}

}

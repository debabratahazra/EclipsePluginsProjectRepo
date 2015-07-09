package com.odcgroup.dost.ui.internal.editors;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.Calendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.ErrorListener;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.LocationEvent;
import org.eclipse.swt.browser.LocationListener;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.ui.part.MultiPageEditorSite;
import org.eclipse.wst.common.uriresolver.internal.ExtensibleURIResolver;
import org.eclipse.wst.sse.ui.StructuredTextEditor;
import org.eclipse.wst.xml.core.internal.provisional.contenttype.ContentTypeIdForXML;
import org.osgi.framework.Bundle;
import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.odcgroup.dost.ui.internal.Activator;

@SuppressWarnings("restriction")
public class TopicEditor extends MultiPageEditorPart implements
		IResourceChangeListener {

	final public static String EDITOR_ID = "com.odcgroup.dost.ui.internal.editors.TopicEditor";
	
	/** The text editor used in page 0. */
	private StructuredTextEditor editor;

	/** The browser widget used in page 2. */
	private Browser browser;

	private int sourcePageIndex;
	private int previewPageIndex;

	private PreviewErrorHandler errorHandler = new PreviewErrorHandler();
	private ExtensibleURIResolver uriResolver = new ExtensibleURIResolver();

	/**
	 * Creates a multi-page editor example.
	 */
	public TopicEditor() {
		super();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
	}

	@Override
	protected IEditorSite createSite(IEditorPart page) {
		IEditorSite site = null;

		if (page == editor) {
			site = new MultiPageEditorSite(this, page) {
				public String getId() {
					// Sets this ID so nested editor is configured for XML
					// source
					return ContentTypeIdForXML.ContentTypeID_XML + ".source"; //$NON-NLS-1$;
				}
			};
		} else {
			site = super.createSite(page);
		}
		return site;
	}

	/**
	 * Creates page 0 of the multi-page editor, which contains a text editor.
	 */
	void createSourcePage() {
		try {
			editor = new StructuredTextEditor();
			sourcePageIndex = addPage(editor, getEditorInput());
			setPageText(sourcePageIndex, "Source");
		} catch (PartInitException e) {
			ErrorDialog.openError(getSite().getShell(),
					"Error creating nested text editor", null, e.getStatus());
		}
	}

	/**
	 * Creates page 2 of the multi-page editor, which shows the sorted text.
	 */
	void createPreviewPage() {
		browser = createBrowser();
		previewPageIndex = addPage(browser);
		setPageText(previewPageIndex, "Preview");
	}

	protected Browser createBrowser() {
		Browser newBrowser = new Browser(getContainer(), SWT.NONE);
		newBrowser.addLocationListener(new LocationListener() {
			public void changing(LocationEvent event) {
				String location = event.location;
				
				if(event.location.equals("about:blank")) return; // this is the entry page, so display it as it is
				
				event.doit = false; // the browser should stay where it is
				if(!location.startsWith("http") && location.endsWith(".html")) {	
					location = location.substring(0, location.length()-"html".length()) + "dita";
					IFile[] files = ResourcesPlugin.getWorkspace().getRoot().findFilesForLocationURI(new Path(location).toFile().toURI());
					if(files.length>0 && files[0].exists()) {
						IFile file = files[0];
						IEditorInput input = new FileEditorInput(file);
						try {
							PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(input, EDITOR_ID);
						} catch (PartInitException e) {
							Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
							ErrorDialog.openError(shell, "Error opening editor", e.getLocalizedMessage(), new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Error opening editor for " + file.getLocation().toString(), e));
						}
					} else {
						Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
						ErrorDialog.openError(shell, "Error opening editor", "Editor cannot be opened", new Status(IStatus.ERROR, Activator.PLUGIN_ID, "File does not exist: " + location, null));
					}
					return;
				}
			}
			public void changed(LocationEvent event) {
				// do nothing here
			}
		});
		return newBrowser;
	}

	/**
	 * Creates the pages of the multi-page editor.
	 */
	protected void createPages() {
		createSourcePage();
		createPreviewPage();
	}

	/**
	 * The <code>MultiPageEditorPart</code> implementation of this
	 * <code>IWorkbenchPart</code> method disposes all nested editors.
	 * Subclasses may extend.
	 */
	public void dispose() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
		super.dispose();
	}

	/**
	 * Saves the multi-page editor's document.
	 */
	public void doSave(IProgressMonitor monitor) {
		getEditor(0).doSave(monitor);
	}

	/**
	 * Saves the multi-page editor's document as another file. Also updates the
	 * text for page 0's tab, and updates this multi-page editor's input to
	 * correspond to the nested editor's.
	 */
	public void doSaveAs() {
		IEditorPart editor = getEditor(0);
		editor.doSaveAs();
		setPageText(0, editor.getTitle());
		setInput(editor.getEditorInput());
	}

	public void gotoMarker(IMarker marker) {
		setActivePage(0);
		IDE.gotoMarker(getEditor(0), marker);
	}

	/**
	 * The <code>MultiPageEditorExample</code> implementation of this method
	 * checks that the input is an instance of <code>IFileEditorInput</code>.
	 */
	public void init(IEditorSite site, IEditorInput editorInput)
			throws PartInitException {
		if (!(editorInput instanceof IFileEditorInput)) {
			throw new PartInitException(
					"Invalid Input: Must be IFileEditorInput");
		}

		setPartName(editorInput.getName());
		setTitleToolTip(editorInput.getToolTipText());
		super.init(site, editorInput);
	}

	/**
	 * @see org.eclipse.ui.part.EditorPart#isSaveAsAllowed()
	 */
	public boolean isSaveAsAllowed() {
		return true;
	}

	/**
	 * Calculates the contents of page 2 when previewPageIndexit is activated.
	 */
	protected void pageChange(int newPageIndex) {
		super.pageChange(newPageIndex);

		if (newPageIndex == previewPageIndex) {
			Runnable job = new Runnable() {

				public void run() {
					generatePreview();
				}
			};
			
			browser.getDisplay().asyncExec(job);
			BusyIndicator.showWhile(browser.getDisplay(), job);
		}
	}

	private void generatePreview() {
		IFileEditorInput input = (IFileEditorInput) getEditorInput();
		IFile file = input.getFile();
		final String baseLocation = file.getParent().getLocationURI()
				.toString().substring("file:/".length());
		IDocument doc = editor.getDocumentProvider().getDocument(input);
		String text = doc.get();

		StringWriter writer = new StringWriter();

		try {
			Bundle dost = Platform.getBundle("com.odcgroup.dost");
			URL css = dost.getEntry("/dost/resource/");
			css = FileLocator.resolve(css);
			css = FileLocator.toFileURL(css);

			DocumentBuilderFactory bf = DocumentBuilderFactory.newInstance();
			bf.setValidating(false);

			DocumentBuilder builder = bf.newDocumentBuilder();
			builder.setEntityResolver(new EntityResolver() {

				public InputSource resolveEntity(String publicId,
						String systemId) throws SAXException, IOException {
					String location = uriResolver.resolve(baseLocation,
							publicId, systemId);
					return (location == null) ? null
							: new InputSource(location);
				}
			});
			builder.setErrorHandler(errorHandler);
			Document dom = builder
					.parse(new InputSource(new StringReader(text)));

			Templates templates = getXSLTemplates();

			Transformer transformer = templates.newTransformer();
			transformer.setErrorListener(errorHandler);
			transformer.setParameter("CSSPATH", css.toString());
			transformer.setParameter("CSS", "../../style/odyssey.css");
			transformer.setParameter("FILENAME", file.getName());
			transformer.setParameter("DITAEXT", "." + file.getFileExtension());
			transformer.setParameter("WORKDIR", baseLocation + "/");
			transformer.setParameter("YEAR", Integer.toString(Calendar
					.getInstance().get(Calendar.YEAR)));
			transformer.transform(new DOMSource(dom), new StreamResult(writer));
			writer.flush();
		} catch (IOException e) {
			log(IStatus.ERROR, e);
		} catch (TransformerException e) {
			log(IStatus.ERROR, e);
		} catch (ParserConfigurationException e) {
			log(IStatus.ERROR, e);
		} catch (SAXException e) {
		}

		browser.setText(writer.getBuffer().toString());
	}

	protected Templates getXSLTemplates() throws TransformerException,
			IOException {
		return Activator.getDefault().getTopicPreviewTemplates();
	}

	private void log(int severity, Exception e) {
		Activator.getDefault().log(severity, e);
	}

	/**
	 * Closes all project files on project close.
	 */
	public void resourceChanged(final IResourceChangeEvent event) {
		if (event.getType() == IResourceChangeEvent.PRE_CLOSE) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					IWorkbenchPage[] pages = getSite().getWorkbenchWindow()
							.getPages();
					for (int i = 0; i < pages.length; i++) {
						if (((FileEditorInput) editor.getEditorInput())
								.getFile().getProject().equals(
										event.getResource())) {
							IEditorPart editorPart = pages[i].findEditor(editor
									.getEditorInput());
							pages[i].closeEditor(editorPart, true);
						}
					}
				}
			});
		}
	}

	private final class PreviewErrorHandler implements ErrorHandler,
			ErrorListener {

		public void error(SAXParseException e) throws SAXException {
			log(IStatus.ERROR, e);
		}

		public void fatalError(SAXParseException e) throws SAXException {
			log(IStatus.ERROR, e);
			throw e;
		}

		public void warning(SAXParseException e) throws SAXException {
			log(IStatus.WARNING, e);
		}

		public void error(TransformerException e) throws TransformerException {
		}

		public void fatalError(TransformerException e)
				throws TransformerException {
			log(IStatus.ERROR, e);
			throw e;
		}

		public void warning(TransformerException e) throws TransformerException {
			log(IStatus.WARNING, e);
		}

	}

}

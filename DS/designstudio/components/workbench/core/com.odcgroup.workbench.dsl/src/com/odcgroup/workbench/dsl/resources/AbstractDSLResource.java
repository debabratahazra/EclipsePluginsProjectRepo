package com.odcgroup.workbench.dsl.resources;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.nodemodel.impl.RootNode;
import org.eclipse.xtext.resource.DerivedStateAwareResource;

/**
 * This is an abstract implementation of an EMF Resource with DSL serialization. It should be used by all concrete
 * implementations of Resource, which make use of DSL/Xtext.
 * 
 * @author Kai Kreuzer
 */
abstract public class AbstractDSLResource extends /* NOT just XtextResource, BUT */ DerivedStateAwareResource /* which extends LazyLinkingResource! */ {

	public static final String DSL_FILE_HEADER_COMMENT = "# UTF-8\n";
	
	public AbstractDSLResource() {
		super();
	}

	/**
	 * All code in here, and subclasses, MUST use this _getContents() instead of
	 * the original getContents() during save & load hooks... if you do not, then
	 * call getContents(), which is @Override in DerivedStateAwareResource, will
	 * (re) installDerivedState() during save, and all hell will break loose...
	 * @see http://rd.oams.com/browse/DS-7339
	 * @author Michael Vorburger
	 */
	protected EList<EObject> _getContents() {
		return doGetContents();
	}
	
	@Override
	public EObject getEObject(String uriFragment) {
		if(uriFragment==null) {
			final EList<EObject> _contents = _getContents();
			if(_contents.size()>0) {
				return _contents.get(0);
			} else {
				return null;
			}
		} else {
			return super.getEObject(uriFragment);
		}
	}

	@Override
	protected void doLoad(InputStream inputStream, Map<?, ?> options) throws IOException {
		super.doLoad(inputStream, options);
		if(!getErrors().isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for (Diagnostic error : getErrors()) {
				sb.append(error.getMessage() + "\n");
			}
			throw new IOException("Resource has syntactical errors:\n" + sb.toString());
		}
		final EObject rootObject = _getContents().get(0);
		postProcessModelAfterLoading(rootObject);		
	}

	/**
	 * When the model is saved, we do some pre-processing of the model to ensure
	 * a nice serialization.
	 */
	@Override
	public void save(Map<?, ?> options) throws IOException {
		final EObject rootObject = _getContents().get(0);
		preProcessModelBeforeSaving(rootObject);
		super.save(options);
		postProcessModelAfterSaving(rootObject);
	}

	@Override
	public void doSave(OutputStream outputStream, Map<?, ?> options)
			throws IOException {
		addUTF8HeaderIfNecessary(outputStream);
		super.doSave(outputStream, options);
	}

	// DS-4353
	private void addUTF8HeaderIfNecessary(OutputStream outputStream) throws IOException {
		final EList<EObject> _contents = _getContents();
		if(_contents.size()>0) {
			EObject root = _contents.get(0);
			RootNode rootNodeAdapter = (RootNode) EcoreUtil.getAdapter(root.eAdapters(), RootNode.class);
			if(rootNodeAdapter==null || !rootNodeAdapter.getFirstChild().getText().startsWith(DSL_FILE_HEADER_COMMENT)) {
				outputStream.write(DSL_FILE_HEADER_COMMENT.getBytes());
			}
		}
	}

	protected abstract void preProcessModelBeforeSaving(EObject rootObject);
	
	protected abstract void postProcessModelAfterLoading(EObject rootObject);

	protected abstract void postProcessModelAfterSaving(EObject rootObject);
}
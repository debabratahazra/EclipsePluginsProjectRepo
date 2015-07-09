package com.odcgroup.t24.menu.resource;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;
import org.eclipse.xtext.nodemodel.impl.RootNode;

import com.odcgroup.t24.menu.menu.MenuRoot;
import com.odcgroup.workbench.core.OfsCore;

/**
 * @author pkk
 *
 */
public class MenuResource extends LazyLinkingResource /*AbstractDSLResource*/ {

	public static final String DSL_FILE_HEADER_COMMENT = "# UTF-8\n";

	@Override
	public void doSave(OutputStream outputStream, Map<?, ?> options)
			throws IOException {
		addUTF8HeaderIfNecessary(outputStream);
		super.doSave(outputStream, options);
	}

	// DS-4353
	private void addUTF8HeaderIfNecessary(OutputStream outputStream) throws IOException {
		if(getContents().size()>0) {
			EObject root = getContents().get(0);
			RootNode rootNodeAdapter = (RootNode) EcoreUtil.getAdapter(root.eAdapters(), RootNode.class);
			if(rootNodeAdapter==null || !rootNodeAdapter.getFirstChild().getText().startsWith(DSL_FILE_HEADER_COMMENT)) {
				outputStream.write(DSL_FILE_HEADER_COMMENT.getBytes());
			}
		}
	}

	protected void preProcessModelBeforeSaving(EObject rootObject) {
		if (rootObject instanceof MenuRoot) {
			MenuRoot root = (MenuRoot) rootObject;
			root.setMetamodelVersion(OfsCore.getCurrentMetaModelVersion("menu"));
		}
	}

}

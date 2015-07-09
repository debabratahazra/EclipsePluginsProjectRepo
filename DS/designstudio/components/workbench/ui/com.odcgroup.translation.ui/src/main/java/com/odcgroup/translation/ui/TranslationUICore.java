package com.odcgroup.translation.ui;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Composite;
import org.osgi.framework.BundleContext;

import com.odcgroup.translation.ui.editor.model.ITranslationCollectorProvider;
import com.odcgroup.translation.ui.internal.TranslationTableManager;
import com.odcgroup.translation.ui.internal.TranslationUIManager;
import com.odcgroup.translation.ui.internal.views.TranslationViewer;
import com.odcgroup.translation.ui.views.ITranslationTableProvider;
import com.odcgroup.translation.ui.views.ITranslationViewer;
import com.odcgroup.workbench.ui.AbstractUIActivator;

/**
 * The activator class controls the plug-in life cycle
 * 
 * @author atr
 */
public class TranslationUICore extends AbstractUIActivator {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.translation.ui";
	

	private static List<ITranslationCollectorProvider> translationCollectors;

	// The shared instance
	private static TranslationUICore plugin;

	/**
	 * The constructor
	 */
	public TranslationUICore() {
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		translationCollectors = TranslationUIManager.loadCollectorProviders();
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		translationCollectors.clear();
		translationCollectors = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static TranslationUICore getDefault() {
		return plugin;
	}

	/**
	 * Retrieves a Boolean value indicating whether tracing is enabled for the
	 * specified debug option.
	 * 
	 * @return Whether tracing is enabled for the debug option of the plug-in.
	 * @param option The debug option for which to determine trace enablement.
	 * 
	 */
	 public static boolean shouldTrace(String option) {
		boolean trace = false;
		if(getDefault().isDebugging()) {
			trace = isTraceOptionEnabled(option);
		}
		return trace;
	}		
	 
	/**
	 * @param parent
	 * @return
	 */
	public static ITranslationViewer getTranslationViewer(IProject project, Composite parent) {
		ITranslationTableProvider tableProvider = 
			new TranslationTableManager(project).getTranslationTableProvider();
		return new TranslationViewer(project, parent, tableProvider);
	}
	
	/**
	 * @return translation collector providers
	 */
	public static final List<ITranslationCollectorProvider> getTranslationCollectors() {
		return translationCollectors;
	}
	
	/**
	 * Returns an image descriptor for the image file at the given plug-in
	 * relative path
	 * 
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

}

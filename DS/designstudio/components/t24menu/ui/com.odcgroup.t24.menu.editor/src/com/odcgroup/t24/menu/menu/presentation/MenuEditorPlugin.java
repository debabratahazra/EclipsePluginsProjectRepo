/**
 */
package com.odcgroup.t24.menu.menu.presentation;

//import com.odcgroup.edge.t24ui.provider.T24uiEditPlugin;
//import com.odcgroup.mdf.ecore.provider.MdfEditPlugin;
//import com.odcgroup.t24.enquiry.provider.EnquiryEditPlugin;
//import com.odcgroup.t24.version.versionDSL.provider.VersionDSLEditPlugin;
//import com.odcgroup.translation.translationDsl.provider.TranslationDslEditPlugin;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.ui.EclipseUIPlugin;
import org.eclipse.emf.common.util.ResourceLocator;

/**
 * This is the central singleton for the Menu editor plugin.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public final class MenuEditorPlugin extends EMFPlugin {
	
	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.t24.menu.editor"; //$NON-NLS-1$

	/** The context id */
	public static final String CONTEXT_ID = "com.odcgroup.t24.menu.editor.context";

	/**
	 * Keep track of the singleton.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final MenuEditorPlugin INSTANCE = new MenuEditorPlugin();
	
	/**
	 * Keep track of the singleton.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static Implementation plugin;

	/**
	 * Create the instance.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MenuEditorPlugin() {
		super
			(new ResourceLocator [] {
//				MdfEditPlugin.INSTANCE,
//				T24uiEditPlugin.INSTANCE,
//				EnquiryEditPlugin.INSTANCE,
//				VersionDSLEditPlugin.INSTANCE,
//				TranslationDslEditPlugin.INSTANCE,
			});
	}

	/**
	 * Returns the singleton instance of the Eclipse plugin.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the singleton instance.
	 * @generated
	 */
	@Override
	public ResourceLocator getPluginResourceLocator() {
		return plugin;
	}
	
	/**
	 * Returns the singleton instance of the Eclipse plugin.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the singleton instance.
	 * @generated
	 */
	public static Implementation getPlugin() {
		return plugin;
	}
	
	/**
	 * The actual implementation of the Eclipse <b>Plugin</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static class Implementation extends EclipseUIPlugin {
		/**
		 * Creates an instance.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public Implementation() {
			super();
	
			// Remember the static instance.
			//
			plugin = this;
		}
	}

}

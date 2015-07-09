/**
 */
package com.odcgroup.t24.menu.menu.provider;

//import com.odcgroup.edge.t24ui.provider.T24uiEditPlugin;
//import com.odcgroup.mdf.ecore.provider.MdfEditPlugin;
//import com.odcgroup.t24.enquiry.provider.EnquiryEditPlugin;
//import com.odcgroup.t24.version.versionDSL.provider.VersionDSLEditPlugin;
//import com.odcgroup.translation.translationDsl.provider.TranslationDslEditPlugin;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.ResourceLocator;

/**
 * This is the central singleton for the Menu edit plugin.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public final class MenuEditPlugin extends EMFPlugin {
	/**
	 * Keep track of the singleton.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final MenuEditPlugin INSTANCE = new MenuEditPlugin();

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
	public MenuEditPlugin() {
		super
		  (new ResourceLocator [] {
//		     MdfEditPlugin.INSTANCE,
//		     T24uiEditPlugin.INSTANCE,
//		     EnquiryEditPlugin.INSTANCE,
//		     VersionDSLEditPlugin.INSTANCE,
//		     TranslationDslEditPlugin.INSTANCE,
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
	public static class Implementation extends EclipsePlugin {
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

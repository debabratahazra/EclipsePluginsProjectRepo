/**
 */
package com.odcgroup.t24.server.properties.server;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

import com.odcgroup.t24.server.properties.server.impl.ServerPackageImpl;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see Server.ServerFactory
 * @model kind="package"
 * @generated
 */
public interface ServerPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "Server";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http:///server";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ServerPackage eINSTANCE = ServerPackageImpl.init();

	/**
	 * The meta object id for the '{@link Server.impl.ServerImpl <em>Server</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see Server.impl.ServerImpl
	 * @see Server.impl.ServerPackageImpl#getServer()
	 * @generated
	 */
	int SERVER = 0;

	/**
	 * The feature id for the '<em><b>Username</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVER__USERNAME = 0;

	/**
	 * The feature id for the '<em><b>Password</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVER__PASSWORD = 1;

	/**
	 * The feature id for the '<em><b>Branch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVER__BRANCH = 2;

	/**
	 * The feature id for the '<em><b>Host</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVER__HOST = 3;

	/**
	 * The feature id for the '<em><b>Env Username</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVER__ENV_USERNAME = 4;

	/**
	 * The feature id for the '<em><b>Env Password</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVER__ENV_PASSWORD = 5;

	/**
	 * The feature id for the '<em><b>Port</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVER__PORT = 6;

	/**
	 * The feature id for the '<em><b>Ofs ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVER__OFS_ID = 7;

	/**
	 * The feature id for the '<em><b>Projects</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVER__PROJECTS = 8;

	/**
	 * The feature id for the '<em><b>Protocol</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVER__PROTOCOL = 9;

	/**
	 * The feature id for the '<em><b>Ostype</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVER__OSTYPE = 10;

	/**
	 * The feature id for the '<em><b>Tafchome</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVER__TAFCHOME = 11;

	/**
	 * The number of structural features of the '<em>Server</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVER_FEATURE_COUNT = 12;


	/**
	 * Returns the meta object for class '{@link Server.Server <em>Server</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Server</em>'.
	 * @see Server.Server
	 * @generated
	 */
	EClass getServer();

	/**
	 * Returns the meta object for the attribute '{@link Server.Server#getUsername <em>Username</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Username</em>'.
	 * @see Server.Server#getUsername()
	 * @see #getServer()
	 * @generated
	 */
	EAttribute getServer_Username();

	/**
	 * Returns the meta object for the attribute '{@link Server.Server#getPassword <em>Password</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Password</em>'.
	 * @see Server.Server#getPassword()
	 * @see #getServer()
	 * @generated
	 */
	EAttribute getServer_Password();

	/**
	 * Returns the meta object for the attribute '{@link Server.Server#getBranch <em>Branch</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Branch</em>'.
	 * @see Server.Server#getBranch()
	 * @see #getServer()
	 * @generated
	 */
	EAttribute getServer_Branch();

	/**
	 * Returns the meta object for the attribute '{@link Server.Server#getHost <em>Host</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Host</em>'.
	 * @see Server.Server#getHost()
	 * @see #getServer()
	 * @generated
	 */
	EAttribute getServer_Host();

	/**
	 * Returns the meta object for the attribute '{@link Server.Server#getEnvUsername <em>Env Username</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Env Username</em>'.
	 * @see Server.Server#getEnvUsername()
	 * @see #getServer()
	 * @generated
	 */
	EAttribute getServer_EnvUsername();

	/**
	 * Returns the meta object for the attribute '{@link Server.Server#getEnvPassword <em>Env Password</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Env Password</em>'.
	 * @see Server.Server#getEnvPassword()
	 * @see #getServer()
	 * @generated
	 */
	EAttribute getServer_EnvPassword();

	/**
	 * Returns the meta object for the attribute '{@link Server.Server#getPort <em>Port</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Port</em>'.
	 * @see Server.Server#getPort()
	 * @see #getServer()
	 * @generated
	 */
	EAttribute getServer_Port();

	/**
	 * Returns the meta object for the attribute '{@link Server.Server#getOfsID <em>Ofs ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ofs ID</em>'.
	 * @see Server.Server#getOfsID()
	 * @see #getServer()
	 * @generated
	 */
	EAttribute getServer_OfsID();

	/**
	 * Returns the meta object for the attribute list '{@link Server.Server#getProjects <em>Projects</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Projects</em>'.
	 * @see Server.Server#getProjects()
	 * @see #getServer()
	 * @generated
	 */
	EAttribute getServer_Projects();

	/**
	 * Returns the meta object for the attribute '{@link Server.Server#getProtocol <em>Protocol</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Protocol</em>'.
	 * @see Server.Server#getProtocol()
	 * @see #getServer()
	 * @generated
	 */
	EAttribute getServer_Protocol();

	/**
	 * Returns the meta object for the attribute '{@link Server.Server#getOstype <em>Ostype</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ostype</em>'.
	 * @see Server.Server#getOstype()
	 * @see #getServer()
	 * @generated
	 */
	EAttribute getServer_Ostype();

	/**
	 * Returns the meta object for the attribute '{@link Server.Server#getTafchome <em>Tafchome</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Tafchome</em>'.
	 * @see Server.Server#getTafchome()
	 * @see #getServer()
	 * @generated
	 */
	EAttribute getServer_Tafchome();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ServerFactory getServerFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link Server.impl.ServerImpl <em>Server</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see Server.impl.ServerImpl
		 * @see Server.impl.ServerPackageImpl#getServer()
		 * @generated
		 */
		EClass SERVER = eINSTANCE.getServer();

		/**
		 * The meta object literal for the '<em><b>Username</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SERVER__USERNAME = eINSTANCE.getServer_Username();

		/**
		 * The meta object literal for the '<em><b>Password</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SERVER__PASSWORD = eINSTANCE.getServer_Password();

		/**
		 * The meta object literal for the '<em><b>Branch</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SERVER__BRANCH = eINSTANCE.getServer_Branch();

		/**
		 * The meta object literal for the '<em><b>Host</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SERVER__HOST = eINSTANCE.getServer_Host();

		/**
		 * The meta object literal for the '<em><b>Env Username</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SERVER__ENV_USERNAME = eINSTANCE.getServer_EnvUsername();

		/**
		 * The meta object literal for the '<em><b>Env Password</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SERVER__ENV_PASSWORD = eINSTANCE.getServer_EnvPassword();

		/**
		 * The meta object literal for the '<em><b>Port</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SERVER__PORT = eINSTANCE.getServer_Port();

		/**
		 * The meta object literal for the '<em><b>Ofs ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SERVER__OFS_ID = eINSTANCE.getServer_OfsID();

		/**
		 * The meta object literal for the '<em><b>Projects</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SERVER__PROJECTS = eINSTANCE.getServer_Projects();

		/**
		 * The meta object literal for the '<em><b>Protocol</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SERVER__PROTOCOL = eINSTANCE.getServer_Protocol();

		/**
		 * The meta object literal for the '<em><b>Ostype</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SERVER__OSTYPE = eINSTANCE.getServer_Ostype();

		/**
		 * The meta object literal for the '<em><b>Tafchome</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SERVER__TAFCHOME = eINSTANCE.getServer_Tafchome();

	}

} //ServerPackage

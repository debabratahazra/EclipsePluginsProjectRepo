/**
 */
package com.odcgroup.t24.server.properties.server;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Server</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link Server.Server#getUsername <em>Username</em>}</li>
 *   <li>{@link Server.Server#getPassword <em>Password</em>}</li>
 *   <li>{@link Server.Server#getBranch <em>Branch</em>}</li>
 *   <li>{@link Server.Server#getHost <em>Host</em>}</li>
 *   <li>{@link Server.Server#getEnvUsername <em>Env Username</em>}</li>
 *   <li>{@link Server.Server#getEnvPassword <em>Env Password</em>}</li>
 *   <li>{@link Server.Server#getPort <em>Port</em>}</li>
 *   <li>{@link Server.Server#getOfsID <em>Ofs ID</em>}</li>
 *   <li>{@link Server.Server#getProjects <em>Projects</em>}</li>
 *   <li>{@link Server.Server#getProtocol <em>Protocol</em>}</li>
 *   <li>{@link Server.Server#getOstype <em>Ostype</em>}</li>
 *   <li>{@link Server.Server#getTafchome <em>Tafchome</em>}</li>
 * </ul>
 * </p>
 *
 * @see Server.ServerPackage#getServer()
 * @model
 * @generated
 */
public interface Server extends EObject {
	/**
	 * Returns the value of the '<em><b>Username</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Username</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Username</em>' attribute.
	 * @see #setUsername(String)
	 * @see Server.ServerPackage#getServer_Username()
	 * @model
	 * @generated
	 */
	String getUsername();

	/**
	 * Sets the value of the '{@link Server.Server#getUsername <em>Username</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Username</em>' attribute.
	 * @see #getUsername()
	 * @generated
	 */
	void setUsername(String value);

	/**
	 * Returns the value of the '<em><b>Password</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Password</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Password</em>' attribute.
	 * @see #setPassword(String)
	 * @see Server.ServerPackage#getServer_Password()
	 * @model
	 * @generated
	 */
	String getPassword();

	/**
	 * Sets the value of the '{@link Server.Server#getPassword <em>Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Password</em>' attribute.
	 * @see #getPassword()
	 * @generated
	 */
	void setPassword(String value);

	/**
	 * Returns the value of the '<em><b>Branch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Branch</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Branch</em>' attribute.
	 * @see #setBranch(String)
	 * @see Server.ServerPackage#getServer_Branch()
	 * @model
	 * @generated
	 */
	String getBranch();

	/**
	 * Sets the value of the '{@link Server.Server#getBranch <em>Branch</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Branch</em>' attribute.
	 * @see #getBranch()
	 * @generated
	 */
	void setBranch(String value);

	/**
	 * Returns the value of the '<em><b>Host</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Host</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Host</em>' attribute.
	 * @see #setHost(String)
	 * @see Server.ServerPackage#getServer_Host()
	 * @model
	 * @generated
	 */
	String getHost();

	/**
	 * Sets the value of the '{@link Server.Server#getHost <em>Host</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Host</em>' attribute.
	 * @see #getHost()
	 * @generated
	 */
	void setHost(String value);

	/**
	 * Returns the value of the '<em><b>Env Username</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Env Username</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Env Username</em>' attribute.
	 * @see #setEnvUsername(String)
	 * @see Server.ServerPackage#getServer_EnvUsername()
	 * @model
	 * @generated
	 */
	String getEnvUsername();

	/**
	 * Sets the value of the '{@link Server.Server#getEnvUsername <em>Env Username</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Env Username</em>' attribute.
	 * @see #getEnvUsername()
	 * @generated
	 */
	void setEnvUsername(String value);

	/**
	 * Returns the value of the '<em><b>Env Password</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Env Password</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Env Password</em>' attribute.
	 * @see #setEnvPassword(String)
	 * @see Server.ServerPackage#getServer_EnvPassword()
	 * @model
	 * @generated
	 */
	String getEnvPassword();

	/**
	 * Sets the value of the '{@link Server.Server#getEnvPassword <em>Env Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Env Password</em>' attribute.
	 * @see #getEnvPassword()
	 * @generated
	 */
	void setEnvPassword(String value);

	/**
	 * Returns the value of the '<em><b>Port</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Port</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Port</em>' attribute.
	 * @see #setPort(String)
	 * @see Server.ServerPackage#getServer_Port()
	 * @model
	 * @generated
	 */
	String getPort();

	/**
	 * Sets the value of the '{@link Server.Server#getPort <em>Port</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Port</em>' attribute.
	 * @see #getPort()
	 * @generated
	 */
	void setPort(String value);

	/**
	 * Returns the value of the '<em><b>Ofs ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ofs ID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ofs ID</em>' attribute.
	 * @see #setOfsID(String)
	 * @see Server.ServerPackage#getServer_OfsID()
	 * @model
	 * @generated
	 */
	String getOfsID();

	/**
	 * Sets the value of the '{@link Server.Server#getOfsID <em>Ofs ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ofs ID</em>' attribute.
	 * @see #getOfsID()
	 * @generated
	 */
	void setOfsID(String value);

	/**
	 * Returns the value of the '<em><b>Projects</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Projects</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Projects</em>' attribute list.
	 * @see Server.ServerPackage#getServer_Projects()
	 * @model
	 * @generated
	 */
	EList<String> getProjects();

	/**
	 * Returns the value of the '<em><b>Protocol</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Protocol</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Protocol</em>' attribute.
	 * @see #setProtocol(String)
	 * @see Server.ServerPackage#getServer_Protocol()
	 * @model
	 * @generated
	 */
	String getProtocol();

	/**
	 * Sets the value of the '{@link Server.Server#getProtocol <em>Protocol</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Protocol</em>' attribute.
	 * @see #getProtocol()
	 * @generated
	 */
	void setProtocol(String value);

	/**
	 * Returns the value of the '<em><b>Ostype</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ostype</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ostype</em>' attribute.
	 * @see #setOstype(String)
	 * @see Server.ServerPackage#getServer_Ostype()
	 * @model
	 * @generated
	 */
	String getOstype();

	/**
	 * Sets the value of the '{@link Server.Server#getOstype <em>Ostype</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ostype</em>' attribute.
	 * @see #getOstype()
	 * @generated
	 */
	void setOstype(String value);

	/**
	 * Returns the value of the '<em><b>Tafchome</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tafchome</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tafchome</em>' attribute.
	 * @see #setTafchome(String)
	 * @see Server.ServerPackage#getServer_Tafchome()
	 * @model
	 * @generated
	 */
	String getTafchome();

	/**
	 * Sets the value of the '{@link Server.Server#getTafchome <em>Tafchome</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tafchome</em>' attribute.
	 * @see #getTafchome()
	 * @generated
	 */
	void setTafchome(String value);

} // Server

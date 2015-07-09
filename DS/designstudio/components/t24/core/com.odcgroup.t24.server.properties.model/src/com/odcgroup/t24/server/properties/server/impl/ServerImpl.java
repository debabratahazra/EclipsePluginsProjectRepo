/**
 */
package com.odcgroup.t24.server.properties.server.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

import com.odcgroup.t24.server.properties.server.Server;
import com.odcgroup.t24.server.properties.server.ServerPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Server</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link Server.impl.ServerImpl#getUsername <em>Username</em>}</li>
 *   <li>{@link Server.impl.ServerImpl#getPassword <em>Password</em>}</li>
 *   <li>{@link Server.impl.ServerImpl#getBranch <em>Branch</em>}</li>
 *   <li>{@link Server.impl.ServerImpl#getHost <em>Host</em>}</li>
 *   <li>{@link Server.impl.ServerImpl#getEnvUsername <em>Env Username</em>}</li>
 *   <li>{@link Server.impl.ServerImpl#getEnvPassword <em>Env Password</em>}</li>
 *   <li>{@link Server.impl.ServerImpl#getPort <em>Port</em>}</li>
 *   <li>{@link Server.impl.ServerImpl#getOfsID <em>Ofs ID</em>}</li>
 *   <li>{@link Server.impl.ServerImpl#getProjects <em>Projects</em>}</li>
 *   <li>{@link Server.impl.ServerImpl#getProtocol <em>Protocol</em>}</li>
 *   <li>{@link Server.impl.ServerImpl#getOstype <em>Ostype</em>}</li>
 *   <li>{@link Server.impl.ServerImpl#getTafchome <em>Tafchome</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ServerImpl extends EObjectImpl implements Server {
	/**
	 * The default value of the '{@link #getUsername() <em>Username</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUsername()
	 * @generated
	 * @ordered
	 */
	protected static final String USERNAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getUsername() <em>Username</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUsername()
	 * @generated
	 * @ordered
	 */
	protected String username = USERNAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getPassword() <em>Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPassword()
	 * @generated
	 * @ordered
	 */
	protected static final String PASSWORD_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPassword() <em>Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPassword()
	 * @generated
	 * @ordered
	 */
	protected String password = PASSWORD_EDEFAULT;

	/**
	 * The default value of the '{@link #getBranch() <em>Branch</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBranch()
	 * @generated
	 * @ordered
	 */
	protected static final String BRANCH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBranch() <em>Branch</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBranch()
	 * @generated
	 * @ordered
	 */
	protected String branch = BRANCH_EDEFAULT;

	/**
	 * The default value of the '{@link #getHost() <em>Host</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHost()
	 * @generated
	 * @ordered
	 */
	protected static final String HOST_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getHost() <em>Host</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHost()
	 * @generated
	 * @ordered
	 */
	protected String host = HOST_EDEFAULT;

	/**
	 * The default value of the '{@link #getEnvUsername() <em>Env Username</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEnvUsername()
	 * @generated
	 * @ordered
	 */
	protected static final String ENV_USERNAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEnvUsername() <em>Env Username</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEnvUsername()
	 * @generated
	 * @ordered
	 */
	protected String envUsername = ENV_USERNAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getEnvPassword() <em>Env Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEnvPassword()
	 * @generated
	 * @ordered
	 */
	protected static final String ENV_PASSWORD_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEnvPassword() <em>Env Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEnvPassword()
	 * @generated
	 * @ordered
	 */
	protected String envPassword = ENV_PASSWORD_EDEFAULT;

	/**
	 * The default value of the '{@link #getPort() <em>Port</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPort()
	 * @generated
	 * @ordered
	 */
	protected static final String PORT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPort() <em>Port</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPort()
	 * @generated
	 * @ordered
	 */
	protected String port = PORT_EDEFAULT;

	/**
	 * The default value of the '{@link #getOfsID() <em>Ofs ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOfsID()
	 * @generated
	 * @ordered
	 */
	protected static final String OFS_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getOfsID() <em>Ofs ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOfsID()
	 * @generated
	 * @ordered
	 */
	protected String ofsID = OFS_ID_EDEFAULT;

	/**
	 * The cached value of the '{@link #getProjects() <em>Projects</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProjects()
	 * @generated
	 * @ordered
	 */
	protected EList<String> projects;

	/**
	 * The default value of the '{@link #getProtocol() <em>Protocol</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProtocol()
	 * @generated
	 * @ordered
	 */
	protected static final String PROTOCOL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getProtocol() <em>Protocol</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProtocol()
	 * @generated
	 * @ordered
	 */
	protected String protocol = PROTOCOL_EDEFAULT;

	/**
	 * The default value of the '{@link #getOstype() <em>Ostype</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOstype()
	 * @generated
	 * @ordered
	 */
	protected static final String OSTYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getOstype() <em>Ostype</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOstype()
	 * @generated
	 * @ordered
	 */
	protected String ostype = OSTYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getTafchome() <em>Tafchome</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTafchome()
	 * @generated
	 * @ordered
	 */
	protected static final String TAFCHOME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTafchome() <em>Tafchome</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTafchome()
	 * @generated
	 * @ordered
	 */
	protected String tafchome = TAFCHOME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ServerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ServerPackage.Literals.SERVER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUsername(String newUsername) {
		String oldUsername = username;
		username = newUsername;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ServerPackage.SERVER__USERNAME, oldUsername, username));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPassword(String newPassword) {
		String oldPassword = password;
		password = newPassword;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ServerPackage.SERVER__PASSWORD, oldPassword, password));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getBranch() {
		return branch;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBranch(String newBranch) {
		String oldBranch = branch;
		branch = newBranch;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ServerPackage.SERVER__BRANCH, oldBranch, branch));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getHost() {
		return host;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHost(String newHost) {
		String oldHost = host;
		host = newHost;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ServerPackage.SERVER__HOST, oldHost, host));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getEnvUsername() {
		return envUsername;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEnvUsername(String newEnvUsername) {
		String oldEnvUsername = envUsername;
		envUsername = newEnvUsername;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ServerPackage.SERVER__ENV_USERNAME, oldEnvUsername, envUsername));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getEnvPassword() {
		return envPassword;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEnvPassword(String newEnvPassword) {
		String oldEnvPassword = envPassword;
		envPassword = newEnvPassword;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ServerPackage.SERVER__ENV_PASSWORD, oldEnvPassword, envPassword));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPort() {
		return port;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPort(String newPort) {
		String oldPort = port;
		port = newPort;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ServerPackage.SERVER__PORT, oldPort, port));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getOfsID() {
		return ofsID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOfsID(String newOfsID) {
		String oldOfsID = ofsID;
		ofsID = newOfsID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ServerPackage.SERVER__OFS_ID, oldOfsID, ofsID));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getProjects() {
		if (projects == null) {
			projects = new EDataTypeUniqueEList<String>(String.class, this, ServerPackage.SERVER__PROJECTS);
		}
		return projects;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getProtocol() {
		return protocol;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProtocol(String newProtocol) {
		String oldProtocol = protocol;
		protocol = newProtocol;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ServerPackage.SERVER__PROTOCOL, oldProtocol, protocol));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getOstype() {
		return ostype;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOstype(String newOstype) {
		String oldOstype = ostype;
		ostype = newOstype;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ServerPackage.SERVER__OSTYPE, oldOstype, ostype));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTafchome() {
		return tafchome;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTafchome(String newTafchome) {
		String oldTafchome = tafchome;
		tafchome = newTafchome;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ServerPackage.SERVER__TAFCHOME, oldTafchome, tafchome));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ServerPackage.SERVER__USERNAME:
				return getUsername();
			case ServerPackage.SERVER__PASSWORD:
				return getPassword();
			case ServerPackage.SERVER__BRANCH:
				return getBranch();
			case ServerPackage.SERVER__HOST:
				return getHost();
			case ServerPackage.SERVER__ENV_USERNAME:
				return getEnvUsername();
			case ServerPackage.SERVER__ENV_PASSWORD:
				return getEnvPassword();
			case ServerPackage.SERVER__PORT:
				return getPort();
			case ServerPackage.SERVER__OFS_ID:
				return getOfsID();
			case ServerPackage.SERVER__PROJECTS:
				return getProjects();
			case ServerPackage.SERVER__PROTOCOL:
				return getProtocol();
			case ServerPackage.SERVER__OSTYPE:
				return getOstype();
			case ServerPackage.SERVER__TAFCHOME:
				return getTafchome();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ServerPackage.SERVER__USERNAME:
				setUsername((String)newValue);
				return;
			case ServerPackage.SERVER__PASSWORD:
				setPassword((String)newValue);
				return;
			case ServerPackage.SERVER__BRANCH:
				setBranch((String)newValue);
				return;
			case ServerPackage.SERVER__HOST:
				setHost((String)newValue);
				return;
			case ServerPackage.SERVER__ENV_USERNAME:
				setEnvUsername((String)newValue);
				return;
			case ServerPackage.SERVER__ENV_PASSWORD:
				setEnvPassword((String)newValue);
				return;
			case ServerPackage.SERVER__PORT:
				setPort((String)newValue);
				return;
			case ServerPackage.SERVER__OFS_ID:
				setOfsID((String)newValue);
				return;
			case ServerPackage.SERVER__PROJECTS:
				getProjects().clear();
				getProjects().addAll((Collection<? extends String>)newValue);
				return;
			case ServerPackage.SERVER__PROTOCOL:
				setProtocol((String)newValue);
				return;
			case ServerPackage.SERVER__OSTYPE:
				setOstype((String)newValue);
				return;
			case ServerPackage.SERVER__TAFCHOME:
				setTafchome((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ServerPackage.SERVER__USERNAME:
				setUsername(USERNAME_EDEFAULT);
				return;
			case ServerPackage.SERVER__PASSWORD:
				setPassword(PASSWORD_EDEFAULT);
				return;
			case ServerPackage.SERVER__BRANCH:
				setBranch(BRANCH_EDEFAULT);
				return;
			case ServerPackage.SERVER__HOST:
				setHost(HOST_EDEFAULT);
				return;
			case ServerPackage.SERVER__ENV_USERNAME:
				setEnvUsername(ENV_USERNAME_EDEFAULT);
				return;
			case ServerPackage.SERVER__ENV_PASSWORD:
				setEnvPassword(ENV_PASSWORD_EDEFAULT);
				return;
			case ServerPackage.SERVER__PORT:
				setPort(PORT_EDEFAULT);
				return;
			case ServerPackage.SERVER__OFS_ID:
				setOfsID(OFS_ID_EDEFAULT);
				return;
			case ServerPackage.SERVER__PROJECTS:
				getProjects().clear();
				return;
			case ServerPackage.SERVER__PROTOCOL:
				setProtocol(PROTOCOL_EDEFAULT);
				return;
			case ServerPackage.SERVER__OSTYPE:
				setOstype(OSTYPE_EDEFAULT);
				return;
			case ServerPackage.SERVER__TAFCHOME:
				setTafchome(TAFCHOME_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ServerPackage.SERVER__USERNAME:
				return USERNAME_EDEFAULT == null ? username != null : !USERNAME_EDEFAULT.equals(username);
			case ServerPackage.SERVER__PASSWORD:
				return PASSWORD_EDEFAULT == null ? password != null : !PASSWORD_EDEFAULT.equals(password);
			case ServerPackage.SERVER__BRANCH:
				return BRANCH_EDEFAULT == null ? branch != null : !BRANCH_EDEFAULT.equals(branch);
			case ServerPackage.SERVER__HOST:
				return HOST_EDEFAULT == null ? host != null : !HOST_EDEFAULT.equals(host);
			case ServerPackage.SERVER__ENV_USERNAME:
				return ENV_USERNAME_EDEFAULT == null ? envUsername != null : !ENV_USERNAME_EDEFAULT.equals(envUsername);
			case ServerPackage.SERVER__ENV_PASSWORD:
				return ENV_PASSWORD_EDEFAULT == null ? envPassword != null : !ENV_PASSWORD_EDEFAULT.equals(envPassword);
			case ServerPackage.SERVER__PORT:
				return PORT_EDEFAULT == null ? port != null : !PORT_EDEFAULT.equals(port);
			case ServerPackage.SERVER__OFS_ID:
				return OFS_ID_EDEFAULT == null ? ofsID != null : !OFS_ID_EDEFAULT.equals(ofsID);
			case ServerPackage.SERVER__PROJECTS:
				return projects != null && !projects.isEmpty();
			case ServerPackage.SERVER__PROTOCOL:
				return PROTOCOL_EDEFAULT == null ? protocol != null : !PROTOCOL_EDEFAULT.equals(protocol);
			case ServerPackage.SERVER__OSTYPE:
				return OSTYPE_EDEFAULT == null ? ostype != null : !OSTYPE_EDEFAULT.equals(ostype);
			case ServerPackage.SERVER__TAFCHOME:
				return TAFCHOME_EDEFAULT == null ? tafchome != null : !TAFCHOME_EDEFAULT.equals(tafchome);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (username: ");
		result.append(username);
		result.append(", password: ");
		result.append(password);
		result.append(", Branch: ");
		result.append(branch);
		result.append(", host: ");
		result.append(host);
		result.append(", envUsername: ");
		result.append(envUsername);
		result.append(", envPassword: ");
		result.append(envPassword);
		result.append(", port: ");
		result.append(port);
		result.append(", ofsID: ");
		result.append(ofsID);
		result.append(", projects: ");
		result.append(projects);
		result.append(", protocol: ");
		result.append(protocol);
		result.append(", ostype: ");
		result.append(ostype);
		result.append(", tafchome: ");
		result.append(tafchome);
		result.append(')');
		return result.toString();
	}

} //ServerImpl

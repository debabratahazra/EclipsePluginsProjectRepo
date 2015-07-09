package com.odcgroup.workbench.security.internal.general;

import com.odcgroup.workbench.security.IUser;

/**
 * The default system user if no special login has occurred.
 *
 * @author Kai Kreuzer
 *
 */
final public class DefaultUser implements IUser {

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.security.IUser#getName()
	 */
	public String getName() {
		return System.getProperty("user.name");
	}

}

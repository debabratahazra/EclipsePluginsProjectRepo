package com.odcgroup.menu.model.validation;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.StringTokenizer;

import com.odcgroup.menu.model.MenuItem;

public final class MenuItemValidatorHelper {

	private MenuItemValidatorHelper() {
	}
	
	/**
	 * @param menuitem extract shortcut
	 * @return array of tokenized strings
	 */
	public static String[] getTokenizedShortcuts(MenuItem menuitem) {
		StringTokenizer st = new StringTokenizer(menuitem.getShortcut(), "+");
		String[] sepKeys = new String[st.countTokens() + 1];
		int i = 0;
		while (st.hasMoreTokens()) {
			String val = st.nextToken();
			if (val != null ){ //&& !val.trim().equals("")) {
				sepKeys[i] = val;
				i++;
			}
		}
		return sepKeys;
	}
	


	
	public static boolean isKeyRepeated(MenuItem menuitem) {
		String[] sepKeys = MenuItemValidatorHelper.getTokenizedShortcuts(menuitem);
		boolean isRepeated = false;
		Set<String> set = new HashSet<String>();
		for(int i=0; i < sepKeys.length; i++){
			if (sepKeys[i]!=null) {
				if (!sepKeys[i].trim().equals("")
						&& set.contains(sepKeys[i].trim().toUpperCase(Locale.ENGLISH))) {
					isRepeated = true;
				} else {
					set.add(sepKeys[i].trim().toUpperCase(Locale.ENGLISH));
				}
			}
		}
		return isRepeated;
	}
}
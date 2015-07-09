package com.odcgroup.t24.aa.product.importer.internal;

public class StringMatch {

	/**
	 * Checks whether a string matches a given wildcard pattern.
	 * 
	 * @param string input string
	 * @param pattern pattern to match
	 * @return <code>true</code> if string matches the pattern, 
	 * otherwise <code>false</code>
	 */
	public static boolean match(String string, String pattern) {
		return match(string, pattern, 0, 0);
	}

	private static boolean match(String string, String pattern,
			int stringStartNdx, int patternStartNdx) {
		int pNdx = patternStartNdx;
		int sNdx = stringStartNdx;
		int pLen = pattern.length();
		if (pLen == 1) {
			if (pattern.charAt(0) == '*') {
				return true;
			}
		}
		int sLen = string.length();
		boolean nextIsNotWildcard = false;

		while (true) {

			if ((sNdx >= sLen) == true) {
				while ((pNdx < pLen) && (pattern.charAt(pNdx) == '*')) {
					pNdx++;
				}
				return pNdx >= pLen;
			}
			if (pNdx >= pLen) {
				return false;
			}
			char p = pattern.charAt(pNdx);

			if (nextIsNotWildcard == false) {

				if (p == '\\') {
					pNdx++;
					nextIsNotWildcard = true;
					continue;
				}
				if (p == '?') {
					sNdx++;
					pNdx++;
					continue;
				}
				if (p == '*') {
					char pnext = 0;
					if (pNdx + 1 < pLen) {
						pnext = pattern.charAt(pNdx + 1);
					}
					if (pnext == '*') {
						pNdx++;
						continue;
					}
					int i;
					pNdx++;

					for (i = string.length(); i >= sNdx; i--) {
						if (match(string, pattern, i, pNdx) == true) {
							return true;
						}
					}
					return false;
				}
			} else {
				nextIsNotWildcard = false;
			}

			if (p != string.charAt(sNdx)) {
				return false;
			}

			sNdx++;
			pNdx++;
		}
	}
}

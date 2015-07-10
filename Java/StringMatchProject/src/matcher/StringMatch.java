package matcher;

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
		int patternIndex = patternStartNdx;
		int stringIndex = stringStartNdx;
		int patternLen = pattern.length();
		if (patternLen == 1) {
			if (pattern.charAt(0) == '*') {
				return true;
			}
		}
		int stringLen = string.length();
		boolean nextIsNotWildcard = false;

		while (true) {

			if ((stringIndex >= stringLen) == true) {
				while ((patternIndex < patternLen) && (pattern.charAt(patternIndex) == '*')) {
					patternIndex++;
				}
				return patternIndex >= patternLen;
			}
			if (patternIndex >= patternLen) {
				return false;
			}
			char p = pattern.charAt(patternIndex);

			if (nextIsNotWildcard == false) {

				if (p == '\\') {
					patternIndex++;
					nextIsNotWildcard = true;
					continue;
				}
				if (p == '?') {
					stringIndex++;
					patternIndex++;
					continue;
				}
				if (p == '*') {
					char pnext = 0;
					if (patternIndex + 1 < patternLen) {
						pnext = pattern.charAt(patternIndex + 1);
					}
					if (pnext == '*') {
						patternIndex++;
						continue;
					}
					int i;
					patternIndex++;

					for (i = string.length(); i >= stringIndex; i--) {
						if (match(string, pattern, i, patternIndex) == true) {
							return true;
						}
					}
					return false;
				}
			} else {
				nextIsNotWildcard = false;
			}

			if (p != string.charAt(stringIndex)) {
				return false;
			}

			stringIndex++;
			patternIndex++;
		}
	}
}

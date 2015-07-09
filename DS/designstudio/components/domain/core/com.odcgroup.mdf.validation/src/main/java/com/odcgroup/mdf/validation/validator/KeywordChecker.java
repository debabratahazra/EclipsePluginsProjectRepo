package com.odcgroup.mdf.validation.validator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public abstract class KeywordChecker {

	private final boolean caseSensitive;
	private Set<String> keywords = new HashSet<String>();

	public KeywordChecker(String filename, boolean caseSensitive)
			throws IOException {
		this.caseSensitive = caseSensitive;
		loadKeywords(filename);
	}

	public boolean isKeyword(String value) {
		if (value != null) {
			if (caseSensitive) {
				return keywords.contains(value);
			} else {
				return keywords.contains(value.toLowerCase());
			}
		} else {
			return false;
		}
	}

	private void loadKeywords(String filename) throws IOException {
		URL location = getClass().getResource(filename);
		if (location == null)
			throw new FileNotFoundException(filename);

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				location.openStream()));

		try {
			for (String line = reader.readLine(); line != null; line = reader
					.readLine()) {
				line = line.trim();
				if (!line.startsWith("#")) {
					if (caseSensitive) {
						keywords.add(line);
					} else {
						keywords.add(line.toLowerCase());
					}
				}
			}
		} finally {
			reader.close();
		}
	}

}

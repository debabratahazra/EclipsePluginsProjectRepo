/*
* generated by Xtext
*/
package com.odcgroup.t24.menu.parser.antlr;

import java.io.InputStream;
import org.eclipse.xtext.parser.antlr.IAntlrTokenFileProvider;

public class MenuAntlrTokenFileProvider implements IAntlrTokenFileProvider {
	
	public InputStream getAntlrTokenFile() {
		ClassLoader classLoader = getClass().getClassLoader();
    	return classLoader.getResourceAsStream("com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.tokens");
	}
}
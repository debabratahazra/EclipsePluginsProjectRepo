/*
* generated by Xtext
*/
package com.odcgroup.service.model.parser.antlr;

import java.io.InputStream;
import org.eclipse.xtext.parser.antlr.IAntlrTokenFileProvider;

public class ComponentAntlrTokenFileProvider implements IAntlrTokenFileProvider {

	public InputStream getAntlrTokenFile() {
		ClassLoader classLoader = getClass().getClassLoader();
	return classLoader.getResourceAsStream("com/odcgroup/service/model/parser/antlr/internal/InternalComponent.tokens");
	}
}
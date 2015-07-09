package com.temenos.t24.tools.eclipse.basic.editors.scanners;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.swt.graphics.RGB;

import com.temenos.t24.tools.eclipse.basic.PluginConstants;
import com.temenos.t24.tools.eclipse.basic.T24BasicPlugin;
import com.temenos.t24.tools.eclipse.basic.editors.util.ColorManager;

/**
 *  Scanner to handle literals. Literals are strings including
 *  white spaces contained within "" or ''. *     
 */
public class LiteralScanner extends RuleBasedScanner {

	public LiteralScanner(ColorManager manager) {
        
	    IPreferenceStore store = (IPreferenceStore)T24BasicPlugin.getBean("preferenceStore");
        RGB colorRGB = PreferenceConverter.getColor(store, PluginConstants.T24_EDITOR_COLOR_LITERAL);        
        
	    setDefaultReturnToken(
				new Token(
					new TextAttribute(
						manager.getColor(colorRGB))));
		
		IToken literal =
			new Token(
				new TextAttribute(
					manager.getColor(colorRGB)));

		List<IRule> rules = new ArrayList<IRule>();
		
		// Add rules for literals
		rules.add(new MultiLineRule("'", "'", literal, '\\'));
		rules.add(new MultiLineRule("\"", "\"", literal, '\\'));

        IRule[] result = rules.toArray(new IRule[rules.size()]);
        setRules(result);
	}
}

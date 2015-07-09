package com.temenos.t24.tools.eclipse.basic.editors.scanners;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.swt.graphics.RGB;

import com.temenos.t24.tools.eclipse.basic.PluginConstants;
import com.temenos.t24.tools.eclipse.basic.T24BasicPlugin;
import com.temenos.t24.tools.eclipse.basic.editors.util.ColorManager;

/**
 * DefaultScanner provides rules to handle the DEFAULT_CONTENT_TYPE regions
 * within a document. 
 * Within a default region set of rules can be applied for detecting
 * keywords.
 */
public class DefaultScanner extends RuleBasedScanner {

	public DefaultScanner(ColorManager colorManager) {
		
	    IPreferenceStore store = (IPreferenceStore)T24BasicPlugin.getBean("preferenceStore");
        RGB colorDefaultRGB = PreferenceConverter.getColor(store, PluginConstants.T24_EDITOR_COLOR_DEFAULT);
        
        setDefaultReturnToken(new Token(new TextAttribute(colorManager.getColor(colorDefaultRGB))));

        List<IRule> rules = new ArrayList<IRule>();
        
		rules.add(new KeywordRule(colorManager));
		
		setRules(rules.toArray(new IRule[rules.size()]));
	}
}

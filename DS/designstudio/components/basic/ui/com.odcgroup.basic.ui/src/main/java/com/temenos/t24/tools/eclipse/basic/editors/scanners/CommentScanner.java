package com.temenos.t24.tools.eclipse.basic.editors.scanners;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.Token;

import com.temenos.t24.tools.eclipse.basic.PluginConstants;
import com.temenos.t24.tools.eclipse.basic.T24BasicPlugin;
import com.temenos.t24.tools.eclipse.basic.editors.util.ColorManager;

/**
 *  Scanner used to handle comments. Lines which begin with
 *  an asterisk '*' or a REM keyword are considered comments.   
 */
public class CommentScanner extends RuleBasedScanner {

    private static IPreferenceStore store = (IPreferenceStore)T24BasicPlugin.getBean("preferenceStore");
    
	public CommentScanner(ColorManager colorManager) {
		setDefaultReturnToken(
				new Token(
					new TextAttribute(
						colorManager.getColor(
						        PreferenceConverter.getColor(store, PluginConstants.T24_EDITOR_COLOR_COMMENT)))));
		
		IToken basicComment =
			new Token(
				new TextAttribute(
					colorManager.getColor(
					        PreferenceConverter.getColor(store, PluginConstants.T24_EDITOR_COLOR_COMMENT))));
        
		List<IRule> rules = new ArrayList<IRule>();
		
		// Add rule for comments
		
		
		rules.add(new MultiLineRule("\n*", "\n", basicComment));
		rules.add(new MultiLineRule("*--", "\n", basicComment));
		rules.add(new MultiLineRule("***", "\n", basicComment));
		rules.add(new MultiLineRule("*!", "\n", basicComment));
		rules.add(new MultiLineRule("\r*", "\n", basicComment));
		rules.add(new MultiLineRule("* ", "\n", basicComment));
		rules.add(new MultiLineRule("*", "\n", basicComment));
        rules.add(new MultiLineRule("\r*", "\n", basicComment));
        rules.add(new MultiLineRule("*\r", "\n", basicComment));        
        rules.add(new MultiLineRule(" *", "\n", basicComment));
        rules.add(new EndOfLineRule("*\n", basicComment));
        rules.add(new EndOfLineRule(";*", basicComment));
        rules.add(new EndOfLineRule("REM ", basicComment));
        
		IRule[] result = rules.toArray(new IRule[rules.size()]);
		setRules(result);
	}
}

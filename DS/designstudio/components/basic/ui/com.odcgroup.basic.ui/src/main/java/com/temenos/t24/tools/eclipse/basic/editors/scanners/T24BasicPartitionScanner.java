package com.temenos.t24.tools.eclipse.basic.editors.scanners;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;

/**
 * Class used to partition document into regions.
 */
public class T24BasicPartitionScanner extends RuleBasedPartitionScanner {
	public final static String BASIC_COMMENT  = "__basic_comment";
	public final static String BASIC_LITERAL  = "__basic_literal";
    public final static String BASIC_VERTICAL = "__basic_vertical";
    public final static String BASIC_KEYWORD  = "__basic_keyword";

	public T24BasicPartitionScanner() {

		IToken basicComment  = new Token(BASIC_COMMENT);
		IToken basicLiteral  = new Token(BASIC_LITERAL);
        IToken basicVertical = new Token(BASIC_VERTICAL);

        List<IPredicateRule> rules = new ArrayList<IPredicateRule>();

        rules.add(new MultiLineRule("\n*", "\n", basicComment));
        rules.add(new MultiLineRule("*!", "\n", basicComment));
        rules.add(new MultiLineRule("***", "\n", basicComment));
        rules.add(new MultiLineRule("*--", "\n", basicComment));
        rules.add(new MultiLineRule("* ", "\n", basicComment));
        rules.add(new MultiLineRule("*\r", "\n", basicComment));
        rules.add(new MultiLineRule("\r*", "\n", basicComment));
        rules.add(new MultiLineRule(" *", "\n", basicComment));
        rules.add(new EndOfLineRule("*\n", basicComment));
        rules.add(new EndOfLineRule(";*", basicComment));
        rules.add(new EndOfLineRule("REM ", basicComment));
		rules.add(new SingleLineRule("\"","\"", basicLiteral,'\\'));
		rules.add(new SingleLineRule("'","'", basicLiteral,'\\'));
		rules.add(new SingleLineRule("|","|", basicVertical));
	
		setPredicateRules(rules.toArray(new IPredicateRule[rules.size()]));
	}
}

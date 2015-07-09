package com.temenos.t24.tools.eclipse.basic.editors.scanners;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.swt.graphics.RGB;

import com.google.common.io.Closeables;
import com.temenos.t24.tools.eclipse.basic.PluginConstants;
import com.temenos.t24.tools.eclipse.basic.T24BasicPlugin;
import com.temenos.t24.tools.eclipse.basic.editors.util.ColorManager;
import com.temenos.t24.tools.eclipse.basic.help.HelpCache;

public class KeywordRule extends T24BasicWordRule {

    private static IPreferenceStore store = (IPreferenceStore) T24BasicPlugin.getBean("preferenceStore");

    /**
     * Internal class used by WordRule
     */
    static class MyWordDetector implements IWordDetector {

        /**
         * Returns whether the specified character is valid as a subsequent
         * character in a word.
         * 
         * @param c the character to be checked
         * @return <code>true</code> if the character is a valid word part,
         *         <code>false</code> otherwise
         */
        public boolean isWordPart(char character) {
            Character c = new Character(character);
            if (c.equals('.'))
                return false;
            return Character.isLetter(character);
        }

        /**
         * Returns whether the specified character is valid as the first
         * character in a word.
         * 
         * @param c the character to be checked
         * @return <code>true</code> is a valid first character in a word,
         *         <code>false</code> otherwise
         */
        public boolean isWordStart(char character) {
            Character c = new Character(character);
            if (c.equals('.') || c.equals('$'))
                return true;
            return Character.isLetter(character);
        }
    }

    public KeywordRule(ColorManager colorManager) {
        super(new MyWordDetector(), new Token(new TextAttribute(colorManager.getColor(PreferenceConverter.getColor(store,
                PluginConstants.T24_EDITOR_COLOR_DEFAULT)))));
        // Token returned by WordRule when it detects a keyword
        RGB keywordRGB = PreferenceConverter.getColor(store, PluginConstants.T24_EDITOR_COLOR_KEYWORD);
        RGB commonVariableRGB = PreferenceConverter.getColor(store, PluginConstants.T24_EDITOR_COLOR_COMMON_VARIABLE);
        IToken keywordToken = new Token(new TextAttribute(colorManager.getColor(keywordRGB)));
        IToken commonVarToken = new Token(new TextAttribute(colorManager.getColor(commonVariableRGB)));
        InputStream is = null;
        try {
            // Read all the legal keywords from an external source, and populate
            // the
            // table used by WordRule with them.
            List<String> keywords = HelpCache.getInstance().getKeywordList();
            Iterator<String> listIterator = keywords.iterator();
            while (listIterator.hasNext()) {
                super.addWord(listIterator.next(), keywordToken);
            }
            // Read all the common variables from an external source, and
            // populate the
            // table used by WordRule with them.
            String keyword;
            String commonVarFile = PluginConstants.config_dir + PluginConstants.commonVariablesFilename;
            is = KeywordRule.class.getResourceAsStream(commonVarFile);
            if (is != null) {
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                keyword = br.readLine();
                while (keyword != null) {
                    super.addWord(keyword, commonVarToken);
                    keyword = br.readLine();
                }
            }
        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            Closeables.closeQuietly(is);
        }
    }
}

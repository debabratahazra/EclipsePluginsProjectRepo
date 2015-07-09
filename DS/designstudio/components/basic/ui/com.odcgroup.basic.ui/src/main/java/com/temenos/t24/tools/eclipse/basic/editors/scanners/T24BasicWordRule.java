package com.temenos.t24.tools.eclipse.basic.editors.scanners;

import org.eclipse.jface.text.Assert;
import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WordRule;

/**
 * Own implementation of <code>WordRule</code>, used for scanning for 
 * T24 keywords. 
 */
public class T24BasicWordRule extends WordRule {

    /** Buffer used for pattern detection */
    private StringBuffer fBuffer= new StringBuffer();    
    
    public T24BasicWordRule(IWordDetector detector, IToken defaultToken) {
        super(detector, defaultToken);
        Assert.isNotNull(detector);
        Assert.isNotNull(defaultToken);

        fDetector= detector;
        fDefaultToken= defaultToken;
    }
    
    /** 
     * Simple method to detect a blank character 
     * @param c
     * @return boolean
     */
    private boolean isBlankSpace(char c){
        Character ch = new Character(c);
        if(ch.equals(' ')){
            return true;
        } else {
            return false;
        }
    }
    
    
    /**
     * @see IRule#evaluate(ICharacterScanner)
     * @params scanner - a buffer that holds a string of characters
     * @return IToken - if a keyword is successfully detected a token associated
     * to that keyword will be returned (it must have been associated previously),
     * otherwise a default token is returned
     */
    @Override
    public IToken evaluate(ICharacterScanner scanner) {
        // Read the first character
        int c = scanner.read();
        if (fDetector.isWordStart((char) c)) {
            // fColumn holds the an index pointing to the last char read
            // it's UNDEFINED or -1 if no char has yet been read
            if (fColumn == UNDEFINED || (fColumn == scanner.getColumn() - 1)) {
                fBuffer.setLength(0);
                do {
                    fBuffer.append((char) c);
                    c = scanner.read();
                } while (c!=ICharacterScanner.EOF && 
                         !isBlankSpace((char) c) &&
                         c!='\r' && c!='\n' && c!='<' && c!='(');
                
                scanner.unread();
                IToken token = (IToken) fWords.get(fBuffer.toString());
                if (token != null)
                    return token;
                if (fDefaultToken.isUndefined())
                    unreadBuffer(scanner);
                return fDefaultToken;
            }
        }
        scanner.unread();
        return Token.UNDEFINED;
    }    
    
}

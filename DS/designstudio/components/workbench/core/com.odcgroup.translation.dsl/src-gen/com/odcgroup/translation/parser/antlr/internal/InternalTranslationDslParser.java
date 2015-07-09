package com.odcgroup.translation.parser.antlr.internal; 

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import com.odcgroup.translation.services.TranslationDslGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalTranslationDslParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ID", "RULE_STRING", "RULE_INT", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "','", "'='"
    };
    public static final int RULE_ID=4;
    public static final int RULE_STRING=5;
    public static final int T__12=12;
    public static final int T__11=11;
    public static final int RULE_ANY_OTHER=10;
    public static final int RULE_INT=6;
    public static final int RULE_WS=9;
    public static final int RULE_SL_COMMENT=8;
    public static final int EOF=-1;
    public static final int RULE_ML_COMMENT=7;

    // delegates
    // delegators


        public InternalTranslationDslParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalTranslationDslParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalTranslationDslParser.tokenNames; }
    public String getGrammarFileName() { return "../com.odcgroup.translation.dsl/src-gen/com/odcgroup/translation/parser/antlr/internal/InternalTranslationDsl.g"; }



     	private TranslationDslGrammarAccess grammarAccess;
     	
        public InternalTranslationDslParser(TokenStream input, TranslationDslGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }
        
        @Override
        protected String getFirstRuleName() {
        	return "LocalTranslations";	
       	}
       	
       	@Override
       	protected TranslationDslGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}



    // $ANTLR start "entryRuleLocalTranslations"
    // ../com.odcgroup.translation.dsl/src-gen/com/odcgroup/translation/parser/antlr/internal/InternalTranslationDsl.g:67:1: entryRuleLocalTranslations returns [EObject current=null] : iv_ruleLocalTranslations= ruleLocalTranslations EOF ;
    public final EObject entryRuleLocalTranslations() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLocalTranslations = null;


        try {
            // ../com.odcgroup.translation.dsl/src-gen/com/odcgroup/translation/parser/antlr/internal/InternalTranslationDsl.g:68:2: (iv_ruleLocalTranslations= ruleLocalTranslations EOF )
            // ../com.odcgroup.translation.dsl/src-gen/com/odcgroup/translation/parser/antlr/internal/InternalTranslationDsl.g:69:2: iv_ruleLocalTranslations= ruleLocalTranslations EOF
            {
             newCompositeNode(grammarAccess.getLocalTranslationsRule()); 
            pushFollow(FOLLOW_ruleLocalTranslations_in_entryRuleLocalTranslations75);
            iv_ruleLocalTranslations=ruleLocalTranslations();

            state._fsp--;

             current =iv_ruleLocalTranslations; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLocalTranslations85); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleLocalTranslations"


    // $ANTLR start "ruleLocalTranslations"
    // ../com.odcgroup.translation.dsl/src-gen/com/odcgroup/translation/parser/antlr/internal/InternalTranslationDsl.g:76:1: ruleLocalTranslations returns [EObject current=null] : ( ( (lv_translations_0_0= ruleLocalTranslation ) ) (otherlv_1= ',' ( (lv_translations_2_0= ruleLocalTranslation ) ) )* ) ;
    public final EObject ruleLocalTranslations() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_translations_0_0 = null;

        EObject lv_translations_2_0 = null;


         enterRule(); 
            
        try {
            // ../com.odcgroup.translation.dsl/src-gen/com/odcgroup/translation/parser/antlr/internal/InternalTranslationDsl.g:79:28: ( ( ( (lv_translations_0_0= ruleLocalTranslation ) ) (otherlv_1= ',' ( (lv_translations_2_0= ruleLocalTranslation ) ) )* ) )
            // ../com.odcgroup.translation.dsl/src-gen/com/odcgroup/translation/parser/antlr/internal/InternalTranslationDsl.g:80:1: ( ( (lv_translations_0_0= ruleLocalTranslation ) ) (otherlv_1= ',' ( (lv_translations_2_0= ruleLocalTranslation ) ) )* )
            {
            // ../com.odcgroup.translation.dsl/src-gen/com/odcgroup/translation/parser/antlr/internal/InternalTranslationDsl.g:80:1: ( ( (lv_translations_0_0= ruleLocalTranslation ) ) (otherlv_1= ',' ( (lv_translations_2_0= ruleLocalTranslation ) ) )* )
            // ../com.odcgroup.translation.dsl/src-gen/com/odcgroup/translation/parser/antlr/internal/InternalTranslationDsl.g:80:2: ( (lv_translations_0_0= ruleLocalTranslation ) ) (otherlv_1= ',' ( (lv_translations_2_0= ruleLocalTranslation ) ) )*
            {
            // ../com.odcgroup.translation.dsl/src-gen/com/odcgroup/translation/parser/antlr/internal/InternalTranslationDsl.g:80:2: ( (lv_translations_0_0= ruleLocalTranslation ) )
            // ../com.odcgroup.translation.dsl/src-gen/com/odcgroup/translation/parser/antlr/internal/InternalTranslationDsl.g:81:1: (lv_translations_0_0= ruleLocalTranslation )
            {
            // ../com.odcgroup.translation.dsl/src-gen/com/odcgroup/translation/parser/antlr/internal/InternalTranslationDsl.g:81:1: (lv_translations_0_0= ruleLocalTranslation )
            // ../com.odcgroup.translation.dsl/src-gen/com/odcgroup/translation/parser/antlr/internal/InternalTranslationDsl.g:82:3: lv_translations_0_0= ruleLocalTranslation
            {
             
            	        newCompositeNode(grammarAccess.getLocalTranslationsAccess().getTranslationsLocalTranslationParserRuleCall_0_0()); 
            	    
            pushFollow(FOLLOW_ruleLocalTranslation_in_ruleLocalTranslations131);
            lv_translations_0_0=ruleLocalTranslation();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getLocalTranslationsRule());
            	        }
                   		add(
                   			current, 
                   			"translations",
                    		lv_translations_0_0, 
                    		"LocalTranslation");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../com.odcgroup.translation.dsl/src-gen/com/odcgroup/translation/parser/antlr/internal/InternalTranslationDsl.g:98:2: (otherlv_1= ',' ( (lv_translations_2_0= ruleLocalTranslation ) ) )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==11) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // ../com.odcgroup.translation.dsl/src-gen/com/odcgroup/translation/parser/antlr/internal/InternalTranslationDsl.g:98:4: otherlv_1= ',' ( (lv_translations_2_0= ruleLocalTranslation ) )
            	    {
            	    otherlv_1=(Token)match(input,11,FOLLOW_11_in_ruleLocalTranslations144); 

            	        	newLeafNode(otherlv_1, grammarAccess.getLocalTranslationsAccess().getCommaKeyword_1_0());
            	        
            	    // ../com.odcgroup.translation.dsl/src-gen/com/odcgroup/translation/parser/antlr/internal/InternalTranslationDsl.g:102:1: ( (lv_translations_2_0= ruleLocalTranslation ) )
            	    // ../com.odcgroup.translation.dsl/src-gen/com/odcgroup/translation/parser/antlr/internal/InternalTranslationDsl.g:103:1: (lv_translations_2_0= ruleLocalTranslation )
            	    {
            	    // ../com.odcgroup.translation.dsl/src-gen/com/odcgroup/translation/parser/antlr/internal/InternalTranslationDsl.g:103:1: (lv_translations_2_0= ruleLocalTranslation )
            	    // ../com.odcgroup.translation.dsl/src-gen/com/odcgroup/translation/parser/antlr/internal/InternalTranslationDsl.g:104:3: lv_translations_2_0= ruleLocalTranslation
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getLocalTranslationsAccess().getTranslationsLocalTranslationParserRuleCall_1_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleLocalTranslation_in_ruleLocalTranslations165);
            	    lv_translations_2_0=ruleLocalTranslation();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getLocalTranslationsRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"translations",
            	            		lv_translations_2_0, 
            	            		"LocalTranslation");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleLocalTranslations"


    // $ANTLR start "entryRuleLocalTranslation"
    // ../com.odcgroup.translation.dsl/src-gen/com/odcgroup/translation/parser/antlr/internal/InternalTranslationDsl.g:128:1: entryRuleLocalTranslation returns [EObject current=null] : iv_ruleLocalTranslation= ruleLocalTranslation EOF ;
    public final EObject entryRuleLocalTranslation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLocalTranslation = null;


        try {
            // ../com.odcgroup.translation.dsl/src-gen/com/odcgroup/translation/parser/antlr/internal/InternalTranslationDsl.g:129:2: (iv_ruleLocalTranslation= ruleLocalTranslation EOF )
            // ../com.odcgroup.translation.dsl/src-gen/com/odcgroup/translation/parser/antlr/internal/InternalTranslationDsl.g:130:2: iv_ruleLocalTranslation= ruleLocalTranslation EOF
            {
             newCompositeNode(grammarAccess.getLocalTranslationRule()); 
            pushFollow(FOLLOW_ruleLocalTranslation_in_entryRuleLocalTranslation203);
            iv_ruleLocalTranslation=ruleLocalTranslation();

            state._fsp--;

             current =iv_ruleLocalTranslation; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLocalTranslation213); 

            }

        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleLocalTranslation"


    // $ANTLR start "ruleLocalTranslation"
    // ../com.odcgroup.translation.dsl/src-gen/com/odcgroup/translation/parser/antlr/internal/InternalTranslationDsl.g:137:1: ruleLocalTranslation returns [EObject current=null] : ( ( (lv_locale_0_0= RULE_ID ) ) otherlv_1= '=' ( (lv_text_2_0= RULE_STRING ) ) ) ;
    public final EObject ruleLocalTranslation() throws RecognitionException {
        EObject current = null;

        Token lv_locale_0_0=null;
        Token otherlv_1=null;
        Token lv_text_2_0=null;

         enterRule(); 
            
        try {
            // ../com.odcgroup.translation.dsl/src-gen/com/odcgroup/translation/parser/antlr/internal/InternalTranslationDsl.g:140:28: ( ( ( (lv_locale_0_0= RULE_ID ) ) otherlv_1= '=' ( (lv_text_2_0= RULE_STRING ) ) ) )
            // ../com.odcgroup.translation.dsl/src-gen/com/odcgroup/translation/parser/antlr/internal/InternalTranslationDsl.g:141:1: ( ( (lv_locale_0_0= RULE_ID ) ) otherlv_1= '=' ( (lv_text_2_0= RULE_STRING ) ) )
            {
            // ../com.odcgroup.translation.dsl/src-gen/com/odcgroup/translation/parser/antlr/internal/InternalTranslationDsl.g:141:1: ( ( (lv_locale_0_0= RULE_ID ) ) otherlv_1= '=' ( (lv_text_2_0= RULE_STRING ) ) )
            // ../com.odcgroup.translation.dsl/src-gen/com/odcgroup/translation/parser/antlr/internal/InternalTranslationDsl.g:141:2: ( (lv_locale_0_0= RULE_ID ) ) otherlv_1= '=' ( (lv_text_2_0= RULE_STRING ) )
            {
            // ../com.odcgroup.translation.dsl/src-gen/com/odcgroup/translation/parser/antlr/internal/InternalTranslationDsl.g:141:2: ( (lv_locale_0_0= RULE_ID ) )
            // ../com.odcgroup.translation.dsl/src-gen/com/odcgroup/translation/parser/antlr/internal/InternalTranslationDsl.g:142:1: (lv_locale_0_0= RULE_ID )
            {
            // ../com.odcgroup.translation.dsl/src-gen/com/odcgroup/translation/parser/antlr/internal/InternalTranslationDsl.g:142:1: (lv_locale_0_0= RULE_ID )
            // ../com.odcgroup.translation.dsl/src-gen/com/odcgroup/translation/parser/antlr/internal/InternalTranslationDsl.g:143:3: lv_locale_0_0= RULE_ID
            {
            lv_locale_0_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleLocalTranslation255); 

            			newLeafNode(lv_locale_0_0, grammarAccess.getLocalTranslationAccess().getLocaleIDTerminalRuleCall_0_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getLocalTranslationRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"locale",
                    		lv_locale_0_0, 
                    		"ID");
            	    

            }


            }

            otherlv_1=(Token)match(input,12,FOLLOW_12_in_ruleLocalTranslation272); 

                	newLeafNode(otherlv_1, grammarAccess.getLocalTranslationAccess().getEqualsSignKeyword_1());
                
            // ../com.odcgroup.translation.dsl/src-gen/com/odcgroup/translation/parser/antlr/internal/InternalTranslationDsl.g:163:1: ( (lv_text_2_0= RULE_STRING ) )
            // ../com.odcgroup.translation.dsl/src-gen/com/odcgroup/translation/parser/antlr/internal/InternalTranslationDsl.g:164:1: (lv_text_2_0= RULE_STRING )
            {
            // ../com.odcgroup.translation.dsl/src-gen/com/odcgroup/translation/parser/antlr/internal/InternalTranslationDsl.g:164:1: (lv_text_2_0= RULE_STRING )
            // ../com.odcgroup.translation.dsl/src-gen/com/odcgroup/translation/parser/antlr/internal/InternalTranslationDsl.g:165:3: lv_text_2_0= RULE_STRING
            {
            lv_text_2_0=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleLocalTranslation289); 

            			newLeafNode(lv_text_2_0, grammarAccess.getLocalTranslationAccess().getTextSTRINGTerminalRuleCall_2_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getLocalTranslationRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"text",
                    		lv_text_2_0, 
                    		"STRING");
            	    

            }


            }


            }


            }

             leaveRule(); 
        }
         
            catch (RecognitionException re) { 
                recover(input,re); 
                appendSkippedTokens();
            } 
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleLocalTranslation"

    // Delegated rules


 

    public static final BitSet FOLLOW_ruleLocalTranslations_in_entryRuleLocalTranslations75 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLocalTranslations85 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLocalTranslation_in_ruleLocalTranslations131 = new BitSet(new long[]{0x0000000000000802L});
    public static final BitSet FOLLOW_11_in_ruleLocalTranslations144 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleLocalTranslation_in_ruleLocalTranslations165 = new BitSet(new long[]{0x0000000000000802L});
    public static final BitSet FOLLOW_ruleLocalTranslation_in_entryRuleLocalTranslation203 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLocalTranslation213 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleLocalTranslation255 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleLocalTranslation272 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleLocalTranslation289 = new BitSet(new long[]{0x0000000000000002L});

}
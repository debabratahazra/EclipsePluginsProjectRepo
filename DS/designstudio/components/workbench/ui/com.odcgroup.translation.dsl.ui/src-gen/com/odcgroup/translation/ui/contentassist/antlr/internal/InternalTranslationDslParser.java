package com.odcgroup.translation.ui.contentassist.antlr.internal; 

import java.io.InputStream;
import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.ui.editor.contentassist.antlr.internal.AbstractInternalContentAssistParser;
import org.eclipse.xtext.ui.editor.contentassist.antlr.internal.DFA;
import com.odcgroup.translation.services.TranslationDslGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalTranslationDslParser extends AbstractInternalContentAssistParser {
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
    public String getGrammarFileName() { return "../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g"; }


     
     	private TranslationDslGrammarAccess grammarAccess;
     	
        public void setGrammarAccess(TranslationDslGrammarAccess grammarAccess) {
        	this.grammarAccess = grammarAccess;
        }
        
        @Override
        protected Grammar getGrammar() {
        	return grammarAccess.getGrammar();
        }
        
        @Override
        protected String getValueForTokenName(String tokenName) {
        	return tokenName;
        }




    // $ANTLR start "entryRuleLocalTranslations"
    // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:60:1: entryRuleLocalTranslations : ruleLocalTranslations EOF ;
    public final void entryRuleLocalTranslations() throws RecognitionException {
        try {
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:61:1: ( ruleLocalTranslations EOF )
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:62:1: ruleLocalTranslations EOF
            {
             before(grammarAccess.getLocalTranslationsRule()); 
            pushFollow(FOLLOW_ruleLocalTranslations_in_entryRuleLocalTranslations61);
            ruleLocalTranslations();

            state._fsp--;

             after(grammarAccess.getLocalTranslationsRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLocalTranslations68); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleLocalTranslations"


    // $ANTLR start "ruleLocalTranslations"
    // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:69:1: ruleLocalTranslations : ( ( rule__LocalTranslations__Group__0 ) ) ;
    public final void ruleLocalTranslations() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:73:2: ( ( ( rule__LocalTranslations__Group__0 ) ) )
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:74:1: ( ( rule__LocalTranslations__Group__0 ) )
            {
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:74:1: ( ( rule__LocalTranslations__Group__0 ) )
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:75:1: ( rule__LocalTranslations__Group__0 )
            {
             before(grammarAccess.getLocalTranslationsAccess().getGroup()); 
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:76:1: ( rule__LocalTranslations__Group__0 )
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:76:2: rule__LocalTranslations__Group__0
            {
            pushFollow(FOLLOW_rule__LocalTranslations__Group__0_in_ruleLocalTranslations94);
            rule__LocalTranslations__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getLocalTranslationsAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleLocalTranslations"


    // $ANTLR start "entryRuleLocalTranslation"
    // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:88:1: entryRuleLocalTranslation : ruleLocalTranslation EOF ;
    public final void entryRuleLocalTranslation() throws RecognitionException {
        try {
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:89:1: ( ruleLocalTranslation EOF )
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:90:1: ruleLocalTranslation EOF
            {
             before(grammarAccess.getLocalTranslationRule()); 
            pushFollow(FOLLOW_ruleLocalTranslation_in_entryRuleLocalTranslation121);
            ruleLocalTranslation();

            state._fsp--;

             after(grammarAccess.getLocalTranslationRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLocalTranslation128); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleLocalTranslation"


    // $ANTLR start "ruleLocalTranslation"
    // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:97:1: ruleLocalTranslation : ( ( rule__LocalTranslation__Group__0 ) ) ;
    public final void ruleLocalTranslation() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:101:2: ( ( ( rule__LocalTranslation__Group__0 ) ) )
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:102:1: ( ( rule__LocalTranslation__Group__0 ) )
            {
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:102:1: ( ( rule__LocalTranslation__Group__0 ) )
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:103:1: ( rule__LocalTranslation__Group__0 )
            {
             before(grammarAccess.getLocalTranslationAccess().getGroup()); 
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:104:1: ( rule__LocalTranslation__Group__0 )
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:104:2: rule__LocalTranslation__Group__0
            {
            pushFollow(FOLLOW_rule__LocalTranslation__Group__0_in_ruleLocalTranslation154);
            rule__LocalTranslation__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getLocalTranslationAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleLocalTranslation"


    // $ANTLR start "rule__LocalTranslations__Group__0"
    // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:120:1: rule__LocalTranslations__Group__0 : rule__LocalTranslations__Group__0__Impl rule__LocalTranslations__Group__1 ;
    public final void rule__LocalTranslations__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:124:1: ( rule__LocalTranslations__Group__0__Impl rule__LocalTranslations__Group__1 )
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:125:2: rule__LocalTranslations__Group__0__Impl rule__LocalTranslations__Group__1
            {
            pushFollow(FOLLOW_rule__LocalTranslations__Group__0__Impl_in_rule__LocalTranslations__Group__0190);
            rule__LocalTranslations__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__LocalTranslations__Group__1_in_rule__LocalTranslations__Group__0193);
            rule__LocalTranslations__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LocalTranslations__Group__0"


    // $ANTLR start "rule__LocalTranslations__Group__0__Impl"
    // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:132:1: rule__LocalTranslations__Group__0__Impl : ( ( rule__LocalTranslations__TranslationsAssignment_0 ) ) ;
    public final void rule__LocalTranslations__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:136:1: ( ( ( rule__LocalTranslations__TranslationsAssignment_0 ) ) )
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:137:1: ( ( rule__LocalTranslations__TranslationsAssignment_0 ) )
            {
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:137:1: ( ( rule__LocalTranslations__TranslationsAssignment_0 ) )
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:138:1: ( rule__LocalTranslations__TranslationsAssignment_0 )
            {
             before(grammarAccess.getLocalTranslationsAccess().getTranslationsAssignment_0()); 
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:139:1: ( rule__LocalTranslations__TranslationsAssignment_0 )
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:139:2: rule__LocalTranslations__TranslationsAssignment_0
            {
            pushFollow(FOLLOW_rule__LocalTranslations__TranslationsAssignment_0_in_rule__LocalTranslations__Group__0__Impl220);
            rule__LocalTranslations__TranslationsAssignment_0();

            state._fsp--;


            }

             after(grammarAccess.getLocalTranslationsAccess().getTranslationsAssignment_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LocalTranslations__Group__0__Impl"


    // $ANTLR start "rule__LocalTranslations__Group__1"
    // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:149:1: rule__LocalTranslations__Group__1 : rule__LocalTranslations__Group__1__Impl ;
    public final void rule__LocalTranslations__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:153:1: ( rule__LocalTranslations__Group__1__Impl )
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:154:2: rule__LocalTranslations__Group__1__Impl
            {
            pushFollow(FOLLOW_rule__LocalTranslations__Group__1__Impl_in_rule__LocalTranslations__Group__1250);
            rule__LocalTranslations__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LocalTranslations__Group__1"


    // $ANTLR start "rule__LocalTranslations__Group__1__Impl"
    // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:160:1: rule__LocalTranslations__Group__1__Impl : ( ( rule__LocalTranslations__Group_1__0 )* ) ;
    public final void rule__LocalTranslations__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:164:1: ( ( ( rule__LocalTranslations__Group_1__0 )* ) )
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:165:1: ( ( rule__LocalTranslations__Group_1__0 )* )
            {
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:165:1: ( ( rule__LocalTranslations__Group_1__0 )* )
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:166:1: ( rule__LocalTranslations__Group_1__0 )*
            {
             before(grammarAccess.getLocalTranslationsAccess().getGroup_1()); 
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:167:1: ( rule__LocalTranslations__Group_1__0 )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==11) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:167:2: rule__LocalTranslations__Group_1__0
            	    {
            	    pushFollow(FOLLOW_rule__LocalTranslations__Group_1__0_in_rule__LocalTranslations__Group__1__Impl277);
            	    rule__LocalTranslations__Group_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

             after(grammarAccess.getLocalTranslationsAccess().getGroup_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LocalTranslations__Group__1__Impl"


    // $ANTLR start "rule__LocalTranslations__Group_1__0"
    // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:181:1: rule__LocalTranslations__Group_1__0 : rule__LocalTranslations__Group_1__0__Impl rule__LocalTranslations__Group_1__1 ;
    public final void rule__LocalTranslations__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:185:1: ( rule__LocalTranslations__Group_1__0__Impl rule__LocalTranslations__Group_1__1 )
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:186:2: rule__LocalTranslations__Group_1__0__Impl rule__LocalTranslations__Group_1__1
            {
            pushFollow(FOLLOW_rule__LocalTranslations__Group_1__0__Impl_in_rule__LocalTranslations__Group_1__0312);
            rule__LocalTranslations__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__LocalTranslations__Group_1__1_in_rule__LocalTranslations__Group_1__0315);
            rule__LocalTranslations__Group_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LocalTranslations__Group_1__0"


    // $ANTLR start "rule__LocalTranslations__Group_1__0__Impl"
    // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:193:1: rule__LocalTranslations__Group_1__0__Impl : ( ',' ) ;
    public final void rule__LocalTranslations__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:197:1: ( ( ',' ) )
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:198:1: ( ',' )
            {
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:198:1: ( ',' )
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:199:1: ','
            {
             before(grammarAccess.getLocalTranslationsAccess().getCommaKeyword_1_0()); 
            match(input,11,FOLLOW_11_in_rule__LocalTranslations__Group_1__0__Impl343); 
             after(grammarAccess.getLocalTranslationsAccess().getCommaKeyword_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LocalTranslations__Group_1__0__Impl"


    // $ANTLR start "rule__LocalTranslations__Group_1__1"
    // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:212:1: rule__LocalTranslations__Group_1__1 : rule__LocalTranslations__Group_1__1__Impl ;
    public final void rule__LocalTranslations__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:216:1: ( rule__LocalTranslations__Group_1__1__Impl )
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:217:2: rule__LocalTranslations__Group_1__1__Impl
            {
            pushFollow(FOLLOW_rule__LocalTranslations__Group_1__1__Impl_in_rule__LocalTranslations__Group_1__1374);
            rule__LocalTranslations__Group_1__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LocalTranslations__Group_1__1"


    // $ANTLR start "rule__LocalTranslations__Group_1__1__Impl"
    // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:223:1: rule__LocalTranslations__Group_1__1__Impl : ( ( rule__LocalTranslations__TranslationsAssignment_1_1 ) ) ;
    public final void rule__LocalTranslations__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:227:1: ( ( ( rule__LocalTranslations__TranslationsAssignment_1_1 ) ) )
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:228:1: ( ( rule__LocalTranslations__TranslationsAssignment_1_1 ) )
            {
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:228:1: ( ( rule__LocalTranslations__TranslationsAssignment_1_1 ) )
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:229:1: ( rule__LocalTranslations__TranslationsAssignment_1_1 )
            {
             before(grammarAccess.getLocalTranslationsAccess().getTranslationsAssignment_1_1()); 
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:230:1: ( rule__LocalTranslations__TranslationsAssignment_1_1 )
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:230:2: rule__LocalTranslations__TranslationsAssignment_1_1
            {
            pushFollow(FOLLOW_rule__LocalTranslations__TranslationsAssignment_1_1_in_rule__LocalTranslations__Group_1__1__Impl401);
            rule__LocalTranslations__TranslationsAssignment_1_1();

            state._fsp--;


            }

             after(grammarAccess.getLocalTranslationsAccess().getTranslationsAssignment_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LocalTranslations__Group_1__1__Impl"


    // $ANTLR start "rule__LocalTranslation__Group__0"
    // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:244:1: rule__LocalTranslation__Group__0 : rule__LocalTranslation__Group__0__Impl rule__LocalTranslation__Group__1 ;
    public final void rule__LocalTranslation__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:248:1: ( rule__LocalTranslation__Group__0__Impl rule__LocalTranslation__Group__1 )
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:249:2: rule__LocalTranslation__Group__0__Impl rule__LocalTranslation__Group__1
            {
            pushFollow(FOLLOW_rule__LocalTranslation__Group__0__Impl_in_rule__LocalTranslation__Group__0435);
            rule__LocalTranslation__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__LocalTranslation__Group__1_in_rule__LocalTranslation__Group__0438);
            rule__LocalTranslation__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LocalTranslation__Group__0"


    // $ANTLR start "rule__LocalTranslation__Group__0__Impl"
    // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:256:1: rule__LocalTranslation__Group__0__Impl : ( ( rule__LocalTranslation__LocaleAssignment_0 ) ) ;
    public final void rule__LocalTranslation__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:260:1: ( ( ( rule__LocalTranslation__LocaleAssignment_0 ) ) )
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:261:1: ( ( rule__LocalTranslation__LocaleAssignment_0 ) )
            {
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:261:1: ( ( rule__LocalTranslation__LocaleAssignment_0 ) )
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:262:1: ( rule__LocalTranslation__LocaleAssignment_0 )
            {
             before(grammarAccess.getLocalTranslationAccess().getLocaleAssignment_0()); 
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:263:1: ( rule__LocalTranslation__LocaleAssignment_0 )
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:263:2: rule__LocalTranslation__LocaleAssignment_0
            {
            pushFollow(FOLLOW_rule__LocalTranslation__LocaleAssignment_0_in_rule__LocalTranslation__Group__0__Impl465);
            rule__LocalTranslation__LocaleAssignment_0();

            state._fsp--;


            }

             after(grammarAccess.getLocalTranslationAccess().getLocaleAssignment_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LocalTranslation__Group__0__Impl"


    // $ANTLR start "rule__LocalTranslation__Group__1"
    // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:273:1: rule__LocalTranslation__Group__1 : rule__LocalTranslation__Group__1__Impl rule__LocalTranslation__Group__2 ;
    public final void rule__LocalTranslation__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:277:1: ( rule__LocalTranslation__Group__1__Impl rule__LocalTranslation__Group__2 )
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:278:2: rule__LocalTranslation__Group__1__Impl rule__LocalTranslation__Group__2
            {
            pushFollow(FOLLOW_rule__LocalTranslation__Group__1__Impl_in_rule__LocalTranslation__Group__1495);
            rule__LocalTranslation__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__LocalTranslation__Group__2_in_rule__LocalTranslation__Group__1498);
            rule__LocalTranslation__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LocalTranslation__Group__1"


    // $ANTLR start "rule__LocalTranslation__Group__1__Impl"
    // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:285:1: rule__LocalTranslation__Group__1__Impl : ( '=' ) ;
    public final void rule__LocalTranslation__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:289:1: ( ( '=' ) )
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:290:1: ( '=' )
            {
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:290:1: ( '=' )
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:291:1: '='
            {
             before(grammarAccess.getLocalTranslationAccess().getEqualsSignKeyword_1()); 
            match(input,12,FOLLOW_12_in_rule__LocalTranslation__Group__1__Impl526); 
             after(grammarAccess.getLocalTranslationAccess().getEqualsSignKeyword_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LocalTranslation__Group__1__Impl"


    // $ANTLR start "rule__LocalTranslation__Group__2"
    // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:304:1: rule__LocalTranslation__Group__2 : rule__LocalTranslation__Group__2__Impl ;
    public final void rule__LocalTranslation__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:308:1: ( rule__LocalTranslation__Group__2__Impl )
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:309:2: rule__LocalTranslation__Group__2__Impl
            {
            pushFollow(FOLLOW_rule__LocalTranslation__Group__2__Impl_in_rule__LocalTranslation__Group__2557);
            rule__LocalTranslation__Group__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LocalTranslation__Group__2"


    // $ANTLR start "rule__LocalTranslation__Group__2__Impl"
    // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:315:1: rule__LocalTranslation__Group__2__Impl : ( ( rule__LocalTranslation__TextAssignment_2 ) ) ;
    public final void rule__LocalTranslation__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:319:1: ( ( ( rule__LocalTranslation__TextAssignment_2 ) ) )
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:320:1: ( ( rule__LocalTranslation__TextAssignment_2 ) )
            {
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:320:1: ( ( rule__LocalTranslation__TextAssignment_2 ) )
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:321:1: ( rule__LocalTranslation__TextAssignment_2 )
            {
             before(grammarAccess.getLocalTranslationAccess().getTextAssignment_2()); 
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:322:1: ( rule__LocalTranslation__TextAssignment_2 )
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:322:2: rule__LocalTranslation__TextAssignment_2
            {
            pushFollow(FOLLOW_rule__LocalTranslation__TextAssignment_2_in_rule__LocalTranslation__Group__2__Impl584);
            rule__LocalTranslation__TextAssignment_2();

            state._fsp--;


            }

             after(grammarAccess.getLocalTranslationAccess().getTextAssignment_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LocalTranslation__Group__2__Impl"


    // $ANTLR start "rule__LocalTranslations__TranslationsAssignment_0"
    // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:339:1: rule__LocalTranslations__TranslationsAssignment_0 : ( ruleLocalTranslation ) ;
    public final void rule__LocalTranslations__TranslationsAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:343:1: ( ( ruleLocalTranslation ) )
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:344:1: ( ruleLocalTranslation )
            {
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:344:1: ( ruleLocalTranslation )
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:345:1: ruleLocalTranslation
            {
             before(grammarAccess.getLocalTranslationsAccess().getTranslationsLocalTranslationParserRuleCall_0_0()); 
            pushFollow(FOLLOW_ruleLocalTranslation_in_rule__LocalTranslations__TranslationsAssignment_0625);
            ruleLocalTranslation();

            state._fsp--;

             after(grammarAccess.getLocalTranslationsAccess().getTranslationsLocalTranslationParserRuleCall_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LocalTranslations__TranslationsAssignment_0"


    // $ANTLR start "rule__LocalTranslations__TranslationsAssignment_1_1"
    // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:354:1: rule__LocalTranslations__TranslationsAssignment_1_1 : ( ruleLocalTranslation ) ;
    public final void rule__LocalTranslations__TranslationsAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:358:1: ( ( ruleLocalTranslation ) )
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:359:1: ( ruleLocalTranslation )
            {
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:359:1: ( ruleLocalTranslation )
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:360:1: ruleLocalTranslation
            {
             before(grammarAccess.getLocalTranslationsAccess().getTranslationsLocalTranslationParserRuleCall_1_1_0()); 
            pushFollow(FOLLOW_ruleLocalTranslation_in_rule__LocalTranslations__TranslationsAssignment_1_1656);
            ruleLocalTranslation();

            state._fsp--;

             after(grammarAccess.getLocalTranslationsAccess().getTranslationsLocalTranslationParserRuleCall_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LocalTranslations__TranslationsAssignment_1_1"


    // $ANTLR start "rule__LocalTranslation__LocaleAssignment_0"
    // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:369:1: rule__LocalTranslation__LocaleAssignment_0 : ( RULE_ID ) ;
    public final void rule__LocalTranslation__LocaleAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:373:1: ( ( RULE_ID ) )
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:374:1: ( RULE_ID )
            {
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:374:1: ( RULE_ID )
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:375:1: RULE_ID
            {
             before(grammarAccess.getLocalTranslationAccess().getLocaleIDTerminalRuleCall_0_0()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__LocalTranslation__LocaleAssignment_0687); 
             after(grammarAccess.getLocalTranslationAccess().getLocaleIDTerminalRuleCall_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LocalTranslation__LocaleAssignment_0"


    // $ANTLR start "rule__LocalTranslation__TextAssignment_2"
    // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:384:1: rule__LocalTranslation__TextAssignment_2 : ( RULE_STRING ) ;
    public final void rule__LocalTranslation__TextAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:388:1: ( ( RULE_STRING ) )
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:389:1: ( RULE_STRING )
            {
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:389:1: ( RULE_STRING )
            // ../../ui/com.odcgroup.translation.dsl.ui/src-gen/com/odcgroup/translation/ui/contentassist/antlr/internal/InternalTranslationDsl.g:390:1: RULE_STRING
            {
             before(grammarAccess.getLocalTranslationAccess().getTextSTRINGTerminalRuleCall_2_0()); 
            match(input,RULE_STRING,FOLLOW_RULE_STRING_in_rule__LocalTranslation__TextAssignment_2718); 
             after(grammarAccess.getLocalTranslationAccess().getTextSTRINGTerminalRuleCall_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LocalTranslation__TextAssignment_2"

    // Delegated rules


 

    public static final BitSet FOLLOW_ruleLocalTranslations_in_entryRuleLocalTranslations61 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLocalTranslations68 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__LocalTranslations__Group__0_in_ruleLocalTranslations94 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLocalTranslation_in_entryRuleLocalTranslation121 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLocalTranslation128 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__LocalTranslation__Group__0_in_ruleLocalTranslation154 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__LocalTranslations__Group__0__Impl_in_rule__LocalTranslations__Group__0190 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_rule__LocalTranslations__Group__1_in_rule__LocalTranslations__Group__0193 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__LocalTranslations__TranslationsAssignment_0_in_rule__LocalTranslations__Group__0__Impl220 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__LocalTranslations__Group__1__Impl_in_rule__LocalTranslations__Group__1250 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__LocalTranslations__Group_1__0_in_rule__LocalTranslations__Group__1__Impl277 = new BitSet(new long[]{0x0000000000000802L});
    public static final BitSet FOLLOW_rule__LocalTranslations__Group_1__0__Impl_in_rule__LocalTranslations__Group_1__0312 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__LocalTranslations__Group_1__1_in_rule__LocalTranslations__Group_1__0315 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_11_in_rule__LocalTranslations__Group_1__0__Impl343 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__LocalTranslations__Group_1__1__Impl_in_rule__LocalTranslations__Group_1__1374 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__LocalTranslations__TranslationsAssignment_1_1_in_rule__LocalTranslations__Group_1__1__Impl401 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__LocalTranslation__Group__0__Impl_in_rule__LocalTranslation__Group__0435 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_rule__LocalTranslation__Group__1_in_rule__LocalTranslation__Group__0438 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__LocalTranslation__LocaleAssignment_0_in_rule__LocalTranslation__Group__0__Impl465 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__LocalTranslation__Group__1__Impl_in_rule__LocalTranslation__Group__1495 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_rule__LocalTranslation__Group__2_in_rule__LocalTranslation__Group__1498 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_12_in_rule__LocalTranslation__Group__1__Impl526 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__LocalTranslation__Group__2__Impl_in_rule__LocalTranslation__Group__2557 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__LocalTranslation__TextAssignment_2_in_rule__LocalTranslation__Group__2__Impl584 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLocalTranslation_in_rule__LocalTranslations__TranslationsAssignment_0625 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLocalTranslation_in_rule__LocalTranslations__TranslationsAssignment_1_1656 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__LocalTranslation__LocaleAssignment_0687 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_STRING_in_rule__LocalTranslation__TextAssignment_2718 = new BitSet(new long[]{0x0000000000000002L});

}
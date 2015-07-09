package com.odcgroup.page.ui.contentassist.antlr.internal; 

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
import com.odcgroup.page.services.PageGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalPageParser extends AbstractInternalContentAssistParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ID", "RULE_STRING", "RULE_VALUE", "RULE_URI", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "'A'", "'S'", "'metamodelVersion'", "'='", "'---'", "':'", "'labels'", "','", "'toolTips'", "'{'", "'}'", "'!'", "'Event'", "'('", "')'", "'//'", "'messages'", "'Snippet'", "'ud'"
    };
    public static final int RULE_VALUE=6;
    public static final int RULE_ID=4;
    public static final int T__29=29;
    public static final int T__28=28;
    public static final int T__27=27;
    public static final int T__26=26;
    public static final int T__25=25;
    public static final int T__24=24;
    public static final int T__23=23;
    public static final int T__22=22;
    public static final int T__21=21;
    public static final int T__20=20;
    public static final int RULE_URI=7;
    public static final int RULE_SL_COMMENT=9;
    public static final int EOF=-1;
    public static final int RULE_ML_COMMENT=8;
    public static final int T__19=19;
    public static final int RULE_STRING=5;
    public static final int T__16=16;
    public static final int T__15=15;
    public static final int T__18=18;
    public static final int T__17=17;
    public static final int T__12=12;
    public static final int T__11=11;
    public static final int T__14=14;
    public static final int T__13=13;
    public static final int RULE_WS=10;

    // delegates
    // delegators


        public InternalPageParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalPageParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalPageParser.tokenNames; }
    public String getGrammarFileName() { return "../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g"; }


     
     	private PageGrammarAccess grammarAccess;
     	
        public void setGrammarAccess(PageGrammarAccess grammarAccess) {
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




    // $ANTLR start "entryRuleModel"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:60:1: entryRuleModel : ruleModel EOF ;
    public final void entryRuleModel() throws RecognitionException {
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:61:1: ( ruleModel EOF )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:62:1: ruleModel EOF
            {
             before(grammarAccess.getModelRule()); 
            pushFollow(FollowSets000.FOLLOW_ruleModel_in_entryRuleModel61);
            ruleModel();

            state._fsp--;

             after(grammarAccess.getModelRule()); 
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleModel68); 

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
    // $ANTLR end "entryRuleModel"


    // $ANTLR start "ruleModel"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:69:1: ruleModel : ( ( rule__Model__Group__0 ) ) ;
    public final void ruleModel() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:73:2: ( ( ( rule__Model__Group__0 ) ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:74:1: ( ( rule__Model__Group__0 ) )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:74:1: ( ( rule__Model__Group__0 ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:75:1: ( rule__Model__Group__0 )
            {
             before(grammarAccess.getModelAccess().getGroup()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:76:1: ( rule__Model__Group__0 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:76:2: rule__Model__Group__0
            {
            pushFollow(FollowSets000.FOLLOW_rule__Model__Group__0_in_ruleModel94);
            rule__Model__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getModelAccess().getGroup()); 

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
    // $ANTLR end "ruleModel"


    // $ANTLR start "entryRuleWidget"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:88:1: entryRuleWidget : ruleWidget EOF ;
    public final void entryRuleWidget() throws RecognitionException {
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:89:1: ( ruleWidget EOF )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:90:1: ruleWidget EOF
            {
             before(grammarAccess.getWidgetRule()); 
            pushFollow(FollowSets000.FOLLOW_ruleWidget_in_entryRuleWidget121);
            ruleWidget();

            state._fsp--;

             after(grammarAccess.getWidgetRule()); 
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleWidget128); 

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
    // $ANTLR end "entryRuleWidget"


    // $ANTLR start "ruleWidget"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:97:1: ruleWidget : ( ( rule__Widget__Group__0 ) ) ;
    public final void ruleWidget() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:101:2: ( ( ( rule__Widget__Group__0 ) ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:102:1: ( ( rule__Widget__Group__0 ) )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:102:1: ( ( rule__Widget__Group__0 ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:103:1: ( rule__Widget__Group__0 )
            {
             before(grammarAccess.getWidgetAccess().getGroup()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:104:1: ( rule__Widget__Group__0 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:104:2: rule__Widget__Group__0
            {
            pushFollow(FollowSets000.FOLLOW_rule__Widget__Group__0_in_ruleWidget154);
            rule__Widget__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getWidgetAccess().getGroup()); 

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
    // $ANTLR end "ruleWidget"


    // $ANTLR start "entryRuleProperty"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:118:1: entryRuleProperty : ruleProperty EOF ;
    public final void entryRuleProperty() throws RecognitionException {
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:119:1: ( ruleProperty EOF )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:120:1: ruleProperty EOF
            {
             before(grammarAccess.getPropertyRule()); 
            pushFollow(FollowSets000.FOLLOW_ruleProperty_in_entryRuleProperty183);
            ruleProperty();

            state._fsp--;

             after(grammarAccess.getPropertyRule()); 
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleProperty190); 

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
    // $ANTLR end "entryRuleProperty"


    // $ANTLR start "ruleProperty"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:127:1: ruleProperty : ( ( rule__Property__Group__0 ) ) ;
    public final void ruleProperty() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:131:2: ( ( ( rule__Property__Group__0 ) ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:132:1: ( ( rule__Property__Group__0 ) )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:132:1: ( ( rule__Property__Group__0 ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:133:1: ( rule__Property__Group__0 )
            {
             before(grammarAccess.getPropertyAccess().getGroup()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:134:1: ( rule__Property__Group__0 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:134:2: rule__Property__Group__0
            {
            pushFollow(FollowSets000.FOLLOW_rule__Property__Group__0_in_ruleProperty216);
            rule__Property__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getPropertyAccess().getGroup()); 

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
    // $ANTLR end "ruleProperty"


    // $ANTLR start "entryRuleEvent"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:146:1: entryRuleEvent : ruleEvent EOF ;
    public final void entryRuleEvent() throws RecognitionException {
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:147:1: ( ruleEvent EOF )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:148:1: ruleEvent EOF
            {
             before(grammarAccess.getEventRule()); 
            pushFollow(FollowSets000.FOLLOW_ruleEvent_in_entryRuleEvent243);
            ruleEvent();

            state._fsp--;

             after(grammarAccess.getEventRule()); 
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleEvent250); 

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
    // $ANTLR end "entryRuleEvent"


    // $ANTLR start "ruleEvent"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:155:1: ruleEvent : ( ( rule__Event__Group__0 ) ) ;
    public final void ruleEvent() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:159:2: ( ( ( rule__Event__Group__0 ) ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:160:1: ( ( rule__Event__Group__0 ) )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:160:1: ( ( rule__Event__Group__0 ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:161:1: ( rule__Event__Group__0 )
            {
             before(grammarAccess.getEventAccess().getGroup()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:162:1: ( rule__Event__Group__0 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:162:2: rule__Event__Group__0
            {
            pushFollow(FollowSets000.FOLLOW_rule__Event__Group__0_in_ruleEvent276);
            rule__Event__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getEventAccess().getGroup()); 

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
    // $ANTLR end "ruleEvent"


    // $ANTLR start "entryRuleSnippet"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:174:1: entryRuleSnippet : ruleSnippet EOF ;
    public final void entryRuleSnippet() throws RecognitionException {
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:175:1: ( ruleSnippet EOF )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:176:1: ruleSnippet EOF
            {
             before(grammarAccess.getSnippetRule()); 
            pushFollow(FollowSets000.FOLLOW_ruleSnippet_in_entryRuleSnippet303);
            ruleSnippet();

            state._fsp--;

             after(grammarAccess.getSnippetRule()); 
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleSnippet310); 

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
    // $ANTLR end "entryRuleSnippet"


    // $ANTLR start "ruleSnippet"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:183:1: ruleSnippet : ( ( rule__Snippet__Group__0 ) ) ;
    public final void ruleSnippet() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:187:2: ( ( ( rule__Snippet__Group__0 ) ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:188:1: ( ( rule__Snippet__Group__0 ) )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:188:1: ( ( rule__Snippet__Group__0 ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:189:1: ( rule__Snippet__Group__0 )
            {
             before(grammarAccess.getSnippetAccess().getGroup()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:190:1: ( rule__Snippet__Group__0 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:190:2: rule__Snippet__Group__0
            {
            pushFollow(FollowSets000.FOLLOW_rule__Snippet__Group__0_in_ruleSnippet336);
            rule__Snippet__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getSnippetAccess().getGroup()); 

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
    // $ANTLR end "ruleSnippet"


    // $ANTLR start "entryRuleTranslation"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:202:1: entryRuleTranslation : ruleTranslation EOF ;
    public final void entryRuleTranslation() throws RecognitionException {
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:203:1: ( ruleTranslation EOF )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:204:1: ruleTranslation EOF
            {
             before(grammarAccess.getTranslationRule()); 
            pushFollow(FollowSets000.FOLLOW_ruleTranslation_in_entryRuleTranslation363);
            ruleTranslation();

            state._fsp--;

             after(grammarAccess.getTranslationRule()); 
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleTranslation370); 

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
    // $ANTLR end "entryRuleTranslation"


    // $ANTLR start "ruleTranslation"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:211:1: ruleTranslation : ( ( rule__Translation__Group__0 ) ) ;
    public final void ruleTranslation() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:215:2: ( ( ( rule__Translation__Group__0 ) ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:216:1: ( ( rule__Translation__Group__0 ) )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:216:1: ( ( rule__Translation__Group__0 ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:217:1: ( rule__Translation__Group__0 )
            {
             before(grammarAccess.getTranslationAccess().getGroup()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:218:1: ( rule__Translation__Group__0 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:218:2: rule__Translation__Group__0
            {
            pushFollow(FollowSets000.FOLLOW_rule__Translation__Group__0_in_ruleTranslation396);
            rule__Translation__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getTranslationAccess().getGroup()); 

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
    // $ANTLR end "ruleTranslation"


    // $ANTLR start "entryRuleParameter"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:230:1: entryRuleParameter : ruleParameter EOF ;
    public final void entryRuleParameter() throws RecognitionException {
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:231:1: ( ruleParameter EOF )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:232:1: ruleParameter EOF
            {
             before(grammarAccess.getParameterRule()); 
            pushFollow(FollowSets000.FOLLOW_ruleParameter_in_entryRuleParameter423);
            ruleParameter();

            state._fsp--;

             after(grammarAccess.getParameterRule()); 
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleParameter430); 

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
    // $ANTLR end "entryRuleParameter"


    // $ANTLR start "ruleParameter"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:239:1: ruleParameter : ( ( rule__Parameter__Group__0 ) ) ;
    public final void ruleParameter() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:243:2: ( ( ( rule__Parameter__Group__0 ) ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:244:1: ( ( rule__Parameter__Group__0 ) )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:244:1: ( ( rule__Parameter__Group__0 ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:245:1: ( rule__Parameter__Group__0 )
            {
             before(grammarAccess.getParameterAccess().getGroup()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:246:1: ( rule__Parameter__Group__0 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:246:2: rule__Parameter__Group__0
            {
            pushFollow(FollowSets000.FOLLOW_rule__Parameter__Group__0_in_ruleParameter456);
            rule__Parameter__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getParameterAccess().getGroup()); 

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
    // $ANTLR end "ruleParameter"


    // $ANTLR start "entryRuleLibraryName"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:258:1: entryRuleLibraryName : ruleLibraryName EOF ;
    public final void entryRuleLibraryName() throws RecognitionException {
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:259:1: ( ruleLibraryName EOF )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:260:1: ruleLibraryName EOF
            {
             before(grammarAccess.getLibraryNameRule()); 
            pushFollow(FollowSets000.FOLLOW_ruleLibraryName_in_entryRuleLibraryName483);
            ruleLibraryName();

            state._fsp--;

             after(grammarAccess.getLibraryNameRule()); 
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleLibraryName490); 

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
    // $ANTLR end "entryRuleLibraryName"


    // $ANTLR start "ruleLibraryName"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:267:1: ruleLibraryName : ( RULE_ID ) ;
    public final void ruleLibraryName() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:271:2: ( ( RULE_ID ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:272:1: ( RULE_ID )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:272:1: ( RULE_ID )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:273:1: RULE_ID
            {
             before(grammarAccess.getLibraryNameAccess().getIDTerminalRuleCall()); 
            match(input,RULE_ID,FollowSets000.FOLLOW_RULE_ID_in_ruleLibraryName516); 
             after(grammarAccess.getLibraryNameAccess().getIDTerminalRuleCall()); 

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
    // $ANTLR end "ruleLibraryName"


    // $ANTLR start "entryRuleString_Value"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:286:1: entryRuleString_Value : ruleString_Value EOF ;
    public final void entryRuleString_Value() throws RecognitionException {
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:287:1: ( ruleString_Value EOF )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:288:1: ruleString_Value EOF
            {
             before(grammarAccess.getString_ValueRule()); 
            pushFollow(FollowSets000.FOLLOW_ruleString_Value_in_entryRuleString_Value542);
            ruleString_Value();

            state._fsp--;

             after(grammarAccess.getString_ValueRule()); 
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleString_Value549); 

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
    // $ANTLR end "entryRuleString_Value"


    // $ANTLR start "ruleString_Value"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:295:1: ruleString_Value : ( ( rule__String_Value__Alternatives ) ) ;
    public final void ruleString_Value() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:299:2: ( ( ( rule__String_Value__Alternatives ) ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:300:1: ( ( rule__String_Value__Alternatives ) )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:300:1: ( ( rule__String_Value__Alternatives ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:301:1: ( rule__String_Value__Alternatives )
            {
             before(grammarAccess.getString_ValueAccess().getAlternatives()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:302:1: ( rule__String_Value__Alternatives )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:302:2: rule__String_Value__Alternatives
            {
            pushFollow(FollowSets000.FOLLOW_rule__String_Value__Alternatives_in_ruleString_Value575);
            rule__String_Value__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getString_ValueAccess().getAlternatives()); 

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
    // $ANTLR end "ruleString_Value"


    // $ANTLR start "ruleEventNature"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:315:1: ruleEventNature : ( ( rule__EventNature__Alternatives ) ) ;
    public final void ruleEventNature() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:319:1: ( ( ( rule__EventNature__Alternatives ) ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:320:1: ( ( rule__EventNature__Alternatives ) )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:320:1: ( ( rule__EventNature__Alternatives ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:321:1: ( rule__EventNature__Alternatives )
            {
             before(grammarAccess.getEventNatureAccess().getAlternatives()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:322:1: ( rule__EventNature__Alternatives )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:322:2: rule__EventNature__Alternatives
            {
            pushFollow(FollowSets000.FOLLOW_rule__EventNature__Alternatives_in_ruleEventNature612);
            rule__EventNature__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getEventNatureAccess().getAlternatives()); 

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
    // $ANTLR end "ruleEventNature"


    // $ANTLR start "rule__String_Value__Alternatives"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:333:1: rule__String_Value__Alternatives : ( ( RULE_STRING ) | ( RULE_ID ) | ( RULE_VALUE ) );
    public final void rule__String_Value__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:337:1: ( ( RULE_STRING ) | ( RULE_ID ) | ( RULE_VALUE ) )
            int alt1=3;
            switch ( input.LA(1) ) {
            case RULE_STRING:
                {
                alt1=1;
                }
                break;
            case RULE_ID:
                {
                alt1=2;
                }
                break;
            case RULE_VALUE:
                {
                alt1=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }

            switch (alt1) {
                case 1 :
                    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:338:1: ( RULE_STRING )
                    {
                    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:338:1: ( RULE_STRING )
                    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:339:1: RULE_STRING
                    {
                     before(grammarAccess.getString_ValueAccess().getSTRINGTerminalRuleCall_0()); 
                    match(input,RULE_STRING,FollowSets000.FOLLOW_RULE_STRING_in_rule__String_Value__Alternatives647); 
                     after(grammarAccess.getString_ValueAccess().getSTRINGTerminalRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:344:6: ( RULE_ID )
                    {
                    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:344:6: ( RULE_ID )
                    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:345:1: RULE_ID
                    {
                     before(grammarAccess.getString_ValueAccess().getIDTerminalRuleCall_1()); 
                    match(input,RULE_ID,FollowSets000.FOLLOW_RULE_ID_in_rule__String_Value__Alternatives664); 
                     after(grammarAccess.getString_ValueAccess().getIDTerminalRuleCall_1()); 

                    }


                    }
                    break;
                case 3 :
                    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:350:6: ( RULE_VALUE )
                    {
                    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:350:6: ( RULE_VALUE )
                    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:351:1: RULE_VALUE
                    {
                     before(grammarAccess.getString_ValueAccess().getVALUETerminalRuleCall_2()); 
                    match(input,RULE_VALUE,FollowSets000.FOLLOW_RULE_VALUE_in_rule__String_Value__Alternatives681); 
                     after(grammarAccess.getString_ValueAccess().getVALUETerminalRuleCall_2()); 

                    }


                    }
                    break;

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
    // $ANTLR end "rule__String_Value__Alternatives"


    // $ANTLR start "rule__EventNature__Alternatives"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:361:1: rule__EventNature__Alternatives : ( ( ( 'A' ) ) | ( ( 'S' ) ) );
    public final void rule__EventNature__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:365:1: ( ( ( 'A' ) ) | ( ( 'S' ) ) )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==11) ) {
                alt2=1;
            }
            else if ( (LA2_0==12) ) {
                alt2=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:366:1: ( ( 'A' ) )
                    {
                    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:366:1: ( ( 'A' ) )
                    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:367:1: ( 'A' )
                    {
                     before(grammarAccess.getEventNatureAccess().getADVANCEDEnumLiteralDeclaration_0()); 
                    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:368:1: ( 'A' )
                    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:368:3: 'A'
                    {
                    match(input,11,FollowSets000.FOLLOW_11_in_rule__EventNature__Alternatives714); 

                    }

                     after(grammarAccess.getEventNatureAccess().getADVANCEDEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:373:6: ( ( 'S' ) )
                    {
                    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:373:6: ( ( 'S' ) )
                    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:374:1: ( 'S' )
                    {
                     before(grammarAccess.getEventNatureAccess().getSIMPLIFIEDEnumLiteralDeclaration_1()); 
                    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:375:1: ( 'S' )
                    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:375:3: 'S'
                    {
                    match(input,12,FollowSets000.FOLLOW_12_in_rule__EventNature__Alternatives735); 

                    }

                     after(grammarAccess.getEventNatureAccess().getSIMPLIFIEDEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;

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
    // $ANTLR end "rule__EventNature__Alternatives"


    // $ANTLR start "rule__Model__Group__0"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:387:1: rule__Model__Group__0 : rule__Model__Group__0__Impl rule__Model__Group__1 ;
    public final void rule__Model__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:391:1: ( rule__Model__Group__0__Impl rule__Model__Group__1 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:392:2: rule__Model__Group__0__Impl rule__Model__Group__1
            {
            pushFollow(FollowSets000.FOLLOW_rule__Model__Group__0__Impl_in_rule__Model__Group__0768);
            rule__Model__Group__0__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Model__Group__1_in_rule__Model__Group__0771);
            rule__Model__Group__1();

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
    // $ANTLR end "rule__Model__Group__0"


    // $ANTLR start "rule__Model__Group__0__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:399:1: rule__Model__Group__0__Impl : ( () ) ;
    public final void rule__Model__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:403:1: ( ( () ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:404:1: ( () )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:404:1: ( () )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:405:1: ()
            {
             before(grammarAccess.getModelAccess().getModelAction_0()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:406:1: ()
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:408:1: 
            {
            }

             after(grammarAccess.getModelAccess().getModelAction_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Model__Group__0__Impl"


    // $ANTLR start "rule__Model__Group__1"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:418:1: rule__Model__Group__1 : rule__Model__Group__1__Impl rule__Model__Group__2 ;
    public final void rule__Model__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:422:1: ( rule__Model__Group__1__Impl rule__Model__Group__2 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:423:2: rule__Model__Group__1__Impl rule__Model__Group__2
            {
            pushFollow(FollowSets000.FOLLOW_rule__Model__Group__1__Impl_in_rule__Model__Group__1829);
            rule__Model__Group__1__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Model__Group__2_in_rule__Model__Group__1832);
            rule__Model__Group__2();

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
    // $ANTLR end "rule__Model__Group__1"


    // $ANTLR start "rule__Model__Group__1__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:430:1: rule__Model__Group__1__Impl : ( 'metamodelVersion' ) ;
    public final void rule__Model__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:434:1: ( ( 'metamodelVersion' ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:435:1: ( 'metamodelVersion' )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:435:1: ( 'metamodelVersion' )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:436:1: 'metamodelVersion'
            {
             before(grammarAccess.getModelAccess().getMetamodelVersionKeyword_1()); 
            match(input,13,FollowSets000.FOLLOW_13_in_rule__Model__Group__1__Impl860); 
             after(grammarAccess.getModelAccess().getMetamodelVersionKeyword_1()); 

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
    // $ANTLR end "rule__Model__Group__1__Impl"


    // $ANTLR start "rule__Model__Group__2"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:449:1: rule__Model__Group__2 : rule__Model__Group__2__Impl rule__Model__Group__3 ;
    public final void rule__Model__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:453:1: ( rule__Model__Group__2__Impl rule__Model__Group__3 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:454:2: rule__Model__Group__2__Impl rule__Model__Group__3
            {
            pushFollow(FollowSets000.FOLLOW_rule__Model__Group__2__Impl_in_rule__Model__Group__2891);
            rule__Model__Group__2__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Model__Group__3_in_rule__Model__Group__2894);
            rule__Model__Group__3();

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
    // $ANTLR end "rule__Model__Group__2"


    // $ANTLR start "rule__Model__Group__2__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:461:1: rule__Model__Group__2__Impl : ( '=' ) ;
    public final void rule__Model__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:465:1: ( ( '=' ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:466:1: ( '=' )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:466:1: ( '=' )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:467:1: '='
            {
             before(grammarAccess.getModelAccess().getEqualsSignKeyword_2()); 
            match(input,14,FollowSets000.FOLLOW_14_in_rule__Model__Group__2__Impl922); 
             after(grammarAccess.getModelAccess().getEqualsSignKeyword_2()); 

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
    // $ANTLR end "rule__Model__Group__2__Impl"


    // $ANTLR start "rule__Model__Group__3"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:480:1: rule__Model__Group__3 : rule__Model__Group__3__Impl rule__Model__Group__4 ;
    public final void rule__Model__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:484:1: ( rule__Model__Group__3__Impl rule__Model__Group__4 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:485:2: rule__Model__Group__3__Impl rule__Model__Group__4
            {
            pushFollow(FollowSets000.FOLLOW_rule__Model__Group__3__Impl_in_rule__Model__Group__3953);
            rule__Model__Group__3__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Model__Group__4_in_rule__Model__Group__3956);
            rule__Model__Group__4();

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
    // $ANTLR end "rule__Model__Group__3"


    // $ANTLR start "rule__Model__Group__3__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:492:1: rule__Model__Group__3__Impl : ( ( rule__Model__MetamodelVersionAssignment_3 ) ) ;
    public final void rule__Model__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:496:1: ( ( ( rule__Model__MetamodelVersionAssignment_3 ) ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:497:1: ( ( rule__Model__MetamodelVersionAssignment_3 ) )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:497:1: ( ( rule__Model__MetamodelVersionAssignment_3 ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:498:1: ( rule__Model__MetamodelVersionAssignment_3 )
            {
             before(grammarAccess.getModelAccess().getMetamodelVersionAssignment_3()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:499:1: ( rule__Model__MetamodelVersionAssignment_3 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:499:2: rule__Model__MetamodelVersionAssignment_3
            {
            pushFollow(FollowSets000.FOLLOW_rule__Model__MetamodelVersionAssignment_3_in_rule__Model__Group__3__Impl983);
            rule__Model__MetamodelVersionAssignment_3();

            state._fsp--;


            }

             after(grammarAccess.getModelAccess().getMetamodelVersionAssignment_3()); 

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
    // $ANTLR end "rule__Model__Group__3__Impl"


    // $ANTLR start "rule__Model__Group__4"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:509:1: rule__Model__Group__4 : rule__Model__Group__4__Impl ;
    public final void rule__Model__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:513:1: ( rule__Model__Group__4__Impl )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:514:2: rule__Model__Group__4__Impl
            {
            pushFollow(FollowSets000.FOLLOW_rule__Model__Group__4__Impl_in_rule__Model__Group__41013);
            rule__Model__Group__4__Impl();

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
    // $ANTLR end "rule__Model__Group__4"


    // $ANTLR start "rule__Model__Group__4__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:520:1: rule__Model__Group__4__Impl : ( ( rule__Model__WidgetAssignment_4 ) ) ;
    public final void rule__Model__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:524:1: ( ( ( rule__Model__WidgetAssignment_4 ) ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:525:1: ( ( rule__Model__WidgetAssignment_4 ) )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:525:1: ( ( rule__Model__WidgetAssignment_4 ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:526:1: ( rule__Model__WidgetAssignment_4 )
            {
             before(grammarAccess.getModelAccess().getWidgetAssignment_4()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:527:1: ( rule__Model__WidgetAssignment_4 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:527:2: rule__Model__WidgetAssignment_4
            {
            pushFollow(FollowSets000.FOLLOW_rule__Model__WidgetAssignment_4_in_rule__Model__Group__4__Impl1040);
            rule__Model__WidgetAssignment_4();

            state._fsp--;


            }

             after(grammarAccess.getModelAccess().getWidgetAssignment_4()); 

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
    // $ANTLR end "rule__Model__Group__4__Impl"


    // $ANTLR start "rule__Widget__Group__0"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:547:1: rule__Widget__Group__0 : rule__Widget__Group__0__Impl rule__Widget__Group__1 ;
    public final void rule__Widget__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:551:1: ( rule__Widget__Group__0__Impl rule__Widget__Group__1 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:552:2: rule__Widget__Group__0__Impl rule__Widget__Group__1
            {
            pushFollow(FollowSets000.FOLLOW_rule__Widget__Group__0__Impl_in_rule__Widget__Group__01080);
            rule__Widget__Group__0__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Widget__Group__1_in_rule__Widget__Group__01083);
            rule__Widget__Group__1();

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
    // $ANTLR end "rule__Widget__Group__0"


    // $ANTLR start "rule__Widget__Group__0__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:559:1: rule__Widget__Group__0__Impl : ( () ) ;
    public final void rule__Widget__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:563:1: ( ( () ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:564:1: ( () )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:564:1: ( () )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:565:1: ()
            {
             before(grammarAccess.getWidgetAccess().getWidgetAction_0()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:566:1: ()
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:568:1: 
            {
            }

             after(grammarAccess.getWidgetAccess().getWidgetAction_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Widget__Group__0__Impl"


    // $ANTLR start "rule__Widget__Group__1"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:578:1: rule__Widget__Group__1 : rule__Widget__Group__1__Impl rule__Widget__Group__2 ;
    public final void rule__Widget__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:582:1: ( rule__Widget__Group__1__Impl rule__Widget__Group__2 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:583:2: rule__Widget__Group__1__Impl rule__Widget__Group__2
            {
            pushFollow(FollowSets000.FOLLOW_rule__Widget__Group__1__Impl_in_rule__Widget__Group__11141);
            rule__Widget__Group__1__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Widget__Group__2_in_rule__Widget__Group__11144);
            rule__Widget__Group__2();

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
    // $ANTLR end "rule__Widget__Group__1"


    // $ANTLR start "rule__Widget__Group__1__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:590:1: rule__Widget__Group__1__Impl : ( '---' ) ;
    public final void rule__Widget__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:594:1: ( ( '---' ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:595:1: ( '---' )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:595:1: ( '---' )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:596:1: '---'
            {
             before(grammarAccess.getWidgetAccess().getHyphenMinusHyphenMinusHyphenMinusKeyword_1()); 
            match(input,15,FollowSets000.FOLLOW_15_in_rule__Widget__Group__1__Impl1172); 
             after(grammarAccess.getWidgetAccess().getHyphenMinusHyphenMinusHyphenMinusKeyword_1()); 

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
    // $ANTLR end "rule__Widget__Group__1__Impl"


    // $ANTLR start "rule__Widget__Group__2"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:609:1: rule__Widget__Group__2 : rule__Widget__Group__2__Impl rule__Widget__Group__3 ;
    public final void rule__Widget__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:613:1: ( rule__Widget__Group__2__Impl rule__Widget__Group__3 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:614:2: rule__Widget__Group__2__Impl rule__Widget__Group__3
            {
            pushFollow(FollowSets000.FOLLOW_rule__Widget__Group__2__Impl_in_rule__Widget__Group__21203);
            rule__Widget__Group__2__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Widget__Group__3_in_rule__Widget__Group__21206);
            rule__Widget__Group__3();

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
    // $ANTLR end "rule__Widget__Group__2"


    // $ANTLR start "rule__Widget__Group__2__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:621:1: rule__Widget__Group__2__Impl : ( ( rule__Widget__Group_2__0 )? ) ;
    public final void rule__Widget__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:625:1: ( ( ( rule__Widget__Group_2__0 )? ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:626:1: ( ( rule__Widget__Group_2__0 )? )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:626:1: ( ( rule__Widget__Group_2__0 )? )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:627:1: ( rule__Widget__Group_2__0 )?
            {
             before(grammarAccess.getWidgetAccess().getGroup_2()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:628:1: ( rule__Widget__Group_2__0 )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==RULE_ID) ) {
                int LA3_1 = input.LA(2);

                if ( (LA3_1==16) ) {
                    alt3=1;
                }
            }
            switch (alt3) {
                case 1 :
                    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:628:2: rule__Widget__Group_2__0
                    {
                    pushFollow(FollowSets000.FOLLOW_rule__Widget__Group_2__0_in_rule__Widget__Group__2__Impl1233);
                    rule__Widget__Group_2__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getWidgetAccess().getGroup_2()); 

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
    // $ANTLR end "rule__Widget__Group__2__Impl"


    // $ANTLR start "rule__Widget__Group__3"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:638:1: rule__Widget__Group__3 : rule__Widget__Group__3__Impl rule__Widget__Group__4 ;
    public final void rule__Widget__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:642:1: ( rule__Widget__Group__3__Impl rule__Widget__Group__4 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:643:2: rule__Widget__Group__3__Impl rule__Widget__Group__4
            {
            pushFollow(FollowSets000.FOLLOW_rule__Widget__Group__3__Impl_in_rule__Widget__Group__31264);
            rule__Widget__Group__3__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Widget__Group__4_in_rule__Widget__Group__31267);
            rule__Widget__Group__4();

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
    // $ANTLR end "rule__Widget__Group__3"


    // $ANTLR start "rule__Widget__Group__3__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:650:1: rule__Widget__Group__3__Impl : ( ( rule__Widget__TypeNameAssignment_3 ) ) ;
    public final void rule__Widget__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:654:1: ( ( ( rule__Widget__TypeNameAssignment_3 ) ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:655:1: ( ( rule__Widget__TypeNameAssignment_3 ) )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:655:1: ( ( rule__Widget__TypeNameAssignment_3 ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:656:1: ( rule__Widget__TypeNameAssignment_3 )
            {
             before(grammarAccess.getWidgetAccess().getTypeNameAssignment_3()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:657:1: ( rule__Widget__TypeNameAssignment_3 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:657:2: rule__Widget__TypeNameAssignment_3
            {
            pushFollow(FollowSets000.FOLLOW_rule__Widget__TypeNameAssignment_3_in_rule__Widget__Group__3__Impl1294);
            rule__Widget__TypeNameAssignment_3();

            state._fsp--;


            }

             after(grammarAccess.getWidgetAccess().getTypeNameAssignment_3()); 

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
    // $ANTLR end "rule__Widget__Group__3__Impl"


    // $ANTLR start "rule__Widget__Group__4"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:667:1: rule__Widget__Group__4 : rule__Widget__Group__4__Impl rule__Widget__Group__5 ;
    public final void rule__Widget__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:671:1: ( rule__Widget__Group__4__Impl rule__Widget__Group__5 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:672:2: rule__Widget__Group__4__Impl rule__Widget__Group__5
            {
            pushFollow(FollowSets000.FOLLOW_rule__Widget__Group__4__Impl_in_rule__Widget__Group__41324);
            rule__Widget__Group__4__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Widget__Group__5_in_rule__Widget__Group__41327);
            rule__Widget__Group__5();

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
    // $ANTLR end "rule__Widget__Group__4"


    // $ANTLR start "rule__Widget__Group__4__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:679:1: rule__Widget__Group__4__Impl : ( '---' ) ;
    public final void rule__Widget__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:683:1: ( ( '---' ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:684:1: ( '---' )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:684:1: ( '---' )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:685:1: '---'
            {
             before(grammarAccess.getWidgetAccess().getHyphenMinusHyphenMinusHyphenMinusKeyword_4()); 
            match(input,15,FollowSets000.FOLLOW_15_in_rule__Widget__Group__4__Impl1355); 
             after(grammarAccess.getWidgetAccess().getHyphenMinusHyphenMinusHyphenMinusKeyword_4()); 

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
    // $ANTLR end "rule__Widget__Group__4__Impl"


    // $ANTLR start "rule__Widget__Group__5"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:698:1: rule__Widget__Group__5 : rule__Widget__Group__5__Impl rule__Widget__Group__6 ;
    public final void rule__Widget__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:702:1: ( rule__Widget__Group__5__Impl rule__Widget__Group__6 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:703:2: rule__Widget__Group__5__Impl rule__Widget__Group__6
            {
            pushFollow(FollowSets000.FOLLOW_rule__Widget__Group__5__Impl_in_rule__Widget__Group__51386);
            rule__Widget__Group__5__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Widget__Group__6_in_rule__Widget__Group__51389);
            rule__Widget__Group__6();

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
    // $ANTLR end "rule__Widget__Group__5"


    // $ANTLR start "rule__Widget__Group__5__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:710:1: rule__Widget__Group__5__Impl : ( ( rule__Widget__Group_5__0 )? ) ;
    public final void rule__Widget__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:714:1: ( ( ( rule__Widget__Group_5__0 )? ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:715:1: ( ( rule__Widget__Group_5__0 )? )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:715:1: ( ( rule__Widget__Group_5__0 )? )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:716:1: ( rule__Widget__Group_5__0 )?
            {
             before(grammarAccess.getWidgetAccess().getGroup_5()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:717:1: ( rule__Widget__Group_5__0 )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==17) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:717:2: rule__Widget__Group_5__0
                    {
                    pushFollow(FollowSets000.FOLLOW_rule__Widget__Group_5__0_in_rule__Widget__Group__5__Impl1416);
                    rule__Widget__Group_5__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getWidgetAccess().getGroup_5()); 

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
    // $ANTLR end "rule__Widget__Group__5__Impl"


    // $ANTLR start "rule__Widget__Group__6"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:727:1: rule__Widget__Group__6 : rule__Widget__Group__6__Impl rule__Widget__Group__7 ;
    public final void rule__Widget__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:731:1: ( rule__Widget__Group__6__Impl rule__Widget__Group__7 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:732:2: rule__Widget__Group__6__Impl rule__Widget__Group__7
            {
            pushFollow(FollowSets000.FOLLOW_rule__Widget__Group__6__Impl_in_rule__Widget__Group__61447);
            rule__Widget__Group__6__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Widget__Group__7_in_rule__Widget__Group__61450);
            rule__Widget__Group__7();

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
    // $ANTLR end "rule__Widget__Group__6"


    // $ANTLR start "rule__Widget__Group__6__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:739:1: rule__Widget__Group__6__Impl : ( ( rule__Widget__Group_6__0 )? ) ;
    public final void rule__Widget__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:743:1: ( ( ( rule__Widget__Group_6__0 )? ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:744:1: ( ( rule__Widget__Group_6__0 )? )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:744:1: ( ( rule__Widget__Group_6__0 )? )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:745:1: ( rule__Widget__Group_6__0 )?
            {
             before(grammarAccess.getWidgetAccess().getGroup_6()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:746:1: ( rule__Widget__Group_6__0 )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==19) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:746:2: rule__Widget__Group_6__0
                    {
                    pushFollow(FollowSets000.FOLLOW_rule__Widget__Group_6__0_in_rule__Widget__Group__6__Impl1477);
                    rule__Widget__Group_6__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getWidgetAccess().getGroup_6()); 

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
    // $ANTLR end "rule__Widget__Group__6__Impl"


    // $ANTLR start "rule__Widget__Group__7"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:756:1: rule__Widget__Group__7 : rule__Widget__Group__7__Impl rule__Widget__Group__8 ;
    public final void rule__Widget__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:760:1: ( rule__Widget__Group__7__Impl rule__Widget__Group__8 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:761:2: rule__Widget__Group__7__Impl rule__Widget__Group__8
            {
            pushFollow(FollowSets000.FOLLOW_rule__Widget__Group__7__Impl_in_rule__Widget__Group__71508);
            rule__Widget__Group__7__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Widget__Group__8_in_rule__Widget__Group__71511);
            rule__Widget__Group__8();

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
    // $ANTLR end "rule__Widget__Group__7"


    // $ANTLR start "rule__Widget__Group__7__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:768:1: rule__Widget__Group__7__Impl : ( ( rule__Widget__PropertiesAssignment_7 )* ) ;
    public final void rule__Widget__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:772:1: ( ( ( rule__Widget__PropertiesAssignment_7 )* ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:773:1: ( ( rule__Widget__PropertiesAssignment_7 )* )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:773:1: ( ( rule__Widget__PropertiesAssignment_7 )* )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:774:1: ( rule__Widget__PropertiesAssignment_7 )*
            {
             before(grammarAccess.getWidgetAccess().getPropertiesAssignment_7()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:775:1: ( rule__Widget__PropertiesAssignment_7 )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==RULE_ID) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:775:2: rule__Widget__PropertiesAssignment_7
            	    {
            	    pushFollow(FollowSets000.FOLLOW_rule__Widget__PropertiesAssignment_7_in_rule__Widget__Group__7__Impl1538);
            	    rule__Widget__PropertiesAssignment_7();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);

             after(grammarAccess.getWidgetAccess().getPropertiesAssignment_7()); 

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
    // $ANTLR end "rule__Widget__Group__7__Impl"


    // $ANTLR start "rule__Widget__Group__8"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:785:1: rule__Widget__Group__8 : rule__Widget__Group__8__Impl ;
    public final void rule__Widget__Group__8() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:789:1: ( rule__Widget__Group__8__Impl )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:790:2: rule__Widget__Group__8__Impl
            {
            pushFollow(FollowSets000.FOLLOW_rule__Widget__Group__8__Impl_in_rule__Widget__Group__81569);
            rule__Widget__Group__8__Impl();

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
    // $ANTLR end "rule__Widget__Group__8"


    // $ANTLR start "rule__Widget__Group__8__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:796:1: rule__Widget__Group__8__Impl : ( ( rule__Widget__Group_8__0 )? ) ;
    public final void rule__Widget__Group__8__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:800:1: ( ( ( rule__Widget__Group_8__0 )? ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:801:1: ( ( rule__Widget__Group_8__0 )? )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:801:1: ( ( rule__Widget__Group_8__0 )? )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:802:1: ( rule__Widget__Group_8__0 )?
            {
             before(grammarAccess.getWidgetAccess().getGroup_8()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:803:1: ( rule__Widget__Group_8__0 )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==20) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:803:2: rule__Widget__Group_8__0
                    {
                    pushFollow(FollowSets000.FOLLOW_rule__Widget__Group_8__0_in_rule__Widget__Group__8__Impl1596);
                    rule__Widget__Group_8__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getWidgetAccess().getGroup_8()); 

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
    // $ANTLR end "rule__Widget__Group__8__Impl"


    // $ANTLR start "rule__Widget__Group_2__0"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:831:1: rule__Widget__Group_2__0 : rule__Widget__Group_2__0__Impl rule__Widget__Group_2__1 ;
    public final void rule__Widget__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:835:1: ( rule__Widget__Group_2__0__Impl rule__Widget__Group_2__1 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:836:2: rule__Widget__Group_2__0__Impl rule__Widget__Group_2__1
            {
            pushFollow(FollowSets000.FOLLOW_rule__Widget__Group_2__0__Impl_in_rule__Widget__Group_2__01645);
            rule__Widget__Group_2__0__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Widget__Group_2__1_in_rule__Widget__Group_2__01648);
            rule__Widget__Group_2__1();

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
    // $ANTLR end "rule__Widget__Group_2__0"


    // $ANTLR start "rule__Widget__Group_2__0__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:843:1: rule__Widget__Group_2__0__Impl : ( ( rule__Widget__LibraryNameAssignment_2_0 ) ) ;
    public final void rule__Widget__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:847:1: ( ( ( rule__Widget__LibraryNameAssignment_2_0 ) ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:848:1: ( ( rule__Widget__LibraryNameAssignment_2_0 ) )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:848:1: ( ( rule__Widget__LibraryNameAssignment_2_0 ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:849:1: ( rule__Widget__LibraryNameAssignment_2_0 )
            {
             before(grammarAccess.getWidgetAccess().getLibraryNameAssignment_2_0()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:850:1: ( rule__Widget__LibraryNameAssignment_2_0 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:850:2: rule__Widget__LibraryNameAssignment_2_0
            {
            pushFollow(FollowSets000.FOLLOW_rule__Widget__LibraryNameAssignment_2_0_in_rule__Widget__Group_2__0__Impl1675);
            rule__Widget__LibraryNameAssignment_2_0();

            state._fsp--;


            }

             after(grammarAccess.getWidgetAccess().getLibraryNameAssignment_2_0()); 

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
    // $ANTLR end "rule__Widget__Group_2__0__Impl"


    // $ANTLR start "rule__Widget__Group_2__1"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:860:1: rule__Widget__Group_2__1 : rule__Widget__Group_2__1__Impl ;
    public final void rule__Widget__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:864:1: ( rule__Widget__Group_2__1__Impl )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:865:2: rule__Widget__Group_2__1__Impl
            {
            pushFollow(FollowSets000.FOLLOW_rule__Widget__Group_2__1__Impl_in_rule__Widget__Group_2__11705);
            rule__Widget__Group_2__1__Impl();

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
    // $ANTLR end "rule__Widget__Group_2__1"


    // $ANTLR start "rule__Widget__Group_2__1__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:871:1: rule__Widget__Group_2__1__Impl : ( ':' ) ;
    public final void rule__Widget__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:875:1: ( ( ':' ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:876:1: ( ':' )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:876:1: ( ':' )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:877:1: ':'
            {
             before(grammarAccess.getWidgetAccess().getColonKeyword_2_1()); 
            match(input,16,FollowSets000.FOLLOW_16_in_rule__Widget__Group_2__1__Impl1733); 
             after(grammarAccess.getWidgetAccess().getColonKeyword_2_1()); 

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
    // $ANTLR end "rule__Widget__Group_2__1__Impl"


    // $ANTLR start "rule__Widget__Group_5__0"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:894:1: rule__Widget__Group_5__0 : rule__Widget__Group_5__0__Impl rule__Widget__Group_5__1 ;
    public final void rule__Widget__Group_5__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:898:1: ( rule__Widget__Group_5__0__Impl rule__Widget__Group_5__1 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:899:2: rule__Widget__Group_5__0__Impl rule__Widget__Group_5__1
            {
            pushFollow(FollowSets000.FOLLOW_rule__Widget__Group_5__0__Impl_in_rule__Widget__Group_5__01768);
            rule__Widget__Group_5__0__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Widget__Group_5__1_in_rule__Widget__Group_5__01771);
            rule__Widget__Group_5__1();

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
    // $ANTLR end "rule__Widget__Group_5__0"


    // $ANTLR start "rule__Widget__Group_5__0__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:906:1: rule__Widget__Group_5__0__Impl : ( 'labels' ) ;
    public final void rule__Widget__Group_5__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:910:1: ( ( 'labels' ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:911:1: ( 'labels' )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:911:1: ( 'labels' )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:912:1: 'labels'
            {
             before(grammarAccess.getWidgetAccess().getLabelsKeyword_5_0()); 
            match(input,17,FollowSets000.FOLLOW_17_in_rule__Widget__Group_5__0__Impl1799); 
             after(grammarAccess.getWidgetAccess().getLabelsKeyword_5_0()); 

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
    // $ANTLR end "rule__Widget__Group_5__0__Impl"


    // $ANTLR start "rule__Widget__Group_5__1"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:925:1: rule__Widget__Group_5__1 : rule__Widget__Group_5__1__Impl rule__Widget__Group_5__2 ;
    public final void rule__Widget__Group_5__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:929:1: ( rule__Widget__Group_5__1__Impl rule__Widget__Group_5__2 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:930:2: rule__Widget__Group_5__1__Impl rule__Widget__Group_5__2
            {
            pushFollow(FollowSets000.FOLLOW_rule__Widget__Group_5__1__Impl_in_rule__Widget__Group_5__11830);
            rule__Widget__Group_5__1__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Widget__Group_5__2_in_rule__Widget__Group_5__11833);
            rule__Widget__Group_5__2();

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
    // $ANTLR end "rule__Widget__Group_5__1"


    // $ANTLR start "rule__Widget__Group_5__1__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:937:1: rule__Widget__Group_5__1__Impl : ( ( rule__Widget__LabelsAssignment_5_1 ) ) ;
    public final void rule__Widget__Group_5__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:941:1: ( ( ( rule__Widget__LabelsAssignment_5_1 ) ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:942:1: ( ( rule__Widget__LabelsAssignment_5_1 ) )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:942:1: ( ( rule__Widget__LabelsAssignment_5_1 ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:943:1: ( rule__Widget__LabelsAssignment_5_1 )
            {
             before(grammarAccess.getWidgetAccess().getLabelsAssignment_5_1()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:944:1: ( rule__Widget__LabelsAssignment_5_1 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:944:2: rule__Widget__LabelsAssignment_5_1
            {
            pushFollow(FollowSets000.FOLLOW_rule__Widget__LabelsAssignment_5_1_in_rule__Widget__Group_5__1__Impl1860);
            rule__Widget__LabelsAssignment_5_1();

            state._fsp--;


            }

             after(grammarAccess.getWidgetAccess().getLabelsAssignment_5_1()); 

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
    // $ANTLR end "rule__Widget__Group_5__1__Impl"


    // $ANTLR start "rule__Widget__Group_5__2"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:954:1: rule__Widget__Group_5__2 : rule__Widget__Group_5__2__Impl ;
    public final void rule__Widget__Group_5__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:958:1: ( rule__Widget__Group_5__2__Impl )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:959:2: rule__Widget__Group_5__2__Impl
            {
            pushFollow(FollowSets000.FOLLOW_rule__Widget__Group_5__2__Impl_in_rule__Widget__Group_5__21890);
            rule__Widget__Group_5__2__Impl();

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
    // $ANTLR end "rule__Widget__Group_5__2"


    // $ANTLR start "rule__Widget__Group_5__2__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:965:1: rule__Widget__Group_5__2__Impl : ( ( rule__Widget__Group_5_2__0 )* ) ;
    public final void rule__Widget__Group_5__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:969:1: ( ( ( rule__Widget__Group_5_2__0 )* ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:970:1: ( ( rule__Widget__Group_5_2__0 )* )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:970:1: ( ( rule__Widget__Group_5_2__0 )* )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:971:1: ( rule__Widget__Group_5_2__0 )*
            {
             before(grammarAccess.getWidgetAccess().getGroup_5_2()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:972:1: ( rule__Widget__Group_5_2__0 )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==18) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:972:2: rule__Widget__Group_5_2__0
            	    {
            	    pushFollow(FollowSets000.FOLLOW_rule__Widget__Group_5_2__0_in_rule__Widget__Group_5__2__Impl1917);
            	    rule__Widget__Group_5_2__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);

             after(grammarAccess.getWidgetAccess().getGroup_5_2()); 

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
    // $ANTLR end "rule__Widget__Group_5__2__Impl"


    // $ANTLR start "rule__Widget__Group_5_2__0"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:988:1: rule__Widget__Group_5_2__0 : rule__Widget__Group_5_2__0__Impl rule__Widget__Group_5_2__1 ;
    public final void rule__Widget__Group_5_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:992:1: ( rule__Widget__Group_5_2__0__Impl rule__Widget__Group_5_2__1 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:993:2: rule__Widget__Group_5_2__0__Impl rule__Widget__Group_5_2__1
            {
            pushFollow(FollowSets000.FOLLOW_rule__Widget__Group_5_2__0__Impl_in_rule__Widget__Group_5_2__01954);
            rule__Widget__Group_5_2__0__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Widget__Group_5_2__1_in_rule__Widget__Group_5_2__01957);
            rule__Widget__Group_5_2__1();

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
    // $ANTLR end "rule__Widget__Group_5_2__0"


    // $ANTLR start "rule__Widget__Group_5_2__0__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1000:1: rule__Widget__Group_5_2__0__Impl : ( ',' ) ;
    public final void rule__Widget__Group_5_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1004:1: ( ( ',' ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1005:1: ( ',' )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1005:1: ( ',' )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1006:1: ','
            {
             before(grammarAccess.getWidgetAccess().getCommaKeyword_5_2_0()); 
            match(input,18,FollowSets000.FOLLOW_18_in_rule__Widget__Group_5_2__0__Impl1985); 
             after(grammarAccess.getWidgetAccess().getCommaKeyword_5_2_0()); 

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
    // $ANTLR end "rule__Widget__Group_5_2__0__Impl"


    // $ANTLR start "rule__Widget__Group_5_2__1"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1019:1: rule__Widget__Group_5_2__1 : rule__Widget__Group_5_2__1__Impl ;
    public final void rule__Widget__Group_5_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1023:1: ( rule__Widget__Group_5_2__1__Impl )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1024:2: rule__Widget__Group_5_2__1__Impl
            {
            pushFollow(FollowSets000.FOLLOW_rule__Widget__Group_5_2__1__Impl_in_rule__Widget__Group_5_2__12016);
            rule__Widget__Group_5_2__1__Impl();

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
    // $ANTLR end "rule__Widget__Group_5_2__1"


    // $ANTLR start "rule__Widget__Group_5_2__1__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1030:1: rule__Widget__Group_5_2__1__Impl : ( ( rule__Widget__LabelsAssignment_5_2_1 ) ) ;
    public final void rule__Widget__Group_5_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1034:1: ( ( ( rule__Widget__LabelsAssignment_5_2_1 ) ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1035:1: ( ( rule__Widget__LabelsAssignment_5_2_1 ) )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1035:1: ( ( rule__Widget__LabelsAssignment_5_2_1 ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1036:1: ( rule__Widget__LabelsAssignment_5_2_1 )
            {
             before(grammarAccess.getWidgetAccess().getLabelsAssignment_5_2_1()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1037:1: ( rule__Widget__LabelsAssignment_5_2_1 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1037:2: rule__Widget__LabelsAssignment_5_2_1
            {
            pushFollow(FollowSets000.FOLLOW_rule__Widget__LabelsAssignment_5_2_1_in_rule__Widget__Group_5_2__1__Impl2043);
            rule__Widget__LabelsAssignment_5_2_1();

            state._fsp--;


            }

             after(grammarAccess.getWidgetAccess().getLabelsAssignment_5_2_1()); 

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
    // $ANTLR end "rule__Widget__Group_5_2__1__Impl"


    // $ANTLR start "rule__Widget__Group_6__0"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1051:1: rule__Widget__Group_6__0 : rule__Widget__Group_6__0__Impl rule__Widget__Group_6__1 ;
    public final void rule__Widget__Group_6__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1055:1: ( rule__Widget__Group_6__0__Impl rule__Widget__Group_6__1 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1056:2: rule__Widget__Group_6__0__Impl rule__Widget__Group_6__1
            {
            pushFollow(FollowSets000.FOLLOW_rule__Widget__Group_6__0__Impl_in_rule__Widget__Group_6__02077);
            rule__Widget__Group_6__0__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Widget__Group_6__1_in_rule__Widget__Group_6__02080);
            rule__Widget__Group_6__1();

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
    // $ANTLR end "rule__Widget__Group_6__0"


    // $ANTLR start "rule__Widget__Group_6__0__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1063:1: rule__Widget__Group_6__0__Impl : ( 'toolTips' ) ;
    public final void rule__Widget__Group_6__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1067:1: ( ( 'toolTips' ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1068:1: ( 'toolTips' )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1068:1: ( 'toolTips' )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1069:1: 'toolTips'
            {
             before(grammarAccess.getWidgetAccess().getToolTipsKeyword_6_0()); 
            match(input,19,FollowSets000.FOLLOW_19_in_rule__Widget__Group_6__0__Impl2108); 
             after(grammarAccess.getWidgetAccess().getToolTipsKeyword_6_0()); 

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
    // $ANTLR end "rule__Widget__Group_6__0__Impl"


    // $ANTLR start "rule__Widget__Group_6__1"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1082:1: rule__Widget__Group_6__1 : rule__Widget__Group_6__1__Impl rule__Widget__Group_6__2 ;
    public final void rule__Widget__Group_6__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1086:1: ( rule__Widget__Group_6__1__Impl rule__Widget__Group_6__2 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1087:2: rule__Widget__Group_6__1__Impl rule__Widget__Group_6__2
            {
            pushFollow(FollowSets000.FOLLOW_rule__Widget__Group_6__1__Impl_in_rule__Widget__Group_6__12139);
            rule__Widget__Group_6__1__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Widget__Group_6__2_in_rule__Widget__Group_6__12142);
            rule__Widget__Group_6__2();

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
    // $ANTLR end "rule__Widget__Group_6__1"


    // $ANTLR start "rule__Widget__Group_6__1__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1094:1: rule__Widget__Group_6__1__Impl : ( ( rule__Widget__ToolTipsAssignment_6_1 ) ) ;
    public final void rule__Widget__Group_6__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1098:1: ( ( ( rule__Widget__ToolTipsAssignment_6_1 ) ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1099:1: ( ( rule__Widget__ToolTipsAssignment_6_1 ) )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1099:1: ( ( rule__Widget__ToolTipsAssignment_6_1 ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1100:1: ( rule__Widget__ToolTipsAssignment_6_1 )
            {
             before(grammarAccess.getWidgetAccess().getToolTipsAssignment_6_1()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1101:1: ( rule__Widget__ToolTipsAssignment_6_1 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1101:2: rule__Widget__ToolTipsAssignment_6_1
            {
            pushFollow(FollowSets000.FOLLOW_rule__Widget__ToolTipsAssignment_6_1_in_rule__Widget__Group_6__1__Impl2169);
            rule__Widget__ToolTipsAssignment_6_1();

            state._fsp--;


            }

             after(grammarAccess.getWidgetAccess().getToolTipsAssignment_6_1()); 

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
    // $ANTLR end "rule__Widget__Group_6__1__Impl"


    // $ANTLR start "rule__Widget__Group_6__2"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1111:1: rule__Widget__Group_6__2 : rule__Widget__Group_6__2__Impl ;
    public final void rule__Widget__Group_6__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1115:1: ( rule__Widget__Group_6__2__Impl )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1116:2: rule__Widget__Group_6__2__Impl
            {
            pushFollow(FollowSets000.FOLLOW_rule__Widget__Group_6__2__Impl_in_rule__Widget__Group_6__22199);
            rule__Widget__Group_6__2__Impl();

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
    // $ANTLR end "rule__Widget__Group_6__2"


    // $ANTLR start "rule__Widget__Group_6__2__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1122:1: rule__Widget__Group_6__2__Impl : ( ( rule__Widget__Group_6_2__0 )* ) ;
    public final void rule__Widget__Group_6__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1126:1: ( ( ( rule__Widget__Group_6_2__0 )* ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1127:1: ( ( rule__Widget__Group_6_2__0 )* )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1127:1: ( ( rule__Widget__Group_6_2__0 )* )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1128:1: ( rule__Widget__Group_6_2__0 )*
            {
             before(grammarAccess.getWidgetAccess().getGroup_6_2()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1129:1: ( rule__Widget__Group_6_2__0 )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0==18) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1129:2: rule__Widget__Group_6_2__0
            	    {
            	    pushFollow(FollowSets000.FOLLOW_rule__Widget__Group_6_2__0_in_rule__Widget__Group_6__2__Impl2226);
            	    rule__Widget__Group_6_2__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop9;
                }
            } while (true);

             after(grammarAccess.getWidgetAccess().getGroup_6_2()); 

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
    // $ANTLR end "rule__Widget__Group_6__2__Impl"


    // $ANTLR start "rule__Widget__Group_6_2__0"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1145:1: rule__Widget__Group_6_2__0 : rule__Widget__Group_6_2__0__Impl rule__Widget__Group_6_2__1 ;
    public final void rule__Widget__Group_6_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1149:1: ( rule__Widget__Group_6_2__0__Impl rule__Widget__Group_6_2__1 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1150:2: rule__Widget__Group_6_2__0__Impl rule__Widget__Group_6_2__1
            {
            pushFollow(FollowSets000.FOLLOW_rule__Widget__Group_6_2__0__Impl_in_rule__Widget__Group_6_2__02263);
            rule__Widget__Group_6_2__0__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Widget__Group_6_2__1_in_rule__Widget__Group_6_2__02266);
            rule__Widget__Group_6_2__1();

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
    // $ANTLR end "rule__Widget__Group_6_2__0"


    // $ANTLR start "rule__Widget__Group_6_2__0__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1157:1: rule__Widget__Group_6_2__0__Impl : ( ',' ) ;
    public final void rule__Widget__Group_6_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1161:1: ( ( ',' ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1162:1: ( ',' )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1162:1: ( ',' )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1163:1: ','
            {
             before(grammarAccess.getWidgetAccess().getCommaKeyword_6_2_0()); 
            match(input,18,FollowSets000.FOLLOW_18_in_rule__Widget__Group_6_2__0__Impl2294); 
             after(grammarAccess.getWidgetAccess().getCommaKeyword_6_2_0()); 

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
    // $ANTLR end "rule__Widget__Group_6_2__0__Impl"


    // $ANTLR start "rule__Widget__Group_6_2__1"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1176:1: rule__Widget__Group_6_2__1 : rule__Widget__Group_6_2__1__Impl ;
    public final void rule__Widget__Group_6_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1180:1: ( rule__Widget__Group_6_2__1__Impl )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1181:2: rule__Widget__Group_6_2__1__Impl
            {
            pushFollow(FollowSets000.FOLLOW_rule__Widget__Group_6_2__1__Impl_in_rule__Widget__Group_6_2__12325);
            rule__Widget__Group_6_2__1__Impl();

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
    // $ANTLR end "rule__Widget__Group_6_2__1"


    // $ANTLR start "rule__Widget__Group_6_2__1__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1187:1: rule__Widget__Group_6_2__1__Impl : ( ( rule__Widget__ToolTipsAssignment_6_2_1 ) ) ;
    public final void rule__Widget__Group_6_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1191:1: ( ( ( rule__Widget__ToolTipsAssignment_6_2_1 ) ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1192:1: ( ( rule__Widget__ToolTipsAssignment_6_2_1 ) )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1192:1: ( ( rule__Widget__ToolTipsAssignment_6_2_1 ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1193:1: ( rule__Widget__ToolTipsAssignment_6_2_1 )
            {
             before(grammarAccess.getWidgetAccess().getToolTipsAssignment_6_2_1()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1194:1: ( rule__Widget__ToolTipsAssignment_6_2_1 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1194:2: rule__Widget__ToolTipsAssignment_6_2_1
            {
            pushFollow(FollowSets000.FOLLOW_rule__Widget__ToolTipsAssignment_6_2_1_in_rule__Widget__Group_6_2__1__Impl2352);
            rule__Widget__ToolTipsAssignment_6_2_1();

            state._fsp--;


            }

             after(grammarAccess.getWidgetAccess().getToolTipsAssignment_6_2_1()); 

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
    // $ANTLR end "rule__Widget__Group_6_2__1__Impl"


    // $ANTLR start "rule__Widget__Group_8__0"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1208:1: rule__Widget__Group_8__0 : rule__Widget__Group_8__0__Impl rule__Widget__Group_8__1 ;
    public final void rule__Widget__Group_8__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1212:1: ( rule__Widget__Group_8__0__Impl rule__Widget__Group_8__1 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1213:2: rule__Widget__Group_8__0__Impl rule__Widget__Group_8__1
            {
            pushFollow(FollowSets000.FOLLOW_rule__Widget__Group_8__0__Impl_in_rule__Widget__Group_8__02386);
            rule__Widget__Group_8__0__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Widget__Group_8__1_in_rule__Widget__Group_8__02389);
            rule__Widget__Group_8__1();

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
    // $ANTLR end "rule__Widget__Group_8__0"


    // $ANTLR start "rule__Widget__Group_8__0__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1220:1: rule__Widget__Group_8__0__Impl : ( '{' ) ;
    public final void rule__Widget__Group_8__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1224:1: ( ( '{' ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1225:1: ( '{' )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1225:1: ( '{' )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1226:1: '{'
            {
             before(grammarAccess.getWidgetAccess().getLeftCurlyBracketKeyword_8_0()); 
            match(input,20,FollowSets000.FOLLOW_20_in_rule__Widget__Group_8__0__Impl2417); 
             after(grammarAccess.getWidgetAccess().getLeftCurlyBracketKeyword_8_0()); 

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
    // $ANTLR end "rule__Widget__Group_8__0__Impl"


    // $ANTLR start "rule__Widget__Group_8__1"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1239:1: rule__Widget__Group_8__1 : rule__Widget__Group_8__1__Impl rule__Widget__Group_8__2 ;
    public final void rule__Widget__Group_8__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1243:1: ( rule__Widget__Group_8__1__Impl rule__Widget__Group_8__2 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1244:2: rule__Widget__Group_8__1__Impl rule__Widget__Group_8__2
            {
            pushFollow(FollowSets000.FOLLOW_rule__Widget__Group_8__1__Impl_in_rule__Widget__Group_8__12448);
            rule__Widget__Group_8__1__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Widget__Group_8__2_in_rule__Widget__Group_8__12451);
            rule__Widget__Group_8__2();

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
    // $ANTLR end "rule__Widget__Group_8__1"


    // $ANTLR start "rule__Widget__Group_8__1__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1251:1: rule__Widget__Group_8__1__Impl : ( ( rule__Widget__Group_8_1__0 )? ) ;
    public final void rule__Widget__Group_8__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1255:1: ( ( ( rule__Widget__Group_8_1__0 )? ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1256:1: ( ( rule__Widget__Group_8_1__0 )? )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1256:1: ( ( rule__Widget__Group_8_1__0 )? )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1257:1: ( rule__Widget__Group_8_1__0 )?
            {
             before(grammarAccess.getWidgetAccess().getGroup_8_1()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1258:1: ( rule__Widget__Group_8_1__0 )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==23) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1258:2: rule__Widget__Group_8_1__0
                    {
                    pushFollow(FollowSets000.FOLLOW_rule__Widget__Group_8_1__0_in_rule__Widget__Group_8__1__Impl2478);
                    rule__Widget__Group_8_1__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getWidgetAccess().getGroup_8_1()); 

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
    // $ANTLR end "rule__Widget__Group_8__1__Impl"


    // $ANTLR start "rule__Widget__Group_8__2"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1268:1: rule__Widget__Group_8__2 : rule__Widget__Group_8__2__Impl rule__Widget__Group_8__3 ;
    public final void rule__Widget__Group_8__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1272:1: ( rule__Widget__Group_8__2__Impl rule__Widget__Group_8__3 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1273:2: rule__Widget__Group_8__2__Impl rule__Widget__Group_8__3
            {
            pushFollow(FollowSets000.FOLLOW_rule__Widget__Group_8__2__Impl_in_rule__Widget__Group_8__22509);
            rule__Widget__Group_8__2__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Widget__Group_8__3_in_rule__Widget__Group_8__22512);
            rule__Widget__Group_8__3();

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
    // $ANTLR end "rule__Widget__Group_8__2"


    // $ANTLR start "rule__Widget__Group_8__2__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1280:1: rule__Widget__Group_8__2__Impl : ( ( rule__Widget__Group_8_2__0 )? ) ;
    public final void rule__Widget__Group_8__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1284:1: ( ( ( rule__Widget__Group_8_2__0 )? ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1285:1: ( ( rule__Widget__Group_8_2__0 )? )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1285:1: ( ( rule__Widget__Group_8_2__0 )? )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1286:1: ( rule__Widget__Group_8_2__0 )?
            {
             before(grammarAccess.getWidgetAccess().getGroup_8_2()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1287:1: ( rule__Widget__Group_8_2__0 )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==28) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1287:2: rule__Widget__Group_8_2__0
                    {
                    pushFollow(FollowSets000.FOLLOW_rule__Widget__Group_8_2__0_in_rule__Widget__Group_8__2__Impl2539);
                    rule__Widget__Group_8_2__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getWidgetAccess().getGroup_8_2()); 

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
    // $ANTLR end "rule__Widget__Group_8__2__Impl"


    // $ANTLR start "rule__Widget__Group_8__3"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1297:1: rule__Widget__Group_8__3 : rule__Widget__Group_8__3__Impl rule__Widget__Group_8__4 ;
    public final void rule__Widget__Group_8__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1301:1: ( rule__Widget__Group_8__3__Impl rule__Widget__Group_8__4 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1302:2: rule__Widget__Group_8__3__Impl rule__Widget__Group_8__4
            {
            pushFollow(FollowSets000.FOLLOW_rule__Widget__Group_8__3__Impl_in_rule__Widget__Group_8__32570);
            rule__Widget__Group_8__3__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Widget__Group_8__4_in_rule__Widget__Group_8__32573);
            rule__Widget__Group_8__4();

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
    // $ANTLR end "rule__Widget__Group_8__3"


    // $ANTLR start "rule__Widget__Group_8__3__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1309:1: rule__Widget__Group_8__3__Impl : ( ( rule__Widget__Group_8_3__0 )? ) ;
    public final void rule__Widget__Group_8__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1313:1: ( ( ( rule__Widget__Group_8_3__0 )? ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1314:1: ( ( rule__Widget__Group_8_3__0 )? )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1314:1: ( ( rule__Widget__Group_8_3__0 )? )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1315:1: ( rule__Widget__Group_8_3__0 )?
            {
             before(grammarAccess.getWidgetAccess().getGroup_8_3()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1316:1: ( rule__Widget__Group_8_3__0 )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==15) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1316:2: rule__Widget__Group_8_3__0
                    {
                    pushFollow(FollowSets000.FOLLOW_rule__Widget__Group_8_3__0_in_rule__Widget__Group_8__3__Impl2600);
                    rule__Widget__Group_8_3__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getWidgetAccess().getGroup_8_3()); 

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
    // $ANTLR end "rule__Widget__Group_8__3__Impl"


    // $ANTLR start "rule__Widget__Group_8__4"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1326:1: rule__Widget__Group_8__4 : rule__Widget__Group_8__4__Impl ;
    public final void rule__Widget__Group_8__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1330:1: ( rule__Widget__Group_8__4__Impl )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1331:2: rule__Widget__Group_8__4__Impl
            {
            pushFollow(FollowSets000.FOLLOW_rule__Widget__Group_8__4__Impl_in_rule__Widget__Group_8__42631);
            rule__Widget__Group_8__4__Impl();

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
    // $ANTLR end "rule__Widget__Group_8__4"


    // $ANTLR start "rule__Widget__Group_8__4__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1337:1: rule__Widget__Group_8__4__Impl : ( '}' ) ;
    public final void rule__Widget__Group_8__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1341:1: ( ( '}' ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1342:1: ( '}' )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1342:1: ( '}' )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1343:1: '}'
            {
             before(grammarAccess.getWidgetAccess().getRightCurlyBracketKeyword_8_4()); 
            match(input,21,FollowSets000.FOLLOW_21_in_rule__Widget__Group_8__4__Impl2659); 
             after(grammarAccess.getWidgetAccess().getRightCurlyBracketKeyword_8_4()); 

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
    // $ANTLR end "rule__Widget__Group_8__4__Impl"


    // $ANTLR start "rule__Widget__Group_8_1__0"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1366:1: rule__Widget__Group_8_1__0 : rule__Widget__Group_8_1__0__Impl rule__Widget__Group_8_1__1 ;
    public final void rule__Widget__Group_8_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1370:1: ( rule__Widget__Group_8_1__0__Impl rule__Widget__Group_8_1__1 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1371:2: rule__Widget__Group_8_1__0__Impl rule__Widget__Group_8_1__1
            {
            pushFollow(FollowSets000.FOLLOW_rule__Widget__Group_8_1__0__Impl_in_rule__Widget__Group_8_1__02700);
            rule__Widget__Group_8_1__0__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Widget__Group_8_1__1_in_rule__Widget__Group_8_1__02703);
            rule__Widget__Group_8_1__1();

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
    // $ANTLR end "rule__Widget__Group_8_1__0"


    // $ANTLR start "rule__Widget__Group_8_1__0__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1378:1: rule__Widget__Group_8_1__0__Impl : ( ( rule__Widget__EventsAssignment_8_1_0 ) ) ;
    public final void rule__Widget__Group_8_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1382:1: ( ( ( rule__Widget__EventsAssignment_8_1_0 ) ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1383:1: ( ( rule__Widget__EventsAssignment_8_1_0 ) )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1383:1: ( ( rule__Widget__EventsAssignment_8_1_0 ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1384:1: ( rule__Widget__EventsAssignment_8_1_0 )
            {
             before(grammarAccess.getWidgetAccess().getEventsAssignment_8_1_0()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1385:1: ( rule__Widget__EventsAssignment_8_1_0 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1385:2: rule__Widget__EventsAssignment_8_1_0
            {
            pushFollow(FollowSets000.FOLLOW_rule__Widget__EventsAssignment_8_1_0_in_rule__Widget__Group_8_1__0__Impl2730);
            rule__Widget__EventsAssignment_8_1_0();

            state._fsp--;


            }

             after(grammarAccess.getWidgetAccess().getEventsAssignment_8_1_0()); 

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
    // $ANTLR end "rule__Widget__Group_8_1__0__Impl"


    // $ANTLR start "rule__Widget__Group_8_1__1"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1395:1: rule__Widget__Group_8_1__1 : rule__Widget__Group_8_1__1__Impl ;
    public final void rule__Widget__Group_8_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1399:1: ( rule__Widget__Group_8_1__1__Impl )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1400:2: rule__Widget__Group_8_1__1__Impl
            {
            pushFollow(FollowSets000.FOLLOW_rule__Widget__Group_8_1__1__Impl_in_rule__Widget__Group_8_1__12760);
            rule__Widget__Group_8_1__1__Impl();

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
    // $ANTLR end "rule__Widget__Group_8_1__1"


    // $ANTLR start "rule__Widget__Group_8_1__1__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1406:1: rule__Widget__Group_8_1__1__Impl : ( ( rule__Widget__EventsAssignment_8_1_1 )* ) ;
    public final void rule__Widget__Group_8_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1410:1: ( ( ( rule__Widget__EventsAssignment_8_1_1 )* ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1411:1: ( ( rule__Widget__EventsAssignment_8_1_1 )* )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1411:1: ( ( rule__Widget__EventsAssignment_8_1_1 )* )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1412:1: ( rule__Widget__EventsAssignment_8_1_1 )*
            {
             before(grammarAccess.getWidgetAccess().getEventsAssignment_8_1_1()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1413:1: ( rule__Widget__EventsAssignment_8_1_1 )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==23) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1413:2: rule__Widget__EventsAssignment_8_1_1
            	    {
            	    pushFollow(FollowSets000.FOLLOW_rule__Widget__EventsAssignment_8_1_1_in_rule__Widget__Group_8_1__1__Impl2787);
            	    rule__Widget__EventsAssignment_8_1_1();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop13;
                }
            } while (true);

             after(grammarAccess.getWidgetAccess().getEventsAssignment_8_1_1()); 

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
    // $ANTLR end "rule__Widget__Group_8_1__1__Impl"


    // $ANTLR start "rule__Widget__Group_8_2__0"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1427:1: rule__Widget__Group_8_2__0 : rule__Widget__Group_8_2__0__Impl rule__Widget__Group_8_2__1 ;
    public final void rule__Widget__Group_8_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1431:1: ( rule__Widget__Group_8_2__0__Impl rule__Widget__Group_8_2__1 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1432:2: rule__Widget__Group_8_2__0__Impl rule__Widget__Group_8_2__1
            {
            pushFollow(FollowSets000.FOLLOW_rule__Widget__Group_8_2__0__Impl_in_rule__Widget__Group_8_2__02822);
            rule__Widget__Group_8_2__0__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Widget__Group_8_2__1_in_rule__Widget__Group_8_2__02825);
            rule__Widget__Group_8_2__1();

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
    // $ANTLR end "rule__Widget__Group_8_2__0"


    // $ANTLR start "rule__Widget__Group_8_2__0__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1439:1: rule__Widget__Group_8_2__0__Impl : ( ( rule__Widget__SnippetsAssignment_8_2_0 ) ) ;
    public final void rule__Widget__Group_8_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1443:1: ( ( ( rule__Widget__SnippetsAssignment_8_2_0 ) ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1444:1: ( ( rule__Widget__SnippetsAssignment_8_2_0 ) )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1444:1: ( ( rule__Widget__SnippetsAssignment_8_2_0 ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1445:1: ( rule__Widget__SnippetsAssignment_8_2_0 )
            {
             before(grammarAccess.getWidgetAccess().getSnippetsAssignment_8_2_0()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1446:1: ( rule__Widget__SnippetsAssignment_8_2_0 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1446:2: rule__Widget__SnippetsAssignment_8_2_0
            {
            pushFollow(FollowSets000.FOLLOW_rule__Widget__SnippetsAssignment_8_2_0_in_rule__Widget__Group_8_2__0__Impl2852);
            rule__Widget__SnippetsAssignment_8_2_0();

            state._fsp--;


            }

             after(grammarAccess.getWidgetAccess().getSnippetsAssignment_8_2_0()); 

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
    // $ANTLR end "rule__Widget__Group_8_2__0__Impl"


    // $ANTLR start "rule__Widget__Group_8_2__1"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1456:1: rule__Widget__Group_8_2__1 : rule__Widget__Group_8_2__1__Impl ;
    public final void rule__Widget__Group_8_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1460:1: ( rule__Widget__Group_8_2__1__Impl )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1461:2: rule__Widget__Group_8_2__1__Impl
            {
            pushFollow(FollowSets000.FOLLOW_rule__Widget__Group_8_2__1__Impl_in_rule__Widget__Group_8_2__12882);
            rule__Widget__Group_8_2__1__Impl();

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
    // $ANTLR end "rule__Widget__Group_8_2__1"


    // $ANTLR start "rule__Widget__Group_8_2__1__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1467:1: rule__Widget__Group_8_2__1__Impl : ( ( rule__Widget__SnippetsAssignment_8_2_1 )* ) ;
    public final void rule__Widget__Group_8_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1471:1: ( ( ( rule__Widget__SnippetsAssignment_8_2_1 )* ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1472:1: ( ( rule__Widget__SnippetsAssignment_8_2_1 )* )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1472:1: ( ( rule__Widget__SnippetsAssignment_8_2_1 )* )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1473:1: ( rule__Widget__SnippetsAssignment_8_2_1 )*
            {
             before(grammarAccess.getWidgetAccess().getSnippetsAssignment_8_2_1()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1474:1: ( rule__Widget__SnippetsAssignment_8_2_1 )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( (LA14_0==28) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1474:2: rule__Widget__SnippetsAssignment_8_2_1
            	    {
            	    pushFollow(FollowSets000.FOLLOW_rule__Widget__SnippetsAssignment_8_2_1_in_rule__Widget__Group_8_2__1__Impl2909);
            	    rule__Widget__SnippetsAssignment_8_2_1();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop14;
                }
            } while (true);

             after(grammarAccess.getWidgetAccess().getSnippetsAssignment_8_2_1()); 

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
    // $ANTLR end "rule__Widget__Group_8_2__1__Impl"


    // $ANTLR start "rule__Widget__Group_8_3__0"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1488:1: rule__Widget__Group_8_3__0 : rule__Widget__Group_8_3__0__Impl rule__Widget__Group_8_3__1 ;
    public final void rule__Widget__Group_8_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1492:1: ( rule__Widget__Group_8_3__0__Impl rule__Widget__Group_8_3__1 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1493:2: rule__Widget__Group_8_3__0__Impl rule__Widget__Group_8_3__1
            {
            pushFollow(FollowSets000.FOLLOW_rule__Widget__Group_8_3__0__Impl_in_rule__Widget__Group_8_3__02944);
            rule__Widget__Group_8_3__0__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Widget__Group_8_3__1_in_rule__Widget__Group_8_3__02947);
            rule__Widget__Group_8_3__1();

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
    // $ANTLR end "rule__Widget__Group_8_3__0"


    // $ANTLR start "rule__Widget__Group_8_3__0__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1500:1: rule__Widget__Group_8_3__0__Impl : ( ( rule__Widget__ContentsAssignment_8_3_0 ) ) ;
    public final void rule__Widget__Group_8_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1504:1: ( ( ( rule__Widget__ContentsAssignment_8_3_0 ) ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1505:1: ( ( rule__Widget__ContentsAssignment_8_3_0 ) )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1505:1: ( ( rule__Widget__ContentsAssignment_8_3_0 ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1506:1: ( rule__Widget__ContentsAssignment_8_3_0 )
            {
             before(grammarAccess.getWidgetAccess().getContentsAssignment_8_3_0()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1507:1: ( rule__Widget__ContentsAssignment_8_3_0 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1507:2: rule__Widget__ContentsAssignment_8_3_0
            {
            pushFollow(FollowSets000.FOLLOW_rule__Widget__ContentsAssignment_8_3_0_in_rule__Widget__Group_8_3__0__Impl2974);
            rule__Widget__ContentsAssignment_8_3_0();

            state._fsp--;


            }

             after(grammarAccess.getWidgetAccess().getContentsAssignment_8_3_0()); 

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
    // $ANTLR end "rule__Widget__Group_8_3__0__Impl"


    // $ANTLR start "rule__Widget__Group_8_3__1"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1517:1: rule__Widget__Group_8_3__1 : rule__Widget__Group_8_3__1__Impl ;
    public final void rule__Widget__Group_8_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1521:1: ( rule__Widget__Group_8_3__1__Impl )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1522:2: rule__Widget__Group_8_3__1__Impl
            {
            pushFollow(FollowSets000.FOLLOW_rule__Widget__Group_8_3__1__Impl_in_rule__Widget__Group_8_3__13004);
            rule__Widget__Group_8_3__1__Impl();

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
    // $ANTLR end "rule__Widget__Group_8_3__1"


    // $ANTLR start "rule__Widget__Group_8_3__1__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1528:1: rule__Widget__Group_8_3__1__Impl : ( ( rule__Widget__ContentsAssignment_8_3_1 )* ) ;
    public final void rule__Widget__Group_8_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1532:1: ( ( ( rule__Widget__ContentsAssignment_8_3_1 )* ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1533:1: ( ( rule__Widget__ContentsAssignment_8_3_1 )* )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1533:1: ( ( rule__Widget__ContentsAssignment_8_3_1 )* )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1534:1: ( rule__Widget__ContentsAssignment_8_3_1 )*
            {
             before(grammarAccess.getWidgetAccess().getContentsAssignment_8_3_1()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1535:1: ( rule__Widget__ContentsAssignment_8_3_1 )*
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( (LA15_0==15) ) {
                    alt15=1;
                }


                switch (alt15) {
            	case 1 :
            	    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1535:2: rule__Widget__ContentsAssignment_8_3_1
            	    {
            	    pushFollow(FollowSets000.FOLLOW_rule__Widget__ContentsAssignment_8_3_1_in_rule__Widget__Group_8_3__1__Impl3031);
            	    rule__Widget__ContentsAssignment_8_3_1();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop15;
                }
            } while (true);

             after(grammarAccess.getWidgetAccess().getContentsAssignment_8_3_1()); 

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
    // $ANTLR end "rule__Widget__Group_8_3__1__Impl"


    // $ANTLR start "rule__Property__Group__0"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1549:1: rule__Property__Group__0 : rule__Property__Group__0__Impl rule__Property__Group__1 ;
    public final void rule__Property__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1553:1: ( rule__Property__Group__0__Impl rule__Property__Group__1 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1554:2: rule__Property__Group__0__Impl rule__Property__Group__1
            {
            pushFollow(FollowSets000.FOLLOW_rule__Property__Group__0__Impl_in_rule__Property__Group__03066);
            rule__Property__Group__0__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Property__Group__1_in_rule__Property__Group__03069);
            rule__Property__Group__1();

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
    // $ANTLR end "rule__Property__Group__0"


    // $ANTLR start "rule__Property__Group__0__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1561:1: rule__Property__Group__0__Impl : ( () ) ;
    public final void rule__Property__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1565:1: ( ( () ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1566:1: ( () )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1566:1: ( () )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1567:1: ()
            {
             before(grammarAccess.getPropertyAccess().getPropertyAction_0()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1568:1: ()
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1570:1: 
            {
            }

             after(grammarAccess.getPropertyAccess().getPropertyAction_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Property__Group__0__Impl"


    // $ANTLR start "rule__Property__Group__1"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1580:1: rule__Property__Group__1 : rule__Property__Group__1__Impl rule__Property__Group__2 ;
    public final void rule__Property__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1584:1: ( rule__Property__Group__1__Impl rule__Property__Group__2 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1585:2: rule__Property__Group__1__Impl rule__Property__Group__2
            {
            pushFollow(FollowSets000.FOLLOW_rule__Property__Group__1__Impl_in_rule__Property__Group__13127);
            rule__Property__Group__1__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Property__Group__2_in_rule__Property__Group__13130);
            rule__Property__Group__2();

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
    // $ANTLR end "rule__Property__Group__1"


    // $ANTLR start "rule__Property__Group__1__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1592:1: rule__Property__Group__1__Impl : ( ( rule__Property__Group_1__0 )? ) ;
    public final void rule__Property__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1596:1: ( ( ( rule__Property__Group_1__0 )? ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1597:1: ( ( rule__Property__Group_1__0 )? )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1597:1: ( ( rule__Property__Group_1__0 )? )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1598:1: ( rule__Property__Group_1__0 )?
            {
             before(grammarAccess.getPropertyAccess().getGroup_1()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1599:1: ( rule__Property__Group_1__0 )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==RULE_ID) ) {
                int LA16_1 = input.LA(2);

                if ( (LA16_1==16) ) {
                    alt16=1;
                }
            }
            switch (alt16) {
                case 1 :
                    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1599:2: rule__Property__Group_1__0
                    {
                    pushFollow(FollowSets000.FOLLOW_rule__Property__Group_1__0_in_rule__Property__Group__1__Impl3157);
                    rule__Property__Group_1__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getPropertyAccess().getGroup_1()); 

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
    // $ANTLR end "rule__Property__Group__1__Impl"


    // $ANTLR start "rule__Property__Group__2"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1609:1: rule__Property__Group__2 : rule__Property__Group__2__Impl rule__Property__Group__3 ;
    public final void rule__Property__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1613:1: ( rule__Property__Group__2__Impl rule__Property__Group__3 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1614:2: rule__Property__Group__2__Impl rule__Property__Group__3
            {
            pushFollow(FollowSets000.FOLLOW_rule__Property__Group__2__Impl_in_rule__Property__Group__23188);
            rule__Property__Group__2__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Property__Group__3_in_rule__Property__Group__23191);
            rule__Property__Group__3();

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
    // $ANTLR end "rule__Property__Group__2"


    // $ANTLR start "rule__Property__Group__2__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1621:1: rule__Property__Group__2__Impl : ( ( rule__Property__TypeNameAssignment_2 ) ) ;
    public final void rule__Property__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1625:1: ( ( ( rule__Property__TypeNameAssignment_2 ) ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1626:1: ( ( rule__Property__TypeNameAssignment_2 ) )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1626:1: ( ( rule__Property__TypeNameAssignment_2 ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1627:1: ( rule__Property__TypeNameAssignment_2 )
            {
             before(grammarAccess.getPropertyAccess().getTypeNameAssignment_2()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1628:1: ( rule__Property__TypeNameAssignment_2 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1628:2: rule__Property__TypeNameAssignment_2
            {
            pushFollow(FollowSets000.FOLLOW_rule__Property__TypeNameAssignment_2_in_rule__Property__Group__2__Impl3218);
            rule__Property__TypeNameAssignment_2();

            state._fsp--;


            }

             after(grammarAccess.getPropertyAccess().getTypeNameAssignment_2()); 

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
    // $ANTLR end "rule__Property__Group__2__Impl"


    // $ANTLR start "rule__Property__Group__3"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1638:1: rule__Property__Group__3 : rule__Property__Group__3__Impl rule__Property__Group__4 ;
    public final void rule__Property__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1642:1: ( rule__Property__Group__3__Impl rule__Property__Group__4 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1643:2: rule__Property__Group__3__Impl rule__Property__Group__4
            {
            pushFollow(FollowSets000.FOLLOW_rule__Property__Group__3__Impl_in_rule__Property__Group__33248);
            rule__Property__Group__3__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Property__Group__4_in_rule__Property__Group__33251);
            rule__Property__Group__4();

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
    // $ANTLR end "rule__Property__Group__3"


    // $ANTLR start "rule__Property__Group__3__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1650:1: rule__Property__Group__3__Impl : ( '=' ) ;
    public final void rule__Property__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1654:1: ( ( '=' ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1655:1: ( '=' )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1655:1: ( '=' )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1656:1: '='
            {
             before(grammarAccess.getPropertyAccess().getEqualsSignKeyword_3()); 
            match(input,14,FollowSets000.FOLLOW_14_in_rule__Property__Group__3__Impl3279); 
             after(grammarAccess.getPropertyAccess().getEqualsSignKeyword_3()); 

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
    // $ANTLR end "rule__Property__Group__3__Impl"


    // $ANTLR start "rule__Property__Group__4"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1669:1: rule__Property__Group__4 : rule__Property__Group__4__Impl rule__Property__Group__5 ;
    public final void rule__Property__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1673:1: ( rule__Property__Group__4__Impl rule__Property__Group__5 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1674:2: rule__Property__Group__4__Impl rule__Property__Group__5
            {
            pushFollow(FollowSets000.FOLLOW_rule__Property__Group__4__Impl_in_rule__Property__Group__43310);
            rule__Property__Group__4__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Property__Group__5_in_rule__Property__Group__43313);
            rule__Property__Group__5();

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
    // $ANTLR end "rule__Property__Group__4"


    // $ANTLR start "rule__Property__Group__4__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1681:1: rule__Property__Group__4__Impl : ( ( rule__Property__ValueAssignment_4 ) ) ;
    public final void rule__Property__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1685:1: ( ( ( rule__Property__ValueAssignment_4 ) ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1686:1: ( ( rule__Property__ValueAssignment_4 ) )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1686:1: ( ( rule__Property__ValueAssignment_4 ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1687:1: ( rule__Property__ValueAssignment_4 )
            {
             before(grammarAccess.getPropertyAccess().getValueAssignment_4()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1688:1: ( rule__Property__ValueAssignment_4 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1688:2: rule__Property__ValueAssignment_4
            {
            pushFollow(FollowSets000.FOLLOW_rule__Property__ValueAssignment_4_in_rule__Property__Group__4__Impl3340);
            rule__Property__ValueAssignment_4();

            state._fsp--;


            }

             after(grammarAccess.getPropertyAccess().getValueAssignment_4()); 

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
    // $ANTLR end "rule__Property__Group__4__Impl"


    // $ANTLR start "rule__Property__Group__5"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1698:1: rule__Property__Group__5 : rule__Property__Group__5__Impl rule__Property__Group__6 ;
    public final void rule__Property__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1702:1: ( rule__Property__Group__5__Impl rule__Property__Group__6 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1703:2: rule__Property__Group__5__Impl rule__Property__Group__6
            {
            pushFollow(FollowSets000.FOLLOW_rule__Property__Group__5__Impl_in_rule__Property__Group__53370);
            rule__Property__Group__5__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Property__Group__6_in_rule__Property__Group__53373);
            rule__Property__Group__6();

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
    // $ANTLR end "rule__Property__Group__5"


    // $ANTLR start "rule__Property__Group__5__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1710:1: rule__Property__Group__5__Impl : ( ( rule__Property__ReadonlyAssignment_5 )? ) ;
    public final void rule__Property__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1714:1: ( ( ( rule__Property__ReadonlyAssignment_5 )? ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1715:1: ( ( rule__Property__ReadonlyAssignment_5 )? )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1715:1: ( ( rule__Property__ReadonlyAssignment_5 )? )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1716:1: ( rule__Property__ReadonlyAssignment_5 )?
            {
             before(grammarAccess.getPropertyAccess().getReadonlyAssignment_5()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1717:1: ( rule__Property__ReadonlyAssignment_5 )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==22) ) {
                int LA17_1 = input.LA(2);

                if ( (LA17_1==EOF||LA17_1==RULE_ID||LA17_1==15||(LA17_1>=20 && LA17_1<=23)||(LA17_1>=27 && LA17_1<=28)) ) {
                    alt17=1;
                }
            }
            switch (alt17) {
                case 1 :
                    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1717:2: rule__Property__ReadonlyAssignment_5
                    {
                    pushFollow(FollowSets000.FOLLOW_rule__Property__ReadonlyAssignment_5_in_rule__Property__Group__5__Impl3400);
                    rule__Property__ReadonlyAssignment_5();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getPropertyAccess().getReadonlyAssignment_5()); 

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
    // $ANTLR end "rule__Property__Group__5__Impl"


    // $ANTLR start "rule__Property__Group__6"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1727:1: rule__Property__Group__6 : rule__Property__Group__6__Impl ;
    public final void rule__Property__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1731:1: ( rule__Property__Group__6__Impl )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1732:2: rule__Property__Group__6__Impl
            {
            pushFollow(FollowSets000.FOLLOW_rule__Property__Group__6__Impl_in_rule__Property__Group__63431);
            rule__Property__Group__6__Impl();

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
    // $ANTLR end "rule__Property__Group__6"


    // $ANTLR start "rule__Property__Group__6__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1738:1: rule__Property__Group__6__Impl : ( ( rule__Property__Group_6__0 )? ) ;
    public final void rule__Property__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1742:1: ( ( ( rule__Property__Group_6__0 )? ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1743:1: ( ( rule__Property__Group_6__0 )? )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1743:1: ( ( rule__Property__Group_6__0 )? )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1744:1: ( rule__Property__Group_6__0 )?
            {
             before(grammarAccess.getPropertyAccess().getGroup_6()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1745:1: ( rule__Property__Group_6__0 )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==22) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1745:2: rule__Property__Group_6__0
                    {
                    pushFollow(FollowSets000.FOLLOW_rule__Property__Group_6__0_in_rule__Property__Group__6__Impl3458);
                    rule__Property__Group_6__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getPropertyAccess().getGroup_6()); 

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
    // $ANTLR end "rule__Property__Group__6__Impl"


    // $ANTLR start "rule__Property__Group_1__0"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1769:1: rule__Property__Group_1__0 : rule__Property__Group_1__0__Impl rule__Property__Group_1__1 ;
    public final void rule__Property__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1773:1: ( rule__Property__Group_1__0__Impl rule__Property__Group_1__1 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1774:2: rule__Property__Group_1__0__Impl rule__Property__Group_1__1
            {
            pushFollow(FollowSets000.FOLLOW_rule__Property__Group_1__0__Impl_in_rule__Property__Group_1__03503);
            rule__Property__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Property__Group_1__1_in_rule__Property__Group_1__03506);
            rule__Property__Group_1__1();

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
    // $ANTLR end "rule__Property__Group_1__0"


    // $ANTLR start "rule__Property__Group_1__0__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1781:1: rule__Property__Group_1__0__Impl : ( ( rule__Property__LibraryNameAssignment_1_0 ) ) ;
    public final void rule__Property__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1785:1: ( ( ( rule__Property__LibraryNameAssignment_1_0 ) ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1786:1: ( ( rule__Property__LibraryNameAssignment_1_0 ) )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1786:1: ( ( rule__Property__LibraryNameAssignment_1_0 ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1787:1: ( rule__Property__LibraryNameAssignment_1_0 )
            {
             before(grammarAccess.getPropertyAccess().getLibraryNameAssignment_1_0()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1788:1: ( rule__Property__LibraryNameAssignment_1_0 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1788:2: rule__Property__LibraryNameAssignment_1_0
            {
            pushFollow(FollowSets000.FOLLOW_rule__Property__LibraryNameAssignment_1_0_in_rule__Property__Group_1__0__Impl3533);
            rule__Property__LibraryNameAssignment_1_0();

            state._fsp--;


            }

             after(grammarAccess.getPropertyAccess().getLibraryNameAssignment_1_0()); 

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
    // $ANTLR end "rule__Property__Group_1__0__Impl"


    // $ANTLR start "rule__Property__Group_1__1"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1798:1: rule__Property__Group_1__1 : rule__Property__Group_1__1__Impl ;
    public final void rule__Property__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1802:1: ( rule__Property__Group_1__1__Impl )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1803:2: rule__Property__Group_1__1__Impl
            {
            pushFollow(FollowSets000.FOLLOW_rule__Property__Group_1__1__Impl_in_rule__Property__Group_1__13563);
            rule__Property__Group_1__1__Impl();

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
    // $ANTLR end "rule__Property__Group_1__1"


    // $ANTLR start "rule__Property__Group_1__1__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1809:1: rule__Property__Group_1__1__Impl : ( ':' ) ;
    public final void rule__Property__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1813:1: ( ( ':' ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1814:1: ( ':' )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1814:1: ( ':' )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1815:1: ':'
            {
             before(grammarAccess.getPropertyAccess().getColonKeyword_1_1()); 
            match(input,16,FollowSets000.FOLLOW_16_in_rule__Property__Group_1__1__Impl3591); 
             after(grammarAccess.getPropertyAccess().getColonKeyword_1_1()); 

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
    // $ANTLR end "rule__Property__Group_1__1__Impl"


    // $ANTLR start "rule__Property__Group_6__0"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1832:1: rule__Property__Group_6__0 : rule__Property__Group_6__0__Impl rule__Property__Group_6__1 ;
    public final void rule__Property__Group_6__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1836:1: ( rule__Property__Group_6__0__Impl rule__Property__Group_6__1 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1837:2: rule__Property__Group_6__0__Impl rule__Property__Group_6__1
            {
            pushFollow(FollowSets000.FOLLOW_rule__Property__Group_6__0__Impl_in_rule__Property__Group_6__03626);
            rule__Property__Group_6__0__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Property__Group_6__1_in_rule__Property__Group_6__03629);
            rule__Property__Group_6__1();

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
    // $ANTLR end "rule__Property__Group_6__0"


    // $ANTLR start "rule__Property__Group_6__0__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1844:1: rule__Property__Group_6__0__Impl : ( '!' ) ;
    public final void rule__Property__Group_6__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1848:1: ( ( '!' ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1849:1: ( '!' )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1849:1: ( '!' )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1850:1: '!'
            {
             before(grammarAccess.getPropertyAccess().getExclamationMarkKeyword_6_0()); 
            match(input,22,FollowSets000.FOLLOW_22_in_rule__Property__Group_6__0__Impl3657); 
             after(grammarAccess.getPropertyAccess().getExclamationMarkKeyword_6_0()); 

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
    // $ANTLR end "rule__Property__Group_6__0__Impl"


    // $ANTLR start "rule__Property__Group_6__1"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1863:1: rule__Property__Group_6__1 : rule__Property__Group_6__1__Impl ;
    public final void rule__Property__Group_6__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1867:1: ( rule__Property__Group_6__1__Impl )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1868:2: rule__Property__Group_6__1__Impl
            {
            pushFollow(FollowSets000.FOLLOW_rule__Property__Group_6__1__Impl_in_rule__Property__Group_6__13688);
            rule__Property__Group_6__1__Impl();

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
    // $ANTLR end "rule__Property__Group_6__1"


    // $ANTLR start "rule__Property__Group_6__1__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1874:1: rule__Property__Group_6__1__Impl : ( ( rule__Property__ModelAssignment_6_1 ) ) ;
    public final void rule__Property__Group_6__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1878:1: ( ( ( rule__Property__ModelAssignment_6_1 ) ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1879:1: ( ( rule__Property__ModelAssignment_6_1 ) )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1879:1: ( ( rule__Property__ModelAssignment_6_1 ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1880:1: ( rule__Property__ModelAssignment_6_1 )
            {
             before(grammarAccess.getPropertyAccess().getModelAssignment_6_1()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1881:1: ( rule__Property__ModelAssignment_6_1 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1881:2: rule__Property__ModelAssignment_6_1
            {
            pushFollow(FollowSets000.FOLLOW_rule__Property__ModelAssignment_6_1_in_rule__Property__Group_6__1__Impl3715);
            rule__Property__ModelAssignment_6_1();

            state._fsp--;


            }

             after(grammarAccess.getPropertyAccess().getModelAssignment_6_1()); 

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
    // $ANTLR end "rule__Property__Group_6__1__Impl"


    // $ANTLR start "rule__Event__Group__0"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1895:1: rule__Event__Group__0 : rule__Event__Group__0__Impl rule__Event__Group__1 ;
    public final void rule__Event__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1899:1: ( rule__Event__Group__0__Impl rule__Event__Group__1 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1900:2: rule__Event__Group__0__Impl rule__Event__Group__1
            {
            pushFollow(FollowSets000.FOLLOW_rule__Event__Group__0__Impl_in_rule__Event__Group__03749);
            rule__Event__Group__0__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Event__Group__1_in_rule__Event__Group__03752);
            rule__Event__Group__1();

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
    // $ANTLR end "rule__Event__Group__0"


    // $ANTLR start "rule__Event__Group__0__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1907:1: rule__Event__Group__0__Impl : ( 'Event' ) ;
    public final void rule__Event__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1911:1: ( ( 'Event' ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1912:1: ( 'Event' )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1912:1: ( 'Event' )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1913:1: 'Event'
            {
             before(grammarAccess.getEventAccess().getEventKeyword_0()); 
            match(input,23,FollowSets000.FOLLOW_23_in_rule__Event__Group__0__Impl3780); 
             after(grammarAccess.getEventAccess().getEventKeyword_0()); 

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
    // $ANTLR end "rule__Event__Group__0__Impl"


    // $ANTLR start "rule__Event__Group__1"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1926:1: rule__Event__Group__1 : rule__Event__Group__1__Impl rule__Event__Group__2 ;
    public final void rule__Event__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1930:1: ( rule__Event__Group__1__Impl rule__Event__Group__2 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1931:2: rule__Event__Group__1__Impl rule__Event__Group__2
            {
            pushFollow(FollowSets000.FOLLOW_rule__Event__Group__1__Impl_in_rule__Event__Group__13811);
            rule__Event__Group__1__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Event__Group__2_in_rule__Event__Group__13814);
            rule__Event__Group__2();

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
    // $ANTLR end "rule__Event__Group__1"


    // $ANTLR start "rule__Event__Group__1__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1938:1: rule__Event__Group__1__Impl : ( ( rule__Event__EventNameAssignment_1 ) ) ;
    public final void rule__Event__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1942:1: ( ( ( rule__Event__EventNameAssignment_1 ) ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1943:1: ( ( rule__Event__EventNameAssignment_1 ) )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1943:1: ( ( rule__Event__EventNameAssignment_1 ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1944:1: ( rule__Event__EventNameAssignment_1 )
            {
             before(grammarAccess.getEventAccess().getEventNameAssignment_1()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1945:1: ( rule__Event__EventNameAssignment_1 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1945:2: rule__Event__EventNameAssignment_1
            {
            pushFollow(FollowSets000.FOLLOW_rule__Event__EventNameAssignment_1_in_rule__Event__Group__1__Impl3841);
            rule__Event__EventNameAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getEventAccess().getEventNameAssignment_1()); 

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
    // $ANTLR end "rule__Event__Group__1__Impl"


    // $ANTLR start "rule__Event__Group__2"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1955:1: rule__Event__Group__2 : rule__Event__Group__2__Impl rule__Event__Group__3 ;
    public final void rule__Event__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1959:1: ( rule__Event__Group__2__Impl rule__Event__Group__3 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1960:2: rule__Event__Group__2__Impl rule__Event__Group__3
            {
            pushFollow(FollowSets000.FOLLOW_rule__Event__Group__2__Impl_in_rule__Event__Group__23871);
            rule__Event__Group__2__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Event__Group__3_in_rule__Event__Group__23874);
            rule__Event__Group__3();

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
    // $ANTLR end "rule__Event__Group__2"


    // $ANTLR start "rule__Event__Group__2__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1967:1: rule__Event__Group__2__Impl : ( '(' ) ;
    public final void rule__Event__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1971:1: ( ( '(' ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1972:1: ( '(' )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1972:1: ( '(' )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1973:1: '('
            {
             before(grammarAccess.getEventAccess().getLeftParenthesisKeyword_2()); 
            match(input,24,FollowSets000.FOLLOW_24_in_rule__Event__Group__2__Impl3902); 
             after(grammarAccess.getEventAccess().getLeftParenthesisKeyword_2()); 

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
    // $ANTLR end "rule__Event__Group__2__Impl"


    // $ANTLR start "rule__Event__Group__3"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1986:1: rule__Event__Group__3 : rule__Event__Group__3__Impl rule__Event__Group__4 ;
    public final void rule__Event__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1990:1: ( rule__Event__Group__3__Impl rule__Event__Group__4 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1991:2: rule__Event__Group__3__Impl rule__Event__Group__4
            {
            pushFollow(FollowSets000.FOLLOW_rule__Event__Group__3__Impl_in_rule__Event__Group__33933);
            rule__Event__Group__3__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Event__Group__4_in_rule__Event__Group__33936);
            rule__Event__Group__4();

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
    // $ANTLR end "rule__Event__Group__3"


    // $ANTLR start "rule__Event__Group__3__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:1998:1: rule__Event__Group__3__Impl : ( ( rule__Event__FunctionNameAssignment_3 ) ) ;
    public final void rule__Event__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2002:1: ( ( ( rule__Event__FunctionNameAssignment_3 ) ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2003:1: ( ( rule__Event__FunctionNameAssignment_3 ) )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2003:1: ( ( rule__Event__FunctionNameAssignment_3 ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2004:1: ( rule__Event__FunctionNameAssignment_3 )
            {
             before(grammarAccess.getEventAccess().getFunctionNameAssignment_3()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2005:1: ( rule__Event__FunctionNameAssignment_3 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2005:2: rule__Event__FunctionNameAssignment_3
            {
            pushFollow(FollowSets000.FOLLOW_rule__Event__FunctionNameAssignment_3_in_rule__Event__Group__3__Impl3963);
            rule__Event__FunctionNameAssignment_3();

            state._fsp--;


            }

             after(grammarAccess.getEventAccess().getFunctionNameAssignment_3()); 

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
    // $ANTLR end "rule__Event__Group__3__Impl"


    // $ANTLR start "rule__Event__Group__4"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2015:1: rule__Event__Group__4 : rule__Event__Group__4__Impl rule__Event__Group__5 ;
    public final void rule__Event__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2019:1: ( rule__Event__Group__4__Impl rule__Event__Group__5 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2020:2: rule__Event__Group__4__Impl rule__Event__Group__5
            {
            pushFollow(FollowSets000.FOLLOW_rule__Event__Group__4__Impl_in_rule__Event__Group__43993);
            rule__Event__Group__4__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Event__Group__5_in_rule__Event__Group__43996);
            rule__Event__Group__5();

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
    // $ANTLR end "rule__Event__Group__4"


    // $ANTLR start "rule__Event__Group__4__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2027:1: rule__Event__Group__4__Impl : ( ')' ) ;
    public final void rule__Event__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2031:1: ( ( ')' ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2032:1: ( ')' )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2032:1: ( ')' )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2033:1: ')'
            {
             before(grammarAccess.getEventAccess().getRightParenthesisKeyword_4()); 
            match(input,25,FollowSets000.FOLLOW_25_in_rule__Event__Group__4__Impl4024); 
             after(grammarAccess.getEventAccess().getRightParenthesisKeyword_4()); 

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
    // $ANTLR end "rule__Event__Group__4__Impl"


    // $ANTLR start "rule__Event__Group__5"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2046:1: rule__Event__Group__5 : rule__Event__Group__5__Impl rule__Event__Group__6 ;
    public final void rule__Event__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2050:1: ( rule__Event__Group__5__Impl rule__Event__Group__6 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2051:2: rule__Event__Group__5__Impl rule__Event__Group__6
            {
            pushFollow(FollowSets000.FOLLOW_rule__Event__Group__5__Impl_in_rule__Event__Group__54055);
            rule__Event__Group__5__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Event__Group__6_in_rule__Event__Group__54058);
            rule__Event__Group__6();

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
    // $ANTLR end "rule__Event__Group__5"


    // $ANTLR start "rule__Event__Group__5__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2058:1: rule__Event__Group__5__Impl : ( ( rule__Event__NatureAssignment_5 )? ) ;
    public final void rule__Event__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2062:1: ( ( ( rule__Event__NatureAssignment_5 )? ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2063:1: ( ( rule__Event__NatureAssignment_5 )? )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2063:1: ( ( rule__Event__NatureAssignment_5 )? )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2064:1: ( rule__Event__NatureAssignment_5 )?
            {
             before(grammarAccess.getEventAccess().getNatureAssignment_5()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2065:1: ( rule__Event__NatureAssignment_5 )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( ((LA19_0>=11 && LA19_0<=12)) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2065:2: rule__Event__NatureAssignment_5
                    {
                    pushFollow(FollowSets000.FOLLOW_rule__Event__NatureAssignment_5_in_rule__Event__Group__5__Impl4085);
                    rule__Event__NatureAssignment_5();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getEventAccess().getNatureAssignment_5()); 

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
    // $ANTLR end "rule__Event__Group__5__Impl"


    // $ANTLR start "rule__Event__Group__6"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2075:1: rule__Event__Group__6 : rule__Event__Group__6__Impl rule__Event__Group__7 ;
    public final void rule__Event__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2079:1: ( rule__Event__Group__6__Impl rule__Event__Group__7 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2080:2: rule__Event__Group__6__Impl rule__Event__Group__7
            {
            pushFollow(FollowSets000.FOLLOW_rule__Event__Group__6__Impl_in_rule__Event__Group__64116);
            rule__Event__Group__6__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Event__Group__7_in_rule__Event__Group__64119);
            rule__Event__Group__7();

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
    // $ANTLR end "rule__Event__Group__6"


    // $ANTLR start "rule__Event__Group__6__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2087:1: rule__Event__Group__6__Impl : ( ( rule__Event__Group_6__0 )? ) ;
    public final void rule__Event__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2091:1: ( ( ( rule__Event__Group_6__0 )? ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2092:1: ( ( rule__Event__Group_6__0 )? )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2092:1: ( ( rule__Event__Group_6__0 )? )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2093:1: ( rule__Event__Group_6__0 )?
            {
             before(grammarAccess.getEventAccess().getGroup_6()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2094:1: ( rule__Event__Group_6__0 )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==26) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2094:2: rule__Event__Group_6__0
                    {
                    pushFollow(FollowSets000.FOLLOW_rule__Event__Group_6__0_in_rule__Event__Group__6__Impl4146);
                    rule__Event__Group_6__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getEventAccess().getGroup_6()); 

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
    // $ANTLR end "rule__Event__Group__6__Impl"


    // $ANTLR start "rule__Event__Group__7"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2104:1: rule__Event__Group__7 : rule__Event__Group__7__Impl rule__Event__Group__8 ;
    public final void rule__Event__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2108:1: ( rule__Event__Group__7__Impl rule__Event__Group__8 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2109:2: rule__Event__Group__7__Impl rule__Event__Group__8
            {
            pushFollow(FollowSets000.FOLLOW_rule__Event__Group__7__Impl_in_rule__Event__Group__74177);
            rule__Event__Group__7__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Event__Group__8_in_rule__Event__Group__74180);
            rule__Event__Group__8();

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
    // $ANTLR end "rule__Event__Group__7"


    // $ANTLR start "rule__Event__Group__7__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2116:1: rule__Event__Group__7__Impl : ( ( rule__Event__PropertiesAssignment_7 )* ) ;
    public final void rule__Event__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2120:1: ( ( ( rule__Event__PropertiesAssignment_7 )* ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2121:1: ( ( rule__Event__PropertiesAssignment_7 )* )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2121:1: ( ( rule__Event__PropertiesAssignment_7 )* )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2122:1: ( rule__Event__PropertiesAssignment_7 )*
            {
             before(grammarAccess.getEventAccess().getPropertiesAssignment_7()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2123:1: ( rule__Event__PropertiesAssignment_7 )*
            loop21:
            do {
                int alt21=2;
                int LA21_0 = input.LA(1);

                if ( (LA21_0==RULE_ID) ) {
                    alt21=1;
                }


                switch (alt21) {
            	case 1 :
            	    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2123:2: rule__Event__PropertiesAssignment_7
            	    {
            	    pushFollow(FollowSets000.FOLLOW_rule__Event__PropertiesAssignment_7_in_rule__Event__Group__7__Impl4207);
            	    rule__Event__PropertiesAssignment_7();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop21;
                }
            } while (true);

             after(grammarAccess.getEventAccess().getPropertiesAssignment_7()); 

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
    // $ANTLR end "rule__Event__Group__7__Impl"


    // $ANTLR start "rule__Event__Group__8"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2133:1: rule__Event__Group__8 : rule__Event__Group__8__Impl rule__Event__Group__9 ;
    public final void rule__Event__Group__8() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2137:1: ( rule__Event__Group__8__Impl rule__Event__Group__9 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2138:2: rule__Event__Group__8__Impl rule__Event__Group__9
            {
            pushFollow(FollowSets000.FOLLOW_rule__Event__Group__8__Impl_in_rule__Event__Group__84238);
            rule__Event__Group__8__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Event__Group__9_in_rule__Event__Group__84241);
            rule__Event__Group__9();

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
    // $ANTLR end "rule__Event__Group__8"


    // $ANTLR start "rule__Event__Group__8__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2145:1: rule__Event__Group__8__Impl : ( ( rule__Event__Group_8__0 )? ) ;
    public final void rule__Event__Group__8__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2149:1: ( ( ( rule__Event__Group_8__0 )? ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2150:1: ( ( rule__Event__Group_8__0 )? )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2150:1: ( ( rule__Event__Group_8__0 )? )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2151:1: ( rule__Event__Group_8__0 )?
            {
             before(grammarAccess.getEventAccess().getGroup_8()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2152:1: ( rule__Event__Group_8__0 )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==27) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2152:2: rule__Event__Group_8__0
                    {
                    pushFollow(FollowSets000.FOLLOW_rule__Event__Group_8__0_in_rule__Event__Group__8__Impl4268);
                    rule__Event__Group_8__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getEventAccess().getGroup_8()); 

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
    // $ANTLR end "rule__Event__Group__8__Impl"


    // $ANTLR start "rule__Event__Group__9"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2162:1: rule__Event__Group__9 : rule__Event__Group__9__Impl ;
    public final void rule__Event__Group__9() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2166:1: ( rule__Event__Group__9__Impl )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2167:2: rule__Event__Group__9__Impl
            {
            pushFollow(FollowSets000.FOLLOW_rule__Event__Group__9__Impl_in_rule__Event__Group__94299);
            rule__Event__Group__9__Impl();

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
    // $ANTLR end "rule__Event__Group__9"


    // $ANTLR start "rule__Event__Group__9__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2173:1: rule__Event__Group__9__Impl : ( ( rule__Event__Group_9__0 )? ) ;
    public final void rule__Event__Group__9__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2177:1: ( ( ( rule__Event__Group_9__0 )? ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2178:1: ( ( rule__Event__Group_9__0 )? )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2178:1: ( ( rule__Event__Group_9__0 )? )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2179:1: ( rule__Event__Group_9__0 )?
            {
             before(grammarAccess.getEventAccess().getGroup_9()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2180:1: ( rule__Event__Group_9__0 )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==20) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2180:2: rule__Event__Group_9__0
                    {
                    pushFollow(FollowSets000.FOLLOW_rule__Event__Group_9__0_in_rule__Event__Group__9__Impl4326);
                    rule__Event__Group_9__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getEventAccess().getGroup_9()); 

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
    // $ANTLR end "rule__Event__Group__9__Impl"


    // $ANTLR start "rule__Event__Group_6__0"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2210:1: rule__Event__Group_6__0 : rule__Event__Group_6__0__Impl rule__Event__Group_6__1 ;
    public final void rule__Event__Group_6__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2214:1: ( rule__Event__Group_6__0__Impl rule__Event__Group_6__1 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2215:2: rule__Event__Group_6__0__Impl rule__Event__Group_6__1
            {
            pushFollow(FollowSets000.FOLLOW_rule__Event__Group_6__0__Impl_in_rule__Event__Group_6__04377);
            rule__Event__Group_6__0__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Event__Group_6__1_in_rule__Event__Group_6__04380);
            rule__Event__Group_6__1();

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
    // $ANTLR end "rule__Event__Group_6__0"


    // $ANTLR start "rule__Event__Group_6__0__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2222:1: rule__Event__Group_6__0__Impl : ( '//' ) ;
    public final void rule__Event__Group_6__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2226:1: ( ( '//' ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2227:1: ( '//' )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2227:1: ( '//' )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2228:1: '//'
            {
             before(grammarAccess.getEventAccess().getSolidusSolidusKeyword_6_0()); 
            match(input,26,FollowSets000.FOLLOW_26_in_rule__Event__Group_6__0__Impl4408); 
             after(grammarAccess.getEventAccess().getSolidusSolidusKeyword_6_0()); 

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
    // $ANTLR end "rule__Event__Group_6__0__Impl"


    // $ANTLR start "rule__Event__Group_6__1"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2241:1: rule__Event__Group_6__1 : rule__Event__Group_6__1__Impl ;
    public final void rule__Event__Group_6__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2245:1: ( rule__Event__Group_6__1__Impl )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2246:2: rule__Event__Group_6__1__Impl
            {
            pushFollow(FollowSets000.FOLLOW_rule__Event__Group_6__1__Impl_in_rule__Event__Group_6__14439);
            rule__Event__Group_6__1__Impl();

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
    // $ANTLR end "rule__Event__Group_6__1"


    // $ANTLR start "rule__Event__Group_6__1__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2252:1: rule__Event__Group_6__1__Impl : ( ( rule__Event__DescriptionAssignment_6_1 ) ) ;
    public final void rule__Event__Group_6__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2256:1: ( ( ( rule__Event__DescriptionAssignment_6_1 ) ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2257:1: ( ( rule__Event__DescriptionAssignment_6_1 ) )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2257:1: ( ( rule__Event__DescriptionAssignment_6_1 ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2258:1: ( rule__Event__DescriptionAssignment_6_1 )
            {
             before(grammarAccess.getEventAccess().getDescriptionAssignment_6_1()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2259:1: ( rule__Event__DescriptionAssignment_6_1 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2259:2: rule__Event__DescriptionAssignment_6_1
            {
            pushFollow(FollowSets000.FOLLOW_rule__Event__DescriptionAssignment_6_1_in_rule__Event__Group_6__1__Impl4466);
            rule__Event__DescriptionAssignment_6_1();

            state._fsp--;


            }

             after(grammarAccess.getEventAccess().getDescriptionAssignment_6_1()); 

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
    // $ANTLR end "rule__Event__Group_6__1__Impl"


    // $ANTLR start "rule__Event__Group_8__0"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2273:1: rule__Event__Group_8__0 : rule__Event__Group_8__0__Impl rule__Event__Group_8__1 ;
    public final void rule__Event__Group_8__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2277:1: ( rule__Event__Group_8__0__Impl rule__Event__Group_8__1 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2278:2: rule__Event__Group_8__0__Impl rule__Event__Group_8__1
            {
            pushFollow(FollowSets000.FOLLOW_rule__Event__Group_8__0__Impl_in_rule__Event__Group_8__04500);
            rule__Event__Group_8__0__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Event__Group_8__1_in_rule__Event__Group_8__04503);
            rule__Event__Group_8__1();

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
    // $ANTLR end "rule__Event__Group_8__0"


    // $ANTLR start "rule__Event__Group_8__0__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2285:1: rule__Event__Group_8__0__Impl : ( 'messages' ) ;
    public final void rule__Event__Group_8__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2289:1: ( ( 'messages' ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2290:1: ( 'messages' )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2290:1: ( 'messages' )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2291:1: 'messages'
            {
             before(grammarAccess.getEventAccess().getMessagesKeyword_8_0()); 
            match(input,27,FollowSets000.FOLLOW_27_in_rule__Event__Group_8__0__Impl4531); 
             after(grammarAccess.getEventAccess().getMessagesKeyword_8_0()); 

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
    // $ANTLR end "rule__Event__Group_8__0__Impl"


    // $ANTLR start "rule__Event__Group_8__1"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2304:1: rule__Event__Group_8__1 : rule__Event__Group_8__1__Impl rule__Event__Group_8__2 ;
    public final void rule__Event__Group_8__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2308:1: ( rule__Event__Group_8__1__Impl rule__Event__Group_8__2 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2309:2: rule__Event__Group_8__1__Impl rule__Event__Group_8__2
            {
            pushFollow(FollowSets000.FOLLOW_rule__Event__Group_8__1__Impl_in_rule__Event__Group_8__14562);
            rule__Event__Group_8__1__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Event__Group_8__2_in_rule__Event__Group_8__14565);
            rule__Event__Group_8__2();

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
    // $ANTLR end "rule__Event__Group_8__1"


    // $ANTLR start "rule__Event__Group_8__1__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2316:1: rule__Event__Group_8__1__Impl : ( ( rule__Event__MessagesAssignment_8_1 ) ) ;
    public final void rule__Event__Group_8__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2320:1: ( ( ( rule__Event__MessagesAssignment_8_1 ) ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2321:1: ( ( rule__Event__MessagesAssignment_8_1 ) )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2321:1: ( ( rule__Event__MessagesAssignment_8_1 ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2322:1: ( rule__Event__MessagesAssignment_8_1 )
            {
             before(grammarAccess.getEventAccess().getMessagesAssignment_8_1()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2323:1: ( rule__Event__MessagesAssignment_8_1 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2323:2: rule__Event__MessagesAssignment_8_1
            {
            pushFollow(FollowSets000.FOLLOW_rule__Event__MessagesAssignment_8_1_in_rule__Event__Group_8__1__Impl4592);
            rule__Event__MessagesAssignment_8_1();

            state._fsp--;


            }

             after(grammarAccess.getEventAccess().getMessagesAssignment_8_1()); 

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
    // $ANTLR end "rule__Event__Group_8__1__Impl"


    // $ANTLR start "rule__Event__Group_8__2"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2333:1: rule__Event__Group_8__2 : rule__Event__Group_8__2__Impl ;
    public final void rule__Event__Group_8__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2337:1: ( rule__Event__Group_8__2__Impl )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2338:2: rule__Event__Group_8__2__Impl
            {
            pushFollow(FollowSets000.FOLLOW_rule__Event__Group_8__2__Impl_in_rule__Event__Group_8__24622);
            rule__Event__Group_8__2__Impl();

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
    // $ANTLR end "rule__Event__Group_8__2"


    // $ANTLR start "rule__Event__Group_8__2__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2344:1: rule__Event__Group_8__2__Impl : ( ( rule__Event__Group_8_2__0 )* ) ;
    public final void rule__Event__Group_8__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2348:1: ( ( ( rule__Event__Group_8_2__0 )* ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2349:1: ( ( rule__Event__Group_8_2__0 )* )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2349:1: ( ( rule__Event__Group_8_2__0 )* )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2350:1: ( rule__Event__Group_8_2__0 )*
            {
             before(grammarAccess.getEventAccess().getGroup_8_2()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2351:1: ( rule__Event__Group_8_2__0 )*
            loop24:
            do {
                int alt24=2;
                int LA24_0 = input.LA(1);

                if ( (LA24_0==18) ) {
                    alt24=1;
                }


                switch (alt24) {
            	case 1 :
            	    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2351:2: rule__Event__Group_8_2__0
            	    {
            	    pushFollow(FollowSets000.FOLLOW_rule__Event__Group_8_2__0_in_rule__Event__Group_8__2__Impl4649);
            	    rule__Event__Group_8_2__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop24;
                }
            } while (true);

             after(grammarAccess.getEventAccess().getGroup_8_2()); 

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
    // $ANTLR end "rule__Event__Group_8__2__Impl"


    // $ANTLR start "rule__Event__Group_8_2__0"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2367:1: rule__Event__Group_8_2__0 : rule__Event__Group_8_2__0__Impl rule__Event__Group_8_2__1 ;
    public final void rule__Event__Group_8_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2371:1: ( rule__Event__Group_8_2__0__Impl rule__Event__Group_8_2__1 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2372:2: rule__Event__Group_8_2__0__Impl rule__Event__Group_8_2__1
            {
            pushFollow(FollowSets000.FOLLOW_rule__Event__Group_8_2__0__Impl_in_rule__Event__Group_8_2__04686);
            rule__Event__Group_8_2__0__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Event__Group_8_2__1_in_rule__Event__Group_8_2__04689);
            rule__Event__Group_8_2__1();

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
    // $ANTLR end "rule__Event__Group_8_2__0"


    // $ANTLR start "rule__Event__Group_8_2__0__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2379:1: rule__Event__Group_8_2__0__Impl : ( ',' ) ;
    public final void rule__Event__Group_8_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2383:1: ( ( ',' ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2384:1: ( ',' )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2384:1: ( ',' )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2385:1: ','
            {
             before(grammarAccess.getEventAccess().getCommaKeyword_8_2_0()); 
            match(input,18,FollowSets000.FOLLOW_18_in_rule__Event__Group_8_2__0__Impl4717); 
             after(grammarAccess.getEventAccess().getCommaKeyword_8_2_0()); 

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
    // $ANTLR end "rule__Event__Group_8_2__0__Impl"


    // $ANTLR start "rule__Event__Group_8_2__1"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2398:1: rule__Event__Group_8_2__1 : rule__Event__Group_8_2__1__Impl ;
    public final void rule__Event__Group_8_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2402:1: ( rule__Event__Group_8_2__1__Impl )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2403:2: rule__Event__Group_8_2__1__Impl
            {
            pushFollow(FollowSets000.FOLLOW_rule__Event__Group_8_2__1__Impl_in_rule__Event__Group_8_2__14748);
            rule__Event__Group_8_2__1__Impl();

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
    // $ANTLR end "rule__Event__Group_8_2__1"


    // $ANTLR start "rule__Event__Group_8_2__1__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2409:1: rule__Event__Group_8_2__1__Impl : ( ( rule__Event__MessagesAssignment_8_2_1 ) ) ;
    public final void rule__Event__Group_8_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2413:1: ( ( ( rule__Event__MessagesAssignment_8_2_1 ) ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2414:1: ( ( rule__Event__MessagesAssignment_8_2_1 ) )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2414:1: ( ( rule__Event__MessagesAssignment_8_2_1 ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2415:1: ( rule__Event__MessagesAssignment_8_2_1 )
            {
             before(grammarAccess.getEventAccess().getMessagesAssignment_8_2_1()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2416:1: ( rule__Event__MessagesAssignment_8_2_1 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2416:2: rule__Event__MessagesAssignment_8_2_1
            {
            pushFollow(FollowSets000.FOLLOW_rule__Event__MessagesAssignment_8_2_1_in_rule__Event__Group_8_2__1__Impl4775);
            rule__Event__MessagesAssignment_8_2_1();

            state._fsp--;


            }

             after(grammarAccess.getEventAccess().getMessagesAssignment_8_2_1()); 

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
    // $ANTLR end "rule__Event__Group_8_2__1__Impl"


    // $ANTLR start "rule__Event__Group_9__0"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2430:1: rule__Event__Group_9__0 : rule__Event__Group_9__0__Impl rule__Event__Group_9__1 ;
    public final void rule__Event__Group_9__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2434:1: ( rule__Event__Group_9__0__Impl rule__Event__Group_9__1 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2435:2: rule__Event__Group_9__0__Impl rule__Event__Group_9__1
            {
            pushFollow(FollowSets000.FOLLOW_rule__Event__Group_9__0__Impl_in_rule__Event__Group_9__04809);
            rule__Event__Group_9__0__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Event__Group_9__1_in_rule__Event__Group_9__04812);
            rule__Event__Group_9__1();

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
    // $ANTLR end "rule__Event__Group_9__0"


    // $ANTLR start "rule__Event__Group_9__0__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2442:1: rule__Event__Group_9__0__Impl : ( '{' ) ;
    public final void rule__Event__Group_9__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2446:1: ( ( '{' ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2447:1: ( '{' )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2447:1: ( '{' )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2448:1: '{'
            {
             before(grammarAccess.getEventAccess().getLeftCurlyBracketKeyword_9_0()); 
            match(input,20,FollowSets000.FOLLOW_20_in_rule__Event__Group_9__0__Impl4840); 
             after(grammarAccess.getEventAccess().getLeftCurlyBracketKeyword_9_0()); 

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
    // $ANTLR end "rule__Event__Group_9__0__Impl"


    // $ANTLR start "rule__Event__Group_9__1"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2461:1: rule__Event__Group_9__1 : rule__Event__Group_9__1__Impl rule__Event__Group_9__2 ;
    public final void rule__Event__Group_9__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2465:1: ( rule__Event__Group_9__1__Impl rule__Event__Group_9__2 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2466:2: rule__Event__Group_9__1__Impl rule__Event__Group_9__2
            {
            pushFollow(FollowSets000.FOLLOW_rule__Event__Group_9__1__Impl_in_rule__Event__Group_9__14871);
            rule__Event__Group_9__1__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Event__Group_9__2_in_rule__Event__Group_9__14874);
            rule__Event__Group_9__2();

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
    // $ANTLR end "rule__Event__Group_9__1"


    // $ANTLR start "rule__Event__Group_9__1__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2473:1: rule__Event__Group_9__1__Impl : ( ( rule__Event__Group_9_1__0 )? ) ;
    public final void rule__Event__Group_9__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2477:1: ( ( ( rule__Event__Group_9_1__0 )? ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2478:1: ( ( rule__Event__Group_9_1__0 )? )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2478:1: ( ( rule__Event__Group_9_1__0 )? )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2479:1: ( rule__Event__Group_9_1__0 )?
            {
             before(grammarAccess.getEventAccess().getGroup_9_1()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2480:1: ( rule__Event__Group_9_1__0 )?
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==RULE_ID) ) {
                alt25=1;
            }
            switch (alt25) {
                case 1 :
                    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2480:2: rule__Event__Group_9_1__0
                    {
                    pushFollow(FollowSets000.FOLLOW_rule__Event__Group_9_1__0_in_rule__Event__Group_9__1__Impl4901);
                    rule__Event__Group_9_1__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getEventAccess().getGroup_9_1()); 

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
    // $ANTLR end "rule__Event__Group_9__1__Impl"


    // $ANTLR start "rule__Event__Group_9__2"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2490:1: rule__Event__Group_9__2 : rule__Event__Group_9__2__Impl rule__Event__Group_9__3 ;
    public final void rule__Event__Group_9__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2494:1: ( rule__Event__Group_9__2__Impl rule__Event__Group_9__3 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2495:2: rule__Event__Group_9__2__Impl rule__Event__Group_9__3
            {
            pushFollow(FollowSets000.FOLLOW_rule__Event__Group_9__2__Impl_in_rule__Event__Group_9__24932);
            rule__Event__Group_9__2__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Event__Group_9__3_in_rule__Event__Group_9__24935);
            rule__Event__Group_9__3();

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
    // $ANTLR end "rule__Event__Group_9__2"


    // $ANTLR start "rule__Event__Group_9__2__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2502:1: rule__Event__Group_9__2__Impl : ( ( rule__Event__Group_9_2__0 )? ) ;
    public final void rule__Event__Group_9__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2506:1: ( ( ( rule__Event__Group_9_2__0 )? ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2507:1: ( ( rule__Event__Group_9_2__0 )? )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2507:1: ( ( rule__Event__Group_9_2__0 )? )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2508:1: ( rule__Event__Group_9_2__0 )?
            {
             before(grammarAccess.getEventAccess().getGroup_9_2()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2509:1: ( rule__Event__Group_9_2__0 )?
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==28) ) {
                alt26=1;
            }
            switch (alt26) {
                case 1 :
                    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2509:2: rule__Event__Group_9_2__0
                    {
                    pushFollow(FollowSets000.FOLLOW_rule__Event__Group_9_2__0_in_rule__Event__Group_9__2__Impl4962);
                    rule__Event__Group_9_2__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getEventAccess().getGroup_9_2()); 

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
    // $ANTLR end "rule__Event__Group_9__2__Impl"


    // $ANTLR start "rule__Event__Group_9__3"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2519:1: rule__Event__Group_9__3 : rule__Event__Group_9__3__Impl ;
    public final void rule__Event__Group_9__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2523:1: ( rule__Event__Group_9__3__Impl )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2524:2: rule__Event__Group_9__3__Impl
            {
            pushFollow(FollowSets000.FOLLOW_rule__Event__Group_9__3__Impl_in_rule__Event__Group_9__34993);
            rule__Event__Group_9__3__Impl();

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
    // $ANTLR end "rule__Event__Group_9__3"


    // $ANTLR start "rule__Event__Group_9__3__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2530:1: rule__Event__Group_9__3__Impl : ( '}' ) ;
    public final void rule__Event__Group_9__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2534:1: ( ( '}' ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2535:1: ( '}' )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2535:1: ( '}' )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2536:1: '}'
            {
             before(grammarAccess.getEventAccess().getRightCurlyBracketKeyword_9_3()); 
            match(input,21,FollowSets000.FOLLOW_21_in_rule__Event__Group_9__3__Impl5021); 
             after(grammarAccess.getEventAccess().getRightCurlyBracketKeyword_9_3()); 

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
    // $ANTLR end "rule__Event__Group_9__3__Impl"


    // $ANTLR start "rule__Event__Group_9_1__0"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2557:1: rule__Event__Group_9_1__0 : rule__Event__Group_9_1__0__Impl rule__Event__Group_9_1__1 ;
    public final void rule__Event__Group_9_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2561:1: ( rule__Event__Group_9_1__0__Impl rule__Event__Group_9_1__1 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2562:2: rule__Event__Group_9_1__0__Impl rule__Event__Group_9_1__1
            {
            pushFollow(FollowSets000.FOLLOW_rule__Event__Group_9_1__0__Impl_in_rule__Event__Group_9_1__05060);
            rule__Event__Group_9_1__0__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Event__Group_9_1__1_in_rule__Event__Group_9_1__05063);
            rule__Event__Group_9_1__1();

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
    // $ANTLR end "rule__Event__Group_9_1__0"


    // $ANTLR start "rule__Event__Group_9_1__0__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2569:1: rule__Event__Group_9_1__0__Impl : ( ( rule__Event__ParametersAssignment_9_1_0 ) ) ;
    public final void rule__Event__Group_9_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2573:1: ( ( ( rule__Event__ParametersAssignment_9_1_0 ) ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2574:1: ( ( rule__Event__ParametersAssignment_9_1_0 ) )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2574:1: ( ( rule__Event__ParametersAssignment_9_1_0 ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2575:1: ( rule__Event__ParametersAssignment_9_1_0 )
            {
             before(grammarAccess.getEventAccess().getParametersAssignment_9_1_0()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2576:1: ( rule__Event__ParametersAssignment_9_1_0 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2576:2: rule__Event__ParametersAssignment_9_1_0
            {
            pushFollow(FollowSets000.FOLLOW_rule__Event__ParametersAssignment_9_1_0_in_rule__Event__Group_9_1__0__Impl5090);
            rule__Event__ParametersAssignment_9_1_0();

            state._fsp--;


            }

             after(grammarAccess.getEventAccess().getParametersAssignment_9_1_0()); 

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
    // $ANTLR end "rule__Event__Group_9_1__0__Impl"


    // $ANTLR start "rule__Event__Group_9_1__1"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2586:1: rule__Event__Group_9_1__1 : rule__Event__Group_9_1__1__Impl ;
    public final void rule__Event__Group_9_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2590:1: ( rule__Event__Group_9_1__1__Impl )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2591:2: rule__Event__Group_9_1__1__Impl
            {
            pushFollow(FollowSets000.FOLLOW_rule__Event__Group_9_1__1__Impl_in_rule__Event__Group_9_1__15120);
            rule__Event__Group_9_1__1__Impl();

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
    // $ANTLR end "rule__Event__Group_9_1__1"


    // $ANTLR start "rule__Event__Group_9_1__1__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2597:1: rule__Event__Group_9_1__1__Impl : ( ( rule__Event__ParametersAssignment_9_1_1 )* ) ;
    public final void rule__Event__Group_9_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2601:1: ( ( ( rule__Event__ParametersAssignment_9_1_1 )* ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2602:1: ( ( rule__Event__ParametersAssignment_9_1_1 )* )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2602:1: ( ( rule__Event__ParametersAssignment_9_1_1 )* )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2603:1: ( rule__Event__ParametersAssignment_9_1_1 )*
            {
             before(grammarAccess.getEventAccess().getParametersAssignment_9_1_1()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2604:1: ( rule__Event__ParametersAssignment_9_1_1 )*
            loop27:
            do {
                int alt27=2;
                int LA27_0 = input.LA(1);

                if ( (LA27_0==RULE_ID) ) {
                    alt27=1;
                }


                switch (alt27) {
            	case 1 :
            	    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2604:2: rule__Event__ParametersAssignment_9_1_1
            	    {
            	    pushFollow(FollowSets000.FOLLOW_rule__Event__ParametersAssignment_9_1_1_in_rule__Event__Group_9_1__1__Impl5147);
            	    rule__Event__ParametersAssignment_9_1_1();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop27;
                }
            } while (true);

             after(grammarAccess.getEventAccess().getParametersAssignment_9_1_1()); 

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
    // $ANTLR end "rule__Event__Group_9_1__1__Impl"


    // $ANTLR start "rule__Event__Group_9_2__0"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2618:1: rule__Event__Group_9_2__0 : rule__Event__Group_9_2__0__Impl rule__Event__Group_9_2__1 ;
    public final void rule__Event__Group_9_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2622:1: ( rule__Event__Group_9_2__0__Impl rule__Event__Group_9_2__1 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2623:2: rule__Event__Group_9_2__0__Impl rule__Event__Group_9_2__1
            {
            pushFollow(FollowSets000.FOLLOW_rule__Event__Group_9_2__0__Impl_in_rule__Event__Group_9_2__05182);
            rule__Event__Group_9_2__0__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Event__Group_9_2__1_in_rule__Event__Group_9_2__05185);
            rule__Event__Group_9_2__1();

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
    // $ANTLR end "rule__Event__Group_9_2__0"


    // $ANTLR start "rule__Event__Group_9_2__0__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2630:1: rule__Event__Group_9_2__0__Impl : ( ( rule__Event__SnippetsAssignment_9_2_0 ) ) ;
    public final void rule__Event__Group_9_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2634:1: ( ( ( rule__Event__SnippetsAssignment_9_2_0 ) ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2635:1: ( ( rule__Event__SnippetsAssignment_9_2_0 ) )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2635:1: ( ( rule__Event__SnippetsAssignment_9_2_0 ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2636:1: ( rule__Event__SnippetsAssignment_9_2_0 )
            {
             before(grammarAccess.getEventAccess().getSnippetsAssignment_9_2_0()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2637:1: ( rule__Event__SnippetsAssignment_9_2_0 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2637:2: rule__Event__SnippetsAssignment_9_2_0
            {
            pushFollow(FollowSets000.FOLLOW_rule__Event__SnippetsAssignment_9_2_0_in_rule__Event__Group_9_2__0__Impl5212);
            rule__Event__SnippetsAssignment_9_2_0();

            state._fsp--;


            }

             after(grammarAccess.getEventAccess().getSnippetsAssignment_9_2_0()); 

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
    // $ANTLR end "rule__Event__Group_9_2__0__Impl"


    // $ANTLR start "rule__Event__Group_9_2__1"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2647:1: rule__Event__Group_9_2__1 : rule__Event__Group_9_2__1__Impl ;
    public final void rule__Event__Group_9_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2651:1: ( rule__Event__Group_9_2__1__Impl )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2652:2: rule__Event__Group_9_2__1__Impl
            {
            pushFollow(FollowSets000.FOLLOW_rule__Event__Group_9_2__1__Impl_in_rule__Event__Group_9_2__15242);
            rule__Event__Group_9_2__1__Impl();

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
    // $ANTLR end "rule__Event__Group_9_2__1"


    // $ANTLR start "rule__Event__Group_9_2__1__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2658:1: rule__Event__Group_9_2__1__Impl : ( ( rule__Event__SnippetsAssignment_9_2_1 )* ) ;
    public final void rule__Event__Group_9_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2662:1: ( ( ( rule__Event__SnippetsAssignment_9_2_1 )* ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2663:1: ( ( rule__Event__SnippetsAssignment_9_2_1 )* )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2663:1: ( ( rule__Event__SnippetsAssignment_9_2_1 )* )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2664:1: ( rule__Event__SnippetsAssignment_9_2_1 )*
            {
             before(grammarAccess.getEventAccess().getSnippetsAssignment_9_2_1()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2665:1: ( rule__Event__SnippetsAssignment_9_2_1 )*
            loop28:
            do {
                int alt28=2;
                int LA28_0 = input.LA(1);

                if ( (LA28_0==28) ) {
                    alt28=1;
                }


                switch (alt28) {
            	case 1 :
            	    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2665:2: rule__Event__SnippetsAssignment_9_2_1
            	    {
            	    pushFollow(FollowSets000.FOLLOW_rule__Event__SnippetsAssignment_9_2_1_in_rule__Event__Group_9_2__1__Impl5269);
            	    rule__Event__SnippetsAssignment_9_2_1();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop28;
                }
            } while (true);

             after(grammarAccess.getEventAccess().getSnippetsAssignment_9_2_1()); 

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
    // $ANTLR end "rule__Event__Group_9_2__1__Impl"


    // $ANTLR start "rule__Snippet__Group__0"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2679:1: rule__Snippet__Group__0 : rule__Snippet__Group__0__Impl rule__Snippet__Group__1 ;
    public final void rule__Snippet__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2683:1: ( rule__Snippet__Group__0__Impl rule__Snippet__Group__1 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2684:2: rule__Snippet__Group__0__Impl rule__Snippet__Group__1
            {
            pushFollow(FollowSets000.FOLLOW_rule__Snippet__Group__0__Impl_in_rule__Snippet__Group__05304);
            rule__Snippet__Group__0__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Snippet__Group__1_in_rule__Snippet__Group__05307);
            rule__Snippet__Group__1();

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
    // $ANTLR end "rule__Snippet__Group__0"


    // $ANTLR start "rule__Snippet__Group__0__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2691:1: rule__Snippet__Group__0__Impl : ( () ) ;
    public final void rule__Snippet__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2695:1: ( ( () ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2696:1: ( () )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2696:1: ( () )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2697:1: ()
            {
             before(grammarAccess.getSnippetAccess().getSnippetAction_0()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2698:1: ()
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2700:1: 
            {
            }

             after(grammarAccess.getSnippetAccess().getSnippetAction_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Snippet__Group__0__Impl"


    // $ANTLR start "rule__Snippet__Group__1"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2710:1: rule__Snippet__Group__1 : rule__Snippet__Group__1__Impl rule__Snippet__Group__2 ;
    public final void rule__Snippet__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2714:1: ( rule__Snippet__Group__1__Impl rule__Snippet__Group__2 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2715:2: rule__Snippet__Group__1__Impl rule__Snippet__Group__2
            {
            pushFollow(FollowSets000.FOLLOW_rule__Snippet__Group__1__Impl_in_rule__Snippet__Group__15365);
            rule__Snippet__Group__1__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Snippet__Group__2_in_rule__Snippet__Group__15368);
            rule__Snippet__Group__2();

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
    // $ANTLR end "rule__Snippet__Group__1"


    // $ANTLR start "rule__Snippet__Group__1__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2722:1: rule__Snippet__Group__1__Impl : ( 'Snippet' ) ;
    public final void rule__Snippet__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2726:1: ( ( 'Snippet' ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2727:1: ( 'Snippet' )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2727:1: ( 'Snippet' )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2728:1: 'Snippet'
            {
             before(grammarAccess.getSnippetAccess().getSnippetKeyword_1()); 
            match(input,28,FollowSets000.FOLLOW_28_in_rule__Snippet__Group__1__Impl5396); 
             after(grammarAccess.getSnippetAccess().getSnippetKeyword_1()); 

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
    // $ANTLR end "rule__Snippet__Group__1__Impl"


    // $ANTLR start "rule__Snippet__Group__2"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2741:1: rule__Snippet__Group__2 : rule__Snippet__Group__2__Impl rule__Snippet__Group__3 ;
    public final void rule__Snippet__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2745:1: ( rule__Snippet__Group__2__Impl rule__Snippet__Group__3 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2746:2: rule__Snippet__Group__2__Impl rule__Snippet__Group__3
            {
            pushFollow(FollowSets000.FOLLOW_rule__Snippet__Group__2__Impl_in_rule__Snippet__Group__25427);
            rule__Snippet__Group__2__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Snippet__Group__3_in_rule__Snippet__Group__25430);
            rule__Snippet__Group__3();

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
    // $ANTLR end "rule__Snippet__Group__2"


    // $ANTLR start "rule__Snippet__Group__2__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2753:1: rule__Snippet__Group__2__Impl : ( ( rule__Snippet__TypeNameAssignment_2 )? ) ;
    public final void rule__Snippet__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2757:1: ( ( ( rule__Snippet__TypeNameAssignment_2 )? ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2758:1: ( ( rule__Snippet__TypeNameAssignment_2 )? )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2758:1: ( ( rule__Snippet__TypeNameAssignment_2 )? )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2759:1: ( rule__Snippet__TypeNameAssignment_2 )?
            {
             before(grammarAccess.getSnippetAccess().getTypeNameAssignment_2()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2760:1: ( rule__Snippet__TypeNameAssignment_2 )?
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( ((LA29_0>=RULE_STRING && LA29_0<=RULE_VALUE)) ) {
                alt29=1;
            }
            else if ( (LA29_0==RULE_ID) ) {
                int LA29_2 = input.LA(2);

                if ( (LA29_2==EOF||LA29_2==RULE_ID||LA29_2==15||(LA29_2>=20 && LA29_2<=21)||LA29_2==28) ) {
                    alt29=1;
                }
            }
            switch (alt29) {
                case 1 :
                    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2760:2: rule__Snippet__TypeNameAssignment_2
                    {
                    pushFollow(FollowSets000.FOLLOW_rule__Snippet__TypeNameAssignment_2_in_rule__Snippet__Group__2__Impl5457);
                    rule__Snippet__TypeNameAssignment_2();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getSnippetAccess().getTypeNameAssignment_2()); 

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
    // $ANTLR end "rule__Snippet__Group__2__Impl"


    // $ANTLR start "rule__Snippet__Group__3"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2770:1: rule__Snippet__Group__3 : rule__Snippet__Group__3__Impl rule__Snippet__Group__4 ;
    public final void rule__Snippet__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2774:1: ( rule__Snippet__Group__3__Impl rule__Snippet__Group__4 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2775:2: rule__Snippet__Group__3__Impl rule__Snippet__Group__4
            {
            pushFollow(FollowSets000.FOLLOW_rule__Snippet__Group__3__Impl_in_rule__Snippet__Group__35488);
            rule__Snippet__Group__3__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Snippet__Group__4_in_rule__Snippet__Group__35491);
            rule__Snippet__Group__4();

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
    // $ANTLR end "rule__Snippet__Group__3"


    // $ANTLR start "rule__Snippet__Group__3__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2782:1: rule__Snippet__Group__3__Impl : ( ( rule__Snippet__PropertiesAssignment_3 )* ) ;
    public final void rule__Snippet__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2786:1: ( ( ( rule__Snippet__PropertiesAssignment_3 )* ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2787:1: ( ( rule__Snippet__PropertiesAssignment_3 )* )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2787:1: ( ( rule__Snippet__PropertiesAssignment_3 )* )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2788:1: ( rule__Snippet__PropertiesAssignment_3 )*
            {
             before(grammarAccess.getSnippetAccess().getPropertiesAssignment_3()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2789:1: ( rule__Snippet__PropertiesAssignment_3 )*
            loop30:
            do {
                int alt30=2;
                int LA30_0 = input.LA(1);

                if ( (LA30_0==RULE_ID) ) {
                    alt30=1;
                }


                switch (alt30) {
            	case 1 :
            	    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2789:2: rule__Snippet__PropertiesAssignment_3
            	    {
            	    pushFollow(FollowSets000.FOLLOW_rule__Snippet__PropertiesAssignment_3_in_rule__Snippet__Group__3__Impl5518);
            	    rule__Snippet__PropertiesAssignment_3();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop30;
                }
            } while (true);

             after(grammarAccess.getSnippetAccess().getPropertiesAssignment_3()); 

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
    // $ANTLR end "rule__Snippet__Group__3__Impl"


    // $ANTLR start "rule__Snippet__Group__4"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2799:1: rule__Snippet__Group__4 : rule__Snippet__Group__4__Impl ;
    public final void rule__Snippet__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2803:1: ( rule__Snippet__Group__4__Impl )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2804:2: rule__Snippet__Group__4__Impl
            {
            pushFollow(FollowSets000.FOLLOW_rule__Snippet__Group__4__Impl_in_rule__Snippet__Group__45549);
            rule__Snippet__Group__4__Impl();

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
    // $ANTLR end "rule__Snippet__Group__4"


    // $ANTLR start "rule__Snippet__Group__4__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2810:1: rule__Snippet__Group__4__Impl : ( ( rule__Snippet__Group_4__0 )? ) ;
    public final void rule__Snippet__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2814:1: ( ( ( rule__Snippet__Group_4__0 )? ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2815:1: ( ( rule__Snippet__Group_4__0 )? )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2815:1: ( ( rule__Snippet__Group_4__0 )? )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2816:1: ( rule__Snippet__Group_4__0 )?
            {
             before(grammarAccess.getSnippetAccess().getGroup_4()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2817:1: ( rule__Snippet__Group_4__0 )?
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==20) ) {
                alt31=1;
            }
            switch (alt31) {
                case 1 :
                    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2817:2: rule__Snippet__Group_4__0
                    {
                    pushFollow(FollowSets000.FOLLOW_rule__Snippet__Group_4__0_in_rule__Snippet__Group__4__Impl5576);
                    rule__Snippet__Group_4__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getSnippetAccess().getGroup_4()); 

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
    // $ANTLR end "rule__Snippet__Group__4__Impl"


    // $ANTLR start "rule__Snippet__Group_4__0"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2837:1: rule__Snippet__Group_4__0 : rule__Snippet__Group_4__0__Impl rule__Snippet__Group_4__1 ;
    public final void rule__Snippet__Group_4__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2841:1: ( rule__Snippet__Group_4__0__Impl rule__Snippet__Group_4__1 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2842:2: rule__Snippet__Group_4__0__Impl rule__Snippet__Group_4__1
            {
            pushFollow(FollowSets000.FOLLOW_rule__Snippet__Group_4__0__Impl_in_rule__Snippet__Group_4__05617);
            rule__Snippet__Group_4__0__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Snippet__Group_4__1_in_rule__Snippet__Group_4__05620);
            rule__Snippet__Group_4__1();

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
    // $ANTLR end "rule__Snippet__Group_4__0"


    // $ANTLR start "rule__Snippet__Group_4__0__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2849:1: rule__Snippet__Group_4__0__Impl : ( '{' ) ;
    public final void rule__Snippet__Group_4__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2853:1: ( ( '{' ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2854:1: ( '{' )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2854:1: ( '{' )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2855:1: '{'
            {
             before(grammarAccess.getSnippetAccess().getLeftCurlyBracketKeyword_4_0()); 
            match(input,20,FollowSets000.FOLLOW_20_in_rule__Snippet__Group_4__0__Impl5648); 
             after(grammarAccess.getSnippetAccess().getLeftCurlyBracketKeyword_4_0()); 

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
    // $ANTLR end "rule__Snippet__Group_4__0__Impl"


    // $ANTLR start "rule__Snippet__Group_4__1"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2868:1: rule__Snippet__Group_4__1 : rule__Snippet__Group_4__1__Impl rule__Snippet__Group_4__2 ;
    public final void rule__Snippet__Group_4__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2872:1: ( rule__Snippet__Group_4__1__Impl rule__Snippet__Group_4__2 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2873:2: rule__Snippet__Group_4__1__Impl rule__Snippet__Group_4__2
            {
            pushFollow(FollowSets000.FOLLOW_rule__Snippet__Group_4__1__Impl_in_rule__Snippet__Group_4__15679);
            rule__Snippet__Group_4__1__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Snippet__Group_4__2_in_rule__Snippet__Group_4__15682);
            rule__Snippet__Group_4__2();

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
    // $ANTLR end "rule__Snippet__Group_4__1"


    // $ANTLR start "rule__Snippet__Group_4__1__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2880:1: rule__Snippet__Group_4__1__Impl : ( ( rule__Snippet__Group_4_1__0 )? ) ;
    public final void rule__Snippet__Group_4__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2884:1: ( ( ( rule__Snippet__Group_4_1__0 )? ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2885:1: ( ( rule__Snippet__Group_4_1__0 )? )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2885:1: ( ( rule__Snippet__Group_4_1__0 )? )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2886:1: ( rule__Snippet__Group_4_1__0 )?
            {
             before(grammarAccess.getSnippetAccess().getGroup_4_1()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2887:1: ( rule__Snippet__Group_4_1__0 )?
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==28) ) {
                alt32=1;
            }
            switch (alt32) {
                case 1 :
                    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2887:2: rule__Snippet__Group_4_1__0
                    {
                    pushFollow(FollowSets000.FOLLOW_rule__Snippet__Group_4_1__0_in_rule__Snippet__Group_4__1__Impl5709);
                    rule__Snippet__Group_4_1__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getSnippetAccess().getGroup_4_1()); 

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
    // $ANTLR end "rule__Snippet__Group_4__1__Impl"


    // $ANTLR start "rule__Snippet__Group_4__2"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2897:1: rule__Snippet__Group_4__2 : rule__Snippet__Group_4__2__Impl ;
    public final void rule__Snippet__Group_4__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2901:1: ( rule__Snippet__Group_4__2__Impl )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2902:2: rule__Snippet__Group_4__2__Impl
            {
            pushFollow(FollowSets000.FOLLOW_rule__Snippet__Group_4__2__Impl_in_rule__Snippet__Group_4__25740);
            rule__Snippet__Group_4__2__Impl();

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
    // $ANTLR end "rule__Snippet__Group_4__2"


    // $ANTLR start "rule__Snippet__Group_4__2__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2908:1: rule__Snippet__Group_4__2__Impl : ( '}' ) ;
    public final void rule__Snippet__Group_4__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2912:1: ( ( '}' ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2913:1: ( '}' )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2913:1: ( '}' )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2914:1: '}'
            {
             before(grammarAccess.getSnippetAccess().getRightCurlyBracketKeyword_4_2()); 
            match(input,21,FollowSets000.FOLLOW_21_in_rule__Snippet__Group_4__2__Impl5768); 
             after(grammarAccess.getSnippetAccess().getRightCurlyBracketKeyword_4_2()); 

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
    // $ANTLR end "rule__Snippet__Group_4__2__Impl"


    // $ANTLR start "rule__Snippet__Group_4_1__0"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2933:1: rule__Snippet__Group_4_1__0 : rule__Snippet__Group_4_1__0__Impl rule__Snippet__Group_4_1__1 ;
    public final void rule__Snippet__Group_4_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2937:1: ( rule__Snippet__Group_4_1__0__Impl rule__Snippet__Group_4_1__1 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2938:2: rule__Snippet__Group_4_1__0__Impl rule__Snippet__Group_4_1__1
            {
            pushFollow(FollowSets000.FOLLOW_rule__Snippet__Group_4_1__0__Impl_in_rule__Snippet__Group_4_1__05805);
            rule__Snippet__Group_4_1__0__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Snippet__Group_4_1__1_in_rule__Snippet__Group_4_1__05808);
            rule__Snippet__Group_4_1__1();

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
    // $ANTLR end "rule__Snippet__Group_4_1__0"


    // $ANTLR start "rule__Snippet__Group_4_1__0__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2945:1: rule__Snippet__Group_4_1__0__Impl : ( ( rule__Snippet__ContentsAssignment_4_1_0 ) ) ;
    public final void rule__Snippet__Group_4_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2949:1: ( ( ( rule__Snippet__ContentsAssignment_4_1_0 ) ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2950:1: ( ( rule__Snippet__ContentsAssignment_4_1_0 ) )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2950:1: ( ( rule__Snippet__ContentsAssignment_4_1_0 ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2951:1: ( rule__Snippet__ContentsAssignment_4_1_0 )
            {
             before(grammarAccess.getSnippetAccess().getContentsAssignment_4_1_0()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2952:1: ( rule__Snippet__ContentsAssignment_4_1_0 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2952:2: rule__Snippet__ContentsAssignment_4_1_0
            {
            pushFollow(FollowSets000.FOLLOW_rule__Snippet__ContentsAssignment_4_1_0_in_rule__Snippet__Group_4_1__0__Impl5835);
            rule__Snippet__ContentsAssignment_4_1_0();

            state._fsp--;


            }

             after(grammarAccess.getSnippetAccess().getContentsAssignment_4_1_0()); 

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
    // $ANTLR end "rule__Snippet__Group_4_1__0__Impl"


    // $ANTLR start "rule__Snippet__Group_4_1__1"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2962:1: rule__Snippet__Group_4_1__1 : rule__Snippet__Group_4_1__1__Impl ;
    public final void rule__Snippet__Group_4_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2966:1: ( rule__Snippet__Group_4_1__1__Impl )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2967:2: rule__Snippet__Group_4_1__1__Impl
            {
            pushFollow(FollowSets000.FOLLOW_rule__Snippet__Group_4_1__1__Impl_in_rule__Snippet__Group_4_1__15865);
            rule__Snippet__Group_4_1__1__Impl();

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
    // $ANTLR end "rule__Snippet__Group_4_1__1"


    // $ANTLR start "rule__Snippet__Group_4_1__1__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2973:1: rule__Snippet__Group_4_1__1__Impl : ( ( rule__Snippet__ContentsAssignment_4_1_1 )* ) ;
    public final void rule__Snippet__Group_4_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2977:1: ( ( ( rule__Snippet__ContentsAssignment_4_1_1 )* ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2978:1: ( ( rule__Snippet__ContentsAssignment_4_1_1 )* )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2978:1: ( ( rule__Snippet__ContentsAssignment_4_1_1 )* )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2979:1: ( rule__Snippet__ContentsAssignment_4_1_1 )*
            {
             before(grammarAccess.getSnippetAccess().getContentsAssignment_4_1_1()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2980:1: ( rule__Snippet__ContentsAssignment_4_1_1 )*
            loop33:
            do {
                int alt33=2;
                int LA33_0 = input.LA(1);

                if ( (LA33_0==28) ) {
                    alt33=1;
                }


                switch (alt33) {
            	case 1 :
            	    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2980:2: rule__Snippet__ContentsAssignment_4_1_1
            	    {
            	    pushFollow(FollowSets000.FOLLOW_rule__Snippet__ContentsAssignment_4_1_1_in_rule__Snippet__Group_4_1__1__Impl5892);
            	    rule__Snippet__ContentsAssignment_4_1_1();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop33;
                }
            } while (true);

             after(grammarAccess.getSnippetAccess().getContentsAssignment_4_1_1()); 

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
    // $ANTLR end "rule__Snippet__Group_4_1__1__Impl"


    // $ANTLR start "rule__Translation__Group__0"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2994:1: rule__Translation__Group__0 : rule__Translation__Group__0__Impl rule__Translation__Group__1 ;
    public final void rule__Translation__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2998:1: ( rule__Translation__Group__0__Impl rule__Translation__Group__1 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:2999:2: rule__Translation__Group__0__Impl rule__Translation__Group__1
            {
            pushFollow(FollowSets000.FOLLOW_rule__Translation__Group__0__Impl_in_rule__Translation__Group__05927);
            rule__Translation__Group__0__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Translation__Group__1_in_rule__Translation__Group__05930);
            rule__Translation__Group__1();

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
    // $ANTLR end "rule__Translation__Group__0"


    // $ANTLR start "rule__Translation__Group__0__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3006:1: rule__Translation__Group__0__Impl : ( ( rule__Translation__LanguageAssignment_0 ) ) ;
    public final void rule__Translation__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3010:1: ( ( ( rule__Translation__LanguageAssignment_0 ) ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3011:1: ( ( rule__Translation__LanguageAssignment_0 ) )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3011:1: ( ( rule__Translation__LanguageAssignment_0 ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3012:1: ( rule__Translation__LanguageAssignment_0 )
            {
             before(grammarAccess.getTranslationAccess().getLanguageAssignment_0()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3013:1: ( rule__Translation__LanguageAssignment_0 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3013:2: rule__Translation__LanguageAssignment_0
            {
            pushFollow(FollowSets000.FOLLOW_rule__Translation__LanguageAssignment_0_in_rule__Translation__Group__0__Impl5957);
            rule__Translation__LanguageAssignment_0();

            state._fsp--;


            }

             after(grammarAccess.getTranslationAccess().getLanguageAssignment_0()); 

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
    // $ANTLR end "rule__Translation__Group__0__Impl"


    // $ANTLR start "rule__Translation__Group__1"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3023:1: rule__Translation__Group__1 : rule__Translation__Group__1__Impl rule__Translation__Group__2 ;
    public final void rule__Translation__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3027:1: ( rule__Translation__Group__1__Impl rule__Translation__Group__2 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3028:2: rule__Translation__Group__1__Impl rule__Translation__Group__2
            {
            pushFollow(FollowSets000.FOLLOW_rule__Translation__Group__1__Impl_in_rule__Translation__Group__15987);
            rule__Translation__Group__1__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Translation__Group__2_in_rule__Translation__Group__15990);
            rule__Translation__Group__2();

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
    // $ANTLR end "rule__Translation__Group__1"


    // $ANTLR start "rule__Translation__Group__1__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3035:1: rule__Translation__Group__1__Impl : ( '=' ) ;
    public final void rule__Translation__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3039:1: ( ( '=' ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3040:1: ( '=' )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3040:1: ( '=' )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3041:1: '='
            {
             before(grammarAccess.getTranslationAccess().getEqualsSignKeyword_1()); 
            match(input,14,FollowSets000.FOLLOW_14_in_rule__Translation__Group__1__Impl6018); 
             after(grammarAccess.getTranslationAccess().getEqualsSignKeyword_1()); 

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
    // $ANTLR end "rule__Translation__Group__1__Impl"


    // $ANTLR start "rule__Translation__Group__2"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3054:1: rule__Translation__Group__2 : rule__Translation__Group__2__Impl ;
    public final void rule__Translation__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3058:1: ( rule__Translation__Group__2__Impl )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3059:2: rule__Translation__Group__2__Impl
            {
            pushFollow(FollowSets000.FOLLOW_rule__Translation__Group__2__Impl_in_rule__Translation__Group__26049);
            rule__Translation__Group__2__Impl();

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
    // $ANTLR end "rule__Translation__Group__2"


    // $ANTLR start "rule__Translation__Group__2__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3065:1: rule__Translation__Group__2__Impl : ( ( rule__Translation__MessageAssignment_2 ) ) ;
    public final void rule__Translation__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3069:1: ( ( ( rule__Translation__MessageAssignment_2 ) ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3070:1: ( ( rule__Translation__MessageAssignment_2 ) )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3070:1: ( ( rule__Translation__MessageAssignment_2 ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3071:1: ( rule__Translation__MessageAssignment_2 )
            {
             before(grammarAccess.getTranslationAccess().getMessageAssignment_2()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3072:1: ( rule__Translation__MessageAssignment_2 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3072:2: rule__Translation__MessageAssignment_2
            {
            pushFollow(FollowSets000.FOLLOW_rule__Translation__MessageAssignment_2_in_rule__Translation__Group__2__Impl6076);
            rule__Translation__MessageAssignment_2();

            state._fsp--;


            }

             after(grammarAccess.getTranslationAccess().getMessageAssignment_2()); 

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
    // $ANTLR end "rule__Translation__Group__2__Impl"


    // $ANTLR start "rule__Parameter__Group__0"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3088:1: rule__Parameter__Group__0 : rule__Parameter__Group__0__Impl rule__Parameter__Group__1 ;
    public final void rule__Parameter__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3092:1: ( rule__Parameter__Group__0__Impl rule__Parameter__Group__1 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3093:2: rule__Parameter__Group__0__Impl rule__Parameter__Group__1
            {
            pushFollow(FollowSets000.FOLLOW_rule__Parameter__Group__0__Impl_in_rule__Parameter__Group__06112);
            rule__Parameter__Group__0__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Parameter__Group__1_in_rule__Parameter__Group__06115);
            rule__Parameter__Group__1();

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
    // $ANTLR end "rule__Parameter__Group__0"


    // $ANTLR start "rule__Parameter__Group__0__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3100:1: rule__Parameter__Group__0__Impl : ( ( rule__Parameter__NameAssignment_0 ) ) ;
    public final void rule__Parameter__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3104:1: ( ( ( rule__Parameter__NameAssignment_0 ) ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3105:1: ( ( rule__Parameter__NameAssignment_0 ) )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3105:1: ( ( rule__Parameter__NameAssignment_0 ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3106:1: ( rule__Parameter__NameAssignment_0 )
            {
             before(grammarAccess.getParameterAccess().getNameAssignment_0()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3107:1: ( rule__Parameter__NameAssignment_0 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3107:2: rule__Parameter__NameAssignment_0
            {
            pushFollow(FollowSets000.FOLLOW_rule__Parameter__NameAssignment_0_in_rule__Parameter__Group__0__Impl6142);
            rule__Parameter__NameAssignment_0();

            state._fsp--;


            }

             after(grammarAccess.getParameterAccess().getNameAssignment_0()); 

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
    // $ANTLR end "rule__Parameter__Group__0__Impl"


    // $ANTLR start "rule__Parameter__Group__1"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3117:1: rule__Parameter__Group__1 : rule__Parameter__Group__1__Impl rule__Parameter__Group__2 ;
    public final void rule__Parameter__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3121:1: ( rule__Parameter__Group__1__Impl rule__Parameter__Group__2 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3122:2: rule__Parameter__Group__1__Impl rule__Parameter__Group__2
            {
            pushFollow(FollowSets000.FOLLOW_rule__Parameter__Group__1__Impl_in_rule__Parameter__Group__16172);
            rule__Parameter__Group__1__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Parameter__Group__2_in_rule__Parameter__Group__16175);
            rule__Parameter__Group__2();

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
    // $ANTLR end "rule__Parameter__Group__1"


    // $ANTLR start "rule__Parameter__Group__1__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3129:1: rule__Parameter__Group__1__Impl : ( '=' ) ;
    public final void rule__Parameter__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3133:1: ( ( '=' ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3134:1: ( '=' )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3134:1: ( '=' )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3135:1: '='
            {
             before(grammarAccess.getParameterAccess().getEqualsSignKeyword_1()); 
            match(input,14,FollowSets000.FOLLOW_14_in_rule__Parameter__Group__1__Impl6203); 
             after(grammarAccess.getParameterAccess().getEqualsSignKeyword_1()); 

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
    // $ANTLR end "rule__Parameter__Group__1__Impl"


    // $ANTLR start "rule__Parameter__Group__2"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3148:1: rule__Parameter__Group__2 : rule__Parameter__Group__2__Impl rule__Parameter__Group__3 ;
    public final void rule__Parameter__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3152:1: ( rule__Parameter__Group__2__Impl rule__Parameter__Group__3 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3153:2: rule__Parameter__Group__2__Impl rule__Parameter__Group__3
            {
            pushFollow(FollowSets000.FOLLOW_rule__Parameter__Group__2__Impl_in_rule__Parameter__Group__26234);
            rule__Parameter__Group__2__Impl();

            state._fsp--;

            pushFollow(FollowSets000.FOLLOW_rule__Parameter__Group__3_in_rule__Parameter__Group__26237);
            rule__Parameter__Group__3();

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
    // $ANTLR end "rule__Parameter__Group__2"


    // $ANTLR start "rule__Parameter__Group__2__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3160:1: rule__Parameter__Group__2__Impl : ( ( rule__Parameter__ValueAssignment_2 ) ) ;
    public final void rule__Parameter__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3164:1: ( ( ( rule__Parameter__ValueAssignment_2 ) ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3165:1: ( ( rule__Parameter__ValueAssignment_2 ) )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3165:1: ( ( rule__Parameter__ValueAssignment_2 ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3166:1: ( rule__Parameter__ValueAssignment_2 )
            {
             before(grammarAccess.getParameterAccess().getValueAssignment_2()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3167:1: ( rule__Parameter__ValueAssignment_2 )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3167:2: rule__Parameter__ValueAssignment_2
            {
            pushFollow(FollowSets000.FOLLOW_rule__Parameter__ValueAssignment_2_in_rule__Parameter__Group__2__Impl6264);
            rule__Parameter__ValueAssignment_2();

            state._fsp--;


            }

             after(grammarAccess.getParameterAccess().getValueAssignment_2()); 

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
    // $ANTLR end "rule__Parameter__Group__2__Impl"


    // $ANTLR start "rule__Parameter__Group__3"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3177:1: rule__Parameter__Group__3 : rule__Parameter__Group__3__Impl ;
    public final void rule__Parameter__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3181:1: ( rule__Parameter__Group__3__Impl )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3182:2: rule__Parameter__Group__3__Impl
            {
            pushFollow(FollowSets000.FOLLOW_rule__Parameter__Group__3__Impl_in_rule__Parameter__Group__36294);
            rule__Parameter__Group__3__Impl();

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
    // $ANTLR end "rule__Parameter__Group__3"


    // $ANTLR start "rule__Parameter__Group__3__Impl"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3188:1: rule__Parameter__Group__3__Impl : ( ( rule__Parameter__UserDefinedAssignment_3 )? ) ;
    public final void rule__Parameter__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3192:1: ( ( ( rule__Parameter__UserDefinedAssignment_3 )? ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3193:1: ( ( rule__Parameter__UserDefinedAssignment_3 )? )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3193:1: ( ( rule__Parameter__UserDefinedAssignment_3 )? )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3194:1: ( rule__Parameter__UserDefinedAssignment_3 )?
            {
             before(grammarAccess.getParameterAccess().getUserDefinedAssignment_3()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3195:1: ( rule__Parameter__UserDefinedAssignment_3 )?
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==29) ) {
                alt34=1;
            }
            switch (alt34) {
                case 1 :
                    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3195:2: rule__Parameter__UserDefinedAssignment_3
                    {
                    pushFollow(FollowSets000.FOLLOW_rule__Parameter__UserDefinedAssignment_3_in_rule__Parameter__Group__3__Impl6321);
                    rule__Parameter__UserDefinedAssignment_3();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getParameterAccess().getUserDefinedAssignment_3()); 

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
    // $ANTLR end "rule__Parameter__Group__3__Impl"


    // $ANTLR start "rule__Model__MetamodelVersionAssignment_3"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3214:1: rule__Model__MetamodelVersionAssignment_3 : ( ruleString_Value ) ;
    public final void rule__Model__MetamodelVersionAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3218:1: ( ( ruleString_Value ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3219:1: ( ruleString_Value )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3219:1: ( ruleString_Value )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3220:1: ruleString_Value
            {
             before(grammarAccess.getModelAccess().getMetamodelVersionString_ValueParserRuleCall_3_0()); 
            pushFollow(FollowSets000.FOLLOW_ruleString_Value_in_rule__Model__MetamodelVersionAssignment_36365);
            ruleString_Value();

            state._fsp--;

             after(grammarAccess.getModelAccess().getMetamodelVersionString_ValueParserRuleCall_3_0()); 

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
    // $ANTLR end "rule__Model__MetamodelVersionAssignment_3"


    // $ANTLR start "rule__Model__WidgetAssignment_4"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3229:1: rule__Model__WidgetAssignment_4 : ( ruleWidget ) ;
    public final void rule__Model__WidgetAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3233:1: ( ( ruleWidget ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3234:1: ( ruleWidget )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3234:1: ( ruleWidget )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3235:1: ruleWidget
            {
             before(grammarAccess.getModelAccess().getWidgetWidgetParserRuleCall_4_0()); 
            pushFollow(FollowSets000.FOLLOW_ruleWidget_in_rule__Model__WidgetAssignment_46396);
            ruleWidget();

            state._fsp--;

             after(grammarAccess.getModelAccess().getWidgetWidgetParserRuleCall_4_0()); 

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
    // $ANTLR end "rule__Model__WidgetAssignment_4"


    // $ANTLR start "rule__Widget__LibraryNameAssignment_2_0"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3244:1: rule__Widget__LibraryNameAssignment_2_0 : ( ruleLibraryName ) ;
    public final void rule__Widget__LibraryNameAssignment_2_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3248:1: ( ( ruleLibraryName ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3249:1: ( ruleLibraryName )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3249:1: ( ruleLibraryName )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3250:1: ruleLibraryName
            {
             before(grammarAccess.getWidgetAccess().getLibraryNameLibraryNameParserRuleCall_2_0_0()); 
            pushFollow(FollowSets000.FOLLOW_ruleLibraryName_in_rule__Widget__LibraryNameAssignment_2_06427);
            ruleLibraryName();

            state._fsp--;

             after(grammarAccess.getWidgetAccess().getLibraryNameLibraryNameParserRuleCall_2_0_0()); 

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
    // $ANTLR end "rule__Widget__LibraryNameAssignment_2_0"


    // $ANTLR start "rule__Widget__TypeNameAssignment_3"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3259:1: rule__Widget__TypeNameAssignment_3 : ( RULE_ID ) ;
    public final void rule__Widget__TypeNameAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3263:1: ( ( RULE_ID ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3264:1: ( RULE_ID )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3264:1: ( RULE_ID )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3265:1: RULE_ID
            {
             before(grammarAccess.getWidgetAccess().getTypeNameIDTerminalRuleCall_3_0()); 
            match(input,RULE_ID,FollowSets000.FOLLOW_RULE_ID_in_rule__Widget__TypeNameAssignment_36458); 
             after(grammarAccess.getWidgetAccess().getTypeNameIDTerminalRuleCall_3_0()); 

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
    // $ANTLR end "rule__Widget__TypeNameAssignment_3"


    // $ANTLR start "rule__Widget__LabelsAssignment_5_1"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3274:1: rule__Widget__LabelsAssignment_5_1 : ( ruleTranslation ) ;
    public final void rule__Widget__LabelsAssignment_5_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3278:1: ( ( ruleTranslation ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3279:1: ( ruleTranslation )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3279:1: ( ruleTranslation )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3280:1: ruleTranslation
            {
             before(grammarAccess.getWidgetAccess().getLabelsTranslationParserRuleCall_5_1_0()); 
            pushFollow(FollowSets000.FOLLOW_ruleTranslation_in_rule__Widget__LabelsAssignment_5_16489);
            ruleTranslation();

            state._fsp--;

             after(grammarAccess.getWidgetAccess().getLabelsTranslationParserRuleCall_5_1_0()); 

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
    // $ANTLR end "rule__Widget__LabelsAssignment_5_1"


    // $ANTLR start "rule__Widget__LabelsAssignment_5_2_1"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3289:1: rule__Widget__LabelsAssignment_5_2_1 : ( ruleTranslation ) ;
    public final void rule__Widget__LabelsAssignment_5_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3293:1: ( ( ruleTranslation ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3294:1: ( ruleTranslation )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3294:1: ( ruleTranslation )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3295:1: ruleTranslation
            {
             before(grammarAccess.getWidgetAccess().getLabelsTranslationParserRuleCall_5_2_1_0()); 
            pushFollow(FollowSets000.FOLLOW_ruleTranslation_in_rule__Widget__LabelsAssignment_5_2_16520);
            ruleTranslation();

            state._fsp--;

             after(grammarAccess.getWidgetAccess().getLabelsTranslationParserRuleCall_5_2_1_0()); 

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
    // $ANTLR end "rule__Widget__LabelsAssignment_5_2_1"


    // $ANTLR start "rule__Widget__ToolTipsAssignment_6_1"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3304:1: rule__Widget__ToolTipsAssignment_6_1 : ( ruleTranslation ) ;
    public final void rule__Widget__ToolTipsAssignment_6_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3308:1: ( ( ruleTranslation ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3309:1: ( ruleTranslation )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3309:1: ( ruleTranslation )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3310:1: ruleTranslation
            {
             before(grammarAccess.getWidgetAccess().getToolTipsTranslationParserRuleCall_6_1_0()); 
            pushFollow(FollowSets000.FOLLOW_ruleTranslation_in_rule__Widget__ToolTipsAssignment_6_16551);
            ruleTranslation();

            state._fsp--;

             after(grammarAccess.getWidgetAccess().getToolTipsTranslationParserRuleCall_6_1_0()); 

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
    // $ANTLR end "rule__Widget__ToolTipsAssignment_6_1"


    // $ANTLR start "rule__Widget__ToolTipsAssignment_6_2_1"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3319:1: rule__Widget__ToolTipsAssignment_6_2_1 : ( ruleTranslation ) ;
    public final void rule__Widget__ToolTipsAssignment_6_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3323:1: ( ( ruleTranslation ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3324:1: ( ruleTranslation )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3324:1: ( ruleTranslation )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3325:1: ruleTranslation
            {
             before(grammarAccess.getWidgetAccess().getToolTipsTranslationParserRuleCall_6_2_1_0()); 
            pushFollow(FollowSets000.FOLLOW_ruleTranslation_in_rule__Widget__ToolTipsAssignment_6_2_16582);
            ruleTranslation();

            state._fsp--;

             after(grammarAccess.getWidgetAccess().getToolTipsTranslationParserRuleCall_6_2_1_0()); 

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
    // $ANTLR end "rule__Widget__ToolTipsAssignment_6_2_1"


    // $ANTLR start "rule__Widget__PropertiesAssignment_7"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3334:1: rule__Widget__PropertiesAssignment_7 : ( ruleProperty ) ;
    public final void rule__Widget__PropertiesAssignment_7() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3338:1: ( ( ruleProperty ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3339:1: ( ruleProperty )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3339:1: ( ruleProperty )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3340:1: ruleProperty
            {
             before(grammarAccess.getWidgetAccess().getPropertiesPropertyParserRuleCall_7_0()); 
            pushFollow(FollowSets000.FOLLOW_ruleProperty_in_rule__Widget__PropertiesAssignment_76613);
            ruleProperty();

            state._fsp--;

             after(grammarAccess.getWidgetAccess().getPropertiesPropertyParserRuleCall_7_0()); 

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
    // $ANTLR end "rule__Widget__PropertiesAssignment_7"


    // $ANTLR start "rule__Widget__EventsAssignment_8_1_0"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3349:1: rule__Widget__EventsAssignment_8_1_0 : ( ruleEvent ) ;
    public final void rule__Widget__EventsAssignment_8_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3353:1: ( ( ruleEvent ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3354:1: ( ruleEvent )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3354:1: ( ruleEvent )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3355:1: ruleEvent
            {
             before(grammarAccess.getWidgetAccess().getEventsEventParserRuleCall_8_1_0_0()); 
            pushFollow(FollowSets000.FOLLOW_ruleEvent_in_rule__Widget__EventsAssignment_8_1_06644);
            ruleEvent();

            state._fsp--;

             after(grammarAccess.getWidgetAccess().getEventsEventParserRuleCall_8_1_0_0()); 

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
    // $ANTLR end "rule__Widget__EventsAssignment_8_1_0"


    // $ANTLR start "rule__Widget__EventsAssignment_8_1_1"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3364:1: rule__Widget__EventsAssignment_8_1_1 : ( ruleEvent ) ;
    public final void rule__Widget__EventsAssignment_8_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3368:1: ( ( ruleEvent ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3369:1: ( ruleEvent )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3369:1: ( ruleEvent )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3370:1: ruleEvent
            {
             before(grammarAccess.getWidgetAccess().getEventsEventParserRuleCall_8_1_1_0()); 
            pushFollow(FollowSets000.FOLLOW_ruleEvent_in_rule__Widget__EventsAssignment_8_1_16675);
            ruleEvent();

            state._fsp--;

             after(grammarAccess.getWidgetAccess().getEventsEventParserRuleCall_8_1_1_0()); 

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
    // $ANTLR end "rule__Widget__EventsAssignment_8_1_1"


    // $ANTLR start "rule__Widget__SnippetsAssignment_8_2_0"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3379:1: rule__Widget__SnippetsAssignment_8_2_0 : ( ruleSnippet ) ;
    public final void rule__Widget__SnippetsAssignment_8_2_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3383:1: ( ( ruleSnippet ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3384:1: ( ruleSnippet )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3384:1: ( ruleSnippet )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3385:1: ruleSnippet
            {
             before(grammarAccess.getWidgetAccess().getSnippetsSnippetParserRuleCall_8_2_0_0()); 
            pushFollow(FollowSets000.FOLLOW_ruleSnippet_in_rule__Widget__SnippetsAssignment_8_2_06706);
            ruleSnippet();

            state._fsp--;

             after(grammarAccess.getWidgetAccess().getSnippetsSnippetParserRuleCall_8_2_0_0()); 

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
    // $ANTLR end "rule__Widget__SnippetsAssignment_8_2_0"


    // $ANTLR start "rule__Widget__SnippetsAssignment_8_2_1"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3394:1: rule__Widget__SnippetsAssignment_8_2_1 : ( ruleSnippet ) ;
    public final void rule__Widget__SnippetsAssignment_8_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3398:1: ( ( ruleSnippet ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3399:1: ( ruleSnippet )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3399:1: ( ruleSnippet )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3400:1: ruleSnippet
            {
             before(grammarAccess.getWidgetAccess().getSnippetsSnippetParserRuleCall_8_2_1_0()); 
            pushFollow(FollowSets000.FOLLOW_ruleSnippet_in_rule__Widget__SnippetsAssignment_8_2_16737);
            ruleSnippet();

            state._fsp--;

             after(grammarAccess.getWidgetAccess().getSnippetsSnippetParserRuleCall_8_2_1_0()); 

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
    // $ANTLR end "rule__Widget__SnippetsAssignment_8_2_1"


    // $ANTLR start "rule__Widget__ContentsAssignment_8_3_0"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3409:1: rule__Widget__ContentsAssignment_8_3_0 : ( ruleWidget ) ;
    public final void rule__Widget__ContentsAssignment_8_3_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3413:1: ( ( ruleWidget ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3414:1: ( ruleWidget )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3414:1: ( ruleWidget )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3415:1: ruleWidget
            {
             before(grammarAccess.getWidgetAccess().getContentsWidgetParserRuleCall_8_3_0_0()); 
            pushFollow(FollowSets000.FOLLOW_ruleWidget_in_rule__Widget__ContentsAssignment_8_3_06768);
            ruleWidget();

            state._fsp--;

             after(grammarAccess.getWidgetAccess().getContentsWidgetParserRuleCall_8_3_0_0()); 

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
    // $ANTLR end "rule__Widget__ContentsAssignment_8_3_0"


    // $ANTLR start "rule__Widget__ContentsAssignment_8_3_1"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3424:1: rule__Widget__ContentsAssignment_8_3_1 : ( ruleWidget ) ;
    public final void rule__Widget__ContentsAssignment_8_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3428:1: ( ( ruleWidget ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3429:1: ( ruleWidget )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3429:1: ( ruleWidget )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3430:1: ruleWidget
            {
             before(grammarAccess.getWidgetAccess().getContentsWidgetParserRuleCall_8_3_1_0()); 
            pushFollow(FollowSets000.FOLLOW_ruleWidget_in_rule__Widget__ContentsAssignment_8_3_16799);
            ruleWidget();

            state._fsp--;

             after(grammarAccess.getWidgetAccess().getContentsWidgetParserRuleCall_8_3_1_0()); 

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
    // $ANTLR end "rule__Widget__ContentsAssignment_8_3_1"


    // $ANTLR start "rule__Property__LibraryNameAssignment_1_0"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3439:1: rule__Property__LibraryNameAssignment_1_0 : ( RULE_ID ) ;
    public final void rule__Property__LibraryNameAssignment_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3443:1: ( ( RULE_ID ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3444:1: ( RULE_ID )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3444:1: ( RULE_ID )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3445:1: RULE_ID
            {
             before(grammarAccess.getPropertyAccess().getLibraryNameIDTerminalRuleCall_1_0_0()); 
            match(input,RULE_ID,FollowSets000.FOLLOW_RULE_ID_in_rule__Property__LibraryNameAssignment_1_06830); 
             after(grammarAccess.getPropertyAccess().getLibraryNameIDTerminalRuleCall_1_0_0()); 

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
    // $ANTLR end "rule__Property__LibraryNameAssignment_1_0"


    // $ANTLR start "rule__Property__TypeNameAssignment_2"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3454:1: rule__Property__TypeNameAssignment_2 : ( RULE_ID ) ;
    public final void rule__Property__TypeNameAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3458:1: ( ( RULE_ID ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3459:1: ( RULE_ID )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3459:1: ( RULE_ID )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3460:1: RULE_ID
            {
             before(grammarAccess.getPropertyAccess().getTypeNameIDTerminalRuleCall_2_0()); 
            match(input,RULE_ID,FollowSets000.FOLLOW_RULE_ID_in_rule__Property__TypeNameAssignment_26861); 
             after(grammarAccess.getPropertyAccess().getTypeNameIDTerminalRuleCall_2_0()); 

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
    // $ANTLR end "rule__Property__TypeNameAssignment_2"


    // $ANTLR start "rule__Property__ValueAssignment_4"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3469:1: rule__Property__ValueAssignment_4 : ( ruleString_Value ) ;
    public final void rule__Property__ValueAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3473:1: ( ( ruleString_Value ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3474:1: ( ruleString_Value )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3474:1: ( ruleString_Value )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3475:1: ruleString_Value
            {
             before(grammarAccess.getPropertyAccess().getValueString_ValueParserRuleCall_4_0()); 
            pushFollow(FollowSets000.FOLLOW_ruleString_Value_in_rule__Property__ValueAssignment_46892);
            ruleString_Value();

            state._fsp--;

             after(grammarAccess.getPropertyAccess().getValueString_ValueParserRuleCall_4_0()); 

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
    // $ANTLR end "rule__Property__ValueAssignment_4"


    // $ANTLR start "rule__Property__ReadonlyAssignment_5"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3484:1: rule__Property__ReadonlyAssignment_5 : ( ( '!' ) ) ;
    public final void rule__Property__ReadonlyAssignment_5() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3488:1: ( ( ( '!' ) ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3489:1: ( ( '!' ) )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3489:1: ( ( '!' ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3490:1: ( '!' )
            {
             before(grammarAccess.getPropertyAccess().getReadonlyExclamationMarkKeyword_5_0()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3491:1: ( '!' )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3492:1: '!'
            {
             before(grammarAccess.getPropertyAccess().getReadonlyExclamationMarkKeyword_5_0()); 
            match(input,22,FollowSets000.FOLLOW_22_in_rule__Property__ReadonlyAssignment_56928); 
             after(grammarAccess.getPropertyAccess().getReadonlyExclamationMarkKeyword_5_0()); 

            }

             after(grammarAccess.getPropertyAccess().getReadonlyExclamationMarkKeyword_5_0()); 

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
    // $ANTLR end "rule__Property__ReadonlyAssignment_5"


    // $ANTLR start "rule__Property__ModelAssignment_6_1"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3507:1: rule__Property__ModelAssignment_6_1 : ( ( RULE_URI ) ) ;
    public final void rule__Property__ModelAssignment_6_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3511:1: ( ( ( RULE_URI ) ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3512:1: ( ( RULE_URI ) )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3512:1: ( ( RULE_URI ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3513:1: ( RULE_URI )
            {
             before(grammarAccess.getPropertyAccess().getModelModelCrossReference_6_1_0()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3514:1: ( RULE_URI )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3515:1: RULE_URI
            {
             before(grammarAccess.getPropertyAccess().getModelModelURITerminalRuleCall_6_1_0_1()); 
            match(input,RULE_URI,FollowSets000.FOLLOW_RULE_URI_in_rule__Property__ModelAssignment_6_16971); 
             after(grammarAccess.getPropertyAccess().getModelModelURITerminalRuleCall_6_1_0_1()); 

            }

             after(grammarAccess.getPropertyAccess().getModelModelCrossReference_6_1_0()); 

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
    // $ANTLR end "rule__Property__ModelAssignment_6_1"


    // $ANTLR start "rule__Event__EventNameAssignment_1"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3526:1: rule__Event__EventNameAssignment_1 : ( ruleString_Value ) ;
    public final void rule__Event__EventNameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3530:1: ( ( ruleString_Value ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3531:1: ( ruleString_Value )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3531:1: ( ruleString_Value )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3532:1: ruleString_Value
            {
             before(grammarAccess.getEventAccess().getEventNameString_ValueParserRuleCall_1_0()); 
            pushFollow(FollowSets000.FOLLOW_ruleString_Value_in_rule__Event__EventNameAssignment_17006);
            ruleString_Value();

            state._fsp--;

             after(grammarAccess.getEventAccess().getEventNameString_ValueParserRuleCall_1_0()); 

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
    // $ANTLR end "rule__Event__EventNameAssignment_1"


    // $ANTLR start "rule__Event__FunctionNameAssignment_3"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3541:1: rule__Event__FunctionNameAssignment_3 : ( ruleString_Value ) ;
    public final void rule__Event__FunctionNameAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3545:1: ( ( ruleString_Value ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3546:1: ( ruleString_Value )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3546:1: ( ruleString_Value )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3547:1: ruleString_Value
            {
             before(grammarAccess.getEventAccess().getFunctionNameString_ValueParserRuleCall_3_0()); 
            pushFollow(FollowSets000.FOLLOW_ruleString_Value_in_rule__Event__FunctionNameAssignment_37037);
            ruleString_Value();

            state._fsp--;

             after(grammarAccess.getEventAccess().getFunctionNameString_ValueParserRuleCall_3_0()); 

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
    // $ANTLR end "rule__Event__FunctionNameAssignment_3"


    // $ANTLR start "rule__Event__NatureAssignment_5"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3556:1: rule__Event__NatureAssignment_5 : ( ruleEventNature ) ;
    public final void rule__Event__NatureAssignment_5() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3560:1: ( ( ruleEventNature ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3561:1: ( ruleEventNature )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3561:1: ( ruleEventNature )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3562:1: ruleEventNature
            {
             before(grammarAccess.getEventAccess().getNatureEventNatureEnumRuleCall_5_0()); 
            pushFollow(FollowSets000.FOLLOW_ruleEventNature_in_rule__Event__NatureAssignment_57068);
            ruleEventNature();

            state._fsp--;

             after(grammarAccess.getEventAccess().getNatureEventNatureEnumRuleCall_5_0()); 

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
    // $ANTLR end "rule__Event__NatureAssignment_5"


    // $ANTLR start "rule__Event__DescriptionAssignment_6_1"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3571:1: rule__Event__DescriptionAssignment_6_1 : ( ruleString_Value ) ;
    public final void rule__Event__DescriptionAssignment_6_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3575:1: ( ( ruleString_Value ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3576:1: ( ruleString_Value )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3576:1: ( ruleString_Value )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3577:1: ruleString_Value
            {
             before(grammarAccess.getEventAccess().getDescriptionString_ValueParserRuleCall_6_1_0()); 
            pushFollow(FollowSets000.FOLLOW_ruleString_Value_in_rule__Event__DescriptionAssignment_6_17099);
            ruleString_Value();

            state._fsp--;

             after(grammarAccess.getEventAccess().getDescriptionString_ValueParserRuleCall_6_1_0()); 

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
    // $ANTLR end "rule__Event__DescriptionAssignment_6_1"


    // $ANTLR start "rule__Event__PropertiesAssignment_7"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3586:1: rule__Event__PropertiesAssignment_7 : ( ruleProperty ) ;
    public final void rule__Event__PropertiesAssignment_7() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3590:1: ( ( ruleProperty ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3591:1: ( ruleProperty )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3591:1: ( ruleProperty )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3592:1: ruleProperty
            {
             before(grammarAccess.getEventAccess().getPropertiesPropertyParserRuleCall_7_0()); 
            pushFollow(FollowSets000.FOLLOW_ruleProperty_in_rule__Event__PropertiesAssignment_77130);
            ruleProperty();

            state._fsp--;

             after(grammarAccess.getEventAccess().getPropertiesPropertyParserRuleCall_7_0()); 

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
    // $ANTLR end "rule__Event__PropertiesAssignment_7"


    // $ANTLR start "rule__Event__MessagesAssignment_8_1"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3601:1: rule__Event__MessagesAssignment_8_1 : ( ruleTranslation ) ;
    public final void rule__Event__MessagesAssignment_8_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3605:1: ( ( ruleTranslation ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3606:1: ( ruleTranslation )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3606:1: ( ruleTranslation )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3607:1: ruleTranslation
            {
             before(grammarAccess.getEventAccess().getMessagesTranslationParserRuleCall_8_1_0()); 
            pushFollow(FollowSets000.FOLLOW_ruleTranslation_in_rule__Event__MessagesAssignment_8_17161);
            ruleTranslation();

            state._fsp--;

             after(grammarAccess.getEventAccess().getMessagesTranslationParserRuleCall_8_1_0()); 

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
    // $ANTLR end "rule__Event__MessagesAssignment_8_1"


    // $ANTLR start "rule__Event__MessagesAssignment_8_2_1"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3616:1: rule__Event__MessagesAssignment_8_2_1 : ( ruleTranslation ) ;
    public final void rule__Event__MessagesAssignment_8_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3620:1: ( ( ruleTranslation ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3621:1: ( ruleTranslation )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3621:1: ( ruleTranslation )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3622:1: ruleTranslation
            {
             before(grammarAccess.getEventAccess().getMessagesTranslationParserRuleCall_8_2_1_0()); 
            pushFollow(FollowSets000.FOLLOW_ruleTranslation_in_rule__Event__MessagesAssignment_8_2_17192);
            ruleTranslation();

            state._fsp--;

             after(grammarAccess.getEventAccess().getMessagesTranslationParserRuleCall_8_2_1_0()); 

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
    // $ANTLR end "rule__Event__MessagesAssignment_8_2_1"


    // $ANTLR start "rule__Event__ParametersAssignment_9_1_0"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3631:1: rule__Event__ParametersAssignment_9_1_0 : ( ruleParameter ) ;
    public final void rule__Event__ParametersAssignment_9_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3635:1: ( ( ruleParameter ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3636:1: ( ruleParameter )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3636:1: ( ruleParameter )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3637:1: ruleParameter
            {
             before(grammarAccess.getEventAccess().getParametersParameterParserRuleCall_9_1_0_0()); 
            pushFollow(FollowSets000.FOLLOW_ruleParameter_in_rule__Event__ParametersAssignment_9_1_07223);
            ruleParameter();

            state._fsp--;

             after(grammarAccess.getEventAccess().getParametersParameterParserRuleCall_9_1_0_0()); 

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
    // $ANTLR end "rule__Event__ParametersAssignment_9_1_0"


    // $ANTLR start "rule__Event__ParametersAssignment_9_1_1"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3646:1: rule__Event__ParametersAssignment_9_1_1 : ( ruleParameter ) ;
    public final void rule__Event__ParametersAssignment_9_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3650:1: ( ( ruleParameter ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3651:1: ( ruleParameter )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3651:1: ( ruleParameter )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3652:1: ruleParameter
            {
             before(grammarAccess.getEventAccess().getParametersParameterParserRuleCall_9_1_1_0()); 
            pushFollow(FollowSets000.FOLLOW_ruleParameter_in_rule__Event__ParametersAssignment_9_1_17254);
            ruleParameter();

            state._fsp--;

             after(grammarAccess.getEventAccess().getParametersParameterParserRuleCall_9_1_1_0()); 

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
    // $ANTLR end "rule__Event__ParametersAssignment_9_1_1"


    // $ANTLR start "rule__Event__SnippetsAssignment_9_2_0"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3661:1: rule__Event__SnippetsAssignment_9_2_0 : ( ruleSnippet ) ;
    public final void rule__Event__SnippetsAssignment_9_2_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3665:1: ( ( ruleSnippet ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3666:1: ( ruleSnippet )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3666:1: ( ruleSnippet )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3667:1: ruleSnippet
            {
             before(grammarAccess.getEventAccess().getSnippetsSnippetParserRuleCall_9_2_0_0()); 
            pushFollow(FollowSets000.FOLLOW_ruleSnippet_in_rule__Event__SnippetsAssignment_9_2_07285);
            ruleSnippet();

            state._fsp--;

             after(grammarAccess.getEventAccess().getSnippetsSnippetParserRuleCall_9_2_0_0()); 

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
    // $ANTLR end "rule__Event__SnippetsAssignment_9_2_0"


    // $ANTLR start "rule__Event__SnippetsAssignment_9_2_1"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3676:1: rule__Event__SnippetsAssignment_9_2_1 : ( ruleSnippet ) ;
    public final void rule__Event__SnippetsAssignment_9_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3680:1: ( ( ruleSnippet ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3681:1: ( ruleSnippet )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3681:1: ( ruleSnippet )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3682:1: ruleSnippet
            {
             before(grammarAccess.getEventAccess().getSnippetsSnippetParserRuleCall_9_2_1_0()); 
            pushFollow(FollowSets000.FOLLOW_ruleSnippet_in_rule__Event__SnippetsAssignment_9_2_17316);
            ruleSnippet();

            state._fsp--;

             after(grammarAccess.getEventAccess().getSnippetsSnippetParserRuleCall_9_2_1_0()); 

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
    // $ANTLR end "rule__Event__SnippetsAssignment_9_2_1"


    // $ANTLR start "rule__Snippet__TypeNameAssignment_2"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3691:1: rule__Snippet__TypeNameAssignment_2 : ( ruleString_Value ) ;
    public final void rule__Snippet__TypeNameAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3695:1: ( ( ruleString_Value ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3696:1: ( ruleString_Value )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3696:1: ( ruleString_Value )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3697:1: ruleString_Value
            {
             before(grammarAccess.getSnippetAccess().getTypeNameString_ValueParserRuleCall_2_0()); 
            pushFollow(FollowSets000.FOLLOW_ruleString_Value_in_rule__Snippet__TypeNameAssignment_27347);
            ruleString_Value();

            state._fsp--;

             after(grammarAccess.getSnippetAccess().getTypeNameString_ValueParserRuleCall_2_0()); 

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
    // $ANTLR end "rule__Snippet__TypeNameAssignment_2"


    // $ANTLR start "rule__Snippet__PropertiesAssignment_3"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3706:1: rule__Snippet__PropertiesAssignment_3 : ( ruleProperty ) ;
    public final void rule__Snippet__PropertiesAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3710:1: ( ( ruleProperty ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3711:1: ( ruleProperty )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3711:1: ( ruleProperty )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3712:1: ruleProperty
            {
             before(grammarAccess.getSnippetAccess().getPropertiesPropertyParserRuleCall_3_0()); 
            pushFollow(FollowSets000.FOLLOW_ruleProperty_in_rule__Snippet__PropertiesAssignment_37378);
            ruleProperty();

            state._fsp--;

             after(grammarAccess.getSnippetAccess().getPropertiesPropertyParserRuleCall_3_0()); 

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
    // $ANTLR end "rule__Snippet__PropertiesAssignment_3"


    // $ANTLR start "rule__Snippet__ContentsAssignment_4_1_0"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3721:1: rule__Snippet__ContentsAssignment_4_1_0 : ( ruleSnippet ) ;
    public final void rule__Snippet__ContentsAssignment_4_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3725:1: ( ( ruleSnippet ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3726:1: ( ruleSnippet )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3726:1: ( ruleSnippet )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3727:1: ruleSnippet
            {
             before(grammarAccess.getSnippetAccess().getContentsSnippetParserRuleCall_4_1_0_0()); 
            pushFollow(FollowSets000.FOLLOW_ruleSnippet_in_rule__Snippet__ContentsAssignment_4_1_07409);
            ruleSnippet();

            state._fsp--;

             after(grammarAccess.getSnippetAccess().getContentsSnippetParserRuleCall_4_1_0_0()); 

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
    // $ANTLR end "rule__Snippet__ContentsAssignment_4_1_0"


    // $ANTLR start "rule__Snippet__ContentsAssignment_4_1_1"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3736:1: rule__Snippet__ContentsAssignment_4_1_1 : ( ruleSnippet ) ;
    public final void rule__Snippet__ContentsAssignment_4_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3740:1: ( ( ruleSnippet ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3741:1: ( ruleSnippet )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3741:1: ( ruleSnippet )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3742:1: ruleSnippet
            {
             before(grammarAccess.getSnippetAccess().getContentsSnippetParserRuleCall_4_1_1_0()); 
            pushFollow(FollowSets000.FOLLOW_ruleSnippet_in_rule__Snippet__ContentsAssignment_4_1_17440);
            ruleSnippet();

            state._fsp--;

             after(grammarAccess.getSnippetAccess().getContentsSnippetParserRuleCall_4_1_1_0()); 

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
    // $ANTLR end "rule__Snippet__ContentsAssignment_4_1_1"


    // $ANTLR start "rule__Translation__LanguageAssignment_0"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3751:1: rule__Translation__LanguageAssignment_0 : ( RULE_ID ) ;
    public final void rule__Translation__LanguageAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3755:1: ( ( RULE_ID ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3756:1: ( RULE_ID )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3756:1: ( RULE_ID )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3757:1: RULE_ID
            {
             before(grammarAccess.getTranslationAccess().getLanguageIDTerminalRuleCall_0_0()); 
            match(input,RULE_ID,FollowSets000.FOLLOW_RULE_ID_in_rule__Translation__LanguageAssignment_07471); 
             after(grammarAccess.getTranslationAccess().getLanguageIDTerminalRuleCall_0_0()); 

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
    // $ANTLR end "rule__Translation__LanguageAssignment_0"


    // $ANTLR start "rule__Translation__MessageAssignment_2"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3766:1: rule__Translation__MessageAssignment_2 : ( ruleString_Value ) ;
    public final void rule__Translation__MessageAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3770:1: ( ( ruleString_Value ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3771:1: ( ruleString_Value )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3771:1: ( ruleString_Value )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3772:1: ruleString_Value
            {
             before(grammarAccess.getTranslationAccess().getMessageString_ValueParserRuleCall_2_0()); 
            pushFollow(FollowSets000.FOLLOW_ruleString_Value_in_rule__Translation__MessageAssignment_27502);
            ruleString_Value();

            state._fsp--;

             after(grammarAccess.getTranslationAccess().getMessageString_ValueParserRuleCall_2_0()); 

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
    // $ANTLR end "rule__Translation__MessageAssignment_2"


    // $ANTLR start "rule__Parameter__NameAssignment_0"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3781:1: rule__Parameter__NameAssignment_0 : ( RULE_ID ) ;
    public final void rule__Parameter__NameAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3785:1: ( ( RULE_ID ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3786:1: ( RULE_ID )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3786:1: ( RULE_ID )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3787:1: RULE_ID
            {
             before(grammarAccess.getParameterAccess().getNameIDTerminalRuleCall_0_0()); 
            match(input,RULE_ID,FollowSets000.FOLLOW_RULE_ID_in_rule__Parameter__NameAssignment_07533); 
             after(grammarAccess.getParameterAccess().getNameIDTerminalRuleCall_0_0()); 

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
    // $ANTLR end "rule__Parameter__NameAssignment_0"


    // $ANTLR start "rule__Parameter__ValueAssignment_2"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3796:1: rule__Parameter__ValueAssignment_2 : ( ruleString_Value ) ;
    public final void rule__Parameter__ValueAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3800:1: ( ( ruleString_Value ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3801:1: ( ruleString_Value )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3801:1: ( ruleString_Value )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3802:1: ruleString_Value
            {
             before(grammarAccess.getParameterAccess().getValueString_ValueParserRuleCall_2_0()); 
            pushFollow(FollowSets000.FOLLOW_ruleString_Value_in_rule__Parameter__ValueAssignment_27564);
            ruleString_Value();

            state._fsp--;

             after(grammarAccess.getParameterAccess().getValueString_ValueParserRuleCall_2_0()); 

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
    // $ANTLR end "rule__Parameter__ValueAssignment_2"


    // $ANTLR start "rule__Parameter__UserDefinedAssignment_3"
    // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3811:1: rule__Parameter__UserDefinedAssignment_3 : ( ( 'ud' ) ) ;
    public final void rule__Parameter__UserDefinedAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3815:1: ( ( ( 'ud' ) ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3816:1: ( ( 'ud' ) )
            {
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3816:1: ( ( 'ud' ) )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3817:1: ( 'ud' )
            {
             before(grammarAccess.getParameterAccess().getUserDefinedUdKeyword_3_0()); 
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3818:1: ( 'ud' )
            // ../../ui/com.odcgroup.page.model.dsl.ui/src-gen/com/odcgroup/page/ui/contentassist/antlr/internal/InternalPage.g:3819:1: 'ud'
            {
             before(grammarAccess.getParameterAccess().getUserDefinedUdKeyword_3_0()); 
            match(input,29,FollowSets000.FOLLOW_29_in_rule__Parameter__UserDefinedAssignment_37600); 
             after(grammarAccess.getParameterAccess().getUserDefinedUdKeyword_3_0()); 

            }

             after(grammarAccess.getParameterAccess().getUserDefinedUdKeyword_3_0()); 

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
    // $ANTLR end "rule__Parameter__UserDefinedAssignment_3"

    // Delegated rules


 


    private static class FollowSets000 {
        public static final BitSet FOLLOW_ruleModel_in_entryRuleModel61 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleModel68 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Model__Group__0_in_ruleModel94 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleWidget_in_entryRuleWidget121 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleWidget128 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Widget__Group__0_in_ruleWidget154 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleProperty_in_entryRuleProperty183 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleProperty190 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Property__Group__0_in_ruleProperty216 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleEvent_in_entryRuleEvent243 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleEvent250 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Event__Group__0_in_ruleEvent276 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleSnippet_in_entryRuleSnippet303 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleSnippet310 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Snippet__Group__0_in_ruleSnippet336 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleTranslation_in_entryRuleTranslation363 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleTranslation370 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Translation__Group__0_in_ruleTranslation396 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleParameter_in_entryRuleParameter423 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleParameter430 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Parameter__Group__0_in_ruleParameter456 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleLibraryName_in_entryRuleLibraryName483 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleLibraryName490 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_RULE_ID_in_ruleLibraryName516 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleString_Value_in_entryRuleString_Value542 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleString_Value549 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__String_Value__Alternatives_in_ruleString_Value575 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__EventNature__Alternatives_in_ruleEventNature612 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_RULE_STRING_in_rule__String_Value__Alternatives647 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_RULE_ID_in_rule__String_Value__Alternatives664 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_RULE_VALUE_in_rule__String_Value__Alternatives681 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_11_in_rule__EventNature__Alternatives714 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_12_in_rule__EventNature__Alternatives735 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Model__Group__0__Impl_in_rule__Model__Group__0768 = new BitSet(new long[]{0x0000000000002000L});
        public static final BitSet FOLLOW_rule__Model__Group__1_in_rule__Model__Group__0771 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Model__Group__1__Impl_in_rule__Model__Group__1829 = new BitSet(new long[]{0x0000000000004000L});
        public static final BitSet FOLLOW_rule__Model__Group__2_in_rule__Model__Group__1832 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_13_in_rule__Model__Group__1__Impl860 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Model__Group__2__Impl_in_rule__Model__Group__2891 = new BitSet(new long[]{0x0000000000000070L});
        public static final BitSet FOLLOW_rule__Model__Group__3_in_rule__Model__Group__2894 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_14_in_rule__Model__Group__2__Impl922 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Model__Group__3__Impl_in_rule__Model__Group__3953 = new BitSet(new long[]{0x0000000000008000L});
        public static final BitSet FOLLOW_rule__Model__Group__4_in_rule__Model__Group__3956 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Model__MetamodelVersionAssignment_3_in_rule__Model__Group__3__Impl983 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Model__Group__4__Impl_in_rule__Model__Group__41013 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Model__WidgetAssignment_4_in_rule__Model__Group__4__Impl1040 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Widget__Group__0__Impl_in_rule__Widget__Group__01080 = new BitSet(new long[]{0x0000000000008000L});
        public static final BitSet FOLLOW_rule__Widget__Group__1_in_rule__Widget__Group__01083 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Widget__Group__1__Impl_in_rule__Widget__Group__11141 = new BitSet(new long[]{0x0000000000000010L});
        public static final BitSet FOLLOW_rule__Widget__Group__2_in_rule__Widget__Group__11144 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_15_in_rule__Widget__Group__1__Impl1172 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Widget__Group__2__Impl_in_rule__Widget__Group__21203 = new BitSet(new long[]{0x0000000000000010L});
        public static final BitSet FOLLOW_rule__Widget__Group__3_in_rule__Widget__Group__21206 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Widget__Group_2__0_in_rule__Widget__Group__2__Impl1233 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Widget__Group__3__Impl_in_rule__Widget__Group__31264 = new BitSet(new long[]{0x0000000000008000L});
        public static final BitSet FOLLOW_rule__Widget__Group__4_in_rule__Widget__Group__31267 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Widget__TypeNameAssignment_3_in_rule__Widget__Group__3__Impl1294 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Widget__Group__4__Impl_in_rule__Widget__Group__41324 = new BitSet(new long[]{0x00000000001A0010L});
        public static final BitSet FOLLOW_rule__Widget__Group__5_in_rule__Widget__Group__41327 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_15_in_rule__Widget__Group__4__Impl1355 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Widget__Group__5__Impl_in_rule__Widget__Group__51386 = new BitSet(new long[]{0x00000000001A0010L});
        public static final BitSet FOLLOW_rule__Widget__Group__6_in_rule__Widget__Group__51389 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Widget__Group_5__0_in_rule__Widget__Group__5__Impl1416 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Widget__Group__6__Impl_in_rule__Widget__Group__61447 = new BitSet(new long[]{0x00000000001A0010L});
        public static final BitSet FOLLOW_rule__Widget__Group__7_in_rule__Widget__Group__61450 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Widget__Group_6__0_in_rule__Widget__Group__6__Impl1477 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Widget__Group__7__Impl_in_rule__Widget__Group__71508 = new BitSet(new long[]{0x00000000001A0010L});
        public static final BitSet FOLLOW_rule__Widget__Group__8_in_rule__Widget__Group__71511 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Widget__PropertiesAssignment_7_in_rule__Widget__Group__7__Impl1538 = new BitSet(new long[]{0x0000000000000012L});
        public static final BitSet FOLLOW_rule__Widget__Group__8__Impl_in_rule__Widget__Group__81569 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Widget__Group_8__0_in_rule__Widget__Group__8__Impl1596 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Widget__Group_2__0__Impl_in_rule__Widget__Group_2__01645 = new BitSet(new long[]{0x0000000000010000L});
        public static final BitSet FOLLOW_rule__Widget__Group_2__1_in_rule__Widget__Group_2__01648 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Widget__LibraryNameAssignment_2_0_in_rule__Widget__Group_2__0__Impl1675 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Widget__Group_2__1__Impl_in_rule__Widget__Group_2__11705 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_16_in_rule__Widget__Group_2__1__Impl1733 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Widget__Group_5__0__Impl_in_rule__Widget__Group_5__01768 = new BitSet(new long[]{0x0000000000000010L});
        public static final BitSet FOLLOW_rule__Widget__Group_5__1_in_rule__Widget__Group_5__01771 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_17_in_rule__Widget__Group_5__0__Impl1799 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Widget__Group_5__1__Impl_in_rule__Widget__Group_5__11830 = new BitSet(new long[]{0x0000000000040000L});
        public static final BitSet FOLLOW_rule__Widget__Group_5__2_in_rule__Widget__Group_5__11833 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Widget__LabelsAssignment_5_1_in_rule__Widget__Group_5__1__Impl1860 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Widget__Group_5__2__Impl_in_rule__Widget__Group_5__21890 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Widget__Group_5_2__0_in_rule__Widget__Group_5__2__Impl1917 = new BitSet(new long[]{0x0000000000040002L});
        public static final BitSet FOLLOW_rule__Widget__Group_5_2__0__Impl_in_rule__Widget__Group_5_2__01954 = new BitSet(new long[]{0x0000000000000010L});
        public static final BitSet FOLLOW_rule__Widget__Group_5_2__1_in_rule__Widget__Group_5_2__01957 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_18_in_rule__Widget__Group_5_2__0__Impl1985 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Widget__Group_5_2__1__Impl_in_rule__Widget__Group_5_2__12016 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Widget__LabelsAssignment_5_2_1_in_rule__Widget__Group_5_2__1__Impl2043 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Widget__Group_6__0__Impl_in_rule__Widget__Group_6__02077 = new BitSet(new long[]{0x0000000000000010L});
        public static final BitSet FOLLOW_rule__Widget__Group_6__1_in_rule__Widget__Group_6__02080 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_19_in_rule__Widget__Group_6__0__Impl2108 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Widget__Group_6__1__Impl_in_rule__Widget__Group_6__12139 = new BitSet(new long[]{0x0000000000040000L});
        public static final BitSet FOLLOW_rule__Widget__Group_6__2_in_rule__Widget__Group_6__12142 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Widget__ToolTipsAssignment_6_1_in_rule__Widget__Group_6__1__Impl2169 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Widget__Group_6__2__Impl_in_rule__Widget__Group_6__22199 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Widget__Group_6_2__0_in_rule__Widget__Group_6__2__Impl2226 = new BitSet(new long[]{0x0000000000040002L});
        public static final BitSet FOLLOW_rule__Widget__Group_6_2__0__Impl_in_rule__Widget__Group_6_2__02263 = new BitSet(new long[]{0x0000000000000010L});
        public static final BitSet FOLLOW_rule__Widget__Group_6_2__1_in_rule__Widget__Group_6_2__02266 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_18_in_rule__Widget__Group_6_2__0__Impl2294 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Widget__Group_6_2__1__Impl_in_rule__Widget__Group_6_2__12325 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Widget__ToolTipsAssignment_6_2_1_in_rule__Widget__Group_6_2__1__Impl2352 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Widget__Group_8__0__Impl_in_rule__Widget__Group_8__02386 = new BitSet(new long[]{0x0000000010A08000L});
        public static final BitSet FOLLOW_rule__Widget__Group_8__1_in_rule__Widget__Group_8__02389 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_20_in_rule__Widget__Group_8__0__Impl2417 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Widget__Group_8__1__Impl_in_rule__Widget__Group_8__12448 = new BitSet(new long[]{0x0000000010A08000L});
        public static final BitSet FOLLOW_rule__Widget__Group_8__2_in_rule__Widget__Group_8__12451 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Widget__Group_8_1__0_in_rule__Widget__Group_8__1__Impl2478 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Widget__Group_8__2__Impl_in_rule__Widget__Group_8__22509 = new BitSet(new long[]{0x0000000010A08000L});
        public static final BitSet FOLLOW_rule__Widget__Group_8__3_in_rule__Widget__Group_8__22512 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Widget__Group_8_2__0_in_rule__Widget__Group_8__2__Impl2539 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Widget__Group_8__3__Impl_in_rule__Widget__Group_8__32570 = new BitSet(new long[]{0x0000000010A08000L});
        public static final BitSet FOLLOW_rule__Widget__Group_8__4_in_rule__Widget__Group_8__32573 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Widget__Group_8_3__0_in_rule__Widget__Group_8__3__Impl2600 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Widget__Group_8__4__Impl_in_rule__Widget__Group_8__42631 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_21_in_rule__Widget__Group_8__4__Impl2659 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Widget__Group_8_1__0__Impl_in_rule__Widget__Group_8_1__02700 = new BitSet(new long[]{0x0000000000800000L});
        public static final BitSet FOLLOW_rule__Widget__Group_8_1__1_in_rule__Widget__Group_8_1__02703 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Widget__EventsAssignment_8_1_0_in_rule__Widget__Group_8_1__0__Impl2730 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Widget__Group_8_1__1__Impl_in_rule__Widget__Group_8_1__12760 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Widget__EventsAssignment_8_1_1_in_rule__Widget__Group_8_1__1__Impl2787 = new BitSet(new long[]{0x0000000000800002L});
        public static final BitSet FOLLOW_rule__Widget__Group_8_2__0__Impl_in_rule__Widget__Group_8_2__02822 = new BitSet(new long[]{0x0000000010000000L});
        public static final BitSet FOLLOW_rule__Widget__Group_8_2__1_in_rule__Widget__Group_8_2__02825 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Widget__SnippetsAssignment_8_2_0_in_rule__Widget__Group_8_2__0__Impl2852 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Widget__Group_8_2__1__Impl_in_rule__Widget__Group_8_2__12882 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Widget__SnippetsAssignment_8_2_1_in_rule__Widget__Group_8_2__1__Impl2909 = new BitSet(new long[]{0x0000000010000002L});
        public static final BitSet FOLLOW_rule__Widget__Group_8_3__0__Impl_in_rule__Widget__Group_8_3__02944 = new BitSet(new long[]{0x0000000000008000L});
        public static final BitSet FOLLOW_rule__Widget__Group_8_3__1_in_rule__Widget__Group_8_3__02947 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Widget__ContentsAssignment_8_3_0_in_rule__Widget__Group_8_3__0__Impl2974 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Widget__Group_8_3__1__Impl_in_rule__Widget__Group_8_3__13004 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Widget__ContentsAssignment_8_3_1_in_rule__Widget__Group_8_3__1__Impl3031 = new BitSet(new long[]{0x0000000000008002L});
        public static final BitSet FOLLOW_rule__Property__Group__0__Impl_in_rule__Property__Group__03066 = new BitSet(new long[]{0x0000000000000010L});
        public static final BitSet FOLLOW_rule__Property__Group__1_in_rule__Property__Group__03069 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Property__Group__1__Impl_in_rule__Property__Group__13127 = new BitSet(new long[]{0x0000000000000010L});
        public static final BitSet FOLLOW_rule__Property__Group__2_in_rule__Property__Group__13130 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Property__Group_1__0_in_rule__Property__Group__1__Impl3157 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Property__Group__2__Impl_in_rule__Property__Group__23188 = new BitSet(new long[]{0x0000000000004000L});
        public static final BitSet FOLLOW_rule__Property__Group__3_in_rule__Property__Group__23191 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Property__TypeNameAssignment_2_in_rule__Property__Group__2__Impl3218 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Property__Group__3__Impl_in_rule__Property__Group__33248 = new BitSet(new long[]{0x0000000000000070L});
        public static final BitSet FOLLOW_rule__Property__Group__4_in_rule__Property__Group__33251 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_14_in_rule__Property__Group__3__Impl3279 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Property__Group__4__Impl_in_rule__Property__Group__43310 = new BitSet(new long[]{0x0000000000400000L});
        public static final BitSet FOLLOW_rule__Property__Group__5_in_rule__Property__Group__43313 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Property__ValueAssignment_4_in_rule__Property__Group__4__Impl3340 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Property__Group__5__Impl_in_rule__Property__Group__53370 = new BitSet(new long[]{0x0000000000400000L});
        public static final BitSet FOLLOW_rule__Property__Group__6_in_rule__Property__Group__53373 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Property__ReadonlyAssignment_5_in_rule__Property__Group__5__Impl3400 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Property__Group__6__Impl_in_rule__Property__Group__63431 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Property__Group_6__0_in_rule__Property__Group__6__Impl3458 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Property__Group_1__0__Impl_in_rule__Property__Group_1__03503 = new BitSet(new long[]{0x0000000000010000L});
        public static final BitSet FOLLOW_rule__Property__Group_1__1_in_rule__Property__Group_1__03506 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Property__LibraryNameAssignment_1_0_in_rule__Property__Group_1__0__Impl3533 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Property__Group_1__1__Impl_in_rule__Property__Group_1__13563 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_16_in_rule__Property__Group_1__1__Impl3591 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Property__Group_6__0__Impl_in_rule__Property__Group_6__03626 = new BitSet(new long[]{0x0000000000000080L});
        public static final BitSet FOLLOW_rule__Property__Group_6__1_in_rule__Property__Group_6__03629 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_22_in_rule__Property__Group_6__0__Impl3657 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Property__Group_6__1__Impl_in_rule__Property__Group_6__13688 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Property__ModelAssignment_6_1_in_rule__Property__Group_6__1__Impl3715 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Event__Group__0__Impl_in_rule__Event__Group__03749 = new BitSet(new long[]{0x0000000000000070L});
        public static final BitSet FOLLOW_rule__Event__Group__1_in_rule__Event__Group__03752 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_23_in_rule__Event__Group__0__Impl3780 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Event__Group__1__Impl_in_rule__Event__Group__13811 = new BitSet(new long[]{0x0000000001000000L});
        public static final BitSet FOLLOW_rule__Event__Group__2_in_rule__Event__Group__13814 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Event__EventNameAssignment_1_in_rule__Event__Group__1__Impl3841 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Event__Group__2__Impl_in_rule__Event__Group__23871 = new BitSet(new long[]{0x0000000000000070L});
        public static final BitSet FOLLOW_rule__Event__Group__3_in_rule__Event__Group__23874 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_24_in_rule__Event__Group__2__Impl3902 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Event__Group__3__Impl_in_rule__Event__Group__33933 = new BitSet(new long[]{0x0000000002000000L});
        public static final BitSet FOLLOW_rule__Event__Group__4_in_rule__Event__Group__33936 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Event__FunctionNameAssignment_3_in_rule__Event__Group__3__Impl3963 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Event__Group__4__Impl_in_rule__Event__Group__43993 = new BitSet(new long[]{0x000000000C101810L});
        public static final BitSet FOLLOW_rule__Event__Group__5_in_rule__Event__Group__43996 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_25_in_rule__Event__Group__4__Impl4024 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Event__Group__5__Impl_in_rule__Event__Group__54055 = new BitSet(new long[]{0x000000000C101810L});
        public static final BitSet FOLLOW_rule__Event__Group__6_in_rule__Event__Group__54058 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Event__NatureAssignment_5_in_rule__Event__Group__5__Impl4085 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Event__Group__6__Impl_in_rule__Event__Group__64116 = new BitSet(new long[]{0x000000000C101810L});
        public static final BitSet FOLLOW_rule__Event__Group__7_in_rule__Event__Group__64119 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Event__Group_6__0_in_rule__Event__Group__6__Impl4146 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Event__Group__7__Impl_in_rule__Event__Group__74177 = new BitSet(new long[]{0x000000000C101810L});
        public static final BitSet FOLLOW_rule__Event__Group__8_in_rule__Event__Group__74180 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Event__PropertiesAssignment_7_in_rule__Event__Group__7__Impl4207 = new BitSet(new long[]{0x0000000000000012L});
        public static final BitSet FOLLOW_rule__Event__Group__8__Impl_in_rule__Event__Group__84238 = new BitSet(new long[]{0x000000000C101810L});
        public static final BitSet FOLLOW_rule__Event__Group__9_in_rule__Event__Group__84241 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Event__Group_8__0_in_rule__Event__Group__8__Impl4268 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Event__Group__9__Impl_in_rule__Event__Group__94299 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Event__Group_9__0_in_rule__Event__Group__9__Impl4326 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Event__Group_6__0__Impl_in_rule__Event__Group_6__04377 = new BitSet(new long[]{0x0000000000000070L});
        public static final BitSet FOLLOW_rule__Event__Group_6__1_in_rule__Event__Group_6__04380 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_26_in_rule__Event__Group_6__0__Impl4408 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Event__Group_6__1__Impl_in_rule__Event__Group_6__14439 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Event__DescriptionAssignment_6_1_in_rule__Event__Group_6__1__Impl4466 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Event__Group_8__0__Impl_in_rule__Event__Group_8__04500 = new BitSet(new long[]{0x0000000000000010L});
        public static final BitSet FOLLOW_rule__Event__Group_8__1_in_rule__Event__Group_8__04503 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_27_in_rule__Event__Group_8__0__Impl4531 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Event__Group_8__1__Impl_in_rule__Event__Group_8__14562 = new BitSet(new long[]{0x0000000000040000L});
        public static final BitSet FOLLOW_rule__Event__Group_8__2_in_rule__Event__Group_8__14565 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Event__MessagesAssignment_8_1_in_rule__Event__Group_8__1__Impl4592 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Event__Group_8__2__Impl_in_rule__Event__Group_8__24622 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Event__Group_8_2__0_in_rule__Event__Group_8__2__Impl4649 = new BitSet(new long[]{0x0000000000040002L});
        public static final BitSet FOLLOW_rule__Event__Group_8_2__0__Impl_in_rule__Event__Group_8_2__04686 = new BitSet(new long[]{0x0000000000000010L});
        public static final BitSet FOLLOW_rule__Event__Group_8_2__1_in_rule__Event__Group_8_2__04689 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_18_in_rule__Event__Group_8_2__0__Impl4717 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Event__Group_8_2__1__Impl_in_rule__Event__Group_8_2__14748 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Event__MessagesAssignment_8_2_1_in_rule__Event__Group_8_2__1__Impl4775 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Event__Group_9__0__Impl_in_rule__Event__Group_9__04809 = new BitSet(new long[]{0x0000000010200010L});
        public static final BitSet FOLLOW_rule__Event__Group_9__1_in_rule__Event__Group_9__04812 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_20_in_rule__Event__Group_9__0__Impl4840 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Event__Group_9__1__Impl_in_rule__Event__Group_9__14871 = new BitSet(new long[]{0x0000000010200010L});
        public static final BitSet FOLLOW_rule__Event__Group_9__2_in_rule__Event__Group_9__14874 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Event__Group_9_1__0_in_rule__Event__Group_9__1__Impl4901 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Event__Group_9__2__Impl_in_rule__Event__Group_9__24932 = new BitSet(new long[]{0x0000000010200010L});
        public static final BitSet FOLLOW_rule__Event__Group_9__3_in_rule__Event__Group_9__24935 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Event__Group_9_2__0_in_rule__Event__Group_9__2__Impl4962 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Event__Group_9__3__Impl_in_rule__Event__Group_9__34993 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_21_in_rule__Event__Group_9__3__Impl5021 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Event__Group_9_1__0__Impl_in_rule__Event__Group_9_1__05060 = new BitSet(new long[]{0x0000000000000010L});
        public static final BitSet FOLLOW_rule__Event__Group_9_1__1_in_rule__Event__Group_9_1__05063 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Event__ParametersAssignment_9_1_0_in_rule__Event__Group_9_1__0__Impl5090 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Event__Group_9_1__1__Impl_in_rule__Event__Group_9_1__15120 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Event__ParametersAssignment_9_1_1_in_rule__Event__Group_9_1__1__Impl5147 = new BitSet(new long[]{0x0000000000000012L});
        public static final BitSet FOLLOW_rule__Event__Group_9_2__0__Impl_in_rule__Event__Group_9_2__05182 = new BitSet(new long[]{0x0000000010000000L});
        public static final BitSet FOLLOW_rule__Event__Group_9_2__1_in_rule__Event__Group_9_2__05185 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Event__SnippetsAssignment_9_2_0_in_rule__Event__Group_9_2__0__Impl5212 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Event__Group_9_2__1__Impl_in_rule__Event__Group_9_2__15242 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Event__SnippetsAssignment_9_2_1_in_rule__Event__Group_9_2__1__Impl5269 = new BitSet(new long[]{0x0000000010000002L});
        public static final BitSet FOLLOW_rule__Snippet__Group__0__Impl_in_rule__Snippet__Group__05304 = new BitSet(new long[]{0x0000000010000000L});
        public static final BitSet FOLLOW_rule__Snippet__Group__1_in_rule__Snippet__Group__05307 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Snippet__Group__1__Impl_in_rule__Snippet__Group__15365 = new BitSet(new long[]{0x0000000000100070L});
        public static final BitSet FOLLOW_rule__Snippet__Group__2_in_rule__Snippet__Group__15368 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_28_in_rule__Snippet__Group__1__Impl5396 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Snippet__Group__2__Impl_in_rule__Snippet__Group__25427 = new BitSet(new long[]{0x0000000000100070L});
        public static final BitSet FOLLOW_rule__Snippet__Group__3_in_rule__Snippet__Group__25430 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Snippet__TypeNameAssignment_2_in_rule__Snippet__Group__2__Impl5457 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Snippet__Group__3__Impl_in_rule__Snippet__Group__35488 = new BitSet(new long[]{0x0000000000100070L});
        public static final BitSet FOLLOW_rule__Snippet__Group__4_in_rule__Snippet__Group__35491 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Snippet__PropertiesAssignment_3_in_rule__Snippet__Group__3__Impl5518 = new BitSet(new long[]{0x0000000000000012L});
        public static final BitSet FOLLOW_rule__Snippet__Group__4__Impl_in_rule__Snippet__Group__45549 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Snippet__Group_4__0_in_rule__Snippet__Group__4__Impl5576 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Snippet__Group_4__0__Impl_in_rule__Snippet__Group_4__05617 = new BitSet(new long[]{0x0000000010200000L});
        public static final BitSet FOLLOW_rule__Snippet__Group_4__1_in_rule__Snippet__Group_4__05620 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_20_in_rule__Snippet__Group_4__0__Impl5648 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Snippet__Group_4__1__Impl_in_rule__Snippet__Group_4__15679 = new BitSet(new long[]{0x0000000010200000L});
        public static final BitSet FOLLOW_rule__Snippet__Group_4__2_in_rule__Snippet__Group_4__15682 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Snippet__Group_4_1__0_in_rule__Snippet__Group_4__1__Impl5709 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Snippet__Group_4__2__Impl_in_rule__Snippet__Group_4__25740 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_21_in_rule__Snippet__Group_4__2__Impl5768 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Snippet__Group_4_1__0__Impl_in_rule__Snippet__Group_4_1__05805 = new BitSet(new long[]{0x0000000010000000L});
        public static final BitSet FOLLOW_rule__Snippet__Group_4_1__1_in_rule__Snippet__Group_4_1__05808 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Snippet__ContentsAssignment_4_1_0_in_rule__Snippet__Group_4_1__0__Impl5835 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Snippet__Group_4_1__1__Impl_in_rule__Snippet__Group_4_1__15865 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Snippet__ContentsAssignment_4_1_1_in_rule__Snippet__Group_4_1__1__Impl5892 = new BitSet(new long[]{0x0000000010000002L});
        public static final BitSet FOLLOW_rule__Translation__Group__0__Impl_in_rule__Translation__Group__05927 = new BitSet(new long[]{0x0000000000004000L});
        public static final BitSet FOLLOW_rule__Translation__Group__1_in_rule__Translation__Group__05930 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Translation__LanguageAssignment_0_in_rule__Translation__Group__0__Impl5957 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Translation__Group__1__Impl_in_rule__Translation__Group__15987 = new BitSet(new long[]{0x0000000000000070L});
        public static final BitSet FOLLOW_rule__Translation__Group__2_in_rule__Translation__Group__15990 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_14_in_rule__Translation__Group__1__Impl6018 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Translation__Group__2__Impl_in_rule__Translation__Group__26049 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Translation__MessageAssignment_2_in_rule__Translation__Group__2__Impl6076 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Parameter__Group__0__Impl_in_rule__Parameter__Group__06112 = new BitSet(new long[]{0x0000000000004000L});
        public static final BitSet FOLLOW_rule__Parameter__Group__1_in_rule__Parameter__Group__06115 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Parameter__NameAssignment_0_in_rule__Parameter__Group__0__Impl6142 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Parameter__Group__1__Impl_in_rule__Parameter__Group__16172 = new BitSet(new long[]{0x0000000000000070L});
        public static final BitSet FOLLOW_rule__Parameter__Group__2_in_rule__Parameter__Group__16175 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_14_in_rule__Parameter__Group__1__Impl6203 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Parameter__Group__2__Impl_in_rule__Parameter__Group__26234 = new BitSet(new long[]{0x0000000020000000L});
        public static final BitSet FOLLOW_rule__Parameter__Group__3_in_rule__Parameter__Group__26237 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Parameter__ValueAssignment_2_in_rule__Parameter__Group__2__Impl6264 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Parameter__Group__3__Impl_in_rule__Parameter__Group__36294 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_rule__Parameter__UserDefinedAssignment_3_in_rule__Parameter__Group__3__Impl6321 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleString_Value_in_rule__Model__MetamodelVersionAssignment_36365 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleWidget_in_rule__Model__WidgetAssignment_46396 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleLibraryName_in_rule__Widget__LibraryNameAssignment_2_06427 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_RULE_ID_in_rule__Widget__TypeNameAssignment_36458 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleTranslation_in_rule__Widget__LabelsAssignment_5_16489 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleTranslation_in_rule__Widget__LabelsAssignment_5_2_16520 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleTranslation_in_rule__Widget__ToolTipsAssignment_6_16551 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleTranslation_in_rule__Widget__ToolTipsAssignment_6_2_16582 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleProperty_in_rule__Widget__PropertiesAssignment_76613 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleEvent_in_rule__Widget__EventsAssignment_8_1_06644 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleEvent_in_rule__Widget__EventsAssignment_8_1_16675 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleSnippet_in_rule__Widget__SnippetsAssignment_8_2_06706 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleSnippet_in_rule__Widget__SnippetsAssignment_8_2_16737 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleWidget_in_rule__Widget__ContentsAssignment_8_3_06768 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleWidget_in_rule__Widget__ContentsAssignment_8_3_16799 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_RULE_ID_in_rule__Property__LibraryNameAssignment_1_06830 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_RULE_ID_in_rule__Property__TypeNameAssignment_26861 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleString_Value_in_rule__Property__ValueAssignment_46892 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_22_in_rule__Property__ReadonlyAssignment_56928 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_RULE_URI_in_rule__Property__ModelAssignment_6_16971 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleString_Value_in_rule__Event__EventNameAssignment_17006 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleString_Value_in_rule__Event__FunctionNameAssignment_37037 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleEventNature_in_rule__Event__NatureAssignment_57068 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleString_Value_in_rule__Event__DescriptionAssignment_6_17099 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleProperty_in_rule__Event__PropertiesAssignment_77130 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleTranslation_in_rule__Event__MessagesAssignment_8_17161 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleTranslation_in_rule__Event__MessagesAssignment_8_2_17192 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleParameter_in_rule__Event__ParametersAssignment_9_1_07223 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleParameter_in_rule__Event__ParametersAssignment_9_1_17254 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleSnippet_in_rule__Event__SnippetsAssignment_9_2_07285 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleSnippet_in_rule__Event__SnippetsAssignment_9_2_17316 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleString_Value_in_rule__Snippet__TypeNameAssignment_27347 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleProperty_in_rule__Snippet__PropertiesAssignment_37378 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleSnippet_in_rule__Snippet__ContentsAssignment_4_1_07409 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleSnippet_in_rule__Snippet__ContentsAssignment_4_1_17440 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_RULE_ID_in_rule__Translation__LanguageAssignment_07471 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleString_Value_in_rule__Translation__MessageAssignment_27502 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_RULE_ID_in_rule__Parameter__NameAssignment_07533 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleString_Value_in_rule__Parameter__ValueAssignment_27564 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_29_in_rule__Parameter__UserDefinedAssignment_37600 = new BitSet(new long[]{0x0000000000000002L});
    }


}
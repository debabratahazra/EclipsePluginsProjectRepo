package com.odcgroup.menu.model.ui.contentassist.antlr.internal; 

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
import com.odcgroup.menu.model.services.MenuGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalMenuParser extends AbstractInternalContentAssistParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_STRING", "RULE_ID", "RULE_VALUE", "RULE_URI", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "'true'", "'false'", "'conditional'", "'metamodelVersion'", "'='", "'pageflow'", "'enabled'", "'displayTabs'", "'securityRole'", "'labels'", "','", "'{'", "'}'", "'shortcut'"
    };
    public static final int RULE_VALUE=6;
    public static final int RULE_ID=5;
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
    public static final int RULE_STRING=4;
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


        public InternalMenuParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalMenuParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalMenuParser.tokenNames; }
    public String getGrammarFileName() { return "../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g"; }


     
     	private MenuGrammarAccess grammarAccess;
     	
        public void setGrammarAccess(MenuGrammarAccess grammarAccess) {
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




    // $ANTLR start "entryRuleMenuRoot"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:60:1: entryRuleMenuRoot : ruleMenuRoot EOF ;
    public final void entryRuleMenuRoot() throws RecognitionException {
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:61:1: ( ruleMenuRoot EOF )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:62:1: ruleMenuRoot EOF
            {
             before(grammarAccess.getMenuRootRule()); 
            pushFollow(FOLLOW_ruleMenuRoot_in_entryRuleMenuRoot61);
            ruleMenuRoot();

            state._fsp--;

             after(grammarAccess.getMenuRootRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMenuRoot68); 

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
    // $ANTLR end "entryRuleMenuRoot"


    // $ANTLR start "ruleMenuRoot"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:69:1: ruleMenuRoot : ( ( rule__MenuRoot__Group__0 ) ) ;
    public final void ruleMenuRoot() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:73:2: ( ( ( rule__MenuRoot__Group__0 ) ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:74:1: ( ( rule__MenuRoot__Group__0 ) )
            {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:74:1: ( ( rule__MenuRoot__Group__0 ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:75:1: ( rule__MenuRoot__Group__0 )
            {
             before(grammarAccess.getMenuRootAccess().getGroup()); 
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:76:1: ( rule__MenuRoot__Group__0 )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:76:2: rule__MenuRoot__Group__0
            {
            pushFollow(FOLLOW_rule__MenuRoot__Group__0_in_ruleMenuRoot94);
            rule__MenuRoot__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getMenuRootAccess().getGroup()); 

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
    // $ANTLR end "ruleMenuRoot"


    // $ANTLR start "entryRuleMenuItem"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:88:1: entryRuleMenuItem : ruleMenuItem EOF ;
    public final void entryRuleMenuItem() throws RecognitionException {
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:89:1: ( ruleMenuItem EOF )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:90:1: ruleMenuItem EOF
            {
             before(grammarAccess.getMenuItemRule()); 
            pushFollow(FOLLOW_ruleMenuItem_in_entryRuleMenuItem121);
            ruleMenuItem();

            state._fsp--;

             after(grammarAccess.getMenuItemRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMenuItem128); 

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
    // $ANTLR end "entryRuleMenuItem"


    // $ANTLR start "ruleMenuItem"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:97:1: ruleMenuItem : ( ( rule__MenuItem__Group__0 ) ) ;
    public final void ruleMenuItem() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:101:2: ( ( ( rule__MenuItem__Group__0 ) ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:102:1: ( ( rule__MenuItem__Group__0 ) )
            {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:102:1: ( ( rule__MenuItem__Group__0 ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:103:1: ( rule__MenuItem__Group__0 )
            {
             before(grammarAccess.getMenuItemAccess().getGroup()); 
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:104:1: ( rule__MenuItem__Group__0 )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:104:2: rule__MenuItem__Group__0
            {
            pushFollow(FOLLOW_rule__MenuItem__Group__0_in_ruleMenuItem154);
            rule__MenuItem__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getMenuItemAccess().getGroup()); 

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
    // $ANTLR end "ruleMenuItem"


    // $ANTLR start "entryRuleTranslation"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:116:1: entryRuleTranslation : ruleTranslation EOF ;
    public final void entryRuleTranslation() throws RecognitionException {
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:117:1: ( ruleTranslation EOF )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:118:1: ruleTranslation EOF
            {
             before(grammarAccess.getTranslationRule()); 
            pushFollow(FOLLOW_ruleTranslation_in_entryRuleTranslation181);
            ruleTranslation();

            state._fsp--;

             after(grammarAccess.getTranslationRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleTranslation188); 

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
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:125:1: ruleTranslation : ( ( rule__Translation__Group__0 ) ) ;
    public final void ruleTranslation() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:129:2: ( ( ( rule__Translation__Group__0 ) ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:130:1: ( ( rule__Translation__Group__0 ) )
            {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:130:1: ( ( rule__Translation__Group__0 ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:131:1: ( rule__Translation__Group__0 )
            {
             before(grammarAccess.getTranslationAccess().getGroup()); 
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:132:1: ( rule__Translation__Group__0 )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:132:2: rule__Translation__Group__0
            {
            pushFollow(FOLLOW_rule__Translation__Group__0_in_ruleTranslation214);
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


    // $ANTLR start "entryRuleString_Value"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:144:1: entryRuleString_Value : ruleString_Value EOF ;
    public final void entryRuleString_Value() throws RecognitionException {
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:145:1: ( ruleString_Value EOF )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:146:1: ruleString_Value EOF
            {
             before(grammarAccess.getString_ValueRule()); 
            pushFollow(FOLLOW_ruleString_Value_in_entryRuleString_Value241);
            ruleString_Value();

            state._fsp--;

             after(grammarAccess.getString_ValueRule()); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleString_Value248); 

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
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:153:1: ruleString_Value : ( ( rule__String_Value__Alternatives ) ) ;
    public final void ruleString_Value() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:157:2: ( ( ( rule__String_Value__Alternatives ) ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:158:1: ( ( rule__String_Value__Alternatives ) )
            {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:158:1: ( ( rule__String_Value__Alternatives ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:159:1: ( rule__String_Value__Alternatives )
            {
             before(grammarAccess.getString_ValueAccess().getAlternatives()); 
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:160:1: ( rule__String_Value__Alternatives )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:160:2: rule__String_Value__Alternatives
            {
            pushFollow(FOLLOW_rule__String_Value__Alternatives_in_ruleString_Value274);
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


    // $ANTLR start "ruleEnabled"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:173:1: ruleEnabled : ( ( rule__Enabled__Alternatives ) ) ;
    public final void ruleEnabled() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:177:1: ( ( ( rule__Enabled__Alternatives ) ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:178:1: ( ( rule__Enabled__Alternatives ) )
            {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:178:1: ( ( rule__Enabled__Alternatives ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:179:1: ( rule__Enabled__Alternatives )
            {
             before(grammarAccess.getEnabledAccess().getAlternatives()); 
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:180:1: ( rule__Enabled__Alternatives )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:180:2: rule__Enabled__Alternatives
            {
            pushFollow(FOLLOW_rule__Enabled__Alternatives_in_ruleEnabled311);
            rule__Enabled__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getEnabledAccess().getAlternatives()); 

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
    // $ANTLR end "ruleEnabled"


    // $ANTLR start "rule__String_Value__Alternatives"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:191:1: rule__String_Value__Alternatives : ( ( RULE_STRING ) | ( RULE_ID ) | ( RULE_VALUE ) | ( RULE_URI ) );
    public final void rule__String_Value__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:195:1: ( ( RULE_STRING ) | ( RULE_ID ) | ( RULE_VALUE ) | ( RULE_URI ) )
            int alt1=4;
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
            case RULE_URI:
                {
                alt1=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }

            switch (alt1) {
                case 1 :
                    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:196:1: ( RULE_STRING )
                    {
                    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:196:1: ( RULE_STRING )
                    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:197:1: RULE_STRING
                    {
                     before(grammarAccess.getString_ValueAccess().getSTRINGTerminalRuleCall_0()); 
                    match(input,RULE_STRING,FOLLOW_RULE_STRING_in_rule__String_Value__Alternatives346); 
                     after(grammarAccess.getString_ValueAccess().getSTRINGTerminalRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:202:6: ( RULE_ID )
                    {
                    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:202:6: ( RULE_ID )
                    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:203:1: RULE_ID
                    {
                     before(grammarAccess.getString_ValueAccess().getIDTerminalRuleCall_1()); 
                    match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__String_Value__Alternatives363); 
                     after(grammarAccess.getString_ValueAccess().getIDTerminalRuleCall_1()); 

                    }


                    }
                    break;
                case 3 :
                    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:208:6: ( RULE_VALUE )
                    {
                    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:208:6: ( RULE_VALUE )
                    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:209:1: RULE_VALUE
                    {
                     before(grammarAccess.getString_ValueAccess().getVALUETerminalRuleCall_2()); 
                    match(input,RULE_VALUE,FOLLOW_RULE_VALUE_in_rule__String_Value__Alternatives380); 
                     after(grammarAccess.getString_ValueAccess().getVALUETerminalRuleCall_2()); 

                    }


                    }
                    break;
                case 4 :
                    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:214:6: ( RULE_URI )
                    {
                    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:214:6: ( RULE_URI )
                    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:215:1: RULE_URI
                    {
                     before(grammarAccess.getString_ValueAccess().getURITerminalRuleCall_3()); 
                    match(input,RULE_URI,FOLLOW_RULE_URI_in_rule__String_Value__Alternatives397); 
                     after(grammarAccess.getString_ValueAccess().getURITerminalRuleCall_3()); 

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


    // $ANTLR start "rule__Enabled__Alternatives"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:225:1: rule__Enabled__Alternatives : ( ( ( 'true' ) ) | ( ( 'false' ) ) | ( ( 'conditional' ) ) );
    public final void rule__Enabled__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:229:1: ( ( ( 'true' ) ) | ( ( 'false' ) ) | ( ( 'conditional' ) ) )
            int alt2=3;
            switch ( input.LA(1) ) {
            case 11:
                {
                alt2=1;
                }
                break;
            case 12:
                {
                alt2=2;
                }
                break;
            case 13:
                {
                alt2=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }

            switch (alt2) {
                case 1 :
                    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:230:1: ( ( 'true' ) )
                    {
                    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:230:1: ( ( 'true' ) )
                    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:231:1: ( 'true' )
                    {
                     before(grammarAccess.getEnabledAccess().getTrueEnumLiteralDeclaration_0()); 
                    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:232:1: ( 'true' )
                    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:232:3: 'true'
                    {
                    match(input,11,FOLLOW_11_in_rule__Enabled__Alternatives430); 

                    }

                     after(grammarAccess.getEnabledAccess().getTrueEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:237:6: ( ( 'false' ) )
                    {
                    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:237:6: ( ( 'false' ) )
                    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:238:1: ( 'false' )
                    {
                     before(grammarAccess.getEnabledAccess().getFalseEnumLiteralDeclaration_1()); 
                    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:239:1: ( 'false' )
                    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:239:3: 'false'
                    {
                    match(input,12,FOLLOW_12_in_rule__Enabled__Alternatives451); 

                    }

                     after(grammarAccess.getEnabledAccess().getFalseEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:244:6: ( ( 'conditional' ) )
                    {
                    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:244:6: ( ( 'conditional' ) )
                    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:245:1: ( 'conditional' )
                    {
                     before(grammarAccess.getEnabledAccess().getConditionalEnumLiteralDeclaration_2()); 
                    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:246:1: ( 'conditional' )
                    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:246:3: 'conditional'
                    {
                    match(input,13,FOLLOW_13_in_rule__Enabled__Alternatives472); 

                    }

                     after(grammarAccess.getEnabledAccess().getConditionalEnumLiteralDeclaration_2()); 

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
    // $ANTLR end "rule__Enabled__Alternatives"


    // $ANTLR start "rule__MenuRoot__Group__0"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:258:1: rule__MenuRoot__Group__0 : rule__MenuRoot__Group__0__Impl rule__MenuRoot__Group__1 ;
    public final void rule__MenuRoot__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:262:1: ( rule__MenuRoot__Group__0__Impl rule__MenuRoot__Group__1 )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:263:2: rule__MenuRoot__Group__0__Impl rule__MenuRoot__Group__1
            {
            pushFollow(FOLLOW_rule__MenuRoot__Group__0__Impl_in_rule__MenuRoot__Group__0505);
            rule__MenuRoot__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuRoot__Group__1_in_rule__MenuRoot__Group__0508);
            rule__MenuRoot__Group__1();

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
    // $ANTLR end "rule__MenuRoot__Group__0"


    // $ANTLR start "rule__MenuRoot__Group__0__Impl"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:270:1: rule__MenuRoot__Group__0__Impl : ( 'metamodelVersion' ) ;
    public final void rule__MenuRoot__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:274:1: ( ( 'metamodelVersion' ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:275:1: ( 'metamodelVersion' )
            {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:275:1: ( 'metamodelVersion' )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:276:1: 'metamodelVersion'
            {
             before(grammarAccess.getMenuRootAccess().getMetamodelVersionKeyword_0()); 
            match(input,14,FOLLOW_14_in_rule__MenuRoot__Group__0__Impl536); 
             after(grammarAccess.getMenuRootAccess().getMetamodelVersionKeyword_0()); 

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
    // $ANTLR end "rule__MenuRoot__Group__0__Impl"


    // $ANTLR start "rule__MenuRoot__Group__1"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:289:1: rule__MenuRoot__Group__1 : rule__MenuRoot__Group__1__Impl rule__MenuRoot__Group__2 ;
    public final void rule__MenuRoot__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:293:1: ( rule__MenuRoot__Group__1__Impl rule__MenuRoot__Group__2 )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:294:2: rule__MenuRoot__Group__1__Impl rule__MenuRoot__Group__2
            {
            pushFollow(FOLLOW_rule__MenuRoot__Group__1__Impl_in_rule__MenuRoot__Group__1567);
            rule__MenuRoot__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuRoot__Group__2_in_rule__MenuRoot__Group__1570);
            rule__MenuRoot__Group__2();

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
    // $ANTLR end "rule__MenuRoot__Group__1"


    // $ANTLR start "rule__MenuRoot__Group__1__Impl"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:301:1: rule__MenuRoot__Group__1__Impl : ( '=' ) ;
    public final void rule__MenuRoot__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:305:1: ( ( '=' ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:306:1: ( '=' )
            {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:306:1: ( '=' )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:307:1: '='
            {
             before(grammarAccess.getMenuRootAccess().getEqualsSignKeyword_1()); 
            match(input,15,FOLLOW_15_in_rule__MenuRoot__Group__1__Impl598); 
             after(grammarAccess.getMenuRootAccess().getEqualsSignKeyword_1()); 

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
    // $ANTLR end "rule__MenuRoot__Group__1__Impl"


    // $ANTLR start "rule__MenuRoot__Group__2"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:320:1: rule__MenuRoot__Group__2 : rule__MenuRoot__Group__2__Impl rule__MenuRoot__Group__3 ;
    public final void rule__MenuRoot__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:324:1: ( rule__MenuRoot__Group__2__Impl rule__MenuRoot__Group__3 )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:325:2: rule__MenuRoot__Group__2__Impl rule__MenuRoot__Group__3
            {
            pushFollow(FOLLOW_rule__MenuRoot__Group__2__Impl_in_rule__MenuRoot__Group__2629);
            rule__MenuRoot__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuRoot__Group__3_in_rule__MenuRoot__Group__2632);
            rule__MenuRoot__Group__3();

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
    // $ANTLR end "rule__MenuRoot__Group__2"


    // $ANTLR start "rule__MenuRoot__Group__2__Impl"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:332:1: rule__MenuRoot__Group__2__Impl : ( ( rule__MenuRoot__MetamodelVersionAssignment_2 ) ) ;
    public final void rule__MenuRoot__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:336:1: ( ( ( rule__MenuRoot__MetamodelVersionAssignment_2 ) ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:337:1: ( ( rule__MenuRoot__MetamodelVersionAssignment_2 ) )
            {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:337:1: ( ( rule__MenuRoot__MetamodelVersionAssignment_2 ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:338:1: ( rule__MenuRoot__MetamodelVersionAssignment_2 )
            {
             before(grammarAccess.getMenuRootAccess().getMetamodelVersionAssignment_2()); 
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:339:1: ( rule__MenuRoot__MetamodelVersionAssignment_2 )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:339:2: rule__MenuRoot__MetamodelVersionAssignment_2
            {
            pushFollow(FOLLOW_rule__MenuRoot__MetamodelVersionAssignment_2_in_rule__MenuRoot__Group__2__Impl659);
            rule__MenuRoot__MetamodelVersionAssignment_2();

            state._fsp--;


            }

             after(grammarAccess.getMenuRootAccess().getMetamodelVersionAssignment_2()); 

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
    // $ANTLR end "rule__MenuRoot__Group__2__Impl"


    // $ANTLR start "rule__MenuRoot__Group__3"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:349:1: rule__MenuRoot__Group__3 : rule__MenuRoot__Group__3__Impl ;
    public final void rule__MenuRoot__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:353:1: ( rule__MenuRoot__Group__3__Impl )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:354:2: rule__MenuRoot__Group__3__Impl
            {
            pushFollow(FOLLOW_rule__MenuRoot__Group__3__Impl_in_rule__MenuRoot__Group__3689);
            rule__MenuRoot__Group__3__Impl();

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
    // $ANTLR end "rule__MenuRoot__Group__3"


    // $ANTLR start "rule__MenuRoot__Group__3__Impl"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:360:1: rule__MenuRoot__Group__3__Impl : ( ( rule__MenuRoot__RootItemAssignment_3 ) ) ;
    public final void rule__MenuRoot__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:364:1: ( ( ( rule__MenuRoot__RootItemAssignment_3 ) ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:365:1: ( ( rule__MenuRoot__RootItemAssignment_3 ) )
            {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:365:1: ( ( rule__MenuRoot__RootItemAssignment_3 ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:366:1: ( rule__MenuRoot__RootItemAssignment_3 )
            {
             before(grammarAccess.getMenuRootAccess().getRootItemAssignment_3()); 
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:367:1: ( rule__MenuRoot__RootItemAssignment_3 )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:367:2: rule__MenuRoot__RootItemAssignment_3
            {
            pushFollow(FOLLOW_rule__MenuRoot__RootItemAssignment_3_in_rule__MenuRoot__Group__3__Impl716);
            rule__MenuRoot__RootItemAssignment_3();

            state._fsp--;


            }

             after(grammarAccess.getMenuRootAccess().getRootItemAssignment_3()); 

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
    // $ANTLR end "rule__MenuRoot__Group__3__Impl"


    // $ANTLR start "rule__MenuItem__Group__0"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:385:1: rule__MenuItem__Group__0 : rule__MenuItem__Group__0__Impl rule__MenuItem__Group__1 ;
    public final void rule__MenuItem__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:389:1: ( rule__MenuItem__Group__0__Impl rule__MenuItem__Group__1 )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:390:2: rule__MenuItem__Group__0__Impl rule__MenuItem__Group__1
            {
            pushFollow(FOLLOW_rule__MenuItem__Group__0__Impl_in_rule__MenuItem__Group__0754);
            rule__MenuItem__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuItem__Group__1_in_rule__MenuItem__Group__0757);
            rule__MenuItem__Group__1();

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
    // $ANTLR end "rule__MenuItem__Group__0"


    // $ANTLR start "rule__MenuItem__Group__0__Impl"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:397:1: rule__MenuItem__Group__0__Impl : ( ( rule__MenuItem__NameAssignment_0 ) ) ;
    public final void rule__MenuItem__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:401:1: ( ( ( rule__MenuItem__NameAssignment_0 ) ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:402:1: ( ( rule__MenuItem__NameAssignment_0 ) )
            {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:402:1: ( ( rule__MenuItem__NameAssignment_0 ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:403:1: ( rule__MenuItem__NameAssignment_0 )
            {
             before(grammarAccess.getMenuItemAccess().getNameAssignment_0()); 
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:404:1: ( rule__MenuItem__NameAssignment_0 )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:404:2: rule__MenuItem__NameAssignment_0
            {
            pushFollow(FOLLOW_rule__MenuItem__NameAssignment_0_in_rule__MenuItem__Group__0__Impl784);
            rule__MenuItem__NameAssignment_0();

            state._fsp--;


            }

             after(grammarAccess.getMenuItemAccess().getNameAssignment_0()); 

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
    // $ANTLR end "rule__MenuItem__Group__0__Impl"


    // $ANTLR start "rule__MenuItem__Group__1"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:414:1: rule__MenuItem__Group__1 : rule__MenuItem__Group__1__Impl rule__MenuItem__Group__2 ;
    public final void rule__MenuItem__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:418:1: ( rule__MenuItem__Group__1__Impl rule__MenuItem__Group__2 )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:419:2: rule__MenuItem__Group__1__Impl rule__MenuItem__Group__2
            {
            pushFollow(FOLLOW_rule__MenuItem__Group__1__Impl_in_rule__MenuItem__Group__1814);
            rule__MenuItem__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuItem__Group__2_in_rule__MenuItem__Group__1817);
            rule__MenuItem__Group__2();

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
    // $ANTLR end "rule__MenuItem__Group__1"


    // $ANTLR start "rule__MenuItem__Group__1__Impl"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:426:1: rule__MenuItem__Group__1__Impl : ( ( rule__MenuItem__Group_1__0 )? ) ;
    public final void rule__MenuItem__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:430:1: ( ( ( rule__MenuItem__Group_1__0 )? ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:431:1: ( ( rule__MenuItem__Group_1__0 )? )
            {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:431:1: ( ( rule__MenuItem__Group_1__0 )? )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:432:1: ( rule__MenuItem__Group_1__0 )?
            {
             before(grammarAccess.getMenuItemAccess().getGroup_1()); 
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:433:1: ( rule__MenuItem__Group_1__0 )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==16) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:433:2: rule__MenuItem__Group_1__0
                    {
                    pushFollow(FOLLOW_rule__MenuItem__Group_1__0_in_rule__MenuItem__Group__1__Impl844);
                    rule__MenuItem__Group_1__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getMenuItemAccess().getGroup_1()); 

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
    // $ANTLR end "rule__MenuItem__Group__1__Impl"


    // $ANTLR start "rule__MenuItem__Group__2"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:443:1: rule__MenuItem__Group__2 : rule__MenuItem__Group__2__Impl rule__MenuItem__Group__3 ;
    public final void rule__MenuItem__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:447:1: ( rule__MenuItem__Group__2__Impl rule__MenuItem__Group__3 )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:448:2: rule__MenuItem__Group__2__Impl rule__MenuItem__Group__3
            {
            pushFollow(FOLLOW_rule__MenuItem__Group__2__Impl_in_rule__MenuItem__Group__2875);
            rule__MenuItem__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuItem__Group__3_in_rule__MenuItem__Group__2878);
            rule__MenuItem__Group__3();

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
    // $ANTLR end "rule__MenuItem__Group__2"


    // $ANTLR start "rule__MenuItem__Group__2__Impl"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:455:1: rule__MenuItem__Group__2__Impl : ( ( rule__MenuItem__Group_2__0 )? ) ;
    public final void rule__MenuItem__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:459:1: ( ( ( rule__MenuItem__Group_2__0 )? ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:460:1: ( ( rule__MenuItem__Group_2__0 )? )
            {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:460:1: ( ( rule__MenuItem__Group_2__0 )? )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:461:1: ( rule__MenuItem__Group_2__0 )?
            {
             before(grammarAccess.getMenuItemAccess().getGroup_2()); 
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:462:1: ( rule__MenuItem__Group_2__0 )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==17) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:462:2: rule__MenuItem__Group_2__0
                    {
                    pushFollow(FOLLOW_rule__MenuItem__Group_2__0_in_rule__MenuItem__Group__2__Impl905);
                    rule__MenuItem__Group_2__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getMenuItemAccess().getGroup_2()); 

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
    // $ANTLR end "rule__MenuItem__Group__2__Impl"


    // $ANTLR start "rule__MenuItem__Group__3"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:472:1: rule__MenuItem__Group__3 : rule__MenuItem__Group__3__Impl rule__MenuItem__Group__4 ;
    public final void rule__MenuItem__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:476:1: ( rule__MenuItem__Group__3__Impl rule__MenuItem__Group__4 )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:477:2: rule__MenuItem__Group__3__Impl rule__MenuItem__Group__4
            {
            pushFollow(FOLLOW_rule__MenuItem__Group__3__Impl_in_rule__MenuItem__Group__3936);
            rule__MenuItem__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuItem__Group__4_in_rule__MenuItem__Group__3939);
            rule__MenuItem__Group__4();

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
    // $ANTLR end "rule__MenuItem__Group__3"


    // $ANTLR start "rule__MenuItem__Group__3__Impl"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:484:1: rule__MenuItem__Group__3__Impl : ( ( rule__MenuItem__Group_3__0 )? ) ;
    public final void rule__MenuItem__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:488:1: ( ( ( rule__MenuItem__Group_3__0 )? ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:489:1: ( ( rule__MenuItem__Group_3__0 )? )
            {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:489:1: ( ( rule__MenuItem__Group_3__0 )? )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:490:1: ( rule__MenuItem__Group_3__0 )?
            {
             before(grammarAccess.getMenuItemAccess().getGroup_3()); 
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:491:1: ( rule__MenuItem__Group_3__0 )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==18) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:491:2: rule__MenuItem__Group_3__0
                    {
                    pushFollow(FOLLOW_rule__MenuItem__Group_3__0_in_rule__MenuItem__Group__3__Impl966);
                    rule__MenuItem__Group_3__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getMenuItemAccess().getGroup_3()); 

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
    // $ANTLR end "rule__MenuItem__Group__3__Impl"


    // $ANTLR start "rule__MenuItem__Group__4"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:501:1: rule__MenuItem__Group__4 : rule__MenuItem__Group__4__Impl rule__MenuItem__Group__5 ;
    public final void rule__MenuItem__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:505:1: ( rule__MenuItem__Group__4__Impl rule__MenuItem__Group__5 )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:506:2: rule__MenuItem__Group__4__Impl rule__MenuItem__Group__5
            {
            pushFollow(FOLLOW_rule__MenuItem__Group__4__Impl_in_rule__MenuItem__Group__4997);
            rule__MenuItem__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuItem__Group__5_in_rule__MenuItem__Group__41000);
            rule__MenuItem__Group__5();

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
    // $ANTLR end "rule__MenuItem__Group__4"


    // $ANTLR start "rule__MenuItem__Group__4__Impl"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:513:1: rule__MenuItem__Group__4__Impl : ( ( rule__MenuItem__Group_4__0 )? ) ;
    public final void rule__MenuItem__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:517:1: ( ( ( rule__MenuItem__Group_4__0 )? ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:518:1: ( ( rule__MenuItem__Group_4__0 )? )
            {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:518:1: ( ( rule__MenuItem__Group_4__0 )? )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:519:1: ( rule__MenuItem__Group_4__0 )?
            {
             before(grammarAccess.getMenuItemAccess().getGroup_4()); 
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:520:1: ( rule__MenuItem__Group_4__0 )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==19) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:520:2: rule__MenuItem__Group_4__0
                    {
                    pushFollow(FOLLOW_rule__MenuItem__Group_4__0_in_rule__MenuItem__Group__4__Impl1027);
                    rule__MenuItem__Group_4__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getMenuItemAccess().getGroup_4()); 

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
    // $ANTLR end "rule__MenuItem__Group__4__Impl"


    // $ANTLR start "rule__MenuItem__Group__5"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:530:1: rule__MenuItem__Group__5 : rule__MenuItem__Group__5__Impl rule__MenuItem__Group__6 ;
    public final void rule__MenuItem__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:534:1: ( rule__MenuItem__Group__5__Impl rule__MenuItem__Group__6 )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:535:2: rule__MenuItem__Group__5__Impl rule__MenuItem__Group__6
            {
            pushFollow(FOLLOW_rule__MenuItem__Group__5__Impl_in_rule__MenuItem__Group__51058);
            rule__MenuItem__Group__5__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuItem__Group__6_in_rule__MenuItem__Group__51061);
            rule__MenuItem__Group__6();

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
    // $ANTLR end "rule__MenuItem__Group__5"


    // $ANTLR start "rule__MenuItem__Group__5__Impl"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:542:1: rule__MenuItem__Group__5__Impl : ( ( rule__MenuItem__Group_5__0 )? ) ;
    public final void rule__MenuItem__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:546:1: ( ( ( rule__MenuItem__Group_5__0 )? ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:547:1: ( ( rule__MenuItem__Group_5__0 )? )
            {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:547:1: ( ( rule__MenuItem__Group_5__0 )? )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:548:1: ( rule__MenuItem__Group_5__0 )?
            {
             before(grammarAccess.getMenuItemAccess().getGroup_5()); 
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:549:1: ( rule__MenuItem__Group_5__0 )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==20) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:549:2: rule__MenuItem__Group_5__0
                    {
                    pushFollow(FOLLOW_rule__MenuItem__Group_5__0_in_rule__MenuItem__Group__5__Impl1088);
                    rule__MenuItem__Group_5__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getMenuItemAccess().getGroup_5()); 

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
    // $ANTLR end "rule__MenuItem__Group__5__Impl"


    // $ANTLR start "rule__MenuItem__Group__6"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:559:1: rule__MenuItem__Group__6 : rule__MenuItem__Group__6__Impl rule__MenuItem__Group__7 ;
    public final void rule__MenuItem__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:563:1: ( rule__MenuItem__Group__6__Impl rule__MenuItem__Group__7 )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:564:2: rule__MenuItem__Group__6__Impl rule__MenuItem__Group__7
            {
            pushFollow(FOLLOW_rule__MenuItem__Group__6__Impl_in_rule__MenuItem__Group__61119);
            rule__MenuItem__Group__6__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuItem__Group__7_in_rule__MenuItem__Group__61122);
            rule__MenuItem__Group__7();

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
    // $ANTLR end "rule__MenuItem__Group__6"


    // $ANTLR start "rule__MenuItem__Group__6__Impl"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:571:1: rule__MenuItem__Group__6__Impl : ( ( rule__MenuItem__Group_6__0 )? ) ;
    public final void rule__MenuItem__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:575:1: ( ( ( rule__MenuItem__Group_6__0 )? ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:576:1: ( ( rule__MenuItem__Group_6__0 )? )
            {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:576:1: ( ( rule__MenuItem__Group_6__0 )? )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:577:1: ( rule__MenuItem__Group_6__0 )?
            {
             before(grammarAccess.getMenuItemAccess().getGroup_6()); 
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:578:1: ( rule__MenuItem__Group_6__0 )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==22) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:578:2: rule__MenuItem__Group_6__0
                    {
                    pushFollow(FOLLOW_rule__MenuItem__Group_6__0_in_rule__MenuItem__Group__6__Impl1149);
                    rule__MenuItem__Group_6__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getMenuItemAccess().getGroup_6()); 

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
    // $ANTLR end "rule__MenuItem__Group__6__Impl"


    // $ANTLR start "rule__MenuItem__Group__7"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:588:1: rule__MenuItem__Group__7 : rule__MenuItem__Group__7__Impl ;
    public final void rule__MenuItem__Group__7() throws RecognitionException {

			int stackSize = keepStackSize();

        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:592:1: ( rule__MenuItem__Group__7__Impl )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:593:2: rule__MenuItem__Group__7__Impl
            {
            pushFollow(FOLLOW_rule__MenuItem__Group__7__Impl_in_rule__MenuItem__Group__71180);
            rule__MenuItem__Group__7__Impl();

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
    // $ANTLR end "rule__MenuItem__Group__7"


    // $ANTLR start "rule__MenuItem__Group__7__Impl"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:599:1: rule__MenuItem__Group__7__Impl : ( ( rule__MenuItem__Group_7__0 )? ) ;
    public final void rule__MenuItem__Group__7__Impl() throws RecognitionException {

			int stackSize = keepStackSize();

        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:603:1: ( ( ( rule__MenuItem__Group_7__0 )? ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:604:1: ( ( rule__MenuItem__Group_7__0 )? )
            {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:604:1: ( ( rule__MenuItem__Group_7__0 )? )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:605:1: ( rule__MenuItem__Group_7__0 )?
            {
             before(grammarAccess.getMenuItemAccess().getGroup_7());
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:606:1: ( rule__MenuItem__Group_7__0 )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==24) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:606:2: rule__MenuItem__Group_7__0
                    {
                    pushFollow(FOLLOW_rule__MenuItem__Group_7__0_in_rule__MenuItem__Group__7__Impl1207);
                    rule__MenuItem__Group_7__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getMenuItemAccess().getGroup_7());

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
    // $ANTLR end "rule__MenuItem__Group__7__Impl"


    // $ANTLR start "rule__MenuItem__Group_1__0"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:632:1: rule__MenuItem__Group_1__0 : rule__MenuItem__Group_1__0__Impl rule__MenuItem__Group_1__1 ;
    public final void rule__MenuItem__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:636:1: ( rule__MenuItem__Group_1__0__Impl rule__MenuItem__Group_1__1 )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:637:2: rule__MenuItem__Group_1__0__Impl rule__MenuItem__Group_1__1
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_1__0__Impl_in_rule__MenuItem__Group_1__01254);
            rule__MenuItem__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuItem__Group_1__1_in_rule__MenuItem__Group_1__01257);
            rule__MenuItem__Group_1__1();

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
    // $ANTLR end "rule__MenuItem__Group_1__0"


    // $ANTLR start "rule__MenuItem__Group_1__0__Impl"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:644:1: rule__MenuItem__Group_1__0__Impl : ( 'pageflow' ) ;
    public final void rule__MenuItem__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:648:1: ( ( 'pageflow' ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:649:1: ( 'pageflow' )
            {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:649:1: ( 'pageflow' )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:650:1: 'pageflow'
            {
             before(grammarAccess.getMenuItemAccess().getPageflowKeyword_1_0()); 
            match(input,16,FOLLOW_16_in_rule__MenuItem__Group_1__0__Impl1285);
             after(grammarAccess.getMenuItemAccess().getPageflowKeyword_1_0()); 

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
    // $ANTLR end "rule__MenuItem__Group_1__0__Impl"


    // $ANTLR start "rule__MenuItem__Group_1__1"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:663:1: rule__MenuItem__Group_1__1 : rule__MenuItem__Group_1__1__Impl rule__MenuItem__Group_1__2 ;
    public final void rule__MenuItem__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:667:1: ( rule__MenuItem__Group_1__1__Impl rule__MenuItem__Group_1__2 )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:668:2: rule__MenuItem__Group_1__1__Impl rule__MenuItem__Group_1__2
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_1__1__Impl_in_rule__MenuItem__Group_1__11316);
            rule__MenuItem__Group_1__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuItem__Group_1__2_in_rule__MenuItem__Group_1__11319);
            rule__MenuItem__Group_1__2();

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
    // $ANTLR end "rule__MenuItem__Group_1__1"


    // $ANTLR start "rule__MenuItem__Group_1__1__Impl"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:675:1: rule__MenuItem__Group_1__1__Impl : ( '=' ) ;
    public final void rule__MenuItem__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:679:1: ( ( '=' ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:680:1: ( '=' )
            {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:680:1: ( '=' )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:681:1: '='
            {
             before(grammarAccess.getMenuItemAccess().getEqualsSignKeyword_1_1()); 
            match(input,15,FOLLOW_15_in_rule__MenuItem__Group_1__1__Impl1347);
             after(grammarAccess.getMenuItemAccess().getEqualsSignKeyword_1_1()); 

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
    // $ANTLR end "rule__MenuItem__Group_1__1__Impl"


    // $ANTLR start "rule__MenuItem__Group_1__2"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:694:1: rule__MenuItem__Group_1__2 : rule__MenuItem__Group_1__2__Impl ;
    public final void rule__MenuItem__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:698:1: ( rule__MenuItem__Group_1__2__Impl )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:699:2: rule__MenuItem__Group_1__2__Impl
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_1__2__Impl_in_rule__MenuItem__Group_1__21378);
            rule__MenuItem__Group_1__2__Impl();

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
    // $ANTLR end "rule__MenuItem__Group_1__2"


    // $ANTLR start "rule__MenuItem__Group_1__2__Impl"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:705:1: rule__MenuItem__Group_1__2__Impl : ( ( rule__MenuItem__PageflowAssignment_1_2 ) ) ;
    public final void rule__MenuItem__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:709:1: ( ( ( rule__MenuItem__PageflowAssignment_1_2 ) ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:710:1: ( ( rule__MenuItem__PageflowAssignment_1_2 ) )
            {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:710:1: ( ( rule__MenuItem__PageflowAssignment_1_2 ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:711:1: ( rule__MenuItem__PageflowAssignment_1_2 )
            {
             before(grammarAccess.getMenuItemAccess().getPageflowAssignment_1_2()); 
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:712:1: ( rule__MenuItem__PageflowAssignment_1_2 )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:712:2: rule__MenuItem__PageflowAssignment_1_2
            {
            pushFollow(FOLLOW_rule__MenuItem__PageflowAssignment_1_2_in_rule__MenuItem__Group_1__2__Impl1405);
            rule__MenuItem__PageflowAssignment_1_2();

            state._fsp--;


            }

             after(grammarAccess.getMenuItemAccess().getPageflowAssignment_1_2()); 

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
    // $ANTLR end "rule__MenuItem__Group_1__2__Impl"


    // $ANTLR start "rule__MenuItem__Group_2__0"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:728:1: rule__MenuItem__Group_2__0 : rule__MenuItem__Group_2__0__Impl rule__MenuItem__Group_2__1 ;
    public final void rule__MenuItem__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:732:1: ( rule__MenuItem__Group_2__0__Impl rule__MenuItem__Group_2__1 )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:733:2: rule__MenuItem__Group_2__0__Impl rule__MenuItem__Group_2__1
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_2__0__Impl_in_rule__MenuItem__Group_2__01441);
            rule__MenuItem__Group_2__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuItem__Group_2__1_in_rule__MenuItem__Group_2__01444);
            rule__MenuItem__Group_2__1();

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
    // $ANTLR end "rule__MenuItem__Group_2__0"


    // $ANTLR start "rule__MenuItem__Group_2__0__Impl"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:740:1: rule__MenuItem__Group_2__0__Impl : ( 'enabled' ) ;
    public final void rule__MenuItem__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:744:1: ( ( 'enabled' ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:745:1: ( 'enabled' )
            {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:745:1: ( 'enabled' )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:746:1: 'enabled'
            {
             before(grammarAccess.getMenuItemAccess().getEnabledKeyword_2_0()); 
            match(input,17,FOLLOW_17_in_rule__MenuItem__Group_2__0__Impl1472);
             after(grammarAccess.getMenuItemAccess().getEnabledKeyword_2_0()); 

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
    // $ANTLR end "rule__MenuItem__Group_2__0__Impl"


    // $ANTLR start "rule__MenuItem__Group_2__1"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:759:1: rule__MenuItem__Group_2__1 : rule__MenuItem__Group_2__1__Impl rule__MenuItem__Group_2__2 ;
    public final void rule__MenuItem__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:763:1: ( rule__MenuItem__Group_2__1__Impl rule__MenuItem__Group_2__2 )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:764:2: rule__MenuItem__Group_2__1__Impl rule__MenuItem__Group_2__2
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_2__1__Impl_in_rule__MenuItem__Group_2__11503);
            rule__MenuItem__Group_2__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuItem__Group_2__2_in_rule__MenuItem__Group_2__11506);
            rule__MenuItem__Group_2__2();

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
    // $ANTLR end "rule__MenuItem__Group_2__1"


    // $ANTLR start "rule__MenuItem__Group_2__1__Impl"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:771:1: rule__MenuItem__Group_2__1__Impl : ( '=' ) ;
    public final void rule__MenuItem__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:775:1: ( ( '=' ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:776:1: ( '=' )
            {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:776:1: ( '=' )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:777:1: '='
            {
             before(grammarAccess.getMenuItemAccess().getEqualsSignKeyword_2_1()); 
            match(input,15,FOLLOW_15_in_rule__MenuItem__Group_2__1__Impl1534);
             after(grammarAccess.getMenuItemAccess().getEqualsSignKeyword_2_1()); 

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
    // $ANTLR end "rule__MenuItem__Group_2__1__Impl"


    // $ANTLR start "rule__MenuItem__Group_2__2"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:790:1: rule__MenuItem__Group_2__2 : rule__MenuItem__Group_2__2__Impl ;
    public final void rule__MenuItem__Group_2__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:794:1: ( rule__MenuItem__Group_2__2__Impl )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:795:2: rule__MenuItem__Group_2__2__Impl
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_2__2__Impl_in_rule__MenuItem__Group_2__21565);
            rule__MenuItem__Group_2__2__Impl();

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
    // $ANTLR end "rule__MenuItem__Group_2__2"


    // $ANTLR start "rule__MenuItem__Group_2__2__Impl"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:801:1: rule__MenuItem__Group_2__2__Impl : ( ( rule__MenuItem__EnabledAssignment_2_2 ) ) ;
    public final void rule__MenuItem__Group_2__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:805:1: ( ( ( rule__MenuItem__EnabledAssignment_2_2 ) ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:806:1: ( ( rule__MenuItem__EnabledAssignment_2_2 ) )
            {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:806:1: ( ( rule__MenuItem__EnabledAssignment_2_2 ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:807:1: ( rule__MenuItem__EnabledAssignment_2_2 )
            {
             before(grammarAccess.getMenuItemAccess().getEnabledAssignment_2_2()); 
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:808:1: ( rule__MenuItem__EnabledAssignment_2_2 )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:808:2: rule__MenuItem__EnabledAssignment_2_2
            {
            pushFollow(FOLLOW_rule__MenuItem__EnabledAssignment_2_2_in_rule__MenuItem__Group_2__2__Impl1592);
            rule__MenuItem__EnabledAssignment_2_2();

            state._fsp--;


            }

             after(grammarAccess.getMenuItemAccess().getEnabledAssignment_2_2()); 

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
    // $ANTLR end "rule__MenuItem__Group_2__2__Impl"


    // $ANTLR start "rule__MenuItem__Group_3__0"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:824:1: rule__MenuItem__Group_3__0 : rule__MenuItem__Group_3__0__Impl rule__MenuItem__Group_3__1 ;
    public final void rule__MenuItem__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:828:1: ( rule__MenuItem__Group_3__0__Impl rule__MenuItem__Group_3__1 )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:829:2: rule__MenuItem__Group_3__0__Impl rule__MenuItem__Group_3__1
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_3__0__Impl_in_rule__MenuItem__Group_3__01628);
            rule__MenuItem__Group_3__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuItem__Group_3__1_in_rule__MenuItem__Group_3__01631);
            rule__MenuItem__Group_3__1();

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
    // $ANTLR end "rule__MenuItem__Group_3__0"


    // $ANTLR start "rule__MenuItem__Group_3__0__Impl"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:836:1: rule__MenuItem__Group_3__0__Impl : ( 'displayTabs' ) ;
    public final void rule__MenuItem__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:840:1: ( ( 'displayTabs' ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:841:1: ( 'displayTabs' )
            {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:841:1: ( 'displayTabs' )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:842:1: 'displayTabs'
            {
             before(grammarAccess.getMenuItemAccess().getDisplayTabsKeyword_3_0()); 
            match(input,18,FOLLOW_18_in_rule__MenuItem__Group_3__0__Impl1659);
             after(grammarAccess.getMenuItemAccess().getDisplayTabsKeyword_3_0()); 

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
    // $ANTLR end "rule__MenuItem__Group_3__0__Impl"


    // $ANTLR start "rule__MenuItem__Group_3__1"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:855:1: rule__MenuItem__Group_3__1 : rule__MenuItem__Group_3__1__Impl rule__MenuItem__Group_3__2 ;
    public final void rule__MenuItem__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:859:1: ( rule__MenuItem__Group_3__1__Impl rule__MenuItem__Group_3__2 )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:860:2: rule__MenuItem__Group_3__1__Impl rule__MenuItem__Group_3__2
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_3__1__Impl_in_rule__MenuItem__Group_3__11690);
            rule__MenuItem__Group_3__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuItem__Group_3__2_in_rule__MenuItem__Group_3__11693);
            rule__MenuItem__Group_3__2();

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
    // $ANTLR end "rule__MenuItem__Group_3__1"


    // $ANTLR start "rule__MenuItem__Group_3__1__Impl"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:867:1: rule__MenuItem__Group_3__1__Impl : ( '=' ) ;
    public final void rule__MenuItem__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:871:1: ( ( '=' ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:872:1: ( '=' )
            {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:872:1: ( '=' )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:873:1: '='
            {
             before(grammarAccess.getMenuItemAccess().getEqualsSignKeyword_3_1()); 
            match(input,15,FOLLOW_15_in_rule__MenuItem__Group_3__1__Impl1721);
             after(grammarAccess.getMenuItemAccess().getEqualsSignKeyword_3_1()); 

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
    // $ANTLR end "rule__MenuItem__Group_3__1__Impl"


    // $ANTLR start "rule__MenuItem__Group_3__2"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:886:1: rule__MenuItem__Group_3__2 : rule__MenuItem__Group_3__2__Impl ;
    public final void rule__MenuItem__Group_3__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:890:1: ( rule__MenuItem__Group_3__2__Impl )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:891:2: rule__MenuItem__Group_3__2__Impl
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_3__2__Impl_in_rule__MenuItem__Group_3__21752);
            rule__MenuItem__Group_3__2__Impl();

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
    // $ANTLR end "rule__MenuItem__Group_3__2"


    // $ANTLR start "rule__MenuItem__Group_3__2__Impl"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:897:1: rule__MenuItem__Group_3__2__Impl : ( ( rule__MenuItem__DisplayTabsAssignment_3_2 ) ) ;
    public final void rule__MenuItem__Group_3__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:901:1: ( ( ( rule__MenuItem__DisplayTabsAssignment_3_2 ) ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:902:1: ( ( rule__MenuItem__DisplayTabsAssignment_3_2 ) )
            {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:902:1: ( ( rule__MenuItem__DisplayTabsAssignment_3_2 ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:903:1: ( rule__MenuItem__DisplayTabsAssignment_3_2 )
            {
             before(grammarAccess.getMenuItemAccess().getDisplayTabsAssignment_3_2()); 
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:904:1: ( rule__MenuItem__DisplayTabsAssignment_3_2 )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:904:2: rule__MenuItem__DisplayTabsAssignment_3_2
            {
            pushFollow(FOLLOW_rule__MenuItem__DisplayTabsAssignment_3_2_in_rule__MenuItem__Group_3__2__Impl1779);
            rule__MenuItem__DisplayTabsAssignment_3_2();

            state._fsp--;


            }

             after(grammarAccess.getMenuItemAccess().getDisplayTabsAssignment_3_2()); 

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
    // $ANTLR end "rule__MenuItem__Group_3__2__Impl"


    // $ANTLR start "rule__MenuItem__Group_4__0"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:920:1: rule__MenuItem__Group_4__0 : rule__MenuItem__Group_4__0__Impl rule__MenuItem__Group_4__1 ;
    public final void rule__MenuItem__Group_4__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:924:1: ( rule__MenuItem__Group_4__0__Impl rule__MenuItem__Group_4__1 )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:925:2: rule__MenuItem__Group_4__0__Impl rule__MenuItem__Group_4__1
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_4__0__Impl_in_rule__MenuItem__Group_4__01815);
            rule__MenuItem__Group_4__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuItem__Group_4__1_in_rule__MenuItem__Group_4__01818);
            rule__MenuItem__Group_4__1();

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
    // $ANTLR end "rule__MenuItem__Group_4__0"


    // $ANTLR start "rule__MenuItem__Group_4__0__Impl"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:932:1: rule__MenuItem__Group_4__0__Impl : ( 'securityRole' ) ;
    public final void rule__MenuItem__Group_4__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:936:1: ( ( 'securityRole' ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:937:1: ( 'securityRole' )
            {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:937:1: ( 'securityRole' )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:938:1: 'securityRole'
            {
             before(grammarAccess.getMenuItemAccess().getSecurityRoleKeyword_4_0()); 
            match(input,19,FOLLOW_19_in_rule__MenuItem__Group_4__0__Impl1846);
             after(grammarAccess.getMenuItemAccess().getSecurityRoleKeyword_4_0()); 

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
    // $ANTLR end "rule__MenuItem__Group_4__0__Impl"


    // $ANTLR start "rule__MenuItem__Group_4__1"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:951:1: rule__MenuItem__Group_4__1 : rule__MenuItem__Group_4__1__Impl rule__MenuItem__Group_4__2 ;
    public final void rule__MenuItem__Group_4__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:955:1: ( rule__MenuItem__Group_4__1__Impl rule__MenuItem__Group_4__2 )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:956:2: rule__MenuItem__Group_4__1__Impl rule__MenuItem__Group_4__2
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_4__1__Impl_in_rule__MenuItem__Group_4__11877);
            rule__MenuItem__Group_4__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuItem__Group_4__2_in_rule__MenuItem__Group_4__11880);
            rule__MenuItem__Group_4__2();

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
    // $ANTLR end "rule__MenuItem__Group_4__1"


    // $ANTLR start "rule__MenuItem__Group_4__1__Impl"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:963:1: rule__MenuItem__Group_4__1__Impl : ( '=' ) ;
    public final void rule__MenuItem__Group_4__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:967:1: ( ( '=' ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:968:1: ( '=' )
            {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:968:1: ( '=' )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:969:1: '='
            {
             before(grammarAccess.getMenuItemAccess().getEqualsSignKeyword_4_1()); 
            match(input,15,FOLLOW_15_in_rule__MenuItem__Group_4__1__Impl1908);
             after(grammarAccess.getMenuItemAccess().getEqualsSignKeyword_4_1()); 

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
    // $ANTLR end "rule__MenuItem__Group_4__1__Impl"


    // $ANTLR start "rule__MenuItem__Group_4__2"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:982:1: rule__MenuItem__Group_4__2 : rule__MenuItem__Group_4__2__Impl ;
    public final void rule__MenuItem__Group_4__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:986:1: ( rule__MenuItem__Group_4__2__Impl )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:987:2: rule__MenuItem__Group_4__2__Impl
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_4__2__Impl_in_rule__MenuItem__Group_4__21939);
            rule__MenuItem__Group_4__2__Impl();

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
    // $ANTLR end "rule__MenuItem__Group_4__2"


    // $ANTLR start "rule__MenuItem__Group_4__2__Impl"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:993:1: rule__MenuItem__Group_4__2__Impl : ( ( rule__MenuItem__SecurityRoleAssignment_4_2 ) ) ;
    public final void rule__MenuItem__Group_4__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:997:1: ( ( ( rule__MenuItem__SecurityRoleAssignment_4_2 ) ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:998:1: ( ( rule__MenuItem__SecurityRoleAssignment_4_2 ) )
            {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:998:1: ( ( rule__MenuItem__SecurityRoleAssignment_4_2 ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:999:1: ( rule__MenuItem__SecurityRoleAssignment_4_2 )
            {
             before(grammarAccess.getMenuItemAccess().getSecurityRoleAssignment_4_2()); 
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1000:1: ( rule__MenuItem__SecurityRoleAssignment_4_2 )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1000:2: rule__MenuItem__SecurityRoleAssignment_4_2
            {
            pushFollow(FOLLOW_rule__MenuItem__SecurityRoleAssignment_4_2_in_rule__MenuItem__Group_4__2__Impl1966);
            rule__MenuItem__SecurityRoleAssignment_4_2();

            state._fsp--;


            }

             after(grammarAccess.getMenuItemAccess().getSecurityRoleAssignment_4_2()); 

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
    // $ANTLR end "rule__MenuItem__Group_4__2__Impl"


    // $ANTLR start "rule__MenuItem__Group_5__0"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1016:1: rule__MenuItem__Group_5__0 : rule__MenuItem__Group_5__0__Impl rule__MenuItem__Group_5__1 ;
    public final void rule__MenuItem__Group_5__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1020:1: ( rule__MenuItem__Group_5__0__Impl rule__MenuItem__Group_5__1 )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1021:2: rule__MenuItem__Group_5__0__Impl rule__MenuItem__Group_5__1
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_5__0__Impl_in_rule__MenuItem__Group_5__02002);
            rule__MenuItem__Group_5__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuItem__Group_5__1_in_rule__MenuItem__Group_5__02005);
            rule__MenuItem__Group_5__1();

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
    // $ANTLR end "rule__MenuItem__Group_5__0"


    // $ANTLR start "rule__MenuItem__Group_5__0__Impl"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1028:1: rule__MenuItem__Group_5__0__Impl : ( 'labels' ) ;
    public final void rule__MenuItem__Group_5__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1032:1: ( ( 'labels' ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1033:1: ( 'labels' )
            {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1033:1: ( 'labels' )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1034:1: 'labels'
            {
             before(grammarAccess.getMenuItemAccess().getLabelsKeyword_5_0()); 
            match(input,20,FOLLOW_20_in_rule__MenuItem__Group_5__0__Impl2033);
             after(grammarAccess.getMenuItemAccess().getLabelsKeyword_5_0()); 

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
    // $ANTLR end "rule__MenuItem__Group_5__0__Impl"


    // $ANTLR start "rule__MenuItem__Group_5__1"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1047:1: rule__MenuItem__Group_5__1 : rule__MenuItem__Group_5__1__Impl rule__MenuItem__Group_5__2 ;
    public final void rule__MenuItem__Group_5__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1051:1: ( rule__MenuItem__Group_5__1__Impl rule__MenuItem__Group_5__2 )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1052:2: rule__MenuItem__Group_5__1__Impl rule__MenuItem__Group_5__2
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_5__1__Impl_in_rule__MenuItem__Group_5__12064);
            rule__MenuItem__Group_5__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuItem__Group_5__2_in_rule__MenuItem__Group_5__12067);
            rule__MenuItem__Group_5__2();

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
    // $ANTLR end "rule__MenuItem__Group_5__1"


    // $ANTLR start "rule__MenuItem__Group_5__1__Impl"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1059:1: rule__MenuItem__Group_5__1__Impl : ( ( rule__MenuItem__LabelsAssignment_5_1 ) ) ;
    public final void rule__MenuItem__Group_5__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1063:1: ( ( ( rule__MenuItem__LabelsAssignment_5_1 ) ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1064:1: ( ( rule__MenuItem__LabelsAssignment_5_1 ) )
            {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1064:1: ( ( rule__MenuItem__LabelsAssignment_5_1 ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1065:1: ( rule__MenuItem__LabelsAssignment_5_1 )
            {
             before(grammarAccess.getMenuItemAccess().getLabelsAssignment_5_1()); 
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1066:1: ( rule__MenuItem__LabelsAssignment_5_1 )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1066:2: rule__MenuItem__LabelsAssignment_5_1
            {
            pushFollow(FOLLOW_rule__MenuItem__LabelsAssignment_5_1_in_rule__MenuItem__Group_5__1__Impl2094);
            rule__MenuItem__LabelsAssignment_5_1();

            state._fsp--;


            }

             after(grammarAccess.getMenuItemAccess().getLabelsAssignment_5_1()); 

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
    // $ANTLR end "rule__MenuItem__Group_5__1__Impl"


    // $ANTLR start "rule__MenuItem__Group_5__2"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1076:1: rule__MenuItem__Group_5__2 : rule__MenuItem__Group_5__2__Impl ;
    public final void rule__MenuItem__Group_5__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1080:1: ( rule__MenuItem__Group_5__2__Impl )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1081:2: rule__MenuItem__Group_5__2__Impl
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_5__2__Impl_in_rule__MenuItem__Group_5__22124);
            rule__MenuItem__Group_5__2__Impl();

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
    // $ANTLR end "rule__MenuItem__Group_5__2"


    // $ANTLR start "rule__MenuItem__Group_5__2__Impl"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1087:1: rule__MenuItem__Group_5__2__Impl : ( ( rule__MenuItem__Group_5_2__0 )* ) ;
    public final void rule__MenuItem__Group_5__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1091:1: ( ( ( rule__MenuItem__Group_5_2__0 )* ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1092:1: ( ( rule__MenuItem__Group_5_2__0 )* )
            {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1092:1: ( ( rule__MenuItem__Group_5_2__0 )* )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1093:1: ( rule__MenuItem__Group_5_2__0 )*
            {
             before(grammarAccess.getMenuItemAccess().getGroup_5_2()); 
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1094:1: ( rule__MenuItem__Group_5_2__0 )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==21) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
		    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1094:2: rule__MenuItem__Group_5_2__0
            	    {
		    pushFollow(FOLLOW_rule__MenuItem__Group_5_2__0_in_rule__MenuItem__Group_5__2__Impl2151);
            	    rule__MenuItem__Group_5_2__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
		    break loop10;
                }
            } while (true);

             after(grammarAccess.getMenuItemAccess().getGroup_5_2()); 

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
    // $ANTLR end "rule__MenuItem__Group_5__2__Impl"


    // $ANTLR start "rule__MenuItem__Group_5_2__0"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1110:1: rule__MenuItem__Group_5_2__0 : rule__MenuItem__Group_5_2__0__Impl rule__MenuItem__Group_5_2__1 ;
    public final void rule__MenuItem__Group_5_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1114:1: ( rule__MenuItem__Group_5_2__0__Impl rule__MenuItem__Group_5_2__1 )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1115:2: rule__MenuItem__Group_5_2__0__Impl rule__MenuItem__Group_5_2__1
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_5_2__0__Impl_in_rule__MenuItem__Group_5_2__02188);
            rule__MenuItem__Group_5_2__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuItem__Group_5_2__1_in_rule__MenuItem__Group_5_2__02191);
            rule__MenuItem__Group_5_2__1();

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
    // $ANTLR end "rule__MenuItem__Group_5_2__0"


    // $ANTLR start "rule__MenuItem__Group_5_2__0__Impl"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1122:1: rule__MenuItem__Group_5_2__0__Impl : ( ',' ) ;
    public final void rule__MenuItem__Group_5_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1126:1: ( ( ',' ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1127:1: ( ',' )
            {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1127:1: ( ',' )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1128:1: ','
            {
             before(grammarAccess.getMenuItemAccess().getCommaKeyword_5_2_0()); 
            match(input,21,FOLLOW_21_in_rule__MenuItem__Group_5_2__0__Impl2219);
             after(grammarAccess.getMenuItemAccess().getCommaKeyword_5_2_0()); 

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
    // $ANTLR end "rule__MenuItem__Group_5_2__0__Impl"


    // $ANTLR start "rule__MenuItem__Group_5_2__1"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1141:1: rule__MenuItem__Group_5_2__1 : rule__MenuItem__Group_5_2__1__Impl ;
    public final void rule__MenuItem__Group_5_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1145:1: ( rule__MenuItem__Group_5_2__1__Impl )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1146:2: rule__MenuItem__Group_5_2__1__Impl
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_5_2__1__Impl_in_rule__MenuItem__Group_5_2__12250);
            rule__MenuItem__Group_5_2__1__Impl();

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
    // $ANTLR end "rule__MenuItem__Group_5_2__1"


    // $ANTLR start "rule__MenuItem__Group_5_2__1__Impl"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1152:1: rule__MenuItem__Group_5_2__1__Impl : ( ( rule__MenuItem__LabelsAssignment_5_2_1 ) ) ;
    public final void rule__MenuItem__Group_5_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1156:1: ( ( ( rule__MenuItem__LabelsAssignment_5_2_1 ) ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1157:1: ( ( rule__MenuItem__LabelsAssignment_5_2_1 ) )
            {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1157:1: ( ( rule__MenuItem__LabelsAssignment_5_2_1 ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1158:1: ( rule__MenuItem__LabelsAssignment_5_2_1 )
            {
             before(grammarAccess.getMenuItemAccess().getLabelsAssignment_5_2_1()); 
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1159:1: ( rule__MenuItem__LabelsAssignment_5_2_1 )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1159:2: rule__MenuItem__LabelsAssignment_5_2_1
            {
            pushFollow(FOLLOW_rule__MenuItem__LabelsAssignment_5_2_1_in_rule__MenuItem__Group_5_2__1__Impl2277);
            rule__MenuItem__LabelsAssignment_5_2_1();

            state._fsp--;


            }

             after(grammarAccess.getMenuItemAccess().getLabelsAssignment_5_2_1()); 

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
    // $ANTLR end "rule__MenuItem__Group_5_2__1__Impl"


    // $ANTLR start "rule__MenuItem__Group_6__0"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1173:1: rule__MenuItem__Group_6__0 : rule__MenuItem__Group_6__0__Impl rule__MenuItem__Group_6__1 ;
    public final void rule__MenuItem__Group_6__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1177:1: ( rule__MenuItem__Group_6__0__Impl rule__MenuItem__Group_6__1 )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1178:2: rule__MenuItem__Group_6__0__Impl rule__MenuItem__Group_6__1
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_6__0__Impl_in_rule__MenuItem__Group_6__02311);
            rule__MenuItem__Group_6__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuItem__Group_6__1_in_rule__MenuItem__Group_6__02314);
            rule__MenuItem__Group_6__1();

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
    // $ANTLR end "rule__MenuItem__Group_6__0"


    // $ANTLR start "rule__MenuItem__Group_6__0__Impl"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1185:1: rule__MenuItem__Group_6__0__Impl : ( '{' ) ;
    public final void rule__MenuItem__Group_6__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1189:1: ( ( '{' ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1190:1: ( '{' )
            {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1190:1: ( '{' )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1191:1: '{'
            {
             before(grammarAccess.getMenuItemAccess().getLeftCurlyBracketKeyword_6_0()); 
            match(input,22,FOLLOW_22_in_rule__MenuItem__Group_6__0__Impl2342);
             after(grammarAccess.getMenuItemAccess().getLeftCurlyBracketKeyword_6_0()); 

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
    // $ANTLR end "rule__MenuItem__Group_6__0__Impl"


    // $ANTLR start "rule__MenuItem__Group_6__1"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1204:1: rule__MenuItem__Group_6__1 : rule__MenuItem__Group_6__1__Impl rule__MenuItem__Group_6__2 ;
    public final void rule__MenuItem__Group_6__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1208:1: ( rule__MenuItem__Group_6__1__Impl rule__MenuItem__Group_6__2 )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1209:2: rule__MenuItem__Group_6__1__Impl rule__MenuItem__Group_6__2
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_6__1__Impl_in_rule__MenuItem__Group_6__12373);
            rule__MenuItem__Group_6__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuItem__Group_6__2_in_rule__MenuItem__Group_6__12376);
            rule__MenuItem__Group_6__2();

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
    // $ANTLR end "rule__MenuItem__Group_6__1"


    // $ANTLR start "rule__MenuItem__Group_6__1__Impl"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1216:1: rule__MenuItem__Group_6__1__Impl : ( ( rule__MenuItem__Group_6_1__0 )? ) ;
    public final void rule__MenuItem__Group_6__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1220:1: ( ( ( rule__MenuItem__Group_6_1__0 )? ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1221:1: ( ( rule__MenuItem__Group_6_1__0 )? )
            {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1221:1: ( ( rule__MenuItem__Group_6_1__0 )? )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1222:1: ( rule__MenuItem__Group_6_1__0 )?
            {
             before(grammarAccess.getMenuItemAccess().getGroup_6_1()); 
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1223:1: ( rule__MenuItem__Group_6_1__0 )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( ((LA11_0>=RULE_STRING && LA11_0<=RULE_URI)) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1223:2: rule__MenuItem__Group_6_1__0
                    {
                    pushFollow(FOLLOW_rule__MenuItem__Group_6_1__0_in_rule__MenuItem__Group_6__1__Impl2403);
                    rule__MenuItem__Group_6_1__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getMenuItemAccess().getGroup_6_1()); 

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
    // $ANTLR end "rule__MenuItem__Group_6__1__Impl"


    // $ANTLR start "rule__MenuItem__Group_6__2"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1233:1: rule__MenuItem__Group_6__2 : rule__MenuItem__Group_6__2__Impl ;
    public final void rule__MenuItem__Group_6__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1237:1: ( rule__MenuItem__Group_6__2__Impl )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1238:2: rule__MenuItem__Group_6__2__Impl
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_6__2__Impl_in_rule__MenuItem__Group_6__22434);
            rule__MenuItem__Group_6__2__Impl();

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
    // $ANTLR end "rule__MenuItem__Group_6__2"


    // $ANTLR start "rule__MenuItem__Group_6__2__Impl"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1244:1: rule__MenuItem__Group_6__2__Impl : ( '}' ) ;
    public final void rule__MenuItem__Group_6__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1248:1: ( ( '}' ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1249:1: ( '}' )
            {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1249:1: ( '}' )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1250:1: '}'
            {
             before(grammarAccess.getMenuItemAccess().getRightCurlyBracketKeyword_6_2()); 
            match(input,23,FOLLOW_23_in_rule__MenuItem__Group_6__2__Impl2462);
             after(grammarAccess.getMenuItemAccess().getRightCurlyBracketKeyword_6_2()); 

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
    // $ANTLR end "rule__MenuItem__Group_6__2__Impl"


    // $ANTLR start "rule__MenuItem__Group_6_1__0"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1269:1: rule__MenuItem__Group_6_1__0 : rule__MenuItem__Group_6_1__0__Impl rule__MenuItem__Group_6_1__1 ;
    public final void rule__MenuItem__Group_6_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1273:1: ( rule__MenuItem__Group_6_1__0__Impl rule__MenuItem__Group_6_1__1 )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1274:2: rule__MenuItem__Group_6_1__0__Impl rule__MenuItem__Group_6_1__1
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_6_1__0__Impl_in_rule__MenuItem__Group_6_1__02499);
            rule__MenuItem__Group_6_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuItem__Group_6_1__1_in_rule__MenuItem__Group_6_1__02502);
            rule__MenuItem__Group_6_1__1();

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
    // $ANTLR end "rule__MenuItem__Group_6_1__0"


    // $ANTLR start "rule__MenuItem__Group_6_1__0__Impl"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1281:1: rule__MenuItem__Group_6_1__0__Impl : ( ( rule__MenuItem__SubmenusAssignment_6_1_0 ) ) ;
    public final void rule__MenuItem__Group_6_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1285:1: ( ( ( rule__MenuItem__SubmenusAssignment_6_1_0 ) ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1286:1: ( ( rule__MenuItem__SubmenusAssignment_6_1_0 ) )
            {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1286:1: ( ( rule__MenuItem__SubmenusAssignment_6_1_0 ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1287:1: ( rule__MenuItem__SubmenusAssignment_6_1_0 )
            {
             before(grammarAccess.getMenuItemAccess().getSubmenusAssignment_6_1_0()); 
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1288:1: ( rule__MenuItem__SubmenusAssignment_6_1_0 )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1288:2: rule__MenuItem__SubmenusAssignment_6_1_0
            {
            pushFollow(FOLLOW_rule__MenuItem__SubmenusAssignment_6_1_0_in_rule__MenuItem__Group_6_1__0__Impl2529);
            rule__MenuItem__SubmenusAssignment_6_1_0();

            state._fsp--;


            }

             after(grammarAccess.getMenuItemAccess().getSubmenusAssignment_6_1_0()); 

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
    // $ANTLR end "rule__MenuItem__Group_6_1__0__Impl"


    // $ANTLR start "rule__MenuItem__Group_6_1__1"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1298:1: rule__MenuItem__Group_6_1__1 : rule__MenuItem__Group_6_1__1__Impl ;
    public final void rule__MenuItem__Group_6_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1302:1: ( rule__MenuItem__Group_6_1__1__Impl )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1303:2: rule__MenuItem__Group_6_1__1__Impl
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_6_1__1__Impl_in_rule__MenuItem__Group_6_1__12559);
            rule__MenuItem__Group_6_1__1__Impl();

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
    // $ANTLR end "rule__MenuItem__Group_6_1__1"


    // $ANTLR start "rule__MenuItem__Group_6_1__1__Impl"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1309:1: rule__MenuItem__Group_6_1__1__Impl : ( ( rule__MenuItem__SubmenusAssignment_6_1_1 )* ) ;
    public final void rule__MenuItem__Group_6_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1313:1: ( ( ( rule__MenuItem__SubmenusAssignment_6_1_1 )* ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1314:1: ( ( rule__MenuItem__SubmenusAssignment_6_1_1 )* )
            {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1314:1: ( ( rule__MenuItem__SubmenusAssignment_6_1_1 )* )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1315:1: ( rule__MenuItem__SubmenusAssignment_6_1_1 )*
            {
             before(grammarAccess.getMenuItemAccess().getSubmenusAssignment_6_1_1()); 
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1316:1: ( rule__MenuItem__SubmenusAssignment_6_1_1 )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( ((LA12_0>=RULE_STRING && LA12_0<=RULE_URI)) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
		    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1316:2: rule__MenuItem__SubmenusAssignment_6_1_1
            	    {
		    pushFollow(FOLLOW_rule__MenuItem__SubmenusAssignment_6_1_1_in_rule__MenuItem__Group_6_1__1__Impl2586);
            	    rule__MenuItem__SubmenusAssignment_6_1_1();

            	    state._fsp--;


            	    }
            	    break;

            	default :
		    break loop12;
                }
            } while (true);

             after(grammarAccess.getMenuItemAccess().getSubmenusAssignment_6_1_1()); 

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
    // $ANTLR end "rule__MenuItem__Group_6_1__1__Impl"


    // $ANTLR start "rule__MenuItem__Group_7__0"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1330:1: rule__MenuItem__Group_7__0 : rule__MenuItem__Group_7__0__Impl rule__MenuItem__Group_7__1 ;
    public final void rule__MenuItem__Group_7__0() throws RecognitionException {

			int stackSize = keepStackSize();

        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1334:1: ( rule__MenuItem__Group_7__0__Impl rule__MenuItem__Group_7__1 )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1335:2: rule__MenuItem__Group_7__0__Impl rule__MenuItem__Group_7__1
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_7__0__Impl_in_rule__MenuItem__Group_7__02621);
            rule__MenuItem__Group_7__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuItem__Group_7__1_in_rule__MenuItem__Group_7__02624);
            rule__MenuItem__Group_7__1();

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
    // $ANTLR end "rule__MenuItem__Group_7__0"


    // $ANTLR start "rule__MenuItem__Group_7__0__Impl"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1342:1: rule__MenuItem__Group_7__0__Impl : ( 'shortcut' ) ;
    public final void rule__MenuItem__Group_7__0__Impl() throws RecognitionException {

			int stackSize = keepStackSize();

        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1346:1: ( ( 'shortcut' ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1347:1: ( 'shortcut' )
            {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1347:1: ( 'shortcut' )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1348:1: 'shortcut'
            {
             before(grammarAccess.getMenuItemAccess().getShortcutKeyword_7_0());
            match(input,24,FOLLOW_24_in_rule__MenuItem__Group_7__0__Impl2652);
             after(grammarAccess.getMenuItemAccess().getShortcutKeyword_7_0());

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
    // $ANTLR end "rule__MenuItem__Group_7__0__Impl"


    // $ANTLR start "rule__MenuItem__Group_7__1"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1361:1: rule__MenuItem__Group_7__1 : rule__MenuItem__Group_7__1__Impl rule__MenuItem__Group_7__2 ;
    public final void rule__MenuItem__Group_7__1() throws RecognitionException {

			int stackSize = keepStackSize();

        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1365:1: ( rule__MenuItem__Group_7__1__Impl rule__MenuItem__Group_7__2 )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1366:2: rule__MenuItem__Group_7__1__Impl rule__MenuItem__Group_7__2
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_7__1__Impl_in_rule__MenuItem__Group_7__12683);
            rule__MenuItem__Group_7__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuItem__Group_7__2_in_rule__MenuItem__Group_7__12686);
            rule__MenuItem__Group_7__2();

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
    // $ANTLR end "rule__MenuItem__Group_7__1"


    // $ANTLR start "rule__MenuItem__Group_7__1__Impl"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1373:1: rule__MenuItem__Group_7__1__Impl : ( '=' ) ;
    public final void rule__MenuItem__Group_7__1__Impl() throws RecognitionException {

			int stackSize = keepStackSize();

        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1377:1: ( ( '=' ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1378:1: ( '=' )
            {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1378:1: ( '=' )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1379:1: '='
            {
             before(grammarAccess.getMenuItemAccess().getEqualsSignKeyword_7_1());
            match(input,15,FOLLOW_15_in_rule__MenuItem__Group_7__1__Impl2714);
             after(grammarAccess.getMenuItemAccess().getEqualsSignKeyword_7_1());

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
    // $ANTLR end "rule__MenuItem__Group_7__1__Impl"


    // $ANTLR start "rule__MenuItem__Group_7__2"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1392:1: rule__MenuItem__Group_7__2 : rule__MenuItem__Group_7__2__Impl ;
    public final void rule__MenuItem__Group_7__2() throws RecognitionException {

			int stackSize = keepStackSize();

        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1396:1: ( rule__MenuItem__Group_7__2__Impl )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1397:2: rule__MenuItem__Group_7__2__Impl
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_7__2__Impl_in_rule__MenuItem__Group_7__22745);
            rule__MenuItem__Group_7__2__Impl();

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
    // $ANTLR end "rule__MenuItem__Group_7__2"


    // $ANTLR start "rule__MenuItem__Group_7__2__Impl"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1403:1: rule__MenuItem__Group_7__2__Impl : ( ( rule__MenuItem__ShortcutAssignment_7_2 ) ) ;
    public final void rule__MenuItem__Group_7__2__Impl() throws RecognitionException {

			int stackSize = keepStackSize();

        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1407:1: ( ( ( rule__MenuItem__ShortcutAssignment_7_2 ) ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1408:1: ( ( rule__MenuItem__ShortcutAssignment_7_2 ) )
            {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1408:1: ( ( rule__MenuItem__ShortcutAssignment_7_2 ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1409:1: ( rule__MenuItem__ShortcutAssignment_7_2 )
            {
             before(grammarAccess.getMenuItemAccess().getShortcutAssignment_7_2());
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1410:1: ( rule__MenuItem__ShortcutAssignment_7_2 )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1410:2: rule__MenuItem__ShortcutAssignment_7_2
            {
            pushFollow(FOLLOW_rule__MenuItem__ShortcutAssignment_7_2_in_rule__MenuItem__Group_7__2__Impl2772);
            rule__MenuItem__ShortcutAssignment_7_2();

            state._fsp--;


            }

             after(grammarAccess.getMenuItemAccess().getShortcutAssignment_7_2());

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
    // $ANTLR end "rule__MenuItem__Group_7__2__Impl"


    // $ANTLR start "rule__Translation__Group__0"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1426:1: rule__Translation__Group__0 : rule__Translation__Group__0__Impl rule__Translation__Group__1 ;
    public final void rule__Translation__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1430:1: ( rule__Translation__Group__0__Impl rule__Translation__Group__1 )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1431:2: rule__Translation__Group__0__Impl rule__Translation__Group__1
            {
            pushFollow(FOLLOW_rule__Translation__Group__0__Impl_in_rule__Translation__Group__02808);
            rule__Translation__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Translation__Group__1_in_rule__Translation__Group__02811);
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
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1438:1: rule__Translation__Group__0__Impl : ( ( rule__Translation__LanguageAssignment_0 ) ) ;
    public final void rule__Translation__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1442:1: ( ( ( rule__Translation__LanguageAssignment_0 ) ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1443:1: ( ( rule__Translation__LanguageAssignment_0 ) )
            {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1443:1: ( ( rule__Translation__LanguageAssignment_0 ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1444:1: ( rule__Translation__LanguageAssignment_0 )
            {
             before(grammarAccess.getTranslationAccess().getLanguageAssignment_0()); 
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1445:1: ( rule__Translation__LanguageAssignment_0 )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1445:2: rule__Translation__LanguageAssignment_0
            {
            pushFollow(FOLLOW_rule__Translation__LanguageAssignment_0_in_rule__Translation__Group__0__Impl2838);
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
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1455:1: rule__Translation__Group__1 : rule__Translation__Group__1__Impl rule__Translation__Group__2 ;
    public final void rule__Translation__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1459:1: ( rule__Translation__Group__1__Impl rule__Translation__Group__2 )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1460:2: rule__Translation__Group__1__Impl rule__Translation__Group__2
            {
            pushFollow(FOLLOW_rule__Translation__Group__1__Impl_in_rule__Translation__Group__12868);
            rule__Translation__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Translation__Group__2_in_rule__Translation__Group__12871);
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
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1467:1: rule__Translation__Group__1__Impl : ( '=' ) ;
    public final void rule__Translation__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1471:1: ( ( '=' ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1472:1: ( '=' )
            {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1472:1: ( '=' )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1473:1: '='
            {
             before(grammarAccess.getTranslationAccess().getEqualsSignKeyword_1()); 
            match(input,15,FOLLOW_15_in_rule__Translation__Group__1__Impl2899);
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
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1486:1: rule__Translation__Group__2 : rule__Translation__Group__2__Impl ;
    public final void rule__Translation__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1490:1: ( rule__Translation__Group__2__Impl )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1491:2: rule__Translation__Group__2__Impl
            {
            pushFollow(FOLLOW_rule__Translation__Group__2__Impl_in_rule__Translation__Group__22930);
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
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1497:1: rule__Translation__Group__2__Impl : ( ( rule__Translation__MessageAssignment_2 ) ) ;
    public final void rule__Translation__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1501:1: ( ( ( rule__Translation__MessageAssignment_2 ) ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1502:1: ( ( rule__Translation__MessageAssignment_2 ) )
            {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1502:1: ( ( rule__Translation__MessageAssignment_2 ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1503:1: ( rule__Translation__MessageAssignment_2 )
            {
             before(grammarAccess.getTranslationAccess().getMessageAssignment_2()); 
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1504:1: ( rule__Translation__MessageAssignment_2 )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1504:2: rule__Translation__MessageAssignment_2
            {
            pushFollow(FOLLOW_rule__Translation__MessageAssignment_2_in_rule__Translation__Group__2__Impl2957);
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


    // $ANTLR start "rule__MenuRoot__MetamodelVersionAssignment_2"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1521:1: rule__MenuRoot__MetamodelVersionAssignment_2 : ( ruleString_Value ) ;
    public final void rule__MenuRoot__MetamodelVersionAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1525:1: ( ( ruleString_Value ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1526:1: ( ruleString_Value )
            {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1526:1: ( ruleString_Value )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1527:1: ruleString_Value
            {
             before(grammarAccess.getMenuRootAccess().getMetamodelVersionString_ValueParserRuleCall_2_0()); 
            pushFollow(FOLLOW_ruleString_Value_in_rule__MenuRoot__MetamodelVersionAssignment_22998);
            ruleString_Value();

            state._fsp--;

             after(grammarAccess.getMenuRootAccess().getMetamodelVersionString_ValueParserRuleCall_2_0()); 

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
    // $ANTLR end "rule__MenuRoot__MetamodelVersionAssignment_2"


    // $ANTLR start "rule__MenuRoot__RootItemAssignment_3"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1536:1: rule__MenuRoot__RootItemAssignment_3 : ( ruleMenuItem ) ;
    public final void rule__MenuRoot__RootItemAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1540:1: ( ( ruleMenuItem ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1541:1: ( ruleMenuItem )
            {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1541:1: ( ruleMenuItem )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1542:1: ruleMenuItem
            {
             before(grammarAccess.getMenuRootAccess().getRootItemMenuItemParserRuleCall_3_0()); 
            pushFollow(FOLLOW_ruleMenuItem_in_rule__MenuRoot__RootItemAssignment_33029);
            ruleMenuItem();

            state._fsp--;

             after(grammarAccess.getMenuRootAccess().getRootItemMenuItemParserRuleCall_3_0()); 

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
    // $ANTLR end "rule__MenuRoot__RootItemAssignment_3"


    // $ANTLR start "rule__MenuItem__NameAssignment_0"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1551:1: rule__MenuItem__NameAssignment_0 : ( ruleString_Value ) ;
    public final void rule__MenuItem__NameAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1555:1: ( ( ruleString_Value ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1556:1: ( ruleString_Value )
            {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1556:1: ( ruleString_Value )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1557:1: ruleString_Value
            {
             before(grammarAccess.getMenuItemAccess().getNameString_ValueParserRuleCall_0_0()); 
            pushFollow(FOLLOW_ruleString_Value_in_rule__MenuItem__NameAssignment_03060);
            ruleString_Value();

            state._fsp--;

             after(grammarAccess.getMenuItemAccess().getNameString_ValueParserRuleCall_0_0()); 

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
    // $ANTLR end "rule__MenuItem__NameAssignment_0"


    // $ANTLR start "rule__MenuItem__PageflowAssignment_1_2"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1566:1: rule__MenuItem__PageflowAssignment_1_2 : ( ruleString_Value ) ;
    public final void rule__MenuItem__PageflowAssignment_1_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1570:1: ( ( ruleString_Value ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1571:1: ( ruleString_Value )
            {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1571:1: ( ruleString_Value )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1572:1: ruleString_Value
            {
             before(grammarAccess.getMenuItemAccess().getPageflowString_ValueParserRuleCall_1_2_0()); 
            pushFollow(FOLLOW_ruleString_Value_in_rule__MenuItem__PageflowAssignment_1_23091);
            ruleString_Value();

            state._fsp--;

             after(grammarAccess.getMenuItemAccess().getPageflowString_ValueParserRuleCall_1_2_0()); 

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
    // $ANTLR end "rule__MenuItem__PageflowAssignment_1_2"


    // $ANTLR start "rule__MenuItem__EnabledAssignment_2_2"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1581:1: rule__MenuItem__EnabledAssignment_2_2 : ( ruleEnabled ) ;
    public final void rule__MenuItem__EnabledAssignment_2_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1585:1: ( ( ruleEnabled ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1586:1: ( ruleEnabled )
            {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1586:1: ( ruleEnabled )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1587:1: ruleEnabled
            {
             before(grammarAccess.getMenuItemAccess().getEnabledEnabledEnumRuleCall_2_2_0()); 
            pushFollow(FOLLOW_ruleEnabled_in_rule__MenuItem__EnabledAssignment_2_23122);
            ruleEnabled();

            state._fsp--;

             after(grammarAccess.getMenuItemAccess().getEnabledEnabledEnumRuleCall_2_2_0()); 

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
    // $ANTLR end "rule__MenuItem__EnabledAssignment_2_2"


    // $ANTLR start "rule__MenuItem__DisplayTabsAssignment_3_2"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1596:1: rule__MenuItem__DisplayTabsAssignment_3_2 : ( ( 'true' ) ) ;
    public final void rule__MenuItem__DisplayTabsAssignment_3_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1600:1: ( ( ( 'true' ) ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1601:1: ( ( 'true' ) )
            {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1601:1: ( ( 'true' ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1602:1: ( 'true' )
            {
             before(grammarAccess.getMenuItemAccess().getDisplayTabsTrueKeyword_3_2_0()); 
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1603:1: ( 'true' )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1604:1: 'true'
            {
             before(grammarAccess.getMenuItemAccess().getDisplayTabsTrueKeyword_3_2_0()); 
            match(input,11,FOLLOW_11_in_rule__MenuItem__DisplayTabsAssignment_3_23158);
             after(grammarAccess.getMenuItemAccess().getDisplayTabsTrueKeyword_3_2_0()); 

            }

             after(grammarAccess.getMenuItemAccess().getDisplayTabsTrueKeyword_3_2_0()); 

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
    // $ANTLR end "rule__MenuItem__DisplayTabsAssignment_3_2"


    // $ANTLR start "rule__MenuItem__SecurityRoleAssignment_4_2"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1619:1: rule__MenuItem__SecurityRoleAssignment_4_2 : ( ruleString_Value ) ;
    public final void rule__MenuItem__SecurityRoleAssignment_4_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1623:1: ( ( ruleString_Value ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1624:1: ( ruleString_Value )
            {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1624:1: ( ruleString_Value )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1625:1: ruleString_Value
            {
             before(grammarAccess.getMenuItemAccess().getSecurityRoleString_ValueParserRuleCall_4_2_0()); 
            pushFollow(FOLLOW_ruleString_Value_in_rule__MenuItem__SecurityRoleAssignment_4_23197);
            ruleString_Value();

            state._fsp--;

             after(grammarAccess.getMenuItemAccess().getSecurityRoleString_ValueParserRuleCall_4_2_0()); 

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
    // $ANTLR end "rule__MenuItem__SecurityRoleAssignment_4_2"


    // $ANTLR start "rule__MenuItem__LabelsAssignment_5_1"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1634:1: rule__MenuItem__LabelsAssignment_5_1 : ( ruleTranslation ) ;
    public final void rule__MenuItem__LabelsAssignment_5_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1638:1: ( ( ruleTranslation ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1639:1: ( ruleTranslation )
            {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1639:1: ( ruleTranslation )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1640:1: ruleTranslation
            {
             before(grammarAccess.getMenuItemAccess().getLabelsTranslationParserRuleCall_5_1_0()); 
            pushFollow(FOLLOW_ruleTranslation_in_rule__MenuItem__LabelsAssignment_5_13228);
            ruleTranslation();

            state._fsp--;

             after(grammarAccess.getMenuItemAccess().getLabelsTranslationParserRuleCall_5_1_0()); 

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
    // $ANTLR end "rule__MenuItem__LabelsAssignment_5_1"


    // $ANTLR start "rule__MenuItem__LabelsAssignment_5_2_1"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1649:1: rule__MenuItem__LabelsAssignment_5_2_1 : ( ruleTranslation ) ;
    public final void rule__MenuItem__LabelsAssignment_5_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1653:1: ( ( ruleTranslation ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1654:1: ( ruleTranslation )
            {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1654:1: ( ruleTranslation )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1655:1: ruleTranslation
            {
             before(grammarAccess.getMenuItemAccess().getLabelsTranslationParserRuleCall_5_2_1_0()); 
            pushFollow(FOLLOW_ruleTranslation_in_rule__MenuItem__LabelsAssignment_5_2_13259);
            ruleTranslation();

            state._fsp--;

             after(grammarAccess.getMenuItemAccess().getLabelsTranslationParserRuleCall_5_2_1_0()); 

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
    // $ANTLR end "rule__MenuItem__LabelsAssignment_5_2_1"


    // $ANTLR start "rule__MenuItem__SubmenusAssignment_6_1_0"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1664:1: rule__MenuItem__SubmenusAssignment_6_1_0 : ( ruleMenuItem ) ;
    public final void rule__MenuItem__SubmenusAssignment_6_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1668:1: ( ( ruleMenuItem ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1669:1: ( ruleMenuItem )
            {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1669:1: ( ruleMenuItem )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1670:1: ruleMenuItem
            {
             before(grammarAccess.getMenuItemAccess().getSubmenusMenuItemParserRuleCall_6_1_0_0()); 
            pushFollow(FOLLOW_ruleMenuItem_in_rule__MenuItem__SubmenusAssignment_6_1_03290);
            ruleMenuItem();

            state._fsp--;

             after(grammarAccess.getMenuItemAccess().getSubmenusMenuItemParserRuleCall_6_1_0_0()); 

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
    // $ANTLR end "rule__MenuItem__SubmenusAssignment_6_1_0"


    // $ANTLR start "rule__MenuItem__SubmenusAssignment_6_1_1"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1679:1: rule__MenuItem__SubmenusAssignment_6_1_1 : ( ruleMenuItem ) ;
    public final void rule__MenuItem__SubmenusAssignment_6_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1683:1: ( ( ruleMenuItem ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1684:1: ( ruleMenuItem )
            {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1684:1: ( ruleMenuItem )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1685:1: ruleMenuItem
            {
             before(grammarAccess.getMenuItemAccess().getSubmenusMenuItemParserRuleCall_6_1_1_0()); 
            pushFollow(FOLLOW_ruleMenuItem_in_rule__MenuItem__SubmenusAssignment_6_1_13321);
            ruleMenuItem();

            state._fsp--;

             after(grammarAccess.getMenuItemAccess().getSubmenusMenuItemParserRuleCall_6_1_1_0()); 

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
    // $ANTLR end "rule__MenuItem__SubmenusAssignment_6_1_1"


    // $ANTLR start "rule__MenuItem__ShortcutAssignment_7_2"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1694:1: rule__MenuItem__ShortcutAssignment_7_2 : ( ruleString_Value ) ;
    public final void rule__MenuItem__ShortcutAssignment_7_2() throws RecognitionException {

			int stackSize = keepStackSize();

        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1698:1: ( ( ruleString_Value ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1699:1: ( ruleString_Value )
            {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1699:1: ( ruleString_Value )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1700:1: ruleString_Value
            {
             before(grammarAccess.getMenuItemAccess().getShortcutString_ValueParserRuleCall_7_2_0());
            pushFollow(FOLLOW_ruleString_Value_in_rule__MenuItem__ShortcutAssignment_7_23352);
            ruleString_Value();

            state._fsp--;

             after(grammarAccess.getMenuItemAccess().getShortcutString_ValueParserRuleCall_7_2_0());

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
    // $ANTLR end "rule__MenuItem__ShortcutAssignment_7_2"


    // $ANTLR start "rule__Translation__LanguageAssignment_0"
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1709:1: rule__Translation__LanguageAssignment_0 : ( RULE_ID ) ;
    public final void rule__Translation__LanguageAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1713:1: ( ( RULE_ID ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1714:1: ( RULE_ID )
            {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1714:1: ( RULE_ID )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1715:1: RULE_ID
            {
             before(grammarAccess.getTranslationAccess().getLanguageIDTerminalRuleCall_0_0()); 
            match(input,RULE_ID,FOLLOW_RULE_ID_in_rule__Translation__LanguageAssignment_03383);
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
    // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1724:1: rule__Translation__MessageAssignment_2 : ( ruleString_Value ) ;
    public final void rule__Translation__MessageAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1728:1: ( ( ruleString_Value ) )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1729:1: ( ruleString_Value )
            {
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1729:1: ( ruleString_Value )
            // ../../ui/com.odcgroup.menu.model.ui/src-gen/com/odcgroup/menu/model/ui/contentassist/antlr/internal/InternalMenu.g:1730:1: ruleString_Value
            {
             before(grammarAccess.getTranslationAccess().getMessageString_ValueParserRuleCall_2_0()); 
            pushFollow(FOLLOW_ruleString_Value_in_rule__Translation__MessageAssignment_23414);
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

    // Delegated rules


 

    public static final BitSet FOLLOW_ruleMenuRoot_in_entryRuleMenuRoot61 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMenuRoot68 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuRoot__Group__0_in_ruleMenuRoot94 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMenuItem_in_entryRuleMenuItem121 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMenuItem128 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group__0_in_ruleMenuItem154 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTranslation_in_entryRuleTranslation181 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTranslation188 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Translation__Group__0_in_ruleTranslation214 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleString_Value_in_entryRuleString_Value241 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleString_Value248 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__String_Value__Alternatives_in_ruleString_Value274 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Enabled__Alternatives_in_ruleEnabled311 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_STRING_in_rule__String_Value__Alternatives346 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__String_Value__Alternatives363 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_VALUE_in_rule__String_Value__Alternatives380 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_URI_in_rule__String_Value__Alternatives397 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_11_in_rule__Enabled__Alternatives430 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_12_in_rule__Enabled__Alternatives451 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_13_in_rule__Enabled__Alternatives472 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuRoot__Group__0__Impl_in_rule__MenuRoot__Group__0505 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_rule__MenuRoot__Group__1_in_rule__MenuRoot__Group__0508 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_14_in_rule__MenuRoot__Group__0__Impl536 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuRoot__Group__1__Impl_in_rule__MenuRoot__Group__1567 = new BitSet(new long[]{0x00000000000000F0L});
    public static final BitSet FOLLOW_rule__MenuRoot__Group__2_in_rule__MenuRoot__Group__1570 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_15_in_rule__MenuRoot__Group__1__Impl598 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuRoot__Group__2__Impl_in_rule__MenuRoot__Group__2629 = new BitSet(new long[]{0x00000000000000F0L});
    public static final BitSet FOLLOW_rule__MenuRoot__Group__3_in_rule__MenuRoot__Group__2632 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuRoot__MetamodelVersionAssignment_2_in_rule__MenuRoot__Group__2__Impl659 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuRoot__Group__3__Impl_in_rule__MenuRoot__Group__3689 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuRoot__RootItemAssignment_3_in_rule__MenuRoot__Group__3__Impl716 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group__0__Impl_in_rule__MenuItem__Group__0754 = new BitSet(new long[]{0x00000000015F0000L});
    public static final BitSet FOLLOW_rule__MenuItem__Group__1_in_rule__MenuItem__Group__0757 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__NameAssignment_0_in_rule__MenuItem__Group__0__Impl784 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group__1__Impl_in_rule__MenuItem__Group__1814 = new BitSet(new long[]{0x00000000015F0000L});
    public static final BitSet FOLLOW_rule__MenuItem__Group__2_in_rule__MenuItem__Group__1817 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_1__0_in_rule__MenuItem__Group__1__Impl844 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group__2__Impl_in_rule__MenuItem__Group__2875 = new BitSet(new long[]{0x00000000015F0000L});
    public static final BitSet FOLLOW_rule__MenuItem__Group__3_in_rule__MenuItem__Group__2878 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_2__0_in_rule__MenuItem__Group__2__Impl905 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group__3__Impl_in_rule__MenuItem__Group__3936 = new BitSet(new long[]{0x00000000015F0000L});
    public static final BitSet FOLLOW_rule__MenuItem__Group__4_in_rule__MenuItem__Group__3939 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_3__0_in_rule__MenuItem__Group__3__Impl966 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group__4__Impl_in_rule__MenuItem__Group__4997 = new BitSet(new long[]{0x00000000015F0000L});
    public static final BitSet FOLLOW_rule__MenuItem__Group__5_in_rule__MenuItem__Group__41000 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_4__0_in_rule__MenuItem__Group__4__Impl1027 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group__5__Impl_in_rule__MenuItem__Group__51058 = new BitSet(new long[]{0x00000000015F0000L});
    public static final BitSet FOLLOW_rule__MenuItem__Group__6_in_rule__MenuItem__Group__51061 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_5__0_in_rule__MenuItem__Group__5__Impl1088 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group__6__Impl_in_rule__MenuItem__Group__61119 = new BitSet(new long[]{0x00000000015F0000L});
    public static final BitSet FOLLOW_rule__MenuItem__Group__7_in_rule__MenuItem__Group__61122 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_6__0_in_rule__MenuItem__Group__6__Impl1149 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group__7__Impl_in_rule__MenuItem__Group__71180 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_7__0_in_rule__MenuItem__Group__7__Impl1207 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_1__0__Impl_in_rule__MenuItem__Group_1__01254 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_1__1_in_rule__MenuItem__Group_1__01257 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_16_in_rule__MenuItem__Group_1__0__Impl1285 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_1__1__Impl_in_rule__MenuItem__Group_1__11316 = new BitSet(new long[]{0x00000000000000F0L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_1__2_in_rule__MenuItem__Group_1__11319 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_15_in_rule__MenuItem__Group_1__1__Impl1347 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_1__2__Impl_in_rule__MenuItem__Group_1__21378 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__PageflowAssignment_1_2_in_rule__MenuItem__Group_1__2__Impl1405 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_2__0__Impl_in_rule__MenuItem__Group_2__01441 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_2__1_in_rule__MenuItem__Group_2__01444 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_17_in_rule__MenuItem__Group_2__0__Impl1472 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_2__1__Impl_in_rule__MenuItem__Group_2__11503 = new BitSet(new long[]{0x0000000000003800L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_2__2_in_rule__MenuItem__Group_2__11506 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_15_in_rule__MenuItem__Group_2__1__Impl1534 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_2__2__Impl_in_rule__MenuItem__Group_2__21565 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__EnabledAssignment_2_2_in_rule__MenuItem__Group_2__2__Impl1592 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_3__0__Impl_in_rule__MenuItem__Group_3__01628 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_3__1_in_rule__MenuItem__Group_3__01631 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_18_in_rule__MenuItem__Group_3__0__Impl1659 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_3__1__Impl_in_rule__MenuItem__Group_3__11690 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_3__2_in_rule__MenuItem__Group_3__11693 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_15_in_rule__MenuItem__Group_3__1__Impl1721 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_3__2__Impl_in_rule__MenuItem__Group_3__21752 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__DisplayTabsAssignment_3_2_in_rule__MenuItem__Group_3__2__Impl1779 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_4__0__Impl_in_rule__MenuItem__Group_4__01815 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_4__1_in_rule__MenuItem__Group_4__01818 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_19_in_rule__MenuItem__Group_4__0__Impl1846 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_4__1__Impl_in_rule__MenuItem__Group_4__11877 = new BitSet(new long[]{0x00000000000000F0L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_4__2_in_rule__MenuItem__Group_4__11880 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_15_in_rule__MenuItem__Group_4__1__Impl1908 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_4__2__Impl_in_rule__MenuItem__Group_4__21939 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__SecurityRoleAssignment_4_2_in_rule__MenuItem__Group_4__2__Impl1966 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_5__0__Impl_in_rule__MenuItem__Group_5__02002 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_5__1_in_rule__MenuItem__Group_5__02005 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_20_in_rule__MenuItem__Group_5__0__Impl2033 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_5__1__Impl_in_rule__MenuItem__Group_5__12064 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_5__2_in_rule__MenuItem__Group_5__12067 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__LabelsAssignment_5_1_in_rule__MenuItem__Group_5__1__Impl2094 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_5__2__Impl_in_rule__MenuItem__Group_5__22124 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_5_2__0_in_rule__MenuItem__Group_5__2__Impl2151 = new BitSet(new long[]{0x0000000000200002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_5_2__0__Impl_in_rule__MenuItem__Group_5_2__02188 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_5_2__1_in_rule__MenuItem__Group_5_2__02191 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_21_in_rule__MenuItem__Group_5_2__0__Impl2219 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_5_2__1__Impl_in_rule__MenuItem__Group_5_2__12250 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__LabelsAssignment_5_2_1_in_rule__MenuItem__Group_5_2__1__Impl2277 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_6__0__Impl_in_rule__MenuItem__Group_6__02311 = new BitSet(new long[]{0x00000000008000F0L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_6__1_in_rule__MenuItem__Group_6__02314 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_22_in_rule__MenuItem__Group_6__0__Impl2342 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_6__1__Impl_in_rule__MenuItem__Group_6__12373 = new BitSet(new long[]{0x00000000008000F0L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_6__2_in_rule__MenuItem__Group_6__12376 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_6_1__0_in_rule__MenuItem__Group_6__1__Impl2403 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_6__2__Impl_in_rule__MenuItem__Group_6__22434 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_23_in_rule__MenuItem__Group_6__2__Impl2462 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_6_1__0__Impl_in_rule__MenuItem__Group_6_1__02499 = new BitSet(new long[]{0x00000000000000F0L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_6_1__1_in_rule__MenuItem__Group_6_1__02502 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__SubmenusAssignment_6_1_0_in_rule__MenuItem__Group_6_1__0__Impl2529 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_6_1__1__Impl_in_rule__MenuItem__Group_6_1__12559 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__SubmenusAssignment_6_1_1_in_rule__MenuItem__Group_6_1__1__Impl2586 = new BitSet(new long[]{0x00000000000000F2L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_7__0__Impl_in_rule__MenuItem__Group_7__02621 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_7__1_in_rule__MenuItem__Group_7__02624 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_24_in_rule__MenuItem__Group_7__0__Impl2652 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_7__1__Impl_in_rule__MenuItem__Group_7__12683 = new BitSet(new long[]{0x00000000000000F0L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_7__2_in_rule__MenuItem__Group_7__12686 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_15_in_rule__MenuItem__Group_7__1__Impl2714 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_7__2__Impl_in_rule__MenuItem__Group_7__22745 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__ShortcutAssignment_7_2_in_rule__MenuItem__Group_7__2__Impl2772 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Translation__Group__0__Impl_in_rule__Translation__Group__02808 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_rule__Translation__Group__1_in_rule__Translation__Group__02811 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Translation__LanguageAssignment_0_in_rule__Translation__Group__0__Impl2838 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Translation__Group__1__Impl_in_rule__Translation__Group__12868 = new BitSet(new long[]{0x00000000000000F0L});
    public static final BitSet FOLLOW_rule__Translation__Group__2_in_rule__Translation__Group__12871 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_15_in_rule__Translation__Group__1__Impl2899 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Translation__Group__2__Impl_in_rule__Translation__Group__22930 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Translation__MessageAssignment_2_in_rule__Translation__Group__2__Impl2957 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleString_Value_in_rule__MenuRoot__MetamodelVersionAssignment_22998 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMenuItem_in_rule__MenuRoot__RootItemAssignment_33029 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleString_Value_in_rule__MenuItem__NameAssignment_03060 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleString_Value_in_rule__MenuItem__PageflowAssignment_1_23091 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleEnabled_in_rule__MenuItem__EnabledAssignment_2_23122 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_11_in_rule__MenuItem__DisplayTabsAssignment_3_23158 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleString_Value_in_rule__MenuItem__SecurityRoleAssignment_4_23197 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTranslation_in_rule__MenuItem__LabelsAssignment_5_13228 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTranslation_in_rule__MenuItem__LabelsAssignment_5_2_13259 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMenuItem_in_rule__MenuItem__SubmenusAssignment_6_1_03290 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMenuItem_in_rule__MenuItem__SubmenusAssignment_6_1_13321 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleString_Value_in_rule__MenuItem__ShortcutAssignment_7_23352 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_rule__Translation__LanguageAssignment_03383 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleString_Value_in_rule__Translation__MessageAssignment_23414 = new BitSet(new long[]{0x0000000000000002L});

}
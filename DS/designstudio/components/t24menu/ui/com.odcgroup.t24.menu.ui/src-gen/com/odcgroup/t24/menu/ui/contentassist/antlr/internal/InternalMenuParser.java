package com.odcgroup.t24.menu.ui.contentassist.antlr.internal; 

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
import com.odcgroup.t24.menu.services.MenuGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalMenuParser extends AbstractInternalContentAssistParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_STRING", "RULE_VALUE", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "'true'", "'false'", "'conditional'", "'metamodelVersion'", "'='", "'enabled'", "'displayTabs'", "'securityRole'", "'shortcut'", "'labels'", "','", "'version'", "'enquiry'", "'composite-screen'", "'include-menu'", "'application'", "'parameters'", "'{'", "'}'"
    };
    public static final int RULE_VALUE=5;
    public static final int T__27=27;
    public static final int T__26=26;
    public static final int T__25=25;
    public static final int T__24=24;
    public static final int T__23=23;
    public static final int T__22=22;
    public static final int T__21=21;
    public static final int T__20=20;
    public static final int RULE_SL_COMMENT=7;
    public static final int EOF=-1;
    public static final int T__9=9;
    public static final int RULE_ML_COMMENT=6;
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
    public static final int T__10=10;
    public static final int RULE_WS=8;

    // delegates
    // delegators


        public InternalMenuParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalMenuParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalMenuParser.tokenNames; }
    public String getGrammarFileName() { return "../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g"; }


     
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:60:1: entryRuleMenuRoot : ruleMenuRoot EOF ;
    public final void entryRuleMenuRoot() throws RecognitionException {
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:61:1: ( ruleMenuRoot EOF )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:62:1: ruleMenuRoot EOF
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:69:1: ruleMenuRoot : ( ( rule__MenuRoot__Group__0 ) ) ;
    public final void ruleMenuRoot() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:73:2: ( ( ( rule__MenuRoot__Group__0 ) ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:74:1: ( ( rule__MenuRoot__Group__0 ) )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:74:1: ( ( rule__MenuRoot__Group__0 ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:75:1: ( rule__MenuRoot__Group__0 )
            {
             before(grammarAccess.getMenuRootAccess().getGroup()); 
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:76:1: ( rule__MenuRoot__Group__0 )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:76:2: rule__MenuRoot__Group__0
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:88:1: entryRuleMenuItem : ruleMenuItem EOF ;
    public final void entryRuleMenuItem() throws RecognitionException {
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:89:1: ( ruleMenuItem EOF )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:90:1: ruleMenuItem EOF
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:97:1: ruleMenuItem : ( ( rule__MenuItem__Group__0 ) ) ;
    public final void ruleMenuItem() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:101:2: ( ( ( rule__MenuItem__Group__0 ) ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:102:1: ( ( rule__MenuItem__Group__0 ) )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:102:1: ( ( rule__MenuItem__Group__0 ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:103:1: ( rule__MenuItem__Group__0 )
            {
             before(grammarAccess.getMenuItemAccess().getGroup()); 
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:104:1: ( rule__MenuItem__Group__0 )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:104:2: rule__MenuItem__Group__0
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:116:1: entryRuleTranslation : ruleTranslation EOF ;
    public final void entryRuleTranslation() throws RecognitionException {
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:117:1: ( ruleTranslation EOF )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:118:1: ruleTranslation EOF
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:125:1: ruleTranslation : ( ( rule__Translation__Group__0 ) ) ;
    public final void ruleTranslation() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:129:2: ( ( ( rule__Translation__Group__0 ) ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:130:1: ( ( rule__Translation__Group__0 ) )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:130:1: ( ( rule__Translation__Group__0 ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:131:1: ( rule__Translation__Group__0 )
            {
             before(grammarAccess.getTranslationAccess().getGroup()); 
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:132:1: ( rule__Translation__Group__0 )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:132:2: rule__Translation__Group__0
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


    // $ANTLR start "ruleEnabled"
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:145:1: ruleEnabled : ( ( rule__Enabled__Alternatives ) ) ;
    public final void ruleEnabled() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:149:1: ( ( ( rule__Enabled__Alternatives ) ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:150:1: ( ( rule__Enabled__Alternatives ) )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:150:1: ( ( rule__Enabled__Alternatives ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:151:1: ( rule__Enabled__Alternatives )
            {
             before(grammarAccess.getEnabledAccess().getAlternatives()); 
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:152:1: ( rule__Enabled__Alternatives )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:152:2: rule__Enabled__Alternatives
            {
            pushFollow(FOLLOW_rule__Enabled__Alternatives_in_ruleEnabled251);
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


    // $ANTLR start "rule__MenuItem__Alternatives_6"
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:163:1: rule__MenuItem__Alternatives_6 : ( ( ( rule__MenuItem__Group_6_0__0 ) ) | ( ( rule__MenuItem__Group_6_1__0 ) ) | ( ( rule__MenuItem__Group_6_2__0 ) ) | ( ( rule__MenuItem__Group_6_3__0 ) ) | ( ( rule__MenuItem__Group_6_4__0 ) ) );
    public final void rule__MenuItem__Alternatives_6() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:167:1: ( ( ( rule__MenuItem__Group_6_0__0 ) ) | ( ( rule__MenuItem__Group_6_1__0 ) ) | ( ( rule__MenuItem__Group_6_2__0 ) ) | ( ( rule__MenuItem__Group_6_3__0 ) ) | ( ( rule__MenuItem__Group_6_4__0 ) ) )
            int alt1=5;
            switch ( input.LA(1) ) {
            case 20:
                {
                alt1=1;
                }
                break;
            case 21:
                {
                alt1=2;
                }
                break;
            case 22:
                {
                alt1=3;
                }
                break;
            case 23:
                {
                alt1=4;
                }
                break;
            case 24:
                {
                alt1=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }

            switch (alt1) {
                case 1 :
                    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:168:1: ( ( rule__MenuItem__Group_6_0__0 ) )
                    {
                    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:168:1: ( ( rule__MenuItem__Group_6_0__0 ) )
                    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:169:1: ( rule__MenuItem__Group_6_0__0 )
                    {
                     before(grammarAccess.getMenuItemAccess().getGroup_6_0()); 
                    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:170:1: ( rule__MenuItem__Group_6_0__0 )
                    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:170:2: rule__MenuItem__Group_6_0__0
                    {
                    pushFollow(FOLLOW_rule__MenuItem__Group_6_0__0_in_rule__MenuItem__Alternatives_6286);
                    rule__MenuItem__Group_6_0__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getMenuItemAccess().getGroup_6_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:174:6: ( ( rule__MenuItem__Group_6_1__0 ) )
                    {
                    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:174:6: ( ( rule__MenuItem__Group_6_1__0 ) )
                    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:175:1: ( rule__MenuItem__Group_6_1__0 )
                    {
                     before(grammarAccess.getMenuItemAccess().getGroup_6_1()); 
                    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:176:1: ( rule__MenuItem__Group_6_1__0 )
                    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:176:2: rule__MenuItem__Group_6_1__0
                    {
                    pushFollow(FOLLOW_rule__MenuItem__Group_6_1__0_in_rule__MenuItem__Alternatives_6304);
                    rule__MenuItem__Group_6_1__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getMenuItemAccess().getGroup_6_1()); 

                    }


                    }
                    break;
                case 3 :
                    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:180:6: ( ( rule__MenuItem__Group_6_2__0 ) )
                    {
                    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:180:6: ( ( rule__MenuItem__Group_6_2__0 ) )
                    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:181:1: ( rule__MenuItem__Group_6_2__0 )
                    {
                     before(grammarAccess.getMenuItemAccess().getGroup_6_2()); 
                    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:182:1: ( rule__MenuItem__Group_6_2__0 )
                    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:182:2: rule__MenuItem__Group_6_2__0
                    {
                    pushFollow(FOLLOW_rule__MenuItem__Group_6_2__0_in_rule__MenuItem__Alternatives_6322);
                    rule__MenuItem__Group_6_2__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getMenuItemAccess().getGroup_6_2()); 

                    }


                    }
                    break;
                case 4 :
                    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:186:6: ( ( rule__MenuItem__Group_6_3__0 ) )
                    {
                    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:186:6: ( ( rule__MenuItem__Group_6_3__0 ) )
                    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:187:1: ( rule__MenuItem__Group_6_3__0 )
                    {
                     before(grammarAccess.getMenuItemAccess().getGroup_6_3()); 
                    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:188:1: ( rule__MenuItem__Group_6_3__0 )
                    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:188:2: rule__MenuItem__Group_6_3__0
                    {
                    pushFollow(FOLLOW_rule__MenuItem__Group_6_3__0_in_rule__MenuItem__Alternatives_6340);
                    rule__MenuItem__Group_6_3__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getMenuItemAccess().getGroup_6_3()); 

                    }


                    }
                    break;
                case 5 :
                    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:192:6: ( ( rule__MenuItem__Group_6_4__0 ) )
                    {
                    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:192:6: ( ( rule__MenuItem__Group_6_4__0 ) )
                    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:193:1: ( rule__MenuItem__Group_6_4__0 )
                    {
                     before(grammarAccess.getMenuItemAccess().getGroup_6_4()); 
                    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:194:1: ( rule__MenuItem__Group_6_4__0 )
                    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:194:2: rule__MenuItem__Group_6_4__0
                    {
                    pushFollow(FOLLOW_rule__MenuItem__Group_6_4__0_in_rule__MenuItem__Alternatives_6358);
                    rule__MenuItem__Group_6_4__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getMenuItemAccess().getGroup_6_4()); 

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
    // $ANTLR end "rule__MenuItem__Alternatives_6"


    // $ANTLR start "rule__Enabled__Alternatives"
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:203:1: rule__Enabled__Alternatives : ( ( ( 'true' ) ) | ( ( 'false' ) ) | ( ( 'conditional' ) ) );
    public final void rule__Enabled__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:207:1: ( ( ( 'true' ) ) | ( ( 'false' ) ) | ( ( 'conditional' ) ) )
            int alt2=3;
            switch ( input.LA(1) ) {
            case 9:
                {
                alt2=1;
                }
                break;
            case 10:
                {
                alt2=2;
                }
                break;
            case 11:
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
                    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:208:1: ( ( 'true' ) )
                    {
                    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:208:1: ( ( 'true' ) )
                    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:209:1: ( 'true' )
                    {
                     before(grammarAccess.getEnabledAccess().getTrueEnumLiteralDeclaration_0()); 
                    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:210:1: ( 'true' )
                    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:210:3: 'true'
                    {
                    match(input,9,FOLLOW_9_in_rule__Enabled__Alternatives392); 

                    }

                     after(grammarAccess.getEnabledAccess().getTrueEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:215:6: ( ( 'false' ) )
                    {
                    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:215:6: ( ( 'false' ) )
                    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:216:1: ( 'false' )
                    {
                     before(grammarAccess.getEnabledAccess().getFalseEnumLiteralDeclaration_1()); 
                    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:217:1: ( 'false' )
                    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:217:3: 'false'
                    {
                    match(input,10,FOLLOW_10_in_rule__Enabled__Alternatives413); 

                    }

                     after(grammarAccess.getEnabledAccess().getFalseEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:222:6: ( ( 'conditional' ) )
                    {
                    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:222:6: ( ( 'conditional' ) )
                    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:223:1: ( 'conditional' )
                    {
                     before(grammarAccess.getEnabledAccess().getConditionalEnumLiteralDeclaration_2()); 
                    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:224:1: ( 'conditional' )
                    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:224:3: 'conditional'
                    {
                    match(input,11,FOLLOW_11_in_rule__Enabled__Alternatives434); 

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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:236:1: rule__MenuRoot__Group__0 : rule__MenuRoot__Group__0__Impl rule__MenuRoot__Group__1 ;
    public final void rule__MenuRoot__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:240:1: ( rule__MenuRoot__Group__0__Impl rule__MenuRoot__Group__1 )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:241:2: rule__MenuRoot__Group__0__Impl rule__MenuRoot__Group__1
            {
            pushFollow(FOLLOW_rule__MenuRoot__Group__0__Impl_in_rule__MenuRoot__Group__0467);
            rule__MenuRoot__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuRoot__Group__1_in_rule__MenuRoot__Group__0470);
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:248:1: rule__MenuRoot__Group__0__Impl : ( 'metamodelVersion' ) ;
    public final void rule__MenuRoot__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:252:1: ( ( 'metamodelVersion' ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:253:1: ( 'metamodelVersion' )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:253:1: ( 'metamodelVersion' )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:254:1: 'metamodelVersion'
            {
             before(grammarAccess.getMenuRootAccess().getMetamodelVersionKeyword_0()); 
            match(input,12,FOLLOW_12_in_rule__MenuRoot__Group__0__Impl498); 
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:267:1: rule__MenuRoot__Group__1 : rule__MenuRoot__Group__1__Impl rule__MenuRoot__Group__2 ;
    public final void rule__MenuRoot__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:271:1: ( rule__MenuRoot__Group__1__Impl rule__MenuRoot__Group__2 )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:272:2: rule__MenuRoot__Group__1__Impl rule__MenuRoot__Group__2
            {
            pushFollow(FOLLOW_rule__MenuRoot__Group__1__Impl_in_rule__MenuRoot__Group__1529);
            rule__MenuRoot__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuRoot__Group__2_in_rule__MenuRoot__Group__1532);
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:279:1: rule__MenuRoot__Group__1__Impl : ( '=' ) ;
    public final void rule__MenuRoot__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:283:1: ( ( '=' ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:284:1: ( '=' )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:284:1: ( '=' )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:285:1: '='
            {
             before(grammarAccess.getMenuRootAccess().getEqualsSignKeyword_1()); 
            match(input,13,FOLLOW_13_in_rule__MenuRoot__Group__1__Impl560); 
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:298:1: rule__MenuRoot__Group__2 : rule__MenuRoot__Group__2__Impl rule__MenuRoot__Group__3 ;
    public final void rule__MenuRoot__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:302:1: ( rule__MenuRoot__Group__2__Impl rule__MenuRoot__Group__3 )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:303:2: rule__MenuRoot__Group__2__Impl rule__MenuRoot__Group__3
            {
            pushFollow(FOLLOW_rule__MenuRoot__Group__2__Impl_in_rule__MenuRoot__Group__2591);
            rule__MenuRoot__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuRoot__Group__3_in_rule__MenuRoot__Group__2594);
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:310:1: rule__MenuRoot__Group__2__Impl : ( ( rule__MenuRoot__MetamodelVersionAssignment_2 ) ) ;
    public final void rule__MenuRoot__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:314:1: ( ( ( rule__MenuRoot__MetamodelVersionAssignment_2 ) ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:315:1: ( ( rule__MenuRoot__MetamodelVersionAssignment_2 ) )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:315:1: ( ( rule__MenuRoot__MetamodelVersionAssignment_2 ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:316:1: ( rule__MenuRoot__MetamodelVersionAssignment_2 )
            {
             before(grammarAccess.getMenuRootAccess().getMetamodelVersionAssignment_2()); 
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:317:1: ( rule__MenuRoot__MetamodelVersionAssignment_2 )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:317:2: rule__MenuRoot__MetamodelVersionAssignment_2
            {
            pushFollow(FOLLOW_rule__MenuRoot__MetamodelVersionAssignment_2_in_rule__MenuRoot__Group__2__Impl621);
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:327:1: rule__MenuRoot__Group__3 : rule__MenuRoot__Group__3__Impl ;
    public final void rule__MenuRoot__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:331:1: ( rule__MenuRoot__Group__3__Impl )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:332:2: rule__MenuRoot__Group__3__Impl
            {
            pushFollow(FOLLOW_rule__MenuRoot__Group__3__Impl_in_rule__MenuRoot__Group__3651);
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:338:1: rule__MenuRoot__Group__3__Impl : ( ( rule__MenuRoot__RootItemAssignment_3 ) ) ;
    public final void rule__MenuRoot__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:342:1: ( ( ( rule__MenuRoot__RootItemAssignment_3 ) ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:343:1: ( ( rule__MenuRoot__RootItemAssignment_3 ) )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:343:1: ( ( rule__MenuRoot__RootItemAssignment_3 ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:344:1: ( rule__MenuRoot__RootItemAssignment_3 )
            {
             before(grammarAccess.getMenuRootAccess().getRootItemAssignment_3()); 
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:345:1: ( rule__MenuRoot__RootItemAssignment_3 )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:345:2: rule__MenuRoot__RootItemAssignment_3
            {
            pushFollow(FOLLOW_rule__MenuRoot__RootItemAssignment_3_in_rule__MenuRoot__Group__3__Impl678);
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:363:1: rule__MenuItem__Group__0 : rule__MenuItem__Group__0__Impl rule__MenuItem__Group__1 ;
    public final void rule__MenuItem__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:367:1: ( rule__MenuItem__Group__0__Impl rule__MenuItem__Group__1 )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:368:2: rule__MenuItem__Group__0__Impl rule__MenuItem__Group__1
            {
            pushFollow(FOLLOW_rule__MenuItem__Group__0__Impl_in_rule__MenuItem__Group__0716);
            rule__MenuItem__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuItem__Group__1_in_rule__MenuItem__Group__0719);
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:375:1: rule__MenuItem__Group__0__Impl : ( ( rule__MenuItem__NameAssignment_0 ) ) ;
    public final void rule__MenuItem__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:379:1: ( ( ( rule__MenuItem__NameAssignment_0 ) ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:380:1: ( ( rule__MenuItem__NameAssignment_0 ) )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:380:1: ( ( rule__MenuItem__NameAssignment_0 ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:381:1: ( rule__MenuItem__NameAssignment_0 )
            {
             before(grammarAccess.getMenuItemAccess().getNameAssignment_0()); 
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:382:1: ( rule__MenuItem__NameAssignment_0 )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:382:2: rule__MenuItem__NameAssignment_0
            {
            pushFollow(FOLLOW_rule__MenuItem__NameAssignment_0_in_rule__MenuItem__Group__0__Impl746);
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:392:1: rule__MenuItem__Group__1 : rule__MenuItem__Group__1__Impl rule__MenuItem__Group__2 ;
    public final void rule__MenuItem__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:396:1: ( rule__MenuItem__Group__1__Impl rule__MenuItem__Group__2 )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:397:2: rule__MenuItem__Group__1__Impl rule__MenuItem__Group__2
            {
            pushFollow(FOLLOW_rule__MenuItem__Group__1__Impl_in_rule__MenuItem__Group__1776);
            rule__MenuItem__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuItem__Group__2_in_rule__MenuItem__Group__1779);
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:404:1: rule__MenuItem__Group__1__Impl : ( ( rule__MenuItem__Group_1__0 )? ) ;
    public final void rule__MenuItem__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:408:1: ( ( ( rule__MenuItem__Group_1__0 )? ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:409:1: ( ( rule__MenuItem__Group_1__0 )? )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:409:1: ( ( rule__MenuItem__Group_1__0 )? )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:410:1: ( rule__MenuItem__Group_1__0 )?
            {
             before(grammarAccess.getMenuItemAccess().getGroup_1()); 
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:411:1: ( rule__MenuItem__Group_1__0 )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==14) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:411:2: rule__MenuItem__Group_1__0
                    {
                    pushFollow(FOLLOW_rule__MenuItem__Group_1__0_in_rule__MenuItem__Group__1__Impl806);
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:421:1: rule__MenuItem__Group__2 : rule__MenuItem__Group__2__Impl rule__MenuItem__Group__3 ;
    public final void rule__MenuItem__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:425:1: ( rule__MenuItem__Group__2__Impl rule__MenuItem__Group__3 )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:426:2: rule__MenuItem__Group__2__Impl rule__MenuItem__Group__3
            {
            pushFollow(FOLLOW_rule__MenuItem__Group__2__Impl_in_rule__MenuItem__Group__2837);
            rule__MenuItem__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuItem__Group__3_in_rule__MenuItem__Group__2840);
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:433:1: rule__MenuItem__Group__2__Impl : ( ( rule__MenuItem__Group_2__0 )? ) ;
    public final void rule__MenuItem__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:437:1: ( ( ( rule__MenuItem__Group_2__0 )? ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:438:1: ( ( rule__MenuItem__Group_2__0 )? )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:438:1: ( ( rule__MenuItem__Group_2__0 )? )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:439:1: ( rule__MenuItem__Group_2__0 )?
            {
             before(grammarAccess.getMenuItemAccess().getGroup_2()); 
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:440:1: ( rule__MenuItem__Group_2__0 )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==15) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:440:2: rule__MenuItem__Group_2__0
                    {
                    pushFollow(FOLLOW_rule__MenuItem__Group_2__0_in_rule__MenuItem__Group__2__Impl867);
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:450:1: rule__MenuItem__Group__3 : rule__MenuItem__Group__3__Impl rule__MenuItem__Group__4 ;
    public final void rule__MenuItem__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:454:1: ( rule__MenuItem__Group__3__Impl rule__MenuItem__Group__4 )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:455:2: rule__MenuItem__Group__3__Impl rule__MenuItem__Group__4
            {
            pushFollow(FOLLOW_rule__MenuItem__Group__3__Impl_in_rule__MenuItem__Group__3898);
            rule__MenuItem__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuItem__Group__4_in_rule__MenuItem__Group__3901);
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:462:1: rule__MenuItem__Group__3__Impl : ( ( rule__MenuItem__Group_3__0 )? ) ;
    public final void rule__MenuItem__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:466:1: ( ( ( rule__MenuItem__Group_3__0 )? ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:467:1: ( ( rule__MenuItem__Group_3__0 )? )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:467:1: ( ( rule__MenuItem__Group_3__0 )? )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:468:1: ( rule__MenuItem__Group_3__0 )?
            {
             before(grammarAccess.getMenuItemAccess().getGroup_3()); 
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:469:1: ( rule__MenuItem__Group_3__0 )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==16) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:469:2: rule__MenuItem__Group_3__0
                    {
                    pushFollow(FOLLOW_rule__MenuItem__Group_3__0_in_rule__MenuItem__Group__3__Impl928);
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:479:1: rule__MenuItem__Group__4 : rule__MenuItem__Group__4__Impl rule__MenuItem__Group__5 ;
    public final void rule__MenuItem__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:483:1: ( rule__MenuItem__Group__4__Impl rule__MenuItem__Group__5 )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:484:2: rule__MenuItem__Group__4__Impl rule__MenuItem__Group__5
            {
            pushFollow(FOLLOW_rule__MenuItem__Group__4__Impl_in_rule__MenuItem__Group__4959);
            rule__MenuItem__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuItem__Group__5_in_rule__MenuItem__Group__4962);
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:491:1: rule__MenuItem__Group__4__Impl : ( ( rule__MenuItem__Group_4__0 )? ) ;
    public final void rule__MenuItem__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:495:1: ( ( ( rule__MenuItem__Group_4__0 )? ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:496:1: ( ( rule__MenuItem__Group_4__0 )? )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:496:1: ( ( rule__MenuItem__Group_4__0 )? )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:497:1: ( rule__MenuItem__Group_4__0 )?
            {
             before(grammarAccess.getMenuItemAccess().getGroup_4()); 
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:498:1: ( rule__MenuItem__Group_4__0 )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==17) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:498:2: rule__MenuItem__Group_4__0
                    {
                    pushFollow(FOLLOW_rule__MenuItem__Group_4__0_in_rule__MenuItem__Group__4__Impl989);
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:508:1: rule__MenuItem__Group__5 : rule__MenuItem__Group__5__Impl rule__MenuItem__Group__6 ;
    public final void rule__MenuItem__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:512:1: ( rule__MenuItem__Group__5__Impl rule__MenuItem__Group__6 )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:513:2: rule__MenuItem__Group__5__Impl rule__MenuItem__Group__6
            {
            pushFollow(FOLLOW_rule__MenuItem__Group__5__Impl_in_rule__MenuItem__Group__51020);
            rule__MenuItem__Group__5__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuItem__Group__6_in_rule__MenuItem__Group__51023);
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:520:1: rule__MenuItem__Group__5__Impl : ( ( rule__MenuItem__Group_5__0 )? ) ;
    public final void rule__MenuItem__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:524:1: ( ( ( rule__MenuItem__Group_5__0 )? ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:525:1: ( ( rule__MenuItem__Group_5__0 )? )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:525:1: ( ( rule__MenuItem__Group_5__0 )? )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:526:1: ( rule__MenuItem__Group_5__0 )?
            {
             before(grammarAccess.getMenuItemAccess().getGroup_5()); 
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:527:1: ( rule__MenuItem__Group_5__0 )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==18) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:527:2: rule__MenuItem__Group_5__0
                    {
                    pushFollow(FOLLOW_rule__MenuItem__Group_5__0_in_rule__MenuItem__Group__5__Impl1050);
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:537:1: rule__MenuItem__Group__6 : rule__MenuItem__Group__6__Impl rule__MenuItem__Group__7 ;
    public final void rule__MenuItem__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:541:1: ( rule__MenuItem__Group__6__Impl rule__MenuItem__Group__7 )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:542:2: rule__MenuItem__Group__6__Impl rule__MenuItem__Group__7
            {
            pushFollow(FOLLOW_rule__MenuItem__Group__6__Impl_in_rule__MenuItem__Group__61081);
            rule__MenuItem__Group__6__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuItem__Group__7_in_rule__MenuItem__Group__61084);
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:549:1: rule__MenuItem__Group__6__Impl : ( ( rule__MenuItem__Alternatives_6 )? ) ;
    public final void rule__MenuItem__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:553:1: ( ( ( rule__MenuItem__Alternatives_6 )? ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:554:1: ( ( rule__MenuItem__Alternatives_6 )? )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:554:1: ( ( rule__MenuItem__Alternatives_6 )? )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:555:1: ( rule__MenuItem__Alternatives_6 )?
            {
             before(grammarAccess.getMenuItemAccess().getAlternatives_6()); 
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:556:1: ( rule__MenuItem__Alternatives_6 )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( ((LA8_0>=20 && LA8_0<=24)) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:556:2: rule__MenuItem__Alternatives_6
                    {
                    pushFollow(FOLLOW_rule__MenuItem__Alternatives_6_in_rule__MenuItem__Group__6__Impl1111);
                    rule__MenuItem__Alternatives_6();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getMenuItemAccess().getAlternatives_6()); 

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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:566:1: rule__MenuItem__Group__7 : rule__MenuItem__Group__7__Impl rule__MenuItem__Group__8 ;
    public final void rule__MenuItem__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:570:1: ( rule__MenuItem__Group__7__Impl rule__MenuItem__Group__8 )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:571:2: rule__MenuItem__Group__7__Impl rule__MenuItem__Group__8
            {
            pushFollow(FOLLOW_rule__MenuItem__Group__7__Impl_in_rule__MenuItem__Group__71142);
            rule__MenuItem__Group__7__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuItem__Group__8_in_rule__MenuItem__Group__71145);
            rule__MenuItem__Group__8();

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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:578:1: rule__MenuItem__Group__7__Impl : ( ( rule__MenuItem__Group_7__0 )? ) ;
    public final void rule__MenuItem__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:582:1: ( ( ( rule__MenuItem__Group_7__0 )? ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:583:1: ( ( rule__MenuItem__Group_7__0 )? )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:583:1: ( ( rule__MenuItem__Group_7__0 )? )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:584:1: ( rule__MenuItem__Group_7__0 )?
            {
             before(grammarAccess.getMenuItemAccess().getGroup_7()); 
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:585:1: ( rule__MenuItem__Group_7__0 )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==25) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:585:2: rule__MenuItem__Group_7__0
                    {
                    pushFollow(FOLLOW_rule__MenuItem__Group_7__0_in_rule__MenuItem__Group__7__Impl1172);
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


    // $ANTLR start "rule__MenuItem__Group__8"
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:595:1: rule__MenuItem__Group__8 : rule__MenuItem__Group__8__Impl ;
    public final void rule__MenuItem__Group__8() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:599:1: ( rule__MenuItem__Group__8__Impl )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:600:2: rule__MenuItem__Group__8__Impl
            {
            pushFollow(FOLLOW_rule__MenuItem__Group__8__Impl_in_rule__MenuItem__Group__81203);
            rule__MenuItem__Group__8__Impl();

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
    // $ANTLR end "rule__MenuItem__Group__8"


    // $ANTLR start "rule__MenuItem__Group__8__Impl"
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:606:1: rule__MenuItem__Group__8__Impl : ( ( rule__MenuItem__Group_8__0 )? ) ;
    public final void rule__MenuItem__Group__8__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:610:1: ( ( ( rule__MenuItem__Group_8__0 )? ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:611:1: ( ( rule__MenuItem__Group_8__0 )? )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:611:1: ( ( rule__MenuItem__Group_8__0 )? )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:612:1: ( rule__MenuItem__Group_8__0 )?
            {
             before(grammarAccess.getMenuItemAccess().getGroup_8()); 
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:613:1: ( rule__MenuItem__Group_8__0 )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==26) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:613:2: rule__MenuItem__Group_8__0
                    {
                    pushFollow(FOLLOW_rule__MenuItem__Group_8__0_in_rule__MenuItem__Group__8__Impl1230);
                    rule__MenuItem__Group_8__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getMenuItemAccess().getGroup_8()); 

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
    // $ANTLR end "rule__MenuItem__Group__8__Impl"


    // $ANTLR start "rule__MenuItem__Group_1__0"
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:641:1: rule__MenuItem__Group_1__0 : rule__MenuItem__Group_1__0__Impl rule__MenuItem__Group_1__1 ;
    public final void rule__MenuItem__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:645:1: ( rule__MenuItem__Group_1__0__Impl rule__MenuItem__Group_1__1 )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:646:2: rule__MenuItem__Group_1__0__Impl rule__MenuItem__Group_1__1
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_1__0__Impl_in_rule__MenuItem__Group_1__01279);
            rule__MenuItem__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuItem__Group_1__1_in_rule__MenuItem__Group_1__01282);
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:653:1: rule__MenuItem__Group_1__0__Impl : ( 'enabled' ) ;
    public final void rule__MenuItem__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:657:1: ( ( 'enabled' ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:658:1: ( 'enabled' )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:658:1: ( 'enabled' )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:659:1: 'enabled'
            {
             before(grammarAccess.getMenuItemAccess().getEnabledKeyword_1_0()); 
            match(input,14,FOLLOW_14_in_rule__MenuItem__Group_1__0__Impl1310); 
             after(grammarAccess.getMenuItemAccess().getEnabledKeyword_1_0()); 

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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:672:1: rule__MenuItem__Group_1__1 : rule__MenuItem__Group_1__1__Impl rule__MenuItem__Group_1__2 ;
    public final void rule__MenuItem__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:676:1: ( rule__MenuItem__Group_1__1__Impl rule__MenuItem__Group_1__2 )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:677:2: rule__MenuItem__Group_1__1__Impl rule__MenuItem__Group_1__2
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_1__1__Impl_in_rule__MenuItem__Group_1__11341);
            rule__MenuItem__Group_1__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuItem__Group_1__2_in_rule__MenuItem__Group_1__11344);
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:684:1: rule__MenuItem__Group_1__1__Impl : ( '=' ) ;
    public final void rule__MenuItem__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:688:1: ( ( '=' ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:689:1: ( '=' )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:689:1: ( '=' )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:690:1: '='
            {
             before(grammarAccess.getMenuItemAccess().getEqualsSignKeyword_1_1()); 
            match(input,13,FOLLOW_13_in_rule__MenuItem__Group_1__1__Impl1372); 
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:703:1: rule__MenuItem__Group_1__2 : rule__MenuItem__Group_1__2__Impl ;
    public final void rule__MenuItem__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:707:1: ( rule__MenuItem__Group_1__2__Impl )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:708:2: rule__MenuItem__Group_1__2__Impl
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_1__2__Impl_in_rule__MenuItem__Group_1__21403);
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:714:1: rule__MenuItem__Group_1__2__Impl : ( ( rule__MenuItem__EnabledAssignment_1_2 ) ) ;
    public final void rule__MenuItem__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:718:1: ( ( ( rule__MenuItem__EnabledAssignment_1_2 ) ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:719:1: ( ( rule__MenuItem__EnabledAssignment_1_2 ) )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:719:1: ( ( rule__MenuItem__EnabledAssignment_1_2 ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:720:1: ( rule__MenuItem__EnabledAssignment_1_2 )
            {
             before(grammarAccess.getMenuItemAccess().getEnabledAssignment_1_2()); 
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:721:1: ( rule__MenuItem__EnabledAssignment_1_2 )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:721:2: rule__MenuItem__EnabledAssignment_1_2
            {
            pushFollow(FOLLOW_rule__MenuItem__EnabledAssignment_1_2_in_rule__MenuItem__Group_1__2__Impl1430);
            rule__MenuItem__EnabledAssignment_1_2();

            state._fsp--;


            }

             after(grammarAccess.getMenuItemAccess().getEnabledAssignment_1_2()); 

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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:737:1: rule__MenuItem__Group_2__0 : rule__MenuItem__Group_2__0__Impl rule__MenuItem__Group_2__1 ;
    public final void rule__MenuItem__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:741:1: ( rule__MenuItem__Group_2__0__Impl rule__MenuItem__Group_2__1 )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:742:2: rule__MenuItem__Group_2__0__Impl rule__MenuItem__Group_2__1
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_2__0__Impl_in_rule__MenuItem__Group_2__01466);
            rule__MenuItem__Group_2__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuItem__Group_2__1_in_rule__MenuItem__Group_2__01469);
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:749:1: rule__MenuItem__Group_2__0__Impl : ( 'displayTabs' ) ;
    public final void rule__MenuItem__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:753:1: ( ( 'displayTabs' ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:754:1: ( 'displayTabs' )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:754:1: ( 'displayTabs' )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:755:1: 'displayTabs'
            {
             before(grammarAccess.getMenuItemAccess().getDisplayTabsKeyword_2_0()); 
            match(input,15,FOLLOW_15_in_rule__MenuItem__Group_2__0__Impl1497); 
             after(grammarAccess.getMenuItemAccess().getDisplayTabsKeyword_2_0()); 

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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:768:1: rule__MenuItem__Group_2__1 : rule__MenuItem__Group_2__1__Impl rule__MenuItem__Group_2__2 ;
    public final void rule__MenuItem__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:772:1: ( rule__MenuItem__Group_2__1__Impl rule__MenuItem__Group_2__2 )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:773:2: rule__MenuItem__Group_2__1__Impl rule__MenuItem__Group_2__2
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_2__1__Impl_in_rule__MenuItem__Group_2__11528);
            rule__MenuItem__Group_2__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuItem__Group_2__2_in_rule__MenuItem__Group_2__11531);
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:780:1: rule__MenuItem__Group_2__1__Impl : ( '=' ) ;
    public final void rule__MenuItem__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:784:1: ( ( '=' ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:785:1: ( '=' )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:785:1: ( '=' )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:786:1: '='
            {
             before(grammarAccess.getMenuItemAccess().getEqualsSignKeyword_2_1()); 
            match(input,13,FOLLOW_13_in_rule__MenuItem__Group_2__1__Impl1559); 
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:799:1: rule__MenuItem__Group_2__2 : rule__MenuItem__Group_2__2__Impl ;
    public final void rule__MenuItem__Group_2__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:803:1: ( rule__MenuItem__Group_2__2__Impl )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:804:2: rule__MenuItem__Group_2__2__Impl
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_2__2__Impl_in_rule__MenuItem__Group_2__21590);
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:810:1: rule__MenuItem__Group_2__2__Impl : ( ( rule__MenuItem__DisplayTabsAssignment_2_2 ) ) ;
    public final void rule__MenuItem__Group_2__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:814:1: ( ( ( rule__MenuItem__DisplayTabsAssignment_2_2 ) ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:815:1: ( ( rule__MenuItem__DisplayTabsAssignment_2_2 ) )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:815:1: ( ( rule__MenuItem__DisplayTabsAssignment_2_2 ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:816:1: ( rule__MenuItem__DisplayTabsAssignment_2_2 )
            {
             before(grammarAccess.getMenuItemAccess().getDisplayTabsAssignment_2_2()); 
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:817:1: ( rule__MenuItem__DisplayTabsAssignment_2_2 )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:817:2: rule__MenuItem__DisplayTabsAssignment_2_2
            {
            pushFollow(FOLLOW_rule__MenuItem__DisplayTabsAssignment_2_2_in_rule__MenuItem__Group_2__2__Impl1617);
            rule__MenuItem__DisplayTabsAssignment_2_2();

            state._fsp--;


            }

             after(grammarAccess.getMenuItemAccess().getDisplayTabsAssignment_2_2()); 

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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:833:1: rule__MenuItem__Group_3__0 : rule__MenuItem__Group_3__0__Impl rule__MenuItem__Group_3__1 ;
    public final void rule__MenuItem__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:837:1: ( rule__MenuItem__Group_3__0__Impl rule__MenuItem__Group_3__1 )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:838:2: rule__MenuItem__Group_3__0__Impl rule__MenuItem__Group_3__1
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_3__0__Impl_in_rule__MenuItem__Group_3__01653);
            rule__MenuItem__Group_3__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuItem__Group_3__1_in_rule__MenuItem__Group_3__01656);
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:845:1: rule__MenuItem__Group_3__0__Impl : ( 'securityRole' ) ;
    public final void rule__MenuItem__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:849:1: ( ( 'securityRole' ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:850:1: ( 'securityRole' )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:850:1: ( 'securityRole' )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:851:1: 'securityRole'
            {
             before(grammarAccess.getMenuItemAccess().getSecurityRoleKeyword_3_0()); 
            match(input,16,FOLLOW_16_in_rule__MenuItem__Group_3__0__Impl1684); 
             after(grammarAccess.getMenuItemAccess().getSecurityRoleKeyword_3_0()); 

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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:864:1: rule__MenuItem__Group_3__1 : rule__MenuItem__Group_3__1__Impl rule__MenuItem__Group_3__2 ;
    public final void rule__MenuItem__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:868:1: ( rule__MenuItem__Group_3__1__Impl rule__MenuItem__Group_3__2 )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:869:2: rule__MenuItem__Group_3__1__Impl rule__MenuItem__Group_3__2
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_3__1__Impl_in_rule__MenuItem__Group_3__11715);
            rule__MenuItem__Group_3__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuItem__Group_3__2_in_rule__MenuItem__Group_3__11718);
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:876:1: rule__MenuItem__Group_3__1__Impl : ( '=' ) ;
    public final void rule__MenuItem__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:880:1: ( ( '=' ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:881:1: ( '=' )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:881:1: ( '=' )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:882:1: '='
            {
             before(grammarAccess.getMenuItemAccess().getEqualsSignKeyword_3_1()); 
            match(input,13,FOLLOW_13_in_rule__MenuItem__Group_3__1__Impl1746); 
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:895:1: rule__MenuItem__Group_3__2 : rule__MenuItem__Group_3__2__Impl ;
    public final void rule__MenuItem__Group_3__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:899:1: ( rule__MenuItem__Group_3__2__Impl )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:900:2: rule__MenuItem__Group_3__2__Impl
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_3__2__Impl_in_rule__MenuItem__Group_3__21777);
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:906:1: rule__MenuItem__Group_3__2__Impl : ( ( rule__MenuItem__SecurityRoleAssignment_3_2 ) ) ;
    public final void rule__MenuItem__Group_3__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:910:1: ( ( ( rule__MenuItem__SecurityRoleAssignment_3_2 ) ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:911:1: ( ( rule__MenuItem__SecurityRoleAssignment_3_2 ) )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:911:1: ( ( rule__MenuItem__SecurityRoleAssignment_3_2 ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:912:1: ( rule__MenuItem__SecurityRoleAssignment_3_2 )
            {
             before(grammarAccess.getMenuItemAccess().getSecurityRoleAssignment_3_2()); 
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:913:1: ( rule__MenuItem__SecurityRoleAssignment_3_2 )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:913:2: rule__MenuItem__SecurityRoleAssignment_3_2
            {
            pushFollow(FOLLOW_rule__MenuItem__SecurityRoleAssignment_3_2_in_rule__MenuItem__Group_3__2__Impl1804);
            rule__MenuItem__SecurityRoleAssignment_3_2();

            state._fsp--;


            }

             after(grammarAccess.getMenuItemAccess().getSecurityRoleAssignment_3_2()); 

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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:929:1: rule__MenuItem__Group_4__0 : rule__MenuItem__Group_4__0__Impl rule__MenuItem__Group_4__1 ;
    public final void rule__MenuItem__Group_4__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:933:1: ( rule__MenuItem__Group_4__0__Impl rule__MenuItem__Group_4__1 )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:934:2: rule__MenuItem__Group_4__0__Impl rule__MenuItem__Group_4__1
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_4__0__Impl_in_rule__MenuItem__Group_4__01840);
            rule__MenuItem__Group_4__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuItem__Group_4__1_in_rule__MenuItem__Group_4__01843);
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:941:1: rule__MenuItem__Group_4__0__Impl : ( 'shortcut' ) ;
    public final void rule__MenuItem__Group_4__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:945:1: ( ( 'shortcut' ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:946:1: ( 'shortcut' )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:946:1: ( 'shortcut' )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:947:1: 'shortcut'
            {
             before(grammarAccess.getMenuItemAccess().getShortcutKeyword_4_0()); 
            match(input,17,FOLLOW_17_in_rule__MenuItem__Group_4__0__Impl1871); 
             after(grammarAccess.getMenuItemAccess().getShortcutKeyword_4_0()); 

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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:960:1: rule__MenuItem__Group_4__1 : rule__MenuItem__Group_4__1__Impl rule__MenuItem__Group_4__2 ;
    public final void rule__MenuItem__Group_4__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:964:1: ( rule__MenuItem__Group_4__1__Impl rule__MenuItem__Group_4__2 )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:965:2: rule__MenuItem__Group_4__1__Impl rule__MenuItem__Group_4__2
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_4__1__Impl_in_rule__MenuItem__Group_4__11902);
            rule__MenuItem__Group_4__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuItem__Group_4__2_in_rule__MenuItem__Group_4__11905);
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:972:1: rule__MenuItem__Group_4__1__Impl : ( '=' ) ;
    public final void rule__MenuItem__Group_4__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:976:1: ( ( '=' ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:977:1: ( '=' )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:977:1: ( '=' )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:978:1: '='
            {
             before(grammarAccess.getMenuItemAccess().getEqualsSignKeyword_4_1()); 
            match(input,13,FOLLOW_13_in_rule__MenuItem__Group_4__1__Impl1933); 
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:991:1: rule__MenuItem__Group_4__2 : rule__MenuItem__Group_4__2__Impl ;
    public final void rule__MenuItem__Group_4__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:995:1: ( rule__MenuItem__Group_4__2__Impl )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:996:2: rule__MenuItem__Group_4__2__Impl
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_4__2__Impl_in_rule__MenuItem__Group_4__21964);
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1002:1: rule__MenuItem__Group_4__2__Impl : ( ( rule__MenuItem__ShortcutAssignment_4_2 ) ) ;
    public final void rule__MenuItem__Group_4__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1006:1: ( ( ( rule__MenuItem__ShortcutAssignment_4_2 ) ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1007:1: ( ( rule__MenuItem__ShortcutAssignment_4_2 ) )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1007:1: ( ( rule__MenuItem__ShortcutAssignment_4_2 ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1008:1: ( rule__MenuItem__ShortcutAssignment_4_2 )
            {
             before(grammarAccess.getMenuItemAccess().getShortcutAssignment_4_2()); 
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1009:1: ( rule__MenuItem__ShortcutAssignment_4_2 )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1009:2: rule__MenuItem__ShortcutAssignment_4_2
            {
            pushFollow(FOLLOW_rule__MenuItem__ShortcutAssignment_4_2_in_rule__MenuItem__Group_4__2__Impl1991);
            rule__MenuItem__ShortcutAssignment_4_2();

            state._fsp--;


            }

             after(grammarAccess.getMenuItemAccess().getShortcutAssignment_4_2()); 

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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1025:1: rule__MenuItem__Group_5__0 : rule__MenuItem__Group_5__0__Impl rule__MenuItem__Group_5__1 ;
    public final void rule__MenuItem__Group_5__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1029:1: ( rule__MenuItem__Group_5__0__Impl rule__MenuItem__Group_5__1 )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1030:2: rule__MenuItem__Group_5__0__Impl rule__MenuItem__Group_5__1
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_5__0__Impl_in_rule__MenuItem__Group_5__02027);
            rule__MenuItem__Group_5__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuItem__Group_5__1_in_rule__MenuItem__Group_5__02030);
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1037:1: rule__MenuItem__Group_5__0__Impl : ( 'labels' ) ;
    public final void rule__MenuItem__Group_5__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1041:1: ( ( 'labels' ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1042:1: ( 'labels' )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1042:1: ( 'labels' )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1043:1: 'labels'
            {
             before(grammarAccess.getMenuItemAccess().getLabelsKeyword_5_0()); 
            match(input,18,FOLLOW_18_in_rule__MenuItem__Group_5__0__Impl2058); 
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1056:1: rule__MenuItem__Group_5__1 : rule__MenuItem__Group_5__1__Impl rule__MenuItem__Group_5__2 ;
    public final void rule__MenuItem__Group_5__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1060:1: ( rule__MenuItem__Group_5__1__Impl rule__MenuItem__Group_5__2 )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1061:2: rule__MenuItem__Group_5__1__Impl rule__MenuItem__Group_5__2
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_5__1__Impl_in_rule__MenuItem__Group_5__12089);
            rule__MenuItem__Group_5__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuItem__Group_5__2_in_rule__MenuItem__Group_5__12092);
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1068:1: rule__MenuItem__Group_5__1__Impl : ( ( rule__MenuItem__LabelsAssignment_5_1 ) ) ;
    public final void rule__MenuItem__Group_5__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1072:1: ( ( ( rule__MenuItem__LabelsAssignment_5_1 ) ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1073:1: ( ( rule__MenuItem__LabelsAssignment_5_1 ) )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1073:1: ( ( rule__MenuItem__LabelsAssignment_5_1 ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1074:1: ( rule__MenuItem__LabelsAssignment_5_1 )
            {
             before(grammarAccess.getMenuItemAccess().getLabelsAssignment_5_1()); 
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1075:1: ( rule__MenuItem__LabelsAssignment_5_1 )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1075:2: rule__MenuItem__LabelsAssignment_5_1
            {
            pushFollow(FOLLOW_rule__MenuItem__LabelsAssignment_5_1_in_rule__MenuItem__Group_5__1__Impl2119);
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1085:1: rule__MenuItem__Group_5__2 : rule__MenuItem__Group_5__2__Impl ;
    public final void rule__MenuItem__Group_5__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1089:1: ( rule__MenuItem__Group_5__2__Impl )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1090:2: rule__MenuItem__Group_5__2__Impl
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_5__2__Impl_in_rule__MenuItem__Group_5__22149);
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1096:1: rule__MenuItem__Group_5__2__Impl : ( ( rule__MenuItem__Group_5_2__0 )* ) ;
    public final void rule__MenuItem__Group_5__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1100:1: ( ( ( rule__MenuItem__Group_5_2__0 )* ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1101:1: ( ( rule__MenuItem__Group_5_2__0 )* )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1101:1: ( ( rule__MenuItem__Group_5_2__0 )* )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1102:1: ( rule__MenuItem__Group_5_2__0 )*
            {
             before(grammarAccess.getMenuItemAccess().getGroup_5_2()); 
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1103:1: ( rule__MenuItem__Group_5_2__0 )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==19) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1103:2: rule__MenuItem__Group_5_2__0
            	    {
            	    pushFollow(FOLLOW_rule__MenuItem__Group_5_2__0_in_rule__MenuItem__Group_5__2__Impl2176);
            	    rule__MenuItem__Group_5_2__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop11;
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1119:1: rule__MenuItem__Group_5_2__0 : rule__MenuItem__Group_5_2__0__Impl rule__MenuItem__Group_5_2__1 ;
    public final void rule__MenuItem__Group_5_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1123:1: ( rule__MenuItem__Group_5_2__0__Impl rule__MenuItem__Group_5_2__1 )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1124:2: rule__MenuItem__Group_5_2__0__Impl rule__MenuItem__Group_5_2__1
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_5_2__0__Impl_in_rule__MenuItem__Group_5_2__02213);
            rule__MenuItem__Group_5_2__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuItem__Group_5_2__1_in_rule__MenuItem__Group_5_2__02216);
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1131:1: rule__MenuItem__Group_5_2__0__Impl : ( ',' ) ;
    public final void rule__MenuItem__Group_5_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1135:1: ( ( ',' ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1136:1: ( ',' )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1136:1: ( ',' )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1137:1: ','
            {
             before(grammarAccess.getMenuItemAccess().getCommaKeyword_5_2_0()); 
            match(input,19,FOLLOW_19_in_rule__MenuItem__Group_5_2__0__Impl2244); 
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1150:1: rule__MenuItem__Group_5_2__1 : rule__MenuItem__Group_5_2__1__Impl ;
    public final void rule__MenuItem__Group_5_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1154:1: ( rule__MenuItem__Group_5_2__1__Impl )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1155:2: rule__MenuItem__Group_5_2__1__Impl
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_5_2__1__Impl_in_rule__MenuItem__Group_5_2__12275);
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1161:1: rule__MenuItem__Group_5_2__1__Impl : ( ( rule__MenuItem__LabelsAssignment_5_2_1 ) ) ;
    public final void rule__MenuItem__Group_5_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1165:1: ( ( ( rule__MenuItem__LabelsAssignment_5_2_1 ) ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1166:1: ( ( rule__MenuItem__LabelsAssignment_5_2_1 ) )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1166:1: ( ( rule__MenuItem__LabelsAssignment_5_2_1 ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1167:1: ( rule__MenuItem__LabelsAssignment_5_2_1 )
            {
             before(grammarAccess.getMenuItemAccess().getLabelsAssignment_5_2_1()); 
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1168:1: ( rule__MenuItem__LabelsAssignment_5_2_1 )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1168:2: rule__MenuItem__LabelsAssignment_5_2_1
            {
            pushFollow(FOLLOW_rule__MenuItem__LabelsAssignment_5_2_1_in_rule__MenuItem__Group_5_2__1__Impl2302);
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


    // $ANTLR start "rule__MenuItem__Group_6_0__0"
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1182:1: rule__MenuItem__Group_6_0__0 : rule__MenuItem__Group_6_0__0__Impl rule__MenuItem__Group_6_0__1 ;
    public final void rule__MenuItem__Group_6_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1186:1: ( rule__MenuItem__Group_6_0__0__Impl rule__MenuItem__Group_6_0__1 )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1187:2: rule__MenuItem__Group_6_0__0__Impl rule__MenuItem__Group_6_0__1
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_6_0__0__Impl_in_rule__MenuItem__Group_6_0__02336);
            rule__MenuItem__Group_6_0__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuItem__Group_6_0__1_in_rule__MenuItem__Group_6_0__02339);
            rule__MenuItem__Group_6_0__1();

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
    // $ANTLR end "rule__MenuItem__Group_6_0__0"


    // $ANTLR start "rule__MenuItem__Group_6_0__0__Impl"
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1194:1: rule__MenuItem__Group_6_0__0__Impl : ( 'version' ) ;
    public final void rule__MenuItem__Group_6_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1198:1: ( ( 'version' ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1199:1: ( 'version' )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1199:1: ( 'version' )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1200:1: 'version'
            {
             before(grammarAccess.getMenuItemAccess().getVersionKeyword_6_0_0()); 
            match(input,20,FOLLOW_20_in_rule__MenuItem__Group_6_0__0__Impl2367); 
             after(grammarAccess.getMenuItemAccess().getVersionKeyword_6_0_0()); 

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
    // $ANTLR end "rule__MenuItem__Group_6_0__0__Impl"


    // $ANTLR start "rule__MenuItem__Group_6_0__1"
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1213:1: rule__MenuItem__Group_6_0__1 : rule__MenuItem__Group_6_0__1__Impl rule__MenuItem__Group_6_0__2 ;
    public final void rule__MenuItem__Group_6_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1217:1: ( rule__MenuItem__Group_6_0__1__Impl rule__MenuItem__Group_6_0__2 )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1218:2: rule__MenuItem__Group_6_0__1__Impl rule__MenuItem__Group_6_0__2
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_6_0__1__Impl_in_rule__MenuItem__Group_6_0__12398);
            rule__MenuItem__Group_6_0__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuItem__Group_6_0__2_in_rule__MenuItem__Group_6_0__12401);
            rule__MenuItem__Group_6_0__2();

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
    // $ANTLR end "rule__MenuItem__Group_6_0__1"


    // $ANTLR start "rule__MenuItem__Group_6_0__1__Impl"
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1225:1: rule__MenuItem__Group_6_0__1__Impl : ( '=' ) ;
    public final void rule__MenuItem__Group_6_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1229:1: ( ( '=' ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1230:1: ( '=' )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1230:1: ( '=' )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1231:1: '='
            {
             before(grammarAccess.getMenuItemAccess().getEqualsSignKeyword_6_0_1()); 
            match(input,13,FOLLOW_13_in_rule__MenuItem__Group_6_0__1__Impl2429); 
             after(grammarAccess.getMenuItemAccess().getEqualsSignKeyword_6_0_1()); 

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
    // $ANTLR end "rule__MenuItem__Group_6_0__1__Impl"


    // $ANTLR start "rule__MenuItem__Group_6_0__2"
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1244:1: rule__MenuItem__Group_6_0__2 : rule__MenuItem__Group_6_0__2__Impl ;
    public final void rule__MenuItem__Group_6_0__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1248:1: ( rule__MenuItem__Group_6_0__2__Impl )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1249:2: rule__MenuItem__Group_6_0__2__Impl
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_6_0__2__Impl_in_rule__MenuItem__Group_6_0__22460);
            rule__MenuItem__Group_6_0__2__Impl();

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
    // $ANTLR end "rule__MenuItem__Group_6_0__2"


    // $ANTLR start "rule__MenuItem__Group_6_0__2__Impl"
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1255:1: rule__MenuItem__Group_6_0__2__Impl : ( ( rule__MenuItem__VersionAssignment_6_0_2 ) ) ;
    public final void rule__MenuItem__Group_6_0__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1259:1: ( ( ( rule__MenuItem__VersionAssignment_6_0_2 ) ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1260:1: ( ( rule__MenuItem__VersionAssignment_6_0_2 ) )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1260:1: ( ( rule__MenuItem__VersionAssignment_6_0_2 ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1261:1: ( rule__MenuItem__VersionAssignment_6_0_2 )
            {
             before(grammarAccess.getMenuItemAccess().getVersionAssignment_6_0_2()); 
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1262:1: ( rule__MenuItem__VersionAssignment_6_0_2 )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1262:2: rule__MenuItem__VersionAssignment_6_0_2
            {
            pushFollow(FOLLOW_rule__MenuItem__VersionAssignment_6_0_2_in_rule__MenuItem__Group_6_0__2__Impl2487);
            rule__MenuItem__VersionAssignment_6_0_2();

            state._fsp--;


            }

             after(grammarAccess.getMenuItemAccess().getVersionAssignment_6_0_2()); 

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
    // $ANTLR end "rule__MenuItem__Group_6_0__2__Impl"


    // $ANTLR start "rule__MenuItem__Group_6_1__0"
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1278:1: rule__MenuItem__Group_6_1__0 : rule__MenuItem__Group_6_1__0__Impl rule__MenuItem__Group_6_1__1 ;
    public final void rule__MenuItem__Group_6_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1282:1: ( rule__MenuItem__Group_6_1__0__Impl rule__MenuItem__Group_6_1__1 )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1283:2: rule__MenuItem__Group_6_1__0__Impl rule__MenuItem__Group_6_1__1
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_6_1__0__Impl_in_rule__MenuItem__Group_6_1__02523);
            rule__MenuItem__Group_6_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuItem__Group_6_1__1_in_rule__MenuItem__Group_6_1__02526);
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1290:1: rule__MenuItem__Group_6_1__0__Impl : ( 'enquiry' ) ;
    public final void rule__MenuItem__Group_6_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1294:1: ( ( 'enquiry' ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1295:1: ( 'enquiry' )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1295:1: ( 'enquiry' )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1296:1: 'enquiry'
            {
             before(grammarAccess.getMenuItemAccess().getEnquiryKeyword_6_1_0()); 
            match(input,21,FOLLOW_21_in_rule__MenuItem__Group_6_1__0__Impl2554); 
             after(grammarAccess.getMenuItemAccess().getEnquiryKeyword_6_1_0()); 

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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1309:1: rule__MenuItem__Group_6_1__1 : rule__MenuItem__Group_6_1__1__Impl rule__MenuItem__Group_6_1__2 ;
    public final void rule__MenuItem__Group_6_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1313:1: ( rule__MenuItem__Group_6_1__1__Impl rule__MenuItem__Group_6_1__2 )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1314:2: rule__MenuItem__Group_6_1__1__Impl rule__MenuItem__Group_6_1__2
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_6_1__1__Impl_in_rule__MenuItem__Group_6_1__12585);
            rule__MenuItem__Group_6_1__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuItem__Group_6_1__2_in_rule__MenuItem__Group_6_1__12588);
            rule__MenuItem__Group_6_1__2();

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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1321:1: rule__MenuItem__Group_6_1__1__Impl : ( '=' ) ;
    public final void rule__MenuItem__Group_6_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1325:1: ( ( '=' ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1326:1: ( '=' )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1326:1: ( '=' )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1327:1: '='
            {
             before(grammarAccess.getMenuItemAccess().getEqualsSignKeyword_6_1_1()); 
            match(input,13,FOLLOW_13_in_rule__MenuItem__Group_6_1__1__Impl2616); 
             after(grammarAccess.getMenuItemAccess().getEqualsSignKeyword_6_1_1()); 

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


    // $ANTLR start "rule__MenuItem__Group_6_1__2"
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1340:1: rule__MenuItem__Group_6_1__2 : rule__MenuItem__Group_6_1__2__Impl ;
    public final void rule__MenuItem__Group_6_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1344:1: ( rule__MenuItem__Group_6_1__2__Impl )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1345:2: rule__MenuItem__Group_6_1__2__Impl
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_6_1__2__Impl_in_rule__MenuItem__Group_6_1__22647);
            rule__MenuItem__Group_6_1__2__Impl();

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
    // $ANTLR end "rule__MenuItem__Group_6_1__2"


    // $ANTLR start "rule__MenuItem__Group_6_1__2__Impl"
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1351:1: rule__MenuItem__Group_6_1__2__Impl : ( ( rule__MenuItem__EnquiryAssignment_6_1_2 ) ) ;
    public final void rule__MenuItem__Group_6_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1355:1: ( ( ( rule__MenuItem__EnquiryAssignment_6_1_2 ) ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1356:1: ( ( rule__MenuItem__EnquiryAssignment_6_1_2 ) )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1356:1: ( ( rule__MenuItem__EnquiryAssignment_6_1_2 ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1357:1: ( rule__MenuItem__EnquiryAssignment_6_1_2 )
            {
             before(grammarAccess.getMenuItemAccess().getEnquiryAssignment_6_1_2()); 
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1358:1: ( rule__MenuItem__EnquiryAssignment_6_1_2 )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1358:2: rule__MenuItem__EnquiryAssignment_6_1_2
            {
            pushFollow(FOLLOW_rule__MenuItem__EnquiryAssignment_6_1_2_in_rule__MenuItem__Group_6_1__2__Impl2674);
            rule__MenuItem__EnquiryAssignment_6_1_2();

            state._fsp--;


            }

             after(grammarAccess.getMenuItemAccess().getEnquiryAssignment_6_1_2()); 

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
    // $ANTLR end "rule__MenuItem__Group_6_1__2__Impl"


    // $ANTLR start "rule__MenuItem__Group_6_2__0"
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1374:1: rule__MenuItem__Group_6_2__0 : rule__MenuItem__Group_6_2__0__Impl rule__MenuItem__Group_6_2__1 ;
    public final void rule__MenuItem__Group_6_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1378:1: ( rule__MenuItem__Group_6_2__0__Impl rule__MenuItem__Group_6_2__1 )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1379:2: rule__MenuItem__Group_6_2__0__Impl rule__MenuItem__Group_6_2__1
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_6_2__0__Impl_in_rule__MenuItem__Group_6_2__02710);
            rule__MenuItem__Group_6_2__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuItem__Group_6_2__1_in_rule__MenuItem__Group_6_2__02713);
            rule__MenuItem__Group_6_2__1();

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
    // $ANTLR end "rule__MenuItem__Group_6_2__0"


    // $ANTLR start "rule__MenuItem__Group_6_2__0__Impl"
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1386:1: rule__MenuItem__Group_6_2__0__Impl : ( 'composite-screen' ) ;
    public final void rule__MenuItem__Group_6_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1390:1: ( ( 'composite-screen' ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1391:1: ( 'composite-screen' )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1391:1: ( 'composite-screen' )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1392:1: 'composite-screen'
            {
             before(grammarAccess.getMenuItemAccess().getCompositeScreenKeyword_6_2_0()); 
            match(input,22,FOLLOW_22_in_rule__MenuItem__Group_6_2__0__Impl2741); 
             after(grammarAccess.getMenuItemAccess().getCompositeScreenKeyword_6_2_0()); 

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
    // $ANTLR end "rule__MenuItem__Group_6_2__0__Impl"


    // $ANTLR start "rule__MenuItem__Group_6_2__1"
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1405:1: rule__MenuItem__Group_6_2__1 : rule__MenuItem__Group_6_2__1__Impl rule__MenuItem__Group_6_2__2 ;
    public final void rule__MenuItem__Group_6_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1409:1: ( rule__MenuItem__Group_6_2__1__Impl rule__MenuItem__Group_6_2__2 )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1410:2: rule__MenuItem__Group_6_2__1__Impl rule__MenuItem__Group_6_2__2
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_6_2__1__Impl_in_rule__MenuItem__Group_6_2__12772);
            rule__MenuItem__Group_6_2__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuItem__Group_6_2__2_in_rule__MenuItem__Group_6_2__12775);
            rule__MenuItem__Group_6_2__2();

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
    // $ANTLR end "rule__MenuItem__Group_6_2__1"


    // $ANTLR start "rule__MenuItem__Group_6_2__1__Impl"
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1417:1: rule__MenuItem__Group_6_2__1__Impl : ( '=' ) ;
    public final void rule__MenuItem__Group_6_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1421:1: ( ( '=' ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1422:1: ( '=' )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1422:1: ( '=' )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1423:1: '='
            {
             before(grammarAccess.getMenuItemAccess().getEqualsSignKeyword_6_2_1()); 
            match(input,13,FOLLOW_13_in_rule__MenuItem__Group_6_2__1__Impl2803); 
             after(grammarAccess.getMenuItemAccess().getEqualsSignKeyword_6_2_1()); 

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
    // $ANTLR end "rule__MenuItem__Group_6_2__1__Impl"


    // $ANTLR start "rule__MenuItem__Group_6_2__2"
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1436:1: rule__MenuItem__Group_6_2__2 : rule__MenuItem__Group_6_2__2__Impl ;
    public final void rule__MenuItem__Group_6_2__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1440:1: ( rule__MenuItem__Group_6_2__2__Impl )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1441:2: rule__MenuItem__Group_6_2__2__Impl
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_6_2__2__Impl_in_rule__MenuItem__Group_6_2__22834);
            rule__MenuItem__Group_6_2__2__Impl();

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
    // $ANTLR end "rule__MenuItem__Group_6_2__2"


    // $ANTLR start "rule__MenuItem__Group_6_2__2__Impl"
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1447:1: rule__MenuItem__Group_6_2__2__Impl : ( ( rule__MenuItem__CompositeScreenAssignment_6_2_2 ) ) ;
    public final void rule__MenuItem__Group_6_2__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1451:1: ( ( ( rule__MenuItem__CompositeScreenAssignment_6_2_2 ) ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1452:1: ( ( rule__MenuItem__CompositeScreenAssignment_6_2_2 ) )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1452:1: ( ( rule__MenuItem__CompositeScreenAssignment_6_2_2 ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1453:1: ( rule__MenuItem__CompositeScreenAssignment_6_2_2 )
            {
             before(grammarAccess.getMenuItemAccess().getCompositeScreenAssignment_6_2_2()); 
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1454:1: ( rule__MenuItem__CompositeScreenAssignment_6_2_2 )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1454:2: rule__MenuItem__CompositeScreenAssignment_6_2_2
            {
            pushFollow(FOLLOW_rule__MenuItem__CompositeScreenAssignment_6_2_2_in_rule__MenuItem__Group_6_2__2__Impl2861);
            rule__MenuItem__CompositeScreenAssignment_6_2_2();

            state._fsp--;


            }

             after(grammarAccess.getMenuItemAccess().getCompositeScreenAssignment_6_2_2()); 

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
    // $ANTLR end "rule__MenuItem__Group_6_2__2__Impl"


    // $ANTLR start "rule__MenuItem__Group_6_3__0"
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1470:1: rule__MenuItem__Group_6_3__0 : rule__MenuItem__Group_6_3__0__Impl rule__MenuItem__Group_6_3__1 ;
    public final void rule__MenuItem__Group_6_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1474:1: ( rule__MenuItem__Group_6_3__0__Impl rule__MenuItem__Group_6_3__1 )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1475:2: rule__MenuItem__Group_6_3__0__Impl rule__MenuItem__Group_6_3__1
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_6_3__0__Impl_in_rule__MenuItem__Group_6_3__02897);
            rule__MenuItem__Group_6_3__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuItem__Group_6_3__1_in_rule__MenuItem__Group_6_3__02900);
            rule__MenuItem__Group_6_3__1();

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
    // $ANTLR end "rule__MenuItem__Group_6_3__0"


    // $ANTLR start "rule__MenuItem__Group_6_3__0__Impl"
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1482:1: rule__MenuItem__Group_6_3__0__Impl : ( 'include-menu' ) ;
    public final void rule__MenuItem__Group_6_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1486:1: ( ( 'include-menu' ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1487:1: ( 'include-menu' )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1487:1: ( 'include-menu' )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1488:1: 'include-menu'
            {
             before(grammarAccess.getMenuItemAccess().getIncludeMenuKeyword_6_3_0()); 
            match(input,23,FOLLOW_23_in_rule__MenuItem__Group_6_3__0__Impl2928); 
             after(grammarAccess.getMenuItemAccess().getIncludeMenuKeyword_6_3_0()); 

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
    // $ANTLR end "rule__MenuItem__Group_6_3__0__Impl"


    // $ANTLR start "rule__MenuItem__Group_6_3__1"
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1501:1: rule__MenuItem__Group_6_3__1 : rule__MenuItem__Group_6_3__1__Impl rule__MenuItem__Group_6_3__2 ;
    public final void rule__MenuItem__Group_6_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1505:1: ( rule__MenuItem__Group_6_3__1__Impl rule__MenuItem__Group_6_3__2 )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1506:2: rule__MenuItem__Group_6_3__1__Impl rule__MenuItem__Group_6_3__2
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_6_3__1__Impl_in_rule__MenuItem__Group_6_3__12959);
            rule__MenuItem__Group_6_3__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuItem__Group_6_3__2_in_rule__MenuItem__Group_6_3__12962);
            rule__MenuItem__Group_6_3__2();

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
    // $ANTLR end "rule__MenuItem__Group_6_3__1"


    // $ANTLR start "rule__MenuItem__Group_6_3__1__Impl"
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1513:1: rule__MenuItem__Group_6_3__1__Impl : ( '=' ) ;
    public final void rule__MenuItem__Group_6_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1517:1: ( ( '=' ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1518:1: ( '=' )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1518:1: ( '=' )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1519:1: '='
            {
             before(grammarAccess.getMenuItemAccess().getEqualsSignKeyword_6_3_1()); 
            match(input,13,FOLLOW_13_in_rule__MenuItem__Group_6_3__1__Impl2990); 
             after(grammarAccess.getMenuItemAccess().getEqualsSignKeyword_6_3_1()); 

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
    // $ANTLR end "rule__MenuItem__Group_6_3__1__Impl"


    // $ANTLR start "rule__MenuItem__Group_6_3__2"
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1532:1: rule__MenuItem__Group_6_3__2 : rule__MenuItem__Group_6_3__2__Impl ;
    public final void rule__MenuItem__Group_6_3__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1536:1: ( rule__MenuItem__Group_6_3__2__Impl )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1537:2: rule__MenuItem__Group_6_3__2__Impl
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_6_3__2__Impl_in_rule__MenuItem__Group_6_3__23021);
            rule__MenuItem__Group_6_3__2__Impl();

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
    // $ANTLR end "rule__MenuItem__Group_6_3__2"


    // $ANTLR start "rule__MenuItem__Group_6_3__2__Impl"
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1543:1: rule__MenuItem__Group_6_3__2__Impl : ( ( rule__MenuItem__IncludedMenuAssignment_6_3_2 ) ) ;
    public final void rule__MenuItem__Group_6_3__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1547:1: ( ( ( rule__MenuItem__IncludedMenuAssignment_6_3_2 ) ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1548:1: ( ( rule__MenuItem__IncludedMenuAssignment_6_3_2 ) )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1548:1: ( ( rule__MenuItem__IncludedMenuAssignment_6_3_2 ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1549:1: ( rule__MenuItem__IncludedMenuAssignment_6_3_2 )
            {
             before(grammarAccess.getMenuItemAccess().getIncludedMenuAssignment_6_3_2()); 
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1550:1: ( rule__MenuItem__IncludedMenuAssignment_6_3_2 )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1550:2: rule__MenuItem__IncludedMenuAssignment_6_3_2
            {
            pushFollow(FOLLOW_rule__MenuItem__IncludedMenuAssignment_6_3_2_in_rule__MenuItem__Group_6_3__2__Impl3048);
            rule__MenuItem__IncludedMenuAssignment_6_3_2();

            state._fsp--;


            }

             after(grammarAccess.getMenuItemAccess().getIncludedMenuAssignment_6_3_2()); 

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
    // $ANTLR end "rule__MenuItem__Group_6_3__2__Impl"


    // $ANTLR start "rule__MenuItem__Group_6_4__0"
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1566:1: rule__MenuItem__Group_6_4__0 : rule__MenuItem__Group_6_4__0__Impl rule__MenuItem__Group_6_4__1 ;
    public final void rule__MenuItem__Group_6_4__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1570:1: ( rule__MenuItem__Group_6_4__0__Impl rule__MenuItem__Group_6_4__1 )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1571:2: rule__MenuItem__Group_6_4__0__Impl rule__MenuItem__Group_6_4__1
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_6_4__0__Impl_in_rule__MenuItem__Group_6_4__03084);
            rule__MenuItem__Group_6_4__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuItem__Group_6_4__1_in_rule__MenuItem__Group_6_4__03087);
            rule__MenuItem__Group_6_4__1();

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
    // $ANTLR end "rule__MenuItem__Group_6_4__0"


    // $ANTLR start "rule__MenuItem__Group_6_4__0__Impl"
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1578:1: rule__MenuItem__Group_6_4__0__Impl : ( 'application' ) ;
    public final void rule__MenuItem__Group_6_4__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1582:1: ( ( 'application' ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1583:1: ( 'application' )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1583:1: ( 'application' )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1584:1: 'application'
            {
             before(grammarAccess.getMenuItemAccess().getApplicationKeyword_6_4_0()); 
            match(input,24,FOLLOW_24_in_rule__MenuItem__Group_6_4__0__Impl3115); 
             after(grammarAccess.getMenuItemAccess().getApplicationKeyword_6_4_0()); 

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
    // $ANTLR end "rule__MenuItem__Group_6_4__0__Impl"


    // $ANTLR start "rule__MenuItem__Group_6_4__1"
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1597:1: rule__MenuItem__Group_6_4__1 : rule__MenuItem__Group_6_4__1__Impl rule__MenuItem__Group_6_4__2 ;
    public final void rule__MenuItem__Group_6_4__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1601:1: ( rule__MenuItem__Group_6_4__1__Impl rule__MenuItem__Group_6_4__2 )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1602:2: rule__MenuItem__Group_6_4__1__Impl rule__MenuItem__Group_6_4__2
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_6_4__1__Impl_in_rule__MenuItem__Group_6_4__13146);
            rule__MenuItem__Group_6_4__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuItem__Group_6_4__2_in_rule__MenuItem__Group_6_4__13149);
            rule__MenuItem__Group_6_4__2();

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
    // $ANTLR end "rule__MenuItem__Group_6_4__1"


    // $ANTLR start "rule__MenuItem__Group_6_4__1__Impl"
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1609:1: rule__MenuItem__Group_6_4__1__Impl : ( '=' ) ;
    public final void rule__MenuItem__Group_6_4__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1613:1: ( ( '=' ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1614:1: ( '=' )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1614:1: ( '=' )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1615:1: '='
            {
             before(grammarAccess.getMenuItemAccess().getEqualsSignKeyword_6_4_1()); 
            match(input,13,FOLLOW_13_in_rule__MenuItem__Group_6_4__1__Impl3177); 
             after(grammarAccess.getMenuItemAccess().getEqualsSignKeyword_6_4_1()); 

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
    // $ANTLR end "rule__MenuItem__Group_6_4__1__Impl"


    // $ANTLR start "rule__MenuItem__Group_6_4__2"
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1628:1: rule__MenuItem__Group_6_4__2 : rule__MenuItem__Group_6_4__2__Impl ;
    public final void rule__MenuItem__Group_6_4__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1632:1: ( rule__MenuItem__Group_6_4__2__Impl )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1633:2: rule__MenuItem__Group_6_4__2__Impl
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_6_4__2__Impl_in_rule__MenuItem__Group_6_4__23208);
            rule__MenuItem__Group_6_4__2__Impl();

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
    // $ANTLR end "rule__MenuItem__Group_6_4__2"


    // $ANTLR start "rule__MenuItem__Group_6_4__2__Impl"
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1639:1: rule__MenuItem__Group_6_4__2__Impl : ( ( rule__MenuItem__ApplicationAssignment_6_4_2 ) ) ;
    public final void rule__MenuItem__Group_6_4__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1643:1: ( ( ( rule__MenuItem__ApplicationAssignment_6_4_2 ) ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1644:1: ( ( rule__MenuItem__ApplicationAssignment_6_4_2 ) )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1644:1: ( ( rule__MenuItem__ApplicationAssignment_6_4_2 ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1645:1: ( rule__MenuItem__ApplicationAssignment_6_4_2 )
            {
             before(grammarAccess.getMenuItemAccess().getApplicationAssignment_6_4_2()); 
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1646:1: ( rule__MenuItem__ApplicationAssignment_6_4_2 )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1646:2: rule__MenuItem__ApplicationAssignment_6_4_2
            {
            pushFollow(FOLLOW_rule__MenuItem__ApplicationAssignment_6_4_2_in_rule__MenuItem__Group_6_4__2__Impl3235);
            rule__MenuItem__ApplicationAssignment_6_4_2();

            state._fsp--;


            }

             after(grammarAccess.getMenuItemAccess().getApplicationAssignment_6_4_2()); 

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
    // $ANTLR end "rule__MenuItem__Group_6_4__2__Impl"


    // $ANTLR start "rule__MenuItem__Group_7__0"
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1662:1: rule__MenuItem__Group_7__0 : rule__MenuItem__Group_7__0__Impl rule__MenuItem__Group_7__1 ;
    public final void rule__MenuItem__Group_7__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1666:1: ( rule__MenuItem__Group_7__0__Impl rule__MenuItem__Group_7__1 )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1667:2: rule__MenuItem__Group_7__0__Impl rule__MenuItem__Group_7__1
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_7__0__Impl_in_rule__MenuItem__Group_7__03271);
            rule__MenuItem__Group_7__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuItem__Group_7__1_in_rule__MenuItem__Group_7__03274);
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1674:1: rule__MenuItem__Group_7__0__Impl : ( 'parameters' ) ;
    public final void rule__MenuItem__Group_7__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1678:1: ( ( 'parameters' ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1679:1: ( 'parameters' )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1679:1: ( 'parameters' )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1680:1: 'parameters'
            {
             before(grammarAccess.getMenuItemAccess().getParametersKeyword_7_0()); 
            match(input,25,FOLLOW_25_in_rule__MenuItem__Group_7__0__Impl3302); 
             after(grammarAccess.getMenuItemAccess().getParametersKeyword_7_0()); 

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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1693:1: rule__MenuItem__Group_7__1 : rule__MenuItem__Group_7__1__Impl rule__MenuItem__Group_7__2 ;
    public final void rule__MenuItem__Group_7__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1697:1: ( rule__MenuItem__Group_7__1__Impl rule__MenuItem__Group_7__2 )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1698:2: rule__MenuItem__Group_7__1__Impl rule__MenuItem__Group_7__2
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_7__1__Impl_in_rule__MenuItem__Group_7__13333);
            rule__MenuItem__Group_7__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuItem__Group_7__2_in_rule__MenuItem__Group_7__13336);
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1705:1: rule__MenuItem__Group_7__1__Impl : ( '=' ) ;
    public final void rule__MenuItem__Group_7__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1709:1: ( ( '=' ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1710:1: ( '=' )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1710:1: ( '=' )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1711:1: '='
            {
             before(grammarAccess.getMenuItemAccess().getEqualsSignKeyword_7_1()); 
            match(input,13,FOLLOW_13_in_rule__MenuItem__Group_7__1__Impl3364); 
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1724:1: rule__MenuItem__Group_7__2 : rule__MenuItem__Group_7__2__Impl ;
    public final void rule__MenuItem__Group_7__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1728:1: ( rule__MenuItem__Group_7__2__Impl )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1729:2: rule__MenuItem__Group_7__2__Impl
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_7__2__Impl_in_rule__MenuItem__Group_7__23395);
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1735:1: rule__MenuItem__Group_7__2__Impl : ( ( rule__MenuItem__ParametersAssignment_7_2 ) ) ;
    public final void rule__MenuItem__Group_7__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1739:1: ( ( ( rule__MenuItem__ParametersAssignment_7_2 ) ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1740:1: ( ( rule__MenuItem__ParametersAssignment_7_2 ) )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1740:1: ( ( rule__MenuItem__ParametersAssignment_7_2 ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1741:1: ( rule__MenuItem__ParametersAssignment_7_2 )
            {
             before(grammarAccess.getMenuItemAccess().getParametersAssignment_7_2()); 
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1742:1: ( rule__MenuItem__ParametersAssignment_7_2 )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1742:2: rule__MenuItem__ParametersAssignment_7_2
            {
            pushFollow(FOLLOW_rule__MenuItem__ParametersAssignment_7_2_in_rule__MenuItem__Group_7__2__Impl3422);
            rule__MenuItem__ParametersAssignment_7_2();

            state._fsp--;


            }

             after(grammarAccess.getMenuItemAccess().getParametersAssignment_7_2()); 

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


    // $ANTLR start "rule__MenuItem__Group_8__0"
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1758:1: rule__MenuItem__Group_8__0 : rule__MenuItem__Group_8__0__Impl rule__MenuItem__Group_8__1 ;
    public final void rule__MenuItem__Group_8__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1762:1: ( rule__MenuItem__Group_8__0__Impl rule__MenuItem__Group_8__1 )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1763:2: rule__MenuItem__Group_8__0__Impl rule__MenuItem__Group_8__1
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_8__0__Impl_in_rule__MenuItem__Group_8__03458);
            rule__MenuItem__Group_8__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuItem__Group_8__1_in_rule__MenuItem__Group_8__03461);
            rule__MenuItem__Group_8__1();

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
    // $ANTLR end "rule__MenuItem__Group_8__0"


    // $ANTLR start "rule__MenuItem__Group_8__0__Impl"
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1770:1: rule__MenuItem__Group_8__0__Impl : ( '{' ) ;
    public final void rule__MenuItem__Group_8__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1774:1: ( ( '{' ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1775:1: ( '{' )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1775:1: ( '{' )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1776:1: '{'
            {
             before(grammarAccess.getMenuItemAccess().getLeftCurlyBracketKeyword_8_0()); 
            match(input,26,FOLLOW_26_in_rule__MenuItem__Group_8__0__Impl3489); 
             after(grammarAccess.getMenuItemAccess().getLeftCurlyBracketKeyword_8_0()); 

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
    // $ANTLR end "rule__MenuItem__Group_8__0__Impl"


    // $ANTLR start "rule__MenuItem__Group_8__1"
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1789:1: rule__MenuItem__Group_8__1 : rule__MenuItem__Group_8__1__Impl rule__MenuItem__Group_8__2 ;
    public final void rule__MenuItem__Group_8__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1793:1: ( rule__MenuItem__Group_8__1__Impl rule__MenuItem__Group_8__2 )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1794:2: rule__MenuItem__Group_8__1__Impl rule__MenuItem__Group_8__2
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_8__1__Impl_in_rule__MenuItem__Group_8__13520);
            rule__MenuItem__Group_8__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuItem__Group_8__2_in_rule__MenuItem__Group_8__13523);
            rule__MenuItem__Group_8__2();

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
    // $ANTLR end "rule__MenuItem__Group_8__1"


    // $ANTLR start "rule__MenuItem__Group_8__1__Impl"
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1801:1: rule__MenuItem__Group_8__1__Impl : ( ( rule__MenuItem__Group_8_1__0 )? ) ;
    public final void rule__MenuItem__Group_8__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1805:1: ( ( ( rule__MenuItem__Group_8_1__0 )? ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1806:1: ( ( rule__MenuItem__Group_8_1__0 )? )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1806:1: ( ( rule__MenuItem__Group_8_1__0 )? )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1807:1: ( rule__MenuItem__Group_8_1__0 )?
            {
             before(grammarAccess.getMenuItemAccess().getGroup_8_1()); 
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1808:1: ( rule__MenuItem__Group_8_1__0 )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==RULE_VALUE) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1808:2: rule__MenuItem__Group_8_1__0
                    {
                    pushFollow(FOLLOW_rule__MenuItem__Group_8_1__0_in_rule__MenuItem__Group_8__1__Impl3550);
                    rule__MenuItem__Group_8_1__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getMenuItemAccess().getGroup_8_1()); 

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
    // $ANTLR end "rule__MenuItem__Group_8__1__Impl"


    // $ANTLR start "rule__MenuItem__Group_8__2"
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1818:1: rule__MenuItem__Group_8__2 : rule__MenuItem__Group_8__2__Impl ;
    public final void rule__MenuItem__Group_8__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1822:1: ( rule__MenuItem__Group_8__2__Impl )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1823:2: rule__MenuItem__Group_8__2__Impl
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_8__2__Impl_in_rule__MenuItem__Group_8__23581);
            rule__MenuItem__Group_8__2__Impl();

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
    // $ANTLR end "rule__MenuItem__Group_8__2"


    // $ANTLR start "rule__MenuItem__Group_8__2__Impl"
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1829:1: rule__MenuItem__Group_8__2__Impl : ( '}' ) ;
    public final void rule__MenuItem__Group_8__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1833:1: ( ( '}' ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1834:1: ( '}' )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1834:1: ( '}' )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1835:1: '}'
            {
             before(grammarAccess.getMenuItemAccess().getRightCurlyBracketKeyword_8_2()); 
            match(input,27,FOLLOW_27_in_rule__MenuItem__Group_8__2__Impl3609); 
             after(grammarAccess.getMenuItemAccess().getRightCurlyBracketKeyword_8_2()); 

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
    // $ANTLR end "rule__MenuItem__Group_8__2__Impl"


    // $ANTLR start "rule__MenuItem__Group_8_1__0"
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1854:1: rule__MenuItem__Group_8_1__0 : rule__MenuItem__Group_8_1__0__Impl rule__MenuItem__Group_8_1__1 ;
    public final void rule__MenuItem__Group_8_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1858:1: ( rule__MenuItem__Group_8_1__0__Impl rule__MenuItem__Group_8_1__1 )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1859:2: rule__MenuItem__Group_8_1__0__Impl rule__MenuItem__Group_8_1__1
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_8_1__0__Impl_in_rule__MenuItem__Group_8_1__03646);
            rule__MenuItem__Group_8_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__MenuItem__Group_8_1__1_in_rule__MenuItem__Group_8_1__03649);
            rule__MenuItem__Group_8_1__1();

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
    // $ANTLR end "rule__MenuItem__Group_8_1__0"


    // $ANTLR start "rule__MenuItem__Group_8_1__0__Impl"
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1866:1: rule__MenuItem__Group_8_1__0__Impl : ( ( rule__MenuItem__SubmenusAssignment_8_1_0 ) ) ;
    public final void rule__MenuItem__Group_8_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1870:1: ( ( ( rule__MenuItem__SubmenusAssignment_8_1_0 ) ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1871:1: ( ( rule__MenuItem__SubmenusAssignment_8_1_0 ) )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1871:1: ( ( rule__MenuItem__SubmenusAssignment_8_1_0 ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1872:1: ( rule__MenuItem__SubmenusAssignment_8_1_0 )
            {
             before(grammarAccess.getMenuItemAccess().getSubmenusAssignment_8_1_0()); 
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1873:1: ( rule__MenuItem__SubmenusAssignment_8_1_0 )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1873:2: rule__MenuItem__SubmenusAssignment_8_1_0
            {
            pushFollow(FOLLOW_rule__MenuItem__SubmenusAssignment_8_1_0_in_rule__MenuItem__Group_8_1__0__Impl3676);
            rule__MenuItem__SubmenusAssignment_8_1_0();

            state._fsp--;


            }

             after(grammarAccess.getMenuItemAccess().getSubmenusAssignment_8_1_0()); 

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
    // $ANTLR end "rule__MenuItem__Group_8_1__0__Impl"


    // $ANTLR start "rule__MenuItem__Group_8_1__1"
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1883:1: rule__MenuItem__Group_8_1__1 : rule__MenuItem__Group_8_1__1__Impl ;
    public final void rule__MenuItem__Group_8_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1887:1: ( rule__MenuItem__Group_8_1__1__Impl )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1888:2: rule__MenuItem__Group_8_1__1__Impl
            {
            pushFollow(FOLLOW_rule__MenuItem__Group_8_1__1__Impl_in_rule__MenuItem__Group_8_1__13706);
            rule__MenuItem__Group_8_1__1__Impl();

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
    // $ANTLR end "rule__MenuItem__Group_8_1__1"


    // $ANTLR start "rule__MenuItem__Group_8_1__1__Impl"
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1894:1: rule__MenuItem__Group_8_1__1__Impl : ( ( rule__MenuItem__SubmenusAssignment_8_1_1 )* ) ;
    public final void rule__MenuItem__Group_8_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1898:1: ( ( ( rule__MenuItem__SubmenusAssignment_8_1_1 )* ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1899:1: ( ( rule__MenuItem__SubmenusAssignment_8_1_1 )* )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1899:1: ( ( rule__MenuItem__SubmenusAssignment_8_1_1 )* )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1900:1: ( rule__MenuItem__SubmenusAssignment_8_1_1 )*
            {
             before(grammarAccess.getMenuItemAccess().getSubmenusAssignment_8_1_1()); 
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1901:1: ( rule__MenuItem__SubmenusAssignment_8_1_1 )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==RULE_VALUE) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1901:2: rule__MenuItem__SubmenusAssignment_8_1_1
            	    {
            	    pushFollow(FOLLOW_rule__MenuItem__SubmenusAssignment_8_1_1_in_rule__MenuItem__Group_8_1__1__Impl3733);
            	    rule__MenuItem__SubmenusAssignment_8_1_1();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop13;
                }
            } while (true);

             after(grammarAccess.getMenuItemAccess().getSubmenusAssignment_8_1_1()); 

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
    // $ANTLR end "rule__MenuItem__Group_8_1__1__Impl"


    // $ANTLR start "rule__Translation__Group__0"
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1915:1: rule__Translation__Group__0 : rule__Translation__Group__0__Impl rule__Translation__Group__1 ;
    public final void rule__Translation__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1919:1: ( rule__Translation__Group__0__Impl rule__Translation__Group__1 )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1920:2: rule__Translation__Group__0__Impl rule__Translation__Group__1
            {
            pushFollow(FOLLOW_rule__Translation__Group__0__Impl_in_rule__Translation__Group__03768);
            rule__Translation__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Translation__Group__1_in_rule__Translation__Group__03771);
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1927:1: rule__Translation__Group__0__Impl : ( ( rule__Translation__LanguageAssignment_0 ) ) ;
    public final void rule__Translation__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1931:1: ( ( ( rule__Translation__LanguageAssignment_0 ) ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1932:1: ( ( rule__Translation__LanguageAssignment_0 ) )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1932:1: ( ( rule__Translation__LanguageAssignment_0 ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1933:1: ( rule__Translation__LanguageAssignment_0 )
            {
             before(grammarAccess.getTranslationAccess().getLanguageAssignment_0()); 
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1934:1: ( rule__Translation__LanguageAssignment_0 )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1934:2: rule__Translation__LanguageAssignment_0
            {
            pushFollow(FOLLOW_rule__Translation__LanguageAssignment_0_in_rule__Translation__Group__0__Impl3798);
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1944:1: rule__Translation__Group__1 : rule__Translation__Group__1__Impl rule__Translation__Group__2 ;
    public final void rule__Translation__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1948:1: ( rule__Translation__Group__1__Impl rule__Translation__Group__2 )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1949:2: rule__Translation__Group__1__Impl rule__Translation__Group__2
            {
            pushFollow(FOLLOW_rule__Translation__Group__1__Impl_in_rule__Translation__Group__13828);
            rule__Translation__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_rule__Translation__Group__2_in_rule__Translation__Group__13831);
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1956:1: rule__Translation__Group__1__Impl : ( '=' ) ;
    public final void rule__Translation__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1960:1: ( ( '=' ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1961:1: ( '=' )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1961:1: ( '=' )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1962:1: '='
            {
             before(grammarAccess.getTranslationAccess().getEqualsSignKeyword_1()); 
            match(input,13,FOLLOW_13_in_rule__Translation__Group__1__Impl3859); 
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1975:1: rule__Translation__Group__2 : rule__Translation__Group__2__Impl ;
    public final void rule__Translation__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1979:1: ( rule__Translation__Group__2__Impl )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1980:2: rule__Translation__Group__2__Impl
            {
            pushFollow(FOLLOW_rule__Translation__Group__2__Impl_in_rule__Translation__Group__23890);
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1986:1: rule__Translation__Group__2__Impl : ( ( rule__Translation__MessageAssignment_2 ) ) ;
    public final void rule__Translation__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1990:1: ( ( ( rule__Translation__MessageAssignment_2 ) ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1991:1: ( ( rule__Translation__MessageAssignment_2 ) )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1991:1: ( ( rule__Translation__MessageAssignment_2 ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1992:1: ( rule__Translation__MessageAssignment_2 )
            {
             before(grammarAccess.getTranslationAccess().getMessageAssignment_2()); 
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1993:1: ( rule__Translation__MessageAssignment_2 )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1993:2: rule__Translation__MessageAssignment_2
            {
            pushFollow(FOLLOW_rule__Translation__MessageAssignment_2_in_rule__Translation__Group__2__Impl3917);
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2010:1: rule__MenuRoot__MetamodelVersionAssignment_2 : ( RULE_STRING ) ;
    public final void rule__MenuRoot__MetamodelVersionAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2014:1: ( ( RULE_STRING ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2015:1: ( RULE_STRING )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2015:1: ( RULE_STRING )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2016:1: RULE_STRING
            {
             before(grammarAccess.getMenuRootAccess().getMetamodelVersionSTRINGTerminalRuleCall_2_0()); 
            match(input,RULE_STRING,FOLLOW_RULE_STRING_in_rule__MenuRoot__MetamodelVersionAssignment_23958); 
             after(grammarAccess.getMenuRootAccess().getMetamodelVersionSTRINGTerminalRuleCall_2_0()); 

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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2025:1: rule__MenuRoot__RootItemAssignment_3 : ( ruleMenuItem ) ;
    public final void rule__MenuRoot__RootItemAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2029:1: ( ( ruleMenuItem ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2030:1: ( ruleMenuItem )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2030:1: ( ruleMenuItem )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2031:1: ruleMenuItem
            {
             before(grammarAccess.getMenuRootAccess().getRootItemMenuItemParserRuleCall_3_0()); 
            pushFollow(FOLLOW_ruleMenuItem_in_rule__MenuRoot__RootItemAssignment_33989);
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2040:1: rule__MenuItem__NameAssignment_0 : ( RULE_VALUE ) ;
    public final void rule__MenuItem__NameAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2044:1: ( ( RULE_VALUE ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2045:1: ( RULE_VALUE )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2045:1: ( RULE_VALUE )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2046:1: RULE_VALUE
            {
             before(grammarAccess.getMenuItemAccess().getNameVALUETerminalRuleCall_0_0()); 
            match(input,RULE_VALUE,FOLLOW_RULE_VALUE_in_rule__MenuItem__NameAssignment_04020); 
             after(grammarAccess.getMenuItemAccess().getNameVALUETerminalRuleCall_0_0()); 

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


    // $ANTLR start "rule__MenuItem__EnabledAssignment_1_2"
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2055:1: rule__MenuItem__EnabledAssignment_1_2 : ( ruleEnabled ) ;
    public final void rule__MenuItem__EnabledAssignment_1_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2059:1: ( ( ruleEnabled ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2060:1: ( ruleEnabled )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2060:1: ( ruleEnabled )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2061:1: ruleEnabled
            {
             before(grammarAccess.getMenuItemAccess().getEnabledEnabledEnumRuleCall_1_2_0()); 
            pushFollow(FOLLOW_ruleEnabled_in_rule__MenuItem__EnabledAssignment_1_24051);
            ruleEnabled();

            state._fsp--;

             after(grammarAccess.getMenuItemAccess().getEnabledEnabledEnumRuleCall_1_2_0()); 

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
    // $ANTLR end "rule__MenuItem__EnabledAssignment_1_2"


    // $ANTLR start "rule__MenuItem__DisplayTabsAssignment_2_2"
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2070:1: rule__MenuItem__DisplayTabsAssignment_2_2 : ( ( 'true' ) ) ;
    public final void rule__MenuItem__DisplayTabsAssignment_2_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2074:1: ( ( ( 'true' ) ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2075:1: ( ( 'true' ) )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2075:1: ( ( 'true' ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2076:1: ( 'true' )
            {
             before(grammarAccess.getMenuItemAccess().getDisplayTabsTrueKeyword_2_2_0()); 
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2077:1: ( 'true' )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2078:1: 'true'
            {
             before(grammarAccess.getMenuItemAccess().getDisplayTabsTrueKeyword_2_2_0()); 
            match(input,9,FOLLOW_9_in_rule__MenuItem__DisplayTabsAssignment_2_24087); 
             after(grammarAccess.getMenuItemAccess().getDisplayTabsTrueKeyword_2_2_0()); 

            }

             after(grammarAccess.getMenuItemAccess().getDisplayTabsTrueKeyword_2_2_0()); 

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
    // $ANTLR end "rule__MenuItem__DisplayTabsAssignment_2_2"


    // $ANTLR start "rule__MenuItem__SecurityRoleAssignment_3_2"
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2093:1: rule__MenuItem__SecurityRoleAssignment_3_2 : ( RULE_STRING ) ;
    public final void rule__MenuItem__SecurityRoleAssignment_3_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2097:1: ( ( RULE_STRING ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2098:1: ( RULE_STRING )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2098:1: ( RULE_STRING )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2099:1: RULE_STRING
            {
             before(grammarAccess.getMenuItemAccess().getSecurityRoleSTRINGTerminalRuleCall_3_2_0()); 
            match(input,RULE_STRING,FOLLOW_RULE_STRING_in_rule__MenuItem__SecurityRoleAssignment_3_24126); 
             after(grammarAccess.getMenuItemAccess().getSecurityRoleSTRINGTerminalRuleCall_3_2_0()); 

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
    // $ANTLR end "rule__MenuItem__SecurityRoleAssignment_3_2"


    // $ANTLR start "rule__MenuItem__ShortcutAssignment_4_2"
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2108:1: rule__MenuItem__ShortcutAssignment_4_2 : ( RULE_STRING ) ;
    public final void rule__MenuItem__ShortcutAssignment_4_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2112:1: ( ( RULE_STRING ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2113:1: ( RULE_STRING )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2113:1: ( RULE_STRING )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2114:1: RULE_STRING
            {
             before(grammarAccess.getMenuItemAccess().getShortcutSTRINGTerminalRuleCall_4_2_0()); 
            match(input,RULE_STRING,FOLLOW_RULE_STRING_in_rule__MenuItem__ShortcutAssignment_4_24157); 
             after(grammarAccess.getMenuItemAccess().getShortcutSTRINGTerminalRuleCall_4_2_0()); 

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
    // $ANTLR end "rule__MenuItem__ShortcutAssignment_4_2"


    // $ANTLR start "rule__MenuItem__LabelsAssignment_5_1"
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2123:1: rule__MenuItem__LabelsAssignment_5_1 : ( ruleTranslation ) ;
    public final void rule__MenuItem__LabelsAssignment_5_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2127:1: ( ( ruleTranslation ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2128:1: ( ruleTranslation )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2128:1: ( ruleTranslation )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2129:1: ruleTranslation
            {
             before(grammarAccess.getMenuItemAccess().getLabelsTranslationParserRuleCall_5_1_0()); 
            pushFollow(FOLLOW_ruleTranslation_in_rule__MenuItem__LabelsAssignment_5_14188);
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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2138:1: rule__MenuItem__LabelsAssignment_5_2_1 : ( ruleTranslation ) ;
    public final void rule__MenuItem__LabelsAssignment_5_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2142:1: ( ( ruleTranslation ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2143:1: ( ruleTranslation )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2143:1: ( ruleTranslation )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2144:1: ruleTranslation
            {
             before(grammarAccess.getMenuItemAccess().getLabelsTranslationParserRuleCall_5_2_1_0()); 
            pushFollow(FOLLOW_ruleTranslation_in_rule__MenuItem__LabelsAssignment_5_2_14219);
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


    // $ANTLR start "rule__MenuItem__VersionAssignment_6_0_2"
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2153:1: rule__MenuItem__VersionAssignment_6_0_2 : ( ( RULE_VALUE ) ) ;
    public final void rule__MenuItem__VersionAssignment_6_0_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2157:1: ( ( ( RULE_VALUE ) ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2158:1: ( ( RULE_VALUE ) )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2158:1: ( ( RULE_VALUE ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2159:1: ( RULE_VALUE )
            {
             before(grammarAccess.getMenuItemAccess().getVersionVersionCrossReference_6_0_2_0()); 
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2160:1: ( RULE_VALUE )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2161:1: RULE_VALUE
            {
             before(grammarAccess.getMenuItemAccess().getVersionVersionVALUETerminalRuleCall_6_0_2_0_1()); 
            match(input,RULE_VALUE,FOLLOW_RULE_VALUE_in_rule__MenuItem__VersionAssignment_6_0_24254); 
             after(grammarAccess.getMenuItemAccess().getVersionVersionVALUETerminalRuleCall_6_0_2_0_1()); 

            }

             after(grammarAccess.getMenuItemAccess().getVersionVersionCrossReference_6_0_2_0()); 

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
    // $ANTLR end "rule__MenuItem__VersionAssignment_6_0_2"


    // $ANTLR start "rule__MenuItem__EnquiryAssignment_6_1_2"
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2172:1: rule__MenuItem__EnquiryAssignment_6_1_2 : ( ( RULE_VALUE ) ) ;
    public final void rule__MenuItem__EnquiryAssignment_6_1_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2176:1: ( ( ( RULE_VALUE ) ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2177:1: ( ( RULE_VALUE ) )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2177:1: ( ( RULE_VALUE ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2178:1: ( RULE_VALUE )
            {
             before(grammarAccess.getMenuItemAccess().getEnquiryEnquiryCrossReference_6_1_2_0()); 
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2179:1: ( RULE_VALUE )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2180:1: RULE_VALUE
            {
             before(grammarAccess.getMenuItemAccess().getEnquiryEnquiryVALUETerminalRuleCall_6_1_2_0_1()); 
            match(input,RULE_VALUE,FOLLOW_RULE_VALUE_in_rule__MenuItem__EnquiryAssignment_6_1_24293); 
             after(grammarAccess.getMenuItemAccess().getEnquiryEnquiryVALUETerminalRuleCall_6_1_2_0_1()); 

            }

             after(grammarAccess.getMenuItemAccess().getEnquiryEnquiryCrossReference_6_1_2_0()); 

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
    // $ANTLR end "rule__MenuItem__EnquiryAssignment_6_1_2"


    // $ANTLR start "rule__MenuItem__CompositeScreenAssignment_6_2_2"
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2191:1: rule__MenuItem__CompositeScreenAssignment_6_2_2 : ( ( RULE_VALUE ) ) ;
    public final void rule__MenuItem__CompositeScreenAssignment_6_2_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2195:1: ( ( ( RULE_VALUE ) ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2196:1: ( ( RULE_VALUE ) )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2196:1: ( ( RULE_VALUE ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2197:1: ( RULE_VALUE )
            {
             before(grammarAccess.getMenuItemAccess().getCompositeScreenCompositeScreenCrossReference_6_2_2_0()); 
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2198:1: ( RULE_VALUE )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2199:1: RULE_VALUE
            {
             before(grammarAccess.getMenuItemAccess().getCompositeScreenCompositeScreenVALUETerminalRuleCall_6_2_2_0_1()); 
            match(input,RULE_VALUE,FOLLOW_RULE_VALUE_in_rule__MenuItem__CompositeScreenAssignment_6_2_24332); 
             after(grammarAccess.getMenuItemAccess().getCompositeScreenCompositeScreenVALUETerminalRuleCall_6_2_2_0_1()); 

            }

             after(grammarAccess.getMenuItemAccess().getCompositeScreenCompositeScreenCrossReference_6_2_2_0()); 

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
    // $ANTLR end "rule__MenuItem__CompositeScreenAssignment_6_2_2"


    // $ANTLR start "rule__MenuItem__IncludedMenuAssignment_6_3_2"
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2210:1: rule__MenuItem__IncludedMenuAssignment_6_3_2 : ( ( RULE_VALUE ) ) ;
    public final void rule__MenuItem__IncludedMenuAssignment_6_3_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2214:1: ( ( ( RULE_VALUE ) ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2215:1: ( ( RULE_VALUE ) )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2215:1: ( ( RULE_VALUE ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2216:1: ( RULE_VALUE )
            {
             before(grammarAccess.getMenuItemAccess().getIncludedMenuMenuRootCrossReference_6_3_2_0()); 
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2217:1: ( RULE_VALUE )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2218:1: RULE_VALUE
            {
             before(grammarAccess.getMenuItemAccess().getIncludedMenuMenuRootVALUETerminalRuleCall_6_3_2_0_1()); 
            match(input,RULE_VALUE,FOLLOW_RULE_VALUE_in_rule__MenuItem__IncludedMenuAssignment_6_3_24371); 
             after(grammarAccess.getMenuItemAccess().getIncludedMenuMenuRootVALUETerminalRuleCall_6_3_2_0_1()); 

            }

             after(grammarAccess.getMenuItemAccess().getIncludedMenuMenuRootCrossReference_6_3_2_0()); 

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
    // $ANTLR end "rule__MenuItem__IncludedMenuAssignment_6_3_2"


    // $ANTLR start "rule__MenuItem__ApplicationAssignment_6_4_2"
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2229:1: rule__MenuItem__ApplicationAssignment_6_4_2 : ( ( RULE_VALUE ) ) ;
    public final void rule__MenuItem__ApplicationAssignment_6_4_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2233:1: ( ( ( RULE_VALUE ) ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2234:1: ( ( RULE_VALUE ) )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2234:1: ( ( RULE_VALUE ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2235:1: ( RULE_VALUE )
            {
             before(grammarAccess.getMenuItemAccess().getApplicationMdfClassCrossReference_6_4_2_0()); 
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2236:1: ( RULE_VALUE )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2237:1: RULE_VALUE
            {
             before(grammarAccess.getMenuItemAccess().getApplicationMdfClassVALUETerminalRuleCall_6_4_2_0_1()); 
            match(input,RULE_VALUE,FOLLOW_RULE_VALUE_in_rule__MenuItem__ApplicationAssignment_6_4_24410); 
             after(grammarAccess.getMenuItemAccess().getApplicationMdfClassVALUETerminalRuleCall_6_4_2_0_1()); 

            }

             after(grammarAccess.getMenuItemAccess().getApplicationMdfClassCrossReference_6_4_2_0()); 

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
    // $ANTLR end "rule__MenuItem__ApplicationAssignment_6_4_2"


    // $ANTLR start "rule__MenuItem__ParametersAssignment_7_2"
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2248:1: rule__MenuItem__ParametersAssignment_7_2 : ( RULE_STRING ) ;
    public final void rule__MenuItem__ParametersAssignment_7_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2252:1: ( ( RULE_STRING ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2253:1: ( RULE_STRING )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2253:1: ( RULE_STRING )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2254:1: RULE_STRING
            {
             before(grammarAccess.getMenuItemAccess().getParametersSTRINGTerminalRuleCall_7_2_0()); 
            match(input,RULE_STRING,FOLLOW_RULE_STRING_in_rule__MenuItem__ParametersAssignment_7_24445); 
             after(grammarAccess.getMenuItemAccess().getParametersSTRINGTerminalRuleCall_7_2_0()); 

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
    // $ANTLR end "rule__MenuItem__ParametersAssignment_7_2"


    // $ANTLR start "rule__MenuItem__SubmenusAssignment_8_1_0"
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2263:1: rule__MenuItem__SubmenusAssignment_8_1_0 : ( ruleMenuItem ) ;
    public final void rule__MenuItem__SubmenusAssignment_8_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2267:1: ( ( ruleMenuItem ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2268:1: ( ruleMenuItem )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2268:1: ( ruleMenuItem )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2269:1: ruleMenuItem
            {
             before(grammarAccess.getMenuItemAccess().getSubmenusMenuItemParserRuleCall_8_1_0_0()); 
            pushFollow(FOLLOW_ruleMenuItem_in_rule__MenuItem__SubmenusAssignment_8_1_04476);
            ruleMenuItem();

            state._fsp--;

             after(grammarAccess.getMenuItemAccess().getSubmenusMenuItemParserRuleCall_8_1_0_0()); 

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
    // $ANTLR end "rule__MenuItem__SubmenusAssignment_8_1_0"


    // $ANTLR start "rule__MenuItem__SubmenusAssignment_8_1_1"
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2278:1: rule__MenuItem__SubmenusAssignment_8_1_1 : ( ruleMenuItem ) ;
    public final void rule__MenuItem__SubmenusAssignment_8_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2282:1: ( ( ruleMenuItem ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2283:1: ( ruleMenuItem )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2283:1: ( ruleMenuItem )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2284:1: ruleMenuItem
            {
             before(grammarAccess.getMenuItemAccess().getSubmenusMenuItemParserRuleCall_8_1_1_0()); 
            pushFollow(FOLLOW_ruleMenuItem_in_rule__MenuItem__SubmenusAssignment_8_1_14507);
            ruleMenuItem();

            state._fsp--;

             after(grammarAccess.getMenuItemAccess().getSubmenusMenuItemParserRuleCall_8_1_1_0()); 

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
    // $ANTLR end "rule__MenuItem__SubmenusAssignment_8_1_1"


    // $ANTLR start "rule__Translation__LanguageAssignment_0"
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2293:1: rule__Translation__LanguageAssignment_0 : ( RULE_VALUE ) ;
    public final void rule__Translation__LanguageAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2297:1: ( ( RULE_VALUE ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2298:1: ( RULE_VALUE )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2298:1: ( RULE_VALUE )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2299:1: RULE_VALUE
            {
             before(grammarAccess.getTranslationAccess().getLanguageVALUETerminalRuleCall_0_0()); 
            match(input,RULE_VALUE,FOLLOW_RULE_VALUE_in_rule__Translation__LanguageAssignment_04538); 
             after(grammarAccess.getTranslationAccess().getLanguageVALUETerminalRuleCall_0_0()); 

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
    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2308:1: rule__Translation__MessageAssignment_2 : ( RULE_STRING ) ;
    public final void rule__Translation__MessageAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
            
        try {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2312:1: ( ( RULE_STRING ) )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2313:1: ( RULE_STRING )
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2313:1: ( RULE_STRING )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2314:1: RULE_STRING
            {
             before(grammarAccess.getTranslationAccess().getMessageSTRINGTerminalRuleCall_2_0()); 
            match(input,RULE_STRING,FOLLOW_RULE_STRING_in_rule__Translation__MessageAssignment_24569); 
             after(grammarAccess.getTranslationAccess().getMessageSTRINGTerminalRuleCall_2_0()); 

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
    public static final BitSet FOLLOW_rule__Enabled__Alternatives_in_ruleEnabled251 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_6_0__0_in_rule__MenuItem__Alternatives_6286 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_6_1__0_in_rule__MenuItem__Alternatives_6304 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_6_2__0_in_rule__MenuItem__Alternatives_6322 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_6_3__0_in_rule__MenuItem__Alternatives_6340 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_6_4__0_in_rule__MenuItem__Alternatives_6358 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_9_in_rule__Enabled__Alternatives392 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_10_in_rule__Enabled__Alternatives413 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_11_in_rule__Enabled__Alternatives434 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuRoot__Group__0__Impl_in_rule__MenuRoot__Group__0467 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_rule__MenuRoot__Group__1_in_rule__MenuRoot__Group__0470 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_12_in_rule__MenuRoot__Group__0__Impl498 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuRoot__Group__1__Impl_in_rule__MenuRoot__Group__1529 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__MenuRoot__Group__2_in_rule__MenuRoot__Group__1532 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_13_in_rule__MenuRoot__Group__1__Impl560 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuRoot__Group__2__Impl_in_rule__MenuRoot__Group__2591 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_rule__MenuRoot__Group__3_in_rule__MenuRoot__Group__2594 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuRoot__MetamodelVersionAssignment_2_in_rule__MenuRoot__Group__2__Impl621 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuRoot__Group__3__Impl_in_rule__MenuRoot__Group__3651 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuRoot__RootItemAssignment_3_in_rule__MenuRoot__Group__3__Impl678 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group__0__Impl_in_rule__MenuItem__Group__0716 = new BitSet(new long[]{0x0000000007F7C000L});
    public static final BitSet FOLLOW_rule__MenuItem__Group__1_in_rule__MenuItem__Group__0719 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__NameAssignment_0_in_rule__MenuItem__Group__0__Impl746 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group__1__Impl_in_rule__MenuItem__Group__1776 = new BitSet(new long[]{0x0000000007F7C000L});
    public static final BitSet FOLLOW_rule__MenuItem__Group__2_in_rule__MenuItem__Group__1779 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_1__0_in_rule__MenuItem__Group__1__Impl806 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group__2__Impl_in_rule__MenuItem__Group__2837 = new BitSet(new long[]{0x0000000007F7C000L});
    public static final BitSet FOLLOW_rule__MenuItem__Group__3_in_rule__MenuItem__Group__2840 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_2__0_in_rule__MenuItem__Group__2__Impl867 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group__3__Impl_in_rule__MenuItem__Group__3898 = new BitSet(new long[]{0x0000000007F7C000L});
    public static final BitSet FOLLOW_rule__MenuItem__Group__4_in_rule__MenuItem__Group__3901 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_3__0_in_rule__MenuItem__Group__3__Impl928 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group__4__Impl_in_rule__MenuItem__Group__4959 = new BitSet(new long[]{0x0000000007F7C000L});
    public static final BitSet FOLLOW_rule__MenuItem__Group__5_in_rule__MenuItem__Group__4962 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_4__0_in_rule__MenuItem__Group__4__Impl989 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group__5__Impl_in_rule__MenuItem__Group__51020 = new BitSet(new long[]{0x0000000007F7C000L});
    public static final BitSet FOLLOW_rule__MenuItem__Group__6_in_rule__MenuItem__Group__51023 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_5__0_in_rule__MenuItem__Group__5__Impl1050 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group__6__Impl_in_rule__MenuItem__Group__61081 = new BitSet(new long[]{0x0000000007F7C000L});
    public static final BitSet FOLLOW_rule__MenuItem__Group__7_in_rule__MenuItem__Group__61084 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Alternatives_6_in_rule__MenuItem__Group__6__Impl1111 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group__7__Impl_in_rule__MenuItem__Group__71142 = new BitSet(new long[]{0x0000000007F7C000L});
    public static final BitSet FOLLOW_rule__MenuItem__Group__8_in_rule__MenuItem__Group__71145 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_7__0_in_rule__MenuItem__Group__7__Impl1172 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group__8__Impl_in_rule__MenuItem__Group__81203 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_8__0_in_rule__MenuItem__Group__8__Impl1230 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_1__0__Impl_in_rule__MenuItem__Group_1__01279 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_1__1_in_rule__MenuItem__Group_1__01282 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_14_in_rule__MenuItem__Group_1__0__Impl1310 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_1__1__Impl_in_rule__MenuItem__Group_1__11341 = new BitSet(new long[]{0x0000000000000E00L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_1__2_in_rule__MenuItem__Group_1__11344 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_13_in_rule__MenuItem__Group_1__1__Impl1372 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_1__2__Impl_in_rule__MenuItem__Group_1__21403 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__EnabledAssignment_1_2_in_rule__MenuItem__Group_1__2__Impl1430 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_2__0__Impl_in_rule__MenuItem__Group_2__01466 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_2__1_in_rule__MenuItem__Group_2__01469 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_15_in_rule__MenuItem__Group_2__0__Impl1497 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_2__1__Impl_in_rule__MenuItem__Group_2__11528 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_2__2_in_rule__MenuItem__Group_2__11531 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_13_in_rule__MenuItem__Group_2__1__Impl1559 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_2__2__Impl_in_rule__MenuItem__Group_2__21590 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__DisplayTabsAssignment_2_2_in_rule__MenuItem__Group_2__2__Impl1617 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_3__0__Impl_in_rule__MenuItem__Group_3__01653 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_3__1_in_rule__MenuItem__Group_3__01656 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_16_in_rule__MenuItem__Group_3__0__Impl1684 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_3__1__Impl_in_rule__MenuItem__Group_3__11715 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_3__2_in_rule__MenuItem__Group_3__11718 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_13_in_rule__MenuItem__Group_3__1__Impl1746 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_3__2__Impl_in_rule__MenuItem__Group_3__21777 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__SecurityRoleAssignment_3_2_in_rule__MenuItem__Group_3__2__Impl1804 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_4__0__Impl_in_rule__MenuItem__Group_4__01840 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_4__1_in_rule__MenuItem__Group_4__01843 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_17_in_rule__MenuItem__Group_4__0__Impl1871 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_4__1__Impl_in_rule__MenuItem__Group_4__11902 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_4__2_in_rule__MenuItem__Group_4__11905 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_13_in_rule__MenuItem__Group_4__1__Impl1933 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_4__2__Impl_in_rule__MenuItem__Group_4__21964 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__ShortcutAssignment_4_2_in_rule__MenuItem__Group_4__2__Impl1991 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_5__0__Impl_in_rule__MenuItem__Group_5__02027 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_5__1_in_rule__MenuItem__Group_5__02030 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_18_in_rule__MenuItem__Group_5__0__Impl2058 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_5__1__Impl_in_rule__MenuItem__Group_5__12089 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_5__2_in_rule__MenuItem__Group_5__12092 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__LabelsAssignment_5_1_in_rule__MenuItem__Group_5__1__Impl2119 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_5__2__Impl_in_rule__MenuItem__Group_5__22149 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_5_2__0_in_rule__MenuItem__Group_5__2__Impl2176 = new BitSet(new long[]{0x0000000000080002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_5_2__0__Impl_in_rule__MenuItem__Group_5_2__02213 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_5_2__1_in_rule__MenuItem__Group_5_2__02216 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_19_in_rule__MenuItem__Group_5_2__0__Impl2244 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_5_2__1__Impl_in_rule__MenuItem__Group_5_2__12275 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__LabelsAssignment_5_2_1_in_rule__MenuItem__Group_5_2__1__Impl2302 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_6_0__0__Impl_in_rule__MenuItem__Group_6_0__02336 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_6_0__1_in_rule__MenuItem__Group_6_0__02339 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_20_in_rule__MenuItem__Group_6_0__0__Impl2367 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_6_0__1__Impl_in_rule__MenuItem__Group_6_0__12398 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_6_0__2_in_rule__MenuItem__Group_6_0__12401 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_13_in_rule__MenuItem__Group_6_0__1__Impl2429 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_6_0__2__Impl_in_rule__MenuItem__Group_6_0__22460 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__VersionAssignment_6_0_2_in_rule__MenuItem__Group_6_0__2__Impl2487 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_6_1__0__Impl_in_rule__MenuItem__Group_6_1__02523 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_6_1__1_in_rule__MenuItem__Group_6_1__02526 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_21_in_rule__MenuItem__Group_6_1__0__Impl2554 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_6_1__1__Impl_in_rule__MenuItem__Group_6_1__12585 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_6_1__2_in_rule__MenuItem__Group_6_1__12588 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_13_in_rule__MenuItem__Group_6_1__1__Impl2616 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_6_1__2__Impl_in_rule__MenuItem__Group_6_1__22647 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__EnquiryAssignment_6_1_2_in_rule__MenuItem__Group_6_1__2__Impl2674 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_6_2__0__Impl_in_rule__MenuItem__Group_6_2__02710 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_6_2__1_in_rule__MenuItem__Group_6_2__02713 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_22_in_rule__MenuItem__Group_6_2__0__Impl2741 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_6_2__1__Impl_in_rule__MenuItem__Group_6_2__12772 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_6_2__2_in_rule__MenuItem__Group_6_2__12775 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_13_in_rule__MenuItem__Group_6_2__1__Impl2803 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_6_2__2__Impl_in_rule__MenuItem__Group_6_2__22834 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__CompositeScreenAssignment_6_2_2_in_rule__MenuItem__Group_6_2__2__Impl2861 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_6_3__0__Impl_in_rule__MenuItem__Group_6_3__02897 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_6_3__1_in_rule__MenuItem__Group_6_3__02900 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_23_in_rule__MenuItem__Group_6_3__0__Impl2928 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_6_3__1__Impl_in_rule__MenuItem__Group_6_3__12959 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_6_3__2_in_rule__MenuItem__Group_6_3__12962 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_13_in_rule__MenuItem__Group_6_3__1__Impl2990 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_6_3__2__Impl_in_rule__MenuItem__Group_6_3__23021 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__IncludedMenuAssignment_6_3_2_in_rule__MenuItem__Group_6_3__2__Impl3048 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_6_4__0__Impl_in_rule__MenuItem__Group_6_4__03084 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_6_4__1_in_rule__MenuItem__Group_6_4__03087 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_24_in_rule__MenuItem__Group_6_4__0__Impl3115 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_6_4__1__Impl_in_rule__MenuItem__Group_6_4__13146 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_6_4__2_in_rule__MenuItem__Group_6_4__13149 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_13_in_rule__MenuItem__Group_6_4__1__Impl3177 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_6_4__2__Impl_in_rule__MenuItem__Group_6_4__23208 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__ApplicationAssignment_6_4_2_in_rule__MenuItem__Group_6_4__2__Impl3235 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_7__0__Impl_in_rule__MenuItem__Group_7__03271 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_7__1_in_rule__MenuItem__Group_7__03274 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_25_in_rule__MenuItem__Group_7__0__Impl3302 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_7__1__Impl_in_rule__MenuItem__Group_7__13333 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_7__2_in_rule__MenuItem__Group_7__13336 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_13_in_rule__MenuItem__Group_7__1__Impl3364 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_7__2__Impl_in_rule__MenuItem__Group_7__23395 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__ParametersAssignment_7_2_in_rule__MenuItem__Group_7__2__Impl3422 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_8__0__Impl_in_rule__MenuItem__Group_8__03458 = new BitSet(new long[]{0x0000000008000020L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_8__1_in_rule__MenuItem__Group_8__03461 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_26_in_rule__MenuItem__Group_8__0__Impl3489 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_8__1__Impl_in_rule__MenuItem__Group_8__13520 = new BitSet(new long[]{0x0000000008000020L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_8__2_in_rule__MenuItem__Group_8__13523 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_8_1__0_in_rule__MenuItem__Group_8__1__Impl3550 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_8__2__Impl_in_rule__MenuItem__Group_8__23581 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_27_in_rule__MenuItem__Group_8__2__Impl3609 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_8_1__0__Impl_in_rule__MenuItem__Group_8_1__03646 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_8_1__1_in_rule__MenuItem__Group_8_1__03649 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__SubmenusAssignment_8_1_0_in_rule__MenuItem__Group_8_1__0__Impl3676 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__Group_8_1__1__Impl_in_rule__MenuItem__Group_8_1__13706 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__MenuItem__SubmenusAssignment_8_1_1_in_rule__MenuItem__Group_8_1__1__Impl3733 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_rule__Translation__Group__0__Impl_in_rule__Translation__Group__03768 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_rule__Translation__Group__1_in_rule__Translation__Group__03771 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Translation__LanguageAssignment_0_in_rule__Translation__Group__0__Impl3798 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Translation__Group__1__Impl_in_rule__Translation__Group__13828 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_rule__Translation__Group__2_in_rule__Translation__Group__13831 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_13_in_rule__Translation__Group__1__Impl3859 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Translation__Group__2__Impl_in_rule__Translation__Group__23890 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule__Translation__MessageAssignment_2_in_rule__Translation__Group__2__Impl3917 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_STRING_in_rule__MenuRoot__MetamodelVersionAssignment_23958 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMenuItem_in_rule__MenuRoot__RootItemAssignment_33989 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_VALUE_in_rule__MenuItem__NameAssignment_04020 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleEnabled_in_rule__MenuItem__EnabledAssignment_1_24051 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_9_in_rule__MenuItem__DisplayTabsAssignment_2_24087 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_STRING_in_rule__MenuItem__SecurityRoleAssignment_3_24126 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_STRING_in_rule__MenuItem__ShortcutAssignment_4_24157 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTranslation_in_rule__MenuItem__LabelsAssignment_5_14188 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTranslation_in_rule__MenuItem__LabelsAssignment_5_2_14219 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_VALUE_in_rule__MenuItem__VersionAssignment_6_0_24254 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_VALUE_in_rule__MenuItem__EnquiryAssignment_6_1_24293 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_VALUE_in_rule__MenuItem__CompositeScreenAssignment_6_2_24332 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_VALUE_in_rule__MenuItem__IncludedMenuAssignment_6_3_24371 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_VALUE_in_rule__MenuItem__ApplicationAssignment_6_4_24410 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_STRING_in_rule__MenuItem__ParametersAssignment_7_24445 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMenuItem_in_rule__MenuItem__SubmenusAssignment_8_1_04476 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMenuItem_in_rule__MenuItem__SubmenusAssignment_8_1_14507 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_VALUE_in_rule__Translation__LanguageAssignment_04538 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_STRING_in_rule__Translation__MessageAssignment_24569 = new BitSet(new long[]{0x0000000000000002L});

}
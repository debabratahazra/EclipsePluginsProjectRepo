package com.odcgroup.t24.menu.parser.antlr.internal; 

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import com.odcgroup.t24.menu.services.MenuGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalMenuParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_STRING", "RULE_VALUE", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "'metamodelVersion'", "'='", "'enabled'", "'displayTabs'", "'true'", "'securityRole'", "'shortcut'", "'labels'", "','", "'version'", "'enquiry'", "'composite-screen'", "'include-menu'", "'application'", "'parameters'", "'{'", "'}'", "'false'", "'conditional'"
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
    public String getGrammarFileName() { return "../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g"; }



     	private MenuGrammarAccess grammarAccess;
     	
        public InternalMenuParser(TokenStream input, MenuGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }
        
        @Override
        protected String getFirstRuleName() {
        	return "MenuRoot";	
       	}
       	
       	@Override
       	protected MenuGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}



    // $ANTLR start "entryRuleMenuRoot"
    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:68:1: entryRuleMenuRoot returns [EObject current=null] : iv_ruleMenuRoot= ruleMenuRoot EOF ;
    public final EObject entryRuleMenuRoot() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMenuRoot = null;


        try {
            // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:69:2: (iv_ruleMenuRoot= ruleMenuRoot EOF )
            // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:70:2: iv_ruleMenuRoot= ruleMenuRoot EOF
            {
             newCompositeNode(grammarAccess.getMenuRootRule()); 
            pushFollow(FOLLOW_ruleMenuRoot_in_entryRuleMenuRoot75);
            iv_ruleMenuRoot=ruleMenuRoot();

            state._fsp--;

             current =iv_ruleMenuRoot; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMenuRoot85); 

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
    // $ANTLR end "entryRuleMenuRoot"


    // $ANTLR start "ruleMenuRoot"
    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:77:1: ruleMenuRoot returns [EObject current=null] : (otherlv_0= 'metamodelVersion' otherlv_1= '=' ( (lv_metamodelVersion_2_0= RULE_STRING ) ) ( (lv_rootItem_3_0= ruleMenuItem ) ) ) ;
    public final EObject ruleMenuRoot() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token lv_metamodelVersion_2_0=null;
        EObject lv_rootItem_3_0 = null;


         enterRule(); 
            
        try {
            // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:80:28: ( (otherlv_0= 'metamodelVersion' otherlv_1= '=' ( (lv_metamodelVersion_2_0= RULE_STRING ) ) ( (lv_rootItem_3_0= ruleMenuItem ) ) ) )
            // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:81:1: (otherlv_0= 'metamodelVersion' otherlv_1= '=' ( (lv_metamodelVersion_2_0= RULE_STRING ) ) ( (lv_rootItem_3_0= ruleMenuItem ) ) )
            {
            // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:81:1: (otherlv_0= 'metamodelVersion' otherlv_1= '=' ( (lv_metamodelVersion_2_0= RULE_STRING ) ) ( (lv_rootItem_3_0= ruleMenuItem ) ) )
            // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:81:3: otherlv_0= 'metamodelVersion' otherlv_1= '=' ( (lv_metamodelVersion_2_0= RULE_STRING ) ) ( (lv_rootItem_3_0= ruleMenuItem ) )
            {
            otherlv_0=(Token)match(input,9,FOLLOW_9_in_ruleMenuRoot122); 

                	newLeafNode(otherlv_0, grammarAccess.getMenuRootAccess().getMetamodelVersionKeyword_0());
                
            otherlv_1=(Token)match(input,10,FOLLOW_10_in_ruleMenuRoot134); 

                	newLeafNode(otherlv_1, grammarAccess.getMenuRootAccess().getEqualsSignKeyword_1());
                
            // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:89:1: ( (lv_metamodelVersion_2_0= RULE_STRING ) )
            // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:90:1: (lv_metamodelVersion_2_0= RULE_STRING )
            {
            // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:90:1: (lv_metamodelVersion_2_0= RULE_STRING )
            // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:91:3: lv_metamodelVersion_2_0= RULE_STRING
            {
            lv_metamodelVersion_2_0=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleMenuRoot151); 

            			newLeafNode(lv_metamodelVersion_2_0, grammarAccess.getMenuRootAccess().getMetamodelVersionSTRINGTerminalRuleCall_2_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getMenuRootRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"metamodelVersion",
                    		lv_metamodelVersion_2_0, 
                    		"STRING");
            	    

            }


            }

            // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:107:2: ( (lv_rootItem_3_0= ruleMenuItem ) )
            // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:108:1: (lv_rootItem_3_0= ruleMenuItem )
            {
            // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:108:1: (lv_rootItem_3_0= ruleMenuItem )
            // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:109:3: lv_rootItem_3_0= ruleMenuItem
            {
             
            	        newCompositeNode(grammarAccess.getMenuRootAccess().getRootItemMenuItemParserRuleCall_3_0()); 
            	    
            pushFollow(FOLLOW_ruleMenuItem_in_ruleMenuRoot177);
            lv_rootItem_3_0=ruleMenuItem();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getMenuRootRule());
            	        }
                   		set(
                   			current, 
                   			"rootItem",
                    		lv_rootItem_3_0, 
                    		"MenuItem");
            	        afterParserOrEnumRuleCall();
            	    

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
    // $ANTLR end "ruleMenuRoot"


    // $ANTLR start "entryRuleMenuItem"
    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:133:1: entryRuleMenuItem returns [EObject current=null] : iv_ruleMenuItem= ruleMenuItem EOF ;
    public final EObject entryRuleMenuItem() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMenuItem = null;


        try {
            // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:134:2: (iv_ruleMenuItem= ruleMenuItem EOF )
            // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:135:2: iv_ruleMenuItem= ruleMenuItem EOF
            {
             newCompositeNode(grammarAccess.getMenuItemRule()); 
            pushFollow(FOLLOW_ruleMenuItem_in_entryRuleMenuItem213);
            iv_ruleMenuItem=ruleMenuItem();

            state._fsp--;

             current =iv_ruleMenuItem; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMenuItem223); 

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
    // $ANTLR end "entryRuleMenuItem"


    // $ANTLR start "ruleMenuItem"
    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:142:1: ruleMenuItem returns [EObject current=null] : ( ( (lv_name_0_0= RULE_VALUE ) ) (otherlv_1= 'enabled' otherlv_2= '=' ( (lv_enabled_3_0= ruleEnabled ) ) )? (otherlv_4= 'displayTabs' otherlv_5= '=' ( (lv_displayTabs_6_0= 'true' ) ) )? (otherlv_7= 'securityRole' otherlv_8= '=' ( (lv_securityRole_9_0= RULE_STRING ) ) )? (otherlv_10= 'shortcut' otherlv_11= '=' ( (lv_shortcut_12_0= RULE_STRING ) ) )? (otherlv_13= 'labels' ( (lv_labels_14_0= ruleTranslation ) ) (otherlv_15= ',' ( (lv_labels_16_0= ruleTranslation ) ) )* )? ( (otherlv_17= 'version' otherlv_18= '=' ( (otherlv_19= RULE_VALUE ) ) ) | (otherlv_20= 'enquiry' otherlv_21= '=' ( (otherlv_22= RULE_VALUE ) ) ) | (otherlv_23= 'composite-screen' otherlv_24= '=' ( (otherlv_25= RULE_VALUE ) ) ) | (otherlv_26= 'include-menu' otherlv_27= '=' ( (otherlv_28= RULE_VALUE ) ) ) | (otherlv_29= 'application' otherlv_30= '=' ( (otherlv_31= RULE_VALUE ) ) ) )? (otherlv_32= 'parameters' otherlv_33= '=' ( (lv_parameters_34_0= RULE_STRING ) ) )? (otherlv_35= '{' ( ( (lv_submenus_36_0= ruleMenuItem ) ) ( (lv_submenus_37_0= ruleMenuItem ) )* )? otherlv_38= '}' )? ) ;
    public final EObject ruleMenuItem() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token lv_displayTabs_6_0=null;
        Token otherlv_7=null;
        Token otherlv_8=null;
        Token lv_securityRole_9_0=null;
        Token otherlv_10=null;
        Token otherlv_11=null;
        Token lv_shortcut_12_0=null;
        Token otherlv_13=null;
        Token otherlv_15=null;
        Token otherlv_17=null;
        Token otherlv_18=null;
        Token otherlv_19=null;
        Token otherlv_20=null;
        Token otherlv_21=null;
        Token otherlv_22=null;
        Token otherlv_23=null;
        Token otherlv_24=null;
        Token otherlv_25=null;
        Token otherlv_26=null;
        Token otherlv_27=null;
        Token otherlv_28=null;
        Token otherlv_29=null;
        Token otherlv_30=null;
        Token otherlv_31=null;
        Token otherlv_32=null;
        Token otherlv_33=null;
        Token lv_parameters_34_0=null;
        Token otherlv_35=null;
        Token otherlv_38=null;
        Enumerator lv_enabled_3_0 = null;

        EObject lv_labels_14_0 = null;

        EObject lv_labels_16_0 = null;

        EObject lv_submenus_36_0 = null;

        EObject lv_submenus_37_0 = null;


         enterRule(); 
            
        try {
            // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:145:28: ( ( ( (lv_name_0_0= RULE_VALUE ) ) (otherlv_1= 'enabled' otherlv_2= '=' ( (lv_enabled_3_0= ruleEnabled ) ) )? (otherlv_4= 'displayTabs' otherlv_5= '=' ( (lv_displayTabs_6_0= 'true' ) ) )? (otherlv_7= 'securityRole' otherlv_8= '=' ( (lv_securityRole_9_0= RULE_STRING ) ) )? (otherlv_10= 'shortcut' otherlv_11= '=' ( (lv_shortcut_12_0= RULE_STRING ) ) )? (otherlv_13= 'labels' ( (lv_labels_14_0= ruleTranslation ) ) (otherlv_15= ',' ( (lv_labels_16_0= ruleTranslation ) ) )* )? ( (otherlv_17= 'version' otherlv_18= '=' ( (otherlv_19= RULE_VALUE ) ) ) | (otherlv_20= 'enquiry' otherlv_21= '=' ( (otherlv_22= RULE_VALUE ) ) ) | (otherlv_23= 'composite-screen' otherlv_24= '=' ( (otherlv_25= RULE_VALUE ) ) ) | (otherlv_26= 'include-menu' otherlv_27= '=' ( (otherlv_28= RULE_VALUE ) ) ) | (otherlv_29= 'application' otherlv_30= '=' ( (otherlv_31= RULE_VALUE ) ) ) )? (otherlv_32= 'parameters' otherlv_33= '=' ( (lv_parameters_34_0= RULE_STRING ) ) )? (otherlv_35= '{' ( ( (lv_submenus_36_0= ruleMenuItem ) ) ( (lv_submenus_37_0= ruleMenuItem ) )* )? otherlv_38= '}' )? ) )
            // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:146:1: ( ( (lv_name_0_0= RULE_VALUE ) ) (otherlv_1= 'enabled' otherlv_2= '=' ( (lv_enabled_3_0= ruleEnabled ) ) )? (otherlv_4= 'displayTabs' otherlv_5= '=' ( (lv_displayTabs_6_0= 'true' ) ) )? (otherlv_7= 'securityRole' otherlv_8= '=' ( (lv_securityRole_9_0= RULE_STRING ) ) )? (otherlv_10= 'shortcut' otherlv_11= '=' ( (lv_shortcut_12_0= RULE_STRING ) ) )? (otherlv_13= 'labels' ( (lv_labels_14_0= ruleTranslation ) ) (otherlv_15= ',' ( (lv_labels_16_0= ruleTranslation ) ) )* )? ( (otherlv_17= 'version' otherlv_18= '=' ( (otherlv_19= RULE_VALUE ) ) ) | (otherlv_20= 'enquiry' otherlv_21= '=' ( (otherlv_22= RULE_VALUE ) ) ) | (otherlv_23= 'composite-screen' otherlv_24= '=' ( (otherlv_25= RULE_VALUE ) ) ) | (otherlv_26= 'include-menu' otherlv_27= '=' ( (otherlv_28= RULE_VALUE ) ) ) | (otherlv_29= 'application' otherlv_30= '=' ( (otherlv_31= RULE_VALUE ) ) ) )? (otherlv_32= 'parameters' otherlv_33= '=' ( (lv_parameters_34_0= RULE_STRING ) ) )? (otherlv_35= '{' ( ( (lv_submenus_36_0= ruleMenuItem ) ) ( (lv_submenus_37_0= ruleMenuItem ) )* )? otherlv_38= '}' )? )
            {
            // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:146:1: ( ( (lv_name_0_0= RULE_VALUE ) ) (otherlv_1= 'enabled' otherlv_2= '=' ( (lv_enabled_3_0= ruleEnabled ) ) )? (otherlv_4= 'displayTabs' otherlv_5= '=' ( (lv_displayTabs_6_0= 'true' ) ) )? (otherlv_7= 'securityRole' otherlv_8= '=' ( (lv_securityRole_9_0= RULE_STRING ) ) )? (otherlv_10= 'shortcut' otherlv_11= '=' ( (lv_shortcut_12_0= RULE_STRING ) ) )? (otherlv_13= 'labels' ( (lv_labels_14_0= ruleTranslation ) ) (otherlv_15= ',' ( (lv_labels_16_0= ruleTranslation ) ) )* )? ( (otherlv_17= 'version' otherlv_18= '=' ( (otherlv_19= RULE_VALUE ) ) ) | (otherlv_20= 'enquiry' otherlv_21= '=' ( (otherlv_22= RULE_VALUE ) ) ) | (otherlv_23= 'composite-screen' otherlv_24= '=' ( (otherlv_25= RULE_VALUE ) ) ) | (otherlv_26= 'include-menu' otherlv_27= '=' ( (otherlv_28= RULE_VALUE ) ) ) | (otherlv_29= 'application' otherlv_30= '=' ( (otherlv_31= RULE_VALUE ) ) ) )? (otherlv_32= 'parameters' otherlv_33= '=' ( (lv_parameters_34_0= RULE_STRING ) ) )? (otherlv_35= '{' ( ( (lv_submenus_36_0= ruleMenuItem ) ) ( (lv_submenus_37_0= ruleMenuItem ) )* )? otherlv_38= '}' )? )
            // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:146:2: ( (lv_name_0_0= RULE_VALUE ) ) (otherlv_1= 'enabled' otherlv_2= '=' ( (lv_enabled_3_0= ruleEnabled ) ) )? (otherlv_4= 'displayTabs' otherlv_5= '=' ( (lv_displayTabs_6_0= 'true' ) ) )? (otherlv_7= 'securityRole' otherlv_8= '=' ( (lv_securityRole_9_0= RULE_STRING ) ) )? (otherlv_10= 'shortcut' otherlv_11= '=' ( (lv_shortcut_12_0= RULE_STRING ) ) )? (otherlv_13= 'labels' ( (lv_labels_14_0= ruleTranslation ) ) (otherlv_15= ',' ( (lv_labels_16_0= ruleTranslation ) ) )* )? ( (otherlv_17= 'version' otherlv_18= '=' ( (otherlv_19= RULE_VALUE ) ) ) | (otherlv_20= 'enquiry' otherlv_21= '=' ( (otherlv_22= RULE_VALUE ) ) ) | (otherlv_23= 'composite-screen' otherlv_24= '=' ( (otherlv_25= RULE_VALUE ) ) ) | (otherlv_26= 'include-menu' otherlv_27= '=' ( (otherlv_28= RULE_VALUE ) ) ) | (otherlv_29= 'application' otherlv_30= '=' ( (otherlv_31= RULE_VALUE ) ) ) )? (otherlv_32= 'parameters' otherlv_33= '=' ( (lv_parameters_34_0= RULE_STRING ) ) )? (otherlv_35= '{' ( ( (lv_submenus_36_0= ruleMenuItem ) ) ( (lv_submenus_37_0= ruleMenuItem ) )* )? otherlv_38= '}' )?
            {
            // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:146:2: ( (lv_name_0_0= RULE_VALUE ) )
            // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:147:1: (lv_name_0_0= RULE_VALUE )
            {
            // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:147:1: (lv_name_0_0= RULE_VALUE )
            // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:148:3: lv_name_0_0= RULE_VALUE
            {
            lv_name_0_0=(Token)match(input,RULE_VALUE,FOLLOW_RULE_VALUE_in_ruleMenuItem265); 

            			newLeafNode(lv_name_0_0, grammarAccess.getMenuItemAccess().getNameVALUETerminalRuleCall_0_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getMenuItemRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"name",
                    		lv_name_0_0, 
                    		"VALUE");
            	    

            }


            }

            // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:164:2: (otherlv_1= 'enabled' otherlv_2= '=' ( (lv_enabled_3_0= ruleEnabled ) ) )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==11) ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:164:4: otherlv_1= 'enabled' otherlv_2= '=' ( (lv_enabled_3_0= ruleEnabled ) )
                    {
                    otherlv_1=(Token)match(input,11,FOLLOW_11_in_ruleMenuItem283); 

                        	newLeafNode(otherlv_1, grammarAccess.getMenuItemAccess().getEnabledKeyword_1_0());
                        
                    otherlv_2=(Token)match(input,10,FOLLOW_10_in_ruleMenuItem295); 

                        	newLeafNode(otherlv_2, grammarAccess.getMenuItemAccess().getEqualsSignKeyword_1_1());
                        
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:172:1: ( (lv_enabled_3_0= ruleEnabled ) )
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:173:1: (lv_enabled_3_0= ruleEnabled )
                    {
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:173:1: (lv_enabled_3_0= ruleEnabled )
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:174:3: lv_enabled_3_0= ruleEnabled
                    {
                     
                    	        newCompositeNode(grammarAccess.getMenuItemAccess().getEnabledEnabledEnumRuleCall_1_2_0()); 
                    	    
                    pushFollow(FOLLOW_ruleEnabled_in_ruleMenuItem316);
                    lv_enabled_3_0=ruleEnabled();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getMenuItemRule());
                    	        }
                           		set(
                           			current, 
                           			"enabled",
                            		lv_enabled_3_0, 
                            		"Enabled");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }


                    }
                    break;

            }

            // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:190:4: (otherlv_4= 'displayTabs' otherlv_5= '=' ( (lv_displayTabs_6_0= 'true' ) ) )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==12) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:190:6: otherlv_4= 'displayTabs' otherlv_5= '=' ( (lv_displayTabs_6_0= 'true' ) )
                    {
                    otherlv_4=(Token)match(input,12,FOLLOW_12_in_ruleMenuItem331); 

                        	newLeafNode(otherlv_4, grammarAccess.getMenuItemAccess().getDisplayTabsKeyword_2_0());
                        
                    otherlv_5=(Token)match(input,10,FOLLOW_10_in_ruleMenuItem343); 

                        	newLeafNode(otherlv_5, grammarAccess.getMenuItemAccess().getEqualsSignKeyword_2_1());
                        
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:198:1: ( (lv_displayTabs_6_0= 'true' ) )
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:199:1: (lv_displayTabs_6_0= 'true' )
                    {
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:199:1: (lv_displayTabs_6_0= 'true' )
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:200:3: lv_displayTabs_6_0= 'true'
                    {
                    lv_displayTabs_6_0=(Token)match(input,13,FOLLOW_13_in_ruleMenuItem361); 

                            newLeafNode(lv_displayTabs_6_0, grammarAccess.getMenuItemAccess().getDisplayTabsTrueKeyword_2_2_0());
                        

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getMenuItemRule());
                    	        }
                           		setWithLastConsumed(current, "displayTabs", true, "true");
                    	    

                    }


                    }


                    }
                    break;

            }

            // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:213:4: (otherlv_7= 'securityRole' otherlv_8= '=' ( (lv_securityRole_9_0= RULE_STRING ) ) )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==14) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:213:6: otherlv_7= 'securityRole' otherlv_8= '=' ( (lv_securityRole_9_0= RULE_STRING ) )
                    {
                    otherlv_7=(Token)match(input,14,FOLLOW_14_in_ruleMenuItem389); 

                        	newLeafNode(otherlv_7, grammarAccess.getMenuItemAccess().getSecurityRoleKeyword_3_0());
                        
                    otherlv_8=(Token)match(input,10,FOLLOW_10_in_ruleMenuItem401); 

                        	newLeafNode(otherlv_8, grammarAccess.getMenuItemAccess().getEqualsSignKeyword_3_1());
                        
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:221:1: ( (lv_securityRole_9_0= RULE_STRING ) )
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:222:1: (lv_securityRole_9_0= RULE_STRING )
                    {
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:222:1: (lv_securityRole_9_0= RULE_STRING )
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:223:3: lv_securityRole_9_0= RULE_STRING
                    {
                    lv_securityRole_9_0=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleMenuItem418); 

                    			newLeafNode(lv_securityRole_9_0, grammarAccess.getMenuItemAccess().getSecurityRoleSTRINGTerminalRuleCall_3_2_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getMenuItemRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"securityRole",
                            		lv_securityRole_9_0, 
                            		"STRING");
                    	    

                    }


                    }


                    }
                    break;

            }

            // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:239:4: (otherlv_10= 'shortcut' otherlv_11= '=' ( (lv_shortcut_12_0= RULE_STRING ) ) )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==15) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:239:6: otherlv_10= 'shortcut' otherlv_11= '=' ( (lv_shortcut_12_0= RULE_STRING ) )
                    {
                    otherlv_10=(Token)match(input,15,FOLLOW_15_in_ruleMenuItem438); 

                        	newLeafNode(otherlv_10, grammarAccess.getMenuItemAccess().getShortcutKeyword_4_0());
                        
                    otherlv_11=(Token)match(input,10,FOLLOW_10_in_ruleMenuItem450); 

                        	newLeafNode(otherlv_11, grammarAccess.getMenuItemAccess().getEqualsSignKeyword_4_1());
                        
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:247:1: ( (lv_shortcut_12_0= RULE_STRING ) )
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:248:1: (lv_shortcut_12_0= RULE_STRING )
                    {
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:248:1: (lv_shortcut_12_0= RULE_STRING )
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:249:3: lv_shortcut_12_0= RULE_STRING
                    {
                    lv_shortcut_12_0=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleMenuItem467); 

                    			newLeafNode(lv_shortcut_12_0, grammarAccess.getMenuItemAccess().getShortcutSTRINGTerminalRuleCall_4_2_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getMenuItemRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"shortcut",
                            		lv_shortcut_12_0, 
                            		"STRING");
                    	    

                    }


                    }


                    }
                    break;

            }

            // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:265:4: (otherlv_13= 'labels' ( (lv_labels_14_0= ruleTranslation ) ) (otherlv_15= ',' ( (lv_labels_16_0= ruleTranslation ) ) )* )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==16) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:265:6: otherlv_13= 'labels' ( (lv_labels_14_0= ruleTranslation ) ) (otherlv_15= ',' ( (lv_labels_16_0= ruleTranslation ) ) )*
                    {
                    otherlv_13=(Token)match(input,16,FOLLOW_16_in_ruleMenuItem487); 

                        	newLeafNode(otherlv_13, grammarAccess.getMenuItemAccess().getLabelsKeyword_5_0());
                        
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:269:1: ( (lv_labels_14_0= ruleTranslation ) )
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:270:1: (lv_labels_14_0= ruleTranslation )
                    {
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:270:1: (lv_labels_14_0= ruleTranslation )
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:271:3: lv_labels_14_0= ruleTranslation
                    {
                     
                    	        newCompositeNode(grammarAccess.getMenuItemAccess().getLabelsTranslationParserRuleCall_5_1_0()); 
                    	    
                    pushFollow(FOLLOW_ruleTranslation_in_ruleMenuItem508);
                    lv_labels_14_0=ruleTranslation();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getMenuItemRule());
                    	        }
                           		add(
                           			current, 
                           			"labels",
                            		lv_labels_14_0, 
                            		"Translation");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:287:2: (otherlv_15= ',' ( (lv_labels_16_0= ruleTranslation ) ) )*
                    loop5:
                    do {
                        int alt5=2;
                        int LA5_0 = input.LA(1);

                        if ( (LA5_0==17) ) {
                            alt5=1;
                        }


                        switch (alt5) {
                    	case 1 :
                    	    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:287:4: otherlv_15= ',' ( (lv_labels_16_0= ruleTranslation ) )
                    	    {
                    	    otherlv_15=(Token)match(input,17,FOLLOW_17_in_ruleMenuItem521); 

                    	        	newLeafNode(otherlv_15, grammarAccess.getMenuItemAccess().getCommaKeyword_5_2_0());
                    	        
                    	    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:291:1: ( (lv_labels_16_0= ruleTranslation ) )
                    	    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:292:1: (lv_labels_16_0= ruleTranslation )
                    	    {
                    	    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:292:1: (lv_labels_16_0= ruleTranslation )
                    	    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:293:3: lv_labels_16_0= ruleTranslation
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getMenuItemAccess().getLabelsTranslationParserRuleCall_5_2_1_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleTranslation_in_ruleMenuItem542);
                    	    lv_labels_16_0=ruleTranslation();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getMenuItemRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"labels",
                    	            		lv_labels_16_0, 
                    	            		"Translation");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop5;
                        }
                    } while (true);


                    }
                    break;

            }

            // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:309:6: ( (otherlv_17= 'version' otherlv_18= '=' ( (otherlv_19= RULE_VALUE ) ) ) | (otherlv_20= 'enquiry' otherlv_21= '=' ( (otherlv_22= RULE_VALUE ) ) ) | (otherlv_23= 'composite-screen' otherlv_24= '=' ( (otherlv_25= RULE_VALUE ) ) ) | (otherlv_26= 'include-menu' otherlv_27= '=' ( (otherlv_28= RULE_VALUE ) ) ) | (otherlv_29= 'application' otherlv_30= '=' ( (otherlv_31= RULE_VALUE ) ) ) )?
            int alt7=6;
            switch ( input.LA(1) ) {
                case 18:
                    {
                    alt7=1;
                    }
                    break;
                case 19:
                    {
                    alt7=2;
                    }
                    break;
                case 20:
                    {
                    alt7=3;
                    }
                    break;
                case 21:
                    {
                    alt7=4;
                    }
                    break;
                case 22:
                    {
                    alt7=5;
                    }
                    break;
            }

            switch (alt7) {
                case 1 :
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:309:7: (otherlv_17= 'version' otherlv_18= '=' ( (otherlv_19= RULE_VALUE ) ) )
                    {
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:309:7: (otherlv_17= 'version' otherlv_18= '=' ( (otherlv_19= RULE_VALUE ) ) )
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:309:9: otherlv_17= 'version' otherlv_18= '=' ( (otherlv_19= RULE_VALUE ) )
                    {
                    otherlv_17=(Token)match(input,18,FOLLOW_18_in_ruleMenuItem560); 

                        	newLeafNode(otherlv_17, grammarAccess.getMenuItemAccess().getVersionKeyword_6_0_0());
                        
                    otherlv_18=(Token)match(input,10,FOLLOW_10_in_ruleMenuItem572); 

                        	newLeafNode(otherlv_18, grammarAccess.getMenuItemAccess().getEqualsSignKeyword_6_0_1());
                        
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:317:1: ( (otherlv_19= RULE_VALUE ) )
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:318:1: (otherlv_19= RULE_VALUE )
                    {
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:318:1: (otherlv_19= RULE_VALUE )
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:319:3: otherlv_19= RULE_VALUE
                    {

                    			if (current==null) {
                    	            current = createModelElement(grammarAccess.getMenuItemRule());
                    	        }
                            
                    otherlv_19=(Token)match(input,RULE_VALUE,FOLLOW_RULE_VALUE_in_ruleMenuItem592); 

                    		newLeafNode(otherlv_19, grammarAccess.getMenuItemAccess().getVersionVersionCrossReference_6_0_2_0()); 
                    	

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:331:6: (otherlv_20= 'enquiry' otherlv_21= '=' ( (otherlv_22= RULE_VALUE ) ) )
                    {
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:331:6: (otherlv_20= 'enquiry' otherlv_21= '=' ( (otherlv_22= RULE_VALUE ) ) )
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:331:8: otherlv_20= 'enquiry' otherlv_21= '=' ( (otherlv_22= RULE_VALUE ) )
                    {
                    otherlv_20=(Token)match(input,19,FOLLOW_19_in_ruleMenuItem612); 

                        	newLeafNode(otherlv_20, grammarAccess.getMenuItemAccess().getEnquiryKeyword_6_1_0());
                        
                    otherlv_21=(Token)match(input,10,FOLLOW_10_in_ruleMenuItem624); 

                        	newLeafNode(otherlv_21, grammarAccess.getMenuItemAccess().getEqualsSignKeyword_6_1_1());
                        
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:339:1: ( (otherlv_22= RULE_VALUE ) )
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:340:1: (otherlv_22= RULE_VALUE )
                    {
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:340:1: (otherlv_22= RULE_VALUE )
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:341:3: otherlv_22= RULE_VALUE
                    {

                    			if (current==null) {
                    	            current = createModelElement(grammarAccess.getMenuItemRule());
                    	        }
                            
                    otherlv_22=(Token)match(input,RULE_VALUE,FOLLOW_RULE_VALUE_in_ruleMenuItem644); 

                    		newLeafNode(otherlv_22, grammarAccess.getMenuItemAccess().getEnquiryEnquiryCrossReference_6_1_2_0()); 
                    	

                    }


                    }


                    }


                    }
                    break;
                case 3 :
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:353:6: (otherlv_23= 'composite-screen' otherlv_24= '=' ( (otherlv_25= RULE_VALUE ) ) )
                    {
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:353:6: (otherlv_23= 'composite-screen' otherlv_24= '=' ( (otherlv_25= RULE_VALUE ) ) )
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:353:8: otherlv_23= 'composite-screen' otherlv_24= '=' ( (otherlv_25= RULE_VALUE ) )
                    {
                    otherlv_23=(Token)match(input,20,FOLLOW_20_in_ruleMenuItem664); 

                        	newLeafNode(otherlv_23, grammarAccess.getMenuItemAccess().getCompositeScreenKeyword_6_2_0());
                        
                    otherlv_24=(Token)match(input,10,FOLLOW_10_in_ruleMenuItem676); 

                        	newLeafNode(otherlv_24, grammarAccess.getMenuItemAccess().getEqualsSignKeyword_6_2_1());
                        
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:361:1: ( (otherlv_25= RULE_VALUE ) )
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:362:1: (otherlv_25= RULE_VALUE )
                    {
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:362:1: (otherlv_25= RULE_VALUE )
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:363:3: otherlv_25= RULE_VALUE
                    {

                    			if (current==null) {
                    	            current = createModelElement(grammarAccess.getMenuItemRule());
                    	        }
                            
                    otherlv_25=(Token)match(input,RULE_VALUE,FOLLOW_RULE_VALUE_in_ruleMenuItem696); 

                    		newLeafNode(otherlv_25, grammarAccess.getMenuItemAccess().getCompositeScreenCompositeScreenCrossReference_6_2_2_0()); 
                    	

                    }


                    }


                    }


                    }
                    break;
                case 4 :
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:375:6: (otherlv_26= 'include-menu' otherlv_27= '=' ( (otherlv_28= RULE_VALUE ) ) )
                    {
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:375:6: (otherlv_26= 'include-menu' otherlv_27= '=' ( (otherlv_28= RULE_VALUE ) ) )
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:375:8: otherlv_26= 'include-menu' otherlv_27= '=' ( (otherlv_28= RULE_VALUE ) )
                    {
                    otherlv_26=(Token)match(input,21,FOLLOW_21_in_ruleMenuItem716); 

                        	newLeafNode(otherlv_26, grammarAccess.getMenuItemAccess().getIncludeMenuKeyword_6_3_0());
                        
                    otherlv_27=(Token)match(input,10,FOLLOW_10_in_ruleMenuItem728); 

                        	newLeafNode(otherlv_27, grammarAccess.getMenuItemAccess().getEqualsSignKeyword_6_3_1());
                        
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:383:1: ( (otherlv_28= RULE_VALUE ) )
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:384:1: (otherlv_28= RULE_VALUE )
                    {
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:384:1: (otherlv_28= RULE_VALUE )
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:385:3: otherlv_28= RULE_VALUE
                    {

                    			if (current==null) {
                    	            current = createModelElement(grammarAccess.getMenuItemRule());
                    	        }
                            
                    otherlv_28=(Token)match(input,RULE_VALUE,FOLLOW_RULE_VALUE_in_ruleMenuItem748); 

                    		newLeafNode(otherlv_28, grammarAccess.getMenuItemAccess().getIncludedMenuMenuRootCrossReference_6_3_2_0()); 
                    	

                    }


                    }


                    }


                    }
                    break;
                case 5 :
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:397:6: (otherlv_29= 'application' otherlv_30= '=' ( (otherlv_31= RULE_VALUE ) ) )
                    {
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:397:6: (otherlv_29= 'application' otherlv_30= '=' ( (otherlv_31= RULE_VALUE ) ) )
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:397:8: otherlv_29= 'application' otherlv_30= '=' ( (otherlv_31= RULE_VALUE ) )
                    {
                    otherlv_29=(Token)match(input,22,FOLLOW_22_in_ruleMenuItem768); 

                        	newLeafNode(otherlv_29, grammarAccess.getMenuItemAccess().getApplicationKeyword_6_4_0());
                        
                    otherlv_30=(Token)match(input,10,FOLLOW_10_in_ruleMenuItem780); 

                        	newLeafNode(otherlv_30, grammarAccess.getMenuItemAccess().getEqualsSignKeyword_6_4_1());
                        
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:405:1: ( (otherlv_31= RULE_VALUE ) )
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:406:1: (otherlv_31= RULE_VALUE )
                    {
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:406:1: (otherlv_31= RULE_VALUE )
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:407:3: otherlv_31= RULE_VALUE
                    {

                    			if (current==null) {
                    	            current = createModelElement(grammarAccess.getMenuItemRule());
                    	        }
                            
                    otherlv_31=(Token)match(input,RULE_VALUE,FOLLOW_RULE_VALUE_in_ruleMenuItem800); 

                    		newLeafNode(otherlv_31, grammarAccess.getMenuItemAccess().getApplicationMdfClassCrossReference_6_4_2_0()); 
                    	

                    }


                    }


                    }


                    }
                    break;

            }

            // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:418:5: (otherlv_32= 'parameters' otherlv_33= '=' ( (lv_parameters_34_0= RULE_STRING ) ) )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==23) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:418:7: otherlv_32= 'parameters' otherlv_33= '=' ( (lv_parameters_34_0= RULE_STRING ) )
                    {
                    otherlv_32=(Token)match(input,23,FOLLOW_23_in_ruleMenuItem816); 

                        	newLeafNode(otherlv_32, grammarAccess.getMenuItemAccess().getParametersKeyword_7_0());
                        
                    otherlv_33=(Token)match(input,10,FOLLOW_10_in_ruleMenuItem828); 

                        	newLeafNode(otherlv_33, grammarAccess.getMenuItemAccess().getEqualsSignKeyword_7_1());
                        
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:426:1: ( (lv_parameters_34_0= RULE_STRING ) )
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:427:1: (lv_parameters_34_0= RULE_STRING )
                    {
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:427:1: (lv_parameters_34_0= RULE_STRING )
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:428:3: lv_parameters_34_0= RULE_STRING
                    {
                    lv_parameters_34_0=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleMenuItem845); 

                    			newLeafNode(lv_parameters_34_0, grammarAccess.getMenuItemAccess().getParametersSTRINGTerminalRuleCall_7_2_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getMenuItemRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"parameters",
                            		lv_parameters_34_0, 
                            		"STRING");
                    	    

                    }


                    }


                    }
                    break;

            }

            // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:444:4: (otherlv_35= '{' ( ( (lv_submenus_36_0= ruleMenuItem ) ) ( (lv_submenus_37_0= ruleMenuItem ) )* )? otherlv_38= '}' )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==24) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:444:6: otherlv_35= '{' ( ( (lv_submenus_36_0= ruleMenuItem ) ) ( (lv_submenus_37_0= ruleMenuItem ) )* )? otherlv_38= '}'
                    {
                    otherlv_35=(Token)match(input,24,FOLLOW_24_in_ruleMenuItem865); 

                        	newLeafNode(otherlv_35, grammarAccess.getMenuItemAccess().getLeftCurlyBracketKeyword_8_0());
                        
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:448:1: ( ( (lv_submenus_36_0= ruleMenuItem ) ) ( (lv_submenus_37_0= ruleMenuItem ) )* )?
                    int alt10=2;
                    int LA10_0 = input.LA(1);

                    if ( (LA10_0==RULE_VALUE) ) {
                        alt10=1;
                    }
                    switch (alt10) {
                        case 1 :
                            // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:448:2: ( (lv_submenus_36_0= ruleMenuItem ) ) ( (lv_submenus_37_0= ruleMenuItem ) )*
                            {
                            // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:448:2: ( (lv_submenus_36_0= ruleMenuItem ) )
                            // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:449:1: (lv_submenus_36_0= ruleMenuItem )
                            {
                            // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:449:1: (lv_submenus_36_0= ruleMenuItem )
                            // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:450:3: lv_submenus_36_0= ruleMenuItem
                            {
                             
                            	        newCompositeNode(grammarAccess.getMenuItemAccess().getSubmenusMenuItemParserRuleCall_8_1_0_0()); 
                            	    
                            pushFollow(FOLLOW_ruleMenuItem_in_ruleMenuItem887);
                            lv_submenus_36_0=ruleMenuItem();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getMenuItemRule());
                            	        }
                                   		add(
                                   			current, 
                                   			"submenus",
                                    		lv_submenus_36_0, 
                                    		"MenuItem");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }

                            // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:466:2: ( (lv_submenus_37_0= ruleMenuItem ) )*
                            loop9:
                            do {
                                int alt9=2;
                                int LA9_0 = input.LA(1);

                                if ( (LA9_0==RULE_VALUE) ) {
                                    alt9=1;
                                }


                                switch (alt9) {
                            	case 1 :
                            	    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:467:1: (lv_submenus_37_0= ruleMenuItem )
                            	    {
                            	    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:467:1: (lv_submenus_37_0= ruleMenuItem )
                            	    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:468:3: lv_submenus_37_0= ruleMenuItem
                            	    {
                            	     
                            	    	        newCompositeNode(grammarAccess.getMenuItemAccess().getSubmenusMenuItemParserRuleCall_8_1_1_0()); 
                            	    	    
                            	    pushFollow(FOLLOW_ruleMenuItem_in_ruleMenuItem908);
                            	    lv_submenus_37_0=ruleMenuItem();

                            	    state._fsp--;


                            	    	        if (current==null) {
                            	    	            current = createModelElementForParent(grammarAccess.getMenuItemRule());
                            	    	        }
                            	           		add(
                            	           			current, 
                            	           			"submenus",
                            	            		lv_submenus_37_0, 
                            	            		"MenuItem");
                            	    	        afterParserOrEnumRuleCall();
                            	    	    

                            	    }


                            	    }
                            	    break;

                            	default :
                            	    break loop9;
                                }
                            } while (true);


                            }
                            break;

                    }

                    otherlv_38=(Token)match(input,25,FOLLOW_25_in_ruleMenuItem923); 

                        	newLeafNode(otherlv_38, grammarAccess.getMenuItemAccess().getRightCurlyBracketKeyword_8_2());
                        

                    }
                    break;

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
    // $ANTLR end "ruleMenuItem"


    // $ANTLR start "entryRuleTranslation"
    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:496:1: entryRuleTranslation returns [EObject current=null] : iv_ruleTranslation= ruleTranslation EOF ;
    public final EObject entryRuleTranslation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTranslation = null;


        try {
            // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:497:2: (iv_ruleTranslation= ruleTranslation EOF )
            // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:498:2: iv_ruleTranslation= ruleTranslation EOF
            {
             newCompositeNode(grammarAccess.getTranslationRule()); 
            pushFollow(FOLLOW_ruleTranslation_in_entryRuleTranslation961);
            iv_ruleTranslation=ruleTranslation();

            state._fsp--;

             current =iv_ruleTranslation; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleTranslation971); 

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
    // $ANTLR end "entryRuleTranslation"


    // $ANTLR start "ruleTranslation"
    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:505:1: ruleTranslation returns [EObject current=null] : ( ( (lv_language_0_0= RULE_VALUE ) ) otherlv_1= '=' ( (lv_message_2_0= RULE_STRING ) ) ) ;
    public final EObject ruleTranslation() throws RecognitionException {
        EObject current = null;

        Token lv_language_0_0=null;
        Token otherlv_1=null;
        Token lv_message_2_0=null;

         enterRule(); 
            
        try {
            // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:508:28: ( ( ( (lv_language_0_0= RULE_VALUE ) ) otherlv_1= '=' ( (lv_message_2_0= RULE_STRING ) ) ) )
            // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:509:1: ( ( (lv_language_0_0= RULE_VALUE ) ) otherlv_1= '=' ( (lv_message_2_0= RULE_STRING ) ) )
            {
            // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:509:1: ( ( (lv_language_0_0= RULE_VALUE ) ) otherlv_1= '=' ( (lv_message_2_0= RULE_STRING ) ) )
            // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:509:2: ( (lv_language_0_0= RULE_VALUE ) ) otherlv_1= '=' ( (lv_message_2_0= RULE_STRING ) )
            {
            // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:509:2: ( (lv_language_0_0= RULE_VALUE ) )
            // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:510:1: (lv_language_0_0= RULE_VALUE )
            {
            // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:510:1: (lv_language_0_0= RULE_VALUE )
            // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:511:3: lv_language_0_0= RULE_VALUE
            {
            lv_language_0_0=(Token)match(input,RULE_VALUE,FOLLOW_RULE_VALUE_in_ruleTranslation1013); 

            			newLeafNode(lv_language_0_0, grammarAccess.getTranslationAccess().getLanguageVALUETerminalRuleCall_0_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getTranslationRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"language",
                    		lv_language_0_0, 
                    		"VALUE");
            	    

            }


            }

            otherlv_1=(Token)match(input,10,FOLLOW_10_in_ruleTranslation1030); 

                	newLeafNode(otherlv_1, grammarAccess.getTranslationAccess().getEqualsSignKeyword_1());
                
            // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:531:1: ( (lv_message_2_0= RULE_STRING ) )
            // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:532:1: (lv_message_2_0= RULE_STRING )
            {
            // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:532:1: (lv_message_2_0= RULE_STRING )
            // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:533:3: lv_message_2_0= RULE_STRING
            {
            lv_message_2_0=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleTranslation1047); 

            			newLeafNode(lv_message_2_0, grammarAccess.getTranslationAccess().getMessageSTRINGTerminalRuleCall_2_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getTranslationRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"message",
                    		lv_message_2_0, 
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
    // $ANTLR end "ruleTranslation"


    // $ANTLR start "ruleEnabled"
    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:557:1: ruleEnabled returns [Enumerator current=null] : ( (enumLiteral_0= 'true' ) | (enumLiteral_1= 'false' ) | (enumLiteral_2= 'conditional' ) ) ;
    public final Enumerator ruleEnabled() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;

         enterRule(); 
        try {
            // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:559:28: ( ( (enumLiteral_0= 'true' ) | (enumLiteral_1= 'false' ) | (enumLiteral_2= 'conditional' ) ) )
            // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:560:1: ( (enumLiteral_0= 'true' ) | (enumLiteral_1= 'false' ) | (enumLiteral_2= 'conditional' ) )
            {
            // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:560:1: ( (enumLiteral_0= 'true' ) | (enumLiteral_1= 'false' ) | (enumLiteral_2= 'conditional' ) )
            int alt12=3;
            switch ( input.LA(1) ) {
            case 13:
                {
                alt12=1;
                }
                break;
            case 26:
                {
                alt12=2;
                }
                break;
            case 27:
                {
                alt12=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 12, 0, input);

                throw nvae;
            }

            switch (alt12) {
                case 1 :
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:560:2: (enumLiteral_0= 'true' )
                    {
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:560:2: (enumLiteral_0= 'true' )
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:560:4: enumLiteral_0= 'true'
                    {
                    enumLiteral_0=(Token)match(input,13,FOLLOW_13_in_ruleEnabled1102); 

                            current = grammarAccess.getEnabledAccess().getTrueEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_0, grammarAccess.getEnabledAccess().getTrueEnumLiteralDeclaration_0()); 
                        

                    }


                    }
                    break;
                case 2 :
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:566:6: (enumLiteral_1= 'false' )
                    {
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:566:6: (enumLiteral_1= 'false' )
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:566:8: enumLiteral_1= 'false'
                    {
                    enumLiteral_1=(Token)match(input,26,FOLLOW_26_in_ruleEnabled1119); 

                            current = grammarAccess.getEnabledAccess().getFalseEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_1, grammarAccess.getEnabledAccess().getFalseEnumLiteralDeclaration_1()); 
                        

                    }


                    }
                    break;
                case 3 :
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:572:6: (enumLiteral_2= 'conditional' )
                    {
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:572:6: (enumLiteral_2= 'conditional' )
                    // ../com.odcgroup.t24.menu/src-gen/com/odcgroup/t24/menu/parser/antlr/internal/InternalMenu.g:572:8: enumLiteral_2= 'conditional'
                    {
                    enumLiteral_2=(Token)match(input,27,FOLLOW_27_in_ruleEnabled1136); 

                            current = grammarAccess.getEnabledAccess().getConditionalEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_2, grammarAccess.getEnabledAccess().getConditionalEnumLiteralDeclaration_2()); 
                        

                    }


                    }
                    break;

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
    // $ANTLR end "ruleEnabled"

    // Delegated rules


 

    public static final BitSet FOLLOW_ruleMenuRoot_in_entryRuleMenuRoot75 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMenuRoot85 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_9_in_ruleMenuRoot122 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_10_in_ruleMenuRoot134 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleMenuRoot151 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_ruleMenuItem_in_ruleMenuRoot177 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMenuItem_in_entryRuleMenuItem213 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMenuItem223 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_VALUE_in_ruleMenuItem265 = new BitSet(new long[]{0x0000000001FDD802L});
    public static final BitSet FOLLOW_11_in_ruleMenuItem283 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_10_in_ruleMenuItem295 = new BitSet(new long[]{0x000000000C002000L});
    public static final BitSet FOLLOW_ruleEnabled_in_ruleMenuItem316 = new BitSet(new long[]{0x0000000001FDD002L});
    public static final BitSet FOLLOW_12_in_ruleMenuItem331 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_10_in_ruleMenuItem343 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_13_in_ruleMenuItem361 = new BitSet(new long[]{0x0000000001FDC002L});
    public static final BitSet FOLLOW_14_in_ruleMenuItem389 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_10_in_ruleMenuItem401 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleMenuItem418 = new BitSet(new long[]{0x0000000001FD8002L});
    public static final BitSet FOLLOW_15_in_ruleMenuItem438 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_10_in_ruleMenuItem450 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleMenuItem467 = new BitSet(new long[]{0x0000000001FD0002L});
    public static final BitSet FOLLOW_16_in_ruleMenuItem487 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_ruleTranslation_in_ruleMenuItem508 = new BitSet(new long[]{0x0000000001FE0002L});
    public static final BitSet FOLLOW_17_in_ruleMenuItem521 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_ruleTranslation_in_ruleMenuItem542 = new BitSet(new long[]{0x0000000001FE0002L});
    public static final BitSet FOLLOW_18_in_ruleMenuItem560 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_10_in_ruleMenuItem572 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_RULE_VALUE_in_ruleMenuItem592 = new BitSet(new long[]{0x0000000001800002L});
    public static final BitSet FOLLOW_19_in_ruleMenuItem612 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_10_in_ruleMenuItem624 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_RULE_VALUE_in_ruleMenuItem644 = new BitSet(new long[]{0x0000000001800002L});
    public static final BitSet FOLLOW_20_in_ruleMenuItem664 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_10_in_ruleMenuItem676 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_RULE_VALUE_in_ruleMenuItem696 = new BitSet(new long[]{0x0000000001800002L});
    public static final BitSet FOLLOW_21_in_ruleMenuItem716 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_10_in_ruleMenuItem728 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_RULE_VALUE_in_ruleMenuItem748 = new BitSet(new long[]{0x0000000001800002L});
    public static final BitSet FOLLOW_22_in_ruleMenuItem768 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_10_in_ruleMenuItem780 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_RULE_VALUE_in_ruleMenuItem800 = new BitSet(new long[]{0x0000000001800002L});
    public static final BitSet FOLLOW_23_in_ruleMenuItem816 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_10_in_ruleMenuItem828 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleMenuItem845 = new BitSet(new long[]{0x0000000001000002L});
    public static final BitSet FOLLOW_24_in_ruleMenuItem865 = new BitSet(new long[]{0x0000000002000020L});
    public static final BitSet FOLLOW_ruleMenuItem_in_ruleMenuItem887 = new BitSet(new long[]{0x0000000002000020L});
    public static final BitSet FOLLOW_ruleMenuItem_in_ruleMenuItem908 = new BitSet(new long[]{0x0000000002000020L});
    public static final BitSet FOLLOW_25_in_ruleMenuItem923 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTranslation_in_entryRuleTranslation961 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTranslation971 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_VALUE_in_ruleTranslation1013 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_10_in_ruleTranslation1030 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleTranslation1047 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_13_in_ruleEnabled1102 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_26_in_ruleEnabled1119 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_27_in_ruleEnabled1136 = new BitSet(new long[]{0x0000000000000002L});

}
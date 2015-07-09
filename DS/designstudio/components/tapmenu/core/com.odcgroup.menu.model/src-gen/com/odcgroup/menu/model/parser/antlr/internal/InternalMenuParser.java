package com.odcgroup.menu.model.parser.antlr.internal; 

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
import com.odcgroup.menu.model.services.MenuGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalMenuParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ID", "RULE_STRING", "RULE_VALUE", "RULE_URI", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "'metamodelVersion'", "'='", "'pageflow'", "'enabled'", "'displayTabs'", "'true'", "'securityRole'", "'labels'", "','", "'{'", "'}'", "'shortcut'", "'false'", "'conditional'"
    };
    public static final int RULE_VALUE=6;
    public static final int RULE_ID=4;
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


        public InternalMenuParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalMenuParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalMenuParser.tokenNames; }
    public String getGrammarFileName() { return "../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g"; }



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
    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:68:1: entryRuleMenuRoot returns [EObject current=null] : iv_ruleMenuRoot= ruleMenuRoot EOF ;
    public final EObject entryRuleMenuRoot() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMenuRoot = null;


        try {
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:69:2: (iv_ruleMenuRoot= ruleMenuRoot EOF )
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:70:2: iv_ruleMenuRoot= ruleMenuRoot EOF
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
    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:77:1: ruleMenuRoot returns [EObject current=null] : (otherlv_0= 'metamodelVersion' otherlv_1= '=' ( (lv_metamodelVersion_2_0= ruleString_Value ) ) ( (lv_rootItem_3_0= ruleMenuItem ) ) ) ;
    public final EObject ruleMenuRoot() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        AntlrDatatypeRuleToken lv_metamodelVersion_2_0 = null;

        EObject lv_rootItem_3_0 = null;


         enterRule(); 
            
        try {
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:80:28: ( (otherlv_0= 'metamodelVersion' otherlv_1= '=' ( (lv_metamodelVersion_2_0= ruleString_Value ) ) ( (lv_rootItem_3_0= ruleMenuItem ) ) ) )
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:81:1: (otherlv_0= 'metamodelVersion' otherlv_1= '=' ( (lv_metamodelVersion_2_0= ruleString_Value ) ) ( (lv_rootItem_3_0= ruleMenuItem ) ) )
            {
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:81:1: (otherlv_0= 'metamodelVersion' otherlv_1= '=' ( (lv_metamodelVersion_2_0= ruleString_Value ) ) ( (lv_rootItem_3_0= ruleMenuItem ) ) )
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:81:3: otherlv_0= 'metamodelVersion' otherlv_1= '=' ( (lv_metamodelVersion_2_0= ruleString_Value ) ) ( (lv_rootItem_3_0= ruleMenuItem ) )
            {
            otherlv_0=(Token)match(input,11,FOLLOW_11_in_ruleMenuRoot122); 

                	newLeafNode(otherlv_0, grammarAccess.getMenuRootAccess().getMetamodelVersionKeyword_0());
                
            otherlv_1=(Token)match(input,12,FOLLOW_12_in_ruleMenuRoot134); 

                	newLeafNode(otherlv_1, grammarAccess.getMenuRootAccess().getEqualsSignKeyword_1());
                
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:89:1: ( (lv_metamodelVersion_2_0= ruleString_Value ) )
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:90:1: (lv_metamodelVersion_2_0= ruleString_Value )
            {
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:90:1: (lv_metamodelVersion_2_0= ruleString_Value )
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:91:3: lv_metamodelVersion_2_0= ruleString_Value
            {
             
            	        newCompositeNode(grammarAccess.getMenuRootAccess().getMetamodelVersionString_ValueParserRuleCall_2_0()); 
            	    
            pushFollow(FOLLOW_ruleString_Value_in_ruleMenuRoot155);
            lv_metamodelVersion_2_0=ruleString_Value();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getMenuRootRule());
            	        }
                   		set(
                   			current, 
                   			"metamodelVersion",
                    		lv_metamodelVersion_2_0, 
                    		"String_Value");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:107:2: ( (lv_rootItem_3_0= ruleMenuItem ) )
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:108:1: (lv_rootItem_3_0= ruleMenuItem )
            {
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:108:1: (lv_rootItem_3_0= ruleMenuItem )
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:109:3: lv_rootItem_3_0= ruleMenuItem
            {
             
            	        newCompositeNode(grammarAccess.getMenuRootAccess().getRootItemMenuItemParserRuleCall_3_0()); 
            	    
            pushFollow(FOLLOW_ruleMenuItem_in_ruleMenuRoot176);
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
    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:133:1: entryRuleMenuItem returns [EObject current=null] : iv_ruleMenuItem= ruleMenuItem EOF ;
    public final EObject entryRuleMenuItem() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMenuItem = null;


        try {
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:134:2: (iv_ruleMenuItem= ruleMenuItem EOF )
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:135:2: iv_ruleMenuItem= ruleMenuItem EOF
            {
             newCompositeNode(grammarAccess.getMenuItemRule()); 
            pushFollow(FOLLOW_ruleMenuItem_in_entryRuleMenuItem212);
            iv_ruleMenuItem=ruleMenuItem();

            state._fsp--;

             current =iv_ruleMenuItem; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleMenuItem222); 

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
    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:142:1: ruleMenuItem returns [EObject current=null] : ( ( (lv_name_0_0= ruleString_Value ) ) (otherlv_1= 'pageflow' otherlv_2= '=' ( (lv_pageflow_3_0= ruleString_Value ) ) )? (otherlv_4= 'enabled' otherlv_5= '=' ( (lv_enabled_6_0= ruleEnabled ) ) )? (otherlv_7= 'displayTabs' otherlv_8= '=' ( (lv_displayTabs_9_0= 'true' ) ) )? (otherlv_10= 'securityRole' otherlv_11= '=' ( (lv_securityRole_12_0= ruleString_Value ) ) )? (otherlv_13= 'labels' ( (lv_labels_14_0= ruleTranslation ) ) (otherlv_15= ',' ( (lv_labels_16_0= ruleTranslation ) ) )* )? (otherlv_17= '{' ( ( (lv_submenus_18_0= ruleMenuItem ) ) ( (lv_submenus_19_0= ruleMenuItem ) )* )? otherlv_20= '}' )? (otherlv_21= 'shortcut' otherlv_22= '=' ( (lv_shortcut_23_0= ruleString_Value ) ) )? ) ;
    public final EObject ruleMenuItem() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        Token otherlv_8=null;
        Token lv_displayTabs_9_0=null;
        Token otherlv_10=null;
        Token otherlv_11=null;
        Token otherlv_13=null;
        Token otherlv_15=null;
        Token otherlv_17=null;
        Token otherlv_20=null;
        Token otherlv_21=null;
        Token otherlv_22=null;
        AntlrDatatypeRuleToken lv_name_0_0 = null;

        AntlrDatatypeRuleToken lv_pageflow_3_0 = null;

        Enumerator lv_enabled_6_0 = null;

        AntlrDatatypeRuleToken lv_securityRole_12_0 = null;

        EObject lv_labels_14_0 = null;

        EObject lv_labels_16_0 = null;

        EObject lv_submenus_18_0 = null;

        EObject lv_submenus_19_0 = null;

        AntlrDatatypeRuleToken lv_shortcut_23_0 = null;


         enterRule(); 
            
        try {
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:145:28: ( ( ( (lv_name_0_0= ruleString_Value ) ) (otherlv_1= 'pageflow' otherlv_2= '=' ( (lv_pageflow_3_0= ruleString_Value ) ) )? (otherlv_4= 'enabled' otherlv_5= '=' ( (lv_enabled_6_0= ruleEnabled ) ) )? (otherlv_7= 'displayTabs' otherlv_8= '=' ( (lv_displayTabs_9_0= 'true' ) ) )? (otherlv_10= 'securityRole' otherlv_11= '=' ( (lv_securityRole_12_0= ruleString_Value ) ) )? (otherlv_13= 'labels' ( (lv_labels_14_0= ruleTranslation ) ) (otherlv_15= ',' ( (lv_labels_16_0= ruleTranslation ) ) )* )? (otherlv_17= '{' ( ( (lv_submenus_18_0= ruleMenuItem ) ) ( (lv_submenus_19_0= ruleMenuItem ) )* )? otherlv_20= '}' )? (otherlv_21= 'shortcut' otherlv_22= '=' ( (lv_shortcut_23_0= ruleString_Value ) ) )? ) )
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:146:1: ( ( (lv_name_0_0= ruleString_Value ) ) (otherlv_1= 'pageflow' otherlv_2= '=' ( (lv_pageflow_3_0= ruleString_Value ) ) )? (otherlv_4= 'enabled' otherlv_5= '=' ( (lv_enabled_6_0= ruleEnabled ) ) )? (otherlv_7= 'displayTabs' otherlv_8= '=' ( (lv_displayTabs_9_0= 'true' ) ) )? (otherlv_10= 'securityRole' otherlv_11= '=' ( (lv_securityRole_12_0= ruleString_Value ) ) )? (otherlv_13= 'labels' ( (lv_labels_14_0= ruleTranslation ) ) (otherlv_15= ',' ( (lv_labels_16_0= ruleTranslation ) ) )* )? (otherlv_17= '{' ( ( (lv_submenus_18_0= ruleMenuItem ) ) ( (lv_submenus_19_0= ruleMenuItem ) )* )? otherlv_20= '}' )? (otherlv_21= 'shortcut' otherlv_22= '=' ( (lv_shortcut_23_0= ruleString_Value ) ) )? )
            {
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:146:1: ( ( (lv_name_0_0= ruleString_Value ) ) (otherlv_1= 'pageflow' otherlv_2= '=' ( (lv_pageflow_3_0= ruleString_Value ) ) )? (otherlv_4= 'enabled' otherlv_5= '=' ( (lv_enabled_6_0= ruleEnabled ) ) )? (otherlv_7= 'displayTabs' otherlv_8= '=' ( (lv_displayTabs_9_0= 'true' ) ) )? (otherlv_10= 'securityRole' otherlv_11= '=' ( (lv_securityRole_12_0= ruleString_Value ) ) )? (otherlv_13= 'labels' ( (lv_labels_14_0= ruleTranslation ) ) (otherlv_15= ',' ( (lv_labels_16_0= ruleTranslation ) ) )* )? (otherlv_17= '{' ( ( (lv_submenus_18_0= ruleMenuItem ) ) ( (lv_submenus_19_0= ruleMenuItem ) )* )? otherlv_20= '}' )? (otherlv_21= 'shortcut' otherlv_22= '=' ( (lv_shortcut_23_0= ruleString_Value ) ) )? )
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:146:2: ( (lv_name_0_0= ruleString_Value ) ) (otherlv_1= 'pageflow' otherlv_2= '=' ( (lv_pageflow_3_0= ruleString_Value ) ) )? (otherlv_4= 'enabled' otherlv_5= '=' ( (lv_enabled_6_0= ruleEnabled ) ) )? (otherlv_7= 'displayTabs' otherlv_8= '=' ( (lv_displayTabs_9_0= 'true' ) ) )? (otherlv_10= 'securityRole' otherlv_11= '=' ( (lv_securityRole_12_0= ruleString_Value ) ) )? (otherlv_13= 'labels' ( (lv_labels_14_0= ruleTranslation ) ) (otherlv_15= ',' ( (lv_labels_16_0= ruleTranslation ) ) )* )? (otherlv_17= '{' ( ( (lv_submenus_18_0= ruleMenuItem ) ) ( (lv_submenus_19_0= ruleMenuItem ) )* )? otherlv_20= '}' )? (otherlv_21= 'shortcut' otherlv_22= '=' ( (lv_shortcut_23_0= ruleString_Value ) ) )?
            {
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:146:2: ( (lv_name_0_0= ruleString_Value ) )
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:147:1: (lv_name_0_0= ruleString_Value )
            {
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:147:1: (lv_name_0_0= ruleString_Value )
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:148:3: lv_name_0_0= ruleString_Value
            {
             
            	        newCompositeNode(grammarAccess.getMenuItemAccess().getNameString_ValueParserRuleCall_0_0()); 
            	    
            pushFollow(FOLLOW_ruleString_Value_in_ruleMenuItem268);
            lv_name_0_0=ruleString_Value();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getMenuItemRule());
            	        }
                   		set(
                   			current, 
                   			"name",
                    		lv_name_0_0, 
                    		"String_Value");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:164:2: (otherlv_1= 'pageflow' otherlv_2= '=' ( (lv_pageflow_3_0= ruleString_Value ) ) )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==13) ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:164:4: otherlv_1= 'pageflow' otherlv_2= '=' ( (lv_pageflow_3_0= ruleString_Value ) )
                    {
                    otherlv_1=(Token)match(input,13,FOLLOW_13_in_ruleMenuItem281); 

                        	newLeafNode(otherlv_1, grammarAccess.getMenuItemAccess().getPageflowKeyword_1_0());
                        
                    otherlv_2=(Token)match(input,12,FOLLOW_12_in_ruleMenuItem293); 

                        	newLeafNode(otherlv_2, grammarAccess.getMenuItemAccess().getEqualsSignKeyword_1_1());
                        
                    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:172:1: ( (lv_pageflow_3_0= ruleString_Value ) )
                    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:173:1: (lv_pageflow_3_0= ruleString_Value )
                    {
                    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:173:1: (lv_pageflow_3_0= ruleString_Value )
                    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:174:3: lv_pageflow_3_0= ruleString_Value
                    {
                     
                    	        newCompositeNode(grammarAccess.getMenuItemAccess().getPageflowString_ValueParserRuleCall_1_2_0()); 
                    	    
                    pushFollow(FOLLOW_ruleString_Value_in_ruleMenuItem314);
                    lv_pageflow_3_0=ruleString_Value();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getMenuItemRule());
                    	        }
                           		set(
                           			current, 
                           			"pageflow",
                            		lv_pageflow_3_0, 
                            		"String_Value");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }


                    }
                    break;

            }

            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:190:4: (otherlv_4= 'enabled' otherlv_5= '=' ( (lv_enabled_6_0= ruleEnabled ) ) )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==14) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:190:6: otherlv_4= 'enabled' otherlv_5= '=' ( (lv_enabled_6_0= ruleEnabled ) )
                    {
                    otherlv_4=(Token)match(input,14,FOLLOW_14_in_ruleMenuItem329); 

                        	newLeafNode(otherlv_4, grammarAccess.getMenuItemAccess().getEnabledKeyword_2_0());
                        
                    otherlv_5=(Token)match(input,12,FOLLOW_12_in_ruleMenuItem341); 

                        	newLeafNode(otherlv_5, grammarAccess.getMenuItemAccess().getEqualsSignKeyword_2_1());
                        
                    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:198:1: ( (lv_enabled_6_0= ruleEnabled ) )
                    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:199:1: (lv_enabled_6_0= ruleEnabled )
                    {
                    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:199:1: (lv_enabled_6_0= ruleEnabled )
                    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:200:3: lv_enabled_6_0= ruleEnabled
                    {
                     
                    	        newCompositeNode(grammarAccess.getMenuItemAccess().getEnabledEnabledEnumRuleCall_2_2_0()); 
                    	    
                    pushFollow(FOLLOW_ruleEnabled_in_ruleMenuItem362);
                    lv_enabled_6_0=ruleEnabled();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getMenuItemRule());
                    	        }
                           		set(
                           			current, 
                           			"enabled",
                            		lv_enabled_6_0, 
                            		"Enabled");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }


                    }
                    break;

            }

            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:216:4: (otherlv_7= 'displayTabs' otherlv_8= '=' ( (lv_displayTabs_9_0= 'true' ) ) )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==15) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:216:6: otherlv_7= 'displayTabs' otherlv_8= '=' ( (lv_displayTabs_9_0= 'true' ) )
                    {
                    otherlv_7=(Token)match(input,15,FOLLOW_15_in_ruleMenuItem377); 

                        	newLeafNode(otherlv_7, grammarAccess.getMenuItemAccess().getDisplayTabsKeyword_3_0());
                        
                    otherlv_8=(Token)match(input,12,FOLLOW_12_in_ruleMenuItem389); 

                        	newLeafNode(otherlv_8, grammarAccess.getMenuItemAccess().getEqualsSignKeyword_3_1());
                        
                    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:224:1: ( (lv_displayTabs_9_0= 'true' ) )
                    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:225:1: (lv_displayTabs_9_0= 'true' )
                    {
                    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:225:1: (lv_displayTabs_9_0= 'true' )
                    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:226:3: lv_displayTabs_9_0= 'true'
                    {
                    lv_displayTabs_9_0=(Token)match(input,16,FOLLOW_16_in_ruleMenuItem407); 

                            newLeafNode(lv_displayTabs_9_0, grammarAccess.getMenuItemAccess().getDisplayTabsTrueKeyword_3_2_0());
                        

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getMenuItemRule());
                    	        }
                           		setWithLastConsumed(current, "displayTabs", true, "true");
                    	    

                    }


                    }


                    }
                    break;

            }

            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:239:4: (otherlv_10= 'securityRole' otherlv_11= '=' ( (lv_securityRole_12_0= ruleString_Value ) ) )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==17) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:239:6: otherlv_10= 'securityRole' otherlv_11= '=' ( (lv_securityRole_12_0= ruleString_Value ) )
                    {
                    otherlv_10=(Token)match(input,17,FOLLOW_17_in_ruleMenuItem435); 

                        	newLeafNode(otherlv_10, grammarAccess.getMenuItemAccess().getSecurityRoleKeyword_4_0());
                        
                    otherlv_11=(Token)match(input,12,FOLLOW_12_in_ruleMenuItem447); 

                        	newLeafNode(otherlv_11, grammarAccess.getMenuItemAccess().getEqualsSignKeyword_4_1());
                        
                    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:247:1: ( (lv_securityRole_12_0= ruleString_Value ) )
                    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:248:1: (lv_securityRole_12_0= ruleString_Value )
                    {
                    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:248:1: (lv_securityRole_12_0= ruleString_Value )
                    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:249:3: lv_securityRole_12_0= ruleString_Value
                    {
                     
                    	        newCompositeNode(grammarAccess.getMenuItemAccess().getSecurityRoleString_ValueParserRuleCall_4_2_0()); 
                    	    
                    pushFollow(FOLLOW_ruleString_Value_in_ruleMenuItem468);
                    lv_securityRole_12_0=ruleString_Value();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getMenuItemRule());
                    	        }
                           		set(
                           			current, 
                           			"securityRole",
                            		lv_securityRole_12_0, 
                            		"String_Value");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }


                    }
                    break;

            }

            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:265:4: (otherlv_13= 'labels' ( (lv_labels_14_0= ruleTranslation ) ) (otherlv_15= ',' ( (lv_labels_16_0= ruleTranslation ) ) )* )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==18) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:265:6: otherlv_13= 'labels' ( (lv_labels_14_0= ruleTranslation ) ) (otherlv_15= ',' ( (lv_labels_16_0= ruleTranslation ) ) )*
                    {
                    otherlv_13=(Token)match(input,18,FOLLOW_18_in_ruleMenuItem483); 

                        	newLeafNode(otherlv_13, grammarAccess.getMenuItemAccess().getLabelsKeyword_5_0());
                        
                    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:269:1: ( (lv_labels_14_0= ruleTranslation ) )
                    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:270:1: (lv_labels_14_0= ruleTranslation )
                    {
                    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:270:1: (lv_labels_14_0= ruleTranslation )
                    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:271:3: lv_labels_14_0= ruleTranslation
                    {
                     
                    	        newCompositeNode(grammarAccess.getMenuItemAccess().getLabelsTranslationParserRuleCall_5_1_0()); 
                    	    
                    pushFollow(FOLLOW_ruleTranslation_in_ruleMenuItem504);
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

                    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:287:2: (otherlv_15= ',' ( (lv_labels_16_0= ruleTranslation ) ) )*
                    loop5:
                    do {
                        int alt5=2;
                        int LA5_0 = input.LA(1);

                        if ( (LA5_0==19) ) {
                            alt5=1;
                        }


                        switch (alt5) {
                    	case 1 :
                    	    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:287:4: otherlv_15= ',' ( (lv_labels_16_0= ruleTranslation ) )
                    	    {
                    	    otherlv_15=(Token)match(input,19,FOLLOW_19_in_ruleMenuItem517); 

                    	        	newLeafNode(otherlv_15, grammarAccess.getMenuItemAccess().getCommaKeyword_5_2_0());
                    	        
                    	    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:291:1: ( (lv_labels_16_0= ruleTranslation ) )
                    	    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:292:1: (lv_labels_16_0= ruleTranslation )
                    	    {
                    	    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:292:1: (lv_labels_16_0= ruleTranslation )
                    	    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:293:3: lv_labels_16_0= ruleTranslation
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getMenuItemAccess().getLabelsTranslationParserRuleCall_5_2_1_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleTranslation_in_ruleMenuItem538);
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

            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:309:6: (otherlv_17= '{' ( ( (lv_submenus_18_0= ruleMenuItem ) ) ( (lv_submenus_19_0= ruleMenuItem ) )* )? otherlv_20= '}' )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==20) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:309:8: otherlv_17= '{' ( ( (lv_submenus_18_0= ruleMenuItem ) ) ( (lv_submenus_19_0= ruleMenuItem ) )* )? otherlv_20= '}'
                    {
                    otherlv_17=(Token)match(input,20,FOLLOW_20_in_ruleMenuItem555); 

                        	newLeafNode(otherlv_17, grammarAccess.getMenuItemAccess().getLeftCurlyBracketKeyword_6_0());
                        
                    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:313:1: ( ( (lv_submenus_18_0= ruleMenuItem ) ) ( (lv_submenus_19_0= ruleMenuItem ) )* )?
                    int alt8=2;
                    int LA8_0 = input.LA(1);

                    if ( ((LA8_0>=RULE_ID && LA8_0<=RULE_URI)) ) {
                        alt8=1;
                    }
                    switch (alt8) {
                        case 1 :
                            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:313:2: ( (lv_submenus_18_0= ruleMenuItem ) ) ( (lv_submenus_19_0= ruleMenuItem ) )*
                            {
                            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:313:2: ( (lv_submenus_18_0= ruleMenuItem ) )
                            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:314:1: (lv_submenus_18_0= ruleMenuItem )
                            {
                            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:314:1: (lv_submenus_18_0= ruleMenuItem )
                            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:315:3: lv_submenus_18_0= ruleMenuItem
                            {
                             
                            	        newCompositeNode(grammarAccess.getMenuItemAccess().getSubmenusMenuItemParserRuleCall_6_1_0_0()); 
                            	    
                            pushFollow(FOLLOW_ruleMenuItem_in_ruleMenuItem577);
                            lv_submenus_18_0=ruleMenuItem();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getMenuItemRule());
                            	        }
                                   		add(
                                   			current, 
                                   			"submenus",
                                    		lv_submenus_18_0, 
                                    		"MenuItem");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }

                            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:331:2: ( (lv_submenus_19_0= ruleMenuItem ) )*
                            loop7:
                            do {
                                int alt7=2;
                                int LA7_0 = input.LA(1);

                                if ( ((LA7_0>=RULE_ID && LA7_0<=RULE_URI)) ) {
                                    alt7=1;
                                }


                                switch (alt7) {
                            	case 1 :
                            	    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:332:1: (lv_submenus_19_0= ruleMenuItem )
                            	    {
                            	    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:332:1: (lv_submenus_19_0= ruleMenuItem )
                            	    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:333:3: lv_submenus_19_0= ruleMenuItem
                            	    {
                            	     
                            	    	        newCompositeNode(grammarAccess.getMenuItemAccess().getSubmenusMenuItemParserRuleCall_6_1_1_0()); 
                            	    	    
                            	    pushFollow(FOLLOW_ruleMenuItem_in_ruleMenuItem598);
                            	    lv_submenus_19_0=ruleMenuItem();

                            	    state._fsp--;


                            	    	        if (current==null) {
                            	    	            current = createModelElementForParent(grammarAccess.getMenuItemRule());
                            	    	        }
                            	           		add(
                            	           			current, 
                            	           			"submenus",
                            	            		lv_submenus_19_0, 
                            	            		"MenuItem");
                            	    	        afterParserOrEnumRuleCall();
                            	    	    

                            	    }


                            	    }
                            	    break;

                            	default :
                            	    break loop7;
                                }
                            } while (true);


                            }
                            break;

                    }

                    otherlv_20=(Token)match(input,21,FOLLOW_21_in_ruleMenuItem613); 

                        	newLeafNode(otherlv_20, grammarAccess.getMenuItemAccess().getRightCurlyBracketKeyword_6_2());
                        

                    }
                    break;

            }

            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:353:3: (otherlv_21= 'shortcut' otherlv_22= '=' ( (lv_shortcut_23_0= ruleString_Value ) ) )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==22) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:353:5: otherlv_21= 'shortcut' otherlv_22= '=' ( (lv_shortcut_23_0= ruleString_Value ) )
                    {
                    otherlv_21=(Token)match(input,22,FOLLOW_22_in_ruleMenuItem628); 

                        	newLeafNode(otherlv_21, grammarAccess.getMenuItemAccess().getShortcutKeyword_7_0());
                        
                    otherlv_22=(Token)match(input,12,FOLLOW_12_in_ruleMenuItem640); 

                        	newLeafNode(otherlv_22, grammarAccess.getMenuItemAccess().getEqualsSignKeyword_7_1());
                        
                    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:361:1: ( (lv_shortcut_23_0= ruleString_Value ) )
                    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:362:1: (lv_shortcut_23_0= ruleString_Value )
                    {
                    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:362:1: (lv_shortcut_23_0= ruleString_Value )
                    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:363:3: lv_shortcut_23_0= ruleString_Value
                    {
                     
                    	        newCompositeNode(grammarAccess.getMenuItemAccess().getShortcutString_ValueParserRuleCall_7_2_0()); 
                    	    
                    pushFollow(FOLLOW_ruleString_Value_in_ruleMenuItem661);
                    lv_shortcut_23_0=ruleString_Value();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getMenuItemRule());
                    	        }
                           		set(
                           			current, 
                           			"shortcut",
                            		lv_shortcut_23_0, 
                            		"String_Value");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }


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
    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:387:1: entryRuleTranslation returns [EObject current=null] : iv_ruleTranslation= ruleTranslation EOF ;
    public final EObject entryRuleTranslation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTranslation = null;


        try {
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:388:2: (iv_ruleTranslation= ruleTranslation EOF )
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:389:2: iv_ruleTranslation= ruleTranslation EOF
            {
             newCompositeNode(grammarAccess.getTranslationRule()); 
            pushFollow(FOLLOW_ruleTranslation_in_entryRuleTranslation699);
            iv_ruleTranslation=ruleTranslation();

            state._fsp--;

             current =iv_ruleTranslation; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleTranslation709); 

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
    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:396:1: ruleTranslation returns [EObject current=null] : ( ( (lv_language_0_0= RULE_ID ) ) otherlv_1= '=' ( (lv_message_2_0= ruleString_Value ) ) ) ;
    public final EObject ruleTranslation() throws RecognitionException {
        EObject current = null;

        Token lv_language_0_0=null;
        Token otherlv_1=null;
        AntlrDatatypeRuleToken lv_message_2_0 = null;


         enterRule(); 
            
        try {
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:399:28: ( ( ( (lv_language_0_0= RULE_ID ) ) otherlv_1= '=' ( (lv_message_2_0= ruleString_Value ) ) ) )
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:400:1: ( ( (lv_language_0_0= RULE_ID ) ) otherlv_1= '=' ( (lv_message_2_0= ruleString_Value ) ) )
            {
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:400:1: ( ( (lv_language_0_0= RULE_ID ) ) otherlv_1= '=' ( (lv_message_2_0= ruleString_Value ) ) )
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:400:2: ( (lv_language_0_0= RULE_ID ) ) otherlv_1= '=' ( (lv_message_2_0= ruleString_Value ) )
            {
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:400:2: ( (lv_language_0_0= RULE_ID ) )
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:401:1: (lv_language_0_0= RULE_ID )
            {
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:401:1: (lv_language_0_0= RULE_ID )
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:402:3: lv_language_0_0= RULE_ID
            {
            lv_language_0_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleTranslation751); 

            			newLeafNode(lv_language_0_0, grammarAccess.getTranslationAccess().getLanguageIDTerminalRuleCall_0_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getTranslationRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"language",
                    		lv_language_0_0, 
                    		"ID");
            	    

            }


            }

            otherlv_1=(Token)match(input,12,FOLLOW_12_in_ruleTranslation768); 

                	newLeafNode(otherlv_1, grammarAccess.getTranslationAccess().getEqualsSignKeyword_1());
                
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:422:1: ( (lv_message_2_0= ruleString_Value ) )
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:423:1: (lv_message_2_0= ruleString_Value )
            {
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:423:1: (lv_message_2_0= ruleString_Value )
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:424:3: lv_message_2_0= ruleString_Value
            {
             
            	        newCompositeNode(grammarAccess.getTranslationAccess().getMessageString_ValueParserRuleCall_2_0()); 
            	    
            pushFollow(FOLLOW_ruleString_Value_in_ruleTranslation789);
            lv_message_2_0=ruleString_Value();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getTranslationRule());
            	        }
                   		set(
                   			current, 
                   			"message",
                    		lv_message_2_0, 
                    		"String_Value");
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
    // $ANTLR end "ruleTranslation"


    // $ANTLR start "entryRuleString_Value"
    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:448:1: entryRuleString_Value returns [String current=null] : iv_ruleString_Value= ruleString_Value EOF ;
    public final String entryRuleString_Value() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleString_Value = null;


        try {
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:449:2: (iv_ruleString_Value= ruleString_Value EOF )
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:450:2: iv_ruleString_Value= ruleString_Value EOF
            {
             newCompositeNode(grammarAccess.getString_ValueRule()); 
            pushFollow(FOLLOW_ruleString_Value_in_entryRuleString_Value826);
            iv_ruleString_Value=ruleString_Value();

            state._fsp--;

             current =iv_ruleString_Value.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleString_Value837); 

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
    // $ANTLR end "entryRuleString_Value"


    // $ANTLR start "ruleString_Value"
    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:457:1: ruleString_Value returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_STRING_0= RULE_STRING | this_ID_1= RULE_ID | this_VALUE_2= RULE_VALUE | this_URI_3= RULE_URI ) ;
    public final AntlrDatatypeRuleToken ruleString_Value() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_STRING_0=null;
        Token this_ID_1=null;
        Token this_VALUE_2=null;
        Token this_URI_3=null;

         enterRule(); 
            
        try {
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:460:28: ( (this_STRING_0= RULE_STRING | this_ID_1= RULE_ID | this_VALUE_2= RULE_VALUE | this_URI_3= RULE_URI ) )
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:461:1: (this_STRING_0= RULE_STRING | this_ID_1= RULE_ID | this_VALUE_2= RULE_VALUE | this_URI_3= RULE_URI )
            {
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:461:1: (this_STRING_0= RULE_STRING | this_ID_1= RULE_ID | this_VALUE_2= RULE_VALUE | this_URI_3= RULE_URI )
            int alt11=4;
            switch ( input.LA(1) ) {
            case RULE_STRING:
                {
                alt11=1;
                }
                break;
            case RULE_ID:
                {
                alt11=2;
                }
                break;
            case RULE_VALUE:
                {
                alt11=3;
                }
                break;
            case RULE_URI:
                {
                alt11=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 0, input);

                throw nvae;
            }

            switch (alt11) {
                case 1 :
                    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:461:6: this_STRING_0= RULE_STRING
                    {
                    this_STRING_0=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleString_Value877); 

                    		current.merge(this_STRING_0);
                        
                     
                        newLeafNode(this_STRING_0, grammarAccess.getString_ValueAccess().getSTRINGTerminalRuleCall_0()); 
                        

                    }
                    break;
                case 2 :
                    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:469:10: this_ID_1= RULE_ID
                    {
                    this_ID_1=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleString_Value903); 

                    		current.merge(this_ID_1);
                        
                     
                        newLeafNode(this_ID_1, grammarAccess.getString_ValueAccess().getIDTerminalRuleCall_1()); 
                        

                    }
                    break;
                case 3 :
                    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:477:10: this_VALUE_2= RULE_VALUE
                    {
                    this_VALUE_2=(Token)match(input,RULE_VALUE,FOLLOW_RULE_VALUE_in_ruleString_Value929); 

                    		current.merge(this_VALUE_2);
                        
                     
                        newLeafNode(this_VALUE_2, grammarAccess.getString_ValueAccess().getVALUETerminalRuleCall_2()); 
                        

                    }
                    break;
                case 4 :
                    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:485:10: this_URI_3= RULE_URI
                    {
                    this_URI_3=(Token)match(input,RULE_URI,FOLLOW_RULE_URI_in_ruleString_Value955); 

                    		current.merge(this_URI_3);
                        
                     
                        newLeafNode(this_URI_3, grammarAccess.getString_ValueAccess().getURITerminalRuleCall_3()); 
                        

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
    // $ANTLR end "ruleString_Value"


    // $ANTLR start "ruleEnabled"
    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:500:1: ruleEnabled returns [Enumerator current=null] : ( (enumLiteral_0= 'true' ) | (enumLiteral_1= 'false' ) | (enumLiteral_2= 'conditional' ) ) ;
    public final Enumerator ruleEnabled() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;

         enterRule(); 
        try {
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:502:28: ( ( (enumLiteral_0= 'true' ) | (enumLiteral_1= 'false' ) | (enumLiteral_2= 'conditional' ) ) )
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:503:1: ( (enumLiteral_0= 'true' ) | (enumLiteral_1= 'false' ) | (enumLiteral_2= 'conditional' ) )
            {
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:503:1: ( (enumLiteral_0= 'true' ) | (enumLiteral_1= 'false' ) | (enumLiteral_2= 'conditional' ) )
            int alt12=3;
            switch ( input.LA(1) ) {
            case 16:
                {
                alt12=1;
                }
                break;
            case 23:
                {
                alt12=2;
                }
                break;
            case 24:
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
                    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:503:2: (enumLiteral_0= 'true' )
                    {
                    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:503:2: (enumLiteral_0= 'true' )
                    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:503:4: enumLiteral_0= 'true'
                    {
                    enumLiteral_0=(Token)match(input,16,FOLLOW_16_in_ruleEnabled1014); 

                            current = grammarAccess.getEnabledAccess().getTrueEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_0, grammarAccess.getEnabledAccess().getTrueEnumLiteralDeclaration_0()); 
                        

                    }


                    }
                    break;
                case 2 :
                    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:509:6: (enumLiteral_1= 'false' )
                    {
                    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:509:6: (enumLiteral_1= 'false' )
                    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:509:8: enumLiteral_1= 'false'
                    {
                    enumLiteral_1=(Token)match(input,23,FOLLOW_23_in_ruleEnabled1031); 

                            current = grammarAccess.getEnabledAccess().getFalseEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_1, grammarAccess.getEnabledAccess().getFalseEnumLiteralDeclaration_1()); 
                        

                    }


                    }
                    break;
                case 3 :
                    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:515:6: (enumLiteral_2= 'conditional' )
                    {
                    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:515:6: (enumLiteral_2= 'conditional' )
                    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:515:8: enumLiteral_2= 'conditional'
                    {
                    enumLiteral_2=(Token)match(input,24,FOLLOW_24_in_ruleEnabled1048); 

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
    public static final BitSet FOLLOW_11_in_ruleMenuRoot122 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleMenuRoot134 = new BitSet(new long[]{0x00000000000000F0L});
    public static final BitSet FOLLOW_ruleString_Value_in_ruleMenuRoot155 = new BitSet(new long[]{0x00000000000000F0L});
    public static final BitSet FOLLOW_ruleMenuItem_in_ruleMenuRoot176 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMenuItem_in_entryRuleMenuItem212 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMenuItem222 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleString_Value_in_ruleMenuItem268 = new BitSet(new long[]{0x000000000056E002L});
    public static final BitSet FOLLOW_13_in_ruleMenuItem281 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleMenuItem293 = new BitSet(new long[]{0x00000000000000F0L});
    public static final BitSet FOLLOW_ruleString_Value_in_ruleMenuItem314 = new BitSet(new long[]{0x000000000056C002L});
    public static final BitSet FOLLOW_14_in_ruleMenuItem329 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleMenuItem341 = new BitSet(new long[]{0x0000000001810000L});
    public static final BitSet FOLLOW_ruleEnabled_in_ruleMenuItem362 = new BitSet(new long[]{0x0000000000568002L});
    public static final BitSet FOLLOW_15_in_ruleMenuItem377 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleMenuItem389 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_ruleMenuItem407 = new BitSet(new long[]{0x0000000000560002L});
    public static final BitSet FOLLOW_17_in_ruleMenuItem435 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleMenuItem447 = new BitSet(new long[]{0x00000000000000F0L});
    public static final BitSet FOLLOW_ruleString_Value_in_ruleMenuItem468 = new BitSet(new long[]{0x0000000000540002L});
    public static final BitSet FOLLOW_18_in_ruleMenuItem483 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleTranslation_in_ruleMenuItem504 = new BitSet(new long[]{0x0000000000580002L});
    public static final BitSet FOLLOW_19_in_ruleMenuItem517 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleTranslation_in_ruleMenuItem538 = new BitSet(new long[]{0x0000000000580002L});
    public static final BitSet FOLLOW_20_in_ruleMenuItem555 = new BitSet(new long[]{0x00000000002000F0L});
    public static final BitSet FOLLOW_ruleMenuItem_in_ruleMenuItem577 = new BitSet(new long[]{0x00000000002000F0L});
    public static final BitSet FOLLOW_ruleMenuItem_in_ruleMenuItem598 = new BitSet(new long[]{0x00000000002000F0L});
    public static final BitSet FOLLOW_21_in_ruleMenuItem613 = new BitSet(new long[]{0x0000000000400002L});
    public static final BitSet FOLLOW_22_in_ruleMenuItem628 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleMenuItem640 = new BitSet(new long[]{0x00000000000000F0L});
    public static final BitSet FOLLOW_ruleString_Value_in_ruleMenuItem661 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTranslation_in_entryRuleTranslation699 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTranslation709 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleTranslation751 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleTranslation768 = new BitSet(new long[]{0x00000000000000F0L});
    public static final BitSet FOLLOW_ruleString_Value_in_ruleTranslation789 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleString_Value_in_entryRuleString_Value826 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleString_Value837 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleString_Value877 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleString_Value903 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_VALUE_in_ruleString_Value929 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_URI_in_ruleString_Value955 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_16_in_ruleEnabled1014 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_23_in_ruleEnabled1031 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_24_in_ruleEnabled1048 = new BitSet(new long[]{0x0000000000000002L});

}
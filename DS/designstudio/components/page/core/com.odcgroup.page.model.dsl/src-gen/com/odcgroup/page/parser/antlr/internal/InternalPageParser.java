package com.odcgroup.page.parser.antlr.internal; 

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
import com.odcgroup.page.services.PageGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalPageParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ID", "RULE_URI", "RULE_STRING", "RULE_VALUE", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "'metamodelVersion'", "'='", "'---'", "':'", "'labels'", "','", "'toolTips'", "'{'", "'}'", "'!'", "'Event'", "'('", "')'", "'//'", "'messages'", "'Snippet'", "'ud'", "'A'", "'S'"
    };
    public static final int RULE_VALUE=7;
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
    public static final int RULE_URI=5;
    public static final int RULE_SL_COMMENT=9;
    public static final int EOF=-1;
    public static final int RULE_ML_COMMENT=8;
    public static final int T__19=19;
    public static final int RULE_STRING=6;
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
    public String getGrammarFileName() { return "../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g"; }



     	private PageGrammarAccess grammarAccess;
     	
        public InternalPageParser(TokenStream input, PageGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }
        
        @Override
        protected String getFirstRuleName() {
        	return "Model";	
       	}
       	
       	@Override
       	protected PageGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}



    // $ANTLR start "entryRuleModel"
    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:68:1: entryRuleModel returns [EObject current=null] : iv_ruleModel= ruleModel EOF ;
    public final EObject entryRuleModel() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleModel = null;


        try {
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:69:2: (iv_ruleModel= ruleModel EOF )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:70:2: iv_ruleModel= ruleModel EOF
            {
             newCompositeNode(grammarAccess.getModelRule()); 
            pushFollow(FollowSets000.FOLLOW_ruleModel_in_entryRuleModel75);
            iv_ruleModel=ruleModel();

            state._fsp--;

             current =iv_ruleModel; 
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleModel85); 

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
    // $ANTLR end "entryRuleModel"


    // $ANTLR start "ruleModel"
    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:77:1: ruleModel returns [EObject current=null] : ( () otherlv_1= 'metamodelVersion' otherlv_2= '=' ( (lv_metamodelVersion_3_0= ruleString_Value ) ) ( (lv_widget_4_0= ruleWidget ) ) ) ;
    public final EObject ruleModel() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        AntlrDatatypeRuleToken lv_metamodelVersion_3_0 = null;

        EObject lv_widget_4_0 = null;


         enterRule(); 
            
        try {
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:80:28: ( ( () otherlv_1= 'metamodelVersion' otherlv_2= '=' ( (lv_metamodelVersion_3_0= ruleString_Value ) ) ( (lv_widget_4_0= ruleWidget ) ) ) )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:81:1: ( () otherlv_1= 'metamodelVersion' otherlv_2= '=' ( (lv_metamodelVersion_3_0= ruleString_Value ) ) ( (lv_widget_4_0= ruleWidget ) ) )
            {
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:81:1: ( () otherlv_1= 'metamodelVersion' otherlv_2= '=' ( (lv_metamodelVersion_3_0= ruleString_Value ) ) ( (lv_widget_4_0= ruleWidget ) ) )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:81:2: () otherlv_1= 'metamodelVersion' otherlv_2= '=' ( (lv_metamodelVersion_3_0= ruleString_Value ) ) ( (lv_widget_4_0= ruleWidget ) )
            {
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:81:2: ()
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:82:5: 
            {

                    current = forceCreateModelElement(
                        grammarAccess.getModelAccess().getModelAction_0(),
                        current);
                

            }

            otherlv_1=(Token)match(input,11,FollowSets000.FOLLOW_11_in_ruleModel131); 

                	newLeafNode(otherlv_1, grammarAccess.getModelAccess().getMetamodelVersionKeyword_1());
                
            otherlv_2=(Token)match(input,12,FollowSets000.FOLLOW_12_in_ruleModel143); 

                	newLeafNode(otherlv_2, grammarAccess.getModelAccess().getEqualsSignKeyword_2());
                
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:95:1: ( (lv_metamodelVersion_3_0= ruleString_Value ) )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:96:1: (lv_metamodelVersion_3_0= ruleString_Value )
            {
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:96:1: (lv_metamodelVersion_3_0= ruleString_Value )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:97:3: lv_metamodelVersion_3_0= ruleString_Value
            {
             
            	        newCompositeNode(grammarAccess.getModelAccess().getMetamodelVersionString_ValueParserRuleCall_3_0()); 
            	    
            pushFollow(FollowSets000.FOLLOW_ruleString_Value_in_ruleModel164);
            lv_metamodelVersion_3_0=ruleString_Value();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getModelRule());
            	        }
                   		set(
                   			current, 
                   			"metamodelVersion",
                    		lv_metamodelVersion_3_0, 
                    		"String_Value");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:113:2: ( (lv_widget_4_0= ruleWidget ) )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:114:1: (lv_widget_4_0= ruleWidget )
            {
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:114:1: (lv_widget_4_0= ruleWidget )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:115:3: lv_widget_4_0= ruleWidget
            {
             
            	        newCompositeNode(grammarAccess.getModelAccess().getWidgetWidgetParserRuleCall_4_0()); 
            	    
            pushFollow(FollowSets000.FOLLOW_ruleWidget_in_ruleModel185);
            lv_widget_4_0=ruleWidget();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getModelRule());
            	        }
                   		set(
                   			current, 
                   			"widget",
                    		lv_widget_4_0, 
                    		"Widget");
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
    // $ANTLR end "ruleModel"


    // $ANTLR start "entryRuleWidget"
    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:139:1: entryRuleWidget returns [EObject current=null] : iv_ruleWidget= ruleWidget EOF ;
    public final EObject entryRuleWidget() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleWidget = null;


        try {
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:140:2: (iv_ruleWidget= ruleWidget EOF )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:141:2: iv_ruleWidget= ruleWidget EOF
            {
             newCompositeNode(grammarAccess.getWidgetRule()); 
            pushFollow(FollowSets000.FOLLOW_ruleWidget_in_entryRuleWidget221);
            iv_ruleWidget=ruleWidget();

            state._fsp--;

             current =iv_ruleWidget; 
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleWidget231); 

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
    // $ANTLR end "entryRuleWidget"


    // $ANTLR start "ruleWidget"
    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:148:1: ruleWidget returns [EObject current=null] : ( () otherlv_1= '---' ( ( (lv_libraryName_2_0= ruleLibraryName ) ) otherlv_3= ':' )? ( (lv_typeName_4_0= RULE_ID ) ) otherlv_5= '---' (otherlv_6= 'labels' ( (lv_labels_7_0= ruleTranslation ) ) (otherlv_8= ',' ( (lv_labels_9_0= ruleTranslation ) ) )* )? (otherlv_10= 'toolTips' ( (lv_toolTips_11_0= ruleTranslation ) ) (otherlv_12= ',' ( (lv_toolTips_13_0= ruleTranslation ) ) )* )? ( (lv_properties_14_0= ruleProperty ) )* (otherlv_15= '{' ( ( (lv_events_16_0= ruleEvent ) ) ( (lv_events_17_0= ruleEvent ) )* )? ( ( (lv_snippets_18_0= ruleSnippet ) ) ( (lv_snippets_19_0= ruleSnippet ) )* )? ( ( (lv_contents_20_0= ruleWidget ) ) ( (lv_contents_21_0= ruleWidget ) )* )? otherlv_22= '}' )? ) ;
    public final EObject ruleWidget() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token lv_typeName_4_0=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        Token otherlv_10=null;
        Token otherlv_12=null;
        Token otherlv_15=null;
        Token otherlv_22=null;
        AntlrDatatypeRuleToken lv_libraryName_2_0 = null;

        EObject lv_labels_7_0 = null;

        EObject lv_labels_9_0 = null;

        EObject lv_toolTips_11_0 = null;

        EObject lv_toolTips_13_0 = null;

        EObject lv_properties_14_0 = null;

        EObject lv_events_16_0 = null;

        EObject lv_events_17_0 = null;

        EObject lv_snippets_18_0 = null;

        EObject lv_snippets_19_0 = null;

        EObject lv_contents_20_0 = null;

        EObject lv_contents_21_0 = null;


         enterRule(); 
            
        try {
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:151:28: ( ( () otherlv_1= '---' ( ( (lv_libraryName_2_0= ruleLibraryName ) ) otherlv_3= ':' )? ( (lv_typeName_4_0= RULE_ID ) ) otherlv_5= '---' (otherlv_6= 'labels' ( (lv_labels_7_0= ruleTranslation ) ) (otherlv_8= ',' ( (lv_labels_9_0= ruleTranslation ) ) )* )? (otherlv_10= 'toolTips' ( (lv_toolTips_11_0= ruleTranslation ) ) (otherlv_12= ',' ( (lv_toolTips_13_0= ruleTranslation ) ) )* )? ( (lv_properties_14_0= ruleProperty ) )* (otherlv_15= '{' ( ( (lv_events_16_0= ruleEvent ) ) ( (lv_events_17_0= ruleEvent ) )* )? ( ( (lv_snippets_18_0= ruleSnippet ) ) ( (lv_snippets_19_0= ruleSnippet ) )* )? ( ( (lv_contents_20_0= ruleWidget ) ) ( (lv_contents_21_0= ruleWidget ) )* )? otherlv_22= '}' )? ) )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:152:1: ( () otherlv_1= '---' ( ( (lv_libraryName_2_0= ruleLibraryName ) ) otherlv_3= ':' )? ( (lv_typeName_4_0= RULE_ID ) ) otherlv_5= '---' (otherlv_6= 'labels' ( (lv_labels_7_0= ruleTranslation ) ) (otherlv_8= ',' ( (lv_labels_9_0= ruleTranslation ) ) )* )? (otherlv_10= 'toolTips' ( (lv_toolTips_11_0= ruleTranslation ) ) (otherlv_12= ',' ( (lv_toolTips_13_0= ruleTranslation ) ) )* )? ( (lv_properties_14_0= ruleProperty ) )* (otherlv_15= '{' ( ( (lv_events_16_0= ruleEvent ) ) ( (lv_events_17_0= ruleEvent ) )* )? ( ( (lv_snippets_18_0= ruleSnippet ) ) ( (lv_snippets_19_0= ruleSnippet ) )* )? ( ( (lv_contents_20_0= ruleWidget ) ) ( (lv_contents_21_0= ruleWidget ) )* )? otherlv_22= '}' )? )
            {
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:152:1: ( () otherlv_1= '---' ( ( (lv_libraryName_2_0= ruleLibraryName ) ) otherlv_3= ':' )? ( (lv_typeName_4_0= RULE_ID ) ) otherlv_5= '---' (otherlv_6= 'labels' ( (lv_labels_7_0= ruleTranslation ) ) (otherlv_8= ',' ( (lv_labels_9_0= ruleTranslation ) ) )* )? (otherlv_10= 'toolTips' ( (lv_toolTips_11_0= ruleTranslation ) ) (otherlv_12= ',' ( (lv_toolTips_13_0= ruleTranslation ) ) )* )? ( (lv_properties_14_0= ruleProperty ) )* (otherlv_15= '{' ( ( (lv_events_16_0= ruleEvent ) ) ( (lv_events_17_0= ruleEvent ) )* )? ( ( (lv_snippets_18_0= ruleSnippet ) ) ( (lv_snippets_19_0= ruleSnippet ) )* )? ( ( (lv_contents_20_0= ruleWidget ) ) ( (lv_contents_21_0= ruleWidget ) )* )? otherlv_22= '}' )? )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:152:2: () otherlv_1= '---' ( ( (lv_libraryName_2_0= ruleLibraryName ) ) otherlv_3= ':' )? ( (lv_typeName_4_0= RULE_ID ) ) otherlv_5= '---' (otherlv_6= 'labels' ( (lv_labels_7_0= ruleTranslation ) ) (otherlv_8= ',' ( (lv_labels_9_0= ruleTranslation ) ) )* )? (otherlv_10= 'toolTips' ( (lv_toolTips_11_0= ruleTranslation ) ) (otherlv_12= ',' ( (lv_toolTips_13_0= ruleTranslation ) ) )* )? ( (lv_properties_14_0= ruleProperty ) )* (otherlv_15= '{' ( ( (lv_events_16_0= ruleEvent ) ) ( (lv_events_17_0= ruleEvent ) )* )? ( ( (lv_snippets_18_0= ruleSnippet ) ) ( (lv_snippets_19_0= ruleSnippet ) )* )? ( ( (lv_contents_20_0= ruleWidget ) ) ( (lv_contents_21_0= ruleWidget ) )* )? otherlv_22= '}' )?
            {
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:152:2: ()
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:153:5: 
            {

                    current = forceCreateModelElement(
                        grammarAccess.getWidgetAccess().getWidgetAction_0(),
                        current);
                

            }

            otherlv_1=(Token)match(input,13,FollowSets000.FOLLOW_13_in_ruleWidget277); 

                	newLeafNode(otherlv_1, grammarAccess.getWidgetAccess().getHyphenMinusHyphenMinusHyphenMinusKeyword_1());
                
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:162:1: ( ( (lv_libraryName_2_0= ruleLibraryName ) ) otherlv_3= ':' )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==RULE_ID) ) {
                int LA1_1 = input.LA(2);

                if ( (LA1_1==14) ) {
                    alt1=1;
                }
            }
            switch (alt1) {
                case 1 :
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:162:2: ( (lv_libraryName_2_0= ruleLibraryName ) ) otherlv_3= ':'
                    {
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:162:2: ( (lv_libraryName_2_0= ruleLibraryName ) )
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:163:1: (lv_libraryName_2_0= ruleLibraryName )
                    {
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:163:1: (lv_libraryName_2_0= ruleLibraryName )
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:164:3: lv_libraryName_2_0= ruleLibraryName
                    {
                     
                    	        newCompositeNode(grammarAccess.getWidgetAccess().getLibraryNameLibraryNameParserRuleCall_2_0_0()); 
                    	    
                    pushFollow(FollowSets000.FOLLOW_ruleLibraryName_in_ruleWidget299);
                    lv_libraryName_2_0=ruleLibraryName();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getWidgetRule());
                    	        }
                           		set(
                           			current, 
                           			"libraryName",
                            		lv_libraryName_2_0, 
                            		"LibraryName");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    otherlv_3=(Token)match(input,14,FollowSets000.FOLLOW_14_in_ruleWidget311); 

                        	newLeafNode(otherlv_3, grammarAccess.getWidgetAccess().getColonKeyword_2_1());
                        

                    }
                    break;

            }

            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:184:3: ( (lv_typeName_4_0= RULE_ID ) )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:185:1: (lv_typeName_4_0= RULE_ID )
            {
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:185:1: (lv_typeName_4_0= RULE_ID )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:186:3: lv_typeName_4_0= RULE_ID
            {
            lv_typeName_4_0=(Token)match(input,RULE_ID,FollowSets000.FOLLOW_RULE_ID_in_ruleWidget330); 

            			newLeafNode(lv_typeName_4_0, grammarAccess.getWidgetAccess().getTypeNameIDTerminalRuleCall_3_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getWidgetRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"typeName",
                    		lv_typeName_4_0, 
                    		"ID");
            	    

            }


            }

            otherlv_5=(Token)match(input,13,FollowSets000.FOLLOW_13_in_ruleWidget347); 

                	newLeafNode(otherlv_5, grammarAccess.getWidgetAccess().getHyphenMinusHyphenMinusHyphenMinusKeyword_4());
                
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:206:1: (otherlv_6= 'labels' ( (lv_labels_7_0= ruleTranslation ) ) (otherlv_8= ',' ( (lv_labels_9_0= ruleTranslation ) ) )* )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==15) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:206:3: otherlv_6= 'labels' ( (lv_labels_7_0= ruleTranslation ) ) (otherlv_8= ',' ( (lv_labels_9_0= ruleTranslation ) ) )*
                    {
                    otherlv_6=(Token)match(input,15,FollowSets000.FOLLOW_15_in_ruleWidget360); 

                        	newLeafNode(otherlv_6, grammarAccess.getWidgetAccess().getLabelsKeyword_5_0());
                        
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:210:1: ( (lv_labels_7_0= ruleTranslation ) )
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:211:1: (lv_labels_7_0= ruleTranslation )
                    {
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:211:1: (lv_labels_7_0= ruleTranslation )
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:212:3: lv_labels_7_0= ruleTranslation
                    {
                     
                    	        newCompositeNode(grammarAccess.getWidgetAccess().getLabelsTranslationParserRuleCall_5_1_0()); 
                    	    
                    pushFollow(FollowSets000.FOLLOW_ruleTranslation_in_ruleWidget381);
                    lv_labels_7_0=ruleTranslation();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getWidgetRule());
                    	        }
                           		add(
                           			current, 
                           			"labels",
                            		lv_labels_7_0, 
                            		"Translation");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:228:2: (otherlv_8= ',' ( (lv_labels_9_0= ruleTranslation ) ) )*
                    loop2:
                    do {
                        int alt2=2;
                        int LA2_0 = input.LA(1);

                        if ( (LA2_0==16) ) {
                            alt2=1;
                        }


                        switch (alt2) {
                    	case 1 :
                    	    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:228:4: otherlv_8= ',' ( (lv_labels_9_0= ruleTranslation ) )
                    	    {
                    	    otherlv_8=(Token)match(input,16,FollowSets000.FOLLOW_16_in_ruleWidget394); 

                    	        	newLeafNode(otherlv_8, grammarAccess.getWidgetAccess().getCommaKeyword_5_2_0());
                    	        
                    	    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:232:1: ( (lv_labels_9_0= ruleTranslation ) )
                    	    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:233:1: (lv_labels_9_0= ruleTranslation )
                    	    {
                    	    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:233:1: (lv_labels_9_0= ruleTranslation )
                    	    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:234:3: lv_labels_9_0= ruleTranslation
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getWidgetAccess().getLabelsTranslationParserRuleCall_5_2_1_0()); 
                    	    	    
                    	    pushFollow(FollowSets000.FOLLOW_ruleTranslation_in_ruleWidget415);
                    	    lv_labels_9_0=ruleTranslation();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getWidgetRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"labels",
                    	            		lv_labels_9_0, 
                    	            		"Translation");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop2;
                        }
                    } while (true);


                    }
                    break;

            }

            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:250:6: (otherlv_10= 'toolTips' ( (lv_toolTips_11_0= ruleTranslation ) ) (otherlv_12= ',' ( (lv_toolTips_13_0= ruleTranslation ) ) )* )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==17) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:250:8: otherlv_10= 'toolTips' ( (lv_toolTips_11_0= ruleTranslation ) ) (otherlv_12= ',' ( (lv_toolTips_13_0= ruleTranslation ) ) )*
                    {
                    otherlv_10=(Token)match(input,17,FollowSets000.FOLLOW_17_in_ruleWidget432); 

                        	newLeafNode(otherlv_10, grammarAccess.getWidgetAccess().getToolTipsKeyword_6_0());
                        
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:254:1: ( (lv_toolTips_11_0= ruleTranslation ) )
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:255:1: (lv_toolTips_11_0= ruleTranslation )
                    {
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:255:1: (lv_toolTips_11_0= ruleTranslation )
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:256:3: lv_toolTips_11_0= ruleTranslation
                    {
                     
                    	        newCompositeNode(grammarAccess.getWidgetAccess().getToolTipsTranslationParserRuleCall_6_1_0()); 
                    	    
                    pushFollow(FollowSets000.FOLLOW_ruleTranslation_in_ruleWidget453);
                    lv_toolTips_11_0=ruleTranslation();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getWidgetRule());
                    	        }
                           		add(
                           			current, 
                           			"toolTips",
                            		lv_toolTips_11_0, 
                            		"Translation");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:272:2: (otherlv_12= ',' ( (lv_toolTips_13_0= ruleTranslation ) ) )*
                    loop4:
                    do {
                        int alt4=2;
                        int LA4_0 = input.LA(1);

                        if ( (LA4_0==16) ) {
                            alt4=1;
                        }


                        switch (alt4) {
                    	case 1 :
                    	    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:272:4: otherlv_12= ',' ( (lv_toolTips_13_0= ruleTranslation ) )
                    	    {
                    	    otherlv_12=(Token)match(input,16,FollowSets000.FOLLOW_16_in_ruleWidget466); 

                    	        	newLeafNode(otherlv_12, grammarAccess.getWidgetAccess().getCommaKeyword_6_2_0());
                    	        
                    	    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:276:1: ( (lv_toolTips_13_0= ruleTranslation ) )
                    	    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:277:1: (lv_toolTips_13_0= ruleTranslation )
                    	    {
                    	    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:277:1: (lv_toolTips_13_0= ruleTranslation )
                    	    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:278:3: lv_toolTips_13_0= ruleTranslation
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getWidgetAccess().getToolTipsTranslationParserRuleCall_6_2_1_0()); 
                    	    	    
                    	    pushFollow(FollowSets000.FOLLOW_ruleTranslation_in_ruleWidget487);
                    	    lv_toolTips_13_0=ruleTranslation();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getWidgetRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"toolTips",
                    	            		lv_toolTips_13_0, 
                    	            		"Translation");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop4;
                        }
                    } while (true);


                    }
                    break;

            }

            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:294:6: ( (lv_properties_14_0= ruleProperty ) )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==RULE_ID) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:295:1: (lv_properties_14_0= ruleProperty )
            	    {
            	    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:295:1: (lv_properties_14_0= ruleProperty )
            	    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:296:3: lv_properties_14_0= ruleProperty
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getWidgetAccess().getPropertiesPropertyParserRuleCall_7_0()); 
            	    	    
            	    pushFollow(FollowSets000.FOLLOW_ruleProperty_in_ruleWidget512);
            	    lv_properties_14_0=ruleProperty();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getWidgetRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"properties",
            	            		lv_properties_14_0, 
            	            		"Property");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);

            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:312:3: (otherlv_15= '{' ( ( (lv_events_16_0= ruleEvent ) ) ( (lv_events_17_0= ruleEvent ) )* )? ( ( (lv_snippets_18_0= ruleSnippet ) ) ( (lv_snippets_19_0= ruleSnippet ) )* )? ( ( (lv_contents_20_0= ruleWidget ) ) ( (lv_contents_21_0= ruleWidget ) )* )? otherlv_22= '}' )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==18) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:312:5: otherlv_15= '{' ( ( (lv_events_16_0= ruleEvent ) ) ( (lv_events_17_0= ruleEvent ) )* )? ( ( (lv_snippets_18_0= ruleSnippet ) ) ( (lv_snippets_19_0= ruleSnippet ) )* )? ( ( (lv_contents_20_0= ruleWidget ) ) ( (lv_contents_21_0= ruleWidget ) )* )? otherlv_22= '}'
                    {
                    otherlv_15=(Token)match(input,18,FollowSets000.FOLLOW_18_in_ruleWidget526); 

                        	newLeafNode(otherlv_15, grammarAccess.getWidgetAccess().getLeftCurlyBracketKeyword_8_0());
                        
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:316:1: ( ( (lv_events_16_0= ruleEvent ) ) ( (lv_events_17_0= ruleEvent ) )* )?
                    int alt8=2;
                    int LA8_0 = input.LA(1);

                    if ( (LA8_0==21) ) {
                        alt8=1;
                    }
                    switch (alt8) {
                        case 1 :
                            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:316:2: ( (lv_events_16_0= ruleEvent ) ) ( (lv_events_17_0= ruleEvent ) )*
                            {
                            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:316:2: ( (lv_events_16_0= ruleEvent ) )
                            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:317:1: (lv_events_16_0= ruleEvent )
                            {
                            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:317:1: (lv_events_16_0= ruleEvent )
                            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:318:3: lv_events_16_0= ruleEvent
                            {
                             
                            	        newCompositeNode(grammarAccess.getWidgetAccess().getEventsEventParserRuleCall_8_1_0_0()); 
                            	    
                            pushFollow(FollowSets000.FOLLOW_ruleEvent_in_ruleWidget548);
                            lv_events_16_0=ruleEvent();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getWidgetRule());
                            	        }
                                   		add(
                                   			current, 
                                   			"events",
                                    		lv_events_16_0, 
                                    		"Event");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }

                            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:334:2: ( (lv_events_17_0= ruleEvent ) )*
                            loop7:
                            do {
                                int alt7=2;
                                int LA7_0 = input.LA(1);

                                if ( (LA7_0==21) ) {
                                    alt7=1;
                                }


                                switch (alt7) {
                            	case 1 :
                            	    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:335:1: (lv_events_17_0= ruleEvent )
                            	    {
                            	    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:335:1: (lv_events_17_0= ruleEvent )
                            	    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:336:3: lv_events_17_0= ruleEvent
                            	    {
                            	     
                            	    	        newCompositeNode(grammarAccess.getWidgetAccess().getEventsEventParserRuleCall_8_1_1_0()); 
                            	    	    
                            	    pushFollow(FollowSets000.FOLLOW_ruleEvent_in_ruleWidget569);
                            	    lv_events_17_0=ruleEvent();

                            	    state._fsp--;


                            	    	        if (current==null) {
                            	    	            current = createModelElementForParent(grammarAccess.getWidgetRule());
                            	    	        }
                            	           		add(
                            	           			current, 
                            	           			"events",
                            	            		lv_events_17_0, 
                            	            		"Event");
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

                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:352:5: ( ( (lv_snippets_18_0= ruleSnippet ) ) ( (lv_snippets_19_0= ruleSnippet ) )* )?
                    int alt10=2;
                    int LA10_0 = input.LA(1);

                    if ( (LA10_0==26) ) {
                        alt10=1;
                    }
                    switch (alt10) {
                        case 1 :
                            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:352:6: ( (lv_snippets_18_0= ruleSnippet ) ) ( (lv_snippets_19_0= ruleSnippet ) )*
                            {
                            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:352:6: ( (lv_snippets_18_0= ruleSnippet ) )
                            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:353:1: (lv_snippets_18_0= ruleSnippet )
                            {
                            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:353:1: (lv_snippets_18_0= ruleSnippet )
                            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:354:3: lv_snippets_18_0= ruleSnippet
                            {
                             
                            	        newCompositeNode(grammarAccess.getWidgetAccess().getSnippetsSnippetParserRuleCall_8_2_0_0()); 
                            	    
                            pushFollow(FollowSets000.FOLLOW_ruleSnippet_in_ruleWidget594);
                            lv_snippets_18_0=ruleSnippet();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getWidgetRule());
                            	        }
                                   		add(
                                   			current, 
                                   			"snippets",
                                    		lv_snippets_18_0, 
                                    		"Snippet");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }

                            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:370:2: ( (lv_snippets_19_0= ruleSnippet ) )*
                            loop9:
                            do {
                                int alt9=2;
                                int LA9_0 = input.LA(1);

                                if ( (LA9_0==26) ) {
                                    alt9=1;
                                }


                                switch (alt9) {
                            	case 1 :
                            	    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:371:1: (lv_snippets_19_0= ruleSnippet )
                            	    {
                            	    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:371:1: (lv_snippets_19_0= ruleSnippet )
                            	    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:372:3: lv_snippets_19_0= ruleSnippet
                            	    {
                            	     
                            	    	        newCompositeNode(grammarAccess.getWidgetAccess().getSnippetsSnippetParserRuleCall_8_2_1_0()); 
                            	    	    
                            	    pushFollow(FollowSets000.FOLLOW_ruleSnippet_in_ruleWidget615);
                            	    lv_snippets_19_0=ruleSnippet();

                            	    state._fsp--;


                            	    	        if (current==null) {
                            	    	            current = createModelElementForParent(grammarAccess.getWidgetRule());
                            	    	        }
                            	           		add(
                            	           			current, 
                            	           			"snippets",
                            	            		lv_snippets_19_0, 
                            	            		"Snippet");
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

                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:388:5: ( ( (lv_contents_20_0= ruleWidget ) ) ( (lv_contents_21_0= ruleWidget ) )* )?
                    int alt12=2;
                    int LA12_0 = input.LA(1);

                    if ( (LA12_0==13) ) {
                        alt12=1;
                    }
                    switch (alt12) {
                        case 1 :
                            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:388:6: ( (lv_contents_20_0= ruleWidget ) ) ( (lv_contents_21_0= ruleWidget ) )*
                            {
                            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:388:6: ( (lv_contents_20_0= ruleWidget ) )
                            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:389:1: (lv_contents_20_0= ruleWidget )
                            {
                            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:389:1: (lv_contents_20_0= ruleWidget )
                            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:390:3: lv_contents_20_0= ruleWidget
                            {
                             
                            	        newCompositeNode(grammarAccess.getWidgetAccess().getContentsWidgetParserRuleCall_8_3_0_0()); 
                            	    
                            pushFollow(FollowSets000.FOLLOW_ruleWidget_in_ruleWidget640);
                            lv_contents_20_0=ruleWidget();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getWidgetRule());
                            	        }
                                   		add(
                                   			current, 
                                   			"contents",
                                    		lv_contents_20_0, 
                                    		"Widget");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }

                            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:406:2: ( (lv_contents_21_0= ruleWidget ) )*
                            loop11:
                            do {
                                int alt11=2;
                                int LA11_0 = input.LA(1);

                                if ( (LA11_0==13) ) {
                                    alt11=1;
                                }


                                switch (alt11) {
                            	case 1 :
                            	    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:407:1: (lv_contents_21_0= ruleWidget )
                            	    {
                            	    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:407:1: (lv_contents_21_0= ruleWidget )
                            	    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:408:3: lv_contents_21_0= ruleWidget
                            	    {
                            	     
                            	    	        newCompositeNode(grammarAccess.getWidgetAccess().getContentsWidgetParserRuleCall_8_3_1_0()); 
                            	    	    
                            	    pushFollow(FollowSets000.FOLLOW_ruleWidget_in_ruleWidget661);
                            	    lv_contents_21_0=ruleWidget();

                            	    state._fsp--;


                            	    	        if (current==null) {
                            	    	            current = createModelElementForParent(grammarAccess.getWidgetRule());
                            	    	        }
                            	           		add(
                            	           			current, 
                            	           			"contents",
                            	            		lv_contents_21_0, 
                            	            		"Widget");
                            	    	        afterParserOrEnumRuleCall();
                            	    	    

                            	    }


                            	    }
                            	    break;

                            	default :
                            	    break loop11;
                                }
                            } while (true);


                            }
                            break;

                    }

                    otherlv_22=(Token)match(input,19,FollowSets000.FOLLOW_19_in_ruleWidget676); 

                        	newLeafNode(otherlv_22, grammarAccess.getWidgetAccess().getRightCurlyBracketKeyword_8_4());
                        

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
    // $ANTLR end "ruleWidget"


    // $ANTLR start "entryRuleProperty"
    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:438:1: entryRuleProperty returns [EObject current=null] : iv_ruleProperty= ruleProperty EOF ;
    public final EObject entryRuleProperty() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleProperty = null;


        try {
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:439:2: (iv_ruleProperty= ruleProperty EOF )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:440:2: iv_ruleProperty= ruleProperty EOF
            {
             newCompositeNode(grammarAccess.getPropertyRule()); 
            pushFollow(FollowSets000.FOLLOW_ruleProperty_in_entryRuleProperty716);
            iv_ruleProperty=ruleProperty();

            state._fsp--;

             current =iv_ruleProperty; 
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleProperty726); 

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
    // $ANTLR end "entryRuleProperty"


    // $ANTLR start "ruleProperty"
    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:447:1: ruleProperty returns [EObject current=null] : ( () ( ( (lv_libraryName_1_0= RULE_ID ) ) otherlv_2= ':' )? ( (lv_typeName_3_0= RULE_ID ) ) otherlv_4= '=' ( (lv_value_5_0= ruleString_Value ) ) ( (lv_readonly_6_0= '!' ) )? (otherlv_7= '!' ( (otherlv_8= RULE_URI ) ) )? ) ;
    public final EObject ruleProperty() throws RecognitionException {
        EObject current = null;

        Token lv_libraryName_1_0=null;
        Token otherlv_2=null;
        Token lv_typeName_3_0=null;
        Token otherlv_4=null;
        Token lv_readonly_6_0=null;
        Token otherlv_7=null;
        Token otherlv_8=null;
        AntlrDatatypeRuleToken lv_value_5_0 = null;


         enterRule(); 
            
        try {
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:450:28: ( ( () ( ( (lv_libraryName_1_0= RULE_ID ) ) otherlv_2= ':' )? ( (lv_typeName_3_0= RULE_ID ) ) otherlv_4= '=' ( (lv_value_5_0= ruleString_Value ) ) ( (lv_readonly_6_0= '!' ) )? (otherlv_7= '!' ( (otherlv_8= RULE_URI ) ) )? ) )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:451:1: ( () ( ( (lv_libraryName_1_0= RULE_ID ) ) otherlv_2= ':' )? ( (lv_typeName_3_0= RULE_ID ) ) otherlv_4= '=' ( (lv_value_5_0= ruleString_Value ) ) ( (lv_readonly_6_0= '!' ) )? (otherlv_7= '!' ( (otherlv_8= RULE_URI ) ) )? )
            {
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:451:1: ( () ( ( (lv_libraryName_1_0= RULE_ID ) ) otherlv_2= ':' )? ( (lv_typeName_3_0= RULE_ID ) ) otherlv_4= '=' ( (lv_value_5_0= ruleString_Value ) ) ( (lv_readonly_6_0= '!' ) )? (otherlv_7= '!' ( (otherlv_8= RULE_URI ) ) )? )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:451:2: () ( ( (lv_libraryName_1_0= RULE_ID ) ) otherlv_2= ':' )? ( (lv_typeName_3_0= RULE_ID ) ) otherlv_4= '=' ( (lv_value_5_0= ruleString_Value ) ) ( (lv_readonly_6_0= '!' ) )? (otherlv_7= '!' ( (otherlv_8= RULE_URI ) ) )?
            {
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:451:2: ()
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:452:5: 
            {

                    current = forceCreateModelElement(
                        grammarAccess.getPropertyAccess().getPropertyAction_0(),
                        current);
                

            }

            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:457:2: ( ( (lv_libraryName_1_0= RULE_ID ) ) otherlv_2= ':' )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==RULE_ID) ) {
                int LA14_1 = input.LA(2);

                if ( (LA14_1==14) ) {
                    alt14=1;
                }
            }
            switch (alt14) {
                case 1 :
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:457:3: ( (lv_libraryName_1_0= RULE_ID ) ) otherlv_2= ':'
                    {
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:457:3: ( (lv_libraryName_1_0= RULE_ID ) )
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:458:1: (lv_libraryName_1_0= RULE_ID )
                    {
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:458:1: (lv_libraryName_1_0= RULE_ID )
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:459:3: lv_libraryName_1_0= RULE_ID
                    {
                    lv_libraryName_1_0=(Token)match(input,RULE_ID,FollowSets000.FOLLOW_RULE_ID_in_ruleProperty778); 

                    			newLeafNode(lv_libraryName_1_0, grammarAccess.getPropertyAccess().getLibraryNameIDTerminalRuleCall_1_0_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getPropertyRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"libraryName",
                            		lv_libraryName_1_0, 
                            		"ID");
                    	    

                    }


                    }

                    otherlv_2=(Token)match(input,14,FollowSets000.FOLLOW_14_in_ruleProperty795); 

                        	newLeafNode(otherlv_2, grammarAccess.getPropertyAccess().getColonKeyword_1_1());
                        

                    }
                    break;

            }

            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:479:3: ( (lv_typeName_3_0= RULE_ID ) )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:480:1: (lv_typeName_3_0= RULE_ID )
            {
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:480:1: (lv_typeName_3_0= RULE_ID )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:481:3: lv_typeName_3_0= RULE_ID
            {
            lv_typeName_3_0=(Token)match(input,RULE_ID,FollowSets000.FOLLOW_RULE_ID_in_ruleProperty814); 

            			newLeafNode(lv_typeName_3_0, grammarAccess.getPropertyAccess().getTypeNameIDTerminalRuleCall_2_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getPropertyRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"typeName",
                    		lv_typeName_3_0, 
                    		"ID");
            	    

            }


            }

            otherlv_4=(Token)match(input,12,FollowSets000.FOLLOW_12_in_ruleProperty831); 

                	newLeafNode(otherlv_4, grammarAccess.getPropertyAccess().getEqualsSignKeyword_3());
                
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:501:1: ( (lv_value_5_0= ruleString_Value ) )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:502:1: (lv_value_5_0= ruleString_Value )
            {
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:502:1: (lv_value_5_0= ruleString_Value )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:503:3: lv_value_5_0= ruleString_Value
            {
             
            	        newCompositeNode(grammarAccess.getPropertyAccess().getValueString_ValueParserRuleCall_4_0()); 
            	    
            pushFollow(FollowSets000.FOLLOW_ruleString_Value_in_ruleProperty852);
            lv_value_5_0=ruleString_Value();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getPropertyRule());
            	        }
                   		set(
                   			current, 
                   			"value",
                    		lv_value_5_0, 
                    		"String_Value");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:519:2: ( (lv_readonly_6_0= '!' ) )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==20) ) {
                int LA15_1 = input.LA(2);

                if ( (LA15_1==EOF||LA15_1==RULE_ID||LA15_1==13||(LA15_1>=18 && LA15_1<=21)||(LA15_1>=25 && LA15_1<=26)) ) {
                    alt15=1;
                }
            }
            switch (alt15) {
                case 1 :
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:520:1: (lv_readonly_6_0= '!' )
                    {
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:520:1: (lv_readonly_6_0= '!' )
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:521:3: lv_readonly_6_0= '!'
                    {
                    lv_readonly_6_0=(Token)match(input,20,FollowSets000.FOLLOW_20_in_ruleProperty870); 

                            newLeafNode(lv_readonly_6_0, grammarAccess.getPropertyAccess().getReadonlyExclamationMarkKeyword_5_0());
                        

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getPropertyRule());
                    	        }
                           		setWithLastConsumed(current, "readonly", true, "!");
                    	    

                    }


                    }
                    break;

            }

            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:534:3: (otherlv_7= '!' ( (otherlv_8= RULE_URI ) ) )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==20) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:534:5: otherlv_7= '!' ( (otherlv_8= RULE_URI ) )
                    {
                    otherlv_7=(Token)match(input,20,FollowSets000.FOLLOW_20_in_ruleProperty897); 

                        	newLeafNode(otherlv_7, grammarAccess.getPropertyAccess().getExclamationMarkKeyword_6_0());
                        
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:538:1: ( (otherlv_8= RULE_URI ) )
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:539:1: (otherlv_8= RULE_URI )
                    {
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:539:1: (otherlv_8= RULE_URI )
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:540:3: otherlv_8= RULE_URI
                    {

                    			if (current==null) {
                    	            current = createModelElement(grammarAccess.getPropertyRule());
                    	        }
                            
                    otherlv_8=(Token)match(input,RULE_URI,FollowSets000.FOLLOW_RULE_URI_in_ruleProperty917); 

                    		newLeafNode(otherlv_8, grammarAccess.getPropertyAccess().getModelModelCrossReference_6_1_0()); 
                    	

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
    // $ANTLR end "ruleProperty"


    // $ANTLR start "entryRuleEvent"
    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:559:1: entryRuleEvent returns [EObject current=null] : iv_ruleEvent= ruleEvent EOF ;
    public final EObject entryRuleEvent() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEvent = null;


        try {
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:560:2: (iv_ruleEvent= ruleEvent EOF )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:561:2: iv_ruleEvent= ruleEvent EOF
            {
             newCompositeNode(grammarAccess.getEventRule()); 
            pushFollow(FollowSets000.FOLLOW_ruleEvent_in_entryRuleEvent955);
            iv_ruleEvent=ruleEvent();

            state._fsp--;

             current =iv_ruleEvent; 
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleEvent965); 

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
    // $ANTLR end "entryRuleEvent"


    // $ANTLR start "ruleEvent"
    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:568:1: ruleEvent returns [EObject current=null] : (otherlv_0= 'Event' ( (lv_eventName_1_0= ruleString_Value ) ) otherlv_2= '(' ( (lv_functionName_3_0= ruleString_Value ) ) otherlv_4= ')' ( (lv_nature_5_0= ruleEventNature ) )? (otherlv_6= '//' ( (lv_description_7_0= ruleString_Value ) ) )? ( (lv_properties_8_0= ruleProperty ) )* (otherlv_9= 'messages' ( (lv_messages_10_0= ruleTranslation ) ) (otherlv_11= ',' ( (lv_messages_12_0= ruleTranslation ) ) )* )? (otherlv_13= '{' ( ( (lv_parameters_14_0= ruleParameter ) ) ( (lv_parameters_15_0= ruleParameter ) )* )? ( ( (lv_snippets_16_0= ruleSnippet ) ) ( (lv_snippets_17_0= ruleSnippet ) )* )? otherlv_18= '}' )? ) ;
    public final EObject ruleEvent() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_9=null;
        Token otherlv_11=null;
        Token otherlv_13=null;
        Token otherlv_18=null;
        AntlrDatatypeRuleToken lv_eventName_1_0 = null;

        AntlrDatatypeRuleToken lv_functionName_3_0 = null;

        Enumerator lv_nature_5_0 = null;

        AntlrDatatypeRuleToken lv_description_7_0 = null;

        EObject lv_properties_8_0 = null;

        EObject lv_messages_10_0 = null;

        EObject lv_messages_12_0 = null;

        EObject lv_parameters_14_0 = null;

        EObject lv_parameters_15_0 = null;

        EObject lv_snippets_16_0 = null;

        EObject lv_snippets_17_0 = null;


         enterRule(); 
            
        try {
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:571:28: ( (otherlv_0= 'Event' ( (lv_eventName_1_0= ruleString_Value ) ) otherlv_2= '(' ( (lv_functionName_3_0= ruleString_Value ) ) otherlv_4= ')' ( (lv_nature_5_0= ruleEventNature ) )? (otherlv_6= '//' ( (lv_description_7_0= ruleString_Value ) ) )? ( (lv_properties_8_0= ruleProperty ) )* (otherlv_9= 'messages' ( (lv_messages_10_0= ruleTranslation ) ) (otherlv_11= ',' ( (lv_messages_12_0= ruleTranslation ) ) )* )? (otherlv_13= '{' ( ( (lv_parameters_14_0= ruleParameter ) ) ( (lv_parameters_15_0= ruleParameter ) )* )? ( ( (lv_snippets_16_0= ruleSnippet ) ) ( (lv_snippets_17_0= ruleSnippet ) )* )? otherlv_18= '}' )? ) )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:572:1: (otherlv_0= 'Event' ( (lv_eventName_1_0= ruleString_Value ) ) otherlv_2= '(' ( (lv_functionName_3_0= ruleString_Value ) ) otherlv_4= ')' ( (lv_nature_5_0= ruleEventNature ) )? (otherlv_6= '//' ( (lv_description_7_0= ruleString_Value ) ) )? ( (lv_properties_8_0= ruleProperty ) )* (otherlv_9= 'messages' ( (lv_messages_10_0= ruleTranslation ) ) (otherlv_11= ',' ( (lv_messages_12_0= ruleTranslation ) ) )* )? (otherlv_13= '{' ( ( (lv_parameters_14_0= ruleParameter ) ) ( (lv_parameters_15_0= ruleParameter ) )* )? ( ( (lv_snippets_16_0= ruleSnippet ) ) ( (lv_snippets_17_0= ruleSnippet ) )* )? otherlv_18= '}' )? )
            {
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:572:1: (otherlv_0= 'Event' ( (lv_eventName_1_0= ruleString_Value ) ) otherlv_2= '(' ( (lv_functionName_3_0= ruleString_Value ) ) otherlv_4= ')' ( (lv_nature_5_0= ruleEventNature ) )? (otherlv_6= '//' ( (lv_description_7_0= ruleString_Value ) ) )? ( (lv_properties_8_0= ruleProperty ) )* (otherlv_9= 'messages' ( (lv_messages_10_0= ruleTranslation ) ) (otherlv_11= ',' ( (lv_messages_12_0= ruleTranslation ) ) )* )? (otherlv_13= '{' ( ( (lv_parameters_14_0= ruleParameter ) ) ( (lv_parameters_15_0= ruleParameter ) )* )? ( ( (lv_snippets_16_0= ruleSnippet ) ) ( (lv_snippets_17_0= ruleSnippet ) )* )? otherlv_18= '}' )? )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:572:3: otherlv_0= 'Event' ( (lv_eventName_1_0= ruleString_Value ) ) otherlv_2= '(' ( (lv_functionName_3_0= ruleString_Value ) ) otherlv_4= ')' ( (lv_nature_5_0= ruleEventNature ) )? (otherlv_6= '//' ( (lv_description_7_0= ruleString_Value ) ) )? ( (lv_properties_8_0= ruleProperty ) )* (otherlv_9= 'messages' ( (lv_messages_10_0= ruleTranslation ) ) (otherlv_11= ',' ( (lv_messages_12_0= ruleTranslation ) ) )* )? (otherlv_13= '{' ( ( (lv_parameters_14_0= ruleParameter ) ) ( (lv_parameters_15_0= ruleParameter ) )* )? ( ( (lv_snippets_16_0= ruleSnippet ) ) ( (lv_snippets_17_0= ruleSnippet ) )* )? otherlv_18= '}' )?
            {
            otherlv_0=(Token)match(input,21,FollowSets000.FOLLOW_21_in_ruleEvent1002); 

                	newLeafNode(otherlv_0, grammarAccess.getEventAccess().getEventKeyword_0());
                
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:576:1: ( (lv_eventName_1_0= ruleString_Value ) )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:577:1: (lv_eventName_1_0= ruleString_Value )
            {
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:577:1: (lv_eventName_1_0= ruleString_Value )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:578:3: lv_eventName_1_0= ruleString_Value
            {
             
            	        newCompositeNode(grammarAccess.getEventAccess().getEventNameString_ValueParserRuleCall_1_0()); 
            	    
            pushFollow(FollowSets000.FOLLOW_ruleString_Value_in_ruleEvent1023);
            lv_eventName_1_0=ruleString_Value();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getEventRule());
            	        }
                   		set(
                   			current, 
                   			"eventName",
                    		lv_eventName_1_0, 
                    		"String_Value");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_2=(Token)match(input,22,FollowSets000.FOLLOW_22_in_ruleEvent1035); 

                	newLeafNode(otherlv_2, grammarAccess.getEventAccess().getLeftParenthesisKeyword_2());
                
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:598:1: ( (lv_functionName_3_0= ruleString_Value ) )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:599:1: (lv_functionName_3_0= ruleString_Value )
            {
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:599:1: (lv_functionName_3_0= ruleString_Value )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:600:3: lv_functionName_3_0= ruleString_Value
            {
             
            	        newCompositeNode(grammarAccess.getEventAccess().getFunctionNameString_ValueParserRuleCall_3_0()); 
            	    
            pushFollow(FollowSets000.FOLLOW_ruleString_Value_in_ruleEvent1056);
            lv_functionName_3_0=ruleString_Value();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getEventRule());
            	        }
                   		set(
                   			current, 
                   			"functionName",
                    		lv_functionName_3_0, 
                    		"String_Value");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_4=(Token)match(input,23,FollowSets000.FOLLOW_23_in_ruleEvent1068); 

                	newLeafNode(otherlv_4, grammarAccess.getEventAccess().getRightParenthesisKeyword_4());
                
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:620:1: ( (lv_nature_5_0= ruleEventNature ) )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( ((LA17_0>=28 && LA17_0<=29)) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:621:1: (lv_nature_5_0= ruleEventNature )
                    {
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:621:1: (lv_nature_5_0= ruleEventNature )
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:622:3: lv_nature_5_0= ruleEventNature
                    {
                     
                    	        newCompositeNode(grammarAccess.getEventAccess().getNatureEventNatureEnumRuleCall_5_0()); 
                    	    
                    pushFollow(FollowSets000.FOLLOW_ruleEventNature_in_ruleEvent1089);
                    lv_nature_5_0=ruleEventNature();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getEventRule());
                    	        }
                           		set(
                           			current, 
                           			"nature",
                            		lv_nature_5_0, 
                            		"EventNature");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }
                    break;

            }

            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:638:3: (otherlv_6= '//' ( (lv_description_7_0= ruleString_Value ) ) )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==24) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:638:5: otherlv_6= '//' ( (lv_description_7_0= ruleString_Value ) )
                    {
                    otherlv_6=(Token)match(input,24,FollowSets000.FOLLOW_24_in_ruleEvent1103); 

                        	newLeafNode(otherlv_6, grammarAccess.getEventAccess().getSolidusSolidusKeyword_6_0());
                        
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:642:1: ( (lv_description_7_0= ruleString_Value ) )
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:643:1: (lv_description_7_0= ruleString_Value )
                    {
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:643:1: (lv_description_7_0= ruleString_Value )
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:644:3: lv_description_7_0= ruleString_Value
                    {
                     
                    	        newCompositeNode(grammarAccess.getEventAccess().getDescriptionString_ValueParserRuleCall_6_1_0()); 
                    	    
                    pushFollow(FollowSets000.FOLLOW_ruleString_Value_in_ruleEvent1124);
                    lv_description_7_0=ruleString_Value();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getEventRule());
                    	        }
                           		set(
                           			current, 
                           			"description",
                            		lv_description_7_0, 
                            		"String_Value");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }


                    }
                    break;

            }

            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:660:4: ( (lv_properties_8_0= ruleProperty ) )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( (LA19_0==RULE_ID) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:661:1: (lv_properties_8_0= ruleProperty )
            	    {
            	    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:661:1: (lv_properties_8_0= ruleProperty )
            	    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:662:3: lv_properties_8_0= ruleProperty
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getEventAccess().getPropertiesPropertyParserRuleCall_7_0()); 
            	    	    
            	    pushFollow(FollowSets000.FOLLOW_ruleProperty_in_ruleEvent1147);
            	    lv_properties_8_0=ruleProperty();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getEventRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"properties",
            	            		lv_properties_8_0, 
            	            		"Property");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop19;
                }
            } while (true);

            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:678:3: (otherlv_9= 'messages' ( (lv_messages_10_0= ruleTranslation ) ) (otherlv_11= ',' ( (lv_messages_12_0= ruleTranslation ) ) )* )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==25) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:678:5: otherlv_9= 'messages' ( (lv_messages_10_0= ruleTranslation ) ) (otherlv_11= ',' ( (lv_messages_12_0= ruleTranslation ) ) )*
                    {
                    otherlv_9=(Token)match(input,25,FollowSets000.FOLLOW_25_in_ruleEvent1161); 

                        	newLeafNode(otherlv_9, grammarAccess.getEventAccess().getMessagesKeyword_8_0());
                        
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:682:1: ( (lv_messages_10_0= ruleTranslation ) )
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:683:1: (lv_messages_10_0= ruleTranslation )
                    {
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:683:1: (lv_messages_10_0= ruleTranslation )
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:684:3: lv_messages_10_0= ruleTranslation
                    {
                     
                    	        newCompositeNode(grammarAccess.getEventAccess().getMessagesTranslationParserRuleCall_8_1_0()); 
                    	    
                    pushFollow(FollowSets000.FOLLOW_ruleTranslation_in_ruleEvent1182);
                    lv_messages_10_0=ruleTranslation();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getEventRule());
                    	        }
                           		add(
                           			current, 
                           			"messages",
                            		lv_messages_10_0, 
                            		"Translation");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:700:2: (otherlv_11= ',' ( (lv_messages_12_0= ruleTranslation ) ) )*
                    loop20:
                    do {
                        int alt20=2;
                        int LA20_0 = input.LA(1);

                        if ( (LA20_0==16) ) {
                            alt20=1;
                        }


                        switch (alt20) {
                    	case 1 :
                    	    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:700:4: otherlv_11= ',' ( (lv_messages_12_0= ruleTranslation ) )
                    	    {
                    	    otherlv_11=(Token)match(input,16,FollowSets000.FOLLOW_16_in_ruleEvent1195); 

                    	        	newLeafNode(otherlv_11, grammarAccess.getEventAccess().getCommaKeyword_8_2_0());
                    	        
                    	    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:704:1: ( (lv_messages_12_0= ruleTranslation ) )
                    	    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:705:1: (lv_messages_12_0= ruleTranslation )
                    	    {
                    	    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:705:1: (lv_messages_12_0= ruleTranslation )
                    	    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:706:3: lv_messages_12_0= ruleTranslation
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getEventAccess().getMessagesTranslationParserRuleCall_8_2_1_0()); 
                    	    	    
                    	    pushFollow(FollowSets000.FOLLOW_ruleTranslation_in_ruleEvent1216);
                    	    lv_messages_12_0=ruleTranslation();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getEventRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"messages",
                    	            		lv_messages_12_0, 
                    	            		"Translation");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop20;
                        }
                    } while (true);


                    }
                    break;

            }

            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:722:6: (otherlv_13= '{' ( ( (lv_parameters_14_0= ruleParameter ) ) ( (lv_parameters_15_0= ruleParameter ) )* )? ( ( (lv_snippets_16_0= ruleSnippet ) ) ( (lv_snippets_17_0= ruleSnippet ) )* )? otherlv_18= '}' )?
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==18) ) {
                alt26=1;
            }
            switch (alt26) {
                case 1 :
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:722:8: otherlv_13= '{' ( ( (lv_parameters_14_0= ruleParameter ) ) ( (lv_parameters_15_0= ruleParameter ) )* )? ( ( (lv_snippets_16_0= ruleSnippet ) ) ( (lv_snippets_17_0= ruleSnippet ) )* )? otherlv_18= '}'
                    {
                    otherlv_13=(Token)match(input,18,FollowSets000.FOLLOW_18_in_ruleEvent1233); 

                        	newLeafNode(otherlv_13, grammarAccess.getEventAccess().getLeftCurlyBracketKeyword_9_0());
                        
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:726:1: ( ( (lv_parameters_14_0= ruleParameter ) ) ( (lv_parameters_15_0= ruleParameter ) )* )?
                    int alt23=2;
                    int LA23_0 = input.LA(1);

                    if ( (LA23_0==RULE_ID) ) {
                        alt23=1;
                    }
                    switch (alt23) {
                        case 1 :
                            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:726:2: ( (lv_parameters_14_0= ruleParameter ) ) ( (lv_parameters_15_0= ruleParameter ) )*
                            {
                            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:726:2: ( (lv_parameters_14_0= ruleParameter ) )
                            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:727:1: (lv_parameters_14_0= ruleParameter )
                            {
                            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:727:1: (lv_parameters_14_0= ruleParameter )
                            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:728:3: lv_parameters_14_0= ruleParameter
                            {
                             
                            	        newCompositeNode(grammarAccess.getEventAccess().getParametersParameterParserRuleCall_9_1_0_0()); 
                            	    
                            pushFollow(FollowSets000.FOLLOW_ruleParameter_in_ruleEvent1255);
                            lv_parameters_14_0=ruleParameter();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getEventRule());
                            	        }
                                   		add(
                                   			current, 
                                   			"parameters",
                                    		lv_parameters_14_0, 
                                    		"Parameter");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }

                            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:744:2: ( (lv_parameters_15_0= ruleParameter ) )*
                            loop22:
                            do {
                                int alt22=2;
                                int LA22_0 = input.LA(1);

                                if ( (LA22_0==RULE_ID) ) {
                                    alt22=1;
                                }


                                switch (alt22) {
                            	case 1 :
                            	    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:745:1: (lv_parameters_15_0= ruleParameter )
                            	    {
                            	    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:745:1: (lv_parameters_15_0= ruleParameter )
                            	    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:746:3: lv_parameters_15_0= ruleParameter
                            	    {
                            	     
                            	    	        newCompositeNode(grammarAccess.getEventAccess().getParametersParameterParserRuleCall_9_1_1_0()); 
                            	    	    
                            	    pushFollow(FollowSets000.FOLLOW_ruleParameter_in_ruleEvent1276);
                            	    lv_parameters_15_0=ruleParameter();

                            	    state._fsp--;


                            	    	        if (current==null) {
                            	    	            current = createModelElementForParent(grammarAccess.getEventRule());
                            	    	        }
                            	           		add(
                            	           			current, 
                            	           			"parameters",
                            	            		lv_parameters_15_0, 
                            	            		"Parameter");
                            	    	        afterParserOrEnumRuleCall();
                            	    	    

                            	    }


                            	    }
                            	    break;

                            	default :
                            	    break loop22;
                                }
                            } while (true);


                            }
                            break;

                    }

                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:762:5: ( ( (lv_snippets_16_0= ruleSnippet ) ) ( (lv_snippets_17_0= ruleSnippet ) )* )?
                    int alt25=2;
                    int LA25_0 = input.LA(1);

                    if ( (LA25_0==26) ) {
                        alt25=1;
                    }
                    switch (alt25) {
                        case 1 :
                            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:762:6: ( (lv_snippets_16_0= ruleSnippet ) ) ( (lv_snippets_17_0= ruleSnippet ) )*
                            {
                            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:762:6: ( (lv_snippets_16_0= ruleSnippet ) )
                            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:763:1: (lv_snippets_16_0= ruleSnippet )
                            {
                            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:763:1: (lv_snippets_16_0= ruleSnippet )
                            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:764:3: lv_snippets_16_0= ruleSnippet
                            {
                             
                            	        newCompositeNode(grammarAccess.getEventAccess().getSnippetsSnippetParserRuleCall_9_2_0_0()); 
                            	    
                            pushFollow(FollowSets000.FOLLOW_ruleSnippet_in_ruleEvent1301);
                            lv_snippets_16_0=ruleSnippet();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getEventRule());
                            	        }
                                   		add(
                                   			current, 
                                   			"snippets",
                                    		lv_snippets_16_0, 
                                    		"Snippet");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }

                            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:780:2: ( (lv_snippets_17_0= ruleSnippet ) )*
                            loop24:
                            do {
                                int alt24=2;
                                int LA24_0 = input.LA(1);

                                if ( (LA24_0==26) ) {
                                    alt24=1;
                                }


                                switch (alt24) {
                            	case 1 :
                            	    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:781:1: (lv_snippets_17_0= ruleSnippet )
                            	    {
                            	    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:781:1: (lv_snippets_17_0= ruleSnippet )
                            	    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:782:3: lv_snippets_17_0= ruleSnippet
                            	    {
                            	     
                            	    	        newCompositeNode(grammarAccess.getEventAccess().getSnippetsSnippetParserRuleCall_9_2_1_0()); 
                            	    	    
                            	    pushFollow(FollowSets000.FOLLOW_ruleSnippet_in_ruleEvent1322);
                            	    lv_snippets_17_0=ruleSnippet();

                            	    state._fsp--;


                            	    	        if (current==null) {
                            	    	            current = createModelElementForParent(grammarAccess.getEventRule());
                            	    	        }
                            	           		add(
                            	           			current, 
                            	           			"snippets",
                            	            		lv_snippets_17_0, 
                            	            		"Snippet");
                            	    	        afterParserOrEnumRuleCall();
                            	    	    

                            	    }


                            	    }
                            	    break;

                            	default :
                            	    break loop24;
                                }
                            } while (true);


                            }
                            break;

                    }

                    otherlv_18=(Token)match(input,19,FollowSets000.FOLLOW_19_in_ruleEvent1337); 

                        	newLeafNode(otherlv_18, grammarAccess.getEventAccess().getRightCurlyBracketKeyword_9_3());
                        

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
    // $ANTLR end "ruleEvent"


    // $ANTLR start "entryRuleSnippet"
    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:810:1: entryRuleSnippet returns [EObject current=null] : iv_ruleSnippet= ruleSnippet EOF ;
    public final EObject entryRuleSnippet() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSnippet = null;


        try {
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:811:2: (iv_ruleSnippet= ruleSnippet EOF )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:812:2: iv_ruleSnippet= ruleSnippet EOF
            {
             newCompositeNode(grammarAccess.getSnippetRule()); 
            pushFollow(FollowSets000.FOLLOW_ruleSnippet_in_entryRuleSnippet1375);
            iv_ruleSnippet=ruleSnippet();

            state._fsp--;

             current =iv_ruleSnippet; 
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleSnippet1385); 

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
    // $ANTLR end "entryRuleSnippet"


    // $ANTLR start "ruleSnippet"
    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:819:1: ruleSnippet returns [EObject current=null] : ( () otherlv_1= 'Snippet' ( (lv_typeName_2_0= ruleString_Value ) )? ( (lv_properties_3_0= ruleProperty ) )* (otherlv_4= '{' ( ( (lv_contents_5_0= ruleSnippet ) ) ( (lv_contents_6_0= ruleSnippet ) )* )? otherlv_7= '}' )? ) ;
    public final EObject ruleSnippet() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_4=null;
        Token otherlv_7=null;
        AntlrDatatypeRuleToken lv_typeName_2_0 = null;

        EObject lv_properties_3_0 = null;

        EObject lv_contents_5_0 = null;

        EObject lv_contents_6_0 = null;


         enterRule(); 
            
        try {
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:822:28: ( ( () otherlv_1= 'Snippet' ( (lv_typeName_2_0= ruleString_Value ) )? ( (lv_properties_3_0= ruleProperty ) )* (otherlv_4= '{' ( ( (lv_contents_5_0= ruleSnippet ) ) ( (lv_contents_6_0= ruleSnippet ) )* )? otherlv_7= '}' )? ) )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:823:1: ( () otherlv_1= 'Snippet' ( (lv_typeName_2_0= ruleString_Value ) )? ( (lv_properties_3_0= ruleProperty ) )* (otherlv_4= '{' ( ( (lv_contents_5_0= ruleSnippet ) ) ( (lv_contents_6_0= ruleSnippet ) )* )? otherlv_7= '}' )? )
            {
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:823:1: ( () otherlv_1= 'Snippet' ( (lv_typeName_2_0= ruleString_Value ) )? ( (lv_properties_3_0= ruleProperty ) )* (otherlv_4= '{' ( ( (lv_contents_5_0= ruleSnippet ) ) ( (lv_contents_6_0= ruleSnippet ) )* )? otherlv_7= '}' )? )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:823:2: () otherlv_1= 'Snippet' ( (lv_typeName_2_0= ruleString_Value ) )? ( (lv_properties_3_0= ruleProperty ) )* (otherlv_4= '{' ( ( (lv_contents_5_0= ruleSnippet ) ) ( (lv_contents_6_0= ruleSnippet ) )* )? otherlv_7= '}' )?
            {
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:823:2: ()
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:824:5: 
            {

                    current = forceCreateModelElement(
                        grammarAccess.getSnippetAccess().getSnippetAction_0(),
                        current);
                

            }

            otherlv_1=(Token)match(input,26,FollowSets000.FOLLOW_26_in_ruleSnippet1431); 

                	newLeafNode(otherlv_1, grammarAccess.getSnippetAccess().getSnippetKeyword_1());
                
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:833:1: ( (lv_typeName_2_0= ruleString_Value ) )?
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( ((LA27_0>=RULE_STRING && LA27_0<=RULE_VALUE)) ) {
                alt27=1;
            }
            else if ( (LA27_0==RULE_ID) ) {
                int LA27_2 = input.LA(2);

                if ( (LA27_2==EOF||LA27_2==RULE_ID||LA27_2==13||(LA27_2>=18 && LA27_2<=19)||LA27_2==26) ) {
                    alt27=1;
                }
            }
            switch (alt27) {
                case 1 :
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:834:1: (lv_typeName_2_0= ruleString_Value )
                    {
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:834:1: (lv_typeName_2_0= ruleString_Value )
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:835:3: lv_typeName_2_0= ruleString_Value
                    {
                     
                    	        newCompositeNode(grammarAccess.getSnippetAccess().getTypeNameString_ValueParserRuleCall_2_0()); 
                    	    
                    pushFollow(FollowSets000.FOLLOW_ruleString_Value_in_ruleSnippet1452);
                    lv_typeName_2_0=ruleString_Value();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getSnippetRule());
                    	        }
                           		set(
                           			current, 
                           			"typeName",
                            		lv_typeName_2_0, 
                            		"String_Value");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }
                    break;

            }

            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:851:3: ( (lv_properties_3_0= ruleProperty ) )*
            loop28:
            do {
                int alt28=2;
                int LA28_0 = input.LA(1);

                if ( (LA28_0==RULE_ID) ) {
                    alt28=1;
                }


                switch (alt28) {
            	case 1 :
            	    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:852:1: (lv_properties_3_0= ruleProperty )
            	    {
            	    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:852:1: (lv_properties_3_0= ruleProperty )
            	    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:853:3: lv_properties_3_0= ruleProperty
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getSnippetAccess().getPropertiesPropertyParserRuleCall_3_0()); 
            	    	    
            	    pushFollow(FollowSets000.FOLLOW_ruleProperty_in_ruleSnippet1474);
            	    lv_properties_3_0=ruleProperty();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getSnippetRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"properties",
            	            		lv_properties_3_0, 
            	            		"Property");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop28;
                }
            } while (true);

            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:869:3: (otherlv_4= '{' ( ( (lv_contents_5_0= ruleSnippet ) ) ( (lv_contents_6_0= ruleSnippet ) )* )? otherlv_7= '}' )?
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==18) ) {
                alt31=1;
            }
            switch (alt31) {
                case 1 :
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:869:5: otherlv_4= '{' ( ( (lv_contents_5_0= ruleSnippet ) ) ( (lv_contents_6_0= ruleSnippet ) )* )? otherlv_7= '}'
                    {
                    otherlv_4=(Token)match(input,18,FollowSets000.FOLLOW_18_in_ruleSnippet1488); 

                        	newLeafNode(otherlv_4, grammarAccess.getSnippetAccess().getLeftCurlyBracketKeyword_4_0());
                        
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:873:1: ( ( (lv_contents_5_0= ruleSnippet ) ) ( (lv_contents_6_0= ruleSnippet ) )* )?
                    int alt30=2;
                    int LA30_0 = input.LA(1);

                    if ( (LA30_0==26) ) {
                        alt30=1;
                    }
                    switch (alt30) {
                        case 1 :
                            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:873:2: ( (lv_contents_5_0= ruleSnippet ) ) ( (lv_contents_6_0= ruleSnippet ) )*
                            {
                            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:873:2: ( (lv_contents_5_0= ruleSnippet ) )
                            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:874:1: (lv_contents_5_0= ruleSnippet )
                            {
                            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:874:1: (lv_contents_5_0= ruleSnippet )
                            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:875:3: lv_contents_5_0= ruleSnippet
                            {
                             
                            	        newCompositeNode(grammarAccess.getSnippetAccess().getContentsSnippetParserRuleCall_4_1_0_0()); 
                            	    
                            pushFollow(FollowSets000.FOLLOW_ruleSnippet_in_ruleSnippet1510);
                            lv_contents_5_0=ruleSnippet();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getSnippetRule());
                            	        }
                                   		add(
                                   			current, 
                                   			"contents",
                                    		lv_contents_5_0, 
                                    		"Snippet");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }

                            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:891:2: ( (lv_contents_6_0= ruleSnippet ) )*
                            loop29:
                            do {
                                int alt29=2;
                                int LA29_0 = input.LA(1);

                                if ( (LA29_0==26) ) {
                                    alt29=1;
                                }


                                switch (alt29) {
                            	case 1 :
                            	    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:892:1: (lv_contents_6_0= ruleSnippet )
                            	    {
                            	    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:892:1: (lv_contents_6_0= ruleSnippet )
                            	    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:893:3: lv_contents_6_0= ruleSnippet
                            	    {
                            	     
                            	    	        newCompositeNode(grammarAccess.getSnippetAccess().getContentsSnippetParserRuleCall_4_1_1_0()); 
                            	    	    
                            	    pushFollow(FollowSets000.FOLLOW_ruleSnippet_in_ruleSnippet1531);
                            	    lv_contents_6_0=ruleSnippet();

                            	    state._fsp--;


                            	    	        if (current==null) {
                            	    	            current = createModelElementForParent(grammarAccess.getSnippetRule());
                            	    	        }
                            	           		add(
                            	           			current, 
                            	           			"contents",
                            	            		lv_contents_6_0, 
                            	            		"Snippet");
                            	    	        afterParserOrEnumRuleCall();
                            	    	    

                            	    }


                            	    }
                            	    break;

                            	default :
                            	    break loop29;
                                }
                            } while (true);


                            }
                            break;

                    }

                    otherlv_7=(Token)match(input,19,FollowSets000.FOLLOW_19_in_ruleSnippet1546); 

                        	newLeafNode(otherlv_7, grammarAccess.getSnippetAccess().getRightCurlyBracketKeyword_4_2());
                        

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
    // $ANTLR end "ruleSnippet"


    // $ANTLR start "entryRuleTranslation"
    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:921:1: entryRuleTranslation returns [EObject current=null] : iv_ruleTranslation= ruleTranslation EOF ;
    public final EObject entryRuleTranslation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTranslation = null;


        try {
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:922:2: (iv_ruleTranslation= ruleTranslation EOF )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:923:2: iv_ruleTranslation= ruleTranslation EOF
            {
             newCompositeNode(grammarAccess.getTranslationRule()); 
            pushFollow(FollowSets000.FOLLOW_ruleTranslation_in_entryRuleTranslation1584);
            iv_ruleTranslation=ruleTranslation();

            state._fsp--;

             current =iv_ruleTranslation; 
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleTranslation1594); 

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
    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:930:1: ruleTranslation returns [EObject current=null] : ( ( (lv_language_0_0= RULE_ID ) ) otherlv_1= '=' ( (lv_message_2_0= ruleString_Value ) ) ) ;
    public final EObject ruleTranslation() throws RecognitionException {
        EObject current = null;

        Token lv_language_0_0=null;
        Token otherlv_1=null;
        AntlrDatatypeRuleToken lv_message_2_0 = null;


         enterRule(); 
            
        try {
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:933:28: ( ( ( (lv_language_0_0= RULE_ID ) ) otherlv_1= '=' ( (lv_message_2_0= ruleString_Value ) ) ) )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:934:1: ( ( (lv_language_0_0= RULE_ID ) ) otherlv_1= '=' ( (lv_message_2_0= ruleString_Value ) ) )
            {
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:934:1: ( ( (lv_language_0_0= RULE_ID ) ) otherlv_1= '=' ( (lv_message_2_0= ruleString_Value ) ) )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:934:2: ( (lv_language_0_0= RULE_ID ) ) otherlv_1= '=' ( (lv_message_2_0= ruleString_Value ) )
            {
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:934:2: ( (lv_language_0_0= RULE_ID ) )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:935:1: (lv_language_0_0= RULE_ID )
            {
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:935:1: (lv_language_0_0= RULE_ID )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:936:3: lv_language_0_0= RULE_ID
            {
            lv_language_0_0=(Token)match(input,RULE_ID,FollowSets000.FOLLOW_RULE_ID_in_ruleTranslation1636); 

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

            otherlv_1=(Token)match(input,12,FollowSets000.FOLLOW_12_in_ruleTranslation1653); 

                	newLeafNode(otherlv_1, grammarAccess.getTranslationAccess().getEqualsSignKeyword_1());
                
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:956:1: ( (lv_message_2_0= ruleString_Value ) )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:957:1: (lv_message_2_0= ruleString_Value )
            {
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:957:1: (lv_message_2_0= ruleString_Value )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:958:3: lv_message_2_0= ruleString_Value
            {
             
            	        newCompositeNode(grammarAccess.getTranslationAccess().getMessageString_ValueParserRuleCall_2_0()); 
            	    
            pushFollow(FollowSets000.FOLLOW_ruleString_Value_in_ruleTranslation1674);
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


    // $ANTLR start "entryRuleParameter"
    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:982:1: entryRuleParameter returns [EObject current=null] : iv_ruleParameter= ruleParameter EOF ;
    public final EObject entryRuleParameter() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleParameter = null;


        try {
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:983:2: (iv_ruleParameter= ruleParameter EOF )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:984:2: iv_ruleParameter= ruleParameter EOF
            {
             newCompositeNode(grammarAccess.getParameterRule()); 
            pushFollow(FollowSets000.FOLLOW_ruleParameter_in_entryRuleParameter1710);
            iv_ruleParameter=ruleParameter();

            state._fsp--;

             current =iv_ruleParameter; 
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleParameter1720); 

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
    // $ANTLR end "entryRuleParameter"


    // $ANTLR start "ruleParameter"
    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:991:1: ruleParameter returns [EObject current=null] : ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= '=' ( (lv_value_2_0= ruleString_Value ) ) ( (lv_userDefined_3_0= 'ud' ) )? ) ;
    public final EObject ruleParameter() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        Token lv_userDefined_3_0=null;
        AntlrDatatypeRuleToken lv_value_2_0 = null;


         enterRule(); 
            
        try {
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:994:28: ( ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= '=' ( (lv_value_2_0= ruleString_Value ) ) ( (lv_userDefined_3_0= 'ud' ) )? ) )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:995:1: ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= '=' ( (lv_value_2_0= ruleString_Value ) ) ( (lv_userDefined_3_0= 'ud' ) )? )
            {
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:995:1: ( ( (lv_name_0_0= RULE_ID ) ) otherlv_1= '=' ( (lv_value_2_0= ruleString_Value ) ) ( (lv_userDefined_3_0= 'ud' ) )? )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:995:2: ( (lv_name_0_0= RULE_ID ) ) otherlv_1= '=' ( (lv_value_2_0= ruleString_Value ) ) ( (lv_userDefined_3_0= 'ud' ) )?
            {
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:995:2: ( (lv_name_0_0= RULE_ID ) )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:996:1: (lv_name_0_0= RULE_ID )
            {
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:996:1: (lv_name_0_0= RULE_ID )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:997:3: lv_name_0_0= RULE_ID
            {
            lv_name_0_0=(Token)match(input,RULE_ID,FollowSets000.FOLLOW_RULE_ID_in_ruleParameter1762); 

            			newLeafNode(lv_name_0_0, grammarAccess.getParameterAccess().getNameIDTerminalRuleCall_0_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getParameterRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"name",
                    		lv_name_0_0, 
                    		"ID");
            	    

            }


            }

            otherlv_1=(Token)match(input,12,FollowSets000.FOLLOW_12_in_ruleParameter1779); 

                	newLeafNode(otherlv_1, grammarAccess.getParameterAccess().getEqualsSignKeyword_1());
                
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1017:1: ( (lv_value_2_0= ruleString_Value ) )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1018:1: (lv_value_2_0= ruleString_Value )
            {
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1018:1: (lv_value_2_0= ruleString_Value )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1019:3: lv_value_2_0= ruleString_Value
            {
             
            	        newCompositeNode(grammarAccess.getParameterAccess().getValueString_ValueParserRuleCall_2_0()); 
            	    
            pushFollow(FollowSets000.FOLLOW_ruleString_Value_in_ruleParameter1800);
            lv_value_2_0=ruleString_Value();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getParameterRule());
            	        }
                   		set(
                   			current, 
                   			"value",
                    		lv_value_2_0, 
                    		"String_Value");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1035:2: ( (lv_userDefined_3_0= 'ud' ) )?
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==27) ) {
                alt32=1;
            }
            switch (alt32) {
                case 1 :
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1036:1: (lv_userDefined_3_0= 'ud' )
                    {
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1036:1: (lv_userDefined_3_0= 'ud' )
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1037:3: lv_userDefined_3_0= 'ud'
                    {
                    lv_userDefined_3_0=(Token)match(input,27,FollowSets000.FOLLOW_27_in_ruleParameter1818); 

                            newLeafNode(lv_userDefined_3_0, grammarAccess.getParameterAccess().getUserDefinedUdKeyword_3_0());
                        

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getParameterRule());
                    	        }
                           		setWithLastConsumed(current, "userDefined", true, "ud");
                    	    

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
    // $ANTLR end "ruleParameter"


    // $ANTLR start "entryRuleLibraryName"
    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1058:1: entryRuleLibraryName returns [String current=null] : iv_ruleLibraryName= ruleLibraryName EOF ;
    public final String entryRuleLibraryName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleLibraryName = null;


        try {
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1059:2: (iv_ruleLibraryName= ruleLibraryName EOF )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1060:2: iv_ruleLibraryName= ruleLibraryName EOF
            {
             newCompositeNode(grammarAccess.getLibraryNameRule()); 
            pushFollow(FollowSets000.FOLLOW_ruleLibraryName_in_entryRuleLibraryName1869);
            iv_ruleLibraryName=ruleLibraryName();

            state._fsp--;

             current =iv_ruleLibraryName.getText(); 
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleLibraryName1880); 

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
    // $ANTLR end "entryRuleLibraryName"


    // $ANTLR start "ruleLibraryName"
    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1067:1: ruleLibraryName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_ID_0= RULE_ID ;
    public final AntlrDatatypeRuleToken ruleLibraryName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;

         enterRule(); 
            
        try {
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1070:28: (this_ID_0= RULE_ID )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1071:5: this_ID_0= RULE_ID
            {
            this_ID_0=(Token)match(input,RULE_ID,FollowSets000.FOLLOW_RULE_ID_in_ruleLibraryName1919); 

            		current.merge(this_ID_0);
                
             
                newLeafNode(this_ID_0, grammarAccess.getLibraryNameAccess().getIDTerminalRuleCall()); 
                

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
    // $ANTLR end "ruleLibraryName"


    // $ANTLR start "entryRuleString_Value"
    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1086:1: entryRuleString_Value returns [String current=null] : iv_ruleString_Value= ruleString_Value EOF ;
    public final String entryRuleString_Value() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleString_Value = null;


        try {
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1087:2: (iv_ruleString_Value= ruleString_Value EOF )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1088:2: iv_ruleString_Value= ruleString_Value EOF
            {
             newCompositeNode(grammarAccess.getString_ValueRule()); 
            pushFollow(FollowSets000.FOLLOW_ruleString_Value_in_entryRuleString_Value1964);
            iv_ruleString_Value=ruleString_Value();

            state._fsp--;

             current =iv_ruleString_Value.getText(); 
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleString_Value1975); 

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
    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1095:1: ruleString_Value returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_STRING_0= RULE_STRING | this_ID_1= RULE_ID | this_VALUE_2= RULE_VALUE ) ;
    public final AntlrDatatypeRuleToken ruleString_Value() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_STRING_0=null;
        Token this_ID_1=null;
        Token this_VALUE_2=null;

         enterRule(); 
            
        try {
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1098:28: ( (this_STRING_0= RULE_STRING | this_ID_1= RULE_ID | this_VALUE_2= RULE_VALUE ) )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1099:1: (this_STRING_0= RULE_STRING | this_ID_1= RULE_ID | this_VALUE_2= RULE_VALUE )
            {
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1099:1: (this_STRING_0= RULE_STRING | this_ID_1= RULE_ID | this_VALUE_2= RULE_VALUE )
            int alt33=3;
            switch ( input.LA(1) ) {
            case RULE_STRING:
                {
                alt33=1;
                }
                break;
            case RULE_ID:
                {
                alt33=2;
                }
                break;
            case RULE_VALUE:
                {
                alt33=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 33, 0, input);

                throw nvae;
            }

            switch (alt33) {
                case 1 :
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1099:6: this_STRING_0= RULE_STRING
                    {
                    this_STRING_0=(Token)match(input,RULE_STRING,FollowSets000.FOLLOW_RULE_STRING_in_ruleString_Value2015); 

                    		current.merge(this_STRING_0);
                        
                     
                        newLeafNode(this_STRING_0, grammarAccess.getString_ValueAccess().getSTRINGTerminalRuleCall_0()); 
                        

                    }
                    break;
                case 2 :
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1107:10: this_ID_1= RULE_ID
                    {
                    this_ID_1=(Token)match(input,RULE_ID,FollowSets000.FOLLOW_RULE_ID_in_ruleString_Value2041); 

                    		current.merge(this_ID_1);
                        
                     
                        newLeafNode(this_ID_1, grammarAccess.getString_ValueAccess().getIDTerminalRuleCall_1()); 
                        

                    }
                    break;
                case 3 :
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1115:10: this_VALUE_2= RULE_VALUE
                    {
                    this_VALUE_2=(Token)match(input,RULE_VALUE,FollowSets000.FOLLOW_RULE_VALUE_in_ruleString_Value2067); 

                    		current.merge(this_VALUE_2);
                        
                     
                        newLeafNode(this_VALUE_2, grammarAccess.getString_ValueAccess().getVALUETerminalRuleCall_2()); 
                        

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


    // $ANTLR start "ruleEventNature"
    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1130:1: ruleEventNature returns [Enumerator current=null] : ( (enumLiteral_0= 'A' ) | (enumLiteral_1= 'S' ) ) ;
    public final Enumerator ruleEventNature() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;

         enterRule(); 
        try {
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1132:28: ( ( (enumLiteral_0= 'A' ) | (enumLiteral_1= 'S' ) ) )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1133:1: ( (enumLiteral_0= 'A' ) | (enumLiteral_1= 'S' ) )
            {
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1133:1: ( (enumLiteral_0= 'A' ) | (enumLiteral_1= 'S' ) )
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==28) ) {
                alt34=1;
            }
            else if ( (LA34_0==29) ) {
                alt34=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 34, 0, input);

                throw nvae;
            }
            switch (alt34) {
                case 1 :
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1133:2: (enumLiteral_0= 'A' )
                    {
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1133:2: (enumLiteral_0= 'A' )
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1133:4: enumLiteral_0= 'A'
                    {
                    enumLiteral_0=(Token)match(input,28,FollowSets000.FOLLOW_28_in_ruleEventNature2126); 

                            current = grammarAccess.getEventNatureAccess().getADVANCEDEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_0, grammarAccess.getEventNatureAccess().getADVANCEDEnumLiteralDeclaration_0()); 
                        

                    }


                    }
                    break;
                case 2 :
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1139:6: (enumLiteral_1= 'S' )
                    {
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1139:6: (enumLiteral_1= 'S' )
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1139:8: enumLiteral_1= 'S'
                    {
                    enumLiteral_1=(Token)match(input,29,FollowSets000.FOLLOW_29_in_ruleEventNature2143); 

                            current = grammarAccess.getEventNatureAccess().getSIMPLIFIEDEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_1, grammarAccess.getEventNatureAccess().getSIMPLIFIEDEnumLiteralDeclaration_1()); 
                        

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
    // $ANTLR end "ruleEventNature"

    // Delegated rules


 


    private static class FollowSets000 {
        public static final BitSet FOLLOW_ruleModel_in_entryRuleModel75 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleModel85 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_11_in_ruleModel131 = new BitSet(new long[]{0x0000000000001000L});
        public static final BitSet FOLLOW_12_in_ruleModel143 = new BitSet(new long[]{0x00000000000000D0L});
        public static final BitSet FOLLOW_ruleString_Value_in_ruleModel164 = new BitSet(new long[]{0x0000000000002000L});
        public static final BitSet FOLLOW_ruleWidget_in_ruleModel185 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleWidget_in_entryRuleWidget221 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleWidget231 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_13_in_ruleWidget277 = new BitSet(new long[]{0x0000000000000010L});
        public static final BitSet FOLLOW_ruleLibraryName_in_ruleWidget299 = new BitSet(new long[]{0x0000000000004000L});
        public static final BitSet FOLLOW_14_in_ruleWidget311 = new BitSet(new long[]{0x0000000000000010L});
        public static final BitSet FOLLOW_RULE_ID_in_ruleWidget330 = new BitSet(new long[]{0x0000000000002000L});
        public static final BitSet FOLLOW_13_in_ruleWidget347 = new BitSet(new long[]{0x0000000000068012L});
        public static final BitSet FOLLOW_15_in_ruleWidget360 = new BitSet(new long[]{0x0000000000000010L});
        public static final BitSet FOLLOW_ruleTranslation_in_ruleWidget381 = new BitSet(new long[]{0x0000000000070012L});
        public static final BitSet FOLLOW_16_in_ruleWidget394 = new BitSet(new long[]{0x0000000000000010L});
        public static final BitSet FOLLOW_ruleTranslation_in_ruleWidget415 = new BitSet(new long[]{0x0000000000070012L});
        public static final BitSet FOLLOW_17_in_ruleWidget432 = new BitSet(new long[]{0x0000000000000010L});
        public static final BitSet FOLLOW_ruleTranslation_in_ruleWidget453 = new BitSet(new long[]{0x0000000000050012L});
        public static final BitSet FOLLOW_16_in_ruleWidget466 = new BitSet(new long[]{0x0000000000000010L});
        public static final BitSet FOLLOW_ruleTranslation_in_ruleWidget487 = new BitSet(new long[]{0x0000000000050012L});
        public static final BitSet FOLLOW_ruleProperty_in_ruleWidget512 = new BitSet(new long[]{0x0000000000040012L});
        public static final BitSet FOLLOW_18_in_ruleWidget526 = new BitSet(new long[]{0x0000000004282000L});
        public static final BitSet FOLLOW_ruleEvent_in_ruleWidget548 = new BitSet(new long[]{0x0000000004282000L});
        public static final BitSet FOLLOW_ruleEvent_in_ruleWidget569 = new BitSet(new long[]{0x0000000004282000L});
        public static final BitSet FOLLOW_ruleSnippet_in_ruleWidget594 = new BitSet(new long[]{0x0000000004082000L});
        public static final BitSet FOLLOW_ruleSnippet_in_ruleWidget615 = new BitSet(new long[]{0x0000000004082000L});
        public static final BitSet FOLLOW_ruleWidget_in_ruleWidget640 = new BitSet(new long[]{0x0000000000082000L});
        public static final BitSet FOLLOW_ruleWidget_in_ruleWidget661 = new BitSet(new long[]{0x0000000000082000L});
        public static final BitSet FOLLOW_19_in_ruleWidget676 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleProperty_in_entryRuleProperty716 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleProperty726 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_RULE_ID_in_ruleProperty778 = new BitSet(new long[]{0x0000000000004000L});
        public static final BitSet FOLLOW_14_in_ruleProperty795 = new BitSet(new long[]{0x0000000000000010L});
        public static final BitSet FOLLOW_RULE_ID_in_ruleProperty814 = new BitSet(new long[]{0x0000000000001000L});
        public static final BitSet FOLLOW_12_in_ruleProperty831 = new BitSet(new long[]{0x00000000000000D0L});
        public static final BitSet FOLLOW_ruleString_Value_in_ruleProperty852 = new BitSet(new long[]{0x0000000000100002L});
        public static final BitSet FOLLOW_20_in_ruleProperty870 = new BitSet(new long[]{0x0000000000100002L});
        public static final BitSet FOLLOW_20_in_ruleProperty897 = new BitSet(new long[]{0x0000000000000020L});
        public static final BitSet FOLLOW_RULE_URI_in_ruleProperty917 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleEvent_in_entryRuleEvent955 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleEvent965 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_21_in_ruleEvent1002 = new BitSet(new long[]{0x00000000000000D0L});
        public static final BitSet FOLLOW_ruleString_Value_in_ruleEvent1023 = new BitSet(new long[]{0x0000000000400000L});
        public static final BitSet FOLLOW_22_in_ruleEvent1035 = new BitSet(new long[]{0x00000000000000D0L});
        public static final BitSet FOLLOW_ruleString_Value_in_ruleEvent1056 = new BitSet(new long[]{0x0000000000800000L});
        public static final BitSet FOLLOW_23_in_ruleEvent1068 = new BitSet(new long[]{0x0000000033040012L});
        public static final BitSet FOLLOW_ruleEventNature_in_ruleEvent1089 = new BitSet(new long[]{0x0000000003040012L});
        public static final BitSet FOLLOW_24_in_ruleEvent1103 = new BitSet(new long[]{0x00000000000000D0L});
        public static final BitSet FOLLOW_ruleString_Value_in_ruleEvent1124 = new BitSet(new long[]{0x0000000002040012L});
        public static final BitSet FOLLOW_ruleProperty_in_ruleEvent1147 = new BitSet(new long[]{0x0000000002040012L});
        public static final BitSet FOLLOW_25_in_ruleEvent1161 = new BitSet(new long[]{0x0000000000000010L});
        public static final BitSet FOLLOW_ruleTranslation_in_ruleEvent1182 = new BitSet(new long[]{0x0000000000050002L});
        public static final BitSet FOLLOW_16_in_ruleEvent1195 = new BitSet(new long[]{0x0000000000000010L});
        public static final BitSet FOLLOW_ruleTranslation_in_ruleEvent1216 = new BitSet(new long[]{0x0000000000050002L});
        public static final BitSet FOLLOW_18_in_ruleEvent1233 = new BitSet(new long[]{0x0000000004080010L});
        public static final BitSet FOLLOW_ruleParameter_in_ruleEvent1255 = new BitSet(new long[]{0x0000000004080010L});
        public static final BitSet FOLLOW_ruleParameter_in_ruleEvent1276 = new BitSet(new long[]{0x0000000004080010L});
        public static final BitSet FOLLOW_ruleSnippet_in_ruleEvent1301 = new BitSet(new long[]{0x0000000004080000L});
        public static final BitSet FOLLOW_ruleSnippet_in_ruleEvent1322 = new BitSet(new long[]{0x0000000004080000L});
        public static final BitSet FOLLOW_19_in_ruleEvent1337 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleSnippet_in_entryRuleSnippet1375 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleSnippet1385 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_26_in_ruleSnippet1431 = new BitSet(new long[]{0x00000000000400D2L});
        public static final BitSet FOLLOW_ruleString_Value_in_ruleSnippet1452 = new BitSet(new long[]{0x0000000000040012L});
        public static final BitSet FOLLOW_ruleProperty_in_ruleSnippet1474 = new BitSet(new long[]{0x0000000000040012L});
        public static final BitSet FOLLOW_18_in_ruleSnippet1488 = new BitSet(new long[]{0x0000000004080000L});
        public static final BitSet FOLLOW_ruleSnippet_in_ruleSnippet1510 = new BitSet(new long[]{0x0000000004080000L});
        public static final BitSet FOLLOW_ruleSnippet_in_ruleSnippet1531 = new BitSet(new long[]{0x0000000004080000L});
        public static final BitSet FOLLOW_19_in_ruleSnippet1546 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleTranslation_in_entryRuleTranslation1584 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleTranslation1594 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_RULE_ID_in_ruleTranslation1636 = new BitSet(new long[]{0x0000000000001000L});
        public static final BitSet FOLLOW_12_in_ruleTranslation1653 = new BitSet(new long[]{0x00000000000000D0L});
        public static final BitSet FOLLOW_ruleString_Value_in_ruleTranslation1674 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleParameter_in_entryRuleParameter1710 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleParameter1720 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_RULE_ID_in_ruleParameter1762 = new BitSet(new long[]{0x0000000000001000L});
        public static final BitSet FOLLOW_12_in_ruleParameter1779 = new BitSet(new long[]{0x00000000000000D0L});
        public static final BitSet FOLLOW_ruleString_Value_in_ruleParameter1800 = new BitSet(new long[]{0x0000000008000002L});
        public static final BitSet FOLLOW_27_in_ruleParameter1818 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleLibraryName_in_entryRuleLibraryName1869 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleLibraryName1880 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_RULE_ID_in_ruleLibraryName1919 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleString_Value_in_entryRuleString_Value1964 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleString_Value1975 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_RULE_STRING_in_ruleString_Value2015 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_RULE_ID_in_ruleString_Value2041 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_RULE_VALUE_in_ruleString_Value2067 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_28_in_ruleEventNature2126 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_29_in_ruleEventNature2143 = new BitSet(new long[]{0x0000000000000002L});
    }


}
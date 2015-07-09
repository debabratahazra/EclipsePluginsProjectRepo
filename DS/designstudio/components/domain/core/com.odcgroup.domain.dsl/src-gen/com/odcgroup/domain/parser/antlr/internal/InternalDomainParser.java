package com.odcgroup.domain.parser.antlr.internal; 

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
import com.odcgroup.domain.services.DomainGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalDomainParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ID", "RULE_ML_DOC", "RULE_STRING", "RULE_VALUE", "RULE_SL_COMMENT", "RULE_WS", "RULE_URI", "'Domain'", "'namespace'", "'metamodelVersion'", "'deprecationInfo'", "'Classes'", "'{'", "'}'", "'Datasets'", "'BusinessTypes'", "'Enumerations'", "'version='", "'comment='", "'@'", "':'", "'('", "','", "')'", "'abstract'", "'secured'", "'extends'", "'basedOn'", "'default'", "'synchronized'", "'acceptNullValue'", "'cDATA'", "'='", "'BK'", "'PK'", "'required'", "'->'", "'reverse'", "'<-'", "'singleValued'", "'['", "']'", "'notUnique'", "'*'", "'ONE'", "'byReference'", "'byValue'"
    };
    public static final int RULE_VALUE=7;
    public static final int RULE_ID=4;
    public static final int T__29=29;
    public static final int T__28=28;
    public static final int T__27=27;
    public static final int RULE_ML_DOC=5;
    public static final int T__26=26;
    public static final int T__25=25;
    public static final int T__24=24;
    public static final int T__23=23;
    public static final int T__22=22;
    public static final int T__21=21;
    public static final int T__20=20;
    public static final int EOF=-1;
    public static final int T__19=19;
    public static final int T__16=16;
    public static final int T__15=15;
    public static final int T__18=18;
    public static final int T__17=17;
    public static final int T__12=12;
    public static final int T__11=11;
    public static final int T__14=14;
    public static final int T__13=13;
    public static final int T__50=50;
    public static final int T__42=42;
    public static final int T__43=43;
    public static final int T__40=40;
    public static final int T__41=41;
    public static final int T__46=46;
    public static final int T__47=47;
    public static final int T__44=44;
    public static final int T__45=45;
    public static final int T__48=48;
    public static final int T__49=49;
    public static final int RULE_URI=10;
    public static final int RULE_SL_COMMENT=8;
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int RULE_STRING=6;
    public static final int T__32=32;
    public static final int T__33=33;
    public static final int T__34=34;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int T__37=37;
    public static final int T__38=38;
    public static final int T__39=39;
    public static final int RULE_WS=9;

    // delegates
    // delegators


        public InternalDomainParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalDomainParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalDomainParser.tokenNames; }
    public String getGrammarFileName() { return "../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g"; }



     	private DomainGrammarAccess grammarAccess;
     	
        public InternalDomainParser(TokenStream input, DomainGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }
        
        @Override
        protected String getFirstRuleName() {
        	return "MdfDomain";	
       	}
       	
       	@Override
       	protected DomainGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}



    // $ANTLR start "entryRuleMdfDomain"
    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:68:1: entryRuleMdfDomain returns [EObject current=null] : iv_ruleMdfDomain= ruleMdfDomain EOF ;
    public final EObject entryRuleMdfDomain() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMdfDomain = null;


        try {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:69:2: (iv_ruleMdfDomain= ruleMdfDomain EOF )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:70:2: iv_ruleMdfDomain= ruleMdfDomain EOF
            {
             newCompositeNode(grammarAccess.getMdfDomainRule()); 
            pushFollow(FollowSets000.FOLLOW_ruleMdfDomain_in_entryRuleMdfDomain75);
            iv_ruleMdfDomain=ruleMdfDomain();

            state._fsp--;

             current =iv_ruleMdfDomain; 
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleMdfDomain85); 

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
    // $ANTLR end "entryRuleMdfDomain"


    // $ANTLR start "ruleMdfDomain"
    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:77:1: ruleMdfDomain returns [EObject current=null] : ( ( (lv_documentation_0_0= ruleDocumentation ) )? otherlv_1= 'Domain' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= ruleMdfAnnotation ) )* otherlv_4= 'namespace' ( (lv_namespace_5_0= ruleString_Value ) ) otherlv_6= 'metamodelVersion' ( (lv_metamodelVersion_7_0= ruleString_Value ) ) (otherlv_8= 'deprecationInfo' ruleMdfDeprecationInfo )? (otherlv_10= 'Classes' otherlv_11= '{' ( (lv_classes_12_0= ruleMdfClass ) )+ otherlv_13= '}' )? (otherlv_14= 'Datasets' otherlv_15= '{' ( (lv_datasets_16_0= ruleMdfDataset ) )+ otherlv_17= '}' )? (otherlv_18= 'BusinessTypes' otherlv_19= '{' ( (lv_businessTypes_20_0= ruleMdfBusinessType ) )+ otherlv_21= '}' )? (otherlv_22= 'Enumerations' otherlv_23= '{' ( (lv_enumerations_24_0= ruleMdfEnumeration ) )+ otherlv_25= '}' )? ) ;
    public final EObject ruleMdfDomain() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_name_2_0=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        Token otherlv_10=null;
        Token otherlv_11=null;
        Token otherlv_13=null;
        Token otherlv_14=null;
        Token otherlv_15=null;
        Token otherlv_17=null;
        Token otherlv_18=null;
        Token otherlv_19=null;
        Token otherlv_21=null;
        Token otherlv_22=null;
        Token otherlv_23=null;
        Token otherlv_25=null;
        AntlrDatatypeRuleToken lv_documentation_0_0 = null;

        EObject lv_annotations_3_0 = null;

        AntlrDatatypeRuleToken lv_namespace_5_0 = null;

        AntlrDatatypeRuleToken lv_metamodelVersion_7_0 = null;

        EObject lv_classes_12_0 = null;

        EObject lv_datasets_16_0 = null;

        EObject lv_businessTypes_20_0 = null;

        EObject lv_enumerations_24_0 = null;


         enterRule(); 
            
        try {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:80:28: ( ( ( (lv_documentation_0_0= ruleDocumentation ) )? otherlv_1= 'Domain' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= ruleMdfAnnotation ) )* otherlv_4= 'namespace' ( (lv_namespace_5_0= ruleString_Value ) ) otherlv_6= 'metamodelVersion' ( (lv_metamodelVersion_7_0= ruleString_Value ) ) (otherlv_8= 'deprecationInfo' ruleMdfDeprecationInfo )? (otherlv_10= 'Classes' otherlv_11= '{' ( (lv_classes_12_0= ruleMdfClass ) )+ otherlv_13= '}' )? (otherlv_14= 'Datasets' otherlv_15= '{' ( (lv_datasets_16_0= ruleMdfDataset ) )+ otherlv_17= '}' )? (otherlv_18= 'BusinessTypes' otherlv_19= '{' ( (lv_businessTypes_20_0= ruleMdfBusinessType ) )+ otherlv_21= '}' )? (otherlv_22= 'Enumerations' otherlv_23= '{' ( (lv_enumerations_24_0= ruleMdfEnumeration ) )+ otherlv_25= '}' )? ) )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:81:1: ( ( (lv_documentation_0_0= ruleDocumentation ) )? otherlv_1= 'Domain' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= ruleMdfAnnotation ) )* otherlv_4= 'namespace' ( (lv_namespace_5_0= ruleString_Value ) ) otherlv_6= 'metamodelVersion' ( (lv_metamodelVersion_7_0= ruleString_Value ) ) (otherlv_8= 'deprecationInfo' ruleMdfDeprecationInfo )? (otherlv_10= 'Classes' otherlv_11= '{' ( (lv_classes_12_0= ruleMdfClass ) )+ otherlv_13= '}' )? (otherlv_14= 'Datasets' otherlv_15= '{' ( (lv_datasets_16_0= ruleMdfDataset ) )+ otherlv_17= '}' )? (otherlv_18= 'BusinessTypes' otherlv_19= '{' ( (lv_businessTypes_20_0= ruleMdfBusinessType ) )+ otherlv_21= '}' )? (otherlv_22= 'Enumerations' otherlv_23= '{' ( (lv_enumerations_24_0= ruleMdfEnumeration ) )+ otherlv_25= '}' )? )
            {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:81:1: ( ( (lv_documentation_0_0= ruleDocumentation ) )? otherlv_1= 'Domain' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= ruleMdfAnnotation ) )* otherlv_4= 'namespace' ( (lv_namespace_5_0= ruleString_Value ) ) otherlv_6= 'metamodelVersion' ( (lv_metamodelVersion_7_0= ruleString_Value ) ) (otherlv_8= 'deprecationInfo' ruleMdfDeprecationInfo )? (otherlv_10= 'Classes' otherlv_11= '{' ( (lv_classes_12_0= ruleMdfClass ) )+ otherlv_13= '}' )? (otherlv_14= 'Datasets' otherlv_15= '{' ( (lv_datasets_16_0= ruleMdfDataset ) )+ otherlv_17= '}' )? (otherlv_18= 'BusinessTypes' otherlv_19= '{' ( (lv_businessTypes_20_0= ruleMdfBusinessType ) )+ otherlv_21= '}' )? (otherlv_22= 'Enumerations' otherlv_23= '{' ( (lv_enumerations_24_0= ruleMdfEnumeration ) )+ otherlv_25= '}' )? )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:81:2: ( (lv_documentation_0_0= ruleDocumentation ) )? otherlv_1= 'Domain' ( (lv_name_2_0= RULE_ID ) ) ( (lv_annotations_3_0= ruleMdfAnnotation ) )* otherlv_4= 'namespace' ( (lv_namespace_5_0= ruleString_Value ) ) otherlv_6= 'metamodelVersion' ( (lv_metamodelVersion_7_0= ruleString_Value ) ) (otherlv_8= 'deprecationInfo' ruleMdfDeprecationInfo )? (otherlv_10= 'Classes' otherlv_11= '{' ( (lv_classes_12_0= ruleMdfClass ) )+ otherlv_13= '}' )? (otherlv_14= 'Datasets' otherlv_15= '{' ( (lv_datasets_16_0= ruleMdfDataset ) )+ otherlv_17= '}' )? (otherlv_18= 'BusinessTypes' otherlv_19= '{' ( (lv_businessTypes_20_0= ruleMdfBusinessType ) )+ otherlv_21= '}' )? (otherlv_22= 'Enumerations' otherlv_23= '{' ( (lv_enumerations_24_0= ruleMdfEnumeration ) )+ otherlv_25= '}' )?
            {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:81:2: ( (lv_documentation_0_0= ruleDocumentation ) )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==RULE_ML_DOC) ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:82:1: (lv_documentation_0_0= ruleDocumentation )
                    {
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:82:1: (lv_documentation_0_0= ruleDocumentation )
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:83:3: lv_documentation_0_0= ruleDocumentation
                    {
                     
                    	        newCompositeNode(grammarAccess.getMdfDomainAccess().getDocumentationDocumentationParserRuleCall_0_0()); 
                    	    
                    pushFollow(FollowSets000.FOLLOW_ruleDocumentation_in_ruleMdfDomain131);
                    lv_documentation_0_0=ruleDocumentation();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getMdfDomainRule());
                    	        }
                           		set(
                           			current, 
                           			"documentation",
                            		lv_documentation_0_0, 
                            		"Documentation");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }
                    break;

            }

            otherlv_1=(Token)match(input,11,FollowSets000.FOLLOW_11_in_ruleMdfDomain144); 

                	newLeafNode(otherlv_1, grammarAccess.getMdfDomainAccess().getDomainKeyword_1());
                
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:103:1: ( (lv_name_2_0= RULE_ID ) )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:104:1: (lv_name_2_0= RULE_ID )
            {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:104:1: (lv_name_2_0= RULE_ID )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:105:3: lv_name_2_0= RULE_ID
            {
            lv_name_2_0=(Token)match(input,RULE_ID,FollowSets000.FOLLOW_RULE_ID_in_ruleMdfDomain161); 

            			newLeafNode(lv_name_2_0, grammarAccess.getMdfDomainAccess().getNameIDTerminalRuleCall_2_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getMdfDomainRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"name",
                    		lv_name_2_0, 
                    		"ID");
            	    

            }


            }

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:121:2: ( (lv_annotations_3_0= ruleMdfAnnotation ) )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==23) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:122:1: (lv_annotations_3_0= ruleMdfAnnotation )
            	    {
            	    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:122:1: (lv_annotations_3_0= ruleMdfAnnotation )
            	    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:123:3: lv_annotations_3_0= ruleMdfAnnotation
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getMdfDomainAccess().getAnnotationsMdfAnnotationParserRuleCall_3_0()); 
            	    	    
            	    pushFollow(FollowSets000.FOLLOW_ruleMdfAnnotation_in_ruleMdfDomain187);
            	    lv_annotations_3_0=ruleMdfAnnotation();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getMdfDomainRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"annotations",
            	            		lv_annotations_3_0, 
            	            		"MdfAnnotation");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            otherlv_4=(Token)match(input,12,FollowSets000.FOLLOW_12_in_ruleMdfDomain200); 

                	newLeafNode(otherlv_4, grammarAccess.getMdfDomainAccess().getNamespaceKeyword_4());
                
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:143:1: ( (lv_namespace_5_0= ruleString_Value ) )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:144:1: (lv_namespace_5_0= ruleString_Value )
            {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:144:1: (lv_namespace_5_0= ruleString_Value )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:145:3: lv_namespace_5_0= ruleString_Value
            {
             
            	        newCompositeNode(grammarAccess.getMdfDomainAccess().getNamespaceString_ValueParserRuleCall_5_0()); 
            	    
            pushFollow(FollowSets000.FOLLOW_ruleString_Value_in_ruleMdfDomain221);
            lv_namespace_5_0=ruleString_Value();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getMdfDomainRule());
            	        }
                   		set(
                   			current, 
                   			"namespace",
                    		lv_namespace_5_0, 
                    		"String_Value");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_6=(Token)match(input,13,FollowSets000.FOLLOW_13_in_ruleMdfDomain233); 

                	newLeafNode(otherlv_6, grammarAccess.getMdfDomainAccess().getMetamodelVersionKeyword_6());
                
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:165:1: ( (lv_metamodelVersion_7_0= ruleString_Value ) )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:166:1: (lv_metamodelVersion_7_0= ruleString_Value )
            {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:166:1: (lv_metamodelVersion_7_0= ruleString_Value )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:167:3: lv_metamodelVersion_7_0= ruleString_Value
            {
             
            	        newCompositeNode(grammarAccess.getMdfDomainAccess().getMetamodelVersionString_ValueParserRuleCall_7_0()); 
            	    
            pushFollow(FollowSets000.FOLLOW_ruleString_Value_in_ruleMdfDomain254);
            lv_metamodelVersion_7_0=ruleString_Value();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getMdfDomainRule());
            	        }
                   		set(
                   			current, 
                   			"metamodelVersion",
                    		lv_metamodelVersion_7_0, 
                    		"String_Value");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:183:2: (otherlv_8= 'deprecationInfo' ruleMdfDeprecationInfo )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==14) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:183:4: otherlv_8= 'deprecationInfo' ruleMdfDeprecationInfo
                    {
                    otherlv_8=(Token)match(input,14,FollowSets000.FOLLOW_14_in_ruleMdfDomain267); 

                        	newLeafNode(otherlv_8, grammarAccess.getMdfDomainAccess().getDeprecationInfoKeyword_8_0());
                        
                     
                            newCompositeNode(grammarAccess.getMdfDomainAccess().getMdfDeprecationInfoParserRuleCall_8_1()); 
                        
                    pushFollow(FollowSets000.FOLLOW_ruleMdfDeprecationInfo_in_ruleMdfDomain283);
                    ruleMdfDeprecationInfo();

                    state._fsp--;

                     
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;

            }

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:195:3: (otherlv_10= 'Classes' otherlv_11= '{' ( (lv_classes_12_0= ruleMdfClass ) )+ otherlv_13= '}' )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==15) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:195:5: otherlv_10= 'Classes' otherlv_11= '{' ( (lv_classes_12_0= ruleMdfClass ) )+ otherlv_13= '}'
                    {
                    otherlv_10=(Token)match(input,15,FollowSets000.FOLLOW_15_in_ruleMdfDomain297); 

                        	newLeafNode(otherlv_10, grammarAccess.getMdfDomainAccess().getClassesKeyword_9_0());
                        
                    otherlv_11=(Token)match(input,16,FollowSets000.FOLLOW_16_in_ruleMdfDomain309); 

                        	newLeafNode(otherlv_11, grammarAccess.getMdfDomainAccess().getLeftCurlyBracketKeyword_9_1());
                        
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:203:1: ( (lv_classes_12_0= ruleMdfClass ) )+
                    int cnt4=0;
                    loop4:
                    do {
                        int alt4=2;
                        int LA4_0 = input.LA(1);

                        if ( ((LA4_0>=RULE_ID && LA4_0<=RULE_VALUE)||(LA4_0>=28 && LA4_0<=29)) ) {
                            alt4=1;
                        }


                        switch (alt4) {
                    	case 1 :
                    	    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:204:1: (lv_classes_12_0= ruleMdfClass )
                    	    {
                    	    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:204:1: (lv_classes_12_0= ruleMdfClass )
                    	    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:205:3: lv_classes_12_0= ruleMdfClass
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getMdfDomainAccess().getClassesMdfClassParserRuleCall_9_2_0()); 
                    	    	    
                    	    pushFollow(FollowSets000.FOLLOW_ruleMdfClass_in_ruleMdfDomain330);
                    	    lv_classes_12_0=ruleMdfClass();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getMdfDomainRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"classes",
                    	            		lv_classes_12_0, 
                    	            		"MdfClass");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt4 >= 1 ) break loop4;
                                EarlyExitException eee =
                                    new EarlyExitException(4, input);
                                throw eee;
                        }
                        cnt4++;
                    } while (true);

                    otherlv_13=(Token)match(input,17,FollowSets000.FOLLOW_17_in_ruleMdfDomain343); 

                        	newLeafNode(otherlv_13, grammarAccess.getMdfDomainAccess().getRightCurlyBracketKeyword_9_3());
                        

                    }
                    break;

            }

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:225:3: (otherlv_14= 'Datasets' otherlv_15= '{' ( (lv_datasets_16_0= ruleMdfDataset ) )+ otherlv_17= '}' )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==18) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:225:5: otherlv_14= 'Datasets' otherlv_15= '{' ( (lv_datasets_16_0= ruleMdfDataset ) )+ otherlv_17= '}'
                    {
                    otherlv_14=(Token)match(input,18,FollowSets000.FOLLOW_18_in_ruleMdfDomain358); 

                        	newLeafNode(otherlv_14, grammarAccess.getMdfDomainAccess().getDatasetsKeyword_10_0());
                        
                    otherlv_15=(Token)match(input,16,FollowSets000.FOLLOW_16_in_ruleMdfDomain370); 

                        	newLeafNode(otherlv_15, grammarAccess.getMdfDomainAccess().getLeftCurlyBracketKeyword_10_1());
                        
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:233:1: ( (lv_datasets_16_0= ruleMdfDataset ) )+
                    int cnt6=0;
                    loop6:
                    do {
                        int alt6=2;
                        int LA6_0 = input.LA(1);

                        if ( ((LA6_0>=RULE_ID && LA6_0<=RULE_VALUE)) ) {
                            alt6=1;
                        }


                        switch (alt6) {
                    	case 1 :
                    	    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:234:1: (lv_datasets_16_0= ruleMdfDataset )
                    	    {
                    	    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:234:1: (lv_datasets_16_0= ruleMdfDataset )
                    	    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:235:3: lv_datasets_16_0= ruleMdfDataset
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getMdfDomainAccess().getDatasetsMdfDatasetParserRuleCall_10_2_0()); 
                    	    	    
                    	    pushFollow(FollowSets000.FOLLOW_ruleMdfDataset_in_ruleMdfDomain391);
                    	    lv_datasets_16_0=ruleMdfDataset();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getMdfDomainRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"datasets",
                    	            		lv_datasets_16_0, 
                    	            		"MdfDataset");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt6 >= 1 ) break loop6;
                                EarlyExitException eee =
                                    new EarlyExitException(6, input);
                                throw eee;
                        }
                        cnt6++;
                    } while (true);

                    otherlv_17=(Token)match(input,17,FollowSets000.FOLLOW_17_in_ruleMdfDomain404); 

                        	newLeafNode(otherlv_17, grammarAccess.getMdfDomainAccess().getRightCurlyBracketKeyword_10_3());
                        

                    }
                    break;

            }

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:255:3: (otherlv_18= 'BusinessTypes' otherlv_19= '{' ( (lv_businessTypes_20_0= ruleMdfBusinessType ) )+ otherlv_21= '}' )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==19) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:255:5: otherlv_18= 'BusinessTypes' otherlv_19= '{' ( (lv_businessTypes_20_0= ruleMdfBusinessType ) )+ otherlv_21= '}'
                    {
                    otherlv_18=(Token)match(input,19,FollowSets000.FOLLOW_19_in_ruleMdfDomain419); 

                        	newLeafNode(otherlv_18, grammarAccess.getMdfDomainAccess().getBusinessTypesKeyword_11_0());
                        
                    otherlv_19=(Token)match(input,16,FollowSets000.FOLLOW_16_in_ruleMdfDomain431); 

                        	newLeafNode(otherlv_19, grammarAccess.getMdfDomainAccess().getLeftCurlyBracketKeyword_11_1());
                        
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:263:1: ( (lv_businessTypes_20_0= ruleMdfBusinessType ) )+
                    int cnt8=0;
                    loop8:
                    do {
                        int alt8=2;
                        int LA8_0 = input.LA(1);

                        if ( ((LA8_0>=RULE_ID && LA8_0<=RULE_VALUE)) ) {
                            alt8=1;
                        }


                        switch (alt8) {
                    	case 1 :
                    	    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:264:1: (lv_businessTypes_20_0= ruleMdfBusinessType )
                    	    {
                    	    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:264:1: (lv_businessTypes_20_0= ruleMdfBusinessType )
                    	    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:265:3: lv_businessTypes_20_0= ruleMdfBusinessType
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getMdfDomainAccess().getBusinessTypesMdfBusinessTypeParserRuleCall_11_2_0()); 
                    	    	    
                    	    pushFollow(FollowSets000.FOLLOW_ruleMdfBusinessType_in_ruleMdfDomain452);
                    	    lv_businessTypes_20_0=ruleMdfBusinessType();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getMdfDomainRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"businessTypes",
                    	            		lv_businessTypes_20_0, 
                    	            		"MdfBusinessType");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt8 >= 1 ) break loop8;
                                EarlyExitException eee =
                                    new EarlyExitException(8, input);
                                throw eee;
                        }
                        cnt8++;
                    } while (true);

                    otherlv_21=(Token)match(input,17,FollowSets000.FOLLOW_17_in_ruleMdfDomain465); 

                        	newLeafNode(otherlv_21, grammarAccess.getMdfDomainAccess().getRightCurlyBracketKeyword_11_3());
                        

                    }
                    break;

            }

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:285:3: (otherlv_22= 'Enumerations' otherlv_23= '{' ( (lv_enumerations_24_0= ruleMdfEnumeration ) )+ otherlv_25= '}' )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==20) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:285:5: otherlv_22= 'Enumerations' otherlv_23= '{' ( (lv_enumerations_24_0= ruleMdfEnumeration ) )+ otherlv_25= '}'
                    {
                    otherlv_22=(Token)match(input,20,FollowSets000.FOLLOW_20_in_ruleMdfDomain480); 

                        	newLeafNode(otherlv_22, grammarAccess.getMdfDomainAccess().getEnumerationsKeyword_12_0());
                        
                    otherlv_23=(Token)match(input,16,FollowSets000.FOLLOW_16_in_ruleMdfDomain492); 

                        	newLeafNode(otherlv_23, grammarAccess.getMdfDomainAccess().getLeftCurlyBracketKeyword_12_1());
                        
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:293:1: ( (lv_enumerations_24_0= ruleMdfEnumeration ) )+
                    int cnt10=0;
                    loop10:
                    do {
                        int alt10=2;
                        int LA10_0 = input.LA(1);

                        if ( ((LA10_0>=RULE_ID && LA10_0<=RULE_VALUE)) ) {
                            alt10=1;
                        }


                        switch (alt10) {
                    	case 1 :
                    	    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:294:1: (lv_enumerations_24_0= ruleMdfEnumeration )
                    	    {
                    	    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:294:1: (lv_enumerations_24_0= ruleMdfEnumeration )
                    	    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:295:3: lv_enumerations_24_0= ruleMdfEnumeration
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getMdfDomainAccess().getEnumerationsMdfEnumerationParserRuleCall_12_2_0()); 
                    	    	    
                    	    pushFollow(FollowSets000.FOLLOW_ruleMdfEnumeration_in_ruleMdfDomain513);
                    	    lv_enumerations_24_0=ruleMdfEnumeration();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getMdfDomainRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"enumerations",
                    	            		lv_enumerations_24_0, 
                    	            		"MdfEnumeration");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt10 >= 1 ) break loop10;
                                EarlyExitException eee =
                                    new EarlyExitException(10, input);
                                throw eee;
                        }
                        cnt10++;
                    } while (true);

                    otherlv_25=(Token)match(input,17,FollowSets000.FOLLOW_17_in_ruleMdfDomain526); 

                        	newLeafNode(otherlv_25, grammarAccess.getMdfDomainAccess().getRightCurlyBracketKeyword_12_3());
                        

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
    // $ANTLR end "ruleMdfDomain"


    // $ANTLR start "entryRuleMdfProperty"
    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:325:1: entryRuleMdfProperty returns [EObject current=null] : iv_ruleMdfProperty= ruleMdfProperty EOF ;
    public final EObject entryRuleMdfProperty() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMdfProperty = null;


        try {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:326:2: (iv_ruleMdfProperty= ruleMdfProperty EOF )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:327:2: iv_ruleMdfProperty= ruleMdfProperty EOF
            {
             newCompositeNode(grammarAccess.getMdfPropertyRule()); 
            pushFollow(FollowSets000.FOLLOW_ruleMdfProperty_in_entryRuleMdfProperty566);
            iv_ruleMdfProperty=ruleMdfProperty();

            state._fsp--;

             current =iv_ruleMdfProperty; 
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleMdfProperty576); 

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
    // $ANTLR end "entryRuleMdfProperty"


    // $ANTLR start "ruleMdfProperty"
    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:334:1: ruleMdfProperty returns [EObject current=null] : (this_MdfAttribute_0= ruleMdfAttribute | this_MdfAssociation_1= ruleMdfAssociation | this_MdfReverseAssociation_2= ruleMdfReverseAssociation ) ;
    public final EObject ruleMdfProperty() throws RecognitionException {
        EObject current = null;

        EObject this_MdfAttribute_0 = null;

        EObject this_MdfAssociation_1 = null;

        EObject this_MdfReverseAssociation_2 = null;


         enterRule(); 
            
        try {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:337:28: ( (this_MdfAttribute_0= ruleMdfAttribute | this_MdfAssociation_1= ruleMdfAssociation | this_MdfReverseAssociation_2= ruleMdfReverseAssociation ) )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:338:1: (this_MdfAttribute_0= ruleMdfAttribute | this_MdfAssociation_1= ruleMdfAssociation | this_MdfReverseAssociation_2= ruleMdfReverseAssociation )
            {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:338:1: (this_MdfAttribute_0= ruleMdfAttribute | this_MdfAssociation_1= ruleMdfAssociation | this_MdfReverseAssociation_2= ruleMdfReverseAssociation )
            int alt12=3;
            switch ( input.LA(1) ) {
            case RULE_ML_DOC:
                {
                switch ( input.LA(2) ) {
                case RULE_STRING:
                    {
                    switch ( input.LA(3) ) {
                    case 42:
                        {
                        alt12=3;
                        }
                        break;
                    case 24:
                        {
                        alt12=1;
                        }
                        break;
                    case 40:
                        {
                        alt12=2;
                        }
                        break;
                    default:
                        NoViableAltException nvae =
                            new NoViableAltException("", 12, 2, input);

                        throw nvae;
                    }

                    }
                    break;
                case RULE_ID:
                    {
                    switch ( input.LA(3) ) {
                    case 40:
                        {
                        alt12=2;
                        }
                        break;
                    case 24:
                        {
                        alt12=1;
                        }
                        break;
                    case 42:
                        {
                        alt12=3;
                        }
                        break;
                    default:
                        NoViableAltException nvae =
                            new NoViableAltException("", 12, 3, input);

                        throw nvae;
                    }

                    }
                    break;
                case RULE_VALUE:
                    {
                    switch ( input.LA(3) ) {
                    case 40:
                        {
                        alt12=2;
                        }
                        break;
                    case 42:
                        {
                        alt12=3;
                        }
                        break;
                    case 24:
                        {
                        alt12=1;
                        }
                        break;
                    default:
                        NoViableAltException nvae =
                            new NoViableAltException("", 12, 4, input);

                        throw nvae;
                    }

                    }
                    break;
                default:
                    NoViableAltException nvae =
                        new NoViableAltException("", 12, 1, input);

                    throw nvae;
                }

                }
                break;
            case RULE_STRING:
                {
                switch ( input.LA(2) ) {
                case 42:
                    {
                    alt12=3;
                    }
                    break;
                case 24:
                    {
                    alt12=1;
                    }
                    break;
                case 40:
                    {
                    alt12=2;
                    }
                    break;
                default:
                    NoViableAltException nvae =
                        new NoViableAltException("", 12, 2, input);

                    throw nvae;
                }

                }
                break;
            case RULE_ID:
                {
                switch ( input.LA(2) ) {
                case 40:
                    {
                    alt12=2;
                    }
                    break;
                case 24:
                    {
                    alt12=1;
                    }
                    break;
                case 42:
                    {
                    alt12=3;
                    }
                    break;
                default:
                    NoViableAltException nvae =
                        new NoViableAltException("", 12, 3, input);

                    throw nvae;
                }

                }
                break;
            case RULE_VALUE:
                {
                switch ( input.LA(2) ) {
                case 40:
                    {
                    alt12=2;
                    }
                    break;
                case 42:
                    {
                    alt12=3;
                    }
                    break;
                case 24:
                    {
                    alt12=1;
                    }
                    break;
                default:
                    NoViableAltException nvae =
                        new NoViableAltException("", 12, 4, input);

                    throw nvae;
                }

                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 12, 0, input);

                throw nvae;
            }

            switch (alt12) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:339:5: this_MdfAttribute_0= ruleMdfAttribute
                    {
                     
                            newCompositeNode(grammarAccess.getMdfPropertyAccess().getMdfAttributeParserRuleCall_0()); 
                        
                    pushFollow(FollowSets000.FOLLOW_ruleMdfAttribute_in_ruleMdfProperty623);
                    this_MdfAttribute_0=ruleMdfAttribute();

                    state._fsp--;

                     
                            current = this_MdfAttribute_0; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 2 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:349:5: this_MdfAssociation_1= ruleMdfAssociation
                    {
                     
                            newCompositeNode(grammarAccess.getMdfPropertyAccess().getMdfAssociationParserRuleCall_1()); 
                        
                    pushFollow(FollowSets000.FOLLOW_ruleMdfAssociation_in_ruleMdfProperty650);
                    this_MdfAssociation_1=ruleMdfAssociation();

                    state._fsp--;

                     
                            current = this_MdfAssociation_1; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 3 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:359:5: this_MdfReverseAssociation_2= ruleMdfReverseAssociation
                    {
                     
                            newCompositeNode(grammarAccess.getMdfPropertyAccess().getMdfReverseAssociationParserRuleCall_2()); 
                        
                    pushFollow(FollowSets000.FOLLOW_ruleMdfReverseAssociation_in_ruleMdfProperty677);
                    this_MdfReverseAssociation_2=ruleMdfReverseAssociation();

                    state._fsp--;

                     
                            current = this_MdfReverseAssociation_2; 
                            afterParserOrEnumRuleCall();
                        

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
    // $ANTLR end "ruleMdfProperty"


    // $ANTLR start "entryRuleMdfDatasetProperty"
    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:375:1: entryRuleMdfDatasetProperty returns [EObject current=null] : iv_ruleMdfDatasetProperty= ruleMdfDatasetProperty EOF ;
    public final EObject entryRuleMdfDatasetProperty() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMdfDatasetProperty = null;


        try {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:376:2: (iv_ruleMdfDatasetProperty= ruleMdfDatasetProperty EOF )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:377:2: iv_ruleMdfDatasetProperty= ruleMdfDatasetProperty EOF
            {
             newCompositeNode(grammarAccess.getMdfDatasetPropertyRule()); 
            pushFollow(FollowSets000.FOLLOW_ruleMdfDatasetProperty_in_entryRuleMdfDatasetProperty712);
            iv_ruleMdfDatasetProperty=ruleMdfDatasetProperty();

            state._fsp--;

             current =iv_ruleMdfDatasetProperty; 
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleMdfDatasetProperty722); 

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
    // $ANTLR end "entryRuleMdfDatasetProperty"


    // $ANTLR start "ruleMdfDatasetProperty"
    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:384:1: ruleMdfDatasetProperty returns [EObject current=null] : (this_MdfDatasetDerivedProperty_0= ruleMdfDatasetDerivedProperty | this_MdfDatasetMappedProperty_1= ruleMdfDatasetMappedProperty ) ;
    public final EObject ruleMdfDatasetProperty() throws RecognitionException {
        EObject current = null;

        EObject this_MdfDatasetDerivedProperty_0 = null;

        EObject this_MdfDatasetMappedProperty_1 = null;


         enterRule(); 
            
        try {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:387:28: ( (this_MdfDatasetDerivedProperty_0= ruleMdfDatasetDerivedProperty | this_MdfDatasetMappedProperty_1= ruleMdfDatasetMappedProperty ) )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:388:1: (this_MdfDatasetDerivedProperty_0= ruleMdfDatasetDerivedProperty | this_MdfDatasetMappedProperty_1= ruleMdfDatasetMappedProperty )
            {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:388:1: (this_MdfDatasetDerivedProperty_0= ruleMdfDatasetDerivedProperty | this_MdfDatasetMappedProperty_1= ruleMdfDatasetMappedProperty )
            int alt13=2;
            switch ( input.LA(1) ) {
            case RULE_ML_DOC:
                {
                switch ( input.LA(2) ) {
                case RULE_STRING:
                    {
                    int LA13_2 = input.LA(3);

                    if ( (LA13_2==40||LA13_2==43||LA13_2==46) ) {
                        alt13=2;
                    }
                    else if ( (LA13_2==24) ) {
                        alt13=1;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 13, 2, input);

                        throw nvae;
                    }
                    }
                    break;
                case RULE_ID:
                    {
                    int LA13_3 = input.LA(3);

                    if ( (LA13_3==24) ) {
                        alt13=1;
                    }
                    else if ( (LA13_3==40||LA13_3==43||LA13_3==46) ) {
                        alt13=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 13, 3, input);

                        throw nvae;
                    }
                    }
                    break;
                case RULE_VALUE:
                    {
                    int LA13_4 = input.LA(3);

                    if ( (LA13_4==24) ) {
                        alt13=1;
                    }
                    else if ( (LA13_4==40||LA13_4==43||LA13_4==46) ) {
                        alt13=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 13, 4, input);

                        throw nvae;
                    }
                    }
                    break;
                default:
                    NoViableAltException nvae =
                        new NoViableAltException("", 13, 1, input);

                    throw nvae;
                }

                }
                break;
            case RULE_STRING:
                {
                int LA13_2 = input.LA(2);

                if ( (LA13_2==40||LA13_2==43||LA13_2==46) ) {
                    alt13=2;
                }
                else if ( (LA13_2==24) ) {
                    alt13=1;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 13, 2, input);

                    throw nvae;
                }
                }
                break;
            case RULE_ID:
                {
                int LA13_3 = input.LA(2);

                if ( (LA13_3==24) ) {
                    alt13=1;
                }
                else if ( (LA13_3==40||LA13_3==43||LA13_3==46) ) {
                    alt13=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 13, 3, input);

                    throw nvae;
                }
                }
                break;
            case RULE_VALUE:
                {
                int LA13_4 = input.LA(2);

                if ( (LA13_4==24) ) {
                    alt13=1;
                }
                else if ( (LA13_4==40||LA13_4==43||LA13_4==46) ) {
                    alt13=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 13, 4, input);

                    throw nvae;
                }
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 13, 0, input);

                throw nvae;
            }

            switch (alt13) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:389:5: this_MdfDatasetDerivedProperty_0= ruleMdfDatasetDerivedProperty
                    {
                     
                            newCompositeNode(grammarAccess.getMdfDatasetPropertyAccess().getMdfDatasetDerivedPropertyParserRuleCall_0()); 
                        
                    pushFollow(FollowSets000.FOLLOW_ruleMdfDatasetDerivedProperty_in_ruleMdfDatasetProperty769);
                    this_MdfDatasetDerivedProperty_0=ruleMdfDatasetDerivedProperty();

                    state._fsp--;

                     
                            current = this_MdfDatasetDerivedProperty_0; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 2 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:399:5: this_MdfDatasetMappedProperty_1= ruleMdfDatasetMappedProperty
                    {
                     
                            newCompositeNode(grammarAccess.getMdfDatasetPropertyAccess().getMdfDatasetMappedPropertyParserRuleCall_1()); 
                        
                    pushFollow(FollowSets000.FOLLOW_ruleMdfDatasetMappedProperty_in_ruleMdfDatasetProperty796);
                    this_MdfDatasetMappedProperty_1=ruleMdfDatasetMappedProperty();

                    state._fsp--;

                     
                            current = this_MdfDatasetMappedProperty_1; 
                            afterParserOrEnumRuleCall();
                        

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
    // $ANTLR end "ruleMdfDatasetProperty"


    // $ANTLR start "entryRuleMdfDeprecationInfo"
    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:417:1: entryRuleMdfDeprecationInfo returns [String current=null] : iv_ruleMdfDeprecationInfo= ruleMdfDeprecationInfo EOF ;
    public final String entryRuleMdfDeprecationInfo() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleMdfDeprecationInfo = null;


        try {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:418:2: (iv_ruleMdfDeprecationInfo= ruleMdfDeprecationInfo EOF )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:419:2: iv_ruleMdfDeprecationInfo= ruleMdfDeprecationInfo EOF
            {
             newCompositeNode(grammarAccess.getMdfDeprecationInfoRule()); 
            pushFollow(FollowSets000.FOLLOW_ruleMdfDeprecationInfo_in_entryRuleMdfDeprecationInfo834);
            iv_ruleMdfDeprecationInfo=ruleMdfDeprecationInfo();

            state._fsp--;

             current =iv_ruleMdfDeprecationInfo.getText(); 
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleMdfDeprecationInfo845); 

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
    // $ANTLR end "entryRuleMdfDeprecationInfo"


    // $ANTLR start "ruleMdfDeprecationInfo"
    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:426:1: ruleMdfDeprecationInfo returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'version=' this_String_Value_1= ruleString_Value kw= 'comment=' this_String_Value_3= ruleString_Value ) ;
    public final AntlrDatatypeRuleToken ruleMdfDeprecationInfo() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_String_Value_1 = null;

        AntlrDatatypeRuleToken this_String_Value_3 = null;


         enterRule(); 
            
        try {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:429:28: ( (kw= 'version=' this_String_Value_1= ruleString_Value kw= 'comment=' this_String_Value_3= ruleString_Value ) )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:430:1: (kw= 'version=' this_String_Value_1= ruleString_Value kw= 'comment=' this_String_Value_3= ruleString_Value )
            {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:430:1: (kw= 'version=' this_String_Value_1= ruleString_Value kw= 'comment=' this_String_Value_3= ruleString_Value )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:431:2: kw= 'version=' this_String_Value_1= ruleString_Value kw= 'comment=' this_String_Value_3= ruleString_Value
            {
            kw=(Token)match(input,21,FollowSets000.FOLLOW_21_in_ruleMdfDeprecationInfo883); 

                    current.merge(kw);
                    newLeafNode(kw, grammarAccess.getMdfDeprecationInfoAccess().getVersionKeyword_0()); 
                
             
                    newCompositeNode(grammarAccess.getMdfDeprecationInfoAccess().getString_ValueParserRuleCall_1()); 
                
            pushFollow(FollowSets000.FOLLOW_ruleString_Value_in_ruleMdfDeprecationInfo905);
            this_String_Value_1=ruleString_Value();

            state._fsp--;


            		current.merge(this_String_Value_1);
                
             
                    afterParserOrEnumRuleCall();
                
            kw=(Token)match(input,22,FollowSets000.FOLLOW_22_in_ruleMdfDeprecationInfo923); 

                    current.merge(kw);
                    newLeafNode(kw, grammarAccess.getMdfDeprecationInfoAccess().getCommentKeyword_2()); 
                
             
                    newCompositeNode(grammarAccess.getMdfDeprecationInfoAccess().getString_ValueParserRuleCall_3()); 
                
            pushFollow(FollowSets000.FOLLOW_ruleString_Value_in_ruleMdfDeprecationInfo945);
            this_String_Value_3=ruleString_Value();

            state._fsp--;


            		current.merge(this_String_Value_3);
                
             
                    afterParserOrEnumRuleCall();
                

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
    // $ANTLR end "ruleMdfDeprecationInfo"


    // $ANTLR start "entryRuleMdfAnnotation"
    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:472:1: entryRuleMdfAnnotation returns [EObject current=null] : iv_ruleMdfAnnotation= ruleMdfAnnotation EOF ;
    public final EObject entryRuleMdfAnnotation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMdfAnnotation = null;


        try {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:473:2: (iv_ruleMdfAnnotation= ruleMdfAnnotation EOF )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:474:2: iv_ruleMdfAnnotation= ruleMdfAnnotation EOF
            {
             newCompositeNode(grammarAccess.getMdfAnnotationRule()); 
            pushFollow(FollowSets000.FOLLOW_ruleMdfAnnotation_in_entryRuleMdfAnnotation990);
            iv_ruleMdfAnnotation=ruleMdfAnnotation();

            state._fsp--;

             current =iv_ruleMdfAnnotation; 
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleMdfAnnotation1000); 

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
    // $ANTLR end "entryRuleMdfAnnotation"


    // $ANTLR start "ruleMdfAnnotation"
    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:481:1: ruleMdfAnnotation returns [EObject current=null] : (otherlv_0= '@' ( (lv_namespace_1_0= ruleNamespace ) ) otherlv_2= ':' ( (lv_name_3_0= RULE_ID ) ) (otherlv_4= '(' ( (lv_properties_5_0= ruleMdfAnnotationProperty ) ) (otherlv_6= ',' ( (lv_properties_7_0= ruleMdfAnnotationProperty ) ) )* otherlv_8= ')' )? ) ;
    public final EObject ruleMdfAnnotation() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token lv_name_3_0=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        AntlrDatatypeRuleToken lv_namespace_1_0 = null;

        EObject lv_properties_5_0 = null;

        EObject lv_properties_7_0 = null;


         enterRule(); 
            
        try {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:484:28: ( (otherlv_0= '@' ( (lv_namespace_1_0= ruleNamespace ) ) otherlv_2= ':' ( (lv_name_3_0= RULE_ID ) ) (otherlv_4= '(' ( (lv_properties_5_0= ruleMdfAnnotationProperty ) ) (otherlv_6= ',' ( (lv_properties_7_0= ruleMdfAnnotationProperty ) ) )* otherlv_8= ')' )? ) )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:485:1: (otherlv_0= '@' ( (lv_namespace_1_0= ruleNamespace ) ) otherlv_2= ':' ( (lv_name_3_0= RULE_ID ) ) (otherlv_4= '(' ( (lv_properties_5_0= ruleMdfAnnotationProperty ) ) (otherlv_6= ',' ( (lv_properties_7_0= ruleMdfAnnotationProperty ) ) )* otherlv_8= ')' )? )
            {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:485:1: (otherlv_0= '@' ( (lv_namespace_1_0= ruleNamespace ) ) otherlv_2= ':' ( (lv_name_3_0= RULE_ID ) ) (otherlv_4= '(' ( (lv_properties_5_0= ruleMdfAnnotationProperty ) ) (otherlv_6= ',' ( (lv_properties_7_0= ruleMdfAnnotationProperty ) ) )* otherlv_8= ')' )? )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:485:3: otherlv_0= '@' ( (lv_namespace_1_0= ruleNamespace ) ) otherlv_2= ':' ( (lv_name_3_0= RULE_ID ) ) (otherlv_4= '(' ( (lv_properties_5_0= ruleMdfAnnotationProperty ) ) (otherlv_6= ',' ( (lv_properties_7_0= ruleMdfAnnotationProperty ) ) )* otherlv_8= ')' )?
            {
            otherlv_0=(Token)match(input,23,FollowSets000.FOLLOW_23_in_ruleMdfAnnotation1037); 

                	newLeafNode(otherlv_0, grammarAccess.getMdfAnnotationAccess().getCommercialAtKeyword_0());
                
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:489:1: ( (lv_namespace_1_0= ruleNamespace ) )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:490:1: (lv_namespace_1_0= ruleNamespace )
            {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:490:1: (lv_namespace_1_0= ruleNamespace )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:491:3: lv_namespace_1_0= ruleNamespace
            {
             
            	        newCompositeNode(grammarAccess.getMdfAnnotationAccess().getNamespaceNamespaceParserRuleCall_1_0()); 
            	    
            pushFollow(FollowSets000.FOLLOW_ruleNamespace_in_ruleMdfAnnotation1058);
            lv_namespace_1_0=ruleNamespace();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getMdfAnnotationRule());
            	        }
                   		set(
                   			current, 
                   			"namespace",
                    		lv_namespace_1_0, 
                    		"Namespace");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_2=(Token)match(input,24,FollowSets000.FOLLOW_24_in_ruleMdfAnnotation1070); 

                	newLeafNode(otherlv_2, grammarAccess.getMdfAnnotationAccess().getColonKeyword_2());
                
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:511:1: ( (lv_name_3_0= RULE_ID ) )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:512:1: (lv_name_3_0= RULE_ID )
            {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:512:1: (lv_name_3_0= RULE_ID )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:513:3: lv_name_3_0= RULE_ID
            {
            lv_name_3_0=(Token)match(input,RULE_ID,FollowSets000.FOLLOW_RULE_ID_in_ruleMdfAnnotation1087); 

            			newLeafNode(lv_name_3_0, grammarAccess.getMdfAnnotationAccess().getNameIDTerminalRuleCall_3_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getMdfAnnotationRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"name",
                    		lv_name_3_0, 
                    		"ID");
            	    

            }


            }

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:529:2: (otherlv_4= '(' ( (lv_properties_5_0= ruleMdfAnnotationProperty ) ) (otherlv_6= ',' ( (lv_properties_7_0= ruleMdfAnnotationProperty ) ) )* otherlv_8= ')' )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==25) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:529:4: otherlv_4= '(' ( (lv_properties_5_0= ruleMdfAnnotationProperty ) ) (otherlv_6= ',' ( (lv_properties_7_0= ruleMdfAnnotationProperty ) ) )* otherlv_8= ')'
                    {
                    otherlv_4=(Token)match(input,25,FollowSets000.FOLLOW_25_in_ruleMdfAnnotation1105); 

                        	newLeafNode(otherlv_4, grammarAccess.getMdfAnnotationAccess().getLeftParenthesisKeyword_4_0());
                        
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:533:1: ( (lv_properties_5_0= ruleMdfAnnotationProperty ) )
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:534:1: (lv_properties_5_0= ruleMdfAnnotationProperty )
                    {
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:534:1: (lv_properties_5_0= ruleMdfAnnotationProperty )
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:535:3: lv_properties_5_0= ruleMdfAnnotationProperty
                    {
                     
                    	        newCompositeNode(grammarAccess.getMdfAnnotationAccess().getPropertiesMdfAnnotationPropertyParserRuleCall_4_1_0()); 
                    	    
                    pushFollow(FollowSets000.FOLLOW_ruleMdfAnnotationProperty_in_ruleMdfAnnotation1126);
                    lv_properties_5_0=ruleMdfAnnotationProperty();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getMdfAnnotationRule());
                    	        }
                           		add(
                           			current, 
                           			"properties",
                            		lv_properties_5_0, 
                            		"MdfAnnotationProperty");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:551:2: (otherlv_6= ',' ( (lv_properties_7_0= ruleMdfAnnotationProperty ) ) )*
                    loop14:
                    do {
                        int alt14=2;
                        int LA14_0 = input.LA(1);

                        if ( (LA14_0==26) ) {
                            alt14=1;
                        }


                        switch (alt14) {
                    	case 1 :
                    	    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:551:4: otherlv_6= ',' ( (lv_properties_7_0= ruleMdfAnnotationProperty ) )
                    	    {
                    	    otherlv_6=(Token)match(input,26,FollowSets000.FOLLOW_26_in_ruleMdfAnnotation1139); 

                    	        	newLeafNode(otherlv_6, grammarAccess.getMdfAnnotationAccess().getCommaKeyword_4_2_0());
                    	        
                    	    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:555:1: ( (lv_properties_7_0= ruleMdfAnnotationProperty ) )
                    	    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:556:1: (lv_properties_7_0= ruleMdfAnnotationProperty )
                    	    {
                    	    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:556:1: (lv_properties_7_0= ruleMdfAnnotationProperty )
                    	    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:557:3: lv_properties_7_0= ruleMdfAnnotationProperty
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getMdfAnnotationAccess().getPropertiesMdfAnnotationPropertyParserRuleCall_4_2_1_0()); 
                    	    	    
                    	    pushFollow(FollowSets000.FOLLOW_ruleMdfAnnotationProperty_in_ruleMdfAnnotation1160);
                    	    lv_properties_7_0=ruleMdfAnnotationProperty();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getMdfAnnotationRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"properties",
                    	            		lv_properties_7_0, 
                    	            		"MdfAnnotationProperty");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop14;
                        }
                    } while (true);

                    otherlv_8=(Token)match(input,27,FollowSets000.FOLLOW_27_in_ruleMdfAnnotation1174); 

                        	newLeafNode(otherlv_8, grammarAccess.getMdfAnnotationAccess().getRightParenthesisKeyword_4_3());
                        

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
    // $ANTLR end "ruleMdfAnnotation"


    // $ANTLR start "entryRuleNamespace"
    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:585:1: entryRuleNamespace returns [String current=null] : iv_ruleNamespace= ruleNamespace EOF ;
    public final String entryRuleNamespace() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleNamespace = null;


        try {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:586:2: (iv_ruleNamespace= ruleNamespace EOF )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:587:2: iv_ruleNamespace= ruleNamespace EOF
            {
             newCompositeNode(grammarAccess.getNamespaceRule()); 
            pushFollow(FollowSets000.FOLLOW_ruleNamespace_in_entryRuleNamespace1213);
            iv_ruleNamespace=ruleNamespace();

            state._fsp--;

             current =iv_ruleNamespace.getText(); 
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleNamespace1224); 

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
    // $ANTLR end "entryRuleNamespace"


    // $ANTLR start "ruleNamespace"
    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:594:1: ruleNamespace returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_String_Value_0= ruleString_Value ;
    public final AntlrDatatypeRuleToken ruleNamespace() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_String_Value_0 = null;


         enterRule(); 
            
        try {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:597:28: (this_String_Value_0= ruleString_Value )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:599:5: this_String_Value_0= ruleString_Value
            {
             
                    newCompositeNode(grammarAccess.getNamespaceAccess().getString_ValueParserRuleCall()); 
                
            pushFollow(FollowSets000.FOLLOW_ruleString_Value_in_ruleNamespace1270);
            this_String_Value_0=ruleString_Value();

            state._fsp--;


            		current.merge(this_String_Value_0);
                
             
                    afterParserOrEnumRuleCall();
                

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
    // $ANTLR end "ruleNamespace"


    // $ANTLR start "entryRuleDocumentation"
    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:617:1: entryRuleDocumentation returns [String current=null] : iv_ruleDocumentation= ruleDocumentation EOF ;
    public final String entryRuleDocumentation() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleDocumentation = null;


        try {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:618:2: (iv_ruleDocumentation= ruleDocumentation EOF )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:619:2: iv_ruleDocumentation= ruleDocumentation EOF
            {
             newCompositeNode(grammarAccess.getDocumentationRule()); 
            pushFollow(FollowSets000.FOLLOW_ruleDocumentation_in_entryRuleDocumentation1315);
            iv_ruleDocumentation=ruleDocumentation();

            state._fsp--;

             current =iv_ruleDocumentation.getText(); 
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleDocumentation1326); 

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
    // $ANTLR end "entryRuleDocumentation"


    // $ANTLR start "ruleDocumentation"
    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:626:1: ruleDocumentation returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_ML_DOC_0= RULE_ML_DOC ;
    public final AntlrDatatypeRuleToken ruleDocumentation() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ML_DOC_0=null;

         enterRule(); 
            
        try {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:629:28: (this_ML_DOC_0= RULE_ML_DOC )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:630:5: this_ML_DOC_0= RULE_ML_DOC
            {
            this_ML_DOC_0=(Token)match(input,RULE_ML_DOC,FollowSets000.FOLLOW_RULE_ML_DOC_in_ruleDocumentation1365); 

            		current.merge(this_ML_DOC_0);
                
             
                newLeafNode(this_ML_DOC_0, grammarAccess.getDocumentationAccess().getML_DOCTerminalRuleCall()); 
                

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
    // $ANTLR end "ruleDocumentation"


    // $ANTLR start "entryRuleMdfClass"
    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:645:1: entryRuleMdfClass returns [EObject current=null] : iv_ruleMdfClass= ruleMdfClass EOF ;
    public final EObject entryRuleMdfClass() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMdfClass = null;


        try {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:646:2: (iv_ruleMdfClass= ruleMdfClass EOF )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:647:2: iv_ruleMdfClass= ruleMdfClass EOF
            {
             newCompositeNode(grammarAccess.getMdfClassRule()); 
            pushFollow(FollowSets000.FOLLOW_ruleMdfClass_in_entryRuleMdfClass1409);
            iv_ruleMdfClass=ruleMdfClass();

            state._fsp--;

             current =iv_ruleMdfClass; 
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleMdfClass1419); 

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
    // $ANTLR end "entryRuleMdfClass"


    // $ANTLR start "ruleMdfClass"
    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:654:1: ruleMdfClass returns [EObject current=null] : ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_abstract_1_0= 'abstract' ) )? (otherlv_2= 'secured' )? ( (lv_name_3_0= ruleString_Value ) ) (otherlv_4= 'extends' ( ( ruleRef ) ) )? ( (lv_annotations_6_0= ruleMdfAnnotation ) )* (otherlv_7= 'deprecationInfo' ruleMdfDeprecationInfo )? (otherlv_9= '{' ( (lv_properties_10_0= ruleMdfProperty ) )* otherlv_11= '}' ) ) ;
    public final EObject ruleMdfClass() throws RecognitionException {
        EObject current = null;

        Token lv_abstract_1_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_7=null;
        Token otherlv_9=null;
        Token otherlv_11=null;
        AntlrDatatypeRuleToken lv_documentation_0_0 = null;

        AntlrDatatypeRuleToken lv_name_3_0 = null;

        EObject lv_annotations_6_0 = null;

        EObject lv_properties_10_0 = null;


         enterRule(); 
            
        try {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:657:28: ( ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_abstract_1_0= 'abstract' ) )? (otherlv_2= 'secured' )? ( (lv_name_3_0= ruleString_Value ) ) (otherlv_4= 'extends' ( ( ruleRef ) ) )? ( (lv_annotations_6_0= ruleMdfAnnotation ) )* (otherlv_7= 'deprecationInfo' ruleMdfDeprecationInfo )? (otherlv_9= '{' ( (lv_properties_10_0= ruleMdfProperty ) )* otherlv_11= '}' ) ) )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:658:1: ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_abstract_1_0= 'abstract' ) )? (otherlv_2= 'secured' )? ( (lv_name_3_0= ruleString_Value ) ) (otherlv_4= 'extends' ( ( ruleRef ) ) )? ( (lv_annotations_6_0= ruleMdfAnnotation ) )* (otherlv_7= 'deprecationInfo' ruleMdfDeprecationInfo )? (otherlv_9= '{' ( (lv_properties_10_0= ruleMdfProperty ) )* otherlv_11= '}' ) )
            {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:658:1: ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_abstract_1_0= 'abstract' ) )? (otherlv_2= 'secured' )? ( (lv_name_3_0= ruleString_Value ) ) (otherlv_4= 'extends' ( ( ruleRef ) ) )? ( (lv_annotations_6_0= ruleMdfAnnotation ) )* (otherlv_7= 'deprecationInfo' ruleMdfDeprecationInfo )? (otherlv_9= '{' ( (lv_properties_10_0= ruleMdfProperty ) )* otherlv_11= '}' ) )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:658:2: ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_abstract_1_0= 'abstract' ) )? (otherlv_2= 'secured' )? ( (lv_name_3_0= ruleString_Value ) ) (otherlv_4= 'extends' ( ( ruleRef ) ) )? ( (lv_annotations_6_0= ruleMdfAnnotation ) )* (otherlv_7= 'deprecationInfo' ruleMdfDeprecationInfo )? (otherlv_9= '{' ( (lv_properties_10_0= ruleMdfProperty ) )* otherlv_11= '}' )
            {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:658:2: ( (lv_documentation_0_0= ruleDocumentation ) )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==RULE_ML_DOC) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:659:1: (lv_documentation_0_0= ruleDocumentation )
                    {
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:659:1: (lv_documentation_0_0= ruleDocumentation )
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:660:3: lv_documentation_0_0= ruleDocumentation
                    {
                     
                    	        newCompositeNode(grammarAccess.getMdfClassAccess().getDocumentationDocumentationParserRuleCall_0_0()); 
                    	    
                    pushFollow(FollowSets000.FOLLOW_ruleDocumentation_in_ruleMdfClass1465);
                    lv_documentation_0_0=ruleDocumentation();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getMdfClassRule());
                    	        }
                           		set(
                           			current, 
                           			"documentation",
                            		lv_documentation_0_0, 
                            		"Documentation");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }
                    break;

            }

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:676:3: ( (lv_abstract_1_0= 'abstract' ) )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==28) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:677:1: (lv_abstract_1_0= 'abstract' )
                    {
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:677:1: (lv_abstract_1_0= 'abstract' )
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:678:3: lv_abstract_1_0= 'abstract'
                    {
                    lv_abstract_1_0=(Token)match(input,28,FollowSets000.FOLLOW_28_in_ruleMdfClass1484); 

                            newLeafNode(lv_abstract_1_0, grammarAccess.getMdfClassAccess().getAbstractAbstractKeyword_1_0());
                        

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getMdfClassRule());
                    	        }
                           		setWithLastConsumed(current, "abstract", true, "abstract");
                    	    

                    }


                    }
                    break;

            }

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:691:3: (otherlv_2= 'secured' )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==29) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:691:5: otherlv_2= 'secured'
                    {
                    otherlv_2=(Token)match(input,29,FollowSets000.FOLLOW_29_in_ruleMdfClass1511); 

                        	newLeafNode(otherlv_2, grammarAccess.getMdfClassAccess().getSecuredKeyword_2());
                        

                    }
                    break;

            }

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:695:3: ( (lv_name_3_0= ruleString_Value ) )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:696:1: (lv_name_3_0= ruleString_Value )
            {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:696:1: (lv_name_3_0= ruleString_Value )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:697:3: lv_name_3_0= ruleString_Value
            {
             
            	        newCompositeNode(grammarAccess.getMdfClassAccess().getNameString_ValueParserRuleCall_3_0()); 
            	    
            pushFollow(FollowSets000.FOLLOW_ruleString_Value_in_ruleMdfClass1534);
            lv_name_3_0=ruleString_Value();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getMdfClassRule());
            	        }
                   		set(
                   			current, 
                   			"name",
                    		lv_name_3_0, 
                    		"String_Value");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:713:2: (otherlv_4= 'extends' ( ( ruleRef ) ) )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==30) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:713:4: otherlv_4= 'extends' ( ( ruleRef ) )
                    {
                    otherlv_4=(Token)match(input,30,FollowSets000.FOLLOW_30_in_ruleMdfClass1547); 

                        	newLeafNode(otherlv_4, grammarAccess.getMdfClassAccess().getExtendsKeyword_4_0());
                        
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:717:1: ( ( ruleRef ) )
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:718:1: ( ruleRef )
                    {
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:718:1: ( ruleRef )
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:719:3: ruleRef
                    {

                    			if (current==null) {
                    	            current = createModelElement(grammarAccess.getMdfClassRule());
                    	        }
                            
                     
                    	        newCompositeNode(grammarAccess.getMdfClassAccess().getBaseClassMdfClassCrossReference_4_1_0()); 
                    	    
                    pushFollow(FollowSets000.FOLLOW_ruleRef_in_ruleMdfClass1570);
                    ruleRef();

                    state._fsp--;

                     
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }


                    }
                    break;

            }

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:732:4: ( (lv_annotations_6_0= ruleMdfAnnotation ) )*
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( (LA20_0==23) ) {
                    alt20=1;
                }


                switch (alt20) {
            	case 1 :
            	    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:733:1: (lv_annotations_6_0= ruleMdfAnnotation )
            	    {
            	    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:733:1: (lv_annotations_6_0= ruleMdfAnnotation )
            	    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:734:3: lv_annotations_6_0= ruleMdfAnnotation
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getMdfClassAccess().getAnnotationsMdfAnnotationParserRuleCall_5_0()); 
            	    	    
            	    pushFollow(FollowSets000.FOLLOW_ruleMdfAnnotation_in_ruleMdfClass1593);
            	    lv_annotations_6_0=ruleMdfAnnotation();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getMdfClassRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"annotations",
            	            		lv_annotations_6_0, 
            	            		"MdfAnnotation");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop20;
                }
            } while (true);

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:750:3: (otherlv_7= 'deprecationInfo' ruleMdfDeprecationInfo )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==14) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:750:5: otherlv_7= 'deprecationInfo' ruleMdfDeprecationInfo
                    {
                    otherlv_7=(Token)match(input,14,FollowSets000.FOLLOW_14_in_ruleMdfClass1607); 

                        	newLeafNode(otherlv_7, grammarAccess.getMdfClassAccess().getDeprecationInfoKeyword_6_0());
                        
                     
                            newCompositeNode(grammarAccess.getMdfClassAccess().getMdfDeprecationInfoParserRuleCall_6_1()); 
                        
                    pushFollow(FollowSets000.FOLLOW_ruleMdfDeprecationInfo_in_ruleMdfClass1623);
                    ruleMdfDeprecationInfo();

                    state._fsp--;

                     
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;

            }

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:762:3: (otherlv_9= '{' ( (lv_properties_10_0= ruleMdfProperty ) )* otherlv_11= '}' )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:762:5: otherlv_9= '{' ( (lv_properties_10_0= ruleMdfProperty ) )* otherlv_11= '}'
            {
            otherlv_9=(Token)match(input,16,FollowSets000.FOLLOW_16_in_ruleMdfClass1637); 

                	newLeafNode(otherlv_9, grammarAccess.getMdfClassAccess().getLeftCurlyBracketKeyword_7_0());
                
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:766:1: ( (lv_properties_10_0= ruleMdfProperty ) )*
            loop22:
            do {
                int alt22=2;
                int LA22_0 = input.LA(1);

                if ( ((LA22_0>=RULE_ID && LA22_0<=RULE_VALUE)) ) {
                    alt22=1;
                }


                switch (alt22) {
            	case 1 :
            	    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:767:1: (lv_properties_10_0= ruleMdfProperty )
            	    {
            	    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:767:1: (lv_properties_10_0= ruleMdfProperty )
            	    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:768:3: lv_properties_10_0= ruleMdfProperty
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getMdfClassAccess().getPropertiesMdfPropertyParserRuleCall_7_1_0()); 
            	    	    
            	    pushFollow(FollowSets000.FOLLOW_ruleMdfProperty_in_ruleMdfClass1658);
            	    lv_properties_10_0=ruleMdfProperty();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getMdfClassRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"properties",
            	            		lv_properties_10_0, 
            	            		"MdfProperty");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop22;
                }
            } while (true);

            otherlv_11=(Token)match(input,17,FollowSets000.FOLLOW_17_in_ruleMdfClass1671); 

                	newLeafNode(otherlv_11, grammarAccess.getMdfClassAccess().getRightCurlyBracketKeyword_7_2());
                

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
    // $ANTLR end "ruleMdfClass"


    // $ANTLR start "entryRuleMdfDataset"
    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:796:1: entryRuleMdfDataset returns [EObject current=null] : iv_ruleMdfDataset= ruleMdfDataset EOF ;
    public final EObject entryRuleMdfDataset() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMdfDataset = null;


        try {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:797:2: (iv_ruleMdfDataset= ruleMdfDataset EOF )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:798:2: iv_ruleMdfDataset= ruleMdfDataset EOF
            {
             newCompositeNode(grammarAccess.getMdfDatasetRule()); 
            pushFollow(FollowSets000.FOLLOW_ruleMdfDataset_in_entryRuleMdfDataset1708);
            iv_ruleMdfDataset=ruleMdfDataset();

            state._fsp--;

             current =iv_ruleMdfDataset; 
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleMdfDataset1718); 

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
    // $ANTLR end "entryRuleMdfDataset"


    // $ANTLR start "ruleMdfDataset"
    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:805:1: ruleMdfDataset returns [EObject current=null] : ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_name_1_0= ruleString_Value ) ) (otherlv_2= 'basedOn' ( ( ruleRef ) ) )? ( (lv_linked_4_0= 'default' ) )? ( (lv_sync_5_0= 'synchronized' ) )? ( (lv_annotations_6_0= ruleMdfAnnotation ) )* (otherlv_7= 'deprecationInfo' ruleMdfDeprecationInfo )? (otherlv_9= '{' ( (lv_properties_10_0= ruleMdfDatasetProperty ) )* otherlv_11= '}' )? ) ;
    public final EObject ruleMdfDataset() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token lv_linked_4_0=null;
        Token lv_sync_5_0=null;
        Token otherlv_7=null;
        Token otherlv_9=null;
        Token otherlv_11=null;
        AntlrDatatypeRuleToken lv_documentation_0_0 = null;

        AntlrDatatypeRuleToken lv_name_1_0 = null;

        EObject lv_annotations_6_0 = null;

        EObject lv_properties_10_0 = null;


         enterRule(); 
            
        try {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:808:28: ( ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_name_1_0= ruleString_Value ) ) (otherlv_2= 'basedOn' ( ( ruleRef ) ) )? ( (lv_linked_4_0= 'default' ) )? ( (lv_sync_5_0= 'synchronized' ) )? ( (lv_annotations_6_0= ruleMdfAnnotation ) )* (otherlv_7= 'deprecationInfo' ruleMdfDeprecationInfo )? (otherlv_9= '{' ( (lv_properties_10_0= ruleMdfDatasetProperty ) )* otherlv_11= '}' )? ) )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:809:1: ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_name_1_0= ruleString_Value ) ) (otherlv_2= 'basedOn' ( ( ruleRef ) ) )? ( (lv_linked_4_0= 'default' ) )? ( (lv_sync_5_0= 'synchronized' ) )? ( (lv_annotations_6_0= ruleMdfAnnotation ) )* (otherlv_7= 'deprecationInfo' ruleMdfDeprecationInfo )? (otherlv_9= '{' ( (lv_properties_10_0= ruleMdfDatasetProperty ) )* otherlv_11= '}' )? )
            {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:809:1: ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_name_1_0= ruleString_Value ) ) (otherlv_2= 'basedOn' ( ( ruleRef ) ) )? ( (lv_linked_4_0= 'default' ) )? ( (lv_sync_5_0= 'synchronized' ) )? ( (lv_annotations_6_0= ruleMdfAnnotation ) )* (otherlv_7= 'deprecationInfo' ruleMdfDeprecationInfo )? (otherlv_9= '{' ( (lv_properties_10_0= ruleMdfDatasetProperty ) )* otherlv_11= '}' )? )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:809:2: ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_name_1_0= ruleString_Value ) ) (otherlv_2= 'basedOn' ( ( ruleRef ) ) )? ( (lv_linked_4_0= 'default' ) )? ( (lv_sync_5_0= 'synchronized' ) )? ( (lv_annotations_6_0= ruleMdfAnnotation ) )* (otherlv_7= 'deprecationInfo' ruleMdfDeprecationInfo )? (otherlv_9= '{' ( (lv_properties_10_0= ruleMdfDatasetProperty ) )* otherlv_11= '}' )?
            {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:809:2: ( (lv_documentation_0_0= ruleDocumentation ) )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==RULE_ML_DOC) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:810:1: (lv_documentation_0_0= ruleDocumentation )
                    {
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:810:1: (lv_documentation_0_0= ruleDocumentation )
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:811:3: lv_documentation_0_0= ruleDocumentation
                    {
                     
                    	        newCompositeNode(grammarAccess.getMdfDatasetAccess().getDocumentationDocumentationParserRuleCall_0_0()); 
                    	    
                    pushFollow(FollowSets000.FOLLOW_ruleDocumentation_in_ruleMdfDataset1764);
                    lv_documentation_0_0=ruleDocumentation();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getMdfDatasetRule());
                    	        }
                           		set(
                           			current, 
                           			"documentation",
                            		lv_documentation_0_0, 
                            		"Documentation");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }
                    break;

            }

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:827:3: ( (lv_name_1_0= ruleString_Value ) )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:828:1: (lv_name_1_0= ruleString_Value )
            {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:828:1: (lv_name_1_0= ruleString_Value )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:829:3: lv_name_1_0= ruleString_Value
            {
             
            	        newCompositeNode(grammarAccess.getMdfDatasetAccess().getNameString_ValueParserRuleCall_1_0()); 
            	    
            pushFollow(FollowSets000.FOLLOW_ruleString_Value_in_ruleMdfDataset1786);
            lv_name_1_0=ruleString_Value();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getMdfDatasetRule());
            	        }
                   		set(
                   			current, 
                   			"name",
                    		lv_name_1_0, 
                    		"String_Value");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:845:2: (otherlv_2= 'basedOn' ( ( ruleRef ) ) )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==31) ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:845:4: otherlv_2= 'basedOn' ( ( ruleRef ) )
                    {
                    otherlv_2=(Token)match(input,31,FollowSets000.FOLLOW_31_in_ruleMdfDataset1799); 

                        	newLeafNode(otherlv_2, grammarAccess.getMdfDatasetAccess().getBasedOnKeyword_2_0());
                        
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:849:1: ( ( ruleRef ) )
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:850:1: ( ruleRef )
                    {
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:850:1: ( ruleRef )
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:851:3: ruleRef
                    {

                    			if (current==null) {
                    	            current = createModelElement(grammarAccess.getMdfDatasetRule());
                    	        }
                            
                     
                    	        newCompositeNode(grammarAccess.getMdfDatasetAccess().getBaseClassMdfClassCrossReference_2_1_0()); 
                    	    
                    pushFollow(FollowSets000.FOLLOW_ruleRef_in_ruleMdfDataset1822);
                    ruleRef();

                    state._fsp--;

                     
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }


                    }
                    break;

            }

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:864:4: ( (lv_linked_4_0= 'default' ) )?
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==32) ) {
                alt25=1;
            }
            switch (alt25) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:865:1: (lv_linked_4_0= 'default' )
                    {
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:865:1: (lv_linked_4_0= 'default' )
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:866:3: lv_linked_4_0= 'default'
                    {
                    lv_linked_4_0=(Token)match(input,32,FollowSets000.FOLLOW_32_in_ruleMdfDataset1842); 

                            newLeafNode(lv_linked_4_0, grammarAccess.getMdfDatasetAccess().getLinkedDefaultKeyword_3_0());
                        

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getMdfDatasetRule());
                    	        }
                           		setWithLastConsumed(current, "linked", true, "default");
                    	    

                    }


                    }
                    break;

            }

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:879:3: ( (lv_sync_5_0= 'synchronized' ) )?
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==33) ) {
                alt26=1;
            }
            switch (alt26) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:880:1: (lv_sync_5_0= 'synchronized' )
                    {
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:880:1: (lv_sync_5_0= 'synchronized' )
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:881:3: lv_sync_5_0= 'synchronized'
                    {
                    lv_sync_5_0=(Token)match(input,33,FollowSets000.FOLLOW_33_in_ruleMdfDataset1874); 

                            newLeafNode(lv_sync_5_0, grammarAccess.getMdfDatasetAccess().getSyncSynchronizedKeyword_4_0());
                        

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getMdfDatasetRule());
                    	        }
                           		setWithLastConsumed(current, "sync", true, "synchronized");
                    	    

                    }


                    }
                    break;

            }

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:894:3: ( (lv_annotations_6_0= ruleMdfAnnotation ) )*
            loop27:
            do {
                int alt27=2;
                int LA27_0 = input.LA(1);

                if ( (LA27_0==23) ) {
                    alt27=1;
                }


                switch (alt27) {
            	case 1 :
            	    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:895:1: (lv_annotations_6_0= ruleMdfAnnotation )
            	    {
            	    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:895:1: (lv_annotations_6_0= ruleMdfAnnotation )
            	    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:896:3: lv_annotations_6_0= ruleMdfAnnotation
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getMdfDatasetAccess().getAnnotationsMdfAnnotationParserRuleCall_5_0()); 
            	    	    
            	    pushFollow(FollowSets000.FOLLOW_ruleMdfAnnotation_in_ruleMdfDataset1909);
            	    lv_annotations_6_0=ruleMdfAnnotation();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getMdfDatasetRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"annotations",
            	            		lv_annotations_6_0, 
            	            		"MdfAnnotation");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop27;
                }
            } while (true);

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:912:3: (otherlv_7= 'deprecationInfo' ruleMdfDeprecationInfo )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==14) ) {
                alt28=1;
            }
            switch (alt28) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:912:5: otherlv_7= 'deprecationInfo' ruleMdfDeprecationInfo
                    {
                    otherlv_7=(Token)match(input,14,FollowSets000.FOLLOW_14_in_ruleMdfDataset1923); 

                        	newLeafNode(otherlv_7, grammarAccess.getMdfDatasetAccess().getDeprecationInfoKeyword_6_0());
                        
                     
                            newCompositeNode(grammarAccess.getMdfDatasetAccess().getMdfDeprecationInfoParserRuleCall_6_1()); 
                        
                    pushFollow(FollowSets000.FOLLOW_ruleMdfDeprecationInfo_in_ruleMdfDataset1939);
                    ruleMdfDeprecationInfo();

                    state._fsp--;

                     
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;

            }

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:924:3: (otherlv_9= '{' ( (lv_properties_10_0= ruleMdfDatasetProperty ) )* otherlv_11= '}' )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==16) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:924:5: otherlv_9= '{' ( (lv_properties_10_0= ruleMdfDatasetProperty ) )* otherlv_11= '}'
                    {
                    otherlv_9=(Token)match(input,16,FollowSets000.FOLLOW_16_in_ruleMdfDataset1953); 

                        	newLeafNode(otherlv_9, grammarAccess.getMdfDatasetAccess().getLeftCurlyBracketKeyword_7_0());
                        
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:928:1: ( (lv_properties_10_0= ruleMdfDatasetProperty ) )*
                    loop29:
                    do {
                        int alt29=2;
                        int LA29_0 = input.LA(1);

                        if ( ((LA29_0>=RULE_ID && LA29_0<=RULE_VALUE)) ) {
                            alt29=1;
                        }


                        switch (alt29) {
                    	case 1 :
                    	    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:929:1: (lv_properties_10_0= ruleMdfDatasetProperty )
                    	    {
                    	    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:929:1: (lv_properties_10_0= ruleMdfDatasetProperty )
                    	    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:930:3: lv_properties_10_0= ruleMdfDatasetProperty
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getMdfDatasetAccess().getPropertiesMdfDatasetPropertyParserRuleCall_7_1_0()); 
                    	    	    
                    	    pushFollow(FollowSets000.FOLLOW_ruleMdfDatasetProperty_in_ruleMdfDataset1974);
                    	    lv_properties_10_0=ruleMdfDatasetProperty();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getMdfDatasetRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"properties",
                    	            		lv_properties_10_0, 
                    	            		"MdfDatasetProperty");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop29;
                        }
                    } while (true);

                    otherlv_11=(Token)match(input,17,FollowSets000.FOLLOW_17_in_ruleMdfDataset1987); 

                        	newLeafNode(otherlv_11, grammarAccess.getMdfDatasetAccess().getRightCurlyBracketKeyword_7_2());
                        

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
    // $ANTLR end "ruleMdfDataset"


    // $ANTLR start "entryRuleMdfBusinessType"
    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:958:1: entryRuleMdfBusinessType returns [EObject current=null] : iv_ruleMdfBusinessType= ruleMdfBusinessType EOF ;
    public final EObject entryRuleMdfBusinessType() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMdfBusinessType = null;


        try {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:959:2: (iv_ruleMdfBusinessType= ruleMdfBusinessType EOF )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:960:2: iv_ruleMdfBusinessType= ruleMdfBusinessType EOF
            {
             newCompositeNode(grammarAccess.getMdfBusinessTypeRule()); 
            pushFollow(FollowSets000.FOLLOW_ruleMdfBusinessType_in_entryRuleMdfBusinessType2025);
            iv_ruleMdfBusinessType=ruleMdfBusinessType();

            state._fsp--;

             current =iv_ruleMdfBusinessType; 
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleMdfBusinessType2035); 

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
    // $ANTLR end "entryRuleMdfBusinessType"


    // $ANTLR start "ruleMdfBusinessType"
    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:967:1: ruleMdfBusinessType returns [EObject current=null] : ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_name_1_0= ruleString_Value ) ) otherlv_2= ':' ( ( ruleRef ) )? ( (lv_annotations_4_0= ruleMdfAnnotation ) )* (otherlv_5= 'deprecationInfo' ruleMdfDeprecationInfo )? ) ;
    public final EObject ruleMdfBusinessType() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token otherlv_5=null;
        AntlrDatatypeRuleToken lv_documentation_0_0 = null;

        AntlrDatatypeRuleToken lv_name_1_0 = null;

        EObject lv_annotations_4_0 = null;


         enterRule(); 
            
        try {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:970:28: ( ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_name_1_0= ruleString_Value ) ) otherlv_2= ':' ( ( ruleRef ) )? ( (lv_annotations_4_0= ruleMdfAnnotation ) )* (otherlv_5= 'deprecationInfo' ruleMdfDeprecationInfo )? ) )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:971:1: ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_name_1_0= ruleString_Value ) ) otherlv_2= ':' ( ( ruleRef ) )? ( (lv_annotations_4_0= ruleMdfAnnotation ) )* (otherlv_5= 'deprecationInfo' ruleMdfDeprecationInfo )? )
            {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:971:1: ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_name_1_0= ruleString_Value ) ) otherlv_2= ':' ( ( ruleRef ) )? ( (lv_annotations_4_0= ruleMdfAnnotation ) )* (otherlv_5= 'deprecationInfo' ruleMdfDeprecationInfo )? )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:971:2: ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_name_1_0= ruleString_Value ) ) otherlv_2= ':' ( ( ruleRef ) )? ( (lv_annotations_4_0= ruleMdfAnnotation ) )* (otherlv_5= 'deprecationInfo' ruleMdfDeprecationInfo )?
            {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:971:2: ( (lv_documentation_0_0= ruleDocumentation ) )?
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==RULE_ML_DOC) ) {
                alt31=1;
            }
            switch (alt31) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:972:1: (lv_documentation_0_0= ruleDocumentation )
                    {
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:972:1: (lv_documentation_0_0= ruleDocumentation )
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:973:3: lv_documentation_0_0= ruleDocumentation
                    {
                     
                    	        newCompositeNode(grammarAccess.getMdfBusinessTypeAccess().getDocumentationDocumentationParserRuleCall_0_0()); 
                    	    
                    pushFollow(FollowSets000.FOLLOW_ruleDocumentation_in_ruleMdfBusinessType2081);
                    lv_documentation_0_0=ruleDocumentation();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getMdfBusinessTypeRule());
                    	        }
                           		set(
                           			current, 
                           			"documentation",
                            		lv_documentation_0_0, 
                            		"Documentation");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }
                    break;

            }

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:989:3: ( (lv_name_1_0= ruleString_Value ) )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:990:1: (lv_name_1_0= ruleString_Value )
            {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:990:1: (lv_name_1_0= ruleString_Value )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:991:3: lv_name_1_0= ruleString_Value
            {
             
            	        newCompositeNode(grammarAccess.getMdfBusinessTypeAccess().getNameString_ValueParserRuleCall_1_0()); 
            	    
            pushFollow(FollowSets000.FOLLOW_ruleString_Value_in_ruleMdfBusinessType2103);
            lv_name_1_0=ruleString_Value();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getMdfBusinessTypeRule());
            	        }
                   		set(
                   			current, 
                   			"name",
                    		lv_name_1_0, 
                    		"String_Value");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_2=(Token)match(input,24,FollowSets000.FOLLOW_24_in_ruleMdfBusinessType2115); 

                	newLeafNode(otherlv_2, grammarAccess.getMdfBusinessTypeAccess().getColonKeyword_2());
                
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1011:1: ( ( ruleRef ) )?
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==RULE_ID) ) {
                int LA32_1 = input.LA(2);

                if ( (LA32_1==24) ) {
                    int LA32_3 = input.LA(3);

                    if ( (LA32_3==RULE_ID) ) {
                        alt32=1;
                    }
                }
                else if ( (LA32_1==EOF||(LA32_1>=RULE_ID && LA32_1<=RULE_VALUE)||LA32_1==14||LA32_1==17||LA32_1==23) ) {
                    alt32=1;
                }
            }
            switch (alt32) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1012:1: ( ruleRef )
                    {
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1012:1: ( ruleRef )
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1013:3: ruleRef
                    {

                    			if (current==null) {
                    	            current = createModelElement(grammarAccess.getMdfBusinessTypeRule());
                    	        }
                            
                     
                    	        newCompositeNode(grammarAccess.getMdfBusinessTypeAccess().getTypeMdfPrimitiveCrossReference_3_0()); 
                    	    
                    pushFollow(FollowSets000.FOLLOW_ruleRef_in_ruleMdfBusinessType2138);
                    ruleRef();

                    state._fsp--;

                     
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }
                    break;

            }

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1026:3: ( (lv_annotations_4_0= ruleMdfAnnotation ) )*
            loop33:
            do {
                int alt33=2;
                int LA33_0 = input.LA(1);

                if ( (LA33_0==23) ) {
                    alt33=1;
                }


                switch (alt33) {
            	case 1 :
            	    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1027:1: (lv_annotations_4_0= ruleMdfAnnotation )
            	    {
            	    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1027:1: (lv_annotations_4_0= ruleMdfAnnotation )
            	    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1028:3: lv_annotations_4_0= ruleMdfAnnotation
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getMdfBusinessTypeAccess().getAnnotationsMdfAnnotationParserRuleCall_4_0()); 
            	    	    
            	    pushFollow(FollowSets000.FOLLOW_ruleMdfAnnotation_in_ruleMdfBusinessType2160);
            	    lv_annotations_4_0=ruleMdfAnnotation();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getMdfBusinessTypeRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"annotations",
            	            		lv_annotations_4_0, 
            	            		"MdfAnnotation");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop33;
                }
            } while (true);

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1044:3: (otherlv_5= 'deprecationInfo' ruleMdfDeprecationInfo )?
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==14) ) {
                alt34=1;
            }
            switch (alt34) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1044:5: otherlv_5= 'deprecationInfo' ruleMdfDeprecationInfo
                    {
                    otherlv_5=(Token)match(input,14,FollowSets000.FOLLOW_14_in_ruleMdfBusinessType2174); 

                        	newLeafNode(otherlv_5, grammarAccess.getMdfBusinessTypeAccess().getDeprecationInfoKeyword_5_0());
                        
                     
                            newCompositeNode(grammarAccess.getMdfBusinessTypeAccess().getMdfDeprecationInfoParserRuleCall_5_1()); 
                        
                    pushFollow(FollowSets000.FOLLOW_ruleMdfDeprecationInfo_in_ruleMdfBusinessType2190);
                    ruleMdfDeprecationInfo();

                    state._fsp--;

                     
                            afterParserOrEnumRuleCall();
                        

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
    // $ANTLR end "ruleMdfBusinessType"


    // $ANTLR start "entryRuleMdfEnumeration"
    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1064:1: entryRuleMdfEnumeration returns [EObject current=null] : iv_ruleMdfEnumeration= ruleMdfEnumeration EOF ;
    public final EObject entryRuleMdfEnumeration() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMdfEnumeration = null;


        try {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1065:2: (iv_ruleMdfEnumeration= ruleMdfEnumeration EOF )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1066:2: iv_ruleMdfEnumeration= ruleMdfEnumeration EOF
            {
             newCompositeNode(grammarAccess.getMdfEnumerationRule()); 
            pushFollow(FollowSets000.FOLLOW_ruleMdfEnumeration_in_entryRuleMdfEnumeration2227);
            iv_ruleMdfEnumeration=ruleMdfEnumeration();

            state._fsp--;

             current =iv_ruleMdfEnumeration; 
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleMdfEnumeration2237); 

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
    // $ANTLR end "entryRuleMdfEnumeration"


    // $ANTLR start "ruleMdfEnumeration"
    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1073:1: ruleMdfEnumeration returns [EObject current=null] : ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_name_1_0= ruleString_Value ) ) otherlv_2= ':' ( ( ruleRef ) )? ( (lv_acceptNullValue_4_0= 'acceptNullValue' ) )? ( (lv_annotations_5_0= ruleMdfAnnotation ) )* (otherlv_6= 'deprecationInfo' ruleMdfDeprecationInfo )? ( (lv_values_8_0= ruleMdfEnumValue ) )* ) ;
    public final EObject ruleMdfEnumeration() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token lv_acceptNullValue_4_0=null;
        Token otherlv_6=null;
        AntlrDatatypeRuleToken lv_documentation_0_0 = null;

        AntlrDatatypeRuleToken lv_name_1_0 = null;

        EObject lv_annotations_5_0 = null;

        EObject lv_values_8_0 = null;


         enterRule(); 
            
        try {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1076:28: ( ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_name_1_0= ruleString_Value ) ) otherlv_2= ':' ( ( ruleRef ) )? ( (lv_acceptNullValue_4_0= 'acceptNullValue' ) )? ( (lv_annotations_5_0= ruleMdfAnnotation ) )* (otherlv_6= 'deprecationInfo' ruleMdfDeprecationInfo )? ( (lv_values_8_0= ruleMdfEnumValue ) )* ) )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1077:1: ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_name_1_0= ruleString_Value ) ) otherlv_2= ':' ( ( ruleRef ) )? ( (lv_acceptNullValue_4_0= 'acceptNullValue' ) )? ( (lv_annotations_5_0= ruleMdfAnnotation ) )* (otherlv_6= 'deprecationInfo' ruleMdfDeprecationInfo )? ( (lv_values_8_0= ruleMdfEnumValue ) )* )
            {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1077:1: ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_name_1_0= ruleString_Value ) ) otherlv_2= ':' ( ( ruleRef ) )? ( (lv_acceptNullValue_4_0= 'acceptNullValue' ) )? ( (lv_annotations_5_0= ruleMdfAnnotation ) )* (otherlv_6= 'deprecationInfo' ruleMdfDeprecationInfo )? ( (lv_values_8_0= ruleMdfEnumValue ) )* )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1077:2: ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_name_1_0= ruleString_Value ) ) otherlv_2= ':' ( ( ruleRef ) )? ( (lv_acceptNullValue_4_0= 'acceptNullValue' ) )? ( (lv_annotations_5_0= ruleMdfAnnotation ) )* (otherlv_6= 'deprecationInfo' ruleMdfDeprecationInfo )? ( (lv_values_8_0= ruleMdfEnumValue ) )*
            {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1077:2: ( (lv_documentation_0_0= ruleDocumentation ) )?
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==RULE_ML_DOC) ) {
                alt35=1;
            }
            switch (alt35) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1078:1: (lv_documentation_0_0= ruleDocumentation )
                    {
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1078:1: (lv_documentation_0_0= ruleDocumentation )
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1079:3: lv_documentation_0_0= ruleDocumentation
                    {
                     
                    	        newCompositeNode(grammarAccess.getMdfEnumerationAccess().getDocumentationDocumentationParserRuleCall_0_0()); 
                    	    
                    pushFollow(FollowSets000.FOLLOW_ruleDocumentation_in_ruleMdfEnumeration2283);
                    lv_documentation_0_0=ruleDocumentation();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getMdfEnumerationRule());
                    	        }
                           		set(
                           			current, 
                           			"documentation",
                            		lv_documentation_0_0, 
                            		"Documentation");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }
                    break;

            }

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1095:3: ( (lv_name_1_0= ruleString_Value ) )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1096:1: (lv_name_1_0= ruleString_Value )
            {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1096:1: (lv_name_1_0= ruleString_Value )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1097:3: lv_name_1_0= ruleString_Value
            {
             
            	        newCompositeNode(grammarAccess.getMdfEnumerationAccess().getNameString_ValueParserRuleCall_1_0()); 
            	    
            pushFollow(FollowSets000.FOLLOW_ruleString_Value_in_ruleMdfEnumeration2305);
            lv_name_1_0=ruleString_Value();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getMdfEnumerationRule());
            	        }
                   		set(
                   			current, 
                   			"name",
                    		lv_name_1_0, 
                    		"String_Value");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_2=(Token)match(input,24,FollowSets000.FOLLOW_24_in_ruleMdfEnumeration2317); 

                	newLeafNode(otherlv_2, grammarAccess.getMdfEnumerationAccess().getColonKeyword_2());
                
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1117:1: ( ( ruleRef ) )?
            int alt36=2;
            int LA36_0 = input.LA(1);

            if ( (LA36_0==RULE_ID) ) {
                int LA36_1 = input.LA(2);

                if ( (LA36_1==24) ) {
                    int LA36_3 = input.LA(3);

                    if ( (LA36_3==RULE_ID) ) {
                        alt36=1;
                    }
                }
                else if ( (LA36_1==EOF||(LA36_1>=RULE_ID && LA36_1<=RULE_VALUE)||LA36_1==14||LA36_1==17||LA36_1==23||LA36_1==34) ) {
                    alt36=1;
                }
            }
            switch (alt36) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1118:1: ( ruleRef )
                    {
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1118:1: ( ruleRef )
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1119:3: ruleRef
                    {

                    			if (current==null) {
                    	            current = createModelElement(grammarAccess.getMdfEnumerationRule());
                    	        }
                            
                     
                    	        newCompositeNode(grammarAccess.getMdfEnumerationAccess().getTypeMdfPrimitiveCrossReference_3_0()); 
                    	    
                    pushFollow(FollowSets000.FOLLOW_ruleRef_in_ruleMdfEnumeration2340);
                    ruleRef();

                    state._fsp--;

                     
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }
                    break;

            }

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1132:3: ( (lv_acceptNullValue_4_0= 'acceptNullValue' ) )?
            int alt37=2;
            int LA37_0 = input.LA(1);

            if ( (LA37_0==34) ) {
                alt37=1;
            }
            switch (alt37) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1133:1: (lv_acceptNullValue_4_0= 'acceptNullValue' )
                    {
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1133:1: (lv_acceptNullValue_4_0= 'acceptNullValue' )
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1134:3: lv_acceptNullValue_4_0= 'acceptNullValue'
                    {
                    lv_acceptNullValue_4_0=(Token)match(input,34,FollowSets000.FOLLOW_34_in_ruleMdfEnumeration2359); 

                            newLeafNode(lv_acceptNullValue_4_0, grammarAccess.getMdfEnumerationAccess().getAcceptNullValueAcceptNullValueKeyword_4_0());
                        

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getMdfEnumerationRule());
                    	        }
                           		setWithLastConsumed(current, "acceptNullValue", true, "acceptNullValue");
                    	    

                    }


                    }
                    break;

            }

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1147:3: ( (lv_annotations_5_0= ruleMdfAnnotation ) )*
            loop38:
            do {
                int alt38=2;
                int LA38_0 = input.LA(1);

                if ( (LA38_0==23) ) {
                    alt38=1;
                }


                switch (alt38) {
            	case 1 :
            	    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1148:1: (lv_annotations_5_0= ruleMdfAnnotation )
            	    {
            	    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1148:1: (lv_annotations_5_0= ruleMdfAnnotation )
            	    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1149:3: lv_annotations_5_0= ruleMdfAnnotation
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getMdfEnumerationAccess().getAnnotationsMdfAnnotationParserRuleCall_5_0()); 
            	    	    
            	    pushFollow(FollowSets000.FOLLOW_ruleMdfAnnotation_in_ruleMdfEnumeration2394);
            	    lv_annotations_5_0=ruleMdfAnnotation();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getMdfEnumerationRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"annotations",
            	            		lv_annotations_5_0, 
            	            		"MdfAnnotation");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop38;
                }
            } while (true);

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1165:3: (otherlv_6= 'deprecationInfo' ruleMdfDeprecationInfo )?
            int alt39=2;
            int LA39_0 = input.LA(1);

            if ( (LA39_0==14) ) {
                alt39=1;
            }
            switch (alt39) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1165:5: otherlv_6= 'deprecationInfo' ruleMdfDeprecationInfo
                    {
                    otherlv_6=(Token)match(input,14,FollowSets000.FOLLOW_14_in_ruleMdfEnumeration2408); 

                        	newLeafNode(otherlv_6, grammarAccess.getMdfEnumerationAccess().getDeprecationInfoKeyword_6_0());
                        
                     
                            newCompositeNode(grammarAccess.getMdfEnumerationAccess().getMdfDeprecationInfoParserRuleCall_6_1()); 
                        
                    pushFollow(FollowSets000.FOLLOW_ruleMdfDeprecationInfo_in_ruleMdfEnumeration2424);
                    ruleMdfDeprecationInfo();

                    state._fsp--;

                     
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;

            }

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1177:3: ( (lv_values_8_0= ruleMdfEnumValue ) )*
            loop40:
            do {
                int alt40=2;
                switch ( input.LA(1) ) {
                case RULE_ML_DOC:
                    {
                    switch ( input.LA(2) ) {
                    case RULE_STRING:
                        {
                        int LA40_3 = input.LA(3);

                        if ( (LA40_3==36) ) {
                            alt40=1;
                        }


                        }
                        break;
                    case RULE_ID:
                        {
                        int LA40_4 = input.LA(3);

                        if ( (LA40_4==36) ) {
                            alt40=1;
                        }


                        }
                        break;
                    case RULE_VALUE:
                        {
                        int LA40_5 = input.LA(3);

                        if ( (LA40_5==36) ) {
                            alt40=1;
                        }


                        }
                        break;

                    }

                    }
                    break;
                case RULE_STRING:
                    {
                    int LA40_3 = input.LA(2);

                    if ( (LA40_3==36) ) {
                        alt40=1;
                    }


                    }
                    break;
                case RULE_ID:
                    {
                    int LA40_4 = input.LA(2);

                    if ( (LA40_4==36) ) {
                        alt40=1;
                    }


                    }
                    break;
                case RULE_VALUE:
                    {
                    int LA40_5 = input.LA(2);

                    if ( (LA40_5==36) ) {
                        alt40=1;
                    }


                    }
                    break;

                }

                switch (alt40) {
            	case 1 :
            	    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1178:1: (lv_values_8_0= ruleMdfEnumValue )
            	    {
            	    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1178:1: (lv_values_8_0= ruleMdfEnumValue )
            	    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1179:3: lv_values_8_0= ruleMdfEnumValue
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getMdfEnumerationAccess().getValuesMdfEnumValueParserRuleCall_7_0()); 
            	    	    
            	    pushFollow(FollowSets000.FOLLOW_ruleMdfEnumValue_in_ruleMdfEnumeration2446);
            	    lv_values_8_0=ruleMdfEnumValue();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getMdfEnumerationRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"values",
            	            		lv_values_8_0, 
            	            		"MdfEnumValue");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop40;
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
    // $ANTLR end "ruleMdfEnumeration"


    // $ANTLR start "entryRuleMdfPrimitive_Impl"
    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1203:1: entryRuleMdfPrimitive_Impl returns [EObject current=null] : iv_ruleMdfPrimitive_Impl= ruleMdfPrimitive_Impl EOF ;
    public final EObject entryRuleMdfPrimitive_Impl() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMdfPrimitive_Impl = null;


        try {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1204:2: (iv_ruleMdfPrimitive_Impl= ruleMdfPrimitive_Impl EOF )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1205:2: iv_ruleMdfPrimitive_Impl= ruleMdfPrimitive_Impl EOF
            {
             newCompositeNode(grammarAccess.getMdfPrimitive_ImplRule()); 
            pushFollow(FollowSets000.FOLLOW_ruleMdfPrimitive_Impl_in_entryRuleMdfPrimitive_Impl2483);
            iv_ruleMdfPrimitive_Impl=ruleMdfPrimitive_Impl();

            state._fsp--;

             current =iv_ruleMdfPrimitive_Impl; 
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleMdfPrimitive_Impl2493); 

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
    // $ANTLR end "entryRuleMdfPrimitive_Impl"


    // $ANTLR start "ruleMdfPrimitive_Impl"
    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1212:1: ruleMdfPrimitive_Impl returns [EObject current=null] : ( () ( (lv_name_1_0= RULE_ID ) ) ) ;
    public final EObject ruleMdfPrimitive_Impl() throws RecognitionException {
        EObject current = null;

        Token lv_name_1_0=null;

         enterRule(); 
            
        try {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1215:28: ( ( () ( (lv_name_1_0= RULE_ID ) ) ) )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1216:1: ( () ( (lv_name_1_0= RULE_ID ) ) )
            {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1216:1: ( () ( (lv_name_1_0= RULE_ID ) ) )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1216:2: () ( (lv_name_1_0= RULE_ID ) )
            {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1216:2: ()
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1217:5: 
            {

                    current = forceCreateModelElement(
                        grammarAccess.getMdfPrimitive_ImplAccess().getMdfPrimitiveAction_0(),
                        current);
                

            }

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1222:2: ( (lv_name_1_0= RULE_ID ) )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1223:1: (lv_name_1_0= RULE_ID )
            {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1223:1: (lv_name_1_0= RULE_ID )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1224:3: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FollowSets000.FOLLOW_RULE_ID_in_ruleMdfPrimitive_Impl2544); 

            			newLeafNode(lv_name_1_0, grammarAccess.getMdfPrimitive_ImplAccess().getNameIDTerminalRuleCall_1_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getMdfPrimitive_ImplRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"name",
                    		lv_name_1_0, 
                    		"ID");
            	    

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
    // $ANTLR end "ruleMdfPrimitive_Impl"


    // $ANTLR start "entryRuleMdfAnnotationProperty"
    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1248:1: entryRuleMdfAnnotationProperty returns [EObject current=null] : iv_ruleMdfAnnotationProperty= ruleMdfAnnotationProperty EOF ;
    public final EObject entryRuleMdfAnnotationProperty() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMdfAnnotationProperty = null;


        try {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1249:2: (iv_ruleMdfAnnotationProperty= ruleMdfAnnotationProperty EOF )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1250:2: iv_ruleMdfAnnotationProperty= ruleMdfAnnotationProperty EOF
            {
             newCompositeNode(grammarAccess.getMdfAnnotationPropertyRule()); 
            pushFollow(FollowSets000.FOLLOW_ruleMdfAnnotationProperty_in_entryRuleMdfAnnotationProperty2585);
            iv_ruleMdfAnnotationProperty=ruleMdfAnnotationProperty();

            state._fsp--;

             current =iv_ruleMdfAnnotationProperty; 
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleMdfAnnotationProperty2595); 

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
    // $ANTLR end "entryRuleMdfAnnotationProperty"


    // $ANTLR start "ruleMdfAnnotationProperty"
    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1257:1: ruleMdfAnnotationProperty returns [EObject current=null] : ( ( (lv_cDATA_0_0= 'cDATA' ) )? ( (lv_name_1_0= ruleString_Value ) ) (otherlv_2= '=' ( (lv_value_3_0= ruleString_Value ) ) )? ) ;
    public final EObject ruleMdfAnnotationProperty() throws RecognitionException {
        EObject current = null;

        Token lv_cDATA_0_0=null;
        Token otherlv_2=null;
        AntlrDatatypeRuleToken lv_name_1_0 = null;

        AntlrDatatypeRuleToken lv_value_3_0 = null;


         enterRule(); 
            
        try {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1260:28: ( ( ( (lv_cDATA_0_0= 'cDATA' ) )? ( (lv_name_1_0= ruleString_Value ) ) (otherlv_2= '=' ( (lv_value_3_0= ruleString_Value ) ) )? ) )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1261:1: ( ( (lv_cDATA_0_0= 'cDATA' ) )? ( (lv_name_1_0= ruleString_Value ) ) (otherlv_2= '=' ( (lv_value_3_0= ruleString_Value ) ) )? )
            {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1261:1: ( ( (lv_cDATA_0_0= 'cDATA' ) )? ( (lv_name_1_0= ruleString_Value ) ) (otherlv_2= '=' ( (lv_value_3_0= ruleString_Value ) ) )? )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1261:2: ( (lv_cDATA_0_0= 'cDATA' ) )? ( (lv_name_1_0= ruleString_Value ) ) (otherlv_2= '=' ( (lv_value_3_0= ruleString_Value ) ) )?
            {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1261:2: ( (lv_cDATA_0_0= 'cDATA' ) )?
            int alt41=2;
            int LA41_0 = input.LA(1);

            if ( (LA41_0==35) ) {
                alt41=1;
            }
            switch (alt41) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1262:1: (lv_cDATA_0_0= 'cDATA' )
                    {
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1262:1: (lv_cDATA_0_0= 'cDATA' )
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1263:3: lv_cDATA_0_0= 'cDATA'
                    {
                    lv_cDATA_0_0=(Token)match(input,35,FollowSets000.FOLLOW_35_in_ruleMdfAnnotationProperty2638); 

                            newLeafNode(lv_cDATA_0_0, grammarAccess.getMdfAnnotationPropertyAccess().getCDATACDATAKeyword_0_0());
                        

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getMdfAnnotationPropertyRule());
                    	        }
                           		setWithLastConsumed(current, "cDATA", true, "cDATA");
                    	    

                    }


                    }
                    break;

            }

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1276:3: ( (lv_name_1_0= ruleString_Value ) )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1277:1: (lv_name_1_0= ruleString_Value )
            {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1277:1: (lv_name_1_0= ruleString_Value )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1278:3: lv_name_1_0= ruleString_Value
            {
             
            	        newCompositeNode(grammarAccess.getMdfAnnotationPropertyAccess().getNameString_ValueParserRuleCall_1_0()); 
            	    
            pushFollow(FollowSets000.FOLLOW_ruleString_Value_in_ruleMdfAnnotationProperty2673);
            lv_name_1_0=ruleString_Value();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getMdfAnnotationPropertyRule());
            	        }
                   		set(
                   			current, 
                   			"name",
                    		lv_name_1_0, 
                    		"String_Value");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1294:2: (otherlv_2= '=' ( (lv_value_3_0= ruleString_Value ) ) )?
            int alt42=2;
            int LA42_0 = input.LA(1);

            if ( (LA42_0==36) ) {
                alt42=1;
            }
            switch (alt42) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1294:4: otherlv_2= '=' ( (lv_value_3_0= ruleString_Value ) )
                    {
                    otherlv_2=(Token)match(input,36,FollowSets000.FOLLOW_36_in_ruleMdfAnnotationProperty2686); 

                        	newLeafNode(otherlv_2, grammarAccess.getMdfAnnotationPropertyAccess().getEqualsSignKeyword_2_0());
                        
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1298:1: ( (lv_value_3_0= ruleString_Value ) )
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1299:1: (lv_value_3_0= ruleString_Value )
                    {
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1299:1: (lv_value_3_0= ruleString_Value )
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1300:3: lv_value_3_0= ruleString_Value
                    {
                     
                    	        newCompositeNode(grammarAccess.getMdfAnnotationPropertyAccess().getValueString_ValueParserRuleCall_2_1_0()); 
                    	    
                    pushFollow(FollowSets000.FOLLOW_ruleString_Value_in_ruleMdfAnnotationProperty2707);
                    lv_value_3_0=ruleString_Value();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getMdfAnnotationPropertyRule());
                    	        }
                           		set(
                           			current, 
                           			"value",
                            		lv_value_3_0, 
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
    // $ANTLR end "ruleMdfAnnotationProperty"


    // $ANTLR start "entryRuleMdfAttribute"
    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1324:1: entryRuleMdfAttribute returns [EObject current=null] : iv_ruleMdfAttribute= ruleMdfAttribute EOF ;
    public final EObject entryRuleMdfAttribute() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMdfAttribute = null;


        try {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1325:2: (iv_ruleMdfAttribute= ruleMdfAttribute EOF )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1326:2: iv_ruleMdfAttribute= ruleMdfAttribute EOF
            {
             newCompositeNode(grammarAccess.getMdfAttributeRule()); 
            pushFollow(FollowSets000.FOLLOW_ruleMdfAttribute_in_entryRuleMdfAttribute2745);
            iv_ruleMdfAttribute=ruleMdfAttribute();

            state._fsp--;

             current =iv_ruleMdfAttribute; 
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleMdfAttribute2755); 

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
    // $ANTLR end "entryRuleMdfAttribute"


    // $ANTLR start "ruleMdfAttribute"
    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1333:1: ruleMdfAttribute returns [EObject current=null] : ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_name_1_0= ruleString_Value ) ) otherlv_2= ':' ( ( ruleRef ) )? ( (lv_multiplicity_4_0= ruleMdfMultiplicity ) )? ( (lv_businessKey_5_0= 'BK' ) )? ( (lv_primaryKey_6_0= 'PK' ) )? ( (lv_required_7_0= 'required' ) )? (otherlv_8= 'default' otherlv_9= '=' ( (lv_default_10_0= ruleString_Value ) ) )? ( (lv_annotations_11_0= ruleMdfAnnotation ) )* (otherlv_12= 'deprecationInfo' ruleMdfDeprecationInfo )? ) ;
    public final EObject ruleMdfAttribute() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token lv_businessKey_5_0=null;
        Token lv_primaryKey_6_0=null;
        Token lv_required_7_0=null;
        Token otherlv_8=null;
        Token otherlv_9=null;
        Token otherlv_12=null;
        AntlrDatatypeRuleToken lv_documentation_0_0 = null;

        AntlrDatatypeRuleToken lv_name_1_0 = null;

        Enumerator lv_multiplicity_4_0 = null;

        AntlrDatatypeRuleToken lv_default_10_0 = null;

        EObject lv_annotations_11_0 = null;


         enterRule(); 
            
        try {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1336:28: ( ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_name_1_0= ruleString_Value ) ) otherlv_2= ':' ( ( ruleRef ) )? ( (lv_multiplicity_4_0= ruleMdfMultiplicity ) )? ( (lv_businessKey_5_0= 'BK' ) )? ( (lv_primaryKey_6_0= 'PK' ) )? ( (lv_required_7_0= 'required' ) )? (otherlv_8= 'default' otherlv_9= '=' ( (lv_default_10_0= ruleString_Value ) ) )? ( (lv_annotations_11_0= ruleMdfAnnotation ) )* (otherlv_12= 'deprecationInfo' ruleMdfDeprecationInfo )? ) )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1337:1: ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_name_1_0= ruleString_Value ) ) otherlv_2= ':' ( ( ruleRef ) )? ( (lv_multiplicity_4_0= ruleMdfMultiplicity ) )? ( (lv_businessKey_5_0= 'BK' ) )? ( (lv_primaryKey_6_0= 'PK' ) )? ( (lv_required_7_0= 'required' ) )? (otherlv_8= 'default' otherlv_9= '=' ( (lv_default_10_0= ruleString_Value ) ) )? ( (lv_annotations_11_0= ruleMdfAnnotation ) )* (otherlv_12= 'deprecationInfo' ruleMdfDeprecationInfo )? )
            {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1337:1: ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_name_1_0= ruleString_Value ) ) otherlv_2= ':' ( ( ruleRef ) )? ( (lv_multiplicity_4_0= ruleMdfMultiplicity ) )? ( (lv_businessKey_5_0= 'BK' ) )? ( (lv_primaryKey_6_0= 'PK' ) )? ( (lv_required_7_0= 'required' ) )? (otherlv_8= 'default' otherlv_9= '=' ( (lv_default_10_0= ruleString_Value ) ) )? ( (lv_annotations_11_0= ruleMdfAnnotation ) )* (otherlv_12= 'deprecationInfo' ruleMdfDeprecationInfo )? )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1337:2: ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_name_1_0= ruleString_Value ) ) otherlv_2= ':' ( ( ruleRef ) )? ( (lv_multiplicity_4_0= ruleMdfMultiplicity ) )? ( (lv_businessKey_5_0= 'BK' ) )? ( (lv_primaryKey_6_0= 'PK' ) )? ( (lv_required_7_0= 'required' ) )? (otherlv_8= 'default' otherlv_9= '=' ( (lv_default_10_0= ruleString_Value ) ) )? ( (lv_annotations_11_0= ruleMdfAnnotation ) )* (otherlv_12= 'deprecationInfo' ruleMdfDeprecationInfo )?
            {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1337:2: ( (lv_documentation_0_0= ruleDocumentation ) )?
            int alt43=2;
            int LA43_0 = input.LA(1);

            if ( (LA43_0==RULE_ML_DOC) ) {
                alt43=1;
            }
            switch (alt43) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1338:1: (lv_documentation_0_0= ruleDocumentation )
                    {
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1338:1: (lv_documentation_0_0= ruleDocumentation )
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1339:3: lv_documentation_0_0= ruleDocumentation
                    {
                     
                    	        newCompositeNode(grammarAccess.getMdfAttributeAccess().getDocumentationDocumentationParserRuleCall_0_0()); 
                    	    
                    pushFollow(FollowSets000.FOLLOW_ruleDocumentation_in_ruleMdfAttribute2801);
                    lv_documentation_0_0=ruleDocumentation();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getMdfAttributeRule());
                    	        }
                           		set(
                           			current, 
                           			"documentation",
                            		lv_documentation_0_0, 
                            		"Documentation");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }
                    break;

            }

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1355:3: ( (lv_name_1_0= ruleString_Value ) )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1356:1: (lv_name_1_0= ruleString_Value )
            {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1356:1: (lv_name_1_0= ruleString_Value )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1357:3: lv_name_1_0= ruleString_Value
            {
             
            	        newCompositeNode(grammarAccess.getMdfAttributeAccess().getNameString_ValueParserRuleCall_1_0()); 
            	    
            pushFollow(FollowSets000.FOLLOW_ruleString_Value_in_ruleMdfAttribute2823);
            lv_name_1_0=ruleString_Value();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getMdfAttributeRule());
            	        }
                   		set(
                   			current, 
                   			"name",
                    		lv_name_1_0, 
                    		"String_Value");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_2=(Token)match(input,24,FollowSets000.FOLLOW_24_in_ruleMdfAttribute2835); 

                	newLeafNode(otherlv_2, grammarAccess.getMdfAttributeAccess().getColonKeyword_2());
                
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1377:1: ( ( ruleRef ) )?
            int alt44=2;
            int LA44_0 = input.LA(1);

            if ( (LA44_0==RULE_ID) ) {
                int LA44_1 = input.LA(2);

                if ( (LA44_1==24) ) {
                    int LA44_3 = input.LA(3);

                    if ( (LA44_3==RULE_ID) ) {
                        alt44=1;
                    }
                }
                else if ( (LA44_1==EOF||(LA44_1>=RULE_ID && LA44_1<=RULE_VALUE)||LA44_1==14||LA44_1==17||LA44_1==23||LA44_1==32||(LA44_1>=37 && LA44_1<=39)||(LA44_1>=47 && LA44_1<=48)) ) {
                    alt44=1;
                }
            }
            switch (alt44) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1378:1: ( ruleRef )
                    {
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1378:1: ( ruleRef )
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1379:3: ruleRef
                    {

                    			if (current==null) {
                    	            current = createModelElement(grammarAccess.getMdfAttributeRule());
                    	        }
                            
                     
                    	        newCompositeNode(grammarAccess.getMdfAttributeAccess().getTypeMdfPrimitiveCrossReference_3_0()); 
                    	    
                    pushFollow(FollowSets000.FOLLOW_ruleRef_in_ruleMdfAttribute2858);
                    ruleRef();

                    state._fsp--;

                     
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }
                    break;

            }

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1392:3: ( (lv_multiplicity_4_0= ruleMdfMultiplicity ) )?
            int alt45=2;
            int LA45_0 = input.LA(1);

            if ( ((LA45_0>=47 && LA45_0<=48)) ) {
                alt45=1;
            }
            switch (alt45) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1393:1: (lv_multiplicity_4_0= ruleMdfMultiplicity )
                    {
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1393:1: (lv_multiplicity_4_0= ruleMdfMultiplicity )
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1394:3: lv_multiplicity_4_0= ruleMdfMultiplicity
                    {
                     
                    	        newCompositeNode(grammarAccess.getMdfAttributeAccess().getMultiplicityMdfMultiplicityEnumRuleCall_4_0()); 
                    	    
                    pushFollow(FollowSets000.FOLLOW_ruleMdfMultiplicity_in_ruleMdfAttribute2880);
                    lv_multiplicity_4_0=ruleMdfMultiplicity();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getMdfAttributeRule());
                    	        }
                           		set(
                           			current, 
                           			"multiplicity",
                            		lv_multiplicity_4_0, 
                            		"MdfMultiplicity");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }
                    break;

            }

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1410:3: ( (lv_businessKey_5_0= 'BK' ) )?
            int alt46=2;
            int LA46_0 = input.LA(1);

            if ( (LA46_0==37) ) {
                alt46=1;
            }
            switch (alt46) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1411:1: (lv_businessKey_5_0= 'BK' )
                    {
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1411:1: (lv_businessKey_5_0= 'BK' )
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1412:3: lv_businessKey_5_0= 'BK'
                    {
                    lv_businessKey_5_0=(Token)match(input,37,FollowSets000.FOLLOW_37_in_ruleMdfAttribute2899); 

                            newLeafNode(lv_businessKey_5_0, grammarAccess.getMdfAttributeAccess().getBusinessKeyBKKeyword_5_0());
                        

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getMdfAttributeRule());
                    	        }
                           		setWithLastConsumed(current, "businessKey", true, "BK");
                    	    

                    }


                    }
                    break;

            }

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1425:3: ( (lv_primaryKey_6_0= 'PK' ) )?
            int alt47=2;
            int LA47_0 = input.LA(1);

            if ( (LA47_0==38) ) {
                alt47=1;
            }
            switch (alt47) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1426:1: (lv_primaryKey_6_0= 'PK' )
                    {
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1426:1: (lv_primaryKey_6_0= 'PK' )
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1427:3: lv_primaryKey_6_0= 'PK'
                    {
                    lv_primaryKey_6_0=(Token)match(input,38,FollowSets000.FOLLOW_38_in_ruleMdfAttribute2931); 

                            newLeafNode(lv_primaryKey_6_0, grammarAccess.getMdfAttributeAccess().getPrimaryKeyPKKeyword_6_0());
                        

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getMdfAttributeRule());
                    	        }
                           		setWithLastConsumed(current, "primaryKey", true, "PK");
                    	    

                    }


                    }
                    break;

            }

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1440:3: ( (lv_required_7_0= 'required' ) )?
            int alt48=2;
            int LA48_0 = input.LA(1);

            if ( (LA48_0==39) ) {
                alt48=1;
            }
            switch (alt48) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1441:1: (lv_required_7_0= 'required' )
                    {
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1441:1: (lv_required_7_0= 'required' )
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1442:3: lv_required_7_0= 'required'
                    {
                    lv_required_7_0=(Token)match(input,39,FollowSets000.FOLLOW_39_in_ruleMdfAttribute2963); 

                            newLeafNode(lv_required_7_0, grammarAccess.getMdfAttributeAccess().getRequiredRequiredKeyword_7_0());
                        

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getMdfAttributeRule());
                    	        }
                           		setWithLastConsumed(current, "required", true, "required");
                    	    

                    }


                    }
                    break;

            }

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1455:3: (otherlv_8= 'default' otherlv_9= '=' ( (lv_default_10_0= ruleString_Value ) ) )?
            int alt49=2;
            int LA49_0 = input.LA(1);

            if ( (LA49_0==32) ) {
                alt49=1;
            }
            switch (alt49) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1455:5: otherlv_8= 'default' otherlv_9= '=' ( (lv_default_10_0= ruleString_Value ) )
                    {
                    otherlv_8=(Token)match(input,32,FollowSets000.FOLLOW_32_in_ruleMdfAttribute2990); 

                        	newLeafNode(otherlv_8, grammarAccess.getMdfAttributeAccess().getDefaultKeyword_8_0());
                        
                    otherlv_9=(Token)match(input,36,FollowSets000.FOLLOW_36_in_ruleMdfAttribute3002); 

                        	newLeafNode(otherlv_9, grammarAccess.getMdfAttributeAccess().getEqualsSignKeyword_8_1());
                        
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1463:1: ( (lv_default_10_0= ruleString_Value ) )
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1464:1: (lv_default_10_0= ruleString_Value )
                    {
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1464:1: (lv_default_10_0= ruleString_Value )
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1465:3: lv_default_10_0= ruleString_Value
                    {
                     
                    	        newCompositeNode(grammarAccess.getMdfAttributeAccess().getDefaultString_ValueParserRuleCall_8_2_0()); 
                    	    
                    pushFollow(FollowSets000.FOLLOW_ruleString_Value_in_ruleMdfAttribute3023);
                    lv_default_10_0=ruleString_Value();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getMdfAttributeRule());
                    	        }
                           		set(
                           			current, 
                           			"default",
                            		lv_default_10_0, 
                            		"String_Value");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }


                    }
                    break;

            }

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1481:4: ( (lv_annotations_11_0= ruleMdfAnnotation ) )*
            loop50:
            do {
                int alt50=2;
                int LA50_0 = input.LA(1);

                if ( (LA50_0==23) ) {
                    alt50=1;
                }


                switch (alt50) {
            	case 1 :
            	    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1482:1: (lv_annotations_11_0= ruleMdfAnnotation )
            	    {
            	    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1482:1: (lv_annotations_11_0= ruleMdfAnnotation )
            	    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1483:3: lv_annotations_11_0= ruleMdfAnnotation
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getMdfAttributeAccess().getAnnotationsMdfAnnotationParserRuleCall_9_0()); 
            	    	    
            	    pushFollow(FollowSets000.FOLLOW_ruleMdfAnnotation_in_ruleMdfAttribute3046);
            	    lv_annotations_11_0=ruleMdfAnnotation();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getMdfAttributeRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"annotations",
            	            		lv_annotations_11_0, 
            	            		"MdfAnnotation");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop50;
                }
            } while (true);

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1499:3: (otherlv_12= 'deprecationInfo' ruleMdfDeprecationInfo )?
            int alt51=2;
            int LA51_0 = input.LA(1);

            if ( (LA51_0==14) ) {
                alt51=1;
            }
            switch (alt51) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1499:5: otherlv_12= 'deprecationInfo' ruleMdfDeprecationInfo
                    {
                    otherlv_12=(Token)match(input,14,FollowSets000.FOLLOW_14_in_ruleMdfAttribute3060); 

                        	newLeafNode(otherlv_12, grammarAccess.getMdfAttributeAccess().getDeprecationInfoKeyword_10_0());
                        
                     
                            newCompositeNode(grammarAccess.getMdfAttributeAccess().getMdfDeprecationInfoParserRuleCall_10_1()); 
                        
                    pushFollow(FollowSets000.FOLLOW_ruleMdfDeprecationInfo_in_ruleMdfAttribute3076);
                    ruleMdfDeprecationInfo();

                    state._fsp--;

                     
                            afterParserOrEnumRuleCall();
                        

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
    // $ANTLR end "ruleMdfAttribute"


    // $ANTLR start "entryRuleMdfAssociation"
    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1519:1: entryRuleMdfAssociation returns [EObject current=null] : iv_ruleMdfAssociation= ruleMdfAssociation EOF ;
    public final EObject entryRuleMdfAssociation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMdfAssociation = null;


        try {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1520:2: (iv_ruleMdfAssociation= ruleMdfAssociation EOF )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1521:2: iv_ruleMdfAssociation= ruleMdfAssociation EOF
            {
             newCompositeNode(grammarAccess.getMdfAssociationRule()); 
            pushFollow(FollowSets000.FOLLOW_ruleMdfAssociation_in_entryRuleMdfAssociation3113);
            iv_ruleMdfAssociation=ruleMdfAssociation();

            state._fsp--;

             current =iv_ruleMdfAssociation; 
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleMdfAssociation3123); 

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
    // $ANTLR end "entryRuleMdfAssociation"


    // $ANTLR start "ruleMdfAssociation"
    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1528:1: ruleMdfAssociation returns [EObject current=null] : ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_name_1_0= ruleString_Value ) ) otherlv_2= '->' ( (lv_containment_3_0= ruleMdfContainment ) )? ( ( ruleRef ) )? ( (lv_multiplicity_5_0= ruleMdfMultiplicity ) )? ( (lv_businessKey_6_0= 'BK' ) )? ( (lv_primaryKey_7_0= 'PK' ) )? ( (lv_required_8_0= 'required' ) )? (otherlv_9= 'reverse' otherlv_10= '{' ( (lv_reverse_11_0= ruleMdfReverseAssociation ) ) otherlv_12= '}' )? ( (lv_annotations_13_0= ruleMdfAnnotation ) )* (otherlv_14= 'deprecationInfo' ruleMdfDeprecationInfo )? ) ;
    public final EObject ruleMdfAssociation() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token lv_businessKey_6_0=null;
        Token lv_primaryKey_7_0=null;
        Token lv_required_8_0=null;
        Token otherlv_9=null;
        Token otherlv_10=null;
        Token otherlv_12=null;
        Token otherlv_14=null;
        AntlrDatatypeRuleToken lv_documentation_0_0 = null;

        AntlrDatatypeRuleToken lv_name_1_0 = null;

        Enumerator lv_containment_3_0 = null;

        Enumerator lv_multiplicity_5_0 = null;

        EObject lv_reverse_11_0 = null;

        EObject lv_annotations_13_0 = null;


         enterRule(); 
            
        try {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1531:28: ( ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_name_1_0= ruleString_Value ) ) otherlv_2= '->' ( (lv_containment_3_0= ruleMdfContainment ) )? ( ( ruleRef ) )? ( (lv_multiplicity_5_0= ruleMdfMultiplicity ) )? ( (lv_businessKey_6_0= 'BK' ) )? ( (lv_primaryKey_7_0= 'PK' ) )? ( (lv_required_8_0= 'required' ) )? (otherlv_9= 'reverse' otherlv_10= '{' ( (lv_reverse_11_0= ruleMdfReverseAssociation ) ) otherlv_12= '}' )? ( (lv_annotations_13_0= ruleMdfAnnotation ) )* (otherlv_14= 'deprecationInfo' ruleMdfDeprecationInfo )? ) )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1532:1: ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_name_1_0= ruleString_Value ) ) otherlv_2= '->' ( (lv_containment_3_0= ruleMdfContainment ) )? ( ( ruleRef ) )? ( (lv_multiplicity_5_0= ruleMdfMultiplicity ) )? ( (lv_businessKey_6_0= 'BK' ) )? ( (lv_primaryKey_7_0= 'PK' ) )? ( (lv_required_8_0= 'required' ) )? (otherlv_9= 'reverse' otherlv_10= '{' ( (lv_reverse_11_0= ruleMdfReverseAssociation ) ) otherlv_12= '}' )? ( (lv_annotations_13_0= ruleMdfAnnotation ) )* (otherlv_14= 'deprecationInfo' ruleMdfDeprecationInfo )? )
            {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1532:1: ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_name_1_0= ruleString_Value ) ) otherlv_2= '->' ( (lv_containment_3_0= ruleMdfContainment ) )? ( ( ruleRef ) )? ( (lv_multiplicity_5_0= ruleMdfMultiplicity ) )? ( (lv_businessKey_6_0= 'BK' ) )? ( (lv_primaryKey_7_0= 'PK' ) )? ( (lv_required_8_0= 'required' ) )? (otherlv_9= 'reverse' otherlv_10= '{' ( (lv_reverse_11_0= ruleMdfReverseAssociation ) ) otherlv_12= '}' )? ( (lv_annotations_13_0= ruleMdfAnnotation ) )* (otherlv_14= 'deprecationInfo' ruleMdfDeprecationInfo )? )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1532:2: ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_name_1_0= ruleString_Value ) ) otherlv_2= '->' ( (lv_containment_3_0= ruleMdfContainment ) )? ( ( ruleRef ) )? ( (lv_multiplicity_5_0= ruleMdfMultiplicity ) )? ( (lv_businessKey_6_0= 'BK' ) )? ( (lv_primaryKey_7_0= 'PK' ) )? ( (lv_required_8_0= 'required' ) )? (otherlv_9= 'reverse' otherlv_10= '{' ( (lv_reverse_11_0= ruleMdfReverseAssociation ) ) otherlv_12= '}' )? ( (lv_annotations_13_0= ruleMdfAnnotation ) )* (otherlv_14= 'deprecationInfo' ruleMdfDeprecationInfo )?
            {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1532:2: ( (lv_documentation_0_0= ruleDocumentation ) )?
            int alt52=2;
            int LA52_0 = input.LA(1);

            if ( (LA52_0==RULE_ML_DOC) ) {
                alt52=1;
            }
            switch (alt52) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1533:1: (lv_documentation_0_0= ruleDocumentation )
                    {
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1533:1: (lv_documentation_0_0= ruleDocumentation )
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1534:3: lv_documentation_0_0= ruleDocumentation
                    {
                     
                    	        newCompositeNode(grammarAccess.getMdfAssociationAccess().getDocumentationDocumentationParserRuleCall_0_0()); 
                    	    
                    pushFollow(FollowSets000.FOLLOW_ruleDocumentation_in_ruleMdfAssociation3169);
                    lv_documentation_0_0=ruleDocumentation();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getMdfAssociationRule());
                    	        }
                           		set(
                           			current, 
                           			"documentation",
                            		lv_documentation_0_0, 
                            		"Documentation");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }
                    break;

            }

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1550:3: ( (lv_name_1_0= ruleString_Value ) )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1551:1: (lv_name_1_0= ruleString_Value )
            {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1551:1: (lv_name_1_0= ruleString_Value )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1552:3: lv_name_1_0= ruleString_Value
            {
             
            	        newCompositeNode(grammarAccess.getMdfAssociationAccess().getNameString_ValueParserRuleCall_1_0()); 
            	    
            pushFollow(FollowSets000.FOLLOW_ruleString_Value_in_ruleMdfAssociation3191);
            lv_name_1_0=ruleString_Value();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getMdfAssociationRule());
            	        }
                   		set(
                   			current, 
                   			"name",
                    		lv_name_1_0, 
                    		"String_Value");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_2=(Token)match(input,40,FollowSets000.FOLLOW_40_in_ruleMdfAssociation3203); 

                	newLeafNode(otherlv_2, grammarAccess.getMdfAssociationAccess().getHyphenMinusGreaterThanSignKeyword_2());
                
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1572:1: ( (lv_containment_3_0= ruleMdfContainment ) )?
            int alt53=2;
            int LA53_0 = input.LA(1);

            if ( ((LA53_0>=49 && LA53_0<=50)) ) {
                alt53=1;
            }
            switch (alt53) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1573:1: (lv_containment_3_0= ruleMdfContainment )
                    {
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1573:1: (lv_containment_3_0= ruleMdfContainment )
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1574:3: lv_containment_3_0= ruleMdfContainment
                    {
                     
                    	        newCompositeNode(grammarAccess.getMdfAssociationAccess().getContainmentMdfContainmentEnumRuleCall_3_0()); 
                    	    
                    pushFollow(FollowSets000.FOLLOW_ruleMdfContainment_in_ruleMdfAssociation3224);
                    lv_containment_3_0=ruleMdfContainment();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getMdfAssociationRule());
                    	        }
                           		set(
                           			current, 
                           			"containment",
                            		lv_containment_3_0, 
                            		"MdfContainment");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }
                    break;

            }

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1590:3: ( ( ruleRef ) )?
            int alt54=2;
            int LA54_0 = input.LA(1);

            if ( (LA54_0==RULE_ID) ) {
                int LA54_1 = input.LA(2);

                if ( (LA54_1==24) ) {
                    int LA54_3 = input.LA(3);

                    if ( (LA54_3==RULE_ID) ) {
                        alt54=1;
                    }
                }
                else if ( (LA54_1==EOF||(LA54_1>=RULE_ID && LA54_1<=RULE_VALUE)||LA54_1==14||LA54_1==17||LA54_1==23||(LA54_1>=37 && LA54_1<=39)||LA54_1==41||(LA54_1>=47 && LA54_1<=48)) ) {
                    alt54=1;
                }
            }
            switch (alt54) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1591:1: ( ruleRef )
                    {
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1591:1: ( ruleRef )
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1592:3: ruleRef
                    {

                    			if (current==null) {
                    	            current = createModelElement(grammarAccess.getMdfAssociationRule());
                    	        }
                            
                     
                    	        newCompositeNode(grammarAccess.getMdfAssociationAccess().getTypeMdfClassCrossReference_4_0()); 
                    	    
                    pushFollow(FollowSets000.FOLLOW_ruleRef_in_ruleMdfAssociation3248);
                    ruleRef();

                    state._fsp--;

                     
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }
                    break;

            }

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1605:3: ( (lv_multiplicity_5_0= ruleMdfMultiplicity ) )?
            int alt55=2;
            int LA55_0 = input.LA(1);

            if ( ((LA55_0>=47 && LA55_0<=48)) ) {
                alt55=1;
            }
            switch (alt55) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1606:1: (lv_multiplicity_5_0= ruleMdfMultiplicity )
                    {
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1606:1: (lv_multiplicity_5_0= ruleMdfMultiplicity )
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1607:3: lv_multiplicity_5_0= ruleMdfMultiplicity
                    {
                     
                    	        newCompositeNode(grammarAccess.getMdfAssociationAccess().getMultiplicityMdfMultiplicityEnumRuleCall_5_0()); 
                    	    
                    pushFollow(FollowSets000.FOLLOW_ruleMdfMultiplicity_in_ruleMdfAssociation3270);
                    lv_multiplicity_5_0=ruleMdfMultiplicity();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getMdfAssociationRule());
                    	        }
                           		set(
                           			current, 
                           			"multiplicity",
                            		lv_multiplicity_5_0, 
                            		"MdfMultiplicity");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }
                    break;

            }

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1623:3: ( (lv_businessKey_6_0= 'BK' ) )?
            int alt56=2;
            int LA56_0 = input.LA(1);

            if ( (LA56_0==37) ) {
                alt56=1;
            }
            switch (alt56) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1624:1: (lv_businessKey_6_0= 'BK' )
                    {
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1624:1: (lv_businessKey_6_0= 'BK' )
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1625:3: lv_businessKey_6_0= 'BK'
                    {
                    lv_businessKey_6_0=(Token)match(input,37,FollowSets000.FOLLOW_37_in_ruleMdfAssociation3289); 

                            newLeafNode(lv_businessKey_6_0, grammarAccess.getMdfAssociationAccess().getBusinessKeyBKKeyword_6_0());
                        

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getMdfAssociationRule());
                    	        }
                           		setWithLastConsumed(current, "businessKey", true, "BK");
                    	    

                    }


                    }
                    break;

            }

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1638:3: ( (lv_primaryKey_7_0= 'PK' ) )?
            int alt57=2;
            int LA57_0 = input.LA(1);

            if ( (LA57_0==38) ) {
                alt57=1;
            }
            switch (alt57) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1639:1: (lv_primaryKey_7_0= 'PK' )
                    {
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1639:1: (lv_primaryKey_7_0= 'PK' )
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1640:3: lv_primaryKey_7_0= 'PK'
                    {
                    lv_primaryKey_7_0=(Token)match(input,38,FollowSets000.FOLLOW_38_in_ruleMdfAssociation3321); 

                            newLeafNode(lv_primaryKey_7_0, grammarAccess.getMdfAssociationAccess().getPrimaryKeyPKKeyword_7_0());
                        

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getMdfAssociationRule());
                    	        }
                           		setWithLastConsumed(current, "primaryKey", true, "PK");
                    	    

                    }


                    }
                    break;

            }

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1653:3: ( (lv_required_8_0= 'required' ) )?
            int alt58=2;
            int LA58_0 = input.LA(1);

            if ( (LA58_0==39) ) {
                alt58=1;
            }
            switch (alt58) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1654:1: (lv_required_8_0= 'required' )
                    {
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1654:1: (lv_required_8_0= 'required' )
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1655:3: lv_required_8_0= 'required'
                    {
                    lv_required_8_0=(Token)match(input,39,FollowSets000.FOLLOW_39_in_ruleMdfAssociation3353); 

                            newLeafNode(lv_required_8_0, grammarAccess.getMdfAssociationAccess().getRequiredRequiredKeyword_8_0());
                        

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getMdfAssociationRule());
                    	        }
                           		setWithLastConsumed(current, "required", true, "required");
                    	    

                    }


                    }
                    break;

            }

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1668:3: (otherlv_9= 'reverse' otherlv_10= '{' ( (lv_reverse_11_0= ruleMdfReverseAssociation ) ) otherlv_12= '}' )?
            int alt59=2;
            int LA59_0 = input.LA(1);

            if ( (LA59_0==41) ) {
                alt59=1;
            }
            switch (alt59) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1668:5: otherlv_9= 'reverse' otherlv_10= '{' ( (lv_reverse_11_0= ruleMdfReverseAssociation ) ) otherlv_12= '}'
                    {
                    otherlv_9=(Token)match(input,41,FollowSets000.FOLLOW_41_in_ruleMdfAssociation3380); 

                        	newLeafNode(otherlv_9, grammarAccess.getMdfAssociationAccess().getReverseKeyword_9_0());
                        
                    otherlv_10=(Token)match(input,16,FollowSets000.FOLLOW_16_in_ruleMdfAssociation3392); 

                        	newLeafNode(otherlv_10, grammarAccess.getMdfAssociationAccess().getLeftCurlyBracketKeyword_9_1());
                        
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1676:1: ( (lv_reverse_11_0= ruleMdfReverseAssociation ) )
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1677:1: (lv_reverse_11_0= ruleMdfReverseAssociation )
                    {
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1677:1: (lv_reverse_11_0= ruleMdfReverseAssociation )
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1678:3: lv_reverse_11_0= ruleMdfReverseAssociation
                    {
                     
                    	        newCompositeNode(grammarAccess.getMdfAssociationAccess().getReverseMdfReverseAssociationParserRuleCall_9_2_0()); 
                    	    
                    pushFollow(FollowSets000.FOLLOW_ruleMdfReverseAssociation_in_ruleMdfAssociation3413);
                    lv_reverse_11_0=ruleMdfReverseAssociation();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getMdfAssociationRule());
                    	        }
                           		set(
                           			current, 
                           			"reverse",
                            		lv_reverse_11_0, 
                            		"MdfReverseAssociation");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    otherlv_12=(Token)match(input,17,FollowSets000.FOLLOW_17_in_ruleMdfAssociation3425); 

                        	newLeafNode(otherlv_12, grammarAccess.getMdfAssociationAccess().getRightCurlyBracketKeyword_9_3());
                        

                    }
                    break;

            }

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1698:3: ( (lv_annotations_13_0= ruleMdfAnnotation ) )*
            loop60:
            do {
                int alt60=2;
                int LA60_0 = input.LA(1);

                if ( (LA60_0==23) ) {
                    alt60=1;
                }


                switch (alt60) {
            	case 1 :
            	    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1699:1: (lv_annotations_13_0= ruleMdfAnnotation )
            	    {
            	    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1699:1: (lv_annotations_13_0= ruleMdfAnnotation )
            	    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1700:3: lv_annotations_13_0= ruleMdfAnnotation
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getMdfAssociationAccess().getAnnotationsMdfAnnotationParserRuleCall_10_0()); 
            	    	    
            	    pushFollow(FollowSets000.FOLLOW_ruleMdfAnnotation_in_ruleMdfAssociation3448);
            	    lv_annotations_13_0=ruleMdfAnnotation();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getMdfAssociationRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"annotations",
            	            		lv_annotations_13_0, 
            	            		"MdfAnnotation");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop60;
                }
            } while (true);

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1716:3: (otherlv_14= 'deprecationInfo' ruleMdfDeprecationInfo )?
            int alt61=2;
            int LA61_0 = input.LA(1);

            if ( (LA61_0==14) ) {
                alt61=1;
            }
            switch (alt61) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1716:5: otherlv_14= 'deprecationInfo' ruleMdfDeprecationInfo
                    {
                    otherlv_14=(Token)match(input,14,FollowSets000.FOLLOW_14_in_ruleMdfAssociation3462); 

                        	newLeafNode(otherlv_14, grammarAccess.getMdfAssociationAccess().getDeprecationInfoKeyword_11_0());
                        
                     
                            newCompositeNode(grammarAccess.getMdfAssociationAccess().getMdfDeprecationInfoParserRuleCall_11_1()); 
                        
                    pushFollow(FollowSets000.FOLLOW_ruleMdfDeprecationInfo_in_ruleMdfAssociation3478);
                    ruleMdfDeprecationInfo();

                    state._fsp--;

                     
                            afterParserOrEnumRuleCall();
                        

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
    // $ANTLR end "ruleMdfAssociation"


    // $ANTLR start "entryRuleMdfReverseAssociation"
    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1736:1: entryRuleMdfReverseAssociation returns [EObject current=null] : iv_ruleMdfReverseAssociation= ruleMdfReverseAssociation EOF ;
    public final EObject entryRuleMdfReverseAssociation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMdfReverseAssociation = null;


        try {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1737:2: (iv_ruleMdfReverseAssociation= ruleMdfReverseAssociation EOF )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1738:2: iv_ruleMdfReverseAssociation= ruleMdfReverseAssociation EOF
            {
             newCompositeNode(grammarAccess.getMdfReverseAssociationRule()); 
            pushFollow(FollowSets000.FOLLOW_ruleMdfReverseAssociation_in_entryRuleMdfReverseAssociation3515);
            iv_ruleMdfReverseAssociation=ruleMdfReverseAssociation();

            state._fsp--;

             current =iv_ruleMdfReverseAssociation; 
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleMdfReverseAssociation3525); 

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
    // $ANTLR end "entryRuleMdfReverseAssociation"


    // $ANTLR start "ruleMdfReverseAssociation"
    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1745:1: ruleMdfReverseAssociation returns [EObject current=null] : ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_name_1_0= ruleString_Value ) ) otherlv_2= '<-' ( ( ruleRef ) ) otherlv_4= ':' ( (otherlv_5= RULE_ID ) ) ( (lv_multiplicity_6_0= ruleMdfMultiplicity ) )? ( (lv_businessKey_7_0= 'BK' ) )? ( (lv_primaryKey_8_0= 'PK' ) )? ( (lv_required_9_0= 'required' ) )? ( (lv_annotations_10_0= ruleMdfAnnotation ) )* (otherlv_11= 'deprecationInfo' ruleMdfDeprecationInfo )? ) ;
    public final EObject ruleMdfReverseAssociation() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token lv_businessKey_7_0=null;
        Token lv_primaryKey_8_0=null;
        Token lv_required_9_0=null;
        Token otherlv_11=null;
        AntlrDatatypeRuleToken lv_documentation_0_0 = null;

        AntlrDatatypeRuleToken lv_name_1_0 = null;

        Enumerator lv_multiplicity_6_0 = null;

        EObject lv_annotations_10_0 = null;


         enterRule(); 
            
        try {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1748:28: ( ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_name_1_0= ruleString_Value ) ) otherlv_2= '<-' ( ( ruleRef ) ) otherlv_4= ':' ( (otherlv_5= RULE_ID ) ) ( (lv_multiplicity_6_0= ruleMdfMultiplicity ) )? ( (lv_businessKey_7_0= 'BK' ) )? ( (lv_primaryKey_8_0= 'PK' ) )? ( (lv_required_9_0= 'required' ) )? ( (lv_annotations_10_0= ruleMdfAnnotation ) )* (otherlv_11= 'deprecationInfo' ruleMdfDeprecationInfo )? ) )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1749:1: ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_name_1_0= ruleString_Value ) ) otherlv_2= '<-' ( ( ruleRef ) ) otherlv_4= ':' ( (otherlv_5= RULE_ID ) ) ( (lv_multiplicity_6_0= ruleMdfMultiplicity ) )? ( (lv_businessKey_7_0= 'BK' ) )? ( (lv_primaryKey_8_0= 'PK' ) )? ( (lv_required_9_0= 'required' ) )? ( (lv_annotations_10_0= ruleMdfAnnotation ) )* (otherlv_11= 'deprecationInfo' ruleMdfDeprecationInfo )? )
            {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1749:1: ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_name_1_0= ruleString_Value ) ) otherlv_2= '<-' ( ( ruleRef ) ) otherlv_4= ':' ( (otherlv_5= RULE_ID ) ) ( (lv_multiplicity_6_0= ruleMdfMultiplicity ) )? ( (lv_businessKey_7_0= 'BK' ) )? ( (lv_primaryKey_8_0= 'PK' ) )? ( (lv_required_9_0= 'required' ) )? ( (lv_annotations_10_0= ruleMdfAnnotation ) )* (otherlv_11= 'deprecationInfo' ruleMdfDeprecationInfo )? )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1749:2: ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_name_1_0= ruleString_Value ) ) otherlv_2= '<-' ( ( ruleRef ) ) otherlv_4= ':' ( (otherlv_5= RULE_ID ) ) ( (lv_multiplicity_6_0= ruleMdfMultiplicity ) )? ( (lv_businessKey_7_0= 'BK' ) )? ( (lv_primaryKey_8_0= 'PK' ) )? ( (lv_required_9_0= 'required' ) )? ( (lv_annotations_10_0= ruleMdfAnnotation ) )* (otherlv_11= 'deprecationInfo' ruleMdfDeprecationInfo )?
            {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1749:2: ( (lv_documentation_0_0= ruleDocumentation ) )?
            int alt62=2;
            int LA62_0 = input.LA(1);

            if ( (LA62_0==RULE_ML_DOC) ) {
                alt62=1;
            }
            switch (alt62) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1750:1: (lv_documentation_0_0= ruleDocumentation )
                    {
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1750:1: (lv_documentation_0_0= ruleDocumentation )
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1751:3: lv_documentation_0_0= ruleDocumentation
                    {
                     
                    	        newCompositeNode(grammarAccess.getMdfReverseAssociationAccess().getDocumentationDocumentationParserRuleCall_0_0()); 
                    	    
                    pushFollow(FollowSets000.FOLLOW_ruleDocumentation_in_ruleMdfReverseAssociation3571);
                    lv_documentation_0_0=ruleDocumentation();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getMdfReverseAssociationRule());
                    	        }
                           		set(
                           			current, 
                           			"documentation",
                            		lv_documentation_0_0, 
                            		"Documentation");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }
                    break;

            }

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1767:3: ( (lv_name_1_0= ruleString_Value ) )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1768:1: (lv_name_1_0= ruleString_Value )
            {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1768:1: (lv_name_1_0= ruleString_Value )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1769:3: lv_name_1_0= ruleString_Value
            {
             
            	        newCompositeNode(grammarAccess.getMdfReverseAssociationAccess().getNameString_ValueParserRuleCall_1_0()); 
            	    
            pushFollow(FollowSets000.FOLLOW_ruleString_Value_in_ruleMdfReverseAssociation3593);
            lv_name_1_0=ruleString_Value();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getMdfReverseAssociationRule());
            	        }
                   		set(
                   			current, 
                   			"name",
                    		lv_name_1_0, 
                    		"String_Value");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_2=(Token)match(input,42,FollowSets000.FOLLOW_42_in_ruleMdfReverseAssociation3605); 

                	newLeafNode(otherlv_2, grammarAccess.getMdfReverseAssociationAccess().getLessThanSignHyphenMinusKeyword_2());
                
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1789:1: ( ( ruleRef ) )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1790:1: ( ruleRef )
            {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1790:1: ( ruleRef )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1791:3: ruleRef
            {

            			if (current==null) {
            	            current = createModelElement(grammarAccess.getMdfReverseAssociationRule());
            	        }
                    
             
            	        newCompositeNode(grammarAccess.getMdfReverseAssociationAccess().getReversedAssociationTypeMdfClassCrossReference_3_0()); 
            	    
            pushFollow(FollowSets000.FOLLOW_ruleRef_in_ruleMdfReverseAssociation3628);
            ruleRef();

            state._fsp--;

             
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_4=(Token)match(input,24,FollowSets000.FOLLOW_24_in_ruleMdfReverseAssociation3640); 

                	newLeafNode(otherlv_4, grammarAccess.getMdfReverseAssociationAccess().getColonKeyword_4());
                
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1808:1: ( (otherlv_5= RULE_ID ) )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1809:1: (otherlv_5= RULE_ID )
            {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1809:1: (otherlv_5= RULE_ID )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1810:3: otherlv_5= RULE_ID
            {

            			if (current==null) {
            	            current = createModelElement(grammarAccess.getMdfReverseAssociationRule());
            	        }
                    
            otherlv_5=(Token)match(input,RULE_ID,FollowSets000.FOLLOW_RULE_ID_in_ruleMdfReverseAssociation3660); 

            		newLeafNode(otherlv_5, grammarAccess.getMdfReverseAssociationAccess().getReversedAssociationMdfAssociationCrossReference_5_0()); 
            	

            }


            }

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1821:2: ( (lv_multiplicity_6_0= ruleMdfMultiplicity ) )?
            int alt63=2;
            int LA63_0 = input.LA(1);

            if ( ((LA63_0>=47 && LA63_0<=48)) ) {
                alt63=1;
            }
            switch (alt63) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1822:1: (lv_multiplicity_6_0= ruleMdfMultiplicity )
                    {
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1822:1: (lv_multiplicity_6_0= ruleMdfMultiplicity )
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1823:3: lv_multiplicity_6_0= ruleMdfMultiplicity
                    {
                     
                    	        newCompositeNode(grammarAccess.getMdfReverseAssociationAccess().getMultiplicityMdfMultiplicityEnumRuleCall_6_0()); 
                    	    
                    pushFollow(FollowSets000.FOLLOW_ruleMdfMultiplicity_in_ruleMdfReverseAssociation3681);
                    lv_multiplicity_6_0=ruleMdfMultiplicity();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getMdfReverseAssociationRule());
                    	        }
                           		set(
                           			current, 
                           			"multiplicity",
                            		lv_multiplicity_6_0, 
                            		"MdfMultiplicity");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }
                    break;

            }

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1839:3: ( (lv_businessKey_7_0= 'BK' ) )?
            int alt64=2;
            int LA64_0 = input.LA(1);

            if ( (LA64_0==37) ) {
                alt64=1;
            }
            switch (alt64) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1840:1: (lv_businessKey_7_0= 'BK' )
                    {
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1840:1: (lv_businessKey_7_0= 'BK' )
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1841:3: lv_businessKey_7_0= 'BK'
                    {
                    lv_businessKey_7_0=(Token)match(input,37,FollowSets000.FOLLOW_37_in_ruleMdfReverseAssociation3700); 

                            newLeafNode(lv_businessKey_7_0, grammarAccess.getMdfReverseAssociationAccess().getBusinessKeyBKKeyword_7_0());
                        

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getMdfReverseAssociationRule());
                    	        }
                           		setWithLastConsumed(current, "businessKey", true, "BK");
                    	    

                    }


                    }
                    break;

            }

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1854:3: ( (lv_primaryKey_8_0= 'PK' ) )?
            int alt65=2;
            int LA65_0 = input.LA(1);

            if ( (LA65_0==38) ) {
                alt65=1;
            }
            switch (alt65) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1855:1: (lv_primaryKey_8_0= 'PK' )
                    {
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1855:1: (lv_primaryKey_8_0= 'PK' )
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1856:3: lv_primaryKey_8_0= 'PK'
                    {
                    lv_primaryKey_8_0=(Token)match(input,38,FollowSets000.FOLLOW_38_in_ruleMdfReverseAssociation3732); 

                            newLeafNode(lv_primaryKey_8_0, grammarAccess.getMdfReverseAssociationAccess().getPrimaryKeyPKKeyword_8_0());
                        

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getMdfReverseAssociationRule());
                    	        }
                           		setWithLastConsumed(current, "primaryKey", true, "PK");
                    	    

                    }


                    }
                    break;

            }

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1869:3: ( (lv_required_9_0= 'required' ) )?
            int alt66=2;
            int LA66_0 = input.LA(1);

            if ( (LA66_0==39) ) {
                alt66=1;
            }
            switch (alt66) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1870:1: (lv_required_9_0= 'required' )
                    {
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1870:1: (lv_required_9_0= 'required' )
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1871:3: lv_required_9_0= 'required'
                    {
                    lv_required_9_0=(Token)match(input,39,FollowSets000.FOLLOW_39_in_ruleMdfReverseAssociation3764); 

                            newLeafNode(lv_required_9_0, grammarAccess.getMdfReverseAssociationAccess().getRequiredRequiredKeyword_9_0());
                        

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getMdfReverseAssociationRule());
                    	        }
                           		setWithLastConsumed(current, "required", true, "required");
                    	    

                    }


                    }
                    break;

            }

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1884:3: ( (lv_annotations_10_0= ruleMdfAnnotation ) )*
            loop67:
            do {
                int alt67=2;
                int LA67_0 = input.LA(1);

                if ( (LA67_0==23) ) {
                    alt67=1;
                }


                switch (alt67) {
            	case 1 :
            	    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1885:1: (lv_annotations_10_0= ruleMdfAnnotation )
            	    {
            	    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1885:1: (lv_annotations_10_0= ruleMdfAnnotation )
            	    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1886:3: lv_annotations_10_0= ruleMdfAnnotation
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getMdfReverseAssociationAccess().getAnnotationsMdfAnnotationParserRuleCall_10_0()); 
            	    	    
            	    pushFollow(FollowSets000.FOLLOW_ruleMdfAnnotation_in_ruleMdfReverseAssociation3799);
            	    lv_annotations_10_0=ruleMdfAnnotation();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getMdfReverseAssociationRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"annotations",
            	            		lv_annotations_10_0, 
            	            		"MdfAnnotation");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop67;
                }
            } while (true);

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1902:3: (otherlv_11= 'deprecationInfo' ruleMdfDeprecationInfo )?
            int alt68=2;
            int LA68_0 = input.LA(1);

            if ( (LA68_0==14) ) {
                alt68=1;
            }
            switch (alt68) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1902:5: otherlv_11= 'deprecationInfo' ruleMdfDeprecationInfo
                    {
                    otherlv_11=(Token)match(input,14,FollowSets000.FOLLOW_14_in_ruleMdfReverseAssociation3813); 

                        	newLeafNode(otherlv_11, grammarAccess.getMdfReverseAssociationAccess().getDeprecationInfoKeyword_11_0());
                        
                     
                            newCompositeNode(grammarAccess.getMdfReverseAssociationAccess().getMdfDeprecationInfoParserRuleCall_11_1()); 
                        
                    pushFollow(FollowSets000.FOLLOW_ruleMdfDeprecationInfo_in_ruleMdfReverseAssociation3829);
                    ruleMdfDeprecationInfo();

                    state._fsp--;

                     
                            afterParserOrEnumRuleCall();
                        

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
    // $ANTLR end "ruleMdfReverseAssociation"


    // $ANTLR start "entryRuleMdfDatasetDerivedProperty"
    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1922:1: entryRuleMdfDatasetDerivedProperty returns [EObject current=null] : iv_ruleMdfDatasetDerivedProperty= ruleMdfDatasetDerivedProperty EOF ;
    public final EObject entryRuleMdfDatasetDerivedProperty() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMdfDatasetDerivedProperty = null;


        try {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1923:2: (iv_ruleMdfDatasetDerivedProperty= ruleMdfDatasetDerivedProperty EOF )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1924:2: iv_ruleMdfDatasetDerivedProperty= ruleMdfDatasetDerivedProperty EOF
            {
             newCompositeNode(grammarAccess.getMdfDatasetDerivedPropertyRule()); 
            pushFollow(FollowSets000.FOLLOW_ruleMdfDatasetDerivedProperty_in_entryRuleMdfDatasetDerivedProperty3866);
            iv_ruleMdfDatasetDerivedProperty=ruleMdfDatasetDerivedProperty();

            state._fsp--;

             current =iv_ruleMdfDatasetDerivedProperty; 
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleMdfDatasetDerivedProperty3876); 

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
    // $ANTLR end "entryRuleMdfDatasetDerivedProperty"


    // $ANTLR start "ruleMdfDatasetDerivedProperty"
    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1931:1: ruleMdfDatasetDerivedProperty returns [EObject current=null] : ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_name_1_0= ruleString_Value ) ) otherlv_2= ':' ( ( ruleRef ) )? (otherlv_4= 'default' otherlv_5= '=' ( (lv_default_6_0= ruleString_Value ) ) )? ( (lv_multiplicity_7_0= ruleMdfMultiplicity ) )? ( (lv_annotations_8_0= ruleMdfAnnotation ) )* (otherlv_9= 'deprecationInfo' ruleMdfDeprecationInfo )? ) ;
    public final EObject ruleMdfDatasetDerivedProperty() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token otherlv_9=null;
        AntlrDatatypeRuleToken lv_documentation_0_0 = null;

        AntlrDatatypeRuleToken lv_name_1_0 = null;

        AntlrDatatypeRuleToken lv_default_6_0 = null;

        Enumerator lv_multiplicity_7_0 = null;

        EObject lv_annotations_8_0 = null;


         enterRule(); 
            
        try {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1934:28: ( ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_name_1_0= ruleString_Value ) ) otherlv_2= ':' ( ( ruleRef ) )? (otherlv_4= 'default' otherlv_5= '=' ( (lv_default_6_0= ruleString_Value ) ) )? ( (lv_multiplicity_7_0= ruleMdfMultiplicity ) )? ( (lv_annotations_8_0= ruleMdfAnnotation ) )* (otherlv_9= 'deprecationInfo' ruleMdfDeprecationInfo )? ) )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1935:1: ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_name_1_0= ruleString_Value ) ) otherlv_2= ':' ( ( ruleRef ) )? (otherlv_4= 'default' otherlv_5= '=' ( (lv_default_6_0= ruleString_Value ) ) )? ( (lv_multiplicity_7_0= ruleMdfMultiplicity ) )? ( (lv_annotations_8_0= ruleMdfAnnotation ) )* (otherlv_9= 'deprecationInfo' ruleMdfDeprecationInfo )? )
            {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1935:1: ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_name_1_0= ruleString_Value ) ) otherlv_2= ':' ( ( ruleRef ) )? (otherlv_4= 'default' otherlv_5= '=' ( (lv_default_6_0= ruleString_Value ) ) )? ( (lv_multiplicity_7_0= ruleMdfMultiplicity ) )? ( (lv_annotations_8_0= ruleMdfAnnotation ) )* (otherlv_9= 'deprecationInfo' ruleMdfDeprecationInfo )? )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1935:2: ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_name_1_0= ruleString_Value ) ) otherlv_2= ':' ( ( ruleRef ) )? (otherlv_4= 'default' otherlv_5= '=' ( (lv_default_6_0= ruleString_Value ) ) )? ( (lv_multiplicity_7_0= ruleMdfMultiplicity ) )? ( (lv_annotations_8_0= ruleMdfAnnotation ) )* (otherlv_9= 'deprecationInfo' ruleMdfDeprecationInfo )?
            {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1935:2: ( (lv_documentation_0_0= ruleDocumentation ) )?
            int alt69=2;
            int LA69_0 = input.LA(1);

            if ( (LA69_0==RULE_ML_DOC) ) {
                alt69=1;
            }
            switch (alt69) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1936:1: (lv_documentation_0_0= ruleDocumentation )
                    {
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1936:1: (lv_documentation_0_0= ruleDocumentation )
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1937:3: lv_documentation_0_0= ruleDocumentation
                    {
                     
                    	        newCompositeNode(grammarAccess.getMdfDatasetDerivedPropertyAccess().getDocumentationDocumentationParserRuleCall_0_0()); 
                    	    
                    pushFollow(FollowSets000.FOLLOW_ruleDocumentation_in_ruleMdfDatasetDerivedProperty3922);
                    lv_documentation_0_0=ruleDocumentation();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getMdfDatasetDerivedPropertyRule());
                    	        }
                           		set(
                           			current, 
                           			"documentation",
                            		lv_documentation_0_0, 
                            		"Documentation");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }
                    break;

            }

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1953:3: ( (lv_name_1_0= ruleString_Value ) )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1954:1: (lv_name_1_0= ruleString_Value )
            {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1954:1: (lv_name_1_0= ruleString_Value )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1955:3: lv_name_1_0= ruleString_Value
            {
             
            	        newCompositeNode(grammarAccess.getMdfDatasetDerivedPropertyAccess().getNameString_ValueParserRuleCall_1_0()); 
            	    
            pushFollow(FollowSets000.FOLLOW_ruleString_Value_in_ruleMdfDatasetDerivedProperty3944);
            lv_name_1_0=ruleString_Value();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getMdfDatasetDerivedPropertyRule());
            	        }
                   		set(
                   			current, 
                   			"name",
                    		lv_name_1_0, 
                    		"String_Value");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_2=(Token)match(input,24,FollowSets000.FOLLOW_24_in_ruleMdfDatasetDerivedProperty3956); 

                	newLeafNode(otherlv_2, grammarAccess.getMdfDatasetDerivedPropertyAccess().getColonKeyword_2());
                
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1975:1: ( ( ruleRef ) )?
            int alt70=2;
            int LA70_0 = input.LA(1);

            if ( (LA70_0==RULE_ID) ) {
                int LA70_1 = input.LA(2);

                if ( (LA70_1==24) ) {
                    int LA70_3 = input.LA(3);

                    if ( (LA70_3==RULE_ID) ) {
                        alt70=1;
                    }
                }
                else if ( (LA70_1==EOF||(LA70_1>=RULE_ID && LA70_1<=RULE_VALUE)||LA70_1==14||LA70_1==17||LA70_1==23||LA70_1==32||(LA70_1>=47 && LA70_1<=48)) ) {
                    alt70=1;
                }
            }
            switch (alt70) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1976:1: ( ruleRef )
                    {
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1976:1: ( ruleRef )
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1977:3: ruleRef
                    {

                    			if (current==null) {
                    	            current = createModelElement(grammarAccess.getMdfDatasetDerivedPropertyRule());
                    	        }
                            
                     
                    	        newCompositeNode(grammarAccess.getMdfDatasetDerivedPropertyAccess().getTypeMdfEntityCrossReference_3_0()); 
                    	    
                    pushFollow(FollowSets000.FOLLOW_ruleRef_in_ruleMdfDatasetDerivedProperty3979);
                    ruleRef();

                    state._fsp--;

                     
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }
                    break;

            }

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1990:3: (otherlv_4= 'default' otherlv_5= '=' ( (lv_default_6_0= ruleString_Value ) ) )?
            int alt71=2;
            int LA71_0 = input.LA(1);

            if ( (LA71_0==32) ) {
                alt71=1;
            }
            switch (alt71) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1990:5: otherlv_4= 'default' otherlv_5= '=' ( (lv_default_6_0= ruleString_Value ) )
                    {
                    otherlv_4=(Token)match(input,32,FollowSets000.FOLLOW_32_in_ruleMdfDatasetDerivedProperty3993); 

                        	newLeafNode(otherlv_4, grammarAccess.getMdfDatasetDerivedPropertyAccess().getDefaultKeyword_4_0());
                        
                    otherlv_5=(Token)match(input,36,FollowSets000.FOLLOW_36_in_ruleMdfDatasetDerivedProperty4005); 

                        	newLeafNode(otherlv_5, grammarAccess.getMdfDatasetDerivedPropertyAccess().getEqualsSignKeyword_4_1());
                        
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1998:1: ( (lv_default_6_0= ruleString_Value ) )
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1999:1: (lv_default_6_0= ruleString_Value )
                    {
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:1999:1: (lv_default_6_0= ruleString_Value )
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2000:3: lv_default_6_0= ruleString_Value
                    {
                     
                    	        newCompositeNode(grammarAccess.getMdfDatasetDerivedPropertyAccess().getDefaultString_ValueParserRuleCall_4_2_0()); 
                    	    
                    pushFollow(FollowSets000.FOLLOW_ruleString_Value_in_ruleMdfDatasetDerivedProperty4026);
                    lv_default_6_0=ruleString_Value();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getMdfDatasetDerivedPropertyRule());
                    	        }
                           		set(
                           			current, 
                           			"default",
                            		lv_default_6_0, 
                            		"String_Value");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }


                    }
                    break;

            }

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2016:4: ( (lv_multiplicity_7_0= ruleMdfMultiplicity ) )?
            int alt72=2;
            int LA72_0 = input.LA(1);

            if ( ((LA72_0>=47 && LA72_0<=48)) ) {
                alt72=1;
            }
            switch (alt72) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2017:1: (lv_multiplicity_7_0= ruleMdfMultiplicity )
                    {
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2017:1: (lv_multiplicity_7_0= ruleMdfMultiplicity )
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2018:3: lv_multiplicity_7_0= ruleMdfMultiplicity
                    {
                     
                    	        newCompositeNode(grammarAccess.getMdfDatasetDerivedPropertyAccess().getMultiplicityMdfMultiplicityEnumRuleCall_5_0()); 
                    	    
                    pushFollow(FollowSets000.FOLLOW_ruleMdfMultiplicity_in_ruleMdfDatasetDerivedProperty4049);
                    lv_multiplicity_7_0=ruleMdfMultiplicity();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getMdfDatasetDerivedPropertyRule());
                    	        }
                           		set(
                           			current, 
                           			"multiplicity",
                            		lv_multiplicity_7_0, 
                            		"MdfMultiplicity");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }
                    break;

            }

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2034:3: ( (lv_annotations_8_0= ruleMdfAnnotation ) )*
            loop73:
            do {
                int alt73=2;
                int LA73_0 = input.LA(1);

                if ( (LA73_0==23) ) {
                    alt73=1;
                }


                switch (alt73) {
            	case 1 :
            	    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2035:1: (lv_annotations_8_0= ruleMdfAnnotation )
            	    {
            	    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2035:1: (lv_annotations_8_0= ruleMdfAnnotation )
            	    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2036:3: lv_annotations_8_0= ruleMdfAnnotation
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getMdfDatasetDerivedPropertyAccess().getAnnotationsMdfAnnotationParserRuleCall_6_0()); 
            	    	    
            	    pushFollow(FollowSets000.FOLLOW_ruleMdfAnnotation_in_ruleMdfDatasetDerivedProperty4071);
            	    lv_annotations_8_0=ruleMdfAnnotation();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getMdfDatasetDerivedPropertyRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"annotations",
            	            		lv_annotations_8_0, 
            	            		"MdfAnnotation");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop73;
                }
            } while (true);

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2052:3: (otherlv_9= 'deprecationInfo' ruleMdfDeprecationInfo )?
            int alt74=2;
            int LA74_0 = input.LA(1);

            if ( (LA74_0==14) ) {
                alt74=1;
            }
            switch (alt74) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2052:5: otherlv_9= 'deprecationInfo' ruleMdfDeprecationInfo
                    {
                    otherlv_9=(Token)match(input,14,FollowSets000.FOLLOW_14_in_ruleMdfDatasetDerivedProperty4085); 

                        	newLeafNode(otherlv_9, grammarAccess.getMdfDatasetDerivedPropertyAccess().getDeprecationInfoKeyword_7_0());
                        
                     
                            newCompositeNode(grammarAccess.getMdfDatasetDerivedPropertyAccess().getMdfDeprecationInfoParserRuleCall_7_1()); 
                        
                    pushFollow(FollowSets000.FOLLOW_ruleMdfDeprecationInfo_in_ruleMdfDatasetDerivedProperty4101);
                    ruleMdfDeprecationInfo();

                    state._fsp--;

                     
                            afterParserOrEnumRuleCall();
                        

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
    // $ANTLR end "ruleMdfDatasetDerivedProperty"


    // $ANTLR start "entryRuleMdfDatasetMappedProperty"
    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2072:1: entryRuleMdfDatasetMappedProperty returns [EObject current=null] : iv_ruleMdfDatasetMappedProperty= ruleMdfDatasetMappedProperty EOF ;
    public final EObject entryRuleMdfDatasetMappedProperty() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMdfDatasetMappedProperty = null;


        try {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2073:2: (iv_ruleMdfDatasetMappedProperty= ruleMdfDatasetMappedProperty EOF )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2074:2: iv_ruleMdfDatasetMappedProperty= ruleMdfDatasetMappedProperty EOF
            {
             newCompositeNode(grammarAccess.getMdfDatasetMappedPropertyRule()); 
            pushFollow(FollowSets000.FOLLOW_ruleMdfDatasetMappedProperty_in_entryRuleMdfDatasetMappedProperty4138);
            iv_ruleMdfDatasetMappedProperty=ruleMdfDatasetMappedProperty();

            state._fsp--;

             current =iv_ruleMdfDatasetMappedProperty; 
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleMdfDatasetMappedProperty4148); 

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
    // $ANTLR end "entryRuleMdfDatasetMappedProperty"


    // $ANTLR start "ruleMdfDatasetMappedProperty"
    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2081:1: ruleMdfDatasetMappedProperty returns [EObject current=null] : ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_name_1_0= ruleString_Value ) ) ( (lv_unique_2_0= ruleUniqueBoolean ) )? ( (lv_singleValued_3_0= 'singleValued' ) )? otherlv_4= '->' ( (lv_path_5_0= ruleString_Value ) )? (otherlv_6= '[' ( ( ruleRef ) ) otherlv_8= ']' )? ( (lv_annotations_9_0= ruleMdfAnnotation ) )* (otherlv_10= 'deprecationInfo' ruleMdfDeprecationInfo )? ) ;
    public final EObject ruleMdfDatasetMappedProperty() throws RecognitionException {
        EObject current = null;

        Token lv_singleValued_3_0=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        Token otherlv_10=null;
        AntlrDatatypeRuleToken lv_documentation_0_0 = null;

        AntlrDatatypeRuleToken lv_name_1_0 = null;

        AntlrDatatypeRuleToken lv_unique_2_0 = null;

        AntlrDatatypeRuleToken lv_path_5_0 = null;

        EObject lv_annotations_9_0 = null;


         enterRule(); 
            
        try {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2084:28: ( ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_name_1_0= ruleString_Value ) ) ( (lv_unique_2_0= ruleUniqueBoolean ) )? ( (lv_singleValued_3_0= 'singleValued' ) )? otherlv_4= '->' ( (lv_path_5_0= ruleString_Value ) )? (otherlv_6= '[' ( ( ruleRef ) ) otherlv_8= ']' )? ( (lv_annotations_9_0= ruleMdfAnnotation ) )* (otherlv_10= 'deprecationInfo' ruleMdfDeprecationInfo )? ) )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2085:1: ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_name_1_0= ruleString_Value ) ) ( (lv_unique_2_0= ruleUniqueBoolean ) )? ( (lv_singleValued_3_0= 'singleValued' ) )? otherlv_4= '->' ( (lv_path_5_0= ruleString_Value ) )? (otherlv_6= '[' ( ( ruleRef ) ) otherlv_8= ']' )? ( (lv_annotations_9_0= ruleMdfAnnotation ) )* (otherlv_10= 'deprecationInfo' ruleMdfDeprecationInfo )? )
            {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2085:1: ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_name_1_0= ruleString_Value ) ) ( (lv_unique_2_0= ruleUniqueBoolean ) )? ( (lv_singleValued_3_0= 'singleValued' ) )? otherlv_4= '->' ( (lv_path_5_0= ruleString_Value ) )? (otherlv_6= '[' ( ( ruleRef ) ) otherlv_8= ']' )? ( (lv_annotations_9_0= ruleMdfAnnotation ) )* (otherlv_10= 'deprecationInfo' ruleMdfDeprecationInfo )? )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2085:2: ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_name_1_0= ruleString_Value ) ) ( (lv_unique_2_0= ruleUniqueBoolean ) )? ( (lv_singleValued_3_0= 'singleValued' ) )? otherlv_4= '->' ( (lv_path_5_0= ruleString_Value ) )? (otherlv_6= '[' ( ( ruleRef ) ) otherlv_8= ']' )? ( (lv_annotations_9_0= ruleMdfAnnotation ) )* (otherlv_10= 'deprecationInfo' ruleMdfDeprecationInfo )?
            {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2085:2: ( (lv_documentation_0_0= ruleDocumentation ) )?
            int alt75=2;
            int LA75_0 = input.LA(1);

            if ( (LA75_0==RULE_ML_DOC) ) {
                alt75=1;
            }
            switch (alt75) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2086:1: (lv_documentation_0_0= ruleDocumentation )
                    {
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2086:1: (lv_documentation_0_0= ruleDocumentation )
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2087:3: lv_documentation_0_0= ruleDocumentation
                    {
                     
                    	        newCompositeNode(grammarAccess.getMdfDatasetMappedPropertyAccess().getDocumentationDocumentationParserRuleCall_0_0()); 
                    	    
                    pushFollow(FollowSets000.FOLLOW_ruleDocumentation_in_ruleMdfDatasetMappedProperty4194);
                    lv_documentation_0_0=ruleDocumentation();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getMdfDatasetMappedPropertyRule());
                    	        }
                           		set(
                           			current, 
                           			"documentation",
                            		lv_documentation_0_0, 
                            		"Documentation");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }
                    break;

            }

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2103:3: ( (lv_name_1_0= ruleString_Value ) )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2104:1: (lv_name_1_0= ruleString_Value )
            {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2104:1: (lv_name_1_0= ruleString_Value )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2105:3: lv_name_1_0= ruleString_Value
            {
             
            	        newCompositeNode(grammarAccess.getMdfDatasetMappedPropertyAccess().getNameString_ValueParserRuleCall_1_0()); 
            	    
            pushFollow(FollowSets000.FOLLOW_ruleString_Value_in_ruleMdfDatasetMappedProperty4216);
            lv_name_1_0=ruleString_Value();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getMdfDatasetMappedPropertyRule());
            	        }
                   		set(
                   			current, 
                   			"name",
                    		lv_name_1_0, 
                    		"String_Value");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2121:2: ( (lv_unique_2_0= ruleUniqueBoolean ) )?
            int alt76=2;
            int LA76_0 = input.LA(1);

            if ( (LA76_0==46) ) {
                alt76=1;
            }
            switch (alt76) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2122:1: (lv_unique_2_0= ruleUniqueBoolean )
                    {
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2122:1: (lv_unique_2_0= ruleUniqueBoolean )
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2123:3: lv_unique_2_0= ruleUniqueBoolean
                    {
                     
                    	        newCompositeNode(grammarAccess.getMdfDatasetMappedPropertyAccess().getUniqueUniqueBooleanParserRuleCall_2_0()); 
                    	    
                    pushFollow(FollowSets000.FOLLOW_ruleUniqueBoolean_in_ruleMdfDatasetMappedProperty4237);
                    lv_unique_2_0=ruleUniqueBoolean();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getMdfDatasetMappedPropertyRule());
                    	        }
                           		set(
                           			current, 
                           			"unique",
                            		lv_unique_2_0, 
                            		"UniqueBoolean");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }
                    break;

            }

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2139:3: ( (lv_singleValued_3_0= 'singleValued' ) )?
            int alt77=2;
            int LA77_0 = input.LA(1);

            if ( (LA77_0==43) ) {
                alt77=1;
            }
            switch (alt77) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2140:1: (lv_singleValued_3_0= 'singleValued' )
                    {
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2140:1: (lv_singleValued_3_0= 'singleValued' )
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2141:3: lv_singleValued_3_0= 'singleValued'
                    {
                    lv_singleValued_3_0=(Token)match(input,43,FollowSets000.FOLLOW_43_in_ruleMdfDatasetMappedProperty4256); 

                            newLeafNode(lv_singleValued_3_0, grammarAccess.getMdfDatasetMappedPropertyAccess().getSingleValuedSingleValuedKeyword_3_0());
                        

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getMdfDatasetMappedPropertyRule());
                    	        }
                           		setWithLastConsumed(current, "singleValued", true, "singleValued");
                    	    

                    }


                    }
                    break;

            }

            otherlv_4=(Token)match(input,40,FollowSets000.FOLLOW_40_in_ruleMdfDatasetMappedProperty4282); 

                	newLeafNode(otherlv_4, grammarAccess.getMdfDatasetMappedPropertyAccess().getHyphenMinusGreaterThanSignKeyword_4());
                
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2158:1: ( (lv_path_5_0= ruleString_Value ) )?
            int alt78=2;
            switch ( input.LA(1) ) {
                case RULE_STRING:
                    {
                    int LA78_1 = input.LA(2);

                    if ( (LA78_1==EOF||(LA78_1>=RULE_ID && LA78_1<=RULE_VALUE)||LA78_1==14||LA78_1==17||LA78_1==23||LA78_1==44) ) {
                        alt78=1;
                    }
                    }
                    break;
                case RULE_ID:
                    {
                    int LA78_2 = input.LA(2);

                    if ( (LA78_2==EOF||(LA78_2>=RULE_ID && LA78_2<=RULE_VALUE)||LA78_2==14||LA78_2==17||LA78_2==23||LA78_2==44) ) {
                        alt78=1;
                    }
                    }
                    break;
                case RULE_VALUE:
                    {
                    int LA78_3 = input.LA(2);

                    if ( (LA78_3==EOF||(LA78_3>=RULE_ID && LA78_3<=RULE_VALUE)||LA78_3==14||LA78_3==17||LA78_3==23||LA78_3==44) ) {
                        alt78=1;
                    }
                    }
                    break;
            }

            switch (alt78) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2159:1: (lv_path_5_0= ruleString_Value )
                    {
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2159:1: (lv_path_5_0= ruleString_Value )
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2160:3: lv_path_5_0= ruleString_Value
                    {
                     
                    	        newCompositeNode(grammarAccess.getMdfDatasetMappedPropertyAccess().getPathString_ValueParserRuleCall_5_0()); 
                    	    
                    pushFollow(FollowSets000.FOLLOW_ruleString_Value_in_ruleMdfDatasetMappedProperty4303);
                    lv_path_5_0=ruleString_Value();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getMdfDatasetMappedPropertyRule());
                    	        }
                           		set(
                           			current, 
                           			"path",
                            		lv_path_5_0, 
                            		"String_Value");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }
                    break;

            }

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2176:3: (otherlv_6= '[' ( ( ruleRef ) ) otherlv_8= ']' )?
            int alt79=2;
            int LA79_0 = input.LA(1);

            if ( (LA79_0==44) ) {
                alt79=1;
            }
            switch (alt79) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2176:5: otherlv_6= '[' ( ( ruleRef ) ) otherlv_8= ']'
                    {
                    otherlv_6=(Token)match(input,44,FollowSets000.FOLLOW_44_in_ruleMdfDatasetMappedProperty4317); 

                        	newLeafNode(otherlv_6, grammarAccess.getMdfDatasetMappedPropertyAccess().getLeftSquareBracketKeyword_6_0());
                        
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2180:1: ( ( ruleRef ) )
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2181:1: ( ruleRef )
                    {
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2181:1: ( ruleRef )
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2182:3: ruleRef
                    {

                    			if (current==null) {
                    	            current = createModelElement(grammarAccess.getMdfDatasetMappedPropertyRule());
                    	        }
                            
                     
                    	        newCompositeNode(grammarAccess.getMdfDatasetMappedPropertyAccess().getLinkDatasetMdfDatasetCrossReference_6_1_0()); 
                    	    
                    pushFollow(FollowSets000.FOLLOW_ruleRef_in_ruleMdfDatasetMappedProperty4340);
                    ruleRef();

                    state._fsp--;

                     
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    otherlv_8=(Token)match(input,45,FollowSets000.FOLLOW_45_in_ruleMdfDatasetMappedProperty4352); 

                        	newLeafNode(otherlv_8, grammarAccess.getMdfDatasetMappedPropertyAccess().getRightSquareBracketKeyword_6_2());
                        

                    }
                    break;

            }

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2199:3: ( (lv_annotations_9_0= ruleMdfAnnotation ) )*
            loop80:
            do {
                int alt80=2;
                int LA80_0 = input.LA(1);

                if ( (LA80_0==23) ) {
                    alt80=1;
                }


                switch (alt80) {
            	case 1 :
            	    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2200:1: (lv_annotations_9_0= ruleMdfAnnotation )
            	    {
            	    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2200:1: (lv_annotations_9_0= ruleMdfAnnotation )
            	    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2201:3: lv_annotations_9_0= ruleMdfAnnotation
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getMdfDatasetMappedPropertyAccess().getAnnotationsMdfAnnotationParserRuleCall_7_0()); 
            	    	    
            	    pushFollow(FollowSets000.FOLLOW_ruleMdfAnnotation_in_ruleMdfDatasetMappedProperty4375);
            	    lv_annotations_9_0=ruleMdfAnnotation();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getMdfDatasetMappedPropertyRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"annotations",
            	            		lv_annotations_9_0, 
            	            		"MdfAnnotation");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop80;
                }
            } while (true);

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2217:3: (otherlv_10= 'deprecationInfo' ruleMdfDeprecationInfo )?
            int alt81=2;
            int LA81_0 = input.LA(1);

            if ( (LA81_0==14) ) {
                alt81=1;
            }
            switch (alt81) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2217:5: otherlv_10= 'deprecationInfo' ruleMdfDeprecationInfo
                    {
                    otherlv_10=(Token)match(input,14,FollowSets000.FOLLOW_14_in_ruleMdfDatasetMappedProperty4389); 

                        	newLeafNode(otherlv_10, grammarAccess.getMdfDatasetMappedPropertyAccess().getDeprecationInfoKeyword_8_0());
                        
                     
                            newCompositeNode(grammarAccess.getMdfDatasetMappedPropertyAccess().getMdfDeprecationInfoParserRuleCall_8_1()); 
                        
                    pushFollow(FollowSets000.FOLLOW_ruleMdfDeprecationInfo_in_ruleMdfDatasetMappedProperty4405);
                    ruleMdfDeprecationInfo();

                    state._fsp--;

                     
                            afterParserOrEnumRuleCall();
                        

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
    // $ANTLR end "ruleMdfDatasetMappedProperty"


    // $ANTLR start "entryRuleMdfEnumValue"
    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2237:1: entryRuleMdfEnumValue returns [EObject current=null] : iv_ruleMdfEnumValue= ruleMdfEnumValue EOF ;
    public final EObject entryRuleMdfEnumValue() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMdfEnumValue = null;


        try {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2238:2: (iv_ruleMdfEnumValue= ruleMdfEnumValue EOF )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2239:2: iv_ruleMdfEnumValue= ruleMdfEnumValue EOF
            {
             newCompositeNode(grammarAccess.getMdfEnumValueRule()); 
            pushFollow(FollowSets000.FOLLOW_ruleMdfEnumValue_in_entryRuleMdfEnumValue4442);
            iv_ruleMdfEnumValue=ruleMdfEnumValue();

            state._fsp--;

             current =iv_ruleMdfEnumValue; 
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleMdfEnumValue4452); 

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
    // $ANTLR end "entryRuleMdfEnumValue"


    // $ANTLR start "ruleMdfEnumValue"
    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2246:1: ruleMdfEnumValue returns [EObject current=null] : ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_name_1_0= ruleString_Value ) ) otherlv_2= '=' ( (lv_value_3_0= ruleString_Value ) ) ( (lv_annotations_4_0= ruleMdfAnnotation ) )* (otherlv_5= 'deprecationInfo' ruleMdfDeprecationInfo )? ) ;
    public final EObject ruleMdfEnumValue() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token otherlv_5=null;
        AntlrDatatypeRuleToken lv_documentation_0_0 = null;

        AntlrDatatypeRuleToken lv_name_1_0 = null;

        AntlrDatatypeRuleToken lv_value_3_0 = null;

        EObject lv_annotations_4_0 = null;


         enterRule(); 
            
        try {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2249:28: ( ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_name_1_0= ruleString_Value ) ) otherlv_2= '=' ( (lv_value_3_0= ruleString_Value ) ) ( (lv_annotations_4_0= ruleMdfAnnotation ) )* (otherlv_5= 'deprecationInfo' ruleMdfDeprecationInfo )? ) )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2250:1: ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_name_1_0= ruleString_Value ) ) otherlv_2= '=' ( (lv_value_3_0= ruleString_Value ) ) ( (lv_annotations_4_0= ruleMdfAnnotation ) )* (otherlv_5= 'deprecationInfo' ruleMdfDeprecationInfo )? )
            {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2250:1: ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_name_1_0= ruleString_Value ) ) otherlv_2= '=' ( (lv_value_3_0= ruleString_Value ) ) ( (lv_annotations_4_0= ruleMdfAnnotation ) )* (otherlv_5= 'deprecationInfo' ruleMdfDeprecationInfo )? )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2250:2: ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_name_1_0= ruleString_Value ) ) otherlv_2= '=' ( (lv_value_3_0= ruleString_Value ) ) ( (lv_annotations_4_0= ruleMdfAnnotation ) )* (otherlv_5= 'deprecationInfo' ruleMdfDeprecationInfo )?
            {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2250:2: ( (lv_documentation_0_0= ruleDocumentation ) )?
            int alt82=2;
            int LA82_0 = input.LA(1);

            if ( (LA82_0==RULE_ML_DOC) ) {
                alt82=1;
            }
            switch (alt82) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2251:1: (lv_documentation_0_0= ruleDocumentation )
                    {
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2251:1: (lv_documentation_0_0= ruleDocumentation )
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2252:3: lv_documentation_0_0= ruleDocumentation
                    {
                     
                    	        newCompositeNode(grammarAccess.getMdfEnumValueAccess().getDocumentationDocumentationParserRuleCall_0_0()); 
                    	    
                    pushFollow(FollowSets000.FOLLOW_ruleDocumentation_in_ruleMdfEnumValue4498);
                    lv_documentation_0_0=ruleDocumentation();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getMdfEnumValueRule());
                    	        }
                           		set(
                           			current, 
                           			"documentation",
                            		lv_documentation_0_0, 
                            		"Documentation");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }
                    break;

            }

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2268:3: ( (lv_name_1_0= ruleString_Value ) )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2269:1: (lv_name_1_0= ruleString_Value )
            {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2269:1: (lv_name_1_0= ruleString_Value )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2270:3: lv_name_1_0= ruleString_Value
            {
             
            	        newCompositeNode(grammarAccess.getMdfEnumValueAccess().getNameString_ValueParserRuleCall_1_0()); 
            	    
            pushFollow(FollowSets000.FOLLOW_ruleString_Value_in_ruleMdfEnumValue4520);
            lv_name_1_0=ruleString_Value();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getMdfEnumValueRule());
            	        }
                   		set(
                   			current, 
                   			"name",
                    		lv_name_1_0, 
                    		"String_Value");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_2=(Token)match(input,36,FollowSets000.FOLLOW_36_in_ruleMdfEnumValue4532); 

                	newLeafNode(otherlv_2, grammarAccess.getMdfEnumValueAccess().getEqualsSignKeyword_2());
                
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2290:1: ( (lv_value_3_0= ruleString_Value ) )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2291:1: (lv_value_3_0= ruleString_Value )
            {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2291:1: (lv_value_3_0= ruleString_Value )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2292:3: lv_value_3_0= ruleString_Value
            {
             
            	        newCompositeNode(grammarAccess.getMdfEnumValueAccess().getValueString_ValueParserRuleCall_3_0()); 
            	    
            pushFollow(FollowSets000.FOLLOW_ruleString_Value_in_ruleMdfEnumValue4553);
            lv_value_3_0=ruleString_Value();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getMdfEnumValueRule());
            	        }
                   		set(
                   			current, 
                   			"value",
                    		lv_value_3_0, 
                    		"String_Value");
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2308:2: ( (lv_annotations_4_0= ruleMdfAnnotation ) )*
            loop83:
            do {
                int alt83=2;
                int LA83_0 = input.LA(1);

                if ( (LA83_0==23) ) {
                    alt83=1;
                }


                switch (alt83) {
            	case 1 :
            	    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2309:1: (lv_annotations_4_0= ruleMdfAnnotation )
            	    {
            	    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2309:1: (lv_annotations_4_0= ruleMdfAnnotation )
            	    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2310:3: lv_annotations_4_0= ruleMdfAnnotation
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getMdfEnumValueAccess().getAnnotationsMdfAnnotationParserRuleCall_4_0()); 
            	    	    
            	    pushFollow(FollowSets000.FOLLOW_ruleMdfAnnotation_in_ruleMdfEnumValue4574);
            	    lv_annotations_4_0=ruleMdfAnnotation();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getMdfEnumValueRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"annotations",
            	            		lv_annotations_4_0, 
            	            		"MdfAnnotation");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop83;
                }
            } while (true);

            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2326:3: (otherlv_5= 'deprecationInfo' ruleMdfDeprecationInfo )?
            int alt84=2;
            int LA84_0 = input.LA(1);

            if ( (LA84_0==14) ) {
                alt84=1;
            }
            switch (alt84) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2326:5: otherlv_5= 'deprecationInfo' ruleMdfDeprecationInfo
                    {
                    otherlv_5=(Token)match(input,14,FollowSets000.FOLLOW_14_in_ruleMdfEnumValue4588); 

                        	newLeafNode(otherlv_5, grammarAccess.getMdfEnumValueAccess().getDeprecationInfoKeyword_5_0());
                        
                     
                            newCompositeNode(grammarAccess.getMdfEnumValueAccess().getMdfDeprecationInfoParserRuleCall_5_1()); 
                        
                    pushFollow(FollowSets000.FOLLOW_ruleMdfDeprecationInfo_in_ruleMdfEnumValue4604);
                    ruleMdfDeprecationInfo();

                    state._fsp--;

                     
                            afterParserOrEnumRuleCall();
                        

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
    // $ANTLR end "ruleMdfEnumValue"


    // $ANTLR start "entryRuleRef"
    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2346:1: entryRuleRef returns [String current=null] : iv_ruleRef= ruleRef EOF ;
    public final String entryRuleRef() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleRef = null;


        try {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2347:2: (iv_ruleRef= ruleRef EOF )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2348:2: iv_ruleRef= ruleRef EOF
            {
             newCompositeNode(grammarAccess.getRefRule()); 
            pushFollow(FollowSets000.FOLLOW_ruleRef_in_entryRuleRef4642);
            iv_ruleRef=ruleRef();

            state._fsp--;

             current =iv_ruleRef.getText(); 
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleRef4653); 

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
    // $ANTLR end "entryRuleRef"


    // $ANTLR start "ruleRef"
    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2355:1: ruleRef returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID (kw= ':' this_ID_2= RULE_ID )? ) ;
    public final AntlrDatatypeRuleToken ruleRef() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        Token kw=null;
        Token this_ID_2=null;

         enterRule(); 
            
        try {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2358:28: ( (this_ID_0= RULE_ID (kw= ':' this_ID_2= RULE_ID )? ) )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2359:1: (this_ID_0= RULE_ID (kw= ':' this_ID_2= RULE_ID )? )
            {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2359:1: (this_ID_0= RULE_ID (kw= ':' this_ID_2= RULE_ID )? )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2359:6: this_ID_0= RULE_ID (kw= ':' this_ID_2= RULE_ID )?
            {
            this_ID_0=(Token)match(input,RULE_ID,FollowSets000.FOLLOW_RULE_ID_in_ruleRef4693); 

            		current.merge(this_ID_0);
                
             
                newLeafNode(this_ID_0, grammarAccess.getRefAccess().getIDTerminalRuleCall_0()); 
                
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2366:1: (kw= ':' this_ID_2= RULE_ID )?
            int alt85=2;
            int LA85_0 = input.LA(1);

            if ( (LA85_0==24) ) {
                int LA85_1 = input.LA(2);

                if ( (LA85_1==RULE_ID) ) {
                    alt85=1;
                }
            }
            switch (alt85) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2367:2: kw= ':' this_ID_2= RULE_ID
                    {
                    kw=(Token)match(input,24,FollowSets000.FOLLOW_24_in_ruleRef4712); 

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getRefAccess().getColonKeyword_1_0()); 
                        
                    this_ID_2=(Token)match(input,RULE_ID,FollowSets000.FOLLOW_RULE_ID_in_ruleRef4727); 

                    		current.merge(this_ID_2);
                        
                     
                        newLeafNode(this_ID_2, grammarAccess.getRefAccess().getIDTerminalRuleCall_1_1()); 
                        

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
    // $ANTLR end "ruleRef"


    // $ANTLR start "entryRuleUniqueBoolean"
    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2387:1: entryRuleUniqueBoolean returns [String current=null] : iv_ruleUniqueBoolean= ruleUniqueBoolean EOF ;
    public final String entryRuleUniqueBoolean() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleUniqueBoolean = null;


        try {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2388:2: (iv_ruleUniqueBoolean= ruleUniqueBoolean EOF )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2389:2: iv_ruleUniqueBoolean= ruleUniqueBoolean EOF
            {
             newCompositeNode(grammarAccess.getUniqueBooleanRule()); 
            pushFollow(FollowSets000.FOLLOW_ruleUniqueBoolean_in_entryRuleUniqueBoolean4775);
            iv_ruleUniqueBoolean=ruleUniqueBoolean();

            state._fsp--;

             current =iv_ruleUniqueBoolean.getText(); 
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleUniqueBoolean4786); 

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
    // $ANTLR end "entryRuleUniqueBoolean"


    // $ANTLR start "ruleUniqueBoolean"
    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2396:1: ruleUniqueBoolean returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : kw= 'notUnique' ;
    public final AntlrDatatypeRuleToken ruleUniqueBoolean() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;

         enterRule(); 
            
        try {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2399:28: (kw= 'notUnique' )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2401:2: kw= 'notUnique'
            {
            kw=(Token)match(input,46,FollowSets000.FOLLOW_46_in_ruleUniqueBoolean4823); 

                    current.merge(kw);
                    newLeafNode(kw, grammarAccess.getUniqueBooleanAccess().getNotUniqueKeyword()); 
                

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
    // $ANTLR end "ruleUniqueBoolean"


    // $ANTLR start "entryRuleString_Value"
    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2414:1: entryRuleString_Value returns [String current=null] : iv_ruleString_Value= ruleString_Value EOF ;
    public final String entryRuleString_Value() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleString_Value = null;


        try {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2415:2: (iv_ruleString_Value= ruleString_Value EOF )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2416:2: iv_ruleString_Value= ruleString_Value EOF
            {
             newCompositeNode(grammarAccess.getString_ValueRule()); 
            pushFollow(FollowSets000.FOLLOW_ruleString_Value_in_entryRuleString_Value4863);
            iv_ruleString_Value=ruleString_Value();

            state._fsp--;

             current =iv_ruleString_Value.getText(); 
            match(input,EOF,FollowSets000.FOLLOW_EOF_in_entryRuleString_Value4874); 

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
    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2423:1: ruleString_Value returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_STRING_0= RULE_STRING | this_ID_1= RULE_ID | this_VALUE_2= RULE_VALUE ) ;
    public final AntlrDatatypeRuleToken ruleString_Value() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_STRING_0=null;
        Token this_ID_1=null;
        Token this_VALUE_2=null;

         enterRule(); 
            
        try {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2426:28: ( (this_STRING_0= RULE_STRING | this_ID_1= RULE_ID | this_VALUE_2= RULE_VALUE ) )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2427:1: (this_STRING_0= RULE_STRING | this_ID_1= RULE_ID | this_VALUE_2= RULE_VALUE )
            {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2427:1: (this_STRING_0= RULE_STRING | this_ID_1= RULE_ID | this_VALUE_2= RULE_VALUE )
            int alt86=3;
            switch ( input.LA(1) ) {
            case RULE_STRING:
                {
                alt86=1;
                }
                break;
            case RULE_ID:
                {
                alt86=2;
                }
                break;
            case RULE_VALUE:
                {
                alt86=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 86, 0, input);

                throw nvae;
            }

            switch (alt86) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2427:6: this_STRING_0= RULE_STRING
                    {
                    this_STRING_0=(Token)match(input,RULE_STRING,FollowSets000.FOLLOW_RULE_STRING_in_ruleString_Value4914); 

                    		current.merge(this_STRING_0);
                        
                     
                        newLeafNode(this_STRING_0, grammarAccess.getString_ValueAccess().getSTRINGTerminalRuleCall_0()); 
                        

                    }
                    break;
                case 2 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2435:10: this_ID_1= RULE_ID
                    {
                    this_ID_1=(Token)match(input,RULE_ID,FollowSets000.FOLLOW_RULE_ID_in_ruleString_Value4940); 

                    		current.merge(this_ID_1);
                        
                     
                        newLeafNode(this_ID_1, grammarAccess.getString_ValueAccess().getIDTerminalRuleCall_1()); 
                        

                    }
                    break;
                case 3 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2443:10: this_VALUE_2= RULE_VALUE
                    {
                    this_VALUE_2=(Token)match(input,RULE_VALUE,FollowSets000.FOLLOW_RULE_VALUE_in_ruleString_Value4966); 

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


    // $ANTLR start "ruleMdfMultiplicity"
    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2458:1: ruleMdfMultiplicity returns [Enumerator current=null] : ( (enumLiteral_0= '*' ) | (enumLiteral_1= 'ONE' ) ) ;
    public final Enumerator ruleMdfMultiplicity() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;

         enterRule(); 
        try {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2460:28: ( ( (enumLiteral_0= '*' ) | (enumLiteral_1= 'ONE' ) ) )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2461:1: ( (enumLiteral_0= '*' ) | (enumLiteral_1= 'ONE' ) )
            {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2461:1: ( (enumLiteral_0= '*' ) | (enumLiteral_1= 'ONE' ) )
            int alt87=2;
            int LA87_0 = input.LA(1);

            if ( (LA87_0==47) ) {
                alt87=1;
            }
            else if ( (LA87_0==48) ) {
                alt87=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 87, 0, input);

                throw nvae;
            }
            switch (alt87) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2461:2: (enumLiteral_0= '*' )
                    {
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2461:2: (enumLiteral_0= '*' )
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2461:4: enumLiteral_0= '*'
                    {
                    enumLiteral_0=(Token)match(input,47,FollowSets000.FOLLOW_47_in_ruleMdfMultiplicity5025); 

                            current = grammarAccess.getMdfMultiplicityAccess().getMANYEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_0, grammarAccess.getMdfMultiplicityAccess().getMANYEnumLiteralDeclaration_0()); 
                        

                    }


                    }
                    break;
                case 2 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2467:6: (enumLiteral_1= 'ONE' )
                    {
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2467:6: (enumLiteral_1= 'ONE' )
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2467:8: enumLiteral_1= 'ONE'
                    {
                    enumLiteral_1=(Token)match(input,48,FollowSets000.FOLLOW_48_in_ruleMdfMultiplicity5042); 

                            current = grammarAccess.getMdfMultiplicityAccess().getONEEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_1, grammarAccess.getMdfMultiplicityAccess().getONEEnumLiteralDeclaration_1()); 
                        

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
    // $ANTLR end "ruleMdfMultiplicity"


    // $ANTLR start "ruleMdfContainment"
    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2477:1: ruleMdfContainment returns [Enumerator current=null] : ( (enumLiteral_0= 'byReference' ) | (enumLiteral_1= 'byValue' ) ) ;
    public final Enumerator ruleMdfContainment() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;

         enterRule(); 
        try {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2479:28: ( ( (enumLiteral_0= 'byReference' ) | (enumLiteral_1= 'byValue' ) ) )
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2480:1: ( (enumLiteral_0= 'byReference' ) | (enumLiteral_1= 'byValue' ) )
            {
            // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2480:1: ( (enumLiteral_0= 'byReference' ) | (enumLiteral_1= 'byValue' ) )
            int alt88=2;
            int LA88_0 = input.LA(1);

            if ( (LA88_0==49) ) {
                alt88=1;
            }
            else if ( (LA88_0==50) ) {
                alt88=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 88, 0, input);

                throw nvae;
            }
            switch (alt88) {
                case 1 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2480:2: (enumLiteral_0= 'byReference' )
                    {
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2480:2: (enumLiteral_0= 'byReference' )
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2480:4: enumLiteral_0= 'byReference'
                    {
                    enumLiteral_0=(Token)match(input,49,FollowSets000.FOLLOW_49_in_ruleMdfContainment5087); 

                            current = grammarAccess.getMdfContainmentAccess().getBY_REFERENCEEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_0, grammarAccess.getMdfContainmentAccess().getBY_REFERENCEEnumLiteralDeclaration_0()); 
                        

                    }


                    }
                    break;
                case 2 :
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2486:6: (enumLiteral_1= 'byValue' )
                    {
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2486:6: (enumLiteral_1= 'byValue' )
                    // ../com.odcgroup.domain.dsl/src-gen/com/odcgroup/domain/parser/antlr/internal/InternalDomain.g:2486:8: enumLiteral_1= 'byValue'
                    {
                    enumLiteral_1=(Token)match(input,50,FollowSets000.FOLLOW_50_in_ruleMdfContainment5104); 

                            current = grammarAccess.getMdfContainmentAccess().getBY_VALUEEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_1, grammarAccess.getMdfContainmentAccess().getBY_VALUEEnumLiteralDeclaration_1()); 
                        

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
    // $ANTLR end "ruleMdfContainment"

    // Delegated rules


 

    
    private static class FollowSets000 {
        public static final BitSet FOLLOW_ruleMdfDomain_in_entryRuleMdfDomain75 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleMdfDomain85 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleDocumentation_in_ruleMdfDomain131 = new BitSet(new long[]{0x0000000000000800L});
        public static final BitSet FOLLOW_11_in_ruleMdfDomain144 = new BitSet(new long[]{0x0000000000000010L});
        public static final BitSet FOLLOW_RULE_ID_in_ruleMdfDomain161 = new BitSet(new long[]{0x0000000000801000L});
        public static final BitSet FOLLOW_ruleMdfAnnotation_in_ruleMdfDomain187 = new BitSet(new long[]{0x0000000000801000L});
        public static final BitSet FOLLOW_12_in_ruleMdfDomain200 = new BitSet(new long[]{0x00000000000000D0L});
        public static final BitSet FOLLOW_ruleString_Value_in_ruleMdfDomain221 = new BitSet(new long[]{0x0000000000002000L});
        public static final BitSet FOLLOW_13_in_ruleMdfDomain233 = new BitSet(new long[]{0x00000000000000D0L});
        public static final BitSet FOLLOW_ruleString_Value_in_ruleMdfDomain254 = new BitSet(new long[]{0x00000000001CC002L});
        public static final BitSet FOLLOW_14_in_ruleMdfDomain267 = new BitSet(new long[]{0x0000000000200000L});
        public static final BitSet FOLLOW_ruleMdfDeprecationInfo_in_ruleMdfDomain283 = new BitSet(new long[]{0x00000000001C8002L});
        public static final BitSet FOLLOW_15_in_ruleMdfDomain297 = new BitSet(new long[]{0x0000000000010000L});
        public static final BitSet FOLLOW_16_in_ruleMdfDomain309 = new BitSet(new long[]{0x00000000300000F0L});
        public static final BitSet FOLLOW_ruleMdfClass_in_ruleMdfDomain330 = new BitSet(new long[]{0x00000000300200F0L});
        public static final BitSet FOLLOW_17_in_ruleMdfDomain343 = new BitSet(new long[]{0x00000000001C0002L});
        public static final BitSet FOLLOW_18_in_ruleMdfDomain358 = new BitSet(new long[]{0x0000000000010000L});
        public static final BitSet FOLLOW_16_in_ruleMdfDomain370 = new BitSet(new long[]{0x00000000000000F0L});
        public static final BitSet FOLLOW_ruleMdfDataset_in_ruleMdfDomain391 = new BitSet(new long[]{0x00000000000200F0L});
        public static final BitSet FOLLOW_17_in_ruleMdfDomain404 = new BitSet(new long[]{0x0000000000180002L});
        public static final BitSet FOLLOW_19_in_ruleMdfDomain419 = new BitSet(new long[]{0x0000000000010000L});
        public static final BitSet FOLLOW_16_in_ruleMdfDomain431 = new BitSet(new long[]{0x00000000000000F0L});
        public static final BitSet FOLLOW_ruleMdfBusinessType_in_ruleMdfDomain452 = new BitSet(new long[]{0x00000000000200F0L});
        public static final BitSet FOLLOW_17_in_ruleMdfDomain465 = new BitSet(new long[]{0x0000000000100002L});
        public static final BitSet FOLLOW_20_in_ruleMdfDomain480 = new BitSet(new long[]{0x0000000000010000L});
        public static final BitSet FOLLOW_16_in_ruleMdfDomain492 = new BitSet(new long[]{0x00000000000000F0L});
        public static final BitSet FOLLOW_ruleMdfEnumeration_in_ruleMdfDomain513 = new BitSet(new long[]{0x00000000000200F0L});
        public static final BitSet FOLLOW_17_in_ruleMdfDomain526 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleMdfProperty_in_entryRuleMdfProperty566 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleMdfProperty576 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleMdfAttribute_in_ruleMdfProperty623 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleMdfAssociation_in_ruleMdfProperty650 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleMdfReverseAssociation_in_ruleMdfProperty677 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleMdfDatasetProperty_in_entryRuleMdfDatasetProperty712 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleMdfDatasetProperty722 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleMdfDatasetDerivedProperty_in_ruleMdfDatasetProperty769 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleMdfDatasetMappedProperty_in_ruleMdfDatasetProperty796 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleMdfDeprecationInfo_in_entryRuleMdfDeprecationInfo834 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleMdfDeprecationInfo845 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_21_in_ruleMdfDeprecationInfo883 = new BitSet(new long[]{0x00000000000000D0L});
        public static final BitSet FOLLOW_ruleString_Value_in_ruleMdfDeprecationInfo905 = new BitSet(new long[]{0x0000000000400000L});
        public static final BitSet FOLLOW_22_in_ruleMdfDeprecationInfo923 = new BitSet(new long[]{0x00000000000000D0L});
        public static final BitSet FOLLOW_ruleString_Value_in_ruleMdfDeprecationInfo945 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleMdfAnnotation_in_entryRuleMdfAnnotation990 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleMdfAnnotation1000 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_23_in_ruleMdfAnnotation1037 = new BitSet(new long[]{0x00000000000000D0L});
        public static final BitSet FOLLOW_ruleNamespace_in_ruleMdfAnnotation1058 = new BitSet(new long[]{0x0000000001000000L});
        public static final BitSet FOLLOW_24_in_ruleMdfAnnotation1070 = new BitSet(new long[]{0x0000000000000010L});
        public static final BitSet FOLLOW_RULE_ID_in_ruleMdfAnnotation1087 = new BitSet(new long[]{0x0000000002000002L});
        public static final BitSet FOLLOW_25_in_ruleMdfAnnotation1105 = new BitSet(new long[]{0x00000008000000D0L});
        public static final BitSet FOLLOW_ruleMdfAnnotationProperty_in_ruleMdfAnnotation1126 = new BitSet(new long[]{0x000000000C000000L});
        public static final BitSet FOLLOW_26_in_ruleMdfAnnotation1139 = new BitSet(new long[]{0x00000008000000D0L});
        public static final BitSet FOLLOW_ruleMdfAnnotationProperty_in_ruleMdfAnnotation1160 = new BitSet(new long[]{0x000000000C000000L});
        public static final BitSet FOLLOW_27_in_ruleMdfAnnotation1174 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleNamespace_in_entryRuleNamespace1213 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleNamespace1224 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleString_Value_in_ruleNamespace1270 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleDocumentation_in_entryRuleDocumentation1315 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleDocumentation1326 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_RULE_ML_DOC_in_ruleDocumentation1365 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleMdfClass_in_entryRuleMdfClass1409 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleMdfClass1419 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleDocumentation_in_ruleMdfClass1465 = new BitSet(new long[]{0x00000000300000D0L});
        public static final BitSet FOLLOW_28_in_ruleMdfClass1484 = new BitSet(new long[]{0x00000000200000D0L});
        public static final BitSet FOLLOW_29_in_ruleMdfClass1511 = new BitSet(new long[]{0x00000000000000D0L});
        public static final BitSet FOLLOW_ruleString_Value_in_ruleMdfClass1534 = new BitSet(new long[]{0x0000000040814000L});
        public static final BitSet FOLLOW_30_in_ruleMdfClass1547 = new BitSet(new long[]{0x0000000000000010L});
        public static final BitSet FOLLOW_ruleRef_in_ruleMdfClass1570 = new BitSet(new long[]{0x0000000000814000L});
        public static final BitSet FOLLOW_ruleMdfAnnotation_in_ruleMdfClass1593 = new BitSet(new long[]{0x0000000000814000L});
        public static final BitSet FOLLOW_14_in_ruleMdfClass1607 = new BitSet(new long[]{0x0000000000200000L});
        public static final BitSet FOLLOW_ruleMdfDeprecationInfo_in_ruleMdfClass1623 = new BitSet(new long[]{0x0000000000010000L});
        public static final BitSet FOLLOW_16_in_ruleMdfClass1637 = new BitSet(new long[]{0x00000000000200F0L});
        public static final BitSet FOLLOW_ruleMdfProperty_in_ruleMdfClass1658 = new BitSet(new long[]{0x00000000000200F0L});
        public static final BitSet FOLLOW_17_in_ruleMdfClass1671 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleMdfDataset_in_entryRuleMdfDataset1708 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleMdfDataset1718 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleDocumentation_in_ruleMdfDataset1764 = new BitSet(new long[]{0x00000000000000D0L});
        public static final BitSet FOLLOW_ruleString_Value_in_ruleMdfDataset1786 = new BitSet(new long[]{0x0000000380814002L});
        public static final BitSet FOLLOW_31_in_ruleMdfDataset1799 = new BitSet(new long[]{0x0000000000000010L});
        public static final BitSet FOLLOW_ruleRef_in_ruleMdfDataset1822 = new BitSet(new long[]{0x0000000300814002L});
        public static final BitSet FOLLOW_32_in_ruleMdfDataset1842 = new BitSet(new long[]{0x0000000200814002L});
        public static final BitSet FOLLOW_33_in_ruleMdfDataset1874 = new BitSet(new long[]{0x0000000000814002L});
        public static final BitSet FOLLOW_ruleMdfAnnotation_in_ruleMdfDataset1909 = new BitSet(new long[]{0x0000000000814002L});
        public static final BitSet FOLLOW_14_in_ruleMdfDataset1923 = new BitSet(new long[]{0x0000000000200000L});
        public static final BitSet FOLLOW_ruleMdfDeprecationInfo_in_ruleMdfDataset1939 = new BitSet(new long[]{0x0000000000010002L});
        public static final BitSet FOLLOW_16_in_ruleMdfDataset1953 = new BitSet(new long[]{0x00000000000200F0L});
        public static final BitSet FOLLOW_ruleMdfDatasetProperty_in_ruleMdfDataset1974 = new BitSet(new long[]{0x00000000000200F0L});
        public static final BitSet FOLLOW_17_in_ruleMdfDataset1987 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleMdfBusinessType_in_entryRuleMdfBusinessType2025 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleMdfBusinessType2035 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleDocumentation_in_ruleMdfBusinessType2081 = new BitSet(new long[]{0x00000000000000D0L});
        public static final BitSet FOLLOW_ruleString_Value_in_ruleMdfBusinessType2103 = new BitSet(new long[]{0x0000000001000000L});
        public static final BitSet FOLLOW_24_in_ruleMdfBusinessType2115 = new BitSet(new long[]{0x0000000000804012L});
        public static final BitSet FOLLOW_ruleRef_in_ruleMdfBusinessType2138 = new BitSet(new long[]{0x0000000000804002L});
        public static final BitSet FOLLOW_ruleMdfAnnotation_in_ruleMdfBusinessType2160 = new BitSet(new long[]{0x0000000000804002L});
        public static final BitSet FOLLOW_14_in_ruleMdfBusinessType2174 = new BitSet(new long[]{0x0000000000200000L});
        public static final BitSet FOLLOW_ruleMdfDeprecationInfo_in_ruleMdfBusinessType2190 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleMdfEnumeration_in_entryRuleMdfEnumeration2227 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleMdfEnumeration2237 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleDocumentation_in_ruleMdfEnumeration2283 = new BitSet(new long[]{0x00000000000000D0L});
        public static final BitSet FOLLOW_ruleString_Value_in_ruleMdfEnumeration2305 = new BitSet(new long[]{0x0000000001000000L});
        public static final BitSet FOLLOW_24_in_ruleMdfEnumeration2317 = new BitSet(new long[]{0x00000004008040F2L});
        public static final BitSet FOLLOW_ruleRef_in_ruleMdfEnumeration2340 = new BitSet(new long[]{0x00000004008040F2L});
        public static final BitSet FOLLOW_34_in_ruleMdfEnumeration2359 = new BitSet(new long[]{0x00000000008040F2L});
        public static final BitSet FOLLOW_ruleMdfAnnotation_in_ruleMdfEnumeration2394 = new BitSet(new long[]{0x00000000008040F2L});
        public static final BitSet FOLLOW_14_in_ruleMdfEnumeration2408 = new BitSet(new long[]{0x0000000000200000L});
        public static final BitSet FOLLOW_ruleMdfDeprecationInfo_in_ruleMdfEnumeration2424 = new BitSet(new long[]{0x00000000000000F2L});
        public static final BitSet FOLLOW_ruleMdfEnumValue_in_ruleMdfEnumeration2446 = new BitSet(new long[]{0x00000000000000F2L});
        public static final BitSet FOLLOW_ruleMdfPrimitive_Impl_in_entryRuleMdfPrimitive_Impl2483 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleMdfPrimitive_Impl2493 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_RULE_ID_in_ruleMdfPrimitive_Impl2544 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleMdfAnnotationProperty_in_entryRuleMdfAnnotationProperty2585 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleMdfAnnotationProperty2595 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_35_in_ruleMdfAnnotationProperty2638 = new BitSet(new long[]{0x00000000000000D0L});
        public static final BitSet FOLLOW_ruleString_Value_in_ruleMdfAnnotationProperty2673 = new BitSet(new long[]{0x0000001000000002L});
        public static final BitSet FOLLOW_36_in_ruleMdfAnnotationProperty2686 = new BitSet(new long[]{0x00000000000000D0L});
        public static final BitSet FOLLOW_ruleString_Value_in_ruleMdfAnnotationProperty2707 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleMdfAttribute_in_entryRuleMdfAttribute2745 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleMdfAttribute2755 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleDocumentation_in_ruleMdfAttribute2801 = new BitSet(new long[]{0x00000000000000D0L});
        public static final BitSet FOLLOW_ruleString_Value_in_ruleMdfAttribute2823 = new BitSet(new long[]{0x0000000001000000L});
        public static final BitSet FOLLOW_24_in_ruleMdfAttribute2835 = new BitSet(new long[]{0x000180E100804012L});
        public static final BitSet FOLLOW_ruleRef_in_ruleMdfAttribute2858 = new BitSet(new long[]{0x000180E100804002L});
        public static final BitSet FOLLOW_ruleMdfMultiplicity_in_ruleMdfAttribute2880 = new BitSet(new long[]{0x000000E100804002L});
        public static final BitSet FOLLOW_37_in_ruleMdfAttribute2899 = new BitSet(new long[]{0x000000C100804002L});
        public static final BitSet FOLLOW_38_in_ruleMdfAttribute2931 = new BitSet(new long[]{0x0000008100804002L});
        public static final BitSet FOLLOW_39_in_ruleMdfAttribute2963 = new BitSet(new long[]{0x0000000100804002L});
        public static final BitSet FOLLOW_32_in_ruleMdfAttribute2990 = new BitSet(new long[]{0x0000001000000000L});
        public static final BitSet FOLLOW_36_in_ruleMdfAttribute3002 = new BitSet(new long[]{0x00000000000000D0L});
        public static final BitSet FOLLOW_ruleString_Value_in_ruleMdfAttribute3023 = new BitSet(new long[]{0x0000000000804002L});
        public static final BitSet FOLLOW_ruleMdfAnnotation_in_ruleMdfAttribute3046 = new BitSet(new long[]{0x0000000000804002L});
        public static final BitSet FOLLOW_14_in_ruleMdfAttribute3060 = new BitSet(new long[]{0x0000000000200000L});
        public static final BitSet FOLLOW_ruleMdfDeprecationInfo_in_ruleMdfAttribute3076 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleMdfAssociation_in_entryRuleMdfAssociation3113 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleMdfAssociation3123 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleDocumentation_in_ruleMdfAssociation3169 = new BitSet(new long[]{0x00000000000000D0L});
        public static final BitSet FOLLOW_ruleString_Value_in_ruleMdfAssociation3191 = new BitSet(new long[]{0x0000010000000000L});
        public static final BitSet FOLLOW_40_in_ruleMdfAssociation3203 = new BitSet(new long[]{0x000782E000804012L});
        public static final BitSet FOLLOW_ruleMdfContainment_in_ruleMdfAssociation3224 = new BitSet(new long[]{0x000182E000804012L});
        public static final BitSet FOLLOW_ruleRef_in_ruleMdfAssociation3248 = new BitSet(new long[]{0x000182E000804002L});
        public static final BitSet FOLLOW_ruleMdfMultiplicity_in_ruleMdfAssociation3270 = new BitSet(new long[]{0x000002E000804002L});
        public static final BitSet FOLLOW_37_in_ruleMdfAssociation3289 = new BitSet(new long[]{0x000002C000804002L});
        public static final BitSet FOLLOW_38_in_ruleMdfAssociation3321 = new BitSet(new long[]{0x0000028000804002L});
        public static final BitSet FOLLOW_39_in_ruleMdfAssociation3353 = new BitSet(new long[]{0x0000020000804002L});
        public static final BitSet FOLLOW_41_in_ruleMdfAssociation3380 = new BitSet(new long[]{0x0000000000010000L});
        public static final BitSet FOLLOW_16_in_ruleMdfAssociation3392 = new BitSet(new long[]{0x00000000000000F0L});
        public static final BitSet FOLLOW_ruleMdfReverseAssociation_in_ruleMdfAssociation3413 = new BitSet(new long[]{0x0000000000020000L});
        public static final BitSet FOLLOW_17_in_ruleMdfAssociation3425 = new BitSet(new long[]{0x0000000000804002L});
        public static final BitSet FOLLOW_ruleMdfAnnotation_in_ruleMdfAssociation3448 = new BitSet(new long[]{0x0000000000804002L});
        public static final BitSet FOLLOW_14_in_ruleMdfAssociation3462 = new BitSet(new long[]{0x0000000000200000L});
        public static final BitSet FOLLOW_ruleMdfDeprecationInfo_in_ruleMdfAssociation3478 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleMdfReverseAssociation_in_entryRuleMdfReverseAssociation3515 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleMdfReverseAssociation3525 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleDocumentation_in_ruleMdfReverseAssociation3571 = new BitSet(new long[]{0x00000000000000D0L});
        public static final BitSet FOLLOW_ruleString_Value_in_ruleMdfReverseAssociation3593 = new BitSet(new long[]{0x0000040000000000L});
        public static final BitSet FOLLOW_42_in_ruleMdfReverseAssociation3605 = new BitSet(new long[]{0x0000000000000010L});
        public static final BitSet FOLLOW_ruleRef_in_ruleMdfReverseAssociation3628 = new BitSet(new long[]{0x0000000001000000L});
        public static final BitSet FOLLOW_24_in_ruleMdfReverseAssociation3640 = new BitSet(new long[]{0x0000000000000010L});
        public static final BitSet FOLLOW_RULE_ID_in_ruleMdfReverseAssociation3660 = new BitSet(new long[]{0x000180E000804002L});
        public static final BitSet FOLLOW_ruleMdfMultiplicity_in_ruleMdfReverseAssociation3681 = new BitSet(new long[]{0x000000E000804002L});
        public static final BitSet FOLLOW_37_in_ruleMdfReverseAssociation3700 = new BitSet(new long[]{0x000000C000804002L});
        public static final BitSet FOLLOW_38_in_ruleMdfReverseAssociation3732 = new BitSet(new long[]{0x0000008000804002L});
        public static final BitSet FOLLOW_39_in_ruleMdfReverseAssociation3764 = new BitSet(new long[]{0x0000000000804002L});
        public static final BitSet FOLLOW_ruleMdfAnnotation_in_ruleMdfReverseAssociation3799 = new BitSet(new long[]{0x0000000000804002L});
        public static final BitSet FOLLOW_14_in_ruleMdfReverseAssociation3813 = new BitSet(new long[]{0x0000000000200000L});
        public static final BitSet FOLLOW_ruleMdfDeprecationInfo_in_ruleMdfReverseAssociation3829 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleMdfDatasetDerivedProperty_in_entryRuleMdfDatasetDerivedProperty3866 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleMdfDatasetDerivedProperty3876 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleDocumentation_in_ruleMdfDatasetDerivedProperty3922 = new BitSet(new long[]{0x00000000000000D0L});
        public static final BitSet FOLLOW_ruleString_Value_in_ruleMdfDatasetDerivedProperty3944 = new BitSet(new long[]{0x0000000001000000L});
        public static final BitSet FOLLOW_24_in_ruleMdfDatasetDerivedProperty3956 = new BitSet(new long[]{0x0001800100804012L});
        public static final BitSet FOLLOW_ruleRef_in_ruleMdfDatasetDerivedProperty3979 = new BitSet(new long[]{0x0001800100804002L});
        public static final BitSet FOLLOW_32_in_ruleMdfDatasetDerivedProperty3993 = new BitSet(new long[]{0x0000001000000000L});
        public static final BitSet FOLLOW_36_in_ruleMdfDatasetDerivedProperty4005 = new BitSet(new long[]{0x00000000000000D0L});
        public static final BitSet FOLLOW_ruleString_Value_in_ruleMdfDatasetDerivedProperty4026 = new BitSet(new long[]{0x0001800000804002L});
        public static final BitSet FOLLOW_ruleMdfMultiplicity_in_ruleMdfDatasetDerivedProperty4049 = new BitSet(new long[]{0x0000000000804002L});
        public static final BitSet FOLLOW_ruleMdfAnnotation_in_ruleMdfDatasetDerivedProperty4071 = new BitSet(new long[]{0x0000000000804002L});
        public static final BitSet FOLLOW_14_in_ruleMdfDatasetDerivedProperty4085 = new BitSet(new long[]{0x0000000000200000L});
        public static final BitSet FOLLOW_ruleMdfDeprecationInfo_in_ruleMdfDatasetDerivedProperty4101 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleMdfDatasetMappedProperty_in_entryRuleMdfDatasetMappedProperty4138 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleMdfDatasetMappedProperty4148 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleDocumentation_in_ruleMdfDatasetMappedProperty4194 = new BitSet(new long[]{0x00000000000000D0L});
        public static final BitSet FOLLOW_ruleString_Value_in_ruleMdfDatasetMappedProperty4216 = new BitSet(new long[]{0x0000490000000000L});
        public static final BitSet FOLLOW_ruleUniqueBoolean_in_ruleMdfDatasetMappedProperty4237 = new BitSet(new long[]{0x0000090000000000L});
        public static final BitSet FOLLOW_43_in_ruleMdfDatasetMappedProperty4256 = new BitSet(new long[]{0x0000010000000000L});
        public static final BitSet FOLLOW_40_in_ruleMdfDatasetMappedProperty4282 = new BitSet(new long[]{0x00001000008040D2L});
        public static final BitSet FOLLOW_ruleString_Value_in_ruleMdfDatasetMappedProperty4303 = new BitSet(new long[]{0x0000100000804002L});
        public static final BitSet FOLLOW_44_in_ruleMdfDatasetMappedProperty4317 = new BitSet(new long[]{0x0000000000000010L});
        public static final BitSet FOLLOW_ruleRef_in_ruleMdfDatasetMappedProperty4340 = new BitSet(new long[]{0x0000200000000000L});
        public static final BitSet FOLLOW_45_in_ruleMdfDatasetMappedProperty4352 = new BitSet(new long[]{0x0000000000804002L});
        public static final BitSet FOLLOW_ruleMdfAnnotation_in_ruleMdfDatasetMappedProperty4375 = new BitSet(new long[]{0x0000000000804002L});
        public static final BitSet FOLLOW_14_in_ruleMdfDatasetMappedProperty4389 = new BitSet(new long[]{0x0000000000200000L});
        public static final BitSet FOLLOW_ruleMdfDeprecationInfo_in_ruleMdfDatasetMappedProperty4405 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleMdfEnumValue_in_entryRuleMdfEnumValue4442 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleMdfEnumValue4452 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleDocumentation_in_ruleMdfEnumValue4498 = new BitSet(new long[]{0x00000000000000D0L});
        public static final BitSet FOLLOW_ruleString_Value_in_ruleMdfEnumValue4520 = new BitSet(new long[]{0x0000001000000000L});
        public static final BitSet FOLLOW_36_in_ruleMdfEnumValue4532 = new BitSet(new long[]{0x00000000000000D0L});
        public static final BitSet FOLLOW_ruleString_Value_in_ruleMdfEnumValue4553 = new BitSet(new long[]{0x0000000000804002L});
        public static final BitSet FOLLOW_ruleMdfAnnotation_in_ruleMdfEnumValue4574 = new BitSet(new long[]{0x0000000000804002L});
        public static final BitSet FOLLOW_14_in_ruleMdfEnumValue4588 = new BitSet(new long[]{0x0000000000200000L});
        public static final BitSet FOLLOW_ruleMdfDeprecationInfo_in_ruleMdfEnumValue4604 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleRef_in_entryRuleRef4642 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleRef4653 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_RULE_ID_in_ruleRef4693 = new BitSet(new long[]{0x0000000001000002L});
        public static final BitSet FOLLOW_24_in_ruleRef4712 = new BitSet(new long[]{0x0000000000000010L});
        public static final BitSet FOLLOW_RULE_ID_in_ruleRef4727 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleUniqueBoolean_in_entryRuleUniqueBoolean4775 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleUniqueBoolean4786 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_46_in_ruleUniqueBoolean4823 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_ruleString_Value_in_entryRuleString_Value4863 = new BitSet(new long[]{0x0000000000000000L});
        public static final BitSet FOLLOW_EOF_in_entryRuleString_Value4874 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_RULE_STRING_in_ruleString_Value4914 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_RULE_ID_in_ruleString_Value4940 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_RULE_VALUE_in_ruleString_Value4966 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_47_in_ruleMdfMultiplicity5025 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_48_in_ruleMdfMultiplicity5042 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_49_in_ruleMdfContainment5087 = new BitSet(new long[]{0x0000000000000002L});
        public static final BitSet FOLLOW_50_in_ruleMdfContainment5104 = new BitSet(new long[]{0x0000000000000002L});
    }


}
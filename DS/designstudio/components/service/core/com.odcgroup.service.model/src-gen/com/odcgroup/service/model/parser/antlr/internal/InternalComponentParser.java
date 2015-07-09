package com.odcgroup.service.model.parser.antlr.internal;

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
import com.odcgroup.service.model.services.ComponentGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalComponentParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ID", "RULE_INT", "RULE_ML_DOC", "RULE_STRING", "RULE_VALUE", "RULE_SL_COMMENT", "RULE_WS", "RULE_URI", "'component'", "'metamodelVersion'", "'table'", "'{'", "'t24:'", "'fields:'", "'}'", "'constant'", "'('", "')'", "'='", "'property'", "': string'", "'jBC:'", "'->'", "'method'", "','", "'interface'", "'@'", "'t24'", "':'", "'$'", "'integrationFlowSupportable'", "'unsafe'", "'idempotent'", "'readonly'", "'readwrite'", "'module'", "'private'", "'public'", "'external'", "'IN'", "'OUT'", "'INOUT'", "'[0..1]'", "'[0..*]'", "'[1..1]'", "'[1..*]'"
    };
    public static final int RULE_VALUE=8;
    public static final int RULE_ID=4;
    public static final int T__29=29;
    public static final int T__28=28;
    public static final int T__27=27;
    public static final int RULE_ML_DOC=6;
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
    public static final int T__14=14;
    public static final int T__13=13;
    public static final int RULE_INT=5;
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
    public static final int RULE_URI=11;
    public static final int RULE_SL_COMMENT=9;
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int RULE_STRING=7;
    public static final int T__32=32;
    public static final int T__33=33;
    public static final int T__34=34;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int T__37=37;
    public static final int T__38=38;
    public static final int T__39=39;
    public static final int RULE_WS=10;

    // delegates
    // delegators


        public InternalComponentParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalComponentParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);

        }


    public String[] getTokenNames() { return InternalComponentParser.tokenNames; }
    public String getGrammarFileName() { return "../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g"; }



	private ComponentGrammarAccess grammarAccess;

        public InternalComponentParser(TokenStream input, ComponentGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }

        @Override
        protected String getFirstRuleName() {
		return "Component";
	}

	@Override
	protected ComponentGrammarAccess getGrammarAccess() {
		return grammarAccess;
	}



    // $ANTLR start "entryRuleComponent"
    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:68:1: entryRuleComponent returns [EObject current=null] : iv_ruleComponent= ruleComponent EOF ;
    public final EObject entryRuleComponent() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleComponent = null;


        try {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:69:2: (iv_ruleComponent= ruleComponent EOF )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:70:2: iv_ruleComponent= ruleComponent EOF
            {
             newCompositeNode(grammarAccess.getComponentRule());
            pushFollow(FOLLOW_ruleComponent_in_entryRuleComponent75);
            iv_ruleComponent=ruleComponent();

            state._fsp--;

             current =iv_ruleComponent;
            match(input,EOF,FOLLOW_EOF_in_entryRuleComponent85);

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
    // $ANTLR end "entryRuleComponent"


    // $ANTLR start "ruleComponent"
    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:77:1: ruleComponent returns [EObject current=null] : ( ( (lv_documentation_0_0= ruleDocumentation ) )? otherlv_1= 'component' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= 'metamodelVersion' ( (lv_metamodelVersion_4_0= ruleString_Value ) ) ( (lv_content_5_0= ruleContent ) )* ) ;
    public final EObject ruleComponent() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_name_2_0=null;
        Token otherlv_3=null;
        AntlrDatatypeRuleToken lv_documentation_0_0 = null;

        AntlrDatatypeRuleToken lv_metamodelVersion_4_0 = null;

        EObject lv_content_5_0 = null;


         enterRule();

        try {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:80:28: ( ( ( (lv_documentation_0_0= ruleDocumentation ) )? otherlv_1= 'component' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= 'metamodelVersion' ( (lv_metamodelVersion_4_0= ruleString_Value ) ) ( (lv_content_5_0= ruleContent ) )* ) )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:81:1: ( ( (lv_documentation_0_0= ruleDocumentation ) )? otherlv_1= 'component' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= 'metamodelVersion' ( (lv_metamodelVersion_4_0= ruleString_Value ) ) ( (lv_content_5_0= ruleContent ) )* )
            {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:81:1: ( ( (lv_documentation_0_0= ruleDocumentation ) )? otherlv_1= 'component' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= 'metamodelVersion' ( (lv_metamodelVersion_4_0= ruleString_Value ) ) ( (lv_content_5_0= ruleContent ) )* )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:81:2: ( (lv_documentation_0_0= ruleDocumentation ) )? otherlv_1= 'component' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= 'metamodelVersion' ( (lv_metamodelVersion_4_0= ruleString_Value ) ) ( (lv_content_5_0= ruleContent ) )*
            {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:81:2: ( (lv_documentation_0_0= ruleDocumentation ) )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==RULE_ML_DOC) ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:82:1: (lv_documentation_0_0= ruleDocumentation )
                    {
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:82:1: (lv_documentation_0_0= ruleDocumentation )
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:83:3: lv_documentation_0_0= ruleDocumentation
                    {

			        newCompositeNode(grammarAccess.getComponentAccess().getDocumentationDocumentationParserRuleCall_0_0());

                    pushFollow(FOLLOW_ruleDocumentation_in_ruleComponent131);
                    lv_documentation_0_0=ruleDocumentation();

                    state._fsp--;


			        if (current==null) {
			            current = createModelElementForParent(grammarAccess.getComponentRule());
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

            otherlv_1=(Token)match(input,12,FOLLOW_12_in_ruleComponent144);

			newLeafNode(otherlv_1, grammarAccess.getComponentAccess().getComponentKeyword_1());

            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:103:1: ( (lv_name_2_0= RULE_ID ) )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:104:1: (lv_name_2_0= RULE_ID )
            {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:104:1: (lv_name_2_0= RULE_ID )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:105:3: lv_name_2_0= RULE_ID
            {
            lv_name_2_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleComponent161);

				newLeafNode(lv_name_2_0, grammarAccess.getComponentAccess().getNameIDTerminalRuleCall_2_0());


		        if (current==null) {
		            current = createModelElement(grammarAccess.getComponentRule());
		        }
				setWithLastConsumed(
					current,
					"name",
				lv_name_2_0,
				"ID");


            }


            }

            otherlv_3=(Token)match(input,13,FOLLOW_13_in_ruleComponent178);

			newLeafNode(otherlv_3, grammarAccess.getComponentAccess().getMetamodelVersionKeyword_3());

            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:125:1: ( (lv_metamodelVersion_4_0= ruleString_Value ) )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:126:1: (lv_metamodelVersion_4_0= ruleString_Value )
            {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:126:1: (lv_metamodelVersion_4_0= ruleString_Value )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:127:3: lv_metamodelVersion_4_0= ruleString_Value
            {

		        newCompositeNode(grammarAccess.getComponentAccess().getMetamodelVersionString_ValueParserRuleCall_4_0());

            pushFollow(FOLLOW_ruleString_Value_in_ruleComponent199);
            lv_metamodelVersion_4_0=ruleString_Value();

            state._fsp--;


		        if (current==null) {
		            current = createModelElementForParent(grammarAccess.getComponentRule());
		        }
				set(
					current,
					"metamodelVersion",
				lv_metamodelVersion_4_0,
				"String_Value");
		        afterParserOrEnumRuleCall();


            }


            }

            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:143:2: ( (lv_content_5_0= ruleContent ) )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==RULE_ML_DOC||(LA2_0>=39 && LA2_0<=42)) ) {
                    alt2=1;
                }


                switch (alt2) {
		case 1 :
		    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:144:1: (lv_content_5_0= ruleContent )
		    {
		    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:144:1: (lv_content_5_0= ruleContent )
		    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:145:3: lv_content_5_0= ruleContent
		    {

			        newCompositeNode(grammarAccess.getComponentAccess().getContentContentParserRuleCall_5_0());

		    pushFollow(FOLLOW_ruleContent_in_ruleComponent220);
		    lv_content_5_0=ruleContent();

		    state._fsp--;


			        if (current==null) {
			            current = createModelElementForParent(grammarAccess.getComponentRule());
			        }
					add(
						current,
						"content",
					lv_content_5_0,
					"Content");
			        afterParserOrEnumRuleCall();


		    }


		    }
		    break;

		default :
		    break loop2;
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
    // $ANTLR end "ruleComponent"


    // $ANTLR start "entryRuleContent"
    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:169:1: entryRuleContent returns [EObject current=null] : iv_ruleContent= ruleContent EOF ;
    public final EObject entryRuleContent() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleContent = null;


        try {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:170:2: (iv_ruleContent= ruleContent EOF )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:171:2: iv_ruleContent= ruleContent EOF
            {
             newCompositeNode(grammarAccess.getContentRule());
            pushFollow(FOLLOW_ruleContent_in_entryRuleContent257);
            iv_ruleContent=ruleContent();

            state._fsp--;

             current =iv_ruleContent;
            match(input,EOF,FOLLOW_EOF_in_entryRuleContent267);

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
    // $ANTLR end "entryRuleContent"


    // $ANTLR start "ruleContent"
    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:178:1: ruleContent returns [EObject current=null] : ( ( (lv_interface_0_0= ruleInterface ) ) | ( (lv_method_1_0= ruleMethod ) ) | ( (lv_property_2_0= ruleProperty ) ) | ( (lv_constant_3_0= ruleConstant ) ) | ( (lv_table_4_0= ruleTable ) ) ) ;
    public final EObject ruleContent() throws RecognitionException {
        EObject current = null;

        EObject lv_interface_0_0 = null;

        EObject lv_method_1_0 = null;

        EObject lv_property_2_0 = null;

        EObject lv_constant_3_0 = null;

        EObject lv_table_4_0 = null;


         enterRule();

        try {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:181:28: ( ( ( (lv_interface_0_0= ruleInterface ) ) | ( (lv_method_1_0= ruleMethod ) ) | ( (lv_property_2_0= ruleProperty ) ) | ( (lv_constant_3_0= ruleConstant ) ) | ( (lv_table_4_0= ruleTable ) ) ) )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:182:1: ( ( (lv_interface_0_0= ruleInterface ) ) | ( (lv_method_1_0= ruleMethod ) ) | ( (lv_property_2_0= ruleProperty ) ) | ( (lv_constant_3_0= ruleConstant ) ) | ( (lv_table_4_0= ruleTable ) ) )
            {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:182:1: ( ( (lv_interface_0_0= ruleInterface ) ) | ( (lv_method_1_0= ruleMethod ) ) | ( (lv_property_2_0= ruleProperty ) ) | ( (lv_constant_3_0= ruleConstant ) ) | ( (lv_table_4_0= ruleTable ) ) )
            int alt3=5;
            alt3 = dfa3.predict(input);
            switch (alt3) {
                case 1 :
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:182:2: ( (lv_interface_0_0= ruleInterface ) )
                    {
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:182:2: ( (lv_interface_0_0= ruleInterface ) )
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:183:1: (lv_interface_0_0= ruleInterface )
                    {
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:183:1: (lv_interface_0_0= ruleInterface )
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:184:3: lv_interface_0_0= ruleInterface
                    {

			        newCompositeNode(grammarAccess.getContentAccess().getInterfaceInterfaceParserRuleCall_0_0());

                    pushFollow(FOLLOW_ruleInterface_in_ruleContent313);
                    lv_interface_0_0=ruleInterface();

                    state._fsp--;


			        if (current==null) {
			            current = createModelElementForParent(grammarAccess.getContentRule());
			        }
					set(
						current,
						"interface",
					lv_interface_0_0,
					"Interface");
			        afterParserOrEnumRuleCall();


                    }


                    }


                    }
                    break;
                case 2 :
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:201:6: ( (lv_method_1_0= ruleMethod ) )
                    {
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:201:6: ( (lv_method_1_0= ruleMethod ) )
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:202:1: (lv_method_1_0= ruleMethod )
                    {
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:202:1: (lv_method_1_0= ruleMethod )
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:203:3: lv_method_1_0= ruleMethod
                    {

			        newCompositeNode(grammarAccess.getContentAccess().getMethodMethodParserRuleCall_1_0());

                    pushFollow(FOLLOW_ruleMethod_in_ruleContent340);
                    lv_method_1_0=ruleMethod();

                    state._fsp--;


			        if (current==null) {
			            current = createModelElementForParent(grammarAccess.getContentRule());
			        }
					add(
						current,
						"method",
					lv_method_1_0,
					"Method");
			        afterParserOrEnumRuleCall();


                    }


                    }


                    }
                    break;
                case 3 :
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:220:6: ( (lv_property_2_0= ruleProperty ) )
                    {
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:220:6: ( (lv_property_2_0= ruleProperty ) )
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:221:1: (lv_property_2_0= ruleProperty )
                    {
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:221:1: (lv_property_2_0= ruleProperty )
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:222:3: lv_property_2_0= ruleProperty
                    {

			        newCompositeNode(grammarAccess.getContentAccess().getPropertyPropertyParserRuleCall_2_0());

                    pushFollow(FOLLOW_ruleProperty_in_ruleContent367);
                    lv_property_2_0=ruleProperty();

                    state._fsp--;


			        if (current==null) {
			            current = createModelElementForParent(grammarAccess.getContentRule());
			        }
					add(
						current,
						"property",
					lv_property_2_0,
					"Property");
			        afterParserOrEnumRuleCall();


                    }


                    }


                    }
                    break;
                case 4 :
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:239:6: ( (lv_constant_3_0= ruleConstant ) )
                    {
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:239:6: ( (lv_constant_3_0= ruleConstant ) )
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:240:1: (lv_constant_3_0= ruleConstant )
                    {
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:240:1: (lv_constant_3_0= ruleConstant )
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:241:3: lv_constant_3_0= ruleConstant
                    {

			        newCompositeNode(grammarAccess.getContentAccess().getConstantConstantParserRuleCall_3_0());

                    pushFollow(FOLLOW_ruleConstant_in_ruleContent394);
                    lv_constant_3_0=ruleConstant();

                    state._fsp--;


			        if (current==null) {
			            current = createModelElementForParent(grammarAccess.getContentRule());
			        }
					add(
						current,
						"constant",
					lv_constant_3_0,
					"Constant");
			        afterParserOrEnumRuleCall();


                    }


                    }


                    }
                    break;
                case 5 :
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:258:6: ( (lv_table_4_0= ruleTable ) )
                    {
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:258:6: ( (lv_table_4_0= ruleTable ) )
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:259:1: (lv_table_4_0= ruleTable )
                    {
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:259:1: (lv_table_4_0= ruleTable )
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:260:3: lv_table_4_0= ruleTable
                    {

			        newCompositeNode(grammarAccess.getContentAccess().getTableTableParserRuleCall_4_0());

                    pushFollow(FOLLOW_ruleTable_in_ruleContent421);
                    lv_table_4_0=ruleTable();

                    state._fsp--;


			        if (current==null) {
			            current = createModelElementForParent(grammarAccess.getContentRule());
			        }
					add(
						current,
						"table",
					lv_table_4_0,
					"Table");
			        afterParserOrEnumRuleCall();


                    }


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
    // $ANTLR end "ruleContent"


    // $ANTLR start "entryRuleTable"
    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:284:1: entryRuleTable returns [EObject current=null] : iv_ruleTable= ruleTable EOF ;
    public final EObject entryRuleTable() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTable = null;


        try {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:285:2: (iv_ruleTable= ruleTable EOF )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:286:2: iv_ruleTable= ruleTable EOF
            {
             newCompositeNode(grammarAccess.getTableRule());
            pushFollow(FOLLOW_ruleTable_in_entryRuleTable457);
            iv_ruleTable=ruleTable();

            state._fsp--;

             current =iv_ruleTable;
            match(input,EOF,FOLLOW_EOF_in_entryRuleTable467);

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
    // $ANTLR end "entryRuleTable"


    // $ANTLR start "ruleTable"
    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:293:1: ruleTable returns [EObject current=null] : ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_accessSpecifier_1_0= ruleAccessSpecifier ) ) otherlv_2= 'table' ( (lv_tableName_3_0= RULE_ID ) ) otherlv_4= '{' otherlv_5= 't24:' ( (lv_type_6_0= RULE_ID ) ) (otherlv_7= 'fields:' otherlv_8= '{' ( (lv_attribute_9_0= ruleAttribute ) )* otherlv_10= '}' )? otherlv_11= '}' ) ;
    public final EObject ruleTable() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token lv_tableName_3_0=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token lv_type_6_0=null;
        Token otherlv_7=null;
        Token otherlv_8=null;
        Token otherlv_10=null;
        Token otherlv_11=null;
        AntlrDatatypeRuleToken lv_documentation_0_0 = null;

        Enumerator lv_accessSpecifier_1_0 = null;

        EObject lv_attribute_9_0 = null;


         enterRule();

        try {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:296:28: ( ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_accessSpecifier_1_0= ruleAccessSpecifier ) ) otherlv_2= 'table' ( (lv_tableName_3_0= RULE_ID ) ) otherlv_4= '{' otherlv_5= 't24:' ( (lv_type_6_0= RULE_ID ) ) (otherlv_7= 'fields:' otherlv_8= '{' ( (lv_attribute_9_0= ruleAttribute ) )* otherlv_10= '}' )? otherlv_11= '}' ) )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:297:1: ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_accessSpecifier_1_0= ruleAccessSpecifier ) ) otherlv_2= 'table' ( (lv_tableName_3_0= RULE_ID ) ) otherlv_4= '{' otherlv_5= 't24:' ( (lv_type_6_0= RULE_ID ) ) (otherlv_7= 'fields:' otherlv_8= '{' ( (lv_attribute_9_0= ruleAttribute ) )* otherlv_10= '}' )? otherlv_11= '}' )
            {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:297:1: ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_accessSpecifier_1_0= ruleAccessSpecifier ) ) otherlv_2= 'table' ( (lv_tableName_3_0= RULE_ID ) ) otherlv_4= '{' otherlv_5= 't24:' ( (lv_type_6_0= RULE_ID ) ) (otherlv_7= 'fields:' otherlv_8= '{' ( (lv_attribute_9_0= ruleAttribute ) )* otherlv_10= '}' )? otherlv_11= '}' )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:297:2: ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_accessSpecifier_1_0= ruleAccessSpecifier ) ) otherlv_2= 'table' ( (lv_tableName_3_0= RULE_ID ) ) otherlv_4= '{' otherlv_5= 't24:' ( (lv_type_6_0= RULE_ID ) ) (otherlv_7= 'fields:' otherlv_8= '{' ( (lv_attribute_9_0= ruleAttribute ) )* otherlv_10= '}' )? otherlv_11= '}'
            {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:297:2: ( (lv_documentation_0_0= ruleDocumentation ) )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==RULE_ML_DOC) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:298:1: (lv_documentation_0_0= ruleDocumentation )
                    {
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:298:1: (lv_documentation_0_0= ruleDocumentation )
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:299:3: lv_documentation_0_0= ruleDocumentation
                    {

			        newCompositeNode(grammarAccess.getTableAccess().getDocumentationDocumentationParserRuleCall_0_0());

                    pushFollow(FOLLOW_ruleDocumentation_in_ruleTable513);
                    lv_documentation_0_0=ruleDocumentation();

                    state._fsp--;


			        if (current==null) {
			            current = createModelElementForParent(grammarAccess.getTableRule());
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

            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:315:3: ( (lv_accessSpecifier_1_0= ruleAccessSpecifier ) )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:316:1: (lv_accessSpecifier_1_0= ruleAccessSpecifier )
            {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:316:1: (lv_accessSpecifier_1_0= ruleAccessSpecifier )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:317:3: lv_accessSpecifier_1_0= ruleAccessSpecifier
            {

		        newCompositeNode(grammarAccess.getTableAccess().getAccessSpecifierAccessSpecifierEnumRuleCall_1_0());

            pushFollow(FOLLOW_ruleAccessSpecifier_in_ruleTable535);
            lv_accessSpecifier_1_0=ruleAccessSpecifier();

            state._fsp--;


		        if (current==null) {
		            current = createModelElementForParent(grammarAccess.getTableRule());
		        }
				set(
					current,
					"accessSpecifier",
				lv_accessSpecifier_1_0,
				"AccessSpecifier");
		        afterParserOrEnumRuleCall();


            }


            }

            otherlv_2=(Token)match(input,14,FOLLOW_14_in_ruleTable547);

			newLeafNode(otherlv_2, grammarAccess.getTableAccess().getTableKeyword_2());

            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:337:1: ( (lv_tableName_3_0= RULE_ID ) )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:338:1: (lv_tableName_3_0= RULE_ID )
            {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:338:1: (lv_tableName_3_0= RULE_ID )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:339:3: lv_tableName_3_0= RULE_ID
            {
            lv_tableName_3_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleTable564);

				newLeafNode(lv_tableName_3_0, grammarAccess.getTableAccess().getTableNameIDTerminalRuleCall_3_0());


		        if (current==null) {
		            current = createModelElement(grammarAccess.getTableRule());
		        }
				setWithLastConsumed(
					current,
					"tableName",
				lv_tableName_3_0,
				"ID");


            }


            }

            otherlv_4=(Token)match(input,15,FOLLOW_15_in_ruleTable581);

			newLeafNode(otherlv_4, grammarAccess.getTableAccess().getLeftCurlyBracketKeyword_4());

            otherlv_5=(Token)match(input,16,FOLLOW_16_in_ruleTable593);

			newLeafNode(otherlv_5, grammarAccess.getTableAccess().getT24Keyword_5());

            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:363:1: ( (lv_type_6_0= RULE_ID ) )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:364:1: (lv_type_6_0= RULE_ID )
            {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:364:1: (lv_type_6_0= RULE_ID )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:365:3: lv_type_6_0= RULE_ID
            {
            lv_type_6_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleTable610);

				newLeafNode(lv_type_6_0, grammarAccess.getTableAccess().getTypeIDTerminalRuleCall_6_0());


		        if (current==null) {
		            current = createModelElement(grammarAccess.getTableRule());
		        }
				setWithLastConsumed(
					current,
					"type",
				lv_type_6_0,
				"ID");


            }


            }

            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:381:2: (otherlv_7= 'fields:' otherlv_8= '{' ( (lv_attribute_9_0= ruleAttribute ) )* otherlv_10= '}' )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==17) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:381:4: otherlv_7= 'fields:' otherlv_8= '{' ( (lv_attribute_9_0= ruleAttribute ) )* otherlv_10= '}'
                    {
                    otherlv_7=(Token)match(input,17,FOLLOW_17_in_ruleTable628);

				newLeafNode(otherlv_7, grammarAccess.getTableAccess().getFieldsKeyword_7_0());

                    otherlv_8=(Token)match(input,15,FOLLOW_15_in_ruleTable640);

				newLeafNode(otherlv_8, grammarAccess.getTableAccess().getLeftCurlyBracketKeyword_7_1());

                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:389:1: ( (lv_attribute_9_0= ruleAttribute ) )*
                    loop5:
                    do {
                        int alt5=2;
                        int LA5_0 = input.LA(1);

                        if ( (LA5_0==RULE_ID) ) {
                            alt5=1;
                        }


                        switch (alt5) {
			case 1 :
			    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:390:1: (lv_attribute_9_0= ruleAttribute )
			    {
			    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:390:1: (lv_attribute_9_0= ruleAttribute )
			    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:391:3: lv_attribute_9_0= ruleAttribute
			    {

				        newCompositeNode(grammarAccess.getTableAccess().getAttributeAttributeParserRuleCall_7_2_0());

			    pushFollow(FOLLOW_ruleAttribute_in_ruleTable661);
			    lv_attribute_9_0=ruleAttribute();

			    state._fsp--;


				        if (current==null) {
				            current = createModelElementForParent(grammarAccess.getTableRule());
				        }
						add(
							current,
							"attribute",
						lv_attribute_9_0,
						"Attribute");
				        afterParserOrEnumRuleCall();


			    }


			    }
			    break;

			default :
			    break loop5;
                        }
                    } while (true);

                    otherlv_10=(Token)match(input,18,FOLLOW_18_in_ruleTable674);

				newLeafNode(otherlv_10, grammarAccess.getTableAccess().getRightCurlyBracketKeyword_7_3());


                    }
                    break;

            }

            otherlv_11=(Token)match(input,18,FOLLOW_18_in_ruleTable688);

			newLeafNode(otherlv_11, grammarAccess.getTableAccess().getRightCurlyBracketKeyword_8());


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
    // $ANTLR end "ruleTable"


    // $ANTLR start "entryRuleConstant"
    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:423:1: entryRuleConstant returns [EObject current=null] : iv_ruleConstant= ruleConstant EOF ;
    public final EObject entryRuleConstant() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleConstant = null;


        try {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:424:2: (iv_ruleConstant= ruleConstant EOF )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:425:2: iv_ruleConstant= ruleConstant EOF
            {
             newCompositeNode(grammarAccess.getConstantRule());
            pushFollow(FOLLOW_ruleConstant_in_entryRuleConstant724);
            iv_ruleConstant=ruleConstant();

            state._fsp--;

             current =iv_ruleConstant;
            match(input,EOF,FOLLOW_EOF_in_entryRuleConstant734);

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
    // $ANTLR end "entryRuleConstant"


    // $ANTLR start "ruleConstant"
    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:432:1: ruleConstant returns [EObject current=null] : ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_accessSpecifier_1_0= ruleAccessSpecifier ) ) otherlv_2= 'constant' ( (lv_constantName_3_0= RULE_ID ) ) (otherlv_4= '(' ( (lv_jBCName_5_0= RULE_ID ) ) otherlv_6= ')' )? otherlv_7= '=' ( (lv_value_8_0= RULE_INT ) ) ) ;
    public final EObject ruleConstant() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token lv_constantName_3_0=null;
        Token otherlv_4=null;
        Token lv_jBCName_5_0=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        Token lv_value_8_0=null;
        AntlrDatatypeRuleToken lv_documentation_0_0 = null;

        Enumerator lv_accessSpecifier_1_0 = null;


         enterRule();

        try {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:435:28: ( ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_accessSpecifier_1_0= ruleAccessSpecifier ) ) otherlv_2= 'constant' ( (lv_constantName_3_0= RULE_ID ) ) (otherlv_4= '(' ( (lv_jBCName_5_0= RULE_ID ) ) otherlv_6= ')' )? otherlv_7= '=' ( (lv_value_8_0= RULE_INT ) ) ) )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:436:1: ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_accessSpecifier_1_0= ruleAccessSpecifier ) ) otherlv_2= 'constant' ( (lv_constantName_3_0= RULE_ID ) ) (otherlv_4= '(' ( (lv_jBCName_5_0= RULE_ID ) ) otherlv_6= ')' )? otherlv_7= '=' ( (lv_value_8_0= RULE_INT ) ) )
            {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:436:1: ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_accessSpecifier_1_0= ruleAccessSpecifier ) ) otherlv_2= 'constant' ( (lv_constantName_3_0= RULE_ID ) ) (otherlv_4= '(' ( (lv_jBCName_5_0= RULE_ID ) ) otherlv_6= ')' )? otherlv_7= '=' ( (lv_value_8_0= RULE_INT ) ) )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:436:2: ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_accessSpecifier_1_0= ruleAccessSpecifier ) ) otherlv_2= 'constant' ( (lv_constantName_3_0= RULE_ID ) ) (otherlv_4= '(' ( (lv_jBCName_5_0= RULE_ID ) ) otherlv_6= ')' )? otherlv_7= '=' ( (lv_value_8_0= RULE_INT ) )
            {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:436:2: ( (lv_documentation_0_0= ruleDocumentation ) )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==RULE_ML_DOC) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:437:1: (lv_documentation_0_0= ruleDocumentation )
                    {
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:437:1: (lv_documentation_0_0= ruleDocumentation )
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:438:3: lv_documentation_0_0= ruleDocumentation
                    {

			        newCompositeNode(grammarAccess.getConstantAccess().getDocumentationDocumentationParserRuleCall_0_0());

                    pushFollow(FOLLOW_ruleDocumentation_in_ruleConstant780);
                    lv_documentation_0_0=ruleDocumentation();

                    state._fsp--;


			        if (current==null) {
			            current = createModelElementForParent(grammarAccess.getConstantRule());
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

            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:454:3: ( (lv_accessSpecifier_1_0= ruleAccessSpecifier ) )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:455:1: (lv_accessSpecifier_1_0= ruleAccessSpecifier )
            {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:455:1: (lv_accessSpecifier_1_0= ruleAccessSpecifier )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:456:3: lv_accessSpecifier_1_0= ruleAccessSpecifier
            {

		        newCompositeNode(grammarAccess.getConstantAccess().getAccessSpecifierAccessSpecifierEnumRuleCall_1_0());

            pushFollow(FOLLOW_ruleAccessSpecifier_in_ruleConstant802);
            lv_accessSpecifier_1_0=ruleAccessSpecifier();

            state._fsp--;


		        if (current==null) {
		            current = createModelElementForParent(grammarAccess.getConstantRule());
		        }
				set(
					current,
					"accessSpecifier",
				lv_accessSpecifier_1_0,
				"AccessSpecifier");
		        afterParserOrEnumRuleCall();


            }


            }

            otherlv_2=(Token)match(input,19,FOLLOW_19_in_ruleConstant814);

			newLeafNode(otherlv_2, grammarAccess.getConstantAccess().getConstantKeyword_2());

            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:476:1: ( (lv_constantName_3_0= RULE_ID ) )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:477:1: (lv_constantName_3_0= RULE_ID )
            {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:477:1: (lv_constantName_3_0= RULE_ID )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:478:3: lv_constantName_3_0= RULE_ID
            {
            lv_constantName_3_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleConstant831);

				newLeafNode(lv_constantName_3_0, grammarAccess.getConstantAccess().getConstantNameIDTerminalRuleCall_3_0());


		        if (current==null) {
		            current = createModelElement(grammarAccess.getConstantRule());
		        }
				setWithLastConsumed(
					current,
					"constantName",
				lv_constantName_3_0,
				"ID");


            }


            }

            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:494:2: (otherlv_4= '(' ( (lv_jBCName_5_0= RULE_ID ) ) otherlv_6= ')' )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==20) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:494:4: otherlv_4= '(' ( (lv_jBCName_5_0= RULE_ID ) ) otherlv_6= ')'
                    {
                    otherlv_4=(Token)match(input,20,FOLLOW_20_in_ruleConstant849);

				newLeafNode(otherlv_4, grammarAccess.getConstantAccess().getLeftParenthesisKeyword_4_0());

                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:498:1: ( (lv_jBCName_5_0= RULE_ID ) )
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:499:1: (lv_jBCName_5_0= RULE_ID )
                    {
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:499:1: (lv_jBCName_5_0= RULE_ID )
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:500:3: lv_jBCName_5_0= RULE_ID
                    {
                    lv_jBCName_5_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleConstant866);

					newLeafNode(lv_jBCName_5_0, grammarAccess.getConstantAccess().getJBCNameIDTerminalRuleCall_4_1_0());


			        if (current==null) {
			            current = createModelElement(grammarAccess.getConstantRule());
			        }
					setWithLastConsumed(
						current,
						"jBCName",
					lv_jBCName_5_0,
					"ID");


                    }


                    }

                    otherlv_6=(Token)match(input,21,FOLLOW_21_in_ruleConstant883);

				newLeafNode(otherlv_6, grammarAccess.getConstantAccess().getRightParenthesisKeyword_4_2());


                    }
                    break;

            }

            otherlv_7=(Token)match(input,22,FOLLOW_22_in_ruleConstant897);

			newLeafNode(otherlv_7, grammarAccess.getConstantAccess().getEqualsSignKeyword_5());

            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:524:1: ( (lv_value_8_0= RULE_INT ) )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:525:1: (lv_value_8_0= RULE_INT )
            {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:525:1: (lv_value_8_0= RULE_INT )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:526:3: lv_value_8_0= RULE_INT
            {
            lv_value_8_0=(Token)match(input,RULE_INT,FOLLOW_RULE_INT_in_ruleConstant914);

				newLeafNode(lv_value_8_0, grammarAccess.getConstantAccess().getValueINTTerminalRuleCall_6_0());


		        if (current==null) {
		            current = createModelElement(grammarAccess.getConstantRule());
		        }
				setWithLastConsumed(
					current,
					"value",
				lv_value_8_0,
				"INT");


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
    // $ANTLR end "ruleConstant"


    // $ANTLR start "entryRuleProperty"
    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:550:1: entryRuleProperty returns [EObject current=null] : iv_ruleProperty= ruleProperty EOF ;
    public final EObject entryRuleProperty() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleProperty = null;


        try {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:551:2: (iv_ruleProperty= ruleProperty EOF )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:552:2: iv_ruleProperty= ruleProperty EOF
            {
             newCompositeNode(grammarAccess.getPropertyRule());
            pushFollow(FOLLOW_ruleProperty_in_entryRuleProperty955);
            iv_ruleProperty=ruleProperty();

            state._fsp--;

             current =iv_ruleProperty;
            match(input,EOF,FOLLOW_EOF_in_entryRuleProperty965);

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
    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:559:1: ruleProperty returns [EObject current=null] : ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_accessSpecifier_1_0= ruleAccessSpecifier ) ) otherlv_2= 'property' ( (lv_readOnly_3_0= ruleReadWrite ) ) ( (lv_propertyName_4_0= RULE_ID ) ) otherlv_5= ': string' otherlv_6= '{' otherlv_7= 'jBC:' ( (lv_type1_8_0= ruleJBC_ID ) ) otherlv_9= '->' ( (lv_type2_10_0= ruleJBC_ID ) ) ( ( (lv_array_11_0= '(' ) ) ( (lv_value_12_0= RULE_INT ) )? otherlv_13= ')' )? otherlv_14= '}' ) ;
    public final EObject ruleProperty() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token lv_propertyName_4_0=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        Token otherlv_9=null;
        Token lv_array_11_0=null;
        Token lv_value_12_0=null;
        Token otherlv_13=null;
        Token otherlv_14=null;
        AntlrDatatypeRuleToken lv_documentation_0_0 = null;

        Enumerator lv_accessSpecifier_1_0 = null;

        Enumerator lv_readOnly_3_0 = null;

        AntlrDatatypeRuleToken lv_type1_8_0 = null;

        AntlrDatatypeRuleToken lv_type2_10_0 = null;


         enterRule();

        try {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:562:28: ( ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_accessSpecifier_1_0= ruleAccessSpecifier ) ) otherlv_2= 'property' ( (lv_readOnly_3_0= ruleReadWrite ) ) ( (lv_propertyName_4_0= RULE_ID ) ) otherlv_5= ': string' otherlv_6= '{' otherlv_7= 'jBC:' ( (lv_type1_8_0= ruleJBC_ID ) ) otherlv_9= '->' ( (lv_type2_10_0= ruleJBC_ID ) ) ( ( (lv_array_11_0= '(' ) ) ( (lv_value_12_0= RULE_INT ) )? otherlv_13= ')' )? otherlv_14= '}' ) )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:563:1: ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_accessSpecifier_1_0= ruleAccessSpecifier ) ) otherlv_2= 'property' ( (lv_readOnly_3_0= ruleReadWrite ) ) ( (lv_propertyName_4_0= RULE_ID ) ) otherlv_5= ': string' otherlv_6= '{' otherlv_7= 'jBC:' ( (lv_type1_8_0= ruleJBC_ID ) ) otherlv_9= '->' ( (lv_type2_10_0= ruleJBC_ID ) ) ( ( (lv_array_11_0= '(' ) ) ( (lv_value_12_0= RULE_INT ) )? otherlv_13= ')' )? otherlv_14= '}' )
            {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:563:1: ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_accessSpecifier_1_0= ruleAccessSpecifier ) ) otherlv_2= 'property' ( (lv_readOnly_3_0= ruleReadWrite ) ) ( (lv_propertyName_4_0= RULE_ID ) ) otherlv_5= ': string' otherlv_6= '{' otherlv_7= 'jBC:' ( (lv_type1_8_0= ruleJBC_ID ) ) otherlv_9= '->' ( (lv_type2_10_0= ruleJBC_ID ) ) ( ( (lv_array_11_0= '(' ) ) ( (lv_value_12_0= RULE_INT ) )? otherlv_13= ')' )? otherlv_14= '}' )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:563:2: ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_accessSpecifier_1_0= ruleAccessSpecifier ) ) otherlv_2= 'property' ( (lv_readOnly_3_0= ruleReadWrite ) ) ( (lv_propertyName_4_0= RULE_ID ) ) otherlv_5= ': string' otherlv_6= '{' otherlv_7= 'jBC:' ( (lv_type1_8_0= ruleJBC_ID ) ) otherlv_9= '->' ( (lv_type2_10_0= ruleJBC_ID ) ) ( ( (lv_array_11_0= '(' ) ) ( (lv_value_12_0= RULE_INT ) )? otherlv_13= ')' )? otherlv_14= '}'
            {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:563:2: ( (lv_documentation_0_0= ruleDocumentation ) )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==RULE_ML_DOC) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:564:1: (lv_documentation_0_0= ruleDocumentation )
                    {
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:564:1: (lv_documentation_0_0= ruleDocumentation )
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:565:3: lv_documentation_0_0= ruleDocumentation
                    {

			        newCompositeNode(grammarAccess.getPropertyAccess().getDocumentationDocumentationParserRuleCall_0_0());

                    pushFollow(FOLLOW_ruleDocumentation_in_ruleProperty1011);
                    lv_documentation_0_0=ruleDocumentation();

                    state._fsp--;


			        if (current==null) {
			            current = createModelElementForParent(grammarAccess.getPropertyRule());
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

            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:581:3: ( (lv_accessSpecifier_1_0= ruleAccessSpecifier ) )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:582:1: (lv_accessSpecifier_1_0= ruleAccessSpecifier )
            {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:582:1: (lv_accessSpecifier_1_0= ruleAccessSpecifier )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:583:3: lv_accessSpecifier_1_0= ruleAccessSpecifier
            {

		        newCompositeNode(grammarAccess.getPropertyAccess().getAccessSpecifierAccessSpecifierEnumRuleCall_1_0());

            pushFollow(FOLLOW_ruleAccessSpecifier_in_ruleProperty1033);
            lv_accessSpecifier_1_0=ruleAccessSpecifier();

            state._fsp--;


		        if (current==null) {
		            current = createModelElementForParent(grammarAccess.getPropertyRule());
		        }
				set(
					current,
					"accessSpecifier",
				lv_accessSpecifier_1_0,
				"AccessSpecifier");
		        afterParserOrEnumRuleCall();


            }


            }

            otherlv_2=(Token)match(input,23,FOLLOW_23_in_ruleProperty1045);

			newLeafNode(otherlv_2, grammarAccess.getPropertyAccess().getPropertyKeyword_2());

            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:603:1: ( (lv_readOnly_3_0= ruleReadWrite ) )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:604:1: (lv_readOnly_3_0= ruleReadWrite )
            {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:604:1: (lv_readOnly_3_0= ruleReadWrite )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:605:3: lv_readOnly_3_0= ruleReadWrite
            {

		        newCompositeNode(grammarAccess.getPropertyAccess().getReadOnlyReadWriteEnumRuleCall_3_0());

            pushFollow(FOLLOW_ruleReadWrite_in_ruleProperty1066);
            lv_readOnly_3_0=ruleReadWrite();

            state._fsp--;


		        if (current==null) {
		            current = createModelElementForParent(grammarAccess.getPropertyRule());
		        }
				set(
					current,
					"readOnly",
				lv_readOnly_3_0,
				"ReadWrite");
		        afterParserOrEnumRuleCall();


            }


            }

            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:621:2: ( (lv_propertyName_4_0= RULE_ID ) )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:622:1: (lv_propertyName_4_0= RULE_ID )
            {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:622:1: (lv_propertyName_4_0= RULE_ID )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:623:3: lv_propertyName_4_0= RULE_ID
            {
            lv_propertyName_4_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleProperty1083);

				newLeafNode(lv_propertyName_4_0, grammarAccess.getPropertyAccess().getPropertyNameIDTerminalRuleCall_4_0());


		        if (current==null) {
		            current = createModelElement(grammarAccess.getPropertyRule());
		        }
				setWithLastConsumed(
					current,
					"propertyName",
				lv_propertyName_4_0,
				"ID");


            }


            }

            otherlv_5=(Token)match(input,24,FOLLOW_24_in_ruleProperty1100);

			newLeafNode(otherlv_5, grammarAccess.getPropertyAccess().getStringKeyword_5());

            otherlv_6=(Token)match(input,15,FOLLOW_15_in_ruleProperty1112);

			newLeafNode(otherlv_6, grammarAccess.getPropertyAccess().getLeftCurlyBracketKeyword_6());

            otherlv_7=(Token)match(input,25,FOLLOW_25_in_ruleProperty1124);

			newLeafNode(otherlv_7, grammarAccess.getPropertyAccess().getJBCKeyword_7());

            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:651:1: ( (lv_type1_8_0= ruleJBC_ID ) )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:652:1: (lv_type1_8_0= ruleJBC_ID )
            {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:652:1: (lv_type1_8_0= ruleJBC_ID )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:653:3: lv_type1_8_0= ruleJBC_ID
            {

		        newCompositeNode(grammarAccess.getPropertyAccess().getType1JBC_IDParserRuleCall_8_0());

            pushFollow(FOLLOW_ruleJBC_ID_in_ruleProperty1145);
            lv_type1_8_0=ruleJBC_ID();

            state._fsp--;


		        if (current==null) {
		            current = createModelElementForParent(grammarAccess.getPropertyRule());
		        }
				set(
					current,
					"type1",
				lv_type1_8_0,
				"JBC_ID");
		        afterParserOrEnumRuleCall();


            }


            }

            otherlv_9=(Token)match(input,26,FOLLOW_26_in_ruleProperty1157);

			newLeafNode(otherlv_9, grammarAccess.getPropertyAccess().getHyphenMinusGreaterThanSignKeyword_9());

            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:673:1: ( (lv_type2_10_0= ruleJBC_ID ) )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:674:1: (lv_type2_10_0= ruleJBC_ID )
            {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:674:1: (lv_type2_10_0= ruleJBC_ID )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:675:3: lv_type2_10_0= ruleJBC_ID
            {

		        newCompositeNode(grammarAccess.getPropertyAccess().getType2JBC_IDParserRuleCall_10_0());

            pushFollow(FOLLOW_ruleJBC_ID_in_ruleProperty1178);
            lv_type2_10_0=ruleJBC_ID();

            state._fsp--;


		        if (current==null) {
		            current = createModelElementForParent(grammarAccess.getPropertyRule());
		        }
				set(
					current,
					"type2",
				lv_type2_10_0,
				"JBC_ID");
		        afterParserOrEnumRuleCall();


            }


            }

            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:691:2: ( ( (lv_array_11_0= '(' ) ) ( (lv_value_12_0= RULE_INT ) )? otherlv_13= ')' )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==20) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:691:3: ( (lv_array_11_0= '(' ) ) ( (lv_value_12_0= RULE_INT ) )? otherlv_13= ')'
                    {
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:691:3: ( (lv_array_11_0= '(' ) )
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:692:1: (lv_array_11_0= '(' )
                    {
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:692:1: (lv_array_11_0= '(' )
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:693:3: lv_array_11_0= '('
                    {
                    lv_array_11_0=(Token)match(input,20,FOLLOW_20_in_ruleProperty1197);

                            newLeafNode(lv_array_11_0, grammarAccess.getPropertyAccess().getArrayLeftParenthesisKeyword_11_0_0());


			        if (current==null) {
			            current = createModelElement(grammarAccess.getPropertyRule());
			        }
					setWithLastConsumed(current, "array", true, "(");


                    }


                    }

                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:706:2: ( (lv_value_12_0= RULE_INT ) )?
                    int alt10=2;
                    int LA10_0 = input.LA(1);

                    if ( (LA10_0==RULE_INT) ) {
                        alt10=1;
                    }
                    switch (alt10) {
                        case 1 :
                            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:707:1: (lv_value_12_0= RULE_INT )
                            {
                            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:707:1: (lv_value_12_0= RULE_INT )
                            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:708:3: lv_value_12_0= RULE_INT
                            {
                            lv_value_12_0=(Token)match(input,RULE_INT,FOLLOW_RULE_INT_in_ruleProperty1227);

						newLeafNode(lv_value_12_0, grammarAccess.getPropertyAccess().getValueINTTerminalRuleCall_11_1_0());


				        if (current==null) {
				            current = createModelElement(grammarAccess.getPropertyRule());
				        }
						setWithLastConsumed(
							current,
							"value",
						lv_value_12_0,
						"INT");


                            }


                            }
                            break;

                    }

                    otherlv_13=(Token)match(input,21,FOLLOW_21_in_ruleProperty1245);

				newLeafNode(otherlv_13, grammarAccess.getPropertyAccess().getRightParenthesisKeyword_11_2());


                    }
                    break;

            }

            otherlv_14=(Token)match(input,18,FOLLOW_18_in_ruleProperty1259);

			newLeafNode(otherlv_14, grammarAccess.getPropertyAccess().getRightCurlyBracketKeyword_12());


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


    // $ANTLR start "entryRuleMethod"
    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:740:1: entryRuleMethod returns [EObject current=null] : iv_ruleMethod= ruleMethod EOF ;
    public final EObject entryRuleMethod() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMethod = null;


        try {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:741:2: (iv_ruleMethod= ruleMethod EOF )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:742:2: iv_ruleMethod= ruleMethod EOF
            {
             newCompositeNode(grammarAccess.getMethodRule());
            pushFollow(FOLLOW_ruleMethod_in_entryRuleMethod1295);
            iv_ruleMethod=ruleMethod();

            state._fsp--;

             current =iv_ruleMethod;
            match(input,EOF,FOLLOW_EOF_in_entryRuleMethod1305);

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
    // $ANTLR end "entryRuleMethod"


    // $ANTLR start "ruleMethod"
    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:749:1: ruleMethod returns [EObject current=null] : ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_accessSpecifier_1_0= ruleMethodAccessSpecifier ) ) otherlv_2= 'method' ( (lv_name_3_0= RULE_ID ) ) ( (lv_annotations_4_0= ruleMethodAnnotation ) )* otherlv_5= '(' ( ( (lv_arguments_6_0= ruleArgument ) ) (otherlv_7= ',' ( (lv_arguments_8_0= ruleArgument ) ) )* )? otherlv_9= ')' (otherlv_10= '{' otherlv_11= 'jBC:' ( (lv_type_12_0= ruleJBC_ID ) ) otherlv_13= '}' )? ) ;
    public final EObject ruleMethod() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token lv_name_3_0=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        Token otherlv_9=null;
        Token otherlv_10=null;
        Token otherlv_11=null;
        Token otherlv_13=null;
        AntlrDatatypeRuleToken lv_documentation_0_0 = null;

        Enumerator lv_accessSpecifier_1_0 = null;

        EObject lv_annotations_4_0 = null;

        EObject lv_arguments_6_0 = null;

        EObject lv_arguments_8_0 = null;

        AntlrDatatypeRuleToken lv_type_12_0 = null;


         enterRule();

        try {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:752:28: ( ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_accessSpecifier_1_0= ruleMethodAccessSpecifier ) ) otherlv_2= 'method' ( (lv_name_3_0= RULE_ID ) ) ( (lv_annotations_4_0= ruleMethodAnnotation ) )* otherlv_5= '(' ( ( (lv_arguments_6_0= ruleArgument ) ) (otherlv_7= ',' ( (lv_arguments_8_0= ruleArgument ) ) )* )? otherlv_9= ')' (otherlv_10= '{' otherlv_11= 'jBC:' ( (lv_type_12_0= ruleJBC_ID ) ) otherlv_13= '}' )? ) )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:753:1: ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_accessSpecifier_1_0= ruleMethodAccessSpecifier ) ) otherlv_2= 'method' ( (lv_name_3_0= RULE_ID ) ) ( (lv_annotations_4_0= ruleMethodAnnotation ) )* otherlv_5= '(' ( ( (lv_arguments_6_0= ruleArgument ) ) (otherlv_7= ',' ( (lv_arguments_8_0= ruleArgument ) ) )* )? otherlv_9= ')' (otherlv_10= '{' otherlv_11= 'jBC:' ( (lv_type_12_0= ruleJBC_ID ) ) otherlv_13= '}' )? )
            {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:753:1: ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_accessSpecifier_1_0= ruleMethodAccessSpecifier ) ) otherlv_2= 'method' ( (lv_name_3_0= RULE_ID ) ) ( (lv_annotations_4_0= ruleMethodAnnotation ) )* otherlv_5= '(' ( ( (lv_arguments_6_0= ruleArgument ) ) (otherlv_7= ',' ( (lv_arguments_8_0= ruleArgument ) ) )* )? otherlv_9= ')' (otherlv_10= '{' otherlv_11= 'jBC:' ( (lv_type_12_0= ruleJBC_ID ) ) otherlv_13= '}' )? )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:753:2: ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_accessSpecifier_1_0= ruleMethodAccessSpecifier ) ) otherlv_2= 'method' ( (lv_name_3_0= RULE_ID ) ) ( (lv_annotations_4_0= ruleMethodAnnotation ) )* otherlv_5= '(' ( ( (lv_arguments_6_0= ruleArgument ) ) (otherlv_7= ',' ( (lv_arguments_8_0= ruleArgument ) ) )* )? otherlv_9= ')' (otherlv_10= '{' otherlv_11= 'jBC:' ( (lv_type_12_0= ruleJBC_ID ) ) otherlv_13= '}' )?
            {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:753:2: ( (lv_documentation_0_0= ruleDocumentation ) )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==RULE_ML_DOC) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:754:1: (lv_documentation_0_0= ruleDocumentation )
                    {
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:754:1: (lv_documentation_0_0= ruleDocumentation )
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:755:3: lv_documentation_0_0= ruleDocumentation
                    {

			        newCompositeNode(grammarAccess.getMethodAccess().getDocumentationDocumentationParserRuleCall_0_0());

                    pushFollow(FOLLOW_ruleDocumentation_in_ruleMethod1351);
                    lv_documentation_0_0=ruleDocumentation();

                    state._fsp--;


			        if (current==null) {
			            current = createModelElementForParent(grammarAccess.getMethodRule());
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

            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:771:3: ( (lv_accessSpecifier_1_0= ruleMethodAccessSpecifier ) )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:772:1: (lv_accessSpecifier_1_0= ruleMethodAccessSpecifier )
            {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:772:1: (lv_accessSpecifier_1_0= ruleMethodAccessSpecifier )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:773:3: lv_accessSpecifier_1_0= ruleMethodAccessSpecifier
            {

		        newCompositeNode(grammarAccess.getMethodAccess().getAccessSpecifierMethodAccessSpecifierEnumRuleCall_1_0());

            pushFollow(FOLLOW_ruleMethodAccessSpecifier_in_ruleMethod1373);
            lv_accessSpecifier_1_0=ruleMethodAccessSpecifier();

            state._fsp--;


		        if (current==null) {
		            current = createModelElementForParent(grammarAccess.getMethodRule());
		        }
				set(
					current,
					"accessSpecifier",
				lv_accessSpecifier_1_0,
				"MethodAccessSpecifier");
		        afterParserOrEnumRuleCall();


            }


            }

            otherlv_2=(Token)match(input,27,FOLLOW_27_in_ruleMethod1385);

			newLeafNode(otherlv_2, grammarAccess.getMethodAccess().getMethodKeyword_2());

            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:793:1: ( (lv_name_3_0= RULE_ID ) )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:794:1: (lv_name_3_0= RULE_ID )
            {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:794:1: (lv_name_3_0= RULE_ID )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:795:3: lv_name_3_0= RULE_ID
            {
            lv_name_3_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleMethod1402);

				newLeafNode(lv_name_3_0, grammarAccess.getMethodAccess().getNameIDTerminalRuleCall_3_0());


		        if (current==null) {
		            current = createModelElement(grammarAccess.getMethodRule());
		        }
				setWithLastConsumed(
					current,
					"name",
				lv_name_3_0,
				"ID");


            }


            }

            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:811:2: ( (lv_annotations_4_0= ruleMethodAnnotation ) )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==30) ) {
                    alt13=1;
                }


                switch (alt13) {
		case 1 :
		    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:812:1: (lv_annotations_4_0= ruleMethodAnnotation )
		    {
		    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:812:1: (lv_annotations_4_0= ruleMethodAnnotation )
		    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:813:3: lv_annotations_4_0= ruleMethodAnnotation
		    {

			        newCompositeNode(grammarAccess.getMethodAccess().getAnnotationsMethodAnnotationParserRuleCall_4_0());

		    pushFollow(FOLLOW_ruleMethodAnnotation_in_ruleMethod1428);
		    lv_annotations_4_0=ruleMethodAnnotation();

		    state._fsp--;


			        if (current==null) {
			            current = createModelElementForParent(grammarAccess.getMethodRule());
			        }
					add(
						current,
						"annotations",
					lv_annotations_4_0,
					"MethodAnnotation");
			        afterParserOrEnumRuleCall();


		    }


		    }
		    break;

		default :
		    break loop13;
                }
            } while (true);

            otherlv_5=(Token)match(input,20,FOLLOW_20_in_ruleMethod1441);

			newLeafNode(otherlv_5, grammarAccess.getMethodAccess().getLeftParenthesisKeyword_5());

            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:833:1: ( ( (lv_arguments_6_0= ruleArgument ) ) (otherlv_7= ',' ( (lv_arguments_8_0= ruleArgument ) ) )* )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==RULE_ID||LA15_0==RULE_ML_DOC||(LA15_0>=43 && LA15_0<=45)) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:833:2: ( (lv_arguments_6_0= ruleArgument ) ) (otherlv_7= ',' ( (lv_arguments_8_0= ruleArgument ) ) )*
                    {
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:833:2: ( (lv_arguments_6_0= ruleArgument ) )
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:834:1: (lv_arguments_6_0= ruleArgument )
                    {
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:834:1: (lv_arguments_6_0= ruleArgument )
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:835:3: lv_arguments_6_0= ruleArgument
                    {

			        newCompositeNode(grammarAccess.getMethodAccess().getArgumentsArgumentParserRuleCall_6_0_0());

                    pushFollow(FOLLOW_ruleArgument_in_ruleMethod1463);
                    lv_arguments_6_0=ruleArgument();

                    state._fsp--;


			        if (current==null) {
			            current = createModelElementForParent(grammarAccess.getMethodRule());
			        }
					add(
						current,
						"arguments",
					lv_arguments_6_0,
					"Argument");
			        afterParserOrEnumRuleCall();


                    }


                    }

                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:851:2: (otherlv_7= ',' ( (lv_arguments_8_0= ruleArgument ) ) )*
                    loop14:
                    do {
                        int alt14=2;
                        int LA14_0 = input.LA(1);

                        if ( (LA14_0==28) ) {
                            alt14=1;
                        }


                        switch (alt14) {
			case 1 :
			    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:851:4: otherlv_7= ',' ( (lv_arguments_8_0= ruleArgument ) )
			    {
			    otherlv_7=(Token)match(input,28,FOLLOW_28_in_ruleMethod1476);

					newLeafNode(otherlv_7, grammarAccess.getMethodAccess().getCommaKeyword_6_1_0());

			    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:855:1: ( (lv_arguments_8_0= ruleArgument ) )
			    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:856:1: (lv_arguments_8_0= ruleArgument )
			    {
			    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:856:1: (lv_arguments_8_0= ruleArgument )
			    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:857:3: lv_arguments_8_0= ruleArgument
			    {

				        newCompositeNode(grammarAccess.getMethodAccess().getArgumentsArgumentParserRuleCall_6_1_1_0());

			    pushFollow(FOLLOW_ruleArgument_in_ruleMethod1497);
			    lv_arguments_8_0=ruleArgument();

			    state._fsp--;


				        if (current==null) {
				            current = createModelElementForParent(grammarAccess.getMethodRule());
				        }
						add(
							current,
							"arguments",
						lv_arguments_8_0,
						"Argument");
				        afterParserOrEnumRuleCall();


			    }


			    }


			    }
			    break;

			default :
			    break loop14;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_9=(Token)match(input,21,FOLLOW_21_in_ruleMethod1513);

			newLeafNode(otherlv_9, grammarAccess.getMethodAccess().getRightParenthesisKeyword_7());

            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:877:1: (otherlv_10= '{' otherlv_11= 'jBC:' ( (lv_type_12_0= ruleJBC_ID ) ) otherlv_13= '}' )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==15) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:877:3: otherlv_10= '{' otherlv_11= 'jBC:' ( (lv_type_12_0= ruleJBC_ID ) ) otherlv_13= '}'
                    {
                    otherlv_10=(Token)match(input,15,FOLLOW_15_in_ruleMethod1526);

				newLeafNode(otherlv_10, grammarAccess.getMethodAccess().getLeftCurlyBracketKeyword_8_0());

                    otherlv_11=(Token)match(input,25,FOLLOW_25_in_ruleMethod1538);

				newLeafNode(otherlv_11, grammarAccess.getMethodAccess().getJBCKeyword_8_1());

                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:885:1: ( (lv_type_12_0= ruleJBC_ID ) )
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:886:1: (lv_type_12_0= ruleJBC_ID )
                    {
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:886:1: (lv_type_12_0= ruleJBC_ID )
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:887:3: lv_type_12_0= ruleJBC_ID
                    {

			        newCompositeNode(grammarAccess.getMethodAccess().getTypeJBC_IDParserRuleCall_8_2_0());

                    pushFollow(FOLLOW_ruleJBC_ID_in_ruleMethod1559);
                    lv_type_12_0=ruleJBC_ID();

                    state._fsp--;


			        if (current==null) {
			            current = createModelElementForParent(grammarAccess.getMethodRule());
			        }
					set(
						current,
						"type",
					lv_type_12_0,
					"JBC_ID");
			        afterParserOrEnumRuleCall();


                    }


                    }

                    otherlv_13=(Token)match(input,18,FOLLOW_18_in_ruleMethod1571);

				newLeafNode(otherlv_13, grammarAccess.getMethodAccess().getRightCurlyBracketKeyword_8_3());


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
    // $ANTLR end "ruleMethod"


    // $ANTLR start "entryRuleInterface"
    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:915:1: entryRuleInterface returns [EObject current=null] : iv_ruleInterface= ruleInterface EOF ;
    public final EObject entryRuleInterface() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleInterface = null;


        try {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:916:2: (iv_ruleInterface= ruleInterface EOF )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:917:2: iv_ruleInterface= ruleInterface EOF
            {
             newCompositeNode(grammarAccess.getInterfaceRule());
            pushFollow(FOLLOW_ruleInterface_in_entryRuleInterface1609);
            iv_ruleInterface=ruleInterface();

            state._fsp--;

             current =iv_ruleInterface;
            match(input,EOF,FOLLOW_EOF_in_entryRuleInterface1619);

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
    // $ANTLR end "entryRuleInterface"


    // $ANTLR start "ruleInterface"
    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:924:1: ruleInterface returns [EObject current=null] : ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_accessSpecifier_1_0= ruleAccessSpecifier ) ) otherlv_2= 'interface' ( (lv_name_3_0= RULE_ID ) ) otherlv_4= '(' ( ( (lv_arguments_5_0= ruleArgument ) ) (otherlv_6= ',' ( (lv_arguments_7_0= ruleArgument ) ) )* )? otherlv_8= ')' ) ;
    public final EObject ruleInterface() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token lv_name_3_0=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        AntlrDatatypeRuleToken lv_documentation_0_0 = null;

        Enumerator lv_accessSpecifier_1_0 = null;

        EObject lv_arguments_5_0 = null;

        EObject lv_arguments_7_0 = null;


         enterRule();

        try {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:927:28: ( ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_accessSpecifier_1_0= ruleAccessSpecifier ) ) otherlv_2= 'interface' ( (lv_name_3_0= RULE_ID ) ) otherlv_4= '(' ( ( (lv_arguments_5_0= ruleArgument ) ) (otherlv_6= ',' ( (lv_arguments_7_0= ruleArgument ) ) )* )? otherlv_8= ')' ) )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:928:1: ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_accessSpecifier_1_0= ruleAccessSpecifier ) ) otherlv_2= 'interface' ( (lv_name_3_0= RULE_ID ) ) otherlv_4= '(' ( ( (lv_arguments_5_0= ruleArgument ) ) (otherlv_6= ',' ( (lv_arguments_7_0= ruleArgument ) ) )* )? otherlv_8= ')' )
            {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:928:1: ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_accessSpecifier_1_0= ruleAccessSpecifier ) ) otherlv_2= 'interface' ( (lv_name_3_0= RULE_ID ) ) otherlv_4= '(' ( ( (lv_arguments_5_0= ruleArgument ) ) (otherlv_6= ',' ( (lv_arguments_7_0= ruleArgument ) ) )* )? otherlv_8= ')' )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:928:2: ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_accessSpecifier_1_0= ruleAccessSpecifier ) ) otherlv_2= 'interface' ( (lv_name_3_0= RULE_ID ) ) otherlv_4= '(' ( ( (lv_arguments_5_0= ruleArgument ) ) (otherlv_6= ',' ( (lv_arguments_7_0= ruleArgument ) ) )* )? otherlv_8= ')'
            {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:928:2: ( (lv_documentation_0_0= ruleDocumentation ) )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==RULE_ML_DOC) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:929:1: (lv_documentation_0_0= ruleDocumentation )
                    {
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:929:1: (lv_documentation_0_0= ruleDocumentation )
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:930:3: lv_documentation_0_0= ruleDocumentation
                    {

			        newCompositeNode(grammarAccess.getInterfaceAccess().getDocumentationDocumentationParserRuleCall_0_0());

                    pushFollow(FOLLOW_ruleDocumentation_in_ruleInterface1665);
                    lv_documentation_0_0=ruleDocumentation();

                    state._fsp--;


			        if (current==null) {
			            current = createModelElementForParent(grammarAccess.getInterfaceRule());
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

            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:946:3: ( (lv_accessSpecifier_1_0= ruleAccessSpecifier ) )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:947:1: (lv_accessSpecifier_1_0= ruleAccessSpecifier )
            {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:947:1: (lv_accessSpecifier_1_0= ruleAccessSpecifier )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:948:3: lv_accessSpecifier_1_0= ruleAccessSpecifier
            {

		        newCompositeNode(grammarAccess.getInterfaceAccess().getAccessSpecifierAccessSpecifierEnumRuleCall_1_0());

            pushFollow(FOLLOW_ruleAccessSpecifier_in_ruleInterface1687);
            lv_accessSpecifier_1_0=ruleAccessSpecifier();

            state._fsp--;


		        if (current==null) {
		            current = createModelElementForParent(grammarAccess.getInterfaceRule());
		        }
				set(
					current,
					"accessSpecifier",
				lv_accessSpecifier_1_0,
				"AccessSpecifier");
		        afterParserOrEnumRuleCall();


            }


            }

            otherlv_2=(Token)match(input,29,FOLLOW_29_in_ruleInterface1699);

			newLeafNode(otherlv_2, grammarAccess.getInterfaceAccess().getInterfaceKeyword_2());

            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:968:1: ( (lv_name_3_0= RULE_ID ) )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:969:1: (lv_name_3_0= RULE_ID )
            {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:969:1: (lv_name_3_0= RULE_ID )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:970:3: lv_name_3_0= RULE_ID
            {
            lv_name_3_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleInterface1716);

				newLeafNode(lv_name_3_0, grammarAccess.getInterfaceAccess().getNameIDTerminalRuleCall_3_0());


		        if (current==null) {
		            current = createModelElement(grammarAccess.getInterfaceRule());
		        }
				setWithLastConsumed(
					current,
					"name",
				lv_name_3_0,
				"ID");


            }


            }

            otherlv_4=(Token)match(input,20,FOLLOW_20_in_ruleInterface1733);

			newLeafNode(otherlv_4, grammarAccess.getInterfaceAccess().getLeftParenthesisKeyword_4());

            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:990:1: ( ( (lv_arguments_5_0= ruleArgument ) ) (otherlv_6= ',' ( (lv_arguments_7_0= ruleArgument ) ) )* )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==RULE_ID||LA19_0==RULE_ML_DOC||(LA19_0>=43 && LA19_0<=45)) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:990:2: ( (lv_arguments_5_0= ruleArgument ) ) (otherlv_6= ',' ( (lv_arguments_7_0= ruleArgument ) ) )*
                    {
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:990:2: ( (lv_arguments_5_0= ruleArgument ) )
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:991:1: (lv_arguments_5_0= ruleArgument )
                    {
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:991:1: (lv_arguments_5_0= ruleArgument )
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:992:3: lv_arguments_5_0= ruleArgument
                    {

			        newCompositeNode(grammarAccess.getInterfaceAccess().getArgumentsArgumentParserRuleCall_5_0_0());

                    pushFollow(FOLLOW_ruleArgument_in_ruleInterface1755);
                    lv_arguments_5_0=ruleArgument();

                    state._fsp--;


			        if (current==null) {
			            current = createModelElementForParent(grammarAccess.getInterfaceRule());
			        }
					add(
						current,
						"arguments",
					lv_arguments_5_0,
					"Argument");
			        afterParserOrEnumRuleCall();


                    }


                    }

                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1008:2: (otherlv_6= ',' ( (lv_arguments_7_0= ruleArgument ) ) )*
                    loop18:
                    do {
                        int alt18=2;
                        int LA18_0 = input.LA(1);

                        if ( (LA18_0==28) ) {
                            alt18=1;
                        }


                        switch (alt18) {
			case 1 :
			    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1008:4: otherlv_6= ',' ( (lv_arguments_7_0= ruleArgument ) )
			    {
			    otherlv_6=(Token)match(input,28,FOLLOW_28_in_ruleInterface1768);

					newLeafNode(otherlv_6, grammarAccess.getInterfaceAccess().getCommaKeyword_5_1_0());

			    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1012:1: ( (lv_arguments_7_0= ruleArgument ) )
			    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1013:1: (lv_arguments_7_0= ruleArgument )
			    {
			    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1013:1: (lv_arguments_7_0= ruleArgument )
			    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1014:3: lv_arguments_7_0= ruleArgument
			    {

				        newCompositeNode(grammarAccess.getInterfaceAccess().getArgumentsArgumentParserRuleCall_5_1_1_0());

			    pushFollow(FOLLOW_ruleArgument_in_ruleInterface1789);
			    lv_arguments_7_0=ruleArgument();

			    state._fsp--;


				        if (current==null) {
				            current = createModelElementForParent(grammarAccess.getInterfaceRule());
				        }
						add(
							current,
							"arguments",
						lv_arguments_7_0,
						"Argument");
				        afterParserOrEnumRuleCall();


			    }


			    }


			    }
			    break;

			default :
			    break loop18;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_8=(Token)match(input,21,FOLLOW_21_in_ruleInterface1805);

			newLeafNode(otherlv_8, grammarAccess.getInterfaceAccess().getRightParenthesisKeyword_6());


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
    // $ANTLR end "ruleInterface"


    // $ANTLR start "entryRuleAttribute"
    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1042:1: entryRuleAttribute returns [EObject current=null] : iv_ruleAttribute= ruleAttribute EOF ;
    public final EObject entryRuleAttribute() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAttribute = null;


        try {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1043:2: (iv_ruleAttribute= ruleAttribute EOF )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1044:2: iv_ruleAttribute= ruleAttribute EOF
            {
             newCompositeNode(grammarAccess.getAttributeRule());
            pushFollow(FOLLOW_ruleAttribute_in_entryRuleAttribute1841);
            iv_ruleAttribute=ruleAttribute();

            state._fsp--;

             current =iv_ruleAttribute;
            match(input,EOF,FOLLOW_EOF_in_entryRuleAttribute1851);

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
    // $ANTLR end "entryRuleAttribute"


    // $ANTLR start "ruleAttribute"
    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1051:1: ruleAttribute returns [EObject current=null] : ( ( (lv_attrName_0_0= RULE_ID ) ) (otherlv_1= '(' ( (lv_jBCName_2_0= RULE_ID ) ) otherlv_3= ')' )? otherlv_4= '=' ( (lv_value_5_0= RULE_INT ) ) ) ;
    public final EObject ruleAttribute() throws RecognitionException {
        EObject current = null;

        Token lv_attrName_0_0=null;
        Token otherlv_1=null;
        Token lv_jBCName_2_0=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token lv_value_5_0=null;

         enterRule();

        try {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1054:28: ( ( ( (lv_attrName_0_0= RULE_ID ) ) (otherlv_1= '(' ( (lv_jBCName_2_0= RULE_ID ) ) otherlv_3= ')' )? otherlv_4= '=' ( (lv_value_5_0= RULE_INT ) ) ) )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1055:1: ( ( (lv_attrName_0_0= RULE_ID ) ) (otherlv_1= '(' ( (lv_jBCName_2_0= RULE_ID ) ) otherlv_3= ')' )? otherlv_4= '=' ( (lv_value_5_0= RULE_INT ) ) )
            {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1055:1: ( ( (lv_attrName_0_0= RULE_ID ) ) (otherlv_1= '(' ( (lv_jBCName_2_0= RULE_ID ) ) otherlv_3= ')' )? otherlv_4= '=' ( (lv_value_5_0= RULE_INT ) ) )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1055:2: ( (lv_attrName_0_0= RULE_ID ) ) (otherlv_1= '(' ( (lv_jBCName_2_0= RULE_ID ) ) otherlv_3= ')' )? otherlv_4= '=' ( (lv_value_5_0= RULE_INT ) )
            {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1055:2: ( (lv_attrName_0_0= RULE_ID ) )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1056:1: (lv_attrName_0_0= RULE_ID )
            {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1056:1: (lv_attrName_0_0= RULE_ID )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1057:3: lv_attrName_0_0= RULE_ID
            {
            lv_attrName_0_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleAttribute1893);

				newLeafNode(lv_attrName_0_0, grammarAccess.getAttributeAccess().getAttrNameIDTerminalRuleCall_0_0());


		        if (current==null) {
		            current = createModelElement(grammarAccess.getAttributeRule());
		        }
				setWithLastConsumed(
					current,
					"attrName",
				lv_attrName_0_0,
				"ID");


            }


            }

            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1073:2: (otherlv_1= '(' ( (lv_jBCName_2_0= RULE_ID ) ) otherlv_3= ')' )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==20) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1073:4: otherlv_1= '(' ( (lv_jBCName_2_0= RULE_ID ) ) otherlv_3= ')'
                    {
                    otherlv_1=(Token)match(input,20,FOLLOW_20_in_ruleAttribute1911);

				newLeafNode(otherlv_1, grammarAccess.getAttributeAccess().getLeftParenthesisKeyword_1_0());

                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1077:1: ( (lv_jBCName_2_0= RULE_ID ) )
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1078:1: (lv_jBCName_2_0= RULE_ID )
                    {
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1078:1: (lv_jBCName_2_0= RULE_ID )
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1079:3: lv_jBCName_2_0= RULE_ID
                    {
                    lv_jBCName_2_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleAttribute1928);

					newLeafNode(lv_jBCName_2_0, grammarAccess.getAttributeAccess().getJBCNameIDTerminalRuleCall_1_1_0());


			        if (current==null) {
			            current = createModelElement(grammarAccess.getAttributeRule());
			        }
					setWithLastConsumed(
						current,
						"jBCName",
					lv_jBCName_2_0,
					"ID");


                    }


                    }

                    otherlv_3=(Token)match(input,21,FOLLOW_21_in_ruleAttribute1945);

				newLeafNode(otherlv_3, grammarAccess.getAttributeAccess().getRightParenthesisKeyword_1_2());


                    }
                    break;

            }

            otherlv_4=(Token)match(input,22,FOLLOW_22_in_ruleAttribute1959);

			newLeafNode(otherlv_4, grammarAccess.getAttributeAccess().getEqualsSignKeyword_2());

            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1103:1: ( (lv_value_5_0= RULE_INT ) )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1104:1: (lv_value_5_0= RULE_INT )
            {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1104:1: (lv_value_5_0= RULE_INT )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1105:3: lv_value_5_0= RULE_INT
            {
            lv_value_5_0=(Token)match(input,RULE_INT,FOLLOW_RULE_INT_in_ruleAttribute1976);

				newLeafNode(lv_value_5_0, grammarAccess.getAttributeAccess().getValueINTTerminalRuleCall_3_0());


		        if (current==null) {
		            current = createModelElement(grammarAccess.getAttributeRule());
		        }
				setWithLastConsumed(
					current,
					"value",
				lv_value_5_0,
				"INT");


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
    // $ANTLR end "ruleAttribute"


    // $ANTLR start "entryRuleArgument"
    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1129:1: entryRuleArgument returns [EObject current=null] : iv_ruleArgument= ruleArgument EOF ;
    public final EObject entryRuleArgument() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleArgument = null;


        try {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1130:2: (iv_ruleArgument= ruleArgument EOF )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1131:2: iv_ruleArgument= ruleArgument EOF
            {
             newCompositeNode(grammarAccess.getArgumentRule());
            pushFollow(FOLLOW_ruleArgument_in_entryRuleArgument2017);
            iv_ruleArgument=ruleArgument();

            state._fsp--;

             current =iv_ruleArgument;
            match(input,EOF,FOLLOW_EOF_in_entryRuleArgument2027);

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
    // $ANTLR end "entryRuleArgument"


    // $ANTLR start "ruleArgument"
    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1138:1: ruleArgument returns [EObject current=null] : ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_inout_1_0= ruleInOutType ) )? ( (lv_name_2_0= RULE_ID ) ) ( ( ruleEntityRef ) ) ( (lv_multiplicity_4_0= ruleMultiplicity ) )? ) ;
    public final EObject ruleArgument() throws RecognitionException {
        EObject current = null;

        Token lv_name_2_0=null;
        AntlrDatatypeRuleToken lv_documentation_0_0 = null;

        Enumerator lv_inout_1_0 = null;

        Enumerator lv_multiplicity_4_0 = null;


         enterRule();

        try {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1141:28: ( ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_inout_1_0= ruleInOutType ) )? ( (lv_name_2_0= RULE_ID ) ) ( ( ruleEntityRef ) ) ( (lv_multiplicity_4_0= ruleMultiplicity ) )? ) )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1142:1: ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_inout_1_0= ruleInOutType ) )? ( (lv_name_2_0= RULE_ID ) ) ( ( ruleEntityRef ) ) ( (lv_multiplicity_4_0= ruleMultiplicity ) )? )
            {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1142:1: ( ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_inout_1_0= ruleInOutType ) )? ( (lv_name_2_0= RULE_ID ) ) ( ( ruleEntityRef ) ) ( (lv_multiplicity_4_0= ruleMultiplicity ) )? )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1142:2: ( (lv_documentation_0_0= ruleDocumentation ) )? ( (lv_inout_1_0= ruleInOutType ) )? ( (lv_name_2_0= RULE_ID ) ) ( ( ruleEntityRef ) ) ( (lv_multiplicity_4_0= ruleMultiplicity ) )?
            {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1142:2: ( (lv_documentation_0_0= ruleDocumentation ) )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==RULE_ML_DOC) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1143:1: (lv_documentation_0_0= ruleDocumentation )
                    {
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1143:1: (lv_documentation_0_0= ruleDocumentation )
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1144:3: lv_documentation_0_0= ruleDocumentation
                    {

			        newCompositeNode(grammarAccess.getArgumentAccess().getDocumentationDocumentationParserRuleCall_0_0());

                    pushFollow(FOLLOW_ruleDocumentation_in_ruleArgument2073);
                    lv_documentation_0_0=ruleDocumentation();

                    state._fsp--;


			        if (current==null) {
			            current = createModelElementForParent(grammarAccess.getArgumentRule());
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

            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1160:3: ( (lv_inout_1_0= ruleInOutType ) )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( ((LA22_0>=43 && LA22_0<=45)) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1161:1: (lv_inout_1_0= ruleInOutType )
                    {
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1161:1: (lv_inout_1_0= ruleInOutType )
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1162:3: lv_inout_1_0= ruleInOutType
                    {

			        newCompositeNode(grammarAccess.getArgumentAccess().getInoutInOutTypeEnumRuleCall_1_0());

                    pushFollow(FOLLOW_ruleInOutType_in_ruleArgument2095);
                    lv_inout_1_0=ruleInOutType();

                    state._fsp--;


			        if (current==null) {
			            current = createModelElementForParent(grammarAccess.getArgumentRule());
			        }
					set(
						current,
						"inout",
					lv_inout_1_0,
					"InOutType");
			        afterParserOrEnumRuleCall();


                    }


                    }
                    break;

            }

            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1178:3: ( (lv_name_2_0= RULE_ID ) )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1179:1: (lv_name_2_0= RULE_ID )
            {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1179:1: (lv_name_2_0= RULE_ID )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1180:3: lv_name_2_0= RULE_ID
            {
            lv_name_2_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleArgument2113);

				newLeafNode(lv_name_2_0, grammarAccess.getArgumentAccess().getNameIDTerminalRuleCall_2_0());


		        if (current==null) {
		            current = createModelElement(grammarAccess.getArgumentRule());
		        }
				setWithLastConsumed(
					current,
					"name",
				lv_name_2_0,
				"ID");


            }


            }

            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1196:2: ( ( ruleEntityRef ) )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1197:1: ( ruleEntityRef )
            {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1197:1: ( ruleEntityRef )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1198:3: ruleEntityRef
            {

				if (current==null) {
		            current = createModelElement(grammarAccess.getArgumentRule());
		        }


		        newCompositeNode(grammarAccess.getArgumentAccess().getTypeMdfEntityCrossReference_3_0());

            pushFollow(FOLLOW_ruleEntityRef_in_ruleArgument2141);
            ruleEntityRef();

            state._fsp--;


		        afterParserOrEnumRuleCall();


            }


            }

            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1211:2: ( (lv_multiplicity_4_0= ruleMultiplicity ) )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( ((LA23_0>=46 && LA23_0<=49)) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1212:1: (lv_multiplicity_4_0= ruleMultiplicity )
                    {
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1212:1: (lv_multiplicity_4_0= ruleMultiplicity )
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1213:3: lv_multiplicity_4_0= ruleMultiplicity
                    {

			        newCompositeNode(grammarAccess.getArgumentAccess().getMultiplicityMultiplicityEnumRuleCall_4_0());

                    pushFollow(FOLLOW_ruleMultiplicity_in_ruleArgument2162);
                    lv_multiplicity_4_0=ruleMultiplicity();

                    state._fsp--;


			        if (current==null) {
			            current = createModelElementForParent(grammarAccess.getArgumentRule());
			        }
					set(
						current,
						"multiplicity",
					lv_multiplicity_4_0,
					"Multiplicity");
			        afterParserOrEnumRuleCall();


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
    // $ANTLR end "ruleArgument"


    // $ANTLR start "entryRuleDocumentation"
    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1237:1: entryRuleDocumentation returns [String current=null] : iv_ruleDocumentation= ruleDocumentation EOF ;
    public final String entryRuleDocumentation() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleDocumentation = null;


        try {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1238:2: (iv_ruleDocumentation= ruleDocumentation EOF )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1239:2: iv_ruleDocumentation= ruleDocumentation EOF
            {
             newCompositeNode(grammarAccess.getDocumentationRule());
            pushFollow(FOLLOW_ruleDocumentation_in_entryRuleDocumentation2200);
            iv_ruleDocumentation=ruleDocumentation();

            state._fsp--;

             current =iv_ruleDocumentation.getText();
            match(input,EOF,FOLLOW_EOF_in_entryRuleDocumentation2211);

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
    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1246:1: ruleDocumentation returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_ML_DOC_0= RULE_ML_DOC ;
    public final AntlrDatatypeRuleToken ruleDocumentation() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ML_DOC_0=null;

         enterRule();

        try {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1249:28: (this_ML_DOC_0= RULE_ML_DOC )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1250:5: this_ML_DOC_0= RULE_ML_DOC
            {
            this_ML_DOC_0=(Token)match(input,RULE_ML_DOC,FOLLOW_RULE_ML_DOC_in_ruleDocumentation2250);

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


    // $ANTLR start "entryRuleMethodAnnotation"
    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1265:1: entryRuleMethodAnnotation returns [EObject current=null] : iv_ruleMethodAnnotation= ruleMethodAnnotation EOF ;
    public final EObject entryRuleMethodAnnotation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMethodAnnotation = null;


        try {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1266:2: (iv_ruleMethodAnnotation= ruleMethodAnnotation EOF )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1267:2: iv_ruleMethodAnnotation= ruleMethodAnnotation EOF
            {
             newCompositeNode(grammarAccess.getMethodAnnotationRule());
            pushFollow(FOLLOW_ruleMethodAnnotation_in_entryRuleMethodAnnotation2294);
            iv_ruleMethodAnnotation=ruleMethodAnnotation();

            state._fsp--;

             current =iv_ruleMethodAnnotation;
            match(input,EOF,FOLLOW_EOF_in_entryRuleMethodAnnotation2304);

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
    // $ANTLR end "entryRuleMethodAnnotation"


    // $ANTLR start "ruleMethodAnnotation"
    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1274:1: ruleMethodAnnotation returns [EObject current=null] : (otherlv_0= '@' otherlv_1= 't24' otherlv_2= ':' ( (lv_name_3_0= ruleT24MethodStereotype ) ) ) ;
    public final EObject ruleMethodAnnotation() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Enumerator lv_name_3_0 = null;


         enterRule();

        try {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1277:28: ( (otherlv_0= '@' otherlv_1= 't24' otherlv_2= ':' ( (lv_name_3_0= ruleT24MethodStereotype ) ) ) )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1278:1: (otherlv_0= '@' otherlv_1= 't24' otherlv_2= ':' ( (lv_name_3_0= ruleT24MethodStereotype ) ) )
            {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1278:1: (otherlv_0= '@' otherlv_1= 't24' otherlv_2= ':' ( (lv_name_3_0= ruleT24MethodStereotype ) ) )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1278:3: otherlv_0= '@' otherlv_1= 't24' otherlv_2= ':' ( (lv_name_3_0= ruleT24MethodStereotype ) )
            {
            otherlv_0=(Token)match(input,30,FOLLOW_30_in_ruleMethodAnnotation2341);

			newLeafNode(otherlv_0, grammarAccess.getMethodAnnotationAccess().getCommercialAtKeyword_0());

            otherlv_1=(Token)match(input,31,FOLLOW_31_in_ruleMethodAnnotation2353);

			newLeafNode(otherlv_1, grammarAccess.getMethodAnnotationAccess().getT24Keyword_1());

            otherlv_2=(Token)match(input,32,FOLLOW_32_in_ruleMethodAnnotation2365);

			newLeafNode(otherlv_2, grammarAccess.getMethodAnnotationAccess().getColonKeyword_2());

            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1290:1: ( (lv_name_3_0= ruleT24MethodStereotype ) )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1291:1: (lv_name_3_0= ruleT24MethodStereotype )
            {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1291:1: (lv_name_3_0= ruleT24MethodStereotype )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1292:3: lv_name_3_0= ruleT24MethodStereotype
            {

		        newCompositeNode(grammarAccess.getMethodAnnotationAccess().getNameT24MethodStereotypeEnumRuleCall_3_0());

            pushFollow(FOLLOW_ruleT24MethodStereotype_in_ruleMethodAnnotation2386);
            lv_name_3_0=ruleT24MethodStereotype();

            state._fsp--;


		        if (current==null) {
		            current = createModelElementForParent(grammarAccess.getMethodAnnotationRule());
		        }
				set(
					current,
					"name",
				lv_name_3_0,
				"T24MethodStereotype");
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
    // $ANTLR end "ruleMethodAnnotation"


    // $ANTLR start "entryRuleEntityRef"
    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1316:1: entryRuleEntityRef returns [String current=null] : iv_ruleEntityRef= ruleEntityRef EOF ;
    public final String entryRuleEntityRef() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleEntityRef = null;


        try {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1317:2: (iv_ruleEntityRef= ruleEntityRef EOF )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1318:2: iv_ruleEntityRef= ruleEntityRef EOF
            {
             newCompositeNode(grammarAccess.getEntityRefRule());
            pushFollow(FOLLOW_ruleEntityRef_in_entryRuleEntityRef2423);
            iv_ruleEntityRef=ruleEntityRef();

            state._fsp--;

             current =iv_ruleEntityRef.getText();
            match(input,EOF,FOLLOW_EOF_in_entryRuleEntityRef2434);

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
    // $ANTLR end "entryRuleEntityRef"


    // $ANTLR start "ruleEntityRef"
    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1325:1: ruleEntityRef returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID (kw= ':' this_ID_2= RULE_ID )? ) ;
    public final AntlrDatatypeRuleToken ruleEntityRef() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        Token kw=null;
        Token this_ID_2=null;

         enterRule();

        try {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1328:28: ( (this_ID_0= RULE_ID (kw= ':' this_ID_2= RULE_ID )? ) )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1329:1: (this_ID_0= RULE_ID (kw= ':' this_ID_2= RULE_ID )? )
            {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1329:1: (this_ID_0= RULE_ID (kw= ':' this_ID_2= RULE_ID )? )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1329:6: this_ID_0= RULE_ID (kw= ':' this_ID_2= RULE_ID )?
            {
            this_ID_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleEntityRef2474);

			current.merge(this_ID_0);


                newLeafNode(this_ID_0, grammarAccess.getEntityRefAccess().getIDTerminalRuleCall_0());

            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1336:1: (kw= ':' this_ID_2= RULE_ID )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==32) ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1337:2: kw= ':' this_ID_2= RULE_ID
                    {
                    kw=(Token)match(input,32,FOLLOW_32_in_ruleEntityRef2493);

                            current.merge(kw);
                            newLeafNode(kw, grammarAccess.getEntityRefAccess().getColonKeyword_1_0());

                    this_ID_2=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleEntityRef2508);

				current.merge(this_ID_2);


                        newLeafNode(this_ID_2, grammarAccess.getEntityRefAccess().getIDTerminalRuleCall_1_1());


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
    // $ANTLR end "ruleEntityRef"


    // $ANTLR start "entryRuleJBC_ID"
    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1357:1: entryRuleJBC_ID returns [String current=null] : iv_ruleJBC_ID= ruleJBC_ID EOF ;
    public final String entryRuleJBC_ID() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleJBC_ID = null;


        try {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1358:2: (iv_ruleJBC_ID= ruleJBC_ID EOF )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1359:2: iv_ruleJBC_ID= ruleJBC_ID EOF
            {
             newCompositeNode(grammarAccess.getJBC_IDRule());
            pushFollow(FOLLOW_ruleJBC_ID_in_entryRuleJBC_ID2556);
            iv_ruleJBC_ID=ruleJBC_ID();

            state._fsp--;

             current =iv_ruleJBC_ID.getText();
            match(input,EOF,FOLLOW_EOF_in_entryRuleJBC_ID2567);

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
    // $ANTLR end "entryRuleJBC_ID"


    // $ANTLR start "ruleJBC_ID"
    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1366:1: ruleJBC_ID returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID (kw= '$' this_ID_2= RULE_ID )* ) ;
    public final AntlrDatatypeRuleToken ruleJBC_ID() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        Token kw=null;
        Token this_ID_2=null;

         enterRule();

        try {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1369:28: ( (this_ID_0= RULE_ID (kw= '$' this_ID_2= RULE_ID )* ) )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1370:1: (this_ID_0= RULE_ID (kw= '$' this_ID_2= RULE_ID )* )
            {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1370:1: (this_ID_0= RULE_ID (kw= '$' this_ID_2= RULE_ID )* )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1370:6: this_ID_0= RULE_ID (kw= '$' this_ID_2= RULE_ID )*
            {
            this_ID_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleJBC_ID2607);

			current.merge(this_ID_0);


                newLeafNode(this_ID_0, grammarAccess.getJBC_IDAccess().getIDTerminalRuleCall_0());

            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1377:1: (kw= '$' this_ID_2= RULE_ID )*
            loop25:
            do {
                int alt25=2;
                int LA25_0 = input.LA(1);

                if ( (LA25_0==33) ) {
                    alt25=1;
                }


                switch (alt25) {
		case 1 :
		    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1378:2: kw= '$' this_ID_2= RULE_ID
		    {
		    kw=(Token)match(input,33,FOLLOW_33_in_ruleJBC_ID2626);

		            current.merge(kw);
		            newLeafNode(kw, grammarAccess.getJBC_IDAccess().getDollarSignKeyword_1_0());

		    this_ID_2=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleJBC_ID2641);

				current.merge(this_ID_2);


		        newLeafNode(this_ID_2, grammarAccess.getJBC_IDAccess().getIDTerminalRuleCall_1_1());


		    }
		    break;

		default :
		    break loop25;
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
    // $ANTLR end "ruleJBC_ID"


    // $ANTLR start "entryRuleString_Value"
    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1398:1: entryRuleString_Value returns [String current=null] : iv_ruleString_Value= ruleString_Value EOF ;
    public final String entryRuleString_Value() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleString_Value = null;


        try {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1399:2: (iv_ruleString_Value= ruleString_Value EOF )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1400:2: iv_ruleString_Value= ruleString_Value EOF
            {
             newCompositeNode(grammarAccess.getString_ValueRule());
            pushFollow(FOLLOW_ruleString_Value_in_entryRuleString_Value2689);
            iv_ruleString_Value=ruleString_Value();

            state._fsp--;

             current =iv_ruleString_Value.getText();
            match(input,EOF,FOLLOW_EOF_in_entryRuleString_Value2700);

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
    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1407:1: ruleString_Value returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_STRING_0= RULE_STRING | this_ID_1= RULE_ID | this_VALUE_2= RULE_VALUE ) ;
    public final AntlrDatatypeRuleToken ruleString_Value() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_STRING_0=null;
        Token this_ID_1=null;
        Token this_VALUE_2=null;

         enterRule();

        try {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1410:28: ( (this_STRING_0= RULE_STRING | this_ID_1= RULE_ID | this_VALUE_2= RULE_VALUE ) )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1411:1: (this_STRING_0= RULE_STRING | this_ID_1= RULE_ID | this_VALUE_2= RULE_VALUE )
            {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1411:1: (this_STRING_0= RULE_STRING | this_ID_1= RULE_ID | this_VALUE_2= RULE_VALUE )
            int alt26=3;
            switch ( input.LA(1) ) {
            case RULE_STRING:
                {
                alt26=1;
                }
                break;
            case RULE_ID:
                {
                alt26=2;
                }
                break;
            case RULE_VALUE:
                {
                alt26=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 26, 0, input);

                throw nvae;
            }

            switch (alt26) {
                case 1 :
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1411:6: this_STRING_0= RULE_STRING
                    {
                    this_STRING_0=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleString_Value2740);

				current.merge(this_STRING_0);


                        newLeafNode(this_STRING_0, grammarAccess.getString_ValueAccess().getSTRINGTerminalRuleCall_0());


                    }
                    break;
                case 2 :
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1419:10: this_ID_1= RULE_ID
                    {
                    this_ID_1=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleString_Value2766);

				current.merge(this_ID_1);


                        newLeafNode(this_ID_1, grammarAccess.getString_ValueAccess().getIDTerminalRuleCall_1());


                    }
                    break;
                case 3 :
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1427:10: this_VALUE_2= RULE_VALUE
                    {
                    this_VALUE_2=(Token)match(input,RULE_VALUE,FOLLOW_RULE_VALUE_in_ruleString_Value2792);

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


    // $ANTLR start "ruleT24MethodStereotype"
    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1442:1: ruleT24MethodStereotype returns [Enumerator current=null] : ( (enumLiteral_0= 'integrationFlowSupportable' ) | (enumLiteral_1= 'unsafe' ) | (enumLiteral_2= 'idempotent' ) ) ;
    public final Enumerator ruleT24MethodStereotype() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;

         enterRule();
        try {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1444:28: ( ( (enumLiteral_0= 'integrationFlowSupportable' ) | (enumLiteral_1= 'unsafe' ) | (enumLiteral_2= 'idempotent' ) ) )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1445:1: ( (enumLiteral_0= 'integrationFlowSupportable' ) | (enumLiteral_1= 'unsafe' ) | (enumLiteral_2= 'idempotent' ) )
            {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1445:1: ( (enumLiteral_0= 'integrationFlowSupportable' ) | (enumLiteral_1= 'unsafe' ) | (enumLiteral_2= 'idempotent' ) )
            int alt27=3;
            switch ( input.LA(1) ) {
            case 34:
                {
                alt27=1;
                }
                break;
            case 35:
                {
                alt27=2;
                }
                break;
            case 36:
                {
                alt27=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 27, 0, input);

                throw nvae;
            }

            switch (alt27) {
                case 1 :
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1445:2: (enumLiteral_0= 'integrationFlowSupportable' )
                    {
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1445:2: (enumLiteral_0= 'integrationFlowSupportable' )
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1445:4: enumLiteral_0= 'integrationFlowSupportable'
                    {
                    enumLiteral_0=(Token)match(input,34,FOLLOW_34_in_ruleT24MethodStereotype2851);

                            current = grammarAccess.getT24MethodStereotypeAccess().getIntegrationFlowSupportableEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_0, grammarAccess.getT24MethodStereotypeAccess().getIntegrationFlowSupportableEnumLiteralDeclaration_0());


                    }


                    }
                    break;
                case 2 :
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1451:6: (enumLiteral_1= 'unsafe' )
                    {
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1451:6: (enumLiteral_1= 'unsafe' )
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1451:8: enumLiteral_1= 'unsafe'
                    {
                    enumLiteral_1=(Token)match(input,35,FOLLOW_35_in_ruleT24MethodStereotype2868);

                            current = grammarAccess.getT24MethodStereotypeAccess().getUnsafeEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_1, grammarAccess.getT24MethodStereotypeAccess().getUnsafeEnumLiteralDeclaration_1());


                    }


                    }
                    break;
                case 3 :
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1457:6: (enumLiteral_2= 'idempotent' )
                    {
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1457:6: (enumLiteral_2= 'idempotent' )
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1457:8: enumLiteral_2= 'idempotent'
                    {
                    enumLiteral_2=(Token)match(input,36,FOLLOW_36_in_ruleT24MethodStereotype2885);

                            current = grammarAccess.getT24MethodStereotypeAccess().getIdempotentEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_2, grammarAccess.getT24MethodStereotypeAccess().getIdempotentEnumLiteralDeclaration_2());


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
    // $ANTLR end "ruleT24MethodStereotype"


    // $ANTLR start "ruleReadWrite"
    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1467:1: ruleReadWrite returns [Enumerator current=null] : ( (enumLiteral_0= 'readonly' ) | (enumLiteral_1= 'readwrite' ) ) ;
    public final Enumerator ruleReadWrite() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;

         enterRule();
        try {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1469:28: ( ( (enumLiteral_0= 'readonly' ) | (enumLiteral_1= 'readwrite' ) ) )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1470:1: ( (enumLiteral_0= 'readonly' ) | (enumLiteral_1= 'readwrite' ) )
            {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1470:1: ( (enumLiteral_0= 'readonly' ) | (enumLiteral_1= 'readwrite' ) )
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==37) ) {
                alt28=1;
            }
            else if ( (LA28_0==38) ) {
                alt28=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 28, 0, input);

                throw nvae;
            }
            switch (alt28) {
                case 1 :
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1470:2: (enumLiteral_0= 'readonly' )
                    {
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1470:2: (enumLiteral_0= 'readonly' )
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1470:4: enumLiteral_0= 'readonly'
                    {
                    enumLiteral_0=(Token)match(input,37,FOLLOW_37_in_ruleReadWrite2930);

                            current = grammarAccess.getReadWriteAccess().getReadonlyEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_0, grammarAccess.getReadWriteAccess().getReadonlyEnumLiteralDeclaration_0());


                    }


                    }
                    break;
                case 2 :
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1476:6: (enumLiteral_1= 'readwrite' )
                    {
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1476:6: (enumLiteral_1= 'readwrite' )
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1476:8: enumLiteral_1= 'readwrite'
                    {
                    enumLiteral_1=(Token)match(input,38,FOLLOW_38_in_ruleReadWrite2947);

                            current = grammarAccess.getReadWriteAccess().getReadwriteEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_1, grammarAccess.getReadWriteAccess().getReadwriteEnumLiteralDeclaration_1());


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
    // $ANTLR end "ruleReadWrite"


    // $ANTLR start "ruleAccessSpecifier"
    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1486:1: ruleAccessSpecifier returns [Enumerator current=null] : ( (enumLiteral_0= 'module' ) | (enumLiteral_1= 'private' ) | (enumLiteral_2= 'public' ) ) ;
    public final Enumerator ruleAccessSpecifier() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;

         enterRule();
        try {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1488:28: ( ( (enumLiteral_0= 'module' ) | (enumLiteral_1= 'private' ) | (enumLiteral_2= 'public' ) ) )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1489:1: ( (enumLiteral_0= 'module' ) | (enumLiteral_1= 'private' ) | (enumLiteral_2= 'public' ) )
            {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1489:1: ( (enumLiteral_0= 'module' ) | (enumLiteral_1= 'private' ) | (enumLiteral_2= 'public' ) )
            int alt29=3;
            switch ( input.LA(1) ) {
            case 39:
                {
                alt29=1;
                }
                break;
            case 40:
                {
                alt29=2;
                }
                break;
            case 41:
                {
                alt29=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 29, 0, input);

                throw nvae;
            }

            switch (alt29) {
                case 1 :
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1489:2: (enumLiteral_0= 'module' )
                    {
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1489:2: (enumLiteral_0= 'module' )
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1489:4: enumLiteral_0= 'module'
                    {
                    enumLiteral_0=(Token)match(input,39,FOLLOW_39_in_ruleAccessSpecifier2992);

                            current = grammarAccess.getAccessSpecifierAccess().getModuleEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_0, grammarAccess.getAccessSpecifierAccess().getModuleEnumLiteralDeclaration_0());


                    }


                    }
                    break;
                case 2 :
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1495:6: (enumLiteral_1= 'private' )
                    {
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1495:6: (enumLiteral_1= 'private' )
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1495:8: enumLiteral_1= 'private'
                    {
                    enumLiteral_1=(Token)match(input,40,FOLLOW_40_in_ruleAccessSpecifier3009);

                            current = grammarAccess.getAccessSpecifierAccess().getPrivateEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_1, grammarAccess.getAccessSpecifierAccess().getPrivateEnumLiteralDeclaration_1());


                    }


                    }
                    break;
                case 3 :
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1501:6: (enumLiteral_2= 'public' )
                    {
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1501:6: (enumLiteral_2= 'public' )
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1501:8: enumLiteral_2= 'public'
                    {
                    enumLiteral_2=(Token)match(input,41,FOLLOW_41_in_ruleAccessSpecifier3026);

                            current = grammarAccess.getAccessSpecifierAccess().getPublicEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_2, grammarAccess.getAccessSpecifierAccess().getPublicEnumLiteralDeclaration_2());


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
    // $ANTLR end "ruleAccessSpecifier"


    // $ANTLR start "ruleMethodAccessSpecifier"
    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1511:1: ruleMethodAccessSpecifier returns [Enumerator current=null] : ( (enumLiteral_0= 'module' ) | (enumLiteral_1= 'private' ) | (enumLiteral_2= 'public' ) | (enumLiteral_3= 'external' ) ) ;
    public final Enumerator ruleMethodAccessSpecifier() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;

         enterRule();
        try {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1513:28: ( ( (enumLiteral_0= 'module' ) | (enumLiteral_1= 'private' ) | (enumLiteral_2= 'public' ) | (enumLiteral_3= 'external' ) ) )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1514:1: ( (enumLiteral_0= 'module' ) | (enumLiteral_1= 'private' ) | (enumLiteral_2= 'public' ) | (enumLiteral_3= 'external' ) )
            {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1514:1: ( (enumLiteral_0= 'module' ) | (enumLiteral_1= 'private' ) | (enumLiteral_2= 'public' ) | (enumLiteral_3= 'external' ) )
            int alt30=4;
            switch ( input.LA(1) ) {
            case 39:
                {
                alt30=1;
                }
                break;
            case 40:
                {
                alt30=2;
                }
                break;
            case 41:
                {
                alt30=3;
                }
                break;
            case 42:
                {
                alt30=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 30, 0, input);

                throw nvae;
            }

            switch (alt30) {
                case 1 :
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1514:2: (enumLiteral_0= 'module' )
                    {
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1514:2: (enumLiteral_0= 'module' )
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1514:4: enumLiteral_0= 'module'
                    {
                    enumLiteral_0=(Token)match(input,39,FOLLOW_39_in_ruleMethodAccessSpecifier3071);

                            current = grammarAccess.getMethodAccessSpecifierAccess().getModuleEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_0, grammarAccess.getMethodAccessSpecifierAccess().getModuleEnumLiteralDeclaration_0());


                    }


                    }
                    break;
                case 2 :
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1520:6: (enumLiteral_1= 'private' )
                    {
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1520:6: (enumLiteral_1= 'private' )
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1520:8: enumLiteral_1= 'private'
                    {
                    enumLiteral_1=(Token)match(input,40,FOLLOW_40_in_ruleMethodAccessSpecifier3088);

                            current = grammarAccess.getMethodAccessSpecifierAccess().getPrivateEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_1, grammarAccess.getMethodAccessSpecifierAccess().getPrivateEnumLiteralDeclaration_1());


                    }


                    }
                    break;
                case 3 :
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1526:6: (enumLiteral_2= 'public' )
                    {
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1526:6: (enumLiteral_2= 'public' )
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1526:8: enumLiteral_2= 'public'
                    {
                    enumLiteral_2=(Token)match(input,41,FOLLOW_41_in_ruleMethodAccessSpecifier3105);

                            current = grammarAccess.getMethodAccessSpecifierAccess().getPublicEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_2, grammarAccess.getMethodAccessSpecifierAccess().getPublicEnumLiteralDeclaration_2());


                    }


                    }
                    break;
                case 4 :
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1532:6: (enumLiteral_3= 'external' )
                    {
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1532:6: (enumLiteral_3= 'external' )
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1532:8: enumLiteral_3= 'external'
                    {
                    enumLiteral_3=(Token)match(input,42,FOLLOW_42_in_ruleMethodAccessSpecifier3122);

                            current = grammarAccess.getMethodAccessSpecifierAccess().getExternalEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_3, grammarAccess.getMethodAccessSpecifierAccess().getExternalEnumLiteralDeclaration_3());


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
    // $ANTLR end "ruleMethodAccessSpecifier"


    // $ANTLR start "ruleInOutType"
    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1542:1: ruleInOutType returns [Enumerator current=null] : ( (enumLiteral_0= 'IN' ) | (enumLiteral_1= 'OUT' ) | (enumLiteral_2= 'INOUT' ) ) ;
    public final Enumerator ruleInOutType() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;

         enterRule();
        try {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1544:28: ( ( (enumLiteral_0= 'IN' ) | (enumLiteral_1= 'OUT' ) | (enumLiteral_2= 'INOUT' ) ) )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1545:1: ( (enumLiteral_0= 'IN' ) | (enumLiteral_1= 'OUT' ) | (enumLiteral_2= 'INOUT' ) )
            {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1545:1: ( (enumLiteral_0= 'IN' ) | (enumLiteral_1= 'OUT' ) | (enumLiteral_2= 'INOUT' ) )
            int alt31=3;
            switch ( input.LA(1) ) {
            case 43:
                {
                alt31=1;
                }
                break;
            case 44:
                {
                alt31=2;
                }
                break;
            case 45:
                {
                alt31=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 31, 0, input);

                throw nvae;
            }

            switch (alt31) {
                case 1 :
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1545:2: (enumLiteral_0= 'IN' )
                    {
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1545:2: (enumLiteral_0= 'IN' )
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1545:4: enumLiteral_0= 'IN'
                    {
                    enumLiteral_0=(Token)match(input,43,FOLLOW_43_in_ruleInOutType3167);

                            current = grammarAccess.getInOutTypeAccess().getINEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_0, grammarAccess.getInOutTypeAccess().getINEnumLiteralDeclaration_0());


                    }


                    }
                    break;
                case 2 :
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1551:6: (enumLiteral_1= 'OUT' )
                    {
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1551:6: (enumLiteral_1= 'OUT' )
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1551:8: enumLiteral_1= 'OUT'
                    {
                    enumLiteral_1=(Token)match(input,44,FOLLOW_44_in_ruleInOutType3184);

                            current = grammarAccess.getInOutTypeAccess().getOUTEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_1, grammarAccess.getInOutTypeAccess().getOUTEnumLiteralDeclaration_1());


                    }


                    }
                    break;
                case 3 :
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1557:6: (enumLiteral_2= 'INOUT' )
                    {
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1557:6: (enumLiteral_2= 'INOUT' )
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1557:8: enumLiteral_2= 'INOUT'
                    {
                    enumLiteral_2=(Token)match(input,45,FOLLOW_45_in_ruleInOutType3201);

                            current = grammarAccess.getInOutTypeAccess().getINOUTEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_2, grammarAccess.getInOutTypeAccess().getINOUTEnumLiteralDeclaration_2());


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
    // $ANTLR end "ruleInOutType"


    // $ANTLR start "ruleMultiplicity"
    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1567:1: ruleMultiplicity returns [Enumerator current=null] : ( (enumLiteral_0= '[0..1]' ) | (enumLiteral_1= '[0..*]' ) | (enumLiteral_2= '[1..1]' ) | (enumLiteral_3= '[1..*]' ) ) ;
    public final Enumerator ruleMultiplicity() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;

         enterRule();
        try {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1569:28: ( ( (enumLiteral_0= '[0..1]' ) | (enumLiteral_1= '[0..*]' ) | (enumLiteral_2= '[1..1]' ) | (enumLiteral_3= '[1..*]' ) ) )
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1570:1: ( (enumLiteral_0= '[0..1]' ) | (enumLiteral_1= '[0..*]' ) | (enumLiteral_2= '[1..1]' ) | (enumLiteral_3= '[1..*]' ) )
            {
            // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1570:1: ( (enumLiteral_0= '[0..1]' ) | (enumLiteral_1= '[0..*]' ) | (enumLiteral_2= '[1..1]' ) | (enumLiteral_3= '[1..*]' ) )
            int alt32=4;
            switch ( input.LA(1) ) {
            case 46:
                {
                alt32=1;
                }
                break;
            case 47:
                {
                alt32=2;
                }
                break;
            case 48:
                {
                alt32=3;
                }
                break;
            case 49:
                {
                alt32=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 32, 0, input);

                throw nvae;
            }

            switch (alt32) {
                case 1 :
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1570:2: (enumLiteral_0= '[0..1]' )
                    {
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1570:2: (enumLiteral_0= '[0..1]' )
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1570:4: enumLiteral_0= '[0..1]'
                    {
                    enumLiteral_0=(Token)match(input,46,FOLLOW_46_in_ruleMultiplicity3246);

                            current = grammarAccess.getMultiplicityAccess().getOPTIONALEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_0, grammarAccess.getMultiplicityAccess().getOPTIONALEnumLiteralDeclaration_0());


                    }


                    }
                    break;
                case 2 :
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1576:6: (enumLiteral_1= '[0..*]' )
                    {
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1576:6: (enumLiteral_1= '[0..*]' )
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1576:8: enumLiteral_1= '[0..*]'
                    {
                    enumLiteral_1=(Token)match(input,47,FOLLOW_47_in_ruleMultiplicity3263);

                            current = grammarAccess.getMultiplicityAccess().getMANYEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_1, grammarAccess.getMultiplicityAccess().getMANYEnumLiteralDeclaration_1());


                    }


                    }
                    break;
                case 3 :
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1582:6: (enumLiteral_2= '[1..1]' )
                    {
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1582:6: (enumLiteral_2= '[1..1]' )
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1582:8: enumLiteral_2= '[1..1]'
                    {
                    enumLiteral_2=(Token)match(input,48,FOLLOW_48_in_ruleMultiplicity3280);

                            current = grammarAccess.getMultiplicityAccess().getMANDATORYEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_2, grammarAccess.getMultiplicityAccess().getMANDATORYEnumLiteralDeclaration_2());


                    }


                    }
                    break;
                case 4 :
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1588:6: (enumLiteral_3= '[1..*]' )
                    {
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1588:6: (enumLiteral_3= '[1..*]' )
                    // ../com.odcgroup.service.model/src-gen/com/odcgroup/service/model/parser/antlr/internal/InternalComponent.g:1588:8: enumLiteral_3= '[1..*]'
                    {
                    enumLiteral_3=(Token)match(input,49,FOLLOW_49_in_ruleMultiplicity3297);

                            current = grammarAccess.getMultiplicityAccess().getONETOMANYEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_3, grammarAccess.getMultiplicityAccess().getONETOMANYEnumLiteralDeclaration_3());


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
    // $ANTLR end "ruleMultiplicity"

    // Delegated rules


    protected DFA3 dfa3 = new DFA3(this);
    static final String DFA3_eotS =
        "\12\uffff";
    static final String DFA3_eofS =
        "\12\uffff";
    static final String DFA3_minS =
        "\1\6\1\47\3\16\5\uffff";
    static final String DFA3_maxS =
        "\2\52\3\35\5\uffff";
    static final String DFA3_acceptS =
        "\5\uffff\1\2\1\5\1\4\1\1\1\3";
    static final String DFA3_specialS =
        "\12\uffff}>";
    static final String[] DFA3_transitionS = {
            "\1\1\40\uffff\1\2\1\3\1\4\1\5",
            "\1\2\1\3\1\4\1\5",
            "\1\6\4\uffff\1\7\3\uffff\1\11\3\uffff\1\5\1\uffff\1\10",
            "\1\6\4\uffff\1\7\3\uffff\1\11\3\uffff\1\5\1\uffff\1\10",
            "\1\6\4\uffff\1\7\3\uffff\1\11\3\uffff\1\5\1\uffff\1\10",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA3_eot = DFA.unpackEncodedString(DFA3_eotS);
    static final short[] DFA3_eof = DFA.unpackEncodedString(DFA3_eofS);
    static final char[] DFA3_min = DFA.unpackEncodedStringToUnsignedChars(DFA3_minS);
    static final char[] DFA3_max = DFA.unpackEncodedStringToUnsignedChars(DFA3_maxS);
    static final short[] DFA3_accept = DFA.unpackEncodedString(DFA3_acceptS);
    static final short[] DFA3_special = DFA.unpackEncodedString(DFA3_specialS);
    static final short[][] DFA3_transition;

    static {
        int numStates = DFA3_transitionS.length;
        DFA3_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA3_transition[i] = DFA.unpackEncodedString(DFA3_transitionS[i]);
        }
    }

    class DFA3 extends DFA {

        public DFA3(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 3;
            this.eot = DFA3_eot;
            this.eof = DFA3_eof;
            this.min = DFA3_min;
            this.max = DFA3_max;
            this.accept = DFA3_accept;
            this.special = DFA3_special;
            this.transition = DFA3_transition;
        }
        public String getDescription() {
            return "182:1: ( ( (lv_interface_0_0= ruleInterface ) ) | ( (lv_method_1_0= ruleMethod ) ) | ( (lv_property_2_0= ruleProperty ) ) | ( (lv_constant_3_0= ruleConstant ) ) | ( (lv_table_4_0= ruleTable ) ) )";
        }
    }


    public static final BitSet FOLLOW_ruleComponent_in_entryRuleComponent75 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleComponent85 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDocumentation_in_ruleComponent131 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleComponent144 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleComponent161 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_13_in_ruleComponent178 = new BitSet(new long[]{0x0000000000000190L});
    public static final BitSet FOLLOW_ruleString_Value_in_ruleComponent199 = new BitSet(new long[]{0x0000078000000042L});
    public static final BitSet FOLLOW_ruleContent_in_ruleComponent220 = new BitSet(new long[]{0x0000078000000042L});
    public static final BitSet FOLLOW_ruleContent_in_entryRuleContent257 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleContent267 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleInterface_in_ruleContent313 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMethod_in_ruleContent340 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleProperty_in_ruleContent367 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleConstant_in_ruleContent394 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTable_in_ruleContent421 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTable_in_entryRuleTable457 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTable467 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDocumentation_in_ruleTable513 = new BitSet(new long[]{0x0000038000000040L});
    public static final BitSet FOLLOW_ruleAccessSpecifier_in_ruleTable535 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_14_in_ruleTable547 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleTable564 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_15_in_ruleTable581 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_ruleTable593 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleTable610 = new BitSet(new long[]{0x0000000000060000L});
    public static final BitSet FOLLOW_17_in_ruleTable628 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_15_in_ruleTable640 = new BitSet(new long[]{0x0000000000040010L});
    public static final BitSet FOLLOW_ruleAttribute_in_ruleTable661 = new BitSet(new long[]{0x0000000000040010L});
    public static final BitSet FOLLOW_18_in_ruleTable674 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_18_in_ruleTable688 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleConstant_in_entryRuleConstant724 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleConstant734 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDocumentation_in_ruleConstant780 = new BitSet(new long[]{0x0000038000000040L});
    public static final BitSet FOLLOW_ruleAccessSpecifier_in_ruleConstant802 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_19_in_ruleConstant814 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleConstant831 = new BitSet(new long[]{0x0000000000500000L});
    public static final BitSet FOLLOW_20_in_ruleConstant849 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleConstant866 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_21_in_ruleConstant883 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_22_in_ruleConstant897 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_RULE_INT_in_ruleConstant914 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleProperty_in_entryRuleProperty955 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleProperty965 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDocumentation_in_ruleProperty1011 = new BitSet(new long[]{0x0000038000000040L});
    public static final BitSet FOLLOW_ruleAccessSpecifier_in_ruleProperty1033 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_23_in_ruleProperty1045 = new BitSet(new long[]{0x0000006000000000L});
    public static final BitSet FOLLOW_ruleReadWrite_in_ruleProperty1066 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleProperty1083 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_24_in_ruleProperty1100 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_15_in_ruleProperty1112 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_25_in_ruleProperty1124 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleJBC_ID_in_ruleProperty1145 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_26_in_ruleProperty1157 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleJBC_ID_in_ruleProperty1178 = new BitSet(new long[]{0x0000000000140000L});
    public static final BitSet FOLLOW_20_in_ruleProperty1197 = new BitSet(new long[]{0x0000000000200020L});
    public static final BitSet FOLLOW_RULE_INT_in_ruleProperty1227 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_21_in_ruleProperty1245 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_18_in_ruleProperty1259 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMethod_in_entryRuleMethod1295 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMethod1305 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDocumentation_in_ruleMethod1351 = new BitSet(new long[]{0x0000078000000040L});
    public static final BitSet FOLLOW_ruleMethodAccessSpecifier_in_ruleMethod1373 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_27_in_ruleMethod1385 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleMethod1402 = new BitSet(new long[]{0x0000000040100000L});
    public static final BitSet FOLLOW_ruleMethodAnnotation_in_ruleMethod1428 = new BitSet(new long[]{0x0000000040100000L});
    public static final BitSet FOLLOW_20_in_ruleMethod1441 = new BitSet(new long[]{0x0000380000200050L});
    public static final BitSet FOLLOW_ruleArgument_in_ruleMethod1463 = new BitSet(new long[]{0x0000000010200000L});
    public static final BitSet FOLLOW_28_in_ruleMethod1476 = new BitSet(new long[]{0x0000380000000050L});
    public static final BitSet FOLLOW_ruleArgument_in_ruleMethod1497 = new BitSet(new long[]{0x0000000010200000L});
    public static final BitSet FOLLOW_21_in_ruleMethod1513 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_15_in_ruleMethod1526 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_25_in_ruleMethod1538 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleJBC_ID_in_ruleMethod1559 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_18_in_ruleMethod1571 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleInterface_in_entryRuleInterface1609 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleInterface1619 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDocumentation_in_ruleInterface1665 = new BitSet(new long[]{0x0000038000000040L});
    public static final BitSet FOLLOW_ruleAccessSpecifier_in_ruleInterface1687 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_29_in_ruleInterface1699 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleInterface1716 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_20_in_ruleInterface1733 = new BitSet(new long[]{0x0000380000200050L});
    public static final BitSet FOLLOW_ruleArgument_in_ruleInterface1755 = new BitSet(new long[]{0x0000000010200000L});
    public static final BitSet FOLLOW_28_in_ruleInterface1768 = new BitSet(new long[]{0x0000380000000050L});
    public static final BitSet FOLLOW_ruleArgument_in_ruleInterface1789 = new BitSet(new long[]{0x0000000010200000L});
    public static final BitSet FOLLOW_21_in_ruleInterface1805 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAttribute_in_entryRuleAttribute1841 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAttribute1851 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleAttribute1893 = new BitSet(new long[]{0x0000000000500000L});
    public static final BitSet FOLLOW_20_in_ruleAttribute1911 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleAttribute1928 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_21_in_ruleAttribute1945 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_22_in_ruleAttribute1959 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_RULE_INT_in_ruleAttribute1976 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleArgument_in_entryRuleArgument2017 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleArgument2027 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDocumentation_in_ruleArgument2073 = new BitSet(new long[]{0x0000380000000010L});
    public static final BitSet FOLLOW_ruleInOutType_in_ruleArgument2095 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleArgument2113 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ruleEntityRef_in_ruleArgument2141 = new BitSet(new long[]{0x0003C00000000002L});
    public static final BitSet FOLLOW_ruleMultiplicity_in_ruleArgument2162 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDocumentation_in_entryRuleDocumentation2200 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleDocumentation2211 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ML_DOC_in_ruleDocumentation2250 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleMethodAnnotation_in_entryRuleMethodAnnotation2294 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleMethodAnnotation2304 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_30_in_ruleMethodAnnotation2341 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_31_in_ruleMethodAnnotation2353 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_32_in_ruleMethodAnnotation2365 = new BitSet(new long[]{0x0000001C00000000L});
    public static final BitSet FOLLOW_ruleT24MethodStereotype_in_ruleMethodAnnotation2386 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleEntityRef_in_entryRuleEntityRef2423 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleEntityRef2434 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleEntityRef2474 = new BitSet(new long[]{0x0000000100000002L});
    public static final BitSet FOLLOW_32_in_ruleEntityRef2493 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleEntityRef2508 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleJBC_ID_in_entryRuleJBC_ID2556 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleJBC_ID2567 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleJBC_ID2607 = new BitSet(new long[]{0x0000000200000002L});
    public static final BitSet FOLLOW_33_in_ruleJBC_ID2626 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleJBC_ID2641 = new BitSet(new long[]{0x0000000200000002L});
    public static final BitSet FOLLOW_ruleString_Value_in_entryRuleString_Value2689 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleString_Value2700 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleString_Value2740 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleString_Value2766 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_VALUE_in_ruleString_Value2792 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_34_in_ruleT24MethodStereotype2851 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_35_in_ruleT24MethodStereotype2868 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_36_in_ruleT24MethodStereotype2885 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_37_in_ruleReadWrite2930 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_38_in_ruleReadWrite2947 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_39_in_ruleAccessSpecifier2992 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_40_in_ruleAccessSpecifier3009 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_41_in_ruleAccessSpecifier3026 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_39_in_ruleMethodAccessSpecifier3071 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_40_in_ruleMethodAccessSpecifier3088 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_41_in_ruleMethodAccessSpecifier3105 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_42_in_ruleMethodAccessSpecifier3122 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_43_in_ruleInOutType3167 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_44_in_ruleInOutType3184 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_45_in_ruleInOutType3201 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_46_in_ruleMultiplicity3246 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_47_in_ruleMultiplicity3263 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_48_in_ruleMultiplicity3280 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_49_in_ruleMultiplicity3297 = new BitSet(new long[]{0x0000000000000002L});

}
package com.odcgroup.t24.version.parser.antlr.internal; 

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
import com.odcgroup.t24.version.services.VersionDSLGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalVersionDSLParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_STRING", "RULE_ID", "RULE_INT", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'Screen'", "','", "'t24Name:'", "'metamodelVersion:'", "'group:'", "'numberOfAuthorisers:'", "'description:'", "'TransactionFlow'", "'{'", "'exceptionProcessing:'", "'interfaceException:'", "'businessDayControl:'", "'keySequence:'", "';'", "'otherCompanyAccess:'", "'autoCompanyChange:'", "'overrideApproval:'", "'dealSlips:'", "'dealSlipsTrigger:'", "'dealSlipStyleSheet:'", "'}'", "'Presentation'", "'recordsPerPage:'", "'fieldsPerLine:'", "'initialCursorPosition:'", "'browserToolbar:'", "'language:'", "'header1:'", "'header2:'", "'header:'", "'footer:'", "'Relationship'", "'nextVersion:'", "'nextVersionFunction:'", "'nextVersionTransactionReference:'", "'AUTO'", "'associatedVersions:'", "'API'", "'includeVersionControl:'", "'authorizationRoutines:'", "'authorizationRoutinesAfterCommit:'", "'inputRoutines:'", "'inputRoutinesAfterCommit:'", "'keyValidationRoutines:'", "'preProcessValidationRoutines:'", "'webValidationRoutines:'", "'IB'", "'confirmVersion:'", "'previewVersion:'", "'challengeResponse:'", "'attributes:'", "'WebServices'", "'publish:'", "'activity:'", "'function:'", "'names:'", "'Connect'", "'generateIFP:'", "'associatedVersionsPresentationPattern:'", "'fieldsLayoutPattern:'", "'Fields'", "'displayType:'", "'inputBehaviour:'", "'caseConvention:'", "'maxLength:'", "'enrichmentLength:'", "'expansion:'", "'rightAdjust:'", "'enrichment:'", "'column:'", "'row:'", "'mandatory:'", "'RekeyRequired:'", "'hyperlink:'", "'hotValidate:'", "'hotField:'", "'webValidate:'", "'selectionEnquiry:'", "'enquiryParameter:'", "'popupBehaviour:'", "'default:'", "'OR'", "'Translations:'", "'label:'", "'toolTip:'", "'validation-routines:'", "'Attributes:'", "'MV:'", "'SV:'", "':'", "'IF'", "'('", "'-'", "')'", "'THEN'", "'@'", "'jBC:'", "'java:'", "'format:'", "'dealSlipFunction: '", "'NULL'", "'Yes'", "'No'", "'None'", "'Calendar'", "'Calculator'", "'RATE CONTROL'", "'Recurrence'", "'Lowercase'", "'Uppercase'", "'Proper_Case'", "'NoDisplay'", "'Combobox'", "'Vertical.Options'", "'Toggle'", "'DropDown.NoInput'", "'I'", "'A'", "'C'", "'R'", "'D'", "'H'", "'Finish'", "'OL'", "'RQ'", "'Normal'", "'Restricted'", "'Closed'", "'Input'", "'Authorise'", "'Reverse'", "'See'", "'Copy'", "'Delete'", "'HistoryRestore'", "'Verify'", "'AuditorReview'", "'NoChange'", "'NoInput'", "'Tabs'", "'Vertical'", "'Accordions'", "'OneRow'", "'OneColumn'", "'TwoColumnHorizontal'", "'TwoColumnVertical'", "'ThreeColumnHorizontal'", "'ThreeColumnVertical'", "'='"
    };
    public static final int RULE_ID=5;
    public static final int T__29=29;
    public static final int T__28=28;
    public static final int T__159=159;
    public static final int T__27=27;
    public static final int T__158=158;
    public static final int T__26=26;
    public static final int T__25=25;
    public static final int T__24=24;
    public static final int T__23=23;
    public static final int T__22=22;
    public static final int RULE_ANY_OTHER=10;
    public static final int T__21=21;
    public static final int T__20=20;
    public static final int EOF=-1;
    public static final int T__93=93;
    public static final int T__19=19;
    public static final int T__94=94;
    public static final int T__91=91;
    public static final int T__92=92;
    public static final int T__148=148;
    public static final int T__16=16;
    public static final int T__147=147;
    public static final int T__15=15;
    public static final int T__90=90;
    public static final int T__18=18;
    public static final int T__149=149;
    public static final int T__17=17;
    public static final int T__12=12;
    public static final int T__11=11;
    public static final int T__14=14;
    public static final int T__13=13;
    public static final int T__154=154;
    public static final int T__155=155;
    public static final int T__156=156;
    public static final int T__157=157;
    public static final int T__99=99;
    public static final int T__150=150;
    public static final int T__98=98;
    public static final int T__151=151;
    public static final int T__97=97;
    public static final int T__152=152;
    public static final int T__96=96;
    public static final int T__153=153;
    public static final int T__95=95;
    public static final int T__139=139;
    public static final int T__138=138;
    public static final int T__137=137;
    public static final int T__136=136;
    public static final int T__80=80;
    public static final int T__81=81;
    public static final int T__82=82;
    public static final int T__83=83;
    public static final int T__141=141;
    public static final int T__85=85;
    public static final int T__142=142;
    public static final int T__84=84;
    public static final int T__87=87;
    public static final int T__140=140;
    public static final int T__86=86;
    public static final int T__145=145;
    public static final int T__89=89;
    public static final int T__146=146;
    public static final int T__88=88;
    public static final int RULE_ML_COMMENT=7;
    public static final int T__143=143;
    public static final int T__144=144;
    public static final int T__126=126;
    public static final int T__125=125;
    public static final int T__128=128;
    public static final int RULE_STRING=4;
    public static final int T__127=127;
    public static final int T__71=71;
    public static final int T__129=129;
    public static final int T__72=72;
    public static final int T__70=70;
    public static final int T__76=76;
    public static final int T__75=75;
    public static final int T__130=130;
    public static final int T__74=74;
    public static final int T__131=131;
    public static final int T__73=73;
    public static final int T__132=132;
    public static final int T__133=133;
    public static final int T__79=79;
    public static final int T__134=134;
    public static final int T__78=78;
    public static final int T__135=135;
    public static final int T__77=77;
    public static final int T__68=68;
    public static final int T__69=69;
    public static final int T__66=66;
    public static final int T__67=67;
    public static final int T__64=64;
    public static final int T__65=65;
    public static final int T__62=62;
    public static final int T__63=63;
    public static final int T__118=118;
    public static final int T__119=119;
    public static final int T__116=116;
    public static final int T__117=117;
    public static final int T__114=114;
    public static final int T__115=115;
    public static final int T__124=124;
    public static final int T__123=123;
    public static final int T__122=122;
    public static final int T__121=121;
    public static final int T__120=120;
    public static final int T__61=61;
    public static final int T__60=60;
    public static final int T__55=55;
    public static final int T__56=56;
    public static final int T__57=57;
    public static final int T__58=58;
    public static final int T__51=51;
    public static final int T__52=52;
    public static final int T__53=53;
    public static final int T__54=54;
    public static final int T__107=107;
    public static final int T__108=108;
    public static final int T__109=109;
    public static final int T__103=103;
    public static final int T__59=59;
    public static final int T__104=104;
    public static final int T__105=105;
    public static final int T__106=106;
    public static final int T__111=111;
    public static final int T__110=110;
    public static final int RULE_INT=6;
    public static final int T__113=113;
    public static final int T__112=112;
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
    public static final int T__102=102;
    public static final int T__101=101;
    public static final int T__100=100;
    public static final int RULE_SL_COMMENT=8;
    public static final int T__30=30;
    public static final int T__31=31;
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


        public InternalVersionDSLParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalVersionDSLParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalVersionDSLParser.tokenNames; }
    public String getGrammarFileName() { return "../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g"; }



     	private VersionDSLGrammarAccess grammarAccess;
     	
        public InternalVersionDSLParser(TokenStream input, VersionDSLGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }
        
        @Override
        protected String getFirstRuleName() {
        	return "Version";	
       	}
       	
       	@Override
       	protected VersionDSLGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}



    // $ANTLR start "entryRuleVersion"
    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:68:1: entryRuleVersion returns [EObject current=null] : iv_ruleVersion= ruleVersion EOF ;
    public final EObject entryRuleVersion() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVersion = null;


        try {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:69:2: (iv_ruleVersion= ruleVersion EOF )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:70:2: iv_ruleVersion= ruleVersion EOF
            {
             newCompositeNode(grammarAccess.getVersionRule()); 
            pushFollow(FOLLOW_ruleVersion_in_entryRuleVersion75);
            iv_ruleVersion=ruleVersion();

            state._fsp--;

             current =iv_ruleVersion; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleVersion85); 

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
    // $ANTLR end "entryRuleVersion"


    // $ANTLR start "ruleVersion"
    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:77:1: ruleVersion returns [EObject current=null] : (otherlv_0= 'Screen' ( ( ruleEntityRef ) ) otherlv_2= ',' ( (lv_shortName_3_0= ruleNID ) )? (otherlv_4= 't24Name:' ( (lv_t24Name_5_0= RULE_STRING ) ) )? (otherlv_6= 'metamodelVersion:' ( (lv_metamodelVersion_7_0= RULE_STRING ) ) )? (otherlv_8= 'group:' ( (lv_group_9_0= RULE_STRING ) ) )? (otherlv_10= 'numberOfAuthorisers:' ( (lv_numberOfAuthorisers_11_0= ruleINTEGER_OBJECT ) ) )? (otherlv_12= 'description:' ( (lv_description_13_0= ruleTranslations ) ) )? (otherlv_14= 'TransactionFlow' otherlv_15= '{' (otherlv_16= 'exceptionProcessing:' ( (lv_exceptionProcessing_17_0= ruleINTEGER_OBJECT ) ) )? (otherlv_18= 'interfaceException:' ( (lv_interfaceException_19_0= ruleINTEGER_OBJECT ) ) )? (otherlv_20= 'businessDayControl:' ( (lv_businessDayControl_21_0= ruleBusinessDayControl ) ) )? (otherlv_22= 'keySequence:' ( (lv_keySequence_23_0= RULE_STRING ) ) (otherlv_24= ';' ( (lv_keySequence_25_0= RULE_STRING ) ) )* )? (otherlv_26= 'otherCompanyAccess:' ( (lv_otherCompanyAccess_27_0= ruleYesNo ) ) )? (otherlv_28= 'autoCompanyChange:' ( (lv_autoCompanyChange_29_0= ruleYesNo ) ) )? (otherlv_30= 'overrideApproval:' ( (lv_overrideApproval_31_0= ruleYesNo ) ) )? (otherlv_32= 'dealSlips:' ( (lv_dealSlipFormats_33_0= ruleDealSlip ) ) (otherlv_34= ';' ( (lv_dealSlipFormats_35_0= ruleDealSlip ) ) )* )? (otherlv_36= 'dealSlipsTrigger:' ( (lv_dealSlipTrigger_37_0= ruleDealSlipTrigger ) ) )? (otherlv_38= 'dealSlipStyleSheet:' ( (lv_dealSlipStyleSheet_39_0= RULE_STRING ) ) )? otherlv_40= '}' )? (otherlv_41= 'Presentation' otherlv_42= '{' (otherlv_43= 'recordsPerPage:' ( (lv_recordsPerPage_44_0= RULE_STRING ) ) )? (otherlv_45= 'fieldsPerLine:' ( (lv_fieldsPerLine_46_0= RULE_STRING ) ) )? (otherlv_47= 'initialCursorPosition:' ( (lv_initialCursorPosition_48_0= RULE_STRING ) ) )? (otherlv_49= 'browserToolbar:' ( (lv_browserToolbar_50_0= RULE_STRING ) ) )? (otherlv_51= 'language:' ( (lv_languageLocale_52_0= ruleNID ) ) (otherlv_53= ';' ( (lv_languageLocale_54_0= ruleNID ) ) )* )? (otherlv_55= 'header1:' ( (lv_header1_56_0= ruleTranslations ) ) )? (otherlv_57= 'header2:' ( (lv_header2_58_0= ruleTranslations ) ) )? (otherlv_59= 'header:' ( (lv_header_60_0= ruleTranslations ) ) )? (otherlv_61= 'footer:' ( (lv_footer_62_0= ruleTranslations ) ) )? otherlv_63= '}' )? (otherlv_64= 'Relationship' otherlv_65= '{' (otherlv_66= 'nextVersion:' ( ( ruleVersionRef ) ) (otherlv_68= 'nextVersionFunction:' ( (lv_nextVersionFunction_69_0= ruleFunction ) ) )? (otherlv_70= 'nextVersionTransactionReference:' ( ( (lv_nextTransactionReference_71_1= 'AUTO' | lv_nextTransactionReference_71_2= RULE_STRING ) ) ) )? )? (otherlv_72= 'associatedVersions:' ( ( ruleVersionRef ) ) (otherlv_74= ';' ( ( ruleVersionRef ) ) )* )? otherlv_76= '}' )? (otherlv_77= 'API' otherlv_78= '{' (otherlv_79= 'includeVersionControl:' ( (lv_includeVersionControl_80_0= ruleYesNo ) ) )? (otherlv_81= 'authorizationRoutines:' ( (lv_authorizationRoutines_82_0= ruleRoutine ) ) (otherlv_83= ';' ( (lv_authorizationRoutines_84_0= ruleRoutine ) ) )* )? (otherlv_85= 'authorizationRoutinesAfterCommit:' ( (lv_authorizationRoutinesAfterCommit_86_0= ruleRoutine ) ) (otherlv_87= ';' ( (lv_authorizationRoutinesAfterCommit_88_0= ruleRoutine ) ) )* )? (otherlv_89= 'inputRoutines:' ( (lv_inputRoutines_90_0= ruleRoutine ) ) (otherlv_91= ';' ( (lv_inputRoutines_92_0= ruleRoutine ) ) )* )? (otherlv_93= 'inputRoutinesAfterCommit:' ( (lv_inputRoutinesAfterCommit_94_0= ruleRoutine ) ) (otherlv_95= ';' ( (lv_inputRoutinesAfterCommit_96_0= ruleRoutine ) ) )* )? (otherlv_97= 'keyValidationRoutines:' ( (lv_keyValidationRoutines_98_0= ruleRoutine ) ) (otherlv_99= ';' ( (lv_keyValidationRoutines_100_0= ruleRoutine ) ) )* )? (otherlv_101= 'preProcessValidationRoutines:' ( (lv_preProcessValidationRoutines_102_0= ruleRoutine ) ) (otherlv_103= ';' ( (lv_preProcessValidationRoutines_104_0= ruleRoutine ) ) )* )? (otherlv_105= 'webValidationRoutines:' ( (lv_webValidationRoutines_106_0= ruleRoutine ) ) (otherlv_107= ';' ( (lv_webValidationRoutines_108_0= ruleRoutine ) ) )* )? otherlv_109= '}' )? (otherlv_110= 'IB' otherlv_111= '{' (otherlv_112= 'confirmVersion:' ( ( ruleVersionRef ) ) )? (otherlv_114= 'previewVersion:' ( ( ruleVersionRef ) ) )? (otherlv_116= 'challengeResponse:' ( (lv_challengeResponse_117_0= RULE_STRING ) ) )? otherlv_118= '}' )? (otherlv_119= 'attributes:' ( (lv_attributes_120_0= RULE_STRING ) ) (otherlv_121= ';' ( (lv_attributes_122_0= RULE_STRING ) ) )* )? (otherlv_123= 'WebServices' otherlv_124= '{' (otherlv_125= 'publish:' ( (lv_publishWebService_126_0= ruleYesNo ) ) )? (otherlv_127= 'activity:' ( (lv_webServiceActivity_128_0= RULE_STRING ) ) )? (otherlv_129= 'function:' ( (lv_webServiceFunction_130_0= ruleFunction ) ) )? (otherlv_131= 'description:' ( (lv_webServiceDescription_132_0= RULE_STRING ) ) )? (otherlv_133= 'names:' ( (lv_webServiceNames_134_0= RULE_STRING ) ) (otherlv_135= ';' ( (lv_webServiceNames_136_0= RULE_STRING ) ) )* )? otherlv_137= '}' )? (otherlv_138= 'Connect' otherlv_139= '{' (otherlv_140= 'generateIFP:' ( (lv_generateIFP_141_0= ruleYesNo ) ) )? (otherlv_142= 'associatedVersionsPresentationPattern:' ( (lv_associatedVersionsPresentationPattern_143_0= ruleAssociatedVersionsPresentationPattern ) ) )? (otherlv_144= 'fieldsLayoutPattern:' ( (lv_fieldsLayoutPattern_145_0= ruleFieldsLayoutPattern ) ) )? otherlv_146= '}' )? otherlv_147= 'Fields' otherlv_148= '{' ( (lv_fields_149_0= ruleField ) )* otherlv_150= '}' ) ;
    public final EObject ruleVersion() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token lv_t24Name_5_0=null;
        Token otherlv_6=null;
        Token lv_metamodelVersion_7_0=null;
        Token otherlv_8=null;
        Token lv_group_9_0=null;
        Token otherlv_10=null;
        Token otherlv_12=null;
        Token otherlv_14=null;
        Token otherlv_15=null;
        Token otherlv_16=null;
        Token otherlv_18=null;
        Token otherlv_20=null;
        Token otherlv_22=null;
        Token lv_keySequence_23_0=null;
        Token otherlv_24=null;
        Token lv_keySequence_25_0=null;
        Token otherlv_26=null;
        Token otherlv_28=null;
        Token otherlv_30=null;
        Token otherlv_32=null;
        Token otherlv_34=null;
        Token otherlv_36=null;
        Token otherlv_38=null;
        Token lv_dealSlipStyleSheet_39_0=null;
        Token otherlv_40=null;
        Token otherlv_41=null;
        Token otherlv_42=null;
        Token otherlv_43=null;
        Token lv_recordsPerPage_44_0=null;
        Token otherlv_45=null;
        Token lv_fieldsPerLine_46_0=null;
        Token otherlv_47=null;
        Token lv_initialCursorPosition_48_0=null;
        Token otherlv_49=null;
        Token lv_browserToolbar_50_0=null;
        Token otherlv_51=null;
        Token otherlv_53=null;
        Token otherlv_55=null;
        Token otherlv_57=null;
        Token otherlv_59=null;
        Token otherlv_61=null;
        Token otherlv_63=null;
        Token otherlv_64=null;
        Token otherlv_65=null;
        Token otherlv_66=null;
        Token otherlv_68=null;
        Token otherlv_70=null;
        Token lv_nextTransactionReference_71_1=null;
        Token lv_nextTransactionReference_71_2=null;
        Token otherlv_72=null;
        Token otherlv_74=null;
        Token otherlv_76=null;
        Token otherlv_77=null;
        Token otherlv_78=null;
        Token otherlv_79=null;
        Token otherlv_81=null;
        Token otherlv_83=null;
        Token otherlv_85=null;
        Token otherlv_87=null;
        Token otherlv_89=null;
        Token otherlv_91=null;
        Token otherlv_93=null;
        Token otherlv_95=null;
        Token otherlv_97=null;
        Token otherlv_99=null;
        Token otherlv_101=null;
        Token otherlv_103=null;
        Token otherlv_105=null;
        Token otherlv_107=null;
        Token otherlv_109=null;
        Token otherlv_110=null;
        Token otherlv_111=null;
        Token otherlv_112=null;
        Token otherlv_114=null;
        Token otherlv_116=null;
        Token lv_challengeResponse_117_0=null;
        Token otherlv_118=null;
        Token otherlv_119=null;
        Token lv_attributes_120_0=null;
        Token otherlv_121=null;
        Token lv_attributes_122_0=null;
        Token otherlv_123=null;
        Token otherlv_124=null;
        Token otherlv_125=null;
        Token otherlv_127=null;
        Token lv_webServiceActivity_128_0=null;
        Token otherlv_129=null;
        Token otherlv_131=null;
        Token lv_webServiceDescription_132_0=null;
        Token otherlv_133=null;
        Token lv_webServiceNames_134_0=null;
        Token otherlv_135=null;
        Token lv_webServiceNames_136_0=null;
        Token otherlv_137=null;
        Token otherlv_138=null;
        Token otherlv_139=null;
        Token otherlv_140=null;
        Token otherlv_142=null;
        Token otherlv_144=null;
        Token otherlv_146=null;
        Token otherlv_147=null;
        Token otherlv_148=null;
        Token otherlv_150=null;
        AntlrDatatypeRuleToken lv_shortName_3_0 = null;

        AntlrDatatypeRuleToken lv_numberOfAuthorisers_11_0 = null;

        EObject lv_description_13_0 = null;

        AntlrDatatypeRuleToken lv_exceptionProcessing_17_0 = null;

        AntlrDatatypeRuleToken lv_interfaceException_19_0 = null;

        Enumerator lv_businessDayControl_21_0 = null;

        Enumerator lv_otherCompanyAccess_27_0 = null;

        Enumerator lv_autoCompanyChange_29_0 = null;

        Enumerator lv_overrideApproval_31_0 = null;

        EObject lv_dealSlipFormats_33_0 = null;

        EObject lv_dealSlipFormats_35_0 = null;

        Enumerator lv_dealSlipTrigger_37_0 = null;

        AntlrDatatypeRuleToken lv_languageLocale_52_0 = null;

        AntlrDatatypeRuleToken lv_languageLocale_54_0 = null;

        EObject lv_header1_56_0 = null;

        EObject lv_header2_58_0 = null;

        EObject lv_header_60_0 = null;

        EObject lv_footer_62_0 = null;

        Enumerator lv_nextVersionFunction_69_0 = null;

        Enumerator lv_includeVersionControl_80_0 = null;

        EObject lv_authorizationRoutines_82_0 = null;

        EObject lv_authorizationRoutines_84_0 = null;

        EObject lv_authorizationRoutinesAfterCommit_86_0 = null;

        EObject lv_authorizationRoutinesAfterCommit_88_0 = null;

        EObject lv_inputRoutines_90_0 = null;

        EObject lv_inputRoutines_92_0 = null;

        EObject lv_inputRoutinesAfterCommit_94_0 = null;

        EObject lv_inputRoutinesAfterCommit_96_0 = null;

        EObject lv_keyValidationRoutines_98_0 = null;

        EObject lv_keyValidationRoutines_100_0 = null;

        EObject lv_preProcessValidationRoutines_102_0 = null;

        EObject lv_preProcessValidationRoutines_104_0 = null;

        EObject lv_webValidationRoutines_106_0 = null;

        EObject lv_webValidationRoutines_108_0 = null;

        Enumerator lv_publishWebService_126_0 = null;

        Enumerator lv_webServiceFunction_130_0 = null;

        Enumerator lv_generateIFP_141_0 = null;

        Enumerator lv_associatedVersionsPresentationPattern_143_0 = null;

        Enumerator lv_fieldsLayoutPattern_145_0 = null;

        EObject lv_fields_149_0 = null;


         enterRule(); 
            
        try {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:80:28: ( (otherlv_0= 'Screen' ( ( ruleEntityRef ) ) otherlv_2= ',' ( (lv_shortName_3_0= ruleNID ) )? (otherlv_4= 't24Name:' ( (lv_t24Name_5_0= RULE_STRING ) ) )? (otherlv_6= 'metamodelVersion:' ( (lv_metamodelVersion_7_0= RULE_STRING ) ) )? (otherlv_8= 'group:' ( (lv_group_9_0= RULE_STRING ) ) )? (otherlv_10= 'numberOfAuthorisers:' ( (lv_numberOfAuthorisers_11_0= ruleINTEGER_OBJECT ) ) )? (otherlv_12= 'description:' ( (lv_description_13_0= ruleTranslations ) ) )? (otherlv_14= 'TransactionFlow' otherlv_15= '{' (otherlv_16= 'exceptionProcessing:' ( (lv_exceptionProcessing_17_0= ruleINTEGER_OBJECT ) ) )? (otherlv_18= 'interfaceException:' ( (lv_interfaceException_19_0= ruleINTEGER_OBJECT ) ) )? (otherlv_20= 'businessDayControl:' ( (lv_businessDayControl_21_0= ruleBusinessDayControl ) ) )? (otherlv_22= 'keySequence:' ( (lv_keySequence_23_0= RULE_STRING ) ) (otherlv_24= ';' ( (lv_keySequence_25_0= RULE_STRING ) ) )* )? (otherlv_26= 'otherCompanyAccess:' ( (lv_otherCompanyAccess_27_0= ruleYesNo ) ) )? (otherlv_28= 'autoCompanyChange:' ( (lv_autoCompanyChange_29_0= ruleYesNo ) ) )? (otherlv_30= 'overrideApproval:' ( (lv_overrideApproval_31_0= ruleYesNo ) ) )? (otherlv_32= 'dealSlips:' ( (lv_dealSlipFormats_33_0= ruleDealSlip ) ) (otherlv_34= ';' ( (lv_dealSlipFormats_35_0= ruleDealSlip ) ) )* )? (otherlv_36= 'dealSlipsTrigger:' ( (lv_dealSlipTrigger_37_0= ruleDealSlipTrigger ) ) )? (otherlv_38= 'dealSlipStyleSheet:' ( (lv_dealSlipStyleSheet_39_0= RULE_STRING ) ) )? otherlv_40= '}' )? (otherlv_41= 'Presentation' otherlv_42= '{' (otherlv_43= 'recordsPerPage:' ( (lv_recordsPerPage_44_0= RULE_STRING ) ) )? (otherlv_45= 'fieldsPerLine:' ( (lv_fieldsPerLine_46_0= RULE_STRING ) ) )? (otherlv_47= 'initialCursorPosition:' ( (lv_initialCursorPosition_48_0= RULE_STRING ) ) )? (otherlv_49= 'browserToolbar:' ( (lv_browserToolbar_50_0= RULE_STRING ) ) )? (otherlv_51= 'language:' ( (lv_languageLocale_52_0= ruleNID ) ) (otherlv_53= ';' ( (lv_languageLocale_54_0= ruleNID ) ) )* )? (otherlv_55= 'header1:' ( (lv_header1_56_0= ruleTranslations ) ) )? (otherlv_57= 'header2:' ( (lv_header2_58_0= ruleTranslations ) ) )? (otherlv_59= 'header:' ( (lv_header_60_0= ruleTranslations ) ) )? (otherlv_61= 'footer:' ( (lv_footer_62_0= ruleTranslations ) ) )? otherlv_63= '}' )? (otherlv_64= 'Relationship' otherlv_65= '{' (otherlv_66= 'nextVersion:' ( ( ruleVersionRef ) ) (otherlv_68= 'nextVersionFunction:' ( (lv_nextVersionFunction_69_0= ruleFunction ) ) )? (otherlv_70= 'nextVersionTransactionReference:' ( ( (lv_nextTransactionReference_71_1= 'AUTO' | lv_nextTransactionReference_71_2= RULE_STRING ) ) ) )? )? (otherlv_72= 'associatedVersions:' ( ( ruleVersionRef ) ) (otherlv_74= ';' ( ( ruleVersionRef ) ) )* )? otherlv_76= '}' )? (otherlv_77= 'API' otherlv_78= '{' (otherlv_79= 'includeVersionControl:' ( (lv_includeVersionControl_80_0= ruleYesNo ) ) )? (otherlv_81= 'authorizationRoutines:' ( (lv_authorizationRoutines_82_0= ruleRoutine ) ) (otherlv_83= ';' ( (lv_authorizationRoutines_84_0= ruleRoutine ) ) )* )? (otherlv_85= 'authorizationRoutinesAfterCommit:' ( (lv_authorizationRoutinesAfterCommit_86_0= ruleRoutine ) ) (otherlv_87= ';' ( (lv_authorizationRoutinesAfterCommit_88_0= ruleRoutine ) ) )* )? (otherlv_89= 'inputRoutines:' ( (lv_inputRoutines_90_0= ruleRoutine ) ) (otherlv_91= ';' ( (lv_inputRoutines_92_0= ruleRoutine ) ) )* )? (otherlv_93= 'inputRoutinesAfterCommit:' ( (lv_inputRoutinesAfterCommit_94_0= ruleRoutine ) ) (otherlv_95= ';' ( (lv_inputRoutinesAfterCommit_96_0= ruleRoutine ) ) )* )? (otherlv_97= 'keyValidationRoutines:' ( (lv_keyValidationRoutines_98_0= ruleRoutine ) ) (otherlv_99= ';' ( (lv_keyValidationRoutines_100_0= ruleRoutine ) ) )* )? (otherlv_101= 'preProcessValidationRoutines:' ( (lv_preProcessValidationRoutines_102_0= ruleRoutine ) ) (otherlv_103= ';' ( (lv_preProcessValidationRoutines_104_0= ruleRoutine ) ) )* )? (otherlv_105= 'webValidationRoutines:' ( (lv_webValidationRoutines_106_0= ruleRoutine ) ) (otherlv_107= ';' ( (lv_webValidationRoutines_108_0= ruleRoutine ) ) )* )? otherlv_109= '}' )? (otherlv_110= 'IB' otherlv_111= '{' (otherlv_112= 'confirmVersion:' ( ( ruleVersionRef ) ) )? (otherlv_114= 'previewVersion:' ( ( ruleVersionRef ) ) )? (otherlv_116= 'challengeResponse:' ( (lv_challengeResponse_117_0= RULE_STRING ) ) )? otherlv_118= '}' )? (otherlv_119= 'attributes:' ( (lv_attributes_120_0= RULE_STRING ) ) (otherlv_121= ';' ( (lv_attributes_122_0= RULE_STRING ) ) )* )? (otherlv_123= 'WebServices' otherlv_124= '{' (otherlv_125= 'publish:' ( (lv_publishWebService_126_0= ruleYesNo ) ) )? (otherlv_127= 'activity:' ( (lv_webServiceActivity_128_0= RULE_STRING ) ) )? (otherlv_129= 'function:' ( (lv_webServiceFunction_130_0= ruleFunction ) ) )? (otherlv_131= 'description:' ( (lv_webServiceDescription_132_0= RULE_STRING ) ) )? (otherlv_133= 'names:' ( (lv_webServiceNames_134_0= RULE_STRING ) ) (otherlv_135= ';' ( (lv_webServiceNames_136_0= RULE_STRING ) ) )* )? otherlv_137= '}' )? (otherlv_138= 'Connect' otherlv_139= '{' (otherlv_140= 'generateIFP:' ( (lv_generateIFP_141_0= ruleYesNo ) ) )? (otherlv_142= 'associatedVersionsPresentationPattern:' ( (lv_associatedVersionsPresentationPattern_143_0= ruleAssociatedVersionsPresentationPattern ) ) )? (otherlv_144= 'fieldsLayoutPattern:' ( (lv_fieldsLayoutPattern_145_0= ruleFieldsLayoutPattern ) ) )? otherlv_146= '}' )? otherlv_147= 'Fields' otherlv_148= '{' ( (lv_fields_149_0= ruleField ) )* otherlv_150= '}' ) )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:81:1: (otherlv_0= 'Screen' ( ( ruleEntityRef ) ) otherlv_2= ',' ( (lv_shortName_3_0= ruleNID ) )? (otherlv_4= 't24Name:' ( (lv_t24Name_5_0= RULE_STRING ) ) )? (otherlv_6= 'metamodelVersion:' ( (lv_metamodelVersion_7_0= RULE_STRING ) ) )? (otherlv_8= 'group:' ( (lv_group_9_0= RULE_STRING ) ) )? (otherlv_10= 'numberOfAuthorisers:' ( (lv_numberOfAuthorisers_11_0= ruleINTEGER_OBJECT ) ) )? (otherlv_12= 'description:' ( (lv_description_13_0= ruleTranslations ) ) )? (otherlv_14= 'TransactionFlow' otherlv_15= '{' (otherlv_16= 'exceptionProcessing:' ( (lv_exceptionProcessing_17_0= ruleINTEGER_OBJECT ) ) )? (otherlv_18= 'interfaceException:' ( (lv_interfaceException_19_0= ruleINTEGER_OBJECT ) ) )? (otherlv_20= 'businessDayControl:' ( (lv_businessDayControl_21_0= ruleBusinessDayControl ) ) )? (otherlv_22= 'keySequence:' ( (lv_keySequence_23_0= RULE_STRING ) ) (otherlv_24= ';' ( (lv_keySequence_25_0= RULE_STRING ) ) )* )? (otherlv_26= 'otherCompanyAccess:' ( (lv_otherCompanyAccess_27_0= ruleYesNo ) ) )? (otherlv_28= 'autoCompanyChange:' ( (lv_autoCompanyChange_29_0= ruleYesNo ) ) )? (otherlv_30= 'overrideApproval:' ( (lv_overrideApproval_31_0= ruleYesNo ) ) )? (otherlv_32= 'dealSlips:' ( (lv_dealSlipFormats_33_0= ruleDealSlip ) ) (otherlv_34= ';' ( (lv_dealSlipFormats_35_0= ruleDealSlip ) ) )* )? (otherlv_36= 'dealSlipsTrigger:' ( (lv_dealSlipTrigger_37_0= ruleDealSlipTrigger ) ) )? (otherlv_38= 'dealSlipStyleSheet:' ( (lv_dealSlipStyleSheet_39_0= RULE_STRING ) ) )? otherlv_40= '}' )? (otherlv_41= 'Presentation' otherlv_42= '{' (otherlv_43= 'recordsPerPage:' ( (lv_recordsPerPage_44_0= RULE_STRING ) ) )? (otherlv_45= 'fieldsPerLine:' ( (lv_fieldsPerLine_46_0= RULE_STRING ) ) )? (otherlv_47= 'initialCursorPosition:' ( (lv_initialCursorPosition_48_0= RULE_STRING ) ) )? (otherlv_49= 'browserToolbar:' ( (lv_browserToolbar_50_0= RULE_STRING ) ) )? (otherlv_51= 'language:' ( (lv_languageLocale_52_0= ruleNID ) ) (otherlv_53= ';' ( (lv_languageLocale_54_0= ruleNID ) ) )* )? (otherlv_55= 'header1:' ( (lv_header1_56_0= ruleTranslations ) ) )? (otherlv_57= 'header2:' ( (lv_header2_58_0= ruleTranslations ) ) )? (otherlv_59= 'header:' ( (lv_header_60_0= ruleTranslations ) ) )? (otherlv_61= 'footer:' ( (lv_footer_62_0= ruleTranslations ) ) )? otherlv_63= '}' )? (otherlv_64= 'Relationship' otherlv_65= '{' (otherlv_66= 'nextVersion:' ( ( ruleVersionRef ) ) (otherlv_68= 'nextVersionFunction:' ( (lv_nextVersionFunction_69_0= ruleFunction ) ) )? (otherlv_70= 'nextVersionTransactionReference:' ( ( (lv_nextTransactionReference_71_1= 'AUTO' | lv_nextTransactionReference_71_2= RULE_STRING ) ) ) )? )? (otherlv_72= 'associatedVersions:' ( ( ruleVersionRef ) ) (otherlv_74= ';' ( ( ruleVersionRef ) ) )* )? otherlv_76= '}' )? (otherlv_77= 'API' otherlv_78= '{' (otherlv_79= 'includeVersionControl:' ( (lv_includeVersionControl_80_0= ruleYesNo ) ) )? (otherlv_81= 'authorizationRoutines:' ( (lv_authorizationRoutines_82_0= ruleRoutine ) ) (otherlv_83= ';' ( (lv_authorizationRoutines_84_0= ruleRoutine ) ) )* )? (otherlv_85= 'authorizationRoutinesAfterCommit:' ( (lv_authorizationRoutinesAfterCommit_86_0= ruleRoutine ) ) (otherlv_87= ';' ( (lv_authorizationRoutinesAfterCommit_88_0= ruleRoutine ) ) )* )? (otherlv_89= 'inputRoutines:' ( (lv_inputRoutines_90_0= ruleRoutine ) ) (otherlv_91= ';' ( (lv_inputRoutines_92_0= ruleRoutine ) ) )* )? (otherlv_93= 'inputRoutinesAfterCommit:' ( (lv_inputRoutinesAfterCommit_94_0= ruleRoutine ) ) (otherlv_95= ';' ( (lv_inputRoutinesAfterCommit_96_0= ruleRoutine ) ) )* )? (otherlv_97= 'keyValidationRoutines:' ( (lv_keyValidationRoutines_98_0= ruleRoutine ) ) (otherlv_99= ';' ( (lv_keyValidationRoutines_100_0= ruleRoutine ) ) )* )? (otherlv_101= 'preProcessValidationRoutines:' ( (lv_preProcessValidationRoutines_102_0= ruleRoutine ) ) (otherlv_103= ';' ( (lv_preProcessValidationRoutines_104_0= ruleRoutine ) ) )* )? (otherlv_105= 'webValidationRoutines:' ( (lv_webValidationRoutines_106_0= ruleRoutine ) ) (otherlv_107= ';' ( (lv_webValidationRoutines_108_0= ruleRoutine ) ) )* )? otherlv_109= '}' )? (otherlv_110= 'IB' otherlv_111= '{' (otherlv_112= 'confirmVersion:' ( ( ruleVersionRef ) ) )? (otherlv_114= 'previewVersion:' ( ( ruleVersionRef ) ) )? (otherlv_116= 'challengeResponse:' ( (lv_challengeResponse_117_0= RULE_STRING ) ) )? otherlv_118= '}' )? (otherlv_119= 'attributes:' ( (lv_attributes_120_0= RULE_STRING ) ) (otherlv_121= ';' ( (lv_attributes_122_0= RULE_STRING ) ) )* )? (otherlv_123= 'WebServices' otherlv_124= '{' (otherlv_125= 'publish:' ( (lv_publishWebService_126_0= ruleYesNo ) ) )? (otherlv_127= 'activity:' ( (lv_webServiceActivity_128_0= RULE_STRING ) ) )? (otherlv_129= 'function:' ( (lv_webServiceFunction_130_0= ruleFunction ) ) )? (otherlv_131= 'description:' ( (lv_webServiceDescription_132_0= RULE_STRING ) ) )? (otherlv_133= 'names:' ( (lv_webServiceNames_134_0= RULE_STRING ) ) (otherlv_135= ';' ( (lv_webServiceNames_136_0= RULE_STRING ) ) )* )? otherlv_137= '}' )? (otherlv_138= 'Connect' otherlv_139= '{' (otherlv_140= 'generateIFP:' ( (lv_generateIFP_141_0= ruleYesNo ) ) )? (otherlv_142= 'associatedVersionsPresentationPattern:' ( (lv_associatedVersionsPresentationPattern_143_0= ruleAssociatedVersionsPresentationPattern ) ) )? (otherlv_144= 'fieldsLayoutPattern:' ( (lv_fieldsLayoutPattern_145_0= ruleFieldsLayoutPattern ) ) )? otherlv_146= '}' )? otherlv_147= 'Fields' otherlv_148= '{' ( (lv_fields_149_0= ruleField ) )* otherlv_150= '}' )
            {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:81:1: (otherlv_0= 'Screen' ( ( ruleEntityRef ) ) otherlv_2= ',' ( (lv_shortName_3_0= ruleNID ) )? (otherlv_4= 't24Name:' ( (lv_t24Name_5_0= RULE_STRING ) ) )? (otherlv_6= 'metamodelVersion:' ( (lv_metamodelVersion_7_0= RULE_STRING ) ) )? (otherlv_8= 'group:' ( (lv_group_9_0= RULE_STRING ) ) )? (otherlv_10= 'numberOfAuthorisers:' ( (lv_numberOfAuthorisers_11_0= ruleINTEGER_OBJECT ) ) )? (otherlv_12= 'description:' ( (lv_description_13_0= ruleTranslations ) ) )? (otherlv_14= 'TransactionFlow' otherlv_15= '{' (otherlv_16= 'exceptionProcessing:' ( (lv_exceptionProcessing_17_0= ruleINTEGER_OBJECT ) ) )? (otherlv_18= 'interfaceException:' ( (lv_interfaceException_19_0= ruleINTEGER_OBJECT ) ) )? (otherlv_20= 'businessDayControl:' ( (lv_businessDayControl_21_0= ruleBusinessDayControl ) ) )? (otherlv_22= 'keySequence:' ( (lv_keySequence_23_0= RULE_STRING ) ) (otherlv_24= ';' ( (lv_keySequence_25_0= RULE_STRING ) ) )* )? (otherlv_26= 'otherCompanyAccess:' ( (lv_otherCompanyAccess_27_0= ruleYesNo ) ) )? (otherlv_28= 'autoCompanyChange:' ( (lv_autoCompanyChange_29_0= ruleYesNo ) ) )? (otherlv_30= 'overrideApproval:' ( (lv_overrideApproval_31_0= ruleYesNo ) ) )? (otherlv_32= 'dealSlips:' ( (lv_dealSlipFormats_33_0= ruleDealSlip ) ) (otherlv_34= ';' ( (lv_dealSlipFormats_35_0= ruleDealSlip ) ) )* )? (otherlv_36= 'dealSlipsTrigger:' ( (lv_dealSlipTrigger_37_0= ruleDealSlipTrigger ) ) )? (otherlv_38= 'dealSlipStyleSheet:' ( (lv_dealSlipStyleSheet_39_0= RULE_STRING ) ) )? otherlv_40= '}' )? (otherlv_41= 'Presentation' otherlv_42= '{' (otherlv_43= 'recordsPerPage:' ( (lv_recordsPerPage_44_0= RULE_STRING ) ) )? (otherlv_45= 'fieldsPerLine:' ( (lv_fieldsPerLine_46_0= RULE_STRING ) ) )? (otherlv_47= 'initialCursorPosition:' ( (lv_initialCursorPosition_48_0= RULE_STRING ) ) )? (otherlv_49= 'browserToolbar:' ( (lv_browserToolbar_50_0= RULE_STRING ) ) )? (otherlv_51= 'language:' ( (lv_languageLocale_52_0= ruleNID ) ) (otherlv_53= ';' ( (lv_languageLocale_54_0= ruleNID ) ) )* )? (otherlv_55= 'header1:' ( (lv_header1_56_0= ruleTranslations ) ) )? (otherlv_57= 'header2:' ( (lv_header2_58_0= ruleTranslations ) ) )? (otherlv_59= 'header:' ( (lv_header_60_0= ruleTranslations ) ) )? (otherlv_61= 'footer:' ( (lv_footer_62_0= ruleTranslations ) ) )? otherlv_63= '}' )? (otherlv_64= 'Relationship' otherlv_65= '{' (otherlv_66= 'nextVersion:' ( ( ruleVersionRef ) ) (otherlv_68= 'nextVersionFunction:' ( (lv_nextVersionFunction_69_0= ruleFunction ) ) )? (otherlv_70= 'nextVersionTransactionReference:' ( ( (lv_nextTransactionReference_71_1= 'AUTO' | lv_nextTransactionReference_71_2= RULE_STRING ) ) ) )? )? (otherlv_72= 'associatedVersions:' ( ( ruleVersionRef ) ) (otherlv_74= ';' ( ( ruleVersionRef ) ) )* )? otherlv_76= '}' )? (otherlv_77= 'API' otherlv_78= '{' (otherlv_79= 'includeVersionControl:' ( (lv_includeVersionControl_80_0= ruleYesNo ) ) )? (otherlv_81= 'authorizationRoutines:' ( (lv_authorizationRoutines_82_0= ruleRoutine ) ) (otherlv_83= ';' ( (lv_authorizationRoutines_84_0= ruleRoutine ) ) )* )? (otherlv_85= 'authorizationRoutinesAfterCommit:' ( (lv_authorizationRoutinesAfterCommit_86_0= ruleRoutine ) ) (otherlv_87= ';' ( (lv_authorizationRoutinesAfterCommit_88_0= ruleRoutine ) ) )* )? (otherlv_89= 'inputRoutines:' ( (lv_inputRoutines_90_0= ruleRoutine ) ) (otherlv_91= ';' ( (lv_inputRoutines_92_0= ruleRoutine ) ) )* )? (otherlv_93= 'inputRoutinesAfterCommit:' ( (lv_inputRoutinesAfterCommit_94_0= ruleRoutine ) ) (otherlv_95= ';' ( (lv_inputRoutinesAfterCommit_96_0= ruleRoutine ) ) )* )? (otherlv_97= 'keyValidationRoutines:' ( (lv_keyValidationRoutines_98_0= ruleRoutine ) ) (otherlv_99= ';' ( (lv_keyValidationRoutines_100_0= ruleRoutine ) ) )* )? (otherlv_101= 'preProcessValidationRoutines:' ( (lv_preProcessValidationRoutines_102_0= ruleRoutine ) ) (otherlv_103= ';' ( (lv_preProcessValidationRoutines_104_0= ruleRoutine ) ) )* )? (otherlv_105= 'webValidationRoutines:' ( (lv_webValidationRoutines_106_0= ruleRoutine ) ) (otherlv_107= ';' ( (lv_webValidationRoutines_108_0= ruleRoutine ) ) )* )? otherlv_109= '}' )? (otherlv_110= 'IB' otherlv_111= '{' (otherlv_112= 'confirmVersion:' ( ( ruleVersionRef ) ) )? (otherlv_114= 'previewVersion:' ( ( ruleVersionRef ) ) )? (otherlv_116= 'challengeResponse:' ( (lv_challengeResponse_117_0= RULE_STRING ) ) )? otherlv_118= '}' )? (otherlv_119= 'attributes:' ( (lv_attributes_120_0= RULE_STRING ) ) (otherlv_121= ';' ( (lv_attributes_122_0= RULE_STRING ) ) )* )? (otherlv_123= 'WebServices' otherlv_124= '{' (otherlv_125= 'publish:' ( (lv_publishWebService_126_0= ruleYesNo ) ) )? (otherlv_127= 'activity:' ( (lv_webServiceActivity_128_0= RULE_STRING ) ) )? (otherlv_129= 'function:' ( (lv_webServiceFunction_130_0= ruleFunction ) ) )? (otherlv_131= 'description:' ( (lv_webServiceDescription_132_0= RULE_STRING ) ) )? (otherlv_133= 'names:' ( (lv_webServiceNames_134_0= RULE_STRING ) ) (otherlv_135= ';' ( (lv_webServiceNames_136_0= RULE_STRING ) ) )* )? otherlv_137= '}' )? (otherlv_138= 'Connect' otherlv_139= '{' (otherlv_140= 'generateIFP:' ( (lv_generateIFP_141_0= ruleYesNo ) ) )? (otherlv_142= 'associatedVersionsPresentationPattern:' ( (lv_associatedVersionsPresentationPattern_143_0= ruleAssociatedVersionsPresentationPattern ) ) )? (otherlv_144= 'fieldsLayoutPattern:' ( (lv_fieldsLayoutPattern_145_0= ruleFieldsLayoutPattern ) ) )? otherlv_146= '}' )? otherlv_147= 'Fields' otherlv_148= '{' ( (lv_fields_149_0= ruleField ) )* otherlv_150= '}' )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:81:3: otherlv_0= 'Screen' ( ( ruleEntityRef ) ) otherlv_2= ',' ( (lv_shortName_3_0= ruleNID ) )? (otherlv_4= 't24Name:' ( (lv_t24Name_5_0= RULE_STRING ) ) )? (otherlv_6= 'metamodelVersion:' ( (lv_metamodelVersion_7_0= RULE_STRING ) ) )? (otherlv_8= 'group:' ( (lv_group_9_0= RULE_STRING ) ) )? (otherlv_10= 'numberOfAuthorisers:' ( (lv_numberOfAuthorisers_11_0= ruleINTEGER_OBJECT ) ) )? (otherlv_12= 'description:' ( (lv_description_13_0= ruleTranslations ) ) )? (otherlv_14= 'TransactionFlow' otherlv_15= '{' (otherlv_16= 'exceptionProcessing:' ( (lv_exceptionProcessing_17_0= ruleINTEGER_OBJECT ) ) )? (otherlv_18= 'interfaceException:' ( (lv_interfaceException_19_0= ruleINTEGER_OBJECT ) ) )? (otherlv_20= 'businessDayControl:' ( (lv_businessDayControl_21_0= ruleBusinessDayControl ) ) )? (otherlv_22= 'keySequence:' ( (lv_keySequence_23_0= RULE_STRING ) ) (otherlv_24= ';' ( (lv_keySequence_25_0= RULE_STRING ) ) )* )? (otherlv_26= 'otherCompanyAccess:' ( (lv_otherCompanyAccess_27_0= ruleYesNo ) ) )? (otherlv_28= 'autoCompanyChange:' ( (lv_autoCompanyChange_29_0= ruleYesNo ) ) )? (otherlv_30= 'overrideApproval:' ( (lv_overrideApproval_31_0= ruleYesNo ) ) )? (otherlv_32= 'dealSlips:' ( (lv_dealSlipFormats_33_0= ruleDealSlip ) ) (otherlv_34= ';' ( (lv_dealSlipFormats_35_0= ruleDealSlip ) ) )* )? (otherlv_36= 'dealSlipsTrigger:' ( (lv_dealSlipTrigger_37_0= ruleDealSlipTrigger ) ) )? (otherlv_38= 'dealSlipStyleSheet:' ( (lv_dealSlipStyleSheet_39_0= RULE_STRING ) ) )? otherlv_40= '}' )? (otherlv_41= 'Presentation' otherlv_42= '{' (otherlv_43= 'recordsPerPage:' ( (lv_recordsPerPage_44_0= RULE_STRING ) ) )? (otherlv_45= 'fieldsPerLine:' ( (lv_fieldsPerLine_46_0= RULE_STRING ) ) )? (otherlv_47= 'initialCursorPosition:' ( (lv_initialCursorPosition_48_0= RULE_STRING ) ) )? (otherlv_49= 'browserToolbar:' ( (lv_browserToolbar_50_0= RULE_STRING ) ) )? (otherlv_51= 'language:' ( (lv_languageLocale_52_0= ruleNID ) ) (otherlv_53= ';' ( (lv_languageLocale_54_0= ruleNID ) ) )* )? (otherlv_55= 'header1:' ( (lv_header1_56_0= ruleTranslations ) ) )? (otherlv_57= 'header2:' ( (lv_header2_58_0= ruleTranslations ) ) )? (otherlv_59= 'header:' ( (lv_header_60_0= ruleTranslations ) ) )? (otherlv_61= 'footer:' ( (lv_footer_62_0= ruleTranslations ) ) )? otherlv_63= '}' )? (otherlv_64= 'Relationship' otherlv_65= '{' (otherlv_66= 'nextVersion:' ( ( ruleVersionRef ) ) (otherlv_68= 'nextVersionFunction:' ( (lv_nextVersionFunction_69_0= ruleFunction ) ) )? (otherlv_70= 'nextVersionTransactionReference:' ( ( (lv_nextTransactionReference_71_1= 'AUTO' | lv_nextTransactionReference_71_2= RULE_STRING ) ) ) )? )? (otherlv_72= 'associatedVersions:' ( ( ruleVersionRef ) ) (otherlv_74= ';' ( ( ruleVersionRef ) ) )* )? otherlv_76= '}' )? (otherlv_77= 'API' otherlv_78= '{' (otherlv_79= 'includeVersionControl:' ( (lv_includeVersionControl_80_0= ruleYesNo ) ) )? (otherlv_81= 'authorizationRoutines:' ( (lv_authorizationRoutines_82_0= ruleRoutine ) ) (otherlv_83= ';' ( (lv_authorizationRoutines_84_0= ruleRoutine ) ) )* )? (otherlv_85= 'authorizationRoutinesAfterCommit:' ( (lv_authorizationRoutinesAfterCommit_86_0= ruleRoutine ) ) (otherlv_87= ';' ( (lv_authorizationRoutinesAfterCommit_88_0= ruleRoutine ) ) )* )? (otherlv_89= 'inputRoutines:' ( (lv_inputRoutines_90_0= ruleRoutine ) ) (otherlv_91= ';' ( (lv_inputRoutines_92_0= ruleRoutine ) ) )* )? (otherlv_93= 'inputRoutinesAfterCommit:' ( (lv_inputRoutinesAfterCommit_94_0= ruleRoutine ) ) (otherlv_95= ';' ( (lv_inputRoutinesAfterCommit_96_0= ruleRoutine ) ) )* )? (otherlv_97= 'keyValidationRoutines:' ( (lv_keyValidationRoutines_98_0= ruleRoutine ) ) (otherlv_99= ';' ( (lv_keyValidationRoutines_100_0= ruleRoutine ) ) )* )? (otherlv_101= 'preProcessValidationRoutines:' ( (lv_preProcessValidationRoutines_102_0= ruleRoutine ) ) (otherlv_103= ';' ( (lv_preProcessValidationRoutines_104_0= ruleRoutine ) ) )* )? (otherlv_105= 'webValidationRoutines:' ( (lv_webValidationRoutines_106_0= ruleRoutine ) ) (otherlv_107= ';' ( (lv_webValidationRoutines_108_0= ruleRoutine ) ) )* )? otherlv_109= '}' )? (otherlv_110= 'IB' otherlv_111= '{' (otherlv_112= 'confirmVersion:' ( ( ruleVersionRef ) ) )? (otherlv_114= 'previewVersion:' ( ( ruleVersionRef ) ) )? (otherlv_116= 'challengeResponse:' ( (lv_challengeResponse_117_0= RULE_STRING ) ) )? otherlv_118= '}' )? (otherlv_119= 'attributes:' ( (lv_attributes_120_0= RULE_STRING ) ) (otherlv_121= ';' ( (lv_attributes_122_0= RULE_STRING ) ) )* )? (otherlv_123= 'WebServices' otherlv_124= '{' (otherlv_125= 'publish:' ( (lv_publishWebService_126_0= ruleYesNo ) ) )? (otherlv_127= 'activity:' ( (lv_webServiceActivity_128_0= RULE_STRING ) ) )? (otherlv_129= 'function:' ( (lv_webServiceFunction_130_0= ruleFunction ) ) )? (otherlv_131= 'description:' ( (lv_webServiceDescription_132_0= RULE_STRING ) ) )? (otherlv_133= 'names:' ( (lv_webServiceNames_134_0= RULE_STRING ) ) (otherlv_135= ';' ( (lv_webServiceNames_136_0= RULE_STRING ) ) )* )? otherlv_137= '}' )? (otherlv_138= 'Connect' otherlv_139= '{' (otherlv_140= 'generateIFP:' ( (lv_generateIFP_141_0= ruleYesNo ) ) )? (otherlv_142= 'associatedVersionsPresentationPattern:' ( (lv_associatedVersionsPresentationPattern_143_0= ruleAssociatedVersionsPresentationPattern ) ) )? (otherlv_144= 'fieldsLayoutPattern:' ( (lv_fieldsLayoutPattern_145_0= ruleFieldsLayoutPattern ) ) )? otherlv_146= '}' )? otherlv_147= 'Fields' otherlv_148= '{' ( (lv_fields_149_0= ruleField ) )* otherlv_150= '}'
            {
            otherlv_0=(Token)match(input,11,FOLLOW_11_in_ruleVersion122); 

                	newLeafNode(otherlv_0, grammarAccess.getVersionAccess().getScreenKeyword_0());
                
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:85:1: ( ( ruleEntityRef ) )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:86:1: ( ruleEntityRef )
            {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:86:1: ( ruleEntityRef )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:87:3: ruleEntityRef
            {

            			if (current==null) {
            	            current = createModelElement(grammarAccess.getVersionRule());
            	        }
                    
             
            	        newCompositeNode(grammarAccess.getVersionAccess().getForApplicationMdfClassCrossReference_1_0()); 
            	    
            pushFollow(FOLLOW_ruleEntityRef_in_ruleVersion145);
            ruleEntityRef();

            state._fsp--;

             
            	        afterParserOrEnumRuleCall();
            	    

            }


            }

            otherlv_2=(Token)match(input,12,FOLLOW_12_in_ruleVersion157); 

                	newLeafNode(otherlv_2, grammarAccess.getVersionAccess().getCommaKeyword_2());
                
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:104:1: ( (lv_shortName_3_0= ruleNID ) )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( ((LA1_0>=RULE_ID && LA1_0<=RULE_INT)||(LA1_0>=111 && LA1_0<=158)) ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:105:1: (lv_shortName_3_0= ruleNID )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:105:1: (lv_shortName_3_0= ruleNID )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:106:3: lv_shortName_3_0= ruleNID
                    {
                     
                    	        newCompositeNode(grammarAccess.getVersionAccess().getShortNameNIDParserRuleCall_3_0()); 
                    	    
                    pushFollow(FOLLOW_ruleNID_in_ruleVersion178);
                    lv_shortName_3_0=ruleNID();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getVersionRule());
                    	        }
                           		set(
                           			current, 
                           			"shortName",
                            		lv_shortName_3_0, 
                            		"NID");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }
                    break;

            }

            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:122:3: (otherlv_4= 't24Name:' ( (lv_t24Name_5_0= RULE_STRING ) ) )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==13) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:122:5: otherlv_4= 't24Name:' ( (lv_t24Name_5_0= RULE_STRING ) )
                    {
                    otherlv_4=(Token)match(input,13,FOLLOW_13_in_ruleVersion192); 

                        	newLeafNode(otherlv_4, grammarAccess.getVersionAccess().getT24NameKeyword_4_0());
                        
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:126:1: ( (lv_t24Name_5_0= RULE_STRING ) )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:127:1: (lv_t24Name_5_0= RULE_STRING )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:127:1: (lv_t24Name_5_0= RULE_STRING )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:128:3: lv_t24Name_5_0= RULE_STRING
                    {
                    lv_t24Name_5_0=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleVersion209); 

                    			newLeafNode(lv_t24Name_5_0, grammarAccess.getVersionAccess().getT24NameSTRINGTerminalRuleCall_4_1_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getVersionRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"t24Name",
                            		lv_t24Name_5_0, 
                            		"STRING");
                    	    

                    }


                    }


                    }
                    break;

            }

            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:144:4: (otherlv_6= 'metamodelVersion:' ( (lv_metamodelVersion_7_0= RULE_STRING ) ) )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==14) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:144:6: otherlv_6= 'metamodelVersion:' ( (lv_metamodelVersion_7_0= RULE_STRING ) )
                    {
                    otherlv_6=(Token)match(input,14,FOLLOW_14_in_ruleVersion229); 

                        	newLeafNode(otherlv_6, grammarAccess.getVersionAccess().getMetamodelVersionKeyword_5_0());
                        
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:148:1: ( (lv_metamodelVersion_7_0= RULE_STRING ) )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:149:1: (lv_metamodelVersion_7_0= RULE_STRING )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:149:1: (lv_metamodelVersion_7_0= RULE_STRING )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:150:3: lv_metamodelVersion_7_0= RULE_STRING
                    {
                    lv_metamodelVersion_7_0=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleVersion246); 

                    			newLeafNode(lv_metamodelVersion_7_0, grammarAccess.getVersionAccess().getMetamodelVersionSTRINGTerminalRuleCall_5_1_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getVersionRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"metamodelVersion",
                            		lv_metamodelVersion_7_0, 
                            		"STRING");
                    	    

                    }


                    }


                    }
                    break;

            }

            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:166:4: (otherlv_8= 'group:' ( (lv_group_9_0= RULE_STRING ) ) )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==15) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:166:6: otherlv_8= 'group:' ( (lv_group_9_0= RULE_STRING ) )
                    {
                    otherlv_8=(Token)match(input,15,FOLLOW_15_in_ruleVersion266); 

                        	newLeafNode(otherlv_8, grammarAccess.getVersionAccess().getGroupKeyword_6_0());
                        
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:170:1: ( (lv_group_9_0= RULE_STRING ) )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:171:1: (lv_group_9_0= RULE_STRING )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:171:1: (lv_group_9_0= RULE_STRING )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:172:3: lv_group_9_0= RULE_STRING
                    {
                    lv_group_9_0=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleVersion283); 

                    			newLeafNode(lv_group_9_0, grammarAccess.getVersionAccess().getGroupSTRINGTerminalRuleCall_6_1_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getVersionRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"group",
                            		lv_group_9_0, 
                            		"STRING");
                    	    

                    }


                    }


                    }
                    break;

            }

            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:188:4: (otherlv_10= 'numberOfAuthorisers:' ( (lv_numberOfAuthorisers_11_0= ruleINTEGER_OBJECT ) ) )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==16) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:188:6: otherlv_10= 'numberOfAuthorisers:' ( (lv_numberOfAuthorisers_11_0= ruleINTEGER_OBJECT ) )
                    {
                    otherlv_10=(Token)match(input,16,FOLLOW_16_in_ruleVersion303); 

                        	newLeafNode(otherlv_10, grammarAccess.getVersionAccess().getNumberOfAuthorisersKeyword_7_0());
                        
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:192:1: ( (lv_numberOfAuthorisers_11_0= ruleINTEGER_OBJECT ) )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:193:1: (lv_numberOfAuthorisers_11_0= ruleINTEGER_OBJECT )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:193:1: (lv_numberOfAuthorisers_11_0= ruleINTEGER_OBJECT )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:194:3: lv_numberOfAuthorisers_11_0= ruleINTEGER_OBJECT
                    {
                     
                    	        newCompositeNode(grammarAccess.getVersionAccess().getNumberOfAuthorisersINTEGER_OBJECTParserRuleCall_7_1_0()); 
                    	    
                    pushFollow(FOLLOW_ruleINTEGER_OBJECT_in_ruleVersion324);
                    lv_numberOfAuthorisers_11_0=ruleINTEGER_OBJECT();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getVersionRule());
                    	        }
                           		set(
                           			current, 
                           			"numberOfAuthorisers",
                            		lv_numberOfAuthorisers_11_0, 
                            		"INTEGER_OBJECT");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }


                    }
                    break;

            }

            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:210:4: (otherlv_12= 'description:' ( (lv_description_13_0= ruleTranslations ) ) )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==17) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:210:6: otherlv_12= 'description:' ( (lv_description_13_0= ruleTranslations ) )
                    {
                    otherlv_12=(Token)match(input,17,FOLLOW_17_in_ruleVersion339); 

                        	newLeafNode(otherlv_12, grammarAccess.getVersionAccess().getDescriptionKeyword_8_0());
                        
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:214:1: ( (lv_description_13_0= ruleTranslations ) )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:215:1: (lv_description_13_0= ruleTranslations )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:215:1: (lv_description_13_0= ruleTranslations )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:216:3: lv_description_13_0= ruleTranslations
                    {
                     
                    	        newCompositeNode(grammarAccess.getVersionAccess().getDescriptionTranslationsParserRuleCall_8_1_0()); 
                    	    
                    pushFollow(FOLLOW_ruleTranslations_in_ruleVersion360);
                    lv_description_13_0=ruleTranslations();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getVersionRule());
                    	        }
                           		set(
                           			current, 
                           			"description",
                            		lv_description_13_0, 
                            		"Translations");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }


                    }
                    break;

            }

            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:232:4: (otherlv_14= 'TransactionFlow' otherlv_15= '{' (otherlv_16= 'exceptionProcessing:' ( (lv_exceptionProcessing_17_0= ruleINTEGER_OBJECT ) ) )? (otherlv_18= 'interfaceException:' ( (lv_interfaceException_19_0= ruleINTEGER_OBJECT ) ) )? (otherlv_20= 'businessDayControl:' ( (lv_businessDayControl_21_0= ruleBusinessDayControl ) ) )? (otherlv_22= 'keySequence:' ( (lv_keySequence_23_0= RULE_STRING ) ) (otherlv_24= ';' ( (lv_keySequence_25_0= RULE_STRING ) ) )* )? (otherlv_26= 'otherCompanyAccess:' ( (lv_otherCompanyAccess_27_0= ruleYesNo ) ) )? (otherlv_28= 'autoCompanyChange:' ( (lv_autoCompanyChange_29_0= ruleYesNo ) ) )? (otherlv_30= 'overrideApproval:' ( (lv_overrideApproval_31_0= ruleYesNo ) ) )? (otherlv_32= 'dealSlips:' ( (lv_dealSlipFormats_33_0= ruleDealSlip ) ) (otherlv_34= ';' ( (lv_dealSlipFormats_35_0= ruleDealSlip ) ) )* )? (otherlv_36= 'dealSlipsTrigger:' ( (lv_dealSlipTrigger_37_0= ruleDealSlipTrigger ) ) )? (otherlv_38= 'dealSlipStyleSheet:' ( (lv_dealSlipStyleSheet_39_0= RULE_STRING ) ) )? otherlv_40= '}' )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==18) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:232:6: otherlv_14= 'TransactionFlow' otherlv_15= '{' (otherlv_16= 'exceptionProcessing:' ( (lv_exceptionProcessing_17_0= ruleINTEGER_OBJECT ) ) )? (otherlv_18= 'interfaceException:' ( (lv_interfaceException_19_0= ruleINTEGER_OBJECT ) ) )? (otherlv_20= 'businessDayControl:' ( (lv_businessDayControl_21_0= ruleBusinessDayControl ) ) )? (otherlv_22= 'keySequence:' ( (lv_keySequence_23_0= RULE_STRING ) ) (otherlv_24= ';' ( (lv_keySequence_25_0= RULE_STRING ) ) )* )? (otherlv_26= 'otherCompanyAccess:' ( (lv_otherCompanyAccess_27_0= ruleYesNo ) ) )? (otherlv_28= 'autoCompanyChange:' ( (lv_autoCompanyChange_29_0= ruleYesNo ) ) )? (otherlv_30= 'overrideApproval:' ( (lv_overrideApproval_31_0= ruleYesNo ) ) )? (otherlv_32= 'dealSlips:' ( (lv_dealSlipFormats_33_0= ruleDealSlip ) ) (otherlv_34= ';' ( (lv_dealSlipFormats_35_0= ruleDealSlip ) ) )* )? (otherlv_36= 'dealSlipsTrigger:' ( (lv_dealSlipTrigger_37_0= ruleDealSlipTrigger ) ) )? (otherlv_38= 'dealSlipStyleSheet:' ( (lv_dealSlipStyleSheet_39_0= RULE_STRING ) ) )? otherlv_40= '}'
                    {
                    otherlv_14=(Token)match(input,18,FOLLOW_18_in_ruleVersion375); 

                        	newLeafNode(otherlv_14, grammarAccess.getVersionAccess().getTransactionFlowKeyword_9_0());
                        
                    otherlv_15=(Token)match(input,19,FOLLOW_19_in_ruleVersion387); 

                        	newLeafNode(otherlv_15, grammarAccess.getVersionAccess().getLeftCurlyBracketKeyword_9_1());
                        
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:240:1: (otherlv_16= 'exceptionProcessing:' ( (lv_exceptionProcessing_17_0= ruleINTEGER_OBJECT ) ) )?
                    int alt7=2;
                    int LA7_0 = input.LA(1);

                    if ( (LA7_0==20) ) {
                        alt7=1;
                    }
                    switch (alt7) {
                        case 1 :
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:240:3: otherlv_16= 'exceptionProcessing:' ( (lv_exceptionProcessing_17_0= ruleINTEGER_OBJECT ) )
                            {
                            otherlv_16=(Token)match(input,20,FOLLOW_20_in_ruleVersion400); 

                                	newLeafNode(otherlv_16, grammarAccess.getVersionAccess().getExceptionProcessingKeyword_9_2_0());
                                
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:244:1: ( (lv_exceptionProcessing_17_0= ruleINTEGER_OBJECT ) )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:245:1: (lv_exceptionProcessing_17_0= ruleINTEGER_OBJECT )
                            {
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:245:1: (lv_exceptionProcessing_17_0= ruleINTEGER_OBJECT )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:246:3: lv_exceptionProcessing_17_0= ruleINTEGER_OBJECT
                            {
                             
                            	        newCompositeNode(grammarAccess.getVersionAccess().getExceptionProcessingINTEGER_OBJECTParserRuleCall_9_2_1_0()); 
                            	    
                            pushFollow(FOLLOW_ruleINTEGER_OBJECT_in_ruleVersion421);
                            lv_exceptionProcessing_17_0=ruleINTEGER_OBJECT();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getVersionRule());
                            	        }
                                   		set(
                                   			current, 
                                   			"exceptionProcessing",
                                    		lv_exceptionProcessing_17_0, 
                                    		"INTEGER_OBJECT");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }


                            }
                            break;

                    }

                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:262:4: (otherlv_18= 'interfaceException:' ( (lv_interfaceException_19_0= ruleINTEGER_OBJECT ) ) )?
                    int alt8=2;
                    int LA8_0 = input.LA(1);

                    if ( (LA8_0==21) ) {
                        alt8=1;
                    }
                    switch (alt8) {
                        case 1 :
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:262:6: otherlv_18= 'interfaceException:' ( (lv_interfaceException_19_0= ruleINTEGER_OBJECT ) )
                            {
                            otherlv_18=(Token)match(input,21,FOLLOW_21_in_ruleVersion436); 

                                	newLeafNode(otherlv_18, grammarAccess.getVersionAccess().getInterfaceExceptionKeyword_9_3_0());
                                
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:266:1: ( (lv_interfaceException_19_0= ruleINTEGER_OBJECT ) )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:267:1: (lv_interfaceException_19_0= ruleINTEGER_OBJECT )
                            {
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:267:1: (lv_interfaceException_19_0= ruleINTEGER_OBJECT )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:268:3: lv_interfaceException_19_0= ruleINTEGER_OBJECT
                            {
                             
                            	        newCompositeNode(grammarAccess.getVersionAccess().getInterfaceExceptionINTEGER_OBJECTParserRuleCall_9_3_1_0()); 
                            	    
                            pushFollow(FOLLOW_ruleINTEGER_OBJECT_in_ruleVersion457);
                            lv_interfaceException_19_0=ruleINTEGER_OBJECT();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getVersionRule());
                            	        }
                                   		set(
                                   			current, 
                                   			"interfaceException",
                                    		lv_interfaceException_19_0, 
                                    		"INTEGER_OBJECT");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }


                            }
                            break;

                    }

                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:284:4: (otherlv_20= 'businessDayControl:' ( (lv_businessDayControl_21_0= ruleBusinessDayControl ) ) )?
                    int alt9=2;
                    int LA9_0 = input.LA(1);

                    if ( (LA9_0==22) ) {
                        alt9=1;
                    }
                    switch (alt9) {
                        case 1 :
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:284:6: otherlv_20= 'businessDayControl:' ( (lv_businessDayControl_21_0= ruleBusinessDayControl ) )
                            {
                            otherlv_20=(Token)match(input,22,FOLLOW_22_in_ruleVersion472); 

                                	newLeafNode(otherlv_20, grammarAccess.getVersionAccess().getBusinessDayControlKeyword_9_4_0());
                                
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:288:1: ( (lv_businessDayControl_21_0= ruleBusinessDayControl ) )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:289:1: (lv_businessDayControl_21_0= ruleBusinessDayControl )
                            {
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:289:1: (lv_businessDayControl_21_0= ruleBusinessDayControl )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:290:3: lv_businessDayControl_21_0= ruleBusinessDayControl
                            {
                             
                            	        newCompositeNode(grammarAccess.getVersionAccess().getBusinessDayControlBusinessDayControlEnumRuleCall_9_4_1_0()); 
                            	    
                            pushFollow(FOLLOW_ruleBusinessDayControl_in_ruleVersion493);
                            lv_businessDayControl_21_0=ruleBusinessDayControl();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getVersionRule());
                            	        }
                                   		set(
                                   			current, 
                                   			"businessDayControl",
                                    		lv_businessDayControl_21_0, 
                                    		"BusinessDayControl");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }


                            }
                            break;

                    }

                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:306:4: (otherlv_22= 'keySequence:' ( (lv_keySequence_23_0= RULE_STRING ) ) (otherlv_24= ';' ( (lv_keySequence_25_0= RULE_STRING ) ) )* )?
                    int alt11=2;
                    int LA11_0 = input.LA(1);

                    if ( (LA11_0==23) ) {
                        alt11=1;
                    }
                    switch (alt11) {
                        case 1 :
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:306:6: otherlv_22= 'keySequence:' ( (lv_keySequence_23_0= RULE_STRING ) ) (otherlv_24= ';' ( (lv_keySequence_25_0= RULE_STRING ) ) )*
                            {
                            otherlv_22=(Token)match(input,23,FOLLOW_23_in_ruleVersion508); 

                                	newLeafNode(otherlv_22, grammarAccess.getVersionAccess().getKeySequenceKeyword_9_5_0());
                                
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:310:1: ( (lv_keySequence_23_0= RULE_STRING ) )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:311:1: (lv_keySequence_23_0= RULE_STRING )
                            {
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:311:1: (lv_keySequence_23_0= RULE_STRING )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:312:3: lv_keySequence_23_0= RULE_STRING
                            {
                            lv_keySequence_23_0=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleVersion525); 

                            			newLeafNode(lv_keySequence_23_0, grammarAccess.getVersionAccess().getKeySequenceSTRINGTerminalRuleCall_9_5_1_0()); 
                            		

                            	        if (current==null) {
                            	            current = createModelElement(grammarAccess.getVersionRule());
                            	        }
                                   		addWithLastConsumed(
                                   			current, 
                                   			"keySequence",
                                    		lv_keySequence_23_0, 
                                    		"STRING");
                            	    

                            }


                            }

                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:328:2: (otherlv_24= ';' ( (lv_keySequence_25_0= RULE_STRING ) ) )*
                            loop10:
                            do {
                                int alt10=2;
                                int LA10_0 = input.LA(1);

                                if ( (LA10_0==24) ) {
                                    alt10=1;
                                }


                                switch (alt10) {
                            	case 1 :
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:328:4: otherlv_24= ';' ( (lv_keySequence_25_0= RULE_STRING ) )
                            	    {
                            	    otherlv_24=(Token)match(input,24,FOLLOW_24_in_ruleVersion543); 

                            	        	newLeafNode(otherlv_24, grammarAccess.getVersionAccess().getSemicolonKeyword_9_5_2_0());
                            	        
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:332:1: ( (lv_keySequence_25_0= RULE_STRING ) )
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:333:1: (lv_keySequence_25_0= RULE_STRING )
                            	    {
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:333:1: (lv_keySequence_25_0= RULE_STRING )
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:334:3: lv_keySequence_25_0= RULE_STRING
                            	    {
                            	    lv_keySequence_25_0=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleVersion560); 

                            	    			newLeafNode(lv_keySequence_25_0, grammarAccess.getVersionAccess().getKeySequenceSTRINGTerminalRuleCall_9_5_2_1_0()); 
                            	    		

                            	    	        if (current==null) {
                            	    	            current = createModelElement(grammarAccess.getVersionRule());
                            	    	        }
                            	           		addWithLastConsumed(
                            	           			current, 
                            	           			"keySequence",
                            	            		lv_keySequence_25_0, 
                            	            		"STRING");
                            	    	    

                            	    }


                            	    }


                            	    }
                            	    break;

                            	default :
                            	    break loop10;
                                }
                            } while (true);


                            }
                            break;

                    }

                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:350:6: (otherlv_26= 'otherCompanyAccess:' ( (lv_otherCompanyAccess_27_0= ruleYesNo ) ) )?
                    int alt12=2;
                    int LA12_0 = input.LA(1);

                    if ( (LA12_0==25) ) {
                        alt12=1;
                    }
                    switch (alt12) {
                        case 1 :
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:350:8: otherlv_26= 'otherCompanyAccess:' ( (lv_otherCompanyAccess_27_0= ruleYesNo ) )
                            {
                            otherlv_26=(Token)match(input,25,FOLLOW_25_in_ruleVersion582); 

                                	newLeafNode(otherlv_26, grammarAccess.getVersionAccess().getOtherCompanyAccessKeyword_9_6_0());
                                
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:354:1: ( (lv_otherCompanyAccess_27_0= ruleYesNo ) )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:355:1: (lv_otherCompanyAccess_27_0= ruleYesNo )
                            {
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:355:1: (lv_otherCompanyAccess_27_0= ruleYesNo )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:356:3: lv_otherCompanyAccess_27_0= ruleYesNo
                            {
                             
                            	        newCompositeNode(grammarAccess.getVersionAccess().getOtherCompanyAccessYesNoEnumRuleCall_9_6_1_0()); 
                            	    
                            pushFollow(FOLLOW_ruleYesNo_in_ruleVersion603);
                            lv_otherCompanyAccess_27_0=ruleYesNo();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getVersionRule());
                            	        }
                                   		set(
                                   			current, 
                                   			"otherCompanyAccess",
                                    		lv_otherCompanyAccess_27_0, 
                                    		"YesNo");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }


                            }
                            break;

                    }

                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:372:4: (otherlv_28= 'autoCompanyChange:' ( (lv_autoCompanyChange_29_0= ruleYesNo ) ) )?
                    int alt13=2;
                    int LA13_0 = input.LA(1);

                    if ( (LA13_0==26) ) {
                        alt13=1;
                    }
                    switch (alt13) {
                        case 1 :
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:372:6: otherlv_28= 'autoCompanyChange:' ( (lv_autoCompanyChange_29_0= ruleYesNo ) )
                            {
                            otherlv_28=(Token)match(input,26,FOLLOW_26_in_ruleVersion618); 

                                	newLeafNode(otherlv_28, grammarAccess.getVersionAccess().getAutoCompanyChangeKeyword_9_7_0());
                                
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:376:1: ( (lv_autoCompanyChange_29_0= ruleYesNo ) )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:377:1: (lv_autoCompanyChange_29_0= ruleYesNo )
                            {
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:377:1: (lv_autoCompanyChange_29_0= ruleYesNo )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:378:3: lv_autoCompanyChange_29_0= ruleYesNo
                            {
                             
                            	        newCompositeNode(grammarAccess.getVersionAccess().getAutoCompanyChangeYesNoEnumRuleCall_9_7_1_0()); 
                            	    
                            pushFollow(FOLLOW_ruleYesNo_in_ruleVersion639);
                            lv_autoCompanyChange_29_0=ruleYesNo();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getVersionRule());
                            	        }
                                   		set(
                                   			current, 
                                   			"autoCompanyChange",
                                    		lv_autoCompanyChange_29_0, 
                                    		"YesNo");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }


                            }
                            break;

                    }

                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:394:4: (otherlv_30= 'overrideApproval:' ( (lv_overrideApproval_31_0= ruleYesNo ) ) )?
                    int alt14=2;
                    int LA14_0 = input.LA(1);

                    if ( (LA14_0==27) ) {
                        alt14=1;
                    }
                    switch (alt14) {
                        case 1 :
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:394:6: otherlv_30= 'overrideApproval:' ( (lv_overrideApproval_31_0= ruleYesNo ) )
                            {
                            otherlv_30=(Token)match(input,27,FOLLOW_27_in_ruleVersion654); 

                                	newLeafNode(otherlv_30, grammarAccess.getVersionAccess().getOverrideApprovalKeyword_9_8_0());
                                
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:398:1: ( (lv_overrideApproval_31_0= ruleYesNo ) )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:399:1: (lv_overrideApproval_31_0= ruleYesNo )
                            {
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:399:1: (lv_overrideApproval_31_0= ruleYesNo )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:400:3: lv_overrideApproval_31_0= ruleYesNo
                            {
                             
                            	        newCompositeNode(grammarAccess.getVersionAccess().getOverrideApprovalYesNoEnumRuleCall_9_8_1_0()); 
                            	    
                            pushFollow(FOLLOW_ruleYesNo_in_ruleVersion675);
                            lv_overrideApproval_31_0=ruleYesNo();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getVersionRule());
                            	        }
                                   		set(
                                   			current, 
                                   			"overrideApproval",
                                    		lv_overrideApproval_31_0, 
                                    		"YesNo");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }


                            }
                            break;

                    }

                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:416:4: (otherlv_32= 'dealSlips:' ( (lv_dealSlipFormats_33_0= ruleDealSlip ) ) (otherlv_34= ';' ( (lv_dealSlipFormats_35_0= ruleDealSlip ) ) )* )?
                    int alt16=2;
                    int LA16_0 = input.LA(1);

                    if ( (LA16_0==28) ) {
                        alt16=1;
                    }
                    switch (alt16) {
                        case 1 :
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:416:6: otherlv_32= 'dealSlips:' ( (lv_dealSlipFormats_33_0= ruleDealSlip ) ) (otherlv_34= ';' ( (lv_dealSlipFormats_35_0= ruleDealSlip ) ) )*
                            {
                            otherlv_32=(Token)match(input,28,FOLLOW_28_in_ruleVersion690); 

                                	newLeafNode(otherlv_32, grammarAccess.getVersionAccess().getDealSlipsKeyword_9_9_0());
                                
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:420:1: ( (lv_dealSlipFormats_33_0= ruleDealSlip ) )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:421:1: (lv_dealSlipFormats_33_0= ruleDealSlip )
                            {
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:421:1: (lv_dealSlipFormats_33_0= ruleDealSlip )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:422:3: lv_dealSlipFormats_33_0= ruleDealSlip
                            {
                             
                            	        newCompositeNode(grammarAccess.getVersionAccess().getDealSlipFormatsDealSlipParserRuleCall_9_9_1_0()); 
                            	    
                            pushFollow(FOLLOW_ruleDealSlip_in_ruleVersion711);
                            lv_dealSlipFormats_33_0=ruleDealSlip();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getVersionRule());
                            	        }
                                   		add(
                                   			current, 
                                   			"dealSlipFormats",
                                    		lv_dealSlipFormats_33_0, 
                                    		"DealSlip");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }

                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:438:2: (otherlv_34= ';' ( (lv_dealSlipFormats_35_0= ruleDealSlip ) ) )*
                            loop15:
                            do {
                                int alt15=2;
                                int LA15_0 = input.LA(1);

                                if ( (LA15_0==24) ) {
                                    alt15=1;
                                }


                                switch (alt15) {
                            	case 1 :
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:438:4: otherlv_34= ';' ( (lv_dealSlipFormats_35_0= ruleDealSlip ) )
                            	    {
                            	    otherlv_34=(Token)match(input,24,FOLLOW_24_in_ruleVersion724); 

                            	        	newLeafNode(otherlv_34, grammarAccess.getVersionAccess().getSemicolonKeyword_9_9_2_0());
                            	        
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:442:1: ( (lv_dealSlipFormats_35_0= ruleDealSlip ) )
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:443:1: (lv_dealSlipFormats_35_0= ruleDealSlip )
                            	    {
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:443:1: (lv_dealSlipFormats_35_0= ruleDealSlip )
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:444:3: lv_dealSlipFormats_35_0= ruleDealSlip
                            	    {
                            	     
                            	    	        newCompositeNode(grammarAccess.getVersionAccess().getDealSlipFormatsDealSlipParserRuleCall_9_9_2_1_0()); 
                            	    	    
                            	    pushFollow(FOLLOW_ruleDealSlip_in_ruleVersion745);
                            	    lv_dealSlipFormats_35_0=ruleDealSlip();

                            	    state._fsp--;


                            	    	        if (current==null) {
                            	    	            current = createModelElementForParent(grammarAccess.getVersionRule());
                            	    	        }
                            	           		add(
                            	           			current, 
                            	           			"dealSlipFormats",
                            	            		lv_dealSlipFormats_35_0, 
                            	            		"DealSlip");
                            	    	        afterParserOrEnumRuleCall();
                            	    	    

                            	    }


                            	    }


                            	    }
                            	    break;

                            	default :
                            	    break loop15;
                                }
                            } while (true);


                            }
                            break;

                    }

                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:460:6: (otherlv_36= 'dealSlipsTrigger:' ( (lv_dealSlipTrigger_37_0= ruleDealSlipTrigger ) ) )?
                    int alt17=2;
                    int LA17_0 = input.LA(1);

                    if ( (LA17_0==29) ) {
                        alt17=1;
                    }
                    switch (alt17) {
                        case 1 :
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:460:8: otherlv_36= 'dealSlipsTrigger:' ( (lv_dealSlipTrigger_37_0= ruleDealSlipTrigger ) )
                            {
                            otherlv_36=(Token)match(input,29,FOLLOW_29_in_ruleVersion762); 

                                	newLeafNode(otherlv_36, grammarAccess.getVersionAccess().getDealSlipsTriggerKeyword_9_10_0());
                                
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:464:1: ( (lv_dealSlipTrigger_37_0= ruleDealSlipTrigger ) )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:465:1: (lv_dealSlipTrigger_37_0= ruleDealSlipTrigger )
                            {
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:465:1: (lv_dealSlipTrigger_37_0= ruleDealSlipTrigger )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:466:3: lv_dealSlipTrigger_37_0= ruleDealSlipTrigger
                            {
                             
                            	        newCompositeNode(grammarAccess.getVersionAccess().getDealSlipTriggerDealSlipTriggerEnumRuleCall_9_10_1_0()); 
                            	    
                            pushFollow(FOLLOW_ruleDealSlipTrigger_in_ruleVersion783);
                            lv_dealSlipTrigger_37_0=ruleDealSlipTrigger();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getVersionRule());
                            	        }
                                   		set(
                                   			current, 
                                   			"dealSlipTrigger",
                                    		lv_dealSlipTrigger_37_0, 
                                    		"DealSlipTrigger");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }


                            }
                            break;

                    }

                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:482:4: (otherlv_38= 'dealSlipStyleSheet:' ( (lv_dealSlipStyleSheet_39_0= RULE_STRING ) ) )?
                    int alt18=2;
                    int LA18_0 = input.LA(1);

                    if ( (LA18_0==30) ) {
                        alt18=1;
                    }
                    switch (alt18) {
                        case 1 :
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:482:6: otherlv_38= 'dealSlipStyleSheet:' ( (lv_dealSlipStyleSheet_39_0= RULE_STRING ) )
                            {
                            otherlv_38=(Token)match(input,30,FOLLOW_30_in_ruleVersion798); 

                                	newLeafNode(otherlv_38, grammarAccess.getVersionAccess().getDealSlipStyleSheetKeyword_9_11_0());
                                
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:486:1: ( (lv_dealSlipStyleSheet_39_0= RULE_STRING ) )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:487:1: (lv_dealSlipStyleSheet_39_0= RULE_STRING )
                            {
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:487:1: (lv_dealSlipStyleSheet_39_0= RULE_STRING )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:488:3: lv_dealSlipStyleSheet_39_0= RULE_STRING
                            {
                            lv_dealSlipStyleSheet_39_0=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleVersion815); 

                            			newLeafNode(lv_dealSlipStyleSheet_39_0, grammarAccess.getVersionAccess().getDealSlipStyleSheetSTRINGTerminalRuleCall_9_11_1_0()); 
                            		

                            	        if (current==null) {
                            	            current = createModelElement(grammarAccess.getVersionRule());
                            	        }
                                   		setWithLastConsumed(
                                   			current, 
                                   			"dealSlipStyleSheet",
                                    		lv_dealSlipStyleSheet_39_0, 
                                    		"STRING");
                            	    

                            }


                            }


                            }
                            break;

                    }

                    otherlv_40=(Token)match(input,31,FOLLOW_31_in_ruleVersion834); 

                        	newLeafNode(otherlv_40, grammarAccess.getVersionAccess().getRightCurlyBracketKeyword_9_12());
                        

                    }
                    break;

            }

            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:508:3: (otherlv_41= 'Presentation' otherlv_42= '{' (otherlv_43= 'recordsPerPage:' ( (lv_recordsPerPage_44_0= RULE_STRING ) ) )? (otherlv_45= 'fieldsPerLine:' ( (lv_fieldsPerLine_46_0= RULE_STRING ) ) )? (otherlv_47= 'initialCursorPosition:' ( (lv_initialCursorPosition_48_0= RULE_STRING ) ) )? (otherlv_49= 'browserToolbar:' ( (lv_browserToolbar_50_0= RULE_STRING ) ) )? (otherlv_51= 'language:' ( (lv_languageLocale_52_0= ruleNID ) ) (otherlv_53= ';' ( (lv_languageLocale_54_0= ruleNID ) ) )* )? (otherlv_55= 'header1:' ( (lv_header1_56_0= ruleTranslations ) ) )? (otherlv_57= 'header2:' ( (lv_header2_58_0= ruleTranslations ) ) )? (otherlv_59= 'header:' ( (lv_header_60_0= ruleTranslations ) ) )? (otherlv_61= 'footer:' ( (lv_footer_62_0= ruleTranslations ) ) )? otherlv_63= '}' )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==32) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:508:5: otherlv_41= 'Presentation' otherlv_42= '{' (otherlv_43= 'recordsPerPage:' ( (lv_recordsPerPage_44_0= RULE_STRING ) ) )? (otherlv_45= 'fieldsPerLine:' ( (lv_fieldsPerLine_46_0= RULE_STRING ) ) )? (otherlv_47= 'initialCursorPosition:' ( (lv_initialCursorPosition_48_0= RULE_STRING ) ) )? (otherlv_49= 'browserToolbar:' ( (lv_browserToolbar_50_0= RULE_STRING ) ) )? (otherlv_51= 'language:' ( (lv_languageLocale_52_0= ruleNID ) ) (otherlv_53= ';' ( (lv_languageLocale_54_0= ruleNID ) ) )* )? (otherlv_55= 'header1:' ( (lv_header1_56_0= ruleTranslations ) ) )? (otherlv_57= 'header2:' ( (lv_header2_58_0= ruleTranslations ) ) )? (otherlv_59= 'header:' ( (lv_header_60_0= ruleTranslations ) ) )? (otherlv_61= 'footer:' ( (lv_footer_62_0= ruleTranslations ) ) )? otherlv_63= '}'
                    {
                    otherlv_41=(Token)match(input,32,FOLLOW_32_in_ruleVersion849); 

                        	newLeafNode(otherlv_41, grammarAccess.getVersionAccess().getPresentationKeyword_10_0());
                        
                    otherlv_42=(Token)match(input,19,FOLLOW_19_in_ruleVersion861); 

                        	newLeafNode(otherlv_42, grammarAccess.getVersionAccess().getLeftCurlyBracketKeyword_10_1());
                        
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:516:1: (otherlv_43= 'recordsPerPage:' ( (lv_recordsPerPage_44_0= RULE_STRING ) ) )?
                    int alt20=2;
                    int LA20_0 = input.LA(1);

                    if ( (LA20_0==33) ) {
                        alt20=1;
                    }
                    switch (alt20) {
                        case 1 :
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:516:3: otherlv_43= 'recordsPerPage:' ( (lv_recordsPerPage_44_0= RULE_STRING ) )
                            {
                            otherlv_43=(Token)match(input,33,FOLLOW_33_in_ruleVersion874); 

                                	newLeafNode(otherlv_43, grammarAccess.getVersionAccess().getRecordsPerPageKeyword_10_2_0());
                                
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:520:1: ( (lv_recordsPerPage_44_0= RULE_STRING ) )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:521:1: (lv_recordsPerPage_44_0= RULE_STRING )
                            {
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:521:1: (lv_recordsPerPage_44_0= RULE_STRING )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:522:3: lv_recordsPerPage_44_0= RULE_STRING
                            {
                            lv_recordsPerPage_44_0=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleVersion891); 

                            			newLeafNode(lv_recordsPerPage_44_0, grammarAccess.getVersionAccess().getRecordsPerPageSTRINGTerminalRuleCall_10_2_1_0()); 
                            		

                            	        if (current==null) {
                            	            current = createModelElement(grammarAccess.getVersionRule());
                            	        }
                                   		setWithLastConsumed(
                                   			current, 
                                   			"recordsPerPage",
                                    		lv_recordsPerPage_44_0, 
                                    		"STRING");
                            	    

                            }


                            }


                            }
                            break;

                    }

                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:538:4: (otherlv_45= 'fieldsPerLine:' ( (lv_fieldsPerLine_46_0= RULE_STRING ) ) )?
                    int alt21=2;
                    int LA21_0 = input.LA(1);

                    if ( (LA21_0==34) ) {
                        alt21=1;
                    }
                    switch (alt21) {
                        case 1 :
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:538:6: otherlv_45= 'fieldsPerLine:' ( (lv_fieldsPerLine_46_0= RULE_STRING ) )
                            {
                            otherlv_45=(Token)match(input,34,FOLLOW_34_in_ruleVersion911); 

                                	newLeafNode(otherlv_45, grammarAccess.getVersionAccess().getFieldsPerLineKeyword_10_3_0());
                                
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:542:1: ( (lv_fieldsPerLine_46_0= RULE_STRING ) )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:543:1: (lv_fieldsPerLine_46_0= RULE_STRING )
                            {
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:543:1: (lv_fieldsPerLine_46_0= RULE_STRING )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:544:3: lv_fieldsPerLine_46_0= RULE_STRING
                            {
                            lv_fieldsPerLine_46_0=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleVersion928); 

                            			newLeafNode(lv_fieldsPerLine_46_0, grammarAccess.getVersionAccess().getFieldsPerLineSTRINGTerminalRuleCall_10_3_1_0()); 
                            		

                            	        if (current==null) {
                            	            current = createModelElement(grammarAccess.getVersionRule());
                            	        }
                                   		setWithLastConsumed(
                                   			current, 
                                   			"fieldsPerLine",
                                    		lv_fieldsPerLine_46_0, 
                                    		"STRING");
                            	    

                            }


                            }


                            }
                            break;

                    }

                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:560:4: (otherlv_47= 'initialCursorPosition:' ( (lv_initialCursorPosition_48_0= RULE_STRING ) ) )?
                    int alt22=2;
                    int LA22_0 = input.LA(1);

                    if ( (LA22_0==35) ) {
                        alt22=1;
                    }
                    switch (alt22) {
                        case 1 :
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:560:6: otherlv_47= 'initialCursorPosition:' ( (lv_initialCursorPosition_48_0= RULE_STRING ) )
                            {
                            otherlv_47=(Token)match(input,35,FOLLOW_35_in_ruleVersion948); 

                                	newLeafNode(otherlv_47, grammarAccess.getVersionAccess().getInitialCursorPositionKeyword_10_4_0());
                                
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:564:1: ( (lv_initialCursorPosition_48_0= RULE_STRING ) )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:565:1: (lv_initialCursorPosition_48_0= RULE_STRING )
                            {
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:565:1: (lv_initialCursorPosition_48_0= RULE_STRING )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:566:3: lv_initialCursorPosition_48_0= RULE_STRING
                            {
                            lv_initialCursorPosition_48_0=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleVersion965); 

                            			newLeafNode(lv_initialCursorPosition_48_0, grammarAccess.getVersionAccess().getInitialCursorPositionSTRINGTerminalRuleCall_10_4_1_0()); 
                            		

                            	        if (current==null) {
                            	            current = createModelElement(grammarAccess.getVersionRule());
                            	        }
                                   		setWithLastConsumed(
                                   			current, 
                                   			"initialCursorPosition",
                                    		lv_initialCursorPosition_48_0, 
                                    		"STRING");
                            	    

                            }


                            }


                            }
                            break;

                    }

                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:582:4: (otherlv_49= 'browserToolbar:' ( (lv_browserToolbar_50_0= RULE_STRING ) ) )?
                    int alt23=2;
                    int LA23_0 = input.LA(1);

                    if ( (LA23_0==36) ) {
                        alt23=1;
                    }
                    switch (alt23) {
                        case 1 :
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:582:6: otherlv_49= 'browserToolbar:' ( (lv_browserToolbar_50_0= RULE_STRING ) )
                            {
                            otherlv_49=(Token)match(input,36,FOLLOW_36_in_ruleVersion985); 

                                	newLeafNode(otherlv_49, grammarAccess.getVersionAccess().getBrowserToolbarKeyword_10_5_0());
                                
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:586:1: ( (lv_browserToolbar_50_0= RULE_STRING ) )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:587:1: (lv_browserToolbar_50_0= RULE_STRING )
                            {
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:587:1: (lv_browserToolbar_50_0= RULE_STRING )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:588:3: lv_browserToolbar_50_0= RULE_STRING
                            {
                            lv_browserToolbar_50_0=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleVersion1002); 

                            			newLeafNode(lv_browserToolbar_50_0, grammarAccess.getVersionAccess().getBrowserToolbarSTRINGTerminalRuleCall_10_5_1_0()); 
                            		

                            	        if (current==null) {
                            	            current = createModelElement(grammarAccess.getVersionRule());
                            	        }
                                   		setWithLastConsumed(
                                   			current, 
                                   			"browserToolbar",
                                    		lv_browserToolbar_50_0, 
                                    		"STRING");
                            	    

                            }


                            }


                            }
                            break;

                    }

                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:604:4: (otherlv_51= 'language:' ( (lv_languageLocale_52_0= ruleNID ) ) (otherlv_53= ';' ( (lv_languageLocale_54_0= ruleNID ) ) )* )?
                    int alt25=2;
                    int LA25_0 = input.LA(1);

                    if ( (LA25_0==37) ) {
                        alt25=1;
                    }
                    switch (alt25) {
                        case 1 :
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:604:6: otherlv_51= 'language:' ( (lv_languageLocale_52_0= ruleNID ) ) (otherlv_53= ';' ( (lv_languageLocale_54_0= ruleNID ) ) )*
                            {
                            otherlv_51=(Token)match(input,37,FOLLOW_37_in_ruleVersion1022); 

                                	newLeafNode(otherlv_51, grammarAccess.getVersionAccess().getLanguageKeyword_10_6_0());
                                
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:608:1: ( (lv_languageLocale_52_0= ruleNID ) )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:609:1: (lv_languageLocale_52_0= ruleNID )
                            {
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:609:1: (lv_languageLocale_52_0= ruleNID )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:610:3: lv_languageLocale_52_0= ruleNID
                            {
                             
                            	        newCompositeNode(grammarAccess.getVersionAccess().getLanguageLocaleNIDParserRuleCall_10_6_1_0()); 
                            	    
                            pushFollow(FOLLOW_ruleNID_in_ruleVersion1043);
                            lv_languageLocale_52_0=ruleNID();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getVersionRule());
                            	        }
                                   		add(
                                   			current, 
                                   			"languageLocale",
                                    		lv_languageLocale_52_0, 
                                    		"NID");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }

                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:626:2: (otherlv_53= ';' ( (lv_languageLocale_54_0= ruleNID ) ) )*
                            loop24:
                            do {
                                int alt24=2;
                                int LA24_0 = input.LA(1);

                                if ( (LA24_0==24) ) {
                                    alt24=1;
                                }


                                switch (alt24) {
                            	case 1 :
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:626:4: otherlv_53= ';' ( (lv_languageLocale_54_0= ruleNID ) )
                            	    {
                            	    otherlv_53=(Token)match(input,24,FOLLOW_24_in_ruleVersion1056); 

                            	        	newLeafNode(otherlv_53, grammarAccess.getVersionAccess().getSemicolonKeyword_10_6_2_0());
                            	        
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:630:1: ( (lv_languageLocale_54_0= ruleNID ) )
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:631:1: (lv_languageLocale_54_0= ruleNID )
                            	    {
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:631:1: (lv_languageLocale_54_0= ruleNID )
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:632:3: lv_languageLocale_54_0= ruleNID
                            	    {
                            	     
                            	    	        newCompositeNode(grammarAccess.getVersionAccess().getLanguageLocaleNIDParserRuleCall_10_6_2_1_0()); 
                            	    	    
                            	    pushFollow(FOLLOW_ruleNID_in_ruleVersion1077);
                            	    lv_languageLocale_54_0=ruleNID();

                            	    state._fsp--;


                            	    	        if (current==null) {
                            	    	            current = createModelElementForParent(grammarAccess.getVersionRule());
                            	    	        }
                            	           		add(
                            	           			current, 
                            	           			"languageLocale",
                            	            		lv_languageLocale_54_0, 
                            	            		"NID");
                            	    	        afterParserOrEnumRuleCall();
                            	    	    

                            	    }


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

                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:648:6: (otherlv_55= 'header1:' ( (lv_header1_56_0= ruleTranslations ) ) )?
                    int alt26=2;
                    int LA26_0 = input.LA(1);

                    if ( (LA26_0==38) ) {
                        alt26=1;
                    }
                    switch (alt26) {
                        case 1 :
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:648:8: otherlv_55= 'header1:' ( (lv_header1_56_0= ruleTranslations ) )
                            {
                            otherlv_55=(Token)match(input,38,FOLLOW_38_in_ruleVersion1094); 

                                	newLeafNode(otherlv_55, grammarAccess.getVersionAccess().getHeader1Keyword_10_7_0());
                                
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:652:1: ( (lv_header1_56_0= ruleTranslations ) )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:653:1: (lv_header1_56_0= ruleTranslations )
                            {
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:653:1: (lv_header1_56_0= ruleTranslations )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:654:3: lv_header1_56_0= ruleTranslations
                            {
                             
                            	        newCompositeNode(grammarAccess.getVersionAccess().getHeader1TranslationsParserRuleCall_10_7_1_0()); 
                            	    
                            pushFollow(FOLLOW_ruleTranslations_in_ruleVersion1115);
                            lv_header1_56_0=ruleTranslations();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getVersionRule());
                            	        }
                                   		set(
                                   			current, 
                                   			"header1",
                                    		lv_header1_56_0, 
                                    		"Translations");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }


                            }
                            break;

                    }

                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:670:4: (otherlv_57= 'header2:' ( (lv_header2_58_0= ruleTranslations ) ) )?
                    int alt27=2;
                    int LA27_0 = input.LA(1);

                    if ( (LA27_0==39) ) {
                        alt27=1;
                    }
                    switch (alt27) {
                        case 1 :
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:670:6: otherlv_57= 'header2:' ( (lv_header2_58_0= ruleTranslations ) )
                            {
                            otherlv_57=(Token)match(input,39,FOLLOW_39_in_ruleVersion1130); 

                                	newLeafNode(otherlv_57, grammarAccess.getVersionAccess().getHeader2Keyword_10_8_0());
                                
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:674:1: ( (lv_header2_58_0= ruleTranslations ) )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:675:1: (lv_header2_58_0= ruleTranslations )
                            {
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:675:1: (lv_header2_58_0= ruleTranslations )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:676:3: lv_header2_58_0= ruleTranslations
                            {
                             
                            	        newCompositeNode(grammarAccess.getVersionAccess().getHeader2TranslationsParserRuleCall_10_8_1_0()); 
                            	    
                            pushFollow(FOLLOW_ruleTranslations_in_ruleVersion1151);
                            lv_header2_58_0=ruleTranslations();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getVersionRule());
                            	        }
                                   		set(
                                   			current, 
                                   			"header2",
                                    		lv_header2_58_0, 
                                    		"Translations");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }


                            }
                            break;

                    }

                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:692:4: (otherlv_59= 'header:' ( (lv_header_60_0= ruleTranslations ) ) )?
                    int alt28=2;
                    int LA28_0 = input.LA(1);

                    if ( (LA28_0==40) ) {
                        alt28=1;
                    }
                    switch (alt28) {
                        case 1 :
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:692:6: otherlv_59= 'header:' ( (lv_header_60_0= ruleTranslations ) )
                            {
                            otherlv_59=(Token)match(input,40,FOLLOW_40_in_ruleVersion1166); 

                                	newLeafNode(otherlv_59, grammarAccess.getVersionAccess().getHeaderKeyword_10_9_0());
                                
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:696:1: ( (lv_header_60_0= ruleTranslations ) )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:697:1: (lv_header_60_0= ruleTranslations )
                            {
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:697:1: (lv_header_60_0= ruleTranslations )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:698:3: lv_header_60_0= ruleTranslations
                            {
                             
                            	        newCompositeNode(grammarAccess.getVersionAccess().getHeaderTranslationsParserRuleCall_10_9_1_0()); 
                            	    
                            pushFollow(FOLLOW_ruleTranslations_in_ruleVersion1187);
                            lv_header_60_0=ruleTranslations();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getVersionRule());
                            	        }
                                   		set(
                                   			current, 
                                   			"header",
                                    		lv_header_60_0, 
                                    		"Translations");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }


                            }
                            break;

                    }

                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:714:4: (otherlv_61= 'footer:' ( (lv_footer_62_0= ruleTranslations ) ) )?
                    int alt29=2;
                    int LA29_0 = input.LA(1);

                    if ( (LA29_0==41) ) {
                        alt29=1;
                    }
                    switch (alt29) {
                        case 1 :
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:714:6: otherlv_61= 'footer:' ( (lv_footer_62_0= ruleTranslations ) )
                            {
                            otherlv_61=(Token)match(input,41,FOLLOW_41_in_ruleVersion1202); 

                                	newLeafNode(otherlv_61, grammarAccess.getVersionAccess().getFooterKeyword_10_10_0());
                                
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:718:1: ( (lv_footer_62_0= ruleTranslations ) )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:719:1: (lv_footer_62_0= ruleTranslations )
                            {
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:719:1: (lv_footer_62_0= ruleTranslations )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:720:3: lv_footer_62_0= ruleTranslations
                            {
                             
                            	        newCompositeNode(grammarAccess.getVersionAccess().getFooterTranslationsParserRuleCall_10_10_1_0()); 
                            	    
                            pushFollow(FOLLOW_ruleTranslations_in_ruleVersion1223);
                            lv_footer_62_0=ruleTranslations();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getVersionRule());
                            	        }
                                   		set(
                                   			current, 
                                   			"footer",
                                    		lv_footer_62_0, 
                                    		"Translations");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }


                            }
                            break;

                    }

                    otherlv_63=(Token)match(input,31,FOLLOW_31_in_ruleVersion1237); 

                        	newLeafNode(otherlv_63, grammarAccess.getVersionAccess().getRightCurlyBracketKeyword_10_11());
                        

                    }
                    break;

            }

            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:740:3: (otherlv_64= 'Relationship' otherlv_65= '{' (otherlv_66= 'nextVersion:' ( ( ruleVersionRef ) ) (otherlv_68= 'nextVersionFunction:' ( (lv_nextVersionFunction_69_0= ruleFunction ) ) )? (otherlv_70= 'nextVersionTransactionReference:' ( ( (lv_nextTransactionReference_71_1= 'AUTO' | lv_nextTransactionReference_71_2= RULE_STRING ) ) ) )? )? (otherlv_72= 'associatedVersions:' ( ( ruleVersionRef ) ) (otherlv_74= ';' ( ( ruleVersionRef ) ) )* )? otherlv_76= '}' )?
            int alt37=2;
            int LA37_0 = input.LA(1);

            if ( (LA37_0==42) ) {
                alt37=1;
            }
            switch (alt37) {
                case 1 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:740:5: otherlv_64= 'Relationship' otherlv_65= '{' (otherlv_66= 'nextVersion:' ( ( ruleVersionRef ) ) (otherlv_68= 'nextVersionFunction:' ( (lv_nextVersionFunction_69_0= ruleFunction ) ) )? (otherlv_70= 'nextVersionTransactionReference:' ( ( (lv_nextTransactionReference_71_1= 'AUTO' | lv_nextTransactionReference_71_2= RULE_STRING ) ) ) )? )? (otherlv_72= 'associatedVersions:' ( ( ruleVersionRef ) ) (otherlv_74= ';' ( ( ruleVersionRef ) ) )* )? otherlv_76= '}'
                    {
                    otherlv_64=(Token)match(input,42,FOLLOW_42_in_ruleVersion1252); 

                        	newLeafNode(otherlv_64, grammarAccess.getVersionAccess().getRelationshipKeyword_11_0());
                        
                    otherlv_65=(Token)match(input,19,FOLLOW_19_in_ruleVersion1264); 

                        	newLeafNode(otherlv_65, grammarAccess.getVersionAccess().getLeftCurlyBracketKeyword_11_1());
                        
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:748:1: (otherlv_66= 'nextVersion:' ( ( ruleVersionRef ) ) (otherlv_68= 'nextVersionFunction:' ( (lv_nextVersionFunction_69_0= ruleFunction ) ) )? (otherlv_70= 'nextVersionTransactionReference:' ( ( (lv_nextTransactionReference_71_1= 'AUTO' | lv_nextTransactionReference_71_2= RULE_STRING ) ) ) )? )?
                    int alt34=2;
                    int LA34_0 = input.LA(1);

                    if ( (LA34_0==43) ) {
                        alt34=1;
                    }
                    switch (alt34) {
                        case 1 :
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:748:3: otherlv_66= 'nextVersion:' ( ( ruleVersionRef ) ) (otherlv_68= 'nextVersionFunction:' ( (lv_nextVersionFunction_69_0= ruleFunction ) ) )? (otherlv_70= 'nextVersionTransactionReference:' ( ( (lv_nextTransactionReference_71_1= 'AUTO' | lv_nextTransactionReference_71_2= RULE_STRING ) ) ) )?
                            {
                            otherlv_66=(Token)match(input,43,FOLLOW_43_in_ruleVersion1277); 

                                	newLeafNode(otherlv_66, grammarAccess.getVersionAccess().getNextVersionKeyword_11_2_0());
                                
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:752:1: ( ( ruleVersionRef ) )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:753:1: ( ruleVersionRef )
                            {
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:753:1: ( ruleVersionRef )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:754:3: ruleVersionRef
                            {

                            			if (current==null) {
                            	            current = createModelElement(grammarAccess.getVersionRule());
                            	        }
                                    
                             
                            	        newCompositeNode(grammarAccess.getVersionAccess().getNextVersionVersionCrossReference_11_2_1_0()); 
                            	    
                            pushFollow(FOLLOW_ruleVersionRef_in_ruleVersion1300);
                            ruleVersionRef();

                            state._fsp--;

                             
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }

                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:767:2: (otherlv_68= 'nextVersionFunction:' ( (lv_nextVersionFunction_69_0= ruleFunction ) ) )?
                            int alt31=2;
                            int LA31_0 = input.LA(1);

                            if ( (LA31_0==44) ) {
                                alt31=1;
                            }
                            switch (alt31) {
                                case 1 :
                                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:767:4: otherlv_68= 'nextVersionFunction:' ( (lv_nextVersionFunction_69_0= ruleFunction ) )
                                    {
                                    otherlv_68=(Token)match(input,44,FOLLOW_44_in_ruleVersion1313); 

                                        	newLeafNode(otherlv_68, grammarAccess.getVersionAccess().getNextVersionFunctionKeyword_11_2_2_0());
                                        
                                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:771:1: ( (lv_nextVersionFunction_69_0= ruleFunction ) )
                                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:772:1: (lv_nextVersionFunction_69_0= ruleFunction )
                                    {
                                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:772:1: (lv_nextVersionFunction_69_0= ruleFunction )
                                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:773:3: lv_nextVersionFunction_69_0= ruleFunction
                                    {
                                     
                                    	        newCompositeNode(grammarAccess.getVersionAccess().getNextVersionFunctionFunctionEnumRuleCall_11_2_2_1_0()); 
                                    	    
                                    pushFollow(FOLLOW_ruleFunction_in_ruleVersion1334);
                                    lv_nextVersionFunction_69_0=ruleFunction();

                                    state._fsp--;


                                    	        if (current==null) {
                                    	            current = createModelElementForParent(grammarAccess.getVersionRule());
                                    	        }
                                           		set(
                                           			current, 
                                           			"nextVersionFunction",
                                            		lv_nextVersionFunction_69_0, 
                                            		"Function");
                                    	        afterParserOrEnumRuleCall();
                                    	    

                                    }


                                    }


                                    }
                                    break;

                            }

                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:789:4: (otherlv_70= 'nextVersionTransactionReference:' ( ( (lv_nextTransactionReference_71_1= 'AUTO' | lv_nextTransactionReference_71_2= RULE_STRING ) ) ) )?
                            int alt33=2;
                            int LA33_0 = input.LA(1);

                            if ( (LA33_0==45) ) {
                                alt33=1;
                            }
                            switch (alt33) {
                                case 1 :
                                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:789:6: otherlv_70= 'nextVersionTransactionReference:' ( ( (lv_nextTransactionReference_71_1= 'AUTO' | lv_nextTransactionReference_71_2= RULE_STRING ) ) )
                                    {
                                    otherlv_70=(Token)match(input,45,FOLLOW_45_in_ruleVersion1349); 

                                        	newLeafNode(otherlv_70, grammarAccess.getVersionAccess().getNextVersionTransactionReferenceKeyword_11_2_3_0());
                                        
                                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:793:1: ( ( (lv_nextTransactionReference_71_1= 'AUTO' | lv_nextTransactionReference_71_2= RULE_STRING ) ) )
                                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:794:1: ( (lv_nextTransactionReference_71_1= 'AUTO' | lv_nextTransactionReference_71_2= RULE_STRING ) )
                                    {
                                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:794:1: ( (lv_nextTransactionReference_71_1= 'AUTO' | lv_nextTransactionReference_71_2= RULE_STRING ) )
                                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:795:1: (lv_nextTransactionReference_71_1= 'AUTO' | lv_nextTransactionReference_71_2= RULE_STRING )
                                    {
                                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:795:1: (lv_nextTransactionReference_71_1= 'AUTO' | lv_nextTransactionReference_71_2= RULE_STRING )
                                    int alt32=2;
                                    int LA32_0 = input.LA(1);

                                    if ( (LA32_0==46) ) {
                                        alt32=1;
                                    }
                                    else if ( (LA32_0==RULE_STRING) ) {
                                        alt32=2;
                                    }
                                    else {
                                        NoViableAltException nvae =
                                            new NoViableAltException("", 32, 0, input);

                                        throw nvae;
                                    }
                                    switch (alt32) {
                                        case 1 :
                                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:796:3: lv_nextTransactionReference_71_1= 'AUTO'
                                            {
                                            lv_nextTransactionReference_71_1=(Token)match(input,46,FOLLOW_46_in_ruleVersion1369); 

                                                    newLeafNode(lv_nextTransactionReference_71_1, grammarAccess.getVersionAccess().getNextTransactionReferenceAUTOKeyword_11_2_3_1_0_0());
                                                

                                            	        if (current==null) {
                                            	            current = createModelElement(grammarAccess.getVersionRule());
                                            	        }
                                                   		setWithLastConsumed(current, "nextTransactionReference", lv_nextTransactionReference_71_1, null);
                                            	    

                                            }
                                            break;
                                        case 2 :
                                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:808:8: lv_nextTransactionReference_71_2= RULE_STRING
                                            {
                                            lv_nextTransactionReference_71_2=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleVersion1397); 

                                            			newLeafNode(lv_nextTransactionReference_71_2, grammarAccess.getVersionAccess().getNextTransactionReferenceSTRINGTerminalRuleCall_11_2_3_1_0_1()); 
                                            		

                                            	        if (current==null) {
                                            	            current = createModelElement(grammarAccess.getVersionRule());
                                            	        }
                                                   		setWithLastConsumed(
                                                   			current, 
                                                   			"nextTransactionReference",
                                                    		lv_nextTransactionReference_71_2, 
                                                    		"STRING");
                                            	    

                                            }
                                            break;

                                    }


                                    }


                                    }


                                    }
                                    break;

                            }


                            }
                            break;

                    }

                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:826:6: (otherlv_72= 'associatedVersions:' ( ( ruleVersionRef ) ) (otherlv_74= ';' ( ( ruleVersionRef ) ) )* )?
                    int alt36=2;
                    int LA36_0 = input.LA(1);

                    if ( (LA36_0==47) ) {
                        alt36=1;
                    }
                    switch (alt36) {
                        case 1 :
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:826:8: otherlv_72= 'associatedVersions:' ( ( ruleVersionRef ) ) (otherlv_74= ';' ( ( ruleVersionRef ) ) )*
                            {
                            otherlv_72=(Token)match(input,47,FOLLOW_47_in_ruleVersion1422); 

                                	newLeafNode(otherlv_72, grammarAccess.getVersionAccess().getAssociatedVersionsKeyword_11_3_0());
                                
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:830:1: ( ( ruleVersionRef ) )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:831:1: ( ruleVersionRef )
                            {
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:831:1: ( ruleVersionRef )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:832:3: ruleVersionRef
                            {

                            			if (current==null) {
                            	            current = createModelElement(grammarAccess.getVersionRule());
                            	        }
                                    
                             
                            	        newCompositeNode(grammarAccess.getVersionAccess().getAssociatedVersionsVersionCrossReference_11_3_1_0()); 
                            	    
                            pushFollow(FOLLOW_ruleVersionRef_in_ruleVersion1445);
                            ruleVersionRef();

                            state._fsp--;

                             
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }

                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:845:2: (otherlv_74= ';' ( ( ruleVersionRef ) ) )*
                            loop35:
                            do {
                                int alt35=2;
                                int LA35_0 = input.LA(1);

                                if ( (LA35_0==24) ) {
                                    alt35=1;
                                }


                                switch (alt35) {
                            	case 1 :
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:845:4: otherlv_74= ';' ( ( ruleVersionRef ) )
                            	    {
                            	    otherlv_74=(Token)match(input,24,FOLLOW_24_in_ruleVersion1458); 

                            	        	newLeafNode(otherlv_74, grammarAccess.getVersionAccess().getSemicolonKeyword_11_3_2_0());
                            	        
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:849:1: ( ( ruleVersionRef ) )
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:850:1: ( ruleVersionRef )
                            	    {
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:850:1: ( ruleVersionRef )
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:851:3: ruleVersionRef
                            	    {

                            	    			if (current==null) {
                            	    	            current = createModelElement(grammarAccess.getVersionRule());
                            	    	        }
                            	            
                            	     
                            	    	        newCompositeNode(grammarAccess.getVersionAccess().getAssociatedVersionsVersionCrossReference_11_3_2_1_0()); 
                            	    	    
                            	    pushFollow(FOLLOW_ruleVersionRef_in_ruleVersion1481);
                            	    ruleVersionRef();

                            	    state._fsp--;

                            	     
                            	    	        afterParserOrEnumRuleCall();
                            	    	    

                            	    }


                            	    }


                            	    }
                            	    break;

                            	default :
                            	    break loop35;
                                }
                            } while (true);


                            }
                            break;

                    }

                    otherlv_76=(Token)match(input,31,FOLLOW_31_in_ruleVersion1497); 

                        	newLeafNode(otherlv_76, grammarAccess.getVersionAccess().getRightCurlyBracketKeyword_11_4());
                        

                    }
                    break;

            }

            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:868:3: (otherlv_77= 'API' otherlv_78= '{' (otherlv_79= 'includeVersionControl:' ( (lv_includeVersionControl_80_0= ruleYesNo ) ) )? (otherlv_81= 'authorizationRoutines:' ( (lv_authorizationRoutines_82_0= ruleRoutine ) ) (otherlv_83= ';' ( (lv_authorizationRoutines_84_0= ruleRoutine ) ) )* )? (otherlv_85= 'authorizationRoutinesAfterCommit:' ( (lv_authorizationRoutinesAfterCommit_86_0= ruleRoutine ) ) (otherlv_87= ';' ( (lv_authorizationRoutinesAfterCommit_88_0= ruleRoutine ) ) )* )? (otherlv_89= 'inputRoutines:' ( (lv_inputRoutines_90_0= ruleRoutine ) ) (otherlv_91= ';' ( (lv_inputRoutines_92_0= ruleRoutine ) ) )* )? (otherlv_93= 'inputRoutinesAfterCommit:' ( (lv_inputRoutinesAfterCommit_94_0= ruleRoutine ) ) (otherlv_95= ';' ( (lv_inputRoutinesAfterCommit_96_0= ruleRoutine ) ) )* )? (otherlv_97= 'keyValidationRoutines:' ( (lv_keyValidationRoutines_98_0= ruleRoutine ) ) (otherlv_99= ';' ( (lv_keyValidationRoutines_100_0= ruleRoutine ) ) )* )? (otherlv_101= 'preProcessValidationRoutines:' ( (lv_preProcessValidationRoutines_102_0= ruleRoutine ) ) (otherlv_103= ';' ( (lv_preProcessValidationRoutines_104_0= ruleRoutine ) ) )* )? (otherlv_105= 'webValidationRoutines:' ( (lv_webValidationRoutines_106_0= ruleRoutine ) ) (otherlv_107= ';' ( (lv_webValidationRoutines_108_0= ruleRoutine ) ) )* )? otherlv_109= '}' )?
            int alt53=2;
            int LA53_0 = input.LA(1);

            if ( (LA53_0==48) ) {
                alt53=1;
            }
            switch (alt53) {
                case 1 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:868:5: otherlv_77= 'API' otherlv_78= '{' (otherlv_79= 'includeVersionControl:' ( (lv_includeVersionControl_80_0= ruleYesNo ) ) )? (otherlv_81= 'authorizationRoutines:' ( (lv_authorizationRoutines_82_0= ruleRoutine ) ) (otherlv_83= ';' ( (lv_authorizationRoutines_84_0= ruleRoutine ) ) )* )? (otherlv_85= 'authorizationRoutinesAfterCommit:' ( (lv_authorizationRoutinesAfterCommit_86_0= ruleRoutine ) ) (otherlv_87= ';' ( (lv_authorizationRoutinesAfterCommit_88_0= ruleRoutine ) ) )* )? (otherlv_89= 'inputRoutines:' ( (lv_inputRoutines_90_0= ruleRoutine ) ) (otherlv_91= ';' ( (lv_inputRoutines_92_0= ruleRoutine ) ) )* )? (otherlv_93= 'inputRoutinesAfterCommit:' ( (lv_inputRoutinesAfterCommit_94_0= ruleRoutine ) ) (otherlv_95= ';' ( (lv_inputRoutinesAfterCommit_96_0= ruleRoutine ) ) )* )? (otherlv_97= 'keyValidationRoutines:' ( (lv_keyValidationRoutines_98_0= ruleRoutine ) ) (otherlv_99= ';' ( (lv_keyValidationRoutines_100_0= ruleRoutine ) ) )* )? (otherlv_101= 'preProcessValidationRoutines:' ( (lv_preProcessValidationRoutines_102_0= ruleRoutine ) ) (otherlv_103= ';' ( (lv_preProcessValidationRoutines_104_0= ruleRoutine ) ) )* )? (otherlv_105= 'webValidationRoutines:' ( (lv_webValidationRoutines_106_0= ruleRoutine ) ) (otherlv_107= ';' ( (lv_webValidationRoutines_108_0= ruleRoutine ) ) )* )? otherlv_109= '}'
                    {
                    otherlv_77=(Token)match(input,48,FOLLOW_48_in_ruleVersion1512); 

                        	newLeafNode(otherlv_77, grammarAccess.getVersionAccess().getAPIKeyword_12_0());
                        
                    otherlv_78=(Token)match(input,19,FOLLOW_19_in_ruleVersion1524); 

                        	newLeafNode(otherlv_78, grammarAccess.getVersionAccess().getLeftCurlyBracketKeyword_12_1());
                        
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:876:1: (otherlv_79= 'includeVersionControl:' ( (lv_includeVersionControl_80_0= ruleYesNo ) ) )?
                    int alt38=2;
                    int LA38_0 = input.LA(1);

                    if ( (LA38_0==49) ) {
                        alt38=1;
                    }
                    switch (alt38) {
                        case 1 :
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:876:3: otherlv_79= 'includeVersionControl:' ( (lv_includeVersionControl_80_0= ruleYesNo ) )
                            {
                            otherlv_79=(Token)match(input,49,FOLLOW_49_in_ruleVersion1537); 

                                	newLeafNode(otherlv_79, grammarAccess.getVersionAccess().getIncludeVersionControlKeyword_12_2_0());
                                
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:880:1: ( (lv_includeVersionControl_80_0= ruleYesNo ) )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:881:1: (lv_includeVersionControl_80_0= ruleYesNo )
                            {
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:881:1: (lv_includeVersionControl_80_0= ruleYesNo )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:882:3: lv_includeVersionControl_80_0= ruleYesNo
                            {
                             
                            	        newCompositeNode(grammarAccess.getVersionAccess().getIncludeVersionControlYesNoEnumRuleCall_12_2_1_0()); 
                            	    
                            pushFollow(FOLLOW_ruleYesNo_in_ruleVersion1558);
                            lv_includeVersionControl_80_0=ruleYesNo();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getVersionRule());
                            	        }
                                   		set(
                                   			current, 
                                   			"includeVersionControl",
                                    		lv_includeVersionControl_80_0, 
                                    		"YesNo");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }


                            }
                            break;

                    }

                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:898:4: (otherlv_81= 'authorizationRoutines:' ( (lv_authorizationRoutines_82_0= ruleRoutine ) ) (otherlv_83= ';' ( (lv_authorizationRoutines_84_0= ruleRoutine ) ) )* )?
                    int alt40=2;
                    int LA40_0 = input.LA(1);

                    if ( (LA40_0==50) ) {
                        alt40=1;
                    }
                    switch (alt40) {
                        case 1 :
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:898:6: otherlv_81= 'authorizationRoutines:' ( (lv_authorizationRoutines_82_0= ruleRoutine ) ) (otherlv_83= ';' ( (lv_authorizationRoutines_84_0= ruleRoutine ) ) )*
                            {
                            otherlv_81=(Token)match(input,50,FOLLOW_50_in_ruleVersion1573); 

                                	newLeafNode(otherlv_81, grammarAccess.getVersionAccess().getAuthorizationRoutinesKeyword_12_3_0());
                                
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:902:1: ( (lv_authorizationRoutines_82_0= ruleRoutine ) )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:903:1: (lv_authorizationRoutines_82_0= ruleRoutine )
                            {
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:903:1: (lv_authorizationRoutines_82_0= ruleRoutine )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:904:3: lv_authorizationRoutines_82_0= ruleRoutine
                            {
                             
                            	        newCompositeNode(grammarAccess.getVersionAccess().getAuthorizationRoutinesRoutineParserRuleCall_12_3_1_0()); 
                            	    
                            pushFollow(FOLLOW_ruleRoutine_in_ruleVersion1594);
                            lv_authorizationRoutines_82_0=ruleRoutine();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getVersionRule());
                            	        }
                                   		add(
                                   			current, 
                                   			"authorizationRoutines",
                                    		lv_authorizationRoutines_82_0, 
                                    		"Routine");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }

                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:920:2: (otherlv_83= ';' ( (lv_authorizationRoutines_84_0= ruleRoutine ) ) )*
                            loop39:
                            do {
                                int alt39=2;
                                int LA39_0 = input.LA(1);

                                if ( (LA39_0==24) ) {
                                    alt39=1;
                                }


                                switch (alt39) {
                            	case 1 :
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:920:4: otherlv_83= ';' ( (lv_authorizationRoutines_84_0= ruleRoutine ) )
                            	    {
                            	    otherlv_83=(Token)match(input,24,FOLLOW_24_in_ruleVersion1607); 

                            	        	newLeafNode(otherlv_83, grammarAccess.getVersionAccess().getSemicolonKeyword_12_3_2_0());
                            	        
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:924:1: ( (lv_authorizationRoutines_84_0= ruleRoutine ) )
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:925:1: (lv_authorizationRoutines_84_0= ruleRoutine )
                            	    {
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:925:1: (lv_authorizationRoutines_84_0= ruleRoutine )
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:926:3: lv_authorizationRoutines_84_0= ruleRoutine
                            	    {
                            	     
                            	    	        newCompositeNode(grammarAccess.getVersionAccess().getAuthorizationRoutinesRoutineParserRuleCall_12_3_2_1_0()); 
                            	    	    
                            	    pushFollow(FOLLOW_ruleRoutine_in_ruleVersion1628);
                            	    lv_authorizationRoutines_84_0=ruleRoutine();

                            	    state._fsp--;


                            	    	        if (current==null) {
                            	    	            current = createModelElementForParent(grammarAccess.getVersionRule());
                            	    	        }
                            	           		add(
                            	           			current, 
                            	           			"authorizationRoutines",
                            	            		lv_authorizationRoutines_84_0, 
                            	            		"Routine");
                            	    	        afterParserOrEnumRuleCall();
                            	    	    

                            	    }


                            	    }


                            	    }
                            	    break;

                            	default :
                            	    break loop39;
                                }
                            } while (true);


                            }
                            break;

                    }

                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:942:6: (otherlv_85= 'authorizationRoutinesAfterCommit:' ( (lv_authorizationRoutinesAfterCommit_86_0= ruleRoutine ) ) (otherlv_87= ';' ( (lv_authorizationRoutinesAfterCommit_88_0= ruleRoutine ) ) )* )?
                    int alt42=2;
                    int LA42_0 = input.LA(1);

                    if ( (LA42_0==51) ) {
                        alt42=1;
                    }
                    switch (alt42) {
                        case 1 :
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:942:8: otherlv_85= 'authorizationRoutinesAfterCommit:' ( (lv_authorizationRoutinesAfterCommit_86_0= ruleRoutine ) ) (otherlv_87= ';' ( (lv_authorizationRoutinesAfterCommit_88_0= ruleRoutine ) ) )*
                            {
                            otherlv_85=(Token)match(input,51,FOLLOW_51_in_ruleVersion1645); 

                                	newLeafNode(otherlv_85, grammarAccess.getVersionAccess().getAuthorizationRoutinesAfterCommitKeyword_12_4_0());
                                
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:946:1: ( (lv_authorizationRoutinesAfterCommit_86_0= ruleRoutine ) )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:947:1: (lv_authorizationRoutinesAfterCommit_86_0= ruleRoutine )
                            {
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:947:1: (lv_authorizationRoutinesAfterCommit_86_0= ruleRoutine )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:948:3: lv_authorizationRoutinesAfterCommit_86_0= ruleRoutine
                            {
                             
                            	        newCompositeNode(grammarAccess.getVersionAccess().getAuthorizationRoutinesAfterCommitRoutineParserRuleCall_12_4_1_0()); 
                            	    
                            pushFollow(FOLLOW_ruleRoutine_in_ruleVersion1666);
                            lv_authorizationRoutinesAfterCommit_86_0=ruleRoutine();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getVersionRule());
                            	        }
                                   		add(
                                   			current, 
                                   			"authorizationRoutinesAfterCommit",
                                    		lv_authorizationRoutinesAfterCommit_86_0, 
                                    		"Routine");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }

                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:964:2: (otherlv_87= ';' ( (lv_authorizationRoutinesAfterCommit_88_0= ruleRoutine ) ) )*
                            loop41:
                            do {
                                int alt41=2;
                                int LA41_0 = input.LA(1);

                                if ( (LA41_0==24) ) {
                                    alt41=1;
                                }


                                switch (alt41) {
                            	case 1 :
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:964:4: otherlv_87= ';' ( (lv_authorizationRoutinesAfterCommit_88_0= ruleRoutine ) )
                            	    {
                            	    otherlv_87=(Token)match(input,24,FOLLOW_24_in_ruleVersion1679); 

                            	        	newLeafNode(otherlv_87, grammarAccess.getVersionAccess().getSemicolonKeyword_12_4_2_0());
                            	        
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:968:1: ( (lv_authorizationRoutinesAfterCommit_88_0= ruleRoutine ) )
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:969:1: (lv_authorizationRoutinesAfterCommit_88_0= ruleRoutine )
                            	    {
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:969:1: (lv_authorizationRoutinesAfterCommit_88_0= ruleRoutine )
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:970:3: lv_authorizationRoutinesAfterCommit_88_0= ruleRoutine
                            	    {
                            	     
                            	    	        newCompositeNode(grammarAccess.getVersionAccess().getAuthorizationRoutinesAfterCommitRoutineParserRuleCall_12_4_2_1_0()); 
                            	    	    
                            	    pushFollow(FOLLOW_ruleRoutine_in_ruleVersion1700);
                            	    lv_authorizationRoutinesAfterCommit_88_0=ruleRoutine();

                            	    state._fsp--;


                            	    	        if (current==null) {
                            	    	            current = createModelElementForParent(grammarAccess.getVersionRule());
                            	    	        }
                            	           		add(
                            	           			current, 
                            	           			"authorizationRoutinesAfterCommit",
                            	            		lv_authorizationRoutinesAfterCommit_88_0, 
                            	            		"Routine");
                            	    	        afterParserOrEnumRuleCall();
                            	    	    

                            	    }


                            	    }


                            	    }
                            	    break;

                            	default :
                            	    break loop41;
                                }
                            } while (true);


                            }
                            break;

                    }

                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:986:6: (otherlv_89= 'inputRoutines:' ( (lv_inputRoutines_90_0= ruleRoutine ) ) (otherlv_91= ';' ( (lv_inputRoutines_92_0= ruleRoutine ) ) )* )?
                    int alt44=2;
                    int LA44_0 = input.LA(1);

                    if ( (LA44_0==52) ) {
                        alt44=1;
                    }
                    switch (alt44) {
                        case 1 :
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:986:8: otherlv_89= 'inputRoutines:' ( (lv_inputRoutines_90_0= ruleRoutine ) ) (otherlv_91= ';' ( (lv_inputRoutines_92_0= ruleRoutine ) ) )*
                            {
                            otherlv_89=(Token)match(input,52,FOLLOW_52_in_ruleVersion1717); 

                                	newLeafNode(otherlv_89, grammarAccess.getVersionAccess().getInputRoutinesKeyword_12_5_0());
                                
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:990:1: ( (lv_inputRoutines_90_0= ruleRoutine ) )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:991:1: (lv_inputRoutines_90_0= ruleRoutine )
                            {
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:991:1: (lv_inputRoutines_90_0= ruleRoutine )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:992:3: lv_inputRoutines_90_0= ruleRoutine
                            {
                             
                            	        newCompositeNode(grammarAccess.getVersionAccess().getInputRoutinesRoutineParserRuleCall_12_5_1_0()); 
                            	    
                            pushFollow(FOLLOW_ruleRoutine_in_ruleVersion1738);
                            lv_inputRoutines_90_0=ruleRoutine();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getVersionRule());
                            	        }
                                   		add(
                                   			current, 
                                   			"inputRoutines",
                                    		lv_inputRoutines_90_0, 
                                    		"Routine");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }

                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1008:2: (otherlv_91= ';' ( (lv_inputRoutines_92_0= ruleRoutine ) ) )*
                            loop43:
                            do {
                                int alt43=2;
                                int LA43_0 = input.LA(1);

                                if ( (LA43_0==24) ) {
                                    alt43=1;
                                }


                                switch (alt43) {
                            	case 1 :
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1008:4: otherlv_91= ';' ( (lv_inputRoutines_92_0= ruleRoutine ) )
                            	    {
                            	    otherlv_91=(Token)match(input,24,FOLLOW_24_in_ruleVersion1751); 

                            	        	newLeafNode(otherlv_91, grammarAccess.getVersionAccess().getSemicolonKeyword_12_5_2_0());
                            	        
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1012:1: ( (lv_inputRoutines_92_0= ruleRoutine ) )
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1013:1: (lv_inputRoutines_92_0= ruleRoutine )
                            	    {
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1013:1: (lv_inputRoutines_92_0= ruleRoutine )
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1014:3: lv_inputRoutines_92_0= ruleRoutine
                            	    {
                            	     
                            	    	        newCompositeNode(grammarAccess.getVersionAccess().getInputRoutinesRoutineParserRuleCall_12_5_2_1_0()); 
                            	    	    
                            	    pushFollow(FOLLOW_ruleRoutine_in_ruleVersion1772);
                            	    lv_inputRoutines_92_0=ruleRoutine();

                            	    state._fsp--;


                            	    	        if (current==null) {
                            	    	            current = createModelElementForParent(grammarAccess.getVersionRule());
                            	    	        }
                            	           		add(
                            	           			current, 
                            	           			"inputRoutines",
                            	            		lv_inputRoutines_92_0, 
                            	            		"Routine");
                            	    	        afterParserOrEnumRuleCall();
                            	    	    

                            	    }


                            	    }


                            	    }
                            	    break;

                            	default :
                            	    break loop43;
                                }
                            } while (true);


                            }
                            break;

                    }

                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1030:6: (otherlv_93= 'inputRoutinesAfterCommit:' ( (lv_inputRoutinesAfterCommit_94_0= ruleRoutine ) ) (otherlv_95= ';' ( (lv_inputRoutinesAfterCommit_96_0= ruleRoutine ) ) )* )?
                    int alt46=2;
                    int LA46_0 = input.LA(1);

                    if ( (LA46_0==53) ) {
                        alt46=1;
                    }
                    switch (alt46) {
                        case 1 :
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1030:8: otherlv_93= 'inputRoutinesAfterCommit:' ( (lv_inputRoutinesAfterCommit_94_0= ruleRoutine ) ) (otherlv_95= ';' ( (lv_inputRoutinesAfterCommit_96_0= ruleRoutine ) ) )*
                            {
                            otherlv_93=(Token)match(input,53,FOLLOW_53_in_ruleVersion1789); 

                                	newLeafNode(otherlv_93, grammarAccess.getVersionAccess().getInputRoutinesAfterCommitKeyword_12_6_0());
                                
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1034:1: ( (lv_inputRoutinesAfterCommit_94_0= ruleRoutine ) )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1035:1: (lv_inputRoutinesAfterCommit_94_0= ruleRoutine )
                            {
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1035:1: (lv_inputRoutinesAfterCommit_94_0= ruleRoutine )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1036:3: lv_inputRoutinesAfterCommit_94_0= ruleRoutine
                            {
                             
                            	        newCompositeNode(grammarAccess.getVersionAccess().getInputRoutinesAfterCommitRoutineParserRuleCall_12_6_1_0()); 
                            	    
                            pushFollow(FOLLOW_ruleRoutine_in_ruleVersion1810);
                            lv_inputRoutinesAfterCommit_94_0=ruleRoutine();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getVersionRule());
                            	        }
                                   		add(
                                   			current, 
                                   			"inputRoutinesAfterCommit",
                                    		lv_inputRoutinesAfterCommit_94_0, 
                                    		"Routine");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }

                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1052:2: (otherlv_95= ';' ( (lv_inputRoutinesAfterCommit_96_0= ruleRoutine ) ) )*
                            loop45:
                            do {
                                int alt45=2;
                                int LA45_0 = input.LA(1);

                                if ( (LA45_0==24) ) {
                                    alt45=1;
                                }


                                switch (alt45) {
                            	case 1 :
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1052:4: otherlv_95= ';' ( (lv_inputRoutinesAfterCommit_96_0= ruleRoutine ) )
                            	    {
                            	    otherlv_95=(Token)match(input,24,FOLLOW_24_in_ruleVersion1823); 

                            	        	newLeafNode(otherlv_95, grammarAccess.getVersionAccess().getSemicolonKeyword_12_6_2_0());
                            	        
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1056:1: ( (lv_inputRoutinesAfterCommit_96_0= ruleRoutine ) )
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1057:1: (lv_inputRoutinesAfterCommit_96_0= ruleRoutine )
                            	    {
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1057:1: (lv_inputRoutinesAfterCommit_96_0= ruleRoutine )
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1058:3: lv_inputRoutinesAfterCommit_96_0= ruleRoutine
                            	    {
                            	     
                            	    	        newCompositeNode(grammarAccess.getVersionAccess().getInputRoutinesAfterCommitRoutineParserRuleCall_12_6_2_1_0()); 
                            	    	    
                            	    pushFollow(FOLLOW_ruleRoutine_in_ruleVersion1844);
                            	    lv_inputRoutinesAfterCommit_96_0=ruleRoutine();

                            	    state._fsp--;


                            	    	        if (current==null) {
                            	    	            current = createModelElementForParent(grammarAccess.getVersionRule());
                            	    	        }
                            	           		add(
                            	           			current, 
                            	           			"inputRoutinesAfterCommit",
                            	            		lv_inputRoutinesAfterCommit_96_0, 
                            	            		"Routine");
                            	    	        afterParserOrEnumRuleCall();
                            	    	    

                            	    }


                            	    }


                            	    }
                            	    break;

                            	default :
                            	    break loop45;
                                }
                            } while (true);


                            }
                            break;

                    }

                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1074:6: (otherlv_97= 'keyValidationRoutines:' ( (lv_keyValidationRoutines_98_0= ruleRoutine ) ) (otherlv_99= ';' ( (lv_keyValidationRoutines_100_0= ruleRoutine ) ) )* )?
                    int alt48=2;
                    int LA48_0 = input.LA(1);

                    if ( (LA48_0==54) ) {
                        alt48=1;
                    }
                    switch (alt48) {
                        case 1 :
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1074:8: otherlv_97= 'keyValidationRoutines:' ( (lv_keyValidationRoutines_98_0= ruleRoutine ) ) (otherlv_99= ';' ( (lv_keyValidationRoutines_100_0= ruleRoutine ) ) )*
                            {
                            otherlv_97=(Token)match(input,54,FOLLOW_54_in_ruleVersion1861); 

                                	newLeafNode(otherlv_97, grammarAccess.getVersionAccess().getKeyValidationRoutinesKeyword_12_7_0());
                                
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1078:1: ( (lv_keyValidationRoutines_98_0= ruleRoutine ) )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1079:1: (lv_keyValidationRoutines_98_0= ruleRoutine )
                            {
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1079:1: (lv_keyValidationRoutines_98_0= ruleRoutine )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1080:3: lv_keyValidationRoutines_98_0= ruleRoutine
                            {
                             
                            	        newCompositeNode(grammarAccess.getVersionAccess().getKeyValidationRoutinesRoutineParserRuleCall_12_7_1_0()); 
                            	    
                            pushFollow(FOLLOW_ruleRoutine_in_ruleVersion1882);
                            lv_keyValidationRoutines_98_0=ruleRoutine();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getVersionRule());
                            	        }
                                   		add(
                                   			current, 
                                   			"keyValidationRoutines",
                                    		lv_keyValidationRoutines_98_0, 
                                    		"Routine");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }

                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1096:2: (otherlv_99= ';' ( (lv_keyValidationRoutines_100_0= ruleRoutine ) ) )*
                            loop47:
                            do {
                                int alt47=2;
                                int LA47_0 = input.LA(1);

                                if ( (LA47_0==24) ) {
                                    alt47=1;
                                }


                                switch (alt47) {
                            	case 1 :
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1096:4: otherlv_99= ';' ( (lv_keyValidationRoutines_100_0= ruleRoutine ) )
                            	    {
                            	    otherlv_99=(Token)match(input,24,FOLLOW_24_in_ruleVersion1895); 

                            	        	newLeafNode(otherlv_99, grammarAccess.getVersionAccess().getSemicolonKeyword_12_7_2_0());
                            	        
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1100:1: ( (lv_keyValidationRoutines_100_0= ruleRoutine ) )
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1101:1: (lv_keyValidationRoutines_100_0= ruleRoutine )
                            	    {
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1101:1: (lv_keyValidationRoutines_100_0= ruleRoutine )
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1102:3: lv_keyValidationRoutines_100_0= ruleRoutine
                            	    {
                            	     
                            	    	        newCompositeNode(grammarAccess.getVersionAccess().getKeyValidationRoutinesRoutineParserRuleCall_12_7_2_1_0()); 
                            	    	    
                            	    pushFollow(FOLLOW_ruleRoutine_in_ruleVersion1916);
                            	    lv_keyValidationRoutines_100_0=ruleRoutine();

                            	    state._fsp--;


                            	    	        if (current==null) {
                            	    	            current = createModelElementForParent(grammarAccess.getVersionRule());
                            	    	        }
                            	           		add(
                            	           			current, 
                            	           			"keyValidationRoutines",
                            	            		lv_keyValidationRoutines_100_0, 
                            	            		"Routine");
                            	    	        afterParserOrEnumRuleCall();
                            	    	    

                            	    }


                            	    }


                            	    }
                            	    break;

                            	default :
                            	    break loop47;
                                }
                            } while (true);


                            }
                            break;

                    }

                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1118:6: (otherlv_101= 'preProcessValidationRoutines:' ( (lv_preProcessValidationRoutines_102_0= ruleRoutine ) ) (otherlv_103= ';' ( (lv_preProcessValidationRoutines_104_0= ruleRoutine ) ) )* )?
                    int alt50=2;
                    int LA50_0 = input.LA(1);

                    if ( (LA50_0==55) ) {
                        alt50=1;
                    }
                    switch (alt50) {
                        case 1 :
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1118:8: otherlv_101= 'preProcessValidationRoutines:' ( (lv_preProcessValidationRoutines_102_0= ruleRoutine ) ) (otherlv_103= ';' ( (lv_preProcessValidationRoutines_104_0= ruleRoutine ) ) )*
                            {
                            otherlv_101=(Token)match(input,55,FOLLOW_55_in_ruleVersion1933); 

                                	newLeafNode(otherlv_101, grammarAccess.getVersionAccess().getPreProcessValidationRoutinesKeyword_12_8_0());
                                
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1122:1: ( (lv_preProcessValidationRoutines_102_0= ruleRoutine ) )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1123:1: (lv_preProcessValidationRoutines_102_0= ruleRoutine )
                            {
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1123:1: (lv_preProcessValidationRoutines_102_0= ruleRoutine )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1124:3: lv_preProcessValidationRoutines_102_0= ruleRoutine
                            {
                             
                            	        newCompositeNode(grammarAccess.getVersionAccess().getPreProcessValidationRoutinesRoutineParserRuleCall_12_8_1_0()); 
                            	    
                            pushFollow(FOLLOW_ruleRoutine_in_ruleVersion1954);
                            lv_preProcessValidationRoutines_102_0=ruleRoutine();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getVersionRule());
                            	        }
                                   		add(
                                   			current, 
                                   			"preProcessValidationRoutines",
                                    		lv_preProcessValidationRoutines_102_0, 
                                    		"Routine");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }

                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1140:2: (otherlv_103= ';' ( (lv_preProcessValidationRoutines_104_0= ruleRoutine ) ) )*
                            loop49:
                            do {
                                int alt49=2;
                                int LA49_0 = input.LA(1);

                                if ( (LA49_0==24) ) {
                                    alt49=1;
                                }


                                switch (alt49) {
                            	case 1 :
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1140:4: otherlv_103= ';' ( (lv_preProcessValidationRoutines_104_0= ruleRoutine ) )
                            	    {
                            	    otherlv_103=(Token)match(input,24,FOLLOW_24_in_ruleVersion1967); 

                            	        	newLeafNode(otherlv_103, grammarAccess.getVersionAccess().getSemicolonKeyword_12_8_2_0());
                            	        
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1144:1: ( (lv_preProcessValidationRoutines_104_0= ruleRoutine ) )
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1145:1: (lv_preProcessValidationRoutines_104_0= ruleRoutine )
                            	    {
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1145:1: (lv_preProcessValidationRoutines_104_0= ruleRoutine )
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1146:3: lv_preProcessValidationRoutines_104_0= ruleRoutine
                            	    {
                            	     
                            	    	        newCompositeNode(grammarAccess.getVersionAccess().getPreProcessValidationRoutinesRoutineParserRuleCall_12_8_2_1_0()); 
                            	    	    
                            	    pushFollow(FOLLOW_ruleRoutine_in_ruleVersion1988);
                            	    lv_preProcessValidationRoutines_104_0=ruleRoutine();

                            	    state._fsp--;


                            	    	        if (current==null) {
                            	    	            current = createModelElementForParent(grammarAccess.getVersionRule());
                            	    	        }
                            	           		add(
                            	           			current, 
                            	           			"preProcessValidationRoutines",
                            	            		lv_preProcessValidationRoutines_104_0, 
                            	            		"Routine");
                            	    	        afterParserOrEnumRuleCall();
                            	    	    

                            	    }


                            	    }


                            	    }
                            	    break;

                            	default :
                            	    break loop49;
                                }
                            } while (true);


                            }
                            break;

                    }

                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1162:6: (otherlv_105= 'webValidationRoutines:' ( (lv_webValidationRoutines_106_0= ruleRoutine ) ) (otherlv_107= ';' ( (lv_webValidationRoutines_108_0= ruleRoutine ) ) )* )?
                    int alt52=2;
                    int LA52_0 = input.LA(1);

                    if ( (LA52_0==56) ) {
                        alt52=1;
                    }
                    switch (alt52) {
                        case 1 :
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1162:8: otherlv_105= 'webValidationRoutines:' ( (lv_webValidationRoutines_106_0= ruleRoutine ) ) (otherlv_107= ';' ( (lv_webValidationRoutines_108_0= ruleRoutine ) ) )*
                            {
                            otherlv_105=(Token)match(input,56,FOLLOW_56_in_ruleVersion2005); 

                                	newLeafNode(otherlv_105, grammarAccess.getVersionAccess().getWebValidationRoutinesKeyword_12_9_0());
                                
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1166:1: ( (lv_webValidationRoutines_106_0= ruleRoutine ) )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1167:1: (lv_webValidationRoutines_106_0= ruleRoutine )
                            {
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1167:1: (lv_webValidationRoutines_106_0= ruleRoutine )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1168:3: lv_webValidationRoutines_106_0= ruleRoutine
                            {
                             
                            	        newCompositeNode(grammarAccess.getVersionAccess().getWebValidationRoutinesRoutineParserRuleCall_12_9_1_0()); 
                            	    
                            pushFollow(FOLLOW_ruleRoutine_in_ruleVersion2026);
                            lv_webValidationRoutines_106_0=ruleRoutine();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getVersionRule());
                            	        }
                                   		add(
                                   			current, 
                                   			"webValidationRoutines",
                                    		lv_webValidationRoutines_106_0, 
                                    		"Routine");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }

                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1184:2: (otherlv_107= ';' ( (lv_webValidationRoutines_108_0= ruleRoutine ) ) )*
                            loop51:
                            do {
                                int alt51=2;
                                int LA51_0 = input.LA(1);

                                if ( (LA51_0==24) ) {
                                    alt51=1;
                                }


                                switch (alt51) {
                            	case 1 :
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1184:4: otherlv_107= ';' ( (lv_webValidationRoutines_108_0= ruleRoutine ) )
                            	    {
                            	    otherlv_107=(Token)match(input,24,FOLLOW_24_in_ruleVersion2039); 

                            	        	newLeafNode(otherlv_107, grammarAccess.getVersionAccess().getSemicolonKeyword_12_9_2_0());
                            	        
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1188:1: ( (lv_webValidationRoutines_108_0= ruleRoutine ) )
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1189:1: (lv_webValidationRoutines_108_0= ruleRoutine )
                            	    {
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1189:1: (lv_webValidationRoutines_108_0= ruleRoutine )
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1190:3: lv_webValidationRoutines_108_0= ruleRoutine
                            	    {
                            	     
                            	    	        newCompositeNode(grammarAccess.getVersionAccess().getWebValidationRoutinesRoutineParserRuleCall_12_9_2_1_0()); 
                            	    	    
                            	    pushFollow(FOLLOW_ruleRoutine_in_ruleVersion2060);
                            	    lv_webValidationRoutines_108_0=ruleRoutine();

                            	    state._fsp--;


                            	    	        if (current==null) {
                            	    	            current = createModelElementForParent(grammarAccess.getVersionRule());
                            	    	        }
                            	           		add(
                            	           			current, 
                            	           			"webValidationRoutines",
                            	            		lv_webValidationRoutines_108_0, 
                            	            		"Routine");
                            	    	        afterParserOrEnumRuleCall();
                            	    	    

                            	    }


                            	    }


                            	    }
                            	    break;

                            	default :
                            	    break loop51;
                                }
                            } while (true);


                            }
                            break;

                    }

                    otherlv_109=(Token)match(input,31,FOLLOW_31_in_ruleVersion2076); 

                        	newLeafNode(otherlv_109, grammarAccess.getVersionAccess().getRightCurlyBracketKeyword_12_10());
                        

                    }
                    break;

            }

            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1210:3: (otherlv_110= 'IB' otherlv_111= '{' (otherlv_112= 'confirmVersion:' ( ( ruleVersionRef ) ) )? (otherlv_114= 'previewVersion:' ( ( ruleVersionRef ) ) )? (otherlv_116= 'challengeResponse:' ( (lv_challengeResponse_117_0= RULE_STRING ) ) )? otherlv_118= '}' )?
            int alt57=2;
            int LA57_0 = input.LA(1);

            if ( (LA57_0==57) ) {
                alt57=1;
            }
            switch (alt57) {
                case 1 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1210:5: otherlv_110= 'IB' otherlv_111= '{' (otherlv_112= 'confirmVersion:' ( ( ruleVersionRef ) ) )? (otherlv_114= 'previewVersion:' ( ( ruleVersionRef ) ) )? (otherlv_116= 'challengeResponse:' ( (lv_challengeResponse_117_0= RULE_STRING ) ) )? otherlv_118= '}'
                    {
                    otherlv_110=(Token)match(input,57,FOLLOW_57_in_ruleVersion2091); 

                        	newLeafNode(otherlv_110, grammarAccess.getVersionAccess().getIBKeyword_13_0());
                        
                    otherlv_111=(Token)match(input,19,FOLLOW_19_in_ruleVersion2103); 

                        	newLeafNode(otherlv_111, grammarAccess.getVersionAccess().getLeftCurlyBracketKeyword_13_1());
                        
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1218:1: (otherlv_112= 'confirmVersion:' ( ( ruleVersionRef ) ) )?
                    int alt54=2;
                    int LA54_0 = input.LA(1);

                    if ( (LA54_0==58) ) {
                        alt54=1;
                    }
                    switch (alt54) {
                        case 1 :
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1218:3: otherlv_112= 'confirmVersion:' ( ( ruleVersionRef ) )
                            {
                            otherlv_112=(Token)match(input,58,FOLLOW_58_in_ruleVersion2116); 

                                	newLeafNode(otherlv_112, grammarAccess.getVersionAccess().getConfirmVersionKeyword_13_2_0());
                                
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1222:1: ( ( ruleVersionRef ) )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1223:1: ( ruleVersionRef )
                            {
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1223:1: ( ruleVersionRef )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1224:3: ruleVersionRef
                            {

                            			if (current==null) {
                            	            current = createModelElement(grammarAccess.getVersionRule());
                            	        }
                                    
                             
                            	        newCompositeNode(grammarAccess.getVersionAccess().getConfirmVersionVersionCrossReference_13_2_1_0()); 
                            	    
                            pushFollow(FOLLOW_ruleVersionRef_in_ruleVersion2139);
                            ruleVersionRef();

                            state._fsp--;

                             
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }


                            }
                            break;

                    }

                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1237:4: (otherlv_114= 'previewVersion:' ( ( ruleVersionRef ) ) )?
                    int alt55=2;
                    int LA55_0 = input.LA(1);

                    if ( (LA55_0==59) ) {
                        alt55=1;
                    }
                    switch (alt55) {
                        case 1 :
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1237:6: otherlv_114= 'previewVersion:' ( ( ruleVersionRef ) )
                            {
                            otherlv_114=(Token)match(input,59,FOLLOW_59_in_ruleVersion2154); 

                                	newLeafNode(otherlv_114, grammarAccess.getVersionAccess().getPreviewVersionKeyword_13_3_0());
                                
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1241:1: ( ( ruleVersionRef ) )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1242:1: ( ruleVersionRef )
                            {
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1242:1: ( ruleVersionRef )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1243:3: ruleVersionRef
                            {

                            			if (current==null) {
                            	            current = createModelElement(grammarAccess.getVersionRule());
                            	        }
                                    
                             
                            	        newCompositeNode(grammarAccess.getVersionAccess().getPreviewVersionVersionCrossReference_13_3_1_0()); 
                            	    
                            pushFollow(FOLLOW_ruleVersionRef_in_ruleVersion2177);
                            ruleVersionRef();

                            state._fsp--;

                             
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }


                            }
                            break;

                    }

                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1256:4: (otherlv_116= 'challengeResponse:' ( (lv_challengeResponse_117_0= RULE_STRING ) ) )?
                    int alt56=2;
                    int LA56_0 = input.LA(1);

                    if ( (LA56_0==60) ) {
                        alt56=1;
                    }
                    switch (alt56) {
                        case 1 :
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1256:6: otherlv_116= 'challengeResponse:' ( (lv_challengeResponse_117_0= RULE_STRING ) )
                            {
                            otherlv_116=(Token)match(input,60,FOLLOW_60_in_ruleVersion2192); 

                                	newLeafNode(otherlv_116, grammarAccess.getVersionAccess().getChallengeResponseKeyword_13_4_0());
                                
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1260:1: ( (lv_challengeResponse_117_0= RULE_STRING ) )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1261:1: (lv_challengeResponse_117_0= RULE_STRING )
                            {
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1261:1: (lv_challengeResponse_117_0= RULE_STRING )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1262:3: lv_challengeResponse_117_0= RULE_STRING
                            {
                            lv_challengeResponse_117_0=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleVersion2209); 

                            			newLeafNode(lv_challengeResponse_117_0, grammarAccess.getVersionAccess().getChallengeResponseSTRINGTerminalRuleCall_13_4_1_0()); 
                            		

                            	        if (current==null) {
                            	            current = createModelElement(grammarAccess.getVersionRule());
                            	        }
                                   		setWithLastConsumed(
                                   			current, 
                                   			"challengeResponse",
                                    		lv_challengeResponse_117_0, 
                                    		"STRING");
                            	    

                            }


                            }


                            }
                            break;

                    }

                    otherlv_118=(Token)match(input,31,FOLLOW_31_in_ruleVersion2228); 

                        	newLeafNode(otherlv_118, grammarAccess.getVersionAccess().getRightCurlyBracketKeyword_13_5());
                        

                    }
                    break;

            }

            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1282:3: (otherlv_119= 'attributes:' ( (lv_attributes_120_0= RULE_STRING ) ) (otherlv_121= ';' ( (lv_attributes_122_0= RULE_STRING ) ) )* )?
            int alt59=2;
            int LA59_0 = input.LA(1);

            if ( (LA59_0==61) ) {
                alt59=1;
            }
            switch (alt59) {
                case 1 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1282:5: otherlv_119= 'attributes:' ( (lv_attributes_120_0= RULE_STRING ) ) (otherlv_121= ';' ( (lv_attributes_122_0= RULE_STRING ) ) )*
                    {
                    otherlv_119=(Token)match(input,61,FOLLOW_61_in_ruleVersion2243); 

                        	newLeafNode(otherlv_119, grammarAccess.getVersionAccess().getAttributesKeyword_14_0());
                        
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1286:1: ( (lv_attributes_120_0= RULE_STRING ) )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1287:1: (lv_attributes_120_0= RULE_STRING )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1287:1: (lv_attributes_120_0= RULE_STRING )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1288:3: lv_attributes_120_0= RULE_STRING
                    {
                    lv_attributes_120_0=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleVersion2260); 

                    			newLeafNode(lv_attributes_120_0, grammarAccess.getVersionAccess().getAttributesSTRINGTerminalRuleCall_14_1_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getVersionRule());
                    	        }
                           		addWithLastConsumed(
                           			current, 
                           			"attributes",
                            		lv_attributes_120_0, 
                            		"STRING");
                    	    

                    }


                    }

                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1304:2: (otherlv_121= ';' ( (lv_attributes_122_0= RULE_STRING ) ) )*
                    loop58:
                    do {
                        int alt58=2;
                        int LA58_0 = input.LA(1);

                        if ( (LA58_0==24) ) {
                            alt58=1;
                        }


                        switch (alt58) {
                    	case 1 :
                    	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1304:4: otherlv_121= ';' ( (lv_attributes_122_0= RULE_STRING ) )
                    	    {
                    	    otherlv_121=(Token)match(input,24,FOLLOW_24_in_ruleVersion2278); 

                    	        	newLeafNode(otherlv_121, grammarAccess.getVersionAccess().getSemicolonKeyword_14_2_0());
                    	        
                    	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1308:1: ( (lv_attributes_122_0= RULE_STRING ) )
                    	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1309:1: (lv_attributes_122_0= RULE_STRING )
                    	    {
                    	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1309:1: (lv_attributes_122_0= RULE_STRING )
                    	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1310:3: lv_attributes_122_0= RULE_STRING
                    	    {
                    	    lv_attributes_122_0=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleVersion2295); 

                    	    			newLeafNode(lv_attributes_122_0, grammarAccess.getVersionAccess().getAttributesSTRINGTerminalRuleCall_14_2_1_0()); 
                    	    		

                    	    	        if (current==null) {
                    	    	            current = createModelElement(grammarAccess.getVersionRule());
                    	    	        }
                    	           		addWithLastConsumed(
                    	           			current, 
                    	           			"attributes",
                    	            		lv_attributes_122_0, 
                    	            		"STRING");
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop58;
                        }
                    } while (true);


                    }
                    break;

            }

            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1326:6: (otherlv_123= 'WebServices' otherlv_124= '{' (otherlv_125= 'publish:' ( (lv_publishWebService_126_0= ruleYesNo ) ) )? (otherlv_127= 'activity:' ( (lv_webServiceActivity_128_0= RULE_STRING ) ) )? (otherlv_129= 'function:' ( (lv_webServiceFunction_130_0= ruleFunction ) ) )? (otherlv_131= 'description:' ( (lv_webServiceDescription_132_0= RULE_STRING ) ) )? (otherlv_133= 'names:' ( (lv_webServiceNames_134_0= RULE_STRING ) ) (otherlv_135= ';' ( (lv_webServiceNames_136_0= RULE_STRING ) ) )* )? otherlv_137= '}' )?
            int alt66=2;
            int LA66_0 = input.LA(1);

            if ( (LA66_0==62) ) {
                alt66=1;
            }
            switch (alt66) {
                case 1 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1326:8: otherlv_123= 'WebServices' otherlv_124= '{' (otherlv_125= 'publish:' ( (lv_publishWebService_126_0= ruleYesNo ) ) )? (otherlv_127= 'activity:' ( (lv_webServiceActivity_128_0= RULE_STRING ) ) )? (otherlv_129= 'function:' ( (lv_webServiceFunction_130_0= ruleFunction ) ) )? (otherlv_131= 'description:' ( (lv_webServiceDescription_132_0= RULE_STRING ) ) )? (otherlv_133= 'names:' ( (lv_webServiceNames_134_0= RULE_STRING ) ) (otherlv_135= ';' ( (lv_webServiceNames_136_0= RULE_STRING ) ) )* )? otherlv_137= '}'
                    {
                    otherlv_123=(Token)match(input,62,FOLLOW_62_in_ruleVersion2317); 

                        	newLeafNode(otherlv_123, grammarAccess.getVersionAccess().getWebServicesKeyword_15_0());
                        
                    otherlv_124=(Token)match(input,19,FOLLOW_19_in_ruleVersion2329); 

                        	newLeafNode(otherlv_124, grammarAccess.getVersionAccess().getLeftCurlyBracketKeyword_15_1());
                        
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1334:1: (otherlv_125= 'publish:' ( (lv_publishWebService_126_0= ruleYesNo ) ) )?
                    int alt60=2;
                    int LA60_0 = input.LA(1);

                    if ( (LA60_0==63) ) {
                        alt60=1;
                    }
                    switch (alt60) {
                        case 1 :
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1334:3: otherlv_125= 'publish:' ( (lv_publishWebService_126_0= ruleYesNo ) )
                            {
                            otherlv_125=(Token)match(input,63,FOLLOW_63_in_ruleVersion2342); 

                                	newLeafNode(otherlv_125, grammarAccess.getVersionAccess().getPublishKeyword_15_2_0());
                                
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1338:1: ( (lv_publishWebService_126_0= ruleYesNo ) )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1339:1: (lv_publishWebService_126_0= ruleYesNo )
                            {
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1339:1: (lv_publishWebService_126_0= ruleYesNo )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1340:3: lv_publishWebService_126_0= ruleYesNo
                            {
                             
                            	        newCompositeNode(grammarAccess.getVersionAccess().getPublishWebServiceYesNoEnumRuleCall_15_2_1_0()); 
                            	    
                            pushFollow(FOLLOW_ruleYesNo_in_ruleVersion2363);
                            lv_publishWebService_126_0=ruleYesNo();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getVersionRule());
                            	        }
                                   		set(
                                   			current, 
                                   			"publishWebService",
                                    		lv_publishWebService_126_0, 
                                    		"YesNo");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }


                            }
                            break;

                    }

                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1356:4: (otherlv_127= 'activity:' ( (lv_webServiceActivity_128_0= RULE_STRING ) ) )?
                    int alt61=2;
                    int LA61_0 = input.LA(1);

                    if ( (LA61_0==64) ) {
                        alt61=1;
                    }
                    switch (alt61) {
                        case 1 :
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1356:6: otherlv_127= 'activity:' ( (lv_webServiceActivity_128_0= RULE_STRING ) )
                            {
                            otherlv_127=(Token)match(input,64,FOLLOW_64_in_ruleVersion2378); 

                                	newLeafNode(otherlv_127, grammarAccess.getVersionAccess().getActivityKeyword_15_3_0());
                                
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1360:1: ( (lv_webServiceActivity_128_0= RULE_STRING ) )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1361:1: (lv_webServiceActivity_128_0= RULE_STRING )
                            {
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1361:1: (lv_webServiceActivity_128_0= RULE_STRING )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1362:3: lv_webServiceActivity_128_0= RULE_STRING
                            {
                            lv_webServiceActivity_128_0=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleVersion2395); 

                            			newLeafNode(lv_webServiceActivity_128_0, grammarAccess.getVersionAccess().getWebServiceActivitySTRINGTerminalRuleCall_15_3_1_0()); 
                            		

                            	        if (current==null) {
                            	            current = createModelElement(grammarAccess.getVersionRule());
                            	        }
                                   		setWithLastConsumed(
                                   			current, 
                                   			"webServiceActivity",
                                    		lv_webServiceActivity_128_0, 
                                    		"STRING");
                            	    

                            }


                            }


                            }
                            break;

                    }

                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1378:4: (otherlv_129= 'function:' ( (lv_webServiceFunction_130_0= ruleFunction ) ) )?
                    int alt62=2;
                    int LA62_0 = input.LA(1);

                    if ( (LA62_0==65) ) {
                        alt62=1;
                    }
                    switch (alt62) {
                        case 1 :
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1378:6: otherlv_129= 'function:' ( (lv_webServiceFunction_130_0= ruleFunction ) )
                            {
                            otherlv_129=(Token)match(input,65,FOLLOW_65_in_ruleVersion2415); 

                                	newLeafNode(otherlv_129, grammarAccess.getVersionAccess().getFunctionKeyword_15_4_0());
                                
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1382:1: ( (lv_webServiceFunction_130_0= ruleFunction ) )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1383:1: (lv_webServiceFunction_130_0= ruleFunction )
                            {
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1383:1: (lv_webServiceFunction_130_0= ruleFunction )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1384:3: lv_webServiceFunction_130_0= ruleFunction
                            {
                             
                            	        newCompositeNode(grammarAccess.getVersionAccess().getWebServiceFunctionFunctionEnumRuleCall_15_4_1_0()); 
                            	    
                            pushFollow(FOLLOW_ruleFunction_in_ruleVersion2436);
                            lv_webServiceFunction_130_0=ruleFunction();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getVersionRule());
                            	        }
                                   		set(
                                   			current, 
                                   			"webServiceFunction",
                                    		lv_webServiceFunction_130_0, 
                                    		"Function");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }


                            }
                            break;

                    }

                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1400:4: (otherlv_131= 'description:' ( (lv_webServiceDescription_132_0= RULE_STRING ) ) )?
                    int alt63=2;
                    int LA63_0 = input.LA(1);

                    if ( (LA63_0==17) ) {
                        alt63=1;
                    }
                    switch (alt63) {
                        case 1 :
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1400:6: otherlv_131= 'description:' ( (lv_webServiceDescription_132_0= RULE_STRING ) )
                            {
                            otherlv_131=(Token)match(input,17,FOLLOW_17_in_ruleVersion2451); 

                                	newLeafNode(otherlv_131, grammarAccess.getVersionAccess().getDescriptionKeyword_15_5_0());
                                
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1404:1: ( (lv_webServiceDescription_132_0= RULE_STRING ) )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1405:1: (lv_webServiceDescription_132_0= RULE_STRING )
                            {
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1405:1: (lv_webServiceDescription_132_0= RULE_STRING )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1406:3: lv_webServiceDescription_132_0= RULE_STRING
                            {
                            lv_webServiceDescription_132_0=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleVersion2468); 

                            			newLeafNode(lv_webServiceDescription_132_0, grammarAccess.getVersionAccess().getWebServiceDescriptionSTRINGTerminalRuleCall_15_5_1_0()); 
                            		

                            	        if (current==null) {
                            	            current = createModelElement(grammarAccess.getVersionRule());
                            	        }
                                   		setWithLastConsumed(
                                   			current, 
                                   			"webServiceDescription",
                                    		lv_webServiceDescription_132_0, 
                                    		"STRING");
                            	    

                            }


                            }


                            }
                            break;

                    }

                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1422:4: (otherlv_133= 'names:' ( (lv_webServiceNames_134_0= RULE_STRING ) ) (otherlv_135= ';' ( (lv_webServiceNames_136_0= RULE_STRING ) ) )* )?
                    int alt65=2;
                    int LA65_0 = input.LA(1);

                    if ( (LA65_0==66) ) {
                        alt65=1;
                    }
                    switch (alt65) {
                        case 1 :
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1422:6: otherlv_133= 'names:' ( (lv_webServiceNames_134_0= RULE_STRING ) ) (otherlv_135= ';' ( (lv_webServiceNames_136_0= RULE_STRING ) ) )*
                            {
                            otherlv_133=(Token)match(input,66,FOLLOW_66_in_ruleVersion2488); 

                                	newLeafNode(otherlv_133, grammarAccess.getVersionAccess().getNamesKeyword_15_6_0());
                                
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1426:1: ( (lv_webServiceNames_134_0= RULE_STRING ) )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1427:1: (lv_webServiceNames_134_0= RULE_STRING )
                            {
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1427:1: (lv_webServiceNames_134_0= RULE_STRING )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1428:3: lv_webServiceNames_134_0= RULE_STRING
                            {
                            lv_webServiceNames_134_0=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleVersion2505); 

                            			newLeafNode(lv_webServiceNames_134_0, grammarAccess.getVersionAccess().getWebServiceNamesSTRINGTerminalRuleCall_15_6_1_0()); 
                            		

                            	        if (current==null) {
                            	            current = createModelElement(grammarAccess.getVersionRule());
                            	        }
                                   		addWithLastConsumed(
                                   			current, 
                                   			"webServiceNames",
                                    		lv_webServiceNames_134_0, 
                                    		"STRING");
                            	    

                            }


                            }

                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1444:2: (otherlv_135= ';' ( (lv_webServiceNames_136_0= RULE_STRING ) ) )*
                            loop64:
                            do {
                                int alt64=2;
                                int LA64_0 = input.LA(1);

                                if ( (LA64_0==24) ) {
                                    alt64=1;
                                }


                                switch (alt64) {
                            	case 1 :
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1444:4: otherlv_135= ';' ( (lv_webServiceNames_136_0= RULE_STRING ) )
                            	    {
                            	    otherlv_135=(Token)match(input,24,FOLLOW_24_in_ruleVersion2523); 

                            	        	newLeafNode(otherlv_135, grammarAccess.getVersionAccess().getSemicolonKeyword_15_6_2_0());
                            	        
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1448:1: ( (lv_webServiceNames_136_0= RULE_STRING ) )
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1449:1: (lv_webServiceNames_136_0= RULE_STRING )
                            	    {
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1449:1: (lv_webServiceNames_136_0= RULE_STRING )
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1450:3: lv_webServiceNames_136_0= RULE_STRING
                            	    {
                            	    lv_webServiceNames_136_0=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleVersion2540); 

                            	    			newLeafNode(lv_webServiceNames_136_0, grammarAccess.getVersionAccess().getWebServiceNamesSTRINGTerminalRuleCall_15_6_2_1_0()); 
                            	    		

                            	    	        if (current==null) {
                            	    	            current = createModelElement(grammarAccess.getVersionRule());
                            	    	        }
                            	           		addWithLastConsumed(
                            	           			current, 
                            	           			"webServiceNames",
                            	            		lv_webServiceNames_136_0, 
                            	            		"STRING");
                            	    	    

                            	    }


                            	    }


                            	    }
                            	    break;

                            	default :
                            	    break loop64;
                                }
                            } while (true);


                            }
                            break;

                    }

                    otherlv_137=(Token)match(input,31,FOLLOW_31_in_ruleVersion2561); 

                        	newLeafNode(otherlv_137, grammarAccess.getVersionAccess().getRightCurlyBracketKeyword_15_7());
                        

                    }
                    break;

            }

            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1470:3: (otherlv_138= 'Connect' otherlv_139= '{' (otherlv_140= 'generateIFP:' ( (lv_generateIFP_141_0= ruleYesNo ) ) )? (otherlv_142= 'associatedVersionsPresentationPattern:' ( (lv_associatedVersionsPresentationPattern_143_0= ruleAssociatedVersionsPresentationPattern ) ) )? (otherlv_144= 'fieldsLayoutPattern:' ( (lv_fieldsLayoutPattern_145_0= ruleFieldsLayoutPattern ) ) )? otherlv_146= '}' )?
            int alt70=2;
            int LA70_0 = input.LA(1);

            if ( (LA70_0==67) ) {
                alt70=1;
            }
            switch (alt70) {
                case 1 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1470:5: otherlv_138= 'Connect' otherlv_139= '{' (otherlv_140= 'generateIFP:' ( (lv_generateIFP_141_0= ruleYesNo ) ) )? (otherlv_142= 'associatedVersionsPresentationPattern:' ( (lv_associatedVersionsPresentationPattern_143_0= ruleAssociatedVersionsPresentationPattern ) ) )? (otherlv_144= 'fieldsLayoutPattern:' ( (lv_fieldsLayoutPattern_145_0= ruleFieldsLayoutPattern ) ) )? otherlv_146= '}'
                    {
                    otherlv_138=(Token)match(input,67,FOLLOW_67_in_ruleVersion2576); 

                        	newLeafNode(otherlv_138, grammarAccess.getVersionAccess().getConnectKeyword_16_0());
                        
                    otherlv_139=(Token)match(input,19,FOLLOW_19_in_ruleVersion2588); 

                        	newLeafNode(otherlv_139, grammarAccess.getVersionAccess().getLeftCurlyBracketKeyword_16_1());
                        
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1478:1: (otherlv_140= 'generateIFP:' ( (lv_generateIFP_141_0= ruleYesNo ) ) )?
                    int alt67=2;
                    int LA67_0 = input.LA(1);

                    if ( (LA67_0==68) ) {
                        alt67=1;
                    }
                    switch (alt67) {
                        case 1 :
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1478:3: otherlv_140= 'generateIFP:' ( (lv_generateIFP_141_0= ruleYesNo ) )
                            {
                            otherlv_140=(Token)match(input,68,FOLLOW_68_in_ruleVersion2601); 

                                	newLeafNode(otherlv_140, grammarAccess.getVersionAccess().getGenerateIFPKeyword_16_2_0());
                                
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1482:1: ( (lv_generateIFP_141_0= ruleYesNo ) )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1483:1: (lv_generateIFP_141_0= ruleYesNo )
                            {
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1483:1: (lv_generateIFP_141_0= ruleYesNo )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1484:3: lv_generateIFP_141_0= ruleYesNo
                            {
                             
                            	        newCompositeNode(grammarAccess.getVersionAccess().getGenerateIFPYesNoEnumRuleCall_16_2_1_0()); 
                            	    
                            pushFollow(FOLLOW_ruleYesNo_in_ruleVersion2622);
                            lv_generateIFP_141_0=ruleYesNo();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getVersionRule());
                            	        }
                                   		set(
                                   			current, 
                                   			"generateIFP",
                                    		lv_generateIFP_141_0, 
                                    		"YesNo");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }


                            }
                            break;

                    }

                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1500:4: (otherlv_142= 'associatedVersionsPresentationPattern:' ( (lv_associatedVersionsPresentationPattern_143_0= ruleAssociatedVersionsPresentationPattern ) ) )?
                    int alt68=2;
                    int LA68_0 = input.LA(1);

                    if ( (LA68_0==69) ) {
                        alt68=1;
                    }
                    switch (alt68) {
                        case 1 :
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1500:6: otherlv_142= 'associatedVersionsPresentationPattern:' ( (lv_associatedVersionsPresentationPattern_143_0= ruleAssociatedVersionsPresentationPattern ) )
                            {
                            otherlv_142=(Token)match(input,69,FOLLOW_69_in_ruleVersion2637); 

                                	newLeafNode(otherlv_142, grammarAccess.getVersionAccess().getAssociatedVersionsPresentationPatternKeyword_16_3_0());
                                
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1504:1: ( (lv_associatedVersionsPresentationPattern_143_0= ruleAssociatedVersionsPresentationPattern ) )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1505:1: (lv_associatedVersionsPresentationPattern_143_0= ruleAssociatedVersionsPresentationPattern )
                            {
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1505:1: (lv_associatedVersionsPresentationPattern_143_0= ruleAssociatedVersionsPresentationPattern )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1506:3: lv_associatedVersionsPresentationPattern_143_0= ruleAssociatedVersionsPresentationPattern
                            {
                             
                            	        newCompositeNode(grammarAccess.getVersionAccess().getAssociatedVersionsPresentationPatternAssociatedVersionsPresentationPatternEnumRuleCall_16_3_1_0()); 
                            	    
                            pushFollow(FOLLOW_ruleAssociatedVersionsPresentationPattern_in_ruleVersion2658);
                            lv_associatedVersionsPresentationPattern_143_0=ruleAssociatedVersionsPresentationPattern();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getVersionRule());
                            	        }
                                   		set(
                                   			current, 
                                   			"associatedVersionsPresentationPattern",
                                    		lv_associatedVersionsPresentationPattern_143_0, 
                                    		"AssociatedVersionsPresentationPattern");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }


                            }
                            break;

                    }

                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1522:4: (otherlv_144= 'fieldsLayoutPattern:' ( (lv_fieldsLayoutPattern_145_0= ruleFieldsLayoutPattern ) ) )?
                    int alt69=2;
                    int LA69_0 = input.LA(1);

                    if ( (LA69_0==70) ) {
                        alt69=1;
                    }
                    switch (alt69) {
                        case 1 :
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1522:6: otherlv_144= 'fieldsLayoutPattern:' ( (lv_fieldsLayoutPattern_145_0= ruleFieldsLayoutPattern ) )
                            {
                            otherlv_144=(Token)match(input,70,FOLLOW_70_in_ruleVersion2673); 

                                	newLeafNode(otherlv_144, grammarAccess.getVersionAccess().getFieldsLayoutPatternKeyword_16_4_0());
                                
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1526:1: ( (lv_fieldsLayoutPattern_145_0= ruleFieldsLayoutPattern ) )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1527:1: (lv_fieldsLayoutPattern_145_0= ruleFieldsLayoutPattern )
                            {
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1527:1: (lv_fieldsLayoutPattern_145_0= ruleFieldsLayoutPattern )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1528:3: lv_fieldsLayoutPattern_145_0= ruleFieldsLayoutPattern
                            {
                             
                            	        newCompositeNode(grammarAccess.getVersionAccess().getFieldsLayoutPatternFieldsLayoutPatternEnumRuleCall_16_4_1_0()); 
                            	    
                            pushFollow(FOLLOW_ruleFieldsLayoutPattern_in_ruleVersion2694);
                            lv_fieldsLayoutPattern_145_0=ruleFieldsLayoutPattern();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getVersionRule());
                            	        }
                                   		set(
                                   			current, 
                                   			"fieldsLayoutPattern",
                                    		lv_fieldsLayoutPattern_145_0, 
                                    		"FieldsLayoutPattern");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }


                            }
                            break;

                    }

                    otherlv_146=(Token)match(input,31,FOLLOW_31_in_ruleVersion2708); 

                        	newLeafNode(otherlv_146, grammarAccess.getVersionAccess().getRightCurlyBracketKeyword_16_5());
                        

                    }
                    break;

            }

            otherlv_147=(Token)match(input,71,FOLLOW_71_in_ruleVersion2722); 

                	newLeafNode(otherlv_147, grammarAccess.getVersionAccess().getFieldsKeyword_17());
                
            otherlv_148=(Token)match(input,19,FOLLOW_19_in_ruleVersion2734); 

                	newLeafNode(otherlv_148, grammarAccess.getVersionAccess().getLeftCurlyBracketKeyword_18());
                
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1556:1: ( (lv_fields_149_0= ruleField ) )*
            loop71:
            do {
                int alt71=2;
                int LA71_0 = input.LA(1);

                if ( (LA71_0==RULE_STRING) ) {
                    alt71=1;
                }


                switch (alt71) {
            	case 1 :
            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1557:1: (lv_fields_149_0= ruleField )
            	    {
            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1557:1: (lv_fields_149_0= ruleField )
            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1558:3: lv_fields_149_0= ruleField
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getVersionAccess().getFieldsFieldParserRuleCall_19_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleField_in_ruleVersion2755);
            	    lv_fields_149_0=ruleField();

            	    state._fsp--;


            	    	        if (current==null) {
            	    	            current = createModelElementForParent(grammarAccess.getVersionRule());
            	    	        }
            	           		add(
            	           			current, 
            	           			"fields",
            	            		lv_fields_149_0, 
            	            		"Field");
            	    	        afterParserOrEnumRuleCall();
            	    	    

            	    }


            	    }
            	    break;

            	default :
            	    break loop71;
                }
            } while (true);

            otherlv_150=(Token)match(input,31,FOLLOW_31_in_ruleVersion2768); 

                	newLeafNode(otherlv_150, grammarAccess.getVersionAccess().getRightCurlyBracketKeyword_20());
                

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
    // $ANTLR end "ruleVersion"


    // $ANTLR start "entryRuleField"
    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1586:1: entryRuleField returns [EObject current=null] : iv_ruleField= ruleField EOF ;
    public final EObject entryRuleField() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleField = null;


        try {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1587:2: (iv_ruleField= ruleField EOF )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1588:2: iv_ruleField= ruleField EOF
            {
             newCompositeNode(grammarAccess.getFieldRule()); 
            pushFollow(FOLLOW_ruleField_in_entryRuleField2804);
            iv_ruleField=ruleField();

            state._fsp--;

             current =iv_ruleField; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleField2814); 

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
    // $ANTLR end "entryRuleField"


    // $ANTLR start "ruleField"
    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1595:1: ruleField returns [EObject current=null] : ( ( (lv_name_0_0= RULE_STRING ) ) otherlv_1= '{' (otherlv_2= 'Presentation' otherlv_3= '{' (otherlv_4= 'displayType:' ( (lv_displayType_5_0= ruleDisplayType ) ) )? (otherlv_6= 'inputBehaviour:' ( (lv_inputBehaviour_7_0= ruleInputBehaviour ) ) )? (otherlv_8= 'caseConvention:' ( (lv_caseConvention_9_0= ruleCaseConvention ) ) )? (otherlv_10= 'maxLength:' ( (lv_maxLength_11_0= ruleINTEGER_OBJECT ) ) )? (otherlv_12= 'enrichmentLength:' ( (lv_enrichmentLength_13_0= ruleINTEGER_OBJECT ) ) )? (otherlv_14= 'expansion:' ( (lv_expansion_15_0= ruleExpansion ) ) )? (otherlv_16= 'rightAdjust:' ( (lv_rightAdjust_17_0= ruleYesNo ) ) )? (otherlv_18= 'enrichment:' ( (lv_enrichment_19_0= ruleYesNo ) ) )? (otherlv_20= 'column:' ( (lv_column_21_0= ruleINTEGER_OBJECT ) ) )? (otherlv_22= 'row:' ( (lv_row_23_0= ruleINTEGER_OBJECT ) ) )? (otherlv_24= 'mandatory:' ( (lv_mandatory_25_0= ruleYesNo ) ) )? (otherlv_26= 'RekeyRequired:' ( (lv_rekeyRequired_27_0= ruleYesNo ) ) )? (otherlv_28= 'hyperlink:' ( (lv_hyperlink_29_0= RULE_STRING ) ) )? (otherlv_30= 'hotValidate:' ( (lv_hotValidate_31_0= ruleYesNo ) ) )? (otherlv_32= 'hotField:' ( (lv_hotField_33_0= ruleYesNo ) ) )? (otherlv_34= 'webValidate:' ( (lv_webValidate_35_0= ruleYesNo ) ) )? (otherlv_36= 'selectionEnquiry:' ( (lv_selectionEnquiry_37_0= RULE_STRING ) ) )? (otherlv_38= 'enquiryParameter:' ( (lv_enquiryParameter_39_0= RULE_STRING ) ) )? (otherlv_40= 'popupBehaviour:' ( (lv_popupBehaviour_41_0= rulePopupBehaviour ) ) )? otherlv_42= '}' )? (otherlv_43= 'default:' ( (lv_defaults_44_0= ruleDefault ) ) (otherlv_45= 'OR' ( (lv_defaults_46_0= ruleDefault ) ) )* )? (otherlv_47= 'Translations:' (otherlv_48= 'label:' ( (lv_label_49_0= ruleTranslations ) ) )? (otherlv_50= 'toolTip:' ( (lv_toolTip_51_0= ruleTranslations ) ) )? )? (otherlv_52= 'API' otherlv_53= '{' (otherlv_54= 'validation-routines:' ( (lv_validationRoutines_55_0= ruleRoutine ) ) (otherlv_56= ';' ( (lv_validationRoutines_57_0= ruleRoutine ) ) )* )? otherlv_58= '}' )? (otherlv_59= 'Attributes:' ( (lv_attributes_60_0= RULE_STRING ) ) (otherlv_61= ';' ( (lv_attributes_62_0= RULE_STRING ) ) )* )? (otherlv_63= 'MV:' ( (lv_MV_64_0= ruleINTEGER_OBJECT ) ) )? (otherlv_65= 'SV:' ( (lv_SV_66_0= ruleINTEGER_OBJECT ) ) )? otherlv_67= '}' ) ;
    public final EObject ruleField() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        Token otherlv_10=null;
        Token otherlv_12=null;
        Token otherlv_14=null;
        Token otherlv_16=null;
        Token otherlv_18=null;
        Token otherlv_20=null;
        Token otherlv_22=null;
        Token otherlv_24=null;
        Token otherlv_26=null;
        Token otherlv_28=null;
        Token lv_hyperlink_29_0=null;
        Token otherlv_30=null;
        Token otherlv_32=null;
        Token otherlv_34=null;
        Token otherlv_36=null;
        Token lv_selectionEnquiry_37_0=null;
        Token otherlv_38=null;
        Token lv_enquiryParameter_39_0=null;
        Token otherlv_40=null;
        Token otherlv_42=null;
        Token otherlv_43=null;
        Token otherlv_45=null;
        Token otherlv_47=null;
        Token otherlv_48=null;
        Token otherlv_50=null;
        Token otherlv_52=null;
        Token otherlv_53=null;
        Token otherlv_54=null;
        Token otherlv_56=null;
        Token otherlv_58=null;
        Token otherlv_59=null;
        Token lv_attributes_60_0=null;
        Token otherlv_61=null;
        Token lv_attributes_62_0=null;
        Token otherlv_63=null;
        Token otherlv_65=null;
        Token otherlv_67=null;
        Enumerator lv_displayType_5_0 = null;

        Enumerator lv_inputBehaviour_7_0 = null;

        Enumerator lv_caseConvention_9_0 = null;

        AntlrDatatypeRuleToken lv_maxLength_11_0 = null;

        AntlrDatatypeRuleToken lv_enrichmentLength_13_0 = null;

        Enumerator lv_expansion_15_0 = null;

        Enumerator lv_rightAdjust_17_0 = null;

        Enumerator lv_enrichment_19_0 = null;

        AntlrDatatypeRuleToken lv_column_21_0 = null;

        AntlrDatatypeRuleToken lv_row_23_0 = null;

        Enumerator lv_mandatory_25_0 = null;

        Enumerator lv_rekeyRequired_27_0 = null;

        Enumerator lv_hotValidate_31_0 = null;

        Enumerator lv_hotField_33_0 = null;

        Enumerator lv_webValidate_35_0 = null;

        Enumerator lv_popupBehaviour_41_0 = null;

        EObject lv_defaults_44_0 = null;

        EObject lv_defaults_46_0 = null;

        EObject lv_label_49_0 = null;

        EObject lv_toolTip_51_0 = null;

        EObject lv_validationRoutines_55_0 = null;

        EObject lv_validationRoutines_57_0 = null;

        AntlrDatatypeRuleToken lv_MV_64_0 = null;

        AntlrDatatypeRuleToken lv_SV_66_0 = null;


         enterRule(); 
            
        try {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1598:28: ( ( ( (lv_name_0_0= RULE_STRING ) ) otherlv_1= '{' (otherlv_2= 'Presentation' otherlv_3= '{' (otherlv_4= 'displayType:' ( (lv_displayType_5_0= ruleDisplayType ) ) )? (otherlv_6= 'inputBehaviour:' ( (lv_inputBehaviour_7_0= ruleInputBehaviour ) ) )? (otherlv_8= 'caseConvention:' ( (lv_caseConvention_9_0= ruleCaseConvention ) ) )? (otherlv_10= 'maxLength:' ( (lv_maxLength_11_0= ruleINTEGER_OBJECT ) ) )? (otherlv_12= 'enrichmentLength:' ( (lv_enrichmentLength_13_0= ruleINTEGER_OBJECT ) ) )? (otherlv_14= 'expansion:' ( (lv_expansion_15_0= ruleExpansion ) ) )? (otherlv_16= 'rightAdjust:' ( (lv_rightAdjust_17_0= ruleYesNo ) ) )? (otherlv_18= 'enrichment:' ( (lv_enrichment_19_0= ruleYesNo ) ) )? (otherlv_20= 'column:' ( (lv_column_21_0= ruleINTEGER_OBJECT ) ) )? (otherlv_22= 'row:' ( (lv_row_23_0= ruleINTEGER_OBJECT ) ) )? (otherlv_24= 'mandatory:' ( (lv_mandatory_25_0= ruleYesNo ) ) )? (otherlv_26= 'RekeyRequired:' ( (lv_rekeyRequired_27_0= ruleYesNo ) ) )? (otherlv_28= 'hyperlink:' ( (lv_hyperlink_29_0= RULE_STRING ) ) )? (otherlv_30= 'hotValidate:' ( (lv_hotValidate_31_0= ruleYesNo ) ) )? (otherlv_32= 'hotField:' ( (lv_hotField_33_0= ruleYesNo ) ) )? (otherlv_34= 'webValidate:' ( (lv_webValidate_35_0= ruleYesNo ) ) )? (otherlv_36= 'selectionEnquiry:' ( (lv_selectionEnquiry_37_0= RULE_STRING ) ) )? (otherlv_38= 'enquiryParameter:' ( (lv_enquiryParameter_39_0= RULE_STRING ) ) )? (otherlv_40= 'popupBehaviour:' ( (lv_popupBehaviour_41_0= rulePopupBehaviour ) ) )? otherlv_42= '}' )? (otherlv_43= 'default:' ( (lv_defaults_44_0= ruleDefault ) ) (otherlv_45= 'OR' ( (lv_defaults_46_0= ruleDefault ) ) )* )? (otherlv_47= 'Translations:' (otherlv_48= 'label:' ( (lv_label_49_0= ruleTranslations ) ) )? (otherlv_50= 'toolTip:' ( (lv_toolTip_51_0= ruleTranslations ) ) )? )? (otherlv_52= 'API' otherlv_53= '{' (otherlv_54= 'validation-routines:' ( (lv_validationRoutines_55_0= ruleRoutine ) ) (otherlv_56= ';' ( (lv_validationRoutines_57_0= ruleRoutine ) ) )* )? otherlv_58= '}' )? (otherlv_59= 'Attributes:' ( (lv_attributes_60_0= RULE_STRING ) ) (otherlv_61= ';' ( (lv_attributes_62_0= RULE_STRING ) ) )* )? (otherlv_63= 'MV:' ( (lv_MV_64_0= ruleINTEGER_OBJECT ) ) )? (otherlv_65= 'SV:' ( (lv_SV_66_0= ruleINTEGER_OBJECT ) ) )? otherlv_67= '}' ) )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1599:1: ( ( (lv_name_0_0= RULE_STRING ) ) otherlv_1= '{' (otherlv_2= 'Presentation' otherlv_3= '{' (otherlv_4= 'displayType:' ( (lv_displayType_5_0= ruleDisplayType ) ) )? (otherlv_6= 'inputBehaviour:' ( (lv_inputBehaviour_7_0= ruleInputBehaviour ) ) )? (otherlv_8= 'caseConvention:' ( (lv_caseConvention_9_0= ruleCaseConvention ) ) )? (otherlv_10= 'maxLength:' ( (lv_maxLength_11_0= ruleINTEGER_OBJECT ) ) )? (otherlv_12= 'enrichmentLength:' ( (lv_enrichmentLength_13_0= ruleINTEGER_OBJECT ) ) )? (otherlv_14= 'expansion:' ( (lv_expansion_15_0= ruleExpansion ) ) )? (otherlv_16= 'rightAdjust:' ( (lv_rightAdjust_17_0= ruleYesNo ) ) )? (otherlv_18= 'enrichment:' ( (lv_enrichment_19_0= ruleYesNo ) ) )? (otherlv_20= 'column:' ( (lv_column_21_0= ruleINTEGER_OBJECT ) ) )? (otherlv_22= 'row:' ( (lv_row_23_0= ruleINTEGER_OBJECT ) ) )? (otherlv_24= 'mandatory:' ( (lv_mandatory_25_0= ruleYesNo ) ) )? (otherlv_26= 'RekeyRequired:' ( (lv_rekeyRequired_27_0= ruleYesNo ) ) )? (otherlv_28= 'hyperlink:' ( (lv_hyperlink_29_0= RULE_STRING ) ) )? (otherlv_30= 'hotValidate:' ( (lv_hotValidate_31_0= ruleYesNo ) ) )? (otherlv_32= 'hotField:' ( (lv_hotField_33_0= ruleYesNo ) ) )? (otherlv_34= 'webValidate:' ( (lv_webValidate_35_0= ruleYesNo ) ) )? (otherlv_36= 'selectionEnquiry:' ( (lv_selectionEnquiry_37_0= RULE_STRING ) ) )? (otherlv_38= 'enquiryParameter:' ( (lv_enquiryParameter_39_0= RULE_STRING ) ) )? (otherlv_40= 'popupBehaviour:' ( (lv_popupBehaviour_41_0= rulePopupBehaviour ) ) )? otherlv_42= '}' )? (otherlv_43= 'default:' ( (lv_defaults_44_0= ruleDefault ) ) (otherlv_45= 'OR' ( (lv_defaults_46_0= ruleDefault ) ) )* )? (otherlv_47= 'Translations:' (otherlv_48= 'label:' ( (lv_label_49_0= ruleTranslations ) ) )? (otherlv_50= 'toolTip:' ( (lv_toolTip_51_0= ruleTranslations ) ) )? )? (otherlv_52= 'API' otherlv_53= '{' (otherlv_54= 'validation-routines:' ( (lv_validationRoutines_55_0= ruleRoutine ) ) (otherlv_56= ';' ( (lv_validationRoutines_57_0= ruleRoutine ) ) )* )? otherlv_58= '}' )? (otherlv_59= 'Attributes:' ( (lv_attributes_60_0= RULE_STRING ) ) (otherlv_61= ';' ( (lv_attributes_62_0= RULE_STRING ) ) )* )? (otherlv_63= 'MV:' ( (lv_MV_64_0= ruleINTEGER_OBJECT ) ) )? (otherlv_65= 'SV:' ( (lv_SV_66_0= ruleINTEGER_OBJECT ) ) )? otherlv_67= '}' )
            {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1599:1: ( ( (lv_name_0_0= RULE_STRING ) ) otherlv_1= '{' (otherlv_2= 'Presentation' otherlv_3= '{' (otherlv_4= 'displayType:' ( (lv_displayType_5_0= ruleDisplayType ) ) )? (otherlv_6= 'inputBehaviour:' ( (lv_inputBehaviour_7_0= ruleInputBehaviour ) ) )? (otherlv_8= 'caseConvention:' ( (lv_caseConvention_9_0= ruleCaseConvention ) ) )? (otherlv_10= 'maxLength:' ( (lv_maxLength_11_0= ruleINTEGER_OBJECT ) ) )? (otherlv_12= 'enrichmentLength:' ( (lv_enrichmentLength_13_0= ruleINTEGER_OBJECT ) ) )? (otherlv_14= 'expansion:' ( (lv_expansion_15_0= ruleExpansion ) ) )? (otherlv_16= 'rightAdjust:' ( (lv_rightAdjust_17_0= ruleYesNo ) ) )? (otherlv_18= 'enrichment:' ( (lv_enrichment_19_0= ruleYesNo ) ) )? (otherlv_20= 'column:' ( (lv_column_21_0= ruleINTEGER_OBJECT ) ) )? (otherlv_22= 'row:' ( (lv_row_23_0= ruleINTEGER_OBJECT ) ) )? (otherlv_24= 'mandatory:' ( (lv_mandatory_25_0= ruleYesNo ) ) )? (otherlv_26= 'RekeyRequired:' ( (lv_rekeyRequired_27_0= ruleYesNo ) ) )? (otherlv_28= 'hyperlink:' ( (lv_hyperlink_29_0= RULE_STRING ) ) )? (otherlv_30= 'hotValidate:' ( (lv_hotValidate_31_0= ruleYesNo ) ) )? (otherlv_32= 'hotField:' ( (lv_hotField_33_0= ruleYesNo ) ) )? (otherlv_34= 'webValidate:' ( (lv_webValidate_35_0= ruleYesNo ) ) )? (otherlv_36= 'selectionEnquiry:' ( (lv_selectionEnquiry_37_0= RULE_STRING ) ) )? (otherlv_38= 'enquiryParameter:' ( (lv_enquiryParameter_39_0= RULE_STRING ) ) )? (otherlv_40= 'popupBehaviour:' ( (lv_popupBehaviour_41_0= rulePopupBehaviour ) ) )? otherlv_42= '}' )? (otherlv_43= 'default:' ( (lv_defaults_44_0= ruleDefault ) ) (otherlv_45= 'OR' ( (lv_defaults_46_0= ruleDefault ) ) )* )? (otherlv_47= 'Translations:' (otherlv_48= 'label:' ( (lv_label_49_0= ruleTranslations ) ) )? (otherlv_50= 'toolTip:' ( (lv_toolTip_51_0= ruleTranslations ) ) )? )? (otherlv_52= 'API' otherlv_53= '{' (otherlv_54= 'validation-routines:' ( (lv_validationRoutines_55_0= ruleRoutine ) ) (otherlv_56= ';' ( (lv_validationRoutines_57_0= ruleRoutine ) ) )* )? otherlv_58= '}' )? (otherlv_59= 'Attributes:' ( (lv_attributes_60_0= RULE_STRING ) ) (otherlv_61= ';' ( (lv_attributes_62_0= RULE_STRING ) ) )* )? (otherlv_63= 'MV:' ( (lv_MV_64_0= ruleINTEGER_OBJECT ) ) )? (otherlv_65= 'SV:' ( (lv_SV_66_0= ruleINTEGER_OBJECT ) ) )? otherlv_67= '}' )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1599:2: ( (lv_name_0_0= RULE_STRING ) ) otherlv_1= '{' (otherlv_2= 'Presentation' otherlv_3= '{' (otherlv_4= 'displayType:' ( (lv_displayType_5_0= ruleDisplayType ) ) )? (otherlv_6= 'inputBehaviour:' ( (lv_inputBehaviour_7_0= ruleInputBehaviour ) ) )? (otherlv_8= 'caseConvention:' ( (lv_caseConvention_9_0= ruleCaseConvention ) ) )? (otherlv_10= 'maxLength:' ( (lv_maxLength_11_0= ruleINTEGER_OBJECT ) ) )? (otherlv_12= 'enrichmentLength:' ( (lv_enrichmentLength_13_0= ruleINTEGER_OBJECT ) ) )? (otherlv_14= 'expansion:' ( (lv_expansion_15_0= ruleExpansion ) ) )? (otherlv_16= 'rightAdjust:' ( (lv_rightAdjust_17_0= ruleYesNo ) ) )? (otherlv_18= 'enrichment:' ( (lv_enrichment_19_0= ruleYesNo ) ) )? (otherlv_20= 'column:' ( (lv_column_21_0= ruleINTEGER_OBJECT ) ) )? (otherlv_22= 'row:' ( (lv_row_23_0= ruleINTEGER_OBJECT ) ) )? (otherlv_24= 'mandatory:' ( (lv_mandatory_25_0= ruleYesNo ) ) )? (otherlv_26= 'RekeyRequired:' ( (lv_rekeyRequired_27_0= ruleYesNo ) ) )? (otherlv_28= 'hyperlink:' ( (lv_hyperlink_29_0= RULE_STRING ) ) )? (otherlv_30= 'hotValidate:' ( (lv_hotValidate_31_0= ruleYesNo ) ) )? (otherlv_32= 'hotField:' ( (lv_hotField_33_0= ruleYesNo ) ) )? (otherlv_34= 'webValidate:' ( (lv_webValidate_35_0= ruleYesNo ) ) )? (otherlv_36= 'selectionEnquiry:' ( (lv_selectionEnquiry_37_0= RULE_STRING ) ) )? (otherlv_38= 'enquiryParameter:' ( (lv_enquiryParameter_39_0= RULE_STRING ) ) )? (otherlv_40= 'popupBehaviour:' ( (lv_popupBehaviour_41_0= rulePopupBehaviour ) ) )? otherlv_42= '}' )? (otherlv_43= 'default:' ( (lv_defaults_44_0= ruleDefault ) ) (otherlv_45= 'OR' ( (lv_defaults_46_0= ruleDefault ) ) )* )? (otherlv_47= 'Translations:' (otherlv_48= 'label:' ( (lv_label_49_0= ruleTranslations ) ) )? (otherlv_50= 'toolTip:' ( (lv_toolTip_51_0= ruleTranslations ) ) )? )? (otherlv_52= 'API' otherlv_53= '{' (otherlv_54= 'validation-routines:' ( (lv_validationRoutines_55_0= ruleRoutine ) ) (otherlv_56= ';' ( (lv_validationRoutines_57_0= ruleRoutine ) ) )* )? otherlv_58= '}' )? (otherlv_59= 'Attributes:' ( (lv_attributes_60_0= RULE_STRING ) ) (otherlv_61= ';' ( (lv_attributes_62_0= RULE_STRING ) ) )* )? (otherlv_63= 'MV:' ( (lv_MV_64_0= ruleINTEGER_OBJECT ) ) )? (otherlv_65= 'SV:' ( (lv_SV_66_0= ruleINTEGER_OBJECT ) ) )? otherlv_67= '}'
            {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1599:2: ( (lv_name_0_0= RULE_STRING ) )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1600:1: (lv_name_0_0= RULE_STRING )
            {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1600:1: (lv_name_0_0= RULE_STRING )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1601:3: lv_name_0_0= RULE_STRING
            {
            lv_name_0_0=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleField2856); 

            			newLeafNode(lv_name_0_0, grammarAccess.getFieldAccess().getNameSTRINGTerminalRuleCall_0_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getFieldRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"name",
                    		lv_name_0_0, 
                    		"STRING");
            	    

            }


            }

            otherlv_1=(Token)match(input,19,FOLLOW_19_in_ruleField2873); 

                	newLeafNode(otherlv_1, grammarAccess.getFieldAccess().getLeftCurlyBracketKeyword_1());
                
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1621:1: (otherlv_2= 'Presentation' otherlv_3= '{' (otherlv_4= 'displayType:' ( (lv_displayType_5_0= ruleDisplayType ) ) )? (otherlv_6= 'inputBehaviour:' ( (lv_inputBehaviour_7_0= ruleInputBehaviour ) ) )? (otherlv_8= 'caseConvention:' ( (lv_caseConvention_9_0= ruleCaseConvention ) ) )? (otherlv_10= 'maxLength:' ( (lv_maxLength_11_0= ruleINTEGER_OBJECT ) ) )? (otherlv_12= 'enrichmentLength:' ( (lv_enrichmentLength_13_0= ruleINTEGER_OBJECT ) ) )? (otherlv_14= 'expansion:' ( (lv_expansion_15_0= ruleExpansion ) ) )? (otherlv_16= 'rightAdjust:' ( (lv_rightAdjust_17_0= ruleYesNo ) ) )? (otherlv_18= 'enrichment:' ( (lv_enrichment_19_0= ruleYesNo ) ) )? (otherlv_20= 'column:' ( (lv_column_21_0= ruleINTEGER_OBJECT ) ) )? (otherlv_22= 'row:' ( (lv_row_23_0= ruleINTEGER_OBJECT ) ) )? (otherlv_24= 'mandatory:' ( (lv_mandatory_25_0= ruleYesNo ) ) )? (otherlv_26= 'RekeyRequired:' ( (lv_rekeyRequired_27_0= ruleYesNo ) ) )? (otherlv_28= 'hyperlink:' ( (lv_hyperlink_29_0= RULE_STRING ) ) )? (otherlv_30= 'hotValidate:' ( (lv_hotValidate_31_0= ruleYesNo ) ) )? (otherlv_32= 'hotField:' ( (lv_hotField_33_0= ruleYesNo ) ) )? (otherlv_34= 'webValidate:' ( (lv_webValidate_35_0= ruleYesNo ) ) )? (otherlv_36= 'selectionEnquiry:' ( (lv_selectionEnquiry_37_0= RULE_STRING ) ) )? (otherlv_38= 'enquiryParameter:' ( (lv_enquiryParameter_39_0= RULE_STRING ) ) )? (otherlv_40= 'popupBehaviour:' ( (lv_popupBehaviour_41_0= rulePopupBehaviour ) ) )? otherlv_42= '}' )?
            int alt91=2;
            int LA91_0 = input.LA(1);

            if ( (LA91_0==32) ) {
                alt91=1;
            }
            switch (alt91) {
                case 1 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1621:3: otherlv_2= 'Presentation' otherlv_3= '{' (otherlv_4= 'displayType:' ( (lv_displayType_5_0= ruleDisplayType ) ) )? (otherlv_6= 'inputBehaviour:' ( (lv_inputBehaviour_7_0= ruleInputBehaviour ) ) )? (otherlv_8= 'caseConvention:' ( (lv_caseConvention_9_0= ruleCaseConvention ) ) )? (otherlv_10= 'maxLength:' ( (lv_maxLength_11_0= ruleINTEGER_OBJECT ) ) )? (otherlv_12= 'enrichmentLength:' ( (lv_enrichmentLength_13_0= ruleINTEGER_OBJECT ) ) )? (otherlv_14= 'expansion:' ( (lv_expansion_15_0= ruleExpansion ) ) )? (otherlv_16= 'rightAdjust:' ( (lv_rightAdjust_17_0= ruleYesNo ) ) )? (otherlv_18= 'enrichment:' ( (lv_enrichment_19_0= ruleYesNo ) ) )? (otherlv_20= 'column:' ( (lv_column_21_0= ruleINTEGER_OBJECT ) ) )? (otherlv_22= 'row:' ( (lv_row_23_0= ruleINTEGER_OBJECT ) ) )? (otherlv_24= 'mandatory:' ( (lv_mandatory_25_0= ruleYesNo ) ) )? (otherlv_26= 'RekeyRequired:' ( (lv_rekeyRequired_27_0= ruleYesNo ) ) )? (otherlv_28= 'hyperlink:' ( (lv_hyperlink_29_0= RULE_STRING ) ) )? (otherlv_30= 'hotValidate:' ( (lv_hotValidate_31_0= ruleYesNo ) ) )? (otherlv_32= 'hotField:' ( (lv_hotField_33_0= ruleYesNo ) ) )? (otherlv_34= 'webValidate:' ( (lv_webValidate_35_0= ruleYesNo ) ) )? (otherlv_36= 'selectionEnquiry:' ( (lv_selectionEnquiry_37_0= RULE_STRING ) ) )? (otherlv_38= 'enquiryParameter:' ( (lv_enquiryParameter_39_0= RULE_STRING ) ) )? (otherlv_40= 'popupBehaviour:' ( (lv_popupBehaviour_41_0= rulePopupBehaviour ) ) )? otherlv_42= '}'
                    {
                    otherlv_2=(Token)match(input,32,FOLLOW_32_in_ruleField2886); 

                        	newLeafNode(otherlv_2, grammarAccess.getFieldAccess().getPresentationKeyword_2_0());
                        
                    otherlv_3=(Token)match(input,19,FOLLOW_19_in_ruleField2898); 

                        	newLeafNode(otherlv_3, grammarAccess.getFieldAccess().getLeftCurlyBracketKeyword_2_1());
                        
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1629:1: (otherlv_4= 'displayType:' ( (lv_displayType_5_0= ruleDisplayType ) ) )?
                    int alt72=2;
                    int LA72_0 = input.LA(1);

                    if ( (LA72_0==72) ) {
                        alt72=1;
                    }
                    switch (alt72) {
                        case 1 :
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1629:3: otherlv_4= 'displayType:' ( (lv_displayType_5_0= ruleDisplayType ) )
                            {
                            otherlv_4=(Token)match(input,72,FOLLOW_72_in_ruleField2911); 

                                	newLeafNode(otherlv_4, grammarAccess.getFieldAccess().getDisplayTypeKeyword_2_2_0());
                                
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1633:1: ( (lv_displayType_5_0= ruleDisplayType ) )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1634:1: (lv_displayType_5_0= ruleDisplayType )
                            {
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1634:1: (lv_displayType_5_0= ruleDisplayType )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1635:3: lv_displayType_5_0= ruleDisplayType
                            {
                             
                            	        newCompositeNode(grammarAccess.getFieldAccess().getDisplayTypeDisplayTypeEnumRuleCall_2_2_1_0()); 
                            	    
                            pushFollow(FOLLOW_ruleDisplayType_in_ruleField2932);
                            lv_displayType_5_0=ruleDisplayType();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getFieldRule());
                            	        }
                                   		set(
                                   			current, 
                                   			"displayType",
                                    		lv_displayType_5_0, 
                                    		"DisplayType");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }


                            }
                            break;

                    }

                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1651:4: (otherlv_6= 'inputBehaviour:' ( (lv_inputBehaviour_7_0= ruleInputBehaviour ) ) )?
                    int alt73=2;
                    int LA73_0 = input.LA(1);

                    if ( (LA73_0==73) ) {
                        alt73=1;
                    }
                    switch (alt73) {
                        case 1 :
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1651:6: otherlv_6= 'inputBehaviour:' ( (lv_inputBehaviour_7_0= ruleInputBehaviour ) )
                            {
                            otherlv_6=(Token)match(input,73,FOLLOW_73_in_ruleField2947); 

                                	newLeafNode(otherlv_6, grammarAccess.getFieldAccess().getInputBehaviourKeyword_2_3_0());
                                
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1655:1: ( (lv_inputBehaviour_7_0= ruleInputBehaviour ) )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1656:1: (lv_inputBehaviour_7_0= ruleInputBehaviour )
                            {
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1656:1: (lv_inputBehaviour_7_0= ruleInputBehaviour )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1657:3: lv_inputBehaviour_7_0= ruleInputBehaviour
                            {
                             
                            	        newCompositeNode(grammarAccess.getFieldAccess().getInputBehaviourInputBehaviourEnumRuleCall_2_3_1_0()); 
                            	    
                            pushFollow(FOLLOW_ruleInputBehaviour_in_ruleField2968);
                            lv_inputBehaviour_7_0=ruleInputBehaviour();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getFieldRule());
                            	        }
                                   		set(
                                   			current, 
                                   			"inputBehaviour",
                                    		lv_inputBehaviour_7_0, 
                                    		"InputBehaviour");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }


                            }
                            break;

                    }

                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1673:4: (otherlv_8= 'caseConvention:' ( (lv_caseConvention_9_0= ruleCaseConvention ) ) )?
                    int alt74=2;
                    int LA74_0 = input.LA(1);

                    if ( (LA74_0==74) ) {
                        alt74=1;
                    }
                    switch (alt74) {
                        case 1 :
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1673:6: otherlv_8= 'caseConvention:' ( (lv_caseConvention_9_0= ruleCaseConvention ) )
                            {
                            otherlv_8=(Token)match(input,74,FOLLOW_74_in_ruleField2983); 

                                	newLeafNode(otherlv_8, grammarAccess.getFieldAccess().getCaseConventionKeyword_2_4_0());
                                
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1677:1: ( (lv_caseConvention_9_0= ruleCaseConvention ) )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1678:1: (lv_caseConvention_9_0= ruleCaseConvention )
                            {
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1678:1: (lv_caseConvention_9_0= ruleCaseConvention )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1679:3: lv_caseConvention_9_0= ruleCaseConvention
                            {
                             
                            	        newCompositeNode(grammarAccess.getFieldAccess().getCaseConventionCaseConventionEnumRuleCall_2_4_1_0()); 
                            	    
                            pushFollow(FOLLOW_ruleCaseConvention_in_ruleField3004);
                            lv_caseConvention_9_0=ruleCaseConvention();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getFieldRule());
                            	        }
                                   		set(
                                   			current, 
                                   			"caseConvention",
                                    		lv_caseConvention_9_0, 
                                    		"CaseConvention");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }


                            }
                            break;

                    }

                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1695:4: (otherlv_10= 'maxLength:' ( (lv_maxLength_11_0= ruleINTEGER_OBJECT ) ) )?
                    int alt75=2;
                    int LA75_0 = input.LA(1);

                    if ( (LA75_0==75) ) {
                        alt75=1;
                    }
                    switch (alt75) {
                        case 1 :
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1695:6: otherlv_10= 'maxLength:' ( (lv_maxLength_11_0= ruleINTEGER_OBJECT ) )
                            {
                            otherlv_10=(Token)match(input,75,FOLLOW_75_in_ruleField3019); 

                                	newLeafNode(otherlv_10, grammarAccess.getFieldAccess().getMaxLengthKeyword_2_5_0());
                                
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1699:1: ( (lv_maxLength_11_0= ruleINTEGER_OBJECT ) )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1700:1: (lv_maxLength_11_0= ruleINTEGER_OBJECT )
                            {
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1700:1: (lv_maxLength_11_0= ruleINTEGER_OBJECT )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1701:3: lv_maxLength_11_0= ruleINTEGER_OBJECT
                            {
                             
                            	        newCompositeNode(grammarAccess.getFieldAccess().getMaxLengthINTEGER_OBJECTParserRuleCall_2_5_1_0()); 
                            	    
                            pushFollow(FOLLOW_ruleINTEGER_OBJECT_in_ruleField3040);
                            lv_maxLength_11_0=ruleINTEGER_OBJECT();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getFieldRule());
                            	        }
                                   		set(
                                   			current, 
                                   			"maxLength",
                                    		lv_maxLength_11_0, 
                                    		"INTEGER_OBJECT");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }


                            }
                            break;

                    }

                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1717:4: (otherlv_12= 'enrichmentLength:' ( (lv_enrichmentLength_13_0= ruleINTEGER_OBJECT ) ) )?
                    int alt76=2;
                    int LA76_0 = input.LA(1);

                    if ( (LA76_0==76) ) {
                        alt76=1;
                    }
                    switch (alt76) {
                        case 1 :
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1717:6: otherlv_12= 'enrichmentLength:' ( (lv_enrichmentLength_13_0= ruleINTEGER_OBJECT ) )
                            {
                            otherlv_12=(Token)match(input,76,FOLLOW_76_in_ruleField3055); 

                                	newLeafNode(otherlv_12, grammarAccess.getFieldAccess().getEnrichmentLengthKeyword_2_6_0());
                                
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1721:1: ( (lv_enrichmentLength_13_0= ruleINTEGER_OBJECT ) )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1722:1: (lv_enrichmentLength_13_0= ruleINTEGER_OBJECT )
                            {
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1722:1: (lv_enrichmentLength_13_0= ruleINTEGER_OBJECT )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1723:3: lv_enrichmentLength_13_0= ruleINTEGER_OBJECT
                            {
                             
                            	        newCompositeNode(grammarAccess.getFieldAccess().getEnrichmentLengthINTEGER_OBJECTParserRuleCall_2_6_1_0()); 
                            	    
                            pushFollow(FOLLOW_ruleINTEGER_OBJECT_in_ruleField3076);
                            lv_enrichmentLength_13_0=ruleINTEGER_OBJECT();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getFieldRule());
                            	        }
                                   		set(
                                   			current, 
                                   			"enrichmentLength",
                                    		lv_enrichmentLength_13_0, 
                                    		"INTEGER_OBJECT");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }


                            }
                            break;

                    }

                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1739:4: (otherlv_14= 'expansion:' ( (lv_expansion_15_0= ruleExpansion ) ) )?
                    int alt77=2;
                    int LA77_0 = input.LA(1);

                    if ( (LA77_0==77) ) {
                        alt77=1;
                    }
                    switch (alt77) {
                        case 1 :
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1739:6: otherlv_14= 'expansion:' ( (lv_expansion_15_0= ruleExpansion ) )
                            {
                            otherlv_14=(Token)match(input,77,FOLLOW_77_in_ruleField3091); 

                                	newLeafNode(otherlv_14, grammarAccess.getFieldAccess().getExpansionKeyword_2_7_0());
                                
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1743:1: ( (lv_expansion_15_0= ruleExpansion ) )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1744:1: (lv_expansion_15_0= ruleExpansion )
                            {
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1744:1: (lv_expansion_15_0= ruleExpansion )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1745:3: lv_expansion_15_0= ruleExpansion
                            {
                             
                            	        newCompositeNode(grammarAccess.getFieldAccess().getExpansionExpansionEnumRuleCall_2_7_1_0()); 
                            	    
                            pushFollow(FOLLOW_ruleExpansion_in_ruleField3112);
                            lv_expansion_15_0=ruleExpansion();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getFieldRule());
                            	        }
                                   		set(
                                   			current, 
                                   			"expansion",
                                    		lv_expansion_15_0, 
                                    		"Expansion");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }


                            }
                            break;

                    }

                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1761:4: (otherlv_16= 'rightAdjust:' ( (lv_rightAdjust_17_0= ruleYesNo ) ) )?
                    int alt78=2;
                    int LA78_0 = input.LA(1);

                    if ( (LA78_0==78) ) {
                        alt78=1;
                    }
                    switch (alt78) {
                        case 1 :
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1761:6: otherlv_16= 'rightAdjust:' ( (lv_rightAdjust_17_0= ruleYesNo ) )
                            {
                            otherlv_16=(Token)match(input,78,FOLLOW_78_in_ruleField3127); 

                                	newLeafNode(otherlv_16, grammarAccess.getFieldAccess().getRightAdjustKeyword_2_8_0());
                                
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1765:1: ( (lv_rightAdjust_17_0= ruleYesNo ) )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1766:1: (lv_rightAdjust_17_0= ruleYesNo )
                            {
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1766:1: (lv_rightAdjust_17_0= ruleYesNo )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1767:3: lv_rightAdjust_17_0= ruleYesNo
                            {
                             
                            	        newCompositeNode(grammarAccess.getFieldAccess().getRightAdjustYesNoEnumRuleCall_2_8_1_0()); 
                            	    
                            pushFollow(FOLLOW_ruleYesNo_in_ruleField3148);
                            lv_rightAdjust_17_0=ruleYesNo();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getFieldRule());
                            	        }
                                   		set(
                                   			current, 
                                   			"rightAdjust",
                                    		lv_rightAdjust_17_0, 
                                    		"YesNo");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }


                            }
                            break;

                    }

                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1783:4: (otherlv_18= 'enrichment:' ( (lv_enrichment_19_0= ruleYesNo ) ) )?
                    int alt79=2;
                    int LA79_0 = input.LA(1);

                    if ( (LA79_0==79) ) {
                        alt79=1;
                    }
                    switch (alt79) {
                        case 1 :
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1783:6: otherlv_18= 'enrichment:' ( (lv_enrichment_19_0= ruleYesNo ) )
                            {
                            otherlv_18=(Token)match(input,79,FOLLOW_79_in_ruleField3163); 

                                	newLeafNode(otherlv_18, grammarAccess.getFieldAccess().getEnrichmentKeyword_2_9_0());
                                
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1787:1: ( (lv_enrichment_19_0= ruleYesNo ) )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1788:1: (lv_enrichment_19_0= ruleYesNo )
                            {
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1788:1: (lv_enrichment_19_0= ruleYesNo )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1789:3: lv_enrichment_19_0= ruleYesNo
                            {
                             
                            	        newCompositeNode(grammarAccess.getFieldAccess().getEnrichmentYesNoEnumRuleCall_2_9_1_0()); 
                            	    
                            pushFollow(FOLLOW_ruleYesNo_in_ruleField3184);
                            lv_enrichment_19_0=ruleYesNo();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getFieldRule());
                            	        }
                                   		set(
                                   			current, 
                                   			"enrichment",
                                    		lv_enrichment_19_0, 
                                    		"YesNo");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }


                            }
                            break;

                    }

                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1805:4: (otherlv_20= 'column:' ( (lv_column_21_0= ruleINTEGER_OBJECT ) ) )?
                    int alt80=2;
                    int LA80_0 = input.LA(1);

                    if ( (LA80_0==80) ) {
                        alt80=1;
                    }
                    switch (alt80) {
                        case 1 :
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1805:6: otherlv_20= 'column:' ( (lv_column_21_0= ruleINTEGER_OBJECT ) )
                            {
                            otherlv_20=(Token)match(input,80,FOLLOW_80_in_ruleField3199); 

                                	newLeafNode(otherlv_20, grammarAccess.getFieldAccess().getColumnKeyword_2_10_0());
                                
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1809:1: ( (lv_column_21_0= ruleINTEGER_OBJECT ) )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1810:1: (lv_column_21_0= ruleINTEGER_OBJECT )
                            {
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1810:1: (lv_column_21_0= ruleINTEGER_OBJECT )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1811:3: lv_column_21_0= ruleINTEGER_OBJECT
                            {
                             
                            	        newCompositeNode(grammarAccess.getFieldAccess().getColumnINTEGER_OBJECTParserRuleCall_2_10_1_0()); 
                            	    
                            pushFollow(FOLLOW_ruleINTEGER_OBJECT_in_ruleField3220);
                            lv_column_21_0=ruleINTEGER_OBJECT();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getFieldRule());
                            	        }
                                   		set(
                                   			current, 
                                   			"column",
                                    		lv_column_21_0, 
                                    		"INTEGER_OBJECT");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }


                            }
                            break;

                    }

                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1827:4: (otherlv_22= 'row:' ( (lv_row_23_0= ruleINTEGER_OBJECT ) ) )?
                    int alt81=2;
                    int LA81_0 = input.LA(1);

                    if ( (LA81_0==81) ) {
                        alt81=1;
                    }
                    switch (alt81) {
                        case 1 :
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1827:6: otherlv_22= 'row:' ( (lv_row_23_0= ruleINTEGER_OBJECT ) )
                            {
                            otherlv_22=(Token)match(input,81,FOLLOW_81_in_ruleField3235); 

                                	newLeafNode(otherlv_22, grammarAccess.getFieldAccess().getRowKeyword_2_11_0());
                                
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1831:1: ( (lv_row_23_0= ruleINTEGER_OBJECT ) )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1832:1: (lv_row_23_0= ruleINTEGER_OBJECT )
                            {
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1832:1: (lv_row_23_0= ruleINTEGER_OBJECT )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1833:3: lv_row_23_0= ruleINTEGER_OBJECT
                            {
                             
                            	        newCompositeNode(grammarAccess.getFieldAccess().getRowINTEGER_OBJECTParserRuleCall_2_11_1_0()); 
                            	    
                            pushFollow(FOLLOW_ruleINTEGER_OBJECT_in_ruleField3256);
                            lv_row_23_0=ruleINTEGER_OBJECT();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getFieldRule());
                            	        }
                                   		set(
                                   			current, 
                                   			"row",
                                    		lv_row_23_0, 
                                    		"INTEGER_OBJECT");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }


                            }
                            break;

                    }

                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1849:4: (otherlv_24= 'mandatory:' ( (lv_mandatory_25_0= ruleYesNo ) ) )?
                    int alt82=2;
                    int LA82_0 = input.LA(1);

                    if ( (LA82_0==82) ) {
                        alt82=1;
                    }
                    switch (alt82) {
                        case 1 :
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1849:6: otherlv_24= 'mandatory:' ( (lv_mandatory_25_0= ruleYesNo ) )
                            {
                            otherlv_24=(Token)match(input,82,FOLLOW_82_in_ruleField3271); 

                                	newLeafNode(otherlv_24, grammarAccess.getFieldAccess().getMandatoryKeyword_2_12_0());
                                
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1853:1: ( (lv_mandatory_25_0= ruleYesNo ) )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1854:1: (lv_mandatory_25_0= ruleYesNo )
                            {
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1854:1: (lv_mandatory_25_0= ruleYesNo )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1855:3: lv_mandatory_25_0= ruleYesNo
                            {
                             
                            	        newCompositeNode(grammarAccess.getFieldAccess().getMandatoryYesNoEnumRuleCall_2_12_1_0()); 
                            	    
                            pushFollow(FOLLOW_ruleYesNo_in_ruleField3292);
                            lv_mandatory_25_0=ruleYesNo();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getFieldRule());
                            	        }
                                   		set(
                                   			current, 
                                   			"mandatory",
                                    		lv_mandatory_25_0, 
                                    		"YesNo");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }


                            }
                            break;

                    }

                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1871:4: (otherlv_26= 'RekeyRequired:' ( (lv_rekeyRequired_27_0= ruleYesNo ) ) )?
                    int alt83=2;
                    int LA83_0 = input.LA(1);

                    if ( (LA83_0==83) ) {
                        alt83=1;
                    }
                    switch (alt83) {
                        case 1 :
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1871:6: otherlv_26= 'RekeyRequired:' ( (lv_rekeyRequired_27_0= ruleYesNo ) )
                            {
                            otherlv_26=(Token)match(input,83,FOLLOW_83_in_ruleField3307); 

                                	newLeafNode(otherlv_26, grammarAccess.getFieldAccess().getRekeyRequiredKeyword_2_13_0());
                                
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1875:1: ( (lv_rekeyRequired_27_0= ruleYesNo ) )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1876:1: (lv_rekeyRequired_27_0= ruleYesNo )
                            {
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1876:1: (lv_rekeyRequired_27_0= ruleYesNo )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1877:3: lv_rekeyRequired_27_0= ruleYesNo
                            {
                             
                            	        newCompositeNode(grammarAccess.getFieldAccess().getRekeyRequiredYesNoEnumRuleCall_2_13_1_0()); 
                            	    
                            pushFollow(FOLLOW_ruleYesNo_in_ruleField3328);
                            lv_rekeyRequired_27_0=ruleYesNo();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getFieldRule());
                            	        }
                                   		set(
                                   			current, 
                                   			"rekeyRequired",
                                    		lv_rekeyRequired_27_0, 
                                    		"YesNo");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }


                            }
                            break;

                    }

                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1893:4: (otherlv_28= 'hyperlink:' ( (lv_hyperlink_29_0= RULE_STRING ) ) )?
                    int alt84=2;
                    int LA84_0 = input.LA(1);

                    if ( (LA84_0==84) ) {
                        alt84=1;
                    }
                    switch (alt84) {
                        case 1 :
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1893:6: otherlv_28= 'hyperlink:' ( (lv_hyperlink_29_0= RULE_STRING ) )
                            {
                            otherlv_28=(Token)match(input,84,FOLLOW_84_in_ruleField3343); 

                                	newLeafNode(otherlv_28, grammarAccess.getFieldAccess().getHyperlinkKeyword_2_14_0());
                                
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1897:1: ( (lv_hyperlink_29_0= RULE_STRING ) )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1898:1: (lv_hyperlink_29_0= RULE_STRING )
                            {
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1898:1: (lv_hyperlink_29_0= RULE_STRING )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1899:3: lv_hyperlink_29_0= RULE_STRING
                            {
                            lv_hyperlink_29_0=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleField3360); 

                            			newLeafNode(lv_hyperlink_29_0, grammarAccess.getFieldAccess().getHyperlinkSTRINGTerminalRuleCall_2_14_1_0()); 
                            		

                            	        if (current==null) {
                            	            current = createModelElement(grammarAccess.getFieldRule());
                            	        }
                                   		setWithLastConsumed(
                                   			current, 
                                   			"hyperlink",
                                    		lv_hyperlink_29_0, 
                                    		"STRING");
                            	    

                            }


                            }


                            }
                            break;

                    }

                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1915:4: (otherlv_30= 'hotValidate:' ( (lv_hotValidate_31_0= ruleYesNo ) ) )?
                    int alt85=2;
                    int LA85_0 = input.LA(1);

                    if ( (LA85_0==85) ) {
                        alt85=1;
                    }
                    switch (alt85) {
                        case 1 :
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1915:6: otherlv_30= 'hotValidate:' ( (lv_hotValidate_31_0= ruleYesNo ) )
                            {
                            otherlv_30=(Token)match(input,85,FOLLOW_85_in_ruleField3380); 

                                	newLeafNode(otherlv_30, grammarAccess.getFieldAccess().getHotValidateKeyword_2_15_0());
                                
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1919:1: ( (lv_hotValidate_31_0= ruleYesNo ) )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1920:1: (lv_hotValidate_31_0= ruleYesNo )
                            {
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1920:1: (lv_hotValidate_31_0= ruleYesNo )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1921:3: lv_hotValidate_31_0= ruleYesNo
                            {
                             
                            	        newCompositeNode(grammarAccess.getFieldAccess().getHotValidateYesNoEnumRuleCall_2_15_1_0()); 
                            	    
                            pushFollow(FOLLOW_ruleYesNo_in_ruleField3401);
                            lv_hotValidate_31_0=ruleYesNo();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getFieldRule());
                            	        }
                                   		set(
                                   			current, 
                                   			"hotValidate",
                                    		lv_hotValidate_31_0, 
                                    		"YesNo");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }


                            }
                            break;

                    }

                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1937:4: (otherlv_32= 'hotField:' ( (lv_hotField_33_0= ruleYesNo ) ) )?
                    int alt86=2;
                    int LA86_0 = input.LA(1);

                    if ( (LA86_0==86) ) {
                        alt86=1;
                    }
                    switch (alt86) {
                        case 1 :
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1937:6: otherlv_32= 'hotField:' ( (lv_hotField_33_0= ruleYesNo ) )
                            {
                            otherlv_32=(Token)match(input,86,FOLLOW_86_in_ruleField3416); 

                                	newLeafNode(otherlv_32, grammarAccess.getFieldAccess().getHotFieldKeyword_2_16_0());
                                
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1941:1: ( (lv_hotField_33_0= ruleYesNo ) )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1942:1: (lv_hotField_33_0= ruleYesNo )
                            {
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1942:1: (lv_hotField_33_0= ruleYesNo )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1943:3: lv_hotField_33_0= ruleYesNo
                            {
                             
                            	        newCompositeNode(grammarAccess.getFieldAccess().getHotFieldYesNoEnumRuleCall_2_16_1_0()); 
                            	    
                            pushFollow(FOLLOW_ruleYesNo_in_ruleField3437);
                            lv_hotField_33_0=ruleYesNo();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getFieldRule());
                            	        }
                                   		set(
                                   			current, 
                                   			"hotField",
                                    		lv_hotField_33_0, 
                                    		"YesNo");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }


                            }
                            break;

                    }

                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1959:4: (otherlv_34= 'webValidate:' ( (lv_webValidate_35_0= ruleYesNo ) ) )?
                    int alt87=2;
                    int LA87_0 = input.LA(1);

                    if ( (LA87_0==87) ) {
                        alt87=1;
                    }
                    switch (alt87) {
                        case 1 :
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1959:6: otherlv_34= 'webValidate:' ( (lv_webValidate_35_0= ruleYesNo ) )
                            {
                            otherlv_34=(Token)match(input,87,FOLLOW_87_in_ruleField3452); 

                                	newLeafNode(otherlv_34, grammarAccess.getFieldAccess().getWebValidateKeyword_2_17_0());
                                
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1963:1: ( (lv_webValidate_35_0= ruleYesNo ) )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1964:1: (lv_webValidate_35_0= ruleYesNo )
                            {
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1964:1: (lv_webValidate_35_0= ruleYesNo )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1965:3: lv_webValidate_35_0= ruleYesNo
                            {
                             
                            	        newCompositeNode(grammarAccess.getFieldAccess().getWebValidateYesNoEnumRuleCall_2_17_1_0()); 
                            	    
                            pushFollow(FOLLOW_ruleYesNo_in_ruleField3473);
                            lv_webValidate_35_0=ruleYesNo();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getFieldRule());
                            	        }
                                   		set(
                                   			current, 
                                   			"webValidate",
                                    		lv_webValidate_35_0, 
                                    		"YesNo");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }


                            }
                            break;

                    }

                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1981:4: (otherlv_36= 'selectionEnquiry:' ( (lv_selectionEnquiry_37_0= RULE_STRING ) ) )?
                    int alt88=2;
                    int LA88_0 = input.LA(1);

                    if ( (LA88_0==88) ) {
                        alt88=1;
                    }
                    switch (alt88) {
                        case 1 :
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1981:6: otherlv_36= 'selectionEnquiry:' ( (lv_selectionEnquiry_37_0= RULE_STRING ) )
                            {
                            otherlv_36=(Token)match(input,88,FOLLOW_88_in_ruleField3488); 

                                	newLeafNode(otherlv_36, grammarAccess.getFieldAccess().getSelectionEnquiryKeyword_2_18_0());
                                
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1985:1: ( (lv_selectionEnquiry_37_0= RULE_STRING ) )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1986:1: (lv_selectionEnquiry_37_0= RULE_STRING )
                            {
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1986:1: (lv_selectionEnquiry_37_0= RULE_STRING )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:1987:3: lv_selectionEnquiry_37_0= RULE_STRING
                            {
                            lv_selectionEnquiry_37_0=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleField3505); 

                            			newLeafNode(lv_selectionEnquiry_37_0, grammarAccess.getFieldAccess().getSelectionEnquirySTRINGTerminalRuleCall_2_18_1_0()); 
                            		

                            	        if (current==null) {
                            	            current = createModelElement(grammarAccess.getFieldRule());
                            	        }
                                   		setWithLastConsumed(
                                   			current, 
                                   			"selectionEnquiry",
                                    		lv_selectionEnquiry_37_0, 
                                    		"STRING");
                            	    

                            }


                            }


                            }
                            break;

                    }

                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2003:4: (otherlv_38= 'enquiryParameter:' ( (lv_enquiryParameter_39_0= RULE_STRING ) ) )?
                    int alt89=2;
                    int LA89_0 = input.LA(1);

                    if ( (LA89_0==89) ) {
                        alt89=1;
                    }
                    switch (alt89) {
                        case 1 :
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2003:6: otherlv_38= 'enquiryParameter:' ( (lv_enquiryParameter_39_0= RULE_STRING ) )
                            {
                            otherlv_38=(Token)match(input,89,FOLLOW_89_in_ruleField3525); 

                                	newLeafNode(otherlv_38, grammarAccess.getFieldAccess().getEnquiryParameterKeyword_2_19_0());
                                
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2007:1: ( (lv_enquiryParameter_39_0= RULE_STRING ) )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2008:1: (lv_enquiryParameter_39_0= RULE_STRING )
                            {
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2008:1: (lv_enquiryParameter_39_0= RULE_STRING )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2009:3: lv_enquiryParameter_39_0= RULE_STRING
                            {
                            lv_enquiryParameter_39_0=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleField3542); 

                            			newLeafNode(lv_enquiryParameter_39_0, grammarAccess.getFieldAccess().getEnquiryParameterSTRINGTerminalRuleCall_2_19_1_0()); 
                            		

                            	        if (current==null) {
                            	            current = createModelElement(grammarAccess.getFieldRule());
                            	        }
                                   		setWithLastConsumed(
                                   			current, 
                                   			"enquiryParameter",
                                    		lv_enquiryParameter_39_0, 
                                    		"STRING");
                            	    

                            }


                            }


                            }
                            break;

                    }

                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2025:4: (otherlv_40= 'popupBehaviour:' ( (lv_popupBehaviour_41_0= rulePopupBehaviour ) ) )?
                    int alt90=2;
                    int LA90_0 = input.LA(1);

                    if ( (LA90_0==90) ) {
                        alt90=1;
                    }
                    switch (alt90) {
                        case 1 :
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2025:6: otherlv_40= 'popupBehaviour:' ( (lv_popupBehaviour_41_0= rulePopupBehaviour ) )
                            {
                            otherlv_40=(Token)match(input,90,FOLLOW_90_in_ruleField3562); 

                                	newLeafNode(otherlv_40, grammarAccess.getFieldAccess().getPopupBehaviourKeyword_2_20_0());
                                
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2029:1: ( (lv_popupBehaviour_41_0= rulePopupBehaviour ) )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2030:1: (lv_popupBehaviour_41_0= rulePopupBehaviour )
                            {
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2030:1: (lv_popupBehaviour_41_0= rulePopupBehaviour )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2031:3: lv_popupBehaviour_41_0= rulePopupBehaviour
                            {
                             
                            	        newCompositeNode(grammarAccess.getFieldAccess().getPopupBehaviourPopupBehaviourEnumRuleCall_2_20_1_0()); 
                            	    
                            pushFollow(FOLLOW_rulePopupBehaviour_in_ruleField3583);
                            lv_popupBehaviour_41_0=rulePopupBehaviour();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getFieldRule());
                            	        }
                                   		set(
                                   			current, 
                                   			"popupBehaviour",
                                    		lv_popupBehaviour_41_0, 
                                    		"PopupBehaviour");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }


                            }
                            break;

                    }

                    otherlv_42=(Token)match(input,31,FOLLOW_31_in_ruleField3597); 

                        	newLeafNode(otherlv_42, grammarAccess.getFieldAccess().getRightCurlyBracketKeyword_2_21());
                        

                    }
                    break;

            }

            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2051:3: (otherlv_43= 'default:' ( (lv_defaults_44_0= ruleDefault ) ) (otherlv_45= 'OR' ( (lv_defaults_46_0= ruleDefault ) ) )* )?
            int alt93=2;
            int LA93_0 = input.LA(1);

            if ( (LA93_0==91) ) {
                alt93=1;
            }
            switch (alt93) {
                case 1 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2051:5: otherlv_43= 'default:' ( (lv_defaults_44_0= ruleDefault ) ) (otherlv_45= 'OR' ( (lv_defaults_46_0= ruleDefault ) ) )*
                    {
                    otherlv_43=(Token)match(input,91,FOLLOW_91_in_ruleField3612); 

                        	newLeafNode(otherlv_43, grammarAccess.getFieldAccess().getDefaultKeyword_3_0());
                        
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2055:1: ( (lv_defaults_44_0= ruleDefault ) )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2056:1: (lv_defaults_44_0= ruleDefault )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2056:1: (lv_defaults_44_0= ruleDefault )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2057:3: lv_defaults_44_0= ruleDefault
                    {
                     
                    	        newCompositeNode(grammarAccess.getFieldAccess().getDefaultsDefaultParserRuleCall_3_1_0()); 
                    	    
                    pushFollow(FOLLOW_ruleDefault_in_ruleField3633);
                    lv_defaults_44_0=ruleDefault();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getFieldRule());
                    	        }
                           		add(
                           			current, 
                           			"defaults",
                            		lv_defaults_44_0, 
                            		"Default");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }

                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2073:2: (otherlv_45= 'OR' ( (lv_defaults_46_0= ruleDefault ) ) )*
                    loop92:
                    do {
                        int alt92=2;
                        int LA92_0 = input.LA(1);

                        if ( (LA92_0==92) ) {
                            alt92=1;
                        }


                        switch (alt92) {
                    	case 1 :
                    	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2073:4: otherlv_45= 'OR' ( (lv_defaults_46_0= ruleDefault ) )
                    	    {
                    	    otherlv_45=(Token)match(input,92,FOLLOW_92_in_ruleField3646); 

                    	        	newLeafNode(otherlv_45, grammarAccess.getFieldAccess().getORKeyword_3_2_0());
                    	        
                    	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2077:1: ( (lv_defaults_46_0= ruleDefault ) )
                    	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2078:1: (lv_defaults_46_0= ruleDefault )
                    	    {
                    	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2078:1: (lv_defaults_46_0= ruleDefault )
                    	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2079:3: lv_defaults_46_0= ruleDefault
                    	    {
                    	     
                    	    	        newCompositeNode(grammarAccess.getFieldAccess().getDefaultsDefaultParserRuleCall_3_2_1_0()); 
                    	    	    
                    	    pushFollow(FOLLOW_ruleDefault_in_ruleField3667);
                    	    lv_defaults_46_0=ruleDefault();

                    	    state._fsp--;


                    	    	        if (current==null) {
                    	    	            current = createModelElementForParent(grammarAccess.getFieldRule());
                    	    	        }
                    	           		add(
                    	           			current, 
                    	           			"defaults",
                    	            		lv_defaults_46_0, 
                    	            		"Default");
                    	    	        afterParserOrEnumRuleCall();
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop92;
                        }
                    } while (true);


                    }
                    break;

            }

            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2095:6: (otherlv_47= 'Translations:' (otherlv_48= 'label:' ( (lv_label_49_0= ruleTranslations ) ) )? (otherlv_50= 'toolTip:' ( (lv_toolTip_51_0= ruleTranslations ) ) )? )?
            int alt96=2;
            int LA96_0 = input.LA(1);

            if ( (LA96_0==93) ) {
                alt96=1;
            }
            switch (alt96) {
                case 1 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2095:8: otherlv_47= 'Translations:' (otherlv_48= 'label:' ( (lv_label_49_0= ruleTranslations ) ) )? (otherlv_50= 'toolTip:' ( (lv_toolTip_51_0= ruleTranslations ) ) )?
                    {
                    otherlv_47=(Token)match(input,93,FOLLOW_93_in_ruleField3684); 

                        	newLeafNode(otherlv_47, grammarAccess.getFieldAccess().getTranslationsKeyword_4_0());
                        
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2099:1: (otherlv_48= 'label:' ( (lv_label_49_0= ruleTranslations ) ) )?
                    int alt94=2;
                    int LA94_0 = input.LA(1);

                    if ( (LA94_0==94) ) {
                        alt94=1;
                    }
                    switch (alt94) {
                        case 1 :
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2099:3: otherlv_48= 'label:' ( (lv_label_49_0= ruleTranslations ) )
                            {
                            otherlv_48=(Token)match(input,94,FOLLOW_94_in_ruleField3697); 

                                	newLeafNode(otherlv_48, grammarAccess.getFieldAccess().getLabelKeyword_4_1_0());
                                
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2103:1: ( (lv_label_49_0= ruleTranslations ) )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2104:1: (lv_label_49_0= ruleTranslations )
                            {
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2104:1: (lv_label_49_0= ruleTranslations )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2105:3: lv_label_49_0= ruleTranslations
                            {
                             
                            	        newCompositeNode(grammarAccess.getFieldAccess().getLabelTranslationsParserRuleCall_4_1_1_0()); 
                            	    
                            pushFollow(FOLLOW_ruleTranslations_in_ruleField3718);
                            lv_label_49_0=ruleTranslations();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getFieldRule());
                            	        }
                                   		set(
                                   			current, 
                                   			"label",
                                    		lv_label_49_0, 
                                    		"Translations");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }


                            }
                            break;

                    }

                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2121:4: (otherlv_50= 'toolTip:' ( (lv_toolTip_51_0= ruleTranslations ) ) )?
                    int alt95=2;
                    int LA95_0 = input.LA(1);

                    if ( (LA95_0==95) ) {
                        alt95=1;
                    }
                    switch (alt95) {
                        case 1 :
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2121:6: otherlv_50= 'toolTip:' ( (lv_toolTip_51_0= ruleTranslations ) )
                            {
                            otherlv_50=(Token)match(input,95,FOLLOW_95_in_ruleField3733); 

                                	newLeafNode(otherlv_50, grammarAccess.getFieldAccess().getToolTipKeyword_4_2_0());
                                
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2125:1: ( (lv_toolTip_51_0= ruleTranslations ) )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2126:1: (lv_toolTip_51_0= ruleTranslations )
                            {
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2126:1: (lv_toolTip_51_0= ruleTranslations )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2127:3: lv_toolTip_51_0= ruleTranslations
                            {
                             
                            	        newCompositeNode(grammarAccess.getFieldAccess().getToolTipTranslationsParserRuleCall_4_2_1_0()); 
                            	    
                            pushFollow(FOLLOW_ruleTranslations_in_ruleField3754);
                            lv_toolTip_51_0=ruleTranslations();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getFieldRule());
                            	        }
                                   		set(
                                   			current, 
                                   			"toolTip",
                                    		lv_toolTip_51_0, 
                                    		"Translations");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }


                            }
                            break;

                    }


                    }
                    break;

            }

            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2143:6: (otherlv_52= 'API' otherlv_53= '{' (otherlv_54= 'validation-routines:' ( (lv_validationRoutines_55_0= ruleRoutine ) ) (otherlv_56= ';' ( (lv_validationRoutines_57_0= ruleRoutine ) ) )* )? otherlv_58= '}' )?
            int alt99=2;
            int LA99_0 = input.LA(1);

            if ( (LA99_0==48) ) {
                alt99=1;
            }
            switch (alt99) {
                case 1 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2143:8: otherlv_52= 'API' otherlv_53= '{' (otherlv_54= 'validation-routines:' ( (lv_validationRoutines_55_0= ruleRoutine ) ) (otherlv_56= ';' ( (lv_validationRoutines_57_0= ruleRoutine ) ) )* )? otherlv_58= '}'
                    {
                    otherlv_52=(Token)match(input,48,FOLLOW_48_in_ruleField3771); 

                        	newLeafNode(otherlv_52, grammarAccess.getFieldAccess().getAPIKeyword_5_0());
                        
                    otherlv_53=(Token)match(input,19,FOLLOW_19_in_ruleField3783); 

                        	newLeafNode(otherlv_53, grammarAccess.getFieldAccess().getLeftCurlyBracketKeyword_5_1());
                        
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2151:1: (otherlv_54= 'validation-routines:' ( (lv_validationRoutines_55_0= ruleRoutine ) ) (otherlv_56= ';' ( (lv_validationRoutines_57_0= ruleRoutine ) ) )* )?
                    int alt98=2;
                    int LA98_0 = input.LA(1);

                    if ( (LA98_0==96) ) {
                        alt98=1;
                    }
                    switch (alt98) {
                        case 1 :
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2151:3: otherlv_54= 'validation-routines:' ( (lv_validationRoutines_55_0= ruleRoutine ) ) (otherlv_56= ';' ( (lv_validationRoutines_57_0= ruleRoutine ) ) )*
                            {
                            otherlv_54=(Token)match(input,96,FOLLOW_96_in_ruleField3796); 

                                	newLeafNode(otherlv_54, grammarAccess.getFieldAccess().getValidationRoutinesKeyword_5_2_0());
                                
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2155:1: ( (lv_validationRoutines_55_0= ruleRoutine ) )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2156:1: (lv_validationRoutines_55_0= ruleRoutine )
                            {
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2156:1: (lv_validationRoutines_55_0= ruleRoutine )
                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2157:3: lv_validationRoutines_55_0= ruleRoutine
                            {
                             
                            	        newCompositeNode(grammarAccess.getFieldAccess().getValidationRoutinesRoutineParserRuleCall_5_2_1_0()); 
                            	    
                            pushFollow(FOLLOW_ruleRoutine_in_ruleField3817);
                            lv_validationRoutines_55_0=ruleRoutine();

                            state._fsp--;


                            	        if (current==null) {
                            	            current = createModelElementForParent(grammarAccess.getFieldRule());
                            	        }
                                   		add(
                                   			current, 
                                   			"validationRoutines",
                                    		lv_validationRoutines_55_0, 
                                    		"Routine");
                            	        afterParserOrEnumRuleCall();
                            	    

                            }


                            }

                            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2173:2: (otherlv_56= ';' ( (lv_validationRoutines_57_0= ruleRoutine ) ) )*
                            loop97:
                            do {
                                int alt97=2;
                                int LA97_0 = input.LA(1);

                                if ( (LA97_0==24) ) {
                                    alt97=1;
                                }


                                switch (alt97) {
                            	case 1 :
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2173:4: otherlv_56= ';' ( (lv_validationRoutines_57_0= ruleRoutine ) )
                            	    {
                            	    otherlv_56=(Token)match(input,24,FOLLOW_24_in_ruleField3830); 

                            	        	newLeafNode(otherlv_56, grammarAccess.getFieldAccess().getSemicolonKeyword_5_2_2_0());
                            	        
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2177:1: ( (lv_validationRoutines_57_0= ruleRoutine ) )
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2178:1: (lv_validationRoutines_57_0= ruleRoutine )
                            	    {
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2178:1: (lv_validationRoutines_57_0= ruleRoutine )
                            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2179:3: lv_validationRoutines_57_0= ruleRoutine
                            	    {
                            	     
                            	    	        newCompositeNode(grammarAccess.getFieldAccess().getValidationRoutinesRoutineParserRuleCall_5_2_2_1_0()); 
                            	    	    
                            	    pushFollow(FOLLOW_ruleRoutine_in_ruleField3851);
                            	    lv_validationRoutines_57_0=ruleRoutine();

                            	    state._fsp--;


                            	    	        if (current==null) {
                            	    	            current = createModelElementForParent(grammarAccess.getFieldRule());
                            	    	        }
                            	           		add(
                            	           			current, 
                            	           			"validationRoutines",
                            	            		lv_validationRoutines_57_0, 
                            	            		"Routine");
                            	    	        afterParserOrEnumRuleCall();
                            	    	    

                            	    }


                            	    }


                            	    }
                            	    break;

                            	default :
                            	    break loop97;
                                }
                            } while (true);


                            }
                            break;

                    }

                    otherlv_58=(Token)match(input,31,FOLLOW_31_in_ruleField3867); 

                        	newLeafNode(otherlv_58, grammarAccess.getFieldAccess().getRightCurlyBracketKeyword_5_3());
                        

                    }
                    break;

            }

            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2199:3: (otherlv_59= 'Attributes:' ( (lv_attributes_60_0= RULE_STRING ) ) (otherlv_61= ';' ( (lv_attributes_62_0= RULE_STRING ) ) )* )?
            int alt101=2;
            int LA101_0 = input.LA(1);

            if ( (LA101_0==97) ) {
                alt101=1;
            }
            switch (alt101) {
                case 1 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2199:5: otherlv_59= 'Attributes:' ( (lv_attributes_60_0= RULE_STRING ) ) (otherlv_61= ';' ( (lv_attributes_62_0= RULE_STRING ) ) )*
                    {
                    otherlv_59=(Token)match(input,97,FOLLOW_97_in_ruleField3882); 

                        	newLeafNode(otherlv_59, grammarAccess.getFieldAccess().getAttributesKeyword_6_0());
                        
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2203:1: ( (lv_attributes_60_0= RULE_STRING ) )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2204:1: (lv_attributes_60_0= RULE_STRING )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2204:1: (lv_attributes_60_0= RULE_STRING )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2205:3: lv_attributes_60_0= RULE_STRING
                    {
                    lv_attributes_60_0=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleField3899); 

                    			newLeafNode(lv_attributes_60_0, grammarAccess.getFieldAccess().getAttributesSTRINGTerminalRuleCall_6_1_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getFieldRule());
                    	        }
                           		addWithLastConsumed(
                           			current, 
                           			"attributes",
                            		lv_attributes_60_0, 
                            		"STRING");
                    	    

                    }


                    }

                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2221:2: (otherlv_61= ';' ( (lv_attributes_62_0= RULE_STRING ) ) )*
                    loop100:
                    do {
                        int alt100=2;
                        int LA100_0 = input.LA(1);

                        if ( (LA100_0==24) ) {
                            alt100=1;
                        }


                        switch (alt100) {
                    	case 1 :
                    	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2221:4: otherlv_61= ';' ( (lv_attributes_62_0= RULE_STRING ) )
                    	    {
                    	    otherlv_61=(Token)match(input,24,FOLLOW_24_in_ruleField3917); 

                    	        	newLeafNode(otherlv_61, grammarAccess.getFieldAccess().getSemicolonKeyword_6_2_0());
                    	        
                    	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2225:1: ( (lv_attributes_62_0= RULE_STRING ) )
                    	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2226:1: (lv_attributes_62_0= RULE_STRING )
                    	    {
                    	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2226:1: (lv_attributes_62_0= RULE_STRING )
                    	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2227:3: lv_attributes_62_0= RULE_STRING
                    	    {
                    	    lv_attributes_62_0=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleField3934); 

                    	    			newLeafNode(lv_attributes_62_0, grammarAccess.getFieldAccess().getAttributesSTRINGTerminalRuleCall_6_2_1_0()); 
                    	    		

                    	    	        if (current==null) {
                    	    	            current = createModelElement(grammarAccess.getFieldRule());
                    	    	        }
                    	           		addWithLastConsumed(
                    	           			current, 
                    	           			"attributes",
                    	            		lv_attributes_62_0, 
                    	            		"STRING");
                    	    	    

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop100;
                        }
                    } while (true);


                    }
                    break;

            }

            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2243:6: (otherlv_63= 'MV:' ( (lv_MV_64_0= ruleINTEGER_OBJECT ) ) )?
            int alt102=2;
            int LA102_0 = input.LA(1);

            if ( (LA102_0==98) ) {
                alt102=1;
            }
            switch (alt102) {
                case 1 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2243:8: otherlv_63= 'MV:' ( (lv_MV_64_0= ruleINTEGER_OBJECT ) )
                    {
                    otherlv_63=(Token)match(input,98,FOLLOW_98_in_ruleField3956); 

                        	newLeafNode(otherlv_63, grammarAccess.getFieldAccess().getMVKeyword_7_0());
                        
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2247:1: ( (lv_MV_64_0= ruleINTEGER_OBJECT ) )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2248:1: (lv_MV_64_0= ruleINTEGER_OBJECT )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2248:1: (lv_MV_64_0= ruleINTEGER_OBJECT )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2249:3: lv_MV_64_0= ruleINTEGER_OBJECT
                    {
                     
                    	        newCompositeNode(grammarAccess.getFieldAccess().getMVINTEGER_OBJECTParserRuleCall_7_1_0()); 
                    	    
                    pushFollow(FOLLOW_ruleINTEGER_OBJECT_in_ruleField3977);
                    lv_MV_64_0=ruleINTEGER_OBJECT();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getFieldRule());
                    	        }
                           		set(
                           			current, 
                           			"MV",
                            		lv_MV_64_0, 
                            		"INTEGER_OBJECT");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }


                    }
                    break;

            }

            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2265:4: (otherlv_65= 'SV:' ( (lv_SV_66_0= ruleINTEGER_OBJECT ) ) )?
            int alt103=2;
            int LA103_0 = input.LA(1);

            if ( (LA103_0==99) ) {
                alt103=1;
            }
            switch (alt103) {
                case 1 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2265:6: otherlv_65= 'SV:' ( (lv_SV_66_0= ruleINTEGER_OBJECT ) )
                    {
                    otherlv_65=(Token)match(input,99,FOLLOW_99_in_ruleField3992); 

                        	newLeafNode(otherlv_65, grammarAccess.getFieldAccess().getSVKeyword_8_0());
                        
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2269:1: ( (lv_SV_66_0= ruleINTEGER_OBJECT ) )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2270:1: (lv_SV_66_0= ruleINTEGER_OBJECT )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2270:1: (lv_SV_66_0= ruleINTEGER_OBJECT )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2271:3: lv_SV_66_0= ruleINTEGER_OBJECT
                    {
                     
                    	        newCompositeNode(grammarAccess.getFieldAccess().getSVINTEGER_OBJECTParserRuleCall_8_1_0()); 
                    	    
                    pushFollow(FOLLOW_ruleINTEGER_OBJECT_in_ruleField4013);
                    lv_SV_66_0=ruleINTEGER_OBJECT();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getFieldRule());
                    	        }
                           		set(
                           			current, 
                           			"SV",
                            		lv_SV_66_0, 
                            		"INTEGER_OBJECT");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }


                    }
                    break;

            }

            otherlv_67=(Token)match(input,31,FOLLOW_31_in_ruleField4027); 

                	newLeafNode(otherlv_67, grammarAccess.getFieldAccess().getRightCurlyBracketKeyword_9());
                

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
    // $ANTLR end "ruleField"


    // $ANTLR start "entryRuleEntityRef"
    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2299:1: entryRuleEntityRef returns [String current=null] : iv_ruleEntityRef= ruleEntityRef EOF ;
    public final String entryRuleEntityRef() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleEntityRef = null;


        try {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2300:2: (iv_ruleEntityRef= ruleEntityRef EOF )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2301:2: iv_ruleEntityRef= ruleEntityRef EOF
            {
             newCompositeNode(grammarAccess.getEntityRefRule()); 
            pushFollow(FOLLOW_ruleEntityRef_in_entryRuleEntityRef4064);
            iv_ruleEntityRef=ruleEntityRef();

            state._fsp--;

             current =iv_ruleEntityRef.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleEntityRef4075); 

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
    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2308:1: ruleEntityRef returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_NID_0= ruleNID kw= ':' this_NID_2= ruleNID ) ;
    public final AntlrDatatypeRuleToken ruleEntityRef() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_NID_0 = null;

        AntlrDatatypeRuleToken this_NID_2 = null;


         enterRule(); 
            
        try {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2311:28: ( (this_NID_0= ruleNID kw= ':' this_NID_2= ruleNID ) )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2312:1: (this_NID_0= ruleNID kw= ':' this_NID_2= ruleNID )
            {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2312:1: (this_NID_0= ruleNID kw= ':' this_NID_2= ruleNID )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2313:5: this_NID_0= ruleNID kw= ':' this_NID_2= ruleNID
            {
             
                    newCompositeNode(grammarAccess.getEntityRefAccess().getNIDParserRuleCall_0()); 
                
            pushFollow(FOLLOW_ruleNID_in_ruleEntityRef4122);
            this_NID_0=ruleNID();

            state._fsp--;


            		current.merge(this_NID_0);
                
             
                    afterParserOrEnumRuleCall();
                
            kw=(Token)match(input,100,FOLLOW_100_in_ruleEntityRef4140); 

                    current.merge(kw);
                    newLeafNode(kw, grammarAccess.getEntityRefAccess().getColonKeyword_1()); 
                
             
                    newCompositeNode(grammarAccess.getEntityRefAccess().getNIDParserRuleCall_2()); 
                
            pushFollow(FOLLOW_ruleNID_in_ruleEntityRef4162);
            this_NID_2=ruleNID();

            state._fsp--;


            		current.merge(this_NID_2);
                
             
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
    // $ANTLR end "ruleEntityRef"


    // $ANTLR start "entryRuleVersionRef"
    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2348:1: entryRuleVersionRef returns [String current=null] : iv_ruleVersionRef= ruleVersionRef EOF ;
    public final String entryRuleVersionRef() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleVersionRef = null;


        try {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2349:2: (iv_ruleVersionRef= ruleVersionRef EOF )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2350:2: iv_ruleVersionRef= ruleVersionRef EOF
            {
             newCompositeNode(grammarAccess.getVersionRefRule()); 
            pushFollow(FOLLOW_ruleVersionRef_in_entryRuleVersionRef4208);
            iv_ruleVersionRef=ruleVersionRef();

            state._fsp--;

             current =iv_ruleVersionRef.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleVersionRef4219); 

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
    // $ANTLR end "entryRuleVersionRef"


    // $ANTLR start "ruleVersionRef"
    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2357:1: ruleVersionRef returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_NID_0= ruleNID kw= ',' (this_NID_2= ruleNID )? ) ;
    public final AntlrDatatypeRuleToken ruleVersionRef() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_NID_0 = null;

        AntlrDatatypeRuleToken this_NID_2 = null;


         enterRule(); 
            
        try {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2360:28: ( (this_NID_0= ruleNID kw= ',' (this_NID_2= ruleNID )? ) )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2361:1: (this_NID_0= ruleNID kw= ',' (this_NID_2= ruleNID )? )
            {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2361:1: (this_NID_0= ruleNID kw= ',' (this_NID_2= ruleNID )? )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2362:5: this_NID_0= ruleNID kw= ',' (this_NID_2= ruleNID )?
            {
             
                    newCompositeNode(grammarAccess.getVersionRefAccess().getNIDParserRuleCall_0()); 
                
            pushFollow(FOLLOW_ruleNID_in_ruleVersionRef4266);
            this_NID_0=ruleNID();

            state._fsp--;


            		current.merge(this_NID_0);
                
             
                    afterParserOrEnumRuleCall();
                
            kw=(Token)match(input,12,FOLLOW_12_in_ruleVersionRef4284); 

                    current.merge(kw);
                    newLeafNode(kw, grammarAccess.getVersionRefAccess().getCommaKeyword_1()); 
                
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2378:1: (this_NID_2= ruleNID )?
            int alt104=2;
            int LA104_0 = input.LA(1);

            if ( ((LA104_0>=RULE_ID && LA104_0<=RULE_INT)||(LA104_0>=111 && LA104_0<=158)) ) {
                alt104=1;
            }
            switch (alt104) {
                case 1 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2379:5: this_NID_2= ruleNID
                    {
                     
                            newCompositeNode(grammarAccess.getVersionRefAccess().getNIDParserRuleCall_2()); 
                        
                    pushFollow(FOLLOW_ruleNID_in_ruleVersionRef4307);
                    this_NID_2=ruleNID();

                    state._fsp--;


                    		current.merge(this_NID_2);
                        
                     
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
    // $ANTLR end "ruleVersionRef"


    // $ANTLR start "entryRuleDefault"
    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2397:1: entryRuleDefault returns [EObject current=null] : iv_ruleDefault= ruleDefault EOF ;
    public final EObject entryRuleDefault() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDefault = null;


        try {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2398:2: (iv_ruleDefault= ruleDefault EOF )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2399:2: iv_ruleDefault= ruleDefault EOF
            {
             newCompositeNode(grammarAccess.getDefaultRule()); 
            pushFollow(FOLLOW_ruleDefault_in_entryRuleDefault4354);
            iv_ruleDefault=ruleDefault();

            state._fsp--;

             current =iv_ruleDefault; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleDefault4364); 

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
    // $ANTLR end "entryRuleDefault"


    // $ANTLR start "ruleDefault"
    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2406:1: ruleDefault returns [EObject current=null] : (otherlv_0= 'IF' otherlv_1= '(' ( (lv_mv_2_0= ruleINTEGER_OBJECT ) )? (otherlv_3= '-' ( (lv_sv_4_0= ruleINTEGER_OBJECT ) ) )? otherlv_5= ')' ( (lv_defaultIfOldValueEquals_6_0= RULE_STRING ) ) otherlv_7= 'THEN' ( (lv_defaultNewValueOrAtRoutine_8_0= ruleValueOrAtRoutine ) ) ) ;
    public final EObject ruleDefault() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token lv_defaultIfOldValueEquals_6_0=null;
        Token otherlv_7=null;
        AntlrDatatypeRuleToken lv_mv_2_0 = null;

        AntlrDatatypeRuleToken lv_sv_4_0 = null;

        EObject lv_defaultNewValueOrAtRoutine_8_0 = null;


         enterRule(); 
            
        try {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2409:28: ( (otherlv_0= 'IF' otherlv_1= '(' ( (lv_mv_2_0= ruleINTEGER_OBJECT ) )? (otherlv_3= '-' ( (lv_sv_4_0= ruleINTEGER_OBJECT ) ) )? otherlv_5= ')' ( (lv_defaultIfOldValueEquals_6_0= RULE_STRING ) ) otherlv_7= 'THEN' ( (lv_defaultNewValueOrAtRoutine_8_0= ruleValueOrAtRoutine ) ) ) )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2410:1: (otherlv_0= 'IF' otherlv_1= '(' ( (lv_mv_2_0= ruleINTEGER_OBJECT ) )? (otherlv_3= '-' ( (lv_sv_4_0= ruleINTEGER_OBJECT ) ) )? otherlv_5= ')' ( (lv_defaultIfOldValueEquals_6_0= RULE_STRING ) ) otherlv_7= 'THEN' ( (lv_defaultNewValueOrAtRoutine_8_0= ruleValueOrAtRoutine ) ) )
            {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2410:1: (otherlv_0= 'IF' otherlv_1= '(' ( (lv_mv_2_0= ruleINTEGER_OBJECT ) )? (otherlv_3= '-' ( (lv_sv_4_0= ruleINTEGER_OBJECT ) ) )? otherlv_5= ')' ( (lv_defaultIfOldValueEquals_6_0= RULE_STRING ) ) otherlv_7= 'THEN' ( (lv_defaultNewValueOrAtRoutine_8_0= ruleValueOrAtRoutine ) ) )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2410:3: otherlv_0= 'IF' otherlv_1= '(' ( (lv_mv_2_0= ruleINTEGER_OBJECT ) )? (otherlv_3= '-' ( (lv_sv_4_0= ruleINTEGER_OBJECT ) ) )? otherlv_5= ')' ( (lv_defaultIfOldValueEquals_6_0= RULE_STRING ) ) otherlv_7= 'THEN' ( (lv_defaultNewValueOrAtRoutine_8_0= ruleValueOrAtRoutine ) )
            {
            otherlv_0=(Token)match(input,101,FOLLOW_101_in_ruleDefault4401); 

                	newLeafNode(otherlv_0, grammarAccess.getDefaultAccess().getIFKeyword_0());
                
            otherlv_1=(Token)match(input,102,FOLLOW_102_in_ruleDefault4413); 

                	newLeafNode(otherlv_1, grammarAccess.getDefaultAccess().getLeftParenthesisKeyword_1());
                
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2418:1: ( (lv_mv_2_0= ruleINTEGER_OBJECT ) )?
            int alt105=2;
            int LA105_0 = input.LA(1);

            if ( (LA105_0==RULE_INT) ) {
                alt105=1;
            }
            switch (alt105) {
                case 1 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2419:1: (lv_mv_2_0= ruleINTEGER_OBJECT )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2419:1: (lv_mv_2_0= ruleINTEGER_OBJECT )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2420:3: lv_mv_2_0= ruleINTEGER_OBJECT
                    {
                     
                    	        newCompositeNode(grammarAccess.getDefaultAccess().getMvINTEGER_OBJECTParserRuleCall_2_0()); 
                    	    
                    pushFollow(FOLLOW_ruleINTEGER_OBJECT_in_ruleDefault4434);
                    lv_mv_2_0=ruleINTEGER_OBJECT();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getDefaultRule());
                    	        }
                           		set(
                           			current, 
                           			"mv",
                            		lv_mv_2_0, 
                            		"INTEGER_OBJECT");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }
                    break;

            }

            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2436:3: (otherlv_3= '-' ( (lv_sv_4_0= ruleINTEGER_OBJECT ) ) )?
            int alt106=2;
            int LA106_0 = input.LA(1);

            if ( (LA106_0==103) ) {
                alt106=1;
            }
            switch (alt106) {
                case 1 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2436:5: otherlv_3= '-' ( (lv_sv_4_0= ruleINTEGER_OBJECT ) )
                    {
                    otherlv_3=(Token)match(input,103,FOLLOW_103_in_ruleDefault4448); 

                        	newLeafNode(otherlv_3, grammarAccess.getDefaultAccess().getHyphenMinusKeyword_3_0());
                        
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2440:1: ( (lv_sv_4_0= ruleINTEGER_OBJECT ) )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2441:1: (lv_sv_4_0= ruleINTEGER_OBJECT )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2441:1: (lv_sv_4_0= ruleINTEGER_OBJECT )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2442:3: lv_sv_4_0= ruleINTEGER_OBJECT
                    {
                     
                    	        newCompositeNode(grammarAccess.getDefaultAccess().getSvINTEGER_OBJECTParserRuleCall_3_1_0()); 
                    	    
                    pushFollow(FOLLOW_ruleINTEGER_OBJECT_in_ruleDefault4469);
                    lv_sv_4_0=ruleINTEGER_OBJECT();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getDefaultRule());
                    	        }
                           		set(
                           			current, 
                           			"sv",
                            		lv_sv_4_0, 
                            		"INTEGER_OBJECT");
                    	        afterParserOrEnumRuleCall();
                    	    

                    }


                    }


                    }
                    break;

            }

            otherlv_5=(Token)match(input,104,FOLLOW_104_in_ruleDefault4483); 

                	newLeafNode(otherlv_5, grammarAccess.getDefaultAccess().getRightParenthesisKeyword_4());
                
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2462:1: ( (lv_defaultIfOldValueEquals_6_0= RULE_STRING ) )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2463:1: (lv_defaultIfOldValueEquals_6_0= RULE_STRING )
            {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2463:1: (lv_defaultIfOldValueEquals_6_0= RULE_STRING )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2464:3: lv_defaultIfOldValueEquals_6_0= RULE_STRING
            {
            lv_defaultIfOldValueEquals_6_0=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleDefault4500); 

            			newLeafNode(lv_defaultIfOldValueEquals_6_0, grammarAccess.getDefaultAccess().getDefaultIfOldValueEqualsSTRINGTerminalRuleCall_5_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getDefaultRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"defaultIfOldValueEquals",
                    		lv_defaultIfOldValueEquals_6_0, 
                    		"STRING");
            	    

            }


            }

            otherlv_7=(Token)match(input,105,FOLLOW_105_in_ruleDefault4517); 

                	newLeafNode(otherlv_7, grammarAccess.getDefaultAccess().getTHENKeyword_6());
                
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2484:1: ( (lv_defaultNewValueOrAtRoutine_8_0= ruleValueOrAtRoutine ) )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2485:1: (lv_defaultNewValueOrAtRoutine_8_0= ruleValueOrAtRoutine )
            {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2485:1: (lv_defaultNewValueOrAtRoutine_8_0= ruleValueOrAtRoutine )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2486:3: lv_defaultNewValueOrAtRoutine_8_0= ruleValueOrAtRoutine
            {
             
            	        newCompositeNode(grammarAccess.getDefaultAccess().getDefaultNewValueOrAtRoutineValueOrAtRoutineParserRuleCall_7_0()); 
            	    
            pushFollow(FOLLOW_ruleValueOrAtRoutine_in_ruleDefault4538);
            lv_defaultNewValueOrAtRoutine_8_0=ruleValueOrAtRoutine();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getDefaultRule());
            	        }
                   		set(
                   			current, 
                   			"defaultNewValueOrAtRoutine",
                    		lv_defaultNewValueOrAtRoutine_8_0, 
                    		"ValueOrAtRoutine");
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
    // $ANTLR end "ruleDefault"


    // $ANTLR start "entryRuleRoutine"
    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2510:1: entryRuleRoutine returns [EObject current=null] : iv_ruleRoutine= ruleRoutine EOF ;
    public final EObject entryRuleRoutine() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRoutine = null;


        try {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2511:2: (iv_ruleRoutine= ruleRoutine EOF )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2512:2: iv_ruleRoutine= ruleRoutine EOF
            {
             newCompositeNode(grammarAccess.getRoutineRule()); 
            pushFollow(FOLLOW_ruleRoutine_in_entryRuleRoutine4574);
            iv_ruleRoutine=ruleRoutine();

            state._fsp--;

             current =iv_ruleRoutine; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleRoutine4584); 

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
    // $ANTLR end "entryRuleRoutine"


    // $ANTLR start "ruleRoutine"
    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2519:1: ruleRoutine returns [EObject current=null] : (this_JBCRoutine_0= ruleJBCRoutine | this_JavaRoutine_1= ruleJavaRoutine ) ;
    public final EObject ruleRoutine() throws RecognitionException {
        EObject current = null;

        EObject this_JBCRoutine_0 = null;

        EObject this_JavaRoutine_1 = null;


         enterRule(); 
            
        try {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2522:28: ( (this_JBCRoutine_0= ruleJBCRoutine | this_JavaRoutine_1= ruleJavaRoutine ) )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2523:1: (this_JBCRoutine_0= ruleJBCRoutine | this_JavaRoutine_1= ruleJavaRoutine )
            {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2523:1: (this_JBCRoutine_0= ruleJBCRoutine | this_JavaRoutine_1= ruleJavaRoutine )
            int alt107=2;
            int LA107_0 = input.LA(1);

            if ( (LA107_0==107) ) {
                alt107=1;
            }
            else if ( (LA107_0==108) ) {
                alt107=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 107, 0, input);

                throw nvae;
            }
            switch (alt107) {
                case 1 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2524:5: this_JBCRoutine_0= ruleJBCRoutine
                    {
                     
                            newCompositeNode(grammarAccess.getRoutineAccess().getJBCRoutineParserRuleCall_0()); 
                        
                    pushFollow(FOLLOW_ruleJBCRoutine_in_ruleRoutine4631);
                    this_JBCRoutine_0=ruleJBCRoutine();

                    state._fsp--;

                     
                            current = this_JBCRoutine_0; 
                            afterParserOrEnumRuleCall();
                        

                    }
                    break;
                case 2 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2534:5: this_JavaRoutine_1= ruleJavaRoutine
                    {
                     
                            newCompositeNode(grammarAccess.getRoutineAccess().getJavaRoutineParserRuleCall_1()); 
                        
                    pushFollow(FOLLOW_ruleJavaRoutine_in_ruleRoutine4658);
                    this_JavaRoutine_1=ruleJavaRoutine();

                    state._fsp--;

                     
                            current = this_JavaRoutine_1; 
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
    // $ANTLR end "ruleRoutine"


    // $ANTLR start "entryRuleAtRoutine"
    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2550:1: entryRuleAtRoutine returns [EObject current=null] : iv_ruleAtRoutine= ruleAtRoutine EOF ;
    public final EObject entryRuleAtRoutine() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAtRoutine = null;


        try {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2551:2: (iv_ruleAtRoutine= ruleAtRoutine EOF )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2552:2: iv_ruleAtRoutine= ruleAtRoutine EOF
            {
             newCompositeNode(grammarAccess.getAtRoutineRule()); 
            pushFollow(FOLLOW_ruleAtRoutine_in_entryRuleAtRoutine4693);
            iv_ruleAtRoutine=ruleAtRoutine();

            state._fsp--;

             current =iv_ruleAtRoutine; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleAtRoutine4703); 

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
    // $ANTLR end "entryRuleAtRoutine"


    // $ANTLR start "ruleAtRoutine"
    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2559:1: ruleAtRoutine returns [EObject current=null] : (otherlv_0= '@' this_Routine_1= ruleRoutine ) ;
    public final EObject ruleAtRoutine() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        EObject this_Routine_1 = null;


         enterRule(); 
            
        try {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2562:28: ( (otherlv_0= '@' this_Routine_1= ruleRoutine ) )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2563:1: (otherlv_0= '@' this_Routine_1= ruleRoutine )
            {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2563:1: (otherlv_0= '@' this_Routine_1= ruleRoutine )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2563:3: otherlv_0= '@' this_Routine_1= ruleRoutine
            {
            otherlv_0=(Token)match(input,106,FOLLOW_106_in_ruleAtRoutine4740); 

                	newLeafNode(otherlv_0, grammarAccess.getAtRoutineAccess().getCommercialAtKeyword_0());
                
             
                    newCompositeNode(grammarAccess.getAtRoutineAccess().getRoutineParserRuleCall_1()); 
                
            pushFollow(FOLLOW_ruleRoutine_in_ruleAtRoutine4762);
            this_Routine_1=ruleRoutine();

            state._fsp--;

             
                    current = this_Routine_1; 
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
    // $ANTLR end "ruleAtRoutine"


    // $ANTLR start "entryRuleValueOrAtRoutine"
    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2584:1: entryRuleValueOrAtRoutine returns [EObject current=null] : iv_ruleValueOrAtRoutine= ruleValueOrAtRoutine EOF ;
    public final EObject entryRuleValueOrAtRoutine() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleValueOrAtRoutine = null;


        try {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2585:2: (iv_ruleValueOrAtRoutine= ruleValueOrAtRoutine EOF )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2586:2: iv_ruleValueOrAtRoutine= ruleValueOrAtRoutine EOF
            {
             newCompositeNode(grammarAccess.getValueOrAtRoutineRule()); 
            pushFollow(FOLLOW_ruleValueOrAtRoutine_in_entryRuleValueOrAtRoutine4797);
            iv_ruleValueOrAtRoutine=ruleValueOrAtRoutine();

            state._fsp--;

             current =iv_ruleValueOrAtRoutine; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleValueOrAtRoutine4807); 

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
    // $ANTLR end "entryRuleValueOrAtRoutine"


    // $ANTLR start "ruleValueOrAtRoutine"
    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2593:1: ruleValueOrAtRoutine returns [EObject current=null] : ( ( (lv_value_0_0= RULE_STRING ) ) | ( (lv_atRoutine_1_0= ruleAtRoutine ) ) ) ;
    public final EObject ruleValueOrAtRoutine() throws RecognitionException {
        EObject current = null;

        Token lv_value_0_0=null;
        EObject lv_atRoutine_1_0 = null;


         enterRule(); 
            
        try {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2596:28: ( ( ( (lv_value_0_0= RULE_STRING ) ) | ( (lv_atRoutine_1_0= ruleAtRoutine ) ) ) )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2597:1: ( ( (lv_value_0_0= RULE_STRING ) ) | ( (lv_atRoutine_1_0= ruleAtRoutine ) ) )
            {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2597:1: ( ( (lv_value_0_0= RULE_STRING ) ) | ( (lv_atRoutine_1_0= ruleAtRoutine ) ) )
            int alt108=2;
            int LA108_0 = input.LA(1);

            if ( (LA108_0==RULE_STRING) ) {
                alt108=1;
            }
            else if ( (LA108_0==106) ) {
                alt108=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 108, 0, input);

                throw nvae;
            }
            switch (alt108) {
                case 1 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2597:2: ( (lv_value_0_0= RULE_STRING ) )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2597:2: ( (lv_value_0_0= RULE_STRING ) )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2598:1: (lv_value_0_0= RULE_STRING )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2598:1: (lv_value_0_0= RULE_STRING )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2599:3: lv_value_0_0= RULE_STRING
                    {
                    lv_value_0_0=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleValueOrAtRoutine4849); 

                    			newLeafNode(lv_value_0_0, grammarAccess.getValueOrAtRoutineAccess().getValueSTRINGTerminalRuleCall_0_0()); 
                    		

                    	        if (current==null) {
                    	            current = createModelElement(grammarAccess.getValueOrAtRoutineRule());
                    	        }
                           		setWithLastConsumed(
                           			current, 
                           			"value",
                            		lv_value_0_0, 
                            		"STRING");
                    	    

                    }


                    }


                    }
                    break;
                case 2 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2616:6: ( (lv_atRoutine_1_0= ruleAtRoutine ) )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2616:6: ( (lv_atRoutine_1_0= ruleAtRoutine ) )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2617:1: (lv_atRoutine_1_0= ruleAtRoutine )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2617:1: (lv_atRoutine_1_0= ruleAtRoutine )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2618:3: lv_atRoutine_1_0= ruleAtRoutine
                    {
                     
                    	        newCompositeNode(grammarAccess.getValueOrAtRoutineAccess().getAtRoutineAtRoutineParserRuleCall_1_0()); 
                    	    
                    pushFollow(FOLLOW_ruleAtRoutine_in_ruleValueOrAtRoutine4881);
                    lv_atRoutine_1_0=ruleAtRoutine();

                    state._fsp--;


                    	        if (current==null) {
                    	            current = createModelElementForParent(grammarAccess.getValueOrAtRoutineRule());
                    	        }
                           		set(
                           			current, 
                           			"atRoutine",
                            		lv_atRoutine_1_0, 
                            		"AtRoutine");
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
    // $ANTLR end "ruleValueOrAtRoutine"


    // $ANTLR start "entryRuleJBCRoutine"
    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2642:1: entryRuleJBCRoutine returns [EObject current=null] : iv_ruleJBCRoutine= ruleJBCRoutine EOF ;
    public final EObject entryRuleJBCRoutine() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleJBCRoutine = null;


        try {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2643:2: (iv_ruleJBCRoutine= ruleJBCRoutine EOF )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2644:2: iv_ruleJBCRoutine= ruleJBCRoutine EOF
            {
             newCompositeNode(grammarAccess.getJBCRoutineRule()); 
            pushFollow(FOLLOW_ruleJBCRoutine_in_entryRuleJBCRoutine4917);
            iv_ruleJBCRoutine=ruleJBCRoutine();

            state._fsp--;

             current =iv_ruleJBCRoutine; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleJBCRoutine4927); 

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
    // $ANTLR end "entryRuleJBCRoutine"


    // $ANTLR start "ruleJBCRoutine"
    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2651:1: ruleJBCRoutine returns [EObject current=null] : (otherlv_0= 'jBC:' ( (lv_name_1_0= RULE_STRING ) ) ) ;
    public final EObject ruleJBCRoutine() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;

         enterRule(); 
            
        try {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2654:28: ( (otherlv_0= 'jBC:' ( (lv_name_1_0= RULE_STRING ) ) ) )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2655:1: (otherlv_0= 'jBC:' ( (lv_name_1_0= RULE_STRING ) ) )
            {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2655:1: (otherlv_0= 'jBC:' ( (lv_name_1_0= RULE_STRING ) ) )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2655:3: otherlv_0= 'jBC:' ( (lv_name_1_0= RULE_STRING ) )
            {
            otherlv_0=(Token)match(input,107,FOLLOW_107_in_ruleJBCRoutine4964); 

                	newLeafNode(otherlv_0, grammarAccess.getJBCRoutineAccess().getJBCKeyword_0());
                
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2659:1: ( (lv_name_1_0= RULE_STRING ) )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2660:1: (lv_name_1_0= RULE_STRING )
            {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2660:1: (lv_name_1_0= RULE_STRING )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2661:3: lv_name_1_0= RULE_STRING
            {
            lv_name_1_0=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleJBCRoutine4981); 

            			newLeafNode(lv_name_1_0, grammarAccess.getJBCRoutineAccess().getNameSTRINGTerminalRuleCall_1_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getJBCRoutineRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"name",
                    		lv_name_1_0, 
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
    // $ANTLR end "ruleJBCRoutine"


    // $ANTLR start "entryRuleJavaRoutine"
    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2685:1: entryRuleJavaRoutine returns [EObject current=null] : iv_ruleJavaRoutine= ruleJavaRoutine EOF ;
    public final EObject entryRuleJavaRoutine() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleJavaRoutine = null;


        try {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2686:2: (iv_ruleJavaRoutine= ruleJavaRoutine EOF )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2687:2: iv_ruleJavaRoutine= ruleJavaRoutine EOF
            {
             newCompositeNode(grammarAccess.getJavaRoutineRule()); 
            pushFollow(FOLLOW_ruleJavaRoutine_in_entryRuleJavaRoutine5022);
            iv_ruleJavaRoutine=ruleJavaRoutine();

            state._fsp--;

             current =iv_ruleJavaRoutine; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleJavaRoutine5032); 

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
    // $ANTLR end "entryRuleJavaRoutine"


    // $ANTLR start "ruleJavaRoutine"
    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2694:1: ruleJavaRoutine returns [EObject current=null] : (otherlv_0= 'java:' ( (lv_name_1_0= RULE_STRING ) ) ) ;
    public final EObject ruleJavaRoutine() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;

         enterRule(); 
            
        try {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2697:28: ( (otherlv_0= 'java:' ( (lv_name_1_0= RULE_STRING ) ) ) )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2698:1: (otherlv_0= 'java:' ( (lv_name_1_0= RULE_STRING ) ) )
            {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2698:1: (otherlv_0= 'java:' ( (lv_name_1_0= RULE_STRING ) ) )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2698:3: otherlv_0= 'java:' ( (lv_name_1_0= RULE_STRING ) )
            {
            otherlv_0=(Token)match(input,108,FOLLOW_108_in_ruleJavaRoutine5069); 

                	newLeafNode(otherlv_0, grammarAccess.getJavaRoutineAccess().getJavaKeyword_0());
                
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2702:1: ( (lv_name_1_0= RULE_STRING ) )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2703:1: (lv_name_1_0= RULE_STRING )
            {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2703:1: (lv_name_1_0= RULE_STRING )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2704:3: lv_name_1_0= RULE_STRING
            {
            lv_name_1_0=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleJavaRoutine5086); 

            			newLeafNode(lv_name_1_0, grammarAccess.getJavaRoutineAccess().getNameSTRINGTerminalRuleCall_1_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getJavaRoutineRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"name",
                    		lv_name_1_0, 
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
    // $ANTLR end "ruleJavaRoutine"


    // $ANTLR start "entryRuleDealSlip"
    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2728:1: entryRuleDealSlip returns [EObject current=null] : iv_ruleDealSlip= ruleDealSlip EOF ;
    public final EObject entryRuleDealSlip() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDealSlip = null;


        try {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2729:2: (iv_ruleDealSlip= ruleDealSlip EOF )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2730:2: iv_ruleDealSlip= ruleDealSlip EOF
            {
             newCompositeNode(grammarAccess.getDealSlipRule()); 
            pushFollow(FOLLOW_ruleDealSlip_in_entryRuleDealSlip5127);
            iv_ruleDealSlip=ruleDealSlip();

            state._fsp--;

             current =iv_ruleDealSlip; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleDealSlip5137); 

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
    // $ANTLR end "entryRuleDealSlip"


    // $ANTLR start "ruleDealSlip"
    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2737:1: ruleDealSlip returns [EObject current=null] : (otherlv_0= 'format:' ( (lv_format_1_0= RULE_STRING ) ) otherlv_2= 'dealSlipFunction: ' ( (lv_function_3_0= ruleDealSlipFormatFunction ) ) ) ;
    public final EObject ruleDealSlip() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_format_1_0=null;
        Token otherlv_2=null;
        Enumerator lv_function_3_0 = null;


         enterRule(); 
            
        try {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2740:28: ( (otherlv_0= 'format:' ( (lv_format_1_0= RULE_STRING ) ) otherlv_2= 'dealSlipFunction: ' ( (lv_function_3_0= ruleDealSlipFormatFunction ) ) ) )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2741:1: (otherlv_0= 'format:' ( (lv_format_1_0= RULE_STRING ) ) otherlv_2= 'dealSlipFunction: ' ( (lv_function_3_0= ruleDealSlipFormatFunction ) ) )
            {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2741:1: (otherlv_0= 'format:' ( (lv_format_1_0= RULE_STRING ) ) otherlv_2= 'dealSlipFunction: ' ( (lv_function_3_0= ruleDealSlipFormatFunction ) ) )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2741:3: otherlv_0= 'format:' ( (lv_format_1_0= RULE_STRING ) ) otherlv_2= 'dealSlipFunction: ' ( (lv_function_3_0= ruleDealSlipFormatFunction ) )
            {
            otherlv_0=(Token)match(input,109,FOLLOW_109_in_ruleDealSlip5174); 

                	newLeafNode(otherlv_0, grammarAccess.getDealSlipAccess().getFormatKeyword_0());
                
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2745:1: ( (lv_format_1_0= RULE_STRING ) )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2746:1: (lv_format_1_0= RULE_STRING )
            {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2746:1: (lv_format_1_0= RULE_STRING )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2747:3: lv_format_1_0= RULE_STRING
            {
            lv_format_1_0=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleDealSlip5191); 

            			newLeafNode(lv_format_1_0, grammarAccess.getDealSlipAccess().getFormatSTRINGTerminalRuleCall_1_0()); 
            		

            	        if (current==null) {
            	            current = createModelElement(grammarAccess.getDealSlipRule());
            	        }
                   		setWithLastConsumed(
                   			current, 
                   			"format",
                    		lv_format_1_0, 
                    		"STRING");
            	    

            }


            }

            otherlv_2=(Token)match(input,110,FOLLOW_110_in_ruleDealSlip5208); 

                	newLeafNode(otherlv_2, grammarAccess.getDealSlipAccess().getDealSlipFunctionKeyword_2());
                
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2767:1: ( (lv_function_3_0= ruleDealSlipFormatFunction ) )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2768:1: (lv_function_3_0= ruleDealSlipFormatFunction )
            {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2768:1: (lv_function_3_0= ruleDealSlipFormatFunction )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2769:3: lv_function_3_0= ruleDealSlipFormatFunction
            {
             
            	        newCompositeNode(grammarAccess.getDealSlipAccess().getFunctionDealSlipFormatFunctionEnumRuleCall_3_0()); 
            	    
            pushFollow(FOLLOW_ruleDealSlipFormatFunction_in_ruleDealSlip5229);
            lv_function_3_0=ruleDealSlipFormatFunction();

            state._fsp--;


            	        if (current==null) {
            	            current = createModelElementForParent(grammarAccess.getDealSlipRule());
            	        }
                   		set(
                   			current, 
                   			"function",
                    		lv_function_3_0, 
                    		"DealSlipFormatFunction");
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
    // $ANTLR end "ruleDealSlip"


    // $ANTLR start "entryRuleNID"
    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2793:1: entryRuleNID returns [String current=null] : iv_ruleNID= ruleNID EOF ;
    public final String entryRuleNID() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleNID = null;


        try {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2794:2: (iv_ruleNID= ruleNID EOF )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2795:2: iv_ruleNID= ruleNID EOF
            {
             newCompositeNode(grammarAccess.getNIDRule()); 
            pushFollow(FOLLOW_ruleNID_in_entryRuleNID5266);
            iv_ruleNID=ruleNID();

            state._fsp--;

             current =iv_ruleNID.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleNID5277); 

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
    // $ANTLR end "entryRuleNID"


    // $ANTLR start "ruleNID"
    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2802:1: ruleNID returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID | this_INT_1= RULE_INT | kw= 'NULL' | kw= 'Yes' | kw= 'No' | kw= 'None' | kw= 'Calendar' | kw= 'Calculator' | kw= 'RATE CONTROL' | kw= 'Recurrence' | kw= 'Lowercase' | kw= 'Uppercase' | kw= 'Proper_Case' | kw= 'NoDisplay' | kw= 'Combobox' | kw= 'Vertical.Options' | kw= 'Toggle' | kw= 'DropDown.NoInput' | kw= 'I' | kw= 'A' | kw= 'C' | kw= 'R' | kw= 'D' | kw= 'H' | kw= 'Finish' | kw= 'OL' | kw= 'RQ' | kw= 'Normal' | kw= 'Restricted' | kw= 'Closed' | kw= 'Input' | kw= 'Authorise' | kw= 'Reverse' | kw= 'See' | kw= 'Copy' | kw= 'Delete' | kw= 'HistoryRestore' | kw= 'Verify' | kw= 'AuditorReview' | kw= 'NoChange' | kw= 'NoInput' | kw= 'Tabs' | kw= 'Vertical' | kw= 'Accordions' | kw= 'OneRow' | kw= 'OneColumn' | kw= 'TwoColumnHorizontal' | kw= 'TwoColumnVertical' | kw= 'ThreeColumnHorizontal' | kw= 'ThreeColumnVertical' )+ ;
    public final AntlrDatatypeRuleToken ruleNID() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        Token this_INT_1=null;
        Token kw=null;

         enterRule(); 
            
        try {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2805:28: ( (this_ID_0= RULE_ID | this_INT_1= RULE_INT | kw= 'NULL' | kw= 'Yes' | kw= 'No' | kw= 'None' | kw= 'Calendar' | kw= 'Calculator' | kw= 'RATE CONTROL' | kw= 'Recurrence' | kw= 'Lowercase' | kw= 'Uppercase' | kw= 'Proper_Case' | kw= 'NoDisplay' | kw= 'Combobox' | kw= 'Vertical.Options' | kw= 'Toggle' | kw= 'DropDown.NoInput' | kw= 'I' | kw= 'A' | kw= 'C' | kw= 'R' | kw= 'D' | kw= 'H' | kw= 'Finish' | kw= 'OL' | kw= 'RQ' | kw= 'Normal' | kw= 'Restricted' | kw= 'Closed' | kw= 'Input' | kw= 'Authorise' | kw= 'Reverse' | kw= 'See' | kw= 'Copy' | kw= 'Delete' | kw= 'HistoryRestore' | kw= 'Verify' | kw= 'AuditorReview' | kw= 'NoChange' | kw= 'NoInput' | kw= 'Tabs' | kw= 'Vertical' | kw= 'Accordions' | kw= 'OneRow' | kw= 'OneColumn' | kw= 'TwoColumnHorizontal' | kw= 'TwoColumnVertical' | kw= 'ThreeColumnHorizontal' | kw= 'ThreeColumnVertical' )+ )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2806:1: (this_ID_0= RULE_ID | this_INT_1= RULE_INT | kw= 'NULL' | kw= 'Yes' | kw= 'No' | kw= 'None' | kw= 'Calendar' | kw= 'Calculator' | kw= 'RATE CONTROL' | kw= 'Recurrence' | kw= 'Lowercase' | kw= 'Uppercase' | kw= 'Proper_Case' | kw= 'NoDisplay' | kw= 'Combobox' | kw= 'Vertical.Options' | kw= 'Toggle' | kw= 'DropDown.NoInput' | kw= 'I' | kw= 'A' | kw= 'C' | kw= 'R' | kw= 'D' | kw= 'H' | kw= 'Finish' | kw= 'OL' | kw= 'RQ' | kw= 'Normal' | kw= 'Restricted' | kw= 'Closed' | kw= 'Input' | kw= 'Authorise' | kw= 'Reverse' | kw= 'See' | kw= 'Copy' | kw= 'Delete' | kw= 'HistoryRestore' | kw= 'Verify' | kw= 'AuditorReview' | kw= 'NoChange' | kw= 'NoInput' | kw= 'Tabs' | kw= 'Vertical' | kw= 'Accordions' | kw= 'OneRow' | kw= 'OneColumn' | kw= 'TwoColumnHorizontal' | kw= 'TwoColumnVertical' | kw= 'ThreeColumnHorizontal' | kw= 'ThreeColumnVertical' )+
            {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2806:1: (this_ID_0= RULE_ID | this_INT_1= RULE_INT | kw= 'NULL' | kw= 'Yes' | kw= 'No' | kw= 'None' | kw= 'Calendar' | kw= 'Calculator' | kw= 'RATE CONTROL' | kw= 'Recurrence' | kw= 'Lowercase' | kw= 'Uppercase' | kw= 'Proper_Case' | kw= 'NoDisplay' | kw= 'Combobox' | kw= 'Vertical.Options' | kw= 'Toggle' | kw= 'DropDown.NoInput' | kw= 'I' | kw= 'A' | kw= 'C' | kw= 'R' | kw= 'D' | kw= 'H' | kw= 'Finish' | kw= 'OL' | kw= 'RQ' | kw= 'Normal' | kw= 'Restricted' | kw= 'Closed' | kw= 'Input' | kw= 'Authorise' | kw= 'Reverse' | kw= 'See' | kw= 'Copy' | kw= 'Delete' | kw= 'HistoryRestore' | kw= 'Verify' | kw= 'AuditorReview' | kw= 'NoChange' | kw= 'NoInput' | kw= 'Tabs' | kw= 'Vertical' | kw= 'Accordions' | kw= 'OneRow' | kw= 'OneColumn' | kw= 'TwoColumnHorizontal' | kw= 'TwoColumnVertical' | kw= 'ThreeColumnHorizontal' | kw= 'ThreeColumnVertical' )+
            int cnt109=0;
            loop109:
            do {
                int alt109=51;
                switch ( input.LA(1) ) {
                case RULE_ID:
                    {
                    alt109=1;
                    }
                    break;
                case RULE_INT:
                    {
                    alt109=2;
                    }
                    break;
                case 111:
                    {
                    alt109=3;
                    }
                    break;
                case 112:
                    {
                    alt109=4;
                    }
                    break;
                case 113:
                    {
                    alt109=5;
                    }
                    break;
                case 114:
                    {
                    alt109=6;
                    }
                    break;
                case 115:
                    {
                    alt109=7;
                    }
                    break;
                case 116:
                    {
                    alt109=8;
                    }
                    break;
                case 117:
                    {
                    alt109=9;
                    }
                    break;
                case 118:
                    {
                    alt109=10;
                    }
                    break;
                case 119:
                    {
                    alt109=11;
                    }
                    break;
                case 120:
                    {
                    alt109=12;
                    }
                    break;
                case 121:
                    {
                    alt109=13;
                    }
                    break;
                case 122:
                    {
                    alt109=14;
                    }
                    break;
                case 123:
                    {
                    alt109=15;
                    }
                    break;
                case 124:
                    {
                    alt109=16;
                    }
                    break;
                case 125:
                    {
                    alt109=17;
                    }
                    break;
                case 126:
                    {
                    alt109=18;
                    }
                    break;
                case 127:
                    {
                    alt109=19;
                    }
                    break;
                case 128:
                    {
                    alt109=20;
                    }
                    break;
                case 129:
                    {
                    alt109=21;
                    }
                    break;
                case 130:
                    {
                    alt109=22;
                    }
                    break;
                case 131:
                    {
                    alt109=23;
                    }
                    break;
                case 132:
                    {
                    alt109=24;
                    }
                    break;
                case 133:
                    {
                    alt109=25;
                    }
                    break;
                case 134:
                    {
                    alt109=26;
                    }
                    break;
                case 135:
                    {
                    alt109=27;
                    }
                    break;
                case 136:
                    {
                    alt109=28;
                    }
                    break;
                case 137:
                    {
                    alt109=29;
                    }
                    break;
                case 138:
                    {
                    alt109=30;
                    }
                    break;
                case 139:
                    {
                    alt109=31;
                    }
                    break;
                case 140:
                    {
                    alt109=32;
                    }
                    break;
                case 141:
                    {
                    alt109=33;
                    }
                    break;
                case 142:
                    {
                    alt109=34;
                    }
                    break;
                case 143:
                    {
                    alt109=35;
                    }
                    break;
                case 144:
                    {
                    alt109=36;
                    }
                    break;
                case 145:
                    {
                    alt109=37;
                    }
                    break;
                case 146:
                    {
                    alt109=38;
                    }
                    break;
                case 147:
                    {
                    alt109=39;
                    }
                    break;
                case 148:
                    {
                    alt109=40;
                    }
                    break;
                case 149:
                    {
                    alt109=41;
                    }
                    break;
                case 150:
                    {
                    alt109=42;
                    }
                    break;
                case 151:
                    {
                    alt109=43;
                    }
                    break;
                case 152:
                    {
                    alt109=44;
                    }
                    break;
                case 153:
                    {
                    alt109=45;
                    }
                    break;
                case 154:
                    {
                    alt109=46;
                    }
                    break;
                case 155:
                    {
                    alt109=47;
                    }
                    break;
                case 156:
                    {
                    alt109=48;
                    }
                    break;
                case 157:
                    {
                    alt109=49;
                    }
                    break;
                case 158:
                    {
                    alt109=50;
                    }
                    break;

                }

                switch (alt109) {
            	case 1 :
            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2806:6: this_ID_0= RULE_ID
            	    {
            	    this_ID_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleNID5317); 

            	    		current.merge(this_ID_0);
            	        
            	     
            	        newLeafNode(this_ID_0, grammarAccess.getNIDAccess().getIDTerminalRuleCall_0()); 
            	        

            	    }
            	    break;
            	case 2 :
            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2814:10: this_INT_1= RULE_INT
            	    {
            	    this_INT_1=(Token)match(input,RULE_INT,FOLLOW_RULE_INT_in_ruleNID5343); 

            	    		current.merge(this_INT_1);
            	        
            	     
            	        newLeafNode(this_INT_1, grammarAccess.getNIDAccess().getINTTerminalRuleCall_1()); 
            	        

            	    }
            	    break;
            	case 3 :
            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2823:2: kw= 'NULL'
            	    {
            	    kw=(Token)match(input,111,FOLLOW_111_in_ruleNID5367); 

            	            current.merge(kw);
            	            newLeafNode(kw, grammarAccess.getNIDAccess().getNULLKeyword_2()); 
            	        

            	    }
            	    break;
            	case 4 :
            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2830:2: kw= 'Yes'
            	    {
            	    kw=(Token)match(input,112,FOLLOW_112_in_ruleNID5386); 

            	            current.merge(kw);
            	            newLeafNode(kw, grammarAccess.getNIDAccess().getYesKeyword_3()); 
            	        

            	    }
            	    break;
            	case 5 :
            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2837:2: kw= 'No'
            	    {
            	    kw=(Token)match(input,113,FOLLOW_113_in_ruleNID5405); 

            	            current.merge(kw);
            	            newLeafNode(kw, grammarAccess.getNIDAccess().getNoKeyword_4()); 
            	        

            	    }
            	    break;
            	case 6 :
            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2844:2: kw= 'None'
            	    {
            	    kw=(Token)match(input,114,FOLLOW_114_in_ruleNID5424); 

            	            current.merge(kw);
            	            newLeafNode(kw, grammarAccess.getNIDAccess().getNoneKeyword_5()); 
            	        

            	    }
            	    break;
            	case 7 :
            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2851:2: kw= 'Calendar'
            	    {
            	    kw=(Token)match(input,115,FOLLOW_115_in_ruleNID5443); 

            	            current.merge(kw);
            	            newLeafNode(kw, grammarAccess.getNIDAccess().getCalendarKeyword_6()); 
            	        

            	    }
            	    break;
            	case 8 :
            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2858:2: kw= 'Calculator'
            	    {
            	    kw=(Token)match(input,116,FOLLOW_116_in_ruleNID5462); 

            	            current.merge(kw);
            	            newLeafNode(kw, grammarAccess.getNIDAccess().getCalculatorKeyword_7()); 
            	        

            	    }
            	    break;
            	case 9 :
            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2865:2: kw= 'RATE CONTROL'
            	    {
            	    kw=(Token)match(input,117,FOLLOW_117_in_ruleNID5481); 

            	            current.merge(kw);
            	            newLeafNode(kw, grammarAccess.getNIDAccess().getRATECONTROLKeyword_8()); 
            	        

            	    }
            	    break;
            	case 10 :
            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2872:2: kw= 'Recurrence'
            	    {
            	    kw=(Token)match(input,118,FOLLOW_118_in_ruleNID5500); 

            	            current.merge(kw);
            	            newLeafNode(kw, grammarAccess.getNIDAccess().getRecurrenceKeyword_9()); 
            	        

            	    }
            	    break;
            	case 11 :
            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2879:2: kw= 'Lowercase'
            	    {
            	    kw=(Token)match(input,119,FOLLOW_119_in_ruleNID5519); 

            	            current.merge(kw);
            	            newLeafNode(kw, grammarAccess.getNIDAccess().getLowercaseKeyword_10()); 
            	        

            	    }
            	    break;
            	case 12 :
            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2886:2: kw= 'Uppercase'
            	    {
            	    kw=(Token)match(input,120,FOLLOW_120_in_ruleNID5538); 

            	            current.merge(kw);
            	            newLeafNode(kw, grammarAccess.getNIDAccess().getUppercaseKeyword_11()); 
            	        

            	    }
            	    break;
            	case 13 :
            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2893:2: kw= 'Proper_Case'
            	    {
            	    kw=(Token)match(input,121,FOLLOW_121_in_ruleNID5557); 

            	            current.merge(kw);
            	            newLeafNode(kw, grammarAccess.getNIDAccess().getProper_CaseKeyword_12()); 
            	        

            	    }
            	    break;
            	case 14 :
            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2900:2: kw= 'NoDisplay'
            	    {
            	    kw=(Token)match(input,122,FOLLOW_122_in_ruleNID5576); 

            	            current.merge(kw);
            	            newLeafNode(kw, grammarAccess.getNIDAccess().getNoDisplayKeyword_13()); 
            	        

            	    }
            	    break;
            	case 15 :
            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2907:2: kw= 'Combobox'
            	    {
            	    kw=(Token)match(input,123,FOLLOW_123_in_ruleNID5595); 

            	            current.merge(kw);
            	            newLeafNode(kw, grammarAccess.getNIDAccess().getComboboxKeyword_14()); 
            	        

            	    }
            	    break;
            	case 16 :
            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2914:2: kw= 'Vertical.Options'
            	    {
            	    kw=(Token)match(input,124,FOLLOW_124_in_ruleNID5614); 

            	            current.merge(kw);
            	            newLeafNode(kw, grammarAccess.getNIDAccess().getVerticalOptionsKeyword_15()); 
            	        

            	    }
            	    break;
            	case 17 :
            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2921:2: kw= 'Toggle'
            	    {
            	    kw=(Token)match(input,125,FOLLOW_125_in_ruleNID5633); 

            	            current.merge(kw);
            	            newLeafNode(kw, grammarAccess.getNIDAccess().getToggleKeyword_16()); 
            	        

            	    }
            	    break;
            	case 18 :
            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2928:2: kw= 'DropDown.NoInput'
            	    {
            	    kw=(Token)match(input,126,FOLLOW_126_in_ruleNID5652); 

            	            current.merge(kw);
            	            newLeafNode(kw, grammarAccess.getNIDAccess().getDropDownNoInputKeyword_17()); 
            	        

            	    }
            	    break;
            	case 19 :
            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2935:2: kw= 'I'
            	    {
            	    kw=(Token)match(input,127,FOLLOW_127_in_ruleNID5671); 

            	            current.merge(kw);
            	            newLeafNode(kw, grammarAccess.getNIDAccess().getIKeyword_18()); 
            	        

            	    }
            	    break;
            	case 20 :
            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2942:2: kw= 'A'
            	    {
            	    kw=(Token)match(input,128,FOLLOW_128_in_ruleNID5690); 

            	            current.merge(kw);
            	            newLeafNode(kw, grammarAccess.getNIDAccess().getAKeyword_19()); 
            	        

            	    }
            	    break;
            	case 21 :
            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2949:2: kw= 'C'
            	    {
            	    kw=(Token)match(input,129,FOLLOW_129_in_ruleNID5709); 

            	            current.merge(kw);
            	            newLeafNode(kw, grammarAccess.getNIDAccess().getCKeyword_20()); 
            	        

            	    }
            	    break;
            	case 22 :
            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2956:2: kw= 'R'
            	    {
            	    kw=(Token)match(input,130,FOLLOW_130_in_ruleNID5728); 

            	            current.merge(kw);
            	            newLeafNode(kw, grammarAccess.getNIDAccess().getRKeyword_21()); 
            	        

            	    }
            	    break;
            	case 23 :
            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2963:2: kw= 'D'
            	    {
            	    kw=(Token)match(input,131,FOLLOW_131_in_ruleNID5747); 

            	            current.merge(kw);
            	            newLeafNode(kw, grammarAccess.getNIDAccess().getDKeyword_22()); 
            	        

            	    }
            	    break;
            	case 24 :
            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2970:2: kw= 'H'
            	    {
            	    kw=(Token)match(input,132,FOLLOW_132_in_ruleNID5766); 

            	            current.merge(kw);
            	            newLeafNode(kw, grammarAccess.getNIDAccess().getHKeyword_23()); 
            	        

            	    }
            	    break;
            	case 25 :
            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2977:2: kw= 'Finish'
            	    {
            	    kw=(Token)match(input,133,FOLLOW_133_in_ruleNID5785); 

            	            current.merge(kw);
            	            newLeafNode(kw, grammarAccess.getNIDAccess().getFinishKeyword_24()); 
            	        

            	    }
            	    break;
            	case 26 :
            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2984:2: kw= 'OL'
            	    {
            	    kw=(Token)match(input,134,FOLLOW_134_in_ruleNID5804); 

            	            current.merge(kw);
            	            newLeafNode(kw, grammarAccess.getNIDAccess().getOLKeyword_25()); 
            	        

            	    }
            	    break;
            	case 27 :
            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2991:2: kw= 'RQ'
            	    {
            	    kw=(Token)match(input,135,FOLLOW_135_in_ruleNID5823); 

            	            current.merge(kw);
            	            newLeafNode(kw, grammarAccess.getNIDAccess().getRQKeyword_26()); 
            	        

            	    }
            	    break;
            	case 28 :
            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:2998:2: kw= 'Normal'
            	    {
            	    kw=(Token)match(input,136,FOLLOW_136_in_ruleNID5842); 

            	            current.merge(kw);
            	            newLeafNode(kw, grammarAccess.getNIDAccess().getNormalKeyword_27()); 
            	        

            	    }
            	    break;
            	case 29 :
            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3005:2: kw= 'Restricted'
            	    {
            	    kw=(Token)match(input,137,FOLLOW_137_in_ruleNID5861); 

            	            current.merge(kw);
            	            newLeafNode(kw, grammarAccess.getNIDAccess().getRestrictedKeyword_28()); 
            	        

            	    }
            	    break;
            	case 30 :
            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3012:2: kw= 'Closed'
            	    {
            	    kw=(Token)match(input,138,FOLLOW_138_in_ruleNID5880); 

            	            current.merge(kw);
            	            newLeafNode(kw, grammarAccess.getNIDAccess().getClosedKeyword_29()); 
            	        

            	    }
            	    break;
            	case 31 :
            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3019:2: kw= 'Input'
            	    {
            	    kw=(Token)match(input,139,FOLLOW_139_in_ruleNID5899); 

            	            current.merge(kw);
            	            newLeafNode(kw, grammarAccess.getNIDAccess().getInputKeyword_30()); 
            	        

            	    }
            	    break;
            	case 32 :
            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3026:2: kw= 'Authorise'
            	    {
            	    kw=(Token)match(input,140,FOLLOW_140_in_ruleNID5918); 

            	            current.merge(kw);
            	            newLeafNode(kw, grammarAccess.getNIDAccess().getAuthoriseKeyword_31()); 
            	        

            	    }
            	    break;
            	case 33 :
            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3033:2: kw= 'Reverse'
            	    {
            	    kw=(Token)match(input,141,FOLLOW_141_in_ruleNID5937); 

            	            current.merge(kw);
            	            newLeafNode(kw, grammarAccess.getNIDAccess().getReverseKeyword_32()); 
            	        

            	    }
            	    break;
            	case 34 :
            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3040:2: kw= 'See'
            	    {
            	    kw=(Token)match(input,142,FOLLOW_142_in_ruleNID5956); 

            	            current.merge(kw);
            	            newLeafNode(kw, grammarAccess.getNIDAccess().getSeeKeyword_33()); 
            	        

            	    }
            	    break;
            	case 35 :
            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3047:2: kw= 'Copy'
            	    {
            	    kw=(Token)match(input,143,FOLLOW_143_in_ruleNID5975); 

            	            current.merge(kw);
            	            newLeafNode(kw, grammarAccess.getNIDAccess().getCopyKeyword_34()); 
            	        

            	    }
            	    break;
            	case 36 :
            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3054:2: kw= 'Delete'
            	    {
            	    kw=(Token)match(input,144,FOLLOW_144_in_ruleNID5994); 

            	            current.merge(kw);
            	            newLeafNode(kw, grammarAccess.getNIDAccess().getDeleteKeyword_35()); 
            	        

            	    }
            	    break;
            	case 37 :
            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3061:2: kw= 'HistoryRestore'
            	    {
            	    kw=(Token)match(input,145,FOLLOW_145_in_ruleNID6013); 

            	            current.merge(kw);
            	            newLeafNode(kw, grammarAccess.getNIDAccess().getHistoryRestoreKeyword_36()); 
            	        

            	    }
            	    break;
            	case 38 :
            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3068:2: kw= 'Verify'
            	    {
            	    kw=(Token)match(input,146,FOLLOW_146_in_ruleNID6032); 

            	            current.merge(kw);
            	            newLeafNode(kw, grammarAccess.getNIDAccess().getVerifyKeyword_37()); 
            	        

            	    }
            	    break;
            	case 39 :
            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3075:2: kw= 'AuditorReview'
            	    {
            	    kw=(Token)match(input,147,FOLLOW_147_in_ruleNID6051); 

            	            current.merge(kw);
            	            newLeafNode(kw, grammarAccess.getNIDAccess().getAuditorReviewKeyword_38()); 
            	        

            	    }
            	    break;
            	case 40 :
            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3082:2: kw= 'NoChange'
            	    {
            	    kw=(Token)match(input,148,FOLLOW_148_in_ruleNID6070); 

            	            current.merge(kw);
            	            newLeafNode(kw, grammarAccess.getNIDAccess().getNoChangeKeyword_39()); 
            	        

            	    }
            	    break;
            	case 41 :
            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3089:2: kw= 'NoInput'
            	    {
            	    kw=(Token)match(input,149,FOLLOW_149_in_ruleNID6089); 

            	            current.merge(kw);
            	            newLeafNode(kw, grammarAccess.getNIDAccess().getNoInputKeyword_40()); 
            	        

            	    }
            	    break;
            	case 42 :
            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3096:2: kw= 'Tabs'
            	    {
            	    kw=(Token)match(input,150,FOLLOW_150_in_ruleNID6108); 

            	            current.merge(kw);
            	            newLeafNode(kw, grammarAccess.getNIDAccess().getTabsKeyword_41()); 
            	        

            	    }
            	    break;
            	case 43 :
            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3103:2: kw= 'Vertical'
            	    {
            	    kw=(Token)match(input,151,FOLLOW_151_in_ruleNID6127); 

            	            current.merge(kw);
            	            newLeafNode(kw, grammarAccess.getNIDAccess().getVerticalKeyword_42()); 
            	        

            	    }
            	    break;
            	case 44 :
            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3110:2: kw= 'Accordions'
            	    {
            	    kw=(Token)match(input,152,FOLLOW_152_in_ruleNID6146); 

            	            current.merge(kw);
            	            newLeafNode(kw, grammarAccess.getNIDAccess().getAccordionsKeyword_43()); 
            	        

            	    }
            	    break;
            	case 45 :
            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3117:2: kw= 'OneRow'
            	    {
            	    kw=(Token)match(input,153,FOLLOW_153_in_ruleNID6165); 

            	            current.merge(kw);
            	            newLeafNode(kw, grammarAccess.getNIDAccess().getOneRowKeyword_44()); 
            	        

            	    }
            	    break;
            	case 46 :
            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3124:2: kw= 'OneColumn'
            	    {
            	    kw=(Token)match(input,154,FOLLOW_154_in_ruleNID6184); 

            	            current.merge(kw);
            	            newLeafNode(kw, grammarAccess.getNIDAccess().getOneColumnKeyword_45()); 
            	        

            	    }
            	    break;
            	case 47 :
            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3131:2: kw= 'TwoColumnHorizontal'
            	    {
            	    kw=(Token)match(input,155,FOLLOW_155_in_ruleNID6203); 

            	            current.merge(kw);
            	            newLeafNode(kw, grammarAccess.getNIDAccess().getTwoColumnHorizontalKeyword_46()); 
            	        

            	    }
            	    break;
            	case 48 :
            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3138:2: kw= 'TwoColumnVertical'
            	    {
            	    kw=(Token)match(input,156,FOLLOW_156_in_ruleNID6222); 

            	            current.merge(kw);
            	            newLeafNode(kw, grammarAccess.getNIDAccess().getTwoColumnVerticalKeyword_47()); 
            	        

            	    }
            	    break;
            	case 49 :
            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3145:2: kw= 'ThreeColumnHorizontal'
            	    {
            	    kw=(Token)match(input,157,FOLLOW_157_in_ruleNID6241); 

            	            current.merge(kw);
            	            newLeafNode(kw, grammarAccess.getNIDAccess().getThreeColumnHorizontalKeyword_48()); 
            	        

            	    }
            	    break;
            	case 50 :
            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3152:2: kw= 'ThreeColumnVertical'
            	    {
            	    kw=(Token)match(input,158,FOLLOW_158_in_ruleNID6260); 

            	            current.merge(kw);
            	            newLeafNode(kw, grammarAccess.getNIDAccess().getThreeColumnVerticalKeyword_49()); 
            	        

            	    }
            	    break;

            	default :
            	    if ( cnt109 >= 1 ) break loop109;
                        EarlyExitException eee =
                            new EarlyExitException(109, input);
                        throw eee;
                }
                cnt109++;
            } while (true);


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
    // $ANTLR end "ruleNID"


    // $ANTLR start "entryRuleINTEGER_OBJECT"
    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3165:1: entryRuleINTEGER_OBJECT returns [String current=null] : iv_ruleINTEGER_OBJECT= ruleINTEGER_OBJECT EOF ;
    public final String entryRuleINTEGER_OBJECT() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleINTEGER_OBJECT = null;


        try {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3166:2: (iv_ruleINTEGER_OBJECT= ruleINTEGER_OBJECT EOF )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3167:2: iv_ruleINTEGER_OBJECT= ruleINTEGER_OBJECT EOF
            {
             newCompositeNode(grammarAccess.getINTEGER_OBJECTRule()); 
            pushFollow(FOLLOW_ruleINTEGER_OBJECT_in_entryRuleINTEGER_OBJECT6302);
            iv_ruleINTEGER_OBJECT=ruleINTEGER_OBJECT();

            state._fsp--;

             current =iv_ruleINTEGER_OBJECT.getText(); 
            match(input,EOF,FOLLOW_EOF_in_entryRuleINTEGER_OBJECT6313); 

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
    // $ANTLR end "entryRuleINTEGER_OBJECT"


    // $ANTLR start "ruleINTEGER_OBJECT"
    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3174:1: ruleINTEGER_OBJECT returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_INT_0= RULE_INT ;
    public final AntlrDatatypeRuleToken ruleINTEGER_OBJECT() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_INT_0=null;

         enterRule(); 
            
        try {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3177:28: (this_INT_0= RULE_INT )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3178:5: this_INT_0= RULE_INT
            {
            this_INT_0=(Token)match(input,RULE_INT,FOLLOW_RULE_INT_in_ruleINTEGER_OBJECT6352); 

            		current.merge(this_INT_0);
                
             
                newLeafNode(this_INT_0, grammarAccess.getINTEGER_OBJECTAccess().getINTTerminalRuleCall()); 
                

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
    // $ANTLR end "ruleINTEGER_OBJECT"


    // $ANTLR start "entryRuleLocalTranslations"
    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3193:1: entryRuleLocalTranslations returns [EObject current=null] : iv_ruleLocalTranslations= ruleLocalTranslations EOF ;
    public final EObject entryRuleLocalTranslations() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLocalTranslations = null;


        try {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3194:2: (iv_ruleLocalTranslations= ruleLocalTranslations EOF )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3195:2: iv_ruleLocalTranslations= ruleLocalTranslations EOF
            {
             newCompositeNode(grammarAccess.getLocalTranslationsRule()); 
            pushFollow(FOLLOW_ruleLocalTranslations_in_entryRuleLocalTranslations6396);
            iv_ruleLocalTranslations=ruleLocalTranslations();

            state._fsp--;

             current =iv_ruleLocalTranslations; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLocalTranslations6406); 

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
    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3202:1: ruleLocalTranslations returns [EObject current=null] : ( ( (lv_translations_0_0= ruleLocalTranslation ) ) (otherlv_1= ',' ( (lv_translations_2_0= ruleLocalTranslation ) ) )* ) ;
    public final EObject ruleLocalTranslations() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_translations_0_0 = null;

        EObject lv_translations_2_0 = null;


         enterRule(); 
            
        try {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3205:28: ( ( ( (lv_translations_0_0= ruleLocalTranslation ) ) (otherlv_1= ',' ( (lv_translations_2_0= ruleLocalTranslation ) ) )* ) )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3206:1: ( ( (lv_translations_0_0= ruleLocalTranslation ) ) (otherlv_1= ',' ( (lv_translations_2_0= ruleLocalTranslation ) ) )* )
            {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3206:1: ( ( (lv_translations_0_0= ruleLocalTranslation ) ) (otherlv_1= ',' ( (lv_translations_2_0= ruleLocalTranslation ) ) )* )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3206:2: ( (lv_translations_0_0= ruleLocalTranslation ) ) (otherlv_1= ',' ( (lv_translations_2_0= ruleLocalTranslation ) ) )*
            {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3206:2: ( (lv_translations_0_0= ruleLocalTranslation ) )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3207:1: (lv_translations_0_0= ruleLocalTranslation )
            {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3207:1: (lv_translations_0_0= ruleLocalTranslation )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3208:3: lv_translations_0_0= ruleLocalTranslation
            {
             
            	        newCompositeNode(grammarAccess.getLocalTranslationsAccess().getTranslationsLocalTranslationParserRuleCall_0_0()); 
            	    
            pushFollow(FOLLOW_ruleLocalTranslation_in_ruleLocalTranslations6452);
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

            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3224:2: (otherlv_1= ',' ( (lv_translations_2_0= ruleLocalTranslation ) ) )*
            loop110:
            do {
                int alt110=2;
                int LA110_0 = input.LA(1);

                if ( (LA110_0==12) ) {
                    alt110=1;
                }


                switch (alt110) {
            	case 1 :
            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3224:4: otherlv_1= ',' ( (lv_translations_2_0= ruleLocalTranslation ) )
            	    {
            	    otherlv_1=(Token)match(input,12,FOLLOW_12_in_ruleLocalTranslations6465); 

            	        	newLeafNode(otherlv_1, grammarAccess.getLocalTranslationsAccess().getCommaKeyword_1_0());
            	        
            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3228:1: ( (lv_translations_2_0= ruleLocalTranslation ) )
            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3229:1: (lv_translations_2_0= ruleLocalTranslation )
            	    {
            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3229:1: (lv_translations_2_0= ruleLocalTranslation )
            	    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3230:3: lv_translations_2_0= ruleLocalTranslation
            	    {
            	     
            	    	        newCompositeNode(grammarAccess.getLocalTranslationsAccess().getTranslationsLocalTranslationParserRuleCall_1_1_0()); 
            	    	    
            	    pushFollow(FOLLOW_ruleLocalTranslation_in_ruleLocalTranslations6486);
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
            	    break loop110;
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
    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3254:1: entryRuleLocalTranslation returns [EObject current=null] : iv_ruleLocalTranslation= ruleLocalTranslation EOF ;
    public final EObject entryRuleLocalTranslation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLocalTranslation = null;


        try {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3255:2: (iv_ruleLocalTranslation= ruleLocalTranslation EOF )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3256:2: iv_ruleLocalTranslation= ruleLocalTranslation EOF
            {
             newCompositeNode(grammarAccess.getLocalTranslationRule()); 
            pushFollow(FOLLOW_ruleLocalTranslation_in_entryRuleLocalTranslation6524);
            iv_ruleLocalTranslation=ruleLocalTranslation();

            state._fsp--;

             current =iv_ruleLocalTranslation; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleLocalTranslation6534); 

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
    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3263:1: ruleLocalTranslation returns [EObject current=null] : ( ( (lv_locale_0_0= RULE_ID ) ) otherlv_1= '=' ( (lv_text_2_0= RULE_STRING ) ) ) ;
    public final EObject ruleLocalTranslation() throws RecognitionException {
        EObject current = null;

        Token lv_locale_0_0=null;
        Token otherlv_1=null;
        Token lv_text_2_0=null;

         enterRule(); 
            
        try {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3266:28: ( ( ( (lv_locale_0_0= RULE_ID ) ) otherlv_1= '=' ( (lv_text_2_0= RULE_STRING ) ) ) )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3267:1: ( ( (lv_locale_0_0= RULE_ID ) ) otherlv_1= '=' ( (lv_text_2_0= RULE_STRING ) ) )
            {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3267:1: ( ( (lv_locale_0_0= RULE_ID ) ) otherlv_1= '=' ( (lv_text_2_0= RULE_STRING ) ) )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3267:2: ( (lv_locale_0_0= RULE_ID ) ) otherlv_1= '=' ( (lv_text_2_0= RULE_STRING ) )
            {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3267:2: ( (lv_locale_0_0= RULE_ID ) )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3268:1: (lv_locale_0_0= RULE_ID )
            {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3268:1: (lv_locale_0_0= RULE_ID )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3269:3: lv_locale_0_0= RULE_ID
            {
            lv_locale_0_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleLocalTranslation6576); 

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

            otherlv_1=(Token)match(input,159,FOLLOW_159_in_ruleLocalTranslation6593); 

                	newLeafNode(otherlv_1, grammarAccess.getLocalTranslationAccess().getEqualsSignKeyword_1());
                
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3289:1: ( (lv_text_2_0= RULE_STRING ) )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3290:1: (lv_text_2_0= RULE_STRING )
            {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3290:1: (lv_text_2_0= RULE_STRING )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3291:3: lv_text_2_0= RULE_STRING
            {
            lv_text_2_0=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleLocalTranslation6610); 

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


    // $ANTLR start "entryRuleTranslations"
    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3315:1: entryRuleTranslations returns [EObject current=null] : iv_ruleTranslations= ruleTranslations EOF ;
    public final EObject entryRuleTranslations() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTranslations = null;


        try {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3316:2: (iv_ruleTranslations= ruleTranslations EOF )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3317:2: iv_ruleTranslations= ruleTranslations EOF
            {
             newCompositeNode(grammarAccess.getTranslationsRule()); 
            pushFollow(FOLLOW_ruleTranslations_in_entryRuleTranslations6651);
            iv_ruleTranslations=ruleTranslations();

            state._fsp--;

             current =iv_ruleTranslations; 
            match(input,EOF,FOLLOW_EOF_in_entryRuleTranslations6661); 

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
    // $ANTLR end "entryRuleTranslations"


    // $ANTLR start "ruleTranslations"
    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3324:1: ruleTranslations returns [EObject current=null] : this_LocalTranslations_0= ruleLocalTranslations ;
    public final EObject ruleTranslations() throws RecognitionException {
        EObject current = null;

        EObject this_LocalTranslations_0 = null;


         enterRule(); 
            
        try {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3327:28: (this_LocalTranslations_0= ruleLocalTranslations )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3329:5: this_LocalTranslations_0= ruleLocalTranslations
            {
             
                    newCompositeNode(grammarAccess.getTranslationsAccess().getLocalTranslationsParserRuleCall()); 
                
            pushFollow(FOLLOW_ruleLocalTranslations_in_ruleTranslations6707);
            this_LocalTranslations_0=ruleLocalTranslations();

            state._fsp--;

             
                    current = this_LocalTranslations_0; 
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
    // $ANTLR end "ruleTranslations"


    // $ANTLR start "ruleYesNo"
    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3345:1: ruleYesNo returns [Enumerator current=null] : ( (enumLiteral_0= 'NULL' ) | (enumLiteral_1= 'Yes' ) | (enumLiteral_2= 'No' ) ) ;
    public final Enumerator ruleYesNo() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;

         enterRule(); 
        try {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3347:28: ( ( (enumLiteral_0= 'NULL' ) | (enumLiteral_1= 'Yes' ) | (enumLiteral_2= 'No' ) ) )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3348:1: ( (enumLiteral_0= 'NULL' ) | (enumLiteral_1= 'Yes' ) | (enumLiteral_2= 'No' ) )
            {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3348:1: ( (enumLiteral_0= 'NULL' ) | (enumLiteral_1= 'Yes' ) | (enumLiteral_2= 'No' ) )
            int alt111=3;
            switch ( input.LA(1) ) {
            case 111:
                {
                alt111=1;
                }
                break;
            case 112:
                {
                alt111=2;
                }
                break;
            case 113:
                {
                alt111=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 111, 0, input);

                throw nvae;
            }

            switch (alt111) {
                case 1 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3348:2: (enumLiteral_0= 'NULL' )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3348:2: (enumLiteral_0= 'NULL' )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3348:4: enumLiteral_0= 'NULL'
                    {
                    enumLiteral_0=(Token)match(input,111,FOLLOW_111_in_ruleYesNo6755); 

                            current = grammarAccess.getYesNoAccess().getNULLEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_0, grammarAccess.getYesNoAccess().getNULLEnumLiteralDeclaration_0()); 
                        

                    }


                    }
                    break;
                case 2 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3354:6: (enumLiteral_1= 'Yes' )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3354:6: (enumLiteral_1= 'Yes' )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3354:8: enumLiteral_1= 'Yes'
                    {
                    enumLiteral_1=(Token)match(input,112,FOLLOW_112_in_ruleYesNo6772); 

                            current = grammarAccess.getYesNoAccess().getYesEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_1, grammarAccess.getYesNoAccess().getYesEnumLiteralDeclaration_1()); 
                        

                    }


                    }
                    break;
                case 3 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3360:6: (enumLiteral_2= 'No' )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3360:6: (enumLiteral_2= 'No' )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3360:8: enumLiteral_2= 'No'
                    {
                    enumLiteral_2=(Token)match(input,113,FOLLOW_113_in_ruleYesNo6789); 

                            current = grammarAccess.getYesNoAccess().getNoEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_2, grammarAccess.getYesNoAccess().getNoEnumLiteralDeclaration_2()); 
                        

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
    // $ANTLR end "ruleYesNo"


    // $ANTLR start "rulePopupBehaviour"
    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3370:1: rulePopupBehaviour returns [Enumerator current=null] : ( (enumLiteral_0= 'None' ) | (enumLiteral_1= 'Calendar' ) | (enumLiteral_2= 'Calculator' ) | (enumLiteral_3= 'RATE CONTROL' ) | (enumLiteral_4= 'Recurrence' ) ) ;
    public final Enumerator rulePopupBehaviour() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;
        Token enumLiteral_4=null;

         enterRule(); 
        try {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3372:28: ( ( (enumLiteral_0= 'None' ) | (enumLiteral_1= 'Calendar' ) | (enumLiteral_2= 'Calculator' ) | (enumLiteral_3= 'RATE CONTROL' ) | (enumLiteral_4= 'Recurrence' ) ) )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3373:1: ( (enumLiteral_0= 'None' ) | (enumLiteral_1= 'Calendar' ) | (enumLiteral_2= 'Calculator' ) | (enumLiteral_3= 'RATE CONTROL' ) | (enumLiteral_4= 'Recurrence' ) )
            {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3373:1: ( (enumLiteral_0= 'None' ) | (enumLiteral_1= 'Calendar' ) | (enumLiteral_2= 'Calculator' ) | (enumLiteral_3= 'RATE CONTROL' ) | (enumLiteral_4= 'Recurrence' ) )
            int alt112=5;
            switch ( input.LA(1) ) {
            case 114:
                {
                alt112=1;
                }
                break;
            case 115:
                {
                alt112=2;
                }
                break;
            case 116:
                {
                alt112=3;
                }
                break;
            case 117:
                {
                alt112=4;
                }
                break;
            case 118:
                {
                alt112=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 112, 0, input);

                throw nvae;
            }

            switch (alt112) {
                case 1 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3373:2: (enumLiteral_0= 'None' )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3373:2: (enumLiteral_0= 'None' )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3373:4: enumLiteral_0= 'None'
                    {
                    enumLiteral_0=(Token)match(input,114,FOLLOW_114_in_rulePopupBehaviour6834); 

                            current = grammarAccess.getPopupBehaviourAccess().getNoneEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_0, grammarAccess.getPopupBehaviourAccess().getNoneEnumLiteralDeclaration_0()); 
                        

                    }


                    }
                    break;
                case 2 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3379:6: (enumLiteral_1= 'Calendar' )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3379:6: (enumLiteral_1= 'Calendar' )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3379:8: enumLiteral_1= 'Calendar'
                    {
                    enumLiteral_1=(Token)match(input,115,FOLLOW_115_in_rulePopupBehaviour6851); 

                            current = grammarAccess.getPopupBehaviourAccess().getCalendarEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_1, grammarAccess.getPopupBehaviourAccess().getCalendarEnumLiteralDeclaration_1()); 
                        

                    }


                    }
                    break;
                case 3 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3385:6: (enumLiteral_2= 'Calculator' )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3385:6: (enumLiteral_2= 'Calculator' )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3385:8: enumLiteral_2= 'Calculator'
                    {
                    enumLiteral_2=(Token)match(input,116,FOLLOW_116_in_rulePopupBehaviour6868); 

                            current = grammarAccess.getPopupBehaviourAccess().getCalculatorEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_2, grammarAccess.getPopupBehaviourAccess().getCalculatorEnumLiteralDeclaration_2()); 
                        

                    }


                    }
                    break;
                case 4 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3391:6: (enumLiteral_3= 'RATE CONTROL' )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3391:6: (enumLiteral_3= 'RATE CONTROL' )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3391:8: enumLiteral_3= 'RATE CONTROL'
                    {
                    enumLiteral_3=(Token)match(input,117,FOLLOW_117_in_rulePopupBehaviour6885); 

                            current = grammarAccess.getPopupBehaviourAccess().getRateControlEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_3, grammarAccess.getPopupBehaviourAccess().getRateControlEnumLiteralDeclaration_3()); 
                        

                    }


                    }
                    break;
                case 5 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3397:6: (enumLiteral_4= 'Recurrence' )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3397:6: (enumLiteral_4= 'Recurrence' )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3397:8: enumLiteral_4= 'Recurrence'
                    {
                    enumLiteral_4=(Token)match(input,118,FOLLOW_118_in_rulePopupBehaviour6902); 

                            current = grammarAccess.getPopupBehaviourAccess().getRecurrenceEnumLiteralDeclaration_4().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_4, grammarAccess.getPopupBehaviourAccess().getRecurrenceEnumLiteralDeclaration_4()); 
                        

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
    // $ANTLR end "rulePopupBehaviour"


    // $ANTLR start "ruleCaseConvention"
    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3407:1: ruleCaseConvention returns [Enumerator current=null] : ( (enumLiteral_0= 'None' ) | (enumLiteral_1= 'Lowercase' ) | (enumLiteral_2= 'Uppercase' ) | (enumLiteral_3= 'Proper_Case' ) ) ;
    public final Enumerator ruleCaseConvention() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;

         enterRule(); 
        try {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3409:28: ( ( (enumLiteral_0= 'None' ) | (enumLiteral_1= 'Lowercase' ) | (enumLiteral_2= 'Uppercase' ) | (enumLiteral_3= 'Proper_Case' ) ) )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3410:1: ( (enumLiteral_0= 'None' ) | (enumLiteral_1= 'Lowercase' ) | (enumLiteral_2= 'Uppercase' ) | (enumLiteral_3= 'Proper_Case' ) )
            {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3410:1: ( (enumLiteral_0= 'None' ) | (enumLiteral_1= 'Lowercase' ) | (enumLiteral_2= 'Uppercase' ) | (enumLiteral_3= 'Proper_Case' ) )
            int alt113=4;
            switch ( input.LA(1) ) {
            case 114:
                {
                alt113=1;
                }
                break;
            case 119:
                {
                alt113=2;
                }
                break;
            case 120:
                {
                alt113=3;
                }
                break;
            case 121:
                {
                alt113=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 113, 0, input);

                throw nvae;
            }

            switch (alt113) {
                case 1 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3410:2: (enumLiteral_0= 'None' )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3410:2: (enumLiteral_0= 'None' )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3410:4: enumLiteral_0= 'None'
                    {
                    enumLiteral_0=(Token)match(input,114,FOLLOW_114_in_ruleCaseConvention6947); 

                            current = grammarAccess.getCaseConventionAccess().getNoneEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_0, grammarAccess.getCaseConventionAccess().getNoneEnumLiteralDeclaration_0()); 
                        

                    }


                    }
                    break;
                case 2 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3416:6: (enumLiteral_1= 'Lowercase' )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3416:6: (enumLiteral_1= 'Lowercase' )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3416:8: enumLiteral_1= 'Lowercase'
                    {
                    enumLiteral_1=(Token)match(input,119,FOLLOW_119_in_ruleCaseConvention6964); 

                            current = grammarAccess.getCaseConventionAccess().getLowercaseEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_1, grammarAccess.getCaseConventionAccess().getLowercaseEnumLiteralDeclaration_1()); 
                        

                    }


                    }
                    break;
                case 3 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3422:6: (enumLiteral_2= 'Uppercase' )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3422:6: (enumLiteral_2= 'Uppercase' )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3422:8: enumLiteral_2= 'Uppercase'
                    {
                    enumLiteral_2=(Token)match(input,120,FOLLOW_120_in_ruleCaseConvention6981); 

                            current = grammarAccess.getCaseConventionAccess().getUppercaseEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_2, grammarAccess.getCaseConventionAccess().getUppercaseEnumLiteralDeclaration_2()); 
                        

                    }


                    }
                    break;
                case 4 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3428:6: (enumLiteral_3= 'Proper_Case' )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3428:6: (enumLiteral_3= 'Proper_Case' )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3428:8: enumLiteral_3= 'Proper_Case'
                    {
                    enumLiteral_3=(Token)match(input,121,FOLLOW_121_in_ruleCaseConvention6998); 

                            current = grammarAccess.getCaseConventionAccess().getPropercaseEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_3, grammarAccess.getCaseConventionAccess().getPropercaseEnumLiteralDeclaration_3()); 
                        

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
    // $ANTLR end "ruleCaseConvention"


    // $ANTLR start "ruleDisplayType"
    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3438:1: ruleDisplayType returns [Enumerator current=null] : ( (enumLiteral_0= 'None' ) | (enumLiteral_1= 'NoDisplay' ) | (enumLiteral_2= 'Combobox' ) | (enumLiteral_3= 'Vertical.Options' ) | (enumLiteral_4= 'Toggle' ) | (enumLiteral_5= 'DropDown.NoInput' ) ) ;
    public final Enumerator ruleDisplayType() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;
        Token enumLiteral_4=null;
        Token enumLiteral_5=null;

         enterRule(); 
        try {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3440:28: ( ( (enumLiteral_0= 'None' ) | (enumLiteral_1= 'NoDisplay' ) | (enumLiteral_2= 'Combobox' ) | (enumLiteral_3= 'Vertical.Options' ) | (enumLiteral_4= 'Toggle' ) | (enumLiteral_5= 'DropDown.NoInput' ) ) )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3441:1: ( (enumLiteral_0= 'None' ) | (enumLiteral_1= 'NoDisplay' ) | (enumLiteral_2= 'Combobox' ) | (enumLiteral_3= 'Vertical.Options' ) | (enumLiteral_4= 'Toggle' ) | (enumLiteral_5= 'DropDown.NoInput' ) )
            {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3441:1: ( (enumLiteral_0= 'None' ) | (enumLiteral_1= 'NoDisplay' ) | (enumLiteral_2= 'Combobox' ) | (enumLiteral_3= 'Vertical.Options' ) | (enumLiteral_4= 'Toggle' ) | (enumLiteral_5= 'DropDown.NoInput' ) )
            int alt114=6;
            switch ( input.LA(1) ) {
            case 114:
                {
                alt114=1;
                }
                break;
            case 122:
                {
                alt114=2;
                }
                break;
            case 123:
                {
                alt114=3;
                }
                break;
            case 124:
                {
                alt114=4;
                }
                break;
            case 125:
                {
                alt114=5;
                }
                break;
            case 126:
                {
                alt114=6;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 114, 0, input);

                throw nvae;
            }

            switch (alt114) {
                case 1 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3441:2: (enumLiteral_0= 'None' )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3441:2: (enumLiteral_0= 'None' )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3441:4: enumLiteral_0= 'None'
                    {
                    enumLiteral_0=(Token)match(input,114,FOLLOW_114_in_ruleDisplayType7043); 

                            current = grammarAccess.getDisplayTypeAccess().getNoneEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_0, grammarAccess.getDisplayTypeAccess().getNoneEnumLiteralDeclaration_0()); 
                        

                    }


                    }
                    break;
                case 2 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3447:6: (enumLiteral_1= 'NoDisplay' )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3447:6: (enumLiteral_1= 'NoDisplay' )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3447:8: enumLiteral_1= 'NoDisplay'
                    {
                    enumLiteral_1=(Token)match(input,122,FOLLOW_122_in_ruleDisplayType7060); 

                            current = grammarAccess.getDisplayTypeAccess().getNoDisplayEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_1, grammarAccess.getDisplayTypeAccess().getNoDisplayEnumLiteralDeclaration_1()); 
                        

                    }


                    }
                    break;
                case 3 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3453:6: (enumLiteral_2= 'Combobox' )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3453:6: (enumLiteral_2= 'Combobox' )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3453:8: enumLiteral_2= 'Combobox'
                    {
                    enumLiteral_2=(Token)match(input,123,FOLLOW_123_in_ruleDisplayType7077); 

                            current = grammarAccess.getDisplayTypeAccess().getComboboxEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_2, grammarAccess.getDisplayTypeAccess().getComboboxEnumLiteralDeclaration_2()); 
                        

                    }


                    }
                    break;
                case 4 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3459:6: (enumLiteral_3= 'Vertical.Options' )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3459:6: (enumLiteral_3= 'Vertical.Options' )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3459:8: enumLiteral_3= 'Vertical.Options'
                    {
                    enumLiteral_3=(Token)match(input,124,FOLLOW_124_in_ruleDisplayType7094); 

                            current = grammarAccess.getDisplayTypeAccess().getVerticalOptionsEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_3, grammarAccess.getDisplayTypeAccess().getVerticalOptionsEnumLiteralDeclaration_3()); 
                        

                    }


                    }
                    break;
                case 5 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3465:6: (enumLiteral_4= 'Toggle' )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3465:6: (enumLiteral_4= 'Toggle' )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3465:8: enumLiteral_4= 'Toggle'
                    {
                    enumLiteral_4=(Token)match(input,125,FOLLOW_125_in_ruleDisplayType7111); 

                            current = grammarAccess.getDisplayTypeAccess().getToggleEnumLiteralDeclaration_4().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_4, grammarAccess.getDisplayTypeAccess().getToggleEnumLiteralDeclaration_4()); 
                        

                    }


                    }
                    break;
                case 6 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3471:6: (enumLiteral_5= 'DropDown.NoInput' )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3471:6: (enumLiteral_5= 'DropDown.NoInput' )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3471:8: enumLiteral_5= 'DropDown.NoInput'
                    {
                    enumLiteral_5=(Token)match(input,126,FOLLOW_126_in_ruleDisplayType7128); 

                            current = grammarAccess.getDisplayTypeAccess().getDropDownNoInputEnumLiteralDeclaration_5().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_5, grammarAccess.getDisplayTypeAccess().getDropDownNoInputEnumLiteralDeclaration_5()); 
                        

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
    // $ANTLR end "ruleDisplayType"


    // $ANTLR start "ruleDealSlipFormatFunction"
    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3481:1: ruleDealSlipFormatFunction returns [Enumerator current=null] : ( (enumLiteral_0= 'None' ) | (enumLiteral_1= 'I' ) | (enumLiteral_2= 'A' ) | (enumLiteral_3= 'C' ) | (enumLiteral_4= 'R' ) | (enumLiteral_5= 'D' ) | (enumLiteral_6= 'H' ) | (enumLiteral_7= 'Finish' ) ) ;
    public final Enumerator ruleDealSlipFormatFunction() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;
        Token enumLiteral_4=null;
        Token enumLiteral_5=null;
        Token enumLiteral_6=null;
        Token enumLiteral_7=null;

         enterRule(); 
        try {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3483:28: ( ( (enumLiteral_0= 'None' ) | (enumLiteral_1= 'I' ) | (enumLiteral_2= 'A' ) | (enumLiteral_3= 'C' ) | (enumLiteral_4= 'R' ) | (enumLiteral_5= 'D' ) | (enumLiteral_6= 'H' ) | (enumLiteral_7= 'Finish' ) ) )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3484:1: ( (enumLiteral_0= 'None' ) | (enumLiteral_1= 'I' ) | (enumLiteral_2= 'A' ) | (enumLiteral_3= 'C' ) | (enumLiteral_4= 'R' ) | (enumLiteral_5= 'D' ) | (enumLiteral_6= 'H' ) | (enumLiteral_7= 'Finish' ) )
            {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3484:1: ( (enumLiteral_0= 'None' ) | (enumLiteral_1= 'I' ) | (enumLiteral_2= 'A' ) | (enumLiteral_3= 'C' ) | (enumLiteral_4= 'R' ) | (enumLiteral_5= 'D' ) | (enumLiteral_6= 'H' ) | (enumLiteral_7= 'Finish' ) )
            int alt115=8;
            switch ( input.LA(1) ) {
            case 114:
                {
                alt115=1;
                }
                break;
            case 127:
                {
                alt115=2;
                }
                break;
            case 128:
                {
                alt115=3;
                }
                break;
            case 129:
                {
                alt115=4;
                }
                break;
            case 130:
                {
                alt115=5;
                }
                break;
            case 131:
                {
                alt115=6;
                }
                break;
            case 132:
                {
                alt115=7;
                }
                break;
            case 133:
                {
                alt115=8;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 115, 0, input);

                throw nvae;
            }

            switch (alt115) {
                case 1 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3484:2: (enumLiteral_0= 'None' )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3484:2: (enumLiteral_0= 'None' )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3484:4: enumLiteral_0= 'None'
                    {
                    enumLiteral_0=(Token)match(input,114,FOLLOW_114_in_ruleDealSlipFormatFunction7173); 

                            current = grammarAccess.getDealSlipFormatFunctionAccess().getNoneEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_0, grammarAccess.getDealSlipFormatFunctionAccess().getNoneEnumLiteralDeclaration_0()); 
                        

                    }


                    }
                    break;
                case 2 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3490:6: (enumLiteral_1= 'I' )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3490:6: (enumLiteral_1= 'I' )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3490:8: enumLiteral_1= 'I'
                    {
                    enumLiteral_1=(Token)match(input,127,FOLLOW_127_in_ruleDealSlipFormatFunction7190); 

                            current = grammarAccess.getDealSlipFormatFunctionAccess().getIEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_1, grammarAccess.getDealSlipFormatFunctionAccess().getIEnumLiteralDeclaration_1()); 
                        

                    }


                    }
                    break;
                case 3 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3496:6: (enumLiteral_2= 'A' )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3496:6: (enumLiteral_2= 'A' )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3496:8: enumLiteral_2= 'A'
                    {
                    enumLiteral_2=(Token)match(input,128,FOLLOW_128_in_ruleDealSlipFormatFunction7207); 

                            current = grammarAccess.getDealSlipFormatFunctionAccess().getAEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_2, grammarAccess.getDealSlipFormatFunctionAccess().getAEnumLiteralDeclaration_2()); 
                        

                    }


                    }
                    break;
                case 4 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3502:6: (enumLiteral_3= 'C' )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3502:6: (enumLiteral_3= 'C' )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3502:8: enumLiteral_3= 'C'
                    {
                    enumLiteral_3=(Token)match(input,129,FOLLOW_129_in_ruleDealSlipFormatFunction7224); 

                            current = grammarAccess.getDealSlipFormatFunctionAccess().getCEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_3, grammarAccess.getDealSlipFormatFunctionAccess().getCEnumLiteralDeclaration_3()); 
                        

                    }


                    }
                    break;
                case 5 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3508:6: (enumLiteral_4= 'R' )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3508:6: (enumLiteral_4= 'R' )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3508:8: enumLiteral_4= 'R'
                    {
                    enumLiteral_4=(Token)match(input,130,FOLLOW_130_in_ruleDealSlipFormatFunction7241); 

                            current = grammarAccess.getDealSlipFormatFunctionAccess().getREnumLiteralDeclaration_4().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_4, grammarAccess.getDealSlipFormatFunctionAccess().getREnumLiteralDeclaration_4()); 
                        

                    }


                    }
                    break;
                case 6 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3514:6: (enumLiteral_5= 'D' )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3514:6: (enumLiteral_5= 'D' )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3514:8: enumLiteral_5= 'D'
                    {
                    enumLiteral_5=(Token)match(input,131,FOLLOW_131_in_ruleDealSlipFormatFunction7258); 

                            current = grammarAccess.getDealSlipFormatFunctionAccess().getDEnumLiteralDeclaration_5().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_5, grammarAccess.getDealSlipFormatFunctionAccess().getDEnumLiteralDeclaration_5()); 
                        

                    }


                    }
                    break;
                case 7 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3520:6: (enumLiteral_6= 'H' )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3520:6: (enumLiteral_6= 'H' )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3520:8: enumLiteral_6= 'H'
                    {
                    enumLiteral_6=(Token)match(input,132,FOLLOW_132_in_ruleDealSlipFormatFunction7275); 

                            current = grammarAccess.getDealSlipFormatFunctionAccess().getHEnumLiteralDeclaration_6().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_6, grammarAccess.getDealSlipFormatFunctionAccess().getHEnumLiteralDeclaration_6()); 
                        

                    }


                    }
                    break;
                case 8 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3526:6: (enumLiteral_7= 'Finish' )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3526:6: (enumLiteral_7= 'Finish' )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3526:8: enumLiteral_7= 'Finish'
                    {
                    enumLiteral_7=(Token)match(input,133,FOLLOW_133_in_ruleDealSlipFormatFunction7292); 

                            current = grammarAccess.getDealSlipFormatFunctionAccess().getFinishEnumLiteralDeclaration_7().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_7, grammarAccess.getDealSlipFormatFunctionAccess().getFinishEnumLiteralDeclaration_7()); 
                        

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
    // $ANTLR end "ruleDealSlipFormatFunction"


    // $ANTLR start "ruleDealSlipTrigger"
    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3536:1: ruleDealSlipTrigger returns [Enumerator current=null] : ( (enumLiteral_0= 'None' ) | (enumLiteral_1= 'OL' ) | (enumLiteral_2= 'RQ' ) ) ;
    public final Enumerator ruleDealSlipTrigger() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;

         enterRule(); 
        try {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3538:28: ( ( (enumLiteral_0= 'None' ) | (enumLiteral_1= 'OL' ) | (enumLiteral_2= 'RQ' ) ) )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3539:1: ( (enumLiteral_0= 'None' ) | (enumLiteral_1= 'OL' ) | (enumLiteral_2= 'RQ' ) )
            {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3539:1: ( (enumLiteral_0= 'None' ) | (enumLiteral_1= 'OL' ) | (enumLiteral_2= 'RQ' ) )
            int alt116=3;
            switch ( input.LA(1) ) {
            case 114:
                {
                alt116=1;
                }
                break;
            case 134:
                {
                alt116=2;
                }
                break;
            case 135:
                {
                alt116=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 116, 0, input);

                throw nvae;
            }

            switch (alt116) {
                case 1 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3539:2: (enumLiteral_0= 'None' )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3539:2: (enumLiteral_0= 'None' )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3539:4: enumLiteral_0= 'None'
                    {
                    enumLiteral_0=(Token)match(input,114,FOLLOW_114_in_ruleDealSlipTrigger7337); 

                            current = grammarAccess.getDealSlipTriggerAccess().getNoneEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_0, grammarAccess.getDealSlipTriggerAccess().getNoneEnumLiteralDeclaration_0()); 
                        

                    }


                    }
                    break;
                case 2 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3545:6: (enumLiteral_1= 'OL' )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3545:6: (enumLiteral_1= 'OL' )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3545:8: enumLiteral_1= 'OL'
                    {
                    enumLiteral_1=(Token)match(input,134,FOLLOW_134_in_ruleDealSlipTrigger7354); 

                            current = grammarAccess.getDealSlipTriggerAccess().getOLEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_1, grammarAccess.getDealSlipTriggerAccess().getOLEnumLiteralDeclaration_1()); 
                        

                    }


                    }
                    break;
                case 3 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3551:6: (enumLiteral_2= 'RQ' )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3551:6: (enumLiteral_2= 'RQ' )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3551:8: enumLiteral_2= 'RQ'
                    {
                    enumLiteral_2=(Token)match(input,135,FOLLOW_135_in_ruleDealSlipTrigger7371); 

                            current = grammarAccess.getDealSlipTriggerAccess().getRQEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_2, grammarAccess.getDealSlipTriggerAccess().getRQEnumLiteralDeclaration_2()); 
                        

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
    // $ANTLR end "ruleDealSlipTrigger"


    // $ANTLR start "ruleBusinessDayControl"
    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3561:1: ruleBusinessDayControl returns [Enumerator current=null] : ( (enumLiteral_0= 'None' ) | (enumLiteral_1= 'Normal' ) | (enumLiteral_2= 'Restricted' ) | (enumLiteral_3= 'Closed' ) ) ;
    public final Enumerator ruleBusinessDayControl() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;

         enterRule(); 
        try {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3563:28: ( ( (enumLiteral_0= 'None' ) | (enumLiteral_1= 'Normal' ) | (enumLiteral_2= 'Restricted' ) | (enumLiteral_3= 'Closed' ) ) )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3564:1: ( (enumLiteral_0= 'None' ) | (enumLiteral_1= 'Normal' ) | (enumLiteral_2= 'Restricted' ) | (enumLiteral_3= 'Closed' ) )
            {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3564:1: ( (enumLiteral_0= 'None' ) | (enumLiteral_1= 'Normal' ) | (enumLiteral_2= 'Restricted' ) | (enumLiteral_3= 'Closed' ) )
            int alt117=4;
            switch ( input.LA(1) ) {
            case 114:
                {
                alt117=1;
                }
                break;
            case 136:
                {
                alt117=2;
                }
                break;
            case 137:
                {
                alt117=3;
                }
                break;
            case 138:
                {
                alt117=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 117, 0, input);

                throw nvae;
            }

            switch (alt117) {
                case 1 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3564:2: (enumLiteral_0= 'None' )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3564:2: (enumLiteral_0= 'None' )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3564:4: enumLiteral_0= 'None'
                    {
                    enumLiteral_0=(Token)match(input,114,FOLLOW_114_in_ruleBusinessDayControl7416); 

                            current = grammarAccess.getBusinessDayControlAccess().getNoneEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_0, grammarAccess.getBusinessDayControlAccess().getNoneEnumLiteralDeclaration_0()); 
                        

                    }


                    }
                    break;
                case 2 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3570:6: (enumLiteral_1= 'Normal' )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3570:6: (enumLiteral_1= 'Normal' )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3570:8: enumLiteral_1= 'Normal'
                    {
                    enumLiteral_1=(Token)match(input,136,FOLLOW_136_in_ruleBusinessDayControl7433); 

                            current = grammarAccess.getBusinessDayControlAccess().getNormalEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_1, grammarAccess.getBusinessDayControlAccess().getNormalEnumLiteralDeclaration_1()); 
                        

                    }


                    }
                    break;
                case 3 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3576:6: (enumLiteral_2= 'Restricted' )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3576:6: (enumLiteral_2= 'Restricted' )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3576:8: enumLiteral_2= 'Restricted'
                    {
                    enumLiteral_2=(Token)match(input,137,FOLLOW_137_in_ruleBusinessDayControl7450); 

                            current = grammarAccess.getBusinessDayControlAccess().getRestrictedEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_2, grammarAccess.getBusinessDayControlAccess().getRestrictedEnumLiteralDeclaration_2()); 
                        

                    }


                    }
                    break;
                case 4 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3582:6: (enumLiteral_3= 'Closed' )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3582:6: (enumLiteral_3= 'Closed' )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3582:8: enumLiteral_3= 'Closed'
                    {
                    enumLiteral_3=(Token)match(input,138,FOLLOW_138_in_ruleBusinessDayControl7467); 

                            current = grammarAccess.getBusinessDayControlAccess().getClosedEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_3, grammarAccess.getBusinessDayControlAccess().getClosedEnumLiteralDeclaration_3()); 
                        

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
    // $ANTLR end "ruleBusinessDayControl"


    // $ANTLR start "ruleFunction"
    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3592:1: ruleFunction returns [Enumerator current=null] : ( (enumLiteral_0= 'None' ) | (enumLiteral_1= 'Input' ) | (enumLiteral_2= 'Authorise' ) | (enumLiteral_3= 'Reverse' ) | (enumLiteral_4= 'See' ) | (enumLiteral_5= 'Copy' ) | (enumLiteral_6= 'Delete' ) | (enumLiteral_7= 'HistoryRestore' ) | (enumLiteral_8= 'Verify' ) | (enumLiteral_9= 'AuditorReview' ) ) ;
    public final Enumerator ruleFunction() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;
        Token enumLiteral_4=null;
        Token enumLiteral_5=null;
        Token enumLiteral_6=null;
        Token enumLiteral_7=null;
        Token enumLiteral_8=null;
        Token enumLiteral_9=null;

         enterRule(); 
        try {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3594:28: ( ( (enumLiteral_0= 'None' ) | (enumLiteral_1= 'Input' ) | (enumLiteral_2= 'Authorise' ) | (enumLiteral_3= 'Reverse' ) | (enumLiteral_4= 'See' ) | (enumLiteral_5= 'Copy' ) | (enumLiteral_6= 'Delete' ) | (enumLiteral_7= 'HistoryRestore' ) | (enumLiteral_8= 'Verify' ) | (enumLiteral_9= 'AuditorReview' ) ) )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3595:1: ( (enumLiteral_0= 'None' ) | (enumLiteral_1= 'Input' ) | (enumLiteral_2= 'Authorise' ) | (enumLiteral_3= 'Reverse' ) | (enumLiteral_4= 'See' ) | (enumLiteral_5= 'Copy' ) | (enumLiteral_6= 'Delete' ) | (enumLiteral_7= 'HistoryRestore' ) | (enumLiteral_8= 'Verify' ) | (enumLiteral_9= 'AuditorReview' ) )
            {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3595:1: ( (enumLiteral_0= 'None' ) | (enumLiteral_1= 'Input' ) | (enumLiteral_2= 'Authorise' ) | (enumLiteral_3= 'Reverse' ) | (enumLiteral_4= 'See' ) | (enumLiteral_5= 'Copy' ) | (enumLiteral_6= 'Delete' ) | (enumLiteral_7= 'HistoryRestore' ) | (enumLiteral_8= 'Verify' ) | (enumLiteral_9= 'AuditorReview' ) )
            int alt118=10;
            switch ( input.LA(1) ) {
            case 114:
                {
                alt118=1;
                }
                break;
            case 139:
                {
                alt118=2;
                }
                break;
            case 140:
                {
                alt118=3;
                }
                break;
            case 141:
                {
                alt118=4;
                }
                break;
            case 142:
                {
                alt118=5;
                }
                break;
            case 143:
                {
                alt118=6;
                }
                break;
            case 144:
                {
                alt118=7;
                }
                break;
            case 145:
                {
                alt118=8;
                }
                break;
            case 146:
                {
                alt118=9;
                }
                break;
            case 147:
                {
                alt118=10;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 118, 0, input);

                throw nvae;
            }

            switch (alt118) {
                case 1 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3595:2: (enumLiteral_0= 'None' )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3595:2: (enumLiteral_0= 'None' )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3595:4: enumLiteral_0= 'None'
                    {
                    enumLiteral_0=(Token)match(input,114,FOLLOW_114_in_ruleFunction7512); 

                            current = grammarAccess.getFunctionAccess().getNoneEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_0, grammarAccess.getFunctionAccess().getNoneEnumLiteralDeclaration_0()); 
                        

                    }


                    }
                    break;
                case 2 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3601:6: (enumLiteral_1= 'Input' )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3601:6: (enumLiteral_1= 'Input' )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3601:8: enumLiteral_1= 'Input'
                    {
                    enumLiteral_1=(Token)match(input,139,FOLLOW_139_in_ruleFunction7529); 

                            current = grammarAccess.getFunctionAccess().getInputEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_1, grammarAccess.getFunctionAccess().getInputEnumLiteralDeclaration_1()); 
                        

                    }


                    }
                    break;
                case 3 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3607:6: (enumLiteral_2= 'Authorise' )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3607:6: (enumLiteral_2= 'Authorise' )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3607:8: enumLiteral_2= 'Authorise'
                    {
                    enumLiteral_2=(Token)match(input,140,FOLLOW_140_in_ruleFunction7546); 

                            current = grammarAccess.getFunctionAccess().getAuthoriseEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_2, grammarAccess.getFunctionAccess().getAuthoriseEnumLiteralDeclaration_2()); 
                        

                    }


                    }
                    break;
                case 4 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3613:6: (enumLiteral_3= 'Reverse' )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3613:6: (enumLiteral_3= 'Reverse' )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3613:8: enumLiteral_3= 'Reverse'
                    {
                    enumLiteral_3=(Token)match(input,141,FOLLOW_141_in_ruleFunction7563); 

                            current = grammarAccess.getFunctionAccess().getReverseEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_3, grammarAccess.getFunctionAccess().getReverseEnumLiteralDeclaration_3()); 
                        

                    }


                    }
                    break;
                case 5 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3619:6: (enumLiteral_4= 'See' )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3619:6: (enumLiteral_4= 'See' )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3619:8: enumLiteral_4= 'See'
                    {
                    enumLiteral_4=(Token)match(input,142,FOLLOW_142_in_ruleFunction7580); 

                            current = grammarAccess.getFunctionAccess().getSeeEnumLiteralDeclaration_4().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_4, grammarAccess.getFunctionAccess().getSeeEnumLiteralDeclaration_4()); 
                        

                    }


                    }
                    break;
                case 6 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3625:6: (enumLiteral_5= 'Copy' )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3625:6: (enumLiteral_5= 'Copy' )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3625:8: enumLiteral_5= 'Copy'
                    {
                    enumLiteral_5=(Token)match(input,143,FOLLOW_143_in_ruleFunction7597); 

                            current = grammarAccess.getFunctionAccess().getCopyEnumLiteralDeclaration_5().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_5, grammarAccess.getFunctionAccess().getCopyEnumLiteralDeclaration_5()); 
                        

                    }


                    }
                    break;
                case 7 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3631:6: (enumLiteral_6= 'Delete' )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3631:6: (enumLiteral_6= 'Delete' )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3631:8: enumLiteral_6= 'Delete'
                    {
                    enumLiteral_6=(Token)match(input,144,FOLLOW_144_in_ruleFunction7614); 

                            current = grammarAccess.getFunctionAccess().getDeleteEnumLiteralDeclaration_6().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_6, grammarAccess.getFunctionAccess().getDeleteEnumLiteralDeclaration_6()); 
                        

                    }


                    }
                    break;
                case 8 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3637:6: (enumLiteral_7= 'HistoryRestore' )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3637:6: (enumLiteral_7= 'HistoryRestore' )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3637:8: enumLiteral_7= 'HistoryRestore'
                    {
                    enumLiteral_7=(Token)match(input,145,FOLLOW_145_in_ruleFunction7631); 

                            current = grammarAccess.getFunctionAccess().getHistoryRestoreEnumLiteralDeclaration_7().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_7, grammarAccess.getFunctionAccess().getHistoryRestoreEnumLiteralDeclaration_7()); 
                        

                    }


                    }
                    break;
                case 9 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3643:6: (enumLiteral_8= 'Verify' )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3643:6: (enumLiteral_8= 'Verify' )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3643:8: enumLiteral_8= 'Verify'
                    {
                    enumLiteral_8=(Token)match(input,146,FOLLOW_146_in_ruleFunction7648); 

                            current = grammarAccess.getFunctionAccess().getVerifyEnumLiteralDeclaration_8().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_8, grammarAccess.getFunctionAccess().getVerifyEnumLiteralDeclaration_8()); 
                        

                    }


                    }
                    break;
                case 10 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3649:6: (enumLiteral_9= 'AuditorReview' )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3649:6: (enumLiteral_9= 'AuditorReview' )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3649:8: enumLiteral_9= 'AuditorReview'
                    {
                    enumLiteral_9=(Token)match(input,147,FOLLOW_147_in_ruleFunction7665); 

                            current = grammarAccess.getFunctionAccess().getAuditorReviewEnumLiteralDeclaration_9().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_9, grammarAccess.getFunctionAccess().getAuditorReviewEnumLiteralDeclaration_9()); 
                        

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
    // $ANTLR end "ruleFunction"


    // $ANTLR start "ruleInputBehaviour"
    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3659:1: ruleInputBehaviour returns [Enumerator current=null] : ( (enumLiteral_0= 'None' ) | (enumLiteral_1= 'NoChange' ) | (enumLiteral_2= 'NoInput' ) ) ;
    public final Enumerator ruleInputBehaviour() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;

         enterRule(); 
        try {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3661:28: ( ( (enumLiteral_0= 'None' ) | (enumLiteral_1= 'NoChange' ) | (enumLiteral_2= 'NoInput' ) ) )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3662:1: ( (enumLiteral_0= 'None' ) | (enumLiteral_1= 'NoChange' ) | (enumLiteral_2= 'NoInput' ) )
            {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3662:1: ( (enumLiteral_0= 'None' ) | (enumLiteral_1= 'NoChange' ) | (enumLiteral_2= 'NoInput' ) )
            int alt119=3;
            switch ( input.LA(1) ) {
            case 114:
                {
                alt119=1;
                }
                break;
            case 148:
                {
                alt119=2;
                }
                break;
            case 149:
                {
                alt119=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 119, 0, input);

                throw nvae;
            }

            switch (alt119) {
                case 1 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3662:2: (enumLiteral_0= 'None' )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3662:2: (enumLiteral_0= 'None' )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3662:4: enumLiteral_0= 'None'
                    {
                    enumLiteral_0=(Token)match(input,114,FOLLOW_114_in_ruleInputBehaviour7710); 

                            current = grammarAccess.getInputBehaviourAccess().getNoneEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_0, grammarAccess.getInputBehaviourAccess().getNoneEnumLiteralDeclaration_0()); 
                        

                    }


                    }
                    break;
                case 2 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3668:6: (enumLiteral_1= 'NoChange' )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3668:6: (enumLiteral_1= 'NoChange' )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3668:8: enumLiteral_1= 'NoChange'
                    {
                    enumLiteral_1=(Token)match(input,148,FOLLOW_148_in_ruleInputBehaviour7727); 

                            current = grammarAccess.getInputBehaviourAccess().getNoChangeEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_1, grammarAccess.getInputBehaviourAccess().getNoChangeEnumLiteralDeclaration_1()); 
                        

                    }


                    }
                    break;
                case 3 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3674:6: (enumLiteral_2= 'NoInput' )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3674:6: (enumLiteral_2= 'NoInput' )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3674:8: enumLiteral_2= 'NoInput'
                    {
                    enumLiteral_2=(Token)match(input,149,FOLLOW_149_in_ruleInputBehaviour7744); 

                            current = grammarAccess.getInputBehaviourAccess().getNoInputEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_2, grammarAccess.getInputBehaviourAccess().getNoInputEnumLiteralDeclaration_2()); 
                        

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
    // $ANTLR end "ruleInputBehaviour"


    // $ANTLR start "ruleExpansion"
    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3684:1: ruleExpansion returns [Enumerator current=null] : ( (enumLiteral_0= 'None' ) | (enumLiteral_1= 'No' ) ) ;
    public final Enumerator ruleExpansion() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;

         enterRule(); 
        try {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3686:28: ( ( (enumLiteral_0= 'None' ) | (enumLiteral_1= 'No' ) ) )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3687:1: ( (enumLiteral_0= 'None' ) | (enumLiteral_1= 'No' ) )
            {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3687:1: ( (enumLiteral_0= 'None' ) | (enumLiteral_1= 'No' ) )
            int alt120=2;
            int LA120_0 = input.LA(1);

            if ( (LA120_0==114) ) {
                alt120=1;
            }
            else if ( (LA120_0==113) ) {
                alt120=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 120, 0, input);

                throw nvae;
            }
            switch (alt120) {
                case 1 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3687:2: (enumLiteral_0= 'None' )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3687:2: (enumLiteral_0= 'None' )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3687:4: enumLiteral_0= 'None'
                    {
                    enumLiteral_0=(Token)match(input,114,FOLLOW_114_in_ruleExpansion7789); 

                            current = grammarAccess.getExpansionAccess().getNoneEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_0, grammarAccess.getExpansionAccess().getNoneEnumLiteralDeclaration_0()); 
                        

                    }


                    }
                    break;
                case 2 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3693:6: (enumLiteral_1= 'No' )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3693:6: (enumLiteral_1= 'No' )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3693:8: enumLiteral_1= 'No'
                    {
                    enumLiteral_1=(Token)match(input,113,FOLLOW_113_in_ruleExpansion7806); 

                            current = grammarAccess.getExpansionAccess().getNoEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_1, grammarAccess.getExpansionAccess().getNoEnumLiteralDeclaration_1()); 
                        

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
    // $ANTLR end "ruleExpansion"


    // $ANTLR start "ruleAssociatedVersionsPresentationPattern"
    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3703:1: ruleAssociatedVersionsPresentationPattern returns [Enumerator current=null] : ( (enumLiteral_0= 'None' ) | (enumLiteral_1= 'Tabs' ) | (enumLiteral_2= 'Vertical' ) | (enumLiteral_3= 'Accordions' ) ) ;
    public final Enumerator ruleAssociatedVersionsPresentationPattern() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;

         enterRule(); 
        try {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3705:28: ( ( (enumLiteral_0= 'None' ) | (enumLiteral_1= 'Tabs' ) | (enumLiteral_2= 'Vertical' ) | (enumLiteral_3= 'Accordions' ) ) )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3706:1: ( (enumLiteral_0= 'None' ) | (enumLiteral_1= 'Tabs' ) | (enumLiteral_2= 'Vertical' ) | (enumLiteral_3= 'Accordions' ) )
            {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3706:1: ( (enumLiteral_0= 'None' ) | (enumLiteral_1= 'Tabs' ) | (enumLiteral_2= 'Vertical' ) | (enumLiteral_3= 'Accordions' ) )
            int alt121=4;
            switch ( input.LA(1) ) {
            case 114:
                {
                alt121=1;
                }
                break;
            case 150:
                {
                alt121=2;
                }
                break;
            case 151:
                {
                alt121=3;
                }
                break;
            case 152:
                {
                alt121=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 121, 0, input);

                throw nvae;
            }

            switch (alt121) {
                case 1 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3706:2: (enumLiteral_0= 'None' )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3706:2: (enumLiteral_0= 'None' )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3706:4: enumLiteral_0= 'None'
                    {
                    enumLiteral_0=(Token)match(input,114,FOLLOW_114_in_ruleAssociatedVersionsPresentationPattern7851); 

                            current = grammarAccess.getAssociatedVersionsPresentationPatternAccess().getNoneEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_0, grammarAccess.getAssociatedVersionsPresentationPatternAccess().getNoneEnumLiteralDeclaration_0()); 
                        

                    }


                    }
                    break;
                case 2 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3712:6: (enumLiteral_1= 'Tabs' )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3712:6: (enumLiteral_1= 'Tabs' )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3712:8: enumLiteral_1= 'Tabs'
                    {
                    enumLiteral_1=(Token)match(input,150,FOLLOW_150_in_ruleAssociatedVersionsPresentationPattern7868); 

                            current = grammarAccess.getAssociatedVersionsPresentationPatternAccess().getTabsEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_1, grammarAccess.getAssociatedVersionsPresentationPatternAccess().getTabsEnumLiteralDeclaration_1()); 
                        

                    }


                    }
                    break;
                case 3 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3718:6: (enumLiteral_2= 'Vertical' )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3718:6: (enumLiteral_2= 'Vertical' )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3718:8: enumLiteral_2= 'Vertical'
                    {
                    enumLiteral_2=(Token)match(input,151,FOLLOW_151_in_ruleAssociatedVersionsPresentationPattern7885); 

                            current = grammarAccess.getAssociatedVersionsPresentationPatternAccess().getVerticalEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_2, grammarAccess.getAssociatedVersionsPresentationPatternAccess().getVerticalEnumLiteralDeclaration_2()); 
                        

                    }


                    }
                    break;
                case 4 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3724:6: (enumLiteral_3= 'Accordions' )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3724:6: (enumLiteral_3= 'Accordions' )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3724:8: enumLiteral_3= 'Accordions'
                    {
                    enumLiteral_3=(Token)match(input,152,FOLLOW_152_in_ruleAssociatedVersionsPresentationPattern7902); 

                            current = grammarAccess.getAssociatedVersionsPresentationPatternAccess().getAccordionsEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_3, grammarAccess.getAssociatedVersionsPresentationPatternAccess().getAccordionsEnumLiteralDeclaration_3()); 
                        

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
    // $ANTLR end "ruleAssociatedVersionsPresentationPattern"


    // $ANTLR start "ruleFieldsLayoutPattern"
    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3734:1: ruleFieldsLayoutPattern returns [Enumerator current=null] : ( (enumLiteral_0= 'None' ) | (enumLiteral_1= 'OneRow' ) | (enumLiteral_2= 'OneColumn' ) | (enumLiteral_3= 'TwoColumnHorizontal' ) | (enumLiteral_4= 'TwoColumnVertical' ) | (enumLiteral_5= 'ThreeColumnHorizontal' ) | (enumLiteral_6= 'ThreeColumnVertical' ) ) ;
    public final Enumerator ruleFieldsLayoutPattern() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;
        Token enumLiteral_4=null;
        Token enumLiteral_5=null;
        Token enumLiteral_6=null;

         enterRule(); 
        try {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3736:28: ( ( (enumLiteral_0= 'None' ) | (enumLiteral_1= 'OneRow' ) | (enumLiteral_2= 'OneColumn' ) | (enumLiteral_3= 'TwoColumnHorizontal' ) | (enumLiteral_4= 'TwoColumnVertical' ) | (enumLiteral_5= 'ThreeColumnHorizontal' ) | (enumLiteral_6= 'ThreeColumnVertical' ) ) )
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3737:1: ( (enumLiteral_0= 'None' ) | (enumLiteral_1= 'OneRow' ) | (enumLiteral_2= 'OneColumn' ) | (enumLiteral_3= 'TwoColumnHorizontal' ) | (enumLiteral_4= 'TwoColumnVertical' ) | (enumLiteral_5= 'ThreeColumnHorizontal' ) | (enumLiteral_6= 'ThreeColumnVertical' ) )
            {
            // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3737:1: ( (enumLiteral_0= 'None' ) | (enumLiteral_1= 'OneRow' ) | (enumLiteral_2= 'OneColumn' ) | (enumLiteral_3= 'TwoColumnHorizontal' ) | (enumLiteral_4= 'TwoColumnVertical' ) | (enumLiteral_5= 'ThreeColumnHorizontal' ) | (enumLiteral_6= 'ThreeColumnVertical' ) )
            int alt122=7;
            switch ( input.LA(1) ) {
            case 114:
                {
                alt122=1;
                }
                break;
            case 153:
                {
                alt122=2;
                }
                break;
            case 154:
                {
                alt122=3;
                }
                break;
            case 155:
                {
                alt122=4;
                }
                break;
            case 156:
                {
                alt122=5;
                }
                break;
            case 157:
                {
                alt122=6;
                }
                break;
            case 158:
                {
                alt122=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 122, 0, input);

                throw nvae;
            }

            switch (alt122) {
                case 1 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3737:2: (enumLiteral_0= 'None' )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3737:2: (enumLiteral_0= 'None' )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3737:4: enumLiteral_0= 'None'
                    {
                    enumLiteral_0=(Token)match(input,114,FOLLOW_114_in_ruleFieldsLayoutPattern7947); 

                            current = grammarAccess.getFieldsLayoutPatternAccess().getNoneEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_0, grammarAccess.getFieldsLayoutPatternAccess().getNoneEnumLiteralDeclaration_0()); 
                        

                    }


                    }
                    break;
                case 2 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3743:6: (enumLiteral_1= 'OneRow' )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3743:6: (enumLiteral_1= 'OneRow' )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3743:8: enumLiteral_1= 'OneRow'
                    {
                    enumLiteral_1=(Token)match(input,153,FOLLOW_153_in_ruleFieldsLayoutPattern7964); 

                            current = grammarAccess.getFieldsLayoutPatternAccess().getOneRowEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_1, grammarAccess.getFieldsLayoutPatternAccess().getOneRowEnumLiteralDeclaration_1()); 
                        

                    }


                    }
                    break;
                case 3 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3749:6: (enumLiteral_2= 'OneColumn' )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3749:6: (enumLiteral_2= 'OneColumn' )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3749:8: enumLiteral_2= 'OneColumn'
                    {
                    enumLiteral_2=(Token)match(input,154,FOLLOW_154_in_ruleFieldsLayoutPattern7981); 

                            current = grammarAccess.getFieldsLayoutPatternAccess().getOneColumnEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_2, grammarAccess.getFieldsLayoutPatternAccess().getOneColumnEnumLiteralDeclaration_2()); 
                        

                    }


                    }
                    break;
                case 4 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3755:6: (enumLiteral_3= 'TwoColumnHorizontal' )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3755:6: (enumLiteral_3= 'TwoColumnHorizontal' )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3755:8: enumLiteral_3= 'TwoColumnHorizontal'
                    {
                    enumLiteral_3=(Token)match(input,155,FOLLOW_155_in_ruleFieldsLayoutPattern7998); 

                            current = grammarAccess.getFieldsLayoutPatternAccess().getTwoColumnHorizontalEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_3, grammarAccess.getFieldsLayoutPatternAccess().getTwoColumnHorizontalEnumLiteralDeclaration_3()); 
                        

                    }


                    }
                    break;
                case 5 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3761:6: (enumLiteral_4= 'TwoColumnVertical' )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3761:6: (enumLiteral_4= 'TwoColumnVertical' )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3761:8: enumLiteral_4= 'TwoColumnVertical'
                    {
                    enumLiteral_4=(Token)match(input,156,FOLLOW_156_in_ruleFieldsLayoutPattern8015); 

                            current = grammarAccess.getFieldsLayoutPatternAccess().getTwoColumnVerticalEnumLiteralDeclaration_4().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_4, grammarAccess.getFieldsLayoutPatternAccess().getTwoColumnVerticalEnumLiteralDeclaration_4()); 
                        

                    }


                    }
                    break;
                case 6 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3767:6: (enumLiteral_5= 'ThreeColumnHorizontal' )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3767:6: (enumLiteral_5= 'ThreeColumnHorizontal' )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3767:8: enumLiteral_5= 'ThreeColumnHorizontal'
                    {
                    enumLiteral_5=(Token)match(input,157,FOLLOW_157_in_ruleFieldsLayoutPattern8032); 

                            current = grammarAccess.getFieldsLayoutPatternAccess().getThreeColumnHorizontalEnumLiteralDeclaration_5().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_5, grammarAccess.getFieldsLayoutPatternAccess().getThreeColumnHorizontalEnumLiteralDeclaration_5()); 
                        

                    }


                    }
                    break;
                case 7 :
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3773:6: (enumLiteral_6= 'ThreeColumnVertical' )
                    {
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3773:6: (enumLiteral_6= 'ThreeColumnVertical' )
                    // ../com.odcgroup.t24.version.model/src-gen/com/odcgroup/t24/version/parser/antlr/internal/InternalVersionDSL.g:3773:8: enumLiteral_6= 'ThreeColumnVertical'
                    {
                    enumLiteral_6=(Token)match(input,158,FOLLOW_158_in_ruleFieldsLayoutPattern8049); 

                            current = grammarAccess.getFieldsLayoutPatternAccess().getThreeColumnVerticalEnumLiteralDeclaration_6().getEnumLiteral().getInstance();
                            newLeafNode(enumLiteral_6, grammarAccess.getFieldsLayoutPatternAccess().getThreeColumnVerticalEnumLiteralDeclaration_6()); 
                        

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
    // $ANTLR end "ruleFieldsLayoutPattern"

    // Delegated rules


 

    public static final BitSet FOLLOW_ruleVersion_in_entryRuleVersion75 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleVersion85 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_11_in_ruleVersion122 = new BitSet(new long[]{0x0000000000000060L,0xFFFF800000000000L,0x000000007FFFFFFFL});
    public static final BitSet FOLLOW_ruleEntityRef_in_ruleVersion145 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleVersion157 = new BitSet(new long[]{0x620104010007E060L,0xFFFF800000000088L,0x000000007FFFFFFFL});
    public static final BitSet FOLLOW_ruleNID_in_ruleVersion178 = new BitSet(new long[]{0x620104010007E000L,0x0000000000000088L});
    public static final BitSet FOLLOW_13_in_ruleVersion192 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleVersion209 = new BitSet(new long[]{0x620104010007C000L,0x0000000000000088L});
    public static final BitSet FOLLOW_14_in_ruleVersion229 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleVersion246 = new BitSet(new long[]{0x6201040100078000L,0x0000000000000088L});
    public static final BitSet FOLLOW_15_in_ruleVersion266 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleVersion283 = new BitSet(new long[]{0x6201040100070000L,0x0000000000000088L});
    public static final BitSet FOLLOW_16_in_ruleVersion303 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_ruleINTEGER_OBJECT_in_ruleVersion324 = new BitSet(new long[]{0x6201040100060000L,0x0000000000000088L});
    public static final BitSet FOLLOW_17_in_ruleVersion339 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_ruleTranslations_in_ruleVersion360 = new BitSet(new long[]{0x6201040100040000L,0x0000000000000088L});
    public static final BitSet FOLLOW_18_in_ruleVersion375 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_19_in_ruleVersion387 = new BitSet(new long[]{0x00000000FEF00000L});
    public static final BitSet FOLLOW_20_in_ruleVersion400 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_ruleINTEGER_OBJECT_in_ruleVersion421 = new BitSet(new long[]{0x00000000FEE00000L});
    public static final BitSet FOLLOW_21_in_ruleVersion436 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_ruleINTEGER_OBJECT_in_ruleVersion457 = new BitSet(new long[]{0x00000000FEC00000L});
    public static final BitSet FOLLOW_22_in_ruleVersion472 = new BitSet(new long[]{0x0000000000000000L,0x0004000000000000L,0x0000000000000700L});
    public static final BitSet FOLLOW_ruleBusinessDayControl_in_ruleVersion493 = new BitSet(new long[]{0x00000000FE800000L});
    public static final BitSet FOLLOW_23_in_ruleVersion508 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleVersion525 = new BitSet(new long[]{0x00000000FF000000L});
    public static final BitSet FOLLOW_24_in_ruleVersion543 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleVersion560 = new BitSet(new long[]{0x00000000FF000000L});
    public static final BitSet FOLLOW_25_in_ruleVersion582 = new BitSet(new long[]{0x0000000000000000L,0x0003800000000000L});
    public static final BitSet FOLLOW_ruleYesNo_in_ruleVersion603 = new BitSet(new long[]{0x00000000FC000000L});
    public static final BitSet FOLLOW_26_in_ruleVersion618 = new BitSet(new long[]{0x0000000000000000L,0x0003800000000000L});
    public static final BitSet FOLLOW_ruleYesNo_in_ruleVersion639 = new BitSet(new long[]{0x00000000F8000000L});
    public static final BitSet FOLLOW_27_in_ruleVersion654 = new BitSet(new long[]{0x0000000000000000L,0x0003800000000000L});
    public static final BitSet FOLLOW_ruleYesNo_in_ruleVersion675 = new BitSet(new long[]{0x00000000F0000000L});
    public static final BitSet FOLLOW_28_in_ruleVersion690 = new BitSet(new long[]{0x0000000000000000L,0x0000200000000000L});
    public static final BitSet FOLLOW_ruleDealSlip_in_ruleVersion711 = new BitSet(new long[]{0x00000000E1000000L});
    public static final BitSet FOLLOW_24_in_ruleVersion724 = new BitSet(new long[]{0x0000000000000000L,0x0000200000000000L});
    public static final BitSet FOLLOW_ruleDealSlip_in_ruleVersion745 = new BitSet(new long[]{0x00000000E1000000L});
    public static final BitSet FOLLOW_29_in_ruleVersion762 = new BitSet(new long[]{0x0000000000000000L,0x0004000000000000L,0x00000000000000C0L});
    public static final BitSet FOLLOW_ruleDealSlipTrigger_in_ruleVersion783 = new BitSet(new long[]{0x00000000C0000000L});
    public static final BitSet FOLLOW_30_in_ruleVersion798 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleVersion815 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_31_in_ruleVersion834 = new BitSet(new long[]{0x6201040100000000L,0x0000000000000088L});
    public static final BitSet FOLLOW_32_in_ruleVersion849 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_19_in_ruleVersion861 = new BitSet(new long[]{0x000003FE80000000L});
    public static final BitSet FOLLOW_33_in_ruleVersion874 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleVersion891 = new BitSet(new long[]{0x000003FC80000000L});
    public static final BitSet FOLLOW_34_in_ruleVersion911 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleVersion928 = new BitSet(new long[]{0x000003F880000000L});
    public static final BitSet FOLLOW_35_in_ruleVersion948 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleVersion965 = new BitSet(new long[]{0x000003F080000000L});
    public static final BitSet FOLLOW_36_in_ruleVersion985 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleVersion1002 = new BitSet(new long[]{0x000003E080000000L});
    public static final BitSet FOLLOW_37_in_ruleVersion1022 = new BitSet(new long[]{0x0000000000000060L,0xFFFF800000000000L,0x000000007FFFFFFFL});
    public static final BitSet FOLLOW_ruleNID_in_ruleVersion1043 = new BitSet(new long[]{0x000003C081000000L});
    public static final BitSet FOLLOW_24_in_ruleVersion1056 = new BitSet(new long[]{0x0000000000000060L,0xFFFF800000000000L,0x000000007FFFFFFFL});
    public static final BitSet FOLLOW_ruleNID_in_ruleVersion1077 = new BitSet(new long[]{0x000003C081000000L});
    public static final BitSet FOLLOW_38_in_ruleVersion1094 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_ruleTranslations_in_ruleVersion1115 = new BitSet(new long[]{0x0000038080000000L});
    public static final BitSet FOLLOW_39_in_ruleVersion1130 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_ruleTranslations_in_ruleVersion1151 = new BitSet(new long[]{0x0000030080000000L});
    public static final BitSet FOLLOW_40_in_ruleVersion1166 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_ruleTranslations_in_ruleVersion1187 = new BitSet(new long[]{0x0000020080000000L});
    public static final BitSet FOLLOW_41_in_ruleVersion1202 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_ruleTranslations_in_ruleVersion1223 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_31_in_ruleVersion1237 = new BitSet(new long[]{0x6201040000000000L,0x0000000000000088L});
    public static final BitSet FOLLOW_42_in_ruleVersion1252 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_19_in_ruleVersion1264 = new BitSet(new long[]{0x0000880080000000L});
    public static final BitSet FOLLOW_43_in_ruleVersion1277 = new BitSet(new long[]{0x0000000000000060L,0xFFFF800000000000L,0x000000007FFFFFFFL});
    public static final BitSet FOLLOW_ruleVersionRef_in_ruleVersion1300 = new BitSet(new long[]{0x0000B00080000000L});
    public static final BitSet FOLLOW_44_in_ruleVersion1313 = new BitSet(new long[]{0x0000000000000000L,0x0004000000000000L,0x00000000000FF800L});
    public static final BitSet FOLLOW_ruleFunction_in_ruleVersion1334 = new BitSet(new long[]{0x0000A00080000000L});
    public static final BitSet FOLLOW_45_in_ruleVersion1349 = new BitSet(new long[]{0x0000400000000010L});
    public static final BitSet FOLLOW_46_in_ruleVersion1369 = new BitSet(new long[]{0x0000800080000000L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleVersion1397 = new BitSet(new long[]{0x0000800080000000L});
    public static final BitSet FOLLOW_47_in_ruleVersion1422 = new BitSet(new long[]{0x0000000000000060L,0xFFFF800000000000L,0x000000007FFFFFFFL});
    public static final BitSet FOLLOW_ruleVersionRef_in_ruleVersion1445 = new BitSet(new long[]{0x0000000081000000L});
    public static final BitSet FOLLOW_24_in_ruleVersion1458 = new BitSet(new long[]{0x0000000000000060L,0xFFFF800000000000L,0x000000007FFFFFFFL});
    public static final BitSet FOLLOW_ruleVersionRef_in_ruleVersion1481 = new BitSet(new long[]{0x0000000081000000L});
    public static final BitSet FOLLOW_31_in_ruleVersion1497 = new BitSet(new long[]{0x6201000000000000L,0x0000000000000088L});
    public static final BitSet FOLLOW_48_in_ruleVersion1512 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_19_in_ruleVersion1524 = new BitSet(new long[]{0x01FE000080000000L});
    public static final BitSet FOLLOW_49_in_ruleVersion1537 = new BitSet(new long[]{0x0000000000000000L,0x0003800000000000L});
    public static final BitSet FOLLOW_ruleYesNo_in_ruleVersion1558 = new BitSet(new long[]{0x01FC000080000000L});
    public static final BitSet FOLLOW_50_in_ruleVersion1573 = new BitSet(new long[]{0x0000000000000000L,0x0000180000000000L});
    public static final BitSet FOLLOW_ruleRoutine_in_ruleVersion1594 = new BitSet(new long[]{0x01F8000081000000L});
    public static final BitSet FOLLOW_24_in_ruleVersion1607 = new BitSet(new long[]{0x0000000000000000L,0x0000180000000000L});
    public static final BitSet FOLLOW_ruleRoutine_in_ruleVersion1628 = new BitSet(new long[]{0x01F8000081000000L});
    public static final BitSet FOLLOW_51_in_ruleVersion1645 = new BitSet(new long[]{0x0000000000000000L,0x0000180000000000L});
    public static final BitSet FOLLOW_ruleRoutine_in_ruleVersion1666 = new BitSet(new long[]{0x01F0000081000000L});
    public static final BitSet FOLLOW_24_in_ruleVersion1679 = new BitSet(new long[]{0x0000000000000000L,0x0000180000000000L});
    public static final BitSet FOLLOW_ruleRoutine_in_ruleVersion1700 = new BitSet(new long[]{0x01F0000081000000L});
    public static final BitSet FOLLOW_52_in_ruleVersion1717 = new BitSet(new long[]{0x0000000000000000L,0x0000180000000000L});
    public static final BitSet FOLLOW_ruleRoutine_in_ruleVersion1738 = new BitSet(new long[]{0x01E0000081000000L});
    public static final BitSet FOLLOW_24_in_ruleVersion1751 = new BitSet(new long[]{0x0000000000000000L,0x0000180000000000L});
    public static final BitSet FOLLOW_ruleRoutine_in_ruleVersion1772 = new BitSet(new long[]{0x01E0000081000000L});
    public static final BitSet FOLLOW_53_in_ruleVersion1789 = new BitSet(new long[]{0x0000000000000000L,0x0000180000000000L});
    public static final BitSet FOLLOW_ruleRoutine_in_ruleVersion1810 = new BitSet(new long[]{0x01C0000081000000L});
    public static final BitSet FOLLOW_24_in_ruleVersion1823 = new BitSet(new long[]{0x0000000000000000L,0x0000180000000000L});
    public static final BitSet FOLLOW_ruleRoutine_in_ruleVersion1844 = new BitSet(new long[]{0x01C0000081000000L});
    public static final BitSet FOLLOW_54_in_ruleVersion1861 = new BitSet(new long[]{0x0000000000000000L,0x0000180000000000L});
    public static final BitSet FOLLOW_ruleRoutine_in_ruleVersion1882 = new BitSet(new long[]{0x0180000081000000L});
    public static final BitSet FOLLOW_24_in_ruleVersion1895 = new BitSet(new long[]{0x0000000000000000L,0x0000180000000000L});
    public static final BitSet FOLLOW_ruleRoutine_in_ruleVersion1916 = new BitSet(new long[]{0x0180000081000000L});
    public static final BitSet FOLLOW_55_in_ruleVersion1933 = new BitSet(new long[]{0x0000000000000000L,0x0000180000000000L});
    public static final BitSet FOLLOW_ruleRoutine_in_ruleVersion1954 = new BitSet(new long[]{0x0100000081000000L});
    public static final BitSet FOLLOW_24_in_ruleVersion1967 = new BitSet(new long[]{0x0000000000000000L,0x0000180000000000L});
    public static final BitSet FOLLOW_ruleRoutine_in_ruleVersion1988 = new BitSet(new long[]{0x0100000081000000L});
    public static final BitSet FOLLOW_56_in_ruleVersion2005 = new BitSet(new long[]{0x0000000000000000L,0x0000180000000000L});
    public static final BitSet FOLLOW_ruleRoutine_in_ruleVersion2026 = new BitSet(new long[]{0x0000000081000000L});
    public static final BitSet FOLLOW_24_in_ruleVersion2039 = new BitSet(new long[]{0x0000000000000000L,0x0000180000000000L});
    public static final BitSet FOLLOW_ruleRoutine_in_ruleVersion2060 = new BitSet(new long[]{0x0000000081000000L});
    public static final BitSet FOLLOW_31_in_ruleVersion2076 = new BitSet(new long[]{0x6200000000000000L,0x0000000000000088L});
    public static final BitSet FOLLOW_57_in_ruleVersion2091 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_19_in_ruleVersion2103 = new BitSet(new long[]{0x1C00000080000000L});
    public static final BitSet FOLLOW_58_in_ruleVersion2116 = new BitSet(new long[]{0x0000000000000060L,0xFFFF800000000000L,0x000000007FFFFFFFL});
    public static final BitSet FOLLOW_ruleVersionRef_in_ruleVersion2139 = new BitSet(new long[]{0x1800000080000000L});
    public static final BitSet FOLLOW_59_in_ruleVersion2154 = new BitSet(new long[]{0x0000000000000060L,0xFFFF800000000000L,0x000000007FFFFFFFL});
    public static final BitSet FOLLOW_ruleVersionRef_in_ruleVersion2177 = new BitSet(new long[]{0x1000000080000000L});
    public static final BitSet FOLLOW_60_in_ruleVersion2192 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleVersion2209 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_31_in_ruleVersion2228 = new BitSet(new long[]{0x6000000000000000L,0x0000000000000088L});
    public static final BitSet FOLLOW_61_in_ruleVersion2243 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleVersion2260 = new BitSet(new long[]{0x4000000001000000L,0x0000000000000088L});
    public static final BitSet FOLLOW_24_in_ruleVersion2278 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleVersion2295 = new BitSet(new long[]{0x4000000001000000L,0x0000000000000088L});
    public static final BitSet FOLLOW_62_in_ruleVersion2317 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_19_in_ruleVersion2329 = new BitSet(new long[]{0x8000000080020000L,0x0000000000000007L});
    public static final BitSet FOLLOW_63_in_ruleVersion2342 = new BitSet(new long[]{0x0000000000000000L,0x0003800000000000L});
    public static final BitSet FOLLOW_ruleYesNo_in_ruleVersion2363 = new BitSet(new long[]{0x0000000080020000L,0x0000000000000007L});
    public static final BitSet FOLLOW_64_in_ruleVersion2378 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleVersion2395 = new BitSet(new long[]{0x0000000080020000L,0x0000000000000006L});
    public static final BitSet FOLLOW_65_in_ruleVersion2415 = new BitSet(new long[]{0x0000000000000000L,0x0004000000000000L,0x00000000000FF800L});
    public static final BitSet FOLLOW_ruleFunction_in_ruleVersion2436 = new BitSet(new long[]{0x0000000080020000L,0x0000000000000004L});
    public static final BitSet FOLLOW_17_in_ruleVersion2451 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleVersion2468 = new BitSet(new long[]{0x0000000080000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_ruleVersion2488 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleVersion2505 = new BitSet(new long[]{0x0000000081000000L});
    public static final BitSet FOLLOW_24_in_ruleVersion2523 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleVersion2540 = new BitSet(new long[]{0x0000000081000000L});
    public static final BitSet FOLLOW_31_in_ruleVersion2561 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000088L});
    public static final BitSet FOLLOW_67_in_ruleVersion2576 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_19_in_ruleVersion2588 = new BitSet(new long[]{0x0000000080000000L,0x0000000000000070L});
    public static final BitSet FOLLOW_68_in_ruleVersion2601 = new BitSet(new long[]{0x0000000000000000L,0x0003800000000000L});
    public static final BitSet FOLLOW_ruleYesNo_in_ruleVersion2622 = new BitSet(new long[]{0x0000000080000000L,0x0000000000000060L});
    public static final BitSet FOLLOW_69_in_ruleVersion2637 = new BitSet(new long[]{0x0000000000000000L,0x0004000000000000L,0x0000000001C00000L});
    public static final BitSet FOLLOW_ruleAssociatedVersionsPresentationPattern_in_ruleVersion2658 = new BitSet(new long[]{0x0000000080000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_70_in_ruleVersion2673 = new BitSet(new long[]{0x0000000000000000L,0x0004000000000000L,0x000000007E000000L});
    public static final BitSet FOLLOW_ruleFieldsLayoutPattern_in_ruleVersion2694 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_31_in_ruleVersion2708 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_71_in_ruleVersion2722 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_19_in_ruleVersion2734 = new BitSet(new long[]{0x0000000080000010L});
    public static final BitSet FOLLOW_ruleField_in_ruleVersion2755 = new BitSet(new long[]{0x0000000080000010L});
    public static final BitSet FOLLOW_31_in_ruleVersion2768 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleField_in_entryRuleField2804 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleField2814 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleField2856 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_19_in_ruleField2873 = new BitSet(new long[]{0x0001000180000000L,0x0000000E28000000L});
    public static final BitSet FOLLOW_32_in_ruleField2886 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_19_in_ruleField2898 = new BitSet(new long[]{0x0000000080000000L,0x0000000007FFFF00L});
    public static final BitSet FOLLOW_72_in_ruleField2911 = new BitSet(new long[]{0x0000000000000000L,0x7C04000000000000L});
    public static final BitSet FOLLOW_ruleDisplayType_in_ruleField2932 = new BitSet(new long[]{0x0000000080000000L,0x0000000007FFFE00L});
    public static final BitSet FOLLOW_73_in_ruleField2947 = new BitSet(new long[]{0x0000000000000000L,0x0004000000000000L,0x0000000000300000L});
    public static final BitSet FOLLOW_ruleInputBehaviour_in_ruleField2968 = new BitSet(new long[]{0x0000000080000000L,0x0000000007FFFC00L});
    public static final BitSet FOLLOW_74_in_ruleField2983 = new BitSet(new long[]{0x0000000000000000L,0x0384000000000000L});
    public static final BitSet FOLLOW_ruleCaseConvention_in_ruleField3004 = new BitSet(new long[]{0x0000000080000000L,0x0000000007FFF800L});
    public static final BitSet FOLLOW_75_in_ruleField3019 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_ruleINTEGER_OBJECT_in_ruleField3040 = new BitSet(new long[]{0x0000000080000000L,0x0000000007FFF000L});
    public static final BitSet FOLLOW_76_in_ruleField3055 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_ruleINTEGER_OBJECT_in_ruleField3076 = new BitSet(new long[]{0x0000000080000000L,0x0000000007FFE000L});
    public static final BitSet FOLLOW_77_in_ruleField3091 = new BitSet(new long[]{0x0000000000000000L,0x0006000000000000L});
    public static final BitSet FOLLOW_ruleExpansion_in_ruleField3112 = new BitSet(new long[]{0x0000000080000000L,0x0000000007FFC000L});
    public static final BitSet FOLLOW_78_in_ruleField3127 = new BitSet(new long[]{0x0000000000000000L,0x0003800000000000L});
    public static final BitSet FOLLOW_ruleYesNo_in_ruleField3148 = new BitSet(new long[]{0x0000000080000000L,0x0000000007FF8000L});
    public static final BitSet FOLLOW_79_in_ruleField3163 = new BitSet(new long[]{0x0000000000000000L,0x0003800000000000L});
    public static final BitSet FOLLOW_ruleYesNo_in_ruleField3184 = new BitSet(new long[]{0x0000000080000000L,0x0000000007FF0000L});
    public static final BitSet FOLLOW_80_in_ruleField3199 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_ruleINTEGER_OBJECT_in_ruleField3220 = new BitSet(new long[]{0x0000000080000000L,0x0000000007FE0000L});
    public static final BitSet FOLLOW_81_in_ruleField3235 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_ruleINTEGER_OBJECT_in_ruleField3256 = new BitSet(new long[]{0x0000000080000000L,0x0000000007FC0000L});
    public static final BitSet FOLLOW_82_in_ruleField3271 = new BitSet(new long[]{0x0000000000000000L,0x0003800000000000L});
    public static final BitSet FOLLOW_ruleYesNo_in_ruleField3292 = new BitSet(new long[]{0x0000000080000000L,0x0000000007F80000L});
    public static final BitSet FOLLOW_83_in_ruleField3307 = new BitSet(new long[]{0x0000000000000000L,0x0003800000000000L});
    public static final BitSet FOLLOW_ruleYesNo_in_ruleField3328 = new BitSet(new long[]{0x0000000080000000L,0x0000000007F00000L});
    public static final BitSet FOLLOW_84_in_ruleField3343 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleField3360 = new BitSet(new long[]{0x0000000080000000L,0x0000000007E00000L});
    public static final BitSet FOLLOW_85_in_ruleField3380 = new BitSet(new long[]{0x0000000000000000L,0x0003800000000000L});
    public static final BitSet FOLLOW_ruleYesNo_in_ruleField3401 = new BitSet(new long[]{0x0000000080000000L,0x0000000007C00000L});
    public static final BitSet FOLLOW_86_in_ruleField3416 = new BitSet(new long[]{0x0000000000000000L,0x0003800000000000L});
    public static final BitSet FOLLOW_ruleYesNo_in_ruleField3437 = new BitSet(new long[]{0x0000000080000000L,0x0000000007800000L});
    public static final BitSet FOLLOW_87_in_ruleField3452 = new BitSet(new long[]{0x0000000000000000L,0x0003800000000000L});
    public static final BitSet FOLLOW_ruleYesNo_in_ruleField3473 = new BitSet(new long[]{0x0000000080000000L,0x0000000007000000L});
    public static final BitSet FOLLOW_88_in_ruleField3488 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleField3505 = new BitSet(new long[]{0x0000000080000000L,0x0000000006000000L});
    public static final BitSet FOLLOW_89_in_ruleField3525 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleField3542 = new BitSet(new long[]{0x0000000080000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_90_in_ruleField3562 = new BitSet(new long[]{0x0000000000000000L,0x007C000000000000L});
    public static final BitSet FOLLOW_rulePopupBehaviour_in_ruleField3583 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_31_in_ruleField3597 = new BitSet(new long[]{0x0001000080000000L,0x0000000E28000000L});
    public static final BitSet FOLLOW_91_in_ruleField3612 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L});
    public static final BitSet FOLLOW_ruleDefault_in_ruleField3633 = new BitSet(new long[]{0x0001000080000000L,0x0000000E30000000L});
    public static final BitSet FOLLOW_92_in_ruleField3646 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L});
    public static final BitSet FOLLOW_ruleDefault_in_ruleField3667 = new BitSet(new long[]{0x0001000080000000L,0x0000000E30000000L});
    public static final BitSet FOLLOW_93_in_ruleField3684 = new BitSet(new long[]{0x0001000080000000L,0x0000000EC0000000L});
    public static final BitSet FOLLOW_94_in_ruleField3697 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_ruleTranslations_in_ruleField3718 = new BitSet(new long[]{0x0001000080000000L,0x0000000E80000000L});
    public static final BitSet FOLLOW_95_in_ruleField3733 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_ruleTranslations_in_ruleField3754 = new BitSet(new long[]{0x0001000080000000L,0x0000000E00000000L});
    public static final BitSet FOLLOW_48_in_ruleField3771 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_19_in_ruleField3783 = new BitSet(new long[]{0x0000000080000000L,0x0000000100000000L});
    public static final BitSet FOLLOW_96_in_ruleField3796 = new BitSet(new long[]{0x0000000000000000L,0x0000180000000000L});
    public static final BitSet FOLLOW_ruleRoutine_in_ruleField3817 = new BitSet(new long[]{0x0000000081000000L});
    public static final BitSet FOLLOW_24_in_ruleField3830 = new BitSet(new long[]{0x0000000000000000L,0x0000180000000000L});
    public static final BitSet FOLLOW_ruleRoutine_in_ruleField3851 = new BitSet(new long[]{0x0000000081000000L});
    public static final BitSet FOLLOW_31_in_ruleField3867 = new BitSet(new long[]{0x0000000080000000L,0x0000000E00000000L});
    public static final BitSet FOLLOW_97_in_ruleField3882 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleField3899 = new BitSet(new long[]{0x0000000081000000L,0x0000000C00000000L});
    public static final BitSet FOLLOW_24_in_ruleField3917 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleField3934 = new BitSet(new long[]{0x0000000081000000L,0x0000000C00000000L});
    public static final BitSet FOLLOW_98_in_ruleField3956 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_ruleINTEGER_OBJECT_in_ruleField3977 = new BitSet(new long[]{0x0000000080000000L,0x0000000800000000L});
    public static final BitSet FOLLOW_99_in_ruleField3992 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_ruleINTEGER_OBJECT_in_ruleField4013 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_31_in_ruleField4027 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleEntityRef_in_entryRuleEntityRef4064 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleEntityRef4075 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleNID_in_ruleEntityRef4122 = new BitSet(new long[]{0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_100_in_ruleEntityRef4140 = new BitSet(new long[]{0x0000000000000060L,0xFFFF800000000000L,0x000000007FFFFFFFL});
    public static final BitSet FOLLOW_ruleNID_in_ruleEntityRef4162 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleVersionRef_in_entryRuleVersionRef4208 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleVersionRef4219 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleNID_in_ruleVersionRef4266 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_ruleVersionRef4284 = new BitSet(new long[]{0x0000000000000062L,0xFFFF800000000000L,0x000000007FFFFFFFL});
    public static final BitSet FOLLOW_ruleNID_in_ruleVersionRef4307 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDefault_in_entryRuleDefault4354 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleDefault4364 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_101_in_ruleDefault4401 = new BitSet(new long[]{0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_102_in_ruleDefault4413 = new BitSet(new long[]{0x0000000000000040L,0x0000018000000000L});
    public static final BitSet FOLLOW_ruleINTEGER_OBJECT_in_ruleDefault4434 = new BitSet(new long[]{0x0000000000000000L,0x0000018000000000L});
    public static final BitSet FOLLOW_103_in_ruleDefault4448 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_ruleINTEGER_OBJECT_in_ruleDefault4469 = new BitSet(new long[]{0x0000000000000000L,0x0000010000000000L});
    public static final BitSet FOLLOW_104_in_ruleDefault4483 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleDefault4500 = new BitSet(new long[]{0x0000000000000000L,0x0000020000000000L});
    public static final BitSet FOLLOW_105_in_ruleDefault4517 = new BitSet(new long[]{0x0000000000000010L,0x0000040000000000L});
    public static final BitSet FOLLOW_ruleValueOrAtRoutine_in_ruleDefault4538 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleRoutine_in_entryRuleRoutine4574 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleRoutine4584 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleJBCRoutine_in_ruleRoutine4631 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleJavaRoutine_in_ruleRoutine4658 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAtRoutine_in_entryRuleAtRoutine4693 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleAtRoutine4703 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_106_in_ruleAtRoutine4740 = new BitSet(new long[]{0x0000000000000000L,0x0000180000000000L});
    public static final BitSet FOLLOW_ruleRoutine_in_ruleAtRoutine4762 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleValueOrAtRoutine_in_entryRuleValueOrAtRoutine4797 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleValueOrAtRoutine4807 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleValueOrAtRoutine4849 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleAtRoutine_in_ruleValueOrAtRoutine4881 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleJBCRoutine_in_entryRuleJBCRoutine4917 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleJBCRoutine4927 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_107_in_ruleJBCRoutine4964 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleJBCRoutine4981 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleJavaRoutine_in_entryRuleJavaRoutine5022 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleJavaRoutine5032 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_108_in_ruleJavaRoutine5069 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleJavaRoutine5086 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDealSlip_in_entryRuleDealSlip5127 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleDealSlip5137 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_109_in_ruleDealSlip5174 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleDealSlip5191 = new BitSet(new long[]{0x0000000000000000L,0x0000400000000000L});
    public static final BitSet FOLLOW_110_in_ruleDealSlip5208 = new BitSet(new long[]{0x0000000000000000L,0x8004000000000000L,0x000000000000003FL});
    public static final BitSet FOLLOW_ruleDealSlipFormatFunction_in_ruleDealSlip5229 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleNID_in_entryRuleNID5266 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleNID5277 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleNID5317 = new BitSet(new long[]{0x0000000000000062L,0xFFFF800000000000L,0x000000007FFFFFFFL});
    public static final BitSet FOLLOW_RULE_INT_in_ruleNID5343 = new BitSet(new long[]{0x0000000000000062L,0xFFFF800000000000L,0x000000007FFFFFFFL});
    public static final BitSet FOLLOW_111_in_ruleNID5367 = new BitSet(new long[]{0x0000000000000062L,0xFFFF800000000000L,0x000000007FFFFFFFL});
    public static final BitSet FOLLOW_112_in_ruleNID5386 = new BitSet(new long[]{0x0000000000000062L,0xFFFF800000000000L,0x000000007FFFFFFFL});
    public static final BitSet FOLLOW_113_in_ruleNID5405 = new BitSet(new long[]{0x0000000000000062L,0xFFFF800000000000L,0x000000007FFFFFFFL});
    public static final BitSet FOLLOW_114_in_ruleNID5424 = new BitSet(new long[]{0x0000000000000062L,0xFFFF800000000000L,0x000000007FFFFFFFL});
    public static final BitSet FOLLOW_115_in_ruleNID5443 = new BitSet(new long[]{0x0000000000000062L,0xFFFF800000000000L,0x000000007FFFFFFFL});
    public static final BitSet FOLLOW_116_in_ruleNID5462 = new BitSet(new long[]{0x0000000000000062L,0xFFFF800000000000L,0x000000007FFFFFFFL});
    public static final BitSet FOLLOW_117_in_ruleNID5481 = new BitSet(new long[]{0x0000000000000062L,0xFFFF800000000000L,0x000000007FFFFFFFL});
    public static final BitSet FOLLOW_118_in_ruleNID5500 = new BitSet(new long[]{0x0000000000000062L,0xFFFF800000000000L,0x000000007FFFFFFFL});
    public static final BitSet FOLLOW_119_in_ruleNID5519 = new BitSet(new long[]{0x0000000000000062L,0xFFFF800000000000L,0x000000007FFFFFFFL});
    public static final BitSet FOLLOW_120_in_ruleNID5538 = new BitSet(new long[]{0x0000000000000062L,0xFFFF800000000000L,0x000000007FFFFFFFL});
    public static final BitSet FOLLOW_121_in_ruleNID5557 = new BitSet(new long[]{0x0000000000000062L,0xFFFF800000000000L,0x000000007FFFFFFFL});
    public static final BitSet FOLLOW_122_in_ruleNID5576 = new BitSet(new long[]{0x0000000000000062L,0xFFFF800000000000L,0x000000007FFFFFFFL});
    public static final BitSet FOLLOW_123_in_ruleNID5595 = new BitSet(new long[]{0x0000000000000062L,0xFFFF800000000000L,0x000000007FFFFFFFL});
    public static final BitSet FOLLOW_124_in_ruleNID5614 = new BitSet(new long[]{0x0000000000000062L,0xFFFF800000000000L,0x000000007FFFFFFFL});
    public static final BitSet FOLLOW_125_in_ruleNID5633 = new BitSet(new long[]{0x0000000000000062L,0xFFFF800000000000L,0x000000007FFFFFFFL});
    public static final BitSet FOLLOW_126_in_ruleNID5652 = new BitSet(new long[]{0x0000000000000062L,0xFFFF800000000000L,0x000000007FFFFFFFL});
    public static final BitSet FOLLOW_127_in_ruleNID5671 = new BitSet(new long[]{0x0000000000000062L,0xFFFF800000000000L,0x000000007FFFFFFFL});
    public static final BitSet FOLLOW_128_in_ruleNID5690 = new BitSet(new long[]{0x0000000000000062L,0xFFFF800000000000L,0x000000007FFFFFFFL});
    public static final BitSet FOLLOW_129_in_ruleNID5709 = new BitSet(new long[]{0x0000000000000062L,0xFFFF800000000000L,0x000000007FFFFFFFL});
    public static final BitSet FOLLOW_130_in_ruleNID5728 = new BitSet(new long[]{0x0000000000000062L,0xFFFF800000000000L,0x000000007FFFFFFFL});
    public static final BitSet FOLLOW_131_in_ruleNID5747 = new BitSet(new long[]{0x0000000000000062L,0xFFFF800000000000L,0x000000007FFFFFFFL});
    public static final BitSet FOLLOW_132_in_ruleNID5766 = new BitSet(new long[]{0x0000000000000062L,0xFFFF800000000000L,0x000000007FFFFFFFL});
    public static final BitSet FOLLOW_133_in_ruleNID5785 = new BitSet(new long[]{0x0000000000000062L,0xFFFF800000000000L,0x000000007FFFFFFFL});
    public static final BitSet FOLLOW_134_in_ruleNID5804 = new BitSet(new long[]{0x0000000000000062L,0xFFFF800000000000L,0x000000007FFFFFFFL});
    public static final BitSet FOLLOW_135_in_ruleNID5823 = new BitSet(new long[]{0x0000000000000062L,0xFFFF800000000000L,0x000000007FFFFFFFL});
    public static final BitSet FOLLOW_136_in_ruleNID5842 = new BitSet(new long[]{0x0000000000000062L,0xFFFF800000000000L,0x000000007FFFFFFFL});
    public static final BitSet FOLLOW_137_in_ruleNID5861 = new BitSet(new long[]{0x0000000000000062L,0xFFFF800000000000L,0x000000007FFFFFFFL});
    public static final BitSet FOLLOW_138_in_ruleNID5880 = new BitSet(new long[]{0x0000000000000062L,0xFFFF800000000000L,0x000000007FFFFFFFL});
    public static final BitSet FOLLOW_139_in_ruleNID5899 = new BitSet(new long[]{0x0000000000000062L,0xFFFF800000000000L,0x000000007FFFFFFFL});
    public static final BitSet FOLLOW_140_in_ruleNID5918 = new BitSet(new long[]{0x0000000000000062L,0xFFFF800000000000L,0x000000007FFFFFFFL});
    public static final BitSet FOLLOW_141_in_ruleNID5937 = new BitSet(new long[]{0x0000000000000062L,0xFFFF800000000000L,0x000000007FFFFFFFL});
    public static final BitSet FOLLOW_142_in_ruleNID5956 = new BitSet(new long[]{0x0000000000000062L,0xFFFF800000000000L,0x000000007FFFFFFFL});
    public static final BitSet FOLLOW_143_in_ruleNID5975 = new BitSet(new long[]{0x0000000000000062L,0xFFFF800000000000L,0x000000007FFFFFFFL});
    public static final BitSet FOLLOW_144_in_ruleNID5994 = new BitSet(new long[]{0x0000000000000062L,0xFFFF800000000000L,0x000000007FFFFFFFL});
    public static final BitSet FOLLOW_145_in_ruleNID6013 = new BitSet(new long[]{0x0000000000000062L,0xFFFF800000000000L,0x000000007FFFFFFFL});
    public static final BitSet FOLLOW_146_in_ruleNID6032 = new BitSet(new long[]{0x0000000000000062L,0xFFFF800000000000L,0x000000007FFFFFFFL});
    public static final BitSet FOLLOW_147_in_ruleNID6051 = new BitSet(new long[]{0x0000000000000062L,0xFFFF800000000000L,0x000000007FFFFFFFL});
    public static final BitSet FOLLOW_148_in_ruleNID6070 = new BitSet(new long[]{0x0000000000000062L,0xFFFF800000000000L,0x000000007FFFFFFFL});
    public static final BitSet FOLLOW_149_in_ruleNID6089 = new BitSet(new long[]{0x0000000000000062L,0xFFFF800000000000L,0x000000007FFFFFFFL});
    public static final BitSet FOLLOW_150_in_ruleNID6108 = new BitSet(new long[]{0x0000000000000062L,0xFFFF800000000000L,0x000000007FFFFFFFL});
    public static final BitSet FOLLOW_151_in_ruleNID6127 = new BitSet(new long[]{0x0000000000000062L,0xFFFF800000000000L,0x000000007FFFFFFFL});
    public static final BitSet FOLLOW_152_in_ruleNID6146 = new BitSet(new long[]{0x0000000000000062L,0xFFFF800000000000L,0x000000007FFFFFFFL});
    public static final BitSet FOLLOW_153_in_ruleNID6165 = new BitSet(new long[]{0x0000000000000062L,0xFFFF800000000000L,0x000000007FFFFFFFL});
    public static final BitSet FOLLOW_154_in_ruleNID6184 = new BitSet(new long[]{0x0000000000000062L,0xFFFF800000000000L,0x000000007FFFFFFFL});
    public static final BitSet FOLLOW_155_in_ruleNID6203 = new BitSet(new long[]{0x0000000000000062L,0xFFFF800000000000L,0x000000007FFFFFFFL});
    public static final BitSet FOLLOW_156_in_ruleNID6222 = new BitSet(new long[]{0x0000000000000062L,0xFFFF800000000000L,0x000000007FFFFFFFL});
    public static final BitSet FOLLOW_157_in_ruleNID6241 = new BitSet(new long[]{0x0000000000000062L,0xFFFF800000000000L,0x000000007FFFFFFFL});
    public static final BitSet FOLLOW_158_in_ruleNID6260 = new BitSet(new long[]{0x0000000000000062L,0xFFFF800000000000L,0x000000007FFFFFFFL});
    public static final BitSet FOLLOW_ruleINTEGER_OBJECT_in_entryRuleINTEGER_OBJECT6302 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleINTEGER_OBJECT6313 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_INT_in_ruleINTEGER_OBJECT6352 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLocalTranslations_in_entryRuleLocalTranslations6396 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLocalTranslations6406 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLocalTranslation_in_ruleLocalTranslations6452 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_12_in_ruleLocalTranslations6465 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_ruleLocalTranslation_in_ruleLocalTranslations6486 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_ruleLocalTranslation_in_entryRuleLocalTranslation6524 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleLocalTranslation6534 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleLocalTranslation6576 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_159_in_ruleLocalTranslation6593 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleLocalTranslation6610 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleTranslations_in_entryRuleTranslations6651 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleTranslations6661 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleLocalTranslations_in_ruleTranslations6707 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_111_in_ruleYesNo6755 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_112_in_ruleYesNo6772 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_113_in_ruleYesNo6789 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_114_in_rulePopupBehaviour6834 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_115_in_rulePopupBehaviour6851 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_116_in_rulePopupBehaviour6868 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_117_in_rulePopupBehaviour6885 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_118_in_rulePopupBehaviour6902 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_114_in_ruleCaseConvention6947 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_119_in_ruleCaseConvention6964 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_120_in_ruleCaseConvention6981 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_121_in_ruleCaseConvention6998 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_114_in_ruleDisplayType7043 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_122_in_ruleDisplayType7060 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_123_in_ruleDisplayType7077 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_124_in_ruleDisplayType7094 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_125_in_ruleDisplayType7111 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_126_in_ruleDisplayType7128 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_114_in_ruleDealSlipFormatFunction7173 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_127_in_ruleDealSlipFormatFunction7190 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_128_in_ruleDealSlipFormatFunction7207 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_129_in_ruleDealSlipFormatFunction7224 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_130_in_ruleDealSlipFormatFunction7241 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_131_in_ruleDealSlipFormatFunction7258 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_132_in_ruleDealSlipFormatFunction7275 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_133_in_ruleDealSlipFormatFunction7292 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_114_in_ruleDealSlipTrigger7337 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_134_in_ruleDealSlipTrigger7354 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_135_in_ruleDealSlipTrigger7371 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_114_in_ruleBusinessDayControl7416 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_136_in_ruleBusinessDayControl7433 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_137_in_ruleBusinessDayControl7450 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_138_in_ruleBusinessDayControl7467 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_114_in_ruleFunction7512 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_139_in_ruleFunction7529 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_140_in_ruleFunction7546 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_141_in_ruleFunction7563 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_142_in_ruleFunction7580 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_143_in_ruleFunction7597 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_144_in_ruleFunction7614 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_145_in_ruleFunction7631 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_146_in_ruleFunction7648 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_147_in_ruleFunction7665 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_114_in_ruleInputBehaviour7710 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_148_in_ruleInputBehaviour7727 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_149_in_ruleInputBehaviour7744 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_114_in_ruleExpansion7789 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_113_in_ruleExpansion7806 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_114_in_ruleAssociatedVersionsPresentationPattern7851 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_150_in_ruleAssociatedVersionsPresentationPattern7868 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_151_in_ruleAssociatedVersionsPresentationPattern7885 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_152_in_ruleAssociatedVersionsPresentationPattern7902 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_114_in_ruleFieldsLayoutPattern7947 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_153_in_ruleFieldsLayoutPattern7964 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_154_in_ruleFieldsLayoutPattern7981 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_155_in_ruleFieldsLayoutPattern7998 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_156_in_ruleFieldsLayoutPattern8015 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_157_in_ruleFieldsLayoutPattern8032 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_158_in_ruleFieldsLayoutPattern8049 = new BitSet(new long[]{0x0000000000000002L});

}
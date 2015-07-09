package com.odcgroup.workbench.el.parser.antlr.internal;

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import com.odcgroup.workbench.el.services.DSELGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
@SuppressWarnings("all")
public class InternalDSELParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_INT", "RULE_EINT", "RULE_STRING", "RULE_ID", "RULE_COMMENT", "RULE_HEX", "RULE_DECIMAL", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "':='", "'=='", "'='", "'!='", "'<>'", "'>='", "'<='", "'>'", "'<'", "'!<'", "'!>'", "'!'", "'-'", "'+'", "'NOT'", "'||'", "'OR'", "'&&'", "'AND'", "'..'", "'null'", "'NULL'", "'false'", "'FALSE'", "'true'", "'TRUE'", "'.'", "':'", "'+='", "'-='", "'*='", "'/='", "'%='", "'*'", "'**'", "'/'", "'%'", "'as'", "'++'", "'--'", "'::'", "'?.'", "','", "'('", "')'", "'#'", "'{'", "'}'", "'['", "']'", "'|'", "';'", "'if'", "'else'", "'var'", "'val'", "'extends'", "'static'", "'import'", "'extension'", "'super'", "'catch'", "'=>'", "'?'", "'&'"
    };
    public static final int T__68=68;
    public static final int T__69=69;
    public static final int RULE_ID=7;
    public static final int T__66=66;
    public static final int T__67=67;
    public static final int T__64=64;
    public static final int T__29=29;
    public static final int T__65=65;
    public static final int T__28=28;
    public static final int T__62=62;
    public static final int T__27=27;
    public static final int T__63=63;
    public static final int T__26=26;
    public static final int T__25=25;
    public static final int T__24=24;
    public static final int T__23=23;
    public static final int T__22=22;
    public static final int RULE_ANY_OTHER=14;
    public static final int T__21=21;
    public static final int T__20=20;
    public static final int T__61=61;
    public static final int T__60=60;
    public static final int EOF=-1;
    public static final int T__55=55;
    public static final int T__56=56;
    public static final int T__19=19;
    public static final int T__57=57;
    public static final int RULE_HEX=9;
    public static final int T__58=58;
    public static final int T__16=16;
    public static final int T__51=51;
    public static final int T__15=15;
    public static final int T__52=52;
    public static final int T__18=18;
    public static final int T__53=53;
    public static final int T__17=17;
    public static final int T__54=54;
    public static final int T__59=59;
    public static final int RULE_INT=4;
    public static final int RULE_DECIMAL=10;
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
    public static final int RULE_COMMENT=8;
    public static final int RULE_SL_COMMENT=12;
    public static final int RULE_ML_COMMENT=11;
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int RULE_STRING=6;
    public static final int T__32=32;
    public static final int T__71=71;
    public static final int T__33=33;
    public static final int T__72=72;
    public static final int T__34=34;
    public static final int T__35=35;
    public static final int T__70=70;
    public static final int T__36=36;
    public static final int T__37=37;
    public static final int T__38=38;
    public static final int T__39=39;
    public static final int RULE_EINT=5;
    public static final int RULE_WS=13;
    public static final int T__76=76;
    public static final int T__75=75;
    public static final int T__74=74;
    public static final int T__73=73;
    public static final int T__79=79;
    public static final int T__78=78;
    public static final int T__77=77;

    // delegates
    // delegators


        public InternalDSELParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalDSELParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);

        }


    public String[] getTokenNames() { return InternalDSELParser.tokenNames; }
    public String getGrammarFileName() { return "../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g"; }



	private DSELGrammarAccess grammarAccess;

        public InternalDSELParser(TokenStream input, DSELGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }

        @Override
        protected String getFirstRuleName() {
		return "XExpression";
	}

	@Override
	protected DSELGrammarAccess getGrammarAccess() {
		return grammarAccess;
	}



    // $ANTLR start "entryRuleXExpression"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:67:1: entryRuleXExpression returns [EObject current=null] : iv_ruleXExpression= ruleXExpression EOF ;
    public final EObject entryRuleXExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXExpression = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:68:2: (iv_ruleXExpression= ruleXExpression EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:69:2: iv_ruleXExpression= ruleXExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXExpressionRule());
            }
            pushFollow(FOLLOW_ruleXExpression_in_entryRuleXExpression75);
            iv_ruleXExpression=ruleXExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXExpression;
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleXExpression85); if (state.failed) return current;

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
    // $ANTLR end "entryRuleXExpression"


    // $ANTLR start "ruleXExpression"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:76:1: ruleXExpression returns [EObject current=null] : this_XOrExpression_0= ruleXOrExpression ;
    public final EObject ruleXExpression() throws RecognitionException {
        EObject current = null;

        EObject this_XOrExpression_0 = null;


         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:79:28: (this_XOrExpression_0= ruleXOrExpression )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:81:5: this_XOrExpression_0= ruleXOrExpression
            {
            if ( state.backtracking==0 ) {

                      newCompositeNode(grammarAccess.getXExpressionAccess().getXOrExpressionParserRuleCall());

            }
            pushFollow(FOLLOW_ruleXOrExpression_in_ruleXExpression131);
            this_XOrExpression_0=ruleXOrExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current = this_XOrExpression_0;
                      afterParserOrEnumRuleCall();

            }

            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleXExpression"


    // $ANTLR start "entryRuleXLiteral"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:97:1: entryRuleXLiteral returns [EObject current=null] : iv_ruleXLiteral= ruleXLiteral EOF ;
    public final EObject entryRuleXLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXLiteral = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:98:2: (iv_ruleXLiteral= ruleXLiteral EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:99:2: iv_ruleXLiteral= ruleXLiteral EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXLiteralRule());
            }
            pushFollow(FOLLOW_ruleXLiteral_in_entryRuleXLiteral165);
            iv_ruleXLiteral=ruleXLiteral();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXLiteral;
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleXLiteral175); if (state.failed) return current;

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
    // $ANTLR end "entryRuleXLiteral"


    // $ANTLR start "ruleXLiteral"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:106:1: ruleXLiteral returns [EObject current=null] : (this_XBooleanLiteral_0= ruleXBooleanLiteral | this_XNullLiteral_1= ruleXNullLiteral | this_XStringLiteral_2= ruleXStringLiteral | this_DecimalLiteral_3= ruleDecimalLiteral | this_DateLiteral_4= ruleDateLiteral | this_DateTimeLiteral_5= ruleDateTimeLiteral ) ;
    public final EObject ruleXLiteral() throws RecognitionException {
        EObject current = null;

        EObject this_XBooleanLiteral_0 = null;

        EObject this_XNullLiteral_1 = null;

        EObject this_XStringLiteral_2 = null;

        EObject this_DecimalLiteral_3 = null;

        EObject this_DateLiteral_4 = null;

        EObject this_DateTimeLiteral_5 = null;


         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:109:28: ( (this_XBooleanLiteral_0= ruleXBooleanLiteral | this_XNullLiteral_1= ruleXNullLiteral | this_XStringLiteral_2= ruleXStringLiteral | this_DecimalLiteral_3= ruleDecimalLiteral | this_DateLiteral_4= ruleDateLiteral | this_DateTimeLiteral_5= ruleDateTimeLiteral ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:110:1: (this_XBooleanLiteral_0= ruleXBooleanLiteral | this_XNullLiteral_1= ruleXNullLiteral | this_XStringLiteral_2= ruleXStringLiteral | this_DecimalLiteral_3= ruleDecimalLiteral | this_DateLiteral_4= ruleDateLiteral | this_DateTimeLiteral_5= ruleDateTimeLiteral )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:110:1: (this_XBooleanLiteral_0= ruleXBooleanLiteral | this_XNullLiteral_1= ruleXNullLiteral | this_XStringLiteral_2= ruleXStringLiteral | this_DecimalLiteral_3= ruleDecimalLiteral | this_DateLiteral_4= ruleDateLiteral | this_DateTimeLiteral_5= ruleDateTimeLiteral )
            int alt1=6;
            alt1 = dfa1.predict(input);
            switch (alt1) {
                case 1 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:111:5: this_XBooleanLiteral_0= ruleXBooleanLiteral
                    {
                    if ( state.backtracking==0 ) {

                              newCompositeNode(grammarAccess.getXLiteralAccess().getXBooleanLiteralParserRuleCall_0());

                    }
                    pushFollow(FOLLOW_ruleXBooleanLiteral_in_ruleXLiteral222);
                    this_XBooleanLiteral_0=ruleXBooleanLiteral();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = this_XBooleanLiteral_0;
                              afterParserOrEnumRuleCall();

                    }

                    }
                    break;
                case 2 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:121:5: this_XNullLiteral_1= ruleXNullLiteral
                    {
                    if ( state.backtracking==0 ) {

                              newCompositeNode(grammarAccess.getXLiteralAccess().getXNullLiteralParserRuleCall_1());

                    }
                    pushFollow(FOLLOW_ruleXNullLiteral_in_ruleXLiteral249);
                    this_XNullLiteral_1=ruleXNullLiteral();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = this_XNullLiteral_1;
                              afterParserOrEnumRuleCall();

                    }

                    }
                    break;
                case 3 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:131:5: this_XStringLiteral_2= ruleXStringLiteral
                    {
                    if ( state.backtracking==0 ) {

                              newCompositeNode(grammarAccess.getXLiteralAccess().getXStringLiteralParserRuleCall_2());

                    }
                    pushFollow(FOLLOW_ruleXStringLiteral_in_ruleXLiteral276);
                    this_XStringLiteral_2=ruleXStringLiteral();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = this_XStringLiteral_2;
                              afterParserOrEnumRuleCall();

                    }

                    }
                    break;
                case 4 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:141:5: this_DecimalLiteral_3= ruleDecimalLiteral
                    {
                    if ( state.backtracking==0 ) {

                              newCompositeNode(grammarAccess.getXLiteralAccess().getDecimalLiteralParserRuleCall_3());

                    }
                    pushFollow(FOLLOW_ruleDecimalLiteral_in_ruleXLiteral303);
                    this_DecimalLiteral_3=ruleDecimalLiteral();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = this_DecimalLiteral_3;
                              afterParserOrEnumRuleCall();

                    }

                    }
                    break;
                case 5 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:151:5: this_DateLiteral_4= ruleDateLiteral
                    {
                    if ( state.backtracking==0 ) {

                              newCompositeNode(grammarAccess.getXLiteralAccess().getDateLiteralParserRuleCall_4());

                    }
                    pushFollow(FOLLOW_ruleDateLiteral_in_ruleXLiteral330);
                    this_DateLiteral_4=ruleDateLiteral();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = this_DateLiteral_4;
                              afterParserOrEnumRuleCall();

                    }

                    }
                    break;
                case 6 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:161:5: this_DateTimeLiteral_5= ruleDateTimeLiteral
                    {
                    if ( state.backtracking==0 ) {

                              newCompositeNode(grammarAccess.getXLiteralAccess().getDateTimeLiteralParserRuleCall_5());

                    }
                    pushFollow(FOLLOW_ruleDateTimeLiteral_in_ruleXLiteral357);
                    this_DateTimeLiteral_5=ruleDateTimeLiteral();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = this_DateTimeLiteral_5;
                              afterParserOrEnumRuleCall();

                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleXLiteral"


    // $ANTLR start "entryRuleXUnaryOperation"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:177:1: entryRuleXUnaryOperation returns [EObject current=null] : iv_ruleXUnaryOperation= ruleXUnaryOperation EOF ;
    public final EObject entryRuleXUnaryOperation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXUnaryOperation = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:178:2: (iv_ruleXUnaryOperation= ruleXUnaryOperation EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:179:2: iv_ruleXUnaryOperation= ruleXUnaryOperation EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXUnaryOperationRule());
            }
            pushFollow(FOLLOW_ruleXUnaryOperation_in_entryRuleXUnaryOperation392);
            iv_ruleXUnaryOperation=ruleXUnaryOperation();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXUnaryOperation;
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleXUnaryOperation402); if (state.failed) return current;

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
    // $ANTLR end "entryRuleXUnaryOperation"


    // $ANTLR start "ruleXUnaryOperation"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:186:1: ruleXUnaryOperation returns [EObject current=null] : ( ( () ( ( ruleOpUnary ) ) ( (lv_operand_2_0= ruleXUnaryOperation ) ) ) | this_XCastedExpression_3= ruleXCastedExpression ) ;
    public final EObject ruleXUnaryOperation() throws RecognitionException {
        EObject current = null;

        EObject lv_operand_2_0 = null;

        EObject this_XCastedExpression_3 = null;


         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:189:28: ( ( ( () ( ( ruleOpUnary ) ) ( (lv_operand_2_0= ruleXUnaryOperation ) ) ) | this_XCastedExpression_3= ruleXCastedExpression ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:190:1: ( ( () ( ( ruleOpUnary ) ) ( (lv_operand_2_0= ruleXUnaryOperation ) ) ) | this_XCastedExpression_3= ruleXCastedExpression )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:190:1: ( ( () ( ( ruleOpUnary ) ) ( (lv_operand_2_0= ruleXUnaryOperation ) ) ) | this_XCastedExpression_3= ruleXCastedExpression )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( ((LA2_0>=26 && LA2_0<=29)) ) {
                alt2=1;
            }
            else if ( ((LA2_0>=RULE_INT && LA2_0<=RULE_ID)||LA2_0==23||(LA2_0>=35 && LA2_0<=40)||LA2_0==58||LA2_0==67||(LA2_0>=71 && LA2_0<=75)) ) {
                alt2=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:190:2: ( () ( ( ruleOpUnary ) ) ( (lv_operand_2_0= ruleXUnaryOperation ) ) )
                    {
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:190:2: ( () ( ( ruleOpUnary ) ) ( (lv_operand_2_0= ruleXUnaryOperation ) ) )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:190:3: () ( ( ruleOpUnary ) ) ( (lv_operand_2_0= ruleXUnaryOperation ) )
                    {
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:190:3: ()
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:191:5:
                    {
                    if ( state.backtracking==0 ) {

                              current = forceCreateModelElement(
                                  grammarAccess.getXUnaryOperationAccess().getXUnaryOperationAction_0_0(),
                                  current);

                    }

                    }

                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:196:2: ( ( ruleOpUnary ) )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:197:1: ( ruleOpUnary )
                    {
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:197:1: ( ruleOpUnary )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:198:3: ruleOpUnary
                    {
                    if ( state.backtracking==0 ) {

					if (current==null) {
			            current = createModelElement(grammarAccess.getXUnaryOperationRule());
			        }

                    }
                    if ( state.backtracking==0 ) {

			        newCompositeNode(grammarAccess.getXUnaryOperationAccess().getFeatureJvmIdentifiableElementCrossReference_0_1_0());

                    }
                    pushFollow(FOLLOW_ruleOpUnary_in_ruleXUnaryOperation460);
                    ruleOpUnary();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

			        afterParserOrEnumRuleCall();

                    }

                    }


                    }

                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:211:2: ( (lv_operand_2_0= ruleXUnaryOperation ) )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:212:1: (lv_operand_2_0= ruleXUnaryOperation )
                    {
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:212:1: (lv_operand_2_0= ruleXUnaryOperation )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:213:3: lv_operand_2_0= ruleXUnaryOperation
                    {
                    if ( state.backtracking==0 ) {

			        newCompositeNode(grammarAccess.getXUnaryOperationAccess().getOperandXUnaryOperationParserRuleCall_0_2_0());

                    }
                    pushFollow(FOLLOW_ruleXUnaryOperation_in_ruleXUnaryOperation481);
                    lv_operand_2_0=ruleXUnaryOperation();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

			        if (current==null) {
			            current = createModelElementForParent(grammarAccess.getXUnaryOperationRule());
			        }
					set(
						current,
						"operand",
					lv_operand_2_0,
					"XUnaryOperation");
			        afterParserOrEnumRuleCall();

                    }

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:231:5: this_XCastedExpression_3= ruleXCastedExpression
                    {
                    if ( state.backtracking==0 ) {

                              newCompositeNode(grammarAccess.getXUnaryOperationAccess().getXCastedExpressionParserRuleCall_1());

                    }
                    pushFollow(FOLLOW_ruleXCastedExpression_in_ruleXUnaryOperation510);
                    this_XCastedExpression_3=ruleXCastedExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = this_XCastedExpression_3;
                              afterParserOrEnumRuleCall();

                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleXUnaryOperation"


    // $ANTLR start "entryRuleOpSingleAssign"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:251:1: entryRuleOpSingleAssign returns [String current=null] : iv_ruleOpSingleAssign= ruleOpSingleAssign EOF ;
    public final String entryRuleOpSingleAssign() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleOpSingleAssign = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:252:2: (iv_ruleOpSingleAssign= ruleOpSingleAssign EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:253:2: iv_ruleOpSingleAssign= ruleOpSingleAssign EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getOpSingleAssignRule());
            }
            pushFollow(FOLLOW_ruleOpSingleAssign_in_entryRuleOpSingleAssign550);
            iv_ruleOpSingleAssign=ruleOpSingleAssign();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleOpSingleAssign.getText();
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleOpSingleAssign561); if (state.failed) return current;

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
    // $ANTLR end "entryRuleOpSingleAssign"


    // $ANTLR start "ruleOpSingleAssign"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:260:1: ruleOpSingleAssign returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : kw= ':=' ;
    public final AntlrDatatypeRuleToken ruleOpSingleAssign() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;

         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:263:28: (kw= ':=' )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:265:2: kw= ':='
            {
            kw=(Token)match(input,15,FOLLOW_15_in_ruleOpSingleAssign598); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current.merge(kw);
                      newLeafNode(kw, grammarAccess.getOpSingleAssignAccess().getColonEqualsSignKeyword());

            }

            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleOpSingleAssign"


    // $ANTLR start "entryRuleOpEquality"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:278:1: entryRuleOpEquality returns [String current=null] : iv_ruleOpEquality= ruleOpEquality EOF ;
    public final String entryRuleOpEquality() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleOpEquality = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:279:2: (iv_ruleOpEquality= ruleOpEquality EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:280:2: iv_ruleOpEquality= ruleOpEquality EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getOpEqualityRule());
            }
            pushFollow(FOLLOW_ruleOpEquality_in_entryRuleOpEquality638);
            iv_ruleOpEquality=ruleOpEquality();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleOpEquality.getText();
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleOpEquality649); if (state.failed) return current;

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
    // $ANTLR end "entryRuleOpEquality"


    // $ANTLR start "ruleOpEquality"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:287:1: ruleOpEquality returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '==' | kw= '=' | kw= '!=' | kw= '<>' ) ;
    public final AntlrDatatypeRuleToken ruleOpEquality() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;

         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:290:28: ( (kw= '==' | kw= '=' | kw= '!=' | kw= '<>' ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:291:1: (kw= '==' | kw= '=' | kw= '!=' | kw= '<>' )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:291:1: (kw= '==' | kw= '=' | kw= '!=' | kw= '<>' )
            int alt3=4;
            switch ( input.LA(1) ) {
            case 16:
                {
                alt3=1;
                }
                break;
            case 17:
                {
                alt3=2;
                }
                break;
            case 18:
                {
                alt3=3;
                }
                break;
            case 19:
                {
                alt3=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }

            switch (alt3) {
                case 1 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:292:2: kw= '=='
                    {
                    kw=(Token)match(input,16,FOLLOW_16_in_ruleOpEquality687); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current.merge(kw);
                              newLeafNode(kw, grammarAccess.getOpEqualityAccess().getEqualsSignEqualsSignKeyword_0());

                    }

                    }
                    break;
                case 2 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:299:2: kw= '='
                    {
                    kw=(Token)match(input,17,FOLLOW_17_in_ruleOpEquality706); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current.merge(kw);
                              newLeafNode(kw, grammarAccess.getOpEqualityAccess().getEqualsSignKeyword_1());

                    }

                    }
                    break;
                case 3 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:306:2: kw= '!='
                    {
                    kw=(Token)match(input,18,FOLLOW_18_in_ruleOpEquality725); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current.merge(kw);
                              newLeafNode(kw, grammarAccess.getOpEqualityAccess().getExclamationMarkEqualsSignKeyword_2());

                    }

                    }
                    break;
                case 4 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:313:2: kw= '<>'
                    {
                    kw=(Token)match(input,19,FOLLOW_19_in_ruleOpEquality744); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current.merge(kw);
                              newLeafNode(kw, grammarAccess.getOpEqualityAccess().getLessThanSignGreaterThanSignKeyword_3());

                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleOpEquality"


    // $ANTLR start "entryRuleOpCompare"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:326:1: entryRuleOpCompare returns [String current=null] : iv_ruleOpCompare= ruleOpCompare EOF ;
    public final String entryRuleOpCompare() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleOpCompare = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:327:2: (iv_ruleOpCompare= ruleOpCompare EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:328:2: iv_ruleOpCompare= ruleOpCompare EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getOpCompareRule());
            }
            pushFollow(FOLLOW_ruleOpCompare_in_entryRuleOpCompare785);
            iv_ruleOpCompare=ruleOpCompare();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleOpCompare.getText();
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleOpCompare796); if (state.failed) return current;

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
    // $ANTLR end "entryRuleOpCompare"


    // $ANTLR start "ruleOpCompare"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:335:1: ruleOpCompare returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '>=' | kw= '<=' | kw= '>' | kw= '<' | kw= '!<' | kw= '!>' ) ;
    public final AntlrDatatypeRuleToken ruleOpCompare() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;

         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:338:28: ( (kw= '>=' | kw= '<=' | kw= '>' | kw= '<' | kw= '!<' | kw= '!>' ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:339:1: (kw= '>=' | kw= '<=' | kw= '>' | kw= '<' | kw= '!<' | kw= '!>' )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:339:1: (kw= '>=' | kw= '<=' | kw= '>' | kw= '<' | kw= '!<' | kw= '!>' )
            int alt4=6;
            switch ( input.LA(1) ) {
            case 20:
                {
                alt4=1;
                }
                break;
            case 21:
                {
                alt4=2;
                }
                break;
            case 22:
                {
                alt4=3;
                }
                break;
            case 23:
                {
                alt4=4;
                }
                break;
            case 24:
                {
                alt4=5;
                }
                break;
            case 25:
                {
                alt4=6;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }

            switch (alt4) {
                case 1 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:340:2: kw= '>='
                    {
                    kw=(Token)match(input,20,FOLLOW_20_in_ruleOpCompare834); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current.merge(kw);
                              newLeafNode(kw, grammarAccess.getOpCompareAccess().getGreaterThanSignEqualsSignKeyword_0());

                    }

                    }
                    break;
                case 2 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:347:2: kw= '<='
                    {
                    kw=(Token)match(input,21,FOLLOW_21_in_ruleOpCompare853); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current.merge(kw);
                              newLeafNode(kw, grammarAccess.getOpCompareAccess().getLessThanSignEqualsSignKeyword_1());

                    }

                    }
                    break;
                case 3 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:354:2: kw= '>'
                    {
                    kw=(Token)match(input,22,FOLLOW_22_in_ruleOpCompare872); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current.merge(kw);
                              newLeafNode(kw, grammarAccess.getOpCompareAccess().getGreaterThanSignKeyword_2());

                    }

                    }
                    break;
                case 4 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:361:2: kw= '<'
                    {
                    kw=(Token)match(input,23,FOLLOW_23_in_ruleOpCompare891); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current.merge(kw);
                              newLeafNode(kw, grammarAccess.getOpCompareAccess().getLessThanSignKeyword_3());

                    }

                    }
                    break;
                case 5 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:368:2: kw= '!<'
                    {
                    kw=(Token)match(input,24,FOLLOW_24_in_ruleOpCompare910); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current.merge(kw);
                              newLeafNode(kw, grammarAccess.getOpCompareAccess().getExclamationMarkLessThanSignKeyword_4());

                    }

                    }
                    break;
                case 6 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:375:2: kw= '!>'
                    {
                    kw=(Token)match(input,25,FOLLOW_25_in_ruleOpCompare929); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current.merge(kw);
                              newLeafNode(kw, grammarAccess.getOpCompareAccess().getExclamationMarkGreaterThanSignKeyword_5());

                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleOpCompare"


    // $ANTLR start "entryRuleOpUnary"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:388:1: entryRuleOpUnary returns [String current=null] : iv_ruleOpUnary= ruleOpUnary EOF ;
    public final String entryRuleOpUnary() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleOpUnary = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:389:2: (iv_ruleOpUnary= ruleOpUnary EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:390:2: iv_ruleOpUnary= ruleOpUnary EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getOpUnaryRule());
            }
            pushFollow(FOLLOW_ruleOpUnary_in_entryRuleOpUnary970);
            iv_ruleOpUnary=ruleOpUnary();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleOpUnary.getText();
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleOpUnary981); if (state.failed) return current;

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
    // $ANTLR end "entryRuleOpUnary"


    // $ANTLR start "ruleOpUnary"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:397:1: ruleOpUnary returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '!' | kw= '-' | kw= '+' | kw= 'NOT' ) ;
    public final AntlrDatatypeRuleToken ruleOpUnary() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;

         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:400:28: ( (kw= '!' | kw= '-' | kw= '+' | kw= 'NOT' ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:401:1: (kw= '!' | kw= '-' | kw= '+' | kw= 'NOT' )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:401:1: (kw= '!' | kw= '-' | kw= '+' | kw= 'NOT' )
            int alt5=4;
            switch ( input.LA(1) ) {
            case 26:
                {
                alt5=1;
                }
                break;
            case 27:
                {
                alt5=2;
                }
                break;
            case 28:
                {
                alt5=3;
                }
                break;
            case 29:
                {
                alt5=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }

            switch (alt5) {
                case 1 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:402:2: kw= '!'
                    {
                    kw=(Token)match(input,26,FOLLOW_26_in_ruleOpUnary1019); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current.merge(kw);
                              newLeafNode(kw, grammarAccess.getOpUnaryAccess().getExclamationMarkKeyword_0());

                    }

                    }
                    break;
                case 2 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:409:2: kw= '-'
                    {
                    kw=(Token)match(input,27,FOLLOW_27_in_ruleOpUnary1038); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current.merge(kw);
                              newLeafNode(kw, grammarAccess.getOpUnaryAccess().getHyphenMinusKeyword_1());

                    }

                    }
                    break;
                case 3 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:416:2: kw= '+'
                    {
                    kw=(Token)match(input,28,FOLLOW_28_in_ruleOpUnary1057); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current.merge(kw);
                              newLeafNode(kw, grammarAccess.getOpUnaryAccess().getPlusSignKeyword_2());

                    }

                    }
                    break;
                case 4 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:423:2: kw= 'NOT'
                    {
                    kw=(Token)match(input,29,FOLLOW_29_in_ruleOpUnary1076); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current.merge(kw);
                              newLeafNode(kw, grammarAccess.getOpUnaryAccess().getNOTKeyword_3());

                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleOpUnary"


    // $ANTLR start "entryRuleOpOr"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:436:1: entryRuleOpOr returns [String current=null] : iv_ruleOpOr= ruleOpOr EOF ;
    public final String entryRuleOpOr() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleOpOr = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:437:2: (iv_ruleOpOr= ruleOpOr EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:438:2: iv_ruleOpOr= ruleOpOr EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getOpOrRule());
            }
            pushFollow(FOLLOW_ruleOpOr_in_entryRuleOpOr1117);
            iv_ruleOpOr=ruleOpOr();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleOpOr.getText();
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleOpOr1128); if (state.failed) return current;

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
    // $ANTLR end "entryRuleOpOr"


    // $ANTLR start "ruleOpOr"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:445:1: ruleOpOr returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '||' | kw= 'OR' ) ;
    public final AntlrDatatypeRuleToken ruleOpOr() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;

         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:448:28: ( (kw= '||' | kw= 'OR' ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:449:1: (kw= '||' | kw= 'OR' )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:449:1: (kw= '||' | kw= 'OR' )
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==30) ) {
                alt6=1;
            }
            else if ( (LA6_0==31) ) {
                alt6=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }
            switch (alt6) {
                case 1 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:450:2: kw= '||'
                    {
                    kw=(Token)match(input,30,FOLLOW_30_in_ruleOpOr1166); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current.merge(kw);
                              newLeafNode(kw, grammarAccess.getOpOrAccess().getVerticalLineVerticalLineKeyword_0());

                    }

                    }
                    break;
                case 2 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:457:2: kw= 'OR'
                    {
                    kw=(Token)match(input,31,FOLLOW_31_in_ruleOpOr1185); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current.merge(kw);
                              newLeafNode(kw, grammarAccess.getOpOrAccess().getORKeyword_1());

                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleOpOr"


    // $ANTLR start "entryRuleOpAnd"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:470:1: entryRuleOpAnd returns [String current=null] : iv_ruleOpAnd= ruleOpAnd EOF ;
    public final String entryRuleOpAnd() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleOpAnd = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:471:2: (iv_ruleOpAnd= ruleOpAnd EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:472:2: iv_ruleOpAnd= ruleOpAnd EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getOpAndRule());
            }
            pushFollow(FOLLOW_ruleOpAnd_in_entryRuleOpAnd1226);
            iv_ruleOpAnd=ruleOpAnd();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleOpAnd.getText();
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleOpAnd1237); if (state.failed) return current;

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
    // $ANTLR end "entryRuleOpAnd"


    // $ANTLR start "ruleOpAnd"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:479:1: ruleOpAnd returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '&&' | kw= 'AND' ) ;
    public final AntlrDatatypeRuleToken ruleOpAnd() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;

         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:482:28: ( (kw= '&&' | kw= 'AND' ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:483:1: (kw= '&&' | kw= 'AND' )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:483:1: (kw= '&&' | kw= 'AND' )
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==32) ) {
                alt7=1;
            }
            else if ( (LA7_0==33) ) {
                alt7=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }
            switch (alt7) {
                case 1 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:484:2: kw= '&&'
                    {
                    kw=(Token)match(input,32,FOLLOW_32_in_ruleOpAnd1275); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current.merge(kw);
                              newLeafNode(kw, grammarAccess.getOpAndAccess().getAmpersandAmpersandKeyword_0());

                    }

                    }
                    break;
                case 2 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:491:2: kw= 'AND'
                    {
                    kw=(Token)match(input,33,FOLLOW_33_in_ruleOpAnd1294); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current.merge(kw);
                              newLeafNode(kw, grammarAccess.getOpAndAccess().getANDKeyword_1());

                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleOpAnd"


    // $ANTLR start "entryRuleXPrimaryExpression"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:504:1: entryRuleXPrimaryExpression returns [EObject current=null] : iv_ruleXPrimaryExpression= ruleXPrimaryExpression EOF ;
    public final EObject entryRuleXPrimaryExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXPrimaryExpression = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:505:2: (iv_ruleXPrimaryExpression= ruleXPrimaryExpression EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:506:2: iv_ruleXPrimaryExpression= ruleXPrimaryExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXPrimaryExpressionRule());
            }
            pushFollow(FOLLOW_ruleXPrimaryExpression_in_entryRuleXPrimaryExpression1334);
            iv_ruleXPrimaryExpression=ruleXPrimaryExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXPrimaryExpression;
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleXPrimaryExpression1344); if (state.failed) return current;

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
    // $ANTLR end "entryRuleXPrimaryExpression"


    // $ANTLR start "ruleXPrimaryExpression"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:513:1: ruleXPrimaryExpression returns [EObject current=null] : (this_XFeatureCall_0= ruleXFeatureCall | this_XLiteral_1= ruleXLiteral | this_XIfExpression_2= ruleXIfExpression | this_XParenthesizedExpression_3= ruleXParenthesizedExpression ) ;
    public final EObject ruleXPrimaryExpression() throws RecognitionException {
        EObject current = null;

        EObject this_XFeatureCall_0 = null;

        EObject this_XLiteral_1 = null;

        EObject this_XIfExpression_2 = null;

        EObject this_XParenthesizedExpression_3 = null;


         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:516:28: ( (this_XFeatureCall_0= ruleXFeatureCall | this_XLiteral_1= ruleXLiteral | this_XIfExpression_2= ruleXIfExpression | this_XParenthesizedExpression_3= ruleXParenthesizedExpression ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:517:1: (this_XFeatureCall_0= ruleXFeatureCall | this_XLiteral_1= ruleXLiteral | this_XIfExpression_2= ruleXIfExpression | this_XParenthesizedExpression_3= ruleXParenthesizedExpression )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:517:1: (this_XFeatureCall_0= ruleXFeatureCall | this_XLiteral_1= ruleXLiteral | this_XIfExpression_2= ruleXIfExpression | this_XParenthesizedExpression_3= ruleXParenthesizedExpression )
            int alt8=4;
            switch ( input.LA(1) ) {
            case RULE_ID:
            case 23:
            case 71:
            case 72:
            case 73:
            case 74:
            case 75:
                {
                alt8=1;
                }
                break;
            case RULE_INT:
            case RULE_EINT:
            case RULE_STRING:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
                {
                alt8=2;
                }
                break;
            case 67:
                {
                alt8=3;
                }
                break;
            case 58:
                {
                alt8=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }

            switch (alt8) {
                case 1 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:518:5: this_XFeatureCall_0= ruleXFeatureCall
                    {
                    if ( state.backtracking==0 ) {

                              newCompositeNode(grammarAccess.getXPrimaryExpressionAccess().getXFeatureCallParserRuleCall_0());

                    }
                    pushFollow(FOLLOW_ruleXFeatureCall_in_ruleXPrimaryExpression1391);
                    this_XFeatureCall_0=ruleXFeatureCall();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = this_XFeatureCall_0;
                              afterParserOrEnumRuleCall();

                    }

                    }
                    break;
                case 2 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:528:5: this_XLiteral_1= ruleXLiteral
                    {
                    if ( state.backtracking==0 ) {

                              newCompositeNode(grammarAccess.getXPrimaryExpressionAccess().getXLiteralParserRuleCall_1());

                    }
                    pushFollow(FOLLOW_ruleXLiteral_in_ruleXPrimaryExpression1418);
                    this_XLiteral_1=ruleXLiteral();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = this_XLiteral_1;
                              afterParserOrEnumRuleCall();

                    }

                    }
                    break;
                case 3 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:538:5: this_XIfExpression_2= ruleXIfExpression
                    {
                    if ( state.backtracking==0 ) {

                              newCompositeNode(grammarAccess.getXPrimaryExpressionAccess().getXIfExpressionParserRuleCall_2());

                    }
                    pushFollow(FOLLOW_ruleXIfExpression_in_ruleXPrimaryExpression1445);
                    this_XIfExpression_2=ruleXIfExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = this_XIfExpression_2;
                              afterParserOrEnumRuleCall();

                    }

                    }
                    break;
                case 4 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:548:5: this_XParenthesizedExpression_3= ruleXParenthesizedExpression
                    {
                    if ( state.backtracking==0 ) {

                              newCompositeNode(grammarAccess.getXPrimaryExpressionAccess().getXParenthesizedExpressionParserRuleCall_3());

                    }
                    pushFollow(FOLLOW_ruleXParenthesizedExpression_in_ruleXPrimaryExpression1472);
                    this_XParenthesizedExpression_3=ruleXParenthesizedExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = this_XParenthesizedExpression_3;
                              afterParserOrEnumRuleCall();

                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleXPrimaryExpression"


    // $ANTLR start "entryRuleXRelationalExpression"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:564:1: entryRuleXRelationalExpression returns [EObject current=null] : iv_ruleXRelationalExpression= ruleXRelationalExpression EOF ;
    public final EObject entryRuleXRelationalExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXRelationalExpression = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:565:2: (iv_ruleXRelationalExpression= ruleXRelationalExpression EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:566:2: iv_ruleXRelationalExpression= ruleXRelationalExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXRelationalExpressionRule());
            }
            pushFollow(FOLLOW_ruleXRelationalExpression_in_entryRuleXRelationalExpression1507);
            iv_ruleXRelationalExpression=ruleXRelationalExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXRelationalExpression;
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleXRelationalExpression1517); if (state.failed) return current;

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
    // $ANTLR end "entryRuleXRelationalExpression"


    // $ANTLR start "ruleXRelationalExpression"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:573:1: ruleXRelationalExpression returns [EObject current=null] : (this_XOtherOperatorExpression_0= ruleXOtherOperatorExpression ( ( ( ( () ( ( ruleOpCompare ) ) ) )=> ( () ( ( ruleOpCompare ) ) ) ) ( (lv_rightOperand_3_0= ruleXOtherOperatorExpression ) ) )* ) ;
    public final EObject ruleXRelationalExpression() throws RecognitionException {
        EObject current = null;

        EObject this_XOtherOperatorExpression_0 = null;

        EObject lv_rightOperand_3_0 = null;


         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:576:28: ( (this_XOtherOperatorExpression_0= ruleXOtherOperatorExpression ( ( ( ( () ( ( ruleOpCompare ) ) ) )=> ( () ( ( ruleOpCompare ) ) ) ) ( (lv_rightOperand_3_0= ruleXOtherOperatorExpression ) ) )* ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:577:1: (this_XOtherOperatorExpression_0= ruleXOtherOperatorExpression ( ( ( ( () ( ( ruleOpCompare ) ) ) )=> ( () ( ( ruleOpCompare ) ) ) ) ( (lv_rightOperand_3_0= ruleXOtherOperatorExpression ) ) )* )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:577:1: (this_XOtherOperatorExpression_0= ruleXOtherOperatorExpression ( ( ( ( () ( ( ruleOpCompare ) ) ) )=> ( () ( ( ruleOpCompare ) ) ) ) ( (lv_rightOperand_3_0= ruleXOtherOperatorExpression ) ) )* )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:578:5: this_XOtherOperatorExpression_0= ruleXOtherOperatorExpression ( ( ( ( () ( ( ruleOpCompare ) ) ) )=> ( () ( ( ruleOpCompare ) ) ) ) ( (lv_rightOperand_3_0= ruleXOtherOperatorExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

                      newCompositeNode(grammarAccess.getXRelationalExpressionAccess().getXOtherOperatorExpressionParserRuleCall_0());

            }
            pushFollow(FOLLOW_ruleXOtherOperatorExpression_in_ruleXRelationalExpression1564);
            this_XOtherOperatorExpression_0=ruleXOtherOperatorExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current = this_XOtherOperatorExpression_0;
                      afterParserOrEnumRuleCall();

            }
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:586:1: ( ( ( ( () ( ( ruleOpCompare ) ) ) )=> ( () ( ( ruleOpCompare ) ) ) ) ( (lv_rightOperand_3_0= ruleXOtherOperatorExpression ) ) )*
            loop9:
            do {
                int alt9=2;
                switch ( input.LA(1) ) {
                case 20:
                    {
                    int LA9_2 = input.LA(2);

                    if ( (synpred1_InternalDSEL()) ) {
                        alt9=1;
                    }


                    }
                    break;
                case 21:
                    {
                    int LA9_3 = input.LA(2);

                    if ( (synpred1_InternalDSEL()) ) {
                        alt9=1;
                    }


                    }
                    break;
                case 22:
                    {
                    int LA9_4 = input.LA(2);

                    if ( (synpred1_InternalDSEL()) ) {
                        alt9=1;
                    }


                    }
                    break;
                case 23:
                    {
                    int LA9_5 = input.LA(2);

                    if ( (synpred1_InternalDSEL()) ) {
                        alt9=1;
                    }


                    }
                    break;
                case 24:
                    {
                    int LA9_6 = input.LA(2);

                    if ( (synpred1_InternalDSEL()) ) {
                        alt9=1;
                    }


                    }
                    break;
                case 25:
                    {
                    int LA9_7 = input.LA(2);

                    if ( (synpred1_InternalDSEL()) ) {
                        alt9=1;
                    }


                    }
                    break;

                }

                switch (alt9) {
		case 1 :
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:586:2: ( ( ( () ( ( ruleOpCompare ) ) ) )=> ( () ( ( ruleOpCompare ) ) ) ) ( (lv_rightOperand_3_0= ruleXOtherOperatorExpression ) )
		    {
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:586:2: ( ( ( () ( ( ruleOpCompare ) ) ) )=> ( () ( ( ruleOpCompare ) ) ) )
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:586:3: ( ( () ( ( ruleOpCompare ) ) ) )=> ( () ( ( ruleOpCompare ) ) )
		    {
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:591:6: ( () ( ( ruleOpCompare ) ) )
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:591:7: () ( ( ruleOpCompare ) )
		    {
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:591:7: ()
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:592:5:
		    {
		    if ( state.backtracking==0 ) {

		              current = forceCreateModelElementAndSet(
		                  grammarAccess.getXRelationalExpressionAccess().getXBinaryOperationLeftOperandAction_1_0_0_0(),
		                  current);

		    }

		    }

		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:597:2: ( ( ruleOpCompare ) )
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:598:1: ( ruleOpCompare )
		    {
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:598:1: ( ruleOpCompare )
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:599:3: ruleOpCompare
		    {
		    if ( state.backtracking==0 ) {

					if (current==null) {
			            current = createModelElement(grammarAccess.getXRelationalExpressionRule());
			        }

		    }
		    if ( state.backtracking==0 ) {

			        newCompositeNode(grammarAccess.getXRelationalExpressionAccess().getFeatureJvmIdentifiableElementCrossReference_1_0_0_1_0());

		    }
		    pushFollow(FOLLOW_ruleOpCompare_in_ruleXRelationalExpression1617);
		    ruleOpCompare();

		    state._fsp--;
		    if (state.failed) return current;
		    if ( state.backtracking==0 ) {

			        afterParserOrEnumRuleCall();

		    }

		    }


		    }


		    }


		    }

		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:612:4: ( (lv_rightOperand_3_0= ruleXOtherOperatorExpression ) )
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:613:1: (lv_rightOperand_3_0= ruleXOtherOperatorExpression )
		    {
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:613:1: (lv_rightOperand_3_0= ruleXOtherOperatorExpression )
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:614:3: lv_rightOperand_3_0= ruleXOtherOperatorExpression
		    {
		    if ( state.backtracking==0 ) {

			        newCompositeNode(grammarAccess.getXRelationalExpressionAccess().getRightOperandXOtherOperatorExpressionParserRuleCall_1_1_0());

		    }
		    pushFollow(FOLLOW_ruleXOtherOperatorExpression_in_ruleXRelationalExpression1640);
		    lv_rightOperand_3_0=ruleXOtherOperatorExpression();

		    state._fsp--;
		    if (state.failed) return current;
		    if ( state.backtracking==0 ) {

			        if (current==null) {
			            current = createModelElementForParent(grammarAccess.getXRelationalExpressionRule());
			        }
					set(
						current,
						"rightOperand",
					lv_rightOperand_3_0,
					"XOtherOperatorExpression");
			        afterParserOrEnumRuleCall();

		    }

		    }


		    }


		    }
		    break;

		default :
		    break loop9;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleXRelationalExpression"


    // $ANTLR start "entryRuleXOtherOperatorExpression"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:638:1: entryRuleXOtherOperatorExpression returns [EObject current=null] : iv_ruleXOtherOperatorExpression= ruleXOtherOperatorExpression EOF ;
    public final EObject entryRuleXOtherOperatorExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXOtherOperatorExpression = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:639:2: (iv_ruleXOtherOperatorExpression= ruleXOtherOperatorExpression EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:640:2: iv_ruleXOtherOperatorExpression= ruleXOtherOperatorExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXOtherOperatorExpressionRule());
            }
            pushFollow(FOLLOW_ruleXOtherOperatorExpression_in_entryRuleXOtherOperatorExpression1678);
            iv_ruleXOtherOperatorExpression=ruleXOtherOperatorExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXOtherOperatorExpression;
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleXOtherOperatorExpression1688); if (state.failed) return current;

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
    // $ANTLR end "entryRuleXOtherOperatorExpression"


    // $ANTLR start "ruleXOtherOperatorExpression"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:647:1: ruleXOtherOperatorExpression returns [EObject current=null] : (this_XAdditiveExpression_0= ruleXAdditiveExpression ( ( ( ( () ( ( ruleOpOther ) ) ) )=> ( () ( ( ruleOpOther ) ) ) ) ( (lv_rightOperand_3_0= ruleXAdditiveExpression ) ) )* ) ;
    public final EObject ruleXOtherOperatorExpression() throws RecognitionException {
        EObject current = null;

        EObject this_XAdditiveExpression_0 = null;

        EObject lv_rightOperand_3_0 = null;


         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:650:28: ( (this_XAdditiveExpression_0= ruleXAdditiveExpression ( ( ( ( () ( ( ruleOpOther ) ) ) )=> ( () ( ( ruleOpOther ) ) ) ) ( (lv_rightOperand_3_0= ruleXAdditiveExpression ) ) )* ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:651:1: (this_XAdditiveExpression_0= ruleXAdditiveExpression ( ( ( ( () ( ( ruleOpOther ) ) ) )=> ( () ( ( ruleOpOther ) ) ) ) ( (lv_rightOperand_3_0= ruleXAdditiveExpression ) ) )* )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:651:1: (this_XAdditiveExpression_0= ruleXAdditiveExpression ( ( ( ( () ( ( ruleOpOther ) ) ) )=> ( () ( ( ruleOpOther ) ) ) ) ( (lv_rightOperand_3_0= ruleXAdditiveExpression ) ) )* )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:652:5: this_XAdditiveExpression_0= ruleXAdditiveExpression ( ( ( ( () ( ( ruleOpOther ) ) ) )=> ( () ( ( ruleOpOther ) ) ) ) ( (lv_rightOperand_3_0= ruleXAdditiveExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

                      newCompositeNode(grammarAccess.getXOtherOperatorExpressionAccess().getXAdditiveExpressionParserRuleCall_0());

            }
            pushFollow(FOLLOW_ruleXAdditiveExpression_in_ruleXOtherOperatorExpression1735);
            this_XAdditiveExpression_0=ruleXAdditiveExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current = this_XAdditiveExpression_0;
                      afterParserOrEnumRuleCall();

            }
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:660:1: ( ( ( ( () ( ( ruleOpOther ) ) ) )=> ( () ( ( ruleOpOther ) ) ) ) ( (lv_rightOperand_3_0= ruleXAdditiveExpression ) ) )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==34) ) {
                    int LA10_2 = input.LA(2);

                    if ( (synpred2_InternalDSEL()) ) {
                        alt10=1;
                    }


                }


                switch (alt10) {
		case 1 :
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:660:2: ( ( ( () ( ( ruleOpOther ) ) ) )=> ( () ( ( ruleOpOther ) ) ) ) ( (lv_rightOperand_3_0= ruleXAdditiveExpression ) )
		    {
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:660:2: ( ( ( () ( ( ruleOpOther ) ) ) )=> ( () ( ( ruleOpOther ) ) ) )
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:660:3: ( ( () ( ( ruleOpOther ) ) ) )=> ( () ( ( ruleOpOther ) ) )
		    {
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:665:6: ( () ( ( ruleOpOther ) ) )
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:665:7: () ( ( ruleOpOther ) )
		    {
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:665:7: ()
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:666:5:
		    {
		    if ( state.backtracking==0 ) {

		              current = forceCreateModelElementAndSet(
		                  grammarAccess.getXOtherOperatorExpressionAccess().getXBinaryOperationLeftOperandAction_1_0_0_0(),
		                  current);

		    }

		    }

		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:671:2: ( ( ruleOpOther ) )
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:672:1: ( ruleOpOther )
		    {
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:672:1: ( ruleOpOther )
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:673:3: ruleOpOther
		    {
		    if ( state.backtracking==0 ) {

					if (current==null) {
			            current = createModelElement(grammarAccess.getXOtherOperatorExpressionRule());
			        }

		    }
		    if ( state.backtracking==0 ) {

			        newCompositeNode(grammarAccess.getXOtherOperatorExpressionAccess().getFeatureJvmIdentifiableElementCrossReference_1_0_0_1_0());

		    }
		    pushFollow(FOLLOW_ruleOpOther_in_ruleXOtherOperatorExpression1788);
		    ruleOpOther();

		    state._fsp--;
		    if (state.failed) return current;
		    if ( state.backtracking==0 ) {

			        afterParserOrEnumRuleCall();

		    }

		    }


		    }


		    }


		    }

		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:686:4: ( (lv_rightOperand_3_0= ruleXAdditiveExpression ) )
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:687:1: (lv_rightOperand_3_0= ruleXAdditiveExpression )
		    {
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:687:1: (lv_rightOperand_3_0= ruleXAdditiveExpression )
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:688:3: lv_rightOperand_3_0= ruleXAdditiveExpression
		    {
		    if ( state.backtracking==0 ) {

			        newCompositeNode(grammarAccess.getXOtherOperatorExpressionAccess().getRightOperandXAdditiveExpressionParserRuleCall_1_1_0());

		    }
		    pushFollow(FOLLOW_ruleXAdditiveExpression_in_ruleXOtherOperatorExpression1811);
		    lv_rightOperand_3_0=ruleXAdditiveExpression();

		    state._fsp--;
		    if (state.failed) return current;
		    if ( state.backtracking==0 ) {

			        if (current==null) {
			            current = createModelElementForParent(grammarAccess.getXOtherOperatorExpressionRule());
			        }
					set(
						current,
						"rightOperand",
					lv_rightOperand_3_0,
					"XAdditiveExpression");
			        afterParserOrEnumRuleCall();

		    }

		    }


		    }


		    }
		    break;

		default :
		    break loop10;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleXOtherOperatorExpression"


    // $ANTLR start "entryRuleOpOther"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:712:1: entryRuleOpOther returns [String current=null] : iv_ruleOpOther= ruleOpOther EOF ;
    public final String entryRuleOpOther() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleOpOther = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:713:2: (iv_ruleOpOther= ruleOpOther EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:714:2: iv_ruleOpOther= ruleOpOther EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getOpOtherRule());
            }
            pushFollow(FOLLOW_ruleOpOther_in_entryRuleOpOther1850);
            iv_ruleOpOther=ruleOpOther();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleOpOther.getText();
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleOpOther1861); if (state.failed) return current;

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
    // $ANTLR end "entryRuleOpOther"


    // $ANTLR start "ruleOpOther"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:721:1: ruleOpOther returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : kw= '..' ;
    public final AntlrDatatypeRuleToken ruleOpOther() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;

         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:724:28: (kw= '..' )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:726:2: kw= '..'
            {
            kw=(Token)match(input,34,FOLLOW_34_in_ruleOpOther1898); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current.merge(kw);
                      newLeafNode(kw, grammarAccess.getOpOtherAccess().getFullStopFullStopKeyword());

            }

            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleOpOther"


    // $ANTLR start "entryRuleDateLiteral"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:739:1: entryRuleDateLiteral returns [EObject current=null] : iv_ruleDateLiteral= ruleDateLiteral EOF ;
    public final EObject entryRuleDateLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDateLiteral = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:740:2: (iv_ruleDateLiteral= ruleDateLiteral EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:741:2: iv_ruleDateLiteral= ruleDateLiteral EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getDateLiteralRule());
            }
            pushFollow(FOLLOW_ruleDateLiteral_in_entryRuleDateLiteral1937);
            iv_ruleDateLiteral=ruleDateLiteral();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleDateLiteral;
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleDateLiteral1947); if (state.failed) return current;

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
    // $ANTLR end "entryRuleDateLiteral"


    // $ANTLR start "ruleDateLiteral"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:748:1: ruleDateLiteral returns [EObject current=null] : ( () ( (lv_value_1_0= ruleDate ) ) ) ;
    public final EObject ruleDateLiteral() throws RecognitionException {
        EObject current = null;

        AntlrDatatypeRuleToken lv_value_1_0 = null;


         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:751:28: ( ( () ( (lv_value_1_0= ruleDate ) ) ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:752:1: ( () ( (lv_value_1_0= ruleDate ) ) )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:752:1: ( () ( (lv_value_1_0= ruleDate ) ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:752:2: () ( (lv_value_1_0= ruleDate ) )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:752:2: ()
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:753:5:
            {
            if ( state.backtracking==0 ) {

                      current = forceCreateModelElement(
                          grammarAccess.getDateLiteralAccess().getDateLiteralAction_0(),
                          current);

            }

            }

            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:758:2: ( (lv_value_1_0= ruleDate ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:759:1: (lv_value_1_0= ruleDate )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:759:1: (lv_value_1_0= ruleDate )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:760:3: lv_value_1_0= ruleDate
            {
            if ( state.backtracking==0 ) {

		        newCompositeNode(grammarAccess.getDateLiteralAccess().getValueDateParserRuleCall_1_0());

            }
            pushFollow(FOLLOW_ruleDate_in_ruleDateLiteral2002);
            lv_value_1_0=ruleDate();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

		        if (current==null) {
		            current = createModelElementForParent(grammarAccess.getDateLiteralRule());
		        }
				set(
					current,
					"value",
				lv_value_1_0,
				"Date");
		        afterParserOrEnumRuleCall();

            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleDateLiteral"


    // $ANTLR start "entryRuleDateTimeLiteral"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:784:1: entryRuleDateTimeLiteral returns [EObject current=null] : iv_ruleDateTimeLiteral= ruleDateTimeLiteral EOF ;
    public final EObject entryRuleDateTimeLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDateTimeLiteral = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:785:2: (iv_ruleDateTimeLiteral= ruleDateTimeLiteral EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:786:2: iv_ruleDateTimeLiteral= ruleDateTimeLiteral EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getDateTimeLiteralRule());
            }
            pushFollow(FOLLOW_ruleDateTimeLiteral_in_entryRuleDateTimeLiteral2038);
            iv_ruleDateTimeLiteral=ruleDateTimeLiteral();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleDateTimeLiteral;
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleDateTimeLiteral2048); if (state.failed) return current;

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
    // $ANTLR end "entryRuleDateTimeLiteral"


    // $ANTLR start "ruleDateTimeLiteral"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:793:1: ruleDateTimeLiteral returns [EObject current=null] : ( () ( (lv_value_1_0= ruleDate_Time ) ) ) ;
    public final EObject ruleDateTimeLiteral() throws RecognitionException {
        EObject current = null;

        AntlrDatatypeRuleToken lv_value_1_0 = null;


         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:796:28: ( ( () ( (lv_value_1_0= ruleDate_Time ) ) ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:797:1: ( () ( (lv_value_1_0= ruleDate_Time ) ) )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:797:1: ( () ( (lv_value_1_0= ruleDate_Time ) ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:797:2: () ( (lv_value_1_0= ruleDate_Time ) )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:797:2: ()
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:798:5:
            {
            if ( state.backtracking==0 ) {

                      current = forceCreateModelElement(
                          grammarAccess.getDateTimeLiteralAccess().getDateTimeLiteralAction_0(),
                          current);

            }

            }

            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:803:2: ( (lv_value_1_0= ruleDate_Time ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:804:1: (lv_value_1_0= ruleDate_Time )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:804:1: (lv_value_1_0= ruleDate_Time )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:805:3: lv_value_1_0= ruleDate_Time
            {
            if ( state.backtracking==0 ) {

		        newCompositeNode(grammarAccess.getDateTimeLiteralAccess().getValueDate_TimeParserRuleCall_1_0());

            }
            pushFollow(FOLLOW_ruleDate_Time_in_ruleDateTimeLiteral2103);
            lv_value_1_0=ruleDate_Time();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

		        if (current==null) {
		            current = createModelElementForParent(grammarAccess.getDateTimeLiteralRule());
		        }
				set(
					current,
					"value",
				lv_value_1_0,
				"Date_Time");
		        afterParserOrEnumRuleCall();

            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleDateTimeLiteral"


    // $ANTLR start "entryRuleDecimalLiteral"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:829:1: entryRuleDecimalLiteral returns [EObject current=null] : iv_ruleDecimalLiteral= ruleDecimalLiteral EOF ;
    public final EObject entryRuleDecimalLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDecimalLiteral = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:830:2: (iv_ruleDecimalLiteral= ruleDecimalLiteral EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:831:2: iv_ruleDecimalLiteral= ruleDecimalLiteral EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getDecimalLiteralRule());
            }
            pushFollow(FOLLOW_ruleDecimalLiteral_in_entryRuleDecimalLiteral2139);
            iv_ruleDecimalLiteral=ruleDecimalLiteral();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleDecimalLiteral;
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleDecimalLiteral2149); if (state.failed) return current;

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
    // $ANTLR end "entryRuleDecimalLiteral"


    // $ANTLR start "ruleDecimalLiteral"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:838:1: ruleDecimalLiteral returns [EObject current=null] : ( () ( (lv_value_1_0= ruleNumber ) ) ) ;
    public final EObject ruleDecimalLiteral() throws RecognitionException {
        EObject current = null;

        AntlrDatatypeRuleToken lv_value_1_0 = null;


         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:841:28: ( ( () ( (lv_value_1_0= ruleNumber ) ) ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:842:1: ( () ( (lv_value_1_0= ruleNumber ) ) )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:842:1: ( () ( (lv_value_1_0= ruleNumber ) ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:842:2: () ( (lv_value_1_0= ruleNumber ) )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:842:2: ()
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:843:5:
            {
            if ( state.backtracking==0 ) {

                      current = forceCreateModelElement(
                          grammarAccess.getDecimalLiteralAccess().getDecimalLiteralAction_0(),
                          current);

            }

            }

            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:848:2: ( (lv_value_1_0= ruleNumber ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:849:1: (lv_value_1_0= ruleNumber )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:849:1: (lv_value_1_0= ruleNumber )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:850:3: lv_value_1_0= ruleNumber
            {
            if ( state.backtracking==0 ) {

		        newCompositeNode(grammarAccess.getDecimalLiteralAccess().getValueNumberParserRuleCall_1_0());

            }
            pushFollow(FOLLOW_ruleNumber_in_ruleDecimalLiteral2204);
            lv_value_1_0=ruleNumber();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

		        if (current==null) {
		            current = createModelElementForParent(grammarAccess.getDecimalLiteralRule());
		        }
				set(
					current,
					"value",
				lv_value_1_0,
				"Number");
		        afterParserOrEnumRuleCall();

            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleDecimalLiteral"


    // $ANTLR start "entryRuleXNullLiteral"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:874:1: entryRuleXNullLiteral returns [EObject current=null] : iv_ruleXNullLiteral= ruleXNullLiteral EOF ;
    public final EObject entryRuleXNullLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXNullLiteral = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:875:2: (iv_ruleXNullLiteral= ruleXNullLiteral EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:876:2: iv_ruleXNullLiteral= ruleXNullLiteral EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXNullLiteralRule());
            }
            pushFollow(FOLLOW_ruleXNullLiteral_in_entryRuleXNullLiteral2240);
            iv_ruleXNullLiteral=ruleXNullLiteral();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXNullLiteral;
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleXNullLiteral2250); if (state.failed) return current;

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
    // $ANTLR end "entryRuleXNullLiteral"


    // $ANTLR start "ruleXNullLiteral"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:883:1: ruleXNullLiteral returns [EObject current=null] : ( () (otherlv_1= 'null' | otherlv_2= 'NULL' ) ) ;
    public final EObject ruleXNullLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;

         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:886:28: ( ( () (otherlv_1= 'null' | otherlv_2= 'NULL' ) ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:887:1: ( () (otherlv_1= 'null' | otherlv_2= 'NULL' ) )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:887:1: ( () (otherlv_1= 'null' | otherlv_2= 'NULL' ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:887:2: () (otherlv_1= 'null' | otherlv_2= 'NULL' )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:887:2: ()
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:888:5:
            {
            if ( state.backtracking==0 ) {

                      current = forceCreateModelElement(
                          grammarAccess.getXNullLiteralAccess().getXNullLiteralAction_0(),
                          current);

            }

            }

            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:893:2: (otherlv_1= 'null' | otherlv_2= 'NULL' )
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==35) ) {
                alt11=1;
            }
            else if ( (LA11_0==36) ) {
                alt11=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 0, input);

                throw nvae;
            }
            switch (alt11) {
                case 1 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:893:4: otherlv_1= 'null'
                    {
                    otherlv_1=(Token)match(input,35,FOLLOW_35_in_ruleXNullLiteral2297); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

				newLeafNode(otherlv_1, grammarAccess.getXNullLiteralAccess().getNullKeyword_1_0());

                    }

                    }
                    break;
                case 2 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:898:7: otherlv_2= 'NULL'
                    {
                    otherlv_2=(Token)match(input,36,FOLLOW_36_in_ruleXNullLiteral2315); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

				newLeafNode(otherlv_2, grammarAccess.getXNullLiteralAccess().getNULLKeyword_1_1());

                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleXNullLiteral"


    // $ANTLR start "entryRuleXBooleanLiteral"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:910:1: entryRuleXBooleanLiteral returns [EObject current=null] : iv_ruleXBooleanLiteral= ruleXBooleanLiteral EOF ;
    public final EObject entryRuleXBooleanLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXBooleanLiteral = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:911:2: (iv_ruleXBooleanLiteral= ruleXBooleanLiteral EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:912:2: iv_ruleXBooleanLiteral= ruleXBooleanLiteral EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXBooleanLiteralRule());
            }
            pushFollow(FOLLOW_ruleXBooleanLiteral_in_entryRuleXBooleanLiteral2352);
            iv_ruleXBooleanLiteral=ruleXBooleanLiteral();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXBooleanLiteral;
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleXBooleanLiteral2362); if (state.failed) return current;

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
    // $ANTLR end "entryRuleXBooleanLiteral"


    // $ANTLR start "ruleXBooleanLiteral"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:919:1: ruleXBooleanLiteral returns [EObject current=null] : ( () ( (otherlv_1= 'false' | otherlv_2= 'FALSE' ) | ( ( (lv_isTrue_3_1= 'true' | lv_isTrue_3_2= 'TRUE' ) ) ) ) ) ;
    public final EObject ruleXBooleanLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        Token lv_isTrue_3_1=null;
        Token lv_isTrue_3_2=null;

         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:922:28: ( ( () ( (otherlv_1= 'false' | otherlv_2= 'FALSE' ) | ( ( (lv_isTrue_3_1= 'true' | lv_isTrue_3_2= 'TRUE' ) ) ) ) ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:923:1: ( () ( (otherlv_1= 'false' | otherlv_2= 'FALSE' ) | ( ( (lv_isTrue_3_1= 'true' | lv_isTrue_3_2= 'TRUE' ) ) ) ) )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:923:1: ( () ( (otherlv_1= 'false' | otherlv_2= 'FALSE' ) | ( ( (lv_isTrue_3_1= 'true' | lv_isTrue_3_2= 'TRUE' ) ) ) ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:923:2: () ( (otherlv_1= 'false' | otherlv_2= 'FALSE' ) | ( ( (lv_isTrue_3_1= 'true' | lv_isTrue_3_2= 'TRUE' ) ) ) )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:923:2: ()
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:924:5:
            {
            if ( state.backtracking==0 ) {

                      current = forceCreateModelElement(
                          grammarAccess.getXBooleanLiteralAccess().getXBooleanLiteralAction_0(),
                          current);

            }

            }

            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:929:2: ( (otherlv_1= 'false' | otherlv_2= 'FALSE' ) | ( ( (lv_isTrue_3_1= 'true' | lv_isTrue_3_2= 'TRUE' ) ) ) )
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( ((LA14_0>=37 && LA14_0<=38)) ) {
                alt14=1;
            }
            else if ( ((LA14_0>=39 && LA14_0<=40)) ) {
                alt14=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 14, 0, input);

                throw nvae;
            }
            switch (alt14) {
                case 1 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:929:3: (otherlv_1= 'false' | otherlv_2= 'FALSE' )
                    {
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:929:3: (otherlv_1= 'false' | otherlv_2= 'FALSE' )
                    int alt12=2;
                    int LA12_0 = input.LA(1);

                    if ( (LA12_0==37) ) {
                        alt12=1;
                    }
                    else if ( (LA12_0==38) ) {
                        alt12=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 12, 0, input);

                        throw nvae;
                    }
                    switch (alt12) {
                        case 1 :
                            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:929:5: otherlv_1= 'false'
                            {
                            otherlv_1=(Token)match(input,37,FOLLOW_37_in_ruleXBooleanLiteral2410); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

					newLeafNode(otherlv_1, grammarAccess.getXBooleanLiteralAccess().getFalseKeyword_1_0_0());

                            }

                            }
                            break;
                        case 2 :
                            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:934:7: otherlv_2= 'FALSE'
                            {
                            otherlv_2=(Token)match(input,38,FOLLOW_38_in_ruleXBooleanLiteral2428); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

					newLeafNode(otherlv_2, grammarAccess.getXBooleanLiteralAccess().getFALSEKeyword_1_0_1());

                            }

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:939:6: ( ( (lv_isTrue_3_1= 'true' | lv_isTrue_3_2= 'TRUE' ) ) )
                    {
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:939:6: ( ( (lv_isTrue_3_1= 'true' | lv_isTrue_3_2= 'TRUE' ) ) )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:940:1: ( (lv_isTrue_3_1= 'true' | lv_isTrue_3_2= 'TRUE' ) )
                    {
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:940:1: ( (lv_isTrue_3_1= 'true' | lv_isTrue_3_2= 'TRUE' ) )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:941:1: (lv_isTrue_3_1= 'true' | lv_isTrue_3_2= 'TRUE' )
                    {
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:941:1: (lv_isTrue_3_1= 'true' | lv_isTrue_3_2= 'TRUE' )
                    int alt13=2;
                    int LA13_0 = input.LA(1);

                    if ( (LA13_0==39) ) {
                        alt13=1;
                    }
                    else if ( (LA13_0==40) ) {
                        alt13=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 13, 0, input);

                        throw nvae;
                    }
                    switch (alt13) {
                        case 1 :
                            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:942:3: lv_isTrue_3_1= 'true'
                            {
                            lv_isTrue_3_1=(Token)match(input,39,FOLLOW_39_in_ruleXBooleanLiteral2455); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                                      newLeafNode(lv_isTrue_3_1, grammarAccess.getXBooleanLiteralAccess().getIsTrueTrueKeyword_1_1_0_0());

                            }
                            if ( state.backtracking==0 ) {

				        if (current==null) {
				            current = createModelElement(grammarAccess.getXBooleanLiteralRule());
				        }
						setWithLastConsumed(current, "isTrue", true, null);

                            }

                            }
                            break;
                        case 2 :
                            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:954:8: lv_isTrue_3_2= 'TRUE'
                            {
                            lv_isTrue_3_2=(Token)match(input,40,FOLLOW_40_in_ruleXBooleanLiteral2484); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                                      newLeafNode(lv_isTrue_3_2, grammarAccess.getXBooleanLiteralAccess().getIsTrueTRUEKeyword_1_1_0_1());

                            }
                            if ( state.backtracking==0 ) {

				        if (current==null) {
				            current = createModelElement(grammarAccess.getXBooleanLiteralRule());
				        }
						setWithLastConsumed(current, "isTrue", true, null);

                            }

                            }
                            break;

                    }


                    }


                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleXBooleanLiteral"


    // $ANTLR start "entryRuleDate"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:977:1: entryRuleDate returns [String current=null] : iv_ruleDate= ruleDate EOF ;
    public final String entryRuleDate() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleDate = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:978:2: (iv_ruleDate= ruleDate EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:979:2: iv_ruleDate= ruleDate EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getDateRule());
            }
            pushFollow(FOLLOW_ruleDate_in_entryRuleDate2538);
            iv_ruleDate=ruleDate();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleDate.getText();
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleDate2549); if (state.failed) return current;

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
    // $ANTLR end "entryRuleDate"


    // $ANTLR start "ruleDate"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:986:1: ruleDate returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INT_0= RULE_INT kw= '.' this_INT_2= RULE_INT kw= '.' this_INT_4= RULE_INT ) ;
    public final AntlrDatatypeRuleToken ruleDate() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_INT_0=null;
        Token kw=null;
        Token this_INT_2=null;
        Token this_INT_4=null;

         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:989:28: ( (this_INT_0= RULE_INT kw= '.' this_INT_2= RULE_INT kw= '.' this_INT_4= RULE_INT ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:990:1: (this_INT_0= RULE_INT kw= '.' this_INT_2= RULE_INT kw= '.' this_INT_4= RULE_INT )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:990:1: (this_INT_0= RULE_INT kw= '.' this_INT_2= RULE_INT kw= '.' this_INT_4= RULE_INT )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:990:6: this_INT_0= RULE_INT kw= '.' this_INT_2= RULE_INT kw= '.' this_INT_4= RULE_INT
            {
            this_INT_0=(Token)match(input,RULE_INT,FOLLOW_RULE_INT_in_ruleDate2589); if (state.failed) return current;
            if ( state.backtracking==0 ) {

			current.merge(this_INT_0);

            }
            if ( state.backtracking==0 ) {

                  newLeafNode(this_INT_0, grammarAccess.getDateAccess().getINTTerminalRuleCall_0());

            }
            kw=(Token)match(input,41,FOLLOW_41_in_ruleDate2607); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current.merge(kw);
                      newLeafNode(kw, grammarAccess.getDateAccess().getFullStopKeyword_1());

            }
            this_INT_2=(Token)match(input,RULE_INT,FOLLOW_RULE_INT_in_ruleDate2622); if (state.failed) return current;
            if ( state.backtracking==0 ) {

			current.merge(this_INT_2);

            }
            if ( state.backtracking==0 ) {

                  newLeafNode(this_INT_2, grammarAccess.getDateAccess().getINTTerminalRuleCall_2());

            }
            kw=(Token)match(input,41,FOLLOW_41_in_ruleDate2640); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current.merge(kw);
                      newLeafNode(kw, grammarAccess.getDateAccess().getFullStopKeyword_3());

            }
            this_INT_4=(Token)match(input,RULE_INT,FOLLOW_RULE_INT_in_ruleDate2655); if (state.failed) return current;
            if ( state.backtracking==0 ) {

			current.merge(this_INT_4);

            }
            if ( state.backtracking==0 ) {

                  newLeafNode(this_INT_4, grammarAccess.getDateAccess().getINTTerminalRuleCall_4());

            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleDate"


    // $ANTLR start "entryRuleDate_Time"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1031:1: entryRuleDate_Time returns [String current=null] : iv_ruleDate_Time= ruleDate_Time EOF ;
    public final String entryRuleDate_Time() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleDate_Time = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1032:2: (iv_ruleDate_Time= ruleDate_Time EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1033:2: iv_ruleDate_Time= ruleDate_Time EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getDate_TimeRule());
            }
            pushFollow(FOLLOW_ruleDate_Time_in_entryRuleDate_Time2701);
            iv_ruleDate_Time=ruleDate_Time();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleDate_Time.getText();
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleDate_Time2712); if (state.failed) return current;

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
    // $ANTLR end "entryRuleDate_Time"


    // $ANTLR start "ruleDate_Time"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1040:1: ruleDate_Time returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_Date_0= ruleDate this_INT_1= RULE_INT kw= ':' this_INT_3= RULE_INT kw= ':' this_INT_5= RULE_INT ) ;
    public final AntlrDatatypeRuleToken ruleDate_Time() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_INT_1=null;
        Token kw=null;
        Token this_INT_3=null;
        Token this_INT_5=null;
        AntlrDatatypeRuleToken this_Date_0 = null;


         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1043:28: ( (this_Date_0= ruleDate this_INT_1= RULE_INT kw= ':' this_INT_3= RULE_INT kw= ':' this_INT_5= RULE_INT ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1044:1: (this_Date_0= ruleDate this_INT_1= RULE_INT kw= ':' this_INT_3= RULE_INT kw= ':' this_INT_5= RULE_INT )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1044:1: (this_Date_0= ruleDate this_INT_1= RULE_INT kw= ':' this_INT_3= RULE_INT kw= ':' this_INT_5= RULE_INT )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1045:5: this_Date_0= ruleDate this_INT_1= RULE_INT kw= ':' this_INT_3= RULE_INT kw= ':' this_INT_5= RULE_INT
            {
            if ( state.backtracking==0 ) {

                      newCompositeNode(grammarAccess.getDate_TimeAccess().getDateParserRuleCall_0());

            }
            pushFollow(FOLLOW_ruleDate_in_ruleDate_Time2759);
            this_Date_0=ruleDate();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

			current.merge(this_Date_0);

            }
            if ( state.backtracking==0 ) {

                      afterParserOrEnumRuleCall();

            }
            this_INT_1=(Token)match(input,RULE_INT,FOLLOW_RULE_INT_in_ruleDate_Time2779); if (state.failed) return current;
            if ( state.backtracking==0 ) {

			current.merge(this_INT_1);

            }
            if ( state.backtracking==0 ) {

                  newLeafNode(this_INT_1, grammarAccess.getDate_TimeAccess().getINTTerminalRuleCall_1());

            }
            kw=(Token)match(input,42,FOLLOW_42_in_ruleDate_Time2797); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current.merge(kw);
                      newLeafNode(kw, grammarAccess.getDate_TimeAccess().getColonKeyword_2());

            }
            this_INT_3=(Token)match(input,RULE_INT,FOLLOW_RULE_INT_in_ruleDate_Time2812); if (state.failed) return current;
            if ( state.backtracking==0 ) {

			current.merge(this_INT_3);

            }
            if ( state.backtracking==0 ) {

                  newLeafNode(this_INT_3, grammarAccess.getDate_TimeAccess().getINTTerminalRuleCall_3());

            }
            kw=(Token)match(input,42,FOLLOW_42_in_ruleDate_Time2830); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current.merge(kw);
                      newLeafNode(kw, grammarAccess.getDate_TimeAccess().getColonKeyword_4());

            }
            this_INT_5=(Token)match(input,RULE_INT,FOLLOW_RULE_INT_in_ruleDate_Time2845); if (state.failed) return current;
            if ( state.backtracking==0 ) {

			current.merge(this_INT_5);

            }
            if ( state.backtracking==0 ) {

                  newLeafNode(this_INT_5, grammarAccess.getDate_TimeAccess().getINTTerminalRuleCall_5());

            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleDate_Time"


    // $ANTLR start "entryRuleNumber"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1096:1: entryRuleNumber returns [String current=null] : iv_ruleNumber= ruleNumber EOF ;
    public final String entryRuleNumber() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleNumber = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1097:2: (iv_ruleNumber= ruleNumber EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1098:2: iv_ruleNumber= ruleNumber EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getNumberRule());
            }
            pushFollow(FOLLOW_ruleNumber_in_entryRuleNumber2891);
            iv_ruleNumber=ruleNumber();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleNumber.getText();
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleNumber2902); if (state.failed) return current;

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
    // $ANTLR end "entryRuleNumber"


    // $ANTLR start "ruleNumber"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1105:1: ruleNumber returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (this_INT_0= RULE_INT kw= '.' )? (this_INT_2= RULE_INT | this_EINT_3= RULE_EINT ) ) ;
    public final AntlrDatatypeRuleToken ruleNumber() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_INT_0=null;
        Token kw=null;
        Token this_INT_2=null;
        Token this_EINT_3=null;

         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1108:28: ( ( (this_INT_0= RULE_INT kw= '.' )? (this_INT_2= RULE_INT | this_EINT_3= RULE_EINT ) ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1109:1: ( (this_INT_0= RULE_INT kw= '.' )? (this_INT_2= RULE_INT | this_EINT_3= RULE_EINT ) )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1109:1: ( (this_INT_0= RULE_INT kw= '.' )? (this_INT_2= RULE_INT | this_EINT_3= RULE_EINT ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1109:2: (this_INT_0= RULE_INT kw= '.' )? (this_INT_2= RULE_INT | this_EINT_3= RULE_EINT )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1109:2: (this_INT_0= RULE_INT kw= '.' )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==RULE_INT) ) {
                int LA15_1 = input.LA(2);

                if ( (LA15_1==41) ) {
                    int LA15_3 = input.LA(3);

                    if ( ((LA15_3>=RULE_INT && LA15_3<=RULE_EINT)) ) {
                        alt15=1;
                    }
                }
            }
            switch (alt15) {
                case 1 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1109:7: this_INT_0= RULE_INT kw= '.'
                    {
                    this_INT_0=(Token)match(input,RULE_INT,FOLLOW_RULE_INT_in_ruleNumber2943); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

				current.merge(this_INT_0);

                    }
                    if ( state.backtracking==0 ) {

                          newLeafNode(this_INT_0, grammarAccess.getNumberAccess().getINTTerminalRuleCall_0_0());

                    }
                    kw=(Token)match(input,41,FOLLOW_41_in_ruleNumber2961); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current.merge(kw);
                              newLeafNode(kw, grammarAccess.getNumberAccess().getFullStopKeyword_0_1());

                    }

                    }
                    break;

            }

            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1122:3: (this_INT_2= RULE_INT | this_EINT_3= RULE_EINT )
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==RULE_INT) ) {
                alt16=1;
            }
            else if ( (LA16_0==RULE_EINT) ) {
                alt16=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 16, 0, input);

                throw nvae;
            }
            switch (alt16) {
                case 1 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1122:8: this_INT_2= RULE_INT
                    {
                    this_INT_2=(Token)match(input,RULE_INT,FOLLOW_RULE_INT_in_ruleNumber2979); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

				current.merge(this_INT_2);

                    }
                    if ( state.backtracking==0 ) {

                          newLeafNode(this_INT_2, grammarAccess.getNumberAccess().getINTTerminalRuleCall_1_0());

                    }

                    }
                    break;
                case 2 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1130:10: this_EINT_3= RULE_EINT
                    {
                    this_EINT_3=(Token)match(input,RULE_EINT,FOLLOW_RULE_EINT_in_ruleNumber3005); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

				current.merge(this_EINT_3);

                    }
                    if ( state.backtracking==0 ) {

                          newLeafNode(this_EINT_3, grammarAccess.getNumberAccess().getEINTTerminalRuleCall_1_1());

                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleNumber"


    // $ANTLR start "entryRuleXAssignment"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1145:1: entryRuleXAssignment returns [EObject current=null] : iv_ruleXAssignment= ruleXAssignment EOF ;
    public final EObject entryRuleXAssignment() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXAssignment = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1146:2: (iv_ruleXAssignment= ruleXAssignment EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1147:2: iv_ruleXAssignment= ruleXAssignment EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXAssignmentRule());
            }
            pushFollow(FOLLOW_ruleXAssignment_in_entryRuleXAssignment3051);
            iv_ruleXAssignment=ruleXAssignment();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXAssignment;
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleXAssignment3061); if (state.failed) return current;

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
    // $ANTLR end "entryRuleXAssignment"


    // $ANTLR start "ruleXAssignment"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1154:1: ruleXAssignment returns [EObject current=null] : ( ( () ( ( ruleFeatureCallID ) ) ruleOpSingleAssign ( (lv_value_3_0= ruleXAssignment ) ) ) | (this_XOrExpression_4= ruleXOrExpression ( ( ( ( () ( ( ruleOpMultiAssign ) ) ) )=> ( () ( ( ruleOpMultiAssign ) ) ) ) ( (lv_rightOperand_7_0= ruleXAssignment ) ) )? ) ) ;
    public final EObject ruleXAssignment() throws RecognitionException {
        EObject current = null;

        EObject lv_value_3_0 = null;

        EObject this_XOrExpression_4 = null;

        EObject lv_rightOperand_7_0 = null;


         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1157:28: ( ( ( () ( ( ruleFeatureCallID ) ) ruleOpSingleAssign ( (lv_value_3_0= ruleXAssignment ) ) ) | (this_XOrExpression_4= ruleXOrExpression ( ( ( ( () ( ( ruleOpMultiAssign ) ) ) )=> ( () ( ( ruleOpMultiAssign ) ) ) ) ( (lv_rightOperand_7_0= ruleXAssignment ) ) )? ) ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1158:1: ( ( () ( ( ruleFeatureCallID ) ) ruleOpSingleAssign ( (lv_value_3_0= ruleXAssignment ) ) ) | (this_XOrExpression_4= ruleXOrExpression ( ( ( ( () ( ( ruleOpMultiAssign ) ) ) )=> ( () ( ( ruleOpMultiAssign ) ) ) ) ( (lv_rightOperand_7_0= ruleXAssignment ) ) )? ) )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1158:1: ( ( () ( ( ruleFeatureCallID ) ) ruleOpSingleAssign ( (lv_value_3_0= ruleXAssignment ) ) ) | (this_XOrExpression_4= ruleXOrExpression ( ( ( ( () ( ( ruleOpMultiAssign ) ) ) )=> ( () ( ( ruleOpMultiAssign ) ) ) ) ( (lv_rightOperand_7_0= ruleXAssignment ) ) )? ) )
            int alt18=2;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                int LA18_1 = input.LA(2);

                if ( (LA18_1==EOF||(LA18_1>=RULE_INT && LA18_1<=RULE_ID)||(LA18_1>=16 && LA18_1<=41)||(LA18_1>=43 && LA18_1<=59)||(LA18_1>=62 && LA18_1<=64)||(LA18_1>=66 && LA18_1<=75)) ) {
                    alt18=2;
                }
                else if ( (LA18_1==15) ) {
                    alt18=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 18, 1, input);

                    throw nvae;
                }
                }
                break;
            case 71:
                {
                int LA18_2 = input.LA(2);

                if ( (LA18_2==EOF||(LA18_2>=RULE_INT && LA18_2<=RULE_ID)||(LA18_2>=16 && LA18_2<=41)||(LA18_2>=43 && LA18_2<=59)||(LA18_2>=62 && LA18_2<=64)||(LA18_2>=66 && LA18_2<=75)) ) {
                    alt18=2;
                }
                else if ( (LA18_2==15) ) {
                    alt18=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 18, 2, input);

                    throw nvae;
                }
                }
                break;
            case 72:
                {
                int LA18_3 = input.LA(2);

                if ( (LA18_3==EOF||(LA18_3>=RULE_INT && LA18_3<=RULE_ID)||(LA18_3>=16 && LA18_3<=41)||(LA18_3>=43 && LA18_3<=59)||(LA18_3>=62 && LA18_3<=64)||(LA18_3>=66 && LA18_3<=75)) ) {
                    alt18=2;
                }
                else if ( (LA18_3==15) ) {
                    alt18=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 18, 3, input);

                    throw nvae;
                }
                }
                break;
            case 73:
                {
                int LA18_4 = input.LA(2);

                if ( (LA18_4==15) ) {
                    alt18=1;
                }
                else if ( (LA18_4==EOF||(LA18_4>=RULE_INT && LA18_4<=RULE_ID)||(LA18_4>=16 && LA18_4<=41)||(LA18_4>=43 && LA18_4<=59)||(LA18_4>=62 && LA18_4<=64)||(LA18_4>=66 && LA18_4<=75)) ) {
                    alt18=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 18, 4, input);

                    throw nvae;
                }
                }
                break;
            case 74:
                {
                int LA18_5 = input.LA(2);

                if ( (LA18_5==EOF||(LA18_5>=RULE_INT && LA18_5<=RULE_ID)||(LA18_5>=16 && LA18_5<=41)||(LA18_5>=43 && LA18_5<=59)||(LA18_5>=62 && LA18_5<=64)||(LA18_5>=66 && LA18_5<=75)) ) {
                    alt18=2;
                }
                else if ( (LA18_5==15) ) {
                    alt18=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 18, 5, input);

                    throw nvae;
                }
                }
                break;
            case RULE_INT:
            case RULE_EINT:
            case RULE_STRING:
            case 23:
            case 26:
            case 27:
            case 28:
            case 29:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 58:
            case 67:
            case 75:
                {
                alt18=2;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 0, input);

                throw nvae;
            }

            switch (alt18) {
                case 1 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1158:2: ( () ( ( ruleFeatureCallID ) ) ruleOpSingleAssign ( (lv_value_3_0= ruleXAssignment ) ) )
                    {
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1158:2: ( () ( ( ruleFeatureCallID ) ) ruleOpSingleAssign ( (lv_value_3_0= ruleXAssignment ) ) )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1158:3: () ( ( ruleFeatureCallID ) ) ruleOpSingleAssign ( (lv_value_3_0= ruleXAssignment ) )
                    {
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1158:3: ()
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1159:5:
                    {
                    if ( state.backtracking==0 ) {

                              current = forceCreateModelElement(
                                  grammarAccess.getXAssignmentAccess().getXAssignmentAction_0_0(),
                                  current);

                    }

                    }

                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1164:2: ( ( ruleFeatureCallID ) )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1165:1: ( ruleFeatureCallID )
                    {
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1165:1: ( ruleFeatureCallID )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1166:3: ruleFeatureCallID
                    {
                    if ( state.backtracking==0 ) {

					if (current==null) {
			            current = createModelElement(grammarAccess.getXAssignmentRule());
			        }

                    }
                    if ( state.backtracking==0 ) {

			        newCompositeNode(grammarAccess.getXAssignmentAccess().getFeatureJvmIdentifiableElementCrossReference_0_1_0());

                    }
                    pushFollow(FOLLOW_ruleFeatureCallID_in_ruleXAssignment3119);
                    ruleFeatureCallID();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

			        afterParserOrEnumRuleCall();

                    }

                    }


                    }

                    if ( state.backtracking==0 ) {

                              newCompositeNode(grammarAccess.getXAssignmentAccess().getOpSingleAssignParserRuleCall_0_2());

                    }
                    pushFollow(FOLLOW_ruleOpSingleAssign_in_ruleXAssignment3135);
                    ruleOpSingleAssign();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              afterParserOrEnumRuleCall();

                    }
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1187:1: ( (lv_value_3_0= ruleXAssignment ) )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1188:1: (lv_value_3_0= ruleXAssignment )
                    {
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1188:1: (lv_value_3_0= ruleXAssignment )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1189:3: lv_value_3_0= ruleXAssignment
                    {
                    if ( state.backtracking==0 ) {

			        newCompositeNode(grammarAccess.getXAssignmentAccess().getValueXAssignmentParserRuleCall_0_3_0());

                    }
                    pushFollow(FOLLOW_ruleXAssignment_in_ruleXAssignment3155);
                    lv_value_3_0=ruleXAssignment();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

			        if (current==null) {
			            current = createModelElementForParent(grammarAccess.getXAssignmentRule());
			        }
					set(
						current,
						"value",
					lv_value_3_0,
					"XAssignment");
			        afterParserOrEnumRuleCall();

                    }

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1206:6: (this_XOrExpression_4= ruleXOrExpression ( ( ( ( () ( ( ruleOpMultiAssign ) ) ) )=> ( () ( ( ruleOpMultiAssign ) ) ) ) ( (lv_rightOperand_7_0= ruleXAssignment ) ) )? )
                    {
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1206:6: (this_XOrExpression_4= ruleXOrExpression ( ( ( ( () ( ( ruleOpMultiAssign ) ) ) )=> ( () ( ( ruleOpMultiAssign ) ) ) ) ( (lv_rightOperand_7_0= ruleXAssignment ) ) )? )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1207:5: this_XOrExpression_4= ruleXOrExpression ( ( ( ( () ( ( ruleOpMultiAssign ) ) ) )=> ( () ( ( ruleOpMultiAssign ) ) ) ) ( (lv_rightOperand_7_0= ruleXAssignment ) ) )?
                    {
                    if ( state.backtracking==0 ) {

                              newCompositeNode(grammarAccess.getXAssignmentAccess().getXOrExpressionParserRuleCall_1_0());

                    }
                    pushFollow(FOLLOW_ruleXOrExpression_in_ruleXAssignment3185);
                    this_XOrExpression_4=ruleXOrExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = this_XOrExpression_4;
                              afterParserOrEnumRuleCall();

                    }
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1215:1: ( ( ( ( () ( ( ruleOpMultiAssign ) ) ) )=> ( () ( ( ruleOpMultiAssign ) ) ) ) ( (lv_rightOperand_7_0= ruleXAssignment ) ) )?
                    int alt17=2;
                    alt17 = dfa17.predict(input);
                    switch (alt17) {
                        case 1 :
                            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1215:2: ( ( ( () ( ( ruleOpMultiAssign ) ) ) )=> ( () ( ( ruleOpMultiAssign ) ) ) ) ( (lv_rightOperand_7_0= ruleXAssignment ) )
                            {
                            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1215:2: ( ( ( () ( ( ruleOpMultiAssign ) ) ) )=> ( () ( ( ruleOpMultiAssign ) ) ) )
                            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1215:3: ( ( () ( ( ruleOpMultiAssign ) ) ) )=> ( () ( ( ruleOpMultiAssign ) ) )
                            {
                            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1220:6: ( () ( ( ruleOpMultiAssign ) ) )
                            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1220:7: () ( ( ruleOpMultiAssign ) )
                            {
                            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1220:7: ()
                            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1221:5:
                            {
                            if ( state.backtracking==0 ) {

                                      current = forceCreateModelElementAndSet(
                                          grammarAccess.getXAssignmentAccess().getXBinaryOperationLeftOperandAction_1_1_0_0_0(),
                                          current);

                            }

                            }

                            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1226:2: ( ( ruleOpMultiAssign ) )
                            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1227:1: ( ruleOpMultiAssign )
                            {
                            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1227:1: ( ruleOpMultiAssign )
                            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1228:3: ruleOpMultiAssign
                            {
                            if ( state.backtracking==0 ) {

						if (current==null) {
				            current = createModelElement(grammarAccess.getXAssignmentRule());
				        }

                            }
                            if ( state.backtracking==0 ) {

				        newCompositeNode(grammarAccess.getXAssignmentAccess().getFeatureJvmIdentifiableElementCrossReference_1_1_0_0_1_0());

                            }
                            pushFollow(FOLLOW_ruleOpMultiAssign_in_ruleXAssignment3238);
                            ruleOpMultiAssign();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

				        afterParserOrEnumRuleCall();

                            }

                            }


                            }


                            }


                            }

                            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1241:4: ( (lv_rightOperand_7_0= ruleXAssignment ) )
                            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1242:1: (lv_rightOperand_7_0= ruleXAssignment )
                            {
                            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1242:1: (lv_rightOperand_7_0= ruleXAssignment )
                            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1243:3: lv_rightOperand_7_0= ruleXAssignment
                            {
                            if ( state.backtracking==0 ) {

				        newCompositeNode(grammarAccess.getXAssignmentAccess().getRightOperandXAssignmentParserRuleCall_1_1_1_0());

                            }
                            pushFollow(FOLLOW_ruleXAssignment_in_ruleXAssignment3261);
                            lv_rightOperand_7_0=ruleXAssignment();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

				        if (current==null) {
				            current = createModelElementForParent(grammarAccess.getXAssignmentRule());
				        }
						set(
							current,
							"rightOperand",
						lv_rightOperand_7_0,
						"XAssignment");
				        afterParserOrEnumRuleCall();

                            }

                            }


                            }


                            }
                            break;

                    }


                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleXAssignment"


    // $ANTLR start "entryRuleOpMultiAssign"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1267:1: entryRuleOpMultiAssign returns [String current=null] : iv_ruleOpMultiAssign= ruleOpMultiAssign EOF ;
    public final String entryRuleOpMultiAssign() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleOpMultiAssign = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1268:2: (iv_ruleOpMultiAssign= ruleOpMultiAssign EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1269:2: iv_ruleOpMultiAssign= ruleOpMultiAssign EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getOpMultiAssignRule());
            }
            pushFollow(FOLLOW_ruleOpMultiAssign_in_entryRuleOpMultiAssign3301);
            iv_ruleOpMultiAssign=ruleOpMultiAssign();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleOpMultiAssign.getText();
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleOpMultiAssign3312); if (state.failed) return current;

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
    // $ANTLR end "entryRuleOpMultiAssign"


    // $ANTLR start "ruleOpMultiAssign"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1276:1: ruleOpMultiAssign returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '+=' | kw= '-=' | kw= '*=' | kw= '/=' | kw= '%=' | (kw= '<' kw= '<' kw= '=' ) | (kw= '>' (kw= '>' )? kw= '>=' ) ) ;
    public final AntlrDatatypeRuleToken ruleOpMultiAssign() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;

         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1279:28: ( (kw= '+=' | kw= '-=' | kw= '*=' | kw= '/=' | kw= '%=' | (kw= '<' kw= '<' kw= '=' ) | (kw= '>' (kw= '>' )? kw= '>=' ) ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1280:1: (kw= '+=' | kw= '-=' | kw= '*=' | kw= '/=' | kw= '%=' | (kw= '<' kw= '<' kw= '=' ) | (kw= '>' (kw= '>' )? kw= '>=' ) )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1280:1: (kw= '+=' | kw= '-=' | kw= '*=' | kw= '/=' | kw= '%=' | (kw= '<' kw= '<' kw= '=' ) | (kw= '>' (kw= '>' )? kw= '>=' ) )
            int alt20=7;
            switch ( input.LA(1) ) {
            case 43:
                {
                alt20=1;
                }
                break;
            case 44:
                {
                alt20=2;
                }
                break;
            case 45:
                {
                alt20=3;
                }
                break;
            case 46:
                {
                alt20=4;
                }
                break;
            case 47:
                {
                alt20=5;
                }
                break;
            case 23:
                {
                alt20=6;
                }
                break;
            case 22:
                {
                alt20=7;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 20, 0, input);

                throw nvae;
            }

            switch (alt20) {
                case 1 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1281:2: kw= '+='
                    {
                    kw=(Token)match(input,43,FOLLOW_43_in_ruleOpMultiAssign3350); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current.merge(kw);
                              newLeafNode(kw, grammarAccess.getOpMultiAssignAccess().getPlusSignEqualsSignKeyword_0());

                    }

                    }
                    break;
                case 2 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1288:2: kw= '-='
                    {
                    kw=(Token)match(input,44,FOLLOW_44_in_ruleOpMultiAssign3369); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current.merge(kw);
                              newLeafNode(kw, grammarAccess.getOpMultiAssignAccess().getHyphenMinusEqualsSignKeyword_1());

                    }

                    }
                    break;
                case 3 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1295:2: kw= '*='
                    {
                    kw=(Token)match(input,45,FOLLOW_45_in_ruleOpMultiAssign3388); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current.merge(kw);
                              newLeafNode(kw, grammarAccess.getOpMultiAssignAccess().getAsteriskEqualsSignKeyword_2());

                    }

                    }
                    break;
                case 4 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1302:2: kw= '/='
                    {
                    kw=(Token)match(input,46,FOLLOW_46_in_ruleOpMultiAssign3407); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current.merge(kw);
                              newLeafNode(kw, grammarAccess.getOpMultiAssignAccess().getSolidusEqualsSignKeyword_3());

                    }

                    }
                    break;
                case 5 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1309:2: kw= '%='
                    {
                    kw=(Token)match(input,47,FOLLOW_47_in_ruleOpMultiAssign3426); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current.merge(kw);
                              newLeafNode(kw, grammarAccess.getOpMultiAssignAccess().getPercentSignEqualsSignKeyword_4());

                    }

                    }
                    break;
                case 6 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1315:6: (kw= '<' kw= '<' kw= '=' )
                    {
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1315:6: (kw= '<' kw= '<' kw= '=' )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1316:2: kw= '<' kw= '<' kw= '='
                    {
                    kw=(Token)match(input,23,FOLLOW_23_in_ruleOpMultiAssign3446); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current.merge(kw);
                              newLeafNode(kw, grammarAccess.getOpMultiAssignAccess().getLessThanSignKeyword_5_0());

                    }
                    kw=(Token)match(input,23,FOLLOW_23_in_ruleOpMultiAssign3459); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current.merge(kw);
                              newLeafNode(kw, grammarAccess.getOpMultiAssignAccess().getLessThanSignKeyword_5_1());

                    }
                    kw=(Token)match(input,17,FOLLOW_17_in_ruleOpMultiAssign3472); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current.merge(kw);
                              newLeafNode(kw, grammarAccess.getOpMultiAssignAccess().getEqualsSignKeyword_5_2());

                    }

                    }


                    }
                    break;
                case 7 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1334:6: (kw= '>' (kw= '>' )? kw= '>=' )
                    {
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1334:6: (kw= '>' (kw= '>' )? kw= '>=' )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1335:2: kw= '>' (kw= '>' )? kw= '>='
                    {
                    kw=(Token)match(input,22,FOLLOW_22_in_ruleOpMultiAssign3493); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current.merge(kw);
                              newLeafNode(kw, grammarAccess.getOpMultiAssignAccess().getGreaterThanSignKeyword_6_0());

                    }
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1340:1: (kw= '>' )?
                    int alt19=2;
                    int LA19_0 = input.LA(1);

                    if ( (LA19_0==22) ) {
                        alt19=1;
                    }
                    switch (alt19) {
                        case 1 :
                            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1341:2: kw= '>'
                            {
                            kw=(Token)match(input,22,FOLLOW_22_in_ruleOpMultiAssign3507); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                                      current.merge(kw);
                                      newLeafNode(kw, grammarAccess.getOpMultiAssignAccess().getGreaterThanSignKeyword_6_1());

                            }

                            }
                            break;

                    }

                    kw=(Token)match(input,20,FOLLOW_20_in_ruleOpMultiAssign3522); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current.merge(kw);
                              newLeafNode(kw, grammarAccess.getOpMultiAssignAccess().getGreaterThanSignEqualsSignKeyword_6_2());

                    }

                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleOpMultiAssign"


    // $ANTLR start "entryRuleXOrExpression"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1360:1: entryRuleXOrExpression returns [EObject current=null] : iv_ruleXOrExpression= ruleXOrExpression EOF ;
    public final EObject entryRuleXOrExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXOrExpression = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1361:2: (iv_ruleXOrExpression= ruleXOrExpression EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1362:2: iv_ruleXOrExpression= ruleXOrExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXOrExpressionRule());
            }
            pushFollow(FOLLOW_ruleXOrExpression_in_entryRuleXOrExpression3563);
            iv_ruleXOrExpression=ruleXOrExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXOrExpression;
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleXOrExpression3573); if (state.failed) return current;

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
    // $ANTLR end "entryRuleXOrExpression"


    // $ANTLR start "ruleXOrExpression"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1369:1: ruleXOrExpression returns [EObject current=null] : (this_XAndExpression_0= ruleXAndExpression ( ( ( ( () ( ( ruleOpOr ) ) ) )=> ( () ( ( ruleOpOr ) ) ) ) ( (lv_rightOperand_3_0= ruleXAndExpression ) ) )* ) ;
    public final EObject ruleXOrExpression() throws RecognitionException {
        EObject current = null;

        EObject this_XAndExpression_0 = null;

        EObject lv_rightOperand_3_0 = null;


         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1372:28: ( (this_XAndExpression_0= ruleXAndExpression ( ( ( ( () ( ( ruleOpOr ) ) ) )=> ( () ( ( ruleOpOr ) ) ) ) ( (lv_rightOperand_3_0= ruleXAndExpression ) ) )* ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1373:1: (this_XAndExpression_0= ruleXAndExpression ( ( ( ( () ( ( ruleOpOr ) ) ) )=> ( () ( ( ruleOpOr ) ) ) ) ( (lv_rightOperand_3_0= ruleXAndExpression ) ) )* )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1373:1: (this_XAndExpression_0= ruleXAndExpression ( ( ( ( () ( ( ruleOpOr ) ) ) )=> ( () ( ( ruleOpOr ) ) ) ) ( (lv_rightOperand_3_0= ruleXAndExpression ) ) )* )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1374:5: this_XAndExpression_0= ruleXAndExpression ( ( ( ( () ( ( ruleOpOr ) ) ) )=> ( () ( ( ruleOpOr ) ) ) ) ( (lv_rightOperand_3_0= ruleXAndExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

                      newCompositeNode(grammarAccess.getXOrExpressionAccess().getXAndExpressionParserRuleCall_0());

            }
            pushFollow(FOLLOW_ruleXAndExpression_in_ruleXOrExpression3620);
            this_XAndExpression_0=ruleXAndExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current = this_XAndExpression_0;
                      afterParserOrEnumRuleCall();

            }
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1382:1: ( ( ( ( () ( ( ruleOpOr ) ) ) )=> ( () ( ( ruleOpOr ) ) ) ) ( (lv_rightOperand_3_0= ruleXAndExpression ) ) )*
            loop21:
            do {
                int alt21=2;
                int LA21_0 = input.LA(1);

                if ( (LA21_0==30) ) {
                    int LA21_2 = input.LA(2);

                    if ( (synpred4_InternalDSEL()) ) {
                        alt21=1;
                    }


                }
                else if ( (LA21_0==31) ) {
                    int LA21_3 = input.LA(2);

                    if ( (synpred4_InternalDSEL()) ) {
                        alt21=1;
                    }


                }


                switch (alt21) {
		case 1 :
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1382:2: ( ( ( () ( ( ruleOpOr ) ) ) )=> ( () ( ( ruleOpOr ) ) ) ) ( (lv_rightOperand_3_0= ruleXAndExpression ) )
		    {
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1382:2: ( ( ( () ( ( ruleOpOr ) ) ) )=> ( () ( ( ruleOpOr ) ) ) )
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1382:3: ( ( () ( ( ruleOpOr ) ) ) )=> ( () ( ( ruleOpOr ) ) )
		    {
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1387:6: ( () ( ( ruleOpOr ) ) )
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1387:7: () ( ( ruleOpOr ) )
		    {
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1387:7: ()
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1388:5:
		    {
		    if ( state.backtracking==0 ) {

		              current = forceCreateModelElementAndSet(
		                  grammarAccess.getXOrExpressionAccess().getXBinaryOperationLeftOperandAction_1_0_0_0(),
		                  current);

		    }

		    }

		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1393:2: ( ( ruleOpOr ) )
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1394:1: ( ruleOpOr )
		    {
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1394:1: ( ruleOpOr )
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1395:3: ruleOpOr
		    {
		    if ( state.backtracking==0 ) {

					if (current==null) {
			            current = createModelElement(grammarAccess.getXOrExpressionRule());
			        }

		    }
		    if ( state.backtracking==0 ) {

			        newCompositeNode(grammarAccess.getXOrExpressionAccess().getFeatureJvmIdentifiableElementCrossReference_1_0_0_1_0());

		    }
		    pushFollow(FOLLOW_ruleOpOr_in_ruleXOrExpression3673);
		    ruleOpOr();

		    state._fsp--;
		    if (state.failed) return current;
		    if ( state.backtracking==0 ) {

			        afterParserOrEnumRuleCall();

		    }

		    }


		    }


		    }


		    }

		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1408:4: ( (lv_rightOperand_3_0= ruleXAndExpression ) )
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1409:1: (lv_rightOperand_3_0= ruleXAndExpression )
		    {
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1409:1: (lv_rightOperand_3_0= ruleXAndExpression )
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1410:3: lv_rightOperand_3_0= ruleXAndExpression
		    {
		    if ( state.backtracking==0 ) {

			        newCompositeNode(grammarAccess.getXOrExpressionAccess().getRightOperandXAndExpressionParserRuleCall_1_1_0());

		    }
		    pushFollow(FOLLOW_ruleXAndExpression_in_ruleXOrExpression3696);
		    lv_rightOperand_3_0=ruleXAndExpression();

		    state._fsp--;
		    if (state.failed) return current;
		    if ( state.backtracking==0 ) {

			        if (current==null) {
			            current = createModelElementForParent(grammarAccess.getXOrExpressionRule());
			        }
					set(
						current,
						"rightOperand",
					lv_rightOperand_3_0,
					"XAndExpression");
			        afterParserOrEnumRuleCall();

		    }

		    }


		    }


		    }
		    break;

		default :
		    break loop21;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleXOrExpression"


    // $ANTLR start "entryRuleXAndExpression"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1434:1: entryRuleXAndExpression returns [EObject current=null] : iv_ruleXAndExpression= ruleXAndExpression EOF ;
    public final EObject entryRuleXAndExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXAndExpression = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1435:2: (iv_ruleXAndExpression= ruleXAndExpression EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1436:2: iv_ruleXAndExpression= ruleXAndExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXAndExpressionRule());
            }
            pushFollow(FOLLOW_ruleXAndExpression_in_entryRuleXAndExpression3734);
            iv_ruleXAndExpression=ruleXAndExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXAndExpression;
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleXAndExpression3744); if (state.failed) return current;

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
    // $ANTLR end "entryRuleXAndExpression"


    // $ANTLR start "ruleXAndExpression"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1443:1: ruleXAndExpression returns [EObject current=null] : (this_XEqualityExpression_0= ruleXEqualityExpression ( ( ( ( () ( ( ruleOpAnd ) ) ) )=> ( () ( ( ruleOpAnd ) ) ) ) ( (lv_rightOperand_3_0= ruleXEqualityExpression ) ) )* ) ;
    public final EObject ruleXAndExpression() throws RecognitionException {
        EObject current = null;

        EObject this_XEqualityExpression_0 = null;

        EObject lv_rightOperand_3_0 = null;


         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1446:28: ( (this_XEqualityExpression_0= ruleXEqualityExpression ( ( ( ( () ( ( ruleOpAnd ) ) ) )=> ( () ( ( ruleOpAnd ) ) ) ) ( (lv_rightOperand_3_0= ruleXEqualityExpression ) ) )* ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1447:1: (this_XEqualityExpression_0= ruleXEqualityExpression ( ( ( ( () ( ( ruleOpAnd ) ) ) )=> ( () ( ( ruleOpAnd ) ) ) ) ( (lv_rightOperand_3_0= ruleXEqualityExpression ) ) )* )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1447:1: (this_XEqualityExpression_0= ruleXEqualityExpression ( ( ( ( () ( ( ruleOpAnd ) ) ) )=> ( () ( ( ruleOpAnd ) ) ) ) ( (lv_rightOperand_3_0= ruleXEqualityExpression ) ) )* )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1448:5: this_XEqualityExpression_0= ruleXEqualityExpression ( ( ( ( () ( ( ruleOpAnd ) ) ) )=> ( () ( ( ruleOpAnd ) ) ) ) ( (lv_rightOperand_3_0= ruleXEqualityExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

                      newCompositeNode(grammarAccess.getXAndExpressionAccess().getXEqualityExpressionParserRuleCall_0());

            }
            pushFollow(FOLLOW_ruleXEqualityExpression_in_ruleXAndExpression3791);
            this_XEqualityExpression_0=ruleXEqualityExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current = this_XEqualityExpression_0;
                      afterParserOrEnumRuleCall();

            }
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1456:1: ( ( ( ( () ( ( ruleOpAnd ) ) ) )=> ( () ( ( ruleOpAnd ) ) ) ) ( (lv_rightOperand_3_0= ruleXEqualityExpression ) ) )*
            loop22:
            do {
                int alt22=2;
                int LA22_0 = input.LA(1);

                if ( (LA22_0==32) ) {
                    int LA22_2 = input.LA(2);

                    if ( (synpred5_InternalDSEL()) ) {
                        alt22=1;
                    }


                }
                else if ( (LA22_0==33) ) {
                    int LA22_3 = input.LA(2);

                    if ( (synpred5_InternalDSEL()) ) {
                        alt22=1;
                    }


                }


                switch (alt22) {
		case 1 :
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1456:2: ( ( ( () ( ( ruleOpAnd ) ) ) )=> ( () ( ( ruleOpAnd ) ) ) ) ( (lv_rightOperand_3_0= ruleXEqualityExpression ) )
		    {
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1456:2: ( ( ( () ( ( ruleOpAnd ) ) ) )=> ( () ( ( ruleOpAnd ) ) ) )
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1456:3: ( ( () ( ( ruleOpAnd ) ) ) )=> ( () ( ( ruleOpAnd ) ) )
		    {
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1461:6: ( () ( ( ruleOpAnd ) ) )
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1461:7: () ( ( ruleOpAnd ) )
		    {
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1461:7: ()
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1462:5:
		    {
		    if ( state.backtracking==0 ) {

		              current = forceCreateModelElementAndSet(
		                  grammarAccess.getXAndExpressionAccess().getXBinaryOperationLeftOperandAction_1_0_0_0(),
		                  current);

		    }

		    }

		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1467:2: ( ( ruleOpAnd ) )
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1468:1: ( ruleOpAnd )
		    {
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1468:1: ( ruleOpAnd )
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1469:3: ruleOpAnd
		    {
		    if ( state.backtracking==0 ) {

					if (current==null) {
			            current = createModelElement(grammarAccess.getXAndExpressionRule());
			        }

		    }
		    if ( state.backtracking==0 ) {

			        newCompositeNode(grammarAccess.getXAndExpressionAccess().getFeatureJvmIdentifiableElementCrossReference_1_0_0_1_0());

		    }
		    pushFollow(FOLLOW_ruleOpAnd_in_ruleXAndExpression3844);
		    ruleOpAnd();

		    state._fsp--;
		    if (state.failed) return current;
		    if ( state.backtracking==0 ) {

			        afterParserOrEnumRuleCall();

		    }

		    }


		    }


		    }


		    }

		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1482:4: ( (lv_rightOperand_3_0= ruleXEqualityExpression ) )
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1483:1: (lv_rightOperand_3_0= ruleXEqualityExpression )
		    {
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1483:1: (lv_rightOperand_3_0= ruleXEqualityExpression )
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1484:3: lv_rightOperand_3_0= ruleXEqualityExpression
		    {
		    if ( state.backtracking==0 ) {

			        newCompositeNode(grammarAccess.getXAndExpressionAccess().getRightOperandXEqualityExpressionParserRuleCall_1_1_0());

		    }
		    pushFollow(FOLLOW_ruleXEqualityExpression_in_ruleXAndExpression3867);
		    lv_rightOperand_3_0=ruleXEqualityExpression();

		    state._fsp--;
		    if (state.failed) return current;
		    if ( state.backtracking==0 ) {

			        if (current==null) {
			            current = createModelElementForParent(grammarAccess.getXAndExpressionRule());
			        }
					set(
						current,
						"rightOperand",
					lv_rightOperand_3_0,
					"XEqualityExpression");
			        afterParserOrEnumRuleCall();

		    }

		    }


		    }


		    }
		    break;

		default :
		    break loop22;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleXAndExpression"


    // $ANTLR start "entryRuleXEqualityExpression"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1508:1: entryRuleXEqualityExpression returns [EObject current=null] : iv_ruleXEqualityExpression= ruleXEqualityExpression EOF ;
    public final EObject entryRuleXEqualityExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXEqualityExpression = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1509:2: (iv_ruleXEqualityExpression= ruleXEqualityExpression EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1510:2: iv_ruleXEqualityExpression= ruleXEqualityExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXEqualityExpressionRule());
            }
            pushFollow(FOLLOW_ruleXEqualityExpression_in_entryRuleXEqualityExpression3905);
            iv_ruleXEqualityExpression=ruleXEqualityExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXEqualityExpression;
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleXEqualityExpression3915); if (state.failed) return current;

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
    // $ANTLR end "entryRuleXEqualityExpression"


    // $ANTLR start "ruleXEqualityExpression"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1517:1: ruleXEqualityExpression returns [EObject current=null] : (this_XRelationalExpression_0= ruleXRelationalExpression ( ( ( ( () ( ( ruleOpEquality ) ) ) )=> ( () ( ( ruleOpEquality ) ) ) ) ( (lv_rightOperand_3_0= ruleXRelationalExpression ) ) )* ) ;
    public final EObject ruleXEqualityExpression() throws RecognitionException {
        EObject current = null;

        EObject this_XRelationalExpression_0 = null;

        EObject lv_rightOperand_3_0 = null;


         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1520:28: ( (this_XRelationalExpression_0= ruleXRelationalExpression ( ( ( ( () ( ( ruleOpEquality ) ) ) )=> ( () ( ( ruleOpEquality ) ) ) ) ( (lv_rightOperand_3_0= ruleXRelationalExpression ) ) )* ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1521:1: (this_XRelationalExpression_0= ruleXRelationalExpression ( ( ( ( () ( ( ruleOpEquality ) ) ) )=> ( () ( ( ruleOpEquality ) ) ) ) ( (lv_rightOperand_3_0= ruleXRelationalExpression ) ) )* )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1521:1: (this_XRelationalExpression_0= ruleXRelationalExpression ( ( ( ( () ( ( ruleOpEquality ) ) ) )=> ( () ( ( ruleOpEquality ) ) ) ) ( (lv_rightOperand_3_0= ruleXRelationalExpression ) ) )* )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1522:5: this_XRelationalExpression_0= ruleXRelationalExpression ( ( ( ( () ( ( ruleOpEquality ) ) ) )=> ( () ( ( ruleOpEquality ) ) ) ) ( (lv_rightOperand_3_0= ruleXRelationalExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

                      newCompositeNode(grammarAccess.getXEqualityExpressionAccess().getXRelationalExpressionParserRuleCall_0());

            }
            pushFollow(FOLLOW_ruleXRelationalExpression_in_ruleXEqualityExpression3962);
            this_XRelationalExpression_0=ruleXRelationalExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current = this_XRelationalExpression_0;
                      afterParserOrEnumRuleCall();

            }
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1530:1: ( ( ( ( () ( ( ruleOpEquality ) ) ) )=> ( () ( ( ruleOpEquality ) ) ) ) ( (lv_rightOperand_3_0= ruleXRelationalExpression ) ) )*
            loop23:
            do {
                int alt23=2;
                switch ( input.LA(1) ) {
                case 16:
                    {
                    int LA23_2 = input.LA(2);

                    if ( (synpred6_InternalDSEL()) ) {
                        alt23=1;
                    }


                    }
                    break;
                case 17:
                    {
                    int LA23_3 = input.LA(2);

                    if ( (synpred6_InternalDSEL()) ) {
                        alt23=1;
                    }


                    }
                    break;
                case 18:
                    {
                    int LA23_4 = input.LA(2);

                    if ( (synpred6_InternalDSEL()) ) {
                        alt23=1;
                    }


                    }
                    break;
                case 19:
                    {
                    int LA23_5 = input.LA(2);

                    if ( (synpred6_InternalDSEL()) ) {
                        alt23=1;
                    }


                    }
                    break;

                }

                switch (alt23) {
		case 1 :
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1530:2: ( ( ( () ( ( ruleOpEquality ) ) ) )=> ( () ( ( ruleOpEquality ) ) ) ) ( (lv_rightOperand_3_0= ruleXRelationalExpression ) )
		    {
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1530:2: ( ( ( () ( ( ruleOpEquality ) ) ) )=> ( () ( ( ruleOpEquality ) ) ) )
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1530:3: ( ( () ( ( ruleOpEquality ) ) ) )=> ( () ( ( ruleOpEquality ) ) )
		    {
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1535:6: ( () ( ( ruleOpEquality ) ) )
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1535:7: () ( ( ruleOpEquality ) )
		    {
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1535:7: ()
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1536:5:
		    {
		    if ( state.backtracking==0 ) {

		              current = forceCreateModelElementAndSet(
		                  grammarAccess.getXEqualityExpressionAccess().getXBinaryOperationLeftOperandAction_1_0_0_0(),
		                  current);

		    }

		    }

		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1541:2: ( ( ruleOpEquality ) )
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1542:1: ( ruleOpEquality )
		    {
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1542:1: ( ruleOpEquality )
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1543:3: ruleOpEquality
		    {
		    if ( state.backtracking==0 ) {

					if (current==null) {
			            current = createModelElement(grammarAccess.getXEqualityExpressionRule());
			        }

		    }
		    if ( state.backtracking==0 ) {

			        newCompositeNode(grammarAccess.getXEqualityExpressionAccess().getFeatureJvmIdentifiableElementCrossReference_1_0_0_1_0());

		    }
		    pushFollow(FOLLOW_ruleOpEquality_in_ruleXEqualityExpression4015);
		    ruleOpEquality();

		    state._fsp--;
		    if (state.failed) return current;
		    if ( state.backtracking==0 ) {

			        afterParserOrEnumRuleCall();

		    }

		    }


		    }


		    }


		    }

		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1556:4: ( (lv_rightOperand_3_0= ruleXRelationalExpression ) )
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1557:1: (lv_rightOperand_3_0= ruleXRelationalExpression )
		    {
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1557:1: (lv_rightOperand_3_0= ruleXRelationalExpression )
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1558:3: lv_rightOperand_3_0= ruleXRelationalExpression
		    {
		    if ( state.backtracking==0 ) {

			        newCompositeNode(grammarAccess.getXEqualityExpressionAccess().getRightOperandXRelationalExpressionParserRuleCall_1_1_0());

		    }
		    pushFollow(FOLLOW_ruleXRelationalExpression_in_ruleXEqualityExpression4038);
		    lv_rightOperand_3_0=ruleXRelationalExpression();

		    state._fsp--;
		    if (state.failed) return current;
		    if ( state.backtracking==0 ) {

			        if (current==null) {
			            current = createModelElementForParent(grammarAccess.getXEqualityExpressionRule());
			        }
					set(
						current,
						"rightOperand",
					lv_rightOperand_3_0,
					"XRelationalExpression");
			        afterParserOrEnumRuleCall();

		    }

		    }


		    }


		    }
		    break;

		default :
		    break loop23;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleXEqualityExpression"


    // $ANTLR start "entryRuleXAdditiveExpression"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1582:1: entryRuleXAdditiveExpression returns [EObject current=null] : iv_ruleXAdditiveExpression= ruleXAdditiveExpression EOF ;
    public final EObject entryRuleXAdditiveExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXAdditiveExpression = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1583:2: (iv_ruleXAdditiveExpression= ruleXAdditiveExpression EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1584:2: iv_ruleXAdditiveExpression= ruleXAdditiveExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXAdditiveExpressionRule());
            }
            pushFollow(FOLLOW_ruleXAdditiveExpression_in_entryRuleXAdditiveExpression4076);
            iv_ruleXAdditiveExpression=ruleXAdditiveExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXAdditiveExpression;
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleXAdditiveExpression4086); if (state.failed) return current;

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
    // $ANTLR end "entryRuleXAdditiveExpression"


    // $ANTLR start "ruleXAdditiveExpression"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1591:1: ruleXAdditiveExpression returns [EObject current=null] : (this_XMultiplicativeExpression_0= ruleXMultiplicativeExpression ( ( ( ( () ( ( ruleOpAdd ) ) ) )=> ( () ( ( ruleOpAdd ) ) ) ) ( (lv_rightOperand_3_0= ruleXMultiplicativeExpression ) ) )* ) ;
    public final EObject ruleXAdditiveExpression() throws RecognitionException {
        EObject current = null;

        EObject this_XMultiplicativeExpression_0 = null;

        EObject lv_rightOperand_3_0 = null;


         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1594:28: ( (this_XMultiplicativeExpression_0= ruleXMultiplicativeExpression ( ( ( ( () ( ( ruleOpAdd ) ) ) )=> ( () ( ( ruleOpAdd ) ) ) ) ( (lv_rightOperand_3_0= ruleXMultiplicativeExpression ) ) )* ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1595:1: (this_XMultiplicativeExpression_0= ruleXMultiplicativeExpression ( ( ( ( () ( ( ruleOpAdd ) ) ) )=> ( () ( ( ruleOpAdd ) ) ) ) ( (lv_rightOperand_3_0= ruleXMultiplicativeExpression ) ) )* )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1595:1: (this_XMultiplicativeExpression_0= ruleXMultiplicativeExpression ( ( ( ( () ( ( ruleOpAdd ) ) ) )=> ( () ( ( ruleOpAdd ) ) ) ) ( (lv_rightOperand_3_0= ruleXMultiplicativeExpression ) ) )* )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1596:5: this_XMultiplicativeExpression_0= ruleXMultiplicativeExpression ( ( ( ( () ( ( ruleOpAdd ) ) ) )=> ( () ( ( ruleOpAdd ) ) ) ) ( (lv_rightOperand_3_0= ruleXMultiplicativeExpression ) ) )*
            {
            if ( state.backtracking==0 ) {

                      newCompositeNode(grammarAccess.getXAdditiveExpressionAccess().getXMultiplicativeExpressionParserRuleCall_0());

            }
            pushFollow(FOLLOW_ruleXMultiplicativeExpression_in_ruleXAdditiveExpression4133);
            this_XMultiplicativeExpression_0=ruleXMultiplicativeExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current = this_XMultiplicativeExpression_0;
                      afterParserOrEnumRuleCall();

            }
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1604:1: ( ( ( ( () ( ( ruleOpAdd ) ) ) )=> ( () ( ( ruleOpAdd ) ) ) ) ( (lv_rightOperand_3_0= ruleXMultiplicativeExpression ) ) )*
            loop24:
            do {
                int alt24=2;
                int LA24_0 = input.LA(1);

                if ( (LA24_0==28) ) {
                    int LA24_2 = input.LA(2);

                    if ( (synpred7_InternalDSEL()) ) {
                        alt24=1;
                    }


                }
                else if ( (LA24_0==27) ) {
                    int LA24_3 = input.LA(2);

                    if ( (synpred7_InternalDSEL()) ) {
                        alt24=1;
                    }


                }


                switch (alt24) {
		case 1 :
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1604:2: ( ( ( () ( ( ruleOpAdd ) ) ) )=> ( () ( ( ruleOpAdd ) ) ) ) ( (lv_rightOperand_3_0= ruleXMultiplicativeExpression ) )
		    {
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1604:2: ( ( ( () ( ( ruleOpAdd ) ) ) )=> ( () ( ( ruleOpAdd ) ) ) )
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1604:3: ( ( () ( ( ruleOpAdd ) ) ) )=> ( () ( ( ruleOpAdd ) ) )
		    {
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1609:6: ( () ( ( ruleOpAdd ) ) )
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1609:7: () ( ( ruleOpAdd ) )
		    {
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1609:7: ()
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1610:5:
		    {
		    if ( state.backtracking==0 ) {

		              current = forceCreateModelElementAndSet(
		                  grammarAccess.getXAdditiveExpressionAccess().getXBinaryOperationLeftOperandAction_1_0_0_0(),
		                  current);

		    }

		    }

		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1615:2: ( ( ruleOpAdd ) )
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1616:1: ( ruleOpAdd )
		    {
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1616:1: ( ruleOpAdd )
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1617:3: ruleOpAdd
		    {
		    if ( state.backtracking==0 ) {

					if (current==null) {
			            current = createModelElement(grammarAccess.getXAdditiveExpressionRule());
			        }

		    }
		    if ( state.backtracking==0 ) {

			        newCompositeNode(grammarAccess.getXAdditiveExpressionAccess().getFeatureJvmIdentifiableElementCrossReference_1_0_0_1_0());

		    }
		    pushFollow(FOLLOW_ruleOpAdd_in_ruleXAdditiveExpression4186);
		    ruleOpAdd();

		    state._fsp--;
		    if (state.failed) return current;
		    if ( state.backtracking==0 ) {

			        afterParserOrEnumRuleCall();

		    }

		    }


		    }


		    }


		    }

		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1630:4: ( (lv_rightOperand_3_0= ruleXMultiplicativeExpression ) )
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1631:1: (lv_rightOperand_3_0= ruleXMultiplicativeExpression )
		    {
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1631:1: (lv_rightOperand_3_0= ruleXMultiplicativeExpression )
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1632:3: lv_rightOperand_3_0= ruleXMultiplicativeExpression
		    {
		    if ( state.backtracking==0 ) {

			        newCompositeNode(grammarAccess.getXAdditiveExpressionAccess().getRightOperandXMultiplicativeExpressionParserRuleCall_1_1_0());

		    }
		    pushFollow(FOLLOW_ruleXMultiplicativeExpression_in_ruleXAdditiveExpression4209);
		    lv_rightOperand_3_0=ruleXMultiplicativeExpression();

		    state._fsp--;
		    if (state.failed) return current;
		    if ( state.backtracking==0 ) {

			        if (current==null) {
			            current = createModelElementForParent(grammarAccess.getXAdditiveExpressionRule());
			        }
					set(
						current,
						"rightOperand",
					lv_rightOperand_3_0,
					"XMultiplicativeExpression");
			        afterParserOrEnumRuleCall();

		    }

		    }


		    }


		    }
		    break;

		default :
		    break loop24;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleXAdditiveExpression"


    // $ANTLR start "entryRuleOpAdd"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1656:1: entryRuleOpAdd returns [String current=null] : iv_ruleOpAdd= ruleOpAdd EOF ;
    public final String entryRuleOpAdd() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleOpAdd = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1657:2: (iv_ruleOpAdd= ruleOpAdd EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1658:2: iv_ruleOpAdd= ruleOpAdd EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getOpAddRule());
            }
            pushFollow(FOLLOW_ruleOpAdd_in_entryRuleOpAdd4248);
            iv_ruleOpAdd=ruleOpAdd();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleOpAdd.getText();
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleOpAdd4259); if (state.failed) return current;

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
    // $ANTLR end "entryRuleOpAdd"


    // $ANTLR start "ruleOpAdd"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1665:1: ruleOpAdd returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '+' | kw= '-' ) ;
    public final AntlrDatatypeRuleToken ruleOpAdd() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;

         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1668:28: ( (kw= '+' | kw= '-' ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1669:1: (kw= '+' | kw= '-' )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1669:1: (kw= '+' | kw= '-' )
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==28) ) {
                alt25=1;
            }
            else if ( (LA25_0==27) ) {
                alt25=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 25, 0, input);

                throw nvae;
            }
            switch (alt25) {
                case 1 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1670:2: kw= '+'
                    {
                    kw=(Token)match(input,28,FOLLOW_28_in_ruleOpAdd4297); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current.merge(kw);
                              newLeafNode(kw, grammarAccess.getOpAddAccess().getPlusSignKeyword_0());

                    }

                    }
                    break;
                case 2 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1677:2: kw= '-'
                    {
                    kw=(Token)match(input,27,FOLLOW_27_in_ruleOpAdd4316); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current.merge(kw);
                              newLeafNode(kw, grammarAccess.getOpAddAccess().getHyphenMinusKeyword_1());

                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleOpAdd"


    // $ANTLR start "entryRuleXMultiplicativeExpression"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1690:1: entryRuleXMultiplicativeExpression returns [EObject current=null] : iv_ruleXMultiplicativeExpression= ruleXMultiplicativeExpression EOF ;
    public final EObject entryRuleXMultiplicativeExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXMultiplicativeExpression = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1691:2: (iv_ruleXMultiplicativeExpression= ruleXMultiplicativeExpression EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1692:2: iv_ruleXMultiplicativeExpression= ruleXMultiplicativeExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXMultiplicativeExpressionRule());
            }
            pushFollow(FOLLOW_ruleXMultiplicativeExpression_in_entryRuleXMultiplicativeExpression4356);
            iv_ruleXMultiplicativeExpression=ruleXMultiplicativeExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXMultiplicativeExpression;
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleXMultiplicativeExpression4366); if (state.failed) return current;

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
    // $ANTLR end "entryRuleXMultiplicativeExpression"


    // $ANTLR start "ruleXMultiplicativeExpression"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1699:1: ruleXMultiplicativeExpression returns [EObject current=null] : (this_XUnaryOperation_0= ruleXUnaryOperation ( ( ( ( () ( ( ruleOpMulti ) ) ) )=> ( () ( ( ruleOpMulti ) ) ) ) ( (lv_rightOperand_3_0= ruleXUnaryOperation ) ) )* ) ;
    public final EObject ruleXMultiplicativeExpression() throws RecognitionException {
        EObject current = null;

        EObject this_XUnaryOperation_0 = null;

        EObject lv_rightOperand_3_0 = null;


         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1702:28: ( (this_XUnaryOperation_0= ruleXUnaryOperation ( ( ( ( () ( ( ruleOpMulti ) ) ) )=> ( () ( ( ruleOpMulti ) ) ) ) ( (lv_rightOperand_3_0= ruleXUnaryOperation ) ) )* ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1703:1: (this_XUnaryOperation_0= ruleXUnaryOperation ( ( ( ( () ( ( ruleOpMulti ) ) ) )=> ( () ( ( ruleOpMulti ) ) ) ) ( (lv_rightOperand_3_0= ruleXUnaryOperation ) ) )* )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1703:1: (this_XUnaryOperation_0= ruleXUnaryOperation ( ( ( ( () ( ( ruleOpMulti ) ) ) )=> ( () ( ( ruleOpMulti ) ) ) ) ( (lv_rightOperand_3_0= ruleXUnaryOperation ) ) )* )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1704:5: this_XUnaryOperation_0= ruleXUnaryOperation ( ( ( ( () ( ( ruleOpMulti ) ) ) )=> ( () ( ( ruleOpMulti ) ) ) ) ( (lv_rightOperand_3_0= ruleXUnaryOperation ) ) )*
            {
            if ( state.backtracking==0 ) {

                      newCompositeNode(grammarAccess.getXMultiplicativeExpressionAccess().getXUnaryOperationParserRuleCall_0());

            }
            pushFollow(FOLLOW_ruleXUnaryOperation_in_ruleXMultiplicativeExpression4413);
            this_XUnaryOperation_0=ruleXUnaryOperation();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current = this_XUnaryOperation_0;
                      afterParserOrEnumRuleCall();

            }
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1712:1: ( ( ( ( () ( ( ruleOpMulti ) ) ) )=> ( () ( ( ruleOpMulti ) ) ) ) ( (lv_rightOperand_3_0= ruleXUnaryOperation ) ) )*
            loop26:
            do {
                int alt26=2;
                switch ( input.LA(1) ) {
                case 48:
                    {
                    int LA26_2 = input.LA(2);

                    if ( (synpred8_InternalDSEL()) ) {
                        alt26=1;
                    }


                    }
                    break;
                case 49:
                    {
                    int LA26_3 = input.LA(2);

                    if ( (synpred8_InternalDSEL()) ) {
                        alt26=1;
                    }


                    }
                    break;
                case 50:
                    {
                    int LA26_4 = input.LA(2);

                    if ( (synpred8_InternalDSEL()) ) {
                        alt26=1;
                    }


                    }
                    break;
                case 51:
                    {
                    int LA26_5 = input.LA(2);

                    if ( (synpred8_InternalDSEL()) ) {
                        alt26=1;
                    }


                    }
                    break;

                }

                switch (alt26) {
		case 1 :
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1712:2: ( ( ( () ( ( ruleOpMulti ) ) ) )=> ( () ( ( ruleOpMulti ) ) ) ) ( (lv_rightOperand_3_0= ruleXUnaryOperation ) )
		    {
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1712:2: ( ( ( () ( ( ruleOpMulti ) ) ) )=> ( () ( ( ruleOpMulti ) ) ) )
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1712:3: ( ( () ( ( ruleOpMulti ) ) ) )=> ( () ( ( ruleOpMulti ) ) )
		    {
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1717:6: ( () ( ( ruleOpMulti ) ) )
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1717:7: () ( ( ruleOpMulti ) )
		    {
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1717:7: ()
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1718:5:
		    {
		    if ( state.backtracking==0 ) {

		              current = forceCreateModelElementAndSet(
		                  grammarAccess.getXMultiplicativeExpressionAccess().getXBinaryOperationLeftOperandAction_1_0_0_0(),
		                  current);

		    }

		    }

		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1723:2: ( ( ruleOpMulti ) )
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1724:1: ( ruleOpMulti )
		    {
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1724:1: ( ruleOpMulti )
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1725:3: ruleOpMulti
		    {
		    if ( state.backtracking==0 ) {

					if (current==null) {
			            current = createModelElement(grammarAccess.getXMultiplicativeExpressionRule());
			        }

		    }
		    if ( state.backtracking==0 ) {

			        newCompositeNode(grammarAccess.getXMultiplicativeExpressionAccess().getFeatureJvmIdentifiableElementCrossReference_1_0_0_1_0());

		    }
		    pushFollow(FOLLOW_ruleOpMulti_in_ruleXMultiplicativeExpression4466);
		    ruleOpMulti();

		    state._fsp--;
		    if (state.failed) return current;
		    if ( state.backtracking==0 ) {

			        afterParserOrEnumRuleCall();

		    }

		    }


		    }


		    }


		    }

		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1738:4: ( (lv_rightOperand_3_0= ruleXUnaryOperation ) )
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1739:1: (lv_rightOperand_3_0= ruleXUnaryOperation )
		    {
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1739:1: (lv_rightOperand_3_0= ruleXUnaryOperation )
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1740:3: lv_rightOperand_3_0= ruleXUnaryOperation
		    {
		    if ( state.backtracking==0 ) {

			        newCompositeNode(grammarAccess.getXMultiplicativeExpressionAccess().getRightOperandXUnaryOperationParserRuleCall_1_1_0());

		    }
		    pushFollow(FOLLOW_ruleXUnaryOperation_in_ruleXMultiplicativeExpression4489);
		    lv_rightOperand_3_0=ruleXUnaryOperation();

		    state._fsp--;
		    if (state.failed) return current;
		    if ( state.backtracking==0 ) {

			        if (current==null) {
			            current = createModelElementForParent(grammarAccess.getXMultiplicativeExpressionRule());
			        }
					set(
						current,
						"rightOperand",
					lv_rightOperand_3_0,
					"XUnaryOperation");
			        afterParserOrEnumRuleCall();

		    }

		    }


		    }


		    }
		    break;

		default :
		    break loop26;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleXMultiplicativeExpression"


    // $ANTLR start "entryRuleOpMulti"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1764:1: entryRuleOpMulti returns [String current=null] : iv_ruleOpMulti= ruleOpMulti EOF ;
    public final String entryRuleOpMulti() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleOpMulti = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1765:2: (iv_ruleOpMulti= ruleOpMulti EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1766:2: iv_ruleOpMulti= ruleOpMulti EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getOpMultiRule());
            }
            pushFollow(FOLLOW_ruleOpMulti_in_entryRuleOpMulti4528);
            iv_ruleOpMulti=ruleOpMulti();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleOpMulti.getText();
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleOpMulti4539); if (state.failed) return current;

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
    // $ANTLR end "entryRuleOpMulti"


    // $ANTLR start "ruleOpMulti"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1773:1: ruleOpMulti returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '*' | kw= '**' | kw= '/' | kw= '%' ) ;
    public final AntlrDatatypeRuleToken ruleOpMulti() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;

         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1776:28: ( (kw= '*' | kw= '**' | kw= '/' | kw= '%' ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1777:1: (kw= '*' | kw= '**' | kw= '/' | kw= '%' )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1777:1: (kw= '*' | kw= '**' | kw= '/' | kw= '%' )
            int alt27=4;
            switch ( input.LA(1) ) {
            case 48:
                {
                alt27=1;
                }
                break;
            case 49:
                {
                alt27=2;
                }
                break;
            case 50:
                {
                alt27=3;
                }
                break;
            case 51:
                {
                alt27=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 27, 0, input);

                throw nvae;
            }

            switch (alt27) {
                case 1 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1778:2: kw= '*'
                    {
                    kw=(Token)match(input,48,FOLLOW_48_in_ruleOpMulti4577); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current.merge(kw);
                              newLeafNode(kw, grammarAccess.getOpMultiAccess().getAsteriskKeyword_0());

                    }

                    }
                    break;
                case 2 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1785:2: kw= '**'
                    {
                    kw=(Token)match(input,49,FOLLOW_49_in_ruleOpMulti4596); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current.merge(kw);
                              newLeafNode(kw, grammarAccess.getOpMultiAccess().getAsteriskAsteriskKeyword_1());

                    }

                    }
                    break;
                case 3 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1792:2: kw= '/'
                    {
                    kw=(Token)match(input,50,FOLLOW_50_in_ruleOpMulti4615); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current.merge(kw);
                              newLeafNode(kw, grammarAccess.getOpMultiAccess().getSolidusKeyword_2());

                    }

                    }
                    break;
                case 4 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1799:2: kw= '%'
                    {
                    kw=(Token)match(input,51,FOLLOW_51_in_ruleOpMulti4634); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current.merge(kw);
                              newLeafNode(kw, grammarAccess.getOpMultiAccess().getPercentSignKeyword_3());

                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleOpMulti"


    // $ANTLR start "entryRuleXCastedExpression"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1812:1: entryRuleXCastedExpression returns [EObject current=null] : iv_ruleXCastedExpression= ruleXCastedExpression EOF ;
    public final EObject entryRuleXCastedExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXCastedExpression = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1813:2: (iv_ruleXCastedExpression= ruleXCastedExpression EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1814:2: iv_ruleXCastedExpression= ruleXCastedExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXCastedExpressionRule());
            }
            pushFollow(FOLLOW_ruleXCastedExpression_in_entryRuleXCastedExpression4674);
            iv_ruleXCastedExpression=ruleXCastedExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXCastedExpression;
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleXCastedExpression4684); if (state.failed) return current;

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
    // $ANTLR end "entryRuleXCastedExpression"


    // $ANTLR start "ruleXCastedExpression"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1821:1: ruleXCastedExpression returns [EObject current=null] : (this_XPostfixOperation_0= ruleXPostfixOperation ( ( ( ( () 'as' ) )=> ( () otherlv_2= 'as' ) ) ( (lv_type_3_0= ruleJvmTypeReference ) ) )* ) ;
    public final EObject ruleXCastedExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject this_XPostfixOperation_0 = null;

        EObject lv_type_3_0 = null;


         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1824:28: ( (this_XPostfixOperation_0= ruleXPostfixOperation ( ( ( ( () 'as' ) )=> ( () otherlv_2= 'as' ) ) ( (lv_type_3_0= ruleJvmTypeReference ) ) )* ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1825:1: (this_XPostfixOperation_0= ruleXPostfixOperation ( ( ( ( () 'as' ) )=> ( () otherlv_2= 'as' ) ) ( (lv_type_3_0= ruleJvmTypeReference ) ) )* )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1825:1: (this_XPostfixOperation_0= ruleXPostfixOperation ( ( ( ( () 'as' ) )=> ( () otherlv_2= 'as' ) ) ( (lv_type_3_0= ruleJvmTypeReference ) ) )* )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1826:5: this_XPostfixOperation_0= ruleXPostfixOperation ( ( ( ( () 'as' ) )=> ( () otherlv_2= 'as' ) ) ( (lv_type_3_0= ruleJvmTypeReference ) ) )*
            {
            if ( state.backtracking==0 ) {

                      newCompositeNode(grammarAccess.getXCastedExpressionAccess().getXPostfixOperationParserRuleCall_0());

            }
            pushFollow(FOLLOW_ruleXPostfixOperation_in_ruleXCastedExpression4731);
            this_XPostfixOperation_0=ruleXPostfixOperation();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current = this_XPostfixOperation_0;
                      afterParserOrEnumRuleCall();

            }
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1834:1: ( ( ( ( () 'as' ) )=> ( () otherlv_2= 'as' ) ) ( (lv_type_3_0= ruleJvmTypeReference ) ) )*
            loop28:
            do {
                int alt28=2;
                int LA28_0 = input.LA(1);

                if ( (LA28_0==52) ) {
                    int LA28_2 = input.LA(2);

                    if ( (synpred9_InternalDSEL()) ) {
                        alt28=1;
                    }


                }


                switch (alt28) {
		case 1 :
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1834:2: ( ( ( () 'as' ) )=> ( () otherlv_2= 'as' ) ) ( (lv_type_3_0= ruleJvmTypeReference ) )
		    {
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1834:2: ( ( ( () 'as' ) )=> ( () otherlv_2= 'as' ) )
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1834:3: ( ( () 'as' ) )=> ( () otherlv_2= 'as' )
		    {
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1836:5: ( () otherlv_2= 'as' )
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1836:6: () otherlv_2= 'as'
		    {
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1836:6: ()
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1837:5:
		    {
		    if ( state.backtracking==0 ) {

		              current = forceCreateModelElementAndSet(
		                  grammarAccess.getXCastedExpressionAccess().getXCastedExpressionTargetAction_1_0_0_0(),
		                  current);

		    }

		    }

		    otherlv_2=(Token)match(input,52,FOLLOW_52_in_ruleXCastedExpression4766); if (state.failed) return current;
		    if ( state.backtracking==0 ) {

				newLeafNode(otherlv_2, grammarAccess.getXCastedExpressionAccess().getAsKeyword_1_0_0_1());

		    }

		    }


		    }

		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1846:3: ( (lv_type_3_0= ruleJvmTypeReference ) )
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1847:1: (lv_type_3_0= ruleJvmTypeReference )
		    {
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1847:1: (lv_type_3_0= ruleJvmTypeReference )
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1848:3: lv_type_3_0= ruleJvmTypeReference
		    {
		    if ( state.backtracking==0 ) {

			        newCompositeNode(grammarAccess.getXCastedExpressionAccess().getTypeJvmTypeReferenceParserRuleCall_1_1_0());

		    }
		    pushFollow(FOLLOW_ruleJvmTypeReference_in_ruleXCastedExpression4789);
		    lv_type_3_0=ruleJvmTypeReference();

		    state._fsp--;
		    if (state.failed) return current;
		    if ( state.backtracking==0 ) {

			        if (current==null) {
			            current = createModelElementForParent(grammarAccess.getXCastedExpressionRule());
			        }
					set(
						current,
						"type",
					lv_type_3_0,
					"JvmTypeReference");
			        afterParserOrEnumRuleCall();

		    }

		    }


		    }


		    }
		    break;

		default :
		    break loop28;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleXCastedExpression"


    // $ANTLR start "entryRuleXPostfixOperation"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1872:1: entryRuleXPostfixOperation returns [EObject current=null] : iv_ruleXPostfixOperation= ruleXPostfixOperation EOF ;
    public final EObject entryRuleXPostfixOperation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXPostfixOperation = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1873:2: (iv_ruleXPostfixOperation= ruleXPostfixOperation EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1874:2: iv_ruleXPostfixOperation= ruleXPostfixOperation EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXPostfixOperationRule());
            }
            pushFollow(FOLLOW_ruleXPostfixOperation_in_entryRuleXPostfixOperation4827);
            iv_ruleXPostfixOperation=ruleXPostfixOperation();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXPostfixOperation;
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleXPostfixOperation4837); if (state.failed) return current;

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
    // $ANTLR end "entryRuleXPostfixOperation"


    // $ANTLR start "ruleXPostfixOperation"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1881:1: ruleXPostfixOperation returns [EObject current=null] : (this_XMemberFeatureCall_0= ruleXMemberFeatureCall ( ( ( () ( ( ruleOpPostfix ) ) ) )=> ( () ( ( ruleOpPostfix ) ) ) )? ) ;
    public final EObject ruleXPostfixOperation() throws RecognitionException {
        EObject current = null;

        EObject this_XMemberFeatureCall_0 = null;


         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1884:28: ( (this_XMemberFeatureCall_0= ruleXMemberFeatureCall ( ( ( () ( ( ruleOpPostfix ) ) ) )=> ( () ( ( ruleOpPostfix ) ) ) )? ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1885:1: (this_XMemberFeatureCall_0= ruleXMemberFeatureCall ( ( ( () ( ( ruleOpPostfix ) ) ) )=> ( () ( ( ruleOpPostfix ) ) ) )? )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1885:1: (this_XMemberFeatureCall_0= ruleXMemberFeatureCall ( ( ( () ( ( ruleOpPostfix ) ) ) )=> ( () ( ( ruleOpPostfix ) ) ) )? )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1886:5: this_XMemberFeatureCall_0= ruleXMemberFeatureCall ( ( ( () ( ( ruleOpPostfix ) ) ) )=> ( () ( ( ruleOpPostfix ) ) ) )?
            {
            if ( state.backtracking==0 ) {

                      newCompositeNode(grammarAccess.getXPostfixOperationAccess().getXMemberFeatureCallParserRuleCall_0());

            }
            pushFollow(FOLLOW_ruleXMemberFeatureCall_in_ruleXPostfixOperation4884);
            this_XMemberFeatureCall_0=ruleXMemberFeatureCall();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current = this_XMemberFeatureCall_0;
                      afterParserOrEnumRuleCall();

            }
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1894:1: ( ( ( () ( ( ruleOpPostfix ) ) ) )=> ( () ( ( ruleOpPostfix ) ) ) )?
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==53) ) {
                int LA29_1 = input.LA(2);

                if ( (synpred10_InternalDSEL()) ) {
                    alt29=1;
                }
            }
            else if ( (LA29_0==54) ) {
                int LA29_2 = input.LA(2);

                if ( (synpred10_InternalDSEL()) ) {
                    alt29=1;
                }
            }
            switch (alt29) {
                case 1 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1894:2: ( ( () ( ( ruleOpPostfix ) ) ) )=> ( () ( ( ruleOpPostfix ) ) )
                    {
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1899:6: ( () ( ( ruleOpPostfix ) ) )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1899:7: () ( ( ruleOpPostfix ) )
                    {
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1899:7: ()
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1900:5:
                    {
                    if ( state.backtracking==0 ) {

                              current = forceCreateModelElementAndSet(
                                  grammarAccess.getXPostfixOperationAccess().getXPostfixOperationOperandAction_1_0_0(),
                                  current);

                    }

                    }

                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1905:2: ( ( ruleOpPostfix ) )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1906:1: ( ruleOpPostfix )
                    {
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1906:1: ( ruleOpPostfix )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1907:3: ruleOpPostfix
                    {
                    if ( state.backtracking==0 ) {

					if (current==null) {
			            current = createModelElement(grammarAccess.getXPostfixOperationRule());
			        }

                    }
                    if ( state.backtracking==0 ) {

			        newCompositeNode(grammarAccess.getXPostfixOperationAccess().getFeatureJvmIdentifiableElementCrossReference_1_0_1_0());

                    }
                    pushFollow(FOLLOW_ruleOpPostfix_in_ruleXPostfixOperation4936);
                    ruleOpPostfix();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

			        afterParserOrEnumRuleCall();

                    }

                    }


                    }


                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleXPostfixOperation"


    // $ANTLR start "entryRuleOpPostfix"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1928:1: entryRuleOpPostfix returns [String current=null] : iv_ruleOpPostfix= ruleOpPostfix EOF ;
    public final String entryRuleOpPostfix() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleOpPostfix = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1929:2: (iv_ruleOpPostfix= ruleOpPostfix EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1930:2: iv_ruleOpPostfix= ruleOpPostfix EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getOpPostfixRule());
            }
            pushFollow(FOLLOW_ruleOpPostfix_in_entryRuleOpPostfix4976);
            iv_ruleOpPostfix=ruleOpPostfix();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleOpPostfix.getText();
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleOpPostfix4987); if (state.failed) return current;

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
    // $ANTLR end "entryRuleOpPostfix"


    // $ANTLR start "ruleOpPostfix"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1937:1: ruleOpPostfix returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '++' | kw= '--' ) ;
    public final AntlrDatatypeRuleToken ruleOpPostfix() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;

         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1940:28: ( (kw= '++' | kw= '--' ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1941:1: (kw= '++' | kw= '--' )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1941:1: (kw= '++' | kw= '--' )
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==53) ) {
                alt30=1;
            }
            else if ( (LA30_0==54) ) {
                alt30=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 30, 0, input);

                throw nvae;
            }
            switch (alt30) {
                case 1 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1942:2: kw= '++'
                    {
                    kw=(Token)match(input,53,FOLLOW_53_in_ruleOpPostfix5025); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current.merge(kw);
                              newLeafNode(kw, grammarAccess.getOpPostfixAccess().getPlusSignPlusSignKeyword_0());

                    }

                    }
                    break;
                case 2 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1949:2: kw= '--'
                    {
                    kw=(Token)match(input,54,FOLLOW_54_in_ruleOpPostfix5044); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current.merge(kw);
                              newLeafNode(kw, grammarAccess.getOpPostfixAccess().getHyphenMinusHyphenMinusKeyword_1());

                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleOpPostfix"


    // $ANTLR start "entryRuleXMemberFeatureCall"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1962:1: entryRuleXMemberFeatureCall returns [EObject current=null] : iv_ruleXMemberFeatureCall= ruleXMemberFeatureCall EOF ;
    public final EObject entryRuleXMemberFeatureCall() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXMemberFeatureCall = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1963:2: (iv_ruleXMemberFeatureCall= ruleXMemberFeatureCall EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1964:2: iv_ruleXMemberFeatureCall= ruleXMemberFeatureCall EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXMemberFeatureCallRule());
            }
            pushFollow(FOLLOW_ruleXMemberFeatureCall_in_entryRuleXMemberFeatureCall5084);
            iv_ruleXMemberFeatureCall=ruleXMemberFeatureCall();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXMemberFeatureCall;
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleXMemberFeatureCall5094); if (state.failed) return current;

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
    // $ANTLR end "entryRuleXMemberFeatureCall"


    // $ANTLR start "ruleXMemberFeatureCall"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1971:1: ruleXMemberFeatureCall returns [EObject current=null] : (this_XPrimaryExpression_0= ruleXPrimaryExpression ( ( ( ( ( () ( '.' | ( ( '::' ) ) ) ( ( ruleFeatureCallID ) ) ruleOpSingleAssign ) )=> ( () (otherlv_2= '.' | ( (lv_explicitStatic_3_0= '::' ) ) ) ( ( ruleFeatureCallID ) ) ruleOpSingleAssign ) ) ( (lv_value_6_0= ruleXAssignment ) ) ) | ( ( ( ( () ( '.' | ( ( '?.' ) ) | ( ( '::' ) ) ) ) )=> ( () (otherlv_8= '.' | ( (lv_nullSafe_9_0= '?.' ) ) | ( (lv_explicitStatic_10_0= '::' ) ) ) ) ) (otherlv_11= '<' ( (lv_typeArguments_12_0= ruleJvmArgumentTypeReference ) ) (otherlv_13= ',' ( (lv_typeArguments_14_0= ruleJvmArgumentTypeReference ) ) )* otherlv_15= '>' )? ( ( ruleIdOrSuper ) ) ( ( ( ( '(' ) )=> (lv_explicitOperationCall_17_0= '(' ) ) ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_memberCallArguments_18_0= ruleXShortClosure ) ) | ( ( (lv_memberCallArguments_19_0= ruleXExpression ) ) (otherlv_20= ',' ( (lv_memberCallArguments_21_0= ruleXExpression ) ) )* ) )? otherlv_22= ')' )? ( ( ( () '[' ) )=> (lv_memberCallArguments_23_0= ruleXClosure ) )? ) )* ) ;
    public final EObject ruleXMemberFeatureCall() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token lv_explicitStatic_3_0=null;
        Token otherlv_8=null;
        Token lv_nullSafe_9_0=null;
        Token lv_explicitStatic_10_0=null;
        Token otherlv_11=null;
        Token otherlv_13=null;
        Token otherlv_15=null;
        Token lv_explicitOperationCall_17_0=null;
        Token otherlv_20=null;
        Token otherlv_22=null;
        EObject this_XPrimaryExpression_0 = null;

        EObject lv_value_6_0 = null;

        EObject lv_typeArguments_12_0 = null;

        EObject lv_typeArguments_14_0 = null;

        EObject lv_memberCallArguments_18_0 = null;

        EObject lv_memberCallArguments_19_0 = null;

        EObject lv_memberCallArguments_21_0 = null;

        EObject lv_memberCallArguments_23_0 = null;


         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1974:28: ( (this_XPrimaryExpression_0= ruleXPrimaryExpression ( ( ( ( ( () ( '.' | ( ( '::' ) ) ) ( ( ruleFeatureCallID ) ) ruleOpSingleAssign ) )=> ( () (otherlv_2= '.' | ( (lv_explicitStatic_3_0= '::' ) ) ) ( ( ruleFeatureCallID ) ) ruleOpSingleAssign ) ) ( (lv_value_6_0= ruleXAssignment ) ) ) | ( ( ( ( () ( '.' | ( ( '?.' ) ) | ( ( '::' ) ) ) ) )=> ( () (otherlv_8= '.' | ( (lv_nullSafe_9_0= '?.' ) ) | ( (lv_explicitStatic_10_0= '::' ) ) ) ) ) (otherlv_11= '<' ( (lv_typeArguments_12_0= ruleJvmArgumentTypeReference ) ) (otherlv_13= ',' ( (lv_typeArguments_14_0= ruleJvmArgumentTypeReference ) ) )* otherlv_15= '>' )? ( ( ruleIdOrSuper ) ) ( ( ( ( '(' ) )=> (lv_explicitOperationCall_17_0= '(' ) ) ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_memberCallArguments_18_0= ruleXShortClosure ) ) | ( ( (lv_memberCallArguments_19_0= ruleXExpression ) ) (otherlv_20= ',' ( (lv_memberCallArguments_21_0= ruleXExpression ) ) )* ) )? otherlv_22= ')' )? ( ( ( () '[' ) )=> (lv_memberCallArguments_23_0= ruleXClosure ) )? ) )* ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1975:1: (this_XPrimaryExpression_0= ruleXPrimaryExpression ( ( ( ( ( () ( '.' | ( ( '::' ) ) ) ( ( ruleFeatureCallID ) ) ruleOpSingleAssign ) )=> ( () (otherlv_2= '.' | ( (lv_explicitStatic_3_0= '::' ) ) ) ( ( ruleFeatureCallID ) ) ruleOpSingleAssign ) ) ( (lv_value_6_0= ruleXAssignment ) ) ) | ( ( ( ( () ( '.' | ( ( '?.' ) ) | ( ( '::' ) ) ) ) )=> ( () (otherlv_8= '.' | ( (lv_nullSafe_9_0= '?.' ) ) | ( (lv_explicitStatic_10_0= '::' ) ) ) ) ) (otherlv_11= '<' ( (lv_typeArguments_12_0= ruleJvmArgumentTypeReference ) ) (otherlv_13= ',' ( (lv_typeArguments_14_0= ruleJvmArgumentTypeReference ) ) )* otherlv_15= '>' )? ( ( ruleIdOrSuper ) ) ( ( ( ( '(' ) )=> (lv_explicitOperationCall_17_0= '(' ) ) ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_memberCallArguments_18_0= ruleXShortClosure ) ) | ( ( (lv_memberCallArguments_19_0= ruleXExpression ) ) (otherlv_20= ',' ( (lv_memberCallArguments_21_0= ruleXExpression ) ) )* ) )? otherlv_22= ')' )? ( ( ( () '[' ) )=> (lv_memberCallArguments_23_0= ruleXClosure ) )? ) )* )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1975:1: (this_XPrimaryExpression_0= ruleXPrimaryExpression ( ( ( ( ( () ( '.' | ( ( '::' ) ) ) ( ( ruleFeatureCallID ) ) ruleOpSingleAssign ) )=> ( () (otherlv_2= '.' | ( (lv_explicitStatic_3_0= '::' ) ) ) ( ( ruleFeatureCallID ) ) ruleOpSingleAssign ) ) ( (lv_value_6_0= ruleXAssignment ) ) ) | ( ( ( ( () ( '.' | ( ( '?.' ) ) | ( ( '::' ) ) ) ) )=> ( () (otherlv_8= '.' | ( (lv_nullSafe_9_0= '?.' ) ) | ( (lv_explicitStatic_10_0= '::' ) ) ) ) ) (otherlv_11= '<' ( (lv_typeArguments_12_0= ruleJvmArgumentTypeReference ) ) (otherlv_13= ',' ( (lv_typeArguments_14_0= ruleJvmArgumentTypeReference ) ) )* otherlv_15= '>' )? ( ( ruleIdOrSuper ) ) ( ( ( ( '(' ) )=> (lv_explicitOperationCall_17_0= '(' ) ) ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_memberCallArguments_18_0= ruleXShortClosure ) ) | ( ( (lv_memberCallArguments_19_0= ruleXExpression ) ) (otherlv_20= ',' ( (lv_memberCallArguments_21_0= ruleXExpression ) ) )* ) )? otherlv_22= ')' )? ( ( ( () '[' ) )=> (lv_memberCallArguments_23_0= ruleXClosure ) )? ) )* )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1976:5: this_XPrimaryExpression_0= ruleXPrimaryExpression ( ( ( ( ( () ( '.' | ( ( '::' ) ) ) ( ( ruleFeatureCallID ) ) ruleOpSingleAssign ) )=> ( () (otherlv_2= '.' | ( (lv_explicitStatic_3_0= '::' ) ) ) ( ( ruleFeatureCallID ) ) ruleOpSingleAssign ) ) ( (lv_value_6_0= ruleXAssignment ) ) ) | ( ( ( ( () ( '.' | ( ( '?.' ) ) | ( ( '::' ) ) ) ) )=> ( () (otherlv_8= '.' | ( (lv_nullSafe_9_0= '?.' ) ) | ( (lv_explicitStatic_10_0= '::' ) ) ) ) ) (otherlv_11= '<' ( (lv_typeArguments_12_0= ruleJvmArgumentTypeReference ) ) (otherlv_13= ',' ( (lv_typeArguments_14_0= ruleJvmArgumentTypeReference ) ) )* otherlv_15= '>' )? ( ( ruleIdOrSuper ) ) ( ( ( ( '(' ) )=> (lv_explicitOperationCall_17_0= '(' ) ) ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_memberCallArguments_18_0= ruleXShortClosure ) ) | ( ( (lv_memberCallArguments_19_0= ruleXExpression ) ) (otherlv_20= ',' ( (lv_memberCallArguments_21_0= ruleXExpression ) ) )* ) )? otherlv_22= ')' )? ( ( ( () '[' ) )=> (lv_memberCallArguments_23_0= ruleXClosure ) )? ) )*
            {
            if ( state.backtracking==0 ) {

                      newCompositeNode(grammarAccess.getXMemberFeatureCallAccess().getXPrimaryExpressionParserRuleCall_0());

            }
            pushFollow(FOLLOW_ruleXPrimaryExpression_in_ruleXMemberFeatureCall5141);
            this_XPrimaryExpression_0=ruleXPrimaryExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current = this_XPrimaryExpression_0;
                      afterParserOrEnumRuleCall();

            }
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1984:1: ( ( ( ( ( () ( '.' | ( ( '::' ) ) ) ( ( ruleFeatureCallID ) ) ruleOpSingleAssign ) )=> ( () (otherlv_2= '.' | ( (lv_explicitStatic_3_0= '::' ) ) ) ( ( ruleFeatureCallID ) ) ruleOpSingleAssign ) ) ( (lv_value_6_0= ruleXAssignment ) ) ) | ( ( ( ( () ( '.' | ( ( '?.' ) ) | ( ( '::' ) ) ) ) )=> ( () (otherlv_8= '.' | ( (lv_nullSafe_9_0= '?.' ) ) | ( (lv_explicitStatic_10_0= '::' ) ) ) ) ) (otherlv_11= '<' ( (lv_typeArguments_12_0= ruleJvmArgumentTypeReference ) ) (otherlv_13= ',' ( (lv_typeArguments_14_0= ruleJvmArgumentTypeReference ) ) )* otherlv_15= '>' )? ( ( ruleIdOrSuper ) ) ( ( ( ( '(' ) )=> (lv_explicitOperationCall_17_0= '(' ) ) ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_memberCallArguments_18_0= ruleXShortClosure ) ) | ( ( (lv_memberCallArguments_19_0= ruleXExpression ) ) (otherlv_20= ',' ( (lv_memberCallArguments_21_0= ruleXExpression ) ) )* ) )? otherlv_22= ')' )? ( ( ( () '[' ) )=> (lv_memberCallArguments_23_0= ruleXClosure ) )? ) )*
            loop39:
            do {
                int alt39=3;
                switch ( input.LA(1) ) {
                case 41:
                    {
                    int LA39_2 = input.LA(2);

                    if ( (synpred11_InternalDSEL()) ) {
                        alt39=1;
                    }
                    else if ( (synpred12_InternalDSEL()) ) {
                        alt39=2;
                    }


                    }
                    break;
                case 55:
                    {
                    int LA39_3 = input.LA(2);

                    if ( (synpred11_InternalDSEL()) ) {
                        alt39=1;
                    }
                    else if ( (synpred12_InternalDSEL()) ) {
                        alt39=2;
                    }


                    }
                    break;
                case 56:
                    {
                    int LA39_4 = input.LA(2);

                    if ( (synpred12_InternalDSEL()) ) {
                        alt39=2;
                    }


                    }
                    break;

                }

                switch (alt39) {
		case 1 :
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1984:2: ( ( ( ( () ( '.' | ( ( '::' ) ) ) ( ( ruleFeatureCallID ) ) ruleOpSingleAssign ) )=> ( () (otherlv_2= '.' | ( (lv_explicitStatic_3_0= '::' ) ) ) ( ( ruleFeatureCallID ) ) ruleOpSingleAssign ) ) ( (lv_value_6_0= ruleXAssignment ) ) )
		    {
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1984:2: ( ( ( ( () ( '.' | ( ( '::' ) ) ) ( ( ruleFeatureCallID ) ) ruleOpSingleAssign ) )=> ( () (otherlv_2= '.' | ( (lv_explicitStatic_3_0= '::' ) ) ) ( ( ruleFeatureCallID ) ) ruleOpSingleAssign ) ) ( (lv_value_6_0= ruleXAssignment ) ) )
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1984:3: ( ( ( () ( '.' | ( ( '::' ) ) ) ( ( ruleFeatureCallID ) ) ruleOpSingleAssign ) )=> ( () (otherlv_2= '.' | ( (lv_explicitStatic_3_0= '::' ) ) ) ( ( ruleFeatureCallID ) ) ruleOpSingleAssign ) ) ( (lv_value_6_0= ruleXAssignment ) )
		    {
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1984:3: ( ( ( () ( '.' | ( ( '::' ) ) ) ( ( ruleFeatureCallID ) ) ruleOpSingleAssign ) )=> ( () (otherlv_2= '.' | ( (lv_explicitStatic_3_0= '::' ) ) ) ( ( ruleFeatureCallID ) ) ruleOpSingleAssign ) )
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1984:4: ( ( () ( '.' | ( ( '::' ) ) ) ( ( ruleFeatureCallID ) ) ruleOpSingleAssign ) )=> ( () (otherlv_2= '.' | ( (lv_explicitStatic_3_0= '::' ) ) ) ( ( ruleFeatureCallID ) ) ruleOpSingleAssign )
		    {
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1997:25: ( () (otherlv_2= '.' | ( (lv_explicitStatic_3_0= '::' ) ) ) ( ( ruleFeatureCallID ) ) ruleOpSingleAssign )
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1997:26: () (otherlv_2= '.' | ( (lv_explicitStatic_3_0= '::' ) ) ) ( ( ruleFeatureCallID ) ) ruleOpSingleAssign
		    {
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1997:26: ()
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1998:5:
		    {
		    if ( state.backtracking==0 ) {

		              current = forceCreateModelElementAndSet(
		                  grammarAccess.getXMemberFeatureCallAccess().getXAssignmentAssignableAction_1_0_0_0_0(),
		                  current);

		    }

		    }

		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2003:2: (otherlv_2= '.' | ( (lv_explicitStatic_3_0= '::' ) ) )
		    int alt31=2;
		    int LA31_0 = input.LA(1);

		    if ( (LA31_0==41) ) {
		        alt31=1;
		    }
		    else if ( (LA31_0==55) ) {
		        alt31=2;
		    }
		    else {
		        if (state.backtracking>0) {state.failed=true; return current;}
		        NoViableAltException nvae =
		            new NoViableAltException("", 31, 0, input);

		        throw nvae;
		    }
		    switch (alt31) {
		        case 1 :
		            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2003:4: otherlv_2= '.'
		            {
		            otherlv_2=(Token)match(input,41,FOLLOW_41_in_ruleXMemberFeatureCall5213); if (state.failed) return current;
		            if ( state.backtracking==0 ) {

					newLeafNode(otherlv_2, grammarAccess.getXMemberFeatureCallAccess().getFullStopKeyword_1_0_0_0_1_0());

		            }

		            }
		            break;
		        case 2 :
		            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2008:6: ( (lv_explicitStatic_3_0= '::' ) )
		            {
		            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2008:6: ( (lv_explicitStatic_3_0= '::' ) )
		            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2009:1: (lv_explicitStatic_3_0= '::' )
		            {
		            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2009:1: (lv_explicitStatic_3_0= '::' )
		            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2010:3: lv_explicitStatic_3_0= '::'
		            {
		            lv_explicitStatic_3_0=(Token)match(input,55,FOLLOW_55_in_ruleXMemberFeatureCall5237); if (state.failed) return current;
		            if ( state.backtracking==0 ) {

		                      newLeafNode(lv_explicitStatic_3_0, grammarAccess.getXMemberFeatureCallAccess().getExplicitStaticColonColonKeyword_1_0_0_0_1_1_0());

		            }
		            if ( state.backtracking==0 ) {

				        if (current==null) {
				            current = createModelElement(grammarAccess.getXMemberFeatureCallRule());
				        }
						setWithLastConsumed(current, "explicitStatic", true, "::");

		            }

		            }


		            }


		            }
		            break;

		    }

		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2023:3: ( ( ruleFeatureCallID ) )
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2024:1: ( ruleFeatureCallID )
		    {
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2024:1: ( ruleFeatureCallID )
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2025:3: ruleFeatureCallID
		    {
		    if ( state.backtracking==0 ) {

					if (current==null) {
			            current = createModelElement(grammarAccess.getXMemberFeatureCallRule());
			        }

		    }
		    if ( state.backtracking==0 ) {

			        newCompositeNode(grammarAccess.getXMemberFeatureCallAccess().getFeatureJvmIdentifiableElementCrossReference_1_0_0_0_2_0());

		    }
		    pushFollow(FOLLOW_ruleFeatureCallID_in_ruleXMemberFeatureCall5274);
		    ruleFeatureCallID();

		    state._fsp--;
		    if (state.failed) return current;
		    if ( state.backtracking==0 ) {

			        afterParserOrEnumRuleCall();

		    }

		    }


		    }

		    if ( state.backtracking==0 ) {

		              newCompositeNode(grammarAccess.getXMemberFeatureCallAccess().getOpSingleAssignParserRuleCall_1_0_0_0_3());

		    }
		    pushFollow(FOLLOW_ruleOpSingleAssign_in_ruleXMemberFeatureCall5290);
		    ruleOpSingleAssign();

		    state._fsp--;
		    if (state.failed) return current;
		    if ( state.backtracking==0 ) {

		              afterParserOrEnumRuleCall();

		    }

		    }


		    }

		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2046:3: ( (lv_value_6_0= ruleXAssignment ) )
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2047:1: (lv_value_6_0= ruleXAssignment )
		    {
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2047:1: (lv_value_6_0= ruleXAssignment )
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2048:3: lv_value_6_0= ruleXAssignment
		    {
		    if ( state.backtracking==0 ) {

			        newCompositeNode(grammarAccess.getXMemberFeatureCallAccess().getValueXAssignmentParserRuleCall_1_0_1_0());

		    }
		    pushFollow(FOLLOW_ruleXAssignment_in_ruleXMemberFeatureCall5312);
		    lv_value_6_0=ruleXAssignment();

		    state._fsp--;
		    if (state.failed) return current;
		    if ( state.backtracking==0 ) {

			        if (current==null) {
			            current = createModelElementForParent(grammarAccess.getXMemberFeatureCallRule());
			        }
					set(
						current,
						"value",
					lv_value_6_0,
					"XAssignment");
			        afterParserOrEnumRuleCall();

		    }

		    }


		    }


		    }


		    }
		    break;
		case 2 :
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2065:6: ( ( ( ( () ( '.' | ( ( '?.' ) ) | ( ( '::' ) ) ) ) )=> ( () (otherlv_8= '.' | ( (lv_nullSafe_9_0= '?.' ) ) | ( (lv_explicitStatic_10_0= '::' ) ) ) ) ) (otherlv_11= '<' ( (lv_typeArguments_12_0= ruleJvmArgumentTypeReference ) ) (otherlv_13= ',' ( (lv_typeArguments_14_0= ruleJvmArgumentTypeReference ) ) )* otherlv_15= '>' )? ( ( ruleIdOrSuper ) ) ( ( ( ( '(' ) )=> (lv_explicitOperationCall_17_0= '(' ) ) ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_memberCallArguments_18_0= ruleXShortClosure ) ) | ( ( (lv_memberCallArguments_19_0= ruleXExpression ) ) (otherlv_20= ',' ( (lv_memberCallArguments_21_0= ruleXExpression ) ) )* ) )? otherlv_22= ')' )? ( ( ( () '[' ) )=> (lv_memberCallArguments_23_0= ruleXClosure ) )? )
		    {
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2065:6: ( ( ( ( () ( '.' | ( ( '?.' ) ) | ( ( '::' ) ) ) ) )=> ( () (otherlv_8= '.' | ( (lv_nullSafe_9_0= '?.' ) ) | ( (lv_explicitStatic_10_0= '::' ) ) ) ) ) (otherlv_11= '<' ( (lv_typeArguments_12_0= ruleJvmArgumentTypeReference ) ) (otherlv_13= ',' ( (lv_typeArguments_14_0= ruleJvmArgumentTypeReference ) ) )* otherlv_15= '>' )? ( ( ruleIdOrSuper ) ) ( ( ( ( '(' ) )=> (lv_explicitOperationCall_17_0= '(' ) ) ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_memberCallArguments_18_0= ruleXShortClosure ) ) | ( ( (lv_memberCallArguments_19_0= ruleXExpression ) ) (otherlv_20= ',' ( (lv_memberCallArguments_21_0= ruleXExpression ) ) )* ) )? otherlv_22= ')' )? ( ( ( () '[' ) )=> (lv_memberCallArguments_23_0= ruleXClosure ) )? )
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2065:7: ( ( ( () ( '.' | ( ( '?.' ) ) | ( ( '::' ) ) ) ) )=> ( () (otherlv_8= '.' | ( (lv_nullSafe_9_0= '?.' ) ) | ( (lv_explicitStatic_10_0= '::' ) ) ) ) ) (otherlv_11= '<' ( (lv_typeArguments_12_0= ruleJvmArgumentTypeReference ) ) (otherlv_13= ',' ( (lv_typeArguments_14_0= ruleJvmArgumentTypeReference ) ) )* otherlv_15= '>' )? ( ( ruleIdOrSuper ) ) ( ( ( ( '(' ) )=> (lv_explicitOperationCall_17_0= '(' ) ) ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_memberCallArguments_18_0= ruleXShortClosure ) ) | ( ( (lv_memberCallArguments_19_0= ruleXExpression ) ) (otherlv_20= ',' ( (lv_memberCallArguments_21_0= ruleXExpression ) ) )* ) )? otherlv_22= ')' )? ( ( ( () '[' ) )=> (lv_memberCallArguments_23_0= ruleXClosure ) )?
		    {
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2065:7: ( ( ( () ( '.' | ( ( '?.' ) ) | ( ( '::' ) ) ) ) )=> ( () (otherlv_8= '.' | ( (lv_nullSafe_9_0= '?.' ) ) | ( (lv_explicitStatic_10_0= '::' ) ) ) ) )
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2065:8: ( ( () ( '.' | ( ( '?.' ) ) | ( ( '::' ) ) ) ) )=> ( () (otherlv_8= '.' | ( (lv_nullSafe_9_0= '?.' ) ) | ( (lv_explicitStatic_10_0= '::' ) ) ) )
		    {
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2081:7: ( () (otherlv_8= '.' | ( (lv_nullSafe_9_0= '?.' ) ) | ( (lv_explicitStatic_10_0= '::' ) ) ) )
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2081:8: () (otherlv_8= '.' | ( (lv_nullSafe_9_0= '?.' ) ) | ( (lv_explicitStatic_10_0= '::' ) ) )
		    {
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2081:8: ()
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2082:5:
		    {
		    if ( state.backtracking==0 ) {

		              current = forceCreateModelElementAndSet(
		                  grammarAccess.getXMemberFeatureCallAccess().getXMemberFeatureCallMemberCallTargetAction_1_1_0_0_0(),
		                  current);

		    }

		    }

		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2087:2: (otherlv_8= '.' | ( (lv_nullSafe_9_0= '?.' ) ) | ( (lv_explicitStatic_10_0= '::' ) ) )
		    int alt32=3;
		    switch ( input.LA(1) ) {
		    case 41:
		        {
		        alt32=1;
		        }
		        break;
		    case 56:
		        {
		        alt32=2;
		        }
		        break;
		    case 55:
		        {
		        alt32=3;
		        }
		        break;
		    default:
		        if (state.backtracking>0) {state.failed=true; return current;}
		        NoViableAltException nvae =
		            new NoViableAltException("", 32, 0, input);

		        throw nvae;
		    }

		    switch (alt32) {
		        case 1 :
		            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2087:4: otherlv_8= '.'
		            {
		            otherlv_8=(Token)match(input,41,FOLLOW_41_in_ruleXMemberFeatureCall5398); if (state.failed) return current;
		            if ( state.backtracking==0 ) {

					newLeafNode(otherlv_8, grammarAccess.getXMemberFeatureCallAccess().getFullStopKeyword_1_1_0_0_1_0());

		            }

		            }
		            break;
		        case 2 :
		            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2092:6: ( (lv_nullSafe_9_0= '?.' ) )
		            {
		            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2092:6: ( (lv_nullSafe_9_0= '?.' ) )
		            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2093:1: (lv_nullSafe_9_0= '?.' )
		            {
		            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2093:1: (lv_nullSafe_9_0= '?.' )
		            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2094:3: lv_nullSafe_9_0= '?.'
		            {
		            lv_nullSafe_9_0=(Token)match(input,56,FOLLOW_56_in_ruleXMemberFeatureCall5422); if (state.failed) return current;
		            if ( state.backtracking==0 ) {

		                      newLeafNode(lv_nullSafe_9_0, grammarAccess.getXMemberFeatureCallAccess().getNullSafeQuestionMarkFullStopKeyword_1_1_0_0_1_1_0());

		            }
		            if ( state.backtracking==0 ) {

				        if (current==null) {
				            current = createModelElement(grammarAccess.getXMemberFeatureCallRule());
				        }
						setWithLastConsumed(current, "nullSafe", true, "?.");

		            }

		            }


		            }


		            }
		            break;
		        case 3 :
		            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2108:6: ( (lv_explicitStatic_10_0= '::' ) )
		            {
		            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2108:6: ( (lv_explicitStatic_10_0= '::' ) )
		            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2109:1: (lv_explicitStatic_10_0= '::' )
		            {
		            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2109:1: (lv_explicitStatic_10_0= '::' )
		            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2110:3: lv_explicitStatic_10_0= '::'
		            {
		            lv_explicitStatic_10_0=(Token)match(input,55,FOLLOW_55_in_ruleXMemberFeatureCall5459); if (state.failed) return current;
		            if ( state.backtracking==0 ) {

		                      newLeafNode(lv_explicitStatic_10_0, grammarAccess.getXMemberFeatureCallAccess().getExplicitStaticColonColonKeyword_1_1_0_0_1_2_0());

		            }
		            if ( state.backtracking==0 ) {

				        if (current==null) {
				            current = createModelElement(grammarAccess.getXMemberFeatureCallRule());
				        }
						setWithLastConsumed(current, "explicitStatic", true, "::");

		            }

		            }


		            }


		            }
		            break;

		    }


		    }


		    }

		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2123:5: (otherlv_11= '<' ( (lv_typeArguments_12_0= ruleJvmArgumentTypeReference ) ) (otherlv_13= ',' ( (lv_typeArguments_14_0= ruleJvmArgumentTypeReference ) ) )* otherlv_15= '>' )?
		    int alt34=2;
		    int LA34_0 = input.LA(1);

		    if ( (LA34_0==23) ) {
		        alt34=1;
		    }
		    switch (alt34) {
		        case 1 :
		            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2123:7: otherlv_11= '<' ( (lv_typeArguments_12_0= ruleJvmArgumentTypeReference ) ) (otherlv_13= ',' ( (lv_typeArguments_14_0= ruleJvmArgumentTypeReference ) ) )* otherlv_15= '>'
		            {
		            otherlv_11=(Token)match(input,23,FOLLOW_23_in_ruleXMemberFeatureCall5488); if (state.failed) return current;
		            if ( state.backtracking==0 ) {

					newLeafNode(otherlv_11, grammarAccess.getXMemberFeatureCallAccess().getLessThanSignKeyword_1_1_1_0());

		            }
		            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2127:1: ( (lv_typeArguments_12_0= ruleJvmArgumentTypeReference ) )
		            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2128:1: (lv_typeArguments_12_0= ruleJvmArgumentTypeReference )
		            {
		            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2128:1: (lv_typeArguments_12_0= ruleJvmArgumentTypeReference )
		            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2129:3: lv_typeArguments_12_0= ruleJvmArgumentTypeReference
		            {
		            if ( state.backtracking==0 ) {

				        newCompositeNode(grammarAccess.getXMemberFeatureCallAccess().getTypeArgumentsJvmArgumentTypeReferenceParserRuleCall_1_1_1_1_0());

		            }
		            pushFollow(FOLLOW_ruleJvmArgumentTypeReference_in_ruleXMemberFeatureCall5509);
		            lv_typeArguments_12_0=ruleJvmArgumentTypeReference();

		            state._fsp--;
		            if (state.failed) return current;
		            if ( state.backtracking==0 ) {

				        if (current==null) {
				            current = createModelElementForParent(grammarAccess.getXMemberFeatureCallRule());
				        }
						add(
							current,
							"typeArguments",
						lv_typeArguments_12_0,
						"JvmArgumentTypeReference");
				        afterParserOrEnumRuleCall();

		            }

		            }


		            }

		            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2145:2: (otherlv_13= ',' ( (lv_typeArguments_14_0= ruleJvmArgumentTypeReference ) ) )*
		            loop33:
		            do {
		                int alt33=2;
		                int LA33_0 = input.LA(1);

		                if ( (LA33_0==57) ) {
		                    alt33=1;
		                }


		                switch (alt33) {
				case 1 :
				    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2145:4: otherlv_13= ',' ( (lv_typeArguments_14_0= ruleJvmArgumentTypeReference ) )
				    {
				    otherlv_13=(Token)match(input,57,FOLLOW_57_in_ruleXMemberFeatureCall5522); if (state.failed) return current;
				    if ( state.backtracking==0 ) {

						newLeafNode(otherlv_13, grammarAccess.getXMemberFeatureCallAccess().getCommaKeyword_1_1_1_2_0());

				    }
				    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2149:1: ( (lv_typeArguments_14_0= ruleJvmArgumentTypeReference ) )
				    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2150:1: (lv_typeArguments_14_0= ruleJvmArgumentTypeReference )
				    {
				    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2150:1: (lv_typeArguments_14_0= ruleJvmArgumentTypeReference )
				    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2151:3: lv_typeArguments_14_0= ruleJvmArgumentTypeReference
				    {
				    if ( state.backtracking==0 ) {

					        newCompositeNode(grammarAccess.getXMemberFeatureCallAccess().getTypeArgumentsJvmArgumentTypeReferenceParserRuleCall_1_1_1_2_1_0());

				    }
				    pushFollow(FOLLOW_ruleJvmArgumentTypeReference_in_ruleXMemberFeatureCall5543);
				    lv_typeArguments_14_0=ruleJvmArgumentTypeReference();

				    state._fsp--;
				    if (state.failed) return current;
				    if ( state.backtracking==0 ) {

					        if (current==null) {
					            current = createModelElementForParent(grammarAccess.getXMemberFeatureCallRule());
					        }
							add(
								current,
								"typeArguments",
							lv_typeArguments_14_0,
							"JvmArgumentTypeReference");
					        afterParserOrEnumRuleCall();

				    }

				    }


				    }


				    }
				    break;

				default :
				    break loop33;
		                }
		            } while (true);

		            otherlv_15=(Token)match(input,22,FOLLOW_22_in_ruleXMemberFeatureCall5557); if (state.failed) return current;
		            if ( state.backtracking==0 ) {

					newLeafNode(otherlv_15, grammarAccess.getXMemberFeatureCallAccess().getGreaterThanSignKeyword_1_1_1_3());

		            }

		            }
		            break;

		    }

		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2171:3: ( ( ruleIdOrSuper ) )
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2172:1: ( ruleIdOrSuper )
		    {
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2172:1: ( ruleIdOrSuper )
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2173:3: ruleIdOrSuper
		    {
		    if ( state.backtracking==0 ) {

					if (current==null) {
			            current = createModelElement(grammarAccess.getXMemberFeatureCallRule());
			        }

		    }
		    if ( state.backtracking==0 ) {

			        newCompositeNode(grammarAccess.getXMemberFeatureCallAccess().getFeatureJvmIdentifiableElementCrossReference_1_1_2_0());

		    }
		    pushFollow(FOLLOW_ruleIdOrSuper_in_ruleXMemberFeatureCall5582);
		    ruleIdOrSuper();

		    state._fsp--;
		    if (state.failed) return current;
		    if ( state.backtracking==0 ) {

			        afterParserOrEnumRuleCall();

		    }

		    }


		    }

		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2186:2: ( ( ( ( '(' ) )=> (lv_explicitOperationCall_17_0= '(' ) ) ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_memberCallArguments_18_0= ruleXShortClosure ) ) | ( ( (lv_memberCallArguments_19_0= ruleXExpression ) ) (otherlv_20= ',' ( (lv_memberCallArguments_21_0= ruleXExpression ) ) )* ) )? otherlv_22= ')' )?
		    int alt37=2;
		    alt37 = dfa37.predict(input);
		    switch (alt37) {
		        case 1 :
		            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2186:3: ( ( ( '(' ) )=> (lv_explicitOperationCall_17_0= '(' ) ) ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_memberCallArguments_18_0= ruleXShortClosure ) ) | ( ( (lv_memberCallArguments_19_0= ruleXExpression ) ) (otherlv_20= ',' ( (lv_memberCallArguments_21_0= ruleXExpression ) ) )* ) )? otherlv_22= ')'
		            {
		            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2186:3: ( ( ( '(' ) )=> (lv_explicitOperationCall_17_0= '(' ) )
		            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2186:4: ( ( '(' ) )=> (lv_explicitOperationCall_17_0= '(' )
		            {
		            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2193:1: (lv_explicitOperationCall_17_0= '(' )
		            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2194:3: lv_explicitOperationCall_17_0= '('
		            {
		            lv_explicitOperationCall_17_0=(Token)match(input,58,FOLLOW_58_in_ruleXMemberFeatureCall5616); if (state.failed) return current;
		            if ( state.backtracking==0 ) {

		                      newLeafNode(lv_explicitOperationCall_17_0, grammarAccess.getXMemberFeatureCallAccess().getExplicitOperationCallLeftParenthesisKeyword_1_1_3_0_0());

		            }
		            if ( state.backtracking==0 ) {

				        if (current==null) {
				            current = createModelElement(grammarAccess.getXMemberFeatureCallRule());
				        }
						setWithLastConsumed(current, "explicitOperationCall", true, "(");

		            }

		            }


		            }

		            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2207:2: ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_memberCallArguments_18_0= ruleXShortClosure ) ) | ( ( (lv_memberCallArguments_19_0= ruleXExpression ) ) (otherlv_20= ',' ( (lv_memberCallArguments_21_0= ruleXExpression ) ) )* ) )?
		            int alt36=3;
		            alt36 = dfa36.predict(input);
		            switch (alt36) {
		                case 1 :
		                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2207:3: ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_memberCallArguments_18_0= ruleXShortClosure ) )
		                    {
		                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2207:3: ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_memberCallArguments_18_0= ruleXShortClosure ) )
		                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2207:4: ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_memberCallArguments_18_0= ruleXShortClosure )
		                    {
		                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2224:1: (lv_memberCallArguments_18_0= ruleXShortClosure )
		                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2225:3: lv_memberCallArguments_18_0= ruleXShortClosure
		                    {
		                    if ( state.backtracking==0 ) {

					        newCompositeNode(grammarAccess.getXMemberFeatureCallAccess().getMemberCallArgumentsXShortClosureParserRuleCall_1_1_3_1_0_0());

		                    }
		                    pushFollow(FOLLOW_ruleXShortClosure_in_ruleXMemberFeatureCall5701);
		                    lv_memberCallArguments_18_0=ruleXShortClosure();

		                    state._fsp--;
		                    if (state.failed) return current;
		                    if ( state.backtracking==0 ) {

					        if (current==null) {
					            current = createModelElementForParent(grammarAccess.getXMemberFeatureCallRule());
					        }
							add(
								current,
								"memberCallArguments",
							lv_memberCallArguments_18_0,
							"XShortClosure");
					        afterParserOrEnumRuleCall();

		                    }

		                    }


		                    }


		                    }
		                    break;
		                case 2 :
		                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2242:6: ( ( (lv_memberCallArguments_19_0= ruleXExpression ) ) (otherlv_20= ',' ( (lv_memberCallArguments_21_0= ruleXExpression ) ) )* )
		                    {
		                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2242:6: ( ( (lv_memberCallArguments_19_0= ruleXExpression ) ) (otherlv_20= ',' ( (lv_memberCallArguments_21_0= ruleXExpression ) ) )* )
		                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2242:7: ( (lv_memberCallArguments_19_0= ruleXExpression ) ) (otherlv_20= ',' ( (lv_memberCallArguments_21_0= ruleXExpression ) ) )*
		                    {
		                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2242:7: ( (lv_memberCallArguments_19_0= ruleXExpression ) )
		                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2243:1: (lv_memberCallArguments_19_0= ruleXExpression )
		                    {
		                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2243:1: (lv_memberCallArguments_19_0= ruleXExpression )
		                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2244:3: lv_memberCallArguments_19_0= ruleXExpression
		                    {
		                    if ( state.backtracking==0 ) {

					        newCompositeNode(grammarAccess.getXMemberFeatureCallAccess().getMemberCallArgumentsXExpressionParserRuleCall_1_1_3_1_1_0_0());

		                    }
		                    pushFollow(FOLLOW_ruleXExpression_in_ruleXMemberFeatureCall5729);
		                    lv_memberCallArguments_19_0=ruleXExpression();

		                    state._fsp--;
		                    if (state.failed) return current;
		                    if ( state.backtracking==0 ) {

					        if (current==null) {
					            current = createModelElementForParent(grammarAccess.getXMemberFeatureCallRule());
					        }
							add(
								current,
								"memberCallArguments",
							lv_memberCallArguments_19_0,
							"XExpression");
					        afterParserOrEnumRuleCall();

		                    }

		                    }


		                    }

		                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2260:2: (otherlv_20= ',' ( (lv_memberCallArguments_21_0= ruleXExpression ) ) )*
		                    loop35:
		                    do {
		                        int alt35=2;
		                        int LA35_0 = input.LA(1);

		                        if ( (LA35_0==57) ) {
		                            alt35=1;
		                        }


		                        switch (alt35) {
					case 1 :
					    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2260:4: otherlv_20= ',' ( (lv_memberCallArguments_21_0= ruleXExpression ) )
					    {
					    otherlv_20=(Token)match(input,57,FOLLOW_57_in_ruleXMemberFeatureCall5742); if (state.failed) return current;
					    if ( state.backtracking==0 ) {

							newLeafNode(otherlv_20, grammarAccess.getXMemberFeatureCallAccess().getCommaKeyword_1_1_3_1_1_1_0());

					    }
					    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2264:1: ( (lv_memberCallArguments_21_0= ruleXExpression ) )
					    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2265:1: (lv_memberCallArguments_21_0= ruleXExpression )
					    {
					    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2265:1: (lv_memberCallArguments_21_0= ruleXExpression )
					    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2266:3: lv_memberCallArguments_21_0= ruleXExpression
					    {
					    if ( state.backtracking==0 ) {

						        newCompositeNode(grammarAccess.getXMemberFeatureCallAccess().getMemberCallArgumentsXExpressionParserRuleCall_1_1_3_1_1_1_1_0());

					    }
					    pushFollow(FOLLOW_ruleXExpression_in_ruleXMemberFeatureCall5763);
					    lv_memberCallArguments_21_0=ruleXExpression();

					    state._fsp--;
					    if (state.failed) return current;
					    if ( state.backtracking==0 ) {

						        if (current==null) {
						            current = createModelElementForParent(grammarAccess.getXMemberFeatureCallRule());
						        }
								add(
									current,
									"memberCallArguments",
								lv_memberCallArguments_21_0,
								"XExpression");
						        afterParserOrEnumRuleCall();

					    }

					    }


					    }


					    }
					    break;

					default :
					    break loop35;
		                        }
		                    } while (true);


		                    }


		                    }
		                    break;

		            }

		            otherlv_22=(Token)match(input,59,FOLLOW_59_in_ruleXMemberFeatureCall5780); if (state.failed) return current;
		            if ( state.backtracking==0 ) {

					newLeafNode(otherlv_22, grammarAccess.getXMemberFeatureCallAccess().getRightParenthesisKeyword_1_1_3_2());

		            }

		            }
		            break;

		    }

		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2286:3: ( ( ( () '[' ) )=> (lv_memberCallArguments_23_0= ruleXClosure ) )?
		    int alt38=2;
		    int LA38_0 = input.LA(1);

		    if ( (LA38_0==63) && (synpred15_InternalDSEL())) {
		        alt38=1;
		    }
		    switch (alt38) {
		        case 1 :
		            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2286:4: ( ( () '[' ) )=> (lv_memberCallArguments_23_0= ruleXClosure )
		            {
		            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2289:1: (lv_memberCallArguments_23_0= ruleXClosure )
		            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2290:3: lv_memberCallArguments_23_0= ruleXClosure
		            {
		            if ( state.backtracking==0 ) {

				        newCompositeNode(grammarAccess.getXMemberFeatureCallAccess().getMemberCallArgumentsXClosureParserRuleCall_1_1_4_0());

		            }
		            pushFollow(FOLLOW_ruleXClosure_in_ruleXMemberFeatureCall5815);
		            lv_memberCallArguments_23_0=ruleXClosure();

		            state._fsp--;
		            if (state.failed) return current;
		            if ( state.backtracking==0 ) {

				        if (current==null) {
				            current = createModelElementForParent(grammarAccess.getXMemberFeatureCallRule());
				        }
						add(
							current,
							"memberCallArguments",
						lv_memberCallArguments_23_0,
						"XClosure");
				        afterParserOrEnumRuleCall();

		            }

		            }


		            }
		            break;

		    }


		    }


		    }
		    break;

		default :
		    break loop39;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleXMemberFeatureCall"


    // $ANTLR start "entryRuleXSetLiteral"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2316:1: entryRuleXSetLiteral returns [EObject current=null] : iv_ruleXSetLiteral= ruleXSetLiteral EOF ;
    public final EObject entryRuleXSetLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXSetLiteral = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2317:2: (iv_ruleXSetLiteral= ruleXSetLiteral EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2318:2: iv_ruleXSetLiteral= ruleXSetLiteral EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXSetLiteralRule());
            }
            pushFollow(FOLLOW_ruleXSetLiteral_in_entryRuleXSetLiteral5857);
            iv_ruleXSetLiteral=ruleXSetLiteral();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXSetLiteral;
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleXSetLiteral5867); if (state.failed) return current;

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
    // $ANTLR end "entryRuleXSetLiteral"


    // $ANTLR start "ruleXSetLiteral"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2325:1: ruleXSetLiteral returns [EObject current=null] : ( () otherlv_1= '#' otherlv_2= '{' ( ( (lv_elements_3_0= ruleXExpression ) ) (otherlv_4= ',' ( (lv_elements_5_0= ruleXExpression ) ) )* )? otherlv_6= '}' ) ;
    public final EObject ruleXSetLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        EObject lv_elements_3_0 = null;

        EObject lv_elements_5_0 = null;


         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2328:28: ( ( () otherlv_1= '#' otherlv_2= '{' ( ( (lv_elements_3_0= ruleXExpression ) ) (otherlv_4= ',' ( (lv_elements_5_0= ruleXExpression ) ) )* )? otherlv_6= '}' ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2329:1: ( () otherlv_1= '#' otherlv_2= '{' ( ( (lv_elements_3_0= ruleXExpression ) ) (otherlv_4= ',' ( (lv_elements_5_0= ruleXExpression ) ) )* )? otherlv_6= '}' )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2329:1: ( () otherlv_1= '#' otherlv_2= '{' ( ( (lv_elements_3_0= ruleXExpression ) ) (otherlv_4= ',' ( (lv_elements_5_0= ruleXExpression ) ) )* )? otherlv_6= '}' )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2329:2: () otherlv_1= '#' otherlv_2= '{' ( ( (lv_elements_3_0= ruleXExpression ) ) (otherlv_4= ',' ( (lv_elements_5_0= ruleXExpression ) ) )* )? otherlv_6= '}'
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2329:2: ()
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2330:5:
            {
            if ( state.backtracking==0 ) {

                      current = forceCreateModelElement(
                          grammarAccess.getXSetLiteralAccess().getXSetLiteralAction_0(),
                          current);

            }

            }

            otherlv_1=(Token)match(input,60,FOLLOW_60_in_ruleXSetLiteral5913); if (state.failed) return current;
            if ( state.backtracking==0 ) {

			newLeafNode(otherlv_1, grammarAccess.getXSetLiteralAccess().getNumberSignKeyword_1());

            }
            otherlv_2=(Token)match(input,61,FOLLOW_61_in_ruleXSetLiteral5925); if (state.failed) return current;
            if ( state.backtracking==0 ) {

			newLeafNode(otherlv_2, grammarAccess.getXSetLiteralAccess().getLeftCurlyBracketKeyword_2());

            }
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2343:1: ( ( (lv_elements_3_0= ruleXExpression ) ) (otherlv_4= ',' ( (lv_elements_5_0= ruleXExpression ) ) )* )?
            int alt41=2;
            int LA41_0 = input.LA(1);

            if ( ((LA41_0>=RULE_INT && LA41_0<=RULE_ID)||LA41_0==23||(LA41_0>=26 && LA41_0<=29)||(LA41_0>=35 && LA41_0<=40)||LA41_0==58||LA41_0==67||(LA41_0>=71 && LA41_0<=75)) ) {
                alt41=1;
            }
            switch (alt41) {
                case 1 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2343:2: ( (lv_elements_3_0= ruleXExpression ) ) (otherlv_4= ',' ( (lv_elements_5_0= ruleXExpression ) ) )*
                    {
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2343:2: ( (lv_elements_3_0= ruleXExpression ) )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2344:1: (lv_elements_3_0= ruleXExpression )
                    {
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2344:1: (lv_elements_3_0= ruleXExpression )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2345:3: lv_elements_3_0= ruleXExpression
                    {
                    if ( state.backtracking==0 ) {

			        newCompositeNode(grammarAccess.getXSetLiteralAccess().getElementsXExpressionParserRuleCall_3_0_0());

                    }
                    pushFollow(FOLLOW_ruleXExpression_in_ruleXSetLiteral5947);
                    lv_elements_3_0=ruleXExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

			        if (current==null) {
			            current = createModelElementForParent(grammarAccess.getXSetLiteralRule());
			        }
					add(
						current,
						"elements",
					lv_elements_3_0,
					"XExpression");
			        afterParserOrEnumRuleCall();

                    }

                    }


                    }

                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2361:2: (otherlv_4= ',' ( (lv_elements_5_0= ruleXExpression ) ) )*
                    loop40:
                    do {
                        int alt40=2;
                        int LA40_0 = input.LA(1);

                        if ( (LA40_0==57) ) {
                            alt40=1;
                        }


                        switch (alt40) {
			case 1 :
			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2361:4: otherlv_4= ',' ( (lv_elements_5_0= ruleXExpression ) )
			    {
			    otherlv_4=(Token)match(input,57,FOLLOW_57_in_ruleXSetLiteral5960); if (state.failed) return current;
			    if ( state.backtracking==0 ) {

					newLeafNode(otherlv_4, grammarAccess.getXSetLiteralAccess().getCommaKeyword_3_1_0());

			    }
			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2365:1: ( (lv_elements_5_0= ruleXExpression ) )
			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2366:1: (lv_elements_5_0= ruleXExpression )
			    {
			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2366:1: (lv_elements_5_0= ruleXExpression )
			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2367:3: lv_elements_5_0= ruleXExpression
			    {
			    if ( state.backtracking==0 ) {

				        newCompositeNode(grammarAccess.getXSetLiteralAccess().getElementsXExpressionParserRuleCall_3_1_1_0());

			    }
			    pushFollow(FOLLOW_ruleXExpression_in_ruleXSetLiteral5981);
			    lv_elements_5_0=ruleXExpression();

			    state._fsp--;
			    if (state.failed) return current;
			    if ( state.backtracking==0 ) {

				        if (current==null) {
				            current = createModelElementForParent(grammarAccess.getXSetLiteralRule());
				        }
						add(
							current,
							"elements",
						lv_elements_5_0,
						"XExpression");
				        afterParserOrEnumRuleCall();

			    }

			    }


			    }


			    }
			    break;

			default :
			    break loop40;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_6=(Token)match(input,62,FOLLOW_62_in_ruleXSetLiteral5997); if (state.failed) return current;
            if ( state.backtracking==0 ) {

			newLeafNode(otherlv_6, grammarAccess.getXSetLiteralAccess().getRightCurlyBracketKeyword_4());

            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleXSetLiteral"


    // $ANTLR start "entryRuleXListLiteral"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2395:1: entryRuleXListLiteral returns [EObject current=null] : iv_ruleXListLiteral= ruleXListLiteral EOF ;
    public final EObject entryRuleXListLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXListLiteral = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2396:2: (iv_ruleXListLiteral= ruleXListLiteral EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2397:2: iv_ruleXListLiteral= ruleXListLiteral EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXListLiteralRule());
            }
            pushFollow(FOLLOW_ruleXListLiteral_in_entryRuleXListLiteral6033);
            iv_ruleXListLiteral=ruleXListLiteral();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXListLiteral;
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleXListLiteral6043); if (state.failed) return current;

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
    // $ANTLR end "entryRuleXListLiteral"


    // $ANTLR start "ruleXListLiteral"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2404:1: ruleXListLiteral returns [EObject current=null] : ( () otherlv_1= '#' otherlv_2= '[' ( ( (lv_elements_3_0= ruleXExpression ) ) (otherlv_4= ',' ( (lv_elements_5_0= ruleXExpression ) ) )* )? otherlv_6= ']' ) ;
    public final EObject ruleXListLiteral() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        EObject lv_elements_3_0 = null;

        EObject lv_elements_5_0 = null;


         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2407:28: ( ( () otherlv_1= '#' otherlv_2= '[' ( ( (lv_elements_3_0= ruleXExpression ) ) (otherlv_4= ',' ( (lv_elements_5_0= ruleXExpression ) ) )* )? otherlv_6= ']' ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2408:1: ( () otherlv_1= '#' otherlv_2= '[' ( ( (lv_elements_3_0= ruleXExpression ) ) (otherlv_4= ',' ( (lv_elements_5_0= ruleXExpression ) ) )* )? otherlv_6= ']' )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2408:1: ( () otherlv_1= '#' otherlv_2= '[' ( ( (lv_elements_3_0= ruleXExpression ) ) (otherlv_4= ',' ( (lv_elements_5_0= ruleXExpression ) ) )* )? otherlv_6= ']' )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2408:2: () otherlv_1= '#' otherlv_2= '[' ( ( (lv_elements_3_0= ruleXExpression ) ) (otherlv_4= ',' ( (lv_elements_5_0= ruleXExpression ) ) )* )? otherlv_6= ']'
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2408:2: ()
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2409:5:
            {
            if ( state.backtracking==0 ) {

                      current = forceCreateModelElement(
                          grammarAccess.getXListLiteralAccess().getXListLiteralAction_0(),
                          current);

            }

            }

            otherlv_1=(Token)match(input,60,FOLLOW_60_in_ruleXListLiteral6089); if (state.failed) return current;
            if ( state.backtracking==0 ) {

			newLeafNode(otherlv_1, grammarAccess.getXListLiteralAccess().getNumberSignKeyword_1());

            }
            otherlv_2=(Token)match(input,63,FOLLOW_63_in_ruleXListLiteral6101); if (state.failed) return current;
            if ( state.backtracking==0 ) {

			newLeafNode(otherlv_2, grammarAccess.getXListLiteralAccess().getLeftSquareBracketKeyword_2());

            }
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2422:1: ( ( (lv_elements_3_0= ruleXExpression ) ) (otherlv_4= ',' ( (lv_elements_5_0= ruleXExpression ) ) )* )?
            int alt43=2;
            int LA43_0 = input.LA(1);

            if ( ((LA43_0>=RULE_INT && LA43_0<=RULE_ID)||LA43_0==23||(LA43_0>=26 && LA43_0<=29)||(LA43_0>=35 && LA43_0<=40)||LA43_0==58||LA43_0==67||(LA43_0>=71 && LA43_0<=75)) ) {
                alt43=1;
            }
            switch (alt43) {
                case 1 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2422:2: ( (lv_elements_3_0= ruleXExpression ) ) (otherlv_4= ',' ( (lv_elements_5_0= ruleXExpression ) ) )*
                    {
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2422:2: ( (lv_elements_3_0= ruleXExpression ) )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2423:1: (lv_elements_3_0= ruleXExpression )
                    {
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2423:1: (lv_elements_3_0= ruleXExpression )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2424:3: lv_elements_3_0= ruleXExpression
                    {
                    if ( state.backtracking==0 ) {

			        newCompositeNode(grammarAccess.getXListLiteralAccess().getElementsXExpressionParserRuleCall_3_0_0());

                    }
                    pushFollow(FOLLOW_ruleXExpression_in_ruleXListLiteral6123);
                    lv_elements_3_0=ruleXExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

			        if (current==null) {
			            current = createModelElementForParent(grammarAccess.getXListLiteralRule());
			        }
					add(
						current,
						"elements",
					lv_elements_3_0,
					"XExpression");
			        afterParserOrEnumRuleCall();

                    }

                    }


                    }

                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2440:2: (otherlv_4= ',' ( (lv_elements_5_0= ruleXExpression ) ) )*
                    loop42:
                    do {
                        int alt42=2;
                        int LA42_0 = input.LA(1);

                        if ( (LA42_0==57) ) {
                            alt42=1;
                        }


                        switch (alt42) {
			case 1 :
			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2440:4: otherlv_4= ',' ( (lv_elements_5_0= ruleXExpression ) )
			    {
			    otherlv_4=(Token)match(input,57,FOLLOW_57_in_ruleXListLiteral6136); if (state.failed) return current;
			    if ( state.backtracking==0 ) {

					newLeafNode(otherlv_4, grammarAccess.getXListLiteralAccess().getCommaKeyword_3_1_0());

			    }
			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2444:1: ( (lv_elements_5_0= ruleXExpression ) )
			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2445:1: (lv_elements_5_0= ruleXExpression )
			    {
			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2445:1: (lv_elements_5_0= ruleXExpression )
			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2446:3: lv_elements_5_0= ruleXExpression
			    {
			    if ( state.backtracking==0 ) {

				        newCompositeNode(grammarAccess.getXListLiteralAccess().getElementsXExpressionParserRuleCall_3_1_1_0());

			    }
			    pushFollow(FOLLOW_ruleXExpression_in_ruleXListLiteral6157);
			    lv_elements_5_0=ruleXExpression();

			    state._fsp--;
			    if (state.failed) return current;
			    if ( state.backtracking==0 ) {

				        if (current==null) {
				            current = createModelElementForParent(grammarAccess.getXListLiteralRule());
				        }
						add(
							current,
							"elements",
						lv_elements_5_0,
						"XExpression");
				        afterParserOrEnumRuleCall();

			    }

			    }


			    }


			    }
			    break;

			default :
			    break loop42;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_6=(Token)match(input,64,FOLLOW_64_in_ruleXListLiteral6173); if (state.failed) return current;
            if ( state.backtracking==0 ) {

			newLeafNode(otherlv_6, grammarAccess.getXListLiteralAccess().getRightSquareBracketKeyword_4());

            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleXListLiteral"


    // $ANTLR start "entryRuleXClosure"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2474:1: entryRuleXClosure returns [EObject current=null] : iv_ruleXClosure= ruleXClosure EOF ;
    public final EObject entryRuleXClosure() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXClosure = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2475:2: (iv_ruleXClosure= ruleXClosure EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2476:2: iv_ruleXClosure= ruleXClosure EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXClosureRule());
            }
            pushFollow(FOLLOW_ruleXClosure_in_entryRuleXClosure6209);
            iv_ruleXClosure=ruleXClosure();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXClosure;
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleXClosure6219); if (state.failed) return current;

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
    // $ANTLR end "entryRuleXClosure"


    // $ANTLR start "ruleXClosure"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2483:1: ruleXClosure returns [EObject current=null] : ( ( ( ( () '[' ) )=> ( () otherlv_1= '[' ) ) ( ( ( ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> ( ( ( (lv_declaredFormalParameters_2_0= ruleJvmFormalParameter ) ) (otherlv_3= ',' ( (lv_declaredFormalParameters_4_0= ruleJvmFormalParameter ) ) )* )? ( (lv_explicitSyntax_5_0= '|' ) ) ) )? ( (lv_expression_6_0= ruleXExpressionInClosure ) ) otherlv_7= ']' ) ;
    public final EObject ruleXClosure() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token lv_explicitSyntax_5_0=null;
        Token otherlv_7=null;
        EObject lv_declaredFormalParameters_2_0 = null;

        EObject lv_declaredFormalParameters_4_0 = null;

        EObject lv_expression_6_0 = null;


         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2486:28: ( ( ( ( ( () '[' ) )=> ( () otherlv_1= '[' ) ) ( ( ( ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> ( ( ( (lv_declaredFormalParameters_2_0= ruleJvmFormalParameter ) ) (otherlv_3= ',' ( (lv_declaredFormalParameters_4_0= ruleJvmFormalParameter ) ) )* )? ( (lv_explicitSyntax_5_0= '|' ) ) ) )? ( (lv_expression_6_0= ruleXExpressionInClosure ) ) otherlv_7= ']' ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2487:1: ( ( ( ( () '[' ) )=> ( () otherlv_1= '[' ) ) ( ( ( ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> ( ( ( (lv_declaredFormalParameters_2_0= ruleJvmFormalParameter ) ) (otherlv_3= ',' ( (lv_declaredFormalParameters_4_0= ruleJvmFormalParameter ) ) )* )? ( (lv_explicitSyntax_5_0= '|' ) ) ) )? ( (lv_expression_6_0= ruleXExpressionInClosure ) ) otherlv_7= ']' )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2487:1: ( ( ( ( () '[' ) )=> ( () otherlv_1= '[' ) ) ( ( ( ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> ( ( ( (lv_declaredFormalParameters_2_0= ruleJvmFormalParameter ) ) (otherlv_3= ',' ( (lv_declaredFormalParameters_4_0= ruleJvmFormalParameter ) ) )* )? ( (lv_explicitSyntax_5_0= '|' ) ) ) )? ( (lv_expression_6_0= ruleXExpressionInClosure ) ) otherlv_7= ']' )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2487:2: ( ( ( () '[' ) )=> ( () otherlv_1= '[' ) ) ( ( ( ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> ( ( ( (lv_declaredFormalParameters_2_0= ruleJvmFormalParameter ) ) (otherlv_3= ',' ( (lv_declaredFormalParameters_4_0= ruleJvmFormalParameter ) ) )* )? ( (lv_explicitSyntax_5_0= '|' ) ) ) )? ( (lv_expression_6_0= ruleXExpressionInClosure ) ) otherlv_7= ']'
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2487:2: ( ( ( () '[' ) )=> ( () otherlv_1= '[' ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2487:3: ( ( () '[' ) )=> ( () otherlv_1= '[' )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2489:5: ( () otherlv_1= '[' )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2489:6: () otherlv_1= '['
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2489:6: ()
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2490:5:
            {
            if ( state.backtracking==0 ) {

                      current = forceCreateModelElement(
                          grammarAccess.getXClosureAccess().getXClosureAction_0_0_0(),
                          current);

            }

            }

            otherlv_1=(Token)match(input,63,FOLLOW_63_in_ruleXClosure6279); if (state.failed) return current;
            if ( state.backtracking==0 ) {

			newLeafNode(otherlv_1, grammarAccess.getXClosureAccess().getLeftSquareBracketKeyword_0_0_1());

            }

            }


            }

            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2499:3: ( ( ( ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> ( ( ( (lv_declaredFormalParameters_2_0= ruleJvmFormalParameter ) ) (otherlv_3= ',' ( (lv_declaredFormalParameters_4_0= ruleJvmFormalParameter ) ) )* )? ( (lv_explicitSyntax_5_0= '|' ) ) ) )?
            int alt46=2;
            alt46 = dfa46.predict(input);
            switch (alt46) {
                case 1 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2499:4: ( ( ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> ( ( ( (lv_declaredFormalParameters_2_0= ruleJvmFormalParameter ) ) (otherlv_3= ',' ( (lv_declaredFormalParameters_4_0= ruleJvmFormalParameter ) ) )* )? ( (lv_explicitSyntax_5_0= '|' ) ) )
                    {
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2514:6: ( ( ( (lv_declaredFormalParameters_2_0= ruleJvmFormalParameter ) ) (otherlv_3= ',' ( (lv_declaredFormalParameters_4_0= ruleJvmFormalParameter ) ) )* )? ( (lv_explicitSyntax_5_0= '|' ) ) )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2514:7: ( ( (lv_declaredFormalParameters_2_0= ruleJvmFormalParameter ) ) (otherlv_3= ',' ( (lv_declaredFormalParameters_4_0= ruleJvmFormalParameter ) ) )* )? ( (lv_explicitSyntax_5_0= '|' ) )
                    {
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2514:7: ( ( (lv_declaredFormalParameters_2_0= ruleJvmFormalParameter ) ) (otherlv_3= ',' ( (lv_declaredFormalParameters_4_0= ruleJvmFormalParameter ) ) )* )?
                    int alt45=2;
                    int LA45_0 = input.LA(1);

                    if ( (LA45_0==RULE_ID||LA45_0==58||LA45_0==77) ) {
                        alt45=1;
                    }
                    switch (alt45) {
                        case 1 :
                            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2514:8: ( (lv_declaredFormalParameters_2_0= ruleJvmFormalParameter ) ) (otherlv_3= ',' ( (lv_declaredFormalParameters_4_0= ruleJvmFormalParameter ) ) )*
                            {
                            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2514:8: ( (lv_declaredFormalParameters_2_0= ruleJvmFormalParameter ) )
                            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2515:1: (lv_declaredFormalParameters_2_0= ruleJvmFormalParameter )
                            {
                            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2515:1: (lv_declaredFormalParameters_2_0= ruleJvmFormalParameter )
                            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2516:3: lv_declaredFormalParameters_2_0= ruleJvmFormalParameter
                            {
                            if ( state.backtracking==0 ) {

				        newCompositeNode(grammarAccess.getXClosureAccess().getDeclaredFormalParametersJvmFormalParameterParserRuleCall_1_0_0_0_0());

                            }
                            pushFollow(FOLLOW_ruleJvmFormalParameter_in_ruleXClosure6352);
                            lv_declaredFormalParameters_2_0=ruleJvmFormalParameter();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

				        if (current==null) {
				            current = createModelElementForParent(grammarAccess.getXClosureRule());
				        }
						add(
							current,
							"declaredFormalParameters",
						lv_declaredFormalParameters_2_0,
						"JvmFormalParameter");
				        afterParserOrEnumRuleCall();

                            }

                            }


                            }

                            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2532:2: (otherlv_3= ',' ( (lv_declaredFormalParameters_4_0= ruleJvmFormalParameter ) ) )*
                            loop44:
                            do {
                                int alt44=2;
                                int LA44_0 = input.LA(1);

                                if ( (LA44_0==57) ) {
                                    alt44=1;
                                }


                                switch (alt44) {
				case 1 :
				    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2532:4: otherlv_3= ',' ( (lv_declaredFormalParameters_4_0= ruleJvmFormalParameter ) )
				    {
				    otherlv_3=(Token)match(input,57,FOLLOW_57_in_ruleXClosure6365); if (state.failed) return current;
				    if ( state.backtracking==0 ) {

						newLeafNode(otherlv_3, grammarAccess.getXClosureAccess().getCommaKeyword_1_0_0_1_0());

				    }
				    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2536:1: ( (lv_declaredFormalParameters_4_0= ruleJvmFormalParameter ) )
				    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2537:1: (lv_declaredFormalParameters_4_0= ruleJvmFormalParameter )
				    {
				    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2537:1: (lv_declaredFormalParameters_4_0= ruleJvmFormalParameter )
				    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2538:3: lv_declaredFormalParameters_4_0= ruleJvmFormalParameter
				    {
				    if ( state.backtracking==0 ) {

					        newCompositeNode(grammarAccess.getXClosureAccess().getDeclaredFormalParametersJvmFormalParameterParserRuleCall_1_0_0_1_1_0());

				    }
				    pushFollow(FOLLOW_ruleJvmFormalParameter_in_ruleXClosure6386);
				    lv_declaredFormalParameters_4_0=ruleJvmFormalParameter();

				    state._fsp--;
				    if (state.failed) return current;
				    if ( state.backtracking==0 ) {

					        if (current==null) {
					            current = createModelElementForParent(grammarAccess.getXClosureRule());
					        }
							add(
								current,
								"declaredFormalParameters",
							lv_declaredFormalParameters_4_0,
							"JvmFormalParameter");
					        afterParserOrEnumRuleCall();

				    }

				    }


				    }


				    }
				    break;

				default :
				    break loop44;
                                }
                            } while (true);


                            }
                            break;

                    }

                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2554:6: ( (lv_explicitSyntax_5_0= '|' ) )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2555:1: (lv_explicitSyntax_5_0= '|' )
                    {
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2555:1: (lv_explicitSyntax_5_0= '|' )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2556:3: lv_explicitSyntax_5_0= '|'
                    {
                    lv_explicitSyntax_5_0=(Token)match(input,65,FOLLOW_65_in_ruleXClosure6408); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              newLeafNode(lv_explicitSyntax_5_0, grammarAccess.getXClosureAccess().getExplicitSyntaxVerticalLineKeyword_1_0_1_0());

                    }
                    if ( state.backtracking==0 ) {

			        if (current==null) {
			            current = createModelElement(grammarAccess.getXClosureRule());
			        }
					setWithLastConsumed(current, "explicitSyntax", true, "|");

                    }

                    }


                    }


                    }


                    }
                    break;

            }

            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2569:5: ( (lv_expression_6_0= ruleXExpressionInClosure ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2570:1: (lv_expression_6_0= ruleXExpressionInClosure )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2570:1: (lv_expression_6_0= ruleXExpressionInClosure )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2571:3: lv_expression_6_0= ruleXExpressionInClosure
            {
            if ( state.backtracking==0 ) {

		        newCompositeNode(grammarAccess.getXClosureAccess().getExpressionXExpressionInClosureParserRuleCall_2_0());

            }
            pushFollow(FOLLOW_ruleXExpressionInClosure_in_ruleXClosure6445);
            lv_expression_6_0=ruleXExpressionInClosure();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

		        if (current==null) {
		            current = createModelElementForParent(grammarAccess.getXClosureRule());
		        }
				set(
					current,
					"expression",
				lv_expression_6_0,
				"XExpressionInClosure");
		        afterParserOrEnumRuleCall();

            }

            }


            }

            otherlv_7=(Token)match(input,64,FOLLOW_64_in_ruleXClosure6457); if (state.failed) return current;
            if ( state.backtracking==0 ) {

			newLeafNode(otherlv_7, grammarAccess.getXClosureAccess().getRightSquareBracketKeyword_3());

            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleXClosure"


    // $ANTLR start "entryRuleXExpressionInClosure"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2599:1: entryRuleXExpressionInClosure returns [EObject current=null] : iv_ruleXExpressionInClosure= ruleXExpressionInClosure EOF ;
    public final EObject entryRuleXExpressionInClosure() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXExpressionInClosure = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2600:2: (iv_ruleXExpressionInClosure= ruleXExpressionInClosure EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2601:2: iv_ruleXExpressionInClosure= ruleXExpressionInClosure EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXExpressionInClosureRule());
            }
            pushFollow(FOLLOW_ruleXExpressionInClosure_in_entryRuleXExpressionInClosure6493);
            iv_ruleXExpressionInClosure=ruleXExpressionInClosure();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXExpressionInClosure;
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleXExpressionInClosure6503); if (state.failed) return current;

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
    // $ANTLR end "entryRuleXExpressionInClosure"


    // $ANTLR start "ruleXExpressionInClosure"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2608:1: ruleXExpressionInClosure returns [EObject current=null] : ( () ( ( (lv_expressions_1_0= ruleXExpressionOrVarDeclaration ) ) (otherlv_2= ';' )? )* ) ;
    public final EObject ruleXExpressionInClosure() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject lv_expressions_1_0 = null;


         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2611:28: ( ( () ( ( (lv_expressions_1_0= ruleXExpressionOrVarDeclaration ) ) (otherlv_2= ';' )? )* ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2612:1: ( () ( ( (lv_expressions_1_0= ruleXExpressionOrVarDeclaration ) ) (otherlv_2= ';' )? )* )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2612:1: ( () ( ( (lv_expressions_1_0= ruleXExpressionOrVarDeclaration ) ) (otherlv_2= ';' )? )* )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2612:2: () ( ( (lv_expressions_1_0= ruleXExpressionOrVarDeclaration ) ) (otherlv_2= ';' )? )*
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2612:2: ()
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2613:5:
            {
            if ( state.backtracking==0 ) {

                      current = forceCreateModelElement(
                          grammarAccess.getXExpressionInClosureAccess().getXBlockExpressionAction_0(),
                          current);

            }

            }

            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2618:2: ( ( (lv_expressions_1_0= ruleXExpressionOrVarDeclaration ) ) (otherlv_2= ';' )? )*
            loop48:
            do {
                int alt48=2;
                int LA48_0 = input.LA(1);

                if ( ((LA48_0>=RULE_INT && LA48_0<=RULE_ID)||LA48_0==23||(LA48_0>=26 && LA48_0<=29)||(LA48_0>=35 && LA48_0<=40)||LA48_0==58||LA48_0==67||(LA48_0>=69 && LA48_0<=75)) ) {
                    alt48=1;
                }


                switch (alt48) {
		case 1 :
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2618:3: ( (lv_expressions_1_0= ruleXExpressionOrVarDeclaration ) ) (otherlv_2= ';' )?
		    {
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2618:3: ( (lv_expressions_1_0= ruleXExpressionOrVarDeclaration ) )
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2619:1: (lv_expressions_1_0= ruleXExpressionOrVarDeclaration )
		    {
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2619:1: (lv_expressions_1_0= ruleXExpressionOrVarDeclaration )
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2620:3: lv_expressions_1_0= ruleXExpressionOrVarDeclaration
		    {
		    if ( state.backtracking==0 ) {

			        newCompositeNode(grammarAccess.getXExpressionInClosureAccess().getExpressionsXExpressionOrVarDeclarationParserRuleCall_1_0_0());

		    }
		    pushFollow(FOLLOW_ruleXExpressionOrVarDeclaration_in_ruleXExpressionInClosure6559);
		    lv_expressions_1_0=ruleXExpressionOrVarDeclaration();

		    state._fsp--;
		    if (state.failed) return current;
		    if ( state.backtracking==0 ) {

			        if (current==null) {
			            current = createModelElementForParent(grammarAccess.getXExpressionInClosureRule());
			        }
					add(
						current,
						"expressions",
					lv_expressions_1_0,
					"XExpressionOrVarDeclaration");
			        afterParserOrEnumRuleCall();

		    }

		    }


		    }

		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2636:2: (otherlv_2= ';' )?
		    int alt47=2;
		    int LA47_0 = input.LA(1);

		    if ( (LA47_0==66) ) {
		        alt47=1;
		    }
		    switch (alt47) {
		        case 1 :
		            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2636:4: otherlv_2= ';'
		            {
		            otherlv_2=(Token)match(input,66,FOLLOW_66_in_ruleXExpressionInClosure6572); if (state.failed) return current;
		            if ( state.backtracking==0 ) {

					newLeafNode(otherlv_2, grammarAccess.getXExpressionInClosureAccess().getSemicolonKeyword_1_1());

		            }

		            }
		            break;

		    }


		    }
		    break;

		default :
		    break loop48;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleXExpressionInClosure"


    // $ANTLR start "entryRuleXShortClosure"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2648:1: entryRuleXShortClosure returns [EObject current=null] : iv_ruleXShortClosure= ruleXShortClosure EOF ;
    public final EObject entryRuleXShortClosure() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXShortClosure = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2649:2: (iv_ruleXShortClosure= ruleXShortClosure EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2650:2: iv_ruleXShortClosure= ruleXShortClosure EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXShortClosureRule());
            }
            pushFollow(FOLLOW_ruleXShortClosure_in_entryRuleXShortClosure6612);
            iv_ruleXShortClosure=ruleXShortClosure();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXShortClosure;
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleXShortClosure6622); if (state.failed) return current;

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
    // $ANTLR end "entryRuleXShortClosure"


    // $ANTLR start "ruleXShortClosure"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2657:1: ruleXShortClosure returns [EObject current=null] : ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> ( () ( ( (lv_declaredFormalParameters_1_0= ruleJvmFormalParameter ) ) (otherlv_2= ',' ( (lv_declaredFormalParameters_3_0= ruleJvmFormalParameter ) ) )* )? ( (lv_explicitSyntax_4_0= '|' ) ) ) ) ( (lv_expression_5_0= ruleXExpression ) ) ) ;
    public final EObject ruleXShortClosure() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token lv_explicitSyntax_4_0=null;
        EObject lv_declaredFormalParameters_1_0 = null;

        EObject lv_declaredFormalParameters_3_0 = null;

        EObject lv_expression_5_0 = null;


         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2660:28: ( ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> ( () ( ( (lv_declaredFormalParameters_1_0= ruleJvmFormalParameter ) ) (otherlv_2= ',' ( (lv_declaredFormalParameters_3_0= ruleJvmFormalParameter ) ) )* )? ( (lv_explicitSyntax_4_0= '|' ) ) ) ) ( (lv_expression_5_0= ruleXExpression ) ) ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2661:1: ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> ( () ( ( (lv_declaredFormalParameters_1_0= ruleJvmFormalParameter ) ) (otherlv_2= ',' ( (lv_declaredFormalParameters_3_0= ruleJvmFormalParameter ) ) )* )? ( (lv_explicitSyntax_4_0= '|' ) ) ) ) ( (lv_expression_5_0= ruleXExpression ) ) )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2661:1: ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> ( () ( ( (lv_declaredFormalParameters_1_0= ruleJvmFormalParameter ) ) (otherlv_2= ',' ( (lv_declaredFormalParameters_3_0= ruleJvmFormalParameter ) ) )* )? ( (lv_explicitSyntax_4_0= '|' ) ) ) ) ( (lv_expression_5_0= ruleXExpression ) ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2661:2: ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> ( () ( ( (lv_declaredFormalParameters_1_0= ruleJvmFormalParameter ) ) (otherlv_2= ',' ( (lv_declaredFormalParameters_3_0= ruleJvmFormalParameter ) ) )* )? ( (lv_explicitSyntax_4_0= '|' ) ) ) ) ( (lv_expression_5_0= ruleXExpression ) )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2661:2: ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> ( () ( ( (lv_declaredFormalParameters_1_0= ruleJvmFormalParameter ) ) (otherlv_2= ',' ( (lv_declaredFormalParameters_3_0= ruleJvmFormalParameter ) ) )* )? ( (lv_explicitSyntax_4_0= '|' ) ) ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2661:3: ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> ( () ( ( (lv_declaredFormalParameters_1_0= ruleJvmFormalParameter ) ) (otherlv_2= ',' ( (lv_declaredFormalParameters_3_0= ruleJvmFormalParameter ) ) )* )? ( (lv_explicitSyntax_4_0= '|' ) ) )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2677:6: ( () ( ( (lv_declaredFormalParameters_1_0= ruleJvmFormalParameter ) ) (otherlv_2= ',' ( (lv_declaredFormalParameters_3_0= ruleJvmFormalParameter ) ) )* )? ( (lv_explicitSyntax_4_0= '|' ) ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2677:7: () ( ( (lv_declaredFormalParameters_1_0= ruleJvmFormalParameter ) ) (otherlv_2= ',' ( (lv_declaredFormalParameters_3_0= ruleJvmFormalParameter ) ) )* )? ( (lv_explicitSyntax_4_0= '|' ) )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2677:7: ()
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2678:5:
            {
            if ( state.backtracking==0 ) {

                      current = forceCreateModelElement(
                          grammarAccess.getXShortClosureAccess().getXClosureAction_0_0_0(),
                          current);

            }

            }

            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2683:2: ( ( (lv_declaredFormalParameters_1_0= ruleJvmFormalParameter ) ) (otherlv_2= ',' ( (lv_declaredFormalParameters_3_0= ruleJvmFormalParameter ) ) )* )?
            int alt50=2;
            int LA50_0 = input.LA(1);

            if ( (LA50_0==RULE_ID||LA50_0==58||LA50_0==77) ) {
                alt50=1;
            }
            switch (alt50) {
                case 1 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2683:3: ( (lv_declaredFormalParameters_1_0= ruleJvmFormalParameter ) ) (otherlv_2= ',' ( (lv_declaredFormalParameters_3_0= ruleJvmFormalParameter ) ) )*
                    {
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2683:3: ( (lv_declaredFormalParameters_1_0= ruleJvmFormalParameter ) )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2684:1: (lv_declaredFormalParameters_1_0= ruleJvmFormalParameter )
                    {
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2684:1: (lv_declaredFormalParameters_1_0= ruleJvmFormalParameter )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2685:3: lv_declaredFormalParameters_1_0= ruleJvmFormalParameter
                    {
                    if ( state.backtracking==0 ) {

			        newCompositeNode(grammarAccess.getXShortClosureAccess().getDeclaredFormalParametersJvmFormalParameterParserRuleCall_0_0_1_0_0());

                    }
                    pushFollow(FOLLOW_ruleJvmFormalParameter_in_ruleXShortClosure6730);
                    lv_declaredFormalParameters_1_0=ruleJvmFormalParameter();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

			        if (current==null) {
			            current = createModelElementForParent(grammarAccess.getXShortClosureRule());
			        }
					add(
						current,
						"declaredFormalParameters",
					lv_declaredFormalParameters_1_0,
					"JvmFormalParameter");
			        afterParserOrEnumRuleCall();

                    }

                    }


                    }

                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2701:2: (otherlv_2= ',' ( (lv_declaredFormalParameters_3_0= ruleJvmFormalParameter ) ) )*
                    loop49:
                    do {
                        int alt49=2;
                        int LA49_0 = input.LA(1);

                        if ( (LA49_0==57) ) {
                            alt49=1;
                        }


                        switch (alt49) {
			case 1 :
			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2701:4: otherlv_2= ',' ( (lv_declaredFormalParameters_3_0= ruleJvmFormalParameter ) )
			    {
			    otherlv_2=(Token)match(input,57,FOLLOW_57_in_ruleXShortClosure6743); if (state.failed) return current;
			    if ( state.backtracking==0 ) {

					newLeafNode(otherlv_2, grammarAccess.getXShortClosureAccess().getCommaKeyword_0_0_1_1_0());

			    }
			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2705:1: ( (lv_declaredFormalParameters_3_0= ruleJvmFormalParameter ) )
			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2706:1: (lv_declaredFormalParameters_3_0= ruleJvmFormalParameter )
			    {
			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2706:1: (lv_declaredFormalParameters_3_0= ruleJvmFormalParameter )
			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2707:3: lv_declaredFormalParameters_3_0= ruleJvmFormalParameter
			    {
			    if ( state.backtracking==0 ) {

				        newCompositeNode(grammarAccess.getXShortClosureAccess().getDeclaredFormalParametersJvmFormalParameterParserRuleCall_0_0_1_1_1_0());

			    }
			    pushFollow(FOLLOW_ruleJvmFormalParameter_in_ruleXShortClosure6764);
			    lv_declaredFormalParameters_3_0=ruleJvmFormalParameter();

			    state._fsp--;
			    if (state.failed) return current;
			    if ( state.backtracking==0 ) {

				        if (current==null) {
				            current = createModelElementForParent(grammarAccess.getXShortClosureRule());
				        }
						add(
							current,
							"declaredFormalParameters",
						lv_declaredFormalParameters_3_0,
						"JvmFormalParameter");
				        afterParserOrEnumRuleCall();

			    }

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

            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2723:6: ( (lv_explicitSyntax_4_0= '|' ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2724:1: (lv_explicitSyntax_4_0= '|' )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2724:1: (lv_explicitSyntax_4_0= '|' )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2725:3: lv_explicitSyntax_4_0= '|'
            {
            lv_explicitSyntax_4_0=(Token)match(input,65,FOLLOW_65_in_ruleXShortClosure6786); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      newLeafNode(lv_explicitSyntax_4_0, grammarAccess.getXShortClosureAccess().getExplicitSyntaxVerticalLineKeyword_0_0_2_0());

            }
            if ( state.backtracking==0 ) {

		        if (current==null) {
		            current = createModelElement(grammarAccess.getXShortClosureRule());
		        }
				setWithLastConsumed(current, "explicitSyntax", true, "|");

            }

            }


            }


            }


            }

            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2738:4: ( (lv_expression_5_0= ruleXExpression ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2739:1: (lv_expression_5_0= ruleXExpression )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2739:1: (lv_expression_5_0= ruleXExpression )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2740:3: lv_expression_5_0= ruleXExpression
            {
            if ( state.backtracking==0 ) {

		        newCompositeNode(grammarAccess.getXShortClosureAccess().getExpressionXExpressionParserRuleCall_1_0());

            }
            pushFollow(FOLLOW_ruleXExpression_in_ruleXShortClosure6822);
            lv_expression_5_0=ruleXExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

		        if (current==null) {
		            current = createModelElementForParent(grammarAccess.getXShortClosureRule());
		        }
				set(
					current,
					"expression",
				lv_expression_5_0,
				"XExpression");
		        afterParserOrEnumRuleCall();

            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleXShortClosure"


    // $ANTLR start "entryRuleXParenthesizedExpression"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2764:1: entryRuleXParenthesizedExpression returns [EObject current=null] : iv_ruleXParenthesizedExpression= ruleXParenthesizedExpression EOF ;
    public final EObject entryRuleXParenthesizedExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXParenthesizedExpression = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2765:2: (iv_ruleXParenthesizedExpression= ruleXParenthesizedExpression EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2766:2: iv_ruleXParenthesizedExpression= ruleXParenthesizedExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXParenthesizedExpressionRule());
            }
            pushFollow(FOLLOW_ruleXParenthesizedExpression_in_entryRuleXParenthesizedExpression6858);
            iv_ruleXParenthesizedExpression=ruleXParenthesizedExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXParenthesizedExpression;
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleXParenthesizedExpression6868); if (state.failed) return current;

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
    // $ANTLR end "entryRuleXParenthesizedExpression"


    // $ANTLR start "ruleXParenthesizedExpression"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2773:1: ruleXParenthesizedExpression returns [EObject current=null] : (otherlv_0= '(' this_XExpression_1= ruleXExpression otherlv_2= ')' ) ;
    public final EObject ruleXParenthesizedExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        EObject this_XExpression_1 = null;


         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2776:28: ( (otherlv_0= '(' this_XExpression_1= ruleXExpression otherlv_2= ')' ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2777:1: (otherlv_0= '(' this_XExpression_1= ruleXExpression otherlv_2= ')' )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2777:1: (otherlv_0= '(' this_XExpression_1= ruleXExpression otherlv_2= ')' )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2777:3: otherlv_0= '(' this_XExpression_1= ruleXExpression otherlv_2= ')'
            {
            otherlv_0=(Token)match(input,58,FOLLOW_58_in_ruleXParenthesizedExpression6905); if (state.failed) return current;
            if ( state.backtracking==0 ) {

			newLeafNode(otherlv_0, grammarAccess.getXParenthesizedExpressionAccess().getLeftParenthesisKeyword_0());

            }
            if ( state.backtracking==0 ) {

                      newCompositeNode(grammarAccess.getXParenthesizedExpressionAccess().getXExpressionParserRuleCall_1());

            }
            pushFollow(FOLLOW_ruleXExpression_in_ruleXParenthesizedExpression6927);
            this_XExpression_1=ruleXExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current = this_XExpression_1;
                      afterParserOrEnumRuleCall();

            }
            otherlv_2=(Token)match(input,59,FOLLOW_59_in_ruleXParenthesizedExpression6938); if (state.failed) return current;
            if ( state.backtracking==0 ) {

			newLeafNode(otherlv_2, grammarAccess.getXParenthesizedExpressionAccess().getRightParenthesisKeyword_2());

            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleXParenthesizedExpression"


    // $ANTLR start "entryRuleXIfExpression"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2802:1: entryRuleXIfExpression returns [EObject current=null] : iv_ruleXIfExpression= ruleXIfExpression EOF ;
    public final EObject entryRuleXIfExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXIfExpression = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2803:2: (iv_ruleXIfExpression= ruleXIfExpression EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2804:2: iv_ruleXIfExpression= ruleXIfExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXIfExpressionRule());
            }
            pushFollow(FOLLOW_ruleXIfExpression_in_entryRuleXIfExpression6974);
            iv_ruleXIfExpression=ruleXIfExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXIfExpression;
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleXIfExpression6984); if (state.failed) return current;

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
    // $ANTLR end "entryRuleXIfExpression"


    // $ANTLR start "ruleXIfExpression"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2811:1: ruleXIfExpression returns [EObject current=null] : ( () otherlv_1= 'if' otherlv_2= '(' ( (lv_if_3_0= ruleXExpression ) ) otherlv_4= ')' ( (lv_then_5_0= ruleXExpression ) ) ( ( ( 'else' )=>otherlv_6= 'else' ) ( (lv_else_7_0= ruleXExpression ) ) )? ) ;
    public final EObject ruleXIfExpression() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        EObject lv_if_3_0 = null;

        EObject lv_then_5_0 = null;

        EObject lv_else_7_0 = null;


         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2814:28: ( ( () otherlv_1= 'if' otherlv_2= '(' ( (lv_if_3_0= ruleXExpression ) ) otherlv_4= ')' ( (lv_then_5_0= ruleXExpression ) ) ( ( ( 'else' )=>otherlv_6= 'else' ) ( (lv_else_7_0= ruleXExpression ) ) )? ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2815:1: ( () otherlv_1= 'if' otherlv_2= '(' ( (lv_if_3_0= ruleXExpression ) ) otherlv_4= ')' ( (lv_then_5_0= ruleXExpression ) ) ( ( ( 'else' )=>otherlv_6= 'else' ) ( (lv_else_7_0= ruleXExpression ) ) )? )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2815:1: ( () otherlv_1= 'if' otherlv_2= '(' ( (lv_if_3_0= ruleXExpression ) ) otherlv_4= ')' ( (lv_then_5_0= ruleXExpression ) ) ( ( ( 'else' )=>otherlv_6= 'else' ) ( (lv_else_7_0= ruleXExpression ) ) )? )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2815:2: () otherlv_1= 'if' otherlv_2= '(' ( (lv_if_3_0= ruleXExpression ) ) otherlv_4= ')' ( (lv_then_5_0= ruleXExpression ) ) ( ( ( 'else' )=>otherlv_6= 'else' ) ( (lv_else_7_0= ruleXExpression ) ) )?
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2815:2: ()
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2816:5:
            {
            if ( state.backtracking==0 ) {

                      current = forceCreateModelElement(
                          grammarAccess.getXIfExpressionAccess().getXIfExpressionAction_0(),
                          current);

            }

            }

            otherlv_1=(Token)match(input,67,FOLLOW_67_in_ruleXIfExpression7030); if (state.failed) return current;
            if ( state.backtracking==0 ) {

			newLeafNode(otherlv_1, grammarAccess.getXIfExpressionAccess().getIfKeyword_1());

            }
            otherlv_2=(Token)match(input,58,FOLLOW_58_in_ruleXIfExpression7042); if (state.failed) return current;
            if ( state.backtracking==0 ) {

			newLeafNode(otherlv_2, grammarAccess.getXIfExpressionAccess().getLeftParenthesisKeyword_2());

            }
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2829:1: ( (lv_if_3_0= ruleXExpression ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2830:1: (lv_if_3_0= ruleXExpression )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2830:1: (lv_if_3_0= ruleXExpression )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2831:3: lv_if_3_0= ruleXExpression
            {
            if ( state.backtracking==0 ) {

		        newCompositeNode(grammarAccess.getXIfExpressionAccess().getIfXExpressionParserRuleCall_3_0());

            }
            pushFollow(FOLLOW_ruleXExpression_in_ruleXIfExpression7063);
            lv_if_3_0=ruleXExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

		        if (current==null) {
		            current = createModelElementForParent(grammarAccess.getXIfExpressionRule());
		        }
				set(
					current,
					"if",
				lv_if_3_0,
				"XExpression");
		        afterParserOrEnumRuleCall();

            }

            }


            }

            otherlv_4=(Token)match(input,59,FOLLOW_59_in_ruleXIfExpression7075); if (state.failed) return current;
            if ( state.backtracking==0 ) {

			newLeafNode(otherlv_4, grammarAccess.getXIfExpressionAccess().getRightParenthesisKeyword_4());

            }
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2851:1: ( (lv_then_5_0= ruleXExpression ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2852:1: (lv_then_5_0= ruleXExpression )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2852:1: (lv_then_5_0= ruleXExpression )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2853:3: lv_then_5_0= ruleXExpression
            {
            if ( state.backtracking==0 ) {

		        newCompositeNode(grammarAccess.getXIfExpressionAccess().getThenXExpressionParserRuleCall_5_0());

            }
            pushFollow(FOLLOW_ruleXExpression_in_ruleXIfExpression7096);
            lv_then_5_0=ruleXExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

		        if (current==null) {
		            current = createModelElementForParent(grammarAccess.getXIfExpressionRule());
		        }
				set(
					current,
					"then",
				lv_then_5_0,
				"XExpression");
		        afterParserOrEnumRuleCall();

            }

            }


            }

            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2869:2: ( ( ( 'else' )=>otherlv_6= 'else' ) ( (lv_else_7_0= ruleXExpression ) ) )?
            int alt51=2;
            int LA51_0 = input.LA(1);

            if ( (LA51_0==68) ) {
                int LA51_1 = input.LA(2);

                if ( (synpred19_InternalDSEL()) ) {
                    alt51=1;
                }
            }
            switch (alt51) {
                case 1 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2869:3: ( ( 'else' )=>otherlv_6= 'else' ) ( (lv_else_7_0= ruleXExpression ) )
                    {
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2869:3: ( ( 'else' )=>otherlv_6= 'else' )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2869:4: ( 'else' )=>otherlv_6= 'else'
                    {
                    otherlv_6=(Token)match(input,68,FOLLOW_68_in_ruleXIfExpression7117); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

				newLeafNode(otherlv_6, grammarAccess.getXIfExpressionAccess().getElseKeyword_6_0());

                    }

                    }

                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2874:2: ( (lv_else_7_0= ruleXExpression ) )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2875:1: (lv_else_7_0= ruleXExpression )
                    {
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2875:1: (lv_else_7_0= ruleXExpression )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2876:3: lv_else_7_0= ruleXExpression
                    {
                    if ( state.backtracking==0 ) {

			        newCompositeNode(grammarAccess.getXIfExpressionAccess().getElseXExpressionParserRuleCall_6_1_0());

                    }
                    pushFollow(FOLLOW_ruleXExpression_in_ruleXIfExpression7139);
                    lv_else_7_0=ruleXExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

			        if (current==null) {
			            current = createModelElementForParent(grammarAccess.getXIfExpressionRule());
			        }
					set(
						current,
						"else",
					lv_else_7_0,
					"XExpression");
			        afterParserOrEnumRuleCall();

                    }

                    }


                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleXIfExpression"


    // $ANTLR start "entryRuleXExpressionOrVarDeclaration"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2910:1: entryRuleXExpressionOrVarDeclaration returns [EObject current=null] : iv_ruleXExpressionOrVarDeclaration= ruleXExpressionOrVarDeclaration EOF ;
    public final EObject entryRuleXExpressionOrVarDeclaration() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXExpressionOrVarDeclaration = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2911:2: (iv_ruleXExpressionOrVarDeclaration= ruleXExpressionOrVarDeclaration EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2912:2: iv_ruleXExpressionOrVarDeclaration= ruleXExpressionOrVarDeclaration EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXExpressionOrVarDeclarationRule());
            }
            pushFollow(FOLLOW_ruleXExpressionOrVarDeclaration_in_entryRuleXExpressionOrVarDeclaration7187);
            iv_ruleXExpressionOrVarDeclaration=ruleXExpressionOrVarDeclaration();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXExpressionOrVarDeclaration;
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleXExpressionOrVarDeclaration7197); if (state.failed) return current;

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
    // $ANTLR end "entryRuleXExpressionOrVarDeclaration"


    // $ANTLR start "ruleXExpressionOrVarDeclaration"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2919:1: ruleXExpressionOrVarDeclaration returns [EObject current=null] : (this_XVariableDeclaration_0= ruleXVariableDeclaration | this_XExpression_1= ruleXExpression ) ;
    public final EObject ruleXExpressionOrVarDeclaration() throws RecognitionException {
        EObject current = null;

        EObject this_XVariableDeclaration_0 = null;

        EObject this_XExpression_1 = null;


         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2922:28: ( (this_XVariableDeclaration_0= ruleXVariableDeclaration | this_XExpression_1= ruleXExpression ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2923:1: (this_XVariableDeclaration_0= ruleXVariableDeclaration | this_XExpression_1= ruleXExpression )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2923:1: (this_XVariableDeclaration_0= ruleXVariableDeclaration | this_XExpression_1= ruleXExpression )
            int alt52=2;
            int LA52_0 = input.LA(1);

            if ( ((LA52_0>=69 && LA52_0<=70)) ) {
                alt52=1;
            }
            else if ( ((LA52_0>=RULE_INT && LA52_0<=RULE_ID)||LA52_0==23||(LA52_0>=26 && LA52_0<=29)||(LA52_0>=35 && LA52_0<=40)||LA52_0==58||LA52_0==67||(LA52_0>=71 && LA52_0<=75)) ) {
                alt52=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 52, 0, input);

                throw nvae;
            }
            switch (alt52) {
                case 1 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2924:5: this_XVariableDeclaration_0= ruleXVariableDeclaration
                    {
                    if ( state.backtracking==0 ) {

                              newCompositeNode(grammarAccess.getXExpressionOrVarDeclarationAccess().getXVariableDeclarationParserRuleCall_0());

                    }
                    pushFollow(FOLLOW_ruleXVariableDeclaration_in_ruleXExpressionOrVarDeclaration7244);
                    this_XVariableDeclaration_0=ruleXVariableDeclaration();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = this_XVariableDeclaration_0;
                              afterParserOrEnumRuleCall();

                    }

                    }
                    break;
                case 2 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2934:5: this_XExpression_1= ruleXExpression
                    {
                    if ( state.backtracking==0 ) {

                              newCompositeNode(grammarAccess.getXExpressionOrVarDeclarationAccess().getXExpressionParserRuleCall_1());

                    }
                    pushFollow(FOLLOW_ruleXExpression_in_ruleXExpressionOrVarDeclaration7271);
                    this_XExpression_1=ruleXExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = this_XExpression_1;
                              afterParserOrEnumRuleCall();

                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleXExpressionOrVarDeclaration"


    // $ANTLR start "entryRuleXVariableDeclaration"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2950:1: entryRuleXVariableDeclaration returns [EObject current=null] : iv_ruleXVariableDeclaration= ruleXVariableDeclaration EOF ;
    public final EObject entryRuleXVariableDeclaration() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXVariableDeclaration = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2951:2: (iv_ruleXVariableDeclaration= ruleXVariableDeclaration EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2952:2: iv_ruleXVariableDeclaration= ruleXVariableDeclaration EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXVariableDeclarationRule());
            }
            pushFollow(FOLLOW_ruleXVariableDeclaration_in_entryRuleXVariableDeclaration7306);
            iv_ruleXVariableDeclaration=ruleXVariableDeclaration();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXVariableDeclaration;
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleXVariableDeclaration7316); if (state.failed) return current;

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
    // $ANTLR end "entryRuleXVariableDeclaration"


    // $ANTLR start "ruleXVariableDeclaration"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2959:1: ruleXVariableDeclaration returns [EObject current=null] : ( () ( ( (lv_writeable_1_0= 'var' ) ) | otherlv_2= 'val' ) ( ( ( ( ( ( ruleJvmTypeReference ) ) ( ( ruleValidID ) ) ) )=> ( ( (lv_type_3_0= ruleJvmTypeReference ) ) ( (lv_name_4_0= ruleValidID ) ) ) ) | ( (lv_name_5_0= ruleValidID ) ) ) (otherlv_6= '=' ( (lv_right_7_0= ruleXExpression ) ) )? ) ;
    public final EObject ruleXVariableDeclaration() throws RecognitionException {
        EObject current = null;

        Token lv_writeable_1_0=null;
        Token otherlv_2=null;
        Token otherlv_6=null;
        EObject lv_type_3_0 = null;

        AntlrDatatypeRuleToken lv_name_4_0 = null;

        AntlrDatatypeRuleToken lv_name_5_0 = null;

        EObject lv_right_7_0 = null;


         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2962:28: ( ( () ( ( (lv_writeable_1_0= 'var' ) ) | otherlv_2= 'val' ) ( ( ( ( ( ( ruleJvmTypeReference ) ) ( ( ruleValidID ) ) ) )=> ( ( (lv_type_3_0= ruleJvmTypeReference ) ) ( (lv_name_4_0= ruleValidID ) ) ) ) | ( (lv_name_5_0= ruleValidID ) ) ) (otherlv_6= '=' ( (lv_right_7_0= ruleXExpression ) ) )? ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2963:1: ( () ( ( (lv_writeable_1_0= 'var' ) ) | otherlv_2= 'val' ) ( ( ( ( ( ( ruleJvmTypeReference ) ) ( ( ruleValidID ) ) ) )=> ( ( (lv_type_3_0= ruleJvmTypeReference ) ) ( (lv_name_4_0= ruleValidID ) ) ) ) | ( (lv_name_5_0= ruleValidID ) ) ) (otherlv_6= '=' ( (lv_right_7_0= ruleXExpression ) ) )? )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2963:1: ( () ( ( (lv_writeable_1_0= 'var' ) ) | otherlv_2= 'val' ) ( ( ( ( ( ( ruleJvmTypeReference ) ) ( ( ruleValidID ) ) ) )=> ( ( (lv_type_3_0= ruleJvmTypeReference ) ) ( (lv_name_4_0= ruleValidID ) ) ) ) | ( (lv_name_5_0= ruleValidID ) ) ) (otherlv_6= '=' ( (lv_right_7_0= ruleXExpression ) ) )? )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2963:2: () ( ( (lv_writeable_1_0= 'var' ) ) | otherlv_2= 'val' ) ( ( ( ( ( ( ruleJvmTypeReference ) ) ( ( ruleValidID ) ) ) )=> ( ( (lv_type_3_0= ruleJvmTypeReference ) ) ( (lv_name_4_0= ruleValidID ) ) ) ) | ( (lv_name_5_0= ruleValidID ) ) ) (otherlv_6= '=' ( (lv_right_7_0= ruleXExpression ) ) )?
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2963:2: ()
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2964:5:
            {
            if ( state.backtracking==0 ) {

                      current = forceCreateModelElement(
                          grammarAccess.getXVariableDeclarationAccess().getXVariableDeclarationAction_0(),
                          current);

            }

            }

            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2969:2: ( ( (lv_writeable_1_0= 'var' ) ) | otherlv_2= 'val' )
            int alt53=2;
            int LA53_0 = input.LA(1);

            if ( (LA53_0==69) ) {
                alt53=1;
            }
            else if ( (LA53_0==70) ) {
                alt53=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 53, 0, input);

                throw nvae;
            }
            switch (alt53) {
                case 1 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2969:3: ( (lv_writeable_1_0= 'var' ) )
                    {
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2969:3: ( (lv_writeable_1_0= 'var' ) )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2970:1: (lv_writeable_1_0= 'var' )
                    {
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2970:1: (lv_writeable_1_0= 'var' )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2971:3: lv_writeable_1_0= 'var'
                    {
                    lv_writeable_1_0=(Token)match(input,69,FOLLOW_69_in_ruleXVariableDeclaration7369); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              newLeafNode(lv_writeable_1_0, grammarAccess.getXVariableDeclarationAccess().getWriteableVarKeyword_1_0_0());

                    }
                    if ( state.backtracking==0 ) {

			        if (current==null) {
			            current = createModelElement(grammarAccess.getXVariableDeclarationRule());
			        }
					setWithLastConsumed(current, "writeable", true, "var");

                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2985:7: otherlv_2= 'val'
                    {
                    otherlv_2=(Token)match(input,70,FOLLOW_70_in_ruleXVariableDeclaration7400); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

				newLeafNode(otherlv_2, grammarAccess.getXVariableDeclarationAccess().getValKeyword_1_1());

                    }

                    }
                    break;

            }

            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2989:2: ( ( ( ( ( ( ruleJvmTypeReference ) ) ( ( ruleValidID ) ) ) )=> ( ( (lv_type_3_0= ruleJvmTypeReference ) ) ( (lv_name_4_0= ruleValidID ) ) ) ) | ( (lv_name_5_0= ruleValidID ) ) )
            int alt54=2;
            int LA54_0 = input.LA(1);

            if ( (LA54_0==RULE_ID) ) {
                int LA54_1 = input.LA(2);

                if ( (synpred20_InternalDSEL()) ) {
                    alt54=1;
                }
                else if ( (true) ) {
                    alt54=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 54, 1, input);

                    throw nvae;
                }
            }
            else if ( (LA54_0==58) && (synpred20_InternalDSEL())) {
                alt54=1;
            }
            else if ( (LA54_0==77) && (synpred20_InternalDSEL())) {
                alt54=1;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 54, 0, input);

                throw nvae;
            }
            switch (alt54) {
                case 1 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2989:3: ( ( ( ( ( ruleJvmTypeReference ) ) ( ( ruleValidID ) ) ) )=> ( ( (lv_type_3_0= ruleJvmTypeReference ) ) ( (lv_name_4_0= ruleValidID ) ) ) )
                    {
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2989:3: ( ( ( ( ( ruleJvmTypeReference ) ) ( ( ruleValidID ) ) ) )=> ( ( (lv_type_3_0= ruleJvmTypeReference ) ) ( (lv_name_4_0= ruleValidID ) ) ) )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2989:4: ( ( ( ( ruleJvmTypeReference ) ) ( ( ruleValidID ) ) ) )=> ( ( (lv_type_3_0= ruleJvmTypeReference ) ) ( (lv_name_4_0= ruleValidID ) ) )
                    {
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2997:6: ( ( (lv_type_3_0= ruleJvmTypeReference ) ) ( (lv_name_4_0= ruleValidID ) ) )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2997:7: ( (lv_type_3_0= ruleJvmTypeReference ) ) ( (lv_name_4_0= ruleValidID ) )
                    {
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2997:7: ( (lv_type_3_0= ruleJvmTypeReference ) )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2998:1: (lv_type_3_0= ruleJvmTypeReference )
                    {
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2998:1: (lv_type_3_0= ruleJvmTypeReference )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2999:3: lv_type_3_0= ruleJvmTypeReference
                    {
                    if ( state.backtracking==0 ) {

			        newCompositeNode(grammarAccess.getXVariableDeclarationAccess().getTypeJvmTypeReferenceParserRuleCall_2_0_0_0_0());

                    }
                    pushFollow(FOLLOW_ruleJvmTypeReference_in_ruleXVariableDeclaration7448);
                    lv_type_3_0=ruleJvmTypeReference();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

			        if (current==null) {
			            current = createModelElementForParent(grammarAccess.getXVariableDeclarationRule());
			        }
					set(
						current,
						"type",
					lv_type_3_0,
					"JvmTypeReference");
			        afterParserOrEnumRuleCall();

                    }

                    }


                    }

                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3015:2: ( (lv_name_4_0= ruleValidID ) )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3016:1: (lv_name_4_0= ruleValidID )
                    {
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3016:1: (lv_name_4_0= ruleValidID )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3017:3: lv_name_4_0= ruleValidID
                    {
                    if ( state.backtracking==0 ) {

			        newCompositeNode(grammarAccess.getXVariableDeclarationAccess().getNameValidIDParserRuleCall_2_0_0_1_0());

                    }
                    pushFollow(FOLLOW_ruleValidID_in_ruleXVariableDeclaration7469);
                    lv_name_4_0=ruleValidID();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

			        if (current==null) {
			            current = createModelElementForParent(grammarAccess.getXVariableDeclarationRule());
			        }
					set(
						current,
						"name",
					lv_name_4_0,
					"ValidID");
			        afterParserOrEnumRuleCall();

                    }

                    }


                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3034:6: ( (lv_name_5_0= ruleValidID ) )
                    {
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3034:6: ( (lv_name_5_0= ruleValidID ) )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3035:1: (lv_name_5_0= ruleValidID )
                    {
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3035:1: (lv_name_5_0= ruleValidID )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3036:3: lv_name_5_0= ruleValidID
                    {
                    if ( state.backtracking==0 ) {

			        newCompositeNode(grammarAccess.getXVariableDeclarationAccess().getNameValidIDParserRuleCall_2_1_0());

                    }
                    pushFollow(FOLLOW_ruleValidID_in_ruleXVariableDeclaration7498);
                    lv_name_5_0=ruleValidID();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

			        if (current==null) {
			            current = createModelElementForParent(grammarAccess.getXVariableDeclarationRule());
			        }
					set(
						current,
						"name",
					lv_name_5_0,
					"ValidID");
			        afterParserOrEnumRuleCall();

                    }

                    }


                    }


                    }
                    break;

            }

            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3052:3: (otherlv_6= '=' ( (lv_right_7_0= ruleXExpression ) ) )?
            int alt55=2;
            int LA55_0 = input.LA(1);

            if ( (LA55_0==17) ) {
                alt55=1;
            }
            switch (alt55) {
                case 1 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3052:5: otherlv_6= '=' ( (lv_right_7_0= ruleXExpression ) )
                    {
                    otherlv_6=(Token)match(input,17,FOLLOW_17_in_ruleXVariableDeclaration7512); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

				newLeafNode(otherlv_6, grammarAccess.getXVariableDeclarationAccess().getEqualsSignKeyword_3_0());

                    }
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3056:1: ( (lv_right_7_0= ruleXExpression ) )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3057:1: (lv_right_7_0= ruleXExpression )
                    {
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3057:1: (lv_right_7_0= ruleXExpression )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3058:3: lv_right_7_0= ruleXExpression
                    {
                    if ( state.backtracking==0 ) {

			        newCompositeNode(grammarAccess.getXVariableDeclarationAccess().getRightXExpressionParserRuleCall_3_1_0());

                    }
                    pushFollow(FOLLOW_ruleXExpression_in_ruleXVariableDeclaration7533);
                    lv_right_7_0=ruleXExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

			        if (current==null) {
			            current = createModelElementForParent(grammarAccess.getXVariableDeclarationRule());
			        }
					set(
						current,
						"right",
					lv_right_7_0,
					"XExpression");
			        afterParserOrEnumRuleCall();

                    }

                    }


                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleXVariableDeclaration"


    // $ANTLR start "entryRuleJvmFormalParameter"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3082:1: entryRuleJvmFormalParameter returns [EObject current=null] : iv_ruleJvmFormalParameter= ruleJvmFormalParameter EOF ;
    public final EObject entryRuleJvmFormalParameter() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleJvmFormalParameter = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3083:2: (iv_ruleJvmFormalParameter= ruleJvmFormalParameter EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3084:2: iv_ruleJvmFormalParameter= ruleJvmFormalParameter EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getJvmFormalParameterRule());
            }
            pushFollow(FOLLOW_ruleJvmFormalParameter_in_entryRuleJvmFormalParameter7571);
            iv_ruleJvmFormalParameter=ruleJvmFormalParameter();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleJvmFormalParameter;
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleJvmFormalParameter7581); if (state.failed) return current;

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
    // $ANTLR end "entryRuleJvmFormalParameter"


    // $ANTLR start "ruleJvmFormalParameter"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3091:1: ruleJvmFormalParameter returns [EObject current=null] : ( ( (lv_parameterType_0_0= ruleJvmTypeReference ) )? ( (lv_name_1_0= ruleValidID ) ) ) ;
    public final EObject ruleJvmFormalParameter() throws RecognitionException {
        EObject current = null;

        EObject lv_parameterType_0_0 = null;

        AntlrDatatypeRuleToken lv_name_1_0 = null;


         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3094:28: ( ( ( (lv_parameterType_0_0= ruleJvmTypeReference ) )? ( (lv_name_1_0= ruleValidID ) ) ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3095:1: ( ( (lv_parameterType_0_0= ruleJvmTypeReference ) )? ( (lv_name_1_0= ruleValidID ) ) )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3095:1: ( ( (lv_parameterType_0_0= ruleJvmTypeReference ) )? ( (lv_name_1_0= ruleValidID ) ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3095:2: ( (lv_parameterType_0_0= ruleJvmTypeReference ) )? ( (lv_name_1_0= ruleValidID ) )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3095:2: ( (lv_parameterType_0_0= ruleJvmTypeReference ) )?
            int alt56=2;
            int LA56_0 = input.LA(1);

            if ( (LA56_0==RULE_ID) ) {
                int LA56_1 = input.LA(2);

                if ( (LA56_1==RULE_ID||LA56_1==23||LA56_1==41||LA56_1==63) ) {
                    alt56=1;
                }
            }
            else if ( (LA56_0==58||LA56_0==77) ) {
                alt56=1;
            }
            switch (alt56) {
                case 1 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3096:1: (lv_parameterType_0_0= ruleJvmTypeReference )
                    {
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3096:1: (lv_parameterType_0_0= ruleJvmTypeReference )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3097:3: lv_parameterType_0_0= ruleJvmTypeReference
                    {
                    if ( state.backtracking==0 ) {

			        newCompositeNode(grammarAccess.getJvmFormalParameterAccess().getParameterTypeJvmTypeReferenceParserRuleCall_0_0());

                    }
                    pushFollow(FOLLOW_ruleJvmTypeReference_in_ruleJvmFormalParameter7627);
                    lv_parameterType_0_0=ruleJvmTypeReference();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

			        if (current==null) {
			            current = createModelElementForParent(grammarAccess.getJvmFormalParameterRule());
			        }
					set(
						current,
						"parameterType",
					lv_parameterType_0_0,
					"JvmTypeReference");
			        afterParserOrEnumRuleCall();

                    }

                    }


                    }
                    break;

            }

            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3113:3: ( (lv_name_1_0= ruleValidID ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3114:1: (lv_name_1_0= ruleValidID )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3114:1: (lv_name_1_0= ruleValidID )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3115:3: lv_name_1_0= ruleValidID
            {
            if ( state.backtracking==0 ) {

		        newCompositeNode(grammarAccess.getJvmFormalParameterAccess().getNameValidIDParserRuleCall_1_0());

            }
            pushFollow(FOLLOW_ruleValidID_in_ruleJvmFormalParameter7649);
            lv_name_1_0=ruleValidID();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

		        if (current==null) {
		            current = createModelElementForParent(grammarAccess.getJvmFormalParameterRule());
		        }
				set(
					current,
					"name",
				lv_name_1_0,
				"ValidID");
		        afterParserOrEnumRuleCall();

            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleJvmFormalParameter"


    // $ANTLR start "entryRuleFullJvmFormalParameter"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3139:1: entryRuleFullJvmFormalParameter returns [EObject current=null] : iv_ruleFullJvmFormalParameter= ruleFullJvmFormalParameter EOF ;
    public final EObject entryRuleFullJvmFormalParameter() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFullJvmFormalParameter = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3140:2: (iv_ruleFullJvmFormalParameter= ruleFullJvmFormalParameter EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3141:2: iv_ruleFullJvmFormalParameter= ruleFullJvmFormalParameter EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getFullJvmFormalParameterRule());
            }
            pushFollow(FOLLOW_ruleFullJvmFormalParameter_in_entryRuleFullJvmFormalParameter7685);
            iv_ruleFullJvmFormalParameter=ruleFullJvmFormalParameter();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleFullJvmFormalParameter;
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleFullJvmFormalParameter7695); if (state.failed) return current;

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
    // $ANTLR end "entryRuleFullJvmFormalParameter"


    // $ANTLR start "ruleFullJvmFormalParameter"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3148:1: ruleFullJvmFormalParameter returns [EObject current=null] : ( ( (lv_parameterType_0_0= ruleJvmTypeReference ) ) ( (lv_name_1_0= ruleValidID ) ) ) ;
    public final EObject ruleFullJvmFormalParameter() throws RecognitionException {
        EObject current = null;

        EObject lv_parameterType_0_0 = null;

        AntlrDatatypeRuleToken lv_name_1_0 = null;


         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3151:28: ( ( ( (lv_parameterType_0_0= ruleJvmTypeReference ) ) ( (lv_name_1_0= ruleValidID ) ) ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3152:1: ( ( (lv_parameterType_0_0= ruleJvmTypeReference ) ) ( (lv_name_1_0= ruleValidID ) ) )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3152:1: ( ( (lv_parameterType_0_0= ruleJvmTypeReference ) ) ( (lv_name_1_0= ruleValidID ) ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3152:2: ( (lv_parameterType_0_0= ruleJvmTypeReference ) ) ( (lv_name_1_0= ruleValidID ) )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3152:2: ( (lv_parameterType_0_0= ruleJvmTypeReference ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3153:1: (lv_parameterType_0_0= ruleJvmTypeReference )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3153:1: (lv_parameterType_0_0= ruleJvmTypeReference )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3154:3: lv_parameterType_0_0= ruleJvmTypeReference
            {
            if ( state.backtracking==0 ) {

		        newCompositeNode(grammarAccess.getFullJvmFormalParameterAccess().getParameterTypeJvmTypeReferenceParserRuleCall_0_0());

            }
            pushFollow(FOLLOW_ruleJvmTypeReference_in_ruleFullJvmFormalParameter7741);
            lv_parameterType_0_0=ruleJvmTypeReference();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

		        if (current==null) {
		            current = createModelElementForParent(grammarAccess.getFullJvmFormalParameterRule());
		        }
				set(
					current,
					"parameterType",
				lv_parameterType_0_0,
				"JvmTypeReference");
		        afterParserOrEnumRuleCall();

            }

            }


            }

            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3170:2: ( (lv_name_1_0= ruleValidID ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3171:1: (lv_name_1_0= ruleValidID )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3171:1: (lv_name_1_0= ruleValidID )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3172:3: lv_name_1_0= ruleValidID
            {
            if ( state.backtracking==0 ) {

		        newCompositeNode(grammarAccess.getFullJvmFormalParameterAccess().getNameValidIDParserRuleCall_1_0());

            }
            pushFollow(FOLLOW_ruleValidID_in_ruleFullJvmFormalParameter7762);
            lv_name_1_0=ruleValidID();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

		        if (current==null) {
		            current = createModelElementForParent(grammarAccess.getFullJvmFormalParameterRule());
		        }
				set(
					current,
					"name",
				lv_name_1_0,
				"ValidID");
		        afterParserOrEnumRuleCall();

            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleFullJvmFormalParameter"


    // $ANTLR start "entryRuleXFeatureCall"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3196:1: entryRuleXFeatureCall returns [EObject current=null] : iv_ruleXFeatureCall= ruleXFeatureCall EOF ;
    public final EObject entryRuleXFeatureCall() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXFeatureCall = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3197:2: (iv_ruleXFeatureCall= ruleXFeatureCall EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3198:2: iv_ruleXFeatureCall= ruleXFeatureCall EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXFeatureCallRule());
            }
            pushFollow(FOLLOW_ruleXFeatureCall_in_entryRuleXFeatureCall7798);
            iv_ruleXFeatureCall=ruleXFeatureCall();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXFeatureCall;
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleXFeatureCall7808); if (state.failed) return current;

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
    // $ANTLR end "entryRuleXFeatureCall"


    // $ANTLR start "ruleXFeatureCall"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3205:1: ruleXFeatureCall returns [EObject current=null] : ( () (otherlv_1= '<' ( (lv_typeArguments_2_0= ruleJvmArgumentTypeReference ) ) (otherlv_3= ',' ( (lv_typeArguments_4_0= ruleJvmArgumentTypeReference ) ) )* otherlv_5= '>' )? ( ( ruleIdOrSuper ) ) ( ( ( ( '(' ) )=> (lv_explicitOperationCall_7_0= '(' ) ) ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_featureCallArguments_8_0= ruleXShortClosure ) ) | ( ( (lv_featureCallArguments_9_0= ruleXExpression ) ) (otherlv_10= ',' ( (lv_featureCallArguments_11_0= ruleXExpression ) ) )* ) )? otherlv_12= ')' )? ( ( ( () '[' ) )=> (lv_featureCallArguments_13_0= ruleXClosure ) )? ) ;
    public final EObject ruleXFeatureCall() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token lv_explicitOperationCall_7_0=null;
        Token otherlv_10=null;
        Token otherlv_12=null;
        EObject lv_typeArguments_2_0 = null;

        EObject lv_typeArguments_4_0 = null;

        EObject lv_featureCallArguments_8_0 = null;

        EObject lv_featureCallArguments_9_0 = null;

        EObject lv_featureCallArguments_11_0 = null;

        EObject lv_featureCallArguments_13_0 = null;


         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3208:28: ( ( () (otherlv_1= '<' ( (lv_typeArguments_2_0= ruleJvmArgumentTypeReference ) ) (otherlv_3= ',' ( (lv_typeArguments_4_0= ruleJvmArgumentTypeReference ) ) )* otherlv_5= '>' )? ( ( ruleIdOrSuper ) ) ( ( ( ( '(' ) )=> (lv_explicitOperationCall_7_0= '(' ) ) ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_featureCallArguments_8_0= ruleXShortClosure ) ) | ( ( (lv_featureCallArguments_9_0= ruleXExpression ) ) (otherlv_10= ',' ( (lv_featureCallArguments_11_0= ruleXExpression ) ) )* ) )? otherlv_12= ')' )? ( ( ( () '[' ) )=> (lv_featureCallArguments_13_0= ruleXClosure ) )? ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3209:1: ( () (otherlv_1= '<' ( (lv_typeArguments_2_0= ruleJvmArgumentTypeReference ) ) (otherlv_3= ',' ( (lv_typeArguments_4_0= ruleJvmArgumentTypeReference ) ) )* otherlv_5= '>' )? ( ( ruleIdOrSuper ) ) ( ( ( ( '(' ) )=> (lv_explicitOperationCall_7_0= '(' ) ) ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_featureCallArguments_8_0= ruleXShortClosure ) ) | ( ( (lv_featureCallArguments_9_0= ruleXExpression ) ) (otherlv_10= ',' ( (lv_featureCallArguments_11_0= ruleXExpression ) ) )* ) )? otherlv_12= ')' )? ( ( ( () '[' ) )=> (lv_featureCallArguments_13_0= ruleXClosure ) )? )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3209:1: ( () (otherlv_1= '<' ( (lv_typeArguments_2_0= ruleJvmArgumentTypeReference ) ) (otherlv_3= ',' ( (lv_typeArguments_4_0= ruleJvmArgumentTypeReference ) ) )* otherlv_5= '>' )? ( ( ruleIdOrSuper ) ) ( ( ( ( '(' ) )=> (lv_explicitOperationCall_7_0= '(' ) ) ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_featureCallArguments_8_0= ruleXShortClosure ) ) | ( ( (lv_featureCallArguments_9_0= ruleXExpression ) ) (otherlv_10= ',' ( (lv_featureCallArguments_11_0= ruleXExpression ) ) )* ) )? otherlv_12= ')' )? ( ( ( () '[' ) )=> (lv_featureCallArguments_13_0= ruleXClosure ) )? )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3209:2: () (otherlv_1= '<' ( (lv_typeArguments_2_0= ruleJvmArgumentTypeReference ) ) (otherlv_3= ',' ( (lv_typeArguments_4_0= ruleJvmArgumentTypeReference ) ) )* otherlv_5= '>' )? ( ( ruleIdOrSuper ) ) ( ( ( ( '(' ) )=> (lv_explicitOperationCall_7_0= '(' ) ) ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_featureCallArguments_8_0= ruleXShortClosure ) ) | ( ( (lv_featureCallArguments_9_0= ruleXExpression ) ) (otherlv_10= ',' ( (lv_featureCallArguments_11_0= ruleXExpression ) ) )* ) )? otherlv_12= ')' )? ( ( ( () '[' ) )=> (lv_featureCallArguments_13_0= ruleXClosure ) )?
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3209:2: ()
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3210:5:
            {
            if ( state.backtracking==0 ) {

                      current = forceCreateModelElement(
                          grammarAccess.getXFeatureCallAccess().getXFeatureCallAction_0(),
                          current);

            }

            }

            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3215:2: (otherlv_1= '<' ( (lv_typeArguments_2_0= ruleJvmArgumentTypeReference ) ) (otherlv_3= ',' ( (lv_typeArguments_4_0= ruleJvmArgumentTypeReference ) ) )* otherlv_5= '>' )?
            int alt58=2;
            int LA58_0 = input.LA(1);

            if ( (LA58_0==23) ) {
                alt58=1;
            }
            switch (alt58) {
                case 1 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3215:4: otherlv_1= '<' ( (lv_typeArguments_2_0= ruleJvmArgumentTypeReference ) ) (otherlv_3= ',' ( (lv_typeArguments_4_0= ruleJvmArgumentTypeReference ) ) )* otherlv_5= '>'
                    {
                    otherlv_1=(Token)match(input,23,FOLLOW_23_in_ruleXFeatureCall7855); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

				newLeafNode(otherlv_1, grammarAccess.getXFeatureCallAccess().getLessThanSignKeyword_1_0());

                    }
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3219:1: ( (lv_typeArguments_2_0= ruleJvmArgumentTypeReference ) )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3220:1: (lv_typeArguments_2_0= ruleJvmArgumentTypeReference )
                    {
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3220:1: (lv_typeArguments_2_0= ruleJvmArgumentTypeReference )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3221:3: lv_typeArguments_2_0= ruleJvmArgumentTypeReference
                    {
                    if ( state.backtracking==0 ) {

			        newCompositeNode(grammarAccess.getXFeatureCallAccess().getTypeArgumentsJvmArgumentTypeReferenceParserRuleCall_1_1_0());

                    }
                    pushFollow(FOLLOW_ruleJvmArgumentTypeReference_in_ruleXFeatureCall7876);
                    lv_typeArguments_2_0=ruleJvmArgumentTypeReference();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

			        if (current==null) {
			            current = createModelElementForParent(grammarAccess.getXFeatureCallRule());
			        }
					add(
						current,
						"typeArguments",
					lv_typeArguments_2_0,
					"JvmArgumentTypeReference");
			        afterParserOrEnumRuleCall();

                    }

                    }


                    }

                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3237:2: (otherlv_3= ',' ( (lv_typeArguments_4_0= ruleJvmArgumentTypeReference ) ) )*
                    loop57:
                    do {
                        int alt57=2;
                        int LA57_0 = input.LA(1);

                        if ( (LA57_0==57) ) {
                            alt57=1;
                        }


                        switch (alt57) {
			case 1 :
			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3237:4: otherlv_3= ',' ( (lv_typeArguments_4_0= ruleJvmArgumentTypeReference ) )
			    {
			    otherlv_3=(Token)match(input,57,FOLLOW_57_in_ruleXFeatureCall7889); if (state.failed) return current;
			    if ( state.backtracking==0 ) {

					newLeafNode(otherlv_3, grammarAccess.getXFeatureCallAccess().getCommaKeyword_1_2_0());

			    }
			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3241:1: ( (lv_typeArguments_4_0= ruleJvmArgumentTypeReference ) )
			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3242:1: (lv_typeArguments_4_0= ruleJvmArgumentTypeReference )
			    {
			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3242:1: (lv_typeArguments_4_0= ruleJvmArgumentTypeReference )
			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3243:3: lv_typeArguments_4_0= ruleJvmArgumentTypeReference
			    {
			    if ( state.backtracking==0 ) {

				        newCompositeNode(grammarAccess.getXFeatureCallAccess().getTypeArgumentsJvmArgumentTypeReferenceParserRuleCall_1_2_1_0());

			    }
			    pushFollow(FOLLOW_ruleJvmArgumentTypeReference_in_ruleXFeatureCall7910);
			    lv_typeArguments_4_0=ruleJvmArgumentTypeReference();

			    state._fsp--;
			    if (state.failed) return current;
			    if ( state.backtracking==0 ) {

				        if (current==null) {
				            current = createModelElementForParent(grammarAccess.getXFeatureCallRule());
				        }
						add(
							current,
							"typeArguments",
						lv_typeArguments_4_0,
						"JvmArgumentTypeReference");
				        afterParserOrEnumRuleCall();

			    }

			    }


			    }


			    }
			    break;

			default :
			    break loop57;
                        }
                    } while (true);

                    otherlv_5=(Token)match(input,22,FOLLOW_22_in_ruleXFeatureCall7924); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

				newLeafNode(otherlv_5, grammarAccess.getXFeatureCallAccess().getGreaterThanSignKeyword_1_3());

                    }

                    }
                    break;

            }

            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3263:3: ( ( ruleIdOrSuper ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3264:1: ( ruleIdOrSuper )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3264:1: ( ruleIdOrSuper )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3265:3: ruleIdOrSuper
            {
            if ( state.backtracking==0 ) {

				if (current==null) {
		            current = createModelElement(grammarAccess.getXFeatureCallRule());
		        }

            }
            if ( state.backtracking==0 ) {

		        newCompositeNode(grammarAccess.getXFeatureCallAccess().getFeatureJvmIdentifiableElementCrossReference_2_0());

            }
            pushFollow(FOLLOW_ruleIdOrSuper_in_ruleXFeatureCall7949);
            ruleIdOrSuper();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

		        afterParserOrEnumRuleCall();

            }

            }


            }

            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3278:2: ( ( ( ( '(' ) )=> (lv_explicitOperationCall_7_0= '(' ) ) ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_featureCallArguments_8_0= ruleXShortClosure ) ) | ( ( (lv_featureCallArguments_9_0= ruleXExpression ) ) (otherlv_10= ',' ( (lv_featureCallArguments_11_0= ruleXExpression ) ) )* ) )? otherlv_12= ')' )?
            int alt61=2;
            alt61 = dfa61.predict(input);
            switch (alt61) {
                case 1 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3278:3: ( ( ( '(' ) )=> (lv_explicitOperationCall_7_0= '(' ) ) ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_featureCallArguments_8_0= ruleXShortClosure ) ) | ( ( (lv_featureCallArguments_9_0= ruleXExpression ) ) (otherlv_10= ',' ( (lv_featureCallArguments_11_0= ruleXExpression ) ) )* ) )? otherlv_12= ')'
                    {
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3278:3: ( ( ( '(' ) )=> (lv_explicitOperationCall_7_0= '(' ) )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3278:4: ( ( '(' ) )=> (lv_explicitOperationCall_7_0= '(' )
                    {
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3285:1: (lv_explicitOperationCall_7_0= '(' )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3286:3: lv_explicitOperationCall_7_0= '('
                    {
                    lv_explicitOperationCall_7_0=(Token)match(input,58,FOLLOW_58_in_ruleXFeatureCall7983); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              newLeafNode(lv_explicitOperationCall_7_0, grammarAccess.getXFeatureCallAccess().getExplicitOperationCallLeftParenthesisKeyword_3_0_0());

                    }
                    if ( state.backtracking==0 ) {

			        if (current==null) {
			            current = createModelElement(grammarAccess.getXFeatureCallRule());
			        }
					setWithLastConsumed(current, "explicitOperationCall", true, "(");

                    }

                    }


                    }

                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3299:2: ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_featureCallArguments_8_0= ruleXShortClosure ) ) | ( ( (lv_featureCallArguments_9_0= ruleXExpression ) ) (otherlv_10= ',' ( (lv_featureCallArguments_11_0= ruleXExpression ) ) )* ) )?
                    int alt60=3;
                    alt60 = dfa60.predict(input);
                    switch (alt60) {
                        case 1 :
                            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3299:3: ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_featureCallArguments_8_0= ruleXShortClosure ) )
                            {
                            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3299:3: ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_featureCallArguments_8_0= ruleXShortClosure ) )
                            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3299:4: ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_featureCallArguments_8_0= ruleXShortClosure )
                            {
                            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3316:1: (lv_featureCallArguments_8_0= ruleXShortClosure )
                            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3317:3: lv_featureCallArguments_8_0= ruleXShortClosure
                            {
                            if ( state.backtracking==0 ) {

				        newCompositeNode(grammarAccess.getXFeatureCallAccess().getFeatureCallArgumentsXShortClosureParserRuleCall_3_1_0_0());

                            }
                            pushFollow(FOLLOW_ruleXShortClosure_in_ruleXFeatureCall8068);
                            lv_featureCallArguments_8_0=ruleXShortClosure();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

				        if (current==null) {
				            current = createModelElementForParent(grammarAccess.getXFeatureCallRule());
				        }
						add(
							current,
							"featureCallArguments",
						lv_featureCallArguments_8_0,
						"XShortClosure");
				        afterParserOrEnumRuleCall();

                            }

                            }


                            }


                            }
                            break;
                        case 2 :
                            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3334:6: ( ( (lv_featureCallArguments_9_0= ruleXExpression ) ) (otherlv_10= ',' ( (lv_featureCallArguments_11_0= ruleXExpression ) ) )* )
                            {
                            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3334:6: ( ( (lv_featureCallArguments_9_0= ruleXExpression ) ) (otherlv_10= ',' ( (lv_featureCallArguments_11_0= ruleXExpression ) ) )* )
                            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3334:7: ( (lv_featureCallArguments_9_0= ruleXExpression ) ) (otherlv_10= ',' ( (lv_featureCallArguments_11_0= ruleXExpression ) ) )*
                            {
                            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3334:7: ( (lv_featureCallArguments_9_0= ruleXExpression ) )
                            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3335:1: (lv_featureCallArguments_9_0= ruleXExpression )
                            {
                            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3335:1: (lv_featureCallArguments_9_0= ruleXExpression )
                            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3336:3: lv_featureCallArguments_9_0= ruleXExpression
                            {
                            if ( state.backtracking==0 ) {

				        newCompositeNode(grammarAccess.getXFeatureCallAccess().getFeatureCallArgumentsXExpressionParserRuleCall_3_1_1_0_0());

                            }
                            pushFollow(FOLLOW_ruleXExpression_in_ruleXFeatureCall8096);
                            lv_featureCallArguments_9_0=ruleXExpression();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

				        if (current==null) {
				            current = createModelElementForParent(grammarAccess.getXFeatureCallRule());
				        }
						add(
							current,
							"featureCallArguments",
						lv_featureCallArguments_9_0,
						"XExpression");
				        afterParserOrEnumRuleCall();

                            }

                            }


                            }

                            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3352:2: (otherlv_10= ',' ( (lv_featureCallArguments_11_0= ruleXExpression ) ) )*
                            loop59:
                            do {
                                int alt59=2;
                                int LA59_0 = input.LA(1);

                                if ( (LA59_0==57) ) {
                                    alt59=1;
                                }


                                switch (alt59) {
				case 1 :
				    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3352:4: otherlv_10= ',' ( (lv_featureCallArguments_11_0= ruleXExpression ) )
				    {
				    otherlv_10=(Token)match(input,57,FOLLOW_57_in_ruleXFeatureCall8109); if (state.failed) return current;
				    if ( state.backtracking==0 ) {

						newLeafNode(otherlv_10, grammarAccess.getXFeatureCallAccess().getCommaKeyword_3_1_1_1_0());

				    }
				    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3356:1: ( (lv_featureCallArguments_11_0= ruleXExpression ) )
				    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3357:1: (lv_featureCallArguments_11_0= ruleXExpression )
				    {
				    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3357:1: (lv_featureCallArguments_11_0= ruleXExpression )
				    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3358:3: lv_featureCallArguments_11_0= ruleXExpression
				    {
				    if ( state.backtracking==0 ) {

					        newCompositeNode(grammarAccess.getXFeatureCallAccess().getFeatureCallArgumentsXExpressionParserRuleCall_3_1_1_1_1_0());

				    }
				    pushFollow(FOLLOW_ruleXExpression_in_ruleXFeatureCall8130);
				    lv_featureCallArguments_11_0=ruleXExpression();

				    state._fsp--;
				    if (state.failed) return current;
				    if ( state.backtracking==0 ) {

					        if (current==null) {
					            current = createModelElementForParent(grammarAccess.getXFeatureCallRule());
					        }
							add(
								current,
								"featureCallArguments",
							lv_featureCallArguments_11_0,
							"XExpression");
					        afterParserOrEnumRuleCall();

				    }

				    }


				    }


				    }
				    break;

				default :
				    break loop59;
                                }
                            } while (true);


                            }


                            }
                            break;

                    }

                    otherlv_12=(Token)match(input,59,FOLLOW_59_in_ruleXFeatureCall8147); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

				newLeafNode(otherlv_12, grammarAccess.getXFeatureCallAccess().getRightParenthesisKeyword_3_2());

                    }

                    }
                    break;

            }

            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3378:3: ( ( ( () '[' ) )=> (lv_featureCallArguments_13_0= ruleXClosure ) )?
            int alt62=2;
            int LA62_0 = input.LA(1);

            if ( (LA62_0==63) && (synpred23_InternalDSEL())) {
                alt62=1;
            }
            switch (alt62) {
                case 1 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3378:4: ( ( () '[' ) )=> (lv_featureCallArguments_13_0= ruleXClosure )
                    {
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3381:1: (lv_featureCallArguments_13_0= ruleXClosure )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3382:3: lv_featureCallArguments_13_0= ruleXClosure
                    {
                    if ( state.backtracking==0 ) {

			        newCompositeNode(grammarAccess.getXFeatureCallAccess().getFeatureCallArgumentsXClosureParserRuleCall_4_0());

                    }
                    pushFollow(FOLLOW_ruleXClosure_in_ruleXFeatureCall8182);
                    lv_featureCallArguments_13_0=ruleXClosure();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

			        if (current==null) {
			            current = createModelElementForParent(grammarAccess.getXFeatureCallRule());
			        }
					add(
						current,
						"featureCallArguments",
					lv_featureCallArguments_13_0,
					"XClosure");
			        afterParserOrEnumRuleCall();

                    }

                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleXFeatureCall"


    // $ANTLR start "entryRuleFeatureCallID"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3406:1: entryRuleFeatureCallID returns [String current=null] : iv_ruleFeatureCallID= ruleFeatureCallID EOF ;
    public final String entryRuleFeatureCallID() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleFeatureCallID = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3407:2: (iv_ruleFeatureCallID= ruleFeatureCallID EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3408:2: iv_ruleFeatureCallID= ruleFeatureCallID EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getFeatureCallIDRule());
            }
            pushFollow(FOLLOW_ruleFeatureCallID_in_entryRuleFeatureCallID8220);
            iv_ruleFeatureCallID=ruleFeatureCallID();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleFeatureCallID.getText();
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleFeatureCallID8231); if (state.failed) return current;

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
    // $ANTLR end "entryRuleFeatureCallID"


    // $ANTLR start "ruleFeatureCallID"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3415:1: ruleFeatureCallID returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ValidID_0= ruleValidID | kw= 'extends' | kw= 'static' | kw= 'import' | kw= 'extension' ) ;
    public final AntlrDatatypeRuleToken ruleFeatureCallID() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_ValidID_0 = null;


         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3418:28: ( (this_ValidID_0= ruleValidID | kw= 'extends' | kw= 'static' | kw= 'import' | kw= 'extension' ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3419:1: (this_ValidID_0= ruleValidID | kw= 'extends' | kw= 'static' | kw= 'import' | kw= 'extension' )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3419:1: (this_ValidID_0= ruleValidID | kw= 'extends' | kw= 'static' | kw= 'import' | kw= 'extension' )
            int alt63=5;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                alt63=1;
                }
                break;
            case 71:
                {
                alt63=2;
                }
                break;
            case 72:
                {
                alt63=3;
                }
                break;
            case 73:
                {
                alt63=4;
                }
                break;
            case 74:
                {
                alt63=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 63, 0, input);

                throw nvae;
            }

            switch (alt63) {
                case 1 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3420:5: this_ValidID_0= ruleValidID
                    {
                    if ( state.backtracking==0 ) {

                              newCompositeNode(grammarAccess.getFeatureCallIDAccess().getValidIDParserRuleCall_0());

                    }
                    pushFollow(FOLLOW_ruleValidID_in_ruleFeatureCallID8278);
                    this_ValidID_0=ruleValidID();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

				current.merge(this_ValidID_0);

                    }
                    if ( state.backtracking==0 ) {

                              afterParserOrEnumRuleCall();

                    }

                    }
                    break;
                case 2 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3432:2: kw= 'extends'
                    {
                    kw=(Token)match(input,71,FOLLOW_71_in_ruleFeatureCallID8302); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current.merge(kw);
                              newLeafNode(kw, grammarAccess.getFeatureCallIDAccess().getExtendsKeyword_1());

                    }

                    }
                    break;
                case 3 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3439:2: kw= 'static'
                    {
                    kw=(Token)match(input,72,FOLLOW_72_in_ruleFeatureCallID8321); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current.merge(kw);
                              newLeafNode(kw, grammarAccess.getFeatureCallIDAccess().getStaticKeyword_2());

                    }

                    }
                    break;
                case 4 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3446:2: kw= 'import'
                    {
                    kw=(Token)match(input,73,FOLLOW_73_in_ruleFeatureCallID8340); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current.merge(kw);
                              newLeafNode(kw, grammarAccess.getFeatureCallIDAccess().getImportKeyword_3());

                    }

                    }
                    break;
                case 5 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3453:2: kw= 'extension'
                    {
                    kw=(Token)match(input,74,FOLLOW_74_in_ruleFeatureCallID8359); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current.merge(kw);
                              newLeafNode(kw, grammarAccess.getFeatureCallIDAccess().getExtensionKeyword_4());

                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleFeatureCallID"


    // $ANTLR start "entryRuleIdOrSuper"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3466:1: entryRuleIdOrSuper returns [String current=null] : iv_ruleIdOrSuper= ruleIdOrSuper EOF ;
    public final String entryRuleIdOrSuper() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleIdOrSuper = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3467:2: (iv_ruleIdOrSuper= ruleIdOrSuper EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3468:2: iv_ruleIdOrSuper= ruleIdOrSuper EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getIdOrSuperRule());
            }
            pushFollow(FOLLOW_ruleIdOrSuper_in_entryRuleIdOrSuper8400);
            iv_ruleIdOrSuper=ruleIdOrSuper();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleIdOrSuper.getText();
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleIdOrSuper8411); if (state.failed) return current;

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
    // $ANTLR end "entryRuleIdOrSuper"


    // $ANTLR start "ruleIdOrSuper"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3475:1: ruleIdOrSuper returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_FeatureCallID_0= ruleFeatureCallID | kw= 'super' ) ;
    public final AntlrDatatypeRuleToken ruleIdOrSuper() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_FeatureCallID_0 = null;


         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3478:28: ( (this_FeatureCallID_0= ruleFeatureCallID | kw= 'super' ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3479:1: (this_FeatureCallID_0= ruleFeatureCallID | kw= 'super' )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3479:1: (this_FeatureCallID_0= ruleFeatureCallID | kw= 'super' )
            int alt64=2;
            int LA64_0 = input.LA(1);

            if ( (LA64_0==RULE_ID||(LA64_0>=71 && LA64_0<=74)) ) {
                alt64=1;
            }
            else if ( (LA64_0==75) ) {
                alt64=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 64, 0, input);

                throw nvae;
            }
            switch (alt64) {
                case 1 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3480:5: this_FeatureCallID_0= ruleFeatureCallID
                    {
                    if ( state.backtracking==0 ) {

                              newCompositeNode(grammarAccess.getIdOrSuperAccess().getFeatureCallIDParserRuleCall_0());

                    }
                    pushFollow(FOLLOW_ruleFeatureCallID_in_ruleIdOrSuper8458);
                    this_FeatureCallID_0=ruleFeatureCallID();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

				current.merge(this_FeatureCallID_0);

                    }
                    if ( state.backtracking==0 ) {

                              afterParserOrEnumRuleCall();

                    }

                    }
                    break;
                case 2 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3492:2: kw= 'super'
                    {
                    kw=(Token)match(input,75,FOLLOW_75_in_ruleIdOrSuper8482); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current.merge(kw);
                              newLeafNode(kw, grammarAccess.getIdOrSuperAccess().getSuperKeyword_1());

                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleIdOrSuper"


    // $ANTLR start "entryRuleXStringLiteral"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3509:1: entryRuleXStringLiteral returns [EObject current=null] : iv_ruleXStringLiteral= ruleXStringLiteral EOF ;
    public final EObject entryRuleXStringLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXStringLiteral = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3510:2: (iv_ruleXStringLiteral= ruleXStringLiteral EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3511:2: iv_ruleXStringLiteral= ruleXStringLiteral EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXStringLiteralRule());
            }
            pushFollow(FOLLOW_ruleXStringLiteral_in_entryRuleXStringLiteral8526);
            iv_ruleXStringLiteral=ruleXStringLiteral();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXStringLiteral;
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleXStringLiteral8536); if (state.failed) return current;

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
    // $ANTLR end "entryRuleXStringLiteral"


    // $ANTLR start "ruleXStringLiteral"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3518:1: ruleXStringLiteral returns [EObject current=null] : ( () ( (lv_value_1_0= RULE_STRING ) ) ) ;
    public final EObject ruleXStringLiteral() throws RecognitionException {
        EObject current = null;

        Token lv_value_1_0=null;

         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3521:28: ( ( () ( (lv_value_1_0= RULE_STRING ) ) ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3522:1: ( () ( (lv_value_1_0= RULE_STRING ) ) )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3522:1: ( () ( (lv_value_1_0= RULE_STRING ) ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3522:2: () ( (lv_value_1_0= RULE_STRING ) )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3522:2: ()
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3523:5:
            {
            if ( state.backtracking==0 ) {

                      current = forceCreateModelElement(
                          grammarAccess.getXStringLiteralAccess().getXStringLiteralAction_0(),
                          current);

            }

            }

            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3528:2: ( (lv_value_1_0= RULE_STRING ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3529:1: (lv_value_1_0= RULE_STRING )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3529:1: (lv_value_1_0= RULE_STRING )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3530:3: lv_value_1_0= RULE_STRING
            {
            lv_value_1_0=(Token)match(input,RULE_STRING,FOLLOW_RULE_STRING_in_ruleXStringLiteral8587); if (state.failed) return current;
            if ( state.backtracking==0 ) {

				newLeafNode(lv_value_1_0, grammarAccess.getXStringLiteralAccess().getValueSTRINGTerminalRuleCall_1_0());

            }
            if ( state.backtracking==0 ) {

		        if (current==null) {
		            current = createModelElement(grammarAccess.getXStringLiteralRule());
		        }
				setWithLastConsumed(
					current,
					"value",
				lv_value_1_0,
				"STRING");

            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleXStringLiteral"


    // $ANTLR start "entryRuleXCatchClause"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3564:1: entryRuleXCatchClause returns [EObject current=null] : iv_ruleXCatchClause= ruleXCatchClause EOF ;
    public final EObject entryRuleXCatchClause() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXCatchClause = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3565:2: (iv_ruleXCatchClause= ruleXCatchClause EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3566:2: iv_ruleXCatchClause= ruleXCatchClause EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXCatchClauseRule());
            }
            pushFollow(FOLLOW_ruleXCatchClause_in_entryRuleXCatchClause8638);
            iv_ruleXCatchClause=ruleXCatchClause();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXCatchClause;
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleXCatchClause8648); if (state.failed) return current;

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
    // $ANTLR end "entryRuleXCatchClause"


    // $ANTLR start "ruleXCatchClause"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3573:1: ruleXCatchClause returns [EObject current=null] : ( ( ( 'catch' )=>otherlv_0= 'catch' ) otherlv_1= '(' ( (lv_declaredParam_2_0= ruleFullJvmFormalParameter ) ) otherlv_3= ')' ( (lv_expression_4_0= ruleXExpression ) ) ) ;
    public final EObject ruleXCatchClause() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_declaredParam_2_0 = null;

        EObject lv_expression_4_0 = null;


         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3576:28: ( ( ( ( 'catch' )=>otherlv_0= 'catch' ) otherlv_1= '(' ( (lv_declaredParam_2_0= ruleFullJvmFormalParameter ) ) otherlv_3= ')' ( (lv_expression_4_0= ruleXExpression ) ) ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3577:1: ( ( ( 'catch' )=>otherlv_0= 'catch' ) otherlv_1= '(' ( (lv_declaredParam_2_0= ruleFullJvmFormalParameter ) ) otherlv_3= ')' ( (lv_expression_4_0= ruleXExpression ) ) )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3577:1: ( ( ( 'catch' )=>otherlv_0= 'catch' ) otherlv_1= '(' ( (lv_declaredParam_2_0= ruleFullJvmFormalParameter ) ) otherlv_3= ')' ( (lv_expression_4_0= ruleXExpression ) ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3577:2: ( ( 'catch' )=>otherlv_0= 'catch' ) otherlv_1= '(' ( (lv_declaredParam_2_0= ruleFullJvmFormalParameter ) ) otherlv_3= ')' ( (lv_expression_4_0= ruleXExpression ) )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3577:2: ( ( 'catch' )=>otherlv_0= 'catch' )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3577:3: ( 'catch' )=>otherlv_0= 'catch'
            {
            otherlv_0=(Token)match(input,76,FOLLOW_76_in_ruleXCatchClause8693); if (state.failed) return current;
            if ( state.backtracking==0 ) {

			newLeafNode(otherlv_0, grammarAccess.getXCatchClauseAccess().getCatchKeyword_0());

            }

            }

            otherlv_1=(Token)match(input,58,FOLLOW_58_in_ruleXCatchClause8706); if (state.failed) return current;
            if ( state.backtracking==0 ) {

			newLeafNode(otherlv_1, grammarAccess.getXCatchClauseAccess().getLeftParenthesisKeyword_1());

            }
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3586:1: ( (lv_declaredParam_2_0= ruleFullJvmFormalParameter ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3587:1: (lv_declaredParam_2_0= ruleFullJvmFormalParameter )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3587:1: (lv_declaredParam_2_0= ruleFullJvmFormalParameter )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3588:3: lv_declaredParam_2_0= ruleFullJvmFormalParameter
            {
            if ( state.backtracking==0 ) {

		        newCompositeNode(grammarAccess.getXCatchClauseAccess().getDeclaredParamFullJvmFormalParameterParserRuleCall_2_0());

            }
            pushFollow(FOLLOW_ruleFullJvmFormalParameter_in_ruleXCatchClause8727);
            lv_declaredParam_2_0=ruleFullJvmFormalParameter();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

		        if (current==null) {
		            current = createModelElementForParent(grammarAccess.getXCatchClauseRule());
		        }
				set(
					current,
					"declaredParam",
				lv_declaredParam_2_0,
				"FullJvmFormalParameter");
		        afterParserOrEnumRuleCall();

            }

            }


            }

            otherlv_3=(Token)match(input,59,FOLLOW_59_in_ruleXCatchClause8739); if (state.failed) return current;
            if ( state.backtracking==0 ) {

			newLeafNode(otherlv_3, grammarAccess.getXCatchClauseAccess().getRightParenthesisKeyword_3());

            }
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3608:1: ( (lv_expression_4_0= ruleXExpression ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3609:1: (lv_expression_4_0= ruleXExpression )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3609:1: (lv_expression_4_0= ruleXExpression )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3610:3: lv_expression_4_0= ruleXExpression
            {
            if ( state.backtracking==0 ) {

		        newCompositeNode(grammarAccess.getXCatchClauseAccess().getExpressionXExpressionParserRuleCall_4_0());

            }
            pushFollow(FOLLOW_ruleXExpression_in_ruleXCatchClause8760);
            lv_expression_4_0=ruleXExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

		        if (current==null) {
		            current = createModelElementForParent(grammarAccess.getXCatchClauseRule());
		        }
				set(
					current,
					"expression",
				lv_expression_4_0,
				"XExpression");
		        afterParserOrEnumRuleCall();

            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleXCatchClause"


    // $ANTLR start "entryRuleQualifiedName"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3634:1: entryRuleQualifiedName returns [String current=null] : iv_ruleQualifiedName= ruleQualifiedName EOF ;
    public final String entryRuleQualifiedName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleQualifiedName = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3635:2: (iv_ruleQualifiedName= ruleQualifiedName EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3636:2: iv_ruleQualifiedName= ruleQualifiedName EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getQualifiedNameRule());
            }
            pushFollow(FOLLOW_ruleQualifiedName_in_entryRuleQualifiedName8797);
            iv_ruleQualifiedName=ruleQualifiedName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleQualifiedName.getText();
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleQualifiedName8808); if (state.failed) return current;

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
    // $ANTLR end "entryRuleQualifiedName"


    // $ANTLR start "ruleQualifiedName"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3643:1: ruleQualifiedName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ValidID_0= ruleValidID ( ( ( '.' )=>kw= '.' ) this_ValidID_2= ruleValidID )* ) ;
    public final AntlrDatatypeRuleToken ruleQualifiedName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_ValidID_0 = null;

        AntlrDatatypeRuleToken this_ValidID_2 = null;


         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3646:28: ( (this_ValidID_0= ruleValidID ( ( ( '.' )=>kw= '.' ) this_ValidID_2= ruleValidID )* ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3647:1: (this_ValidID_0= ruleValidID ( ( ( '.' )=>kw= '.' ) this_ValidID_2= ruleValidID )* )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3647:1: (this_ValidID_0= ruleValidID ( ( ( '.' )=>kw= '.' ) this_ValidID_2= ruleValidID )* )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3648:5: this_ValidID_0= ruleValidID ( ( ( '.' )=>kw= '.' ) this_ValidID_2= ruleValidID )*
            {
            if ( state.backtracking==0 ) {

                      newCompositeNode(grammarAccess.getQualifiedNameAccess().getValidIDParserRuleCall_0());

            }
            pushFollow(FOLLOW_ruleValidID_in_ruleQualifiedName8855);
            this_ValidID_0=ruleValidID();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

			current.merge(this_ValidID_0);

            }
            if ( state.backtracking==0 ) {

                      afterParserOrEnumRuleCall();

            }
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3658:1: ( ( ( '.' )=>kw= '.' ) this_ValidID_2= ruleValidID )*
            loop65:
            do {
                int alt65=2;
                int LA65_0 = input.LA(1);

                if ( (LA65_0==41) ) {
                    int LA65_2 = input.LA(2);

                    if ( (LA65_2==RULE_ID) ) {
                        int LA65_3 = input.LA(3);

                        if ( (synpred25_InternalDSEL()) ) {
                            alt65=1;
                        }


                    }


                }


                switch (alt65) {
		case 1 :
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3658:2: ( ( '.' )=>kw= '.' ) this_ValidID_2= ruleValidID
		    {
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3658:2: ( ( '.' )=>kw= '.' )
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3658:3: ( '.' )=>kw= '.'
		    {
		    kw=(Token)match(input,41,FOLLOW_41_in_ruleQualifiedName8883); if (state.failed) return current;
		    if ( state.backtracking==0 ) {

		              current.merge(kw);
		              newLeafNode(kw, grammarAccess.getQualifiedNameAccess().getFullStopKeyword_1_0());

		    }

		    }

		    if ( state.backtracking==0 ) {

		              newCompositeNode(grammarAccess.getQualifiedNameAccess().getValidIDParserRuleCall_1_1());

		    }
		    pushFollow(FOLLOW_ruleValidID_in_ruleQualifiedName8906);
		    this_ValidID_2=ruleValidID();

		    state._fsp--;
		    if (state.failed) return current;
		    if ( state.backtracking==0 ) {

				current.merge(this_ValidID_2);

		    }
		    if ( state.backtracking==0 ) {

		              afterParserOrEnumRuleCall();

		    }

		    }
		    break;

		default :
		    break loop65;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleQualifiedName"


    // $ANTLR start "entryRuleJvmTypeReference"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3687:1: entryRuleJvmTypeReference returns [EObject current=null] : iv_ruleJvmTypeReference= ruleJvmTypeReference EOF ;
    public final EObject entryRuleJvmTypeReference() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleJvmTypeReference = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3688:2: (iv_ruleJvmTypeReference= ruleJvmTypeReference EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3689:2: iv_ruleJvmTypeReference= ruleJvmTypeReference EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getJvmTypeReferenceRule());
            }
            pushFollow(FOLLOW_ruleJvmTypeReference_in_entryRuleJvmTypeReference8955);
            iv_ruleJvmTypeReference=ruleJvmTypeReference();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleJvmTypeReference;
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleJvmTypeReference8965); if (state.failed) return current;

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
    // $ANTLR end "entryRuleJvmTypeReference"


    // $ANTLR start "ruleJvmTypeReference"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3696:1: ruleJvmTypeReference returns [EObject current=null] : ( (this_JvmParameterizedTypeReference_0= ruleJvmParameterizedTypeReference ( ( ( () ruleArrayBrackets ) )=> ( () ruleArrayBrackets ) )* ) | this_XFunctionTypeRef_3= ruleXFunctionTypeRef ) ;
    public final EObject ruleJvmTypeReference() throws RecognitionException {
        EObject current = null;

        EObject this_JvmParameterizedTypeReference_0 = null;

        EObject this_XFunctionTypeRef_3 = null;


         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3699:28: ( ( (this_JvmParameterizedTypeReference_0= ruleJvmParameterizedTypeReference ( ( ( () ruleArrayBrackets ) )=> ( () ruleArrayBrackets ) )* ) | this_XFunctionTypeRef_3= ruleXFunctionTypeRef ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3700:1: ( (this_JvmParameterizedTypeReference_0= ruleJvmParameterizedTypeReference ( ( ( () ruleArrayBrackets ) )=> ( () ruleArrayBrackets ) )* ) | this_XFunctionTypeRef_3= ruleXFunctionTypeRef )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3700:1: ( (this_JvmParameterizedTypeReference_0= ruleJvmParameterizedTypeReference ( ( ( () ruleArrayBrackets ) )=> ( () ruleArrayBrackets ) )* ) | this_XFunctionTypeRef_3= ruleXFunctionTypeRef )
            int alt67=2;
            int LA67_0 = input.LA(1);

            if ( (LA67_0==RULE_ID) ) {
                alt67=1;
            }
            else if ( (LA67_0==58||LA67_0==77) ) {
                alt67=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 67, 0, input);

                throw nvae;
            }
            switch (alt67) {
                case 1 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3700:2: (this_JvmParameterizedTypeReference_0= ruleJvmParameterizedTypeReference ( ( ( () ruleArrayBrackets ) )=> ( () ruleArrayBrackets ) )* )
                    {
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3700:2: (this_JvmParameterizedTypeReference_0= ruleJvmParameterizedTypeReference ( ( ( () ruleArrayBrackets ) )=> ( () ruleArrayBrackets ) )* )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3701:5: this_JvmParameterizedTypeReference_0= ruleJvmParameterizedTypeReference ( ( ( () ruleArrayBrackets ) )=> ( () ruleArrayBrackets ) )*
                    {
                    if ( state.backtracking==0 ) {

                              newCompositeNode(grammarAccess.getJvmTypeReferenceAccess().getJvmParameterizedTypeReferenceParserRuleCall_0_0());

                    }
                    pushFollow(FOLLOW_ruleJvmParameterizedTypeReference_in_ruleJvmTypeReference9013);
                    this_JvmParameterizedTypeReference_0=ruleJvmParameterizedTypeReference();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = this_JvmParameterizedTypeReference_0;
                              afterParserOrEnumRuleCall();

                    }
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3709:1: ( ( ( () ruleArrayBrackets ) )=> ( () ruleArrayBrackets ) )*
                    loop66:
                    do {
                        int alt66=2;
                        int LA66_0 = input.LA(1);

                        if ( (LA66_0==63) && (synpred26_InternalDSEL())) {
                            alt66=1;
                        }


                        switch (alt66) {
			case 1 :
			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3709:2: ( ( () ruleArrayBrackets ) )=> ( () ruleArrayBrackets )
			    {
			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3710:24: ( () ruleArrayBrackets )
			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3710:25: () ruleArrayBrackets
			    {
			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3710:25: ()
			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3711:5:
			    {
			    if ( state.backtracking==0 ) {

			              current = forceCreateModelElementAndSet(
			                  grammarAccess.getJvmTypeReferenceAccess().getJvmGenericArrayTypeReferenceComponentTypeAction_0_1_0_0(),
			                  current);

			    }

			    }

			    if ( state.backtracking==0 ) {

			              newCompositeNode(grammarAccess.getJvmTypeReferenceAccess().getArrayBracketsParserRuleCall_0_1_0_1());

			    }
			    pushFollow(FOLLOW_ruleArrayBrackets_in_ruleJvmTypeReference9049);
			    ruleArrayBrackets();

			    state._fsp--;
			    if (state.failed) return current;
			    if ( state.backtracking==0 ) {

			              afterParserOrEnumRuleCall();

			    }

			    }


			    }
			    break;

			default :
			    break loop66;
                        }
                    } while (true);


                    }


                    }
                    break;
                case 2 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3726:5: this_XFunctionTypeRef_3= ruleXFunctionTypeRef
                    {
                    if ( state.backtracking==0 ) {

                              newCompositeNode(grammarAccess.getJvmTypeReferenceAccess().getXFunctionTypeRefParserRuleCall_1());

                    }
                    pushFollow(FOLLOW_ruleXFunctionTypeRef_in_ruleJvmTypeReference9080);
                    this_XFunctionTypeRef_3=ruleXFunctionTypeRef();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = this_XFunctionTypeRef_3;
                              afterParserOrEnumRuleCall();

                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleJvmTypeReference"


    // $ANTLR start "entryRuleArrayBrackets"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3742:1: entryRuleArrayBrackets returns [String current=null] : iv_ruleArrayBrackets= ruleArrayBrackets EOF ;
    public final String entryRuleArrayBrackets() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleArrayBrackets = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3743:2: (iv_ruleArrayBrackets= ruleArrayBrackets EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3744:2: iv_ruleArrayBrackets= ruleArrayBrackets EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getArrayBracketsRule());
            }
            pushFollow(FOLLOW_ruleArrayBrackets_in_entryRuleArrayBrackets9116);
            iv_ruleArrayBrackets=ruleArrayBrackets();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleArrayBrackets.getText();
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleArrayBrackets9127); if (state.failed) return current;

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
    // $ANTLR end "entryRuleArrayBrackets"


    // $ANTLR start "ruleArrayBrackets"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3751:1: ruleArrayBrackets returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '[' kw= ']' ) ;
    public final AntlrDatatypeRuleToken ruleArrayBrackets() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;

         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3754:28: ( (kw= '[' kw= ']' ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3755:1: (kw= '[' kw= ']' )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3755:1: (kw= '[' kw= ']' )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3756:2: kw= '[' kw= ']'
            {
            kw=(Token)match(input,63,FOLLOW_63_in_ruleArrayBrackets9165); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current.merge(kw);
                      newLeafNode(kw, grammarAccess.getArrayBracketsAccess().getLeftSquareBracketKeyword_0());

            }
            kw=(Token)match(input,64,FOLLOW_64_in_ruleArrayBrackets9178); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current.merge(kw);
                      newLeafNode(kw, grammarAccess.getArrayBracketsAccess().getRightSquareBracketKeyword_1());

            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleArrayBrackets"


    // $ANTLR start "entryRuleXFunctionTypeRef"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3775:1: entryRuleXFunctionTypeRef returns [EObject current=null] : iv_ruleXFunctionTypeRef= ruleXFunctionTypeRef EOF ;
    public final EObject entryRuleXFunctionTypeRef() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXFunctionTypeRef = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3776:2: (iv_ruleXFunctionTypeRef= ruleXFunctionTypeRef EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3777:2: iv_ruleXFunctionTypeRef= ruleXFunctionTypeRef EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXFunctionTypeRefRule());
            }
            pushFollow(FOLLOW_ruleXFunctionTypeRef_in_entryRuleXFunctionTypeRef9218);
            iv_ruleXFunctionTypeRef=ruleXFunctionTypeRef();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXFunctionTypeRef;
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleXFunctionTypeRef9228); if (state.failed) return current;

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
    // $ANTLR end "entryRuleXFunctionTypeRef"


    // $ANTLR start "ruleXFunctionTypeRef"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3784:1: ruleXFunctionTypeRef returns [EObject current=null] : ( (otherlv_0= '(' ( ( (lv_paramTypes_1_0= ruleJvmTypeReference ) ) (otherlv_2= ',' ( (lv_paramTypes_3_0= ruleJvmTypeReference ) ) )* )? otherlv_4= ')' )? otherlv_5= '=>' ( (lv_returnType_6_0= ruleJvmTypeReference ) ) ) ;
    public final EObject ruleXFunctionTypeRef() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        EObject lv_paramTypes_1_0 = null;

        EObject lv_paramTypes_3_0 = null;

        EObject lv_returnType_6_0 = null;


         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3787:28: ( ( (otherlv_0= '(' ( ( (lv_paramTypes_1_0= ruleJvmTypeReference ) ) (otherlv_2= ',' ( (lv_paramTypes_3_0= ruleJvmTypeReference ) ) )* )? otherlv_4= ')' )? otherlv_5= '=>' ( (lv_returnType_6_0= ruleJvmTypeReference ) ) ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3788:1: ( (otherlv_0= '(' ( ( (lv_paramTypes_1_0= ruleJvmTypeReference ) ) (otherlv_2= ',' ( (lv_paramTypes_3_0= ruleJvmTypeReference ) ) )* )? otherlv_4= ')' )? otherlv_5= '=>' ( (lv_returnType_6_0= ruleJvmTypeReference ) ) )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3788:1: ( (otherlv_0= '(' ( ( (lv_paramTypes_1_0= ruleJvmTypeReference ) ) (otherlv_2= ',' ( (lv_paramTypes_3_0= ruleJvmTypeReference ) ) )* )? otherlv_4= ')' )? otherlv_5= '=>' ( (lv_returnType_6_0= ruleJvmTypeReference ) ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3788:2: (otherlv_0= '(' ( ( (lv_paramTypes_1_0= ruleJvmTypeReference ) ) (otherlv_2= ',' ( (lv_paramTypes_3_0= ruleJvmTypeReference ) ) )* )? otherlv_4= ')' )? otherlv_5= '=>' ( (lv_returnType_6_0= ruleJvmTypeReference ) )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3788:2: (otherlv_0= '(' ( ( (lv_paramTypes_1_0= ruleJvmTypeReference ) ) (otherlv_2= ',' ( (lv_paramTypes_3_0= ruleJvmTypeReference ) ) )* )? otherlv_4= ')' )?
            int alt70=2;
            int LA70_0 = input.LA(1);

            if ( (LA70_0==58) ) {
                alt70=1;
            }
            switch (alt70) {
                case 1 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3788:4: otherlv_0= '(' ( ( (lv_paramTypes_1_0= ruleJvmTypeReference ) ) (otherlv_2= ',' ( (lv_paramTypes_3_0= ruleJvmTypeReference ) ) )* )? otherlv_4= ')'
                    {
                    otherlv_0=(Token)match(input,58,FOLLOW_58_in_ruleXFunctionTypeRef9266); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

				newLeafNode(otherlv_0, grammarAccess.getXFunctionTypeRefAccess().getLeftParenthesisKeyword_0_0());

                    }
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3792:1: ( ( (lv_paramTypes_1_0= ruleJvmTypeReference ) ) (otherlv_2= ',' ( (lv_paramTypes_3_0= ruleJvmTypeReference ) ) )* )?
                    int alt69=2;
                    int LA69_0 = input.LA(1);

                    if ( (LA69_0==RULE_ID||LA69_0==58||LA69_0==77) ) {
                        alt69=1;
                    }
                    switch (alt69) {
                        case 1 :
                            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3792:2: ( (lv_paramTypes_1_0= ruleJvmTypeReference ) ) (otherlv_2= ',' ( (lv_paramTypes_3_0= ruleJvmTypeReference ) ) )*
                            {
                            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3792:2: ( (lv_paramTypes_1_0= ruleJvmTypeReference ) )
                            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3793:1: (lv_paramTypes_1_0= ruleJvmTypeReference )
                            {
                            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3793:1: (lv_paramTypes_1_0= ruleJvmTypeReference )
                            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3794:3: lv_paramTypes_1_0= ruleJvmTypeReference
                            {
                            if ( state.backtracking==0 ) {

				        newCompositeNode(grammarAccess.getXFunctionTypeRefAccess().getParamTypesJvmTypeReferenceParserRuleCall_0_1_0_0());

                            }
                            pushFollow(FOLLOW_ruleJvmTypeReference_in_ruleXFunctionTypeRef9288);
                            lv_paramTypes_1_0=ruleJvmTypeReference();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

				        if (current==null) {
				            current = createModelElementForParent(grammarAccess.getXFunctionTypeRefRule());
				        }
						add(
							current,
							"paramTypes",
						lv_paramTypes_1_0,
						"JvmTypeReference");
				        afterParserOrEnumRuleCall();

                            }

                            }


                            }

                            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3810:2: (otherlv_2= ',' ( (lv_paramTypes_3_0= ruleJvmTypeReference ) ) )*
                            loop68:
                            do {
                                int alt68=2;
                                int LA68_0 = input.LA(1);

                                if ( (LA68_0==57) ) {
                                    alt68=1;
                                }


                                switch (alt68) {
				case 1 :
				    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3810:4: otherlv_2= ',' ( (lv_paramTypes_3_0= ruleJvmTypeReference ) )
				    {
				    otherlv_2=(Token)match(input,57,FOLLOW_57_in_ruleXFunctionTypeRef9301); if (state.failed) return current;
				    if ( state.backtracking==0 ) {

						newLeafNode(otherlv_2, grammarAccess.getXFunctionTypeRefAccess().getCommaKeyword_0_1_1_0());

				    }
				    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3814:1: ( (lv_paramTypes_3_0= ruleJvmTypeReference ) )
				    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3815:1: (lv_paramTypes_3_0= ruleJvmTypeReference )
				    {
				    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3815:1: (lv_paramTypes_3_0= ruleJvmTypeReference )
				    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3816:3: lv_paramTypes_3_0= ruleJvmTypeReference
				    {
				    if ( state.backtracking==0 ) {

					        newCompositeNode(grammarAccess.getXFunctionTypeRefAccess().getParamTypesJvmTypeReferenceParserRuleCall_0_1_1_1_0());

				    }
				    pushFollow(FOLLOW_ruleJvmTypeReference_in_ruleXFunctionTypeRef9322);
				    lv_paramTypes_3_0=ruleJvmTypeReference();

				    state._fsp--;
				    if (state.failed) return current;
				    if ( state.backtracking==0 ) {

					        if (current==null) {
					            current = createModelElementForParent(grammarAccess.getXFunctionTypeRefRule());
					        }
							add(
								current,
								"paramTypes",
							lv_paramTypes_3_0,
							"JvmTypeReference");
					        afterParserOrEnumRuleCall();

				    }

				    }


				    }


				    }
				    break;

				default :
				    break loop68;
                                }
                            } while (true);


                            }
                            break;

                    }

                    otherlv_4=(Token)match(input,59,FOLLOW_59_in_ruleXFunctionTypeRef9338); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

				newLeafNode(otherlv_4, grammarAccess.getXFunctionTypeRefAccess().getRightParenthesisKeyword_0_2());

                    }

                    }
                    break;

            }

            otherlv_5=(Token)match(input,77,FOLLOW_77_in_ruleXFunctionTypeRef9352); if (state.failed) return current;
            if ( state.backtracking==0 ) {

			newLeafNode(otherlv_5, grammarAccess.getXFunctionTypeRefAccess().getEqualsSignGreaterThanSignKeyword_1());

            }
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3840:1: ( (lv_returnType_6_0= ruleJvmTypeReference ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3841:1: (lv_returnType_6_0= ruleJvmTypeReference )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3841:1: (lv_returnType_6_0= ruleJvmTypeReference )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3842:3: lv_returnType_6_0= ruleJvmTypeReference
            {
            if ( state.backtracking==0 ) {

		        newCompositeNode(grammarAccess.getXFunctionTypeRefAccess().getReturnTypeJvmTypeReferenceParserRuleCall_2_0());

            }
            pushFollow(FOLLOW_ruleJvmTypeReference_in_ruleXFunctionTypeRef9373);
            lv_returnType_6_0=ruleJvmTypeReference();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

		        if (current==null) {
		            current = createModelElementForParent(grammarAccess.getXFunctionTypeRefRule());
		        }
				set(
					current,
					"returnType",
				lv_returnType_6_0,
				"JvmTypeReference");
		        afterParserOrEnumRuleCall();

            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleXFunctionTypeRef"


    // $ANTLR start "entryRuleJvmParameterizedTypeReference"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3866:1: entryRuleJvmParameterizedTypeReference returns [EObject current=null] : iv_ruleJvmParameterizedTypeReference= ruleJvmParameterizedTypeReference EOF ;
    public final EObject entryRuleJvmParameterizedTypeReference() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleJvmParameterizedTypeReference = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3867:2: (iv_ruleJvmParameterizedTypeReference= ruleJvmParameterizedTypeReference EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3868:2: iv_ruleJvmParameterizedTypeReference= ruleJvmParameterizedTypeReference EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getJvmParameterizedTypeReferenceRule());
            }
            pushFollow(FOLLOW_ruleJvmParameterizedTypeReference_in_entryRuleJvmParameterizedTypeReference9409);
            iv_ruleJvmParameterizedTypeReference=ruleJvmParameterizedTypeReference();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleJvmParameterizedTypeReference;
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleJvmParameterizedTypeReference9419); if (state.failed) return current;

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
    // $ANTLR end "entryRuleJvmParameterizedTypeReference"


    // $ANTLR start "ruleJvmParameterizedTypeReference"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3875:1: ruleJvmParameterizedTypeReference returns [EObject current=null] : ( ( ( ruleQualifiedName ) ) ( ( ( '<' )=>otherlv_1= '<' ) ( (lv_arguments_2_0= ruleJvmArgumentTypeReference ) ) (otherlv_3= ',' ( (lv_arguments_4_0= ruleJvmArgumentTypeReference ) ) )* otherlv_5= '>' ( ( ( ( () '.' ) )=> ( () otherlv_7= '.' ) ) ( ( ruleValidID ) ) ( ( ( '<' )=>otherlv_9= '<' ) ( (lv_arguments_10_0= ruleJvmArgumentTypeReference ) ) (otherlv_11= ',' ( (lv_arguments_12_0= ruleJvmArgumentTypeReference ) ) )* otherlv_13= '>' )? )* )? ) ;
    public final EObject ruleJvmParameterizedTypeReference() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        Token otherlv_9=null;
        Token otherlv_11=null;
        Token otherlv_13=null;
        EObject lv_arguments_2_0 = null;

        EObject lv_arguments_4_0 = null;

        EObject lv_arguments_10_0 = null;

        EObject lv_arguments_12_0 = null;


         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3878:28: ( ( ( ( ruleQualifiedName ) ) ( ( ( '<' )=>otherlv_1= '<' ) ( (lv_arguments_2_0= ruleJvmArgumentTypeReference ) ) (otherlv_3= ',' ( (lv_arguments_4_0= ruleJvmArgumentTypeReference ) ) )* otherlv_5= '>' ( ( ( ( () '.' ) )=> ( () otherlv_7= '.' ) ) ( ( ruleValidID ) ) ( ( ( '<' )=>otherlv_9= '<' ) ( (lv_arguments_10_0= ruleJvmArgumentTypeReference ) ) (otherlv_11= ',' ( (lv_arguments_12_0= ruleJvmArgumentTypeReference ) ) )* otherlv_13= '>' )? )* )? ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3879:1: ( ( ( ruleQualifiedName ) ) ( ( ( '<' )=>otherlv_1= '<' ) ( (lv_arguments_2_0= ruleJvmArgumentTypeReference ) ) (otherlv_3= ',' ( (lv_arguments_4_0= ruleJvmArgumentTypeReference ) ) )* otherlv_5= '>' ( ( ( ( () '.' ) )=> ( () otherlv_7= '.' ) ) ( ( ruleValidID ) ) ( ( ( '<' )=>otherlv_9= '<' ) ( (lv_arguments_10_0= ruleJvmArgumentTypeReference ) ) (otherlv_11= ',' ( (lv_arguments_12_0= ruleJvmArgumentTypeReference ) ) )* otherlv_13= '>' )? )* )? )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3879:1: ( ( ( ruleQualifiedName ) ) ( ( ( '<' )=>otherlv_1= '<' ) ( (lv_arguments_2_0= ruleJvmArgumentTypeReference ) ) (otherlv_3= ',' ( (lv_arguments_4_0= ruleJvmArgumentTypeReference ) ) )* otherlv_5= '>' ( ( ( ( () '.' ) )=> ( () otherlv_7= '.' ) ) ( ( ruleValidID ) ) ( ( ( '<' )=>otherlv_9= '<' ) ( (lv_arguments_10_0= ruleJvmArgumentTypeReference ) ) (otherlv_11= ',' ( (lv_arguments_12_0= ruleJvmArgumentTypeReference ) ) )* otherlv_13= '>' )? )* )? )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3879:2: ( ( ruleQualifiedName ) ) ( ( ( '<' )=>otherlv_1= '<' ) ( (lv_arguments_2_0= ruleJvmArgumentTypeReference ) ) (otherlv_3= ',' ( (lv_arguments_4_0= ruleJvmArgumentTypeReference ) ) )* otherlv_5= '>' ( ( ( ( () '.' ) )=> ( () otherlv_7= '.' ) ) ( ( ruleValidID ) ) ( ( ( '<' )=>otherlv_9= '<' ) ( (lv_arguments_10_0= ruleJvmArgumentTypeReference ) ) (otherlv_11= ',' ( (lv_arguments_12_0= ruleJvmArgumentTypeReference ) ) )* otherlv_13= '>' )? )* )?
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3879:2: ( ( ruleQualifiedName ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3880:1: ( ruleQualifiedName )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3880:1: ( ruleQualifiedName )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3881:3: ruleQualifiedName
            {
            if ( state.backtracking==0 ) {

				if (current==null) {
		            current = createModelElement(grammarAccess.getJvmParameterizedTypeReferenceRule());
		        }

            }
            if ( state.backtracking==0 ) {

		        newCompositeNode(grammarAccess.getJvmParameterizedTypeReferenceAccess().getTypeJvmTypeCrossReference_0_0());

            }
            pushFollow(FOLLOW_ruleQualifiedName_in_ruleJvmParameterizedTypeReference9467);
            ruleQualifiedName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

		        afterParserOrEnumRuleCall();

            }

            }


            }

            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3894:2: ( ( ( '<' )=>otherlv_1= '<' ) ( (lv_arguments_2_0= ruleJvmArgumentTypeReference ) ) (otherlv_3= ',' ( (lv_arguments_4_0= ruleJvmArgumentTypeReference ) ) )* otherlv_5= '>' ( ( ( ( () '.' ) )=> ( () otherlv_7= '.' ) ) ( ( ruleValidID ) ) ( ( ( '<' )=>otherlv_9= '<' ) ( (lv_arguments_10_0= ruleJvmArgumentTypeReference ) ) (otherlv_11= ',' ( (lv_arguments_12_0= ruleJvmArgumentTypeReference ) ) )* otherlv_13= '>' )? )* )?
            int alt75=2;
            alt75 = dfa75.predict(input);
            switch (alt75) {
                case 1 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3894:3: ( ( '<' )=>otherlv_1= '<' ) ( (lv_arguments_2_0= ruleJvmArgumentTypeReference ) ) (otherlv_3= ',' ( (lv_arguments_4_0= ruleJvmArgumentTypeReference ) ) )* otherlv_5= '>' ( ( ( ( () '.' ) )=> ( () otherlv_7= '.' ) ) ( ( ruleValidID ) ) ( ( ( '<' )=>otherlv_9= '<' ) ( (lv_arguments_10_0= ruleJvmArgumentTypeReference ) ) (otherlv_11= ',' ( (lv_arguments_12_0= ruleJvmArgumentTypeReference ) ) )* otherlv_13= '>' )? )*
                    {
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3894:3: ( ( '<' )=>otherlv_1= '<' )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3894:4: ( '<' )=>otherlv_1= '<'
                    {
                    otherlv_1=(Token)match(input,23,FOLLOW_23_in_ruleJvmParameterizedTypeReference9488); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

				newLeafNode(otherlv_1, grammarAccess.getJvmParameterizedTypeReferenceAccess().getLessThanSignKeyword_1_0());

                    }

                    }

                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3899:2: ( (lv_arguments_2_0= ruleJvmArgumentTypeReference ) )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3900:1: (lv_arguments_2_0= ruleJvmArgumentTypeReference )
                    {
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3900:1: (lv_arguments_2_0= ruleJvmArgumentTypeReference )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3901:3: lv_arguments_2_0= ruleJvmArgumentTypeReference
                    {
                    if ( state.backtracking==0 ) {

			        newCompositeNode(grammarAccess.getJvmParameterizedTypeReferenceAccess().getArgumentsJvmArgumentTypeReferenceParserRuleCall_1_1_0());

                    }
                    pushFollow(FOLLOW_ruleJvmArgumentTypeReference_in_ruleJvmParameterizedTypeReference9510);
                    lv_arguments_2_0=ruleJvmArgumentTypeReference();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

			        if (current==null) {
			            current = createModelElementForParent(grammarAccess.getJvmParameterizedTypeReferenceRule());
			        }
					add(
						current,
						"arguments",
					lv_arguments_2_0,
					"JvmArgumentTypeReference");
			        afterParserOrEnumRuleCall();

                    }

                    }


                    }

                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3917:2: (otherlv_3= ',' ( (lv_arguments_4_0= ruleJvmArgumentTypeReference ) ) )*
                    loop71:
                    do {
                        int alt71=2;
                        int LA71_0 = input.LA(1);

                        if ( (LA71_0==57) ) {
                            alt71=1;
                        }


                        switch (alt71) {
			case 1 :
			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3917:4: otherlv_3= ',' ( (lv_arguments_4_0= ruleJvmArgumentTypeReference ) )
			    {
			    otherlv_3=(Token)match(input,57,FOLLOW_57_in_ruleJvmParameterizedTypeReference9523); if (state.failed) return current;
			    if ( state.backtracking==0 ) {

					newLeafNode(otherlv_3, grammarAccess.getJvmParameterizedTypeReferenceAccess().getCommaKeyword_1_2_0());

			    }
			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3921:1: ( (lv_arguments_4_0= ruleJvmArgumentTypeReference ) )
			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3922:1: (lv_arguments_4_0= ruleJvmArgumentTypeReference )
			    {
			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3922:1: (lv_arguments_4_0= ruleJvmArgumentTypeReference )
			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3923:3: lv_arguments_4_0= ruleJvmArgumentTypeReference
			    {
			    if ( state.backtracking==0 ) {

				        newCompositeNode(grammarAccess.getJvmParameterizedTypeReferenceAccess().getArgumentsJvmArgumentTypeReferenceParserRuleCall_1_2_1_0());

			    }
			    pushFollow(FOLLOW_ruleJvmArgumentTypeReference_in_ruleJvmParameterizedTypeReference9544);
			    lv_arguments_4_0=ruleJvmArgumentTypeReference();

			    state._fsp--;
			    if (state.failed) return current;
			    if ( state.backtracking==0 ) {

				        if (current==null) {
				            current = createModelElementForParent(grammarAccess.getJvmParameterizedTypeReferenceRule());
				        }
						add(
							current,
							"arguments",
						lv_arguments_4_0,
						"JvmArgumentTypeReference");
				        afterParserOrEnumRuleCall();

			    }

			    }


			    }


			    }
			    break;

			default :
			    break loop71;
                        }
                    } while (true);

                    otherlv_5=(Token)match(input,22,FOLLOW_22_in_ruleJvmParameterizedTypeReference9558); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

				newLeafNode(otherlv_5, grammarAccess.getJvmParameterizedTypeReferenceAccess().getGreaterThanSignKeyword_1_3());

                    }
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3943:1: ( ( ( ( () '.' ) )=> ( () otherlv_7= '.' ) ) ( ( ruleValidID ) ) ( ( ( '<' )=>otherlv_9= '<' ) ( (lv_arguments_10_0= ruleJvmArgumentTypeReference ) ) (otherlv_11= ',' ( (lv_arguments_12_0= ruleJvmArgumentTypeReference ) ) )* otherlv_13= '>' )? )*
                    loop74:
                    do {
                        int alt74=2;
                        int LA74_0 = input.LA(1);

                        if ( (LA74_0==41) ) {
                            int LA74_2 = input.LA(2);

                            if ( (LA74_2==RULE_ID) ) {
                                int LA74_3 = input.LA(3);

                                if ( (synpred28_InternalDSEL()) ) {
                                    alt74=1;
                                }


                            }


                        }


                        switch (alt74) {
			case 1 :
			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3943:2: ( ( ( () '.' ) )=> ( () otherlv_7= '.' ) ) ( ( ruleValidID ) ) ( ( ( '<' )=>otherlv_9= '<' ) ( (lv_arguments_10_0= ruleJvmArgumentTypeReference ) ) (otherlv_11= ',' ( (lv_arguments_12_0= ruleJvmArgumentTypeReference ) ) )* otherlv_13= '>' )?
			    {
			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3943:2: ( ( ( () '.' ) )=> ( () otherlv_7= '.' ) )
			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3943:3: ( ( () '.' ) )=> ( () otherlv_7= '.' )
			    {
			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3945:5: ( () otherlv_7= '.' )
			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3945:6: () otherlv_7= '.'
			    {
			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3945:6: ()
			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3946:5:
			    {
			    if ( state.backtracking==0 ) {

			              current = forceCreateModelElementAndSet(
			                  grammarAccess.getJvmParameterizedTypeReferenceAccess().getJvmInnerTypeReferenceOuterAction_1_4_0_0_0(),
			                  current);

			    }

			    }

			    otherlv_7=(Token)match(input,41,FOLLOW_41_in_ruleJvmParameterizedTypeReference9594); if (state.failed) return current;
			    if ( state.backtracking==0 ) {

					newLeafNode(otherlv_7, grammarAccess.getJvmParameterizedTypeReferenceAccess().getFullStopKeyword_1_4_0_0_1());

			    }

			    }


			    }

			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3955:3: ( ( ruleValidID ) )
			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3956:1: ( ruleValidID )
			    {
			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3956:1: ( ruleValidID )
			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3957:3: ruleValidID
			    {
			    if ( state.backtracking==0 ) {

						if (current==null) {
				            current = createModelElement(grammarAccess.getJvmParameterizedTypeReferenceRule());
				        }

			    }
			    if ( state.backtracking==0 ) {

				        newCompositeNode(grammarAccess.getJvmParameterizedTypeReferenceAccess().getTypeJvmTypeCrossReference_1_4_1_0());

			    }
			    pushFollow(FOLLOW_ruleValidID_in_ruleJvmParameterizedTypeReference9619);
			    ruleValidID();

			    state._fsp--;
			    if (state.failed) return current;
			    if ( state.backtracking==0 ) {

				        afterParserOrEnumRuleCall();

			    }

			    }


			    }

			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3970:2: ( ( ( '<' )=>otherlv_9= '<' ) ( (lv_arguments_10_0= ruleJvmArgumentTypeReference ) ) (otherlv_11= ',' ( (lv_arguments_12_0= ruleJvmArgumentTypeReference ) ) )* otherlv_13= '>' )?
			    int alt73=2;
			    alt73 = dfa73.predict(input);
			    switch (alt73) {
			        case 1 :
			            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3970:3: ( ( '<' )=>otherlv_9= '<' ) ( (lv_arguments_10_0= ruleJvmArgumentTypeReference ) ) (otherlv_11= ',' ( (lv_arguments_12_0= ruleJvmArgumentTypeReference ) ) )* otherlv_13= '>'
			            {
			            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3970:3: ( ( '<' )=>otherlv_9= '<' )
			            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3970:4: ( '<' )=>otherlv_9= '<'
			            {
			            otherlv_9=(Token)match(input,23,FOLLOW_23_in_ruleJvmParameterizedTypeReference9640); if (state.failed) return current;
			            if ( state.backtracking==0 ) {

						newLeafNode(otherlv_9, grammarAccess.getJvmParameterizedTypeReferenceAccess().getLessThanSignKeyword_1_4_2_0());

			            }

			            }

			            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3975:2: ( (lv_arguments_10_0= ruleJvmArgumentTypeReference ) )
			            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3976:1: (lv_arguments_10_0= ruleJvmArgumentTypeReference )
			            {
			            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3976:1: (lv_arguments_10_0= ruleJvmArgumentTypeReference )
			            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3977:3: lv_arguments_10_0= ruleJvmArgumentTypeReference
			            {
			            if ( state.backtracking==0 ) {

					        newCompositeNode(grammarAccess.getJvmParameterizedTypeReferenceAccess().getArgumentsJvmArgumentTypeReferenceParserRuleCall_1_4_2_1_0());

			            }
			            pushFollow(FOLLOW_ruleJvmArgumentTypeReference_in_ruleJvmParameterizedTypeReference9662);
			            lv_arguments_10_0=ruleJvmArgumentTypeReference();

			            state._fsp--;
			            if (state.failed) return current;
			            if ( state.backtracking==0 ) {

					        if (current==null) {
					            current = createModelElementForParent(grammarAccess.getJvmParameterizedTypeReferenceRule());
					        }
							add(
								current,
								"arguments",
							lv_arguments_10_0,
							"JvmArgumentTypeReference");
					        afterParserOrEnumRuleCall();

			            }

			            }


			            }

			            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3993:2: (otherlv_11= ',' ( (lv_arguments_12_0= ruleJvmArgumentTypeReference ) ) )*
			            loop72:
			            do {
			                int alt72=2;
			                int LA72_0 = input.LA(1);

			                if ( (LA72_0==57) ) {
			                    alt72=1;
			                }


			                switch (alt72) {
					case 1 :
					    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3993:4: otherlv_11= ',' ( (lv_arguments_12_0= ruleJvmArgumentTypeReference ) )
					    {
					    otherlv_11=(Token)match(input,57,FOLLOW_57_in_ruleJvmParameterizedTypeReference9675); if (state.failed) return current;
					    if ( state.backtracking==0 ) {

							newLeafNode(otherlv_11, grammarAccess.getJvmParameterizedTypeReferenceAccess().getCommaKeyword_1_4_2_2_0());

					    }
					    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3997:1: ( (lv_arguments_12_0= ruleJvmArgumentTypeReference ) )
					    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3998:1: (lv_arguments_12_0= ruleJvmArgumentTypeReference )
					    {
					    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3998:1: (lv_arguments_12_0= ruleJvmArgumentTypeReference )
					    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3999:3: lv_arguments_12_0= ruleJvmArgumentTypeReference
					    {
					    if ( state.backtracking==0 ) {

						        newCompositeNode(grammarAccess.getJvmParameterizedTypeReferenceAccess().getArgumentsJvmArgumentTypeReferenceParserRuleCall_1_4_2_2_1_0());

					    }
					    pushFollow(FOLLOW_ruleJvmArgumentTypeReference_in_ruleJvmParameterizedTypeReference9696);
					    lv_arguments_12_0=ruleJvmArgumentTypeReference();

					    state._fsp--;
					    if (state.failed) return current;
					    if ( state.backtracking==0 ) {

						        if (current==null) {
						            current = createModelElementForParent(grammarAccess.getJvmParameterizedTypeReferenceRule());
						        }
								add(
									current,
									"arguments",
								lv_arguments_12_0,
								"JvmArgumentTypeReference");
						        afterParserOrEnumRuleCall();

					    }

					    }


					    }


					    }
					    break;

					default :
					    break loop72;
			                }
			            } while (true);

			            otherlv_13=(Token)match(input,22,FOLLOW_22_in_ruleJvmParameterizedTypeReference9710); if (state.failed) return current;
			            if ( state.backtracking==0 ) {

						newLeafNode(otherlv_13, grammarAccess.getJvmParameterizedTypeReferenceAccess().getGreaterThanSignKeyword_1_4_2_3());

			            }

			            }
			            break;

			    }


			    }
			    break;

			default :
			    break loop74;
                        }
                    } while (true);


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleJvmParameterizedTypeReference"


    // $ANTLR start "entryRuleJvmArgumentTypeReference"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4027:1: entryRuleJvmArgumentTypeReference returns [EObject current=null] : iv_ruleJvmArgumentTypeReference= ruleJvmArgumentTypeReference EOF ;
    public final EObject entryRuleJvmArgumentTypeReference() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleJvmArgumentTypeReference = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4028:2: (iv_ruleJvmArgumentTypeReference= ruleJvmArgumentTypeReference EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4029:2: iv_ruleJvmArgumentTypeReference= ruleJvmArgumentTypeReference EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getJvmArgumentTypeReferenceRule());
            }
            pushFollow(FOLLOW_ruleJvmArgumentTypeReference_in_entryRuleJvmArgumentTypeReference9752);
            iv_ruleJvmArgumentTypeReference=ruleJvmArgumentTypeReference();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleJvmArgumentTypeReference;
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleJvmArgumentTypeReference9762); if (state.failed) return current;

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
    // $ANTLR end "entryRuleJvmArgumentTypeReference"


    // $ANTLR start "ruleJvmArgumentTypeReference"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4036:1: ruleJvmArgumentTypeReference returns [EObject current=null] : (this_JvmTypeReference_0= ruleJvmTypeReference | this_JvmWildcardTypeReference_1= ruleJvmWildcardTypeReference ) ;
    public final EObject ruleJvmArgumentTypeReference() throws RecognitionException {
        EObject current = null;

        EObject this_JvmTypeReference_0 = null;

        EObject this_JvmWildcardTypeReference_1 = null;


         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4039:28: ( (this_JvmTypeReference_0= ruleJvmTypeReference | this_JvmWildcardTypeReference_1= ruleJvmWildcardTypeReference ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4040:1: (this_JvmTypeReference_0= ruleJvmTypeReference | this_JvmWildcardTypeReference_1= ruleJvmWildcardTypeReference )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4040:1: (this_JvmTypeReference_0= ruleJvmTypeReference | this_JvmWildcardTypeReference_1= ruleJvmWildcardTypeReference )
            int alt76=2;
            int LA76_0 = input.LA(1);

            if ( (LA76_0==RULE_ID||LA76_0==58||LA76_0==77) ) {
                alt76=1;
            }
            else if ( (LA76_0==78) ) {
                alt76=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 76, 0, input);

                throw nvae;
            }
            switch (alt76) {
                case 1 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4041:5: this_JvmTypeReference_0= ruleJvmTypeReference
                    {
                    if ( state.backtracking==0 ) {

                              newCompositeNode(grammarAccess.getJvmArgumentTypeReferenceAccess().getJvmTypeReferenceParserRuleCall_0());

                    }
                    pushFollow(FOLLOW_ruleJvmTypeReference_in_ruleJvmArgumentTypeReference9809);
                    this_JvmTypeReference_0=ruleJvmTypeReference();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = this_JvmTypeReference_0;
                              afterParserOrEnumRuleCall();

                    }

                    }
                    break;
                case 2 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4051:5: this_JvmWildcardTypeReference_1= ruleJvmWildcardTypeReference
                    {
                    if ( state.backtracking==0 ) {

                              newCompositeNode(grammarAccess.getJvmArgumentTypeReferenceAccess().getJvmWildcardTypeReferenceParserRuleCall_1());

                    }
                    pushFollow(FOLLOW_ruleJvmWildcardTypeReference_in_ruleJvmArgumentTypeReference9836);
                    this_JvmWildcardTypeReference_1=ruleJvmWildcardTypeReference();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              current = this_JvmWildcardTypeReference_1;
                              afterParserOrEnumRuleCall();

                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleJvmArgumentTypeReference"


    // $ANTLR start "entryRuleJvmWildcardTypeReference"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4067:1: entryRuleJvmWildcardTypeReference returns [EObject current=null] : iv_ruleJvmWildcardTypeReference= ruleJvmWildcardTypeReference EOF ;
    public final EObject entryRuleJvmWildcardTypeReference() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleJvmWildcardTypeReference = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4068:2: (iv_ruleJvmWildcardTypeReference= ruleJvmWildcardTypeReference EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4069:2: iv_ruleJvmWildcardTypeReference= ruleJvmWildcardTypeReference EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getJvmWildcardTypeReferenceRule());
            }
            pushFollow(FOLLOW_ruleJvmWildcardTypeReference_in_entryRuleJvmWildcardTypeReference9871);
            iv_ruleJvmWildcardTypeReference=ruleJvmWildcardTypeReference();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleJvmWildcardTypeReference;
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleJvmWildcardTypeReference9881); if (state.failed) return current;

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
    // $ANTLR end "entryRuleJvmWildcardTypeReference"


    // $ANTLR start "ruleJvmWildcardTypeReference"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4076:1: ruleJvmWildcardTypeReference returns [EObject current=null] : ( () otherlv_1= '?' ( ( ( (lv_constraints_2_0= ruleJvmUpperBound ) ) ( (lv_constraints_3_0= ruleJvmUpperBoundAnded ) )* ) | ( ( (lv_constraints_4_0= ruleJvmLowerBound ) ) ( (lv_constraints_5_0= ruleJvmLowerBoundAnded ) )* ) )? ) ;
    public final EObject ruleJvmWildcardTypeReference() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_constraints_2_0 = null;

        EObject lv_constraints_3_0 = null;

        EObject lv_constraints_4_0 = null;

        EObject lv_constraints_5_0 = null;


         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4079:28: ( ( () otherlv_1= '?' ( ( ( (lv_constraints_2_0= ruleJvmUpperBound ) ) ( (lv_constraints_3_0= ruleJvmUpperBoundAnded ) )* ) | ( ( (lv_constraints_4_0= ruleJvmLowerBound ) ) ( (lv_constraints_5_0= ruleJvmLowerBoundAnded ) )* ) )? ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4080:1: ( () otherlv_1= '?' ( ( ( (lv_constraints_2_0= ruleJvmUpperBound ) ) ( (lv_constraints_3_0= ruleJvmUpperBoundAnded ) )* ) | ( ( (lv_constraints_4_0= ruleJvmLowerBound ) ) ( (lv_constraints_5_0= ruleJvmLowerBoundAnded ) )* ) )? )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4080:1: ( () otherlv_1= '?' ( ( ( (lv_constraints_2_0= ruleJvmUpperBound ) ) ( (lv_constraints_3_0= ruleJvmUpperBoundAnded ) )* ) | ( ( (lv_constraints_4_0= ruleJvmLowerBound ) ) ( (lv_constraints_5_0= ruleJvmLowerBoundAnded ) )* ) )? )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4080:2: () otherlv_1= '?' ( ( ( (lv_constraints_2_0= ruleJvmUpperBound ) ) ( (lv_constraints_3_0= ruleJvmUpperBoundAnded ) )* ) | ( ( (lv_constraints_4_0= ruleJvmLowerBound ) ) ( (lv_constraints_5_0= ruleJvmLowerBoundAnded ) )* ) )?
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4080:2: ()
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4081:5:
            {
            if ( state.backtracking==0 ) {

                      current = forceCreateModelElement(
                          grammarAccess.getJvmWildcardTypeReferenceAccess().getJvmWildcardTypeReferenceAction_0(),
                          current);

            }

            }

            otherlv_1=(Token)match(input,78,FOLLOW_78_in_ruleJvmWildcardTypeReference9927); if (state.failed) return current;
            if ( state.backtracking==0 ) {

			newLeafNode(otherlv_1, grammarAccess.getJvmWildcardTypeReferenceAccess().getQuestionMarkKeyword_1());

            }
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4090:1: ( ( ( (lv_constraints_2_0= ruleJvmUpperBound ) ) ( (lv_constraints_3_0= ruleJvmUpperBoundAnded ) )* ) | ( ( (lv_constraints_4_0= ruleJvmLowerBound ) ) ( (lv_constraints_5_0= ruleJvmLowerBoundAnded ) )* ) )?
            int alt79=3;
            int LA79_0 = input.LA(1);

            if ( (LA79_0==71) ) {
                alt79=1;
            }
            else if ( (LA79_0==75) ) {
                alt79=2;
            }
            switch (alt79) {
                case 1 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4090:2: ( ( (lv_constraints_2_0= ruleJvmUpperBound ) ) ( (lv_constraints_3_0= ruleJvmUpperBoundAnded ) )* )
                    {
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4090:2: ( ( (lv_constraints_2_0= ruleJvmUpperBound ) ) ( (lv_constraints_3_0= ruleJvmUpperBoundAnded ) )* )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4090:3: ( (lv_constraints_2_0= ruleJvmUpperBound ) ) ( (lv_constraints_3_0= ruleJvmUpperBoundAnded ) )*
                    {
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4090:3: ( (lv_constraints_2_0= ruleJvmUpperBound ) )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4091:1: (lv_constraints_2_0= ruleJvmUpperBound )
                    {
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4091:1: (lv_constraints_2_0= ruleJvmUpperBound )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4092:3: lv_constraints_2_0= ruleJvmUpperBound
                    {
                    if ( state.backtracking==0 ) {

			        newCompositeNode(grammarAccess.getJvmWildcardTypeReferenceAccess().getConstraintsJvmUpperBoundParserRuleCall_2_0_0_0());

                    }
                    pushFollow(FOLLOW_ruleJvmUpperBound_in_ruleJvmWildcardTypeReference9950);
                    lv_constraints_2_0=ruleJvmUpperBound();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

			        if (current==null) {
			            current = createModelElementForParent(grammarAccess.getJvmWildcardTypeReferenceRule());
			        }
					add(
						current,
						"constraints",
					lv_constraints_2_0,
					"JvmUpperBound");
			        afterParserOrEnumRuleCall();

                    }

                    }


                    }

                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4108:2: ( (lv_constraints_3_0= ruleJvmUpperBoundAnded ) )*
                    loop77:
                    do {
                        int alt77=2;
                        int LA77_0 = input.LA(1);

                        if ( (LA77_0==79) ) {
                            alt77=1;
                        }


                        switch (alt77) {
			case 1 :
			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4109:1: (lv_constraints_3_0= ruleJvmUpperBoundAnded )
			    {
			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4109:1: (lv_constraints_3_0= ruleJvmUpperBoundAnded )
			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4110:3: lv_constraints_3_0= ruleJvmUpperBoundAnded
			    {
			    if ( state.backtracking==0 ) {

				        newCompositeNode(grammarAccess.getJvmWildcardTypeReferenceAccess().getConstraintsJvmUpperBoundAndedParserRuleCall_2_0_1_0());

			    }
			    pushFollow(FOLLOW_ruleJvmUpperBoundAnded_in_ruleJvmWildcardTypeReference9971);
			    lv_constraints_3_0=ruleJvmUpperBoundAnded();

			    state._fsp--;
			    if (state.failed) return current;
			    if ( state.backtracking==0 ) {

				        if (current==null) {
				            current = createModelElementForParent(grammarAccess.getJvmWildcardTypeReferenceRule());
				        }
						add(
							current,
							"constraints",
						lv_constraints_3_0,
						"JvmUpperBoundAnded");
				        afterParserOrEnumRuleCall();

			    }

			    }


			    }
			    break;

			default :
			    break loop77;
                        }
                    } while (true);


                    }


                    }
                    break;
                case 2 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4127:6: ( ( (lv_constraints_4_0= ruleJvmLowerBound ) ) ( (lv_constraints_5_0= ruleJvmLowerBoundAnded ) )* )
                    {
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4127:6: ( ( (lv_constraints_4_0= ruleJvmLowerBound ) ) ( (lv_constraints_5_0= ruleJvmLowerBoundAnded ) )* )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4127:7: ( (lv_constraints_4_0= ruleJvmLowerBound ) ) ( (lv_constraints_5_0= ruleJvmLowerBoundAnded ) )*
                    {
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4127:7: ( (lv_constraints_4_0= ruleJvmLowerBound ) )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4128:1: (lv_constraints_4_0= ruleJvmLowerBound )
                    {
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4128:1: (lv_constraints_4_0= ruleJvmLowerBound )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4129:3: lv_constraints_4_0= ruleJvmLowerBound
                    {
                    if ( state.backtracking==0 ) {

			        newCompositeNode(grammarAccess.getJvmWildcardTypeReferenceAccess().getConstraintsJvmLowerBoundParserRuleCall_2_1_0_0());

                    }
                    pushFollow(FOLLOW_ruleJvmLowerBound_in_ruleJvmWildcardTypeReference10001);
                    lv_constraints_4_0=ruleJvmLowerBound();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

			        if (current==null) {
			            current = createModelElementForParent(grammarAccess.getJvmWildcardTypeReferenceRule());
			        }
					add(
						current,
						"constraints",
					lv_constraints_4_0,
					"JvmLowerBound");
			        afterParserOrEnumRuleCall();

                    }

                    }


                    }

                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4145:2: ( (lv_constraints_5_0= ruleJvmLowerBoundAnded ) )*
                    loop78:
                    do {
                        int alt78=2;
                        int LA78_0 = input.LA(1);

                        if ( (LA78_0==79) ) {
                            alt78=1;
                        }


                        switch (alt78) {
			case 1 :
			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4146:1: (lv_constraints_5_0= ruleJvmLowerBoundAnded )
			    {
			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4146:1: (lv_constraints_5_0= ruleJvmLowerBoundAnded )
			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4147:3: lv_constraints_5_0= ruleJvmLowerBoundAnded
			    {
			    if ( state.backtracking==0 ) {

				        newCompositeNode(grammarAccess.getJvmWildcardTypeReferenceAccess().getConstraintsJvmLowerBoundAndedParserRuleCall_2_1_1_0());

			    }
			    pushFollow(FOLLOW_ruleJvmLowerBoundAnded_in_ruleJvmWildcardTypeReference10022);
			    lv_constraints_5_0=ruleJvmLowerBoundAnded();

			    state._fsp--;
			    if (state.failed) return current;
			    if ( state.backtracking==0 ) {

				        if (current==null) {
				            current = createModelElementForParent(grammarAccess.getJvmWildcardTypeReferenceRule());
				        }
						add(
							current,
							"constraints",
						lv_constraints_5_0,
						"JvmLowerBoundAnded");
				        afterParserOrEnumRuleCall();

			    }

			    }


			    }
			    break;

			default :
			    break loop78;
                        }
                    } while (true);


                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleJvmWildcardTypeReference"


    // $ANTLR start "entryRuleJvmUpperBound"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4171:1: entryRuleJvmUpperBound returns [EObject current=null] : iv_ruleJvmUpperBound= ruleJvmUpperBound EOF ;
    public final EObject entryRuleJvmUpperBound() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleJvmUpperBound = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4172:2: (iv_ruleJvmUpperBound= ruleJvmUpperBound EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4173:2: iv_ruleJvmUpperBound= ruleJvmUpperBound EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getJvmUpperBoundRule());
            }
            pushFollow(FOLLOW_ruleJvmUpperBound_in_entryRuleJvmUpperBound10062);
            iv_ruleJvmUpperBound=ruleJvmUpperBound();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleJvmUpperBound;
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleJvmUpperBound10072); if (state.failed) return current;

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
    // $ANTLR end "entryRuleJvmUpperBound"


    // $ANTLR start "ruleJvmUpperBound"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4180:1: ruleJvmUpperBound returns [EObject current=null] : (otherlv_0= 'extends' ( (lv_typeReference_1_0= ruleJvmTypeReference ) ) ) ;
    public final EObject ruleJvmUpperBound() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        EObject lv_typeReference_1_0 = null;


         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4183:28: ( (otherlv_0= 'extends' ( (lv_typeReference_1_0= ruleJvmTypeReference ) ) ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4184:1: (otherlv_0= 'extends' ( (lv_typeReference_1_0= ruleJvmTypeReference ) ) )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4184:1: (otherlv_0= 'extends' ( (lv_typeReference_1_0= ruleJvmTypeReference ) ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4184:3: otherlv_0= 'extends' ( (lv_typeReference_1_0= ruleJvmTypeReference ) )
            {
            otherlv_0=(Token)match(input,71,FOLLOW_71_in_ruleJvmUpperBound10109); if (state.failed) return current;
            if ( state.backtracking==0 ) {

			newLeafNode(otherlv_0, grammarAccess.getJvmUpperBoundAccess().getExtendsKeyword_0());

            }
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4188:1: ( (lv_typeReference_1_0= ruleJvmTypeReference ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4189:1: (lv_typeReference_1_0= ruleJvmTypeReference )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4189:1: (lv_typeReference_1_0= ruleJvmTypeReference )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4190:3: lv_typeReference_1_0= ruleJvmTypeReference
            {
            if ( state.backtracking==0 ) {

		        newCompositeNode(grammarAccess.getJvmUpperBoundAccess().getTypeReferenceJvmTypeReferenceParserRuleCall_1_0());

            }
            pushFollow(FOLLOW_ruleJvmTypeReference_in_ruleJvmUpperBound10130);
            lv_typeReference_1_0=ruleJvmTypeReference();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

		        if (current==null) {
		            current = createModelElementForParent(grammarAccess.getJvmUpperBoundRule());
		        }
				set(
					current,
					"typeReference",
				lv_typeReference_1_0,
				"JvmTypeReference");
		        afterParserOrEnumRuleCall();

            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleJvmUpperBound"


    // $ANTLR start "entryRuleJvmUpperBoundAnded"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4214:1: entryRuleJvmUpperBoundAnded returns [EObject current=null] : iv_ruleJvmUpperBoundAnded= ruleJvmUpperBoundAnded EOF ;
    public final EObject entryRuleJvmUpperBoundAnded() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleJvmUpperBoundAnded = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4215:2: (iv_ruleJvmUpperBoundAnded= ruleJvmUpperBoundAnded EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4216:2: iv_ruleJvmUpperBoundAnded= ruleJvmUpperBoundAnded EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getJvmUpperBoundAndedRule());
            }
            pushFollow(FOLLOW_ruleJvmUpperBoundAnded_in_entryRuleJvmUpperBoundAnded10166);
            iv_ruleJvmUpperBoundAnded=ruleJvmUpperBoundAnded();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleJvmUpperBoundAnded;
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleJvmUpperBoundAnded10176); if (state.failed) return current;

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
    // $ANTLR end "entryRuleJvmUpperBoundAnded"


    // $ANTLR start "ruleJvmUpperBoundAnded"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4223:1: ruleJvmUpperBoundAnded returns [EObject current=null] : (otherlv_0= '&' ( (lv_typeReference_1_0= ruleJvmTypeReference ) ) ) ;
    public final EObject ruleJvmUpperBoundAnded() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        EObject lv_typeReference_1_0 = null;


         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4226:28: ( (otherlv_0= '&' ( (lv_typeReference_1_0= ruleJvmTypeReference ) ) ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4227:1: (otherlv_0= '&' ( (lv_typeReference_1_0= ruleJvmTypeReference ) ) )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4227:1: (otherlv_0= '&' ( (lv_typeReference_1_0= ruleJvmTypeReference ) ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4227:3: otherlv_0= '&' ( (lv_typeReference_1_0= ruleJvmTypeReference ) )
            {
            otherlv_0=(Token)match(input,79,FOLLOW_79_in_ruleJvmUpperBoundAnded10213); if (state.failed) return current;
            if ( state.backtracking==0 ) {

			newLeafNode(otherlv_0, grammarAccess.getJvmUpperBoundAndedAccess().getAmpersandKeyword_0());

            }
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4231:1: ( (lv_typeReference_1_0= ruleJvmTypeReference ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4232:1: (lv_typeReference_1_0= ruleJvmTypeReference )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4232:1: (lv_typeReference_1_0= ruleJvmTypeReference )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4233:3: lv_typeReference_1_0= ruleJvmTypeReference
            {
            if ( state.backtracking==0 ) {

		        newCompositeNode(grammarAccess.getJvmUpperBoundAndedAccess().getTypeReferenceJvmTypeReferenceParserRuleCall_1_0());

            }
            pushFollow(FOLLOW_ruleJvmTypeReference_in_ruleJvmUpperBoundAnded10234);
            lv_typeReference_1_0=ruleJvmTypeReference();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

		        if (current==null) {
		            current = createModelElementForParent(grammarAccess.getJvmUpperBoundAndedRule());
		        }
				set(
					current,
					"typeReference",
				lv_typeReference_1_0,
				"JvmTypeReference");
		        afterParserOrEnumRuleCall();

            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleJvmUpperBoundAnded"


    // $ANTLR start "entryRuleJvmLowerBound"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4257:1: entryRuleJvmLowerBound returns [EObject current=null] : iv_ruleJvmLowerBound= ruleJvmLowerBound EOF ;
    public final EObject entryRuleJvmLowerBound() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleJvmLowerBound = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4258:2: (iv_ruleJvmLowerBound= ruleJvmLowerBound EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4259:2: iv_ruleJvmLowerBound= ruleJvmLowerBound EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getJvmLowerBoundRule());
            }
            pushFollow(FOLLOW_ruleJvmLowerBound_in_entryRuleJvmLowerBound10270);
            iv_ruleJvmLowerBound=ruleJvmLowerBound();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleJvmLowerBound;
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleJvmLowerBound10280); if (state.failed) return current;

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
    // $ANTLR end "entryRuleJvmLowerBound"


    // $ANTLR start "ruleJvmLowerBound"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4266:1: ruleJvmLowerBound returns [EObject current=null] : (otherlv_0= 'super' ( (lv_typeReference_1_0= ruleJvmTypeReference ) ) ) ;
    public final EObject ruleJvmLowerBound() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        EObject lv_typeReference_1_0 = null;


         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4269:28: ( (otherlv_0= 'super' ( (lv_typeReference_1_0= ruleJvmTypeReference ) ) ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4270:1: (otherlv_0= 'super' ( (lv_typeReference_1_0= ruleJvmTypeReference ) ) )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4270:1: (otherlv_0= 'super' ( (lv_typeReference_1_0= ruleJvmTypeReference ) ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4270:3: otherlv_0= 'super' ( (lv_typeReference_1_0= ruleJvmTypeReference ) )
            {
            otherlv_0=(Token)match(input,75,FOLLOW_75_in_ruleJvmLowerBound10317); if (state.failed) return current;
            if ( state.backtracking==0 ) {

			newLeafNode(otherlv_0, grammarAccess.getJvmLowerBoundAccess().getSuperKeyword_0());

            }
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4274:1: ( (lv_typeReference_1_0= ruleJvmTypeReference ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4275:1: (lv_typeReference_1_0= ruleJvmTypeReference )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4275:1: (lv_typeReference_1_0= ruleJvmTypeReference )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4276:3: lv_typeReference_1_0= ruleJvmTypeReference
            {
            if ( state.backtracking==0 ) {

		        newCompositeNode(grammarAccess.getJvmLowerBoundAccess().getTypeReferenceJvmTypeReferenceParserRuleCall_1_0());

            }
            pushFollow(FOLLOW_ruleJvmTypeReference_in_ruleJvmLowerBound10338);
            lv_typeReference_1_0=ruleJvmTypeReference();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

		        if (current==null) {
		            current = createModelElementForParent(grammarAccess.getJvmLowerBoundRule());
		        }
				set(
					current,
					"typeReference",
				lv_typeReference_1_0,
				"JvmTypeReference");
		        afterParserOrEnumRuleCall();

            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleJvmLowerBound"


    // $ANTLR start "entryRuleJvmLowerBoundAnded"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4300:1: entryRuleJvmLowerBoundAnded returns [EObject current=null] : iv_ruleJvmLowerBoundAnded= ruleJvmLowerBoundAnded EOF ;
    public final EObject entryRuleJvmLowerBoundAnded() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleJvmLowerBoundAnded = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4301:2: (iv_ruleJvmLowerBoundAnded= ruleJvmLowerBoundAnded EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4302:2: iv_ruleJvmLowerBoundAnded= ruleJvmLowerBoundAnded EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getJvmLowerBoundAndedRule());
            }
            pushFollow(FOLLOW_ruleJvmLowerBoundAnded_in_entryRuleJvmLowerBoundAnded10374);
            iv_ruleJvmLowerBoundAnded=ruleJvmLowerBoundAnded();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleJvmLowerBoundAnded;
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleJvmLowerBoundAnded10384); if (state.failed) return current;

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
    // $ANTLR end "entryRuleJvmLowerBoundAnded"


    // $ANTLR start "ruleJvmLowerBoundAnded"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4309:1: ruleJvmLowerBoundAnded returns [EObject current=null] : (otherlv_0= '&' ( (lv_typeReference_1_0= ruleJvmTypeReference ) ) ) ;
    public final EObject ruleJvmLowerBoundAnded() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        EObject lv_typeReference_1_0 = null;


         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4312:28: ( (otherlv_0= '&' ( (lv_typeReference_1_0= ruleJvmTypeReference ) ) ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4313:1: (otherlv_0= '&' ( (lv_typeReference_1_0= ruleJvmTypeReference ) ) )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4313:1: (otherlv_0= '&' ( (lv_typeReference_1_0= ruleJvmTypeReference ) ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4313:3: otherlv_0= '&' ( (lv_typeReference_1_0= ruleJvmTypeReference ) )
            {
            otherlv_0=(Token)match(input,79,FOLLOW_79_in_ruleJvmLowerBoundAnded10421); if (state.failed) return current;
            if ( state.backtracking==0 ) {

			newLeafNode(otherlv_0, grammarAccess.getJvmLowerBoundAndedAccess().getAmpersandKeyword_0());

            }
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4317:1: ( (lv_typeReference_1_0= ruleJvmTypeReference ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4318:1: (lv_typeReference_1_0= ruleJvmTypeReference )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4318:1: (lv_typeReference_1_0= ruleJvmTypeReference )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4319:3: lv_typeReference_1_0= ruleJvmTypeReference
            {
            if ( state.backtracking==0 ) {

		        newCompositeNode(grammarAccess.getJvmLowerBoundAndedAccess().getTypeReferenceJvmTypeReferenceParserRuleCall_1_0());

            }
            pushFollow(FOLLOW_ruleJvmTypeReference_in_ruleJvmLowerBoundAnded10442);
            lv_typeReference_1_0=ruleJvmTypeReference();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

		        if (current==null) {
		            current = createModelElementForParent(grammarAccess.getJvmLowerBoundAndedRule());
		        }
				set(
					current,
					"typeReference",
				lv_typeReference_1_0,
				"JvmTypeReference");
		        afterParserOrEnumRuleCall();

            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleJvmLowerBoundAnded"


    // $ANTLR start "entryRuleQualifiedNameWithWildcard"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4345:1: entryRuleQualifiedNameWithWildcard returns [String current=null] : iv_ruleQualifiedNameWithWildcard= ruleQualifiedNameWithWildcard EOF ;
    public final String entryRuleQualifiedNameWithWildcard() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleQualifiedNameWithWildcard = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4346:2: (iv_ruleQualifiedNameWithWildcard= ruleQualifiedNameWithWildcard EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4347:2: iv_ruleQualifiedNameWithWildcard= ruleQualifiedNameWithWildcard EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getQualifiedNameWithWildcardRule());
            }
            pushFollow(FOLLOW_ruleQualifiedNameWithWildcard_in_entryRuleQualifiedNameWithWildcard10481);
            iv_ruleQualifiedNameWithWildcard=ruleQualifiedNameWithWildcard();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleQualifiedNameWithWildcard.getText();
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleQualifiedNameWithWildcard10492); if (state.failed) return current;

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
    // $ANTLR end "entryRuleQualifiedNameWithWildcard"


    // $ANTLR start "ruleQualifiedNameWithWildcard"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4354:1: ruleQualifiedNameWithWildcard returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_QualifiedName_0= ruleQualifiedName kw= '.' kw= '*' ) ;
    public final AntlrDatatypeRuleToken ruleQualifiedNameWithWildcard() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_QualifiedName_0 = null;


         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4357:28: ( (this_QualifiedName_0= ruleQualifiedName kw= '.' kw= '*' ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4358:1: (this_QualifiedName_0= ruleQualifiedName kw= '.' kw= '*' )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4358:1: (this_QualifiedName_0= ruleQualifiedName kw= '.' kw= '*' )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4359:5: this_QualifiedName_0= ruleQualifiedName kw= '.' kw= '*'
            {
            if ( state.backtracking==0 ) {

                      newCompositeNode(grammarAccess.getQualifiedNameWithWildcardAccess().getQualifiedNameParserRuleCall_0());

            }
            pushFollow(FOLLOW_ruleQualifiedName_in_ruleQualifiedNameWithWildcard10539);
            this_QualifiedName_0=ruleQualifiedName();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

			current.merge(this_QualifiedName_0);

            }
            if ( state.backtracking==0 ) {

                      afterParserOrEnumRuleCall();

            }
            kw=(Token)match(input,41,FOLLOW_41_in_ruleQualifiedNameWithWildcard10557); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current.merge(kw);
                      newLeafNode(kw, grammarAccess.getQualifiedNameWithWildcardAccess().getFullStopKeyword_1());

            }
            kw=(Token)match(input,48,FOLLOW_48_in_ruleQualifiedNameWithWildcard10570); if (state.failed) return current;
            if ( state.backtracking==0 ) {

                      current.merge(kw);
                      newLeafNode(kw, grammarAccess.getQualifiedNameWithWildcardAccess().getAsteriskKeyword_2());

            }

            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleQualifiedNameWithWildcard"


    // $ANTLR start "entryRuleValidID"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4389:1: entryRuleValidID returns [String current=null] : iv_ruleValidID= ruleValidID EOF ;
    public final String entryRuleValidID() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleValidID = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4390:2: (iv_ruleValidID= ruleValidID EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4391:2: iv_ruleValidID= ruleValidID EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getValidIDRule());
            }
            pushFollow(FOLLOW_ruleValidID_in_entryRuleValidID10611);
            iv_ruleValidID=ruleValidID();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleValidID.getText();
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleValidID10622); if (state.failed) return current;

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
    // $ANTLR end "entryRuleValidID"


    // $ANTLR start "ruleValidID"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4398:1: ruleValidID returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_ID_0= RULE_ID ;
    public final AntlrDatatypeRuleToken ruleValidID() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;

         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4401:28: (this_ID_0= RULE_ID )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4402:5: this_ID_0= RULE_ID
            {
            this_ID_0=(Token)match(input,RULE_ID,FOLLOW_RULE_ID_in_ruleValidID10661); if (state.failed) return current;
            if ( state.backtracking==0 ) {

			current.merge(this_ID_0);

            }
            if ( state.backtracking==0 ) {

                  newLeafNode(this_ID_0, grammarAccess.getValidIDAccess().getIDTerminalRuleCall());

            }

            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleValidID"


    // $ANTLR start "entryRuleXImportDeclaration"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4419:1: entryRuleXImportDeclaration returns [EObject current=null] : iv_ruleXImportDeclaration= ruleXImportDeclaration EOF ;
    public final EObject entryRuleXImportDeclaration() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleXImportDeclaration = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4420:2: (iv_ruleXImportDeclaration= ruleXImportDeclaration EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4421:2: iv_ruleXImportDeclaration= ruleXImportDeclaration EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getXImportDeclarationRule());
            }
            pushFollow(FOLLOW_ruleXImportDeclaration_in_entryRuleXImportDeclaration10707);
            iv_ruleXImportDeclaration=ruleXImportDeclaration();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleXImportDeclaration;
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleXImportDeclaration10717); if (state.failed) return current;

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
    // $ANTLR end "entryRuleXImportDeclaration"


    // $ANTLR start "ruleXImportDeclaration"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4428:1: ruleXImportDeclaration returns [EObject current=null] : (otherlv_0= 'import' ( ( ( (lv_static_1_0= 'static' ) ) ( (lv_extension_2_0= 'extension' ) )? ( ( ruleQualifiedNameInStaticImport ) ) ( ( (lv_wildcard_4_0= '*' ) ) | ( (lv_memberName_5_0= ruleValidID ) ) ) ) | ( ( ruleQualifiedName ) ) | ( (lv_importedNamespace_7_0= ruleQualifiedNameWithWildcard ) ) ) (otherlv_8= ';' )? ) ;
    public final EObject ruleXImportDeclaration() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_static_1_0=null;
        Token lv_extension_2_0=null;
        Token lv_wildcard_4_0=null;
        Token otherlv_8=null;
        AntlrDatatypeRuleToken lv_memberName_5_0 = null;

        AntlrDatatypeRuleToken lv_importedNamespace_7_0 = null;


         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4431:28: ( (otherlv_0= 'import' ( ( ( (lv_static_1_0= 'static' ) ) ( (lv_extension_2_0= 'extension' ) )? ( ( ruleQualifiedNameInStaticImport ) ) ( ( (lv_wildcard_4_0= '*' ) ) | ( (lv_memberName_5_0= ruleValidID ) ) ) ) | ( ( ruleQualifiedName ) ) | ( (lv_importedNamespace_7_0= ruleQualifiedNameWithWildcard ) ) ) (otherlv_8= ';' )? ) )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4432:1: (otherlv_0= 'import' ( ( ( (lv_static_1_0= 'static' ) ) ( (lv_extension_2_0= 'extension' ) )? ( ( ruleQualifiedNameInStaticImport ) ) ( ( (lv_wildcard_4_0= '*' ) ) | ( (lv_memberName_5_0= ruleValidID ) ) ) ) | ( ( ruleQualifiedName ) ) | ( (lv_importedNamespace_7_0= ruleQualifiedNameWithWildcard ) ) ) (otherlv_8= ';' )? )
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4432:1: (otherlv_0= 'import' ( ( ( (lv_static_1_0= 'static' ) ) ( (lv_extension_2_0= 'extension' ) )? ( ( ruleQualifiedNameInStaticImport ) ) ( ( (lv_wildcard_4_0= '*' ) ) | ( (lv_memberName_5_0= ruleValidID ) ) ) ) | ( ( ruleQualifiedName ) ) | ( (lv_importedNamespace_7_0= ruleQualifiedNameWithWildcard ) ) ) (otherlv_8= ';' )? )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4432:3: otherlv_0= 'import' ( ( ( (lv_static_1_0= 'static' ) ) ( (lv_extension_2_0= 'extension' ) )? ( ( ruleQualifiedNameInStaticImport ) ) ( ( (lv_wildcard_4_0= '*' ) ) | ( (lv_memberName_5_0= ruleValidID ) ) ) ) | ( ( ruleQualifiedName ) ) | ( (lv_importedNamespace_7_0= ruleQualifiedNameWithWildcard ) ) ) (otherlv_8= ';' )?
            {
            otherlv_0=(Token)match(input,73,FOLLOW_73_in_ruleXImportDeclaration10754); if (state.failed) return current;
            if ( state.backtracking==0 ) {

			newLeafNode(otherlv_0, grammarAccess.getXImportDeclarationAccess().getImportKeyword_0());

            }
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4436:1: ( ( ( (lv_static_1_0= 'static' ) ) ( (lv_extension_2_0= 'extension' ) )? ( ( ruleQualifiedNameInStaticImport ) ) ( ( (lv_wildcard_4_0= '*' ) ) | ( (lv_memberName_5_0= ruleValidID ) ) ) ) | ( ( ruleQualifiedName ) ) | ( (lv_importedNamespace_7_0= ruleQualifiedNameWithWildcard ) ) )
            int alt82=3;
            alt82 = dfa82.predict(input);
            switch (alt82) {
                case 1 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4436:2: ( ( (lv_static_1_0= 'static' ) ) ( (lv_extension_2_0= 'extension' ) )? ( ( ruleQualifiedNameInStaticImport ) ) ( ( (lv_wildcard_4_0= '*' ) ) | ( (lv_memberName_5_0= ruleValidID ) ) ) )
                    {
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4436:2: ( ( (lv_static_1_0= 'static' ) ) ( (lv_extension_2_0= 'extension' ) )? ( ( ruleQualifiedNameInStaticImport ) ) ( ( (lv_wildcard_4_0= '*' ) ) | ( (lv_memberName_5_0= ruleValidID ) ) ) )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4436:3: ( (lv_static_1_0= 'static' ) ) ( (lv_extension_2_0= 'extension' ) )? ( ( ruleQualifiedNameInStaticImport ) ) ( ( (lv_wildcard_4_0= '*' ) ) | ( (lv_memberName_5_0= ruleValidID ) ) )
                    {
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4436:3: ( (lv_static_1_0= 'static' ) )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4437:1: (lv_static_1_0= 'static' )
                    {
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4437:1: (lv_static_1_0= 'static' )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4438:3: lv_static_1_0= 'static'
                    {
                    lv_static_1_0=(Token)match(input,72,FOLLOW_72_in_ruleXImportDeclaration10774); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                              newLeafNode(lv_static_1_0, grammarAccess.getXImportDeclarationAccess().getStaticStaticKeyword_1_0_0_0());

                    }
                    if ( state.backtracking==0 ) {

			        if (current==null) {
			            current = createModelElement(grammarAccess.getXImportDeclarationRule());
			        }
					setWithLastConsumed(current, "static", true, "static");

                    }

                    }


                    }

                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4451:2: ( (lv_extension_2_0= 'extension' ) )?
                    int alt80=2;
                    int LA80_0 = input.LA(1);

                    if ( (LA80_0==74) ) {
                        alt80=1;
                    }
                    switch (alt80) {
                        case 1 :
                            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4452:1: (lv_extension_2_0= 'extension' )
                            {
                            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4452:1: (lv_extension_2_0= 'extension' )
                            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4453:3: lv_extension_2_0= 'extension'
                            {
                            lv_extension_2_0=(Token)match(input,74,FOLLOW_74_in_ruleXImportDeclaration10805); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                                      newLeafNode(lv_extension_2_0, grammarAccess.getXImportDeclarationAccess().getExtensionExtensionKeyword_1_0_1_0());

                            }
                            if ( state.backtracking==0 ) {

				        if (current==null) {
				            current = createModelElement(grammarAccess.getXImportDeclarationRule());
				        }
						setWithLastConsumed(current, "extension", true, "extension");

                            }

                            }


                            }
                            break;

                    }

                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4466:3: ( ( ruleQualifiedNameInStaticImport ) )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4467:1: ( ruleQualifiedNameInStaticImport )
                    {
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4467:1: ( ruleQualifiedNameInStaticImport )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4468:3: ruleQualifiedNameInStaticImport
                    {
                    if ( state.backtracking==0 ) {

					if (current==null) {
			            current = createModelElement(grammarAccess.getXImportDeclarationRule());
			        }

                    }
                    if ( state.backtracking==0 ) {

			        newCompositeNode(grammarAccess.getXImportDeclarationAccess().getImportedTypeJvmDeclaredTypeCrossReference_1_0_2_0());

                    }
                    pushFollow(FOLLOW_ruleQualifiedNameInStaticImport_in_ruleXImportDeclaration10842);
                    ruleQualifiedNameInStaticImport();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

			        afterParserOrEnumRuleCall();

                    }

                    }


                    }

                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4481:2: ( ( (lv_wildcard_4_0= '*' ) ) | ( (lv_memberName_5_0= ruleValidID ) ) )
                    int alt81=2;
                    int LA81_0 = input.LA(1);

                    if ( (LA81_0==48) ) {
                        alt81=1;
                    }
                    else if ( (LA81_0==RULE_ID) ) {
                        alt81=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 81, 0, input);

                        throw nvae;
                    }
                    switch (alt81) {
                        case 1 :
                            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4481:3: ( (lv_wildcard_4_0= '*' ) )
                            {
                            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4481:3: ( (lv_wildcard_4_0= '*' ) )
                            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4482:1: (lv_wildcard_4_0= '*' )
                            {
                            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4482:1: (lv_wildcard_4_0= '*' )
                            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4483:3: lv_wildcard_4_0= '*'
                            {
                            lv_wildcard_4_0=(Token)match(input,48,FOLLOW_48_in_ruleXImportDeclaration10861); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                                      newLeafNode(lv_wildcard_4_0, grammarAccess.getXImportDeclarationAccess().getWildcardAsteriskKeyword_1_0_3_0_0());

                            }
                            if ( state.backtracking==0 ) {

				        if (current==null) {
				            current = createModelElement(grammarAccess.getXImportDeclarationRule());
				        }
						setWithLastConsumed(current, "wildcard", true, "*");

                            }

                            }


                            }


                            }
                            break;
                        case 2 :
                            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4497:6: ( (lv_memberName_5_0= ruleValidID ) )
                            {
                            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4497:6: ( (lv_memberName_5_0= ruleValidID ) )
                            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4498:1: (lv_memberName_5_0= ruleValidID )
                            {
                            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4498:1: (lv_memberName_5_0= ruleValidID )
                            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4499:3: lv_memberName_5_0= ruleValidID
                            {
                            if ( state.backtracking==0 ) {

				        newCompositeNode(grammarAccess.getXImportDeclarationAccess().getMemberNameValidIDParserRuleCall_1_0_3_1_0());

                            }
                            pushFollow(FOLLOW_ruleValidID_in_ruleXImportDeclaration10901);
                            lv_memberName_5_0=ruleValidID();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

				        if (current==null) {
				            current = createModelElementForParent(grammarAccess.getXImportDeclarationRule());
				        }
						set(
							current,
							"memberName",
						lv_memberName_5_0,
						"ValidID");
				        afterParserOrEnumRuleCall();

                            }

                            }


                            }


                            }
                            break;

                    }


                    }


                    }
                    break;
                case 2 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4516:6: ( ( ruleQualifiedName ) )
                    {
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4516:6: ( ( ruleQualifiedName ) )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4517:1: ( ruleQualifiedName )
                    {
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4517:1: ( ruleQualifiedName )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4518:3: ruleQualifiedName
                    {
                    if ( state.backtracking==0 ) {

					if (current==null) {
			            current = createModelElement(grammarAccess.getXImportDeclarationRule());
			        }

                    }
                    if ( state.backtracking==0 ) {

			        newCompositeNode(grammarAccess.getXImportDeclarationAccess().getImportedTypeJvmDeclaredTypeCrossReference_1_1_0());

                    }
                    pushFollow(FOLLOW_ruleQualifiedName_in_ruleXImportDeclaration10932);
                    ruleQualifiedName();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

			        afterParserOrEnumRuleCall();

                    }

                    }


                    }


                    }
                    break;
                case 3 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4532:6: ( (lv_importedNamespace_7_0= ruleQualifiedNameWithWildcard ) )
                    {
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4532:6: ( (lv_importedNamespace_7_0= ruleQualifiedNameWithWildcard ) )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4533:1: (lv_importedNamespace_7_0= ruleQualifiedNameWithWildcard )
                    {
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4533:1: (lv_importedNamespace_7_0= ruleQualifiedNameWithWildcard )
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4534:3: lv_importedNamespace_7_0= ruleQualifiedNameWithWildcard
                    {
                    if ( state.backtracking==0 ) {

			        newCompositeNode(grammarAccess.getXImportDeclarationAccess().getImportedNamespaceQualifiedNameWithWildcardParserRuleCall_1_2_0());

                    }
                    pushFollow(FOLLOW_ruleQualifiedNameWithWildcard_in_ruleXImportDeclaration10959);
                    lv_importedNamespace_7_0=ruleQualifiedNameWithWildcard();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

			        if (current==null) {
			            current = createModelElementForParent(grammarAccess.getXImportDeclarationRule());
			        }
					set(
						current,
						"importedNamespace",
					lv_importedNamespace_7_0,
					"QualifiedNameWithWildcard");
			        afterParserOrEnumRuleCall();

                    }

                    }


                    }


                    }
                    break;

            }

            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4550:3: (otherlv_8= ';' )?
            int alt83=2;
            int LA83_0 = input.LA(1);

            if ( (LA83_0==66) ) {
                alt83=1;
            }
            switch (alt83) {
                case 1 :
                    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4550:5: otherlv_8= ';'
                    {
                    otherlv_8=(Token)match(input,66,FOLLOW_66_in_ruleXImportDeclaration10973); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

				newLeafNode(otherlv_8, grammarAccess.getXImportDeclarationAccess().getSemicolonKeyword_2());

                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleXImportDeclaration"


    // $ANTLR start "entryRuleQualifiedNameInStaticImport"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4562:1: entryRuleQualifiedNameInStaticImport returns [String current=null] : iv_ruleQualifiedNameInStaticImport= ruleQualifiedNameInStaticImport EOF ;
    public final String entryRuleQualifiedNameInStaticImport() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleQualifiedNameInStaticImport = null;


        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4563:2: (iv_ruleQualifiedNameInStaticImport= ruleQualifiedNameInStaticImport EOF )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4564:2: iv_ruleQualifiedNameInStaticImport= ruleQualifiedNameInStaticImport EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getQualifiedNameInStaticImportRule());
            }
            pushFollow(FOLLOW_ruleQualifiedNameInStaticImport_in_entryRuleQualifiedNameInStaticImport11012);
            iv_ruleQualifiedNameInStaticImport=ruleQualifiedNameInStaticImport();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleQualifiedNameInStaticImport.getText();
            }
            match(input,EOF,FOLLOW_EOF_in_entryRuleQualifiedNameInStaticImport11023); if (state.failed) return current;

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
    // $ANTLR end "entryRuleQualifiedNameInStaticImport"


    // $ANTLR start "ruleQualifiedNameInStaticImport"
    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4571:1: ruleQualifiedNameInStaticImport returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ValidID_0= ruleValidID kw= '.' )+ ;
    public final AntlrDatatypeRuleToken ruleQualifiedNameInStaticImport() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_ValidID_0 = null;


         enterRule();

        try {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4574:28: ( (this_ValidID_0= ruleValidID kw= '.' )+ )
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4575:1: (this_ValidID_0= ruleValidID kw= '.' )+
            {
            // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4575:1: (this_ValidID_0= ruleValidID kw= '.' )+
            int cnt84=0;
            loop84:
            do {
                int alt84=2;
                int LA84_0 = input.LA(1);

                if ( (LA84_0==RULE_ID) ) {
                    int LA84_2 = input.LA(2);

                    if ( (LA84_2==41) ) {
                        alt84=1;
                    }


                }


                switch (alt84) {
		case 1 :
		    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:4576:5: this_ValidID_0= ruleValidID kw= '.'
		    {
		    if ( state.backtracking==0 ) {

		              newCompositeNode(grammarAccess.getQualifiedNameInStaticImportAccess().getValidIDParserRuleCall_0());

		    }
		    pushFollow(FOLLOW_ruleValidID_in_ruleQualifiedNameInStaticImport11070);
		    this_ValidID_0=ruleValidID();

		    state._fsp--;
		    if (state.failed) return current;
		    if ( state.backtracking==0 ) {

				current.merge(this_ValidID_0);

		    }
		    if ( state.backtracking==0 ) {

		              afterParserOrEnumRuleCall();

		    }
		    kw=(Token)match(input,41,FOLLOW_41_in_ruleQualifiedNameInStaticImport11088); if (state.failed) return current;
		    if ( state.backtracking==0 ) {

		              current.merge(kw);
		              newLeafNode(kw, grammarAccess.getQualifiedNameInStaticImportAccess().getFullStopKeyword_1());

		    }

		    }
		    break;

		default :
		    if ( cnt84 >= 1 ) break loop84;
		    if (state.backtracking>0) {state.failed=true; return current;}
                        EarlyExitException eee =
                            new EarlyExitException(84, input);
                        throw eee;
                }
                cnt84++;
            } while (true);


            }

            if ( state.backtracking==0 ) {
               leaveRule();
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
    // $ANTLR end "ruleQualifiedNameInStaticImport"

    // $ANTLR start synpred1_InternalDSEL
    public final void synpred1_InternalDSEL_fragment() throws RecognitionException {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:586:3: ( ( () ( ( ruleOpCompare ) ) ) )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:586:4: ( () ( ( ruleOpCompare ) ) )
        {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:586:4: ( () ( ( ruleOpCompare ) ) )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:586:5: () ( ( ruleOpCompare ) )
        {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:586:5: ()
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:587:1:
        {
        }

        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:587:2: ( ( ruleOpCompare ) )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:588:1: ( ruleOpCompare )
        {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:588:1: ( ruleOpCompare )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:589:3: ruleOpCompare
        {
        pushFollow(FOLLOW_ruleOpCompare_in_synpred1_InternalDSEL1585);
        ruleOpCompare();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred1_InternalDSEL

    // $ANTLR start synpred2_InternalDSEL
    public final void synpred2_InternalDSEL_fragment() throws RecognitionException {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:660:3: ( ( () ( ( ruleOpOther ) ) ) )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:660:4: ( () ( ( ruleOpOther ) ) )
        {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:660:4: ( () ( ( ruleOpOther ) ) )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:660:5: () ( ( ruleOpOther ) )
        {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:660:5: ()
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:661:1:
        {
        }

        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:661:2: ( ( ruleOpOther ) )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:662:1: ( ruleOpOther )
        {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:662:1: ( ruleOpOther )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:663:3: ruleOpOther
        {
        pushFollow(FOLLOW_ruleOpOther_in_synpred2_InternalDSEL1756);
        ruleOpOther();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred2_InternalDSEL

    // $ANTLR start synpred3_InternalDSEL
    public final void synpred3_InternalDSEL_fragment() throws RecognitionException {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1215:3: ( ( () ( ( ruleOpMultiAssign ) ) ) )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1215:4: ( () ( ( ruleOpMultiAssign ) ) )
        {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1215:4: ( () ( ( ruleOpMultiAssign ) ) )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1215:5: () ( ( ruleOpMultiAssign ) )
        {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1215:5: ()
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1216:1:
        {
        }

        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1216:2: ( ( ruleOpMultiAssign ) )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1217:1: ( ruleOpMultiAssign )
        {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1217:1: ( ruleOpMultiAssign )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1218:3: ruleOpMultiAssign
        {
        pushFollow(FOLLOW_ruleOpMultiAssign_in_synpred3_InternalDSEL3206);
        ruleOpMultiAssign();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred3_InternalDSEL

    // $ANTLR start synpred4_InternalDSEL
    public final void synpred4_InternalDSEL_fragment() throws RecognitionException {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1382:3: ( ( () ( ( ruleOpOr ) ) ) )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1382:4: ( () ( ( ruleOpOr ) ) )
        {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1382:4: ( () ( ( ruleOpOr ) ) )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1382:5: () ( ( ruleOpOr ) )
        {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1382:5: ()
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1383:1:
        {
        }

        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1383:2: ( ( ruleOpOr ) )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1384:1: ( ruleOpOr )
        {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1384:1: ( ruleOpOr )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1385:3: ruleOpOr
        {
        pushFollow(FOLLOW_ruleOpOr_in_synpred4_InternalDSEL3641);
        ruleOpOr();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred4_InternalDSEL

    // $ANTLR start synpred5_InternalDSEL
    public final void synpred5_InternalDSEL_fragment() throws RecognitionException {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1456:3: ( ( () ( ( ruleOpAnd ) ) ) )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1456:4: ( () ( ( ruleOpAnd ) ) )
        {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1456:4: ( () ( ( ruleOpAnd ) ) )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1456:5: () ( ( ruleOpAnd ) )
        {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1456:5: ()
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1457:1:
        {
        }

        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1457:2: ( ( ruleOpAnd ) )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1458:1: ( ruleOpAnd )
        {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1458:1: ( ruleOpAnd )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1459:3: ruleOpAnd
        {
        pushFollow(FOLLOW_ruleOpAnd_in_synpred5_InternalDSEL3812);
        ruleOpAnd();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred5_InternalDSEL

    // $ANTLR start synpred6_InternalDSEL
    public final void synpred6_InternalDSEL_fragment() throws RecognitionException {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1530:3: ( ( () ( ( ruleOpEquality ) ) ) )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1530:4: ( () ( ( ruleOpEquality ) ) )
        {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1530:4: ( () ( ( ruleOpEquality ) ) )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1530:5: () ( ( ruleOpEquality ) )
        {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1530:5: ()
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1531:1:
        {
        }

        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1531:2: ( ( ruleOpEquality ) )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1532:1: ( ruleOpEquality )
        {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1532:1: ( ruleOpEquality )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1533:3: ruleOpEquality
        {
        pushFollow(FOLLOW_ruleOpEquality_in_synpred6_InternalDSEL3983);
        ruleOpEquality();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred6_InternalDSEL

    // $ANTLR start synpred7_InternalDSEL
    public final void synpred7_InternalDSEL_fragment() throws RecognitionException {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1604:3: ( ( () ( ( ruleOpAdd ) ) ) )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1604:4: ( () ( ( ruleOpAdd ) ) )
        {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1604:4: ( () ( ( ruleOpAdd ) ) )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1604:5: () ( ( ruleOpAdd ) )
        {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1604:5: ()
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1605:1:
        {
        }

        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1605:2: ( ( ruleOpAdd ) )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1606:1: ( ruleOpAdd )
        {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1606:1: ( ruleOpAdd )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1607:3: ruleOpAdd
        {
        pushFollow(FOLLOW_ruleOpAdd_in_synpred7_InternalDSEL4154);
        ruleOpAdd();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred7_InternalDSEL

    // $ANTLR start synpred8_InternalDSEL
    public final void synpred8_InternalDSEL_fragment() throws RecognitionException {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1712:3: ( ( () ( ( ruleOpMulti ) ) ) )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1712:4: ( () ( ( ruleOpMulti ) ) )
        {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1712:4: ( () ( ( ruleOpMulti ) ) )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1712:5: () ( ( ruleOpMulti ) )
        {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1712:5: ()
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1713:1:
        {
        }

        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1713:2: ( ( ruleOpMulti ) )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1714:1: ( ruleOpMulti )
        {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1714:1: ( ruleOpMulti )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1715:3: ruleOpMulti
        {
        pushFollow(FOLLOW_ruleOpMulti_in_synpred8_InternalDSEL4434);
        ruleOpMulti();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred8_InternalDSEL

    // $ANTLR start synpred9_InternalDSEL
    public final void synpred9_InternalDSEL_fragment() throws RecognitionException {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1834:3: ( ( () 'as' ) )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1834:4: ( () 'as' )
        {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1834:4: ( () 'as' )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1834:5: () 'as'
        {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1834:5: ()
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1835:1:
        {
        }

        match(input,52,FOLLOW_52_in_synpred9_InternalDSEL4747); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred9_InternalDSEL

    // $ANTLR start synpred10_InternalDSEL
    public final void synpred10_InternalDSEL_fragment() throws RecognitionException {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1894:2: ( ( () ( ( ruleOpPostfix ) ) ) )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1894:3: ( () ( ( ruleOpPostfix ) ) )
        {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1894:3: ( () ( ( ruleOpPostfix ) ) )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1894:4: () ( ( ruleOpPostfix ) )
        {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1894:4: ()
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1895:1:
        {
        }

        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1895:2: ( ( ruleOpPostfix ) )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1896:1: ( ruleOpPostfix )
        {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1896:1: ( ruleOpPostfix )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1897:3: ruleOpPostfix
        {
        pushFollow(FOLLOW_ruleOpPostfix_in_synpred10_InternalDSEL4904);
        ruleOpPostfix();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred10_InternalDSEL

    // $ANTLR start synpred11_InternalDSEL
    public final void synpred11_InternalDSEL_fragment() throws RecognitionException {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1984:4: ( ( () ( '.' | ( ( '::' ) ) ) ( ( ruleFeatureCallID ) ) ruleOpSingleAssign ) )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1984:5: ( () ( '.' | ( ( '::' ) ) ) ( ( ruleFeatureCallID ) ) ruleOpSingleAssign )
        {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1984:5: ( () ( '.' | ( ( '::' ) ) ) ( ( ruleFeatureCallID ) ) ruleOpSingleAssign )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1984:6: () ( '.' | ( ( '::' ) ) ) ( ( ruleFeatureCallID ) ) ruleOpSingleAssign
        {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1984:6: ()
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1985:1:
        {
        }

        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1985:2: ( '.' | ( ( '::' ) ) )
        int alt85=2;
        int LA85_0 = input.LA(1);

        if ( (LA85_0==41) ) {
            alt85=1;
        }
        else if ( (LA85_0==55) ) {
            alt85=2;
        }
        else {
            if (state.backtracking>0) {state.failed=true; return ;}
            NoViableAltException nvae =
                new NoViableAltException("", 85, 0, input);

            throw nvae;
        }
        switch (alt85) {
            case 1 :
                // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1985:4: '.'
                {
                match(input,41,FOLLOW_41_in_synpred11_InternalDSEL5159); if (state.failed) return ;

                }
                break;
            case 2 :
                // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1987:6: ( ( '::' ) )
                {
                // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1987:6: ( ( '::' ) )
                // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1988:1: ( '::' )
                {
                // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1988:1: ( '::' )
                // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1989:2: '::'
                {
                match(input,55,FOLLOW_55_in_synpred11_InternalDSEL5173); if (state.failed) return ;

                }


                }


                }
                break;

        }

        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1993:3: ( ( ruleFeatureCallID ) )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1994:1: ( ruleFeatureCallID )
        {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1994:1: ( ruleFeatureCallID )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:1995:3: ruleFeatureCallID
        {
        pushFollow(FOLLOW_ruleFeatureCallID_in_synpred11_InternalDSEL5189);
        ruleFeatureCallID();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        pushFollow(FOLLOW_ruleOpSingleAssign_in_synpred11_InternalDSEL5195);
        ruleOpSingleAssign();

        state._fsp--;
        if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred11_InternalDSEL

    // $ANTLR start synpred12_InternalDSEL
    public final void synpred12_InternalDSEL_fragment() throws RecognitionException {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2065:8: ( ( () ( '.' | ( ( '?.' ) ) | ( ( '::' ) ) ) ) )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2065:9: ( () ( '.' | ( ( '?.' ) ) | ( ( '::' ) ) ) )
        {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2065:9: ( () ( '.' | ( ( '?.' ) ) | ( ( '::' ) ) ) )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2065:10: () ( '.' | ( ( '?.' ) ) | ( ( '::' ) ) )
        {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2065:10: ()
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2066:1:
        {
        }

        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2066:2: ( '.' | ( ( '?.' ) ) | ( ( '::' ) ) )
        int alt86=3;
        switch ( input.LA(1) ) {
        case 41:
            {
            alt86=1;
            }
            break;
        case 56:
            {
            alt86=2;
            }
            break;
        case 55:
            {
            alt86=3;
            }
            break;
        default:
            if (state.backtracking>0) {state.failed=true; return ;}
            NoViableAltException nvae =
                new NoViableAltException("", 86, 0, input);

            throw nvae;
        }

        switch (alt86) {
            case 1 :
                // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2066:4: '.'
                {
                match(input,41,FOLLOW_41_in_synpred12_InternalDSEL5337); if (state.failed) return ;

                }
                break;
            case 2 :
                // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2068:6: ( ( '?.' ) )
                {
                // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2068:6: ( ( '?.' ) )
                // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2069:1: ( '?.' )
                {
                // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2069:1: ( '?.' )
                // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2070:2: '?.'
                {
                match(input,56,FOLLOW_56_in_synpred12_InternalDSEL5351); if (state.failed) return ;

                }


                }


                }
                break;
            case 3 :
                // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2075:6: ( ( '::' ) )
                {
                // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2075:6: ( ( '::' ) )
                // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2076:1: ( '::' )
                {
                // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2076:1: ( '::' )
                // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2077:2: '::'
                {
                match(input,55,FOLLOW_55_in_synpred12_InternalDSEL5371); if (state.failed) return ;

                }


                }


                }
                break;

        }


        }


        }
    }
    // $ANTLR end synpred12_InternalDSEL

    // $ANTLR start synpred13_InternalDSEL
    public final void synpred13_InternalDSEL_fragment() throws RecognitionException {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2186:4: ( ( '(' ) )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2187:1: ( '(' )
        {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2187:1: ( '(' )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2188:2: '('
        {
        match(input,58,FOLLOW_58_in_synpred13_InternalDSEL5598); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred13_InternalDSEL

    // $ANTLR start synpred14_InternalDSEL
    public final void synpred14_InternalDSEL_fragment() throws RecognitionException {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2207:4: ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2207:5: ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) )
        {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2207:5: ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2207:6: () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) )
        {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2207:6: ()
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2208:1:
        {
        }

        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2208:2: ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )?
        int alt88=2;
        int LA88_0 = input.LA(1);

        if ( (LA88_0==RULE_ID||LA88_0==58||LA88_0==77) ) {
            alt88=1;
        }
        switch (alt88) {
            case 1 :
                // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2208:3: ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )*
                {
                // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2208:3: ( ( ruleJvmFormalParameter ) )
                // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2209:1: ( ruleJvmFormalParameter )
                {
                // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2209:1: ( ruleJvmFormalParameter )
                // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2210:1: ruleJvmFormalParameter
                {
                pushFollow(FOLLOW_ruleJvmFormalParameter_in_synpred14_InternalDSEL5650);
                ruleJvmFormalParameter();

                state._fsp--;
                if (state.failed) return ;

                }


                }

                // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2212:2: ( ',' ( ( ruleJvmFormalParameter ) ) )*
                loop87:
                do {
                    int alt87=2;
                    int LA87_0 = input.LA(1);

                    if ( (LA87_0==57) ) {
                        alt87=1;
                    }


                    switch (alt87) {
			case 1 :
			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2212:4: ',' ( ( ruleJvmFormalParameter ) )
			    {
			    match(input,57,FOLLOW_57_in_synpred14_InternalDSEL5657); if (state.failed) return ;
			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2213:1: ( ( ruleJvmFormalParameter ) )
			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2214:1: ( ruleJvmFormalParameter )
			    {
			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2214:1: ( ruleJvmFormalParameter )
			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2215:1: ruleJvmFormalParameter
			    {
			    pushFollow(FOLLOW_ruleJvmFormalParameter_in_synpred14_InternalDSEL5664);
			    ruleJvmFormalParameter();

			    state._fsp--;
			    if (state.failed) return ;

			    }


			    }


			    }
			    break;

			default :
			    break loop87;
                    }
                } while (true);


                }
                break;

        }

        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2217:6: ( ( '|' ) )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2218:1: ( '|' )
        {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2218:1: ( '|' )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2219:2: '|'
        {
        match(input,65,FOLLOW_65_in_synpred14_InternalDSEL5678); if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred14_InternalDSEL

    // $ANTLR start synpred15_InternalDSEL
    public final void synpred15_InternalDSEL_fragment() throws RecognitionException {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2286:4: ( ( () '[' ) )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2286:5: ( () '[' )
        {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2286:5: ( () '[' )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2286:6: () '['
        {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2286:6: ()
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2287:1:
        {
        }

        match(input,63,FOLLOW_63_in_synpred15_InternalDSEL5798); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred15_InternalDSEL

    // $ANTLR start synpred17_InternalDSEL
    public final void synpred17_InternalDSEL_fragment() throws RecognitionException {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2499:4: ( ( ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2499:5: ( ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) )
        {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2499:5: ( ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2499:6: ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) )
        {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2499:6: ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )?
        int alt90=2;
        int LA90_0 = input.LA(1);

        if ( (LA90_0==RULE_ID||LA90_0==58||LA90_0==77) ) {
            alt90=1;
        }
        switch (alt90) {
            case 1 :
                // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2499:7: ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )*
                {
                // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2499:7: ( ( ruleJvmFormalParameter ) )
                // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2500:1: ( ruleJvmFormalParameter )
                {
                // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2500:1: ( ruleJvmFormalParameter )
                // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2501:1: ruleJvmFormalParameter
                {
                pushFollow(FOLLOW_ruleJvmFormalParameter_in_synpred17_InternalDSEL6298);
                ruleJvmFormalParameter();

                state._fsp--;
                if (state.failed) return ;

                }


                }

                // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2503:2: ( ',' ( ( ruleJvmFormalParameter ) ) )*
                loop89:
                do {
                    int alt89=2;
                    int LA89_0 = input.LA(1);

                    if ( (LA89_0==57) ) {
                        alt89=1;
                    }


                    switch (alt89) {
			case 1 :
			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2503:4: ',' ( ( ruleJvmFormalParameter ) )
			    {
			    match(input,57,FOLLOW_57_in_synpred17_InternalDSEL6305); if (state.failed) return ;
			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2504:1: ( ( ruleJvmFormalParameter ) )
			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2505:1: ( ruleJvmFormalParameter )
			    {
			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2505:1: ( ruleJvmFormalParameter )
			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2506:1: ruleJvmFormalParameter
			    {
			    pushFollow(FOLLOW_ruleJvmFormalParameter_in_synpred17_InternalDSEL6312);
			    ruleJvmFormalParameter();

			    state._fsp--;
			    if (state.failed) return ;

			    }


			    }


			    }
			    break;

			default :
			    break loop89;
                    }
                } while (true);


                }
                break;

        }

        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2508:6: ( ( '|' ) )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2509:1: ( '|' )
        {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2509:1: ( '|' )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2510:2: '|'
        {
        match(input,65,FOLLOW_65_in_synpred17_InternalDSEL6326); if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred17_InternalDSEL

    // $ANTLR start synpred19_InternalDSEL
    public final void synpred19_InternalDSEL_fragment() throws RecognitionException {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2869:4: ( 'else' )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2869:6: 'else'
        {
        match(input,68,FOLLOW_68_in_synpred19_InternalDSEL7109); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred19_InternalDSEL

    // $ANTLR start synpred20_InternalDSEL
    public final void synpred20_InternalDSEL_fragment() throws RecognitionException {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2989:4: ( ( ( ( ruleJvmTypeReference ) ) ( ( ruleValidID ) ) ) )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2989:5: ( ( ( ruleJvmTypeReference ) ) ( ( ruleValidID ) ) )
        {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2989:5: ( ( ( ruleJvmTypeReference ) ) ( ( ruleValidID ) ) )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2989:6: ( ( ruleJvmTypeReference ) ) ( ( ruleValidID ) )
        {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2989:6: ( ( ruleJvmTypeReference ) )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2990:1: ( ruleJvmTypeReference )
        {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2990:1: ( ruleJvmTypeReference )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2991:1: ruleJvmTypeReference
        {
        pushFollow(FOLLOW_ruleJvmTypeReference_in_synpred20_InternalDSEL7418);
        ruleJvmTypeReference();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2993:2: ( ( ruleValidID ) )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2994:1: ( ruleValidID )
        {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2994:1: ( ruleValidID )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:2995:1: ruleValidID
        {
        pushFollow(FOLLOW_ruleValidID_in_synpred20_InternalDSEL7427);
        ruleValidID();

        state._fsp--;
        if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred20_InternalDSEL

    // $ANTLR start synpred21_InternalDSEL
    public final void synpred21_InternalDSEL_fragment() throws RecognitionException {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3278:4: ( ( '(' ) )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3279:1: ( '(' )
        {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3279:1: ( '(' )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3280:2: '('
        {
        match(input,58,FOLLOW_58_in_synpred21_InternalDSEL7965); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred21_InternalDSEL

    // $ANTLR start synpred22_InternalDSEL
    public final void synpred22_InternalDSEL_fragment() throws RecognitionException {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3299:4: ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3299:5: ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) )
        {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3299:5: ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3299:6: () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) )
        {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3299:6: ()
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3300:1:
        {
        }

        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3300:2: ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )?
        int alt94=2;
        int LA94_0 = input.LA(1);

        if ( (LA94_0==RULE_ID||LA94_0==58||LA94_0==77) ) {
            alt94=1;
        }
        switch (alt94) {
            case 1 :
                // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3300:3: ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )*
                {
                // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3300:3: ( ( ruleJvmFormalParameter ) )
                // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3301:1: ( ruleJvmFormalParameter )
                {
                // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3301:1: ( ruleJvmFormalParameter )
                // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3302:1: ruleJvmFormalParameter
                {
                pushFollow(FOLLOW_ruleJvmFormalParameter_in_synpred22_InternalDSEL8017);
                ruleJvmFormalParameter();

                state._fsp--;
                if (state.failed) return ;

                }


                }

                // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3304:2: ( ',' ( ( ruleJvmFormalParameter ) ) )*
                loop93:
                do {
                    int alt93=2;
                    int LA93_0 = input.LA(1);

                    if ( (LA93_0==57) ) {
                        alt93=1;
                    }


                    switch (alt93) {
			case 1 :
			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3304:4: ',' ( ( ruleJvmFormalParameter ) )
			    {
			    match(input,57,FOLLOW_57_in_synpred22_InternalDSEL8024); if (state.failed) return ;
			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3305:1: ( ( ruleJvmFormalParameter ) )
			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3306:1: ( ruleJvmFormalParameter )
			    {
			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3306:1: ( ruleJvmFormalParameter )
			    // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3307:1: ruleJvmFormalParameter
			    {
			    pushFollow(FOLLOW_ruleJvmFormalParameter_in_synpred22_InternalDSEL8031);
			    ruleJvmFormalParameter();

			    state._fsp--;
			    if (state.failed) return ;

			    }


			    }


			    }
			    break;

			default :
			    break loop93;
                    }
                } while (true);


                }
                break;

        }

        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3309:6: ( ( '|' ) )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3310:1: ( '|' )
        {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3310:1: ( '|' )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3311:2: '|'
        {
        match(input,65,FOLLOW_65_in_synpred22_InternalDSEL8045); if (state.failed) return ;

        }


        }


        }


        }
    }
    // $ANTLR end synpred22_InternalDSEL

    // $ANTLR start synpred23_InternalDSEL
    public final void synpred23_InternalDSEL_fragment() throws RecognitionException {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3378:4: ( ( () '[' ) )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3378:5: ( () '[' )
        {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3378:5: ( () '[' )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3378:6: () '['
        {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3378:6: ()
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3379:1:
        {
        }

        match(input,63,FOLLOW_63_in_synpred23_InternalDSEL8165); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred23_InternalDSEL

    // $ANTLR start synpred25_InternalDSEL
    public final void synpred25_InternalDSEL_fragment() throws RecognitionException {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3658:3: ( '.' )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3659:2: '.'
        {
        match(input,41,FOLLOW_41_in_synpred25_InternalDSEL8874); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred25_InternalDSEL

    // $ANTLR start synpred26_InternalDSEL
    public final void synpred26_InternalDSEL_fragment() throws RecognitionException {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3709:2: ( ( () ruleArrayBrackets ) )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3709:3: ( () ruleArrayBrackets )
        {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3709:3: ( () ruleArrayBrackets )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3709:4: () ruleArrayBrackets
        {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3709:4: ()
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3710:1:
        {
        }

        pushFollow(FOLLOW_ruleArrayBrackets_in_synpred26_InternalDSEL9028);
        ruleArrayBrackets();

        state._fsp--;
        if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred26_InternalDSEL

    // $ANTLR start synpred27_InternalDSEL
    public final void synpred27_InternalDSEL_fragment() throws RecognitionException {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3894:4: ( '<' )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3894:6: '<'
        {
        match(input,23,FOLLOW_23_in_synpred27_InternalDSEL9480); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred27_InternalDSEL

    // $ANTLR start synpred28_InternalDSEL
    public final void synpred28_InternalDSEL_fragment() throws RecognitionException {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3943:3: ( ( () '.' ) )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3943:4: ( () '.' )
        {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3943:4: ( () '.' )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3943:5: () '.'
        {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3943:5: ()
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3944:1:
        {
        }

        match(input,41,FOLLOW_41_in_synpred28_InternalDSEL9575); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred28_InternalDSEL

    // $ANTLR start synpred29_InternalDSEL
    public final void synpred29_InternalDSEL_fragment() throws RecognitionException {
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3970:4: ( '<' )
        // ../com.odcgroup.workbench.el/src-gen/com/odcgroup/workbench/el/parser/antlr/internal/InternalDSEL.g:3970:6: '<'
        {
        match(input,23,FOLLOW_23_in_synpred29_InternalDSEL9632); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred29_InternalDSEL

    // Delegated rules

    public final boolean synpred3_InternalDSEL() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred3_InternalDSEL_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred11_InternalDSEL() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred11_InternalDSEL_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred23_InternalDSEL() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred23_InternalDSEL_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred6_InternalDSEL() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred6_InternalDSEL_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred15_InternalDSEL() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred15_InternalDSEL_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred28_InternalDSEL() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred28_InternalDSEL_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred17_InternalDSEL() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred17_InternalDSEL_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred4_InternalDSEL() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred4_InternalDSEL_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred1_InternalDSEL() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred1_InternalDSEL_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred26_InternalDSEL() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred26_InternalDSEL_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred14_InternalDSEL() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred14_InternalDSEL_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred25_InternalDSEL() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred25_InternalDSEL_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred8_InternalDSEL() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred8_InternalDSEL_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred27_InternalDSEL() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred27_InternalDSEL_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred5_InternalDSEL() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred5_InternalDSEL_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred21_InternalDSEL() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred21_InternalDSEL_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred13_InternalDSEL() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred13_InternalDSEL_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred2_InternalDSEL() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred2_InternalDSEL_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred19_InternalDSEL() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred19_InternalDSEL_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred22_InternalDSEL() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred22_InternalDSEL_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred9_InternalDSEL() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred9_InternalDSEL_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred7_InternalDSEL() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred7_InternalDSEL_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred20_InternalDSEL() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred20_InternalDSEL_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred29_InternalDSEL() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred29_InternalDSEL_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred10_InternalDSEL() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred10_InternalDSEL_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred12_InternalDSEL() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred12_InternalDSEL_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA1 dfa1 = new DFA1(this);
    protected DFA17 dfa17 = new DFA17(this);
    protected DFA37 dfa37 = new DFA37(this);
    protected DFA36 dfa36 = new DFA36(this);
    protected DFA46 dfa46 = new DFA46(this);
    protected DFA61 dfa61 = new DFA61(this);
    protected DFA60 dfa60 = new DFA60(this);
    protected DFA75 dfa75 = new DFA75(this);
    protected DFA73 dfa73 = new DFA73(this);
    protected DFA82 dfa82 = new DFA82(this);
    static final String DFA1_eotS =
        "\15\uffff";
    static final String DFA1_eofS =
        "\4\uffff\1\5\2\uffff\1\5\1\uffff\1\12\1\uffff\1\12\1\uffff";
    static final String DFA1_minS =
        "\1\4\3\uffff\1\4\1\uffff\4\4\1\uffff\1\4\1\uffff";
    static final String DFA1_maxS =
        "\1\50\3\uffff\1\113\1\uffff\4\113\1\uffff\1\113\1\uffff";
    static final String DFA1_acceptS =
        "\1\uffff\1\1\1\2\1\3\1\uffff\1\4\4\uffff\1\5\1\uffff\1\6";
    static final String DFA1_specialS =
        "\15\uffff}>";
    static final String[] DFA1_transitionS = {
            "\1\4\1\5\1\3\34\uffff\2\2\4\1",
            "",
            "",
            "",
            "\4\5\10\uffff\31\5\1\6\1\uffff\21\5\2\uffff\1\5\1\uffff\1"+
            "\5\1\uffff\12\5",
            "",
            "\1\7\1\5\1\uffff\1\5\17\uffff\1\5\57\uffff\5\5",
            "\4\5\10\uffff\31\5\1\10\1\uffff\21\5\2\uffff\1\5\1\uffff\1"+
            "\5\1\uffff\12\5",
            "\1\11\2\uffff\1\5\17\uffff\1\5\57\uffff\5\5",
            "\1\13\3\12\10\uffff\32\12\1\uffff\21\12\2\uffff\1\12\1\uffff"+
            "\1\12\1\uffff\12\12",
            "",
            "\4\12\10\uffff\32\12\1\14\5\uffff\11\12\1\uffff\1\12\5\uffff"+
            "\1\12\1\uffff\2\12\1\uffff\7\12",
            ""
    };

    static final short[] DFA1_eot = DFA.unpackEncodedString(DFA1_eotS);
    static final short[] DFA1_eof = DFA.unpackEncodedString(DFA1_eofS);
    static final char[] DFA1_min = DFA.unpackEncodedStringToUnsignedChars(DFA1_minS);
    static final char[] DFA1_max = DFA.unpackEncodedStringToUnsignedChars(DFA1_maxS);
    static final short[] DFA1_accept = DFA.unpackEncodedString(DFA1_acceptS);
    static final short[] DFA1_special = DFA.unpackEncodedString(DFA1_specialS);
    static final short[][] DFA1_transition;

    static {
        int numStates = DFA1_transitionS.length;
        DFA1_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA1_transition[i] = DFA.unpackEncodedString(DFA1_transitionS[i]);
        }
    }

    class DFA1 extends DFA {

        public DFA1(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 1;
            this.eot = DFA1_eot;
            this.eof = DFA1_eof;
            this.min = DFA1_min;
            this.max = DFA1_max;
            this.accept = DFA1_accept;
            this.special = DFA1_special;
            this.transition = DFA1_transition;
        }
        public String getDescription() {
            return "110:1: (this_XBooleanLiteral_0= ruleXBooleanLiteral | this_XNullLiteral_1= ruleXNullLiteral | this_XStringLiteral_2= ruleXStringLiteral | this_DecimalLiteral_3= ruleDecimalLiteral | this_DateLiteral_4= ruleDateLiteral | this_DateTimeLiteral_5= ruleDateTimeLiteral )";
        }
    }
    static final String DFA17_eotS =
        "\12\uffff";
    static final String DFA17_eofS =
        "\1\10\11\uffff";
    static final String DFA17_minS =
        "\1\4\7\0\2\uffff";
    static final String DFA17_maxS =
        "\1\113\7\0\2\uffff";
    static final String DFA17_acceptS =
        "\10\uffff\1\2\1\1";
    static final String DFA17_specialS =
        "\1\uffff\1\0\1\2\1\3\1\5\1\6\1\1\1\4\2\uffff}>";
    static final String[] DFA17_transitionS = {
            "\4\10\10\uffff\6\10\1\7\1\6\22\10\1\uffff\1\1\1\2\1\3\1\4\1"+
            "\5\14\10\2\uffff\1\10\1\uffff\1\10\1\uffff\12\10",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            ""
    };

    static final short[] DFA17_eot = DFA.unpackEncodedString(DFA17_eotS);
    static final short[] DFA17_eof = DFA.unpackEncodedString(DFA17_eofS);
    static final char[] DFA17_min = DFA.unpackEncodedStringToUnsignedChars(DFA17_minS);
    static final char[] DFA17_max = DFA.unpackEncodedStringToUnsignedChars(DFA17_maxS);
    static final short[] DFA17_accept = DFA.unpackEncodedString(DFA17_acceptS);
    static final short[] DFA17_special = DFA.unpackEncodedString(DFA17_specialS);
    static final short[][] DFA17_transition;

    static {
        int numStates = DFA17_transitionS.length;
        DFA17_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA17_transition[i] = DFA.unpackEncodedString(DFA17_transitionS[i]);
        }
    }

    class DFA17 extends DFA {

        public DFA17(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 17;
            this.eot = DFA17_eot;
            this.eof = DFA17_eof;
            this.min = DFA17_min;
            this.max = DFA17_max;
            this.accept = DFA17_accept;
            this.special = DFA17_special;
            this.transition = DFA17_transition;
        }
        public String getDescription() {
            return "1215:1: ( ( ( ( () ( ( ruleOpMultiAssign ) ) ) )=> ( () ( ( ruleOpMultiAssign ) ) ) ) ( (lv_rightOperand_7_0= ruleXAssignment ) ) )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
		int _s = s;
            switch ( s ) {
                    case 0 :
                        int LA17_1 = input.LA(1);


                        int index17_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalDSEL()) ) {s = 9;}

                        else if ( (true) ) {s = 8;}


                        input.seek(index17_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 :
                        int LA17_6 = input.LA(1);


                        int index17_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalDSEL()) ) {s = 9;}

                        else if ( (true) ) {s = 8;}


                        input.seek(index17_6);
                        if ( s>=0 ) return s;
                        break;
                    case 2 :
                        int LA17_2 = input.LA(1);


                        int index17_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalDSEL()) ) {s = 9;}

                        else if ( (true) ) {s = 8;}


                        input.seek(index17_2);
                        if ( s>=0 ) return s;
                        break;
                    case 3 :
                        int LA17_3 = input.LA(1);


                        int index17_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalDSEL()) ) {s = 9;}

                        else if ( (true) ) {s = 8;}


                        input.seek(index17_3);
                        if ( s>=0 ) return s;
                        break;
                    case 4 :
                        int LA17_7 = input.LA(1);


                        int index17_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalDSEL()) ) {s = 9;}

                        else if ( (true) ) {s = 8;}


                        input.seek(index17_7);
                        if ( s>=0 ) return s;
                        break;
                    case 5 :
                        int LA17_4 = input.LA(1);


                        int index17_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalDSEL()) ) {s = 9;}

                        else if ( (true) ) {s = 8;}


                        input.seek(index17_4);
                        if ( s>=0 ) return s;
                        break;
                    case 6 :
                        int LA17_5 = input.LA(1);


                        int index17_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalDSEL()) ) {s = 9;}

                        else if ( (true) ) {s = 8;}


                        input.seek(index17_5);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 17, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA37_eotS =
        "\77\uffff";
    static final String DFA37_eofS =
        "\1\2\76\uffff";
    static final String DFA37_minS =
        "\1\4\1\0\75\uffff";
    static final String DFA37_maxS =
        "\1\113\1\0\75\uffff";
    static final String DFA37_acceptS =
        "\2\uffff\1\2\73\uffff\1\1";
    static final String DFA37_specialS =
        "\1\uffff\1\0\75\uffff}>";
    static final String[] DFA37_transitionS = {
            "\4\2\10\uffff\32\2\1\uffff\17\2\1\1\1\2\2\uffff\3\2\1\uffff"+
            "\12\2",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA37_eot = DFA.unpackEncodedString(DFA37_eotS);
    static final short[] DFA37_eof = DFA.unpackEncodedString(DFA37_eofS);
    static final char[] DFA37_min = DFA.unpackEncodedStringToUnsignedChars(DFA37_minS);
    static final char[] DFA37_max = DFA.unpackEncodedStringToUnsignedChars(DFA37_maxS);
    static final short[] DFA37_accept = DFA.unpackEncodedString(DFA37_acceptS);
    static final short[] DFA37_special = DFA.unpackEncodedString(DFA37_specialS);
    static final short[][] DFA37_transition;

    static {
        int numStates = DFA37_transitionS.length;
        DFA37_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA37_transition[i] = DFA.unpackEncodedString(DFA37_transitionS[i]);
        }
    }

    class DFA37 extends DFA {

        public DFA37(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 37;
            this.eot = DFA37_eot;
            this.eof = DFA37_eof;
            this.min = DFA37_min;
            this.max = DFA37_max;
            this.accept = DFA37_accept;
            this.special = DFA37_special;
            this.transition = DFA37_transition;
        }
        public String getDescription() {
            return "2186:2: ( ( ( ( '(' ) )=> (lv_explicitOperationCall_17_0= '(' ) ) ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_memberCallArguments_18_0= ruleXShortClosure ) ) | ( ( (lv_memberCallArguments_19_0= ruleXExpression ) ) (otherlv_20= ',' ( (lv_memberCallArguments_21_0= ruleXExpression ) ) )* ) )? otherlv_22= ')' )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
		int _s = s;
            switch ( s ) {
                    case 0 :
                        int LA37_1 = input.LA(1);


                        int index37_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred13_InternalDSEL()) ) {s = 62;}

                        else if ( (true) ) {s = 2;}


                        input.seek(index37_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 37, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA36_eotS =
        "\32\uffff";
    static final String DFA36_eofS =
        "\32\uffff";
    static final String DFA36_minS =
        "\1\4\2\0\27\uffff";
    static final String DFA36_maxS =
        "\1\115\2\0\27\uffff";
    static final String DFA36_acceptS =
        "\3\uffff\2\1\1\2\23\uffff\1\3";
    static final String DFA36_specialS =
        "\1\0\1\1\1\2\27\uffff}>";
    static final String[] DFA36_transitionS = {
            "\3\5\1\1\17\uffff\1\5\2\uffff\4\5\5\uffff\6\5\21\uffff\1\2"+
            "\1\31\5\uffff\1\4\1\uffff\1\5\3\uffff\5\5\1\uffff\1\3",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA36_eot = DFA.unpackEncodedString(DFA36_eotS);
    static final short[] DFA36_eof = DFA.unpackEncodedString(DFA36_eofS);
    static final char[] DFA36_min = DFA.unpackEncodedStringToUnsignedChars(DFA36_minS);
    static final char[] DFA36_max = DFA.unpackEncodedStringToUnsignedChars(DFA36_maxS);
    static final short[] DFA36_accept = DFA.unpackEncodedString(DFA36_acceptS);
    static final short[] DFA36_special = DFA.unpackEncodedString(DFA36_specialS);
    static final short[][] DFA36_transition;

    static {
        int numStates = DFA36_transitionS.length;
        DFA36_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA36_transition[i] = DFA.unpackEncodedString(DFA36_transitionS[i]);
        }
    }

    class DFA36 extends DFA {

        public DFA36(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 36;
            this.eot = DFA36_eot;
            this.eof = DFA36_eof;
            this.min = DFA36_min;
            this.max = DFA36_max;
            this.accept = DFA36_accept;
            this.special = DFA36_special;
            this.transition = DFA36_transition;
        }
        public String getDescription() {
            return "2207:2: ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_memberCallArguments_18_0= ruleXShortClosure ) ) | ( ( (lv_memberCallArguments_19_0= ruleXExpression ) ) (otherlv_20= ',' ( (lv_memberCallArguments_21_0= ruleXExpression ) ) )* ) )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
		int _s = s;
            switch ( s ) {
                    case 0 :
                        int LA36_0 = input.LA(1);


                        int index36_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA36_0==RULE_ID) ) {s = 1;}

                        else if ( (LA36_0==58) ) {s = 2;}

                        else if ( (LA36_0==77) && (synpred14_InternalDSEL())) {s = 3;}

                        else if ( (LA36_0==65) && (synpred14_InternalDSEL())) {s = 4;}

                        else if ( ((LA36_0>=RULE_INT && LA36_0<=RULE_STRING)||LA36_0==23||(LA36_0>=26 && LA36_0<=29)||(LA36_0>=35 && LA36_0<=40)||LA36_0==67||(LA36_0>=71 && LA36_0<=75)) ) {s = 5;}

                        else if ( (LA36_0==59) ) {s = 25;}


                        input.seek(index36_0);
                        if ( s>=0 ) return s;
                        break;
                    case 1 :
                        int LA36_1 = input.LA(1);


                        int index36_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred14_InternalDSEL()) ) {s = 4;}

                        else if ( (true) ) {s = 5;}


                        input.seek(index36_1);
                        if ( s>=0 ) return s;
                        break;
                    case 2 :
                        int LA36_2 = input.LA(1);


                        int index36_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred14_InternalDSEL()) ) {s = 4;}

                        else if ( (true) ) {s = 5;}


                        input.seek(index36_2);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 36, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA46_eotS =
        "\34\uffff";
    static final String DFA46_eofS =
        "\34\uffff";
    static final String DFA46_minS =
        "\1\4\2\0\31\uffff";
    static final String DFA46_maxS =
        "\1\115\2\0\31\uffff";
    static final String DFA46_acceptS =
        "\3\uffff\2\1\1\2\26\uffff";
    static final String DFA46_specialS =
        "\1\0\1\1\1\2\31\uffff}>";
    static final String[] DFA46_transitionS = {
            "\3\5\1\1\17\uffff\1\5\2\uffff\4\5\5\uffff\6\5\21\uffff\1\2"+
            "\5\uffff\1\5\1\4\1\uffff\1\5\1\uffff\7\5\1\uffff\1\3",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA46_eot = DFA.unpackEncodedString(DFA46_eotS);
    static final short[] DFA46_eof = DFA.unpackEncodedString(DFA46_eofS);
    static final char[] DFA46_min = DFA.unpackEncodedStringToUnsignedChars(DFA46_minS);
    static final char[] DFA46_max = DFA.unpackEncodedStringToUnsignedChars(DFA46_maxS);
    static final short[] DFA46_accept = DFA.unpackEncodedString(DFA46_acceptS);
    static final short[] DFA46_special = DFA.unpackEncodedString(DFA46_specialS);
    static final short[][] DFA46_transition;

    static {
        int numStates = DFA46_transitionS.length;
        DFA46_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA46_transition[i] = DFA.unpackEncodedString(DFA46_transitionS[i]);
        }
    }

    class DFA46 extends DFA {

        public DFA46(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 46;
            this.eot = DFA46_eot;
            this.eof = DFA46_eof;
            this.min = DFA46_min;
            this.max = DFA46_max;
            this.accept = DFA46_accept;
            this.special = DFA46_special;
            this.transition = DFA46_transition;
        }
        public String getDescription() {
            return "2499:3: ( ( ( ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> ( ( ( (lv_declaredFormalParameters_2_0= ruleJvmFormalParameter ) ) (otherlv_3= ',' ( (lv_declaredFormalParameters_4_0= ruleJvmFormalParameter ) ) )* )? ( (lv_explicitSyntax_5_0= '|' ) ) ) )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
		int _s = s;
            switch ( s ) {
                    case 0 :
                        int LA46_0 = input.LA(1);


                        int index46_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA46_0==RULE_ID) ) {s = 1;}

                        else if ( (LA46_0==58) ) {s = 2;}

                        else if ( (LA46_0==77) && (synpred17_InternalDSEL())) {s = 3;}

                        else if ( (LA46_0==65) && (synpred17_InternalDSEL())) {s = 4;}

                        else if ( ((LA46_0>=RULE_INT && LA46_0<=RULE_STRING)||LA46_0==23||(LA46_0>=26 && LA46_0<=29)||(LA46_0>=35 && LA46_0<=40)||LA46_0==64||LA46_0==67||(LA46_0>=69 && LA46_0<=75)) ) {s = 5;}


                        input.seek(index46_0);
                        if ( s>=0 ) return s;
                        break;
                    case 1 :
                        int LA46_1 = input.LA(1);


                        int index46_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred17_InternalDSEL()) ) {s = 4;}

                        else if ( (true) ) {s = 5;}


                        input.seek(index46_1);
                        if ( s>=0 ) return s;
                        break;
                    case 2 :
                        int LA46_2 = input.LA(1);


                        int index46_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred17_InternalDSEL()) ) {s = 4;}

                        else if ( (true) ) {s = 5;}


                        input.seek(index46_2);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 46, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA61_eotS =
        "\77\uffff";
    static final String DFA61_eofS =
        "\1\2\76\uffff";
    static final String DFA61_minS =
        "\1\4\1\0\75\uffff";
    static final String DFA61_maxS =
        "\1\113\1\0\75\uffff";
    static final String DFA61_acceptS =
        "\2\uffff\1\2\73\uffff\1\1";
    static final String DFA61_specialS =
        "\1\uffff\1\0\75\uffff}>";
    static final String[] DFA61_transitionS = {
            "\4\2\10\uffff\32\2\1\uffff\17\2\1\1\1\2\2\uffff\3\2\1\uffff"+
            "\12\2",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA61_eot = DFA.unpackEncodedString(DFA61_eotS);
    static final short[] DFA61_eof = DFA.unpackEncodedString(DFA61_eofS);
    static final char[] DFA61_min = DFA.unpackEncodedStringToUnsignedChars(DFA61_minS);
    static final char[] DFA61_max = DFA.unpackEncodedStringToUnsignedChars(DFA61_maxS);
    static final short[] DFA61_accept = DFA.unpackEncodedString(DFA61_acceptS);
    static final short[] DFA61_special = DFA.unpackEncodedString(DFA61_specialS);
    static final short[][] DFA61_transition;

    static {
        int numStates = DFA61_transitionS.length;
        DFA61_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA61_transition[i] = DFA.unpackEncodedString(DFA61_transitionS[i]);
        }
    }

    class DFA61 extends DFA {

        public DFA61(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 61;
            this.eot = DFA61_eot;
            this.eof = DFA61_eof;
            this.min = DFA61_min;
            this.max = DFA61_max;
            this.accept = DFA61_accept;
            this.special = DFA61_special;
            this.transition = DFA61_transition;
        }
        public String getDescription() {
            return "3278:2: ( ( ( ( '(' ) )=> (lv_explicitOperationCall_7_0= '(' ) ) ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_featureCallArguments_8_0= ruleXShortClosure ) ) | ( ( (lv_featureCallArguments_9_0= ruleXExpression ) ) (otherlv_10= ',' ( (lv_featureCallArguments_11_0= ruleXExpression ) ) )* ) )? otherlv_12= ')' )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
		int _s = s;
            switch ( s ) {
                    case 0 :
                        int LA61_1 = input.LA(1);


                        int index61_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred21_InternalDSEL()) ) {s = 62;}

                        else if ( (true) ) {s = 2;}


                        input.seek(index61_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 61, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA60_eotS =
        "\32\uffff";
    static final String DFA60_eofS =
        "\32\uffff";
    static final String DFA60_minS =
        "\1\4\2\0\27\uffff";
    static final String DFA60_maxS =
        "\1\115\2\0\27\uffff";
    static final String DFA60_acceptS =
        "\3\uffff\2\1\1\2\23\uffff\1\3";
    static final String DFA60_specialS =
        "\1\0\1\1\1\2\27\uffff}>";
    static final String[] DFA60_transitionS = {
            "\3\5\1\1\17\uffff\1\5\2\uffff\4\5\5\uffff\6\5\21\uffff\1\2"+
            "\1\31\5\uffff\1\4\1\uffff\1\5\3\uffff\5\5\1\uffff\1\3",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA60_eot = DFA.unpackEncodedString(DFA60_eotS);
    static final short[] DFA60_eof = DFA.unpackEncodedString(DFA60_eofS);
    static final char[] DFA60_min = DFA.unpackEncodedStringToUnsignedChars(DFA60_minS);
    static final char[] DFA60_max = DFA.unpackEncodedStringToUnsignedChars(DFA60_maxS);
    static final short[] DFA60_accept = DFA.unpackEncodedString(DFA60_acceptS);
    static final short[] DFA60_special = DFA.unpackEncodedString(DFA60_specialS);
    static final short[][] DFA60_transition;

    static {
        int numStates = DFA60_transitionS.length;
        DFA60_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA60_transition[i] = DFA.unpackEncodedString(DFA60_transitionS[i]);
        }
    }

    class DFA60 extends DFA {

        public DFA60(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 60;
            this.eot = DFA60_eot;
            this.eof = DFA60_eof;
            this.min = DFA60_min;
            this.max = DFA60_max;
            this.accept = DFA60_accept;
            this.special = DFA60_special;
            this.transition = DFA60_transition;
        }
        public String getDescription() {
            return "3299:2: ( ( ( ( () ( ( ( ruleJvmFormalParameter ) ) ( ',' ( ( ruleJvmFormalParameter ) ) )* )? ( ( '|' ) ) ) )=> (lv_featureCallArguments_8_0= ruleXShortClosure ) ) | ( ( (lv_featureCallArguments_9_0= ruleXExpression ) ) (otherlv_10= ',' ( (lv_featureCallArguments_11_0= ruleXExpression ) ) )* ) )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
		int _s = s;
            switch ( s ) {
                    case 0 :
                        int LA60_0 = input.LA(1);


                        int index60_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA60_0==RULE_ID) ) {s = 1;}

                        else if ( (LA60_0==58) ) {s = 2;}

                        else if ( (LA60_0==77) && (synpred22_InternalDSEL())) {s = 3;}

                        else if ( (LA60_0==65) && (synpred22_InternalDSEL())) {s = 4;}

                        else if ( ((LA60_0>=RULE_INT && LA60_0<=RULE_STRING)||LA60_0==23||(LA60_0>=26 && LA60_0<=29)||(LA60_0>=35 && LA60_0<=40)||LA60_0==67||(LA60_0>=71 && LA60_0<=75)) ) {s = 5;}

                        else if ( (LA60_0==59) ) {s = 25;}


                        input.seek(index60_0);
                        if ( s>=0 ) return s;
                        break;
                    case 1 :
                        int LA60_1 = input.LA(1);


                        int index60_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred22_InternalDSEL()) ) {s = 4;}

                        else if ( (true) ) {s = 5;}


                        input.seek(index60_1);
                        if ( s>=0 ) return s;
                        break;
                    case 2 :
                        int LA60_2 = input.LA(1);


                        int index60_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred22_InternalDSEL()) ) {s = 4;}

                        else if ( (true) ) {s = 5;}


                        input.seek(index60_2);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 60, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA75_eotS =
        "\100\uffff";
    static final String DFA75_eofS =
        "\1\2\77\uffff";
    static final String DFA75_minS =
        "\1\4\1\0\76\uffff";
    static final String DFA75_maxS =
        "\1\117\1\0\76\uffff";
    static final String DFA75_acceptS =
        "\2\uffff\1\2\74\uffff\1\1";
    static final String DFA75_specialS =
        "\1\uffff\1\0\76\uffff}>";
    static final String[] DFA75_transitionS = {
            "\4\2\10\uffff\7\2\1\1\22\2\1\uffff\21\2\2\uffff\3\2\1\uffff"+
            "\12\2\3\uffff\1\2",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA75_eot = DFA.unpackEncodedString(DFA75_eotS);
    static final short[] DFA75_eof = DFA.unpackEncodedString(DFA75_eofS);
    static final char[] DFA75_min = DFA.unpackEncodedStringToUnsignedChars(DFA75_minS);
    static final char[] DFA75_max = DFA.unpackEncodedStringToUnsignedChars(DFA75_maxS);
    static final short[] DFA75_accept = DFA.unpackEncodedString(DFA75_acceptS);
    static final short[] DFA75_special = DFA.unpackEncodedString(DFA75_specialS);
    static final short[][] DFA75_transition;

    static {
        int numStates = DFA75_transitionS.length;
        DFA75_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA75_transition[i] = DFA.unpackEncodedString(DFA75_transitionS[i]);
        }
    }

    class DFA75 extends DFA {

        public DFA75(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 75;
            this.eot = DFA75_eot;
            this.eof = DFA75_eof;
            this.min = DFA75_min;
            this.max = DFA75_max;
            this.accept = DFA75_accept;
            this.special = DFA75_special;
            this.transition = DFA75_transition;
        }
        public String getDescription() {
            return "3894:2: ( ( ( '<' )=>otherlv_1= '<' ) ( (lv_arguments_2_0= ruleJvmArgumentTypeReference ) ) (otherlv_3= ',' ( (lv_arguments_4_0= ruleJvmArgumentTypeReference ) ) )* otherlv_5= '>' ( ( ( ( () '.' ) )=> ( () otherlv_7= '.' ) ) ( ( ruleValidID ) ) ( ( ( '<' )=>otherlv_9= '<' ) ( (lv_arguments_10_0= ruleJvmArgumentTypeReference ) ) (otherlv_11= ',' ( (lv_arguments_12_0= ruleJvmArgumentTypeReference ) ) )* otherlv_13= '>' )? )* )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
		int _s = s;
            switch ( s ) {
                    case 0 :
                        int LA75_1 = input.LA(1);


                        int index75_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred27_InternalDSEL()) ) {s = 63;}

                        else if ( (true) ) {s = 2;}


                        input.seek(index75_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 75, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA73_eotS =
        "\100\uffff";
    static final String DFA73_eofS =
        "\1\2\77\uffff";
    static final String DFA73_minS =
        "\1\4\1\0\76\uffff";
    static final String DFA73_maxS =
        "\1\117\1\0\76\uffff";
    static final String DFA73_acceptS =
        "\2\uffff\1\2\74\uffff\1\1";
    static final String DFA73_specialS =
        "\1\uffff\1\0\76\uffff}>";
    static final String[] DFA73_transitionS = {
            "\4\2\10\uffff\7\2\1\1\22\2\1\uffff\21\2\2\uffff\3\2\1\uffff"+
            "\12\2\3\uffff\1\2",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA73_eot = DFA.unpackEncodedString(DFA73_eotS);
    static final short[] DFA73_eof = DFA.unpackEncodedString(DFA73_eofS);
    static final char[] DFA73_min = DFA.unpackEncodedStringToUnsignedChars(DFA73_minS);
    static final char[] DFA73_max = DFA.unpackEncodedStringToUnsignedChars(DFA73_maxS);
    static final short[] DFA73_accept = DFA.unpackEncodedString(DFA73_acceptS);
    static final short[] DFA73_special = DFA.unpackEncodedString(DFA73_specialS);
    static final short[][] DFA73_transition;

    static {
        int numStates = DFA73_transitionS.length;
        DFA73_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA73_transition[i] = DFA.unpackEncodedString(DFA73_transitionS[i]);
        }
    }

    class DFA73 extends DFA {

        public DFA73(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 73;
            this.eot = DFA73_eot;
            this.eof = DFA73_eof;
            this.min = DFA73_min;
            this.max = DFA73_max;
            this.accept = DFA73_accept;
            this.special = DFA73_special;
            this.transition = DFA73_transition;
        }
        public String getDescription() {
            return "3970:2: ( ( ( '<' )=>otherlv_9= '<' ) ( (lv_arguments_10_0= ruleJvmArgumentTypeReference ) ) (otherlv_11= ',' ( (lv_arguments_12_0= ruleJvmArgumentTypeReference ) ) )* otherlv_13= '>' )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
		int _s = s;
            switch ( s ) {
                    case 0 :
                        int LA73_1 = input.LA(1);


                        int index73_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred29_InternalDSEL()) ) {s = 63;}

                        else if ( (true) ) {s = 2;}


                        input.seek(index73_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 73, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA82_eotS =
        "\7\uffff";
    static final String DFA82_eofS =
        "\2\uffff\1\4\2\uffff\1\4\1\uffff";
    static final String DFA82_minS =
        "\1\7\1\uffff\1\51\1\7\1\uffff\1\51\1\uffff";
    static final String DFA82_maxS =
        "\1\110\1\uffff\1\102\1\60\1\uffff\1\102\1\uffff";
    static final String DFA82_acceptS =
        "\1\uffff\1\1\2\uffff\1\2\1\uffff\1\3";
    static final String DFA82_specialS =
        "\7\uffff}>";
    static final String[] DFA82_transitionS = {
            "\1\2\100\uffff\1\1",
            "",
            "\1\3\30\uffff\1\4",
            "\1\5\50\uffff\1\6",
            "",
            "\1\3\30\uffff\1\4",
            ""
    };

    static final short[] DFA82_eot = DFA.unpackEncodedString(DFA82_eotS);
    static final short[] DFA82_eof = DFA.unpackEncodedString(DFA82_eofS);
    static final char[] DFA82_min = DFA.unpackEncodedStringToUnsignedChars(DFA82_minS);
    static final char[] DFA82_max = DFA.unpackEncodedStringToUnsignedChars(DFA82_maxS);
    static final short[] DFA82_accept = DFA.unpackEncodedString(DFA82_acceptS);
    static final short[] DFA82_special = DFA.unpackEncodedString(DFA82_specialS);
    static final short[][] DFA82_transition;

    static {
        int numStates = DFA82_transitionS.length;
        DFA82_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA82_transition[i] = DFA.unpackEncodedString(DFA82_transitionS[i]);
        }
    }

    class DFA82 extends DFA {

        public DFA82(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 82;
            this.eot = DFA82_eot;
            this.eof = DFA82_eof;
            this.min = DFA82_min;
            this.max = DFA82_max;
            this.accept = DFA82_accept;
            this.special = DFA82_special;
            this.transition = DFA82_transition;
        }
        public String getDescription() {
            return "4436:1: ( ( ( (lv_static_1_0= 'static' ) ) ( (lv_extension_2_0= 'extension' ) )? ( ( ruleQualifiedNameInStaticImport ) ) ( ( (lv_wildcard_4_0= '*' ) ) | ( (lv_memberName_5_0= ruleValidID ) ) ) ) | ( ( ruleQualifiedName ) ) | ( (lv_importedNamespace_7_0= ruleQualifiedNameWithWildcard ) ) )";
        }
    }


    public static final BitSet FOLLOW_ruleXExpression_in_entryRuleXExpression75 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleXExpression85 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleXOrExpression_in_ruleXExpression131 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleXLiteral_in_entryRuleXLiteral165 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleXLiteral175 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleXBooleanLiteral_in_ruleXLiteral222 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleXNullLiteral_in_ruleXLiteral249 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleXStringLiteral_in_ruleXLiteral276 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDecimalLiteral_in_ruleXLiteral303 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDateLiteral_in_ruleXLiteral330 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDateTimeLiteral_in_ruleXLiteral357 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleXUnaryOperation_in_entryRuleXUnaryOperation392 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleXUnaryOperation402 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOpUnary_in_ruleXUnaryOperation460 = new BitSet(new long[]{0x040001F83C8000F0L,0x0000000000000F88L});
    public static final BitSet FOLLOW_ruleXUnaryOperation_in_ruleXUnaryOperation481 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleXCastedExpression_in_ruleXUnaryOperation510 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOpSingleAssign_in_entryRuleOpSingleAssign550 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleOpSingleAssign561 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_15_in_ruleOpSingleAssign598 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOpEquality_in_entryRuleOpEquality638 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleOpEquality649 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_16_in_ruleOpEquality687 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_17_in_ruleOpEquality706 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_18_in_ruleOpEquality725 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_19_in_ruleOpEquality744 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOpCompare_in_entryRuleOpCompare785 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleOpCompare796 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_20_in_ruleOpCompare834 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_21_in_ruleOpCompare853 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_22_in_ruleOpCompare872 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_23_in_ruleOpCompare891 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_24_in_ruleOpCompare910 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_25_in_ruleOpCompare929 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOpUnary_in_entryRuleOpUnary970 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleOpUnary981 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_26_in_ruleOpUnary1019 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_27_in_ruleOpUnary1038 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_ruleOpUnary1057 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_29_in_ruleOpUnary1076 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOpOr_in_entryRuleOpOr1117 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleOpOr1128 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_30_in_ruleOpOr1166 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_31_in_ruleOpOr1185 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOpAnd_in_entryRuleOpAnd1226 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleOpAnd1237 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_32_in_ruleOpAnd1275 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_33_in_ruleOpAnd1294 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleXPrimaryExpression_in_entryRuleXPrimaryExpression1334 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleXPrimaryExpression1344 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleXFeatureCall_in_ruleXPrimaryExpression1391 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleXLiteral_in_ruleXPrimaryExpression1418 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleXIfExpression_in_ruleXPrimaryExpression1445 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleXParenthesizedExpression_in_ruleXPrimaryExpression1472 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleXRelationalExpression_in_entryRuleXRelationalExpression1507 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleXRelationalExpression1517 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleXOtherOperatorExpression_in_ruleXRelationalExpression1564 = new BitSet(new long[]{0x0000000003F00002L});
    public static final BitSet FOLLOW_ruleOpCompare_in_ruleXRelationalExpression1617 = new BitSet(new long[]{0x040001F83C8000F0L,0x0000000000000F88L});
    public static final BitSet FOLLOW_ruleXOtherOperatorExpression_in_ruleXRelationalExpression1640 = new BitSet(new long[]{0x0000000003F00002L});
    public static final BitSet FOLLOW_ruleXOtherOperatorExpression_in_entryRuleXOtherOperatorExpression1678 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleXOtherOperatorExpression1688 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleXAdditiveExpression_in_ruleXOtherOperatorExpression1735 = new BitSet(new long[]{0x0000000400000002L});
    public static final BitSet FOLLOW_ruleOpOther_in_ruleXOtherOperatorExpression1788 = new BitSet(new long[]{0x040001F83C8000F0L,0x0000000000000F88L});
    public static final BitSet FOLLOW_ruleXAdditiveExpression_in_ruleXOtherOperatorExpression1811 = new BitSet(new long[]{0x0000000400000002L});
    public static final BitSet FOLLOW_ruleOpOther_in_entryRuleOpOther1850 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleOpOther1861 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_34_in_ruleOpOther1898 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDateLiteral_in_entryRuleDateLiteral1937 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleDateLiteral1947 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDate_in_ruleDateLiteral2002 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDateTimeLiteral_in_entryRuleDateTimeLiteral2038 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleDateTimeLiteral2048 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDate_Time_in_ruleDateTimeLiteral2103 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDecimalLiteral_in_entryRuleDecimalLiteral2139 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleDecimalLiteral2149 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleNumber_in_ruleDecimalLiteral2204 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleXNullLiteral_in_entryRuleXNullLiteral2240 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleXNullLiteral2250 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_35_in_ruleXNullLiteral2297 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_36_in_ruleXNullLiteral2315 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleXBooleanLiteral_in_entryRuleXBooleanLiteral2352 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleXBooleanLiteral2362 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_37_in_ruleXBooleanLiteral2410 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_38_in_ruleXBooleanLiteral2428 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_39_in_ruleXBooleanLiteral2455 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_40_in_ruleXBooleanLiteral2484 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDate_in_entryRuleDate2538 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleDate2549 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_INT_in_ruleDate2589 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_41_in_ruleDate2607 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_INT_in_ruleDate2622 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_41_in_ruleDate2640 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_INT_in_ruleDate2655 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDate_Time_in_entryRuleDate_Time2701 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleDate_Time2712 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleDate_in_ruleDate_Time2759 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_INT_in_ruleDate_Time2779 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_ruleDate_Time2797 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_INT_in_ruleDate_Time2812 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_ruleDate_Time2830 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_RULE_INT_in_ruleDate_Time2845 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleNumber_in_entryRuleNumber2891 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleNumber2902 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_INT_in_ruleNumber2943 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_41_in_ruleNumber2961 = new BitSet(new long[]{0x0000000000000030L});
    public static final BitSet FOLLOW_RULE_INT_in_ruleNumber2979 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_EINT_in_ruleNumber3005 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleXAssignment_in_entryRuleXAssignment3051 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleXAssignment3061 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFeatureCallID_in_ruleXAssignment3119 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_ruleOpSingleAssign_in_ruleXAssignment3135 = new BitSet(new long[]{0x040001F83C8000F0L,0x0000000000000F88L});
    public static final BitSet FOLLOW_ruleXAssignment_in_ruleXAssignment3155 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleXOrExpression_in_ruleXAssignment3185 = new BitSet(new long[]{0x0000F80000C00002L});
    public static final BitSet FOLLOW_ruleOpMultiAssign_in_ruleXAssignment3238 = new BitSet(new long[]{0x040001F83C8000F0L,0x0000000000000F88L});
    public static final BitSet FOLLOW_ruleXAssignment_in_ruleXAssignment3261 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOpMultiAssign_in_entryRuleOpMultiAssign3301 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleOpMultiAssign3312 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_43_in_ruleOpMultiAssign3350 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_44_in_ruleOpMultiAssign3369 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_45_in_ruleOpMultiAssign3388 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_46_in_ruleOpMultiAssign3407 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_47_in_ruleOpMultiAssign3426 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_23_in_ruleOpMultiAssign3446 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_23_in_ruleOpMultiAssign3459 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_17_in_ruleOpMultiAssign3472 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_22_in_ruleOpMultiAssign3493 = new BitSet(new long[]{0x0000000000500000L});
    public static final BitSet FOLLOW_22_in_ruleOpMultiAssign3507 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_20_in_ruleOpMultiAssign3522 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleXOrExpression_in_entryRuleXOrExpression3563 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleXOrExpression3573 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleXAndExpression_in_ruleXOrExpression3620 = new BitSet(new long[]{0x00000000C0000002L});
    public static final BitSet FOLLOW_ruleOpOr_in_ruleXOrExpression3673 = new BitSet(new long[]{0x040001F83C8000F0L,0x0000000000000F88L});
    public static final BitSet FOLLOW_ruleXAndExpression_in_ruleXOrExpression3696 = new BitSet(new long[]{0x00000000C0000002L});
    public static final BitSet FOLLOW_ruleXAndExpression_in_entryRuleXAndExpression3734 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleXAndExpression3744 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleXEqualityExpression_in_ruleXAndExpression3791 = new BitSet(new long[]{0x0000000300000002L});
    public static final BitSet FOLLOW_ruleOpAnd_in_ruleXAndExpression3844 = new BitSet(new long[]{0x040001F83C8000F0L,0x0000000000000F88L});
    public static final BitSet FOLLOW_ruleXEqualityExpression_in_ruleXAndExpression3867 = new BitSet(new long[]{0x0000000300000002L});
    public static final BitSet FOLLOW_ruleXEqualityExpression_in_entryRuleXEqualityExpression3905 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleXEqualityExpression3915 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleXRelationalExpression_in_ruleXEqualityExpression3962 = new BitSet(new long[]{0x00000000000F0002L});
    public static final BitSet FOLLOW_ruleOpEquality_in_ruleXEqualityExpression4015 = new BitSet(new long[]{0x040001F83C8000F0L,0x0000000000000F88L});
    public static final BitSet FOLLOW_ruleXRelationalExpression_in_ruleXEqualityExpression4038 = new BitSet(new long[]{0x00000000000F0002L});
    public static final BitSet FOLLOW_ruleXAdditiveExpression_in_entryRuleXAdditiveExpression4076 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleXAdditiveExpression4086 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleXMultiplicativeExpression_in_ruleXAdditiveExpression4133 = new BitSet(new long[]{0x0000000018000002L});
    public static final BitSet FOLLOW_ruleOpAdd_in_ruleXAdditiveExpression4186 = new BitSet(new long[]{0x040001F83C8000F0L,0x0000000000000F88L});
    public static final BitSet FOLLOW_ruleXMultiplicativeExpression_in_ruleXAdditiveExpression4209 = new BitSet(new long[]{0x0000000018000002L});
    public static final BitSet FOLLOW_ruleOpAdd_in_entryRuleOpAdd4248 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleOpAdd4259 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_ruleOpAdd4297 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_27_in_ruleOpAdd4316 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleXMultiplicativeExpression_in_entryRuleXMultiplicativeExpression4356 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleXMultiplicativeExpression4366 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleXUnaryOperation_in_ruleXMultiplicativeExpression4413 = new BitSet(new long[]{0x000F000000000002L});
    public static final BitSet FOLLOW_ruleOpMulti_in_ruleXMultiplicativeExpression4466 = new BitSet(new long[]{0x040001F83C8000F0L,0x0000000000000F88L});
    public static final BitSet FOLLOW_ruleXUnaryOperation_in_ruleXMultiplicativeExpression4489 = new BitSet(new long[]{0x000F000000000002L});
    public static final BitSet FOLLOW_ruleOpMulti_in_entryRuleOpMulti4528 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleOpMulti4539 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_48_in_ruleOpMulti4577 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_49_in_ruleOpMulti4596 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_50_in_ruleOpMulti4615 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_51_in_ruleOpMulti4634 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleXCastedExpression_in_entryRuleXCastedExpression4674 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleXCastedExpression4684 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleXPostfixOperation_in_ruleXCastedExpression4731 = new BitSet(new long[]{0x0010000000000002L});
    public static final BitSet FOLLOW_52_in_ruleXCastedExpression4766 = new BitSet(new long[]{0x0400000000000080L,0x0000000000002000L});
    public static final BitSet FOLLOW_ruleJvmTypeReference_in_ruleXCastedExpression4789 = new BitSet(new long[]{0x0010000000000002L});
    public static final BitSet FOLLOW_ruleXPostfixOperation_in_entryRuleXPostfixOperation4827 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleXPostfixOperation4837 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleXMemberFeatureCall_in_ruleXPostfixOperation4884 = new BitSet(new long[]{0x0060000000000002L});
    public static final BitSet FOLLOW_ruleOpPostfix_in_ruleXPostfixOperation4936 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOpPostfix_in_entryRuleOpPostfix4976 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleOpPostfix4987 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_53_in_ruleOpPostfix5025 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_54_in_ruleOpPostfix5044 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleXMemberFeatureCall_in_entryRuleXMemberFeatureCall5084 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleXMemberFeatureCall5094 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleXPrimaryExpression_in_ruleXMemberFeatureCall5141 = new BitSet(new long[]{0x0180020000000002L});
    public static final BitSet FOLLOW_41_in_ruleXMemberFeatureCall5213 = new BitSet(new long[]{0x0000000000000080L,0x0000000000000780L});
    public static final BitSet FOLLOW_55_in_ruleXMemberFeatureCall5237 = new BitSet(new long[]{0x0000000000000080L,0x0000000000000780L});
    public static final BitSet FOLLOW_ruleFeatureCallID_in_ruleXMemberFeatureCall5274 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_ruleOpSingleAssign_in_ruleXMemberFeatureCall5290 = new BitSet(new long[]{0x040001F83C8000F0L,0x0000000000000F88L});
    public static final BitSet FOLLOW_ruleXAssignment_in_ruleXMemberFeatureCall5312 = new BitSet(new long[]{0x0180020000000002L});
    public static final BitSet FOLLOW_41_in_ruleXMemberFeatureCall5398 = new BitSet(new long[]{0x0000000000800080L,0x0000000000000F80L});
    public static final BitSet FOLLOW_56_in_ruleXMemberFeatureCall5422 = new BitSet(new long[]{0x0000000000800080L,0x0000000000000F80L});
    public static final BitSet FOLLOW_55_in_ruleXMemberFeatureCall5459 = new BitSet(new long[]{0x0000000000800080L,0x0000000000000F80L});
    public static final BitSet FOLLOW_23_in_ruleXMemberFeatureCall5488 = new BitSet(new long[]{0x0400000000000080L,0x0000000000006000L});
    public static final BitSet FOLLOW_ruleJvmArgumentTypeReference_in_ruleXMemberFeatureCall5509 = new BitSet(new long[]{0x0200000000400000L});
    public static final BitSet FOLLOW_57_in_ruleXMemberFeatureCall5522 = new BitSet(new long[]{0x0400000000000080L,0x0000000000006000L});
    public static final BitSet FOLLOW_ruleJvmArgumentTypeReference_in_ruleXMemberFeatureCall5543 = new BitSet(new long[]{0x0200000000400000L});
    public static final BitSet FOLLOW_22_in_ruleXMemberFeatureCall5557 = new BitSet(new long[]{0x0000000000800080L,0x0000000000000F80L});
    public static final BitSet FOLLOW_ruleIdOrSuper_in_ruleXMemberFeatureCall5582 = new BitSet(new long[]{0x8580020000000002L});
    public static final BitSet FOLLOW_58_in_ruleXMemberFeatureCall5616 = new BitSet(new long[]{0x0C0001F83C8000F0L,0x0000000000002F8AL});
    public static final BitSet FOLLOW_ruleXShortClosure_in_ruleXMemberFeatureCall5701 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_ruleXExpression_in_ruleXMemberFeatureCall5729 = new BitSet(new long[]{0x0A00000000000000L});
    public static final BitSet FOLLOW_57_in_ruleXMemberFeatureCall5742 = new BitSet(new long[]{0x040001F83C8000F0L,0x0000000000000F88L});
    public static final BitSet FOLLOW_ruleXExpression_in_ruleXMemberFeatureCall5763 = new BitSet(new long[]{0x0A00000000000000L});
    public static final BitSet FOLLOW_59_in_ruleXMemberFeatureCall5780 = new BitSet(new long[]{0x8180020000000002L});
    public static final BitSet FOLLOW_ruleXClosure_in_ruleXMemberFeatureCall5815 = new BitSet(new long[]{0x0180020000000002L});
    public static final BitSet FOLLOW_ruleXSetLiteral_in_entryRuleXSetLiteral5857 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleXSetLiteral5867 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_60_in_ruleXSetLiteral5913 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_61_in_ruleXSetLiteral5925 = new BitSet(new long[]{0x440001F83C8000F0L,0x0000000000000F88L});
    public static final BitSet FOLLOW_ruleXExpression_in_ruleXSetLiteral5947 = new BitSet(new long[]{0x4200000000000000L});
    public static final BitSet FOLLOW_57_in_ruleXSetLiteral5960 = new BitSet(new long[]{0x040001F83C8000F0L,0x0000000000000F88L});
    public static final BitSet FOLLOW_ruleXExpression_in_ruleXSetLiteral5981 = new BitSet(new long[]{0x4200000000000000L});
    public static final BitSet FOLLOW_62_in_ruleXSetLiteral5997 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleXListLiteral_in_entryRuleXListLiteral6033 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleXListLiteral6043 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_60_in_ruleXListLiteral6089 = new BitSet(new long[]{0x8000000000000000L});
    public static final BitSet FOLLOW_63_in_ruleXListLiteral6101 = new BitSet(new long[]{0x040001F83C8000F0L,0x0000000000000F89L});
    public static final BitSet FOLLOW_ruleXExpression_in_ruleXListLiteral6123 = new BitSet(new long[]{0x0200000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_57_in_ruleXListLiteral6136 = new BitSet(new long[]{0x040001F83C8000F0L,0x0000000000000F88L});
    public static final BitSet FOLLOW_ruleXExpression_in_ruleXListLiteral6157 = new BitSet(new long[]{0x0200000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_ruleXListLiteral6173 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleXClosure_in_entryRuleXClosure6209 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleXClosure6219 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_63_in_ruleXClosure6279 = new BitSet(new long[]{0x040001F83C8000F0L,0x0000000000002FEBL});
    public static final BitSet FOLLOW_ruleJvmFormalParameter_in_ruleXClosure6352 = new BitSet(new long[]{0x0200000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_57_in_ruleXClosure6365 = new BitSet(new long[]{0x0400000000000080L,0x0000000000002000L});
    public static final BitSet FOLLOW_ruleJvmFormalParameter_in_ruleXClosure6386 = new BitSet(new long[]{0x0200000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_ruleXClosure6408 = new BitSet(new long[]{0x040001F83C8000F0L,0x0000000000000FE9L});
    public static final BitSet FOLLOW_ruleXExpressionInClosure_in_ruleXClosure6445 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_ruleXClosure6457 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleXExpressionInClosure_in_entryRuleXExpressionInClosure6493 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleXExpressionInClosure6503 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleXExpressionOrVarDeclaration_in_ruleXExpressionInClosure6559 = new BitSet(new long[]{0x040001F83C8000F2L,0x0000000000000FECL});
    public static final BitSet FOLLOW_66_in_ruleXExpressionInClosure6572 = new BitSet(new long[]{0x040001F83C8000F2L,0x0000000000000FE8L});
    public static final BitSet FOLLOW_ruleXShortClosure_in_entryRuleXShortClosure6612 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleXShortClosure6622 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleJvmFormalParameter_in_ruleXShortClosure6730 = new BitSet(new long[]{0x0200000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_57_in_ruleXShortClosure6743 = new BitSet(new long[]{0x0400000000000080L,0x0000000000002000L});
    public static final BitSet FOLLOW_ruleJvmFormalParameter_in_ruleXShortClosure6764 = new BitSet(new long[]{0x0200000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_ruleXShortClosure6786 = new BitSet(new long[]{0x040001F83C8000F0L,0x0000000000000F88L});
    public static final BitSet FOLLOW_ruleXExpression_in_ruleXShortClosure6822 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleXParenthesizedExpression_in_entryRuleXParenthesizedExpression6858 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleXParenthesizedExpression6868 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_58_in_ruleXParenthesizedExpression6905 = new BitSet(new long[]{0x040001F83C8000F0L,0x0000000000000F88L});
    public static final BitSet FOLLOW_ruleXExpression_in_ruleXParenthesizedExpression6927 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_59_in_ruleXParenthesizedExpression6938 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleXIfExpression_in_entryRuleXIfExpression6974 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleXIfExpression6984 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_67_in_ruleXIfExpression7030 = new BitSet(new long[]{0x0400000000000000L});
    public static final BitSet FOLLOW_58_in_ruleXIfExpression7042 = new BitSet(new long[]{0x040001F83C8000F0L,0x0000000000000F88L});
    public static final BitSet FOLLOW_ruleXExpression_in_ruleXIfExpression7063 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_59_in_ruleXIfExpression7075 = new BitSet(new long[]{0x040001F83C8000F0L,0x0000000000000F88L});
    public static final BitSet FOLLOW_ruleXExpression_in_ruleXIfExpression7096 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000010L});
    public static final BitSet FOLLOW_68_in_ruleXIfExpression7117 = new BitSet(new long[]{0x040001F83C8000F0L,0x0000000000000F88L});
    public static final BitSet FOLLOW_ruleXExpression_in_ruleXIfExpression7139 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleXExpressionOrVarDeclaration_in_entryRuleXExpressionOrVarDeclaration7187 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleXExpressionOrVarDeclaration7197 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleXVariableDeclaration_in_ruleXExpressionOrVarDeclaration7244 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleXExpression_in_ruleXExpressionOrVarDeclaration7271 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleXVariableDeclaration_in_entryRuleXVariableDeclaration7306 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleXVariableDeclaration7316 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_69_in_ruleXVariableDeclaration7369 = new BitSet(new long[]{0x0400000000000080L,0x0000000000002000L});
    public static final BitSet FOLLOW_70_in_ruleXVariableDeclaration7400 = new BitSet(new long[]{0x0400000000000080L,0x0000000000002000L});
    public static final BitSet FOLLOW_ruleJvmTypeReference_in_ruleXVariableDeclaration7448 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_ruleValidID_in_ruleXVariableDeclaration7469 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_ruleValidID_in_ruleXVariableDeclaration7498 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_17_in_ruleXVariableDeclaration7512 = new BitSet(new long[]{0x040001F83C8000F0L,0x0000000000000F88L});
    public static final BitSet FOLLOW_ruleXExpression_in_ruleXVariableDeclaration7533 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleJvmFormalParameter_in_entryRuleJvmFormalParameter7571 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleJvmFormalParameter7581 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleJvmTypeReference_in_ruleJvmFormalParameter7627 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_ruleValidID_in_ruleJvmFormalParameter7649 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFullJvmFormalParameter_in_entryRuleFullJvmFormalParameter7685 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleFullJvmFormalParameter7695 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleJvmTypeReference_in_ruleFullJvmFormalParameter7741 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_ruleValidID_in_ruleFullJvmFormalParameter7762 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleXFeatureCall_in_entryRuleXFeatureCall7798 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleXFeatureCall7808 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_23_in_ruleXFeatureCall7855 = new BitSet(new long[]{0x0400000000000080L,0x0000000000006000L});
    public static final BitSet FOLLOW_ruleJvmArgumentTypeReference_in_ruleXFeatureCall7876 = new BitSet(new long[]{0x0200000000400000L});
    public static final BitSet FOLLOW_57_in_ruleXFeatureCall7889 = new BitSet(new long[]{0x0400000000000080L,0x0000000000006000L});
    public static final BitSet FOLLOW_ruleJvmArgumentTypeReference_in_ruleXFeatureCall7910 = new BitSet(new long[]{0x0200000000400000L});
    public static final BitSet FOLLOW_22_in_ruleXFeatureCall7924 = new BitSet(new long[]{0x0000000000800080L,0x0000000000000F80L});
    public static final BitSet FOLLOW_ruleIdOrSuper_in_ruleXFeatureCall7949 = new BitSet(new long[]{0x8400000000000002L});
    public static final BitSet FOLLOW_58_in_ruleXFeatureCall7983 = new BitSet(new long[]{0x0C0001F83C8000F0L,0x0000000000002F8AL});
    public static final BitSet FOLLOW_ruleXShortClosure_in_ruleXFeatureCall8068 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_ruleXExpression_in_ruleXFeatureCall8096 = new BitSet(new long[]{0x0A00000000000000L});
    public static final BitSet FOLLOW_57_in_ruleXFeatureCall8109 = new BitSet(new long[]{0x040001F83C8000F0L,0x0000000000000F88L});
    public static final BitSet FOLLOW_ruleXExpression_in_ruleXFeatureCall8130 = new BitSet(new long[]{0x0A00000000000000L});
    public static final BitSet FOLLOW_59_in_ruleXFeatureCall8147 = new BitSet(new long[]{0x8000000000000002L});
    public static final BitSet FOLLOW_ruleXClosure_in_ruleXFeatureCall8182 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFeatureCallID_in_entryRuleFeatureCallID8220 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleFeatureCallID8231 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleValidID_in_ruleFeatureCallID8278 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_71_in_ruleFeatureCallID8302 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_72_in_ruleFeatureCallID8321 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_73_in_ruleFeatureCallID8340 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_74_in_ruleFeatureCallID8359 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleIdOrSuper_in_entryRuleIdOrSuper8400 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleIdOrSuper8411 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleFeatureCallID_in_ruleIdOrSuper8458 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_75_in_ruleIdOrSuper8482 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleXStringLiteral_in_entryRuleXStringLiteral8526 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleXStringLiteral8536 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_STRING_in_ruleXStringLiteral8587 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleXCatchClause_in_entryRuleXCatchClause8638 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleXCatchClause8648 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_76_in_ruleXCatchClause8693 = new BitSet(new long[]{0x0400000000000000L});
    public static final BitSet FOLLOW_58_in_ruleXCatchClause8706 = new BitSet(new long[]{0x0400000000000080L,0x0000000000002000L});
    public static final BitSet FOLLOW_ruleFullJvmFormalParameter_in_ruleXCatchClause8727 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_59_in_ruleXCatchClause8739 = new BitSet(new long[]{0x040001F83C8000F0L,0x0000000000000F88L});
    public static final BitSet FOLLOW_ruleXExpression_in_ruleXCatchClause8760 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleQualifiedName_in_entryRuleQualifiedName8797 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleQualifiedName8808 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleValidID_in_ruleQualifiedName8855 = new BitSet(new long[]{0x0000020000000002L});
    public static final BitSet FOLLOW_41_in_ruleQualifiedName8883 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_ruleValidID_in_ruleQualifiedName8906 = new BitSet(new long[]{0x0000020000000002L});
    public static final BitSet FOLLOW_ruleJvmTypeReference_in_entryRuleJvmTypeReference8955 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleJvmTypeReference8965 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleJvmParameterizedTypeReference_in_ruleJvmTypeReference9013 = new BitSet(new long[]{0x8000000000000002L});
    public static final BitSet FOLLOW_ruleArrayBrackets_in_ruleJvmTypeReference9049 = new BitSet(new long[]{0x8000000000000002L});
    public static final BitSet FOLLOW_ruleXFunctionTypeRef_in_ruleJvmTypeReference9080 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleArrayBrackets_in_entryRuleArrayBrackets9116 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleArrayBrackets9127 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_63_in_ruleArrayBrackets9165 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_ruleArrayBrackets9178 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleXFunctionTypeRef_in_entryRuleXFunctionTypeRef9218 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleXFunctionTypeRef9228 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_58_in_ruleXFunctionTypeRef9266 = new BitSet(new long[]{0x0C00000000000080L,0x0000000000002000L});
    public static final BitSet FOLLOW_ruleJvmTypeReference_in_ruleXFunctionTypeRef9288 = new BitSet(new long[]{0x0A00000000000000L});
    public static final BitSet FOLLOW_57_in_ruleXFunctionTypeRef9301 = new BitSet(new long[]{0x0400000000000080L,0x0000000000002000L});
    public static final BitSet FOLLOW_ruleJvmTypeReference_in_ruleXFunctionTypeRef9322 = new BitSet(new long[]{0x0A00000000000000L});
    public static final BitSet FOLLOW_59_in_ruleXFunctionTypeRef9338 = new BitSet(new long[]{0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_77_in_ruleXFunctionTypeRef9352 = new BitSet(new long[]{0x0400000000000080L,0x0000000000002000L});
    public static final BitSet FOLLOW_ruleJvmTypeReference_in_ruleXFunctionTypeRef9373 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleJvmParameterizedTypeReference_in_entryRuleJvmParameterizedTypeReference9409 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleJvmParameterizedTypeReference9419 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleQualifiedName_in_ruleJvmParameterizedTypeReference9467 = new BitSet(new long[]{0x0000000000800002L});
    public static final BitSet FOLLOW_23_in_ruleJvmParameterizedTypeReference9488 = new BitSet(new long[]{0x0400000000000080L,0x0000000000006000L});
    public static final BitSet FOLLOW_ruleJvmArgumentTypeReference_in_ruleJvmParameterizedTypeReference9510 = new BitSet(new long[]{0x0200000000400000L});
    public static final BitSet FOLLOW_57_in_ruleJvmParameterizedTypeReference9523 = new BitSet(new long[]{0x0400000000000080L,0x0000000000006000L});
    public static final BitSet FOLLOW_ruleJvmArgumentTypeReference_in_ruleJvmParameterizedTypeReference9544 = new BitSet(new long[]{0x0200000000400000L});
    public static final BitSet FOLLOW_22_in_ruleJvmParameterizedTypeReference9558 = new BitSet(new long[]{0x0000020000000002L});
    public static final BitSet FOLLOW_41_in_ruleJvmParameterizedTypeReference9594 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_ruleValidID_in_ruleJvmParameterizedTypeReference9619 = new BitSet(new long[]{0x0000020000800002L});
    public static final BitSet FOLLOW_23_in_ruleJvmParameterizedTypeReference9640 = new BitSet(new long[]{0x0400000000000080L,0x0000000000006000L});
    public static final BitSet FOLLOW_ruleJvmArgumentTypeReference_in_ruleJvmParameterizedTypeReference9662 = new BitSet(new long[]{0x0200000000400000L});
    public static final BitSet FOLLOW_57_in_ruleJvmParameterizedTypeReference9675 = new BitSet(new long[]{0x0400000000000080L,0x0000000000006000L});
    public static final BitSet FOLLOW_ruleJvmArgumentTypeReference_in_ruleJvmParameterizedTypeReference9696 = new BitSet(new long[]{0x0200000000400000L});
    public static final BitSet FOLLOW_22_in_ruleJvmParameterizedTypeReference9710 = new BitSet(new long[]{0x0000020000000002L});
    public static final BitSet FOLLOW_ruleJvmArgumentTypeReference_in_entryRuleJvmArgumentTypeReference9752 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleJvmArgumentTypeReference9762 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleJvmTypeReference_in_ruleJvmArgumentTypeReference9809 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleJvmWildcardTypeReference_in_ruleJvmArgumentTypeReference9836 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleJvmWildcardTypeReference_in_entryRuleJvmWildcardTypeReference9871 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleJvmWildcardTypeReference9881 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_78_in_ruleJvmWildcardTypeReference9927 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000880L});
    public static final BitSet FOLLOW_ruleJvmUpperBound_in_ruleJvmWildcardTypeReference9950 = new BitSet(new long[]{0x0000000000000002L,0x0000000000008000L});
    public static final BitSet FOLLOW_ruleJvmUpperBoundAnded_in_ruleJvmWildcardTypeReference9971 = new BitSet(new long[]{0x0000000000000002L,0x0000000000008000L});
    public static final BitSet FOLLOW_ruleJvmLowerBound_in_ruleJvmWildcardTypeReference10001 = new BitSet(new long[]{0x0000000000000002L,0x0000000000008000L});
    public static final BitSet FOLLOW_ruleJvmLowerBoundAnded_in_ruleJvmWildcardTypeReference10022 = new BitSet(new long[]{0x0000000000000002L,0x0000000000008000L});
    public static final BitSet FOLLOW_ruleJvmUpperBound_in_entryRuleJvmUpperBound10062 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleJvmUpperBound10072 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_71_in_ruleJvmUpperBound10109 = new BitSet(new long[]{0x0400000000000080L,0x0000000000002000L});
    public static final BitSet FOLLOW_ruleJvmTypeReference_in_ruleJvmUpperBound10130 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleJvmUpperBoundAnded_in_entryRuleJvmUpperBoundAnded10166 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleJvmUpperBoundAnded10176 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_79_in_ruleJvmUpperBoundAnded10213 = new BitSet(new long[]{0x0400000000000080L,0x0000000000002000L});
    public static final BitSet FOLLOW_ruleJvmTypeReference_in_ruleJvmUpperBoundAnded10234 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleJvmLowerBound_in_entryRuleJvmLowerBound10270 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleJvmLowerBound10280 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_75_in_ruleJvmLowerBound10317 = new BitSet(new long[]{0x0400000000000080L,0x0000000000002000L});
    public static final BitSet FOLLOW_ruleJvmTypeReference_in_ruleJvmLowerBound10338 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleJvmLowerBoundAnded_in_entryRuleJvmLowerBoundAnded10374 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleJvmLowerBoundAnded10384 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_79_in_ruleJvmLowerBoundAnded10421 = new BitSet(new long[]{0x0400000000000080L,0x0000000000002000L});
    public static final BitSet FOLLOW_ruleJvmTypeReference_in_ruleJvmLowerBoundAnded10442 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleQualifiedNameWithWildcard_in_entryRuleQualifiedNameWithWildcard10481 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleQualifiedNameWithWildcard10492 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleQualifiedName_in_ruleQualifiedNameWithWildcard10539 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_41_in_ruleQualifiedNameWithWildcard10557 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_48_in_ruleQualifiedNameWithWildcard10570 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleValidID_in_entryRuleValidID10611 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleValidID10622 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RULE_ID_in_ruleValidID10661 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleXImportDeclaration_in_entryRuleXImportDeclaration10707 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleXImportDeclaration10717 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_73_in_ruleXImportDeclaration10754 = new BitSet(new long[]{0x0000000000000080L,0x0000000000000100L});
    public static final BitSet FOLLOW_72_in_ruleXImportDeclaration10774 = new BitSet(new long[]{0x0000000000000080L,0x0000000000000400L});
    public static final BitSet FOLLOW_74_in_ruleXImportDeclaration10805 = new BitSet(new long[]{0x0000000000000080L,0x0000000000000400L});
    public static final BitSet FOLLOW_ruleQualifiedNameInStaticImport_in_ruleXImportDeclaration10842 = new BitSet(new long[]{0x0001000000000080L});
    public static final BitSet FOLLOW_48_in_ruleXImportDeclaration10861 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000004L});
    public static final BitSet FOLLOW_ruleValidID_in_ruleXImportDeclaration10901 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000004L});
    public static final BitSet FOLLOW_ruleQualifiedName_in_ruleXImportDeclaration10932 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000004L});
    public static final BitSet FOLLOW_ruleQualifiedNameWithWildcard_in_ruleXImportDeclaration10959 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_ruleXImportDeclaration10973 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleQualifiedNameInStaticImport_in_entryRuleQualifiedNameInStaticImport11012 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_entryRuleQualifiedNameInStaticImport11023 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleValidID_in_ruleQualifiedNameInStaticImport11070 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_41_in_ruleQualifiedNameInStaticImport11088 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_ruleOpCompare_in_synpred1_InternalDSEL1585 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOpOther_in_synpred2_InternalDSEL1756 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOpMultiAssign_in_synpred3_InternalDSEL3206 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOpOr_in_synpred4_InternalDSEL3641 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOpAnd_in_synpred5_InternalDSEL3812 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOpEquality_in_synpred6_InternalDSEL3983 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOpAdd_in_synpred7_InternalDSEL4154 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOpMulti_in_synpred8_InternalDSEL4434 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_52_in_synpred9_InternalDSEL4747 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleOpPostfix_in_synpred10_InternalDSEL4904 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_41_in_synpred11_InternalDSEL5159 = new BitSet(new long[]{0x0000000000000080L,0x0000000000000780L});
    public static final BitSet FOLLOW_55_in_synpred11_InternalDSEL5173 = new BitSet(new long[]{0x0000000000000080L,0x0000000000000780L});
    public static final BitSet FOLLOW_ruleFeatureCallID_in_synpred11_InternalDSEL5189 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_ruleOpSingleAssign_in_synpred11_InternalDSEL5195 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_41_in_synpred12_InternalDSEL5337 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_56_in_synpred12_InternalDSEL5351 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_synpred12_InternalDSEL5371 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_58_in_synpred13_InternalDSEL5598 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleJvmFormalParameter_in_synpred14_InternalDSEL5650 = new BitSet(new long[]{0x0200000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_57_in_synpred14_InternalDSEL5657 = new BitSet(new long[]{0x0400000000000080L,0x0000000000002000L});
    public static final BitSet FOLLOW_ruleJvmFormalParameter_in_synpred14_InternalDSEL5664 = new BitSet(new long[]{0x0200000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_synpred14_InternalDSEL5678 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_63_in_synpred15_InternalDSEL5798 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleJvmFormalParameter_in_synpred17_InternalDSEL6298 = new BitSet(new long[]{0x0200000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_57_in_synpred17_InternalDSEL6305 = new BitSet(new long[]{0x0400000000000080L,0x0000000000002000L});
    public static final BitSet FOLLOW_ruleJvmFormalParameter_in_synpred17_InternalDSEL6312 = new BitSet(new long[]{0x0200000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_synpred17_InternalDSEL6326 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_68_in_synpred19_InternalDSEL7109 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleJvmTypeReference_in_synpred20_InternalDSEL7418 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_ruleValidID_in_synpred20_InternalDSEL7427 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_58_in_synpred21_InternalDSEL7965 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleJvmFormalParameter_in_synpred22_InternalDSEL8017 = new BitSet(new long[]{0x0200000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_57_in_synpred22_InternalDSEL8024 = new BitSet(new long[]{0x0400000000000080L,0x0000000000002000L});
    public static final BitSet FOLLOW_ruleJvmFormalParameter_in_synpred22_InternalDSEL8031 = new BitSet(new long[]{0x0200000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_synpred22_InternalDSEL8045 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_63_in_synpred23_InternalDSEL8165 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_41_in_synpred25_InternalDSEL8874 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ruleArrayBrackets_in_synpred26_InternalDSEL9028 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_23_in_synpred27_InternalDSEL9480 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_41_in_synpred28_InternalDSEL9575 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_23_in_synpred29_InternalDSEL9632 = new BitSet(new long[]{0x0000000000000002L});

}
package com.odcgroup.menu.model.parser.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.parser.antlr.Lexer;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalMenuLexer extends Lexer {
    public static final int RULE_VALUE=6;
    public static final int RULE_ID=4;
    public static final int T__24=24;
    public static final int T__23=23;
    public static final int T__22=22;
    public static final int T__21=21;
    public static final int T__20=20;
    public static final int RULE_URI=7;
    public static final int EOF=-1;
    public static final int RULE_SL_COMMENT=9;
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

    public InternalMenuLexer() {;} 
    public InternalMenuLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public InternalMenuLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g"; }

    // $ANTLR start "T__11"
    public final void mT__11() throws RecognitionException {
        try {
            int _type = T__11;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:11:7: ( 'metamodelVersion' )
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:11:9: 'metamodelVersion'
            {
            match("metamodelVersion"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__11"

    // $ANTLR start "T__12"
    public final void mT__12() throws RecognitionException {
        try {
            int _type = T__12;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:12:7: ( '=' )
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:12:9: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__12"

    // $ANTLR start "T__13"
    public final void mT__13() throws RecognitionException {
        try {
            int _type = T__13;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:13:7: ( 'pageflow' )
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:13:9: 'pageflow'
            {
            match("pageflow"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__13"

    // $ANTLR start "T__14"
    public final void mT__14() throws RecognitionException {
        try {
            int _type = T__14;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:14:7: ( 'enabled' )
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:14:9: 'enabled'
            {
            match("enabled"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__14"

    // $ANTLR start "T__15"
    public final void mT__15() throws RecognitionException {
        try {
            int _type = T__15;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:15:7: ( 'displayTabs' )
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:15:9: 'displayTabs'
            {
            match("displayTabs"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__15"

    // $ANTLR start "T__16"
    public final void mT__16() throws RecognitionException {
        try {
            int _type = T__16;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:16:7: ( 'true' )
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:16:9: 'true'
            {
            match("true"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__16"

    // $ANTLR start "T__17"
    public final void mT__17() throws RecognitionException {
        try {
            int _type = T__17;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:17:7: ( 'securityRole' )
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:17:9: 'securityRole'
            {
            match("securityRole"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__17"

    // $ANTLR start "T__18"
    public final void mT__18() throws RecognitionException {
        try {
            int _type = T__18;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:18:7: ( 'labels' )
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:18:9: 'labels'
            {
            match("labels"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__18"

    // $ANTLR start "T__19"
    public final void mT__19() throws RecognitionException {
        try {
            int _type = T__19;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:19:7: ( ',' )
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:19:9: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__19"

    // $ANTLR start "T__20"
    public final void mT__20() throws RecognitionException {
        try {
            int _type = T__20;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:20:7: ( '{' )
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:20:9: '{'
            {
            match('{'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__20"

    // $ANTLR start "T__21"
    public final void mT__21() throws RecognitionException {
        try {
            int _type = T__21;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:21:7: ( '}' )
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:21:9: '}'
            {
            match('}'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__21"

    // $ANTLR start "T__22"
    public final void mT__22() throws RecognitionException {
        try {
            int _type = T__22;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:22:7: ( 'shortcut' )
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:22:9: 'shortcut'
            {
            match("shortcut"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__22"

    // $ANTLR start "T__23"
    public final void mT__23() throws RecognitionException {
        try {
            int _type = T__23;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:23:7: ( 'false' )
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:23:9: 'false'
            {
            match("false"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__23"

    // $ANTLR start "T__24"
    public final void mT__24() throws RecognitionException {
        try {
            int _type = T__24;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:24:7: ( 'conditional' )
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:24:9: 'conditional'
            {
            match("conditional"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__24"

    // $ANTLR start "RULE_ML_COMMENT"
    public final void mRULE_ML_COMMENT() throws RecognitionException {
        try {
            int _type = RULE_ML_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:524:17: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:524:19: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 

            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:524:24: ( options {greedy=false; } : . )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0=='*') ) {
                    int LA1_1 = input.LA(2);

                    if ( (LA1_1=='/') ) {
                        alt1=2;
                    }
                    else if ( ((LA1_1>='\u0000' && LA1_1<='.')||(LA1_1>='0' && LA1_1<='\uFFFF')) ) {
                        alt1=1;
                    }


                }
                else if ( ((LA1_0>='\u0000' && LA1_0<=')')||(LA1_0>='+' && LA1_0<='\uFFFF')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:524:52: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            match("*/"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_ML_COMMENT"

    // $ANTLR start "RULE_SL_COMMENT"
    public final void mRULE_SL_COMMENT() throws RecognitionException {
        try {
            int _type = RULE_SL_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:526:17: ( '#' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )? )
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:526:19: '#' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )?
            {
            match('#'); 
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:526:23: (~ ( ( '\\n' | '\\r' ) ) )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( ((LA2_0>='\u0000' && LA2_0<='\t')||(LA2_0>='\u000B' && LA2_0<='\f')||(LA2_0>='\u000E' && LA2_0<='\uFFFF')) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:526:23: ~ ( ( '\\n' | '\\r' ) )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\f')||(input.LA(1)>='\u000E' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:526:39: ( ( '\\r' )? '\\n' )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0=='\n'||LA4_0=='\r') ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:526:40: ( '\\r' )? '\\n'
                    {
                    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:526:40: ( '\\r' )?
                    int alt3=2;
                    int LA3_0 = input.LA(1);

                    if ( (LA3_0=='\r') ) {
                        alt3=1;
                    }
                    switch (alt3) {
                        case 1 :
                            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:526:40: '\\r'
                            {
                            match('\r'); 

                            }
                            break;

                    }

                    match('\n'); 

                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_SL_COMMENT"

    // $ANTLR start "RULE_WS"
    public final void mRULE_WS() throws RecognitionException {
        try {
            int _type = RULE_WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:528:9: ( ( ' ' | '\\t' | '\\r' | '\\n' )+ )
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:528:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            {
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:528:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            int cnt5=0;
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( ((LA5_0>='\t' && LA5_0<='\n')||LA5_0=='\r'||LA5_0==' ') ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:
            	    {
            	    if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt5 >= 1 ) break loop5;
                        EarlyExitException eee =
                            new EarlyExitException(5, input);
                        throw eee;
                }
                cnt5++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_WS"

    // $ANTLR start "RULE_ID"
    public final void mRULE_ID() throws RecognitionException {
        try {
            int _type = RULE_ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:530:9: ( ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '-' | '.' | '0' .. '9' )* )
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:530:11: ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '-' | '.' | '0' .. '9' )*
            {
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:530:11: ( '^' )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0=='^') ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:530:11: '^'
                    {
                    match('^'); 

                    }
                    break;

            }

            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:530:40: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '-' | '.' | '0' .. '9' )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( ((LA7_0>='-' && LA7_0<='.')||(LA7_0>='0' && LA7_0<='9')||(LA7_0>='A' && LA7_0<='Z')||LA7_0=='_'||(LA7_0>='a' && LA7_0<='z')) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:
            	    {
            	    if ( (input.LA(1)>='-' && input.LA(1)<='.')||(input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_ID"

    // $ANTLR start "RULE_VALUE"
    public final void mRULE_VALUE() throws RecognitionException {
        try {
            int _type = RULE_VALUE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:532:12: ( ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' | '&' | '/' | '%' | '.' | '-' | '\\u00E9' | '\\u00E8' | '\\u00E0' | '\\u00E4' | '\\u00F6' | '\\u00FC' | '\\u00C4' | '\\u00D6' | '\\u00DC' | '\\u00C9' | '\\u00C8' | '\\u00C0' | '\\u00E7' | '\\u00DF' )+ )
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:532:14: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' | '&' | '/' | '%' | '.' | '-' | '\\u00E9' | '\\u00E8' | '\\u00E0' | '\\u00E4' | '\\u00F6' | '\\u00FC' | '\\u00C4' | '\\u00D6' | '\\u00DC' | '\\u00C9' | '\\u00C8' | '\\u00C0' | '\\u00E7' | '\\u00DF' )+
            {
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:532:14: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' | '&' | '/' | '%' | '.' | '-' | '\\u00E9' | '\\u00E8' | '\\u00E0' | '\\u00E4' | '\\u00F6' | '\\u00FC' | '\\u00C4' | '\\u00D6' | '\\u00DC' | '\\u00C9' | '\\u00C8' | '\\u00C0' | '\\u00E7' | '\\u00DF' )+
            int cnt8=0;
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( ((LA8_0>='%' && LA8_0<='&')||(LA8_0>='-' && LA8_0<='9')||(LA8_0>='A' && LA8_0<='Z')||LA8_0=='_'||(LA8_0>='a' && LA8_0<='z')||LA8_0=='\u00C0'||LA8_0=='\u00C4'||(LA8_0>='\u00C8' && LA8_0<='\u00C9')||LA8_0=='\u00D6'||LA8_0=='\u00DC'||(LA8_0>='\u00DF' && LA8_0<='\u00E0')||LA8_0=='\u00E4'||(LA8_0>='\u00E7' && LA8_0<='\u00E9')||LA8_0=='\u00F6'||LA8_0=='\u00FC') ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:
            	    {
            	    if ( (input.LA(1)>='%' && input.LA(1)<='&')||(input.LA(1)>='-' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z')||input.LA(1)=='\u00C0'||input.LA(1)=='\u00C4'||(input.LA(1)>='\u00C8' && input.LA(1)<='\u00C9')||input.LA(1)=='\u00D6'||input.LA(1)=='\u00DC'||(input.LA(1)>='\u00DF' && input.LA(1)<='\u00E0')||input.LA(1)=='\u00E4'||(input.LA(1)>='\u00E7' && input.LA(1)<='\u00E9')||input.LA(1)=='\u00F6'||input.LA(1)=='\u00FC' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


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


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_VALUE"

    // $ANTLR start "RULE_URI"
    public final void mRULE_URI() throws RecognitionException {
        try {
            int _type = RULE_URI;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:534:10: ( ( 'resource:///' | 'platform:/' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' | '&' | '/' | '%' | '.' | '-' )+ )
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:534:12: ( 'resource:///' | 'platform:/' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' | '&' | '/' | '%' | '.' | '-' )+
            {
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:534:12: ( 'resource:///' | 'platform:/' )
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0=='r') ) {
                alt9=1;
            }
            else if ( (LA9_0=='p') ) {
                alt9=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;
            }
            switch (alt9) {
                case 1 :
                    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:534:13: 'resource:///'
                    {
                    match("resource:///");


                    }
                    break;
                case 2 :
                    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:534:28: 'platform:/'
                    {
                    match("platform:/");


                    }
                    break;

            }

            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:534:42: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' | '&' | '/' | '%' | '.' | '-' )+
            int cnt10=0;
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( ((LA10_0>='%' && LA10_0<='&')||(LA10_0>='-' && LA10_0<='9')||(LA10_0>='A' && LA10_0<='Z')||LA10_0=='_'||(LA10_0>='a' && LA10_0<='z')) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:
            	    {
            	    if ( (input.LA(1)>='%' && input.LA(1)<='&')||(input.LA(1)>='-' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


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


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_URI"

    // $ANTLR start "RULE_STRING"
    public final void mRULE_STRING() throws RecognitionException {
        try {
            int _type = RULE_STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:536:13: ( '\"' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\"' ) ) )* '\"' )
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:536:15: '\"' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\"' ) ) )* '\"'
            {
            match('\"'); 
            // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:536:19: ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\"' ) ) )*
            loop11:
            do {
                int alt11=3;
                int LA11_0 = input.LA(1);

                if ( (LA11_0=='\\') ) {
                    alt11=1;
                }
                else if ( ((LA11_0>='\u0000' && LA11_0<='!')||(LA11_0>='#' && LA11_0<='[')||(LA11_0>=']' && LA11_0<='\uFFFF')) ) {
                    alt11=2;
                }


                switch (alt11) {
            	case 1 :
            	    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:536:20: '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' )
            	    {
            	    match('\\'); 
            	    if ( input.LA(1)=='\"'||input.LA(1)=='\''||input.LA(1)=='\\'||input.LA(1)=='b'||input.LA(1)=='f'||input.LA(1)=='n'||input.LA(1)=='r'||input.LA(1)=='t' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;
            	case 2 :
            	    // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:536:61: ~ ( ( '\\\\' | '\"' ) )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='!')||(input.LA(1)>='#' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
		    break loop11;
                }
            } while (true);

            match('\"'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_STRING"

    public void mTokens() throws RecognitionException {
        // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:1:8: ( T__11 | T__12 | T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_ID | RULE_VALUE | RULE_URI | RULE_STRING )
        int alt12=21;
        alt12 = dfa12.predict(input);
        switch (alt12) {
            case 1 :
                // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:1:10: T__11
                {
                mT__11(); 

                }
                break;
            case 2 :
                // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:1:16: T__12
                {
                mT__12(); 

                }
                break;
            case 3 :
                // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:1:22: T__13
                {
                mT__13(); 

                }
                break;
            case 4 :
                // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:1:28: T__14
                {
                mT__14(); 

                }
                break;
            case 5 :
                // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:1:34: T__15
                {
                mT__15(); 

                }
                break;
            case 6 :
                // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:1:40: T__16
                {
                mT__16(); 

                }
                break;
            case 7 :
                // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:1:46: T__17
                {
                mT__17(); 

                }
                break;
            case 8 :
                // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:1:52: T__18
                {
                mT__18(); 

                }
                break;
            case 9 :
                // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:1:58: T__19
                {
                mT__19(); 

                }
                break;
            case 10 :
                // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:1:64: T__20
                {
                mT__20(); 

                }
                break;
            case 11 :
                // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:1:70: T__21
                {
                mT__21(); 

                }
                break;
            case 12 :
                // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:1:76: T__22
                {
                mT__22(); 

                }
                break;
            case 13 :
                // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:1:82: T__23
                {
                mT__23(); 

                }
                break;
            case 14 :
                // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:1:88: T__24
                {
                mT__24(); 

                }
                break;
            case 15 :
                // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:1:94: RULE_ML_COMMENT
                {
                mRULE_ML_COMMENT(); 

                }
                break;
            case 16 :
                // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:1:110: RULE_SL_COMMENT
                {
                mRULE_SL_COMMENT(); 

                }
                break;
            case 17 :
                // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:1:126: RULE_WS
                {
                mRULE_WS(); 

                }
                break;
            case 18 :
                // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:1:134: RULE_ID
                {
                mRULE_ID(); 

                }
                break;
            case 19 :
                // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:1:142: RULE_VALUE
                {
                mRULE_VALUE(); 

                }
                break;
            case 20 :
                // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:1:153: RULE_URI
                {
                mRULE_URI(); 

                }
                break;
            case 21 :
                // ../com.odcgroup.menu.model/src-gen/com/odcgroup/menu/model/parser/antlr/internal/InternalMenu.g:1:162: RULE_STRING
                {
                mRULE_STRING(); 

                }
                break;

        }

    }


    protected DFA12 dfa12 = new DFA12(this);
    static final String DFA12_eotS =
        "\1\uffff\1\21\1\uffff\6\21\3\uffff\2\21\1\23\3\uffff\1\21\1\uffff"+
        "\1\21\1\uffff\14\21\1\uffff\22\21\1\101\13\21\1\uffff\3\21\1\120"+
        "\11\21\1\132\1\uffff\5\21\1\140\3\21\1\uffff\3\21\1\147\1\21\1\uffff"+
        "\2\21\1\153\3\21\2\uffff\2\21\1\uffff\6\21\1\166\1\21\1\170\1\21"+
        "\1\uffff\1\172\1\uffff\1\21\1\uffff\2\21\1\176\1\uffff";
    static final String DFA12_eofS =
        "\177\uffff";
    static final String DFA12_minS =
        "\1\11\1\45\1\uffff\6\45\3\uffff\2\45\1\52\3\uffff\1\45\1\uffff"+
        "\1\45\1\uffff\14\45\1\uffff\36\45\1\uffff\16\45\1\uffff\11\45\1"+
        "\uffff\5\45\1\uffff\6\45\2\uffff\2\45\1\uffff\12\45\1\uffff\1\45"+
        "\1\uffff\1\45\1\uffff\3\45\1\uffff";
    static final String DFA12_maxS =
        "\2\u00fc\1\uffff\6\u00fc\3\uffff\2\u00fc\1\52\3\uffff\1\u00fc\1"+
        "\uffff\1\u00fc\1\uffff\14\u00fc\1\uffff\36\u00fc\1\uffff\16\u00fc"+
        "\1\uffff\11\u00fc\1\uffff\5\u00fc\1\uffff\6\u00fc\2\uffff\2\u00fc"+
        "\1\uffff\12\u00fc\1\uffff\1\u00fc\1\uffff\1\u00fc\1\uffff\3\u00fc"+
        "\1\uffff";
    static final String DFA12_acceptS =
        "\2\uffff\1\2\6\uffff\1\11\1\12\1\13\3\uffff\1\20\1\21\1\22\1\uffff"+
        "\1\23\1\uffff\1\25\14\uffff\1\17\36\uffff\1\6\16\uffff\1\15\11\uffff"+
        "\1\10\5\uffff\1\4\6\uffff\1\3\1\24\2\uffff\1\14\12\uffff\1\5\1\uffff"+
        "\1\16\1\uffff\1\7\3\uffff\1\1";
    static final String DFA12_specialS =
        "\177\uffff}>";
    static final String[] DFA12_transitionS = {
            "\2\20\2\uffff\1\20\22\uffff\1\20\1\uffff\1\25\1\17\1\uffff"+
            "\2\23\5\uffff\1\11\2\23\1\16\12\23\3\uffff\1\2\3\uffff\32\24"+
            "\3\uffff\1\21\1\24\1\uffff\2\24\1\15\1\5\1\4\1\14\5\24\1\10"+
            "\1\1\2\24\1\3\1\24\1\22\1\7\1\6\6\24\1\12\1\uffff\1\13\102\uffff"+
            "\1\23\3\uffff\1\23\3\uffff\2\23\14\uffff\1\23\5\uffff\1\23\2"+
            "\uffff\2\23\3\uffff\1\23\2\uffff\3\23\14\uffff\1\23\5\uffff"+
            "\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\4\27\1\26\25\27\105\uffff\1\23\3\uffff\1\23\3\uffff\2"+
            "\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2\uffff"+
            "\3\23\14\uffff\1\23\5\uffff\1\23",
            "",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\1\30\12\27\1\31\16\27\105\uffff\1\23\3\uffff\1\23\3\uffff"+
            "\2\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2"+
            "\uffff\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\15\27\1\32\14\27\105\uffff\1\23\3\uffff\1\23\3\uffff"+
            "\2\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2"+
            "\uffff\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\10\27\1\33\21\27\105\uffff\1\23\3\uffff\1\23\3\uffff"+
            "\2\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2"+
            "\uffff\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\21\27\1\34\10\27\105\uffff\1\23\3\uffff\1\23\3\uffff"+
            "\2\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2"+
            "\uffff\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\4\27\1\35\2\27\1\36\22\27\105\uffff\1\23\3\uffff\1\23"+
            "\3\uffff\2\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff"+
            "\1\23\2\uffff\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\1\37\31\27\105\uffff\1\23\3\uffff\1\23\3\uffff\2\23\14"+
            "\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2\uffff\3"+
            "\23\14\uffff\1\23\5\uffff\1\23",
            "",
            "",
            "",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\1\40\31\27\105\uffff\1\23\3\uffff\1\23\3\uffff\2\23\14"+
            "\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2\uffff\3"+
            "\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\16\27\1\41\13\27\105\uffff\1\23\3\uffff\1\23\3\uffff"+
            "\2\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2"+
            "\uffff\3\23\14\uffff\1\23\5\uffff\1\23",
            "\1\42",
            "",
            "",
            "",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\4\27\1\43\25\27\105\uffff\1\23\3\uffff\1\23\3\uffff\2"+
            "\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2\uffff"+
            "\3\23\14\uffff\1\23\5\uffff\1\23",
            "",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\32\27\105\uffff\1\23\3\uffff\1\23\3\uffff\2\23\14\uffff"+
            "\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2\uffff\3\23\14"+
            "\uffff\1\23\5\uffff\1\23",
            "",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\23\27\1\44\6\27\105\uffff\1\23\3\uffff\1\23\3\uffff\2"+
            "\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2\uffff"+
            "\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\32\27\105\uffff\1\23\3\uffff\1\23\3\uffff\2\23\14\uffff"+
            "\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2\uffff\3\23\14"+
            "\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\6\27\1\45\23\27\105\uffff\1\23\3\uffff\1\23\3\uffff\2"+
            "\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2\uffff"+
            "\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\1\46\31\27\105\uffff\1\23\3\uffff\1\23\3\uffff\2\23\14"+
            "\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2\uffff\3"+
            "\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\1\47\31\27\105\uffff\1\23\3\uffff\1\23\3\uffff\2\23\14"+
            "\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2\uffff\3"+
            "\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\22\27\1\50\7\27\105\uffff\1\23\3\uffff\1\23\3\uffff\2"+
            "\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2\uffff"+
            "\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\24\27\1\51\5\27\105\uffff\1\23\3\uffff\1\23\3\uffff\2"+
            "\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2\uffff"+
            "\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\2\27\1\52\27\27\105\uffff\1\23\3\uffff\1\23\3\uffff\2"+
            "\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2\uffff"+
            "\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\16\27\1\53\13\27\105\uffff\1\23\3\uffff\1\23\3\uffff"+
            "\2\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2"+
            "\uffff\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\1\27\1\54\30\27\105\uffff\1\23\3\uffff\1\23\3\uffff\2"+
            "\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2\uffff"+
            "\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\13\27\1\55\16\27\105\uffff\1\23\3\uffff\1\23\3\uffff"+
            "\2\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2"+
            "\uffff\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\15\27\1\56\14\27\105\uffff\1\23\3\uffff\1\23\3\uffff"+
            "\2\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2"+
            "\uffff\3\23\14\uffff\1\23\5\uffff\1\23",
            "",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\22\27\1\57\7\27\105\uffff\1\23\3\uffff\1\23\3\uffff\2"+
            "\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2\uffff"+
            "\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\1\60\31\27\105\uffff\1\23\3\uffff\1\23\3\uffff\2\23\14"+
            "\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2\uffff\3"+
            "\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\4\27\1\61\25\27\105\uffff\1\23\3\uffff\1\23\3\uffff\2"+
            "\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2\uffff"+
            "\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\23\27\1\62\6\27\105\uffff\1\23\3\uffff\1\23\3\uffff\2"+
            "\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2\uffff"+
            "\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\1\27\1\63\30\27\105\uffff\1\23\3\uffff\1\23\3\uffff\2"+
            "\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2\uffff"+
            "\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\17\27\1\64\12\27\105\uffff\1\23\3\uffff\1\23\3\uffff"+
            "\2\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2"+
            "\uffff\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\4\27\1\65\25\27\105\uffff\1\23\3\uffff\1\23\3\uffff\2"+
            "\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2\uffff"+
            "\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\24\27\1\66\5\27\105\uffff\1\23\3\uffff\1\23\3\uffff\2"+
            "\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2\uffff"+
            "\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\21\27\1\67\10\27\105\uffff\1\23\3\uffff\1\23\3\uffff"+
            "\2\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2"+
            "\uffff\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\4\27\1\70\25\27\105\uffff\1\23\3\uffff\1\23\3\uffff\2"+
            "\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2\uffff"+
            "\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\22\27\1\71\7\27\105\uffff\1\23\3\uffff\1\23\3\uffff\2"+
            "\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2\uffff"+
            "\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\3\27\1\72\26\27\105\uffff\1\23\3\uffff\1\23\3\uffff\2"+
            "\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2\uffff"+
            "\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\16\27\1\73\13\27\105\uffff\1\23\3\uffff\1\23\3\uffff"+
            "\2\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2"+
            "\uffff\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\14\27\1\74\15\27\105\uffff\1\23\3\uffff\1\23\3\uffff"+
            "\2\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2"+
            "\uffff\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\5\27\1\75\24\27\105\uffff\1\23\3\uffff\1\23\3\uffff\2"+
            "\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2\uffff"+
            "\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\5\27\1\76\24\27\105\uffff\1\23\3\uffff\1\23\3\uffff\2"+
            "\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2\uffff"+
            "\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\13\27\1\77\16\27\105\uffff\1\23\3\uffff\1\23\3\uffff"+
            "\2\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2"+
            "\uffff\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\13\27\1\100\16\27\105\uffff\1\23\3\uffff\1\23\3\uffff"+
            "\2\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2"+
            "\uffff\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\32\27\105\uffff\1\23\3\uffff\1\23\3\uffff\2\23\14\uffff"+
            "\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2\uffff\3\23\14"+
            "\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\21\27\1\102\10\27\105\uffff\1\23\3\uffff\1\23\3\uffff"+
            "\2\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2"+
            "\uffff\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\23\27\1\103\6\27\105\uffff\1\23\3\uffff\1\23\3\uffff"+
            "\2\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2"+
            "\uffff\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\13\27\1\104\16\27\105\uffff\1\23\3\uffff\1\23\3\uffff"+
            "\2\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2"+
            "\uffff\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\4\27\1\105\25\27\105\uffff\1\23\3\uffff\1\23\3\uffff"+
            "\2\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2"+
            "\uffff\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\10\27\1\106\21\27\105\uffff\1\23\3\uffff\1\23\3\uffff"+
            "\2\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2"+
            "\uffff\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\24\27\1\107\5\27\105\uffff\1\23\3\uffff\1\23\3\uffff"+
            "\2\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2"+
            "\uffff\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\16\27\1\110\13\27\105\uffff\1\23\3\uffff\1\23\3\uffff"+
            "\2\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2"+
            "\uffff\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\13\27\1\111\16\27\105\uffff\1\23\3\uffff\1\23\3\uffff"+
            "\2\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2"+
            "\uffff\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\16\27\1\112\13\27\105\uffff\1\23\3\uffff\1\23\3\uffff"+
            "\2\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2"+
            "\uffff\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\4\27\1\113\25\27\105\uffff\1\23\3\uffff\1\23\3\uffff"+
            "\2\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2"+
            "\uffff\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\1\114\31\27\105\uffff\1\23\3\uffff\1\23\3\uffff\2\23"+
            "\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2\uffff"+
            "\3\23\14\uffff\1\23\5\uffff\1\23",
            "",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\10\27\1\115\21\27\105\uffff\1\23\3\uffff\1\23\3\uffff"+
            "\2\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2"+
            "\uffff\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\2\27\1\116\27\27\105\uffff\1\23\3\uffff\1\23\3\uffff"+
            "\2\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2"+
            "\uffff\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\22\27\1\117\7\27\105\uffff\1\23\3\uffff\1\23\3\uffff"+
            "\2\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2"+
            "\uffff\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\32\27\105\uffff\1\23\3\uffff\1\23\3\uffff\2\23\14\uffff"+
            "\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2\uffff\3\23\14"+
            "\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\23\27\1\121\6\27\105\uffff\1\23\3\uffff\1\23\3\uffff"+
            "\2\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2"+
            "\uffff\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\21\27\1\122\10\27\105\uffff\1\23\3\uffff\1\23\3\uffff"+
            "\2\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2"+
            "\uffff\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\3\27\1\123\26\27\105\uffff\1\23\3\uffff\1\23\3\uffff"+
            "\2\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2"+
            "\uffff\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\16\27\1\124\13\27\105\uffff\1\23\3\uffff\1\23\3\uffff"+
            "\2\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2"+
            "\uffff\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\21\27\1\125\10\27\105\uffff\1\23\3\uffff\1\23\3\uffff"+
            "\2\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2"+
            "\uffff\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\3\27\1\126\26\27\105\uffff\1\23\3\uffff\1\23\3\uffff"+
            "\2\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2"+
            "\uffff\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\30\27\1\127\1\27\105\uffff\1\23\3\uffff\1\23\3\uffff"+
            "\2\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2"+
            "\uffff\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\23\27\1\130\6\27\105\uffff\1\23\3\uffff\1\23\3\uffff"+
            "\2\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2"+
            "\uffff\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\24\27\1\131\5\27\105\uffff\1\23\3\uffff\1\23\3\uffff"+
            "\2\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2"+
            "\uffff\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\32\27\105\uffff\1\23\3\uffff\1\23\3\uffff\2\23\14\uffff"+
            "\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2\uffff\3\23\14"+
            "\uffff\1\23\5\uffff\1\23",
            "",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\10\27\1\133\21\27\105\uffff\1\23\3\uffff\1\23\3\uffff"+
            "\2\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2"+
            "\uffff\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\2\27\1\134\27\27\105\uffff\1\23\3\uffff\1\23\3\uffff"+
            "\2\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2"+
            "\uffff\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\4\27\1\135\25\27\105\uffff\1\23\3\uffff\1\23\3\uffff"+
            "\2\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2"+
            "\uffff\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\26\27\1\136\3\27\105\uffff\1\23\3\uffff\1\23\3\uffff"+
            "\2\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2"+
            "\uffff\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\14\27\1\137\15\27\105\uffff\1\23\3\uffff\1\23\3\uffff"+
            "\2\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2"+
            "\uffff\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\32\27\105\uffff\1\23\3\uffff\1\23\3\uffff\2\23\14\uffff"+
            "\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2\uffff\3\23\14"+
            "\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\23\27\1\141\6\27\4\uffff"+
            "\1\27\1\uffff\32\27\105\uffff\1\23\3\uffff\1\23\3\uffff\2\23"+
            "\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2\uffff"+
            "\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\30\27\1\142\1\27\105\uffff\1\23\3\uffff\1\23\3\uffff"+
            "\2\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2"+
            "\uffff\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\23\27\1\143\6\27\105\uffff\1\23\3\uffff\1\23\3\uffff"+
            "\2\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2"+
            "\uffff\3\23\14\uffff\1\23\5\uffff\1\23",
            "",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\16\27\1\144\13\27\105\uffff\1\23\3\uffff\1\23\3\uffff"+
            "\2\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2"+
            "\uffff\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\4\27\1\145\25\27\105\uffff\1\23\3\uffff\1\23\3\uffff"+
            "\2\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2"+
            "\uffff\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\13\27\1\146\16\27\105\uffff\1\23\3\uffff\1\23\3\uffff"+
            "\2\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2"+
            "\uffff\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\32\27\105\uffff\1\23\3\uffff\1\23\3\uffff\2\23\14\uffff"+
            "\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2\uffff\3\23\14"+
            "\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\1\150\6\uffff\32\27\4\uffff"+
            "\1\27\1\uffff\32\27\105\uffff\1\23\3\uffff\1\23\3\uffff\2\23"+
            "\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2\uffff"+
            "\3\23\14\uffff\1\23\5\uffff\1\23",
            "",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\1\151\31\27\105\uffff\1\23\3\uffff\1\23\3\uffff\2\23"+
            "\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2\uffff"+
            "\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\21\27\1\152\10\27\4"+
            "\uffff\1\27\1\uffff\32\27\105\uffff\1\23\3\uffff\1\23\3\uffff"+
            "\2\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2"+
            "\uffff\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\32\27\105\uffff\1\23\3\uffff\1\23\3\uffff\2\23\14\uffff"+
            "\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2\uffff\3\23\14"+
            "\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\15\27\1\154\14\27\105\uffff\1\23\3\uffff\1\23\3\uffff"+
            "\2\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2"+
            "\uffff\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\1\150\6\uffff\32\27\4\uffff"+
            "\1\27\1\uffff\32\27\105\uffff\1\23\3\uffff\1\23\3\uffff\2\23"+
            "\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2\uffff"+
            "\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\25\27\1\155\4\27\4\uffff"+
            "\1\27\1\uffff\32\27\105\uffff\1\23\3\uffff\1\23\3\uffff\2\23"+
            "\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2\uffff"+
            "\3\23\14\uffff\1\23\5\uffff\1\23",
            "",
            "",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\1\27\1\156\30\27\105\uffff\1\23\3\uffff\1\23\3\uffff"+
            "\2\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2"+
            "\uffff\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\16\27\1\157\13\27\105\uffff\1\23\3\uffff\1\23\3\uffff"+
            "\2\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2"+
            "\uffff\3\23\14\uffff\1\23\5\uffff\1\23",
            "",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\1\160\31\27\105\uffff\1\23\3\uffff\1\23\3\uffff\2\23"+
            "\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2\uffff"+
            "\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\4\27\1\161\25\27\105\uffff\1\23\3\uffff\1\23\3\uffff"+
            "\2\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2"+
            "\uffff\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\22\27\1\162\7\27\105\uffff\1\23\3\uffff\1\23\3\uffff"+
            "\2\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2"+
            "\uffff\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\13\27\1\163\16\27\105\uffff\1\23\3\uffff\1\23\3\uffff"+
            "\2\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2"+
            "\uffff\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\13\27\1\164\16\27\105\uffff\1\23\3\uffff\1\23\3\uffff"+
            "\2\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2"+
            "\uffff\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\21\27\1\165\10\27\105\uffff\1\23\3\uffff\1\23\3\uffff"+
            "\2\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2"+
            "\uffff\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\32\27\105\uffff\1\23\3\uffff\1\23\3\uffff\2\23\14\uffff"+
            "\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2\uffff\3\23\14"+
            "\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\4\27\1\167\25\27\105\uffff\1\23\3\uffff\1\23\3\uffff"+
            "\2\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2"+
            "\uffff\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\32\27\105\uffff\1\23\3\uffff\1\23\3\uffff\2\23\14\uffff"+
            "\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2\uffff\3\23\14"+
            "\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\22\27\1\171\7\27\105\uffff\1\23\3\uffff\1\23\3\uffff"+
            "\2\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2"+
            "\uffff\3\23\14\uffff\1\23\5\uffff\1\23",
            "",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\32\27\105\uffff\1\23\3\uffff\1\23\3\uffff\2\23\14\uffff"+
            "\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2\uffff\3\23\14"+
            "\uffff\1\23\5\uffff\1\23",
            "",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\10\27\1\173\21\27\105\uffff\1\23\3\uffff\1\23\3\uffff"+
            "\2\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2"+
            "\uffff\3\23\14\uffff\1\23\5\uffff\1\23",
            "",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\16\27\1\174\13\27\105\uffff\1\23\3\uffff\1\23\3\uffff"+
            "\2\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2"+
            "\uffff\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\15\27\1\175\14\27\105\uffff\1\23\3\uffff\1\23\3\uffff"+
            "\2\23\14\uffff\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2"+
            "\uffff\3\23\14\uffff\1\23\5\uffff\1\23",
            "\2\23\6\uffff\2\27\1\23\12\27\7\uffff\32\27\4\uffff\1\27\1"+
            "\uffff\32\27\105\uffff\1\23\3\uffff\1\23\3\uffff\2\23\14\uffff"+
            "\1\23\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2\uffff\3\23\14"+
            "\uffff\1\23\5\uffff\1\23",
            ""
    };

    static final short[] DFA12_eot = DFA.unpackEncodedString(DFA12_eotS);
    static final short[] DFA12_eof = DFA.unpackEncodedString(DFA12_eofS);
    static final char[] DFA12_min = DFA.unpackEncodedStringToUnsignedChars(DFA12_minS);
    static final char[] DFA12_max = DFA.unpackEncodedStringToUnsignedChars(DFA12_maxS);
    static final short[] DFA12_accept = DFA.unpackEncodedString(DFA12_acceptS);
    static final short[] DFA12_special = DFA.unpackEncodedString(DFA12_specialS);
    static final short[][] DFA12_transition;

    static {
        int numStates = DFA12_transitionS.length;
        DFA12_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA12_transition[i] = DFA.unpackEncodedString(DFA12_transitionS[i]);
        }
    }

    class DFA12 extends DFA {

        public DFA12(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 12;
            this.eot = DFA12_eot;
            this.eof = DFA12_eof;
            this.min = DFA12_min;
            this.max = DFA12_max;
            this.accept = DFA12_accept;
            this.special = DFA12_special;
            this.transition = DFA12_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__11 | T__12 | T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_ID | RULE_VALUE | RULE_URI | RULE_STRING );";
        }
    }
 

}
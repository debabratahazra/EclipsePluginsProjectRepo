package com.odcgroup.page.parser.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.parser.antlr.Lexer;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalPageLexer extends Lexer {
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
    public static final int EOF=-1;
    public static final int RULE_SL_COMMENT=9;
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

    public InternalPageLexer() {;} 
    public InternalPageLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public InternalPageLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g"; }

    // $ANTLR start "T__11"
    public final void mT__11() throws RecognitionException {
        try {
            int _type = T__11;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:11:7: ( 'metamodelVersion' )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:11:9: 'metamodelVersion'
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
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:12:7: ( '=' )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:12:9: '='
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
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:13:7: ( '---' )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:13:9: '---'
            {
            match("---"); 


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
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:14:7: ( ':' )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:14:9: ':'
            {
            match(':'); 

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
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:15:7: ( 'labels' )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:15:9: 'labels'
            {
            match("labels"); 


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
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:16:7: ( ',' )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:16:9: ','
            {
            match(','); 

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
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:17:7: ( 'toolTips' )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:17:9: 'toolTips'
            {
            match("toolTips"); 


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
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:18:7: ( '{' )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:18:9: '{'
            {
            match('{'); 

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
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:19:7: ( '}' )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:19:9: '}'
            {
            match('}'); 

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
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:20:7: ( '!' )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:20:9: '!'
            {
            match('!'); 

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
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:21:7: ( 'Event' )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:21:9: 'Event'
            {
            match("Event"); 


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
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:22:7: ( '(' )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:22:9: '('
            {
            match('('); 

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
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:23:7: ( ')' )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:23:9: ')'
            {
            match(')'); 

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
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:24:7: ( '//' )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:24:9: '//'
            {
            match("//"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__24"

    // $ANTLR start "T__25"
    public final void mT__25() throws RecognitionException {
        try {
            int _type = T__25;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:25:7: ( 'messages' )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:25:9: 'messages'
            {
            match("messages"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__25"

    // $ANTLR start "T__26"
    public final void mT__26() throws RecognitionException {
        try {
            int _type = T__26;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:26:7: ( 'Snippet' )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:26:9: 'Snippet'
            {
            match("Snippet"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__26"

    // $ANTLR start "T__27"
    public final void mT__27() throws RecognitionException {
        try {
            int _type = T__27;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:27:7: ( 'ud' )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:27:9: 'ud'
            {
            match("ud"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__27"

    // $ANTLR start "T__28"
    public final void mT__28() throws RecognitionException {
        try {
            int _type = T__28;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:28:7: ( 'A' )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:28:9: 'A'
            {
            match('A'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__28"

    // $ANTLR start "T__29"
    public final void mT__29() throws RecognitionException {
        try {
            int _type = T__29;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:29:7: ( 'S' )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:29:9: 'S'
            {
            match('S'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__29"

    // $ANTLR start "RULE_ML_COMMENT"
    public final void mRULE_ML_COMMENT() throws RecognitionException {
        try {
            int _type = RULE_ML_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1148:17: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1148:19: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 

            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1148:24: ( options {greedy=false; } : . )*
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
            	    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1148:52: .
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
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1150:17: ( '#' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )? )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1150:19: '#' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )?
            {
            match('#'); 
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1150:23: (~ ( ( '\\n' | '\\r' ) ) )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( ((LA2_0>='\u0000' && LA2_0<='\t')||(LA2_0>='\u000B' && LA2_0<='\f')||(LA2_0>='\u000E' && LA2_0<='\uFFFF')) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1150:23: ~ ( ( '\\n' | '\\r' ) )
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

            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1150:39: ( ( '\\r' )? '\\n' )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0=='\n'||LA4_0=='\r') ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1150:40: ( '\\r' )? '\\n'
                    {
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1150:40: ( '\\r' )?
                    int alt3=2;
                    int LA3_0 = input.LA(1);

                    if ( (LA3_0=='\r') ) {
                        alt3=1;
                    }
                    switch (alt3) {
                        case 1 :
                            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1150:40: '\\r'
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
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1152:9: ( ( ' ' | '\\t' | '\\r' | '\\n' )+ )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1152:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            {
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1152:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
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
            	    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:
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
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1154:9: ( ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '-' | '.' | '0' .. '9' )* )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1154:11: ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '-' | '.' | '0' .. '9' )*
            {
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1154:11: ( '^' )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0=='^') ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1154:11: '^'
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

            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1154:40: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '-' | '.' | '0' .. '9' )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( ((LA7_0>='-' && LA7_0<='.')||(LA7_0>='0' && LA7_0<='9')||(LA7_0>='A' && LA7_0<='Z')||LA7_0=='_'||(LA7_0>='a' && LA7_0<='z')) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:
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
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1156:12: ( ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' | '&' | '/' | '%' | '.' | '-' | '\\u00E9' | '\\u00E8' | '\\u00E0' | '\\u00E4' | '\\u00F6' | '\\u00FC' | '\\u00C4' | '\\u00D6' | '\\u00DC' | '\\u00C9' | '\\u00C8' | '\\u00C0' | '\\u00E7' | '\\u00DF' )+ )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1156:14: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' | '&' | '/' | '%' | '.' | '-' | '\\u00E9' | '\\u00E8' | '\\u00E0' | '\\u00E4' | '\\u00F6' | '\\u00FC' | '\\u00C4' | '\\u00D6' | '\\u00DC' | '\\u00C9' | '\\u00C8' | '\\u00C0' | '\\u00E7' | '\\u00DF' )+
            {
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1156:14: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' | '&' | '/' | '%' | '.' | '-' | '\\u00E9' | '\\u00E8' | '\\u00E0' | '\\u00E4' | '\\u00F6' | '\\u00FC' | '\\u00C4' | '\\u00D6' | '\\u00DC' | '\\u00C9' | '\\u00C8' | '\\u00C0' | '\\u00E7' | '\\u00DF' )+
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
            	    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:
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
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1158:10: ( ( 'resource:///' | 'platform:/' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' | '&' | '/' | '%' | '.' | '-' )+ )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1158:12: ( 'resource:///' | 'platform:/' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' | '&' | '/' | '%' | '.' | '-' )+
            {
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1158:12: ( 'resource:///' | 'platform:/' )
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
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1158:13: 'resource:///'
                    {
                    match("resource:///"); 


                    }
                    break;
                case 2 :
                    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1158:28: 'platform:/'
                    {
                    match("platform:/"); 


                    }
                    break;

            }

            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1158:42: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' | '&' | '/' | '%' | '.' | '-' )+
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
            	    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:
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
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1160:13: ( '\"' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\"' ) ) )* '\"' )
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1160:15: '\"' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\"' ) ) )* '\"'
            {
            match('\"'); 
            // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1160:19: ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\"' ) ) )*
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
            	    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1160:20: '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\"' | '\\'' | '\\\\' )
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
            	    // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1160:61: ~ ( ( '\\\\' | '\"' ) )
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
        // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1:8: ( T__11 | T__12 | T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_ID | RULE_VALUE | RULE_URI | RULE_STRING )
        int alt12=26;
        alt12 = dfa12.predict(input);
        switch (alt12) {
            case 1 :
                // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1:10: T__11
                {
                mT__11(); 

                }
                break;
            case 2 :
                // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1:16: T__12
                {
                mT__12(); 

                }
                break;
            case 3 :
                // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1:22: T__13
                {
                mT__13(); 

                }
                break;
            case 4 :
                // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1:28: T__14
                {
                mT__14(); 

                }
                break;
            case 5 :
                // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1:34: T__15
                {
                mT__15(); 

                }
                break;
            case 6 :
                // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1:40: T__16
                {
                mT__16(); 

                }
                break;
            case 7 :
                // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1:46: T__17
                {
                mT__17(); 

                }
                break;
            case 8 :
                // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1:52: T__18
                {
                mT__18(); 

                }
                break;
            case 9 :
                // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1:58: T__19
                {
                mT__19(); 

                }
                break;
            case 10 :
                // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1:64: T__20
                {
                mT__20(); 

                }
                break;
            case 11 :
                // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1:70: T__21
                {
                mT__21(); 

                }
                break;
            case 12 :
                // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1:76: T__22
                {
                mT__22(); 

                }
                break;
            case 13 :
                // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1:82: T__23
                {
                mT__23(); 

                }
                break;
            case 14 :
                // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1:88: T__24
                {
                mT__24(); 

                }
                break;
            case 15 :
                // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1:94: T__25
                {
                mT__25(); 

                }
                break;
            case 16 :
                // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1:100: T__26
                {
                mT__26(); 

                }
                break;
            case 17 :
                // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1:106: T__27
                {
                mT__27(); 

                }
                break;
            case 18 :
                // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1:112: T__28
                {
                mT__28(); 

                }
                break;
            case 19 :
                // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1:118: T__29
                {
                mT__29(); 

                }
                break;
            case 20 :
                // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1:124: RULE_ML_COMMENT
                {
                mRULE_ML_COMMENT(); 

                }
                break;
            case 21 :
                // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1:140: RULE_SL_COMMENT
                {
                mRULE_SL_COMMENT(); 

                }
                break;
            case 22 :
                // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1:156: RULE_WS
                {
                mRULE_WS(); 

                }
                break;
            case 23 :
                // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1:164: RULE_ID
                {
                mRULE_ID(); 

                }
                break;
            case 24 :
                // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1:172: RULE_VALUE
                {
                mRULE_VALUE(); 

                }
                break;
            case 25 :
                // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1:183: RULE_URI
                {
                mRULE_URI(); 

                }
                break;
            case 26 :
                // ../com.odcgroup.page.model.dsl/src-gen/com/odcgroup/page/parser/antlr/internal/InternalPage.g:1:192: RULE_STRING
                {
                mRULE_STRING(); 

                }
                break;

        }

    }


    protected DFA12 dfa12 = new DFA12(this);
    static final String DFA12_eotS =
        "\1\uffff\1\24\1\uffff\1\26\1\uffff\1\24\1\uffff\1\24\3\uffff\1"+
        "\24\2\uffff\1\26\1\43\1\24\1\45\3\uffff\1\24\1\uffff\2\24\1\uffff"+
        "\2\24\1\26\3\24\1\56\1\uffff\1\24\1\uffff\1\60\1\uffff\4\24\1\65"+
        "\3\24\1\uffff\1\24\1\uffff\4\24\1\uffff\12\24\1\110\5\24\1\116\1"+
        "\24\1\uffff\5\24\1\uffff\1\24\1\126\3\24\1\132\1\133\1\uffff\3\24"+
        "\3\uffff\6\24\1\144\1\uffff";
    static final String DFA12_eofS =
        "\145\uffff";
    static final String DFA12_minS =
        "\1\11\1\45\1\uffff\1\55\1\uffff\1\45\1\uffff\1\45\3\uffff\1\45"+
        "\2\uffff\1\52\3\45\3\uffff\1\45\1\uffff\2\45\1\uffff\2\45\1\55\4"+
        "\45\1\uffff\1\45\1\uffff\1\45\1\uffff\10\45\1\uffff\1\45\1\uffff"+
        "\4\45\1\uffff\22\45\1\uffff\5\45\1\uffff\7\45\1\uffff\3\45\3\uffff"+
        "\7\45\1\uffff";
    static final String DFA12_maxS =
        "\2\u00fc\1\uffff\1\55\1\uffff\1\u00fc\1\uffff\1\u00fc\3\uffff\1"+
        "\u00fc\2\uffff\1\57\3\u00fc\3\uffff\1\u00fc\1\uffff\2\u00fc\1\uffff"+
        "\2\u00fc\1\55\4\u00fc\1\uffff\1\u00fc\1\uffff\1\u00fc\1\uffff\10"+
        "\u00fc\1\uffff\1\u00fc\1\uffff\4\u00fc\1\uffff\22\u00fc\1\uffff"+
        "\5\u00fc\1\uffff\7\u00fc\1\uffff\3\u00fc\3\uffff\7\u00fc\1\uffff";
    static final String DFA12_acceptS =
        "\2\uffff\1\2\1\uffff\1\4\1\uffff\1\6\1\uffff\1\10\1\11\1\12\1\uffff"+
        "\1\14\1\15\4\uffff\1\25\1\26\1\27\1\uffff\1\30\2\uffff\1\32\7\uffff"+
        "\1\24\1\uffff\1\23\1\uffff\1\22\10\uffff\1\16\1\uffff\1\21\4\uffff"+
        "\1\3\22\uffff\1\13\5\uffff\1\5\7\uffff\1\20\3\uffff\1\17\1\7\1\31"+
        "\7\uffff\1\1";
    static final String DFA12_specialS =
        "\145\uffff}>";
    static final String[] DFA12_transitionS = {
            "\2\23\2\uffff\1\23\22\uffff\1\23\1\12\1\31\1\22\1\uffff\2\26"+
            "\1\uffff\1\14\1\15\2\uffff\1\6\1\3\1\26\1\16\12\26\1\4\2\uffff"+
            "\1\2\3\uffff\1\21\3\30\1\13\15\30\1\17\7\30\3\uffff\1\24\1\30"+
            "\1\uffff\13\30\1\5\1\1\2\30\1\27\1\30\1\25\1\30\1\7\1\20\5\30"+
            "\1\10\1\uffff\1\11\102\uffff\1\26\3\uffff\1\26\3\uffff\2\26"+
            "\14\uffff\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2\uffff"+
            "\3\26\14\uffff\1\26\5\uffff\1\26",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\4\33\1\32\25\33\105\uffff\1\26\3\uffff\1\26\3\uffff\2"+
            "\26\14\uffff\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2\uffff"+
            "\3\26\14\uffff\1\26\5\uffff\1\26",
            "",
            "\1\34",
            "",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\1\35\31\33\105\uffff\1\26\3\uffff\1\26\3\uffff\2\26\14"+
            "\uffff\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2\uffff\3"+
            "\26\14\uffff\1\26\5\uffff\1\26",
            "",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\16\33\1\36\13\33\105\uffff\1\26\3\uffff\1\26\3\uffff"+
            "\2\26\14\uffff\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2"+
            "\uffff\3\26\14\uffff\1\26\5\uffff\1\26",
            "",
            "",
            "",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\25\33\1\37\4\33\105\uffff\1\26\3\uffff\1\26\3\uffff\2"+
            "\26\14\uffff\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2\uffff"+
            "\3\26\14\uffff\1\26\5\uffff\1\26",
            "",
            "",
            "\1\41\4\uffff\1\40",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\15\33\1\42\14\33\105\uffff\1\26\3\uffff\1\26\3\uffff"+
            "\2\26\14\uffff\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2"+
            "\uffff\3\26\14\uffff\1\26\5\uffff\1\26",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\3\33\1\44\26\33\105\uffff\1\26\3\uffff\1\26\3\uffff\2"+
            "\26\14\uffff\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2\uffff"+
            "\3\26\14\uffff\1\26\5\uffff\1\26",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\32\33\105\uffff\1\26\3\uffff\1\26\3\uffff\2\26\14\uffff"+
            "\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2\uffff\3\26\14"+
            "\uffff\1\26\5\uffff\1\26",
            "",
            "",
            "",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\4\33\1\46\25\33\105\uffff\1\26\3\uffff\1\26\3\uffff\2"+
            "\26\14\uffff\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2\uffff"+
            "\3\26\14\uffff\1\26\5\uffff\1\26",
            "",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\13\33\1\47\16\33\105\uffff\1\26\3\uffff\1\26\3\uffff"+
            "\2\26\14\uffff\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2"+
            "\uffff\3\26\14\uffff\1\26\5\uffff\1\26",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\32\33\105\uffff\1\26\3\uffff\1\26\3\uffff\2\26\14\uffff"+
            "\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2\uffff\3\26\14"+
            "\uffff\1\26\5\uffff\1\26",
            "",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\22\33\1\51\1\50\6\33\105\uffff\1\26\3\uffff\1\26\3\uffff"+
            "\2\26\14\uffff\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2"+
            "\uffff\3\26\14\uffff\1\26\5\uffff\1\26",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\32\33\105\uffff\1\26\3\uffff\1\26\3\uffff\2\26\14\uffff"+
            "\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2\uffff\3\26\14"+
            "\uffff\1\26\5\uffff\1\26",
            "\1\52",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\1\33\1\53\30\33\105\uffff\1\26\3\uffff\1\26\3\uffff\2"+
            "\26\14\uffff\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2\uffff"+
            "\3\26\14\uffff\1\26\5\uffff\1\26",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\16\33\1\54\13\33\105\uffff\1\26\3\uffff\1\26\3\uffff"+
            "\2\26\14\uffff\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2"+
            "\uffff\3\26\14\uffff\1\26\5\uffff\1\26",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\4\33\1\55\25\33\105\uffff\1\26\3\uffff\1\26\3\uffff\2"+
            "\26\14\uffff\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2\uffff"+
            "\3\26\14\uffff\1\26\5\uffff\1\26",
            "\2\26\6\uffff\15\26\7\uffff\32\26\4\uffff\1\26\1\uffff\32"+
            "\26\105\uffff\1\26\3\uffff\1\26\3\uffff\2\26\14\uffff\1\26\5"+
            "\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2\uffff\3\26\14\uffff"+
            "\1\26\5\uffff\1\26",
            "",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\10\33\1\57\21\33\105\uffff\1\26\3\uffff\1\26\3\uffff"+
            "\2\26\14\uffff\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2"+
            "\uffff\3\26\14\uffff\1\26\5\uffff\1\26",
            "",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\32\33\105\uffff\1\26\3\uffff\1\26\3\uffff\2\26\14\uffff"+
            "\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2\uffff\3\26\14"+
            "\uffff\1\26\5\uffff\1\26",
            "",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\22\33\1\61\7\33\105\uffff\1\26\3\uffff\1\26\3\uffff\2"+
            "\26\14\uffff\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2\uffff"+
            "\3\26\14\uffff\1\26\5\uffff\1\26",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\1\62\31\33\105\uffff\1\26\3\uffff\1\26\3\uffff\2\26\14"+
            "\uffff\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2\uffff\3"+
            "\26\14\uffff\1\26\5\uffff\1\26",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\1\63\31\33\105\uffff\1\26\3\uffff\1\26\3\uffff\2\26\14"+
            "\uffff\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2\uffff\3"+
            "\26\14\uffff\1\26\5\uffff\1\26",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\22\33\1\64\7\33\105\uffff\1\26\3\uffff\1\26\3\uffff\2"+
            "\26\14\uffff\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2\uffff"+
            "\3\26\14\uffff\1\26\5\uffff\1\26",
            "\2\26\6\uffff\15\26\7\uffff\32\26\4\uffff\1\26\1\uffff\32"+
            "\26\105\uffff\1\26\3\uffff\1\26\3\uffff\2\26\14\uffff\1\26\5"+
            "\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2\uffff\3\26\14\uffff"+
            "\1\26\5\uffff\1\26",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\4\33\1\66\25\33\105\uffff\1\26\3\uffff\1\26\3\uffff\2"+
            "\26\14\uffff\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2\uffff"+
            "\3\26\14\uffff\1\26\5\uffff\1\26",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\13\33\1\67\16\33\105\uffff\1\26\3\uffff\1\26\3\uffff"+
            "\2\26\14\uffff\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2"+
            "\uffff\3\26\14\uffff\1\26\5\uffff\1\26",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\15\33\1\70\14\33\105\uffff\1\26\3\uffff\1\26\3\uffff"+
            "\2\26\14\uffff\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2"+
            "\uffff\3\26\14\uffff\1\26\5\uffff\1\26",
            "",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\17\33\1\71\12\33\105\uffff\1\26\3\uffff\1\26\3\uffff"+
            "\2\26\14\uffff\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2"+
            "\uffff\3\26\14\uffff\1\26\5\uffff\1\26",
            "",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\16\33\1\72\13\33\105\uffff\1\26\3\uffff\1\26\3\uffff"+
            "\2\26\14\uffff\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2"+
            "\uffff\3\26\14\uffff\1\26\5\uffff\1\26",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\23\33\1\73\6\33\105\uffff\1\26\3\uffff\1\26\3\uffff\2"+
            "\26\14\uffff\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2\uffff"+
            "\3\26\14\uffff\1\26\5\uffff\1\26",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\14\33\1\74\15\33\105\uffff\1\26\3\uffff\1\26\3\uffff"+
            "\2\26\14\uffff\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2"+
            "\uffff\3\26\14\uffff\1\26\5\uffff\1\26",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\1\75\31\33\105\uffff\1\26\3\uffff\1\26\3\uffff\2\26\14"+
            "\uffff\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2\uffff\3"+
            "\26\14\uffff\1\26\5\uffff\1\26",
            "",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\13\33\1\76\16\33\105\uffff\1\26\3\uffff\1\26\3\uffff"+
            "\2\26\14\uffff\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2"+
            "\uffff\3\26\14\uffff\1\26\5\uffff\1\26",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\23\33\1\77\6\33\4\uffff"+
            "\1\33\1\uffff\32\33\105\uffff\1\26\3\uffff\1\26\3\uffff\2\26"+
            "\14\uffff\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2\uffff"+
            "\3\26\14\uffff\1\26\5\uffff\1\26",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\23\33\1\100\6\33\105\uffff\1\26\3\uffff\1\26\3\uffff"+
            "\2\26\14\uffff\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2"+
            "\uffff\3\26\14\uffff\1\26\5\uffff\1\26",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\17\33\1\101\12\33\105\uffff\1\26\3\uffff\1\26\3\uffff"+
            "\2\26\14\uffff\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2"+
            "\uffff\3\26\14\uffff\1\26\5\uffff\1\26",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\24\33\1\102\5\33\105\uffff\1\26\3\uffff\1\26\3\uffff"+
            "\2\26\14\uffff\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2"+
            "\uffff\3\26\14\uffff\1\26\5\uffff\1\26",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\5\33\1\103\24\33\105\uffff\1\26\3\uffff\1\26\3\uffff"+
            "\2\26\14\uffff\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2"+
            "\uffff\3\26\14\uffff\1\26\5\uffff\1\26",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\16\33\1\104\13\33\105\uffff\1\26\3\uffff\1\26\3\uffff"+
            "\2\26\14\uffff\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2"+
            "\uffff\3\26\14\uffff\1\26\5\uffff\1\26",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\6\33\1\105\23\33\105\uffff\1\26\3\uffff\1\26\3\uffff"+
            "\2\26\14\uffff\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2"+
            "\uffff\3\26\14\uffff\1\26\5\uffff\1\26",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\22\33\1\106\7\33\105\uffff\1\26\3\uffff\1\26\3\uffff"+
            "\2\26\14\uffff\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2"+
            "\uffff\3\26\14\uffff\1\26\5\uffff\1\26",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\10\33\1\107\21\33\105\uffff\1\26\3\uffff\1\26\3\uffff"+
            "\2\26\14\uffff\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2"+
            "\uffff\3\26\14\uffff\1\26\5\uffff\1\26",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\32\33\105\uffff\1\26\3\uffff\1\26\3\uffff\2\26\14\uffff"+
            "\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2\uffff\3\26\14"+
            "\uffff\1\26\5\uffff\1\26",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\4\33\1\111\25\33\105\uffff\1\26\3\uffff\1\26\3\uffff"+
            "\2\26\14\uffff\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2"+
            "\uffff\3\26\14\uffff\1\26\5\uffff\1\26",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\21\33\1\112\10\33\105\uffff\1\26\3\uffff\1\26\3\uffff"+
            "\2\26\14\uffff\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2"+
            "\uffff\3\26\14\uffff\1\26\5\uffff\1\26",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\16\33\1\113\13\33\105\uffff\1\26\3\uffff\1\26\3\uffff"+
            "\2\26\14\uffff\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2"+
            "\uffff\3\26\14\uffff\1\26\5\uffff\1\26",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\3\33\1\114\26\33\105\uffff\1\26\3\uffff\1\26\3\uffff"+
            "\2\26\14\uffff\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2"+
            "\uffff\3\26\14\uffff\1\26\5\uffff\1\26",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\4\33\1\115\25\33\105\uffff\1\26\3\uffff\1\26\3\uffff"+
            "\2\26\14\uffff\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2"+
            "\uffff\3\26\14\uffff\1\26\5\uffff\1\26",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\32\33\105\uffff\1\26\3\uffff\1\26\3\uffff\2\26\14\uffff"+
            "\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2\uffff\3\26\14"+
            "\uffff\1\26\5\uffff\1\26",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\17\33\1\117\12\33\105\uffff\1\26\3\uffff\1\26\3\uffff"+
            "\2\26\14\uffff\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2"+
            "\uffff\3\26\14\uffff\1\26\5\uffff\1\26",
            "",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\23\33\1\120\6\33\105\uffff\1\26\3\uffff\1\26\3\uffff"+
            "\2\26\14\uffff\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2"+
            "\uffff\3\26\14\uffff\1\26\5\uffff\1\26",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\2\33\1\121\27\33\105\uffff\1\26\3\uffff\1\26\3\uffff"+
            "\2\26\14\uffff\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2"+
            "\uffff\3\26\14\uffff\1\26\5\uffff\1\26",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\21\33\1\122\10\33\105\uffff\1\26\3\uffff\1\26\3\uffff"+
            "\2\26\14\uffff\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2"+
            "\uffff\3\26\14\uffff\1\26\5\uffff\1\26",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\4\33\1\123\25\33\105\uffff\1\26\3\uffff\1\26\3\uffff"+
            "\2\26\14\uffff\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2"+
            "\uffff\3\26\14\uffff\1\26\5\uffff\1\26",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\22\33\1\124\7\33\105\uffff\1\26\3\uffff\1\26\3\uffff"+
            "\2\26\14\uffff\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2"+
            "\uffff\3\26\14\uffff\1\26\5\uffff\1\26",
            "",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\22\33\1\125\7\33\105\uffff\1\26\3\uffff\1\26\3\uffff"+
            "\2\26\14\uffff\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2"+
            "\uffff\3\26\14\uffff\1\26\5\uffff\1\26",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\32\33\105\uffff\1\26\3\uffff\1\26\3\uffff\2\26\14\uffff"+
            "\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2\uffff\3\26\14"+
            "\uffff\1\26\5\uffff\1\26",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\4\33\1\127\25\33\105\uffff\1\26\3\uffff\1\26\3\uffff"+
            "\2\26\14\uffff\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2"+
            "\uffff\3\26\14\uffff\1\26\5\uffff\1\26",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\14\33\1\130\15\33\105\uffff\1\26\3\uffff\1\26\3\uffff"+
            "\2\26\14\uffff\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2"+
            "\uffff\3\26\14\uffff\1\26\5\uffff\1\26",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\13\33\1\131\16\33\105\uffff\1\26\3\uffff\1\26\3\uffff"+
            "\2\26\14\uffff\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2"+
            "\uffff\3\26\14\uffff\1\26\5\uffff\1\26",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\32\33\105\uffff\1\26\3\uffff\1\26\3\uffff\2\26\14\uffff"+
            "\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2\uffff\3\26\14"+
            "\uffff\1\26\5\uffff\1\26",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\32\33\105\uffff\1\26\3\uffff\1\26\3\uffff\2\26\14\uffff"+
            "\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2\uffff\3\26\14"+
            "\uffff\1\26\5\uffff\1\26",
            "",
            "\2\26\6\uffff\2\33\1\26\12\33\1\134\6\uffff\32\33\4\uffff"+
            "\1\33\1\uffff\32\33\105\uffff\1\26\3\uffff\1\26\3\uffff\2\26"+
            "\14\uffff\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2\uffff"+
            "\3\26\14\uffff\1\26\5\uffff\1\26",
            "\2\26\6\uffff\2\33\1\26\12\33\1\134\6\uffff\32\33\4\uffff"+
            "\1\33\1\uffff\32\33\105\uffff\1\26\3\uffff\1\26\3\uffff\2\26"+
            "\14\uffff\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2\uffff"+
            "\3\26\14\uffff\1\26\5\uffff\1\26",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\25\33\1\135\4\33\4\uffff"+
            "\1\33\1\uffff\32\33\105\uffff\1\26\3\uffff\1\26\3\uffff\2\26"+
            "\14\uffff\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2\uffff"+
            "\3\26\14\uffff\1\26\5\uffff\1\26",
            "",
            "",
            "",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\4\33\1\136\25\33\105\uffff\1\26\3\uffff\1\26\3\uffff"+
            "\2\26\14\uffff\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2"+
            "\uffff\3\26\14\uffff\1\26\5\uffff\1\26",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\21\33\1\137\10\33\105\uffff\1\26\3\uffff\1\26\3\uffff"+
            "\2\26\14\uffff\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2"+
            "\uffff\3\26\14\uffff\1\26\5\uffff\1\26",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\22\33\1\140\7\33\105\uffff\1\26\3\uffff\1\26\3\uffff"+
            "\2\26\14\uffff\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2"+
            "\uffff\3\26\14\uffff\1\26\5\uffff\1\26",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\10\33\1\141\21\33\105\uffff\1\26\3\uffff\1\26\3\uffff"+
            "\2\26\14\uffff\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2"+
            "\uffff\3\26\14\uffff\1\26\5\uffff\1\26",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\16\33\1\142\13\33\105\uffff\1\26\3\uffff\1\26\3\uffff"+
            "\2\26\14\uffff\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2"+
            "\uffff\3\26\14\uffff\1\26\5\uffff\1\26",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\15\33\1\143\14\33\105\uffff\1\26\3\uffff\1\26\3\uffff"+
            "\2\26\14\uffff\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2"+
            "\uffff\3\26\14\uffff\1\26\5\uffff\1\26",
            "\2\26\6\uffff\2\33\1\26\12\33\7\uffff\32\33\4\uffff\1\33\1"+
            "\uffff\32\33\105\uffff\1\26\3\uffff\1\26\3\uffff\2\26\14\uffff"+
            "\1\26\5\uffff\1\26\2\uffff\2\26\3\uffff\1\26\2\uffff\3\26\14"+
            "\uffff\1\26\5\uffff\1\26",
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
            return "1:1: Tokens : ( T__11 | T__12 | T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_ID | RULE_VALUE | RULE_URI | RULE_STRING );";
        }
    }
 

}
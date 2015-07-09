package com.odcgroup.t24.menu.ui.contentassist.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.ui.editor.contentassist.antlr.internal.Lexer;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalMenuLexer extends Lexer {
    public static final int RULE_VALUE=5;
    public static final int T__27=27;
    public static final int T__26=26;
    public static final int T__25=25;
    public static final int T__24=24;
    public static final int T__23=23;
    public static final int T__22=22;
    public static final int T__21=21;
    public static final int T__20=20;
    public static final int EOF=-1;
    public static final int RULE_SL_COMMENT=7;
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

    public InternalMenuLexer() {;} 
    public InternalMenuLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public InternalMenuLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g"; }

    // $ANTLR start "T__9"
    public final void mT__9() throws RecognitionException {
        try {
            int _type = T__9;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:11:6: ( 'true' )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:11:8: 'true'
            {
            match("true"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__9"

    // $ANTLR start "T__10"
    public final void mT__10() throws RecognitionException {
        try {
            int _type = T__10;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:12:7: ( 'false' )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:12:9: 'false'
            {
            match("false"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__10"

    // $ANTLR start "T__11"
    public final void mT__11() throws RecognitionException {
        try {
            int _type = T__11;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:13:7: ( 'conditional' )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:13:9: 'conditional'
            {
            match("conditional"); 


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
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:14:7: ( 'metamodelVersion' )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:14:9: 'metamodelVersion'
            {
            match("metamodelVersion"); 


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
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:15:7: ( '=' )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:15:9: '='
            {
            match('='); 

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
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:16:7: ( 'enabled' )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:16:9: 'enabled'
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
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:17:7: ( 'displayTabs' )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:17:9: 'displayTabs'
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
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:18:7: ( 'securityRole' )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:18:9: 'securityRole'
            {
            match("securityRole"); 


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
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:19:7: ( 'shortcut' )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:19:9: 'shortcut'
            {
            match("shortcut"); 


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
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:20:7: ( 'labels' )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:20:9: 'labels'
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
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:21:7: ( ',' )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:21:9: ','
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
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:22:7: ( 'version' )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:22:9: 'version'
            {
            match("version"); 


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
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:23:7: ( 'enquiry' )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:23:9: 'enquiry'
            {
            match("enquiry"); 


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
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:24:7: ( 'composite-screen' )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:24:9: 'composite-screen'
            {
            match("composite-screen"); 


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
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:25:7: ( 'include-menu' )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:25:9: 'include-menu'
            {
            match("include-menu"); 


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
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:26:7: ( 'application' )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:26:9: 'application'
            {
            match("application"); 


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
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:27:7: ( 'parameters' )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:27:9: 'parameters'
            {
            match("parameters"); 


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
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:28:7: ( '{' )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:28:9: '{'
            {
            match('{'); 

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
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:29:7: ( '}' )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:29:9: '}'
            {
            match('}'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__27"

    // $ANTLR start "RULE_STRING"
    public final void mRULE_STRING() throws RecognitionException {
        try {
            int _type = RULE_STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2324:13: ( '\"' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\"' ) ) )* '\"' )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2324:15: '\"' ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\"' ) ) )* '\"'
            {
            match('\"'); 
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2324:19: ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '\"' | '\\'' | '\\\\' ) | ~ ( ( '\\\\' | '\"' ) ) )*
            loop1:
            do {
                int alt1=3;
                int LA1_0 = input.LA(1);

                if ( (LA1_0=='\\') ) {
                    alt1=1;
                }
                else if ( ((LA1_0>='\u0000' && LA1_0<='!')||(LA1_0>='#' && LA1_0<='[')||(LA1_0>=']' && LA1_0<='\uFFFF')) ) {
                    alt1=2;
                }


                switch (alt1) {
            	case 1 :
            	    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2324:20: '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | 'u' | '\"' | '\\'' | '\\\\' )
            	    {
            	    match('\\'); 
            	    if ( input.LA(1)=='\"'||input.LA(1)=='\''||input.LA(1)=='\\'||input.LA(1)=='b'||input.LA(1)=='f'||input.LA(1)=='n'||input.LA(1)=='r'||(input.LA(1)>='t' && input.LA(1)<='u') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;
            	case 2 :
            	    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2324:65: ~ ( ( '\\\\' | '\"' ) )
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
            	    break loop1;
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

    // $ANTLR start "RULE_VALUE"
    public final void mRULE_VALUE() throws RecognitionException {
        try {
            int _type = RULE_VALUE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2326:12: ( ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' | '&' | '/' | '%' | '.' | ',' | '-' | '\\u00E9' | '\\u00E8' | '\\u00E0' | '\\u00E4' | '\\u00F6' | '\\u00FC' | '\\u00C4' | '\\u00D6' | '\\u00DC' | '\\u00C9' | '\\u00C8' | '\\u00C0' | '\\u00E7' | '\\u00DF' )+ )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2326:14: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' | '&' | '/' | '%' | '.' | ',' | '-' | '\\u00E9' | '\\u00E8' | '\\u00E0' | '\\u00E4' | '\\u00F6' | '\\u00FC' | '\\u00C4' | '\\u00D6' | '\\u00DC' | '\\u00C9' | '\\u00C8' | '\\u00C0' | '\\u00E7' | '\\u00DF' )+
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2326:14: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' | '&' | '/' | '%' | '.' | ',' | '-' | '\\u00E9' | '\\u00E8' | '\\u00E0' | '\\u00E4' | '\\u00F6' | '\\u00FC' | '\\u00C4' | '\\u00D6' | '\\u00DC' | '\\u00C9' | '\\u00C8' | '\\u00C0' | '\\u00E7' | '\\u00DF' )+
            int cnt2=0;
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( ((LA2_0>='%' && LA2_0<='&')||(LA2_0>=',' && LA2_0<='9')||(LA2_0>='A' && LA2_0<='Z')||LA2_0=='_'||(LA2_0>='a' && LA2_0<='z')||LA2_0=='\u00C0'||LA2_0=='\u00C4'||(LA2_0>='\u00C8' && LA2_0<='\u00C9')||LA2_0=='\u00D6'||LA2_0=='\u00DC'||(LA2_0>='\u00DF' && LA2_0<='\u00E0')||LA2_0=='\u00E4'||(LA2_0>='\u00E7' && LA2_0<='\u00E9')||LA2_0=='\u00F6'||LA2_0=='\u00FC') ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:
            	    {
            	    if ( (input.LA(1)>='%' && input.LA(1)<='&')||(input.LA(1)>=',' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z')||input.LA(1)=='\u00C0'||input.LA(1)=='\u00C4'||(input.LA(1)>='\u00C8' && input.LA(1)<='\u00C9')||input.LA(1)=='\u00D6'||input.LA(1)=='\u00DC'||(input.LA(1)>='\u00DF' && input.LA(1)<='\u00E0')||input.LA(1)=='\u00E4'||(input.LA(1)>='\u00E7' && input.LA(1)<='\u00E9')||input.LA(1)=='\u00F6'||input.LA(1)=='\u00FC' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt2 >= 1 ) break loop2;
                        EarlyExitException eee =
                            new EarlyExitException(2, input);
                        throw eee;
                }
                cnt2++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_VALUE"

    // $ANTLR start "RULE_ML_COMMENT"
    public final void mRULE_ML_COMMENT() throws RecognitionException {
        try {
            int _type = RULE_ML_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2328:17: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2328:19: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 

            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2328:24: ( options {greedy=false; } : . )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0=='*') ) {
                    int LA3_1 = input.LA(2);

                    if ( (LA3_1=='/') ) {
                        alt3=2;
                    }
                    else if ( ((LA3_1>='\u0000' && LA3_1<='.')||(LA3_1>='0' && LA3_1<='\uFFFF')) ) {
                        alt3=1;
                    }


                }
                else if ( ((LA3_0>='\u0000' && LA3_0<=')')||(LA3_0>='+' && LA3_0<='\uFFFF')) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2328:52: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop3;
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
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2330:17: ( '#' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )? )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2330:19: '#' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )?
            {
            match('#'); 
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2330:23: (~ ( ( '\\n' | '\\r' ) ) )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( ((LA4_0>='\u0000' && LA4_0<='\t')||(LA4_0>='\u000B' && LA4_0<='\f')||(LA4_0>='\u000E' && LA4_0<='\uFFFF')) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2330:23: ~ ( ( '\\n' | '\\r' ) )
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
            	    break loop4;
                }
            } while (true);

            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2330:39: ( ( '\\r' )? '\\n' )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0=='\n'||LA6_0=='\r') ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2330:40: ( '\\r' )? '\\n'
                    {
                    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2330:40: ( '\\r' )?
                    int alt5=2;
                    int LA5_0 = input.LA(1);

                    if ( (LA5_0=='\r') ) {
                        alt5=1;
                    }
                    switch (alt5) {
                        case 1 :
                            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2330:40: '\\r'
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
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2332:9: ( ( ' ' | '\\t' | '\\r' | '\\n' )+ )
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2332:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            {
            // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:2332:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            int cnt7=0;
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( ((LA7_0>='\t' && LA7_0<='\n')||LA7_0=='\r'||LA7_0==' ') ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:
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
            	    if ( cnt7 >= 1 ) break loop7;
                        EarlyExitException eee =
                            new EarlyExitException(7, input);
                        throw eee;
                }
                cnt7++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_WS"

    public void mTokens() throws RecognitionException {
        // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1:8: ( T__9 | T__10 | T__11 | T__12 | T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | RULE_STRING | RULE_VALUE | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS )
        int alt8=24;
        alt8 = dfa8.predict(input);
        switch (alt8) {
            case 1 :
                // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1:10: T__9
                {
                mT__9(); 

                }
                break;
            case 2 :
                // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1:15: T__10
                {
                mT__10(); 

                }
                break;
            case 3 :
                // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1:21: T__11
                {
                mT__11(); 

                }
                break;
            case 4 :
                // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1:27: T__12
                {
                mT__12(); 

                }
                break;
            case 5 :
                // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1:33: T__13
                {
                mT__13(); 

                }
                break;
            case 6 :
                // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1:39: T__14
                {
                mT__14(); 

                }
                break;
            case 7 :
                // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1:45: T__15
                {
                mT__15(); 

                }
                break;
            case 8 :
                // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1:51: T__16
                {
                mT__16(); 

                }
                break;
            case 9 :
                // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1:57: T__17
                {
                mT__17(); 

                }
                break;
            case 10 :
                // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1:63: T__18
                {
                mT__18(); 

                }
                break;
            case 11 :
                // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1:69: T__19
                {
                mT__19(); 

                }
                break;
            case 12 :
                // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1:75: T__20
                {
                mT__20(); 

                }
                break;
            case 13 :
                // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1:81: T__21
                {
                mT__21(); 

                }
                break;
            case 14 :
                // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1:87: T__22
                {
                mT__22(); 

                }
                break;
            case 15 :
                // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1:93: T__23
                {
                mT__23(); 

                }
                break;
            case 16 :
                // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1:99: T__24
                {
                mT__24(); 

                }
                break;
            case 17 :
                // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1:105: T__25
                {
                mT__25(); 

                }
                break;
            case 18 :
                // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1:111: T__26
                {
                mT__26(); 

                }
                break;
            case 19 :
                // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1:117: T__27
                {
                mT__27(); 

                }
                break;
            case 20 :
                // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1:123: RULE_STRING
                {
                mRULE_STRING(); 

                }
                break;
            case 21 :
                // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1:135: RULE_VALUE
                {
                mRULE_VALUE(); 

                }
                break;
            case 22 :
                // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1:146: RULE_ML_COMMENT
                {
                mRULE_ML_COMMENT(); 

                }
                break;
            case 23 :
                // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1:162: RULE_SL_COMMENT
                {
                mRULE_SL_COMMENT(); 

                }
                break;
            case 24 :
                // ../../ui/com.odcgroup.t24.menu.ui/src-gen/com/odcgroup/t24/menu/ui/contentassist/antlr/internal/InternalMenu.g:1:178: RULE_WS
                {
                mRULE_WS(); 

                }
                break;

        }

    }


    protected DFA8 dfa8 = new DFA8(this);
    static final String DFA8_eotS =
        "\1\uffff\4\23\1\uffff\4\23\1\37\4\23\3\uffff\1\23\3\uffff\11\23"+
        "\1\uffff\4\23\1\uffff\17\23\1\103\16\23\1\uffff\1\122\15\23\1\uffff"+
        "\10\23\1\150\7\23\1\160\1\161\3\23\1\uffff\1\165\6\23\2\uffff\2"+
        "\23\1\176\1\uffff\10\23\1\uffff\12\23\1\u0091\1\u0092\2\23\1\u0095"+
        "\2\23\1\u0098\2\uffff\2\23\1\uffff\1\u009b\1\u009c\1\uffff\2\23"+
        "\2\uffff\4\23\1\u00a3\1\u00a4\2\uffff";
    static final String DFA8_eofS =
        "\u00a5\uffff";
    static final String DFA8_minS =
        "\1\11\1\162\1\141\1\157\1\145\1\uffff\1\156\1\151\1\145\1\141\1"+
        "\45\1\145\1\156\1\160\1\141\3\uffff\1\52\3\uffff\1\165\1\154\1\155"+
        "\1\164\1\141\1\163\1\143\1\157\1\142\1\uffff\1\162\1\143\1\160\1"+
        "\162\1\uffff\1\145\1\163\1\144\1\160\1\141\1\142\1\165\1\160\1\165"+
        "\1\162\1\145\1\163\2\154\1\141\1\45\1\145\1\151\1\157\1\155\1\154"+
        "\1\151\1\154\1\162\1\164\1\154\1\151\1\165\1\151\1\155\1\uffff\1"+
        "\45\1\164\1\163\1\157\1\145\1\162\1\141\1\151\1\143\1\163\1\157"+
        "\1\144\1\143\1\145\1\uffff\2\151\2\144\2\171\1\164\1\165\1\45\1"+
        "\156\1\145\1\141\1\164\1\157\1\164\1\145\2\45\1\124\1\171\1\164"+
        "\1\uffff\1\45\1\55\1\164\1\145\1\156\1\145\1\154\2\uffff\1\141\1"+
        "\122\1\45\1\uffff\1\155\1\151\1\162\1\141\1\55\1\126\1\142\1\157"+
        "\1\uffff\1\145\1\157\1\163\1\154\1\163\1\145\1\163\1\154\2\156\2"+
        "\45\1\143\1\162\1\45\1\145\1\165\1\45\2\uffff\1\162\1\163\1\uffff"+
        "\2\45\1\uffff\1\145\1\151\2\uffff\1\145\1\157\2\156\2\45\2\uffff";
    static final String DFA8_maxS =
        "\1\u00fc\1\162\1\141\1\157\1\145\1\uffff\1\156\1\151\1\150\1\141"+
        "\1\u00fc\1\145\1\156\1\160\1\141\3\uffff\1\52\3\uffff\1\165\1\154"+
        "\1\156\1\164\1\161\1\163\1\143\1\157\1\142\1\uffff\1\162\1\143\1"+
        "\160\1\162\1\uffff\1\145\1\163\1\144\1\160\1\141\1\142\1\165\1\160"+
        "\1\165\1\162\1\145\1\163\2\154\1\141\1\u00fc\1\145\1\151\1\157\1"+
        "\155\1\154\1\151\1\154\1\162\1\164\1\154\1\151\1\165\1\151\1\155"+
        "\1\uffff\1\u00fc\1\164\1\163\1\157\1\145\1\162\1\141\1\151\1\143"+
        "\1\163\1\157\1\144\1\143\1\145\1\uffff\2\151\2\144\2\171\1\164\1"+
        "\165\1\u00fc\1\156\1\145\1\141\1\164\1\157\1\164\1\145\2\u00fc\1"+
        "\124\1\171\1\164\1\uffff\1\u00fc\1\55\1\164\1\145\1\156\1\145\1"+
        "\154\2\uffff\1\141\1\122\1\u00fc\1\uffff\1\155\1\151\1\162\1\141"+
        "\1\55\1\126\1\142\1\157\1\uffff\1\145\1\157\1\163\1\154\1\163\1"+
        "\145\1\163\1\154\2\156\2\u00fc\1\143\1\162\1\u00fc\1\145\1\165\1"+
        "\u00fc\2\uffff\1\162\1\163\1\uffff\2\u00fc\1\uffff\1\145\1\151\2"+
        "\uffff\1\145\1\157\2\156\2\u00fc\2\uffff";
    static final String DFA8_acceptS =
        "\5\uffff\1\5\11\uffff\1\22\1\23\1\24\1\uffff\1\25\1\27\1\30\11"+
        "\uffff\1\13\4\uffff\1\26\36\uffff\1\1\16\uffff\1\2\25\uffff\1\12"+
        "\7\uffff\1\6\1\15\3\uffff\1\14\10\uffff\1\11\22\uffff\1\21\1\3\2"+
        "\uffff\1\7\2\uffff\1\20\2\uffff\1\10\1\17\6\uffff\1\16\1\4";
    static final String DFA8_specialS =
        "\u00a5\uffff}>";
    static final String[] DFA8_transitionS = {
            "\2\25\2\uffff\1\25\22\uffff\1\25\1\uffff\1\21\1\24\1\uffff"+
            "\2\23\5\uffff\1\12\2\23\1\22\12\23\3\uffff\1\5\3\uffff\32\23"+
            "\4\uffff\1\23\1\uffff\1\15\1\23\1\3\1\7\1\6\1\2\2\23\1\14\2"+
            "\23\1\11\1\4\2\23\1\16\2\23\1\10\1\1\1\23\1\13\4\23\1\17\1\uffff"+
            "\1\20\102\uffff\1\23\3\uffff\1\23\3\uffff\2\23\14\uffff\1\23"+
            "\5\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2\uffff\3\23\14\uffff"+
            "\1\23\5\uffff\1\23",
            "\1\26",
            "\1\27",
            "\1\30",
            "\1\31",
            "",
            "\1\32",
            "\1\33",
            "\1\34\2\uffff\1\35",
            "\1\36",
            "\2\23\5\uffff\16\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32"+
            "\23\105\uffff\1\23\3\uffff\1\23\3\uffff\2\23\14\uffff\1\23\5"+
            "\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2\uffff\3\23\14\uffff"+
            "\1\23\5\uffff\1\23",
            "\1\40",
            "\1\41",
            "\1\42",
            "\1\43",
            "",
            "",
            "",
            "\1\44",
            "",
            "",
            "",
            "\1\45",
            "\1\46",
            "\1\50\1\47",
            "\1\51",
            "\1\52\17\uffff\1\53",
            "\1\54",
            "\1\55",
            "\1\56",
            "\1\57",
            "",
            "\1\60",
            "\1\61",
            "\1\62",
            "\1\63",
            "",
            "\1\64",
            "\1\65",
            "\1\66",
            "\1\67",
            "\1\70",
            "\1\71",
            "\1\72",
            "\1\73",
            "\1\74",
            "\1\75",
            "\1\76",
            "\1\77",
            "\1\100",
            "\1\101",
            "\1\102",
            "\2\23\5\uffff\16\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32"+
            "\23\105\uffff\1\23\3\uffff\1\23\3\uffff\2\23\14\uffff\1\23\5"+
            "\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2\uffff\3\23\14\uffff"+
            "\1\23\5\uffff\1\23",
            "\1\104",
            "\1\105",
            "\1\106",
            "\1\107",
            "\1\110",
            "\1\111",
            "\1\112",
            "\1\113",
            "\1\114",
            "\1\115",
            "\1\116",
            "\1\117",
            "\1\120",
            "\1\121",
            "",
            "\2\23\5\uffff\16\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32"+
            "\23\105\uffff\1\23\3\uffff\1\23\3\uffff\2\23\14\uffff\1\23\5"+
            "\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2\uffff\3\23\14\uffff"+
            "\1\23\5\uffff\1\23",
            "\1\123",
            "\1\124",
            "\1\125",
            "\1\126",
            "\1\127",
            "\1\130",
            "\1\131",
            "\1\132",
            "\1\133",
            "\1\134",
            "\1\135",
            "\1\136",
            "\1\137",
            "",
            "\1\140",
            "\1\141",
            "\1\142",
            "\1\143",
            "\1\144",
            "\1\145",
            "\1\146",
            "\1\147",
            "\2\23\5\uffff\16\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32"+
            "\23\105\uffff\1\23\3\uffff\1\23\3\uffff\2\23\14\uffff\1\23\5"+
            "\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2\uffff\3\23\14\uffff"+
            "\1\23\5\uffff\1\23",
            "\1\151",
            "\1\152",
            "\1\153",
            "\1\154",
            "\1\155",
            "\1\156",
            "\1\157",
            "\2\23\5\uffff\16\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32"+
            "\23\105\uffff\1\23\3\uffff\1\23\3\uffff\2\23\14\uffff\1\23\5"+
            "\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2\uffff\3\23\14\uffff"+
            "\1\23\5\uffff\1\23",
            "\2\23\5\uffff\16\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32"+
            "\23\105\uffff\1\23\3\uffff\1\23\3\uffff\2\23\14\uffff\1\23\5"+
            "\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2\uffff\3\23\14\uffff"+
            "\1\23\5\uffff\1\23",
            "\1\162",
            "\1\163",
            "\1\164",
            "",
            "\2\23\5\uffff\16\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32"+
            "\23\105\uffff\1\23\3\uffff\1\23\3\uffff\2\23\14\uffff\1\23\5"+
            "\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2\uffff\3\23\14\uffff"+
            "\1\23\5\uffff\1\23",
            "\1\166",
            "\1\167",
            "\1\170",
            "\1\171",
            "\1\172",
            "\1\173",
            "",
            "",
            "\1\174",
            "\1\175",
            "\2\23\5\uffff\16\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32"+
            "\23\105\uffff\1\23\3\uffff\1\23\3\uffff\2\23\14\uffff\1\23\5"+
            "\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2\uffff\3\23\14\uffff"+
            "\1\23\5\uffff\1\23",
            "",
            "\1\177",
            "\1\u0080",
            "\1\u0081",
            "\1\u0082",
            "\1\u0083",
            "\1\u0084",
            "\1\u0085",
            "\1\u0086",
            "",
            "\1\u0087",
            "\1\u0088",
            "\1\u0089",
            "\1\u008a",
            "\1\u008b",
            "\1\u008c",
            "\1\u008d",
            "\1\u008e",
            "\1\u008f",
            "\1\u0090",
            "\2\23\5\uffff\16\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32"+
            "\23\105\uffff\1\23\3\uffff\1\23\3\uffff\2\23\14\uffff\1\23\5"+
            "\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2\uffff\3\23\14\uffff"+
            "\1\23\5\uffff\1\23",
            "\2\23\5\uffff\16\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32"+
            "\23\105\uffff\1\23\3\uffff\1\23\3\uffff\2\23\14\uffff\1\23\5"+
            "\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2\uffff\3\23\14\uffff"+
            "\1\23\5\uffff\1\23",
            "\1\u0093",
            "\1\u0094",
            "\2\23\5\uffff\16\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32"+
            "\23\105\uffff\1\23\3\uffff\1\23\3\uffff\2\23\14\uffff\1\23\5"+
            "\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2\uffff\3\23\14\uffff"+
            "\1\23\5\uffff\1\23",
            "\1\u0096",
            "\1\u0097",
            "\2\23\5\uffff\16\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32"+
            "\23\105\uffff\1\23\3\uffff\1\23\3\uffff\2\23\14\uffff\1\23\5"+
            "\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2\uffff\3\23\14\uffff"+
            "\1\23\5\uffff\1\23",
            "",
            "",
            "\1\u0099",
            "\1\u009a",
            "",
            "\2\23\5\uffff\16\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32"+
            "\23\105\uffff\1\23\3\uffff\1\23\3\uffff\2\23\14\uffff\1\23\5"+
            "\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2\uffff\3\23\14\uffff"+
            "\1\23\5\uffff\1\23",
            "\2\23\5\uffff\16\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32"+
            "\23\105\uffff\1\23\3\uffff\1\23\3\uffff\2\23\14\uffff\1\23\5"+
            "\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2\uffff\3\23\14\uffff"+
            "\1\23\5\uffff\1\23",
            "",
            "\1\u009d",
            "\1\u009e",
            "",
            "",
            "\1\u009f",
            "\1\u00a0",
            "\1\u00a1",
            "\1\u00a2",
            "\2\23\5\uffff\16\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32"+
            "\23\105\uffff\1\23\3\uffff\1\23\3\uffff\2\23\14\uffff\1\23\5"+
            "\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2\uffff\3\23\14\uffff"+
            "\1\23\5\uffff\1\23",
            "\2\23\5\uffff\16\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32"+
            "\23\105\uffff\1\23\3\uffff\1\23\3\uffff\2\23\14\uffff\1\23\5"+
            "\uffff\1\23\2\uffff\2\23\3\uffff\1\23\2\uffff\3\23\14\uffff"+
            "\1\23\5\uffff\1\23",
            "",
            ""
    };

    static final short[] DFA8_eot = DFA.unpackEncodedString(DFA8_eotS);
    static final short[] DFA8_eof = DFA.unpackEncodedString(DFA8_eofS);
    static final char[] DFA8_min = DFA.unpackEncodedStringToUnsignedChars(DFA8_minS);
    static final char[] DFA8_max = DFA.unpackEncodedStringToUnsignedChars(DFA8_maxS);
    static final short[] DFA8_accept = DFA.unpackEncodedString(DFA8_acceptS);
    static final short[] DFA8_special = DFA.unpackEncodedString(DFA8_specialS);
    static final short[][] DFA8_transition;

    static {
        int numStates = DFA8_transitionS.length;
        DFA8_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA8_transition[i] = DFA.unpackEncodedString(DFA8_transitionS[i]);
        }
    }

    class DFA8 extends DFA {

        public DFA8(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 8;
            this.eot = DFA8_eot;
            this.eof = DFA8_eof;
            this.min = DFA8_min;
            this.max = DFA8_max;
            this.accept = DFA8_accept;
            this.special = DFA8_special;
            this.transition = DFA8_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__9 | T__10 | T__11 | T__12 | T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | RULE_STRING | RULE_VALUE | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS );";
        }
    }
 

}
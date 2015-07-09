package com.odcgroup.edge.t24.generation.util;

import java.util.Iterator;

import org.eclipse.emf.ecore.EObject;

import com.acquire.util.AssertionUtils;
import com.acquire.util.DetailFormatter;
import com.acquire.util.IDebuggable;
import com.acquire.util.StringUtils;
import com.odcgroup.edge.t24.generation.EGenOptions;
import com.odcgroup.edge.t24.generation.EdgeMapper;
import com.odcgroup.edge.t24.generation.languagemaps.ISOLanguageCode;
import com.odcgroup.edge.t24.generation.languagemaps.TargetTranslationLanguageSet;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.translation.translationDsl.LocalTranslation;
import com.odcgroup.translation.translationDsl.Translations;

/**
 * Simple class to encapsulate a piece of text with its (optional) translations. 
 * 
 * @author sakbar
 *
 */
public class TextTranslations implements IDebuggable
{
    private final String m_text;
    private final Translations m_localTranslations;
    private final ITranslation m_inheritedTranslation;
    private final ITranslationKind m_translationKind;
    
    public static TextTranslations getLabelTranslations(EdgeMapper<?> p_edgeMapper, MdfProperty p_property, Translations p_localTranslations, String p_defaultText)
    {
        return getTextTranslations( p_edgeMapper, ITranslationKind.NAME, p_property, p_localTranslations, p_defaultText );
    }
    
    public static TextTranslations getTooltipTranslations(EdgeMapper<?> p_edgeMapper, MdfProperty p_property, Translations p_localTranslations, String p_defaultText)
    {
        return getTextTranslations( p_edgeMapper, ITranslationKind.TEXT, p_property, p_localTranslations, p_defaultText );
    }
    
    public static TextTranslations getLocalTranslations(EdgeMapper<?> p_edgeMapper, Translations p_localTranslations, String p_defaultText)
    {
        return getTextTranslations( p_edgeMapper, null, null, p_localTranslations, p_defaultText );
    }
    
    /**
     * Gets text that has a default but really no other translation, used as a place holder for later translations
     *  
     * @param p_defaultText
     * @return Non-null TextTranslations, which will contain p_defaultText as the default text
     */
    public static TextTranslations getDefaultText(String p_defaultText)
    {
        AssertionUtils.requireNonNullAndNonEmpty( p_defaultText, "p_defaultText" );
        
        return new DefaultText( p_defaultText );
    }
    
    /**
     * Gets text that cannot be translated, i.e. some dynamic value
     *  
     * @param p_untranslatableText
     * @return Non-null TextTranslations, which will contain p_untranslatableText as the default text
     */
    public static TextTranslations getUntranslatableText(String p_untranslatableText)
    {
        AssertionUtils.requireNonNullAndNonEmpty( p_untranslatableText, "p_untranslatableText" );
        
        return new UntranslatableText( p_untranslatableText );
    }

    /**
     * Gets a non-null object to handle translations. NB: The getText(*) methods may return null <p> 
     * 
     * Also see {@link #hasTranslations()}
     * 
     * @param p_edgeMapper
     * @param p_translationKind
     * @param p_property
     * @param p_localTranslations
     * @param p_defaultText
     * 
     * @return Non-null TextTranslations.
     */
    private static TextTranslations getTextTranslations(EdgeMapper<?> p_edgeMapper, ITranslationKind p_translationKind, MdfProperty p_property, Translations p_localTranslations, String p_defaultText)
    {
        AssertionUtils.requireNonNull( p_edgeMapper, "p_edgeMapper" );
        
        ITranslation inheritedTranslation = null;
        
        if  ( p_property != null )
        {
            inheritedTranslation = p_edgeMapper.getTranslationManager().getTranslation(p_property);
        }

        String text = getTranslatedText( p_localTranslations, inheritedTranslation, p_translationKind, TargetTranslationLanguageSet.getBaseISOLanguageCode() );

        if  ( text != null )
        {
            return new TextTranslations( p_localTranslations, inheritedTranslation, p_translationKind, text ); 
        }

        // Always return an object, which may itself contain a null default text, the caller must verify
        //
        return new DefaultText( p_defaultText );
    }

    /**
     * Instantiates a new text translations and sets the text to the base translation (ensuring a blank text is set as null).
     *
     * @param p_translations the translations
     * @param p_translationKind 
     */
    private TextTranslations(Translations p_translations, ITranslation p_inheritedTranslation, ITranslationKind p_translationKind, String p_text)
    {
        m_inheritedTranslation  = p_inheritedTranslation;
        m_localTranslations     = p_translations;
        m_translationKind       = p_translationKind;
        m_text                  = p_text;
    }

    /**
     * Gets the default text.
     *
     * @return the text, null if no none-blank translation
     */
    public String getText()
    {
        return m_text;
    }

    /**
     * Gets the text for the specified {@link ISOLanguageCode}
     *
     * @param p_isoLanguageCode the language code
     * @return the text, null if there are no translations available or the specific translation is null
     */
    public String getText(ISOLanguageCode p_isoLanguageCode)
    {
        return getTranslatedText( m_localTranslations, m_inheritedTranslation, m_translationKind, p_isoLanguageCode );
    }
    
    public String getText(String p_isoLanguageCode)
    {
        ISOLanguageCode targetTranslationISOCode = TargetTranslationLanguageSet.getTargetTranslationISOCode(p_isoLanguageCode);
        
        if  ( targetTranslationISOCode != null )
        {
            return getText( targetTranslationISOCode );
        }
        
        return null;
    }

    
    private static String getTranslatedText(Translations p_localTranslations, ITranslation p_inheritedTranslation, ITranslationKind p_translationKind, ISOLanguageCode p_isoLanguageCode)
    {
        String text = StringUtils.blankDef( TextTranslations.getLocalLanguageString( p_localTranslations, p_isoLanguageCode.value ), null );
        
        if  ( text != null && EGenOptions.markupLanguage() )
        {
            text += " [L]";
        }
        
        else if ( text == null && p_inheritedTranslation != null && p_translationKind != null )
        {
            try
            {
                text = p_inheritedTranslation.getText( p_translationKind, p_isoLanguageCode.locale );
                text = StringUtils.blankDef( text, null );
                
                if  ( text != null && EGenOptions.markupLanguage() )
                {
                    text += " [I]";
                }
            }
            catch (TranslationException p_ex1)
            {
                // Do Nothing 
            }
        }
        
        if  ( text == null && EGenOptions.markupLanguage() && ! p_isoLanguageCode.equals( TargetTranslationLanguageSet.getBaseISOLanguageCode() ) )
        {
            text = getTranslatedText( p_localTranslations, p_inheritedTranslation, p_translationKind, TargetTranslationLanguageSet.getBaseISOLanguageCode() ) + " (" + p_isoLanguageCode.value + ")";
        }
        
        return text;
    }

    
    /**
     * @return true if there are translations available. NB: Even if this is false, the {@link #getText()} may return non-null default text to use
     */
    public boolean hasTranslations()
    {
        return true;
    }

    /**
     * Gets the language string for the specified translation.
     * 
     * @param p_localTranslations 
     * @param p_isoLanguageCode     
     * @return Translated string, null if p_translations is null or there is no translation available for the specified language code.
     */
    private static String getLocalLanguageString(Translations p_localTranslations, String p_isoLanguageCode)
    {
        if ( p_localTranslations != null )
        {
            final Iterator<EObject> iter = p_localTranslations.eContents().iterator();

            while ( iter.hasNext() )
            {
                EObject o = iter.next();

                if ( o instanceof LocalTranslation )
                {
                    LocalTranslation translation = (LocalTranslation) o;

                    if ( p_isoLanguageCode.equals( translation.getLocale() ) )
                    {
                        return translation.getText();
                    }
                }
            }   
        }
        
        return null;
    }

    @Override
    public String toString()
    {
        return getText();
    }
    
    private static class DefaultText extends TextTranslations
    {
        private DefaultText(String p_defaultText)
        {
            super( null, null, null, p_defaultText );
        }
        
        @Override
        public String getText(ISOLanguageCode p_isoLanguageCode)
        {
            return null;
        }

        @Override
        public String getText(String p_isoLanguageCode)
        {
            return getText();
        }
        
        @Override
        public boolean hasTranslations()
        {
            // We need to force this to true so that we attempt to generate dummy entries for later translations
            //
            return true;
        }
    }
    
    private static class UntranslatableText extends TextTranslations
    {
        private UntranslatableText(String p_untranslatableText)
        {
            super( null, null, null, p_untranslatableText );
        }
        
        @Override
        public String getText(ISOLanguageCode p_isoLanguageCode)
        {
            throw new UnsupportedOperationException("Cannot have locale specific text for untranslateable text: " + getDetails());
        }

        @Override
        public String getText(String p_isoLanguageCode)
        {
            throw new UnsupportedOperationException("Cannot have language specific text for untranslateable text: " + getDetails());
        }
        
        @Override
        public boolean hasTranslations()
        {
            return false;
        }
    }

    @Override
    public String getDetails()
    {
        return IDebuggable.Default.getDetails( this );
    }

    @Override
    public String getDetails(DebugLevel p_debugLevel)
    {
        return IDebuggable.Default.getDetails( this, p_debugLevel );
    }

    @Override
    public StringBuilder addDetails(StringBuilder p_stringBuilder, DebugLevel p_debugLevel)
    {
        DetailFormatter.toString(this, p_debugLevel, p_stringBuilder);

        p_stringBuilder.append( " Default: " ).append(getText());

        if  ( p_debugLevel.display( DebugLevel.HIGH ) )
        {
            // TODO: Add all translations
            //
            p_stringBuilder.append( " Local: " ).append(m_localTranslations);
            p_stringBuilder.append( " Inherited: " ).append(m_inheritedTranslation);
        }
        
        return p_stringBuilder;
    }
}

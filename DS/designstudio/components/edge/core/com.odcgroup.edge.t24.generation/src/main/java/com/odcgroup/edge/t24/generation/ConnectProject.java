package com.odcgroup.edge.t24.generation;

import com.acquire.intelligentforms.FormContext;

// TODO: Make this class redundant if possible

/**
 * The Class ConnectProject.
 */
public class ConnectProject
{
    
    /** The Constant ENGLISH with value of {@value}. */
    public static final int   ENGLISH = 0;
    
    /** The Constant FRENCH with value of {@value}. */
    public static final int   FRENCH  = 1;
    
    /** The Constant GERMAN with value of {@value}. */
    public static final int   GERMAN  = 2;

    /** The form context. */
    private final FormContext m_formContext;

    /**
     * Instantiates a new connect project.
     *
     * @param p_formContext the form context
     */
    public ConnectProject(FormContext p_formContext)
    {
        m_formContext = p_formContext;
    }

    /**
     * Gets the form context.
     *
     * @return the formContext
     */
    public FormContext getFormContext()
    {
        return m_formContext;
    }
}

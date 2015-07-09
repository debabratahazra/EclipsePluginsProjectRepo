package com.odcgroup.edge.t24.generation.util;

import gen.com.acquire.intelligentforms.entities.ComponentItemGroupWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationItemGroupWrapper;
import gen.com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationTypeWrapper;

import java.net.URL;

import com.acquire.intelligentforms.FormContext;
import com.acquire.intelligentforms.entities.ComponentItemGroup;
import com.acquire.intelligentforms.entities.presentation.html.waihtml.richhtml.RichHTMLPresentationItemGroup;
import com.edgeipk.builder.templates.TemplateProject;

/**
 * TODO: Document me!
 *
 * @author sakbar
 *
 */
public class PaletteProject
{
    // TODO: Extend TemplateProject .. do a clone for any returned objects .. maybe dynamic proxy for getters?
    
    private static PaletteProject s_default;
    
    public static PaletteProject getDefault()
    {
        if  ( s_default == null )
        {
            s_default = new PaletteProject("/templates/PaletteTemplate.ifp", "/templates/Brand.ect");
        }
        
        return s_default;
    }

    private TemplateProject m_paletteProject;

    private PaletteProject(String p_project, String p_theme)
    {
        URL projectURL = ResourcesUtil.getMandatoryPlugInResource(p_project);
        
        URL themeURL = ResourcesUtil.getMandatoryPlugInResource(p_theme);
        
        m_paletteProject = TemplateProject.getTemplateProject( projectURL, themeURL );
    }

    /**
     * Creates an the empty interface component item group for use in the specified p_formContext.
     * 
     * @param p_formContext the form context
     * @param p_richPresType the rich presentation type
     * @return the empty interface component item group
     */
    public RichHTMLPresentationItemGroupWrapper createEmptyInterfaceComponentItemGroup(FormContext p_formContext, RichHTMLPresentationTypeWrapper p_richPresType)
    {
        ComponentItemGroupWrapper cigWrapper = (ComponentItemGroupWrapper) m_paletteProject.getPhaseItemGroup( "Palette.Interfaces", "EmptyInterface" );
        
        RichHTMLPresentationItemGroupWrapper presCIGWrapper = (RichHTMLPresentationItemGroupWrapper) m_paletteProject.getPresentationPhaseItemGroup( p_richPresType, cigWrapper );

        // Clone both the phase/pres item group entities
        //
        ComponentItemGroup cig = (ComponentItemGroup) cigWrapper.unwrap().cloneForContextAndMap( p_formContext, null, null, false, false );
        
        RichHTMLPresentationItemGroup presCIG = (RichHTMLPresentationItemGroup) presCIGWrapper.unwrap().cloneForContextAndMap( p_formContext, null, null, false, false );
        
        presCIG.setEntityNode( cig );
        
        cigWrapper = ComponentItemGroupWrapper.wrap( cig );
        
        presCIGWrapper = RichHTMLPresentationItemGroupWrapper.wrap( presCIG );
        
        return presCIGWrapper;
    }
}

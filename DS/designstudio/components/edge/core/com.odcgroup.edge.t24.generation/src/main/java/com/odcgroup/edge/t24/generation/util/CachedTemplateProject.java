/*
 * $RCSfile$
 * $Author$
 * $Revision$
 * $Date$
 *
 * Copyright (c) 2001-2014 TEMENOS HEADQUARTERS SA. All rights reserved.
 *
 * This source code is protected by copyright laws and international copyright treaties,
 * as well as other intellectual property laws and treaties.
 *  
 * Access to, alteration, duplication or redistribution of this source code in any form 
 * is not permitted without the prior written authorisation of TEMENOS HEADQUARTERS SA.
 * 
 */

package com.odcgroup.edge.t24.generation.util;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

import com.acquire.intelligentforms.FormContext;
import com.acquire.intelligentforms.data.DataDictionary;
import com.acquire.intelligentforms.entities.DictionaryRoot;
import com.acquire.intelligentforms.entities.GenericEntity;
import com.acquire.intelligentforms.entities.GenericNode;
import com.acquire.intelligentforms.entities.PresentationRoot;
import com.acquire.intelligentforms.entities.ProductsRoot;
import com.acquire.intelligentforms.entities.RulesRoot;
import com.acquire.intelligentforms.entities.evanotes.EVANoteDefinitionRoot;
import com.acquire.intelligentforms.entities.mapping.ImportRecordRoot;
import com.acquire.intelligentforms.entities.presentation.PresentationObject;
import com.acquire.intelligentforms.entities.presentation.util.PresentationUtility;
import com.acquire.intelligentforms.entities.root.DataTypesRoot;
import com.acquire.intelligentforms.entities.root.IntegrationRoot;
import com.acquire.intelligentforms.entities.root.ListsRoot;
import com.acquire.intelligentforms.entities.root.StructuresRoot;
import com.acquire.intelligentforms.entities.root.TypesRoot;
import com.acquire.intelligentforms.entities.util.EntityUtility;
import com.edgeipk.builder.templates.TemplateProject;
import com.edgeipk.builder.util.DOMProjectLoader;
import com.edgeipk.builder.util.ProjectUtility;
import com.odcgroup.edge.t24.generation.EGenOptions;
import com.odcgroup.edge.t24.generation.EGenOptions.TemplateCacheOption;

/**
 * Cache of template projects, at the moment just the DOM models
 *
 * @author sakbar
 *
 */
public class CachedTemplateProject
{
    private static Map<String, Resource> s_cachedResources = new HashMap<String, Resource>();
    
    public static TemplateProject getTemplateProject(String p_projectResource, String p_themeResource)
    {
        return getTemplateProject(p_projectResource, p_themeResource, EGenOptions.templateCache());
    }

    public static TemplateProject getTemplateProject(String p_projectResource, String p_themeResource, TemplateCacheOption p_option)
    {
        switch (p_option)
        {
            case UNCACHED:
                return getUncached( p_projectResource, p_themeResource );

            case CACHED:
                return buildFromDOMProjectLoaderCache( p_projectResource, p_themeResource );

            case CLONED:
                return cloneFromFormContextCache( p_projectResource, p_themeResource );

            default:
                throw new RuntimeException("Unhandled template cache option: " + p_option);
        }
    }
    
    private static TemplateProject getUncached(String p_projectResource, String p_themeResource)
    {
        DOMProjectLoader projectLoader = getDOMProjectLoader( p_projectResource, p_themeResource );
        
        FormContext formContext = projectLoader.loadProject( p_projectResource, p_themeResource, false);
            
        return TemplateProject.getTemplateProject( formContext );
    }

    private static TemplateProject buildFromDOMProjectLoaderCache(String p_projectResource, String p_themeResource)
    {
        Resource resource = getResource( p_projectResource, p_themeResource );
        
        if  ( resource.m_projectLoader == null )
        {
            resource.m_projectLoader = getDOMProjectLoader( p_projectResource, p_themeResource );
        }
        
        FormContext formContext = resource.m_projectLoader.loadProject( p_projectResource, p_themeResource, true);
        
        return TemplateProject.getTemplateProject( formContext );
    }

    private static TemplateProject cloneFromFormContextCache(String p_projectResource, String p_themeResource)
    {
        Resource resource = getResource( p_projectResource, p_themeResource );
        
        if  ( resource.m_formContext == null )
        {
            DOMProjectLoader projectLoader = getDOMProjectLoader( p_projectResource, p_themeResource );
            
            resource.m_formContext = projectLoader.loadProject( p_projectResource, p_themeResource, false);
        }
        
        FormContext formContext = cloneContext( resource );
        
        return TemplateProject.getTemplateProject( formContext );
    }

    private static DOMProjectLoader getDOMProjectLoader(String p_projectResource, String p_themeResource)
    {
        URL projectURL = ResourcesUtil.getMandatoryPlugInResource(p_projectResource);
        
        URL themeURL = ResourcesUtil.getMandatoryPlugInResource(p_themeResource);
        
        return ProjectUtility.getProjectLoader( projectURL, null, themeURL );
    }

    private static FormContext cloneContext(Resource p_resource)
    {
        FormContext formContext = ProjectUtility.newBuildingFormContext();

        formContext.getProject().clearAllAttributes();
        
        formContext.getProject().setAttributes( p_resource.m_formContext.getProject().getAttributes() );
        
        Map<GenericEntity,GenericEntity> newNodeCloneMap = new IdentityHashMap<GenericEntity,GenericEntity>(p_resource.m_mapSize > 0 ? p_resource.m_mapSize : 200);
        
        // Types - Lists/DataTypes/Structures
        //
        TypesRoot newTypesRoot = new TypesRoot( formContext );
        
        ListsRoot listRoot = (ListsRoot) cloneRootNode( p_resource.m_formContext.getProject().getTypesRoot().getLists(), formContext, null );
        newTypesRoot.setLists( listRoot );

        DataTypesRoot dataTypesRoot = (DataTypesRoot) cloneRootNode( p_resource.m_formContext.getProject().getTypesRoot().getDataTypes(), formContext, null);
        newTypesRoot.setDataTypes( dataTypesRoot );

        StructuresRoot structsRoot = (StructuresRoot) cloneRootNode( p_resource.m_formContext.getProject().getTypesRoot().getStructures(), formContext, null);
        newTypesRoot.setStructures( structsRoot );

        formContext.getProject().setTypesRoot( newTypesRoot );
        
        newTypesRoot.setupDefaults();
        
        // Dictionary
        //
        DictionaryRoot dictRoot = (DictionaryRoot) cloneRootNode( p_resource.m_formContext.getProject().getDictionaryRoot(), formContext, null);
        DataDictionary dictionary = new DataDictionary( formContext );
        formContext.getProject().setDictionary( dictionary );
        dictionary.setDictionaryRoot( dictRoot );

        // Integration
        //
        IntegrationRoot intRoot = (IntegrationRoot) cloneRootNode( p_resource.m_formContext.getProject().getDataMapping(), formContext, null);
        formContext.getProject().setDataMapping( intRoot );
        
        // Rules
        //
        RulesRoot rulesRoot = (RulesRoot) cloneRootNode( p_resource.m_formContext.getProject().getRules(), formContext, newNodeCloneMap);
        formContext.getProject().setRules( rulesRoot );
        
        // Process
        //
        ProductsRoot productsRoot = (ProductsRoot) cloneRootNode( p_resource.m_formContext.getProject().getProducts(), formContext, newNodeCloneMap);
        formContext.getProject().setProducts( productsRoot );
        
        // Presentation
        //
        PresentationRoot presRoot = (PresentationRoot) cloneRootNode( p_resource.m_formContext.getProject().getPresentationTypes(), formContext, newNodeCloneMap);
        formContext.getProject().setPresentationTypes( presRoot );
        
        // Import
        //
        ImportRecordRoot impRoot = (ImportRecordRoot) cloneRootNode( p_resource.m_formContext.getProject().getImportRecords(), formContext, null);
        formContext.getProject().setImportRecords( impRoot );
                
        // Update the presentation lookup, otherwise its empty
        //
        ArrayList<?> childrenList = presRoot.getChildrenList();
        
        for (Object object : childrenList)
        {
            if  ( object instanceof PresentationObject )
            {
                PresentationUtility.updatePresentationEntityLookup((PresentationObject)object);
            }
        }
        
        // Notes
        //
        EVANoteDefinitionRoot notesRoot = (EVANoteDefinitionRoot) cloneRootNode( p_resource.m_formContext.getProject().getEVANotesRoot(), formContext, newNodeCloneMap);
        formContext.getProject().setEVANotesRoot( notesRoot );
        
        // Fix up any links
        //
        EntityUtility.updateLinkedItems(rulesRoot, newNodeCloneMap);
        EntityUtility.updateLinkedItems(productsRoot, newNodeCloneMap);
        EntityUtility.updateLinkedItems(presRoot, newNodeCloneMap);
        
        p_resource.m_mapSize = newNodeCloneMap.size() + 10;
        
        return formContext;
    }
        

    private static GenericNode cloneRootNode(GenericNode p_rootNode, FormContext p_newContext, Map<GenericEntity,GenericEntity> p_newNodeCloneMap)
    {
        try
        {
            GenericEntity.setOptimisedCloneWithinThread( true );
            
            // Need to keep EID in order for presentation to be fixed up with it's entity node
            //
            GenericNode rootNode = (GenericNode) p_rootNode.cloneForContextAndMap( p_newContext, p_newNodeCloneMap, null, true, true );
            return rootNode;
        }
        finally
        {
            GenericEntity.setOptimisedCloneWithinThread( false );
        }
    }

    private static Resource getResource(String p_projectResource, String p_themeResource)
    {
        String cacheKey = p_projectResource + ":" + p_themeResource;
        
        Resource resource = s_cachedResources.get( cacheKey );
        
        if  ( resource == null )
        {
            resource = new Resource();
            
            s_cachedResources.put( cacheKey, resource );
        }
        
        return resource;
    }
    
    public static void clearCache()
    {
        s_cachedResources.clear();
    }
    
    private static class Resource
    {
        private DOMProjectLoader m_projectLoader;
        private FormContext m_formContext;
        private int m_mapSize;
    }
}

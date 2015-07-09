package com.odcgroup.edge.t24.generation;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.slf4j.Logger;

import com.acquire.intelligentforms.entities.Project;
import com.odcgroup.edge.t24.generation.composite.singleifp.SingleIFPCompositeProject;
import com.odcgroup.edge.t24.generation.util.ComponentMap;
import com.odcgroup.edge.t24.generation.util.ComponentMap.ComponentType;
import com.odcgroup.edge.t24.generation.util.GenLogger;
import com.odcgroup.edge.t24.generation.util.MapperUtility;
import com.odcgroup.edge.t24ui.BespokeCompositeScreen;
import com.odcgroup.workbench.generation.cartridge.ng.ModelLoader;
import com.temenos.connect.utils.SlangManager;

public class CompositeScreenMapper extends EdgeMapper<BespokeCompositeScreen>
{
    private static final Logger LOGGER = GenLogger.getLogger(CompositeScreenMapper.class);

    CompositeScreenMapper(IProject p_eclipseProject, MainOutputLocation p_mainOutputLocation, SlangManager p_slangManager)
	{
		super(p_eclipseProject, p_mainOutputLocation, p_slangManager);
	}

    @Override
    public Location getLocation(BespokeCompositeScreen p_bespokeCOS)
    {
        final String ifpFriendlyCompositeName = MapperUtility.processT24NameToIRISName(p_bespokeCOS.getName());
        
        return new Location( "cos_" + ifpFriendlyCompositeName, "composites/singleIFP/" + ifpFriendlyCompositeName, ifpFriendlyCompositeName + Project.PROJECT_FILE_EXTENSION, ifpFriendlyCompositeName ); 
    }
    
	@Override
	public void map(BespokeCompositeScreen p_bespokeCOS, ComponentMap p_componentMap, ModelLoader p_modelLoader, Location p_location, String p_modelName) throws Exception
	{
    	final File compositeComponentDir = new File(p_location.getFullProjectDir());
    	final File compositeTemplatesDir = new File(compositeComponentDir, "templates");
    	
    	LOGGER.info("Composite: {}", p_bespokeCOS.getName());

    	final SingleIFPCompositeProject generator = SingleIFPCompositeProject.create(this, p_bespokeCOS);
    	setConnectProject(new ConnectProject(generator.getFormContext()), p_modelName);    	
    	generator.go(compositeTemplatesDir);
    	
    	// Add it to the component map
    	
    	storeMapperDetails( p_componentMap, p_location, ComponentType.COMPOSITE, getMapperData());
	}
}

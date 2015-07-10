package com.tel.autosysframework.editpart;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MouseListener;

import org.eclipse.gef.AccessibleAnchorProvider;

//import com.tel.autosysframwork.figures.InputPortFigure;
import com.tel.autosysframework.figures.BaseFigure;
import com.tel.autosysframework.figures.OutputFigure;
import com.tel.autosysframework.figures.lte.CRCFigure;
import com.tel.autosysframework.figures.lte.CSBFigure;
import com.tel.autosysframework.figures.lte.ChannelCodingFigure;
import com.tel.autosysframework.figures.lte.CyclicPrefixFigure;
import com.tel.autosysframework.figures.lte.LayerMapperFigure;
import com.tel.autosysframework.figures.lte.ModulatorFigure;
import com.tel.autosysframework.figures.lte.OFDMFigure;
import com.tel.autosysframework.figures.lte.PrecodingFigure;
import com.tel.autosysframework.figures.lte.REMFigure;
import com.tel.autosysframework.figures.lte.RateMatchingFigure;
import com.tel.autosysframework.figures.lte.ScramblerFigure;
import com.tel.autosysframework.figures.ports.InputPortFigure;
import com.tel.autosysframework.figures.ports.OutputPortFigure;
import com.tel.autosysframework.figures.vlcd.MonitorAreaFigure;
import com.tel.autosysframework.figures.vlcd.VideoAnalyserFigure;
import com.tel.autosysframework.figures.vlcd.VideoDisplayFigure;
import com.tel.autosysframework.model.CRC;
import com.tel.autosysframework.model.CSB;
import com.tel.autosysframework.model.ChannelCoding;
import com.tel.autosysframework.model.CyclicPrefix;
import com.tel.autosysframework.model.InputPort;
import com.tel.autosysframework.model.LayerMapper;
import com.tel.autosysframework.model.Modulator;
import com.tel.autosysframework.model.OFDM;
import com.tel.autosysframework.model.OutputPort;
import com.tel.autosysframework.model.Precoding;
import com.tel.autosysframework.model.REM;
import com.tel.autosysframework.model.RateMatching;
import com.tel.autosysframework.model.Scrambler;
import com.tel.autosysframework.model.MonitorArea;
import com.tel.autosysframework.model.VideoAnalyser;
import com.tel.autosysframework.model.VideoDisplay;



/**
 * EditPart for holding gates in the Logic Example.
 */
public class AutoModelEditPart
extends OutputEditPart
{

	/**
	 * Returns a newly created Figure of this.
	 *
	 * @return A new Figure of this.
	 */


	protected IFigure createFigure() {
		/*Output*/IFigure figure = null;
		//	BaseFigure basefigure = null;
		if (getModel() == null)
			return null;
		if (getModel() instanceof Modulator)	
			figure = (OutputFigure)new ModulatorFigure();
		
		else if (getModel() instanceof LayerMapper)	
			figure = (OutputFigure)new LayerMapperFigure();
		
		else if (getModel() instanceof CRC)	
			figure = (OutputFigure)new CRCFigure();
		
		else if (getModel() instanceof CSB)	
			figure = (OutputFigure)new CSBFigure();
		
		else if (getModel() instanceof ChannelCoding)	
			figure = (OutputFigure)new ChannelCodingFigure();
		
		else if (getModel() instanceof RateMatching)	
			figure = (OutputFigure)new RateMatchingFigure();
		
		else if (getModel() instanceof Scrambler)	
			figure = (OutputFigure)new ScramblerFigure();
		
		else if (getModel() instanceof Precoding)	
			figure = (OutputFigure)new PrecodingFigure();
		
		else if (getModel() instanceof REM)	
			figure = (OutputFigure)new REMFigure();
		
		else if (getModel() instanceof OFDM)	
			figure = (OutputFigure)new OFDMFigure();
		
		else if (getModel() instanceof CyclicPrefix)	
			figure = (OutputFigure)new CyclicPrefixFigure();
		
		else if (getModel() instanceof InputPort)	
			/*base*/figure = (/*Base*/OutputFigure)new InputPortFigure();
		
		else if (getModel() instanceof OutputPort) 	
			/*base*/figure = (BaseFigure)new OutputPortFigure();
		
		else if (getModel() instanceof MonitorArea)	
			figure = (OutputFigure)new MonitorAreaFigure();
		
		else if (getModel() instanceof VideoAnalyser)	
			figure = (OutputFigure)new VideoAnalyserFigure();
		
		else if (getModel() instanceof VideoDisplay)	
			figure = (OutputFigure)new VideoDisplayFigure();
		/*else
		figure = new OutputFigure();*/
		return figure;
	}

	public Object getAdapter(Class key) {
		if (key == AccessibleAnchorProvider.class)
			return new DefaultAccessibleAnchorProvider() { 
			public List getSourceAnchorLocations() {
				List list = new ArrayList();
				Vector sourceAnchors = getNodeFigure().getSourceConnectionAnchors();
				for (int i=0; i<sourceAnchors.size(); i++) {
					ConnectionAnchor anchor = (ConnectionAnchor)sourceAnchors.get(i);
					list.add(anchor.getReferencePoint().getTranslated(0, -3));
				}

				return list;
			}
			public List getTargetAnchorLocations() {
				List list = new ArrayList();
				Vector targetAnchors = getNodeFigure().getTargetConnectionAnchors();
				for (int i=0; i<targetAnchors.size(); i++) {
					ConnectionAnchor anchor = (ConnectionAnchor)targetAnchors.get(i);
					list.add(anchor.getReferencePoint());
				}

				return list;
			}
		};
		return super.getAdapter(key);
	}

}

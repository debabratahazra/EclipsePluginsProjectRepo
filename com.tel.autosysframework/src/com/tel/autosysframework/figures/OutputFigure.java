package com.tel.autosysframework.figures;

import org.eclipse.draw2d.FreeformLayout;

import com.tel.autosysframework.model.SimpleOutput;



public class OutputFigure 
extends BaseFigure 
{
	protected FixedConnectionAnchor outputConnectionAnchor = new FixedConnectionAnchor(this);

	public OutputFigure() {	

		
		
		
		outputConnectionAnchors.addElement(outputConnectionAnchor);
		connectionAnchors.put(SimpleOutput.TERMINAL_OUT, outputConnectionAnchor);

		setLayoutManager(new FreeformLayout());
	}

}

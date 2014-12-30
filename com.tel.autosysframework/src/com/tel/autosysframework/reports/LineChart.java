package com.tel.autosysframework.reports;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.eclipse.birt.chart.model.Chart;
import org.eclipse.birt.chart.model.ChartWithAxes;
import org.eclipse.birt.chart.model.attribute.AxisType;
import org.eclipse.birt.chart.model.attribute.IntersectionType;
import org.eclipse.birt.chart.model.attribute.Marker;
import org.eclipse.birt.chart.model.attribute.TickStyle;
import org.eclipse.birt.chart.model.attribute.impl.ColorDefinitionImpl;
import org.eclipse.birt.chart.model.component.Axis;
import org.eclipse.birt.chart.model.component.Series;
import org.eclipse.birt.chart.model.component.impl.SeriesImpl;
import org.eclipse.birt.chart.model.data.BaseSampleData;
import org.eclipse.birt.chart.model.data.DataFactory;
import org.eclipse.birt.chart.model.data.NumberDataSet;
import org.eclipse.birt.chart.model.data.OrthogonalSampleData;
import org.eclipse.birt.chart.model.data.SampleData;
import org.eclipse.birt.chart.model.data.SeriesDefinition;
import org.eclipse.birt.chart.model.data.TextDataSet;
import org.eclipse.birt.chart.model.data.impl.NumberDataSetImpl;
import org.eclipse.birt.chart.model.data.impl.SeriesDefinitionImpl;
import org.eclipse.birt.chart.model.data.impl.TextDataSetImpl;
import org.eclipse.birt.chart.model.impl.ChartWithAxesImpl;
import org.eclipse.birt.chart.model.layout.Plot;
import org.eclipse.birt.chart.model.type.LineSeries;
import org.eclipse.birt.chart.model.type.impl.LineSeriesImpl;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import com.tel.autosysframework.editor.AutosysFrameworkEditor;
import com.tel.autosysframework.views.GeneralConfigure;

public class LineChart 
{
	public static final Chart createStackedLine( )
	{
		ChartWithAxes cwaLine = ChartWithAxesImpl.create( );
		cwaLine.setType( "Line Chart" ); //$NON-NLS-1$
		cwaLine.setSubType( "Stacked" ); //$NON-NLS-1$

		// Plot
		cwaLine.getBlock( ).setBackground( ColorDefinitionImpl.WHITE( ) );
		Plot p = cwaLine.getPlot( );
		p.getClientArea( ).setBackground( ColorDefinitionImpl.create( 255,
				255,
				225 ) );

		// Title
		cwaLine.getTitle( )
		.getLabel( )
		.getCaption( )
		.setValue( "Output Plot" );//$NON-NLS-1$

		// Legend
		cwaLine.getLegend( ).setVisible( false );

		// X-Axis
		Axis xAxisPrimary = ((ChartWithAxesImpl)cwaLine).getPrimaryBaseAxes( )[0];
		xAxisPrimary.setType( AxisType.LINEAR_LITERAL );
		xAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.BELOW_LITERAL );
		xAxisPrimary.getOrigin( ).setType( IntersectionType.MIN_LITERAL );
		xAxisPrimary.getTitle().getCaption().setValue("Index");

		// Y-Axis
		Axis yAxisPrimary = ((ChartWithAxesImpl)cwaLine).getPrimaryOrthogonalAxis( xAxisPrimary );
		yAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.LEFT_LITERAL );
		yAxisPrimary.getTitle().getCaption().setValue("Value");

		// Data Set
		double[] x_values = null;
		double[] y_values = null;
		File datasetsource = null;
		FileInputStream fis = null;
		DataInputStream dis = null;
		MessageBox msgbox = null;

		datasetsource = new File(GeneralConfigure.getOutputpath());
		if(!datasetsource.exists()){
			msgbox = new MessageBox(new Shell(AutosysFrameworkEditor.display),SWT.ERROR);
			msgbox.setText("#Error");
			msgbox.setMessage("DataSet Source not found\n" +
			"Simulation Not done yet");
			msgbox.open();
			return null;
		}
		try {
			fis = new FileInputStream(datasetsource);
			dis = new DataInputStream(fis);
			String outputtype = dis.readLine();
			if(outputtype.equalsIgnoreCase("Binary output________________________________________")){
				outputtype = dis.readLine();
				int i;
				i= Integer.parseInt(outputtype.substring(outputtype.lastIndexOf(":")+1));
				y_values = new double[i];
				x_values = new double[i];
				for (int j = 0; j < i; j++) {
					y_values[j] = Double.parseDouble(dis.readLine());
					x_values[j] = j;
				}
				outputtype = dis.readLine();
				if(!outputtype.equalsIgnoreCase("end")) {
					y_values = null;
					x_values = null;
					i = Integer.parseInt(outputtype.substring(outputtype.lastIndexOf(":")+1));
					y_values = new double[i];
					x_values = new double[i];
					for (int j = 0; j < i; j++){
						y_values[j] = Double.parseDouble(dis.readLine());
						x_values[j] = j;
					}
				}
			} else if(outputtype.equalsIgnoreCase("Complex output________________________________________")){
				outputtype = dis.readLine();
				int i = Integer.parseInt(outputtype.substring(outputtype.lastIndexOf(":")+1));
				y_values = new double[i];
				x_values = new double[i];
				for (int j = 0; j < i; j++){
					outputtype = dis.readLine();
					double real = Double.parseDouble(outputtype.substring(0, outputtype.lastIndexOf("j") - 1).trim());
					double imag = Double.parseDouble(outputtype.substring(outputtype.lastIndexOf("j") + 1).trim());
					y_values[j] = Math.sqrt((real*real)+(imag*imag));
					x_values[j] = j;
				}
			}else {
				msgbox = new MessageBox(new Shell(AutosysFrameworkEditor.display),SWT.ERROR);
				msgbox.setText("#Error");
				msgbox.setMessage("DataSet Source format not supported");
				msgbox.open();
				fis.close();
				dis.close();
				datasetsource = null;
				return null;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		TextDataSet categoryValues1 = TextDataSetImpl.create( new String[]{
		"Index"} );//$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		TextDataSet categoryValues2 = TextDataSetImpl.create( new String[]{
		"Values"} );//$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		NumberDataSet orthoValues1 = NumberDataSetImpl.create( x_values );
		NumberDataSet orthoValues2 = NumberDataSetImpl.create( y_values);

		SampleData sd = DataFactory.eINSTANCE.createSampleData( );
		BaseSampleData sdBase = DataFactory.eINSTANCE.createBaseSampleData( );
		sdBase.setDataSetRepresentation( "" );//$NON-NLS-1$
		sd.getBaseSampleData( ).add( sdBase );

		OrthogonalSampleData sdOrthogonal1 = DataFactory.eINSTANCE.createOrthogonalSampleData( );
		sdOrthogonal1.setDataSetRepresentation( "" );//$NON-NLS-1$
		sdOrthogonal1.setSeriesDefinitionIndex( 0 );
		sd.getOrthogonalSampleData( ).add( sdOrthogonal1 );

		/*OrthogonalSampleData sdOrthogonal2 = DataFactory.eINSTANCE.createOrthogonalSampleData( );
		sdOrthogonal2.setDataSetRepresentation( "" );//$NON-NLS-1$
		sdOrthogonal2.setSeriesDefinitionIndex( 1 );
		sd.getOrthogonalSampleData( ).add( sdOrthogonal2 );*/

		cwaLine.setSampleData( sd );

		// X-Series
		Series seBase = SeriesImpl.create( );
		//		seBase.setDataSet( categoryValues1 );
		seBase.setDataSet( orthoValues1 );

		SeriesDefinition sdX = SeriesDefinitionImpl.create( );
		xAxisPrimary.getSeriesDefinitions( ).add( sdX );
		sdX.getSeries( ).add( seBase );

		// Y-Sereis
		LineSeries ls1 = (LineSeries) LineSeriesImpl.create( );
		ls1.setDataSet( orthoValues2 );
		//		ls1.setDataSet( categoryValues2 );
		ls1.setStacked( true );
		ls1.getLineAttributes( ).setColor( ColorDefinitionImpl.BLUE());
		for ( int i = 0; i < ls1.getMarkers( ).size( ); i++ )
		{
			//			( (Marker) ls1.getMarkers( ).get( i ) ).setType( MarkerType.CROSS_LITERAL );
			//			( (Marker) ls1.getMarkers( ).get( i ) ).unsetType();
			( (Marker) ls1.getMarkers( ).get( i ) ).unsetVisible();
			( (Marker) ls1.getMarkers( ).get( i ) ).unsetSize();
		}
		ls1.getLabel( ).setVisible( false );


		/*LineSeries ls2 = (LineSeries) LineSeriesImpl.create( );
		ls2.setDataSet( orthoValues2 );
		ls2.setStacked( true );
		ls2.getLineAttributes( ).setColor( ColorDefinitionImpl.CREAM( ) );
		for ( int i = 0; i < ls2.getMarkers( ).size( ); i++ )
		{
			( (Marker) ls2.getMarkers( ).get( i ) ).setType( MarkerType.TRIANGLE_LITERAL );
		}
		ls2.getLabel( ).setVisible( true );*/

		SeriesDefinition sdY = SeriesDefinitionImpl.create( );
		sdY.getSeriesPalette( ).shift( -2 );
		yAxisPrimary.getSeriesDefinitions( ).add( sdY );
		sdY.getSeries( ).add( ls1 );
		/*EList list = sdY.getSeries();
		Iterator itr = list.iterator();
		while(itr.hasNext()) {
			SeriesDefinitionImpl Y = (SeriesDefinitionImpl) itr.next();
//			Y.unsetSorting();
			Y.unsetZOrder();
		}*/
		//		sdY.getSeries( ).add( ls2 );

		return cwaLine;
	}
}

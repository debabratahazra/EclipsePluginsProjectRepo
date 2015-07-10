package com.tel.autosysframework.figures;


import java.beans.PropertyChangeListener;
import java.util.Collection;
import java.util.List;

import org.eclipse.draw2d.AncestorListener;
import org.eclipse.draw2d.Border;
import org.eclipse.draw2d.CoordinateListener;
import org.eclipse.draw2d.EventDispatcher;
import org.eclipse.draw2d.FigureListener;
import org.eclipse.draw2d.FocusEvent;
import org.eclipse.draw2d.FocusListener;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.KeyEvent;
import org.eclipse.draw2d.KeyListener;
import org.eclipse.draw2d.LayoutListener;
import org.eclipse.draw2d.LayoutManager;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.MouseMotionListener;
import org.eclipse.draw2d.PolylineDecoration;
import org.eclipse.draw2d.RotatableDecoration;
import org.eclipse.draw2d.RoutingAnimator;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.TreeSearch;
import org.eclipse.draw2d.UpdateManager;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.geometry.Translatable;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;

import com.tel.autosysframework.model.SimpleOutput;
import com.tel.autosysframework.model.Wire;

public class FigureFactory {

public static PolylineConnection createNewBendableWire(Wire wire){
	PolylineConnection conn = new PolylineConnection();
	conn.addRoutingListener(RoutingAnimator.getDefault());	
	//conn.setSourceDecoration(new PolylineDecoration());
	conn.setTargetDecoration(new PolylineDecoration());
	return conn;
}

public static PolylineConnection createNewWire(Wire wire){

	PolylineConnection conn = new PolylineConnection();
	conn.addRoutingListener(RoutingAnimator.getDefault());
	PolygonDecoration arrow;
	
	if (wire == null || wire.getSource() instanceof SimpleOutput)
		arrow = null;
	else {
		arrow = new PolygonDecoration();
		arrow.setTemplate(PolygonDecoration.INVERTED_TRIANGLE_TIP);
		arrow.setScale(5,2.5);
	}
	conn.setSourceDecoration(arrow);
	
	if (wire == null || wire.getTarget() instanceof SimpleOutput)
		arrow = null;
	else {
		arrow = new PolygonDecoration();
		arrow.setTemplate(PolygonDecoration.INVERTED_TRIANGLE_TIP);
		arrow.setScale(5,2.5);
	}
	conn.setTargetDecoration(arrow);
	return conn;
}



}

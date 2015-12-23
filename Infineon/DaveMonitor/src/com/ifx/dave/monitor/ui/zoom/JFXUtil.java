package com.ifx.dave.monitor.ui.zoom;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

public class JFXUtil {

	public static double getXShift( Node descendant, Node ancestor ) {
		double ret = 0.0;
		Node curr = descendant;
		while ( curr != ancestor ) {
			ret += curr.getLocalToParentTransform().getTx();
			curr = curr.getParent();
			if ( curr == null )
				throw new IllegalArgumentException( "'descendant' Node is not a descendant of 'ancestor" );
		}

		return ret;
	}

	public static double getYShift( Node descendant, Node ancestor ) {
		double ret = 0.0;
		Node curr = descendant;
		while ( curr != ancestor ) {
			ret += curr.getLocalToParentTransform().getTy();
			curr = curr.getParent();
			if ( curr == null )
				throw new IllegalArgumentException( "'descendant' Node is not a descendant of 'ancestor" );
		}

		return ret;
	}

	public static void replaceComponent( Node original, Node replacement ) {
		Pane parent = (Pane) original.getParent();
		//transfer any properties (usually constraints)
		replacement.getProperties().putAll( original.getProperties() );
		original.getProperties().clear();

		ObservableList<Node> children = parent.getChildren();
		int originalIndex = children.indexOf( original );
		if ( parent instanceof BorderPane ) {
			BorderPane borderPane = (BorderPane) parent;
			if ( borderPane.getTop() == original ) {
				children.remove( original );
				borderPane.setTop( replacement );

			} else if ( borderPane.getLeft() == original ) {
				children.remove( original );
				borderPane.setLeft( replacement );

			} else if ( borderPane.getCenter() == original ) {
				children.remove( original );
				borderPane.setCenter( replacement );

			} else if ( borderPane.getRight() == original ) {
				children.remove( original );
				borderPane.setRight( replacement );

			} else if ( borderPane.getBottom() == original ) {
				children.remove( original );
				borderPane.setBottom( replacement );
			}
		} else {
			//Hope that preserving the properties and position in the list is sufficient
			children.set( originalIndex, replacement );
		}
	}

	public static StackPane createScalePane( Region region, double w, double h, boolean override ) {
		//If the Region containing the GUI does not already have a preferred width and height, set it.
		//But, if it does, we can use that setting as the "standard" resolution.
		if ( override || region.getPrefWidth() == Region.USE_COMPUTED_SIZE )
			region.setPrefWidth( w );
		else
			w = region.getPrefWidth();

		if ( override || region.getPrefHeight() == Region.USE_COMPUTED_SIZE )
			region.setPrefHeight( h );
		else
			h = region.getPrefHeight();

		StackPane ret = new StackPane();
		ret.setPrefWidth( w );
		ret.setPrefHeight( h );
		if ( region.getParent() != null )
			replaceComponent( region, ret );

		//Wrap the resizable content in a non-resizable container (Group)
		Group group = new Group( region );
		//Place the Group in a StackPane, which will keep it centered
		ret.getChildren().add( group );

		//Bind the scene's width and height to the scaling parameters on the group
		group.scaleXProperty().bind( ret.widthProperty().divide( w ) );
		group.scaleYProperty().bind( ret.heightProperty().divide( h ) );

		return ret;
	}
}

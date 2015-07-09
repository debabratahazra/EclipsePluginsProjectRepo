package com.odcgroup.process.diagram.custom.figures;

import org.eclipse.draw2d.RectangleFigure;

/**
 * externalized the generated figure for reuse
 * @author pkk
 *
 */
public class PoolFigure extends RectangleFigure {

	/**
	 * @generated
	 */
	public static final org.eclipse.swt.graphics.Color BORDER = new org.eclipse.swt.graphics.Color(null, 169, 169, 169);

	/**
	 * @generated
	 */
	public PoolFigure() {
		this.setForegroundColor(org.eclipse.draw2d.ColorConstants.black);
		this.setBorder(new org.eclipse.draw2d.LineBorder(org.eclipse.draw2d.ColorConstants.black));
		createContents();
	}

	/**
	 * @generated
	 */
	private void createContents() {
		VerticalLabel fig_0 = new VerticalLabel();
		setFigurePoolNameFigure(fig_0);
		Object layData0 = null;
		this.add(fig_0, layData0);
	}

	/**
	 * @generated
	 */
	private VerticalLabel fPoolNameFigure;

	/**
	 * @generated
	 */
	public VerticalLabel getFigurePoolNameFigure() {
		return fPoolNameFigure;
	}

	/**
	 * @generated
	 */
	private void setFigurePoolNameFigure(VerticalLabel fig) {
		fPoolNameFigure = fig;
	}

	/**
	 * @generated
	 */
	private boolean myUseLocalCoordinates = false;

	/**
	 * @generated
	 */
	protected boolean useLocalCoordinates() {
		return myUseLocalCoordinates;
	}

	/**
	 * @generated
	 */
	protected void setUseLocalCoordinates(boolean useLocalCoordinates) {
		myUseLocalCoordinates = useLocalCoordinates;
	}

}

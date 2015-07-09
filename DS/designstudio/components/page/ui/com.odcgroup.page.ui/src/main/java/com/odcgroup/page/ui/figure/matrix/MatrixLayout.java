package com.odcgroup.page.ui.figure.matrix;

import java.util.List;

import org.eclipse.draw2d.AbstractLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;

import com.odcgroup.page.model.widgets.matrix.IMatrix;
import com.odcgroup.page.model.widgets.matrix.IMatrixAxis;
import com.odcgroup.page.model.widgets.matrix.IMatrixCell;

/**
 * 
 * @author pkk
 * 
 */
public class MatrixLayout extends AbstractLayout {

	/**
	 * 
	 */
	public MatrixLayout() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.draw2d.AbstractLayout#calculatePreferredSize(org.eclipse.
	 * draw2d.IFigure, int, int)
	 */
	@Override
	protected Dimension calculatePreferredSize(IFigure container, int wHint, int hHint) {
		if (!(container instanceof MatrixFigure)) {
			return new Dimension();
		}
		Dimension size = layout(container, false, 0, 0, wHint, hHint);
		if (wHint != SWT.DEFAULT)
			size.width = wHint;
		if (hHint != SWT.DEFAULT)
			size.height = hHint;

		/*
		 * Adjust for the size of the border
		 */
		size.expand(container.getInsets().getWidth(), container.getInsets().getHeight());
		size.union(getBorderPreferredSize(container));

		return size;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.draw2d.LayoutManager#layout(org.eclipse.draw2d.IFigure)
	 */
	@Override
	public void layout(IFigure container) {
		Rectangle rect = container.getClientArea();
		layout(container, true, rect.x, rect.y, rect.width, rect.height);
	}
	
	/**
	 * @param matrixFigure
	 * @return
	 */
	private MatrixCellFigure getMatrixLastColumn(MatrixFigure matrixFigure) {
		return matrixFigure.getCellFigure(matrixFigure.getChildren(), IMatrixCell.CELLTYPE_LASTCOLUMN);
	}
	
	/**
	 * @param matrixFigure
	 * @return
	 */
	private MatrixCellFigure getMatrixLastRow(MatrixFigure matrixFigure) {
		return matrixFigure.getCellFigure(matrixFigure.getChildren(), IMatrixCell.CELLTYPE_LASTROW);
	}
	
	/**
	 * @param matrixFigure
	 * @return
	 */
	private MatrixCellFigure getMatrixLastCell(MatrixFigure matrixFigure) {
		return matrixFigure.getCellFigure(matrixFigure.getChildren(), IMatrixCell.CELLTYPE_LASTCELL);
	}	
	
	
	/**
	 * @param container
	 * @param move
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param flushCache
	 * @return
	 */
	protected Dimension layout(IFigure container, boolean move, int x, int y, int width, int height) {
		
		MatrixFigure matrixFigure = (MatrixFigure) container;
		IMatrix matrix = matrixFigure.getMatrix();
		boolean thirdRow = true;
		boolean fourthRow = true;
		
		int numRows = 3;
		int numColumns = 4;
		
		if(!matrix.displayLastColumn() && !matrix.displayLastCell()) {
			numColumns = 3;
			fourthRow = false;
		}
		if (!matrix.displayLastRow() && !matrix.displayLastCell()) {
			numRows = 2;
			thirdRow = false;
		}
		
		List<?> children = container.getChildren();
		
		/* Build the grid */
		IFigure[][] grid = new IFigure[numRows][numColumns];
		for (int i = 0; i < children.size(); i++) {
			IFigure child = (IFigure) children.get(i);
			if (child instanceof MatrixAxisFigure) {
				MatrixAxisFigure axisFigure = (MatrixAxisFigure) child;
				IMatrixAxis mAxis = axisFigure.getMatrixAxis();
				if (mAxis.isXAxis()) {
					grid[0][1] = child;
				} else if (mAxis.isYAxis()) {
					grid[1][0] = child;
				}
			} else if (child instanceof MatrixContentCellFigure) {
				grid[1][1] = child;
			} else if (child instanceof MatrixCellFigure) {
				MatrixCellFigure cellFigure = (MatrixCellFigure) child;
				IMatrixCell cell = cellFigure.getMatrixCell();
				if (cell.isLastColumn() && matrix.displayLastColumn()) {
					grid[1][2] = child;
				} else if (cell.isLastRow() && matrix.displayLastRow()) {
					grid[2][1] = child;
				} else if (cell.isLastCell() && matrix.displayLastCell()) {
					grid[2][2] = child;
				}
			} else if (child instanceof MatrixExtraColumnFigure) {
				if (fourthRow) {
					grid[1][3] = child;
				} else {
					grid[1][2] = child;
				}
			}
		}
		
		int horizontalSpacing = 1;
		int verticalSpacing = 1;
		int marginHeight= 3;
		int marginWidth = 9;
		int columnCount = numColumns;

		/* Column widths */
		int availableWidth = width - horizontalSpacing * (columnCount - 1) - marginWidth * 2;
		
		int widthHint = 30;
		if (!fourthRow) {
			widthHint = 50;
		}
		
		int xAxisWidth = (availableWidth) * widthHint / 100;
		int yAxisWidth = (availableWidth) * 20 / 100;
		int aggregationCellWidth = (availableWidth) * 20 / 100;	
		int extraColumnWidth = (availableWidth) * 30 / 100;	
	
	
		// row heights
		int firstRowHeight = matrixFigure.getAxisFigure(children, true).getMinHeight();
		MatrixContentCellFigure contentCell = matrixFigure.getContentCellFigure(children);
		int secondRowHeight = contentCell.getMinHeight();
		if (matrix.displayLastColumn()) {
			int h = getMatrixLastColumn(matrixFigure).getMinHeight();
			secondRowHeight = Math.max(secondRowHeight, h);
		}
		
		int thirdRowHeight = 0;
		if (thirdRow) {
			if (matrix.displayLastRow()) {
				thirdRowHeight = getMatrixLastRow(matrixFigure).getMinHeight();
			}
			if (matrix.displayLastCell()) {
				int h = getMatrixLastCell(matrixFigure).getMinHeight();
				thirdRowHeight = Math.max(thirdRowHeight, h);
			}
		}

		
		/* Position the IFigures */		
		if (move) {
			int gridY = y + marginHeight;
			int gridX = x + marginWidth;
			// ********** first row
			MatrixAxisFigure xAxis = matrixFigure.getAxisFigure(children, true);
			int childX = gridX + yAxisWidth + horizontalSpacing;
			int childY = gridY;
			int childWidth = xAxisWidth+horizontalSpacing;
			int childHeight = firstRowHeight + verticalSpacing;
			xAxis.setBounds(new Rectangle(childX, childY, childWidth, childHeight));
			
			// ********** second row
			int rowY = gridY+childHeight+verticalSpacing;
			MatrixAxisFigure yAxis = matrixFigure.getAxisFigure(children, false);
			childX = gridX;
			childWidth = yAxisWidth+horizontalSpacing;
			childHeight = secondRowHeight+verticalSpacing;
			yAxis.setBounds(new Rectangle(childX, rowY, childWidth, childHeight));
			
			MatrixContentCellFigure content = matrixFigure.getContentCellFigure(children);
			childX = gridX + yAxisWidth+ horizontalSpacing;
			childWidth = xAxisWidth + horizontalSpacing;
			content.setBounds(new Rectangle(childX, rowY, childWidth, childHeight));
			
			if (matrix.displayLastColumn()) {
				MatrixCellFigure lastCol = getMatrixLastColumn(matrixFigure);
				childX = gridX + yAxisWidth + xAxisWidth + horizontalSpacing;
				childWidth = aggregationCellWidth +horizontalSpacing;
				lastCol.setBounds(new Rectangle(childX, rowY, childWidth, childHeight));				
			}
			
			// extra column
			MatrixExtraColumnFigure extra = matrixFigure.getExtraColumnFigure(children);
			childX = gridX + yAxisWidth + xAxisWidth +horizontalSpacing;
			if (fourthRow) {
				childX += aggregationCellWidth +horizontalSpacing;
			}
			childWidth = extraColumnWidth +horizontalSpacing;
			childHeight = secondRowHeight + thirdRowHeight + (2* verticalSpacing);
			extra.setBounds(new Rectangle(childX, rowY, childWidth, childHeight));	
			
			// *********** third row
			if (thirdRow) {
				rowY += secondRowHeight+verticalSpacing;
				childHeight = thirdRowHeight+verticalSpacing;
				
				// lastrow
				if (matrix.displayLastRow()) {
					MatrixCellFigure lastRow = getMatrixLastRow(matrixFigure);
					childX = gridX + yAxisWidth+horizontalSpacing;
					childWidth = xAxisWidth+horizontalSpacing;
					lastRow.setBounds(new Rectangle(childX, rowY, childWidth, childHeight));
				}
				
				// lastcell
				if (matrix.displayLastCell()) {
					MatrixCellFigure lastCell = getMatrixLastCell(matrixFigure);
					childX = gridX + yAxisWidth + xAxisWidth;
					childWidth = aggregationCellWidth +horizontalSpacing;
					lastCell.setBounds(new Rectangle(childX, rowY, childWidth, childHeight));
				}
				
			}
		}
		int parentHeight = (firstRowHeight+secondRowHeight+thirdRowHeight)+ verticalSpacing * (numRows - 1) + marginHeight* 2;
		return new Dimension(width, parentHeight);

	}

}

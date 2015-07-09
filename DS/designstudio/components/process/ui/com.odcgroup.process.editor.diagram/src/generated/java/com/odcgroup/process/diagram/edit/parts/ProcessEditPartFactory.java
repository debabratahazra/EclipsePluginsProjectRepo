/*
 * Odyssey Financial Technologies
 */
package com.odcgroup.process.diagram.edit.parts;

import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITextAwareEditPart;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;

import com.odcgroup.process.diagram.custom.figures.WrapLabel;
import com.odcgroup.process.diagram.part.ProcessVisualIDRegistry;

/**
 * @generated
 */
public class ProcessEditPartFactory implements EditPartFactory {

	/**
	 * @generated
	 */
	public EditPart createEditPart(EditPart context, Object model) {
		if (model instanceof View) {
			View view = (View) model;
			switch (ProcessVisualIDRegistry.getVisualID(view)) {

			case ProcessEditPart.VISUAL_ID:
				return new ProcessEditPart(view);

			case PoolEditPart.VISUAL_ID:
				return new PoolEditPart(view);

			case PoolNameEditPart.VISUAL_ID:
				return new PoolNameEditPart(view);

			case UserTaskEditPart.VISUAL_ID:
				return new UserTaskEditPart(view);

			case UserTaskNameEditPart.VISUAL_ID:
				return new UserTaskNameEditPart(view);

			case ServiceTaskEditPart.VISUAL_ID:
				return new ServiceTaskEditPart(view);

			case ServiceTaskNameEditPart.VISUAL_ID:
				return new ServiceTaskNameEditPart(view);

			case ComplexGatewayEditPart.VISUAL_ID:
				return new ComplexGatewayEditPart(view);

			case ComplexGatewayNameEditPart.VISUAL_ID:
				return new ComplexGatewayNameEditPart(view);

			case ExclusiveMergeEditPart.VISUAL_ID:
				return new ExclusiveMergeEditPart(view);

			case ExclusiveMergeNameEditPart.VISUAL_ID:
				return new ExclusiveMergeNameEditPart(view);

			case ParallelForkEditPart.VISUAL_ID:
				return new ParallelForkEditPart(view);

			case ParallelForkNameEditPart.VISUAL_ID:
				return new ParallelForkNameEditPart(view);

			case ParallelMergeEditPart.VISUAL_ID:
				return new ParallelMergeEditPart(view);

			case ParallelMergeNameEditPart.VISUAL_ID:
				return new ParallelMergeNameEditPart(view);

			case StartEventEditPart.VISUAL_ID:
				return new StartEventEditPart(view);

			case StartEventNameEditPart.VISUAL_ID:
				return new StartEventNameEditPart(view);

			case EndEventEditPart.VISUAL_ID:
				return new EndEventEditPart(view);

			case EndEventNameEditPart.VISUAL_ID:
				return new EndEventNameEditPart(view);

			case PoolProcessItemCompartmentEditPart.VISUAL_ID:
				return new PoolProcessItemCompartmentEditPart(view);

			case FlowEditPart.VISUAL_ID:
				return new FlowEditPart(view);

			case FlowNameEditPart.VISUAL_ID:
				return new FlowNameEditPart(view);
			}
		}
		return createUnrecognizedEditPart(context, model);
	}

	/**
	 * @generated
	 */
	private EditPart createUnrecognizedEditPart(EditPart context, Object model) {
		// Handle creation of unrecognized child node EditParts here
		return null;
	}

	/**
	 * @generated
	 */
	public static CellEditorLocator getTextCellEditorLocator(ITextAwareEditPart source) {
		if (source.getFigure() instanceof WrappingLabel)
			return new TextCellEditorLocator((WrappingLabel) source.getFigure());
		else {
			return new LabelCellEditorLocator((WrapLabel) source.getFigure());
		}
	}

	/**
	 * @generated
	 */
	static private class TextCellEditorLocator implements CellEditorLocator {

		/**
		 * @generated
		 */
		private WrappingLabel wrapLabel;

		/**
		 * @generated
		 */
		public TextCellEditorLocator(WrappingLabel wrapLabel) {
			this.wrapLabel = wrapLabel;
		}

		/**
		 * @generated
		 */
		public WrappingLabel getWrapLabel() {
			return wrapLabel;
		}

		/**
		 * @generated
		 */
		public void relocate(CellEditor celleditor) {
			Text text = (Text) celleditor.getControl();
			Rectangle rect = getWrapLabel().getTextBounds().getCopy();
			getWrapLabel().translateToAbsolute(rect);
			if (getWrapLabel().isTextWrapOn() && getWrapLabel().getText().length() > 0) {
				rect.setSize(new Dimension(text.computeSize(rect.width, SWT.DEFAULT)));
			} else {
				int avr = FigureUtilities.getFontMetrics(text.getFont()).getAverageCharWidth();
				rect.setSize(new Dimension(text.computeSize(SWT.DEFAULT, SWT.DEFAULT)).expand(avr * 2, 0));
			}
			if (!rect.equals(new Rectangle(text.getBounds()))) {
				text.setBounds(rect.x, rect.y, rect.width, rect.height);
			}
		}

	}

	/**
	 * @generated
	 */
	private static class LabelCellEditorLocator implements CellEditorLocator {

		/**
		 * @generated
		 */
		private WrapLabel label;

		/**
		 * @generated
		 */
		public LabelCellEditorLocator(WrapLabel label) {
			this.label = label;
		}

		/**
		 * @generated
		 */
		public WrapLabel getLabel() {
			return label;
		}

		/**
		 * @generated
		 */
		public void relocate(CellEditor celleditor) {
			Text text = (Text) celleditor.getControl();
			Rectangle rect = getLabel().getTextBounds().getCopy();
			getLabel().translateToAbsolute(rect);
			int avr = FigureUtilities.getFontMetrics(text.getFont()).getAverageCharWidth();
			rect.setSize(new Dimension(text.computeSize(SWT.DEFAULT, SWT.DEFAULT)).expand(avr * 2, 0));
			if (!rect.equals(new Rectangle(text.getBounds()))) {
				text.setBounds(rect.x, rect.y, rect.width, rect.height);
			}
		}
	}
}

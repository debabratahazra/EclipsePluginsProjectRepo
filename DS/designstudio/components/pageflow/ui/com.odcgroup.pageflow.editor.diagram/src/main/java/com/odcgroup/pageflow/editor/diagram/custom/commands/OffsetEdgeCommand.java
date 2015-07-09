package com.odcgroup.pageflow.editor.diagram.custom.commands;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.RelativeBendpoints;
import org.eclipse.gmf.runtime.notation.datatype.RelativeBendpoint;

/**
 * @author pkk
 *
 */
@SuppressWarnings("rawtypes")
public class OffsetEdgeCommand extends AbstractTransactionalCommand {
	
	private final List affectedEdges;

	/**
	 * @param domain
	 * @param affectedEdges
	 */
	public OffsetEdgeCommand(TransactionalEditingDomain domain,	List affectedEdges) {
		super(domain, "Offset Pageflow Transitions", affectedEdges);
		this.affectedEdges = affectedEdges;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand#
	 * doExecuteWithResult(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
	 */
	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor,
			IAdaptable info) throws ExecutionException {
		for (Object  obj : affectedEdges) {
			if (obj instanceof Edge) {
				final Edge edge = (Edge) obj;
				RelativeBendpoints bps = (RelativeBendpoints) edge.getBendpoints();
				List<RelativeBendpoint> nbs = new ArrayList<RelativeBendpoint>();
				List<?> points = bps.getPoints();
				if (!points.isEmpty()) {
					RelativeBendpoint first = (RelativeBendpoint) points.get(0);
					RelativeBendpoint last = (RelativeBendpoint) points.get(points.size()-1);
					int sx = RandomUtils.nextInt(20);
					int sy = RandomUtils.nextInt(20);
					int tx = RandomUtils.nextInt(20);
					int ty = RandomUtils.nextInt(20);
					RelativeBendpoint mid = new RelativeBendpoint(sx, sy, tx, ty);
					nbs.add(first);
					nbs.add(mid);
					nbs.add(last);
				}
				bps.setPoints(nbs);
			}
		}
		return CommandResult.newOKCommandResult();
	}
	
}

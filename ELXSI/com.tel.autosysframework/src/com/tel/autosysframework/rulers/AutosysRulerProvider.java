package com.tel.autosysframework.rulers;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.rulers.RulerChangeListener;
import org.eclipse.gef.rulers.RulerProvider;

import com.tel.autosysframework.commands.CreateGuideCommand;
import com.tel.autosysframework.commands.DeleteGuideCommand;
import com.tel.autosysframework.commands.MoveGuideCommand;
import com.tel.autosysframework.model.AutosysGuide;
import com.tel.autosysframework.model.AutosysRuler;

/**
 * @author Pratik Shah
 */
public class AutosysRulerProvider
extends RulerProvider
{

	private AutosysRuler ruler;
	private PropertyChangeListener rulerListener = new PropertyChangeListener() {
		public void propertyChange(PropertyChangeEvent evt) {
			if (evt.getPropertyName().equals(AutosysRuler.PROPERTY_CHILDREN)) {
				AutosysGuide guide = (AutosysGuide)evt.getNewValue();
				if (getGuides().contains(guide)) {
					guide.addPropertyChangeListener(guideListener);
				} else {
					guide.removePropertyChangeListener(guideListener);
				}
				for (int i = 0; i < listeners.size(); i++) {
					((RulerChangeListener)listeners.get(i))
					.notifyGuideReparented(guide);
				}
			} else {
				for (int i = 0; i < listeners.size(); i++) {
					((RulerChangeListener)listeners.get(i))
					.notifyUnitsChanged(ruler.getUnit());
				}
			}
		}
	};
	private PropertyChangeListener guideListener = new PropertyChangeListener() {
		public void propertyChange(PropertyChangeEvent evt) {
			if (evt.getPropertyName().equals(AutosysGuide.PROPERTY_CHILDREN)) {
				for (int i = 0; i < listeners.size(); i++) {
					((RulerChangeListener)listeners.get(i))
					.notifyPartAttachmentChanged(evt.getNewValue(), evt.getSource());
				}
			} else {
				for (int i = 0; i < listeners.size(); i++) {
					((RulerChangeListener)listeners.get(i))
					.notifyGuideMoved(evt.getSource());
				}
			}
		}
	};

	public AutosysRulerProvider(AutosysRuler ruler) {
		this.ruler = ruler;
		this.ruler.addPropertyChangeListener(rulerListener);
		List guides = getGuides();
		for (int i = 0; i < guides.size(); i++) {
			((AutosysGuide)guides.get(i)).addPropertyChangeListener(guideListener);
		}
	}

	public List getAttachedModelObjects(Object guide) {
		return new ArrayList(((AutosysGuide)guide).getParts());
	}

	public Command getCreateGuideCommand(int position) {
		return new CreateGuideCommand(ruler, position);
	}

	public Command getDeleteGuideCommand(Object guide) {
		return new DeleteGuideCommand((AutosysGuide)guide, ruler);
	}

	public Command getMoveGuideCommand(Object guide, int pDelta) {
		return new MoveGuideCommand((AutosysGuide)guide, pDelta);
	}

	public int[] getGuidePositions() {
		List guides = getGuides();
		int[] result = new int[guides.size()];
		for (int i = 0; i < guides.size(); i++) {
			result[i] = ((AutosysGuide)guides.get(i)).getPosition();
		}
		return result;
	}

	public Object getRuler() {
		return ruler;
	}

	public int getUnit() {
		return ruler.getUnit();
	}

	public void setUnit(int newUnit) {
		ruler.setUnit(newUnit);
	}

	public int getGuidePosition(Object guide) {
		return ((AutosysGuide)guide).getPosition();
	}

	public List getGuides() {
		return ruler.getGuides();
	}

}

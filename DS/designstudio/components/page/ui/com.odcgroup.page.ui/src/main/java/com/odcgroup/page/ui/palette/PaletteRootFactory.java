package com.odcgroup.page.ui.palette;

import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.Assert;
import org.eclipse.gef.palette.CombinedTemplateCreationEntry;
import org.eclipse.gef.palette.MarqueeToolEntry;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteEntry;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.PaletteToolbar;
import org.eclipse.gef.palette.SelectionToolEntry;
import org.eclipse.jface.resource.ImageDescriptor;

import com.odcgroup.page.metamodel.WidgetTemplate;
import com.odcgroup.page.ui.PageUIConstants;
import com.odcgroup.page.ui.PageUIPlugin;
import com.odcgroup.page.ui.WidgetCreationFactory;
import com.odcgroup.page.uimodel.Palette;
import com.odcgroup.page.uimodel.PaletteGroup;
import com.odcgroup.page.uimodel.PaletteGroupItem;

/**
 * Creates the Palette using the definition passed in the constructor.
 * 
 * @author Gary Hayes
 */
public class PaletteRootFactory {

	/** The Palette definition used to create the PaletteRoot. */
	private Palette palette;

	/**
	 * Creates a new PaletteRootFactory.
	 * 
	 * @param palette
	 *            The Palette definition used to create the Palette
	 */
	public PaletteRootFactory(Palette palette) {
		Assert.isNotNull(palette);
		this.palette = palette;
	}

	/**
	 * Creates the Palette.
	 * 
	 * @return PaletteRoot The PaletteRoot
	 */
	public PaletteRoot create() {
		PaletteRoot paletteRoot = new PaletteRoot();

		PaletteToolbar ptb = new PaletteToolbar(null); 
		SelectionToolEntry selectionToolEntry = new SelectionToolEntry(); 
		ptb.add(selectionToolEntry); 
		ptb.add(new MarqueeToolEntry());
		paletteRoot.add(ptb); 
		
		for (Iterator it = palette.getGroups().iterator(); it.hasNext();) {
			PaletteGroup pg = (PaletteGroup) it.next();
			paletteRoot.add(createPaletteContainer(pg));
		}
		
		paletteRoot.setDefaultEntry(selectionToolEntry);
		
		return paletteRoot;
	}

	/**
	 * Creates a PaletteContainer using the supplied definition.
	 * 
	 * @param paletteGroup
	 *            The definition of the group
	 * @return PaletteContainer The newly created group
	 */
	private PaletteContainer createPaletteContainer(PaletteGroup paletteGroup) {
		PaletteDrawer drawer = new PaletteDrawer(paletteGroup.getLabel());
		if (paletteGroup.isExpanded()) {
			drawer.setInitialState(PaletteDrawer.INITIAL_STATE_OPEN);
		} else {
			drawer.setInitialState(PaletteDrawer.INITIAL_STATE_CLOSED);
		}
		for (Iterator it = paletteGroup.getItems().iterator(); it.hasNext();) {
			PaletteGroupItem pgi = (PaletteGroupItem) it.next();
			drawer.add(createPaletteEntry(pgi));
		}

		return drawer;
	}

	/**
	 * Creates a new PaletteEntry using the supplied definition.
	 * 
	 * @param paletteGroupItem
	 *            The definition of the item
	 * @return PaletteEntry The newly created entry
	 */
	private PaletteEntry createPaletteEntry(PaletteGroupItem paletteGroupItem) {
		WidgetTemplate wt = paletteGroupItem.getWidgetTemplate();

		CombinedTemplateCreationEntry entry = new CombinedTemplateCreationEntry(paletteGroupItem.getLabel(),
				paletteGroupItem.getTooltip(), new WidgetCreationFactory(wt), getImageDescriptor(paletteGroupItem
						.getSmallImage()), getImageDescriptor(paletteGroupItem.getLargeImage()));

		return entry;
	}

	/**
	 * Gets the image descriptor. If the image name passed in as a parameter is
	 * null or empty returns the default icon's ImageDescriptor.
	 * 
	 * @param imageName
	 *            The name of the image
	 * @return ImageDescriptor The ImageDescriptor
	 */
	private static ImageDescriptor getImageDescriptor(String imageName) {
		if (StringUtils.isEmpty(imageName)) {
			imageName = PageUIConstants.DEFAULT_ICON;
		}
		return PageUIPlugin.getImageDescriptor(imageName);
	}
}

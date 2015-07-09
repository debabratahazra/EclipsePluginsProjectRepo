package com.odcgroup.page.uimodel.util.tests;

import org.eclipse.emf.common.util.EList;
import org.junit.Assert;
import org.junit.Test;

import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.uimodel.Palette;
import com.odcgroup.page.uimodel.PaletteGroup;
import com.odcgroup.page.uimodel.PaletteGroupItem;
import com.odcgroup.page.uimodel.UIModel;
import com.odcgroup.page.uimodel.util.UIModelRegistry;

/**
	 * Tests the UIModelRegistry.
	 * 
	 * @author Gary Hayes
	 */
	
public class UIModelRegistryTest {

		private final String PALETTE_GROUP_SPECIAL = "Special";
		private final String PALETTE_NONE = "none";

		/**
		 * Makes sure that there are no errors in the UIModel.
		 */
		@Test
	public void testUIModel() {
			UIModel uim = UIModelRegistry.getUIModel();
			Assert.assertTrue(uim.eResource().getErrors().size() == 0);
		}

		/**
		 * Make sure that there is IncludeXSP widget available in Regular module palette under
		 * special category.
		 */
		@Test
		public void testRegularUIModelForIncludeXSP(){
			UIModel uim = UIModelRegistry.getUIModel();
			
			EList<Palette> paletteList = uim.getPalette();
			boolean foundIncludeXSP = false;

			for(Palette palette : paletteList){
				if(PALETTE_NONE.equals(palette.getPropertyValue())){
					for(PaletteGroup group:palette.getGroups()){
							if(group.getLabel().equals(PALETTE_GROUP_SPECIAL)){
								for(PaletteGroupItem item:group.getItems()){
									WidgetType type=item.getWidgetTemplate().getType();
									if(type.getName().equals(WidgetTypeConstants.INCLUDE_XSP)){
										foundIncludeXSP = true;
										break;
									}
								 }
							}
						}
				}
			}
			Assert.assertTrue("IncludeXSP widget not found", foundIncludeXSP);
		}
	}

package com.ose.windowtester.swt.runtime;

import org.eclipse.swt.widgets.Slider;
import com.windowtester.runtime.IUIContext;
import com.windowtester.runtime.locator.IWidgetLocator;
import com.windowtester.runtime.swt.locator.SWTWidgetLocator;

public class SliderLocator extends SWTWidgetLocator
{

   private static final long serialVersionUID = 1L;

   public SliderLocator()
   {
      super(Slider.class);
   }

   @Override
   public IWidgetLocator[] findAll(IUIContext ui)
   {
      IWidgetLocator[] refs = super.findAll(ui);
      SliderReference[] sliders = new SliderReference[refs.length];
      for (int i = 0; i < sliders.length; i++)
      {
         sliders[i] = new SliderReference((Slider) refs[i]);
      }
      return sliders;
   }

   public IWidgetLocator find(IUIContext ui)
   {
      IWidgetLocator[] refs = super.findAll(ui);
      SliderReference sliders = null;
      for (int i = 0; i < refs.length; i++)
      {
         sliders = new SliderReference((Slider) refs[i]);
      }
      return sliders;
   }

}

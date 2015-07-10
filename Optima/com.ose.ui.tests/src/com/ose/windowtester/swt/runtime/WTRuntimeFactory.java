package com.ose.windowtester.swt.runtime;

import org.eclipse.swt.widgets.Slider;
import com.windowtester.runtime.locator.IWidgetReference;

public class WTRuntimeFactory implements
      com.windowtester.runtime.internal.factory.WTRuntimeFactory
{

   public IWidgetReference createReference(Object widget)
   {
      if (widget instanceof Slider)
      {
         return new SliderReference((Slider) widget);
      }
      return null;
   }

}

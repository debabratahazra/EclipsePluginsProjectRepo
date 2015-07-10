package com.ose.windowtester.swt.runtime;

import org.eclipse.swt.widgets.Slider;
import com.windowtester.runtime.IUIContext;
import com.windowtester.runtime.locator.IWidgetLocator;
import com.windowtester.runtime.swt.internal.widgets.ControlReference;

@SuppressWarnings("restriction")
public class SliderReference extends ControlReference<Slider>
{

   private final Slider sliderWidget;

   public SliderReference(Slider sliderWidget)
   {
      super(sliderWidget);
      this.sliderWidget = (Slider) sliderWidget;
   }

   public IWidgetLocator[] findAll(IUIContext ui)
   {
      return new SliderLocator().findAll(ui);
   }

   public boolean matches(Object widget)
   {
      return true;
   }

   public Slider getWidget()
   {
      return sliderWidget;
   }
}

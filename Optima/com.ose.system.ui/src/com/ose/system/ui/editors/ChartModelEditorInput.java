/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2006-2007 by Enea Software AB.
 * All rights reserved.
 *
 * This Software is furnished under a software license agreement and
 * may be used only in accordance with the terms of such agreement.
 * Any other use or reproduction is prohibited. No title to and
 * ownership of the Software is hereby transferred.
 *
 * PROPRIETARY NOTICE
 * This Software consists of confidential information.
 * Trade secret law and copyright law protect this Software.
 * The above notice of copyright on this Software does not indicate
 * any actual or intended publication of such Software.
 **************************************************************************
 * COPYRIGHT-END */

package com.ose.system.ui.editors;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;
import com.ose.system.ui.charts.ChartModel;
import com.ose.system.ui.charts.ChartModelInput;
import com.ose.system.ui.util.IPropertyChangeSource;

public class ChartModelEditorInput implements IEditorInput
{
   private IPropertyChangeSource propertyChangeSource;
   private String propertyName;
   private String title;
   private ChartModel chartModel;
   private Object filter;

   public ChartModelEditorInput(IPropertyChangeSource propertyChangeSource,
                                String propertyName,
                                String title,
                                Class chartModelClass,
                                ChartModelInput chartModelInput)
   {
      if ((propertyChangeSource == null) ||
          (propertyName == null) ||
          (title == null) ||
          (chartModelClass == null) ||
          (chartModelInput == null))
      {
         throw new NullPointerException();
      }

      this.propertyChangeSource = propertyChangeSource;
      this.propertyName = propertyName;
      this.title = title;
      this.filter = chartModelInput.getFilter();

      try
      {
         chartModel = (ChartModel) chartModelClass.newInstance();
      }
      catch (Exception e)
      {
         throw new IllegalArgumentException();
      }
      chartModel.update(chartModelInput.getInput());
   }

   public boolean exists()
   {
      return true;
   }

   public ImageDescriptor getImageDescriptor()
   {
      return null;
   }

   public String getName()
   {
      return chartModel.getTitle();
   }

   public IPersistableElement getPersistable()
   {
      return null;
   }

   public String getToolTipText()
   {
      return getName();
   }

   public Object getAdapter(Class adapter)
   {
      return null;
   }

   public IPropertyChangeSource getPropertyChangeSource()
   {
      return propertyChangeSource;
   }

   public String getPropertyName()
   {
      return propertyName;
   }

   public String getTitle()
   {
      return title;
   }

   public ChartModel getChartModel()
   {
      return chartModel;
   }

   public Object getFilter()
   {
      return filter;
   }

   public boolean equals(Object obj)
   {
      boolean result = false;
      if (obj instanceof ChartModelEditorInput)
      {
         ChartModelEditorInput other = (ChartModelEditorInput) obj;
         result = getChartModel().getClass().getName().equals(
               other.getChartModel().getClass().getName());
      }
      return result;
   }
}

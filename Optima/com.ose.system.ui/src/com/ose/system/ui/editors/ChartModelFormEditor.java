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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;
import com.ose.system.ui.charts.ChartModel;
import com.ose.system.ui.charts.ChartModelInput;
import com.ose.system.ui.forms.ChartForm;
import com.ose.system.ui.util.IPropertyChangeSource;

public class ChartModelFormEditor extends EditorPart
   implements PropertyChangeListener
{
   private IPropertyChangeSource propertyChangeSource;
   private String propertyName;
   private ChartModel chartModel;
   private ChartForm form;

   public void init(IEditorSite site, IEditorInput input)
      throws PartInitException
   {
      ChartModelEditorInput chartModelEditorInput;

      if (!(input instanceof ChartModelEditorInput))
      {
         throw new PartInitException("Invalid editor input");
      }

      chartModelEditorInput = (ChartModelEditorInput) input;
      propertyChangeSource = chartModelEditorInput.getPropertyChangeSource();
      propertyName = chartModelEditorInput.getPropertyName();
      chartModel = chartModelEditorInput.getChartModel();

      setSite(site);
      setInput(input);
      setPartName(input.getName());
   }

   public void createPartControl(Composite parent)
   {
      ChartModelEditorInput input = (ChartModelEditorInput) getEditorInput();

      form = new ChartForm(chartModel, input.getTitle(), input.getFilter());
      form.createContents(parent);
      propertyChangeSource.addPropertyChangeListener(propertyName, this);
      propertyChangeSource.addPropertyChangeListener(
            IPropertyChangeSource.PROPERTY_SOURCE_DISPOSED, this);
   }

   public void dispose()
   {
      propertyChangeSource.removePropertyChangeListener(propertyName, this);
      propertyChangeSource.removePropertyChangeListener(
            IPropertyChangeSource.PROPERTY_SOURCE_DISPOSED, this);
      form.dispose();
      super.dispose();
   }

   public void setFocus()
   {
      form.setFocus();
   }

   public boolean isDirty()
   {
      // Save not supported.
      return false;
   }

   public void doSave(IProgressMonitor monitor)
   {
      // Save not supported.
   }

   public void doSaveAs()
   {
      // Save not supported.
   }

   public boolean isSaveAsAllowed()
   {
      // Save not supported.
      return false;
   }

   public Object getAdapter(Class adapter)
   {
      return null;
   }

   public void propertyChange(PropertyChangeEvent event)
   {
      String property = event.getPropertyName();

      if (property.equals(propertyName))
      {
         form.getControl().getDisplay().asyncExec(
               new UpdateEditorRunner((ChartModelInput) event.getNewValue()));
      }
      else if (property.equals(IPropertyChangeSource.PROPERTY_SOURCE_DISPOSED))
      {
         form.getControl().getDisplay().asyncExec(new CloseEditorRunner());
      }
   }

   class UpdateEditorRunner implements Runnable
   {
      private ChartModelInput chartModelInput;

      UpdateEditorRunner(ChartModelInput chartModelInput)
      {
         this.chartModelInput = chartModelInput;
      }

      public void run()
      {
         chartModel.update(chartModelInput.getInput());
         form.setFilter(chartModelInput.getFilter());
         form.refresh();
      }
   }

   class CloseEditorRunner implements Runnable
   {
      public void run()
      {
         IWorkbenchPage page = PlatformUI.getWorkbench()
               .getActiveWorkbenchWindow().getActivePage();
         if (page != null)
         {
            page.closeEditor(ChartModelFormEditor.this, false);
         }
      }
   }
}

/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2010 by Enea Software AB.
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

package com.ose.optima;

import java.util.Properties;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.intro.IIntroSite;
import org.eclipse.ui.intro.config.IIntroAction;

public class OpenPerspectiveAction extends Action implements IIntroAction
{
   private static final String INTRO_VIEW_ID = "org.eclipse.ui.internal.introview";

   public void run(IIntroSite site, Properties params)
   {
      String id = params.getProperty("id");

      if (id != null)
      {
         try
         {
            IWorkbenchWindow workbenchWindow = site.getWorkbenchWindow();
            workbenchWindow.getWorkbench().showPerspective(id, workbenchWindow);
            // Hide the Welcome screen if it is shown in the new perspective.
            IViewPart view = site.getPage().findView(INTRO_VIEW_ID);
            if (view != null)
            {
               site.getPage().hideView(view);
            }
         }
         catch (WorkbenchException e)
         {
            e.printStackTrace();
         }
      }
   }
}

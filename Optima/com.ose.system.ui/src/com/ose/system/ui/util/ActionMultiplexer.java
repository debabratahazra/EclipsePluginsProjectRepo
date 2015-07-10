/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2008 by Enea Software AB.
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

package com.ose.system.ui.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;

/**
 * This class is an action multiplexer that multiplexes a set of actions of
 * the same type. At most one action in the set should be enabled at any time.
 */
public class ActionMultiplexer extends Action
{
   private final List actions;

   private final EnablementHandler enablementHandler;

   public ActionMultiplexer()
   {
      actions = new ArrayList();
      enablementHandler = new EnablementHandler();
      setEnabled(false);
   }

   public void addAction(IAction action)
   {
      if (action == null)
      {
         throw new IllegalArgumentException();
      }
      setEnabled(isEnabled() || action.isEnabled());
      actions.add(action);
      action.addPropertyChangeListener(enablementHandler);
   }

   public void run()
   {
      for (Iterator i = actions.iterator(); i.hasNext();)
      {
         IAction action = (IAction) i.next();
         if (action.isEnabled())
         {
            action.run();
            break;
         }
      }
   }

   private class EnablementHandler implements IPropertyChangeListener
   {
      public void propertyChange(PropertyChangeEvent event)
      {
         if (event.getProperty() == IAction.ENABLED)
         {
            for (Iterator i = actions.iterator(); i.hasNext();)
            {
               IAction action = (IAction) i.next();
               if (action.isEnabled())
               {
                  setEnabled(true);
                  return;
               }
            }
            setEnabled(false);
         }
      }
   }
}

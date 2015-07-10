/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2005-2007 by Enea Software AB.
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

package com.ose.system.ui;

import java.net.MalformedURLException;
import java.net.URL;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;

public class SharedImages
{
   private static ImageRegistry imageRegistry = new ImageRegistry();

   private static URL iconBaseURL =
      Platform.getBundle(SystemBrowserPlugin.getUniqueIdentifier()).getEntry("/icons/");

   private static final String NAME_PREFIX = SystemBrowserPlugin.PLUGIN_ID + '.';

   private static final int NAME_PREFIX_LENGTH = NAME_PREFIX.length();

   public static final String IMG_OBJ_BLOCK =
      NAME_PREFIX + "block.gif";

   public static final String IMG_OBJ_BLOCK_KILLED =
      NAME_PREFIX + "block_killed.gif";

   public static final String IMG_OBJ_EXCLAMATION =
      NAME_PREFIX + "exclamation.gif";

   public static final String IMG_OBJ_GATE =
      NAME_PREFIX + "gate.gif";

   public static final String IMG_OBJ_GATE_KILLED =
      NAME_PREFIX + "gate_killed.gif";

   public static final String IMG_OBJ_HEAP =
      NAME_PREFIX + "heap.gif";

   public static final String IMG_OBJ_HEAP_KILLED =
      NAME_PREFIX + "heap_killed.gif";

   public static final String IMG_OBJ_HEAP_CHART =
      NAME_PREFIX + "heap_chart.gif";

   public static final String IMG_OBJ_HEAP_LIST =
      NAME_PREFIX + "heap_list.gif";

   public static final String IMG_OBJ_LOAD_MODULE =
      NAME_PREFIX + "load_module.gif";

   public static final String IMG_OBJ_POOL =
      NAME_PREFIX + "pool.gif";

   public static final String IMG_OBJ_POOL_KILLED =
      NAME_PREFIX + "pool_killed.gif";

   public static final String IMG_OBJ_PROCESS_BACKGROUND_DELAY =
      NAME_PREFIX + "process_background_delay.gif";

   public static final String IMG_OBJ_PROCESS_BACKGROUND_KILLED =
      NAME_PREFIX + "process_background_killed.gif";

   public static final String IMG_OBJ_PROCESS_BACKGROUND_READY =
      NAME_PREFIX + "process_background_ready.gif";

   public static final String IMG_OBJ_PROCESS_BACKGROUND_RECEIVE =
      NAME_PREFIX + "process_background_receive.gif";

   public static final String IMG_OBJ_PROCESS_BACKGROUND_REMOTE =
      NAME_PREFIX + "process_background_remote.gif";

   public static final String IMG_OBJ_PROCESS_BACKGROUND_SEMAPHORE =
      NAME_PREFIX + "process_background_semaphore.gif";

   public static final String IMG_OBJ_PROCESS_BACKGROUND_STOPPED =
      NAME_PREFIX + "process_background_stopped.gif";

   public static final String IMG_OBJ_PROCESS_INTERRUPT_KILLED =
      NAME_PREFIX + "process_interrupt_killed.gif";

   public static final String IMG_OBJ_PROCESS_INTERRUPT_READY =
      NAME_PREFIX + "process_interrupt_ready.gif";

   public static final String IMG_OBJ_PROCESS_PHANTOM_KILLED =
      NAME_PREFIX + "process_phantom_killed.gif";

   public static final String IMG_OBJ_PROCESS_PHANTOM_READY =
      NAME_PREFIX + "process_phantom_ready.gif";

   public static final String IMG_OBJ_PROCESS_PRIORITIZED_DELAY =
      NAME_PREFIX + "process_prioritized_delay.gif";

   public static final String IMG_OBJ_PROCESS_PRIORITIZED_KILLED =
      NAME_PREFIX + "process_prioritized_killed.gif";

   public static final String IMG_OBJ_PROCESS_PRIORITIZED_READY =
      NAME_PREFIX + "process_prioritized_ready.gif";

   public static final String IMG_OBJ_PROCESS_PRIORITIZED_RECEIVE =
      NAME_PREFIX + "process_prioritized_receive.gif";

   public static final String IMG_OBJ_PROCESS_PRIORITIZED_REMOTE =
      NAME_PREFIX + "process_prioritized_remote.gif";

   public static final String IMG_OBJ_PROCESS_PRIORITIZED_SEMAPHORE =
      NAME_PREFIX + "process_prioritized_semaphore.gif";

   public static final String IMG_OBJ_PROCESS_PRIORITIZED_STOPPED =
      NAME_PREFIX + "process_prioritized_stopped.gif";

   public static final String IMG_OBJ_PROCESS_SIGNALPORT =
      NAME_PREFIX + "process_signalport.gif";

   public static final String IMG_OBJ_PROCESS_SIGNALPORT_KILLED =
      NAME_PREFIX + "process_signalport_killed.gif";

   public static final String IMG_OBJ_PROCESS_TIMER_KILLED =
      NAME_PREFIX + "process_timer_killed.gif";

   public static final String IMG_OBJ_PROCESS_TIMER_READY =
      NAME_PREFIX + "process_timer_ready.gif";

   public static final String IMG_OBJ_PROCESS_TIMER_STOPPED =
      NAME_PREFIX + "process_timer_stopped.gif";

   public static final String IMG_OBJ_PROCESS_UNKNOWN_KILLED =
      NAME_PREFIX + "process_unknown_killed.gif";

   public static final String IMG_OBJ_PROCESS_UNKNOWN_UNKNOWN =
      NAME_PREFIX + "process_unknown_unknown.gif";

   public static final String IMG_OBJ_SEGMENT =
      NAME_PREFIX + "segment.gif";

   public static final String IMG_OBJ_SEGMENT_KILLED =
      NAME_PREFIX + "segment_killed.gif";

   public static final String IMG_OBJ_SIGNAL_CHART =
      NAME_PREFIX + "signal_chart.gif";

   public static final String IMG_OBJ_SIGNAL_LIST =
      NAME_PREFIX + "signal_list.gif";

   public static final String IMG_OBJ_STACK_CHART =
      NAME_PREFIX + "stack_chart.gif";

   public static final String IMG_OBJ_STACK_LIST =
      NAME_PREFIX + "stack_list.gif";

   public static final String IMG_OBJ_TARGET =
      NAME_PREFIX + "target.gif";

   public static final String IMG_OBJ_TARGET_KILLED =
      NAME_PREFIX + "target_killed.gif";

   public static final String IMG_TOOL_CLEAN_ALL =
      NAME_PREFIX + "clean_all.gif";

   public static final String IMG_TOOL_CONNECT =
      NAME_PREFIX + "connect.gif";

   public static final String IMG_TOOL_DISCONNECT =
      NAME_PREFIX + "disconnect.gif";

   public static final String IMG_TOOL_FILTER =
      NAME_PREFIX + "filter.gif";

   public static final String IMG_TOOL_GATE_FIND =
      NAME_PREFIX + "gate_find.gif";

   public static final String IMG_TOOL_GATE_ADD =
      NAME_PREFIX + "gate_add.gif";

   public static final String IMG_TOOL_LINK_EDITOR =
      NAME_PREFIX + "link_editor.gif";

   public static final String IMG_TOOL_LOAD_MODULE_INSTALL =
      NAME_PREFIX + "load_module_install.gif";

   public static final String IMG_TOOL_LOAD_MODULE_UNINSTALL =
      NAME_PREFIX + "load_module_uninstall.gif";

   public static final String IMG_TOOL_OPTIMIZE =
      NAME_PREFIX + "optimize.gif";

   public static final String IMG_TOOL_SAVE_AS =
      NAME_PREFIX + "save_as.gif";

   public static final String IMG_TOOL_TARGET_ADD =
      NAME_PREFIX + "target_add.gif";

   public static final String IMG_TOOL_UPDATE =
      NAME_PREFIX + "update.gif";

   public static final String IMG_TOOL_UPDATE_ALL =
      NAME_PREFIX + "update_all.gif";

   public static final String IMG_TOOL_UPDATE_AUTO =
      NAME_PREFIX + "update_auto.gif";

   public static final ImageDescriptor DESC_OBJ_BLOCK =
      createManaged("obj16/", IMG_OBJ_BLOCK);

   public static final ImageDescriptor DESC_OBJ_BLOCK_KILLED =
      createManaged("obj16/", IMG_OBJ_BLOCK_KILLED);

   public static final ImageDescriptor DESC_OBJ_EXCLAMATION =
      createManaged("obj16/", IMG_OBJ_EXCLAMATION);

   public static final ImageDescriptor DESC_OBJ_GATE =
      createManaged("obj16/", IMG_OBJ_GATE);

   public static final ImageDescriptor DESC_OBJ_GATE_KILLED =
      createManaged("obj16/", IMG_OBJ_GATE_KILLED);

   public static final ImageDescriptor DESC_OBJ_HEAP =
      createManaged("obj16/", IMG_OBJ_HEAP);

   public static final ImageDescriptor DESC_OBJ_HEAP_KILLED =
      createManaged("obj16/", IMG_OBJ_HEAP_KILLED);

   public static final ImageDescriptor DESC_OBJ_HEAP_CHART =
      createManaged("obj16/", IMG_OBJ_HEAP_CHART);

   public static final ImageDescriptor DESC_OBJ_HEAP_LIST =
      createManaged("obj16/", IMG_OBJ_HEAP_LIST);

   public static final ImageDescriptor DESC_OBJ_LOAD_MODULE =
      createManaged("obj16/", IMG_OBJ_LOAD_MODULE);

   public static final ImageDescriptor DESC_OBJ_POOL =
      createManaged("obj16/", IMG_OBJ_POOL);

   public static final ImageDescriptor DESC_OBJ_POOL_KILLED =
      createManaged("obj16/", IMG_OBJ_POOL_KILLED);

   public static final ImageDescriptor DESC_OBJ_PROCESS_BACKGROUND_DELAY =
      createManaged("obj16/", IMG_OBJ_PROCESS_BACKGROUND_DELAY);

   public static final ImageDescriptor DESC_OBJ_PROCESS_BACKGROUND_KILLED =
      createManaged("obj16/", IMG_OBJ_PROCESS_BACKGROUND_KILLED);

   public static final ImageDescriptor DESC_OBJ_PROCESS_BACKGROUND_READY =
      createManaged("obj16/", IMG_OBJ_PROCESS_BACKGROUND_READY);

   public static final ImageDescriptor DESC_OBJ_PROCESS_BACKGROUND_RECEIVE =
      createManaged("obj16/", IMG_OBJ_PROCESS_BACKGROUND_RECEIVE);

   public static final ImageDescriptor DESC_OBJ_PROCESS_BACKGROUND_REMOTE =
      createManaged("obj16/", IMG_OBJ_PROCESS_BACKGROUND_REMOTE);

   public static final ImageDescriptor DESC_OBJ_PROCESS_BACKGROUND_SEMAPHORE =
      createManaged("obj16/", IMG_OBJ_PROCESS_BACKGROUND_SEMAPHORE);

   public static final ImageDescriptor DESC_OBJ_PROCESS_BACKGROUND_STOPPED =
      createManaged("obj16/", IMG_OBJ_PROCESS_BACKGROUND_STOPPED);

   public static final ImageDescriptor DESC_OBJ_PROCESS_INTERRUPT_KILLED =
      createManaged("obj16/", IMG_OBJ_PROCESS_INTERRUPT_KILLED);

   public static final ImageDescriptor DESC_OBJ_PROCESS_INTERRUPT_READY =
      createManaged("obj16/", IMG_OBJ_PROCESS_INTERRUPT_READY);

   public static final ImageDescriptor DESC_OBJ_PROCESS_PHANTOM_KILLED =
      createManaged("obj16/", IMG_OBJ_PROCESS_PHANTOM_KILLED);

   public static final ImageDescriptor DESC_OBJ_PROCESS_PHANTOM_READY =
      createManaged("obj16/", IMG_OBJ_PROCESS_PHANTOM_READY);

   public static final ImageDescriptor DESC_OBJ_PROCESS_PRIORITIZED_DELAY =
      createManaged("obj16/", IMG_OBJ_PROCESS_PRIORITIZED_DELAY);

   public static final ImageDescriptor DESC_OBJ_PROCESS_PRIORITIZED_KILLED =
      createManaged("obj16/", IMG_OBJ_PROCESS_PRIORITIZED_KILLED);

   public static final ImageDescriptor DESC_OBJ_PROCESS_PRIORITIZED_READY =
      createManaged("obj16/", IMG_OBJ_PROCESS_PRIORITIZED_READY);

   public static final ImageDescriptor DESC_OBJ_PROCESS_PRIORITIZED_RECEIVE =
      createManaged("obj16/", IMG_OBJ_PROCESS_PRIORITIZED_RECEIVE);

   public static final ImageDescriptor DESC_OBJ_PROCESS_PRIORITIZED_REMOTE =
      createManaged("obj16/", IMG_OBJ_PROCESS_PRIORITIZED_REMOTE);

   public static final ImageDescriptor DESC_OBJ_PROCESS_PRIORITIZED_SEMAPHORE =
      createManaged("obj16/", IMG_OBJ_PROCESS_PRIORITIZED_SEMAPHORE);

   public static final ImageDescriptor DESC_OBJ_PROCESS_PRIORITIZED_STOPPED =
      createManaged("obj16/", IMG_OBJ_PROCESS_PRIORITIZED_STOPPED);

   public static final ImageDescriptor DESC_OBJ_PROCESS_SIGNALPORT =
      createManaged("obj16/", IMG_OBJ_PROCESS_SIGNALPORT);

   public static final ImageDescriptor DESC_OBJ_PROCESS_SIGNALPORT_KILLED =
      createManaged("obj16/", IMG_OBJ_PROCESS_SIGNALPORT_KILLED);

   public static final ImageDescriptor DESC_OBJ_PROCESS_TIMER_KILLED =
      createManaged("obj16/", IMG_OBJ_PROCESS_TIMER_KILLED);

   public static final ImageDescriptor DESC_OBJ_PROCESS_TIMER_READY =
      createManaged("obj16/", IMG_OBJ_PROCESS_TIMER_READY);

   public static final ImageDescriptor DESC_OBJ_PROCESS_TIMER_STOPPED =
      createManaged("obj16/", IMG_OBJ_PROCESS_TIMER_STOPPED);

   public static final ImageDescriptor DESC_OBJ_PROCESS_UNKNOWN_KILLED =
      createManaged("obj16/", IMG_OBJ_PROCESS_UNKNOWN_KILLED);

   public static final ImageDescriptor DESC_OBJ_PROCESS_UNKNOWN_UNKNOWN =
      createManaged("obj16/", IMG_OBJ_PROCESS_UNKNOWN_UNKNOWN);

   public static final ImageDescriptor DESC_OBJ_SEGMENT =
      createManaged("obj16/", IMG_OBJ_SEGMENT);

   public static final ImageDescriptor DESC_OBJ_SEGMENT_KILLED =
      createManaged("obj16/", IMG_OBJ_SEGMENT_KILLED);

   public static final ImageDescriptor DESC_OBJ_SIGNAL_CHART =
      createManaged("obj16/", IMG_OBJ_SIGNAL_CHART);

   public static final ImageDescriptor DESC_OBJ_SIGNAL_LIST =
      createManaged("obj16/", IMG_OBJ_SIGNAL_LIST);

   public static final ImageDescriptor DESC_OBJ_STACK_CHART =
      createManaged("obj16/", IMG_OBJ_STACK_CHART);

   public static final ImageDescriptor DESC_OBJ_STACK_LIST =
      createManaged("obj16/", IMG_OBJ_STACK_LIST);

   public static final ImageDescriptor DESC_OBJ_TARGET =
      createManaged("obj16/", IMG_OBJ_TARGET);

   public static final ImageDescriptor DESC_OBJ_TARGET_KILLED =
      createManaged("obj16/", IMG_OBJ_TARGET_KILLED);

   public static final ImageDescriptor DESC_TOOL_CLEAN_ALL =
      createManaged("elcl16/", IMG_TOOL_CLEAN_ALL);

   public static final ImageDescriptor DESC_TOOL_CONNECT =
      createManaged("elcl16/", IMG_TOOL_CONNECT);

   public static final ImageDescriptor DESC_TOOL_DISCONNECT =
      createManaged("elcl16/", IMG_TOOL_DISCONNECT);

   public static final ImageDescriptor DESC_TOOL_FILTER =
      createManaged("elcl16/", IMG_TOOL_FILTER);

   public static final ImageDescriptor DESC_TOOL_GATE_FIND =
      createManaged("elcl16/", IMG_TOOL_GATE_FIND);

   public static final ImageDescriptor DESC_TOOL_GATE_ADD =
      createManaged("elcl16/", IMG_TOOL_GATE_ADD);

   public static final ImageDescriptor DESC_TOOL_LINK_EDITOR =
      createManaged("elcl16/", IMG_TOOL_LINK_EDITOR);

   public static final ImageDescriptor DESC_TOOL_LOAD_MODULE_INSTALL =
      createManaged("elcl16/", IMG_TOOL_LOAD_MODULE_INSTALL);

   public static final ImageDescriptor DESC_TOOL_LOAD_MODULE_UNINSTALL =
      createManaged("elcl16/", IMG_TOOL_LOAD_MODULE_UNINSTALL);

   public static final ImageDescriptor DESC_TOOL_OPTIMIZE =
      createManaged("elcl16/", IMG_TOOL_OPTIMIZE);

   public static final ImageDescriptor DESC_TOOL_SAVE_AS =
      createManaged("elcl16/", IMG_TOOL_SAVE_AS);

   public static final ImageDescriptor DESC_TOOL_TARGET_ADD =
      createManaged("elcl16/", IMG_TOOL_TARGET_ADD);

   public static final ImageDescriptor DESC_TOOL_UPDATE =
      createManaged("elcl16/", IMG_TOOL_UPDATE);

   public static final ImageDescriptor DESC_TOOL_UPDATE_ALL =
      createManaged("elcl16/", IMG_TOOL_UPDATE_ALL);

   public static final ImageDescriptor DESC_TOOL_UPDATE_AUTO =
      createManaged("elcl16/", IMG_TOOL_UPDATE_AUTO);

   public static Image get(String key)
   {
      return imageRegistry.get(key);
   }

   private static ImageDescriptor createManaged(String prefix, String name)
   {
      URL iconFileURL = null;
      ImageDescriptor descriptor;

      try
      {
         iconFileURL =
            new URL(iconBaseURL, prefix + name.substring(NAME_PREFIX_LENGTH));
      }
      catch (MalformedURLException e)
      {
         SystemBrowserPlugin.log(e);
      }
      descriptor = ImageDescriptor.createFromURL(iconFileURL);
      imageRegistry.put(name, descriptor);
      return descriptor;
   }
}

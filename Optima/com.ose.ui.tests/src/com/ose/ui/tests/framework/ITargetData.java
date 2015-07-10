/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2011 by Enea Software AB.
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

package com.ose.ui.tests.framework;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;
import com.ose.ui.tests.utils.EntityFilter;
import com.ose.ui.tests.utils.EntityType;

public interface ITargetData
{
   public void initialize() throws IOException;

   public void destroy() throws IOException;

   public InetAddress getGateAddress();

   public int getGatePort();
   
   public String getTestLoadModulePath();
   
   public String getTargetHuntPath();

   /**
    * Returns a list of String[] properties for a given entity type (Process,
    * Block, Heap, or Pool etc).
    * @param type
    * @return
    */
   public List<String[]> getEntities(EntityType type, int scopetype, int scope_id);

   /**
    * Returns a filtered list of String[] properties for a given entity type
    * (Process, Block, Heap, or Pool etc), filter, and column index. Passing a
    * null filter shall return all entities.
    *
    * @param type
    * @param filter
    * @param columnIndex
    * @return list of String[] properties.
    */
   public List<String[]> getFilteredEntities(EntityType type, int scopetype, int scope_id, String filter,
         int columnIndex);

   /**
    * Returns a filtered list of String[] properties for a given entity type
    * (Process, Block, Heap, or Pool etc) and filter. Passing a null filter
    * shall return all entities.
    *
    * @param type
    * @param filter
    * @return list of String[] properties.
    */
   public List<String[]> getFilteredEntities(EntityType type,int scopetype, int scope_id,
         EntityFilter[] filter);
}

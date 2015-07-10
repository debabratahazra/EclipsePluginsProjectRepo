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

package com.ose.ui.tests.utils;

import java.util.Comparator;

public class TestDataComparator implements Comparator
{
   public int compare(Object o1, Object o2){

    //parameter are of type Object, so we have to downcast it to Employee objects

    int value1 = ( (GenericTestData) o1).getValue1();

    int value2 = ( (GenericTestData) o2).getValue1();

    if( value1 > value2 )

    return 1;

    else if( value1 < value2 )

    return -1;

    else

    return 0;

    }
}

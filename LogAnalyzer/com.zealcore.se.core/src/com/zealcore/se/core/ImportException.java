/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2009 by Enea Software AB.
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

package com.zealcore.se.core;

/**
 * Exception indicating that an import of a log file has failed.
 */
public class ImportException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Create a new import exception object.
     *
     * @param cause  the cause of this exception.
     */
    public ImportException(final Exception cause) {
        super(cause);
    }

    /**
     * Create a new import exception object.
     *
     * @param message  the detail message string of this exception.
     */
    public ImportException(final String message) {
        super(message);
    }

    /**
     * Create a new import exception object.
     *
     * @param message  the detail message string of this exception.
     * @param cause    the cause of this exception.
     */
    public ImportException(final String message, final Exception cause) {
        super(message, cause);
    }
}

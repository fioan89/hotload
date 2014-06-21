package org.wavescale.hotload.util;

/**
 * ****************************************************************************
 * Copyright (c) 2005-2014 Faur Ioan-Aurel.                                     *
 * All rights reserved. This program and the accompanying materials             *
 * are made available under the terms of the MIT License                        *
 * which accompanies this distribution, and is available at                     *
 * http://opensource.org/licenses/MIT                                           *
 * *
 * For any issues or questions send an email at: fioan89@gmail.com              *
 * *****************************************************************************
 */
public class ClassUtil {

    /**
     * Replaces every "/" chararcter with ".".
     * @param className a {@link String} representing the class name. Usually the value
     *                  has the following form: domain.package.class
     * @return a {@link String} in which the "/" char is replaced with "."
     */
    public static String normalizeClassName(String className) {
        return className.replace("/", ".");
    }
}

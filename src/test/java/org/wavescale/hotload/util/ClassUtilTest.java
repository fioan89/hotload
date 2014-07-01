package org.wavescale.hotload.util;

import junit.framework.TestCase;

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

public class ClassUtilTest extends TestCase {

    public void testNormalizeClassNameWithForwardSlash() {
        assertEquals("org.wavescale.hotload.agent.Class", ClassUtil.normalizeClassName("org/wavescale/hotload/agent.Class"));
    }

    public void testNormalizeClassNameWithPeriod() {
        assertEquals("org.wavescale.hotload.agent.Class", ClassUtil.normalizeClassName("org.wavescale.hotload.agent.Class"));
    }

    public void testNormalizeClassWithMixedChars() {
        assertEquals("org.wavescale.hotload.agent.Class", ClassUtil.normalizeClassName("org/wavescale/hotload.agent.Class"));
    }

    public void testInTabooList() {
        assertTrue(ClassUtil.inTabooListOfPackages("java.lang"));
    }

    public void testNotInTabooList() {
        assertFalse(ClassUtil.inTabooListOfPackages("gibberishjabberis///"));
    }

    public void testDeNormalizeClassNameWithPeriod() {
        assertEquals("org/wavescale/hotload/agent/Class", ClassUtil.deNormalizeClassName("org.wavescale.hotload.agent.Class"));
    }

    public void testDeNormalizeClassNameWithSlash() {
        assertEquals("org/wavescale/hotload/agent/Class", ClassUtil.deNormalizeClassName("org.wavescale/hotload/agent/Class"));
    }

    public void testDeNormalizeClassNameWithMixedChars() {
        assertEquals("org/wavescale/hotload/agent/Class", ClassUtil.deNormalizeClassName("org/wavescale/hotload.agent.Class"));
    }
}

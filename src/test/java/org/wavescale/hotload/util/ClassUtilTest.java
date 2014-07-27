package org.wavescale.hotload.util;

import junit.framework.TestCase;

import java.nio.file.Path;
import java.nio.file.Paths;

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

    public static final String ORG_WAVESCALE_HOTLOAD_AGENT_CLASS = "org.wavescale.hotload.agent.Class";
    public static final String ORG_WAVESCALE_HOTLOAD_AGENT_CLASS_WITH_FSLASH = "org/wavescale/hotload/agent/Class";
    public static final String PUBLIC_METHOD_NAME_WITH_INDEX = "publicStringMethodOne1";
    public static final String PUBLIC_METHOD_NAME_WITH_INDEX_ZERO = "publicStringMethodOne0";
    public static final String PUBLIC_METHOD_NAME_WITHOUT_INDEX = "publicStringMethodOne";
    public static final Path VALID_CLASS_PATH = Paths.get("file://home/user/MainClass.class");
    public static final Path VALID_RESOURCE_PATH = Paths.get("file://home/user/MainClass.properties");
    public static final Path INVALID_CLASS_PATH = Paths.get("file://home/user/MainClass");

    public void testNormalizeClassNameWithForwardSlash() {
        assertEquals(ORG_WAVESCALE_HOTLOAD_AGENT_CLASS, ClassUtil.normalizeClassName("org/wavescale/hotload/agent.Class"));
    }

    public void testNormalizeClassNameWithPeriod() {
        assertEquals(ORG_WAVESCALE_HOTLOAD_AGENT_CLASS, ClassUtil.normalizeClassName("org.wavescale.hotload.agent.Class"));
    }

    public void testNormalizeClassWithMixedChars() {
        assertEquals(ORG_WAVESCALE_HOTLOAD_AGENT_CLASS, ClassUtil.normalizeClassName("org/wavescale/hotload.agent.Class"));
    }

    public void testInTabooList() {
        assertTrue(ClassUtil.inTabooListOfPackages("java.lang"));
    }

    public void testNotInTabooList() {
        assertFalse(ClassUtil.inTabooListOfPackages("gibberishjabberis///"));
    }

    public void testDeNormalizeClassNameWithPeriod() {
        assertEquals(ORG_WAVESCALE_HOTLOAD_AGENT_CLASS_WITH_FSLASH, ClassUtil.deNormalizeClassName(ORG_WAVESCALE_HOTLOAD_AGENT_CLASS));
    }

    public void testDeNormalizeClassNameWithSlash() {
        assertEquals(ORG_WAVESCALE_HOTLOAD_AGENT_CLASS_WITH_FSLASH, ClassUtil.deNormalizeClassName("org.wavescale/hotload/agent/Class"));
    }

    public void testDeNormalizeClassNameWithMixedChars() {
        assertEquals(ORG_WAVESCALE_HOTLOAD_AGENT_CLASS_WITH_FSLASH, ClassUtil.deNormalizeClassName("org/wavescale/hotload.agent.Class"));
    }

    public void testGetPublicMethodNameWithNegativeIndex() {
        assertEquals(PUBLIC_METHOD_NAME_WITHOUT_INDEX, ClassUtil.getPublicMethodName(String.class, "MethodOne", -1));
    }

    public void testGetPublicMethodNameWithZeroIndex() {
        assertEquals(PUBLIC_METHOD_NAME_WITH_INDEX_ZERO, ClassUtil.getPublicMethodName(String.class, "MethodOne", 0));
    }

    public void testGetPublicMethodNameWithPositiveIndex() {
        assertEquals(PUBLIC_METHOD_NAME_WITH_INDEX, ClassUtil.getPublicMethodName(String.class, "MethodOne", 1));
    }

    public void testIsValidFileTypeForMonitoring() {
        assertTrue(ClassUtil.isValidFileType(VALID_CLASS_PATH));
        assertTrue(ClassUtil.isValidFileType(VALID_RESOURCE_PATH));
    }

    public void testIsNotValid() {
        assertFalse(ClassUtil.isValidFileType(INVALID_CLASS_PATH));
    }
}

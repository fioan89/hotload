package org.wavescale.hotload.util.transformer.api;

import junit.framework.TestCase;
import org.objectweb.asm.Opcodes;
import org.wavescale.hotload.transformer.api.VarArgsHelperMethod;
import org.wavescale.hotload.util.Constants;

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
public class VarArgsHelperMethodTest extends TestCase {
    private static final String METHOD_NAME = "testCaseMethodCall";
    private static final int METHOD_ACCESS = Opcodes.ACC_PUBLIC | Opcodes.ACC_TRANSIENT | Opcodes.ACC_VARARGS;
    private VarArgsHelperMethod varArgsHelperMethod;

    public void setUp() {
        this.varArgsHelperMethod = new VarArgsHelperMethod(String.class, METHOD_NAME);
    }

    public void testMethodName() {
        assertEquals(METHOD_NAME, varArgsHelperMethod.getMethodName());
    }

    public void testMethodDescriptor() {
        assertEquals(Constants.VARARGS_METHOD_CALL_DESCRIPTOR, varArgsHelperMethod.desc);
    }

    public void testMethodAccessModifier() {
        assertEquals(METHOD_ACCESS, varArgsHelperMethod.access);
    }


}

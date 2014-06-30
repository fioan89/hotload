package org.wavescale.hotload.transformer.api;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
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
public class VarArgsHelperMethod extends MethodNode {
    private String methodName;
    private Class clazz;

    public VarArgsHelperMethod(Class clazz, String methodName) {
        super(Opcodes.ASM5, Opcodes.ACC_PUBLIC | Opcodes.ACC_TRANSIENT | Opcodes.ACC_VARARGS, methodName,
                Constants.VARARGS_METHOD_CALL_DESCRIPTOR, null, null);
        this.methodName = methodName;
        this.clazz = clazz;
        addEmptyContent();
    }


    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    /**
     * Fills the method body with an empty content, usually a null return.
     */
    private void addEmptyContent() {
        InsnList insnList = this.instructions;
        LabelNode l0 = new LabelNode();
        insnList.add(l0);
        insnList.add(new InsnNode(Opcodes.ACONST_NULL));
        insnList.add(new InsnNode(Opcodes.ARETURN));
        LabelNode l1 = new LabelNode();
        insnList.add(l1);
        String className = "L" + this.clazz.getCanonicalName();
        this.localVariables.add(new LocalVariableNode("this", "L" + className + ";", null, l0, l1, 0));
        this.localVariables.add(new LocalVariableNode("methodName", "Ljava/lang/String;", null, l0, l1, 1));
        this.localVariables.add(new LocalVariableNode("args", "[Ljava/lang/Object;", null, l0, l1, 2));
    }
}

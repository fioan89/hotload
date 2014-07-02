package org.wavescale.hotload.transformer;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;
import org.wavescale.hotload.agent.ConfigManager;
import org.wavescale.hotload.transformer.api.VarArgsHelperMethod;
import org.wavescale.hotload.util.ClassUtil;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.List;

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

/**
 * Inserts helper methods in the new to be loaded bytecode. Called by the instrumenter.
 */
public class MethodTransformer implements ClassFileTransformer {
    private static final ConfigManager configManager = ConfigManager.getInstance();

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (ClassUtil.inTabooListOfPackages(className)) {
            return classfileBuffer;
        }

        ClassNode cn = new ClassNode(Opcodes.ASM5);
        ClassReader cr = new ClassReader(classfileBuffer);
        ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        // create adapter for method insertion.
        cr.accept(cn, 0);

        // add the public var arg helper methods
        for (int i = 0; i < configManager.getNumberOfMethodsToBeAdded(); i++) {
            String methodName = ClassUtil.getPublicMethodName(classBeingRedefined, "MethodCall", i);
            if (canAddMethodName(cn, methodName)) {
                cn.methods.add(new VarArgsHelperMethod(classBeingRedefined, methodName));
            }
        }

        // get the byte code
        cn.accept(cw);
        classfileBuffer = cw.toByteArray();
        return classfileBuffer;
    }

    /**
     * Checks if a method with name {@code methodName} can be added to the class(i.e if the name is unique)
     *
     * @param classNode  a class node instance where the method will be added.
     * @param methodName the method to be added.
     * @return {@code true} if the method name is not unique, thus can be added, {@code false} otherwise.
     */
    private boolean canAddMethodName(ClassNode classNode, String methodName) {
        List<MethodNode> methodNodes = classNode.methods;
        for (MethodNode methodNode : methodNodes) {
            if (methodNode.name.equals(methodName)) {
                return false;
            }
        }
        return true;
    }
}

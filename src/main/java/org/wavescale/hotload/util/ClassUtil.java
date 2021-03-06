package org.wavescale.hotload.util;

import java.nio.file.Path;

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
     * A list of packages that must be skipped from transforming.
     */
    public static final String[] tabooPackages = {
            "java.applet",
            "java.awt",
            "java.beans",
            "java.io",
            "java.lang",
            "java.lang",
            "java.math",
            "java.net",
            "java.nio",
            "java.rmi",
            "java.security",
            "java.sql",
            "java.text",
            "java.util",
            "javax.accessibility",
            "javax.activation",
            "javax.activity",
            "javax.annotation",
            "javax.crypto",
            "javax.imageio",
            "javax.jws",
            "javax.lang",
            "javax.management",
            "javax.naming",
            "javax.net",
            "javax.print",
            "javax.rmi",
            "javax.script",
            "javax.security",
            "javax.sound",
            "javax.sql",
            "javax.swing",
            "javax.tools",
            "javax.transaction",
            "javax.xml",
            "junit.framework",
            "junit.awtui",
            "junit.extensions",
            "junit.runner",
            "junit.swingui",
            "junit.textui",
            "net.contentobjects",
            "org.ietf.jgss",
            "org.omg",
            "org.w3c",
            "org.xml.sax",
            "org.coldswap",
            "org.objectweb",
            "org.junit",
            "org.hamcrest",
            "sun.util",
            "sun.misc",
            "sun.net",
            "sun.nio",
            "sun.text",
            "sun.reflect",
            "sun.security",
            "com.intellij"
    };

    /**
     * A list of file types extensions that can be watched for different events like
     * creation, modification and remove.
     */
    public static final String[] fileTypesToWatch = {".class", ".properties"};

    /**
     * Replaces every "/" character with ".".
     *
     * @param className a {@link String} representing the class name. Usually the value
     *                  has the following form: domain/package/class. If this is null an empty string will be returned
     * @return a {@link String} in which the "/" char is replaced with "."
     */
    public static String normalizeClassName(String className) {
        if (className != null) {
            return className.replace("/", ".");
        }
        return "";
    }

    /**
     * Replaces every "." character with "/".
     *
     * @param className a {@link String} representing the class name. Usually the value
     *                  has the following form: domain.package.class. If this is null an empty string will be returned
     * @return a {@link String} in which the "." char is replaced with "/"
     */
    public static String deNormalizeClassName(String className) {
        return className.replace(".", "/");
    }

    /**
     * Checks if the class name exists in a list of taboo packages(kept internally)
     *
     * @param className a {@link String} representing a package or full class name
     * @return true if class name should be excluded, false otherwise
     */
    public static boolean inTabooListOfPackages(String className) {
        for (String skipPackage : tabooPackages) {
            if (normalizeClassName(className).startsWith(skipPackage)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Builds a unique method name by concatenating the "public", simple class name, method name
     * and the index if it is greater than negative one.
     *
     * @param clazz      a {@link Class} instance
     * @param methodName a string representing the central part of the method name
     * @param index      if the value is greater than -1, the string value will be appended to the name
     * @return a {code String} instance represented by the concatenation of "public", simple class name, method name
     * and string value of index.
     */
    public static String getPublicMethodName(Class clazz, String methodName, int index) {
        String indexValue = "";
        if (index >= 0) {
            indexValue = String.valueOf(index);
        }

        return "public" + clazz.getSimpleName() + methodName + indexValue;
    }

    /**
     * Checks if the given file path is valid for monitoring (i.e is a class or resource file).
     *
     * @param filePath a {@link Path} instance representing a valid path
     * @return {@code true} if the file is a class file or a resource file, {@code false} otherwise
     */
    public static boolean isValidFileType(Path filePath) {
        for (String fileType : fileTypesToWatch) {
            if (filePath != null && filePath.getFileName().toString().endsWith(fileType)) {
                return true;
            }
        }

        return false;
    }
}

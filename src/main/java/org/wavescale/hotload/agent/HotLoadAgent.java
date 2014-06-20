package org.wavescale.hotload.agent;

import java.lang.instrument.Instrumentation;

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
public class HotLoadAgent {

    public static void premain(String agentArgs, Instrumentation instrumentation) {
        System.out.println("com.wavescale.hotload.agent.HotLoadAgent.premain");
    }
}

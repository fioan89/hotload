package org.wavescale.hotload.agent;

import org.wavescale.hotload.transformer.MethodTransformer;
import org.wavescale.hotload.watcher.FileWatcher;
import org.wavescale.hotload.watcher.WatchHandler;
import org.wavescale.hotload.watcher.api.NotifyHandler;

import java.io.IOException;
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
        ConfigManager configManager = ConfigManager.getInstance();
        NotifyHandler watchHandler = new WatchHandler();
        try {
            FileWatcher fileMonitor = new FileWatcher(configManager.getDirsToMonitor(), configManager.isMonitorRecursive());
            fileMonitor.addNotifyHandler(watchHandler);
            instrumentation.addTransformer(new MethodTransformer());
        } catch (IOException e) {
            // TODO -proper log the exeception
            e.printStackTrace();
        }
    }
}

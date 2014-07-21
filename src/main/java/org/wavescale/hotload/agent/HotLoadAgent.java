package org.wavescale.hotload.agent;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import org.wavescale.hotload.agent.api.OptionsParser;
import org.wavescale.hotload.transformer.MethodTransformer;
import org.wavescale.hotload.watcher.FileWatcher;
import org.wavescale.hotload.watcher.WatchHandler;
import org.wavescale.hotload.watcher.api.NotifyHandler;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

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
    private static final Logger LOGGER = Logger.getLogger(HotLoadAgent.class.getName());

    public static void premain(String agentArgs, Instrumentation instrumentation) {
        ConfigManager configManager = ConfigManager.getInstance();
        initConfigManager(agentArgs.split(" "));
        LogManager.getLogManager().getLogger(Logger.GLOBAL_LOGGER_NAME).setLevel(configManager.getLogLevel());
        NotifyHandler watchHandler = new WatchHandler();
        try {
            FileWatcher fileMonitor = new FileWatcher(configManager.getDirsToMonitor(), configManager.isMonitorRecursive());
            fileMonitor.addNotifyHandler(watchHandler);
            instrumentation.addTransformer(new MethodTransformer());
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, " An I/O error occured with trace:" + e);
        }
    }

    /**
     * Parses agent options and fills up the config manager.
     *
     * @param args agent optional arguments
     */
    private static void initConfigManager(String[] args) {
        ConfigManager configManager = ConfigManager.getInstance();
        OptionsParser parser = new AgentArgParser(args);
        try {
            CommandLine options = parser.parse();
            // get the recursive monitoring option value
            if (options.hasOption("recursive")) {
                String recursive = options.getOptionValue("recursive");
                if (!"true".equals(recursive.toLowerCase())) {
                    configManager.setMonitorRecursive(false);
                }
            }

            // get the number of virtual methods that can be instrumented
            if (options.hasOption("nr-of-virtual-methods")) {
                String nrOfVirtualMethods = options.getOptionValue("nr-of-virtual-methods");
                configManager.setNumberOfMethodsToBeAdded(Short.parseShort(nrOfVirtualMethods));
            }

            if (options.hasOption("class-path")) {
                String[] directoryToMonitor = options.getOptionValue("class-path").split(File.pathSeparator);
                configManager.setDirsToMonitor(directoryToMonitor);
            }

            if (options.hasOption("log-level")) {
                String logLevel = options.getOptionValue("log-level");
                switch (logLevel.toLowerCase()) {
                    case "fine":
                        configManager.setLogLevel(Level.FINE);
                        break;
                    case "info":
                        configManager.setLogLevel(Level.INFO);
                        break;
                    case "warning":
                        configManager.setLogLevel(Level.WARNING);
                        break;
                    case "severe":
                        configManager.setLogLevel(Level.SEVERE);
                        break;
                    case "all":
                        configManager.setLogLevel(Level.ALL);
                        break;
                    case "off":
                        configManager.setLogLevel(Level.OFF);
                        break;

                    default:
                        configManager.setLogLevel(Level.INFO);
                }
            }
        } catch (ParseException e) {
            LOGGER.log(Level.WARNING, "Could not parse agent arguments due to:" + e);
        }
    }
}

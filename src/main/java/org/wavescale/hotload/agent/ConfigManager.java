package org.wavescale.hotload.agent;

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

import org.wavescale.hotload.util.Constants;

import java.util.logging.Level;

/**
 * A configuration model used to store all agent arguments.
 */
public class ConfigManager {
    private static final ConfigManager ourInstance = new ConfigManager();

    private int numberOfMethodsToBeAdded = Constants.DEFAULT_NR_OF_HELPER_METHODS;
    private String[] dirsToMonitor;
    private boolean monitorRecursive = true;
    private Level logLevel = Level.INFO;

    public static ConfigManager getInstance() {
        return ourInstance;
    }

    private ConfigManager() {
    }

    /**
     * Gets the number of helper methods that can be added per type. By default
     * {@link org.wavescale.hotload.util.Constants#DEFAULT_NR_OF_HELPER_METHODS}
     *
     * @return the number of helper methods that can be added per type.
     */
    public int getNumberOfMethodsToBeAdded() {
        return numberOfMethodsToBeAdded;
    }

    /**
     * Sets the number of helper methods that can be added per helper method type.
     *
     * @param numberOfMethodsToBeAdded a value between 0 and {@link java.lang.Short#MAX_VALUE}
     */
    public void setNumberOfMethodsToBeAdded(int numberOfMethodsToBeAdded) {
        if (numberOfMethodsToBeAdded >= 0 && numberOfMethodsToBeAdded <= Short.MAX_VALUE) {
            this.numberOfMethodsToBeAdded = numberOfMethodsToBeAdded;
        } else {
            String errorMessage = String.valueOf(numberOfMethodsToBeAdded) + " of helper methods is invalid. The value mustbe in range [0, " + Short.MAX_VALUE + "]";
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public String[] getDirsToMonitor() {
        return dirsToMonitor;
    }

    public void setDirsToMonitor(String[] dirsToMonitor) {
        this.dirsToMonitor = dirsToMonitor;
    }

    public boolean isMonitorRecursive() {
        return monitorRecursive;
    }

    public void setMonitorRecursive(boolean monitorRecursive) {
        this.monitorRecursive = monitorRecursive;
    }

    public Level getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(Level logLevel) {
        this.logLevel = logLevel;
    }
}

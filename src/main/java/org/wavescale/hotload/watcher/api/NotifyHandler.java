package org.wavescale.hotload.watcher.api;

import java.nio.file.Path;
import java.nio.file.WatchEvent;

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
public interface NotifyHandler {

    /**
     * Called when a new file was created.
     *
     * @param event triggered  when a new file was created.
     */
    public void digestCreatedFile(WatchEvent<Path> event);

    /**
     * Called when a file was modified.
     *
     * @param event triggered  when a file was modified.
     */
    public void digestModifiedFile(WatchEvent<Path> event);
}

package org.wavescale.hotload.watcher.api;

import java.io.IOException;
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
public interface Watcher {

    /**
     * Registers the given directory with the watch service for creation and modification.
     *
     * @param dir a {@link Path} object used to locate a directory under a file system.
     * @throws java.io.IOException if the object could not be registered.
     */
    public void register(Path dir) throws IOException;

    /**
     * Recursively register the given directory and it's subdirectories with the watch service for creation and
     * modification.
     *
     * @param dir a {@link Path} object used to locate a directory under a file system.
     * @throws java.io.IOException if the object could not be registered.
     */
    public void registerAll(Path dir) throws IOException;

    /**
     * Registers handler for handling the file creation and modification event.
     *
     * @param notifyHandler notification handler.
     */
    public void addNotifyHandler(NotifyHandler notifyHandler);
}

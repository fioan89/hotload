package org.wavescale.hotload.watcher;

import org.wavescale.hotload.watcher.api.NotifyHandler;

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
public class WatchHandler implements NotifyHandler {
    @Override
    public void digestCreatedFile(WatchEvent<Path> event) {
        System.out.println("org.wavescale.hotload.watcher.WatchHandler.digestCreatedFile");

        Path name = event.context();
        System.out.println(event.kind().name() + " triggered for " + name.toString());

    }

    @Override
    public void digestModifiedFile(WatchEvent<Path> event) {
        System.out.println("org.wavescale.hotload.watcher.WatchHandler.digestModifiedFile");

        Path name = event.context();
        System.out.println(event.kind().name() + " triggered for " + name.toString());
    }
}

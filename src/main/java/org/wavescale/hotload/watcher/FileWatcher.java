package org.wavescale.hotload.watcher;

import org.wavescale.hotload.watcher.api.NotifyHandler;
import org.wavescale.hotload.watcher.api.Watcher;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.nio.file.StandardWatchEventKinds.*;

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
 * An auto starting daemon thread for file watching. If a file was modified or just created
 * the corresponding digest of all registered notify handlers is called. To register new handlers
 * call {@link #addNotifyHandler(org.wavescale.hotload.watcher.api.NotifyHandler)}. The public interface
 * for this class is thread safe.
 */
public class FileWatcher extends Thread implements Watcher {
    private static final Logger LOGGER = Logger.getLogger(FileWatcher.class.getName());
    private WatchService watcher;
    private Map<WatchKey, Path> keys;
    private List<NotifyHandler> handlers;
    private boolean recursive;

    public FileWatcher(String[] dirsToWatch, boolean isRecursive) throws IOException {
        this.watcher = FileSystems.getDefault().newWatchService();
        this.keys = new HashMap<>();
        this.handlers = new ArrayList<>();
        this.recursive = isRecursive;
        for (String dirToWatch : dirsToWatch) {
            // check if its dir
            if (isFolder(dirToWatch)) {
                LOGGER.log(Level.INFO, "Registering folder " + dirToWatch + " for monitoring");
                if (recursive) {
                    registerAll(Paths.get(dirToWatch));
                } else {
                    register(Paths.get(dirToWatch));
                }

            } else {
                LOGGER.log(Level.WARNING, "Skipping " + dirToWatch + " for the file is not a valid directory");
            }
        }

        this.setDaemon(true);
        this.start();
    }

    private boolean isFolder(String dir) {
        return isFolder(Paths.get(dir));
    }

    private boolean isFolder(Path dir) {
        return Files.isDirectory(dir, LinkOption.NOFOLLOW_LINKS);
    }

    @Override
    public synchronized void register(Path dir) throws IOException {
        WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_MODIFY);
        keys.put(key, dir);
    }

    @Override
    public synchronized void registerAll(Path dir) throws IOException {
        // register directory and sub-directories
        Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                    throws IOException {
                register(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    @Override
    public synchronized void addNotifyHandler(NotifyHandler notifyHandler) {
        this.handlers.add(notifyHandler);
    }

    public void run() {
        processEvents();
    }

    /**
     * Process all events for keys queued to the watcher
     */
    protected void processEvents() {
        for (; ; ) {

            // wait for key to be signalled
            WatchKey key;
            try {
                key = watcher.take();
            } catch (InterruptedException x) {
                return;
            }

            Path dir = keys.get(key);
            if (dir == null) {
                LOGGER.log(Level.WARNING, " Watch key not recognized");
                continue;
            }

            for (WatchEvent<?> event : key.pollEvents()) {
                WatchEvent.Kind kind = event.kind();

                // TBD - provide example of how OVERFLOW event is handled
                if (kind == OVERFLOW) {
                    continue;
                }

                // Context for directory entry event is the file name of entry
                WatchEvent<Path> ev = (WatchEvent<Path>) event;
                Path name = ev.context();
                Path child = dir.resolve(name);

                LOGGER.log(Level.INFO, "The " + event.kind().name() + " event was triggered on:" + child);

                // if directory is created, and watching recursively, then
                // register it and its sub-directories
                if (recursive && (kind == ENTRY_CREATE)) {
                    try {
                        if (isFolder(child)) {
                            registerAll(child);
                        }
                    } catch (IOException x) {
                        // ignore to keep sample readbale
                    }
                }
                // notify the handlers
                for (NotifyHandler handler : handlers) {
                    if (ENTRY_CREATE.equals(kind)) {
                        handler.digestCreatedFile((WatchEvent<Path>) event);
                    } else if (ENTRY_MODIFY.equals(kind)) {
                        handler.digestModifiedFile((WatchEvent<Path>) event);
                    } else {
                        LOGGER.log(Level.FINE, "Skipped event of type " + kind.name());
                    }
                }

            }

            // reset key and remove from set if directory no longer accessible
            boolean valid = key.reset();
            if (!valid) {
                keys.remove(key);

                // all directories are inaccessible
                if (keys.isEmpty()) {
                    break;
                }
            }
        }
    }
}

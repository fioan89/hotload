package org.wavescale.hotload.agent.api;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;

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
public interface OptionsParser {
    CommandLine parse() throws ParseException;

    CommandLine parse(String[] args) throws ParseException;
}

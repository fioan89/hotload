package org.wavescale.hotload.agent;

import junit.framework.TestCase;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import org.wavescale.hotload.agent.api.OptionsParser;

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
public class AgentArgParserTest extends TestCase {
    private static final String OPTIONS_1 = "--nr-of-virtual-methods=5 --recursive=false --class-path=/home/userx";
    private static final String OPTIONS_2 = "--nr-of-virtual-methods=5 --recursive=true --class-path=/home/userx:" +
            "/home/usery";

    public void testParse() {
        OptionsParser options = new AgentArgParser(OPTIONS_1.split(" "));
        try {
            CommandLine cmd = options.parse();
            assertEquals("5", cmd.getOptionValue("nr-of-virtual-methods"));
            assertEquals("false", cmd.getOptionValue("recursive"));
            assertEquals("/home/userx", cmd.getOptionValue("class-path"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void testParseOptionsTwo() {
        OptionsParser options = new AgentArgParser(OPTIONS_1.split(" "));
        try {
            CommandLine cmd = options.parse(OPTIONS_2.split(" "));
            assertEquals("5", cmd.getOptionValue("nr-of-virtual-methods"));
            assertEquals("true", cmd.getOptionValue("recursive"));
            assertEquals("/home/userx:/home/usery", cmd.getOptionValue("class-path"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}

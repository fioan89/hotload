package org.wavescale.hotload.agent;

import org.apache.commons.cli.*;
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
public class AgentArgParser implements OptionsParser {
    private String[] rawArgs;
    private Options options;
    private CommandLineParser parser;

    public AgentArgParser(String[] options) {
        this.rawArgs = options;
        init();
    }

    private void init() {
        options = new Options();
        parser = new PosixParser();

        initDefinitionStage();
    }

    /**
     * Inits the set of options
     */
    private void initDefinitionStage() {
        if (options != null) {
            options.addOption(OptionBuilder.withLongOpt("nr-of-virtual-methods").withDescription("number of virtual" +
                    " methods that can be instrumented").hasArg().withArgName("COUNT").create());
            options.addOption(OptionBuilder.withLongOpt("class-path").withDescription("directory where classes reside")
                    .hasArg().withArgName("PATH").create());
            options.addOption("r", "recursive", true, "monitor the folder recursive");
        }

    }

    /**
     * Parses the default options and returns a {@link org.apache.commons.cli.CommandLine} instance.
     */
    @Override
    public CommandLine parse() throws ParseException {
        return parse(this.rawArgs);

    }

    /**
     * Parses the given list of arguments and returns a {@link org.apache.commons.cli.CommandLine} instance.
     *
     * @param args a list of {@code String} arguments given to the agent.
     * @return a  {@link org.apache.commons.cli.CommandLine} instance that can be used to interrogate and retrieve
     * the agent arguments.
     * @throws ParseException if there are any problems encountered while parsing the command line tokens
     */
    @Override
    public CommandLine parse(String[] args) throws ParseException {
        return parser.parse(this.options, args);
    }

}

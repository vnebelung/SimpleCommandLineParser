/*
 * This file is part of ProDisFuzz, modified on 05.01.20, 10:25.
 * Copyright (c) 2013-2020 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package main;

/**
 * This interface represents a parsed command of a command line string. The parsed command summarizes
 */
public interface ParsedCommand extends ParsedSubcommand {

    /**
     * Returns the parsed subcommand of this command that was submitted by the user via the command line. If the user
     * had not provided a subcommand, the return value will be null.
     *
     * @return the user-provided subcommand or null if no subcommand is found
     */
    ParsedSubcommand getSubcommand();

}

/*
 * This file is part of ProDisFuzz, modified on 19.10.24, 23:58.
 * Copyright (c) 2013-2024 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package main;

import java.util.Set;

/**
 * This interface represents a command of a command line string. A command line string must have the following form:
 * COMMAND SUBCOMMAND --key1 value1 --key2 value2 … Usually COMMAND is the name of the executable file (or jar file)
 * that the user will call on the command line. Since this file name does not change and is always the same for every
 * call, you can only define one single command.
 */
public interface Command extends Subcommand {

    /**
     * Adds a subcommand to this command. The given parameter is now part of the set of subcommands that a user must use
     * (depending on the given parameter's default value) during executing the command line. Because a command can have
     * either subcommands or parameters, an illegal state exception is thrown if this command has parameters.
     *
     * @param subcommand the subcommand to be added
     * @return true if this command did not already contain the given subcommand
     * @throws IllegalStateException if this command already has parameters
     */
    boolean add(Subcommand subcommand) throws IllegalStateException;

    /**
     * Adds a parameter to this command.
     *
     * @param parameter the parameter to be added
     * @return true if this command did not already contain the given parameter
     */
    boolean add(Parameter<?> parameter);

    /**
     * Returns the subcommands of this command ordered by their name. If the command has no subcommands, an empty set
     * is returned.
     *
     * @return the command's subcommands
     */
    Set<Subcommand> getSubcommands();

}

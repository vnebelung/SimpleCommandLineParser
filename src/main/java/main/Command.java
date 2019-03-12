/*
 * This file is part of ProDisFuzz, modified on 3/7/19 11:15 PM.
 * Copyright (c) 2013-2019 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package main;

/**
 * This interface represents a command of a command line string. A command line string must have the following form:
 * COMMAND SUBCOMMAND --key1=value1 --key2=value2… Usually COMMAND is the name of the executable file (or jar file) that
 * the user will call on the command line. Since this file name does not change and is always the same for every call,
 * you can only define one single command.
 */
public interface Command extends Subcommand {

    /**
     * Adds a subcommand to this command. The given parameter is now part of the set of subcommands that a user must use
     * (depending on the given parameter's default value) during executing the command line. Because a command can have
     * either subcommands or parameters, an illegal state exception is thrown if this command has parameters.
     *
     * @param subcommand the subcommand to be added
     */
    void add(Subcommand subcommand) throws IllegalStateException;

    /**
     * Adds a parameter to this command. The given parameter is now part of the set of parameters that a user can or
     * must use (depending on whether the given parameter has a default value) during executing the command line.
     * Because a command can have either subcommands or parameters, an illegal state exception is thrown if this command
     * has a subcommand.
     *
     * @param parameter the parameter to be added
     */
    @Override
    void add(Parameter<?> parameter) throws IllegalStateException;

    /**
     * Returns the subcommand with the given name.
     *
     * @param name the subcommand's name
     * @return the subcommand with the given name or null if no subcommand is found
     */
    Subcommand getSubcommand(String name);

}

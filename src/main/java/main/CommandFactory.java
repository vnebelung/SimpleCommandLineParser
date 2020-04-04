/*
 * This file is part of ProDisFuzz, modified on 04.04.20, 22:47.
 * Copyright (c) 2013-2020 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package main;

import internal.commands.InternalCommand;
import internal.commands.InternalSubcommand;

/**
 * This class is the factory for generating commands and subcommands.
 */
public class CommandFactory {

    private CommandFactory() {
    }

    /**
     * Creates a subcommand with a given name and description. Usually the command is the name of the executable file
     * (or jar file) that the user will call on the command line. Since this file name does not change and is always
     * the same for every call, you can only define one single command.
     *
     * @param name        the command's name
     * @param description the command's description for the help menu
     * @return the created command
     */
    public static Command createCommand(String name, String description) {
        return new InternalCommand(name, description);
    }

    /**
     * Creates a subcommand with a given name and description. The subcommand is used to distinguish different
     * operations that a user can choose on the command line. Every subcommand has its independent set of parameters.
     *
     * @param name        the subcommand's name
     * @param description the subcommand's description for the help menu
     * @return the created subcommand
     */
    public static Subcommand createSubcommand(String name, String description) {
        return new InternalSubcommand(name, description);
    }

}

/*
 * This file is part of ProDisFuzz, modified on 12/14/18 6:19 PM.
 * Copyright (c) 2013-2018 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package commands;

import parameters.Parameter;

import java.util.*;

/**
 * This class is a command of the command line string. The format of this string is as follows: COMMAND SUBCOMMAND
 * --key1=value1 --key2=value2 ...
 */
public class Command extends Subcommand {

    private Map<String, Subcommand> subcommands = new TreeMap<>();

    /**
     * Instantiates a new command.
     *
     * @param name        the command's name
     * @param description the command's description for the help menu
     */
    public Command(String name, String description) {
        super(name, description);
    }

    /**
     * Adds a subcommand to this command. Because a command can have either a subcommand or parameters, this command
     * loses all of its parameters by invoking this method.
     *
     * @param subcommand the subcommand to be added
     */
    public void add(Subcommand subcommand) {
        if (subcommand == null) {
            throw new IllegalArgumentException("Subcommand must not be null");
        }
        if (!getParameters().isEmpty()) {
            throw new IllegalStateException("Subcommand cannot be added because this command already has parameters");
        }
        subcommands.put(subcommand.getName(), subcommand);
    }

    /**
     * Adds a parameter to this subcommand. Because a command can have either a subcommand or parameters, this command
     * loses all of its subcommands by invoking this method.
     *
     * @param parameter the parameter to be added
     */
    @Override
    public void add(Parameter parameter) {
        if (!subcommands.isEmpty()) {
            throw new IllegalStateException("Parameter cannot be added because this command already has subcommands");
        }
        super.add(parameter);
    }

    /**
     * Returns all subcommands ordered by their name.
     *
     * @return the command's subcommands
     */
    public Set<Subcommand> getSubcommands() {
        Set<Subcommand> result = new TreeSet<>(Comparator.comparing(Subcommand::getName));
        result.addAll(subcommands.values());
        return result;
    }

    /**
     * Returns the subcommand with the given name.
     *
     * @param name the subcommand's name
     * @return the subcommand with the given name or null if no subcommand is found
     */
    public Subcommand getSubcommand(String name) {
        return subcommands.get(name);
    }

}

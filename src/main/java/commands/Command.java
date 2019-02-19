/*
 * This file is part of ProDisFuzz, modified on 2/19/19 10:04 PM.
 * Copyright (c) 2013-2019 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package commands;

import parameters.BooleanParameter;
import parameters.IntegerParameter;
import parameters.Parameter;
import parameters.StringParameter;

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
     * Adds a boolean parameter to this command. Because a command can have either a subcommand or parameters, an
     * exception is thrown if this command has a subcommand.
     *
     * @param parameter the parameter to be added
     */
    @Override
    public void add(BooleanParameter parameter) {
        if (!subcommands.isEmpty()) {
            throw new IllegalStateException("Parameter cannot be added because this command already has subcommands");
        }
        super.add(parameter);
    }

    /**
     * Adds a string parameter to this command. Because a command can have either a subcommand or parameters, an
     * exception is thrown if this command has a subcommand.
     *
     * @param parameter the parameter to be added
     */
    @Override
    public void add(StringParameter parameter) {
        if (!subcommands.isEmpty()) {
            throw new IllegalStateException("Parameter cannot be added because this command already has subcommands");
        }
        super.add(parameter);
    }

    /**
     * Adds an integer parameter to this command. Because a command can have either a subcommand or parameters, an
     * exception is thrown if this command has a subcommand.
     *
     * @param parameter the parameter to be added
     */
    @Override
    public void add(IntegerParameter parameter) {
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

    /**
     * Returns a deep copy of this command, that means a newly instantiated command with a name and a description,
     * including all of it's parameters.
     *
     * @return the copied subcommand
     */
    public Command copy() {
        Command result = new Command(getName(), getDescription());
        for (Map.Entry<String, Parameter<Boolean>> nameToParameter : namesToBooleanParameters.entrySet()) {
            result.namesToBooleanParameters.put(nameToParameter.getKey(), nameToParameter.getValue().copy());
        }
        for (Map.Entry<String, Parameter<String>> nameToParameter : namesToStringParameters.entrySet()) {
            result.namesToStringParameters.put(nameToParameter.getKey(), nameToParameter.getValue().copy());
        }
        for (Map.Entry<String, Parameter<Integer>> nameToParameter : namesToIntegerParameters.entrySet()) {
            result.namesToIntegerParameters.put(nameToParameter.getKey(), nameToParameter.getValue().copy());
        }
        return result;
    }
}

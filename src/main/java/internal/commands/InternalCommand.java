/*
 * This file is part of ProDisFuzz, modified on 05.01.20, 10:25.
 * Copyright (c) 2013-2020 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package internal.commands;

import internal.parameters.AbstractParameter;
import main.*;

import java.util.*;

/**
 * This class is a command of the command line string. The format of this string is as follows: COMMAND SUBCOMMAND
 * --key1=value1 --key2=value2 ...
 */
public class InternalCommand extends InternalSubcommand implements Command, ParsedCommand {

    private TreeMap<String, InternalSubcommand> subcommands = new TreeMap<>();

    /**
     * Instantiates a new command.
     *
     * @param name        the command's name
     * @param description the command's description for the help menu
     */
    public InternalCommand(String name, String description) {
        super(name, description);
    }

    @Override
    public void add(Subcommand subcommand) {
        if (subcommand == null) {
            throw new IllegalArgumentException("Subcommand must not be null");
        }
        if (!getParameters().isEmpty()) {
            throw new IllegalStateException("Subcommand cannot be added because this command already has parameters");
        }
        if (!subcommand.getClass().getSimpleName().equals("InternalSubcommand")) {
            throw new IllegalArgumentException("Parameter was not created by the command line parser");
        }
        InternalSubcommand castSubcommand = (InternalSubcommand) subcommand;
        subcommands.put(castSubcommand.getName(), castSubcommand);
    }

    @Override
    public void add(Parameter<?> parameter) {
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
    public Set<InternalSubcommand> getSubcommands() {
        Set<InternalSubcommand> result = new TreeSet<>(Comparator.comparing(InternalSubcommand::getName));
        result.addAll(subcommands.values());
        return result;
    }

    @Override
    public InternalSubcommand getSubcommand(String name) {
        return subcommands.get(name);
    }

    /**
     * Returns a copy of this command, that is a newly instantiated command with the same name, the same description,
     * and the same parameters. Note that subcommands are not copied.
     *
     * @return the copied command
     */
    public InternalCommand copy() {
        InternalCommand result = new InternalCommand(getName(), getDescription());
        for (Map.Entry<String, AbstractParameter<Boolean>> nameToParameter : namesToBooleanParameters.entrySet()) {
            result.namesToBooleanParameters.put(nameToParameter.getKey(), nameToParameter.getValue());
        }
        for (Map.Entry<String, AbstractParameter<String>> nameToParameter : namesToStringParameters.entrySet()) {
            result.namesToStringParameters.put(nameToParameter.getKey(), nameToParameter.getValue());
        }
        for (Map.Entry<String, AbstractParameter<Integer>> nameToParameter : namesToIntegerParameters.entrySet()) {
            result.namesToIntegerParameters.put(nameToParameter.getKey(), nameToParameter.getValue());
        }
        return result;
    }

    @Override
    public ParsedSubcommand getSubcommand() {
        if (subcommands.isEmpty()) {
            return null;
        }
        return subcommands.firstEntry().getValue();
    }
}

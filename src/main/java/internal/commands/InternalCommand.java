/*
 * This file is part of ProDisFuzz, modified on 04.04.20, 23:34.
 * Copyright (c) 2013-2020 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package internal.commands;

import internal.parameters.AbstractParameter;
import main.Command;
import main.Parameter;
import main.ParsedCommand;
import main.Subcommand;

import java.util.*;

/**
 * This class is a command of the command line string. The format of this string is as follows: COMMAND SUBCOMMAND
 * --key1 value1 --key2 value2 ...
 */
public class InternalCommand extends InternalSubcommand implements Command, ParsedCommand {

    private Set<InternalSubcommand> subcommands = new HashSet<>();

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
    public boolean add(Subcommand subcommand) {
        if (subcommand == null) {
            throw new IllegalArgumentException("Subcommand must not be null");
        }
        if (!getParameters().isEmpty()) {
            throw new IllegalStateException("Subcommand cannot be added because this command already has parameters");
        }
        if (subcommand.getClass() != InternalSubcommand.class) {
            throw new IllegalArgumentException("Parameter was not created by the command line parser");
        }
        InternalSubcommand castSubcommand = (InternalSubcommand) subcommand;
        return subcommands.add(castSubcommand);
    }

    @Override
    public boolean add(Parameter<?> parameter) {
        if (!subcommands.isEmpty()) {
            throw new IllegalStateException("Parameter cannot be added because this command already has subcommands");
        }
        return super.add(parameter);
    }

    @Override
    public Set<Subcommand> getSubcommands() {
        Set<InternalSubcommand> result = new TreeSet<>(Comparator.comparing(InternalSubcommand::getName));
        result.addAll(subcommands);
        return Collections.unmodifiableSet(result);
    }

    /**
     * Returns a copy of this command, that is a newly instantiated command with the same name, the same description,
     * and the same parameters. Note that subcommands are not copied.
     *
     * @return the copied command
     */
    public InternalCommand copy() {
        InternalCommand result = new InternalCommand(getName(), getDescription());
        getParameters().forEach(parameter -> result.add(((AbstractParameter<?>) parameter).copy()));
        return result;
    }

    /**
     * Returns the subcommand with the given name.
     *
     * @param name the subcommand's name
     * @return the subcommand or null if a subcommand with the given name cannot be found
     */
    public InternalSubcommand getSubcommand(String name) {
        return subcommands.stream().filter(subcommand -> subcommand.getName().equals(name)).findFirst().orElse(null);
    }

    @Override
    public InternalSubcommand getSubcommand() {
        return subcommands.stream().findFirst().orElse(null);
    }
}

/*
 * This file is part of ProDisFuzz, modified on 04.04.20, 22:47.
 * Copyright (c) 2013-2020 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package internal.commands;

import internal.parameters.AbstractParameter;
import internal.parameters.BooleanParameter;
import internal.parameters.IntegerParameter;
import internal.parameters.StringParameter;
import main.Parameter;
import main.ParsedParameter;
import main.ParsedSubcommand;
import main.Subcommand;

import java.util.*;

/**
 * This class is a subcommand of the command line string. The format of this string is as follows: COMMAND SUBCOMMAND
 * --key1 value1 --key2 value2 ...
 */
public class InternalSubcommand implements Subcommand, ParsedSubcommand {

    private Set<AbstractParameter<Integer>> integerParameters = new HashSet<>();
    private Set<AbstractParameter<String>> stringParameters = new HashSet<>();
    private Set<AbstractParameter<Boolean>> booleanParameters = new HashSet<>();
    private String description;
    private String name;

    /**
     * Instantiates a new subcommand.
     *
     * @param name        the parameter's name
     * @param description the subcommand's description for the help menu
     */
    public InternalSubcommand(String name, String description) {
        if (name == null) {
            throw new IllegalArgumentException("The subcommand's name must not be null");
        }
        if (name.isBlank()) {
            throw new IllegalArgumentException("The subcommand's name must not be empty");
        }
        if (description == null) {
            throw new IllegalArgumentException("The subcommand's description must not be empty");
        }
        if (description.isBlank()) {
            throw new IllegalArgumentException("The subcommand's description must not be empty");
        }
        this.name = name;
        this.description = description;
    }

    @Override
    public Set<Parameter<?>> getParameters() {
        Set<AbstractParameter<?>> results = new TreeSet<>(Comparator.comparing(AbstractParameter::getName));
        results.addAll(integerParameters);
        results.addAll(stringParameters);
        results.addAll(booleanParameters);
        return Collections.unmodifiableSet(results);
    }

    @Override
    public void add(Parameter<?> parameter) {
        if (parameter == null) {
            throw new IllegalArgumentException("Parameter must not be null");
        }
        if (parameter.getClass() == IntegerParameter.class) {
            IntegerParameter integerParameter = (IntegerParameter) parameter;
            integerParameters.add(integerParameter);
            stringParameters.removeIf(p -> p.getName().equals(parameter.getName()));
            booleanParameters.removeIf(p -> p.getName().equals(parameter.getName()));
        } else if (parameter.getClass() == BooleanParameter.class) {
            BooleanParameter booleanParameter = (BooleanParameter) parameter;
            integerParameters.removeIf(p -> p.getName().equals(parameter.getName()));
            stringParameters.removeIf(p -> p.getName().equals(parameter.getName()));
            booleanParameters.add(booleanParameter);
        } else if (parameter.getClass() == StringParameter.class) {
            StringParameter stringParameter = (StringParameter) parameter;
            integerParameters.removeIf(p -> p.getName().equals(parameter.getName()));
            stringParameters.add(stringParameter);
            booleanParameters.removeIf(p -> p.getName().equals(parameter.getName()));
        } else {
            throw new IllegalArgumentException("Parameter was not created by the command line parser");
        }
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public ParsedParameter<Integer> getIntegerParameter(String name) {
        return integerParameters.stream().filter(parameter -> parameter.getName().equals(name)).findFirst()
                .orElse(null);
    }

    @Override
    public ParsedParameter<Boolean> getBooleanParameter(String name) {
        return booleanParameters.stream().filter(parameter -> parameter.getName().equals(name)).findFirst()
                .orElse(null);
    }

    @Override
    public ParsedParameter<String> getStringParameter(String name) {
        return stringParameters.stream().filter(parameter -> parameter.getName().equals(name)).findFirst().orElse(null);
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * Returns a copy of this subcommand, that is a newly instantiated subcommand with the same name, the same
     * description, and the same parameters.
     *
     * @return the copied subcommand
     */
    public InternalSubcommand copy() {
        InternalSubcommand result = new InternalSubcommand(name, description);
        booleanParameters.forEach(parameter -> result.booleanParameters.add(parameter.copy()));
        integerParameters.forEach(parameter -> result.integerParameters.add(parameter.copy()));
        stringParameters.forEach(parameter -> result.stringParameters.add(parameter.copy()));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InternalSubcommand)) {
            return false;
        }
        InternalSubcommand that = (InternalSubcommand) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

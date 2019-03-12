/*
 * This file is part of ProDisFuzz, modified on 3/12/19 8:15 PM.
 * Copyright (c) 2013-2019 Volker Nebelung <vnebelung@prodisfuzz.net>
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
 * --key1=value1 --key2=value2 ...
 */
public class InternalSubcommand implements Subcommand, ParsedSubcommand {

    Map<String, AbstractParameter<Integer>> namesToIntegerParameters = new TreeMap<>();
    Map<String, AbstractParameter<String>> namesToStringParameters = new TreeMap<>();
    Map<String, AbstractParameter<Boolean>> namesToBooleanParameters = new TreeMap<>();
    private String description;
    private String name;

    /**
     * Instantiates a new subcommand.
     *
     * @param name        the parameter's name
     * @param description the subcommand's description for the help menu
     */
    public InternalSubcommand(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public void add(Parameter<?> parameter) {
        if (parameter == null) {
            throw new IllegalArgumentException("Parameter must not be null");
        }
        switch (parameter.getClass().getSimpleName()) {
            case "IntegerParameter":
                IntegerParameter integerParameter = (IntegerParameter) parameter;
                namesToIntegerParameters.put(integerParameter.getName(), integerParameter);
                namesToStringParameters.remove(integerParameter.getName());
                namesToBooleanParameters.remove(integerParameter.getName());
                break;
            case "BooleanParameter":
                BooleanParameter booleanParameter = (BooleanParameter) parameter;
                namesToIntegerParameters.remove(booleanParameter.getName());
                namesToStringParameters.remove(booleanParameter.getName());
                namesToBooleanParameters.put(booleanParameter.getName(), booleanParameter);
                break;
            case "StringParameter":
                StringParameter stringParameter = (StringParameter) parameter;
                namesToIntegerParameters.remove(stringParameter.getName());
                namesToStringParameters.put(stringParameter.getName(), stringParameter);
                namesToBooleanParameters.remove(stringParameter.getName());
                break;
            default:
                throw new IllegalArgumentException("Parameter was not created by the command line parser");
        }
    }

    /**
     * Returns the subcommand's description.
     *
     * @return the description of the subcommand
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns all parameters ordered by their name.
     *
     * @return the parameters of this subcommand
     */
    public Set<AbstractParameter<?>> getParameters() {
        Set<AbstractParameter<?>> result = new TreeSet<>(Comparator.comparing(AbstractParameter::getName));
        result.addAll(namesToIntegerParameters.values());
        result.addAll(namesToStringParameters.values());
        result.addAll(namesToBooleanParameters.values());
        return result;
    }

    @Override
    public ParsedParameter<Integer> getIntegerParameter(String name) {
        return namesToIntegerParameters.get(name);
    }

    @Override
    public ParsedParameter<Boolean> getBooleanParameter(String name) {
        return namesToBooleanParameters.get(name);
    }

    @Override
    public ParsedParameter<String> getStringParameter(String name) {
        return namesToStringParameters.get(name);
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
}

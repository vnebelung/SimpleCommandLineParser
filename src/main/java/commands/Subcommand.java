/*
 * This file is part of ProDisFuzz, modified on 2/21/19 9:55 PM.
 * Copyright (c) 2013-2019 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package commands;

import parameters.*;

import java.util.*;

/**
 * This class is a subcommand of the command line string. The format of this string is as follows: COMMAND SUBCOMMAND
 * --key1=value1 --key2=value2 ...
 */
public class Subcommand {

    Map<String, InternalParameter<Integer>> namesToIntegerParameters = new TreeMap<>();
    Map<String, InternalParameter<String>> namesToStringParameters = new TreeMap<>();
    Map<String, InternalParameter<Boolean>> namesToBooleanParameters = new TreeMap<>();
    private String description;
    private String name;

    /**
     * Instantiates a new subcommand.
     *
     * @param name        the parameter's name
     * @param description the subcommand's description for the help menu
     */
    public Subcommand(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Adds a boolean parameter to this subcommand.
     *
     * @param parameter the parameter to be added
     */
    public void add(BooleanParameter parameter) {
        if (parameter == null) {
            throw new IllegalArgumentException("Parameter must not be null");
        }
        namesToIntegerParameters.remove(parameter.getName());
        namesToStringParameters.remove(parameter.getName());
        namesToBooleanParameters.put(parameter.getName(), parameter);
    }

    /**
     * Adds a string parameter to this subcommand.
     *
     * @param parameter the parameter to be added
     */
    public void add(StringParameter parameter) {
        if (parameter == null) {
            throw new IllegalArgumentException("Parameter must not be null");
        }
        namesToIntegerParameters.remove(parameter.getName());
        namesToBooleanParameters.remove(parameter.getName());
        namesToStringParameters.put(parameter.getName(), parameter);
    }

    /**
     * Adds an integer parameter to this subcommand.
     *
     * @param parameter the parameter to be added
     */
    public void add(IntegerParameter parameter) {
        if (parameter == null) {
            throw new IllegalArgumentException("Parameter must not be null");
        }
        namesToBooleanParameters.remove(parameter.getName());
        namesToStringParameters.remove(parameter.getName());
        namesToIntegerParameters.put(parameter.getName(), parameter);
    }

    /**
     * Returns the description of this subcommand.
     *
     * @return the command's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns all parameters ordered by their name.
     *
     * @return the parameters of this subcommand
     */
    public Set<InternalParameter<?>> getParameters() {
        Set<InternalParameter<?>> result = new TreeSet<>(Comparator.comparing(Parameter::getName));
        result.addAll(namesToIntegerParameters.values());
        result.addAll(namesToStringParameters.values());
        result.addAll(namesToBooleanParameters.values());
        return result;
    }

    /**
     * Returns the integer parameter with the given name.
     *
     * @param name the parameter's name
     * @return the integer parameter with the given name or null if no integer parameter is found
     */
    public Parameter<Integer> getIntegerParameter(String name) {
        return namesToIntegerParameters.get(name);
    }

    /**
     * Returns the boolean parameter with the given name.
     *
     * @param name the parameter's name
     * @return the boolean parameter with the given name or null if no boolean parameter is found
     */
    public Parameter<Boolean> getBooleanParameter(String name) {
        return namesToBooleanParameters.get(name);
    }

    /**
     * Returns the string parameter with the given name.
     *
     * @param name the parameter's name
     * @return the string parameter with the given name or null if no string parameter is found
     */
    public Parameter<String> getStringParameter(String name) {
        return namesToStringParameters.get(name);
    }

    /**
     * Returns the name of the subcommand.
     *
     * @return the subcommand's name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns a deep copy of this subcommand, that means a newly instantiated subcommand with a name and a
     * description, including all of it's parameters.
     *
     * @return the copied subcommand
     */
    public Subcommand copy() {
        Subcommand result = new Subcommand(name, description);
        for (Map.Entry<String, InternalParameter<Boolean>> nameToParameter : namesToBooleanParameters.entrySet()) {
            result.namesToBooleanParameters.put(nameToParameter.getKey(), nameToParameter.getValue().copy());
        }
        for (Map.Entry<String, InternalParameter<String>> nameToParameter : namesToStringParameters.entrySet()) {
            result.namesToStringParameters.put(nameToParameter.getKey(), nameToParameter.getValue().copy());
        }
        for (Map.Entry<String, InternalParameter<Integer>> nameToParameter : namesToIntegerParameters.entrySet()) {
            result.namesToIntegerParameters.put(nameToParameter.getKey(), nameToParameter.getValue().copy());
        }
        return result;
    }
}

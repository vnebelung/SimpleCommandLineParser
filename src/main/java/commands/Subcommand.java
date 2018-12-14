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
 * This class is a subcommand of the command line string. The format of this string is as follows: COMMAND SUBCOMMAND
 * --key1=value1 --key2=value2 ...
 */
public class Subcommand {

    private Map<String, Parameter> parameters = new TreeMap<>();
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
     * Adds a parameter to this subcommand.
     *
     * @param parameter the parameter to be added
     */
    public void add(Parameter parameter) {
        if (parameter == null) {
            throw new IllegalArgumentException("Parameter must not be null");
        }
        parameters.put(parameter.getName(), parameter);
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
    public Set<Parameter> getParameters() {
        Set<Parameter> result = new TreeSet<>(Comparator.comparing(Parameter::getName));
        result.addAll(parameters.values());
        return result;
    }

    /**
     * Returns the parameter with the given name.
     *
     * @param name the parameter's name
     * @return the parameter with the given name or null if no parameter is found
     */
    public Parameter getParameter(String name) {
        return parameters.get(name);
    }

    /**
     * Returns the name of the subcommand.
     *
     * @return the subcommand's name
     */
    public String getName() {
        return name;
    }

}

/*
 * This file is part of ProDisFuzz, modified on 19.10.24, 01:50.
 * Copyright (c) 2013-2024 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package main;

import internal.parameters.BooleanParameter;
import internal.parameters.IntegerParameter;
import internal.parameters.StringParameter;

/**
 * This class is the factory for generating parameters. Every parameter can have one of the following value types:
 * integer, string, or boolean.
 */
public class ParameterFactory {

    private ParameterFactory() {
    }

    /**
     * Creates a new boolean parameter with a given name and description. The parameter must be attached to a
     * command or a subcommand and is used to define a key value pair that a user can use to submit a boolean input. The
     * key is defined by the given name. The parameter is by default mandatory. If the parameter shall be optional,
     * you must set its default value.
     *
     * @param name        the parameter's name
     * @param description the parameter's description for the help menu
     * @return the created boolean parameter
     */
    public static Parameter<Boolean> createBooleanParameter(String name, String description) {
        return new BooleanParameter(name, description);
    }

    /**
     * Creates a new integer parameter with a given name and description. The parameter must be attached to a
     * command or a subcommand and is used to define a key value pair that a user can use to submit an integer input.
     * The key is defined by the given name. The parameter is by default mandatory. If the parameter shall be optional,
     * you must set its default value.
     *
     * @param name        the parameter's name
     * @param description the parameter's description for the help menu
     * @return the created integer parameter
     */
    public static Parameter<Integer> createIntegerParameter(String name, String description) {
        return new IntegerParameter(name, description);
    }

    /**
     * Creates a new integer parameter with a given name, description, and minimum and maximum boundaries. The
     * parameter must be attached to a command or a subcommand and is used to define a key value pair that a user can
     * use to submit an integer input. The key is defined by the given name. The parameter is by default mandatory.
     * If the parameter shall be optional, you must set its default value.
     *
     * @param name         the parameter's name
     * @param description  the parameter's description for the help menu
     * @param minInclusive the parameter's allowed minimum value (inclusive)
     * @param maxInclusive the parameter's allowed maximum value (inclusive)
     * @return the created integer parameter
     */
    public static Parameter<Integer> createIntegerParameter(String name, String description, int minInclusive,
                                                            int maxInclusive) {
        return new IntegerParameter(name, description, minInclusive, maxInclusive);
    }

    /**
     * Creates a new string parameter with a given name, description and (optional) allowed values. The parameter must
     * be attached to a command or a subcommand and is used to define a key value pair that a user can use to submit
     * a string input. The key is defined by the given name. The parameter is by default mandatory. If the parameter
     * shall be optional, you must set its default value.
     *
     * @param name        the parameter's name
     * @param description the parameter's description for the help menu
     * @param values the parameter's allowed values
     * @return the created string parameter
     */
    public static Parameter<String> createStringParameter(String name, String description, String... values) {
        return new StringParameter(name, description, values);
    }
}

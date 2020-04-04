/*
 * This file is part of ProDisFuzz, modified on 04.04.20, 22:47.
 * Copyright (c) 2013-2020 Volker Nebelung <vnebelung@prodisfuzz.net>
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
     */
    public static Parameter<Integer> createIntegerParameter(String name, String description) {
        return new IntegerParameter(name, description);
    }

    /**
     * Creates a new string parameter with a given name and description. The parameter must be attached to a
     * command or a subcommand and is used to define a key value pair that a user can use to submit a string input. The
     * key is defined by the given name. The parameter is by default mandatory. If the parameter shall be optional,
     * you must set its default value.
     *
     * @param name        the parameter's name
     * @param description the parameter's description for the help menu
     */
    public static Parameter<String> createStringParameter(String name, String description) {
        return new StringParameter(name, description);
    }
}

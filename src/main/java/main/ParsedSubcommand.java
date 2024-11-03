/*
 * This file is part of ProDisFuzz, modified on 30.10.24, 23:19.
 * Copyright (c) 2013-2024 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package main;

import java.nio.file.Path;

/**
 * This interface represents a parsed subcommand of a command line string.
 */
public interface ParsedSubcommand {

    /**
     * Returns the integer parameter with the given name that are assigned to this subcommand.
     *
     * @param name the parameter's name
     * @return the integer parameter with the given name or null if no integer parameter is found
     */
    ParsedParameter<Integer> getIntegerParameter(String name);

    /**
     * Returns the boolean parameter with the given name that are assigned to this subcommand.
     *
     * @param name the parameter's name
     * @return the boolean parameter with the given name or null if no boolean parameter is found
     */
    ParsedParameter<Boolean> getBooleanParameter(String name);

    /**
     * Returns the string parameter with the given name that are assigned to this subcommand.
     *
     * @param name the parameter's name
     * @return the string parameter with the given name or null if no string parameter is found
     */
    ParsedParameter<String> getStringParameter(String name);

    /**
     * Returns the path parameter with the given name that are assigned to this subcommand.
     *
     * @param name the parameter's name
     * @return the path parameter with the given name or null if no path parameter is found
     */
    ParsedParameter<Path> getPathParameter(String name);

    /**
     * Returns the double parameter with the given name that are assigned to this subcommand.
     *
     * @param name the parameter's name
     * @return the double parameter with the given name or null if no double parameter is found
     */
    ParsedParameter<Double> getDoubleParameter(String name);

    /**
     * Returns the name of the subcommand.
     *
     * @return the subcommand's name
     */
    String getName();

}

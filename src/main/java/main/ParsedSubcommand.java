/*
 * This file is part of ProDisFuzz, modified on 05.01.20, 10:25.
 * Copyright (c) 2013-2020 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package main;

/**
 * This interface represents a parsed command of a command line string. The parsed command summarizes
 */
public interface ParsedSubcommand {

    /**
     * Returns the integer parameter with the given name.
     *
     * @param name the parameter's name
     * @return the integer parameter with the given name or null if no integer parameter is found
     */
    ParsedParameter<Integer> getIntegerParameter(String name);

    /**
     * Returns the boolean parameter with the given name.
     *
     * @param name the parameter's name
     * @return the boolean parameter with the given name or null if no boolean parameter is found
     */
    ParsedParameter<Boolean> getBooleanParameter(String name);

    /**
     * Returns the string parameter with the given name.
     *
     * @param name the parameter's name
     * @return the string parameter with the given name or null if no string parameter is found
     */
    ParsedParameter<String> getStringParameter(String name);

    /**
     * Returns the name of the subcommand.
     *
     * @return the subcommand's name
     */
    String getName();

}

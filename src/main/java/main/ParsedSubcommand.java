/*
 * This file is part of ProDisFuzz, modified on 29.03.20, 23:33.
 * Copyright (c) 2013-2020 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package main;

/**
 * This interface represents a parsed subcommand of a command line string.
 */
public interface ParsedSubcommand {

    /**
     * Returns the integer parameter with the given name that are assigned to this subcommand.
     *
     * @param name the parameter's name
     * @return the integer parameter with the given name
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
     * Returns the name of the subcommand.
     *
     * @return the subcommand's name
     */
    String getName();

}

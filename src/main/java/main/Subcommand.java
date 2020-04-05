/*
 * This file is part of ProDisFuzz, modified on 04.04.20, 23:34.
 * Copyright (c) 2013-2020 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package main;

import java.util.Set;

/**
 * This interface represents a subcommand of the command line string, which is as follows: COMMAND SUBCOMMAND
 * -key1 value1 -key2 value2 …
 */
public interface Subcommand {

    /**
     * Returns all parameters of this subcommand ordered by their name.
     *
     * @return the subcommand's parameters
     */
    Set<Parameter<?>> getParameters();

    /**
     * Adds a parameter to this subcommand.
     *
     * @param parameter the parameter to be added
     * @return true if this subcommand did not already contain the given parameter
     */
    boolean add(Parameter<?> parameter);

    /**
     * Returns the description of this subcommand.
     *
     * @return the subcommand's description
     */
    String getDescription();

    /**
     * Returns the name of the subcommand.
     *
     * @return the subcommand's name
     */
    String getName();

}

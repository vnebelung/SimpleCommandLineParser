/*
 * This file is part of ProDisFuzz, modified on 3/18/19 12:48 AM.
 * Copyright (c) 2013-2019 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package main;

/**
 * This interface represents a parameter that was successfully parsed from the user-provided command line string.
 *
 * @param <V> the class of the parameter's value
 */
public interface ParsedParameter<V> {

    /**
     * Returns the name of the parameter.
     *
     * @return the parameter's name
     */
    String getName();

    /**
     * Returns the value of the parameter.
     *
     * @return the parameter's value
     */
    V getValue();
}

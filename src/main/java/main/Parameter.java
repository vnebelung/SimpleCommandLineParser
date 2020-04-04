/*
 * This file is part of ProDisFuzz, modified on 26.03.20, 22:09.
 * Copyright (c) 2013-2020 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package main;

import internal.parameters.AbstractParameter;

/**
 * This interface represents a parameter of the command line string used in the command line call. The type parameter
 * T is the class of the parameter's value. It can be one of the following: Boolean, String, or Integer.
 *
 * @param <V> the class of the parameter's value
 */
public interface Parameter<V> {

    /**
     * Returns the description of this parameter.
     *
     * @return the parameter's description
     */
    String getDescription();

    /**
     * Returns whether this parameter is mandatory or optional.
     *
     * @return true if the parameter is optional
     */
    boolean isOptional();

    /**
     * Returns the value of the parameter.
     *
     * @return the parameter's value
     */
    V getValue();

    /**
     * Returns the name of the parameter.
     *
     * @return the parameter's name
     */
    String getName();

    /**
     * Sets the default value for this parameter and returns the parameter. This will make the parameter an optional
     * parameter. If the user does not provide this parameter via the command line, the given default value will be
     * used.
     *
     * @param value the default value
     * @return the parameter with the default value
     */
    AbstractParameter<V> makeOptional(V value);

}

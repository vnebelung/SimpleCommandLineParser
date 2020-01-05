/*
 * This file is part of ProDisFuzz, modified on 05.01.20, 10:25.
 * Copyright (c) 2013-2020 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package main;

/**
 * This interface represents a parameter that can be attached to a (sub)command.
 *
 * @param <V> the class of the parameter's value
 */
public interface Parameter<V> {

    /**
     * Sets the default value for this parameter and returns the parameter. This will make the parameter an optional
     * parameter. If the user does not provide this parameter via the command line, the given default value will be
     * used.
     *
     * @param value the default value
     * @return the parameter with the default value
     */
    Parameter<V> withDefaultValue(V value);

}

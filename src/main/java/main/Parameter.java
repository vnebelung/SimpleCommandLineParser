/*
 * This file is part of ProDisFuzz, modified on 3/13/19 12:17 AM.
 * Copyright (c) 2013-2019 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package main;

/**
 * This interface represents a parameter that can be attached to a (sub)command.
 */
public interface Parameter<T> {

    /**
     * Sets the default value for this parameter and returns the parameter. This will make the parameter an optional
     * parameter. If the user does not provide this parameter via the command line, the given default value will be
     * used.
     *
     * @param value the default value
     * @return the parameter with the default value
     */
    Parameter<T> withDefaultValue(T value);

}

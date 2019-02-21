/*
 * This file is part of ProDisFuzz, modified on 2/21/19 9:55 PM.
 * Copyright (c) 2013-2019 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package parameters;

/**
 * This interface represents a parameter that is attached to a (sub)command.
 */
public interface Parameter<T> {

    /**
     * Returns the description of this parameter.
     *
     * @return the parameter's description
     */
    String getDescription();

    /**
     * Returns the value of the parameter.
     *
     * @return the parameter's value
     */
    T getValue();

    /**
     * Returns the name of the parameter.
     *
     * @return the parameter's name
     */
    String getName();

}

/*
 * This file is part of ProDisFuzz, modified on 2/17/19 11:16 PM.
 * Copyright (c) 2013-2019 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package parameters;

import main.ParameterException;

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
     * Sets the value of the parameter. If the given value cannot be cast to the parameter's type parameter, a
     * ParameterException is thrown.
     *
     * @param value the new parameter's value
     * @throws ParameterException if the given value cannot be cast to the parameter's type parameter
     */
    void setValue(String value) throws ParameterException;

    /**
     * Returns whether this parameter is mandatory or optional.
     *
     * @return true if the parameter is mandatory
     */
    boolean isMandatory();

    /**
     * Returns the name of the parameter.
     *
     * @return the parameter's name
     */
    String getName();

    /**
     * Returns a plain copy of this parameter, that means a newly instantiated parameter with the same name,
     * description, and value.
     *
     * @return the copied parameter
     */
    Parameter<T> copy();
}

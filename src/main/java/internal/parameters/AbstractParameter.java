/*
 * This file is part of ProDisFuzz, modified on 05.01.20, 10:25.
 * Copyright (c) 2013-2020 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package internal.parameters;

import main.Parameter;
import main.ParameterException;
import main.ParsedParameter;

/**
 * This class is an abstract parameter of the command line string used in the command line call. The format of this
 * string is as follows: COMMAND SUBCOMMAND --key1 value1 --key2 value2 … The type parameter T is the class of the
 * parameter's value.
 *
 * @param <V> the class of the parameter's value
 */
public abstract class AbstractParameter<V> implements Parameter<V>, ParsedParameter<V> {

    private V value;
    private String description;
    private boolean isMandatory;
    private String name;

    /**
     * Instantiates a new mandatory parameter with a given description.
     *
     * @param name        the parameter's name
     * @param description the parameter's description for the help menu
     */
    AbstractParameter(String name, String description) {
        this.name = name;
        this.description = description;
        isMandatory = true;
    }

    /**
     * Returns the description of this parameter.
     *
     * @return the parameter's description
     */
    public String getDescription() {
        return description;
    }

    @Override
    public V getValue() {
        return value;
    }

    /**
     * Sets the value of the parameter. If the given value cannot be cast to the parameter's type parameter, a
     * ParameterException is thrown.
     *
     * @param value the new parameter's value
     * @throws ParameterException if the given value cannot be cast to the parameter's type parameter
     */
    public abstract void setValue(String value) throws ParameterException;

    /**
     * Sets the value of the parameter to the given cast value.
     *
     * @param value the parameter's cast value
     */
    void setInternalValue(V value) {
        this.value = value;
    }

    /**
     * Returns whether this parameter is mandatory or optional.
     *
     * @return true if the parameter is mandatory
     */
    public boolean isMandatory() {
        return isMandatory;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public AbstractParameter<V> withDefaultValue(V value) {
        this.value = value;
        isMandatory = false;
        return this;
    }

}

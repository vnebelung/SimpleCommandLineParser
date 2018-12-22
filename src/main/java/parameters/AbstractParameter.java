/*
 * This file is part of ProDisFuzz, modified on 12/18/18 12:44 PM.
 * Copyright (c) 2013-2018 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package parameters;

import main.ParameterException;

/**
 * This class is an abstract parameter of the command line string used in the command line call. The format of this
 * string is as follows: COMMAND SUBCOMMAND --key1 value1 --key2 value2 ... The type parameter T is the class of the
 * parameter's value.
 */
abstract class AbstractParameter<T> implements Parameter<T> {

    private T value;
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
     * Instantiates a new optional parameter with a given description and a given default value that is used when no
     * user-defined value is provided.
     *
     * @param name         the parameter's name
     * @param description  the parameter's description for the help menu
     * @param defaultValue the parameter's default value
     */
    AbstractParameter(String name, String description, T defaultValue) {
        this.name = name;
        this.description = description;
        value = defaultValue;
        isMandatory = false;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public abstract void setValue(String value) throws ParameterException;

    /**
     * Sets the value of the parameter to the given cast value.
     *
     * @param value the parameter's cast value
     */
    void setInternalValue(T value) {
        this.value = value;
    }

    @Override
    public boolean isMandatory() {
        return isMandatory;
    }

    @Override
    public String getName() {
        return name;
    }

}

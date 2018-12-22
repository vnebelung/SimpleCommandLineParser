/*
 * This file is part of ProDisFuzz, modified on 12/21/18 6:01 PM.
 * Copyright (c) 2013-2018 Volker Nebelung <vnebelung@prodisfuzz.net>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package parameters;

import main.ParameterException;

/**
 * This class represents an integer parameter of the command line.
 */
public class IntegerParameter extends AbstractParameter<Integer> {

    /**
     * Instantiates a new mandatory integer parameter.
     *
     * @param name        the parameter's name
     * @param description the parameter's description for the help menu
     */
    public IntegerParameter(String name, String description) {
        super(name, description);
    }

    /**
     * Instantiates a new optional integer parameter.
     *
     * @param name         the parameter's name
     * @param description  the parameter's description for the help menu
     * @param defaultValue the parameter's default value
     */
    public IntegerParameter(String name, String description, int defaultValue) {
        super(name, description, defaultValue);
    }

    @Override
    public void setValue(String value) throws ParameterException {
        if (value.isBlank()) {
            throw new ParameterException("The parameter's value must not be empty");
        }
        try {
            setInternalValue(Integer.valueOf(value));
        } catch (NumberFormatException ignored) {
            throw new ParameterException("The parameter's value is not a valid integer");
        }
    }

    @Override
    public Parameter<Integer> copy() {
        return getValue() == null ? new IntegerParameter(getName(), getDescription()) :
                new IntegerParameter(getName(), getDescription(), getValue());
    }

}
